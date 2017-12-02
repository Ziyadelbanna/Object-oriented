package eg.edu.alexu.csd.oop.db.cs27;

import java.io.File;

import java.io.IOException;

import java.nio.file.StandardOpenOption;

import java.sql.SQLException;

import java.util.LinkedList;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Database;

public class DBM implements Database {

	private static final String gel = "/debug/gel11.log";

	private static boolean flag = false;

	private Validator valid = new Validator();

	private String currentdata;

	private History history;

	private XML xml = new XML();

	private static void log(String str, boolean delete) {

		try {

			if (flag) {

				if (delete) {

					new File(gel).delete();

				}

				java.nio.file.Files.write(java.nio.file.Paths.get(gel), ("\n" + str + "\n").getBytes(),

						new File(gel).exists() ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);

			}

		} catch (Throwable e1) {

			e1.printStackTrace();

		}

	}

	@Override

	public String createDatabase(String databaseName, boolean dropIfExists) {

		if (dropIfExists) {

			try {
				executeStructureQuery("Drop Database " + databaseName);

				executeStructureQuery("Create Database " + databaseName);

			} catch (SQLException e) {

				e.printStackTrace();
			}
		} else {

			File newfolder = new File(databaseName);

			if (newfolder.exists() == false) {

				try {

					executeStructureQuery("Create Database " + databaseName);

				} catch (SQLException e) {

					e.printStackTrace();

				}

			}

		}

		File file = new File(databaseName);

		currentdata = databaseName;

		return file.getAbsolutePath();

	}

	@Override

	public boolean executeStructureQuery(String query) throws SQLException {

		query = query.toLowerCase();

		if (valid.isCreateDatabase(query)) {

			Pattern p = Pattern.compile("([a-z0-9A-Z-_]{1,})$");

			Matcher m = p.matcher(query);

			m.find();

			String filename = m.group().trim();

			File newfolder = new File(filename);

			if (newfolder.exists() == false) {

				if (newfolder.mkdir() == false) {

					return false;

				}

			}

			currentdata = filename;

			return true;

		} else if (valid.isDropDatabase(query)) {

			Pattern p = Pattern.compile("([a-z0-9A-Z-_]{1,})$");

			Matcher m = p.matcher(query);

			m.find();

			String filename = m.group().trim();

			File newfolder = new File(filename);

			if (newfolder.exists()) {

				String[] entries = newfolder.list();

				for (String a : entries) {

					File curfile = new File(newfolder.getPath(), a);

					curfile.delete();

				}

				newfolder.delete();

			} else

				return false;

			return true;

		} else if (valid.isCreateTable(query)) {

			if (currentdata == null)

				throw new SQLException();

			Pattern p = Pattern.compile("([a-z0-9A-Z-_]{1,}\\s+(varchar|int))");

			Matcher m = p.matcher(query);

			LinkedList<String> arr = new LinkedList<String>();

			while (m.find())

				arr.add(m.group().trim());

			String[] type = new String[arr.size()];

			String[] names = new String[arr.size()];

			int idx = 0;

			for (String a : arr) {

				Pattern p2 = Pattern.compile("([a-z0-9A-Z-_]{1,} )");

				Matcher m2 = p2.matcher(a);

				Pattern p3 = Pattern.compile("(varchar|int)");

				Matcher m3 = p3.matcher(a);

				m2.find();

				m3.find();

				type[idx] = m3.group().trim();

				names[idx++] = m2.group().trim();

			}

			Pattern p1 = Pattern.compile("create\\s+table\\s+([a-z0-9A-Z-_]{1,})");

			Matcher m1 = p1.matcher(query);

			m1.find();

			String tablename = m1.group().trim();

			p1 = Pattern.compile("([a-z0-9A-Z-_]{1,})$");

			m1 = p1.matcher(tablename);

			m1.find();

			tablename = m1.group().trim();

			history = new History(arr.size());

			File file = new File(currentdata + "/" + tablename + ".xml");

			if (file.exists() == false) {

				try {

					file.createNewFile();

				} catch (IOException e) {

					return false;

				}

			} else

				return false;

			history.setNames(names);

			history.setTypes(type);

			xml.save(currentdata, tablename, history.get(), history.getNames(), history.getTypes());

			return true;

		} else if (valid.isDropTable(query)) {

			if (currentdata == null)

				throw new SQLException();

			Pattern p1 = Pattern.compile("([a-z0-9A-Z-_]{1,})$");

			Matcher m1 = p1.matcher(query);

			m1.find();

			String tablename = m1.group().trim();

			File file = new File(currentdata + "/" + tablename + ".xml");

			if (file.exists()) {

				file.delete();

			} else

				return false;

			return true;

		} else {

			throw new SQLException();

		}

	}

