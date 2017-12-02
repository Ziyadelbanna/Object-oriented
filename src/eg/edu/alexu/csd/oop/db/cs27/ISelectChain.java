package eg.edu.alexu.csd.oop.db.cs27;

import eg.edu.alexu.csd.oop.db.ISelect;

public class ISelectChain {

private static ISelectChain instance;

	

	private ISelect root;

	

	private ISelectChain() {

		ISelect selectAll = new SelectAll() ; 

		ISelect selectCol = new SelectColumn() ;

		ISelect selectCondition = new SelectConditionedCol() ;

		ISelect SelectAllCond = new SelectAllConditioned() ;

		selectAll.setNextInChain(selectCondition);

		selectCondition.setNextInChain(selectCol);

		selectCol.setNextInChain(SelectAllCond);

		SelectAllCond.setNextInChain(null);

		root = selectAll ;

	}

	

	public static ISelectChain getInstance() {

		if (instance == null) {

			instance = new ISelectChain();

		}

		return instance;

	}

	

	public  ISelect getMatcher(String input) {

		return root.match(input);

	}

}