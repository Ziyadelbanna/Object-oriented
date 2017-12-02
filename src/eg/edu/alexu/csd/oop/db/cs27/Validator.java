package eg.edu.alexu.csd.oop.db.cs27;



import java.util.LinkedList;

import java.util.regex.Matcher;

import java.util.regex.Pattern;



public class Validator {



	public boolean isCreateDatabase(String match) {

		match = match.toLowerCase();

		return match.matches("create\\s+database\\s+(\\w+|_)\\s*$");

	}



	public boolean isCreateTable(String match) {

		match = match.toLowerCase();

		return match.matches(

				"create\\s+table\\s+(\\w+|_)(\\s+)?(\\(((\\s+)?(\\w+|_)\\s+(varchar|int)(\\s+)?(,)?(\\s+)?)+\\))\\s*$");

	}



	public boolean isDropDatabase(String match) {

		match = match.toLowerCase();

		return match.matches("drop\\s+database\\s+(\\w+|_)\\s*$");

	}



	public boolean isDropTable(String match) {

		match = match.toLowerCase();

		return match.matches("drop\\s+table\\s+(\\w+|_)\\s*$");

	}



	public boolean isDeleteAllTable(String match) {

		match = match.toLowerCase();

		return match.matches("delete\\s+(\\*\\s+)?from\\s+([a-z0-9-_]{1,})$");

	}



	public boolean isDeleteRow(String match) {

		match = match.toLowerCase();

		return match.matches(

				"delete\\s+from\\s+([a-z0-9-_]{1,})\\s+where\\s+([a-z0-9-_]{1,})(\\s+)?(=|>|<)(\\s+)?([a-z0-9-_']{1,})$");

	}



	public boolean isSelectWithCond(String match) {

		match = match.toLowerCase();

		return match.matches(

				"select\\s+(([a-z0-9-_]{1,})\\s+)+from\\s+([a-z0-9-_]{1,})\\s+where\\s+([a-z0-9-_]{1,})\\s+(=|<|>)\\s+([a-z0-9-_']{1,})$");

	}



	public boolean isSelectAllWithCond(String match) {

		match = match.toLowerCase();

		return match.matches(

				"select\\s+(\\*)\\s+from\\s+([a-z0-9-_]{1,})\\s+where\\s+([a-z0-9-_]{1,})\\s+(=|<|>)\\s+([a-z0-9-_']{1,})$");

	}



	public boolean isSelectOneCol(String match) {

		match = match.toLowerCase();

		return match.matches("select\\s+([a-z0-9-_]{1,})\\s+from\\s+([a-z0-9-_]{1,})$");

	}



	public boolean isSelectMulCol(String match) {

		match = match.toLowerCase();

		return match.matches("select\\s+(([a-z0-9-_]{1,})\\s+){2,}from\\s+([a-z0-9-_]{1,})$");

	}



	public boolean isSelectAllTable(String match) {

		match = match.toLowerCase();

		return match.matches("select\\s+(\\*)\\s+from\\s+([a-z0-9-_]{1,})$");

	}



	public boolean isInsert(String match) {

		match = match.toLowerCase();

		return match

				.matches("insert\\s+into\\s+([a-z0-9-_]{1,})\\s+values\\s*(\\((\\s*([a-z0-9-_']{1,})\\s*(,)?)+\\))$");

	}



	public boolean isInsertWithColName(String match) {

		match = match.toLowerCase();

		if (!match.replaceAll("\\s*|\t", "").endsWith(")")) {

			return false;

		}

		boolean flag = match.matches(

				"insert\\s+into\\s+(\\w+|_)\\s*(\\((\\s*([a-z0-9-_]{1,})\\s*(,)?)+\\))\\s+values\\s*(\\((\\s*([a-z0-9-_']{1,})\\s*(,)?)+\\))$");

		LinkedList<String> s = new LinkedList<String>();

		if (flag) {

			Pattern p = Pattern.compile("(\\((\\s*([a-z0-9-_']{1,})\\s*(,)?)+\\))");

			Matcher m = p.matcher(match);

			while (m.find()) {

				s.add(m.group().trim());

			}

			int a1 = 0, a2 = 0;

			String s1 = s.get(0);

			for (int i = 0; i < s1.length(); i++) {

				if (s1.charAt(i) == ',')

					a1++;

			}

			s1 = s.get(1);

			for (int i = 0; i < s1.length(); i++) {

				if (s1.charAt(i) == ',')

					a2++;

			}

			return a1 == a2;

		}

		return false;

	}



	public boolean isUpdate(String match) {

		match = match.toLowerCase();

		return match.matches(

				"update\\s+([a-z0-9-_]{1,})\\s+set\\s+(\\s*([a-z0-9-_]{1,})\\s*=\\s*([a-z0-9-_']{1,})(,)?\\s*)+\\s+where\\s+(\\s*([a-z0-9-_]{1,})\\s*(=|>|<)\\s*([a-z0-9-_']{1,})$)");

	}



	public boolean isUpdateAll(String match) {

		match = match.toLowerCase();

		return match.matches(

				"update\\s+([a-z0-9-_]{1,})\\s+set\\s+(\\s*([a-z0-9-_]{1,})\\s*=\\s*([a-z0-9-_']{1,})(,)?\\s*)+");

	}

}