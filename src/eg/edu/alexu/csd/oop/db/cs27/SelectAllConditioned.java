package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Map;

import eg.edu.alexu.csd.oop.db.ISelect;



public class SelectAllConditioned implements ISelect{



	private ISelect next = null ; 

	private String regex = "(?i)\\s*select\\s*\\*\\s*from\\s*(\\w+)\\s+where\\s+(\\w+)\\s*(=|>|<)\\s*(\\w+)";

	@Override

	public void setNextInChain(ISelect next) {

		this.next = next ; 

	}



	@Override

	public ISelect match(String input) {

		boolean res = Utility.matches(regex, input);

		if (res == false) {

			if (next != null) {

				return next.match(input);

			}

			return null ; 

		}

		return this;

	}

	



	@Override

	public Object[][] interpret(String input) throws SQLException {

		Object [][] empty = new Object[0][0] ;

		Folder folder = FolderBuilder.getFolder();

		if(folder == null){

			return empty ;

		}

		String tableName = Utility.getTheNMatcher(regex, input, 1);

	//	System.out.println(tableName);

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

	//	System.out.println("here");

		ArrayList<Row> rows = table.getRows();

		ArrayList<Row> selectedRows = new ArrayList<Row>() ; 

	//	String selector = Utility.getTheNMatcher(regex, input, 2).toLowerCase();

		//"(?i)\\s*select\\s*\\*\\s*from\\s*(\\w+)\\s+where\\s+(\\w+)\\s*(=|>|<)\\s*(\\w+)";

		for (Row i : rows) {

		//	String attr = i.getAttribute(selector);

			Operator solver = OperatorChain.getInstance().getMatcher(Utility.getTheNMatcher(regex, input, 3)) ;

			if(solver == null){

				return empty;

			}

			if(solver.compare(i, table, Utility.getTheNMatcher(regex, input, 2).toLowerCase(), Utility.getTheNMatcher(regex, input, 4))){

				selectedRows.add(i) ;

			}

		}

		

		Map<String , String> m = table.getMap() ;

		for(Map.Entry<String, String> entry : m.entrySet()){

			System.out.println(entry.getKey() + "   " + entry.getValue());

		}

		Object[][] ans = new Object[selectedRows.size()][];

		int count = 0 ; 

		for(Row i : selectedRows){

			Map <String , String> attr = i.getAllAttributes() ;

			ArrayList<Object> oneD = new ArrayList<>() ;

			for(Map.Entry<String, String> entry : attr.entrySet()){

				String val = entry.getValue() ;

				String type = table.getMap().get(entry.getKey());

				System.out.println("type is " + type);

				System.out.println("value is " + val);

				if(Utility.isDigit(val)){

					Integer castToInt = Integer.parseInt(val) ;

					oneD.add(castToInt) ;

				}

				else{

					oneD.add(val) ;

				}

			}

			ans[count++] = oneD.toArray() ;

			System.out.println("----------------------");

		}

		return ans;

	}



}