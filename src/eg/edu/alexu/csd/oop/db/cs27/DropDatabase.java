package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;



public class DropDatabase implements Expression{



	private Expression next = null ; 

	private String regex = "(?i)\\s*drop\\s*database\\s*(\\w+)" ;

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

	public int interpret(String input) {

		String databaseName = Utility.getTheNMatcher(regex, input, 1) ;

		databaseName = databaseName.toLowerCase() ;

		File del = new File("C:" + File.separator + databaseName) ;

		if(del.isDirectory()){

			String[]entries = del.list();

			for(String s: entries){

			    File currentFile = new File(del.getPath(),s);

			    currentFile.delete();

			}

			del.delete() ;

			if (FolderBuilder.getFolder() == null) {

				return 1;

			}

			if(FolderBuilder.getFolder().getSimpleName().equalsIgnoreCase(databaseName)){

				FolderBuilder.setFolder(null);				

			}

			return 1 ; 

		}

		return 0;

	}



	@Override

	public void setNextInChain(Expression next) {

		this.next = next ; 

	}



}