package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.sql.SQLException;

import java.util.AbstractMap;

import java.util.ArrayList;

import java.util.Map;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Expression;



public class Update implements Expression {



	private Expression next = null;

	public static String regex = "(?i)\\s*update\\s*(\\w+)\\s*set\\s*((\\w+)\\s*=\\s*([a-zA-Z0-9_' ]+|\\d+)\\s*,?\\s*?)*\\s*where\\s*(\\w+)\\s*(=|>|<)\\s*(.+);?";

	public static String regex2 = "(?i)(\\w+)\\s*(=|>|<)\\s*([a-zA-Z0-9_' ]+)";

	public static String regexCond = "(?i)where\\s*(\\w+)\\s*(=|<|>)\\s*\'?(\\w+)\'?" ;



	@Override

	public Expression match(String input) {



		boolean res = Utility.matches(regex, input);

		if (res == false) {

			if (next != null) {

				return next.match(input);

			}

			return null;

		}

		return this;

	}



	@Override

	public int interpret(String input) throws SQLException {

		ArrayList<String> attr = new ArrayList<>();

		ArrayList<String> value = new ArrayList<>();

		Pattern pattern = Pattern.compile(regex2);

		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {

			if (matcher.group().length() != 0) {

				attr.add(matcher.group(1));

				value.add(matcher.group(3).replaceAll("'", "").replaceAll("\"", ""));

			}

		}

		Folder folder = FolderBuilder.getFolder();

		if (folder == null) {

			return 0;

		}

		String tableName = Utility.getTheNMatcher(regex, input, 1).toLowerCase();

		ReadXML reader = new ReadXML();

		String path = folder.getPath() + File.separator + tableName + ".xml";

		if (folder.contains(path) == false) {

			return 0;

		}

		Table table;

		try {

			table = reader.read(path);

		} catch (Exception e) {

			return 0;

		}

		if (table == null) {

			return 0;

		}

		ArrayList<Row> rows = table.getRows();

		if (rows == null) {

			return 0;

		}

		Operator solver = OperatorChain.getInstance().getMatcher(Utility.getTheNMatcher(regex, input, 6));

		if (solver == null) {

			return 0;

		}

	//	System.out.println(Utility.getTheNMatcher(regex, input, 6));

		java.util.Iterator<Row> iter = rows.iterator();

		int countOfUpdatedRows = 0;

		String compareAttr = Utility.getTheNMatcher(regexCond, input, 1).toLowerCase();

	//	 System.out.println(compareAttr);

		String compareAttrValue = Utility.getTheNMatcher(regexCond, input, 3).toLowerCase();

		//System.out.println(compareAttr);

	//	 System.out.println(compareAttrValue);

		while (iter.hasNext()) {

			Row thisRow = iter.next();

			if (solver.compare(thisRow, table, compareAttr, compareAttrValue)) {

				for (int j = 0; j < attr.size() - 1; j++) {

					Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<String, String>(

							attr.get(j), value.get(j));

					if (thisRow.getAttribute(attr.get(j)) == null) {

						return 0;

					}

					thisRow.updateAttribute(entry);

				}

				countOfUpdatedRows++;

			}

		}

		table.setRows(rows);

		SaveXML saver = new SaveXML();

		try {

			saver.save(table);



		} catch (Exception e) {

			return 0;

		}



		return countOfUpdatedRows;

	}



	@Override

	public void setNextInChain(Expression next) {

		this.next = next;

	}

}