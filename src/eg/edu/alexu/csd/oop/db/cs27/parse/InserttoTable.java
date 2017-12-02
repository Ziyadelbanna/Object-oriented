package eg.edu.alexu.csd.oop.db.cs27.parse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class InserttoTable implements IParser {
	private String selectedCommand;
	private LinkedList<String> inputs = new LinkedList<>();
	private LinkedList<String> values = new LinkedList<>();
	private String TableName = "";
	private Map<String, Object> collected = new HashMap<String, Object>();

	public InserttoTable() {
		collected.put("Operation", getClass().getSimpleName());

	}

	public LinkedList<String> getInputs() {
		selectedCommand = selectedCommand.replace(" ", "");
		String Allcaps = selectedCommand.toUpperCase();
		String x = selectedCommand.substring(Allcaps.indexOf("INTO") + 4, Allcaps.indexOf("VALUES"));
		if (x.contains(",")) {
			String holding = x.substring(x.indexOf("("), x.indexOf(")")).replace(")", "").replace("(", "");
			String[] INs = holding.split(",");
			for (int i = 0; i < INs.length; i++) {
				inputs.add(INs[i].trim());
			}

		} else {
			if (x.contains(")")) {
				String holding = x.substring(x.indexOf("("), x.indexOf(")")).replace(")", "").replace("(", "");
				inputs.add(holding.trim());

			} else {
				inputs = null;

			}
		}
		return inputs;

	}

	public LinkedList<String> getValues() {
		selectedCommand = selectedCommand.replace(" ", "");
		String Allcaps = selectedCommand.toUpperCase();
		String holding = selectedCommand.substring(Allcaps.indexOf("VALUES") + 6, Allcaps.indexOf(";"));
		if (holding.contains(",")) {
			String getval = holding.replace(")", "").replace("(", "");
			String[] Vals = getval.split(",");
			for (int i = 0; i < Vals.length; i++) {
				values.add(Vals[i].trim());
			}

		} else {
			values.add(holding.replace(")", "").replace("(", "").trim());
		}

		return values;

	}

	public String getTableName() {
		selectedCommand = selectedCommand.replace(" ", "");
		String Allcaps = selectedCommand.toUpperCase();
		String x = selectedCommand.substring(Allcaps.indexOf("INTO") + 4, Allcaps.indexOf("VALUES"));
		if (x.contains(")")) {
			if (x.contains(",")) {
				String get = selectedCommand.substring(Allcaps.indexOf("INTO") + 4, Allcaps.indexOf("("));
				TableName = get.trim();
				return TableName;

			} else {
				String get = selectedCommand.substring(Allcaps.indexOf("INTO") + 4, Allcaps.indexOf("("));
				TableName = get.trim();
				return TableName;
			}
		} else {
			String get = selectedCommand.substring(Allcaps.indexOf("INTO") + 4, Allcaps.indexOf("VALUES"));
			TableName = get.trim();
			return TableName;
		}
	}

	@Override
	public Map<String, Object> getMap(String quary) {
		this.selectedCommand = quary;
		collected.put("TableName", getTableName());
		collected.put("ColumnNames", getInputs());
		collected.put("ColumnValues", getValues());
		return collected;
	}

}