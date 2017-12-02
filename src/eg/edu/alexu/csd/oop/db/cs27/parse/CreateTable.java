package eg.edu.alexu.csd.oop.db.cs27.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateTable implements Parser {
	private Map<String, Object> collected = new HashMap<String, Object>();
	private String regex = new String();
	public ArrayList<String> colNames = new ArrayList<String>();
	public ArrayList<String> colType = new ArrayList<String>();
	public ArrayList<String> size = new ArrayList<String>();
	String TableName = new String();

	public CreateTable() {
		// TODO Auto-generated constructor stub

		collected.put("Operation", "CreateTable");
	}

	public boolean ismatch(String quary) {
		boolean r = false;
		// boolean b = Pattern.matches(regex, quary);
		Pattern x = Pattern.compile(regex);
		Matcher z = x.matcher(quary);
		r = z.matches();
		return r;
	}

	public void parse(String quary) {
		// String [] n = quary.split("//s+");
		Pattern p = Pattern.compile("\\s+");
		String[] n = p.split(quary);

		int i = 0;
		for (i = 0; i < n[2].length(); i++) {
			if (n[2].charAt(i) != '(') {
				TableName += n[2].charAt(i);
			} else {
				break;
			}
		}
		n[2] = n[2].substring(i, n[2].length());

		String temp = new String();
		for (i = 2; i < n.length; i++) {
			temp += n[i] + " ";
		}

		for (i = 0; i < temp.length(); i++) {
			char c = temp.charAt(i);
			if (('a' <= c && c <= 'z') || ('A' <= c && 'Z' >= c)) {
				break;
			}
		}
		temp = temp.substring(i);

		p = Pattern.compile("\\s*,\\s*");
		n = p.split(temp);
		for (i = 0; i < n.length; i++) {
			p = Pattern.compile("\\s*(\\(|\\))\\s*;*");
			String[] m = p.split(n[i]);
			size.add(m[1]);
			m = m[0].split(" ");
			colNames.add(m[0]);
			colType.add(m[1]);
		}

		// System.out.println(TableName);
		// System.out.println("-------------");
		for (i = 0; i < colNames.size(); i++) {
			String out = colNames.get(i);
			out += "  ++  " + colType.get(i);
			out += "  ++  " + size.get(i);

			// System.out.println(out);
			// System.out.println("==========================");
		}

	}

	@Override
	public Map<String, Object> getMap(String quary) {
		Pattern p = Pattern.compile("\\s+");
		String[] n = p.split(quary);

		int i = 0;
		for (i = 0; i < n[2].length(); i++) {
			if (n[2].charAt(i) != '(') {
				TableName += n[2].charAt(i);
			} else {
				break;
			}
		}
		n[2] = n[2].substring(i, n[2].length());

		String temp = new String();
		for (i = 2; i < n.length; i++) {
			temp += n[i] + " ";
		}

		for (i = 0; i < temp.length(); i++) {
			char c = temp.charAt(i);
			if (('a' <= c && c <= 'z') || ('A' <= c && 'Z' >= c)) {
				break;
			}
		}
		temp = temp.substring(i);

		p = Pattern.compile("\\s*,\\s*");
		n = p.split(temp);
		for (i = 0; i < n.length; i++) {
			p = Pattern.compile("\\s*(\\(|\\))\\s*;*");
			// String [] m = p.split(n[i]);
			// size.add(m[1]);
			// m = m.split(" ");
			String[] m = n[i].split(" ");
			colNames.add(m[0]);
			String f = m[1];
			if (f.equals("int")) {
				f = "integer";
			}
			colType.add(f);
		}

		collected.put("TableName", TableName);
		collected.put("ColumnNames", colNames);
		collected.put("ColumnType", colType);
		// collected.put("columnNames", colNames);

		return collected;
	}
}