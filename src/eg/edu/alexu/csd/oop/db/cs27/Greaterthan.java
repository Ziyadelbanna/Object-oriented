package eg.edu.alexu.csd.oop.db.cs27;



public class Greaterthan implements Command {

	@Override

	public boolean execute(String a, String b, char operation) {

		Integer comp = Integer.parseInt(a);

		Integer comp2 = Integer.parseInt(b);

		return comp.compareTo(comp2) > 0;

	}

}