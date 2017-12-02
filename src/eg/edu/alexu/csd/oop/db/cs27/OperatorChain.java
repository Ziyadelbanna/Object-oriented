package eg.edu.alexu.csd.oop.db.cs27;



public class OperatorChain {

	private static OperatorChain instance;

	

	private Operator root;

	

	private OperatorChain() {

		Operator greaterThan = new GreaterThan();

		Operator lessThan = new LessThan();

		Operator equals = new Equal();

		greaterThan.setNextInChain(lessThan);

		lessThan.setNextInChain(equals);

		root = greaterThan;

	}

	public static OperatorChain getInstance() {

		if (instance == null) {

			instance = new OperatorChain();

		}

		return instance;

	}

	

	public Operator getMatcher(String symbol) {

		return root.match(symbol);

	}

	

}