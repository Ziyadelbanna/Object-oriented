package eg.edu.alexu.csd.oop.database.cs27;



public class ResultDataWarpper {



	private String[] colNames, colTypes;

	private String tableName;

	private Object[][] data;



	public String[] getColNames() {

		return colNames;

	}



	public void setColNames(String[] colNames) {

		this.colNames = colNames;

	}



	public String[] getColTypes() {

		return colTypes;

	}



	public void setColTypes(String[] colTypes) {

		this.colTypes = colTypes;

	}



	public String getTableName() {

		return tableName;

	}



	public void setTableName(String tableName) {

		this.tableName = tableName;

	}



	public Object[][] getData() {

		return data;

	}



	public void setData(Object[][] data) {

		this.data = data;

	}



}