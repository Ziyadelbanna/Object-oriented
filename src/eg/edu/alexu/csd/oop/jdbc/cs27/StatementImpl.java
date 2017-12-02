package eg.edu.alexu.csd.oop.jdbc.cs27;



import java.sql.Connection;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.SQLWarning;

import java.sql.Statement;

import java.util.LinkedList;

import java.util.List;

import eg.edu.alexu.csd.oop.database.cs27.Interpreter;
import eg.edu.alexu.csd.oop.database.cs27.InterpreterFactory;
import eg.edu.alexu.csd.oop.database.cs27.InterpreterFactory.QueryType;
import eg.edu.alexu.csd.oop.database.cs27.MyDataBase;
import eg.edu.alexu.csd.oop.database.cs27.ResultDataWarpper;





public class StatementImpl implements Statement {



	private MyDataBase db;

	private List<String> queries = new LinkedList<String>();

	private InterpreterFactory factory;

	private Connection conn;



	public StatementImpl(MyDataBase db, Connection conn) {

	//	OnlineDebuger.logln("new Statement()", false);

		this.db = db;

		this.conn = conn;

	}



	@Override

	public void addBatch(String query) throws SQLException {

		Interpreter interpreter = factory.createInterpreter(QueryType.UpdateQuery);

		if (interpreter.matches(query)) {

			queries.add(query);

		} else

			throw new SQLException();

	}



	@Override

	public boolean execute(String query) throws SQLException {

		try {

		//	OnlineDebuger.logln("execute(" + query + ")", false);

			boolean ret = db.executeStructureQuery(query);

		//	OnlineDebuger.logln("return: " + ret, false);

			return ret;

		} catch (Exception ex) {

			try {

				if (db.executeQuery(query).length == 0)

					return false;

				return true;

			} catch (Exception e1) {

				int ret = executeUpdate(query);

				return (ret != 0);

			}

		}

	}



	@Override

	public int[] executeBatch() throws SQLException {

		int[] result = new int[queries.size()];

		int i = 0;

		for (String query : queries) {

			result[i++] = executeUpdate(query);

		}

		return result;

	}



	@Override

	public void clearBatch() throws SQLException {

		queries.clear();

	}



	@Override

	public ResultSet executeQuery(String query) throws SQLException {

	//	OnlineDebuger.logln("executeQuery(" + query + ")", false);

		try {

			ResultDataWarpper warpper = db.executeQuery(query, true);

			return new ResultSetImpl(warpper, this);

		} catch (Exception e) {

			throw new SQLException();

		}

	}



	@Override

	public int executeUpdate(String query) throws SQLException {

	//	OnlineDebuger.logln("executeUpdate(" + query + ")", false);

		int ret = db.executeUpdateQuery(query);

	//	OnlineDebuger.logln("return: " + ret, false);

		return ret;

	}



	@Override

	public Connection getConnection() throws SQLException {

		return this.conn;

	}



	@Override

	public void close() throws SQLException {

		//OnlineDebuger.logln("Statement closed()", false);

		// TODO

	}



	@Override

	public void setQueryTimeout(int arg0) throws SQLException {

		// TODO

	}



	@Override

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public <T> T unwrap(Class<T> arg0) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public void cancel() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public void clearWarnings() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public void closeOnCompletion() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public boolean execute(String arg0, int arg1) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public boolean execute(String arg0, int[] arg1) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public boolean execute(String arg0, String[] arg1) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public int executeUpdate(String arg0, int arg1) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public int executeUpdate(String arg0, int[] arg1) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public int executeUpdate(String arg0, String[] arg1) throws SQLException {



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

	public ResultSet getGeneratedKeys() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public int getMaxFieldSize() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public int getMaxRows() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public boolean getMoreResults() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public boolean getMoreResults(int arg0) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public int getQueryTimeout() throws SQLException {



		return 0;

	}



	@Override

	public ResultSet getResultSet() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public int getResultSetConcurrency() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public int getResultSetHoldability() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public int getResultSetType() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public int getUpdateCount() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public SQLWarning getWarnings() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public boolean isCloseOnCompletion() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public boolean isClosed() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public boolean isPoolable() throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public void setCursorName(String arg0) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public void setEscapeProcessing(boolean arg0) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public void setFetchDirection(int arg0) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public void setFetchSize(int arg0) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public void setMaxFieldSize(int arg0) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public void setMaxRows(int arg0) throws SQLException {



		throw new UnsupportedOperationException();

	}



	@Override

	public void setPoolable(boolean arg0) throws SQLException {



		throw new UnsupportedOperationException();

	}



}