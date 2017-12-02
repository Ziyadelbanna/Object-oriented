package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.sql.SQLException;

import java.util.LinkedHashMap;

import java.util.Map;



public class InsertWithColName implements Expression {

	

	// INSERT INTO table_name6(column_name1, column_name2) VALUES ('value1', 4) 

	

	Expression next = null;

	public static String regex = "(?i)\\s*insert\\s+into\\s+(\\w+)\\s*\\((\\w+\\s*,?\\s*)*\\)\\s*values\\s*\\(\\s*([a-zA-Z0-9 '_]+\\s*,?\\s*)*\\s*\\)";



	public static String tableCol = "\\((\\w+\\s*,?\\s*)*\\)";

	

	public static String values = "(?i)values\\s*\\(([a-zA-Z0-9 '_]+\\s*,?\\s*)*\\)";

	

	

	@Override

	public Expression match(String input) {

		boolean res = Utility.matches(regex, input);

		if (res) {

			return this;

		}

		else if (next != null) {

			return next.match(input);

		}

		return null;

	}



	@Override

	public int interpret(String input) throws SQLException {

		String tableName = Utility.getTheNMatcher(regex, input, 1).toLowerCase();

		String[] col = Utility.getTheNMatcher(tableCol, input, 0).trim().replaceAll("\\(", "").replaceAll("\\)", "").split(",");

		for (int i = 0; i < col.length; i++) {

			col[i] = col[i].trim();

		}

		String xxx = Utility.getTheNMatcher(values, input, 0);

		String other = xxx.substring(xxx.indexOf("(") - 1, xxx.indexOf(")"));

		String[] colValues = other.replaceAll("\\(", "").replaceAll("\\)", "").split(",");

		for (int i = 0; i < colValues.length; i++) {

			colValues[i] = colValues[i].replaceAll("'","").trim();

		}

		if (col.length != colValues.length) {

			return 0;

		}

		Folder folder = FolderBuilder.getFolder();

		if (folder == null) {

			return 0;

		}

		String path = folder.getPath() + File.separator + tableName + ".xml";

		if (!folder.contains(path)) {

			return 0;

		}

		Table table;

		try {

			ReadXML reader = new ReadXML();

			table = reader.read(path);

		} catch (Exception e) {

			return 0;

		}

		for (int i = 0; i  < col.length; i++) {

			if (!table.contianKey(col[i])) {

				return 0;

			}

		}

		Map<String, String> rowMapTemp = new LinkedHashMap<>();

		Map<String, String> rowMap = new LinkedHashMap<>();

		for (int i = 0; i < col.length; i++) {

			rowMapTemp.put(col[i].toLowerCase(), colValues[i].toLowerCase());

		}

		Map<String, String> tableMap = table.getMap();

		for (Map.Entry<String, String> entry : tableMap.entrySet()) {

			String key = entry.getKey();

			String value = rowMapTemp.get(key);

			rowMap.put(key, value);

		}

		Row row = new Row(rowMap);

		table.addRow(row);

		try {

			SaveXML save = new SaveXML();

			save.save(table);

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