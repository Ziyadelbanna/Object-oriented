package eg.edu.alexu.csd.oop.database.cs27;



import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Iterator;

import java.util.List;

import java.util.Map;

import java.util.Observable;



public class Table extends Observable{



	private List<Row> rows;

	private String[] colNames;

	private Map<String, Class<? extends Comparable<?>>> colTypes;

	private String tableName;

	

	public Table(String tableName, String[] colNames, Map<String, Class<? extends Comparable<?>>> colTypes){

		this.tableName = tableName;

		this.colNames = colNames;

		this.colTypes = colTypes;

		rows = new ArrayList<>();

	}

	

	public String getTableName(){

		return tableName;

	}

	

	public String[] getColNames() {

		return colNames;

	}



	public Map<String, Class<? extends Comparable<?>>> getColTypes() {

		return colTypes;

	}



	/*

	 * add list of rows to existing list of rows, expected to be used in:

	 * reading rows form file

	 * 

	 */

	public void addRows(List<Row> rows) {

		this.rows.addAll(rows);

	}



	/*

	 * add one row to existing list of rows, expected to be used in: [INSERT

	 * INTO] statement

	 */

	public void addRow(Row row) {

		rows.add(row);

		setChanged();

		notifyObservers();

	}



	/*

	 * delete rows from rows that satisfy the given condition expected to be

	 * used in [DELETE FROM] SQL statement

	 * 

	 * @return number of rows deleted

	 */



	public int deleteRows(Condition cond) throws SQLException {

		int cnt = 0;

		Iterator<Row> it = rows.iterator();

		while (it.hasNext()) {

			Row row = it.next();

			if (cond.applysTo(row)) {

				it.remove();

				++cnt;

				setChanged();

			}

		}

		notifyObservers();

		return cnt;

	}



	/*

	 * update rows by (adding to or overWriting) existing row that satisfy the

	 * given condition - expected to be used in [UPDATE SET] SQL statement

	 * 

	 * @return number of rows updated

	 */



	public int update(Row newRow, Condition cond) throws SQLException {

		int cnt = 0;

		for (int i = 0; i < rows.size(); ++i) {

			Row row = rows.get(i);

			if (cond.applysTo(row)) {

				// overwrite the data by the given newRow

				row.putAll(newRow);

				rows.set(i, row);

				++cnt;

				setChanged();

			}

		}

		notifyObservers();

		return cnt;

	}



	/*

	 * return a list with the rows that satisfy the given condition expected to

	 * be used in: [SELECT] SQL statement

	 */

	public List<Row> getRows(Condition cond) throws SQLException {

		List<Row> rowsList = new ArrayList<>();

		for (Row row : rows) {

			if (cond.applysTo(row)) {

				rowsList.add(row);

			}

		}

		return rowsList;

	}



	/*

	 * return a list represents the whole rows - expected to be used in:

	 * writing rows to file

	 */

	public List<Row> getAllRows() {

		return rows;

	}

	

	public String toString(){

		StringBuilder bldr = new StringBuilder();

		bldr.append("table : " + getTableName() + "\n");

		bldr.append("number of rows : " + rows.size() + "\n");

		for (Row row : rows){

			for (String col : colNames)

				bldr.append(col + "=" + row.get(col) + " | ");

			bldr.append("\n");

		}

		return bldr.toString();

	}

}