package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.sql.SQLException;

import java.util.Map;



public class CreateTable implements Expression {



	private Expression next = null;



	public static String regex = "(?i)\\s*create\\s+table\\s+(\\w+)\\s*(\\(.*\\))\\s*";

	public static String tableColRegex = "(?i)\\s*(\\w+)\\s+(int|varchar)\\s*,?\\s*?";

	public static String firstMatch = "(?i)\\s*((\\w+)\\s+(int|varchar)\\s*,?\\s*?)*";

	public String otherInput = "";



	@Override

	public Expression match(String input) {

		boolean res = Utility.matches(regex, input);

		if (res == false) {

			if (next != null) {

				return next.match(input);

			}

			return null;

		}

		res = res && Utility.matchTableParameters(otherInput, firstMatch);

		if (res) {

			if (input.contains("(") && input.contains(")")) {

				otherInput = input.substring(input.indexOf("(") + 1, input.indexOf(")"));

			}

			return this;

		} else if (next != null) {

			return next.match(input);

		}



		return null;

	}



	@Override

	public int interpret(String input) {

		String tableName = Utility.getTableName(regex, input).toLowerCase();

		Folder folder = FolderBuilder.getFolder();

		if (folder == null) {

			try {

				throw new SQLException();

			} catch (SQLException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

		String path = folder.getPath() + File.separator + tableName + ".xml";

		// System.out.println(path);

		if (folder.contains(path)) {

			return 0;

		}

		Table table = new Table(path);

		Map<String, String> tableMap = Utility.getTableCol(otherInput, firstMatch, tableColRegex);

		if (tableMap == null) {

			return 0;

		}

		table.setMap(tableMap);



		folder.addTable(table);

		SaveXML save = new SaveXML();

		try {

			save.save(table);

		} catch (Exception e) {

			return 0;



		}

		// System.out.println(table.getPath());

		GenerateDTD ge = new GenerateDTD();

		try {

			ge.generateDTD(FolderBuilder.getFolder());

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