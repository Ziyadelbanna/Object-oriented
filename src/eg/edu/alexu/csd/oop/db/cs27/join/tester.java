package eg.edu.alexu.csd.oop.db.cs27.join;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import eg.edu.alexu.csd.oop.db.FileHandler;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.Controller;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.FileOperations;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.XmlFileHandler;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.DataType;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.DateType;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.FloatType;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.IntType;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.StringType;
import eg.edu.alexu.csd.oop.db.cs27.Parser.ValidateQuery;
import eg.edu.alexu.csd.oop.db.cs27.tools.Destinct;
import eg.edu.alexu.csd.oop.db.cs27.tools.Intersection;
import eg.edu.alexu.csd.oop.db.cs27.tools.Limit;
import eg.edu.alexu.csd.oop.db.cs27.tools.Sort;
import eg.edu.alexu.csd.oop.db.cs27.tools.Union;

import java.util.Set;

public class tester {

	private String curr_db;
	private String fileType;
	private int output;
	private List<Map<String, String>> Display;
	private List<Map<String, String>> DataExcuted;
	private List<String> parsedQuery;
	private ValidateQuery parser;
	private Controller controller;
	private Intersection and;
	private Limit limit;
	private Sort order;
	private Union or;
	private Destinct destinct;
	private Map<String, Integer> map;
	private Map<String, String> mapWithDataTypes;
	private FileHandler fh;

	public tester() throws SQLException {
		this.curr_db = null;
		this.Display = null;
		this.parser = new ValidateQuery();
		this.controller = new Controller();
		this.and = new Intersection();
		this.limit = new Limit();
		this.order = new Sort();
		this.or = new Union();
		this.map = new HashMap<String, Integer>();
		this.mapWithDataTypes = new HashMap<String, String>();
		this.destinct = new Destinct();
		this.output = 0;
		this.DataExcuted = new ArrayList<Map<String, String>>();
	}

	// maps for Fares
	public Map<String, Integer> getMapWithnoValues() {
		return this.map;
	}

	public Map<String, String> getMapWithDataTypes() {
		return this.mapWithDataTypes;
	}

	// Data from select operation
	public List<Map<String, String>> getResultedData() {
		return this.Display;
	}

	// get the number of the rows those are affected from Modify and delete.
	public int getDataFromModifyAndDelete() {
		return this.output;
	}

	// get file types for abobakr & khaled
	public void setFileType(String xmlORjson) {
		switch (xmlORjson) {
		case "xml":
			fh = new XmlFileHandler();
			break;
		case "json":
			fh = null;
			break;
		}
		this.fileType = xmlORjson;
	}

	private FileOperations getFileInstance() throws SQLException {
		return (FileOperations) fh;
	}

	// Parse with query validation and excute the query
	// parse
	private boolean validate(String query) {
		this.parser.setQuery(query);
		return this.parser.isValid();
	}

	private void getData(String query) throws SQLException {
		if (this.parser.isValid()) {
			this.parsedQuery = this.parser.parseIt();
		} else
			throw new SQLException(this.parser.isValid() + "");
	}

