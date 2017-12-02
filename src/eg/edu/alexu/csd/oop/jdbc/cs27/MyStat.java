package eg.edu.alexu.csd.oop.jdbc.cs27;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import eg.edu.alexu.csd.oop.db.cs27.parse.ParserFiltrer;

public class MyStat implements Statement {
	// public MyDatabase DB = new MyDatabase();
	private ArrayList<String> Batches = new ArrayList<>();
	// MyDriver conPath= new MyDriver();
	ConnectionImp con;
	ParserFiltrer filter = new ParserFiltrer();
	// Adapter adp = new Adapter();
	int QueryTimeout;
	boolean closed = false;

	//////////////////
	/*
	 * int count =0; public static StringBuilder cd = new StringBuilder();
	 *////////////////////

	public MyStat(Connection c) {
		// TODO Auto-generated constructor stub
		con = (ConnectionImp) c;
	}

	@Override
	public void addBatch(String sql) throws SQLException {
		// TODO Auto-generated method stub
		if (closed) {
			throw new SQLException();
		}
		Batches.add(sql);
	}

	@Override
	public void clearBatch() throws SQLException {
		// TODO Auto-generated method stub
		if (closed) {
			throw new SQLException();
		}
		Batches.clear();
	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		/*
		 * DB = new MyDatabase(); Batches = new ArrayList<>(); connec = new
		 * MyConnection();
		 */
		// throw new SQLException("e"+closed);
		closed = true;

	}

	@Override
	public boolean execute(String sql) throws SQLException {
		// TODO Auto-generated method stub
		// i don't which query it is.
		if (closed) {
			throw new SQLException();
		}

		Map<String, Object> ParserMap = null;
		try {
			ParserMap = filter.getInfo(sql);
		} catch (StringIndexOutOfBoundsException e) {
			throw new SQLException(sql + "\n" + e);
		}
		if (((String) ParserMap.get("Operation")).equals("CreateTable")
				|| ((String) ParserMap.get("Operation")).equals("DropTable")
				|| ((String) ParserMap.get("Operation")).equals("DropDB")
				|| ((String) ParserMap.get("Operation")).equals("CreateDB")) {
			return con.db.executeStructureQuery(sql);
			// return 0;
		} else if (((String) ParserMap.get("Operation")).equals("Update")
				|| ((String) ParserMap.get("Operation")).equals("Insert")
				|| ((String) ParserMap.get("Operation")).equals("Delete")) {

			int s = con.db.executeUpdateQuery(sql);
			return s > 0;
		} else if (((String) ParserMap.get("Operation")).equals("Select")) {
			Object[][] sel = con.db.executeQuery(sql);
			return sel != null;
		}

		throw new SQLException(sql + "\n\n\n");

	}

	@Override
	public int[] executeBatch() throws SQLException {
		// return null;
		// TODO Auto-generated method stub
		if (closed) {
			throw new SQLException();
		}

		int[] out = new int[Batches.size()];
		for (int i = 0; i < out.length; i++) {
			out[i] = con.db.executeUpdateQuery(Batches.get(i));
		}
		// throw new SQLException("I am executeBatch right");
		return out;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {

		Object[][] select = con.db.executeQuery(sql);
		ArrayList<String> col = con.db.getColumnsNames();
		MyResultSet result = new MyResultSet(select, col, this, (String) con.db.str.get("TableName"));

		return result;
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		// TODO Auto-generated method stub
		// thread come timeout calcoulating
		if (closed) {
			throw new SQLException();
		}
		Map<String, Object> ParserMap = null;
		try {
			ParserMap = filter.getInfo(sql);
		} catch (StringIndexOutOfBoundsException e) {
			throw new SQLException(sql + "\n" + e);
		}
		if (((String) ParserMap.get("Operation")).equals("CreateTable")
				|| ((String) ParserMap.get("Operation")).equals("DropTable")) {
			con.db.executeStructureQuery(sql);
			return 0;
		} else if (((String) ParserMap.get("Operation")).equals("Update")
				|| ((String) ParserMap.get("Operation")).equals("Insert")
				|| ((String) ParserMap.get("Operation")).equals("Delete")) {
			return con.db.executeUpdateQuery(sql);
		}

		throw new SQLException(sql + "\n\n\n");

	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		if (closed) {
			throw new SQLException();
		}
		// return connec;
		return null;
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		// TODO Auto-generated method stub
		if (closed) {
			throw new SQLException();
		}
		return QueryTimeout;
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		if (closed) {
			throw new SQLException();
		}
		QueryTimeout = seconds;
	}

	/*
	 * cd.append(sql + "\n"); count++; if(count == 1){ //throw new
	 * SQLException(cd.toString() + "\n\n\n\n"); } ////// (non-Javadoc)
	 * 
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/*
	 * @Override public void addBatch(String arg0) throws SQLException { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

	@Override
	public void cancel() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public void clearBatch() throws SQLException { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public void close() throws SQLException { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 */

	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public boolean execute(String arg0) throws SQLException { // TODO
	 * Auto-generated method stub return false; }
	 */

	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/*
	 * @Override public int[] executeBatch() throws SQLException { // TODO
	 * Auto-generated method stub return null; }
	 */

	/*
	 * @Override public ResultSet executeQuery(String arg0) throws SQLException { //
	 * TODO Auto-generated method stub return null; }
	 */

	/*
	 * @Override public int executeUpdate(String arg0) throws SQLException { // TODO
	 * Auto-generated method stub return 0; }
	 */

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/*
	 * @Override public Connection getConnection() throws SQLException { // TODO
	 * Auto-generated method stub return null; }
	 */

	@Override
	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxRows() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/*
	 * @Override public int getQueryTimeout() throws SQLException { // TODO
	 * Auto-generated method stub return 0; }
	 */

	@Override
	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetType() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getUpdateCount() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/*
	 * @Override public void setQueryTimeout(int arg0) throws SQLException { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

}