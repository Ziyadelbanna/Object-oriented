package eg.edu.alexu.csd.oop.db.cs27
;



import java.io.File;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.util.LinkedList;



import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;

import javax.xml.soap.Node;

import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerConfigurationException;

import javax.xml.transform.TransformerException;

import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;



import org.w3c.dom.Attr;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.NamedNodeMap;

import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;



public class XML {

	private String[] types;

	private String[] names;

	private LinkedList<Object[]> table;



	public void load(String databasename, String tablename) {

		File database = new File(databasename + "/" + tablename + ".xml");

		if (database.exists() == false) {

			throw new NullPointerException();

		}

		DocumentBuilder dBuilder = null;

		try {

			dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		} catch (ParserConfigurationException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		Document doc = null;

		try {

			doc = dBuilder.parse(database);

		} catch (SAXException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		doc.getDocumentElement().normalize();

		NodeList table = doc.getChildNodes();

		NodeList list = table.item(0).getChildNodes();

		NodeList name = null;

		int con = 0;

		for (int i = 0; i < list.getLength(); i++) {

			if (list.item(i).getNodeName() != "#text") {

				name = list.item(i).getChildNodes();

				con = i;

				break;

			}

		}

		int siz = 0;

		for (int i = 0; i < name.getLength(); i++)

			if (name.item(i).getNodeName() != "#text")

				siz++;

		names = new String[siz];

		types = new String[siz];

		int e = 0;

		for (int i = 0; i < name.getLength(); i++) {

			if (name.item(i).getNodeName() == "#text")

				continue;

			// System.out.println(name.item(i).getNodeName());

			names[e] = name.item(i).getNodeName();

			NamedNodeMap nodeMap = name.item(i).getAttributes();

			types[e++] = nodeMap.item(0).getNodeValue();

			// System.out.println(names[i]+" "+types[i]);

		}

		LinkedList<Object[]> tabl = new LinkedList<>();

		for (int i = 0; i < list.getLength(); i++) {

			Object[] arr = new Object[names.length];

			if (list.item(i).getNodeName() == "#text" || con == i)

				continue;

			int idx = 0;

			NodeList nl = list.item(i).getChildNodes();

			for (int j = 0; j < nl.getLength(); j++) {

				if (nl.item(j).getNodeName() == "#text")

					continue;

				// System.out.println(nl.item(j).getTextContent());

				if (idx == arr.length)

					break;

				arr[idx++] = nl.item(j).getTextContent();

			}

			tabl.add(arr);

		}

		this.table = tabl;

	}



	public String[] getTypes() {

		return types;

	}



	public String[] getNames() {

		return names;

	}



	public LinkedList<Object[]> getTable() {

		return table;

	}



	public void setTable(LinkedList<Object[]> table) {

		this.table = table;

	}



	public void save(String databasename, String tablename, Object[][] history, String[] names, String[] types) {

		File file = new File(databasename + "/" + tablename + ".xml");

		if (file.exists() == false) {

			throw new NullPointerException();

		}

		for (int i = 0; i < history.length; i++) {

			for (int j = 0; j < history[0].length; j++) {

				if (history[i][j] == null)

					history[i][j] = "null";

			}

		}

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder docBuilder = null;

		try {

			docBuilder = docFactory.newDocumentBuilder();

		} catch (ParserConfigurationException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}



		// root elements

		Document doc = docBuilder.newDocument();

		Element rootElement = doc.createElement("table");

		Element element = doc.createElement(tablename);

		for (int j = 0; j < names.length; j++) {

			Element nextelement = doc.createElement(names[j]);

			Attr attr = doc.createAttribute("type");

			attr.setValue(types[j]);

			nextelement.setAttributeNode(attr);

			nextelement.appendChild(doc.createTextNode("null"));

			element.appendChild(nextelement);

		}

		rootElement.appendChild(element);

		for (int i = 0; i < history.length; i++) {

			element = doc.createElement(tablename);

			for (int j = 0; j < history[0].length; j++) {

				Element nextelement = doc.createElement(names[j]);

				Attr attr = doc.createAttribute("type");

				attr.setValue(types[j]);

				nextelement.setAttributeNode(attr);

				nextelement.appendChild(doc.createTextNode(history[i][j].toString()));

				element.appendChild(nextelement);

			}

			rootElement.appendChild(element);

		}

		doc.appendChild(rootElement);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		Transformer transformer = null;

		try {

			transformer = transformerFactory.newTransformer();

		} catch (TransformerConfigurationException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		DOMSource source = new DOMSource(doc);

		StreamResult result = new StreamResult(file);

		try {

			transformer.transform(source, result);

		} catch (TransformerException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

}