	public void executeQuery(String query) throws SQLException {
		this.parser.setQuery(query);
		this.getData(query);
		boolean throwf = false;
		// switch between the query types
		try {
			switch (parser.getQueryType()) {
			case 1: // use
				this.curr_db = this.parsedQuery.get(0);
				break;
			case 2:
				this.curr_db = this.parsedQuery.get(0);
				this.controller.CreateDB(curr_db);
				// creat database return string (database name)
				break;
			case 3:
				if (this.curr_db == null)
					return;
				else
					try {
						this.createTable(this.parsedQuery);
					} catch (Exception e) {
						throwf = true;
					}

				// creat table
				/*
				 * table name , db name , no. of columns . 2 list of strings --> name of columns
				 * - type of columns(String , Integer)
				 */
				break;
			case 4:
				this.curr_db = this.parsedQuery.get(0);
				this.controller.DropDB(curr_db);
				// drop database return db name
				break;
			case 5:
				if (this.curr_db == null)
					return;
				else
					this.controller.DropTable(curr_db, this.parsedQuery.get(0), this.getFileInstance());
				// drop table return db name , table name
				break;
			case 6:
				if (this.curr_db == null)
					return;
				else {
					this.select(this.parsedQuery);
				}
				// select
				break;
			case 7:
				if (this.curr_db == null)
					return;
				else {
					try {
						this.update(this.parsedQuery);
					} catch (Exception e) {
						throwf = true;
					}

				}
				// update or modify
				break;
			case 8:
				if (this.curr_db == null)
					return;
				else
					this.delete(this.parsedQuery); // delete
				break;
			case 9:
				if (this.curr_db == null)
					return;
				else {
					try {
						this.insert(this.parsedQuery);
					} catch (Exception e) {
						throwf = true;
					}
				} // insert return table name , db name , list of strings of row , list of String
					// of values//
				// AddRow
				break;
			case 10:
				if (this.curr_db == null)
					return;
				else
					this.Display = controller.showTables(curr_db, this.getFileInstance());
				// show tables >>>
				break;
			case 11:
				if (this.curr_db == null)
					return;
				else
					this.Display = controller.describeTable(curr_db, this.parsedQuery.get(0), this.getFileInstance());
				// describe table name , db name
				break;
			case 12:
				if (this.curr_db == null)
					return;
				else
					this.addColumn(this.parsedQuery.get(0), this.parsedQuery.get(1), this.parsedQuery.get(2));
				// table name , column name , column datatype
				// addColoumn
				break;
			case 13:
				if (this.curr_db == null)
					return;
				else
					this.controller.AlterDrop(this.parsedQuery.get(0), this.curr_db, this.parsedQuery.get(1),
							this.getFileInstance());
				// table name, column name
				// dropColoumn
				break;
			case 14:
				if (this.curr_db == null)
					return;
				else {
					this.select(this.parsedQuery);
					this.Display = this.destinct.getDestinctData(this.Display);
					this.output = Display.size();
					System.out.println(Display);
					System.out.println(Display);
					System.out.println(Display);
					System.out.println(Display);
					System.out.println(Display);
					System.out.println(Display);
					System.out.println(Display);
					System.out.println(Display);
					System.out.println(Display);
					System.out.println(Display);
				}
				// destinct
				break;
			case 15:
				if (this.curr_db == null)
					return;
				else {
					try {
						this.insert2(this.parsedQuery);
					} catch (Exception e) {
						throwf = true;
					}
				} // insert2
				break;
			}
		} catch (Exception e) {
			this.output = 0;
		}
		if (throwf)
			throw new SQLException("ERROR");
	}

