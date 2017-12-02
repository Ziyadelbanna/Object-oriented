package eg.edu.alexu.csd.oop.db.cs27;

import java.util.LinkedList;

public abstract class XmlOperations {
	public abstract void operate(String dbPath, String tablePath, LinkedList<String> keys, LinkedList<String> values);

	public abstract LinkedList<String[]> operate(String dbPath, String tablePath, LinkedList<String> seekColumn,
			boolean isCondition, String conditionController, LinkedList<String> expression);

	public abstract boolean operate(String dbName);

	public abstract boolean operate(String dbName, String table);

}