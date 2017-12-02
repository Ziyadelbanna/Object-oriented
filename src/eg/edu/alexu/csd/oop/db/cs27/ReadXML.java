package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.util.LinkedHashMap;

import java.util.Map;



import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;



import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import org.xml.sax.ErrorHandler;

import org.xml.sax.SAXException;

import org.xml.sax.SAXParseException;



public class ReadXML {

	public Table read(String fileName) throws Exception {



		File xmlFile = new File(fileName);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setValidating(true);

		DocumentBuilder documentBuilder = factory.newDocumentBuilder();

		

		documentBuilder.setErrorHandler(new ErrorHandler()  {

			

			@Override

			public void warning(SAXParseException exception) throws SAXException {

				return;

				

			}

			

			@Override

			public void fatalError(SAXParseException exception) throws SAXException {

				return;

				

			}

			

			@Override

			public void error(SAXParseException exception) throws SAXException {

				return;

				

			}

		});

		

		

		Document document = documentBuilder.parse(xmlFile);



		NodeList list = document.getElementsByTagName("table");



		Node node = list.item(0);



		Table ans = null;



		if (node.getNodeType() == Node.ELEMENT_NODE) {



			Element tableTag = (Element) node;

			Map<String, String> tableMap = new LinkedHashMap<>();



			NodeList childs = tableTag.getChildNodes();

			



			NodeList col = document.getElementsByTagName("columns");



			Node xsx = col.item(0);



			if (xsx.getNodeType() == Node.ELEMENT_NODE) {

			//	Element columTag = (Element) xsx;



				NodeList columns = xsx.getChildNodes();



				for (int i = 0; i < columns.getLength(); i++) {

					Node property = columns.item(i);

					if (property.getNodeType() == Node.ELEMENT_NODE) {

						Element please = (Element) property;

						String key = please.getAttribute("key");

						String value = please.getAttribute("value");

						tableMap.put(key, value);

					}



				}



				ans = new Table(fileName);

				ans.setMap(tableMap);

			}



			for (int i = 0; i < childs.getLength(); i++) {

				Node n = childs.item(i);

				if (n.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) n;

					if (!element.getTagName().equals("row")) {

						continue;

					}

					NodeList rowCol = element.getChildNodes();

					Map<String, String> rowMap = new LinkedHashMap<>();

					for (int k = 0; k < rowCol.getLength(); k++) {

						Node hamada = rowCol.item(k);

						if (hamada.getNodeType() == Node.ELEMENT_NODE) {

							Element rowElement = (Element) hamada;

							String key = rowElement.getAttribute("key");

							String value = rowElement.getAttribute("value");

							rowMap.put(key, value);

						}

					}

					

					Row row = new Row(rowMap);

					ans.addRow(row);

				}

			}

		}

		return ans;

	}

}