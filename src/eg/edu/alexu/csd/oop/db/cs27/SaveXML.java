package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.util.Map;



import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.OutputKeys;

import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;



import org.w3c.dom.Attr;

import org.w3c.dom.Document;

import org.w3c.dom.Element;



public class SaveXML {

	public void save(Table table) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder = factory.newDocumentBuilder();

		Document document = documentBuilder.newDocument();



		/* starting Node */

		Element tableTag = document.createElement("table");

		

		Element contents = document.createElement("columns");

		

		for (Map.Entry<String, String> entry : table.getMap().entrySet()) {

			Element col = document.createElement("col");

			Attr y = document.createAttribute("key");

			y.setValue(entry.getKey());

			Attr x = document.createAttribute("value");

			x.setValue(entry.getValue());

			col.setAttributeNode(x);

			col.setAttributeNode(y);

			contents.appendChild(col);

		}

		tableTag.appendChild(contents);

		

		document.appendChild(tableTag);



		for (Row row : table.getRows()) {

			Element element = document.createElement("row");



			for (Map.Entry<String, String> entry : row.getAllAttributes().entrySet()) {

				Element rowCol = document.createElement("col");

				Attr x = document.createAttribute("key");

				x.setValue(entry.getKey());

				

				Attr y = document.createAttribute("value");

				y.setValue(entry.getValue());

				rowCol.setAttributeNode(x);

				rowCol.setAttributeNode(y);

				element.appendChild(rowCol);

			}



			tableTag.appendChild(element);

		}



		TransformerFactory tFactory = TransformerFactory.newInstance();

		Transformer transformer = tFactory.newTransformer();



		DOMSource source = new DOMSource(document);



		StreamResult streamResult = new StreamResult(new File(table.getPath()));



		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "./table.dtd");



		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");



		transformer.transform(source, streamResult);

	}



}