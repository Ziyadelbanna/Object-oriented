package eg.edu.alexu.csd.oop.jdbc.cs27;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eg.edu.alexu.csd.oop.db.cs27.join.tester;

public class DbmsStatement implements java.sql.Statement {

	private List<String> queries;
	private Connection conn;
	private boolean isClose;
	private tester x;
	private resultset rset;

	public DbmsStatement(Connection conn, tester x) {
		this.conn = conn;
		queries = new ArrayList<>();
		isClose = false;
		this.x = x;
	}

	@Override
	public void addBatch(String arg0) throws SQLException {
		if (isClose == true)
			throw new SQLException("Error statement has been closed ..");
		queries.add(arg0);
	}

	@Override
	public void clearBatch() throws SQLException {
		if (isClose == true)
			throw new SQLException("Error statement has been closed ..");
		queries.clear();
	}

	@Override
	public void close() throws SQLException {
		if (isClose == true)
			throw new SQLException("Error statement has been closed ..");
		queries = null;
		isClose = true;
	}

	@Override
	public boolean execute(String arg0) throws SQLException {
		if (isClose == true)
			throw new SQLException("Error statement has been closed ..");
		x.executeQuery(arg0);
		if (x.getResultedData() == null || x.getResultedData().size() == 0) {
			System.out.println("F" + x.getResultedData());
			return false;
		} else {
			rset = new resultset(this, x.getResultedData(), x.getMapWithnoValues(), x.getMapWithDataTypes());
			return true;
		}
	}

	@Override
	public int[] executeBatch() throws SQLException {
		if (isClose == true)
			throw new SQLException("Error statement has been closed ..");
		int arr[] = null;
		if (queries.size() > 0) {
			arr = new int[queries.size()];
			for (int i = 0; i < queries.size(); i++) {
				x.executeQuery(queries.get(i));
				arr[i] = x.getDataFromModifyAndDelete();
			}
			return arr;
		}
		return null;
	}

	@Override
	public ResultSet executeQuery(String arg0) throws SQLException {
		if (isClose == true)
			throw new SQLException("Error statement has been closed ..");
		if (arg0.contains("Insert") || arg0.contains("Delete") || arg0.contains("Update") || arg0.contains("Alter")) {
			throw new SQLException("Error");
		}
		x.executeQuery(arg0);
		if (x.getResultedData() == null)
			throw new SQLException("No ResultSet Object found");
		rset = new resultset(this, x.getResultedData(), x.getMapWithnoValues(), x.getMapWithDataTypes());
		return (ResultSet) rset;
	}

	@Override
	public int executeUpdate(String arg0) throws SQLException {
		if (isClose == true)
			throw new SQLException("Error statement has been closed ..");
		x.executeQuery(arg0);
		return x.getDataFromModifyAndDelete();
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (isClose == true)
			throw new SQLException("Error statement has been closed ..");
		return conn;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		if (isClose == true)
			throw new RuntimeException("Error statement has been closed ..");
		if (rset == null)
			throw new SQLException("GetResultSet must be used only one time");
		resultset temp = rset;
		rset = null;
		return temp;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		if (isClose == true)
			throw new SQLException("Error statement has been closed ..");
		return x.getDataFromModifyAndDelete();
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancel() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxRows() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResultSetType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setQueryTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

}