	@Override

	public Object[][] executeQuery(String query) throws SQLException {

		log("executeQuery(\"" + query + "\")", false);

		query = query.toLowerCase();

		if (currentdata == null)

			throw new SQLException();

		if (valid.isSelectAllTable(query)) {

			Pattern p = Pattern.compile("([a-z0-9-_]{1,})$");

			Matcher m = p.matcher(query);

			m.find();

			String tablename = m.group().trim();

			try {

				xml.load(currentdata, tablename);

			} catch (NullPointerException e) {

				throw new SQLException();

			}

			history.setHistory(xml.getTable());

			String[] types = xml.getTypes();

			String[] names = xml.getNames();

			history.setNames(names);

			history.setTypes(types);

			return history.get();

		} else if (valid.isSelectOneCol(query)) {

			Pattern p = Pattern.compile("([a-z0-9-_]{1,})$");

			Matcher m = p.matcher(query);

			m.find();

			String tablename = m.group().trim();

			try {

				xml.load(currentdata, tablename);

			} catch (NullPointerException e) {

				throw new SQLException();

			}

			history.setHistory(xml.getTable());

			String[] types = xml.getTypes();

			String[] names = xml.getNames();

			history.setNames(names);

			history.setTypes(types);

			p = Pattern.compile("select\\s+([a-z0-9_-]{1,})");

			m = p.matcher(query);

			m.find();

			String colname = m.group().trim();

			p = Pattern.compile("([a-z0-9_-]{1,})$");

			m = p.matcher(colname);

			m.find();

			colname = m.group().trim();

			int idx = 0, e = 0;

			for (String h : names) {

				if (h.equals(colname)) {

					idx = e;

				}

				e++;

			}

			int[] arr = { idx };

			// System.out.println(arr[0]);

			return history.get(arr);

		} else if (valid.isSelectMulCol(query)) {

			Pattern p = Pattern.compile("([a-z0-9-_]{1,})$");

			Matcher m = p.matcher(query);

			m.find();

			String tablename = m.group().trim();

			try {

				xml.load(currentdata, tablename);

			} catch (NullPointerException e) {

				throw new SQLException();

			}

			history.setHistory(xml.getTable());

			String[] types = xml.getTypes();

			String[] names = xml.getNames();

			history.setNames(names);

			history.setTypes(types);

			p = Pattern.compile("select\\s+([a-z0-9-_]{1,}\\s+)+from");

			m = p.matcher(query);

			m.find();

			String inputs = m.group().trim();

			p = Pattern.compile("([a-z0-9-_]{1,})");

			m = p.matcher(inputs);

			LinkedList<String> colnames = new LinkedList<>();

			while (m.find()) {

				colnames.add(m.group().trim());

			}

			int e = 0, idx = 0;

			int[] arr = new int[colnames.size() - 2];

			for (String a : colnames) {

				if (e == 0 || e == colnames.size() - 1) {

					e++;

					continue;

				}

				int g = 0;

				for (String b : names) {

					if (a.equals(b)) {

						arr[idx++] = g;

						break;

					}

					g++;

				}

				e++;

			}

			// for(int a:arr)System.out.println(a);

			return history.get(arr);

		} else if (valid.isSelectAllWithCond(query)) {

			Pattern p = Pattern.compile("from ([a-z0-9-_]{1,})");

			Matcher m = p.matcher(query);

			m.find();

			String tablename = m.group().trim();

			p = Pattern.compile("([a-z0-9-_]{1,})$");

			m = p.matcher(tablename);

			m.find();

			tablename = m.group().trim();

			try {

				xml.load(currentdata, tablename);

			} catch (NullPointerException e) {

				throw new SQLException();

			}

			history.setHistory(xml.getTable());

			String[] types = xml.getTypes();

			String[] names = xml.getNames();

			history.setNames(names);

			history.setTypes(types);

			p = Pattern.compile("([a-z0-9-_]{1,})\\s+(=|>|<)\\s+([a-z0-9-_']{1,})");

			m = p.matcher(query);

			m.find();

			String cond = m.group().trim();

			p = Pattern.compile("([a-z0-9-_']{1,})");

			m = p.matcher(cond);

			m.find();

			String colname = m.group().trim();

			m.find();

			String value = m.group().trim();

			p = Pattern.compile("(=|>|<)");

			m = p.matcher(cond);

			m.find();

			String operator = m.group().trim();

			int idx = 0, e = 0;

			// System.out.println(colname + " " + operator+ " "+value );

			for (String a : names) {

				if (colname.equals(a)) {

					idx = e;

					break;

				}

				e++;

			}

			if (operator.charAt(0) != '0' && types[idx].equals("varchar")) {

				throw new SQLException();

			}

			return history.get(idx, value, operator.charAt(0));

		} else if (valid.isSelectWithCond(query)) {

			Pattern p = Pattern.compile("from ([a-z0-9-_]{1,})");

			Matcher m = p.matcher(query);

			m.find();

			String tablename = m.group().trim();

			p = Pattern.compile("([a-z0-9-_]{1,})$");

			m = p.matcher(tablename);

			m.find();

			tablename = m.group().trim();

			try {

				xml.load(currentdata, tablename);

			} catch (NullPointerException e) {

				throw new SQLException();

			}

			File file = new File(currentdata + "/" + tablename + ".xml");

			history.setHistory(xml.getTable());

			String[] types = xml.getTypes();

			String[] names = xml.getNames();

			String S = Integer.valueOf(types.length).toString() + " " + Integer.valueOf(names.length).toString();

			if (types.length != names.length)

				throw new SQLException(S);

			history.setNames(names);

			history.setTypes(types);

			p = Pattern.compile("select\\s+([a-z0-9-_]{1,}\\s+)+from");

			m = p.matcher(query);

			m.find();

			String inputs = m.group().trim();

			p = Pattern.compile("([a-z0-9-_]{1,})");

			m = p.matcher(inputs);

			LinkedList<String> colnames = new LinkedList<>();

			while (m.find()) {

				colnames.add(m.group().trim());

			}

			int e = 0, idx = 0;

			int[] arr = new int[colnames.size() - 2];

			for (String a : colnames) {

				if (e == 0 || e == colnames.size() - 1) {

					e++;

					continue;

				}

				int g = 0;

				for (String b : names) {

					if (a.equals(b)) {

						// System.out.println(b+" "+e);

						arr[idx++] = g;

						break;

					}

					g++;

				}

				e++;

			}

			p = Pattern.compile("([a-z0-9-_]{1,})\\s+(=|>|<)\\s+([a-z0-9-_']{1,})");

			m = p.matcher(query);

			m.find();

			String cond = m.group().trim();

			p = Pattern.compile("([a-z0-9-_']{1,})");

			m = p.matcher(cond);

			m.find();

			String colname = m.group().trim();

			m.find();

			String value = m.group().trim();

			p = Pattern.compile("(=|>|<)");

			m = p.matcher(cond);

			m.find();

			String operator = m.group().trim();

			idx = -1;

			e = 0;

			for (String a : names) {

				if (colname.equals(a)) {

					idx = e;

					break;

				}

				e++;

			}

			if (idx == -1)

				throw new SQLException(query + " " + S + "  " + file.getAbsolutePath());

			if (operator.charAt(0) != '=' && types[idx].equals("varchar")) {

				throw new SQLException();

			}

			return history.get(arr, idx, value, operator.charAt(0));

		} else

			throw new SQLException();

	}

