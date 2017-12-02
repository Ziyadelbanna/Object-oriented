package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.util.ArrayList;

import java.util.Map;

import eg.edu.alexu.csd.oop.db.ISelect;



public class SelectAll implements ISelect {



	private static String regex = "(?i)\\s*select\\s*\\*\\s*from\\s*(\\w+)";

	private ISelect next;



	@Override

	public ISelect match(String input) {

		boolean res = Utility.matches(regex, input);

		if (res == false) {

			if (next != null) {

				return next.match(input);

			}

			throw new RuntimeException("msh match hna t2ryban") ; 

		}

		return this;

	}



	@Override

	public Object[][] interpret(String input) {

		Folder folder = FolderBuilder.getFolder();



		String tableName = Utility.getTableName(regex, input);



		ReadXML reader = new ReadXML();

		

		Object[][] empty = new Object[0][0];



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

		Object[][] ans = new Object[rows.size()][];

		int count = 0 ;

		for(Row i : rows){

			Map <String , String> attr = i.getAllAttributes() ;

			ArrayList<Object> oneD = new ArrayList<>() ;

			for(Map.Entry<String, String> entry : attr.entrySet()){

				String val = entry.getValue() ;

				if(Utility.isDigit(val)){

					Integer castToInt = Integer.parseInt(val) ;

					oneD.add(castToInt) ;

				}

				else{

					oneD.add(val) ; 					

				}

			}

			ans[count++] = oneD.toArray() ;

		}

		return ans;

	}



	@Override

	public void setNextInChain(ISelect next) {



		this.next = next;

	}



}