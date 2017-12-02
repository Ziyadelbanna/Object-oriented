package eg.edu.alexu.csd.oop.db.cs27.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectFromTable implements IParser {
	private Map<String, Object> collected = new HashMap<String, Object>();
	private String selectedCommand;
	private Boolean Condition = false;

	public Boolean getCondition() {
		return Condition;
	}

	private ArrayList<Object> inputs = new ArrayList<>();
	private ArrayList<Object> whereCond = new ArrayList<>();
	private String TableName = "";

	public SelectFromTable() {
		collected.put("Operation", getClass().getSimpleName());
	}

	public ArrayList<Object> getInputs() {
		selectedCommand = selectedCommand.replace(" ", "");
		String Allcaps = selectedCommand.toUpperCase();
		String in = selectedCommand.substring(Allcaps.indexOf("SELECT") + 6, Allcaps.indexOf("FROM"));
		if (in.contains(",")) {
			String[] INs = in.split(",");
			for (int i = 0; i < INs.length; i++) {
				inputs.add(INs[i].trim());
			}

		} else {
			if (in.trim().length() == 0 || in.trim().equals("*")) {
				inputs = null;
			} else {
				inputs.add(in.trim());
			}
		}
		return inputs;

	}

	public ArrayList<Object> getCond() {
		selectedCommand = selectedCommand.replace(" ", "");
		String Allcaps = selectedCommand.toUpperCase();
		if (Allcaps.contains("WHERE")) {
			String sub = selectedCommand.substring(Allcaps.indexOf("WHERE") + 5, Allcaps.indexOf(";"));
			try {
				String[] conds = sub.split("[<>=]");
				for (int i = 0; i < conds.length; i++) {
					whereCond.add(conds[i].trim());
				}
				if (sub.contains("=")) {
					whereCond.add(1, "=");

				} else if (sub.contains(">")) {
					whereCond.add(1, ">");

				} else if (sub.contains("<")) {
					whereCond.add(1, "<");

				}
			} catch (Exception e) {

			}

			return whereCond;

		} else {
			return null;

		}

	}

	public String getTableName() {
		selectedCommand = selectedCommand.replace(" ", "");
		String Allcaps = selectedCommand.toUpperCase();
		if (Allcaps.contains("WHERE")) {
			String x = selectedCommand.substring(Allcaps.indexOf("FROM") + 4, Allcaps.indexOf("WHERE"));
			TableName = x.trim();
			return TableName;

		} else {
			String x = selectedCommand.substring(Allcaps.indexOf("FROM") + 4, Allcaps.indexOf(";"));
			TableName = x.trim();
			return TableName;

		}

	}

	public Boolean evaluateCond(Object x) {
		ArrayList<Object> c = getCond();
		int I3 = 0;
		String S3 = "";
		try {
			int y = Integer.parseInt((String) x);
			I3 = Integer.parseInt((String) c.get(2));
			String op = (String) c.get(1).toString().trim();
			if (op.equals(">")) {
				if (y > I3) {
					return true;
				} else {
					return false;
				}
			} else if (op.equals("<")) {
				if (y < I3) {
					return true;
				} else {
					return false;
				}
			} else if (op.equals("=")) {
				if (y == I3) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			String op = (String) c.get(1).toString().trim();
			S3 = (String) c.get(2);
			if (op.equals("=")) {
				if ((boolean) x.toString().equals(S3)) {
					return true;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			throw null;
		}
		return false;

	}

	@Override
	public Map<String, Object> getMap(String quary) {
		selectedCommand = quary;
		collected.put("TableName", getTableName());
		collected.put("ColumnNames", getInputs());
		collected.put("Conditions", getCond());
		return collected;
	}

}