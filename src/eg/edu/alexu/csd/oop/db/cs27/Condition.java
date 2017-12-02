package eg.edu.alexu.csd.oop.db.cs27;



public class Condition implements Command {



	@Override

	public boolean execute(String a, String b, char operation) {

		if (operation == '>') {

			Greaterthan gt = new Greaterthan();

			return gt.execute(a, b, operation);

		} else if (operation == '<') {

			Lessthan lt = new Lessthan();

			return lt.execute(a, b, operation);

		} else if (operation == '=') {

			Equal e = new Equal();

			return e.execute(a, b, operation);

		}

		return false;

	}



}