package eg.edu.alexu.csd.oop.database.cs27;



import java.sql.SQLException;

import java.util.List;

import java.util.Map;

import java.util.regex.Matcher;

import java.util.regex.Pattern;


class SelectQueryInterpreter extends Interpreter {



	private static final String COLNAME_REGEX = "(" + NAME_REGEX + "+\\s*,\\s*)*(" + NAME_REGEX + "+)";

	private static final String REGEX = "select\\s+(\\*|" + COLNAME_REGEX + ")\\s+from\\s+(" + NAME_REGEX + "+)(\\s+("

			+ Condition.CONDITION_REGEX + "))?\\s*;?";

	private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

	private Matcher matcher;



	private String tableName;

	private Condition cond;

	private String[] colNames;



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

		if (!tpool.isConnected())

			throw new RuntimeException("no dataBase found to Select from this table: " + tableName);

		

		Table table;

		Object[][] data = new Object[0][0];

		ResultDataWarpper warpper = new ResultDataWarpper();

		warpper.setData(data);

		

		try {

			table = tpool.getTableByName(tableName);

			if (table != null) {

				if (colNames == null) // if colNames == null then select all

					colNames = table.getColNames();

				List<Row> rowsList = table.getRows(cond);

				data = new Object[rowsList.size()][colNames.length];

				int indx = 0;

				for (Row row : rowsList) {

					for (int nameIndx = 0; nameIndx < colNames.length; nameIndx++)

						data[indx][nameIndx] = row.get(colNames[nameIndx]);

					indx++;

				}

				warpper.setData(data);

				// Meta-Data

				warpper.setTableName(tableName);

				warpper.setColNames(colNames);

				String[] colTypesArr = new String[colNames.length];

				Map<String, Class<? extends Comparable<?>>> colTypes = table.getColTypes();

				for (int j = 0; j < colNames.length; ++j)

					colTypesArr[j] = getSQLType(colTypes.get(colNames[j]));

				warpper.setColTypes(colTypesArr);

			}

		} catch (NullPointerException e) {

			// OnlineDebuger.log(e.getMessage() + "\n", false);

		}

		return warpper;

	}



	private void extract() throws SQLException {

		tableName = matcher.group(4).toLowerCase();

		cond = new Condition(matcher.group(6));

		String colNamesStr = matcher.group(1).toLowerCase();

		if (!colNamesStr.equals("*")) {

			colNames = colNamesStr.split("\\s*,\\s*");

		} else

			colNames = null;

	}



}