	@Override

	public int executeUpdateQuery(String query) throws SQLException {

		query = query.toLowerCase();

		if (currentdata == null)

			throw new SQLException();

		if (history == null)

			return 0;

		if (valid.isInsert(query)) {

			// get table name

			String tablename;

			Pattern p1 = Pattern.compile("insert into ([a-z0-9-_]{1,})");

			Matcher m1 = p1.matcher(query);

			m1.find();

			tablename = m1.group().trim();

			p1 = Pattern.compile("([a-z0-9-_]{1,})$");

			m1 = p1.matcher(tablename);

			m1.find();

			tablename = m1.group().trim();

			File file = new File(currentdata + "/" + tablename + ".xml");

			if (file.exists() == false)

				return 0;

			try {

				xml.load(currentdata, tablename);

			} catch (NullPointerException e) {

				throw new SQLException();

			}

			history.setHistory(xml.getTable());

			history.setNames(xml.getNames());

			history.setTypes(xml.getTypes());

			p1 = Pattern.compile("(\\((\\s*(,)?([a-zA-Z0-9-_']{1,})\\s*(,)?)+\\))");

			m1 = p1.matcher(query);

			m1.find();

			String inputs = m1.group().trim();

			p1 = Pattern.compile("([a-zA-Z0-9-_']{1,})");

			m1 = p1.matcher(inputs);

			LinkedList<String> data = new LinkedList<String>();

			while (m1.find()) {

				data.add(m1.group().trim());

				// System.out.println(m1.group().trim());

			}

			if (data.size() > history.getSizeofRow())

				return 0;

			history.insert(data.toArray());

			Object[][] a = history.get();

			xml.save(currentdata, tablename, history.get(), xml.getNames(), xml.getTypes());

			return 1;

		} else if (valid.isInsertWithColName(query)) {

			String tablename;

			Pattern p1 = Pattern.compile("insert into ([a-z0-9-_]{1,})");

			Matcher m1 = p1.matcher(query);

			m1.find();

			tablename = m1.group().trim();

			p1 = Pattern.compile("([a-z0-9-_]{1,})$");

			m1 = p1.matcher(tablename);

			m1.find();

			tablename = m1.group().trim();

			File file = new File(currentdata + "/" + tablename + ".xml");

			if (file.exists() == false)

				return 0;

			try {

				xml.load(currentdata, tablename);

			} catch (NullPointerException e) {

				throw new SQLException();

			}

			history.setHistory(xml.getTable());

			history.setNames(xml.getNames());

			history.setTypes(xml.getTypes());

			Object[][] ans = history.get();

			// System.out.println(ans.length+" "+ans[0].length);

			String[] type = xml.getTypes();

			String[] name = xml.getNames();

			String[] inputs = new String[name.length];

			p1 = Pattern.compile("(\\((\\s*(,)?([a-zA-Z0-9-_']{1,})\\s*(,)?)+\\))");

			m1 = p1.matcher(query);

			m1.find();

			String colnames = m1.group().trim();

			m1.find();

			String values = m1.group().trim();

			LinkedList<String> col = new LinkedList<>();

			p1 = Pattern.compile("([a-z0-9-_']{1,})");

			m1 = p1.matcher(colnames);

			while (m1.find()) {

				col.add(m1.group().trim());

			}

			LinkedList<String> val = new LinkedList<>();

			p1 = Pattern.compile("([a-z0-9-_']{1,})");

			m1 = p1.matcher(values);

			while (m1.find()) {

				val.add(m1.group().trim());

			}

			int e = 0;

			for (String a : col) {

				int f = 0;

				for (String b : name) {

					if (a.equals(b)) {

						inputs[f] = val.get(e);

					}

					f++;

				}

				e++;

			}

			history.insert(inputs);

			xml.save(currentdata, tablename, history.get(), history.getNames(), history.getTypes());

			return 1;

		} else if (valid.isDeleteAllTable(query)) {

			Pattern p = Pattern.compile("([a-z0-9-_']{1,})$");

			Matcher m = p.matcher(query);

			m.find();

			String tablename = m.group().trim();

			try {

				xml.load(currentdata, tablename);

			} catch (NullPointerException e) {

				throw new SQLException();

			}

			int ret = xml.getTable().size();

			history.setHistory(new LinkedList<Object[]>());

			history.setNames(xml.getNames());

			history.setTypes(xml.getTypes());

			xml.save(currentdata, tablename, history.get(), history.getNames(), history.getTypes());

			return ret;

		} else if (valid.isDeleteRow(query)) {

			Pattern p = Pattern.compile("delete\\s+from\\s+([a-z0-9-_']{1,})");

			Matcher m = p.matcher(query);

			m.find();

			String tablename = m.group().trim();

			p = Pattern.compile("([a-z0-9-_']{1,})$");

			m = p.matcher(tablename);

			m.find();

			tablename = m.group().trim();

			try {

				xml.load(currentdata, tablename);

			} catch (NullPointerException e) {

				throw new SQLException();

			}

			history.setHistory(xml.getTable());

			history.setNames(xml.getNames());

			history.setTypes(xml.getTypes());

			p = Pattern.compile("([a-z0-9-_]{1,})\\s*(=|>|<)\\s*([a-z0-9-_']{1,})");

			m = p.matcher(query);

			m.find();

			String cond = m.group().trim();

			p = Pattern.compile("([a-z0-9-_']{1,})");

			m = p.matcher(cond);

			m.find();

			String colname = m.group().trim();

			m.find();

			String value = m.group().trim();

			p = Pattern.compile("(=|>|<)");

			m = p.matcher(cond);

			m.find();

			String operator = m.group().trim();

			String[] name = history.getNames();

			int idx = 0, e = 0;

			for (String a : name) {

				if (a.equals(colname)) {

					idx = e;

				}

				e++;

			}

			int ret = history.delete(idx, value, operator.charAt(0));

			xml.save(currentdata, tablename, history.get(), history.getNames(), history.getTypes());

			return ret;

		} else if (valid.isUpdate(query)) {

			Pattern p = Pattern.compile("update ([a-z0-9_-]{1,})");

			Matcher m = p.matcher(query);

			m.find();

			String tablename = m.group().trim();

			p = Pattern.compile("([a-z0-9_-]{1,})$");

			m = p.matcher(tablename);

			m.find();

			tablename = m.group().trim();

			try {

				xml.load(currentdata, tablename);

			} catch (NullPointerException e) {

				throw new SQLException();

			}

			history.setHistory(xml.getTable());

			history.setNames(xml.getNames());

			history.setTypes(xml.getTypes());

			p = Pattern.compile("set\\s+(\\s*([a-z0-9-_]{1,})\\s*=\\s*([a-z0-9-_']{1,})(,)?\\s*)+\\s+where");

			m = p.matcher(query);

			m.find();

			String inputs = m.group().trim();

			p = Pattern.compile("([a-z0-9-_']{1,})\\s*=\\s*([a-z0-9-_']{1,})");

			m = p.matcher(inputs);

			LinkedList<String> input = new LinkedList<>();

			while (m.find())

				input.add(m.group().trim());

			LinkedList<String> colnames = new LinkedList<>();

			LinkedList<String> values = new LinkedList<String>();

			for (String a : input) {

				p = Pattern.compile("([a-z0-9-_']{1,})");

				m = p.matcher(a);

				m.find();

				colnames.add(m.group().trim());

				m.find();

				values.add(m.group().trim());

			}

			String[] names = history.getNames();

			int[] idx = new int[colnames.size()];

			int e = 0;

			for (String a : colnames) {

				int g = 0;

				for (String b : names) {

					if (a.equals(b)) {

						idx[e] = g;

						break;

					}

					g++;

				}

				e++;

			}

			p = Pattern.compile("([a-z0-9-_']{1,})\\s*(=|<|>)\\s*([a-z0-9-_']{1,})$");

			m = p.matcher(query);

			m.find();

			String cond = m.group().trim();

			p = Pattern.compile("([a-z0-9-_']{1,})");

			m = p.matcher(cond);

			m.find();

			String colname = m.group().trim();

			m.find();

			String val = m.group().trim();

			p = Pattern.compile("(=|>|<)");

			m = p.matcher(cond);

			m.find();

			String operator = m.group().trim();

			int colidx = -1, y = 0;

			for (String a : names) {

				if (colname.equals(a)) {

					colidx = y;

				}

				y++;

			}

			if (colidx == -1)

				throw new SQLException(query + " " + Integer.valueOf(names.length));

			String[] b = new String[values.size()];

			e = 0;

			for (String a : values)

				b[e++] = a;

			int ret = history.update(idx, b, colidx, val, operator.charAt(0));

			xml.save(currentdata, tablename, history.get(), history.getNames(), history.getTypes());

			return ret;

		} else if (valid.isUpdateAll(query)) {

			Pattern p = Pattern.compile("update ([a-z0-9_-]{1,})");

			Matcher m = p.matcher(query);

			m.find();

			String tablename = m.group().trim();

			p = Pattern.compile("([a-z0-9_-]{1,})$");

			m = p.matcher(tablename);

			m.find();

			tablename = m.group().trim();

			try {

				xml.load(currentdata, tablename);

			} catch (NullPointerException e) {

				throw new SQLException();

			}

			history.setHistory(xml.getTable());

			history.setNames(xml.getNames());

			history.setTypes(xml.getTypes());

			p = Pattern.compile("([a-z0-9-_']{1,})\\s*=\\s*([a-z0-9-_']{1,})");

			m = p.matcher(query);

			LinkedList<String> input = new LinkedList<>();

			while (m.find())

				input.add(m.group().trim());

			LinkedList<String> colnames = new LinkedList<>();

			LinkedList<String> values = new LinkedList<String>();

			for (String a : input) {

				p = Pattern.compile("([a-z0-9-_']{1,})");

				m = p.matcher(a);

				m.find();

				colnames.add(m.group().trim());

				m.find();

				values.add(m.group().trim());

			}

			String[] names = history.getNames();

			int[] idx = new int[colnames.size()];

			int e = 0;

			for (String a : colnames) {

				int g = 0;

				for (String b : names) {

					if (a.equals(b)) {

						idx[e] = g;

						break;

					}

					g++;

				}

				e++;

			}

			String[] b = new String[values.size()];

			e = 0;

			for (String a : values)

				b[e++] = a;

			return history.updateall(idx, b);

		} else

			throw new SQLException();

	}

}