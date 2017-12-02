package eg.edu.alexu.csd.oop.database.cs27;



import java.sql.SQLException;

import java.util.regex.Matcher;

import java.util.regex.Pattern;





public class Condition {

	private String colName;

	private Comparable value;

	private ComparisonResult compResult;



	public static final String CONDITION_REGEX = "where\\s+(" + Interpreter.NAME_REGEX + ")\\s*([\\>=\\<])\\s*("

			+ Interpreter.VALUE_REGEX + ")";

	public static final Pattern REGEX_PATTERN = Pattern.compile(CONDITION_REGEX, Pattern.CASE_INSENSITIVE);



	public Condition() {

	}



	public Condition(String expression) throws SQLException {

		setFromExpression(expression);

	}



	public boolean applysTo(Row row) throws SQLException {

		// if the condition is not set, then it is optional and always returns

		// true

		if (colName == null || value == null || compResult == null) {

			return true;

		}

		Comparable valueToBeCompared = row.get(colName);

		try {

			int res;

			try {

				String valueToBeComparedStr = (String) valueToBeCompared;

				res = valueToBeComparedStr.compareToIgnoreCase((String) value);

			} catch (Exception e) {

				res = valueToBeCompared.compareTo(value);

			}

			return compResult.equals(res);

		} catch (NullPointerException ne) {

			throw new SQLException("invalid colName in condition: " + colName + "or this col has not been initialized");

		} catch (ClassCastException ce) {

			throw new SQLException("invalid colType in condition: [" + value + " is " + value.getClass()

					+ " but should be: " + valueToBeCompared.getClass() + "]");

		}

	}



	/*

	 * takes a condition that comes after [where] SQL syntax and extract

	 * colName, ComparisonResult operator and value

	 * 

	 * @throws RuntimeException

	 */

	public void setFromExpression(String expression) throws SQLException {

		if (expression == null)

			return;

		expression = expression.toLowerCase();

		Matcher matcher = REGEX_PATTERN.matcher(expression);

		if (matcher.matches()) {

			colName = matcher.group(1);

			compResult = ComparisonResult.toComparisonResult(matcher.group(2));

			value = Interpreter.getValue(matcher.group(3));

		} else

			throw new RuntimeException("this condition is not well formated : " + expression);

	}



	public String toString() {

		return (colName + " " + compResult + " " + value);

	}



	private static enum ComparisonResult {

		LESS, GREATER, EQUALS, GREATER_OR_EQUALS, LESS_OR_EQUALS, NOT_EQUALS;



		public static ComparisonResult toComparisonResult(int c) {

			if (c == 0)

				return ComparisonResult.EQUALS;

			else if (c > 0)

				return ComparisonResult.GREATER;

			else

				return ComparisonResult.LESS;

		}



		public static ComparisonResult toComparisonResult(String c) {

			if (c.equals("="))

				return ComparisonResult.EQUALS;

			else if (c.equals(">"))

				return ComparisonResult.GREATER;

			else if (c.equals("<"))

				return ComparisonResult.LESS;

			else if (c.equals(">="))

				return ComparisonResult.GREATER_OR_EQUALS;

			else if (c.equals("<>"))

				return ComparisonResult.NOT_EQUALS;

			else if (c.equals("<="))

				return ComparisonResult.LESS_OR_EQUALS;



			return null;

		}



		public boolean equals(int c) {

			return equals(toComparisonResult(c));

		}



		public boolean equals(ComparisonResult val) {

			if (this == GREATER_OR_EQUALS)

				return val == GREATER || val == EQUALS;

			else if (this == LESS_OR_EQUALS)

				return val == LESS || val == EQUALS;

			else if (this == NOT_EQUALS)

				return val != EQUALS;

			return this == val;

		}



	}

}