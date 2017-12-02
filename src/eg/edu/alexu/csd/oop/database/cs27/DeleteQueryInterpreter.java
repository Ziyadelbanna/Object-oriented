package eg.edu.alexu.csd.oop.database.cs27;



import java.sql.SQLException;

import java.util.regex.Matcher;

import java.util.regex.Pattern;




class DeleteQueryInterpreter extends Interpreter {



	private static final String REGEX = "delete\\s+from\\s+(" + NAME_REGEX + ")(\\s+(" + Condition.CONDITION_REGEX

			+ "))?\\s*;?";

	private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

	private Matcher matcher;



	private String tableName;

	private Condition cond;



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

		return table.deleteRows(cond);

	}



	private void extract() throws SQLException {

		tableName = matcher.group(1).toLowerCase();

		cond = new Condition(matcher.group(3));

	}



}