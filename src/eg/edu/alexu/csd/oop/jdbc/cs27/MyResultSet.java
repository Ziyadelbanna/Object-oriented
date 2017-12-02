package eg.edu.alexu.csd.oop.jdbc.cs27;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MyResultSet implements ResultSet {
	private XMLEventReader cursor;
	private File currentFile;
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private int currentRow = ZERO;
	private Object[][] table;
	private ArrayList<String> columnsName;
	private Statement parent;
	private String tableName;

	public MyResultSet(Object[][] x, ArrayList<String> y, Statement z, String a) {
		table = x;
		columnsName = y;
		parent = z;
		tableName = a;
	}
	
	public boolean absolute(int row) throws SQLException {
		int allRows = 0;
		try {
			allRows = table.length;
		} catch (NullPointerException e) {
			throw new SQLException("Table not found");
		}
		if (row == ZERO || allRows - Math.abs(row) < ZERO) {
			if (row > ZERO) {
				currentRow = allRows;
			} else {
				currentRow = ZERO - ONE;
			}

			return false;
		}
		if (row < ZERO) {
			row += allRows;
		}
		currentRow = row;
		return true;
	}

	
	public void afterLast() throws SQLException {
		absolute(table.length + ONE);
	}

	
	public void beforeFirst() throws SQLException {
		absolute(ZERO);
	}

	@Override
	public void close() throws SQLException {
		table = null;
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		return columnsName.indexOf(columnLabel) + 1;
	}

	@Override
	public boolean first() throws SQLException {
		return absolute(ONE);
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		try {
			return (int) table[currentRow][columnIndex - ONE];
		} catch (Exception e) {
			throw new SQLException("Data is invalid");
		}
	}

	private Object getColumnVal(int i, XMLEventReader x) throws SQLException {
		int temp = 0;
		try {
			while (x.hasNext()) {
				XMLEvent tempEvent = x.nextEvent();
				if (tempEvent.isStartElement()) {
					temp++;
					if (temp == i) {
						tempEvent = x.nextEvent();
						return tempEvent.asCharacters().getData();
					}
				}
			}
			throw new SQLException("Column at " + i + " Doesn't Exisit!");
		} catch (Exception e) {
			throw new SQLException("Can't Get Column Value!");
		}
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		return getInt(findColumn(columnLabel));
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		ResultSetMetaDataImp rsmd = new ResultSetMetaDataImp(table, tableName, columnsName);
		return rsmd;
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {

		try {
			return table[currentRow][columnIndex - ONE];
		} catch (Exception e) {
			throw new SQLException("Data is invalid");
		}
	}

	@Override
	public Statement getStatement() throws SQLException {
		return parent;
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		try {
			return (String) table[currentRow][columnIndex - ONE];
		} catch (Exception e) {
			throw new SQLException("Data is invalid");
		}
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		return getString(findColumn(columnLabel));
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		return currentRow >= table.length;
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		return currentRow == -ONE;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return table == null;
	}

	@Override
	public boolean isFirst() throws SQLException {
		return currentRow == ZERO;
	}

	@Override
	public boolean isLast() throws SQLException {
		return currentRow == table.length - ONE;
	}

	@Override
	public boolean last() throws SQLException {
		return absolute(table.length);
	}

	@Override
	public boolean next() throws SQLException {
		return absolute(currentRow + ONE);
	}

	@Override
	public boolean previous() throws SQLException {
		return absolute(currentRow - ONE);
	}

	/**************************** OUR FUNCTION ********************************/
	public void setCursorInFile(File table) {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		currentFile = table;
		try {
			cursor = factory.createXMLEventReader(new FileReader(table));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	public void setParent(Statement parent) {
		this.parent = parent;
	}

	private int getNoRows() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(currentFile);
			Element root = doc.getDocumentElement();
			return root.getElementsByTagName("row").getLength() - ONE;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return ZERO;
	}

	private void resetCursor() throws SQLException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try {
			cursor = factory.createXMLEventReader(new FileReader(currentFile));
		} catch (FileNotFoundException | XMLStreamException e) {
			throw new SQLException("An error occured during connecting to database!.");
		}
	}

	private void setCursorBeforeFirstRow() {
		try {
			XMLEvent tempEvent = cursor.nextEvent();
			while (cursor.hasNext()) {
				tempEvent = cursor.nextEvent();
				if (tempEvent.getEventType() == XMLStreamConstants.START_ELEMENT
						&& tempEvent.asEndElement().getName().toString().equals("row")) {
					break;
				}
			}
			while (cursor.hasNext()) {
				tempEvent = cursor.nextEvent();
				if (tempEvent.getEventType() == XMLStreamConstants.END_ELEMENT
						&& tempEvent.asEndElement().getName().toString().equals("row")) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCursorBeforeFirstRow(XMLEventReader x) {
		try {
			XMLEvent tempEvent = x.nextEvent();
			while (x.hasNext()) {
				tempEvent = x.nextEvent();
				if (tempEvent.getEventType() == XMLStreamConstants.START_ELEMENT
						&& tempEvent.asEndElement().getName().toString().equals("row")) {
					break;
				}
			}
			while (x.hasNext()) {
				tempEvent = x.nextEvent();
				if (tempEvent.getEventType() == XMLStreamConstants.END_ELEMENT
						&& tempEvent.asEndElement().getName().toString().equals("row")) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCursorAt(int i, int r) {
		try {
			int temp = ZERO;
			XMLEvent tempEvent = cursor.nextEvent();
			while (cursor.hasNext()) {
				tempEvent = cursor.nextEvent();
				if (tempEvent.getEventType() == XMLStreamConstants.START_ELEMENT
						&& tempEvent.asEndElement().getName().toString().equals("row")) {
					temp++;
					if (temp == i || temp == r)
						break;
				}
			}
			if (i > r) {
				while (cursor.hasNext()) {
					tempEvent = cursor.nextEvent();
					if (tempEvent.getEventType() == XMLStreamConstants.END_ELEMENT
							&& tempEvent.asEndElement().getName().toString().equals("row")) {
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private XMLEventReader cloneCursor() throws SQLException {
		try {
			int temp = ZERO;
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader x = factory.createXMLEventReader(new FileReader(currentFile));
			setCursorBeforeFirstRow(x);
			XMLEvent tempEvent = x.nextEvent();
			while (x.hasNext()) {
				tempEvent = x.nextEvent();
				if (tempEvent.getEventType() == XMLStreamConstants.START_ELEMENT
						&& tempEvent.asEndElement().getName().toString().equals("row")) {
					temp++;
					if (temp == currentRow)
						break;
				}
			}
			return x;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("Cursor is Unclonable");
		}
	}

	/********************************* END **************************************/
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public boolean absolute(int row) throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public void afterLast() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public void beforeFirst() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 */

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public void close() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 */

	@Override
	public void deleteRow() throws SQLException {
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public int findColumn(String columnLabel) throws SQLException {
	 * throw new UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public boolean first() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 */

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getConcurrency() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public String getCursorName() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public int getInt(int columnIndex) throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public int getInt(String columnLabel) throws SQLException { throw
	 * new UnsupportedOperationException();
	 * 
	 * }
	 */

	@Override
	public long getLong(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public ResultSetMetaData getMetaData() throws SQLException { throw
	 * new UnsupportedOperationException();
	 * 
	 * }
	 */

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public Object getObject(int columnIndex) throws SQLException {
	 * throw new UnsupportedOperationException();
	 * 
	 * }
	 */

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getRow() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public Statement getStatement() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public String getString(int columnIndex) throws SQLException {
	 * throw new UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public String getString(String columnLabel) throws SQLException {
	 * throw new UnsupportedOperationException();
	 * 
	 * }
	 */

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getType() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void insertRow() throws SQLException {
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public boolean isAfterLast() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public boolean isBeforeFirst() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public boolean isClosed() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public boolean isFirst() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public boolean isLast() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public boolean last() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 */

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public boolean next() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 * 
	 * @Override public boolean previous() throws SQLException { throw new
	 * UnsupportedOperationException();
	 * 
	 * }
	 */
	@Override
	public void refreshRow() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean relative(int rows) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean rowDeleted() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean rowInserted() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean rowUpdated() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNull(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateRow() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean wasNull() throws SQLException {
		throw new UnsupportedOperationException();

	}
}
