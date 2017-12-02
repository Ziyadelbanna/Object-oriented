package eg.edu.alexu.csd.oop.db.cs27;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class xmlWritrer {
	public void createTable(String database, String name, ArrayList<String> columnsName, ArrayList<String> dataType) {
		//System.out.println(database);
		if (database == null || database.length() == 0) {
			throw new IndexOutOfBoundsException();
		}
		File direc= new File(database);
		for(File f : direc.listFiles())
		{
			if(f.getName().equalsIgnoreCase(name+".xml"))
				throw null;
		}
		if (database == null || database.length() == 0||!direc.exists()) {
			throw new IndexOutOfBoundsException();
		}
		
		try {
			/*
			 * if(database == null) throw null;
			 */
			StringWriter x = new StringWriter();
			// File file = new File(name+".xml");

			/*
			 * DTD dtd = new DTD(); dtd.addDTDElement("table");
			 * dtd.addDTDChildOrDT("row*", true); dtd.addDTDElement("row");
			 * 
			 * for (int i = 0; i < columnsName.size(); i++) {
			 * dtd.addDTDChildOrDT(columnsName.get(i).toLowerCase(), i ==
			 * columnsName.size() - 1); } for (int i = 0; i <
			 * columnsName.size(); i++) {
			 * dtd.addDTDElement(columnsName.get(i).toLowerCase());
			 * dtd.addDTDChildOrDT("PCDATA", true); } dtd.addAttr("row", "id",
			 * "PCDATA");
			 * 
			 * // dtd.saveDTD(database+"\\"+name);
			 */
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
		//	DOMImplementation impl = doc.getImplementation();
			/*
			 * DocumentType doctype = impl.createDocumentType("doctype", "",
			 * name+".dtd");
			 */

			Element root = doc.createElement("table");
			doc.appendChild(root);
			Element row = doc.createElement("row");
			Attr id = doc.createAttribute("id");
			id.setValue("-1");
			row.setAttributeNode(id);
			root.appendChild(row);
			for (int i = 0; i < columnsName.size(); i++) {
				Element column = doc.createElement(columnsName.get(i).toLowerCase());
				if (dataType.get(i).equalsIgnoreCase("integer"))
					column.setTextContent("integer");
				else {
					column.setTextContent("string");

				}
				row.appendChild(column);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			// transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
			// doctype.getSystemId());

			StreamResult result = new StreamResult(x);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			MyDatabase.tables.put(name.toLowerCase(), x.toString());
			result=new StreamResult(new File(database+File.separator+name+".xml"));
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			throw null;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			throw null;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			throw null;
		}

	}

}