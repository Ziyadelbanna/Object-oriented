package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.sql.SQLException;

import java.util.AbstractMap;

import java.util.ArrayList;

import java.util.Map;

import java.util.regex.Matcher;

import java.util.regex.Pattern;



public class UpdateNoCon implements Expression {



	public static String regex = "(?i)\\s*update\\s*(\\w+)\\s*set\\s*((\\w+)\\s*=\\s*(.+|\\d+),?\\s*?)*\\s*";

	public static String regex2 = "(?i)(\\w+)\\s*(=|>|<)\\s*([a-zA-Z0-9_' ]+)";

	private Expression next = null;



	@Override

	public Expression match(String input) {

		boolean res = Utility.matches(regex, input);

		if (res == false || input.contains("where")) {

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

		String tableName = Utility.getTheNMatcher(regex, input, 1);

		ReadXML reader = new ReadXML();

		String path = folder.getPath() + File.separator + tableName + ".xml";

		if (folder.contains(path) == false) {

			throw new SQLException() ;

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

		java.util.Iterator<Row> iter = rows.iterator();

		int countOfUpdatedRows = 0;

		while (iter.hasNext()) {

			Row thisRow = iter.next();

			for (int j = 0; j < attr.size(); j++) {

				Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<String, String>(

						attr.get(j), value.get(j));

				if (thisRow.getAttribute(attr.get(j)) == null) {

					return 0;

				}

				thisRow.updateAttribute(entry);

				

			}

			countOfUpdatedRows++;

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