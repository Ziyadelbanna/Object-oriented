package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.sql.SQLException;





public class DropTable implements Expression{



	private Expression next = null ;

	private String regex = "(?i)drop table (\\w+)" ;

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

		Folder database = FolderBuilder.getFolder() ;

		if(database == null){

			return 0;

		}

	//	ArrayList<Table> tables = database.getTableList() ;

		String targetedTableName = Utility.getTheNMatcher(regex, input, 1) ;

		String tablePath = database.getPath() + File.separator + targetedTableName + ".xml";

		if(database.removeTable(tablePath)){

			File delMe = new File(tablePath) ;

			delMe.delete() ;

			File x = new File(FolderBuilder.getFolder().getPath()) ;

			String [] dirs = x.list() ;

			if(dirs.length == 1){

				File del = new File(FolderBuilder.getFolder().getPath() + File.separator + dirs[0]) ;

				del.delete() ;

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