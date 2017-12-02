package eg.edu.alexu.csd.oop.database.cs27;



import java.sql.SQLException;

import java.util.regex.Matcher;

import java.util.regex.Pattern;



class DBStructInterpreter extends Interpreter {



	private static final String REGEX = "(create|drop)\\s+database\\s+(" + NAME_REGEX + ")\\s*;?";

	private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

	private Matcher matcher;



	private String DBName;

	private String type;



	@Override

	public boolean matches(String query) {

		matcher = REGEX_PATTERN.matcher(query);

		return matcher.matches();

	}



	@Override

	public Object interpret(MyDataBase db) throws SQLException {

		extract();

		DBName = db.getPath() + DBName;

		TablesPool tpool = db.getTablesPool();

		tpool.dropConnection();

		if (type.equalsIgnoreCase("create")) {

			boolean success = FileOperations.createDirectory(DBName);

		//	OnlineDebuger.logln("Create dataBase: " + db.getPath() + "DataBaseName: " + DBName + " -- success: " + success, false);

			tpool.connectTo(DBName);

			return success;

		} else {

			return FileOperations.deleteDirectory(DBName);

		}

	}



	@Override

	public Object interpret(TablesPool tpool) throws SQLException {

		throw new UnsupportedOperationException();

	}



	private void extract() {

		type = matcher.group(1).toLowerCase();

		DBName = matcher.group(2).toLowerCase();

	}

}