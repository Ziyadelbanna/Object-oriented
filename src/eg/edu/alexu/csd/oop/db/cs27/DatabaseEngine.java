package eg.edu.alexu.csd.oop.db.cs27;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import eg.edu.alexu.csd.oop.db.Database;

public class DatabaseEngine implements Database {

	Map<String, String> xmlFilesPathes = new HashMap<String, String>();

	public void createtable(String tablename, List<String> columns, List<String> datatypes) {
		if(dbDirectory == null || dbDirectory.isEmpty()) {
			return;
		}
		File f = new File(dbDirectory ,tablename + ".xml");
		try {
			f.createNewFile();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		xmlFilesPathes.put(tablename.toLowerCase(), f.getPath());
		Table t = new Table();
		t.columnsAvailable = columns;
		SavetoXml(f.getPath(), t);

	}

	public void droptable(String tablename) {
		File f = new File(xmlFilesPathes.get(tablename.toLowerCase()));
		f.delete();
	}

	public int insertIntoTable(String tableName, List<String> columns, List<Object> values) {

		int count = 0;
		if (columns == null) {
			Table table = LoadfromXml(xmlFilesPathes.get(tableName.toLowerCase()));
			Row r = new Row();
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) instanceof Integer) {
					map.put(table.columnsAvailable.get(i), (Integer) values.get(i));
				} else if (values.get(i) instanceof String) {
					map.put(table.columnsAvailable.get(i), (String) values.get(i));
				}

			}
			r.setrecord(map);
			count++;
			table.gettable().add(r);
			SavetoXml(xmlFilesPathes.get(tableName.toLowerCase()), table);

		} else {
			Table table = LoadfromXml(xmlFilesPathes.get(tableName.toLowerCase()));
			Row r = new Row();
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columns.size(); i++) {
				if (values.get(i) instanceof Integer) {
					map.put(columns.get(i), (Integer) values.get(i));
				} else if (values.get(i) instanceof String) {
					map.put(columns.get(i), (String) values.get(i));
				}
				count++;
			}
			r.setrecord(map);
			table.gettable().add(r);
			SavetoXml(xmlFilesPathes.get(tableName.toLowerCase()), table);
		}
		return count;
	}

	public Object[][] selectFromTable(String tableName, List<String> columns, String columnName, String operator, String value) {
		if (columns != null) {
			
			Table currentTable = LoadfromXml(xmlFilesPathes.get(tableName.toLowerCase()));
			int numCols = columns.size();
			Object[][] selected = new Object[currentTable.records.size()][numCols];
			for (int i = 0; i < currentTable.records.size(); i++) {
				boolean conditionMet = true;
				if(!columnName.isEmpty()) {
					switch(operator) {
					case "=":
						conditionMet = currentTable.records.get(i).record.get(columnName).equals(value);
						break;
					case ">":
						conditionMet = ((String) currentTable.records.get(i).record.get(columnName)).compareTo((String)value) > 0;
						break;
					case "<":
						conditionMet = ((String) currentTable.records.get(i).record.get(columnName)).compareTo((String)value) < 0;
						break;
					}
				}
				if(conditionMet) {
				for (int j = 0; j < numCols; j++) {
					selected[i][j] = currentTable.records.get(i).record.get(columns.get(j));
				}
				}
			}
			return selected;
		} else {
			Table currentTable = LoadfromXml(xmlFilesPathes.get(tableName.toLowerCase()));
			int numCols = currentTable.columnsAvailable.size();
			Object[][] selected = new Object[currentTable.records.size()][numCols];
			for (int i = 0; i < currentTable.records.size(); i++) {
				for (int j = 0; j < numCols; j++) {
					selected[i][j] = currentTable.records.get(i).record.get(currentTable.columnsAvailable.get(j));
				}
			}
			return selected;
		}
	}

	public int deleteFromTable(String tableName, String columnName, String operator, Object value) {
		int count = 0;
		Table currentTable = LoadfromXml(xmlFilesPathes.get(tableName.toLowerCase()));
		int tableSize = currentTable.records.size();
		for (int i = 0; i < tableSize; i++) {
			boolean conditionMet = true;
			if(columnName != null) {
				switch(operator) {
				case "=":
					conditionMet = currentTable.records.get(i).record.get(columnName).equals(value);
					break;
				case ">":
					conditionMet = ((String) currentTable.records.get(i).record.get(columnName)).compareTo((String)value) > 0;
					break;
				case "<":
					conditionMet = ((String) currentTable.records.get(i).record.get(columnName)).compareTo((String)value) < 0;
					break;
				}
			}
				if (conditionMet) {
					currentTable.records.remove(i);
					i--;
					tableSize--;
					count++;
				}
			 /*
				 * else if (operator.equals(">")) { if
				 * ((Integer)currentTable.records.get(i).record.get(columnName) >
				 * (Integer)value) {
				 * 
				 * } }
				 */

		}
		SavetoXml(xmlFilesPathes.get(tableName.toLowerCase()), currentTable);
		return count;
	}

	public int updateTable(String tableName, List<String> columns, List<Object> values, String condionalColumn,
			String operator, Object value) {
		int count = 0;
		Table currentTable = LoadfromXml(xmlFilesPathes.get(tableName.toLowerCase()));
		int tableSize = currentTable.records.size();

		for (int i = 0; i < tableSize; i++) {
			boolean conditionMet = true;
			if(!condionalColumn.isEmpty()) {
				switch(operator) {
				case "=":
					conditionMet = currentTable.records.get(i).record.get(condionalColumn).equals(value);
					break;
				case ">":
					conditionMet = ((String) currentTable.records.get(i).record.get(condionalColumn)).compareTo((String)value) > 0;
					break;
				case "<":
					conditionMet = ((String) currentTable.records.get(i).record.get(condionalColumn)).compareTo((String)value) < 0;
					break;
				}
			}
			if (conditionMet
					) {
				for (int j = 0; j < columns.size(); j++) {
					currentTable.records.get(i).record.put(columns.get(j), values.get(j));
					
				}
				count++;
			}
		}
		SavetoXml(xmlFilesPathes.get(tableName.toLowerCase()), currentTable);
		return count;
	}

	public void SavetoXml(String path, Table table) {

		File fos = new File(path);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Table.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// marshaller.marshal(table, System.out);
			marshaller.marshal(table, fos);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Table LoadfromXml(String path) {
		Table table = new Table();
		table.settable(new LinkedList<Row>(), new LinkedList<String>());
		File f = new File(path);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Table.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Table t = (Table) unmarshaller.unmarshal(f);
			for (Row c : t.gettable()) {
				table.gettable().add((Row) c);
			}
			for (String s : t.getavailcols()) {
				table.getavailcols().add(s);
			}
			// print
			// JAXBContext jaxbContext2 = JAXBContext.newInstance(Table.class);
			// Marshaller marshaller = jaxbContext2.createMarshaller();
			// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// marshaller.marshal(table, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static final boolean TESTING = false;
	
	private String dbDirectory = "";

	private final static class ColumnInfo {
		private String columnName;
		private String columnType;

		public ColumnInfo(String name, String type) {
			columnName = name;
			columnType = type;
		}

		public String getName() {
			return columnName;
		}

		public String getType() {
			return columnType;
		}
	}

	public DatabaseEngine() {
		File dbDirectory = new File("Database");
		if (!dbDirectory.exists()) {
			dbDirectory.mkdir();
		}
	}

	/**
	 * Matches a given SQL query against an expected query format defined by an
	 * array of tokens and returns a Matcher containing the result.
	 *
	 * The SQL query should match the following regular expression: "\s*{tokens,
	 * separated by one or more spaces}\s*;?\s*" Matching is done with the
	 * {@code Pattern.CASE_INSENSITIVE} and {@code Pattern.COMMENTS} flags.
	 *
	 * @param query
	 *            the SQL query to match
	 * @param expectedTokens
	 *            the tokens that define the expected query format. The tokens
	 *            themselves can be regular expressions.
	 * @return a Matcher containing the result of matching {@code query} against
	 *         the specified query format.
	 */
	private Matcher matchQuery(final String query, final String[] expectedTokens) {

		String joinedTokens = "";
		for (int i = 0; i < expectedTokens.length; ++i) {
			joinedTokens += expectedTokens[i];

			// Not last token?
			if (i < expectedTokens.length - 1) {
				joinedTokens += "\\s+";
			}
		}

		Pattern pattern = Pattern.compile(String.format("\\s* %s \\s* ;? \\s*", joinedTokens),
				Pattern.CASE_INSENSITIVE | Pattern.COMMENTS);
		return pattern.matcher(query);
	}

	public void deleteDirectory(String path) {
		File abstractFile = new File("Database", path);
		if (abstractFile.exists()) {
			/*
			 * File.list() ===> Returns an array of strings naming the files and
			 * directories in the directory denoted by this abstract pathname.
			 */
			String[] includedFilesList = abstractFile.list();
			if (includedFilesList.length == 0) {
				abstractFile.delete();
			} else {
				for (int i = 0; i < includedFilesList.length; i++) {
					File f = new File(abstractFile, includedFilesList[i]);
					if (f.isFile() && f.exists()) {
						f.delete();
					}
					if (f.isDirectory()) {
						deleteDirectory(f.getPath());
					}
				}
			}
		}
	}

	@Override
	public final String createDatabase(final String databaseName, final boolean dropIfExists) {

		File dbDirectory = new File("Database", databaseName);

		// Does NOT exist, or exists but must be dropped
		if (!dbDirectory.exists() || dropIfExists) {
			// NOTE: executeStructureQuery internally drops the
			// database if it exists before creation
			try {
				executeStructureQuery(String.format("create database %s", databaseName));
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		this.dbDirectory = dbDirectory.getPath();		
		return this.dbDirectory;
	}

	/**
	 * Creates/drops table or database.
	 *
	 * @param a
	 *            create or drop, table or database query
	 * @returns true if success, or false otherwise
	 * @throws SQLException
	 *             syntax error
	 */
	@Override
	public boolean executeStructureQuery(final String query) throws SQLException {
		// Create database
		Matcher createDbMatcher = matchQuery(query,
				new String[] { "create", "database", "(\\p{Alpha}[\\p{Alnum}_]*)" });
		if (createDbMatcher.matches()) {
			String databaseName = createDbMatcher.group(1);
			File dbDirectory = new File("Database", databaseName);
			if (dbDirectory.exists() && dbDirectory.isDirectory()) {
				deleteDirectory(databaseName);
			}
			boolean result = dbDirectory.mkdir();
			this.dbDirectory = dbDirectory.getPath();
			return true;
		}

		// Drop database
		Matcher dropDbMatcher = matchQuery(query, new String[] { "drop", "database", "(\\p{Alpha}[\\p{Alnum}_]*)" });
		if (dropDbMatcher.matches()) {
			String databaseName = dropDbMatcher.group(1);
			deleteDirectory(databaseName);
			if (this.dbDirectory.equalsIgnoreCase("Database"+System.getProperty("file.separator")+databaseName)) {
				this.dbDirectory = null;
			}
			return true;
		}

		// Create table
		Matcher createTableMatcher = matchQuery(query,
				new String[] { "create", "table",
						"(\\p{Alpha}[\\p{Alnum}_]*)" + "\\s* \\( \\s*"
								+ "(((\\p{Alpha}[\\p{Alnum}_]*) \\s+ (varchar|int) \\s* , \\s*)*"
								+ "((\\p{Alpha}[\\p{Alnum}_]*) \\s+ (varchar|int)))" + "\\s* \\)" });
		if (createTableMatcher.matches()) {
			String tableName = createTableMatcher.group(1);
			List<String> colNames = new LinkedList<String>();
			List<String> colValues = new LinkedList<String>();

			Pattern columnInfoPattern = Pattern.compile("(\\p{Alpha}[\\p{Alnum}_]*) \\s+ (varchar|int)",
					Pattern.CASE_INSENSITIVE | Pattern.COMMENTS);
			Matcher columnInfoMatcher = columnInfoPattern.matcher(createTableMatcher.group(2));
			while (columnInfoMatcher.find()) {
				colNames.add(columnInfoMatcher.group(1));
				colValues.add(columnInfoMatcher.group(2));
			}

			
			    if(this.dbDirectory == null || this.dbDirectory.isEmpty()) {
			    	return false;
			    }
				createtable(tableName, colNames, colValues);
				return true;
		}

		// Drop table
		Matcher dropTableMatcher = matchQuery(query, new String[] { "drop", "table", "(\\p{Alpha}[\\p{Alnum}_]*)" });
		if (dropTableMatcher.matches()) {
			String tableName = dropTableMatcher.group(1);
			droptable(tableName);
			return true;
		}

		throw new SQLException("Syntax error!\n" + "Expected a create/drop database/table query.\n" + "Got: " + query);
	}

	/**
	 * Select from table
	 * 
	 * @param query
	 *            select query
	 * @return the selected records or an empty array if no records match.
	 *         Columns types must be preserved (i.e. int column returns Integer
	 *         objects)
	 * @throws SQLException
	 *             syntax error
	 */
	@Override
	public Object[][] executeQuery(final String query) throws SQLException {
		// SELECT * FROM tableName;
		Matcher selectAllMatcher = matchQuery(query,
				new String[] { "select \\s* \\* \\s* from", "(\\p{Alpha}[\\p{Alnum}_]*)"
						+ "(\\s+where \\s+" + "(\\p{Alpha}[\\p{Alnum}_]*)" + "\\s* (=|>|<) \\s*"
						+ "(\\p{Digit}+ | '(\\p{Alnum}|\\p{Space})*'))?" });
		if (selectAllMatcher.matches()) {
			String tableName = selectAllMatcher.group(1);
			
			String colName = "";
			String comparison = "";
			String colValue = "";
			
			if (selectAllMatcher.group(2) != null) {
				colName = selectAllMatcher.group(3);
				comparison = selectAllMatcher.group(4);
				colValue = selectAllMatcher.group(5).replaceAll("'", "");
			}
			
			return selectFromTable(tableName, null, colName, comparison, colValue);
		}

		// SELECT {columns...} FROM tableName;
		Matcher selectColsMatcher = matchQuery(query,
				new String[] { "select", "((\\p{Alpha}[\\p{Alnum}_]* \\s* , \\s*)*" + "(\\p{Alpha}[\\p{Alnum}_]*))",
						"from", "(\\p{Alpha}[\\p{Alnum}_]*)"
								+ "(\\s+where \\s+" + "(\\p{Alpha}[\\p{Alnum}_]*)" + "\\s* (=|>|<) \\s*"
								+ "(\\p{Digit}+ | '(\\p{Alnum}|\\p{Space})*'))?" });
		if (selectColsMatcher.matches()) {
			String tableName = selectColsMatcher.group(4);
			List<String> columnNames = new LinkedList<String>();
			
			String colName = "";
			String comparison = "";
			String colValue = "";
			
			if (selectColsMatcher.group(5) != null) {
				
				colName = selectColsMatcher.group(6);
				comparison = selectColsMatcher.group(7);
				colValue = selectColsMatcher.group(8).replaceAll("'", "");
			}

			Pattern columnNamePattern = Pattern.compile("\\p{Alpha}[\\p{Alnum}_]*", Pattern.CASE_INSENSITIVE);
			Matcher columnNameMatcher = columnNamePattern.matcher(selectColsMatcher.group(1));
			while (columnNameMatcher.find()) {
				columnNames.add(columnNameMatcher.group());
			}

			if(5 != 4)
			return selectFromTable(tableName, columnNames, colName, comparison, colValue);

			if (TESTING) {
				System.out.println("Table: " + tableName);
				for (String col : columnNames) {
					System.out.println("\t" + col);
				}
				return null;
			}
		}

		throw new SQLException("Syntax error!\n" + "Expected a select query.\n" + "Got: " + query);
	}

	/**
	 * Insert or update or delete the data
	 * 
	 * @param query
	 *            data manipulation query
	 * @return the updated rows count
	 * @throws SQLException
	 *             syntax error
	 */
	@Override
	public int executeUpdateQuery(final String query) throws SQLException {
		// Delete from table
		Matcher deleteMatcher = matchQuery(query,
				new String[] { "delete", "from", "(\\p{Alpha}[\\p{Alnum}_]*)"
						+ "(\\s+where \\s+" + "(\\p{Alpha}[\\p{Alnum}_]*)" + "\\s* (=|>|<) \\s*"
								+ "(\\p{Digit}+ | '(\\p{Alnum}|\\p{Space})*'))?" });
		if (deleteMatcher.matches()) {
			String tableName = deleteMatcher.group(1);
			String colName = deleteMatcher.group(3);
			String comparison = deleteMatcher.group(4);
			String colValue = deleteMatcher.group(5).replaceAll("'", "");

			if(5 != 4) {
				int result = deleteFromTable(tableName, colName, comparison, colValue);
				if(result == 0) {
					throw new SQLException(query);
				}
				return result;
			}

			if (TESTING) {
				System.out.println("Table: " + tableName);
				System.out.println("Column: " + colName);
				System.out.println("Comparison: " + comparison);
				System.out.println("Value: " + colValue);
				return 0;
			}
		}

		// Insert into table (values only)
		String valuePattern = "(\\p{Digit}+ | '(\\p{Alnum}|\\p{Space})*')";
		Matcher insertValuesMatcher = matchQuery(query, new String[] { "insert", "into", "(\\p{Alpha}[\\p{Alnum}_]*)",
				"values" + "\\s* \\( \\s*(" + "(" + valuePattern + "\\s* , \\s*)*" + valuePattern + ")\\s* \\)" });
		if (insertValuesMatcher.matches()) {
			String tableName = insertValuesMatcher.group(1);
			List<Object> colValues = new LinkedList<Object>();

			Matcher valueMatcher = Pattern.compile(valuePattern, Pattern.CASE_INSENSITIVE | Pattern.COMMENTS)
					.matcher(insertValuesMatcher.group(2));
			while (valueMatcher.find()) {
				colValues.add(valueMatcher.group().replaceAll("'", ""));
			}

			if(4 != 3) 
				return insertIntoTable(tableName, null, colValues);

			if (TESTING) {
				System.out.println("Table: " + tableName);
				for (Object val : colValues) {
					System.out.println("\t" + val);
				}
				return 0;
			}
		}

		// Insert into table (column names and values)
		Matcher insertMatcher = matchQuery(query, new String[] { "insert", "into",
				"(\\p{Alpha}[\\p{Alnum}_]*)" + "\\s* \\( \\s*(" + "((\\p{Alpha}[\\p{Alnum}_]* \\s* , \\s*)*"
						+ "(\\p{Alpha}[\\p{Alnum}_]*))" + ")\\s* \\)",
				"values" + "\\s* \\( \\s*(" + "(" + valuePattern + "\\s* , \\s*)*" + valuePattern + ")\\s* \\)" });
		if (insertMatcher.matches()) {
			String tableName = insertMatcher.group(1);
			List<String> colNames = new LinkedList<String>();
			List<Object> colValues = new LinkedList<Object>();

			Matcher nameMatcher = Pattern
					.compile("\\p{Alpha}[\\p{Alnum}_]*", Pattern.CASE_INSENSITIVE | Pattern.COMMENTS)
					.matcher(insertMatcher.group(2));
			while (nameMatcher.find()) {
				colNames.add(nameMatcher.group());
			}

			Matcher valueMatcher = Pattern.compile(valuePattern, Pattern.CASE_INSENSITIVE | Pattern.COMMENTS)
					.matcher(insertMatcher.group(6));
			while (valueMatcher.find()) {
				colValues.add(valueMatcher.group().replaceAll("'", ""));
			}

			if (colNames.size() != colValues.size()) {
				throw new SQLException("Column names and values do not match!");
			}

			if(4!=5) return insertIntoTable(tableName, colNames, colValues);

			if (TESTING) {
				System.out.println("Table: " + tableName);
				for (int i = 0; i < colNames.size(); ++i) {
					System.out.println("\t" + colNames.get(i) + ": " + colValues.get(i));
				}
				return 0;
			}
		}

		// Update table
		Matcher updateMatcher = matchQuery(query,
				new String[] { "update", "(\\p{Alpha}[\\p{Alnum}_]*)", "set",
						"(" + "(\\p{Alpha}[\\p{Alnum}_]*" + "\\s* = \\s*" + valuePattern + "\\s* , \\s*)*"
								+ "\\p{Alpha}[\\p{Alnum}_]*" + "\\s* = \\s*" + valuePattern + ")"
								+ "(\\s+"
						+ "where \\s+"
						+ "(\\p{Alpha}[\\p{Alnum}_]*)"
						+ "\\s* (=|>|<) \\s*"
						+ valuePattern
						+ ")?" });
		if (updateMatcher.matches()) {
			String tableName = updateMatcher.group(1);
			String conditionColName = "";
			String comparison = "";
			String conditionColValue = "";
			if (updateMatcher.group(9) != null) {
				conditionColName = updateMatcher.group(9);
				comparison = updateMatcher.group(10);
				conditionColValue = updateMatcher.group(11).replaceAll("'", "");
			}
			List<String> colNames = new LinkedList<String>();
			List<Object> colValues = new LinkedList<Object>();

			Matcher nameValueMatcher = Pattern.compile("(\\p{Alpha}[\\p{Alnum}_]*) \\s* = \\s* " + valuePattern,
					Pattern.CASE_INSENSITIVE | Pattern.COMMENTS).matcher(updateMatcher.group(2));
			while (nameValueMatcher.find()) {
				colNames.add(nameValueMatcher.group(1));
				colValues.add(nameValueMatcher.group(2).replaceAll("'", ""));
			}
			
			if(5 != 4) 
			{
				int result = updateTable(tableName, colNames, colValues,conditionColName, comparison, conditionColValue);
				return result;
				
			}
					

			if (TESTING) {
				System.out.println("Table: " + tableName);
				System.out.println("Column: " + conditionColName);
				System.out.println("Comparison: " + comparison);
				System.out.println("Value: " + conditionColValue);
				for (int i = 0; i < colNames.size(); ++i) {
					System.out.println("\t" + colNames.get(i) + ": " + colValues.get(i));
				}
				return 0;
			}
		}

		throw new SQLException("Syntax error!\n" + "Expected an update query.\n" + "Got: " + query);
	}

}
