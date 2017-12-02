package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import java.util.LinkedHashMap;

import java.util.Map;

import java.util.regex.Matcher;

import java.util.regex.Pattern;



public class Table {

	

	private Map<String, String> map;

	private ArrayList<Row> rows;

	private String path;

	

	public Table(String path) {

		rows = new ArrayList<>();

		this.path = path;

		File newTable = new File(path) ;

		try {

			if(!newTable.exists()){

				newTable.createNewFile() ;

			}

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	

	public String getPath() {

		return path;

	}

	

	public Map<String, String> getMap() {

		return map;

	}

	

	public void setMap(Map<String, String> map) {

		this.map = new LinkedHashMap<>();

		this.map.putAll(map);

	}

	

	public ArrayList<Row> getRows() {

		return rows;

	}

	

	public void setRows(ArrayList<Row> rows) {

		this.rows = rows;

	}

	

	public void addRow(Row row) {

		rows.add(row);

	}

	

	public String getFolderPath() {

		Pattern pattern = Pattern.compile("(\\w+\\.xml)");

		Matcher match = pattern.matcher(getPath());

		String mat = "";

		while (match.find()) {

			if (match.group().length() != 0) {

			    mat = match.group(1);

			}

		}

		return getPath().replace(mat, "");

	}

	

	public boolean contianKey(String input) {

		if (map == null) {

			return false;

		}

		for (Map.Entry<String, String> entry : map.entrySet()) {

			String key = entry.getKey();

			if (input.equalsIgnoreCase(key)) {

				return true;

			}

		}

		return false;

	}

	

}