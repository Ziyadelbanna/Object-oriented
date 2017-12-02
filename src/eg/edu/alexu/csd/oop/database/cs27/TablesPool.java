package eg.edu.alexu.csd.oop.database.cs27;



import java.io.File;

import java.io.IOException;

import java.util.Hashtable;

import java.util.Map;

import java.util.Observable;

import java.util.Observer;



import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.TransformerException;

import javax.xml.transform.TransformerFactoryConfigurationError;



import org.xml.sax.SAXException;



import eg.edu.alexu.csd.oop.database.cs27.XMLParser;



public class TablesPool implements Observer {



	private Map<String, Table> loadedTables;

	private String databasePath;

	private XMLParser parser;

	private boolean isConnected = false;

	

	

	public boolean connectTo(String databasePath) {

		dropConnection();

		if (FileOperations.isDirectoryExist(databasePath)) {

			this.databasePath = databasePath;

			parser = new XMLParser();

			loadedTables = new Hashtable<>();

			isConnected = true;

		}

		return isConnected();

	}



	public void dropConnection() {

		this.databasePath = null;

		parser = null;

		loadedTables = null;

		isConnected = false;

	}



	public boolean isConnected() {

		return isConnected;

	}

	

	public String getDataBasePath() {

		return databasePath;

	}



	public String getTablePath(String tableName) {

		String fullPath = getDataBasePath();

		if (!fullPath.endsWith(File.separator))

			fullPath += File.separator;

		return fullPath + tableName + ".xml";

	}



	public String getTablePath(Table table) {

		return getTablePath(table.getTableName());

	}

	

	public Table getTableByName(String tableName) {

		Table tempTable = loadedTables.get(tableName);

		if (tempTable != null)

			return tempTable;

		try {

			tempTable = parser.readXML(getTablePath(tableName));

			tempTable.addObserver(this);

			loadedTables.put(tableName, tempTable);

		} catch (Exception e) {

			System.out.println("can not read table " + tableName + " error " + e.getMessage());

		}

		return tempTable;

	}

	

	@Override

	public void update(Observable arg, Object arg1) {

		Table table = (Table) arg;

		try {

			System.out.print("updating : ");

			parser.writeXML(getTablePath(table), table);

		} catch (NullPointerException | ParserConfigurationException | TransformerFactoryConfigurationError

				| TransformerException e) {

			System.out.println("Error: writing updated table to file " + e.getMessage());

		}

	}



}