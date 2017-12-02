package eg.edu.alexu.csd.oop.db.cs27.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Delete implements Parser {
	private Map<String, Object> collected = new HashMap<String, Object>();
	private String regex = new String();
	public ArrayList<String> colNames = new ArrayList<String>();
	public ArrayList<String> values = new ArrayList<String>();
	public ArrayList<String> condition = new ArrayList<String>();
	String TableName = new String();

	public Delete() {
		collected.put("Operation", "Delete");
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
		Pattern p = Pattern.compile("(?i)(delete)\\s+(?i)(from)\\s*");
		String[] n = p.split(quary);

		String temp = n[1];
		int i = 0;

		p = Pattern.compile("\\s*;\\s*");
		n = p.split(temp);
		temp = n[0];

		p = Pattern.compile("\\s*(?i)(where)\\s*");
		n = p.split(temp);
		if (n.length > 1) {
			TableName = n[0];
			String cond = n[1];

			char ope = 'a';
			for (i = 0; i < cond.length(); i++) {
				char c = cond.charAt(i);
				if (c == '=' || c == '<' || c == '>') {
					ope = c;
					break;
				}
			}

			p = Pattern.compile("\\s*(=|<|>)\\s*");
			n = p.split(cond);
			condition.add(n[0]);
			condition.add(String.valueOf(ope));
			condition.add(n[1]);
		} else {
			TableName = n[0];
		}

		/*
		 * System.out.println(TableName); System.out.println("-------------");
		 * 
		 * String out = new String(); for(i = 0; i < condition.size(); i++){
		 * 
		 * out += condition.get(i) + "--"; } System.out.println(out);
		 * System.out.println("==========================");
		 */
	}

	@Override
	public Map<String, Object> getMap(String quary) {
		parse(quary);
		collected.put("TableName", TableName);
		collected.put("Conditions", condition);
		return collected;
	}
}