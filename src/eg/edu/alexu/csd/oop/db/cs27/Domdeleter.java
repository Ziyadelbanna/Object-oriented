package eg.edu.alexu.csd.oop.db.cs27;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Domdeleter {
	public int deleteTable(String database, String name, String[] condition) {
		Set<Element> targetElements = new HashSet<Element>();
		int temp = 0;
		name = name.toLowerCase();
		if (!MyDatabase.tables.containsKey(name))
			return 0;
		try {
			InputStream inputStream;
			Reader reader = null;
			try {
				inputStream = new FileInputStream(database+File.separator+name+ ".xml");
				try {
					reader = new InputStreamReader(inputStream, "ISO-8859-1");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			InputSource is = new InputSource(reader);
			is.setEncoding("ISO-8859-1");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder
					.parse(new InputSource(new ByteArrayInputStream(MyDatabase.tables.get(name).getBytes("utf-8"))));
			// System.out.println("khk"+MyDatabase.tables.get(name));
			NodeList list = doc.getElementsByTagName("row");
			if (condition.length == 0) {
				for (int i = 1; i < list.getLength(); i++) {
					if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element row = (Element) list.item(i);
						{
							targetElements.add(row);
						}
					}
				}
			} else if (condition[0].equals("id")) {
				for (int i = 0; i < list.getLength(); i++) {
					if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element row = (Element) list.item(i);
						{
							if (check(row.getAttribute("id"), condition)) {
								targetElements.add(row);

							}
						}
					}
				}

			} else {
				for (int i = 0; i < list.getLength(); i++) {
					if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element row = (Element) list.item(i);
						NodeList columns = row.getChildNodes();
						for (int j = 0; j < columns.getLength(); j++) {
							if (columns.item(j).getNodeType() == Node.ELEMENT_NODE) {

								Element col = (Element) columns.item(j);
								if (col.getTagName().equals(condition[0])) {
									// System.out.println(col.getTagName());
									if (check(col.getTextContent(), condition)) {
										targetElements.add(row);
									}

								}
							}
						}

					}
				}

			}
			temp = targetElements.size();
			for (Element e : targetElements) {
				e.getParentNode().removeChild(e);
			}
			// write the content on console
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StringWriter x = new StringWriter();
			StreamResult result = new StreamResult(x);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

			transformer.transform(source, result);
			
			MyDatabase.tables.put(name, x.toString());
			
			result= new StreamResult(new File(database+ File.separator+name+".xml"));
			
			transformer.transform(source, result);
		
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return temp;
	}

	private boolean check(String a, String[] c) {
		if (c[1].equals("=")) {
			return a.matches(c[2]);
		} else if (c[1].equals(">")) {
			return (Integer.valueOf(a) > Integer.valueOf(c[2]));
		} else if (c[1].equals("<")) {
			return (Integer.valueOf(a) < Integer.valueOf(c[2]));
		}

		return false;

	}
}