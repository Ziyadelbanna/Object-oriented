package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;



import java.sql.SQLException;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.ISelect;



public class SelectConditionedCol implements ISelect {



	private ISelect next = null;

	private String regex = "(?i)select (\\w+) from (\\w+) where (\\w+) (.{1}) (.+)";



	@Override

	public void setNextInChain(ISelect next) {

		this.next = next;

	}



	@Override

	public ISelect match(String input) {

		boolean res = Utility.matches(regex, input);

		if (res == false) {

			if (next != null) {

				return next.match(input);

			}

			return null;

		}

//		try {

//			java.nio.file.Files.write( java.nio.file.Paths.get("/debug/sss.log"), "some debugging messages".getBytes(), StandardOpenOption.CREATE);

//		} catch (IOException e1) {

//			// TODO Auto-generated catch block

//			e1.printStackTrace();

//		}

		System.out.println("matched here");

		return this;

	}



	@Override

	public Object[][] interpret(String input) throws SQLException {

//		try {

//			java.nio.file.Files.write( java.nio.file.Paths.get("/debug/sss.log"), "some debugging messages".getBytes(), StandardOpenOption.CREATE);

//		} catch (IOException e1) {

//			// TODO Auto-generated catch block

//			e1.printStackTrace();

//		}

		Object [][] empty = new Object[0][0] ;

		Folder folder = FolderBuilder.getFolder();

		if(folder == null){

			return empty ;

		}

		String tableName = Utility.getTheNMatcher(regex, input, 2);

		ReadXML reader = new ReadXML();

		String path = folder.getPath() + File.separator + tableName + ".xml";

		if (folder.contains(path) == false) {

			return empty;

		}

		Table table;

		try {

			table = reader.read(path);

		} catch (Exception e) {

			return empty;

		}

		ArrayList<Row> rows = table.getRows();

		ArrayList<Object> selectedAttributes = new ArrayList<Object>();

		String selector = Utility.getTheNMatcher(regex, input, 1).toLowerCase();

	//	System.out.println(selector);

		for (Row i : rows) {

			String attr = i.getAttribute(selector);

	//		System.out.println(attr);

			Operator solver = OperatorChain.getInstance().getMatcher(Utility.getTheNMatcher(regex, input, 4)) ;

			if(solver == null){

				return empty;

			}

			if(solver.compare(i, table, Utility.getTheNMatcher(regex, input, 3).toLowerCase(), Utility.getTheNMatcher(regex, input, 5))){

				selectedAttributes.add(attr);

			}

		}

		Object[][] ans = new Object[selectedAttributes.size()][];

		for (int i = 0; i < selectedAttributes.size(); i++) {

			Object tmp1 = selectedAttributes.get(i);

			Object[] tmp = new Object[1];

			tmp[0] = tmp1;

			ans[i] = tmp;

		}

		return ans;

	}

}