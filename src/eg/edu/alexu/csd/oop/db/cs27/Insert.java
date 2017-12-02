package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.sql.SQLException;

import java.util.LinkedHashMap;

import java.util.Map;

import eg.edu.alexu.csd.oop.db.Expression;



public class Insert implements Expression {



	public static String regex = "(?i)insert\\s*into\\s+(\\w+)\\s+values\\s*(\\(?.*\\)?)";

	// (5, 'Sherouq', 20)

	public static String firstMatch = "(?i)((\\d+|\'?.+\'?)\\s*,?\\s*)*";



	public static String rowMapRegex = "(?i)(\\d+|\'?[a-zA-Z0-9 _]+\'?)\\s*,?\\s*";



	private String otherInput = "";



	private Expression next;



	@Override

	public Expression match(String input) {

		boolean res = Utility.matches(regex, input);



		if (res == false) {

			if (next != null) {

				return next.match(input);

			}

			return null;

		}

		if (input.contains("(") && input.contains(")")) {

			otherInput = input.substring(input.indexOf("(") + 1,

					input.indexOf(")"));

		}

		// System.out.println(otherInput);

		res = res && Utility.matches(firstMatch, otherInput);



		if (res == false) {

			// System.out.println("no match from second");

		}

		if (res) {

			return this;

		} else if (next != null) {

			return next.match(input);

		}

		return null;

	}



	@Override

	public int interpret(String input) throws SQLException {

		Map<String, String> data = new LinkedHashMap<>();

		Folder folder = FolderBuilder.getFolder();

		if (folder == null) {

			return 0;

		}

		String tableName = Utility.getTableName(regex, input).toLowerCase();

		if (tableName == null || tableName.equals("")) {

			return 0;

		}

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

		data = Utility.getRowMap(table, rowMapRegex, otherInput);

		if (data == null) {

			return 0;

		}

		// System.out.println(data);

		// System.out.println(otherInput);

		// System.out.println(table.getMap());

		Row row = new Row(data);

		table.addRow(row);

		SaveXML saver = new SaveXML();

		try {

			saver.save(table);



		} catch (Exception e) {

			return 0;

		}

		return 1;

	}



	@Override

	public void setNextInChain(Expression next) {

		this.next = next;



	}

}