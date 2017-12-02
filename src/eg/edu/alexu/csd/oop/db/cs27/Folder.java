package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.util.ArrayList;



public class Folder {

	private ArrayList<Table> tables;

	private String path;

	

	public Folder(String path) {

		this.path = path;

		tables = new ArrayList<>();

		File x = new File(path) ;

		x.mkdirs() ;

	}

	

	public void addTable(Table table) {

		tables.add(table);

	}

	

	public boolean removeTable(String tablePath) {

		for (Table x : tables) {

//			System.out.println("d5l");

//			System.out.println(x.getPath());

//			System.out.println(tablePath);

			if (x.getPath().equalsIgnoreCase(tablePath)) {

				tables.remove(x);

				return true;

			}

		}

		return false;

	}

	

	public ArrayList<Table> getTableList() {

		return tables;

	}

	

	public String getPath() {

		return path;

	}

	

	public boolean exists() {

		return false;

	}

	

	public boolean contains(String path) {

		for (Table x : tables) {

			if (x.getPath().equalsIgnoreCase(path)) {

				return true;

			}

		}

		return false;

	}

	public String getSimpleName (){

		return this.getPath().substring(3) ;

	}

}