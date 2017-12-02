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
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DomUpdater {
	Filter filter = new Filter();

	public int updatetable(String database, String name, ArrayList<String> columnsName, ArrayList<String> newValue,
			String[] condition) {
		int result = 0;

		name = name.toLowerCase();
		if (!MyDatabase.tables.containsKey(name) || MyDatabase.tables.get(name) == null)
			return 0;
		try {
			InputStream inputStream;
			Reader reader = null;
			try {
				inputStream = new FileInputStream(database+File.separator+name + ".xml");
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

			Document doc = builder.parse(is);

			// System.out.println("khk"+MyDatabase.tables.get(name));
			NodeList list = doc.getElementsByTagName("row");
			// DOMImplementation impl = doc.getImplementation();
			// DocumentType doctype = impl.createDocumentType("doctype", "",
			// name + ".dtd");
			// System.out.println("3-"+MyDatabase.tables.get(name));

			Element dummy = (Element) list.item(0);
			// System.out.println(dummy.getChildNodes().getLength());
			if (!filter.ColumnValid(dummy, columnsName)) {
				return 0;
			}
			/*
			 * if (!filter.ValueValid(dummy, newValue, columnsName)) { throw
			 * null; }
			 * 
			 */
			if (condition.length == 0) {
				for (int i = 1; i < list.getLength(); i++) {
					if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element row = (Element) list.item(i);
						{
							result++;
							NodeList columns = row.getChildNodes();
							for (int k = 0; k < columns.getLength(); k++) {
								if (columns.item(k).getNodeType() == Node.ELEMENT_NODE) {
									int l = columnsName.indexOf(columns.item(k).getNodeName());
									// System.out.println(columns.item(k).getNodeName());
									if (l != -1) {
										columns.item(k).setTextContent(newValue.get(l));
									}

								}

							}
						}
					}
				}

			} else if (condition[0].equals("id")) {
				for (int i = 1; i < list.getLength(); i++) {
					if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element row = (Element) list.item(i);
						{
							if (check(row.getAttribute("id"), condition)) {
								result++;
								NodeList columns = row.getChildNodes();
								for (int k = 0; k < columns.getLength(); k++) {
									if (columns.item(k).getNodeType() == Node.ELEMENT_NODE) {
										int l = columnsName.indexOf(columns.item(k).getNodeName());
										// System.out.println(columns.item(k).getNodeName());
										if (l != -1) {
											columns.item(k).setTextContent(newValue.get(l));
										}

									}

								}

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
								if (col.getTagName().equalsIgnoreCase(condition[0])) {
									// System.out.println(col.getTagName());
									if (check(col.getTextContent(), condition)) {
										result++;

										for (int k = 0; k < columns.getLength(); k++) {
											if (columns.item(k).getNodeType() == Node.ELEMENT_NODE) {
												int l = columnsName.indexOf(columns.item(k).getNodeName());
												// System.out.println(columns.item(k).getNodeName());
												if (l != -1) {
													columns.item(k).setTextContent(newValue.get(l));
												}

											}

										}
									}

								}
							}
						}

					}
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StringWriter x = new StringWriter();
			StreamResult result1 = new StreamResult(x);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

			transformer.transform(source, result1);
			
			MyDatabase.tables.put(name, x.toString());
			
			result1= new StreamResult(new File(database+ File.separator+name+".xml"));
			
			transformer.transform(source, result1);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			return 0;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return 0;
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			return 0;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			return 0;
		}

		return result;

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