	// My own methods
	private void insert2(List<String> s) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		for (int i = 2; i < s.size(); i++)
			values.add(s.get(i));
		try {
			this.controller.AddRow(s.get(0), this.curr_db, null, values, this.getFileInstance());
			this.output = 1;
		} catch (Exception e) {
			throw new SQLException("FF");
		}
	}

	private void addColumn(String tableName, String columnName, String dataType) throws SQLException {
		if (dataType.equalsIgnoreCase("int"))
			this.controller.AlterAdd(tableName, this.curr_db, columnName, new IntType(), this.getFileInstance());
		else if (dataType.equalsIgnoreCase("varchar"))
			this.controller.AlterAdd(tableName, this.curr_db, columnName, new StringType(), this.getFileInstance());
		else if (dataType.equalsIgnoreCase("float"))
			this.controller.AlterAdd(tableName, this.curr_db, columnName, new FloatType(), this.getFileInstance());
		else if (dataType.equalsIgnoreCase("Date"))
			this.controller.AlterAdd(tableName, this.curr_db, columnName, new DateType(), this.getFileInstance());
	}

	private void createTable(List<String> s) throws SQLException {
		ArrayList<String> colNames = new ArrayList<>();
		ArrayList<DataType> colTypes = new ArrayList<>();
		for (int i = 1; i < s.size() - 1; i += 2) {
			colNames.add(s.get(i));
			if (s.get(i + 1).equalsIgnoreCase("int"))
				colTypes.add(new IntType());
			else if (s.get(i + 1).equalsIgnoreCase("varchar")) {
				colTypes.add(new StringType());
			} else if (s.get(i + 1).equalsIgnoreCase("float")) {
				colTypes.add(new FloatType());
			} else if (s.get(i + 1).equalsIgnoreCase("date")) {
				colTypes.add(new DateType());
			}
		}
		this.controller.CreateTable(s.get(0), curr_db, (s.size() - 1) / 2, colNames, colTypes, this.getFileInstance());
	}

	private void update(List<String> s) throws SQLException {
		ArrayList<String> colNames = new ArrayList<>();
		ArrayList<String> colValues = new ArrayList<>();
		for (int i = 1; i < s.size() - 1; i += 2) {
			colNames.add(s.get(i));
			colValues.add(s.get(i + 1));
		}
		if (this.parser.isWhere()) {
			List<String> old = new ArrayList<>();
			ArrayList<String> w = this.parser.whereData();
			this.Display = this.evaluate2(w, s);
			if (this.Display == null)
				return;
			w.clear();
			for (int i = 0; i < this.Display.size(); i++) {
				for (String ss : this.Display.get(i).keySet()) {
					old.add(this.Display.get(i).get(ss));
					w.add("=");
				}
				this.output = this.controller.ModifyRow(s.get(0), curr_db, colNames, w, old, colNames, colValues,
						this.getFileInstance());
				w.clear();
				old.clear();
			}
			this.output = this.Display.size();
			this.Display = null;
		} else
			this.output = this.controller.ModifyRow(s.get(0), curr_db, null, null, null, colNames, colValues,
					this.getFileInstance());
	}

	private void insert(List<String> s) throws SQLException {
		ArrayList<String> colNames = new ArrayList<>();
		ArrayList<String> colValues = new ArrayList<>();
		for (int i = 2; i < s.size() - 1; i += 2) {
			colNames.add(s.get(i));
			colValues.add(s.get(i + 1));
		}
		try {
			System.out.println(colNames);
			System.out.println(colValues);
			this.controller.AddRow(s.get(0), curr_db, colNames, colValues, this.getFileInstance());
			this.output = 1;
		} catch (Exception e) {
			throw new SQLException("FF");
		}
	}

	private void delete(List<String> s) throws SQLException {
		// loop for conditions

		if (this.parser.isWhere()) {

			List<String> old = new ArrayList<>();
			ArrayList<String> w = this.parser.whereData();
			List<String> x = new ArrayList<>();
			this.Display = this.evaluate2(w, s);

			if (this.Display == null)
				return;
			w.clear();

			for (int i = 0; i < this.Display.size(); i++) {

				for (String ss : this.Display.get(i).keySet()) {
					old.add(this.Display.get(i).get(ss));
					w.add("=");
					x.add(ss);
				}
				this.output = this.controller.DeleteRow(s.get(0), curr_db, x, w, old, this.getFileInstance());
				w.clear();
				old.clear();
				x.clear();

			}

			this.output = this.Display.size();
			this.Display = null;
		} else {
			this.output = this.controller.DeleteRow(s.get(0), curr_db, null, null, null, this.getFileInstance());
		}
	}

	private void select(List<String> s) throws SQLException {
		if (this.parser.isWhere()) {
			ArrayList<String> w = this.parser.whereData();
			this.Display = this.evaluate(w, s);
			this.output = Display.size();
		} else {
			this.Display = this.controller.Select(s.get(1), curr_db, null, null, null, this.getFileInstance());
			this.output = Display.size();
		}

		if (!s.get(0).equals("*")) {
			List<String> cols = Arrays.asList(s.get(0).split(" "));
			List<Map<String, String>> res = new ArrayList<>();
			Map<String, String> m = new HashMap<>();
			boolean flag = true;
			this.map.clear();
			this.mapWithDataTypes.clear();
			for (int j = 0; j < cols.size(); j++) {
				this.map.put(cols.get(j), Integer.valueOf(j + 1));
				this.mapWithDataTypes.put(cols.get(j),
						this.controller.getType(this.curr_db, s.get(1), cols.get(j), this.getFileInstance()));
				if (this.Display == null)
					return;
				if (!this.Display.get(0).containsKey(cols.get(j))) {
					flag = false;
					this.Display = null;
					return;
				} else
					continue;
			}
			if (flag) {
				for (int i = 0; i < this.Display.size(); i++) {
					for (int j = 0; j < cols.size(); j++)
						m.put(cols.get(j), this.Display.get(i).get(cols.get(j)));
					res.add(m);
					m = new HashMap<String, String>();
				}
				this.Display = res;
			}
		} else {
			this.map.clear();
			int cnt = 0;
			this.mapWithDataTypes.clear();
			if (!this.Display.isEmpty()) {
				for (String ss : this.Display.get(0).keySet()) {
					this.map.put(ss, Integer.valueOf(cnt++));
					this.mapWithDataTypes.put(ss,
							this.controller.getType(this.curr_db, s.get(1), ss, this.getFileInstance()));
				}
			}
		}
		if (this.parser.isOrder()) {
			List<String> o = this.parser.orderData();
			if (o.size() == 1)
				this.Display = this.order.sortByKey(this.Display, o.get(0), "asc");
			else
				this.Display = this.order.sortByKey(this.Display, o.get(0), o.get(1));
		}
		if (this.parser.islimit()) {
			ArrayList<String> l = parser.LimitData();
			this.Display = limit.limit(Display, Integer.valueOf(l.get(0)), Integer.valueOf(l.get(1)));
		}
		this.DataExcuted = this.Display;
	}

	private List<Map<String, String>> evaluate(List<String> w, List<String> s) throws SQLException {
		Stack<List<Map<String, String>>> stak = new Stack<>();
		List<String> condtion = new ArrayList<String>();
		List<String> col = new ArrayList<String>();
		List<String> rel = new ArrayList<String>();
		List<String> val = new ArrayList<String>();
		List<Map<String, String>> listMap1 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> listMap2 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> ret;
		for (int i = 0; i < w.size(); i++) {
			col.clear();
			rel.clear();
			val.clear();
			condtion.clear();
			if (!w.get(i).equals("and") && !w.get(i).equals("or")) {
				String warr[] = new String[1];

				if (w.get(i).split("=").length > 1) {
					warr = w.get(i).split("=");
					condtion.add(warr[0]);
					condtion.add("=");
					condtion.add(warr[1]);
				} else if (w.get(i).split("<").length > 1) {
					warr = w.get(i).split("<");

					condtion.add(warr[0]);
					condtion.add("<");
					condtion.add(warr[1]);
				} else if (w.get(i).split(">").length > 1) {
					warr = w.get(i).split(">");
					condtion.add(warr[0]);
					condtion.add(">");
					condtion.add(warr[1]);
				}

				col.add(condtion.get(0));
				rel.add(condtion.get(1));
				val.add(condtion.get(2));
				ret = this.controller.Select(s.get(1), curr_db, col, rel, val, this.getFileInstance());
				stak.push(ret);
				System.out.println(stak.size());
			} else if (!stak.isEmpty() && stak.size() > 1) {
				System.out.println(stak.peek());
				listMap1 = stak.pop();
				listMap2 = stak.pop();
				if (w.get(i).equals("and")) {
					stak.push(and.intersect(listMap1, listMap2));
				} else if (w.get(i).equals("or")) {
					stak.push(or.union(listMap1, listMap2));
					System.out.println(listMap1);
					System.out.println(listMap2);
				} else
					return null;
			}
		}
		return stak.pop();
	}

	private List<Map<String, String>> evaluate2(List<String> w, List<String> s) throws SQLException {
		Stack<List<Map<String, String>>> stak = new Stack<>();
		List<String> condtion = new ArrayList<String>();
		List<String> col = new ArrayList<String>();
		List<String> rel = new ArrayList<String>();
		List<String> val = new ArrayList<String>();
		List<Map<String, String>> listMap1 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> listMap2 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> ret;
		for (int i = 0; i < w.size(); i++) {
			col.clear();
			rel.clear();
			val.clear();
			condtion.clear();
			if (!w.get(i).equals("and") && !w.get(i).equals("or")) {
				String warr[] = new String[1];

				if (w.get(i).split("=").length > 1) {
					warr = w.get(i).split("=");
					condtion.add(warr[0]);
					condtion.add("=");
					condtion.add(warr[1]);
				} else if (w.get(i).split("<").length > 1) {
					warr = w.get(i).split("<");

					condtion.add(warr[0]);
					condtion.add("<");
					condtion.add(warr[1]);
				} else if (w.get(i).split(">").length > 1) {
					warr = w.get(i).split(">");
					condtion.add(warr[0]);
					condtion.add(">");
					condtion.add(warr[1]);
				}

				col.add(condtion.get(0));
				rel.add(condtion.get(1));
				val.add(condtion.get(2));
				ret = this.controller.Select(s.get(0), curr_db, col, rel, val, this.getFileInstance());
				stak.push(ret);
			} else if (!stak.isEmpty() && stak.size() > 1) {
				listMap1 = stak.pop();
				listMap2 = stak.pop();
				if (w.get(i).equals("and")) {
					stak.push(and.intersect(listMap1, listMap2));
				} else if (w.get(i).equals("or")) {
					stak.push(or.union(listMap1, listMap2));
				} else
					return null;
			}
		}
		return stak.pop();
	}

}