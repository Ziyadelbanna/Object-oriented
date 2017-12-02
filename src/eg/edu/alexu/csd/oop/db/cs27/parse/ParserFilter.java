package eg.edu.alexu.csd.oop.db.cs27.parse;

import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.RuntimeErrorException;

public class ParserFilter {
	private Map<String, Object> out = new HashMap<String, Object>();
	private ParserFactory factory = new ParserFactory();
	private Parser parser;

	// private CreateTable ct = new CreateTable();
	public Map<String, Object> getInfo(String quary) throws SQLSyntaxErrorException {
		out = null;
		if (!quary.contains(";")) {
			quary = quary.concat(";");
		}
		if (isSelect(quary)) {
			parser = factory.NewInstance("Select");
			out = parser.getMap(quary);
			
		} else if (isUpdate(quary)) {
			parser = factory.NewInstance("Update");
			out = parser.getMap(quary);

		} else if (isDelete(quary)) {
			parser = factory.NewInstance("Delete");
			out = parser.getMap(quary);
		} else if (isCreateTable(quary)) {
			parser = factory.NewInstance("CreateTable");
			out = parser.getMap(quary);
		} else if (isCreateDataBase(quary)) {
			parser = factory.NewInstance("CreateDB");
			out = parser.getMap(quary);
		} else if (isDropDataBase(quary)) {
			parser = factory.NewInstance("DropDB");
			out = parser.getMap(quary);
		} else if (isDropTable(quary)) {
			parser = factory.NewInstance("DropTable");
			out = parser.getMap(quary);
		} else if (isInsert(quary)) {
			parser = factory.NewInstance("Insert");
			out = parser.getMap(quary);
		} else {
			// return null;
			throw new SQLSyntaxErrorException();

		}

		return out;
	}

	private boolean isSelect(String quary) {
		boolean res = false;
		String regex = "\\s*(?i)(select)\\s+(\\*|([a-zA-Z]\\w*\\s*,\\s*)*[a-zA-Z]\\w*)";
		regex += "\\s+(?i)(from)\\s+[a-zA-Z]\\w*";
		regex += "(\\s+(?i)(where)\\s+[a-zA-Z]\\w*\\s*(=|<|>)\\s*('.+'|\".+\"|\\d+))?\\s*;?";
		Pattern x = Pattern.compile(regex);
		Matcher z = x.matcher(quary);
		res = z.matches();

		return res;
	}

	private boolean isUpdate(String quary) {
		boolean res = false;
		String regex = "\\s*(?i)(update)\\s+[a-zA-Z]\\w*\\s+";
		regex += "(?i)(set)\\s+([a-zA-Z]\\w*\\s*=\\s*('.+'|\".+\"|\\d+)\\s*,\\s*)*";
		regex += "[a-zA-Z]\\w*\\s*=\\s*('.+'|\".+\"|\\d+)";
		regex += "(\\s+(?i)(where)\\s+[a-zA-Z]\\w*\\s*(=|<|>)\\s*('.+'|\".+\"|\\d+))?\\s*;?";
		Pattern x = Pattern.compile(regex);
		Matcher z = x.matcher(quary);
		res = z.matches();
		return res;
	}

	private boolean isInsert(String quary) {
		boolean res = false;
		String regex = "\\s*(?i)(insert\\s+into)\\s+[a-zA-Z]\\w*\\s*";
		regex += "(\\(\\s*([a-zA-Z]\\w*\\s*,\\s*)*[a-zA-Z]\\w*\\s*\\))?";
		regex += "\\s+(?i)(values)\\s+\\(\\s*(('.+'|\".+\"|\\d+)\\s*,\\s*)*('.+'|\".+\"|\\d+)\\s*\\)\\s*;?";
		Pattern x = Pattern.compile(regex);
		Matcher z = x.matcher(quary);
		res = z.matches();
		return res;
	}

	private boolean isDelete(String quary) {
		boolean res = false;
		String regex = "\\s*(?i)(delete)\\s+(?i)(from)\\s+[a-zA-Z]\\w*\\s*";
		regex += "((?i)(where)\\s+[a-zA-Z]\\w*\\s*(=|<|>)\\s*('.+'|\".+\"|\\d+))?\\s*;?";
		Pattern x = Pattern.compile(regex);
		Matcher z = x.matcher(quary);
		res = z.matches();

		return res;
	}

	private boolean isCreateTable(String quary) {
		boolean res = false;
		String regex = "\\s*(?i)(create\\s+table)\\s+[a-zA-Z]\\w*\\s*";
		regex += "\\((\\s*[a-zA-Z]\\w*\\s+(?i)(varchar|int)\\s*(\\(\\s*\\d+\\s*\\))?,\\s*)*";
		regex += "[a-zA-Z]\\w*\\s+(?i)(varchar|int)\\s*(\\(\\s*\\d+\\s*\\))?\\)\\s*;?";
		Pattern x = Pattern.compile(regex);
		Matcher z = x.matcher(quary);
		res = z.matches();

		return res;
	}

	private boolean isCreateDataBase(String quary) {
		boolean res = false;
		String regex = "\\s*(?i)(create\\s+database)\\s+[a-zA-Z]\\w*\\s*;?";
		Pattern x = Pattern.compile(regex);
		Matcher z = x.matcher(quary);
		res = z.matches();

		return res;
	}

	private boolean isDropDataBase(String quary) {
		boolean res = false;
		String regex = "\\s*(?i)(drop\\s+database)\\s+[a-zA-Z]\\w*\\s*;?";
		Pattern x = Pattern.compile(regex);
		Matcher z = x.matcher(quary);
		res = z.matches();

		return res;
	}

	private boolean isDropTable(String quary) {
		boolean res = false;
		String regex = "\\s*(?i)(drop\\s+table)\\s+[a-zA-Z]\\w*\\s*;?";
		Pattern x = Pattern.compile(regex);
		Matcher z = x.matcher(quary);
		res = z.matches();

		return res;
	}

}