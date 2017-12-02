package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.sql.SQLException;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.Expression;



public class DeleteConditioned implements Expression{



	private Expression next = null ; 

	private String regex = "(?i)\\s*delete\\s+from\\s+(\\w+)\\s+where\\s+(\\w+)\\s*(.{1})\\s*(.+)" ;

	@Override

	public Expression match(String input) {

		boolean res = Utility.matches(regex, input) ;

		if(res == false){

			if(next != null){

				return next.match(input) ;

			}

			return null ;

		}

		return this ;

	}



	@Override

	public int interpret(String input) throws SQLException {

		Folder folder = FolderBuilder.getFolder();

		String tableName = Utility.getTheNMatcher(regex, input, 1);

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

		ArrayList<Row> rows = table.getRows();

		ArrayList<Row> rowsAfterDelete = new ArrayList<>() ;

		String selector = Utility.getTheNMatcher(regex, input, 2).toLowerCase();

	//	System.out.println(selector);

		for (Row i : rows) {

			Operator solver = OperatorChain.getInstance().getMatcher(Utility.getTheNMatcher(regex, input, 3)) ;

			if(solver == null){

				return 0 ; 

			}

			if(solver.compare(i, table, selector , Utility.getTheNMatcher(regex, input, 4).replaceAll("'", "").toLowerCase())){

				continue ;

			}

			rowsAfterDelete.add(i) ;

		}

		table.setRows(rowsAfterDelete);

		SaveXML saver = new SaveXML();

		try {

			saver.save(table);

			

		} catch (Exception e) {

			return 0;

		}

		return rows.size() - rowsAfterDelete.size() ;

	}



	@Override

	public void setNextInChain(Expression next) {

		this.next = next ; 

	}



}