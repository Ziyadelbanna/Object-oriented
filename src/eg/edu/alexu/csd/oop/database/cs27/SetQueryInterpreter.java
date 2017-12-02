package eg.edu.alexu.csd.oop.database.cs27;



import java.sql.SQLException;

import java.util.Map;

import java.util.regex.Matcher;

import java.util.regex.Pattern;




class SetQueryInterpreter extends Interpreter {



	private static final String _COLNAMES_REGEX_ = "(" + NAME_REGEX + ")\\s*=\\s*(" + VALUE_REGEX + ")";

	private static final String COLNAMES_REGEX = "((" + _COLNAMES_REGEX_ + "\\s*,\\s*)*(" + _COLNAMES_REGEX_ + "))";

	private static final String REGEX = "update\\s+(" + NAME_REGEX + ")\\s+set\\s+" + COLNAMES_REGEX + "(\\s+("

			+ Condition.CONDITION_REGEX + "))?\\s*;?";

	private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

	private Matcher matcher;



	private String tableName;

	private Condition cond;

	private String[] colNames, values;



	@Override

	public boolean matches(String query) {

		matcher = REGEX_PATTERN.matcher(query);

		matcher.matches();

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

			throw new RuntimeException("no dataBase found to Update this table: " + tableName);

		Table table = tpool.getTableByName(tableName);

		if (table == null)

			throw new SQLException("can not read table " + tableName);



		Row row = new Row();

		// verify colsNames and values and construct new row

		Map<String, Class<? extends Comparable<?>>> colTypes = table.getColTypes();

		for (int i = 0; i < colNames.length; ++i) {

			String name = colNames[i];

			String valueStr = values[i];

			// if (!colTypes.containsKey(name)) // colName not found

			// throw new SQLException("invalid col name " + name);

			Comparable<?> val = getValue(valueStr);

			Class<? extends Comparable<?>> colClass = colTypes.get(name);

			if (val == null || val.getClass() != colClass)

				throw new SQLException("invalid col value " + name + "=" + valueStr);

			row.put(name, val);

		}

		int tmp = table.update(row, cond);

		return tmp;

	}



	private void extract() throws SQLException {

		tableName = matcher.group(1).toLowerCase();

		int conditionIndx = matcher.groupCount() - 3;

		cond = new Condition(matcher.group(conditionIndx));

		String colNamesValuesStr = matcher.group(2);

		String[] colNamesValues = colNamesValuesStr.split("\\s*,\\s*");

		colNames = new String[colNamesValues.length];

		values = new String[colNamesValues.length];

		for (int i = 0; i < colNamesValues.length; ++i) {

			String[] tokens = colNamesValues[i].split("\\s*=\\s*");

			colNames[i] = tokens[0].toLowerCase();

			values[i] = tokens[1];

		}

	}



}