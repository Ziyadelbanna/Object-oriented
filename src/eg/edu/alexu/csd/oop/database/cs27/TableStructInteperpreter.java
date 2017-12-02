package eg.edu.alexu.csd.oop.database.cs27;



import java.sql.SQLException;

import java.util.HashMap;

import java.util.Map;

import java.util.regex.Matcher;

import java.util.regex.Pattern;



import eg.edu.alexu.csd.oop.database.cs27.FileOperations;

import eg.edu.alexu.csd.oop.database.cs27.MyDataBase;

import eg.edu.alexu.csd.oop.database.cs27.Table;

import eg.edu.alexu.csd.oop.database.cs27.TablesPool;

import eg.edu.alexu.csd.oop.database.cs27.XMLParser;



class TableStructInterpreter extends Interpreter {



	private static final String DROP_REGEX = "(drop)\\s+table\\s+(" + NAME_REGEX + ")\\s*;?";

	private static final Pattern DROP_PATTERN = Pattern.compile(DROP_REGEX, Pattern.CASE_INSENSITIVE);



	private static final String COL_REGEX = NAME_REGEX + "\\s+" + SUPPORTED_TYPES_REGEX;

	private static final String COLS_REGEX = "\\(((" + COL_REGEX + "+\\s*,\\s*)*(" + COL_REGEX + "))\\)";

	private static final String CREATE_REGEX = "(create)\\s+table\\s+(" + NAME_REGEX + ")\\s*" + COLS_REGEX + "\\s*;?";

	private static final Pattern CREATE_PATTERN = Pattern.compile(CREATE_REGEX, Pattern.CASE_INSENSITIVE);



	private Matcher matcher;



	private String tableName;

	private String type;

	private String[] colNames;

	private Map<String, Class<? extends Comparable<?>>> types;



	@Override

	public boolean matches(String query) {

		matcher = DROP_PATTERN.matcher(query);

		if (matcher.matches())

			return true;

		matcher = CREATE_PATTERN.matcher(query);

		return matcher.matches();

	}



	@Override

	public Object interpret(MyDataBase db) throws SQLException {

		return interpret(db.getTablesPool());

	}



	@Override

	public Object interpret(TablesPool tpool) throws SQLException {

		extract();

		if (!tpool.isConnected())

			throw new RuntimeException("no dataBase found to create/drop this table: " + tableName);

		try {

			XMLParser xmlParser = new XMLParser();

			String tablePath = tpool.getTablePath(tableName);

			if (type.equalsIgnoreCase("create")) {

				if (FileOperations.isFileExist(tablePath))

					return false;

				Table table = new Table(tableName, colNames, types);

				xmlParser.writeXML(tablePath, table);

				table.addObserver(tpool);

				return true;

			} else

				return FileOperations.deleteDirectory(tablePath);

		} catch (Exception e) {

			System.out.println("Error Creating table: " + e.getMessage());

			// e.printStackTrace();

		}

		return false;

	}



	private void extract() {

		type = matcher.group(1).toLowerCase();

		tableName = matcher.group(2).toLowerCase();

		if (type.equalsIgnoreCase("create")) {

			String[] cols = matcher.group(3).toLowerCase().split("\\s*,\\s*");

			colNames = new String[cols.length];

			types = new HashMap<>();

			for (int i = 0; i < cols.length; ++i) {

				String[] tokens = cols[i].split("\\s+");

				colNames[i] = tokens[0];

				String type = tokens[1];

				types.put(colNames[i], getJavaClass(type));

			}

		}

	}

}