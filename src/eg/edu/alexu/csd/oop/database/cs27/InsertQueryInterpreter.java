package eg.edu.alexu.csd.oop.database.cs27;



import java.sql.SQLException;

import java.util.Map;

import java.util.regex.Matcher;

import java.util.regex.Pattern;


class InsertQueryInterpreter extends Interpreter {



	private static final String COLNAMES_REGEX = "\\(((" + NAME_REGEX + "+\\s*,\\s*)*(" + NAME_REGEX + "))\\)";

	private static final String COLVALUES_REGEX = "\\((((" + VALUE_REGEX + ")+\\s*,\\s*)*(" + VALUE_REGEX + "))\\)";

	private static final String REGEX = "insert\\s+into\\s+(" + NAME_REGEX + ")\\s*(" + COLNAMES_REGEX

			+ ")?\\s*values\\s*" + COLVALUES_REGEX + "\\s*;?";

	private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

	private Matcher matcher;



	private String tableName;

	private String[] colNames, values;



	@Override

	public boolean matches(String query) {

		matcher = REGEX_PATTERN.matcher(query);

		return matcher.matches();

	}



	@Override

	public Object interpret(MyDataBase db) throws SQLException {

		return interpret(db.getTablesPool());

	}



	@Override

	public Object interpret(TablesPool tpool) throws SQLException {

		extract();

		Table table = tpool.getTableByName(tableName);

		if (!tpool.isConnected())

			throw new RuntimeException("no dataBase found to Update this table: " + tableName);

		if (table == null)

			throw new SQLException("can not read table " + tableName);



		Row row = new Row();

		// verify colsNames and values and construct new row

		if (colNames == null)

			colNames = table.getColNames();

		Map<String, Class<? extends Comparable<?>>> colTypes = table.getColTypes();

		for (int i = 0; i < colNames.length; ++i) {

			String name = colNames[i];

			String valueStr = values[i];

			Comparable<?> val = getValue(valueStr);

			Class<? extends Comparable<?>> colClass = colTypes.get(name);

			if (val == null || val.getClass() != colClass)

				throw new SQLException("invalid col value " + valueStr + " for " + name);

			row.put(name, val);

		}



		table.addRow(row);

		return 1;

	}



	private void extract() {

		tableName = matcher.group(1).toLowerCase();

		String colNamesStr = matcher.group(3);

		if (colNamesStr != null)

			colNames = colNamesStr.toLowerCase().split("\\s*,\\s*");

		else

			colNames = null;

		String valuesStr = matcher.group(6);

		values = valuesStr.split("\\s*,\\s*");

	}



}