package eg.edu.alexu.csd.oop.db.cs27.parse;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import eg.edu.alexu.csd.oop.db.cs27.XmlFactory;
import eg.edu.alexu.csd.oop.db.cs27.xmlWritrer;
import eg.edu.alexu.csd.oop.db.cs27.Domdeleter;
import eg.edu.alexu.csd.oop.db.cs27.DomUpdater;
import eg.edu.alexu.csd.oop.db.cs27.DBCreator;
import eg.edu.alexu.csd.oop.db.cs27.DropDataBase;
import eg.edu.alexu.csd.oop.db.cs27.XmlDropTable;
import eg.edu.alexu.csd.oop.db.cs27.XMLinsert;
import eg.edu.alexu.csd.oop.db.cs27.XMLSelect;

public class CasesofQuery {

	XmlFactory factory = new XmlFactory();

	public Object getOutput(Map<String, Object> parseInput)
			throws FileNotFoundException, XMLStreamException, SQLException {
		String operation = (String) (parseInput.get("Operation"));
		Object xmlInstance = factory.getXmlInstance(operation);
		switch (operation) {
		case "Select":
			String dbPath = (String) parseInput.get("DBName");
			String tablePath = (String) parseInput.get("TableName");
			ArrayList<String> colNames = (ArrayList<String>) parseInput.get("ColumnNames");
			LinkedList<String> seekColumn = new LinkedList<String>();
			if (colNames == null) {
				seekColumn = null;
			} else {
				seekColumn.addAll(colNames);

			}
			ArrayList<String> condition = (ArrayList<String>) parseInput.get("Conditions");
			boolean isCondition = condition != null;
			LinkedList<String> expression = null;
			String conditionController = null;
			if (isCondition) {
				expression = new LinkedList<String>();
				expression.addAll(condition);
				conditionController = expression.getFirst();
			}
			/*
			 * System.out.println(dbPath); System.out.println(tablePath);
			 * System.out.println(isCondition); System.out.println(seekColumn);
			 * System.out.println(expression);
			 */
			LinkedList<Object[]> resultx = ((XMLSelect) xmlInstance).selectFrom(dbPath, tablePath, seekColumn,
					isCondition, conditionController, expression);
			Object[][] result = null;
			result = (Object[][]) resultx.toArray(new Object[0][0]);
			return result;

		case "Insert":
			String dbPathI = (String) parseInput.get("DBName");
			String tablePathI = (String) parseInput.get("TableName");
			LinkedList<String> keys = (LinkedList<String>) parseInput.get("ColumnNames");
			LinkedList<String> values = (LinkedList<String>) parseInput.get("ColumnValues");

			((XMLinsert) xmlInstance).insertInto(dbPathI, tablePathI, keys, values);
			return 1;
		case "Delete":
			String dbPathD = (String) parseInput.get("DBName");
			String tablePathD = (String) parseInput.get("TableName");
			// System.out.println(tablePathD);
			ArrayList<String> conditionD = (ArrayList<String>) parseInput.get("Conditions");
			String[] con = null;
			con = conditionD.toArray(new String[0]);
			return ((Domdeleter) xmlInstance).deleteTable(dbPathD, tablePathD, con);

		case "Update":
			String dbPathU = (String) parseInput.get("DBName");
			String tablePathU = (String) parseInput.get("TableName");
			ArrayList<String> conditionU = (ArrayList<String>) parseInput.get("Conditions");
			String[] conU = new String[3];
			conU = conditionU.toArray(new String[0]);
			ArrayList<String> keysU = (ArrayList<String>) parseInput.get("ColumnNames");
			ArrayList<String> valuesU = (ArrayList<String>) parseInput.get("ColumnValues");
			return ((DomUpdater) xmlInstance).updatetable(dbPathU, tablePathU, keysU, valuesU, conU);

		case "CreateTable":
			String dbPathC = (String) parseInput.get("DBName");
			String name = (String) parseInput.get("TableName");

			ArrayList<String> columnsName = (ArrayList<String>) parseInput.get("ColumnNames");
			ArrayList<String> dataType = (ArrayList<String>) parseInput.get("ColumnType");

			((xmlWritrer) xmlInstance).createTable(dbPathC, name, columnsName, dataType);
			return null;
		// return null;

		case "CreateDB":

			String dbPathC1 = (String) parseInput.get("DataBaseName");

			return ((DBCreator) xmlInstance).createDataBase(dbPathC1);
		case "DropTable":

			String dbPathC2 = (String) parseInput.get("DBName");
			String tablePathDR = (String) parseInput.get("TableName");

			((XmlDropTable) xmlInstance).dropTable(dbPathC2, tablePathDR);
			return null;
		case "DropDB":

			String dbPathC21 = (String) parseInput.get("DataBaseName");
			String tablePathDR1 = (String) parseInput.get("TableName");

			((DropDataBase) xmlInstance).dropTable(dbPathC21, tablePathDR1);
			return null;

		}

		return null;
	}
}