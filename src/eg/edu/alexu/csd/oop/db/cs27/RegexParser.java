package eg.edu.alexu.csd.oop.db.cs27;



import java.util.ArrayList;



public class RegexParser {

	public static void main(String[] args) throws Exception {

//		String regex = "(?i)select \\* from (\\w+)";

//		String input = "SELECT * from students" ; 

//		Pattern pattern = Pattern.compile(regex) ;

//		Matcher matcher = pattern.matcher(input) ;

//		System.out.println(matcher.matches());

		

		

		

		//FolderBuilder.create("D:" + File.separator + "folder");

		String input = "create database myDatabase" ;

		CreateDataBase x1 = new CreateDataBase() ;

		Expression zero = x1.match(input) ;

		zero.interpret(input) ;

		

		

		Expression create = new CreateTable();

		

		

		String query = "create table students (id int, name varchar, grade int)";

		

		if (create.match(query) != null) {

			Expression solver = create.match(query);

			solver.interpret(query);

		}

		

		else {

			System.out.println("fail");

		}

		

		

		ArrayList<String> insert = new ArrayList<>();

		insert.add("insert into students(3, 'Ahmed Eid', 19)");

		insert.add("insert into students(5, 'Sherouq', 20)");

		insert.add("insert into students(7, 'Shehab', 15)");

		insert.add("INSERT INTO students (99, 'sherif', 100)");

		Expression insertion = new Insert();

		for (String x : insert) {

			if (insertion.match(x) != null) {

				Expression solver = insertion.match(x);

				solver.interpret(x);

			}

			else {

				System.out.println("fail");

			}

		}

		

		

				

		ISelect solver3 = new SelectAll() ; 

		ISelect res3 = solver3.match("select * from students") ;

		Object [][] answer = res3.interpret("select * from students") ;

		for(int i = 0 ; i < 4 ; i++){

			for(int j = 0 ; j < 3 ; j++){

				System.out.println((String)answer[i][j]);

			}

			System.out.println("");

		}

		

		

		

		

		

		ISelect solver4 = new SelectColumn() ;

		ISelect res4 = solver4.match("select name from students") ;

		Object [][] answer2 = res4.interpret("select name from students") ;

		for(int i = 0 ; i < 4 ; i++){

			for(int j = 0 ; j < 1 ; j++){

				System.out.println(answer2[i][j]);

			}

			System.out.println("");

		}

		

		

		

		

		ISelect solver5 = new SelectConditionedCol() ;

		ISelect res5 = solver5.match("select grade from students where name = Ahmed Eid") ;

		Object [][] answer3 = res5.interpret("select grade from students where name = Ahmed Eid") ;

		for(int i = 0 ; i < 1 ; i++){

			for(int j = 0 ; j < 1 ; j++){

				System.out.println(answer3[i][j]);

			}

			System.out.println("");

		}

		

	}

}