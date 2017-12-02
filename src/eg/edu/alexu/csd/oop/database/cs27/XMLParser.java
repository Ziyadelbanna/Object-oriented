package eg.edu.alexu.csd.oop.database.cs27;



import java.io.BufferedWriter;

import java.io.File;

import java.io.FileWriter;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Hashtable;

import java.util.LinkedList;

import java.util.List;

import java.util.Map;



import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;

import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerException;

import javax.xml.transform.TransformerFactory;

import javax.xml.transform.TransformerFactoryConfigurationError;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;



import org.w3c.dom.Document;

import org.w3c.dom.DocumentType;

import org.w3c.dom.Element;

import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;



import eg.edu.alexu.csd.oop.database.cs27.Row;

import eg.edu.alexu.csd.oop.database.cs27.Table;

import eg.edu.alexu.csd.oop.database.cs27.Interpreter;



public class XMLParser {



	public void writeXML(String tablePath, Table table) throws ParserConfigurationException,

			TransformerFactoryConfigurationError, TransformerException, NullPointerException {



		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder icBuilder;

		icBuilder = icFactory.newDocumentBuilder();

		Document doc = icBuilder.newDocument();

		Element mainRootElement = doc.createElement("table");

		doc.appendChild(mainRootElement);



		String[] colNames = table.getColNames();

		Map<String, Class<? extends Comparable<?>>> colTypes = table.getColTypes();

		List<Row> rows = table.getAllRows();

		

		// write colTypes header Tags

		Element typesTag = doc.createElement("colTypes");

		for (String colName : colNames) {

			Element col = doc.createElement(colName);

			col.setTextContent(Interpreter.getSQLType(colTypes.get(colName)));

			typesTag.appendChild(col);

		}

		mainRootElement.appendChild(typesTag);



		// write rows content

		Element tableTag = doc.createElement("tableRows");

		for (Row m : rows) {

			Element rowNode = doc.createElement("row");

			for (String colName : colNames) {

				Element node = doc.createElement(colName);

				Comparable<?> value = m.get(colName);

				node.appendChild(doc.createTextNode(value.toString()));

				rowNode.appendChild(node);

			}

			tableTag.appendChild(rowNode);

		}

		mainRootElement.appendChild(tableTag);



		// output DOM XML

		this.generateSchema(tablePath, colTypes);

		DocumentType docType = doc.getImplementation().createDocumentType("doctype", "", tablePath.replace(".xml", ".dtd"));

		Transformer transformer = TransformerFactory.newInstance().newTransformer();

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, docType.getPublicId());

		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());

		DOMSource source = new DOMSource(doc);

		StreamResult console = new StreamResult(tablePath);

		transformer.transform(source, console);

	}



	public Table readXML(String tablePath) throws ParserConfigurationException, SAXException, IOException {



		String tableName = getTableName(tablePath);



		File fXmlFile = new File(tablePath);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();

		Element root = doc.getDocumentElement();



		List<Row> rows = new ArrayList<>();

		List<String> colNamesList = new LinkedList<>();

		String[] colNamesArr;

		Map<String, Class<? extends Comparable<?>>> colTypes = new Hashtable<>();



		// read colTypes

		Node typesTag = root.getElementsByTagName("colTypes").item(0);

		NodeList typesList = typesTag.getChildNodes();

		for (int i = 0; i < typesList.getLength(); ++i) {

			Node node = typesList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				String colName = node.getNodeName();

				String type = node.getTextContent();

				colTypes.put(colName, Interpreter.getJavaClass(type));

				colNamesList.add(colName);

			}

		}

		colNamesArr = colNamesList.toArray(new String[colNamesList.size()]);



		// read rows content

		Element tableNode = (Element) root.getElementsByTagName("tableRows").item(0);

		NodeList nList = tableNode.getElementsByTagName("row");

		if (nList != null && nList.getLength() > 0) {

			for (int i = 0; i < nList.getLength(); i++) {

				Node rowNode = nList.item(i);

				if (rowNode.getNodeType() == Node.ELEMENT_NODE) {

					Element ownerNode = (Element) rowNode;

					NodeList innerList = ownerNode.getChildNodes();

					Row rowMap = new Row();

					for (int j = 0; j < innerList.getLength(); j++) {

						if (innerList.item(j).getNodeType() == Node.ELEMENT_NODE) {

							Element innerTag = (Element) innerList.item(j);

							String colName = innerTag.getNodeName();

							String strValue = innerTag.getTextContent();

							try {

								Comparable<?> value = Interpreter.getValue(strValue, colTypes.get(colName));

								rowMap.put(colName, value);

							} catch (Exception e) {

							}

						}

					}

					rows.add(rowMap);

				}

			}

		}

		Table table = new Table(tableName, colNamesArr, colTypes);

		table.addRows(rows);



		return table;

	}

	

	private void generateSchema(String tablePath, Map<String, Class<? extends Comparable<?>>> colTypes) {

		final String ELEMENT_OPEN = "<!ELEMENT ", CLOSE = ">";

		final String PCDATA = " (#PCDATA)";

		tablePath = tablePath.replace(".xml", ".dtd");

		try {

			File file = new File(tablePath);

			// if file doesn't exists, then create it

			if (!file.exists()) {

				file.createNewFile();

			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());

			BufferedWriter bw = new BufferedWriter(fw);



			bw.write(ELEMENT_OPEN + "table (colTypes, tableRows)" + CLOSE + "\n");



			StringBuilder colsNameBuilder = new StringBuilder();

			StringBuilder colsTypesBuilder = new StringBuilder();

			for (Map.Entry<String, Class<? extends Comparable<?>>> entry : colTypes.entrySet()) {

				colsNameBuilder.append(entry.getKey() + ",");

				colsTypesBuilder.append(ELEMENT_OPEN + entry.getKey() + PCDATA + CLOSE + "\n");

			}

			colsNameBuilder.deleteCharAt(colsNameBuilder.length() - 1);



			bw.write(ELEMENT_OPEN + "colTypes (");

			bw.write(colsNameBuilder.toString());

			bw.write(")" + CLOSE + "\n");



			bw.write(ELEMENT_OPEN + "tableRows ((row)*)" + CLOSE + "\n");

			bw.write(ELEMENT_OPEN + "row (");

			bw.write(colsNameBuilder.toString());

			bw.write(")" + CLOSE + "\n");



			bw.write(colsTypesBuilder.toString());



			bw.close();





		} catch (IOException e) {

			e.printStackTrace();

		}

	}



	private String getTableName(String tablePath) {

		String tableName = tablePath.substring(tablePath.lastIndexOf('/') + 1);

		tableName = tableName.replace(".xml", "");

		return tableName;

	}

}