package eg.edu.alexu.csd.oop.database.cs27;



public class InterpreterFactory {



	public static enum QueryType {

		StructureQuery, SelectQuery, UpdateQuery;

	}



	public Interpreter createInterpreter(QueryType qType) {

		if (qType == QueryType.StructureQuery)

			return (Interpreter) createStructureQInterpreter();

		else if (qType == QueryType.SelectQuery)

			return (Interpreter) createSelectQInterpreter();

		else if (qType == QueryType.UpdateQuery)

			return (Interpreter) createUpdateQInterpreter();

		return null;

	}



	private Interpreter createSelectQInterpreter() {

		Interpreter[] selectQueries = { new SelectQueryInterpreter() };

		return new Interpreter(selectQueries);

	}



	private Interpreter createStructureQInterpreter() {

		Interpreter[] structureQueries = { new TableStructInterpreter(), new DBStructInterpreter() };

		return new Interpreter(structureQueries);

	}



	private Interpreter createUpdateQInterpreter() {

		Interpreter[] updateQueries = { new SetQueryInterpreter(), new InsertQueryInterpreter(),

				new DeleteQueryInterpreter() };

		return new Interpreter(updateQueries);

	}

}