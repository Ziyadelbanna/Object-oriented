package eg.edu.alexu.csd.oop.database.cs27;



import java.sql.SQLException;

import java.util.regex.Matcher;

import java.util.regex.Pattern;


public class Interpreter {



	public static final String NAME_REGEX = "[\\w\\d_]+";

	public static final String SUPPORTED_TYPES_REGEX = "(varchar|int)";

	public static final String STRING_REGEX = "'.*?'";

	public static final String NUMBER_REGEX = "\\d+";

	public static final String VALUE_REGEX = STRING_REGEX + "|" + NUMBER_REGEX;

	public static final Pattern STRING_PATTERN = Pattern.compile(STRING_REGEX, Pattern.CASE_INSENSITIVE);

	public static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX, Pattern.CASE_INSENSITIVE);



	private Interpreter queryInterpreter;



	private Interpreter[] interpretersList;



	public Interpreter(Interpreter[] interpretersList) {

		this.interpretersList = interpretersList;

	}



	protected Interpreter() {

	}



	public boolean matches(String query) {

		queryInterpreter = null;

		for (Interpreter inter : interpretersList) {

			try {

				if (inter.matches(query)) {

					queryInterpreter = inter;

					return true;

				}

			} catch (Exception e) {

			}

		}

		return false;

	}



	public Object interpret(MyDataBase db) throws SQLException {

		return queryInterpreter.interpret(db);

	}



	public Object interpret(TablesPool tpool) throws SQLException {

		return queryInterpreter.interpret(tpool);

	}



	public static Comparable<?> getValue(String val) throws SQLException {

		Matcher stringMatcher = STRING_PATTERN.matcher(val);

		Matcher numberMatcher = NUMBER_PATTERN.matcher(val);

		if (stringMatcher.matches()) {

			String ret = stringMatcher.group(0);

			ret = ret.substring(1, ret.length() - 1);

			return ret;

		} else if (numberMatcher.matches()) {

			return Integer.parseInt(numberMatcher.group(0));

		} else

			throw new SQLException("unsupported data type " + val);

	}



	public static Comparable<?> getValue(String val, Class<? extends Comparable<?>> type) throws SQLException {

		if (type == Integer.class)

			return Integer.parseInt(val);

		else if (type == String.class)

			return val;

		else

			throw new SQLException("unsupported data type " + val);

	}



	public static Comparable<?> getValue(String val, String sqlType) throws SQLException {

		return getValue(val, getJavaClass(sqlType));

	}



	public static String getSQLType(Class<? extends Comparable<?>> javaClass) {

		if (javaClass == Integer.class)

			return "int";

		else if (javaClass == String.class)

			return "varchar";

		return null;

	}



	public static Class<? extends Comparable<?>> getJavaClass(String sqlType) {

		if (sqlType.equalsIgnoreCase("int"))

			return Integer.class;

		if (sqlType.equalsIgnoreCase("varchar"))

			return String.class;

		return null;

	}



}