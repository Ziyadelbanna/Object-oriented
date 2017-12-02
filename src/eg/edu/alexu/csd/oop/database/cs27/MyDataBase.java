package eg.edu.alexu.csd.oop.database.cs27;



import java.io.File;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.database.cs27.InterpreterFactory.QueryType;



public class MyDataBase implements Database {

	private String workingPath;

	private InterpreterFactory factory;

	private TablesPool tpool;



	public MyDataBase(String path) {

		tpool = new TablesPool();

		factory = new InterpreterFactory();

		this.setPath(path);

	}



	public MyDataBase() {

		this("");

	}



	private void setPath(String path) {

		if (path.length() > 0 && !path.endsWith(File.separator))

			path += File.separator;

		this.workingPath = path;

	}



	public String getPath() {

		return this.workingPath;

	}



	public TablesPool getTablesPool() {

		return this.tpool;

	}



	public String createDatabase(String databaseName, boolean dropIfExists) {

		databaseName = databaseName.toLowerCase();

		try {

			if (dropIfExists) {

				executeStructureQuery("drop database " + databaseName + ';');

				executeStructureQuery("create database " + databaseName + ';');

			} else {

				executeStructureQuery("create database " + databaseName + ';');

			}

			return workingPath + databaseName;

		} catch (Exception e) {

			return null;

		}

	}



	public boolean executeStructureQuery(String query) throws SQLException {

		Interpreter interpreter = factory.createInterpreter(QueryType.StructureQuery);

		if (interpreter.matches(query)) {

			boolean ret = (boolean) interpreter.interpret(this);

			return ret;

		} else

			throw new SQLException("this is not a valid Structure Query! " + query);

	}



	public Object[][] executeQuery(String query) throws SQLException {

		ResultDataWarpper warpper = (ResultDataWarpper) executeQuery(query, false); 

		return warpper.getData();

	}



	public ResultDataWarpper executeQuery(String query, boolean withMetaData) throws SQLException {

		Interpreter interpreter = factory.createInterpreter(QueryType.SelectQuery);

		if (interpreter.matches(query)) {

			return (ResultDataWarpper) interpreter.interpret(this);

		} else

			throw new SQLException("this is not a valid Query! " + query);

	}



	public int executeUpdateQuery(String query) throws SQLException {

		Interpreter interpreter = factory.createInterpreter(QueryType.UpdateQuery);

		if (interpreter.matches(query)) {

			int ret = (int) interpreter.interpret(this);

			return ret;

		} else

			throw new SQLException("this is not a valid Update Query! " + query);

	}



}