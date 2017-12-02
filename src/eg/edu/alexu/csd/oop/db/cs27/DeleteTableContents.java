package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.util.ArrayList;



public class DeleteTableContents implements Expression {



	private Expression next = null;

	private String regex = "(?i)\\s*delete\\s*\\*?\\s*from\\s*(\\w+)";



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

	public int interpret(String input) {

		Folder folder = FolderBuilder.getFolder();

		String tableName = Utility.getTableName(regex, input);

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

		ArrayList<Row> rows = new ArrayList<>() ;

		rows = table.getRows() ;

		int numberOfDeletedRows = rows.size() ;

		rows.clear();

		table.setRows(rows);

		SaveXML saver = new SaveXML();

		try {

			saver.save(table);

			

		} catch (Exception e) {

			return 0;

		}

		return numberOfDeletedRows ;

	}



	@Override

	public void setNextInChain(Expression next) {

		this.next = next;

	}



}