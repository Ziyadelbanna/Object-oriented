package eg.edu.alexu.csd.oop.db.cs27;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.xml.sax.XMLReader;

public class XMLSelect {
	private LinkedList<String> validator = new LinkedList<>();
	private LinkedList<String> dataType = new LinkedList<>();
	private String[] seekDataType;
	

	public LinkedList<Object[]> selectFrom(String dbPath, String tablePath, LinkedList<String> seekColumn,
			boolean isCondition, String conditionController, LinkedList<String> expression)
			throws XMLStreamException, FileNotFoundException {
		if(conditionController != null)
			conditionController = conditionController.toLowerCase();
		File table = getTable(dbPath, tablePath);
		LinkedList<Object[]> rows = new LinkedList<>();

		// try {
		StringReader x = new StringReader(MyDatabase.tables.get(tablePath));
		XMLInputFactory factory = XMLInputFactory.newInstance();
		//XMLEventReader eventReader = factory.createXMLEventReader(x);
		XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(table));
		boolean isSatisfying = true;
		getDummyRow(eventReader);
		if (seekColumn == null) {
			seekColumn = fillSeekColumn(eventReader);
		} else {
			skipDummyRow(eventReader);
			validate(validator, seekColumn);

		}
		if (isCondition) {
			rows.add(new Object[seekColumn.size()]);
			isSatisfying = !isCondition;
		}
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			switch (event.getEventType()) {
			case XMLStreamConstants.START_ELEMENT:
				StartElement startElement = event.asStartElement();
				if (startElement.getName().toString().equals("row")) {
					if (isSatisfying) {
						if (isCondition) {
							isSatisfying = false;
						}
						rows.add(new Object[seekColumn.size()]);
					}
					if (isCondition && conditionController.equalsIgnoreCase("id")) {
						isSatisfying = evaluateCond(startElement.getAttributeByName(QName.valueOf("id")).getValue(),
								expression);
					}
				} else if (!startElement.getName().equals("table")) {
					String key = startElement.getName().toString();
					Characters value = eventReader.nextEvent().asCharacters();
					if (!isSatisfying && key.equalsIgnoreCase(conditionController)) {
						isSatisfying = evaluateCond(value, expression);
					}
					if (seekColumn.contains(key)) {
						rows.getLast()[seekColumn.indexOf(key)] = getElementDataTyped(value.getData(),
								seekColumn.indexOf(key));
					}
				}
				break;
			}

			// }

			/*
			 * }catch (Exception e) { throw null;
			 */

		}
		if (!isSatisfying && isCondition)
			rows.removeLast();
		return rows;

	}

	private File getTable(String dbPath, String tablePath) {
		File db = new File(dbPath);
		File table = null;
		if (db.exists() && db.isDirectory()) {
			String extendedTable = new String(tablePath);
			if (!extendedTable.endsWith(".xml")) {
				extendedTable = extendedTable.concat(".xml");
			}
			table = filterFiles(db.listFiles(), extendedTable);
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

	public Boolean evaluateCond(Object x, LinkedList<String> expression) {
		LinkedList<String> c = expression;
		int I3 = 0;
		String S3 = "";
		try {
			I3 = Integer.parseInt(c.get(2));
			String op = c.get(1).toString().trim();
			int comparator = Integer.parseInt(x.toString());
			if (op.equals(">")) {
				if (comparator > I3) {
					return true;
				} else {
					return false;
				}
			} else if (op.equals("<")) {
				if (comparator < I3) {
					return true;
				} else {
					return false;
				}
			} else if (op.equals("=")) {
				if (comparator == I3) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			try {
				String op = (String) c.get(1).toString().trim();
				S3 = (String) c.get(2);
				if (op.equals("=")) {
					if ((boolean) x.toString().equals(S3)) {
						return true;
					}
				} else {
					return false;
				}
			} catch (Exception e1) {
				// TODO: handle exception
				throw null;
			}
		}

		return false;

	}

	private LinkedList<String> fillSeekColumn(XMLEventReader reader) {
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
		validate(validator, x);
		return x;
	}

	private void skipDummyRow(XMLEventReader reader) {
		XMLEvent tempEvent = null;
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
					validator.add(tempEvent.asStartElement().getName().toString());
					Characters characters = reader.nextEvent().asCharacters();
					dataType.add(characters.getData());

				}
			} catch (Exception e) {
				throw null;
			}
		}
	}

	private void validate(LinkedList<String> validator, LinkedList<String> seekColumns) {
		seekDataType = new String[seekColumns.size()];
		int i = 0;
		for (String string : seekColumns) {
			seekDataType[i++] = dataType.get(validator.indexOf(string));

		}
		validator = new LinkedList<>();
		dataType = new LinkedList<>();
	}

	private Object getElementDataTyped(String x, int i) {
		if (seekDataType[i].equalsIgnoreCase("integer")) {
			return Integer.parseInt(x);
		} else if (seekDataType[i].equalsIgnoreCase("string")) {
			return x.substring(1, x.length() - 1);
		}
		throw null;
	}

	private void getDummyRow(XMLEventReader eventReader) {
		XMLEvent tempEvent = null;
		try {
			while (eventReader.hasNext()) {
				tempEvent = eventReader.nextEvent();
				if (tempEvent.getEventType() == XMLStreamConstants.START_ELEMENT
						&& tempEvent.asStartElement().getName().toString().equals("row")) {

					break;
				}
			}
		} catch (Exception e) {
			throw null;
		}
	}
}