package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.util.ArrayList;



public class SelectColumn implements ISelect {



	private ISelect next = null;

	private String regex = "(?i)select (\\w+) from (\\w+)";



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

			return null ; 

			//throw new RuntimeException("msh match hna t2ryban") ; 

		}

		System.out.println("here too");

		return this;

	}



	@Override

	public Object[][] interpret(String input) {

		Object [][] empty = new Object[0][0] ;

		Folder folder = FolderBuilder.getFolder();

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

		ArrayList<Object> selectedAttributes = new ArrayList<Object>() ;

		String selector = Utility.getTheNMatcher(regex, input, 1) ;

		for(Row i : rows){

			String attr = i.getAttribute(selector) ;

		//	System.out.println(attr);

			selectedAttributes.add(attr) ;

		}

		Object [][] ans = new Object[selectedAttributes.size()][] ;

		for (int i = 0; i < selectedAttributes.size(); i++) {

			Object tmp1 =  selectedAttributes.get(i) ; 

			Object [] tmp  = new Object[1] ;

			tmp[0] = tmp1 ;

			ans[i] = tmp ;

		}

		return ans;

	}



}