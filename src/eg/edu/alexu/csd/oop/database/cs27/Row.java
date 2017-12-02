package eg.edu.alexu.csd.oop.database.cs27;



import java.util.Hashtable;

import java.util.Map;



public class Row {

	

	private Map<String, Comparable<?>> data;

	

	public Row(){

		data = new Hashtable<>();

	}

	

	public void setData(Map<String, Comparable<?>> data){

		this.data = data;

	}

	

	private Map<String, Comparable<?>> getData(){

		return data;

	}

	

	public void putAll(Row newRow){

		Map<String, Comparable<?>> newRowData = newRow.getData();

		data.putAll(newRowData);

	}

	

	public void put(String colName, Comparable<?> val){

		data.put(colName, val);

	}

	

	public Comparable<?> get (String colName){

		return data.get(colName);

	}

}