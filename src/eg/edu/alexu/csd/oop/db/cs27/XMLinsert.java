package eg.edu.alexu.csd.oop.db.cs27;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XMLinsert {
	private LinkedList<String> validator = new LinkedList<>();
	private LinkedList<String> dataType = new LinkedList<>();
	private String[] seekDataType;
	private LinkedList<String> keysI;
	private LinkedList<String> valuesI;

	public void insertInto(String dbPath, String tablePath, LinkedList<String> keys, LinkedList<String> values) {

		try {
			File table = getTable(dbPath, tablePath);
			Reader tableReader = new InputStreamReader(new FileInputStream(table),"ISO-8859-1" );
			InputSource input = new InputSource(tableReader);
			input.setEncoding("ISO-8859-1");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			StringReader x = new StringReader(MyDatabase.tables.get(tablePath.toLowerCase()));
			XMLInputFactory factory = XMLInputFactory.newInstance();
			//XMLEventReader eventReader = factory.createXMLEventReader(x);
			XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(table));

			getDummyRow(eventReader);
			getOrder(eventReader);
			setOrder(keys, values);

			for (int i = 0; i < keysI.size(); i++)
				keysI.set(i, keysI.get(i).toLowerCase());
			//Document doc = docBuilder.parse(
				//	new InputSource(new ByteArrayInputStream(MyDatabase.tables.get(tablePath).getBytes("ISO-8859-1"))));
			Document doc = docBuilder.parse(input);

			Element root = doc.getDocumentElement();

			Element newElement = doc.createElement("row");
			int lastId = Integer
					.parseInt(root.getElementsByTagName("row").item(root.getElementsByTagName("row").getLength() - 1)
							.getAttributes().getNamedItem("id").getNodeValue());
			newElement.setAttribute("id", String.valueOf(lastId + 1));
			for (int i = 0; i < keysI.size(); i++) {
				Node n = doc.createElement(keysI.get(i));
				checkValue(valuesI.get(i), i);
				n.setTextContent(valuesI.get(i));
				newElement.appendChild(n);
			}
			root.appendChild(newElement);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			StringWriter y = new StringWriter();
			StreamResult result = new StreamResult(y);
			StreamResult resultT = new StreamResult(table);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			transformer.transform(new DOMSource(doc), resultT);
			transformer.transform(new DOMSource(doc), result);
			MyDatabase.tables.put(tablePath.toLowerCase(), y.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// throw new
		// RuntimeException(MyDatabase.tables.get(tablePath.toLowerCase()));
	}

	private File getTable(String dbPath, String tablePath) {
		File db = new File(dbPath);
		File table = null;
		if (db.exists() && db.isDirectory()) {
			String extendedTable = new String(tablePath);
			if (!extendedTable.endsWith(".xml")) {
				extendedTable = extendedTable.concat(".xml");
				table = filterFiles(db.listFiles(), extendedTable);
			} else {
				table = filterFiles(db.listFiles(), extendedTable);
			}

		} else {
			throw null;
		}
		return table;
	}

	private File filterFiles(File[] filesToBeFiltered, String seekTable) {
		for (File f : filesToBeFiltered) {
			if (f.getName().equalsIgnoreCase(seekTable)) {
				return f;
			}
		}
		return null;
	}

	private void getOrder(XMLEventReader reader) throws XMLStreamException {
		LinkedList<String> x = new LinkedList<>();
		XMLEvent tempEvent = null;
		x = new LinkedList<>();
		try {
			tempEvent = reader.nextEvent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (reader.hasNext()) {
			try {
				tempEvent = reader.nextEvent();
				if (tempEvent.getEventType() == XMLStreamConstants.END_ELEMENT
						&& tempEvent.asEndElement().getName().toString().equals("row")) {
					break;
				} else if (tempEvent.getEventType() == XMLStreamConstants.START_ELEMENT) {
					x.add(tempEvent.asStartElement().getName().toString());
					validator.add(tempEvent.asStartElement().getName().toString());
					dataType.add(reader.nextEvent().asCharacters().getData());
				}
			} catch (Exception e) {
				throw null;
			}
		}
		getDatatype(validator, x);
	}

	private void getDatatype(LinkedList<String> validator, LinkedList<String> seekColumns) {
		seekDataType = new String[seekColumns.size()];
		int i = 0;
		for (String string : seekColumns) {
			seekDataType[i++] = dataType.get(validator.indexOf(string));

		}
	}

	private void getDummyRow(XMLEventReader eventReader) throws XMLStreamException {
		XMLEvent tempEvent = null;
		while (eventReader.hasNext()) {
			tempEvent = eventReader.nextEvent();
			if (tempEvent.getEventType() == XMLStreamConstants.START_ELEMENT
					&& tempEvent.asStartElement().getName().toString().equals("row")) {

				break;
			}
		}
	}

	private void setOrder(LinkedList<String> keys, LinkedList<String> values) throws XMLStreamException {
		LinkedList<String> tempKeys = new LinkedList<>();
		LinkedList<String> tempValues = new LinkedList<>();
		if (keys == null) {
			keys = validator;
			if (keys.size() < values.size()) {
				throw null;
			} else {
				while (keys.size() != values.size()) {
					values.add(getSuitableDefault(values.size()));
				}

			}

			keysI = keys;
			valuesI = values;
		} else if (keys.size() == values.size()) {
			for (int i = 0; i < validator.size(); i++) {
				String string = validator.get(i);
				if (keys.contains(string.toLowerCase())) {
					tempKeys.add(string.toLowerCase());
					tempValues.add(values.get(keys.indexOf(string.toLowerCase())));
				} else {
					tempKeys.add(string);
					tempValues.add(getSuitableDefault(i));
				}
			}
			keysI = tempKeys;
			valuesI = tempValues;
		}

	}

	private String getSuitableDefault(int index) {
		if (seekDataType[index].equalsIgnoreCase("integer")) {
			return "0";
		} else {
			return "null";
		}
	}

	private void checkValue(String x, int index) {
		if (seekDataType[index].equalsIgnoreCase("integer")) {
			try {
				Integer.parseInt(x);
			} catch (Exception e) {
				throw null;
			}
		} else {
			try {
				Integer.parseInt(x);
				throw null;
			} catch (NumberFormatException e) {

			}
		}
	}
}