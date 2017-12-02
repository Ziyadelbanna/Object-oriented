package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import eg.edu.alexu.csd.oop.db.Expression;





public class CreateDataBase implements Expression {



	private final String regex = "(?i)\\s*create\\s+database\\s+(.+)";

	private Expression next ; 

	@Override

	public Expression match(String input) {

		boolean res = Utility.matches(regex, input) ;

		if(res == false){

			if(next != null){

				return next.match(input) ;

			}

		}

		return this ; 

	}



	@Override

	public int interpret(String input) {

		// changed to lower case

		String databaseName = Utility.getTheNMatcher(regex, input, 1).toLowerCase();

		File x = new File("C:" + File.separator + databaseName) ;

		if(x.isDirectory()){

			// You should return true if it is exists --> press like and say "sbhan allah"

			FolderBuilder.create("C:" + File.separator + databaseName);

			return 1; 

		}

		FolderBuilder.create("C:" + File.separator + databaseName);

		return 1 ;

	}



	@Override

	public void setNextInChain(Expression next) {

		this.next = next ; 

		

	}





}