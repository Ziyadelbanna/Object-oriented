package eg.edu.alexu.csd.oop.db.cs27;

import eg.edu.alexu.csd.oop.db.Expression;

public class ExpressionChain {



private static ExpressionChain instance;

	

	private Expression root;

	

	private ExpressionChain() {

		Expression update = new Update() ; 

		Expression createTable = new CreateTable() ;

		Expression createDatabase = new CreateDataBase() ; 

		Expression ins = new Insert() ;

		Expression dropTable = new DropTable() ;

		Expression dropDatabase = new DropDatabase() ;

		Expression deleteTableContents = new DeleteTableContents() ;

		Expression deleteCondition = new DeleteConditioned() ;

		Expression tarek = new UpdateNoCon();

		Expression insCoNames = new InsertWithColName();

		

		

		createDatabase.setNextInChain(createTable);

		createTable.setNextInChain(ins);

		ins.setNextInChain(update);

		update.setNextInChain(dropTable);

		dropTable.setNextInChain(dropDatabase);

		dropDatabase.setNextInChain(deleteTableContents);

		deleteTableContents.setNextInChain(deleteCondition);

		deleteCondition.setNextInChain(tarek);

		tarek.setNextInChain(insCoNames);

		insCoNames.setNextInChain(null);

		root = createDatabase ;

	}

	

	public static ExpressionChain getInstance() {

		if (instance == null) {

			instance = new ExpressionChain();

		}

		return instance;

	}

	

	public Expression getMatcher(String input) {

		return root.match(input);

	}

}