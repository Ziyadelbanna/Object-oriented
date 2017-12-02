package eg.edu.alexu.csd.oop.db.cs27;



import java.util.ArrayList;

import java.util.LinkedHashMap;

import java.util.Map;

import java.util.Map.Entry;



public class Row {

	private Map<String, String> map;



	public Row(Map<String, String> map) {

		if (map == null) {

			this.map = null;

			return;

		}

		this.map = new LinkedHashMap<>();

		this.map.putAll(map);

	}



	public String getAttribute(String key) {

		if (map == null) {

			return null;

		}

		String value = null;

		for (Map.Entry<String, String> entry : map.entrySet()) {

			String mapKey = entry.getKey();

			if (mapKey.equalsIgnoreCase(key)) {

				value = entry.getValue();

			}

		}

		return value;

	}



	public Map<String, String> getAllAttributes() {

		return map;

	}



	public void addAttribuite(Entry<String, String> entry) {

		if (entry != null) {

			map.put(entry.getKey().toLowerCase(), entry.getValue().toLowerCase());

		}

	}



	public void updateAttribute(Entry<String, String> entry) {

		if (entry != null) {

			map.put(entry.getKey().toLowerCase(), entry.getValue().toLowerCase());

		}

	}





	public boolean satisfy(Entry<String, String> entry, String type, int result) {

		if (type.equals("varchar")) {

			String value = map.get(entry.getKey());

			return value.compareTo(entry.getValue()) == result;

		} else if (type.equals("int")) {

			Integer value = Integer.parseInt(map.get(entry.getKey()));

			Integer otherValue = Integer.parseInt(entry.getValue());

			return value.compareTo(otherValue) == result;

		}

		return false;

	}



	public String toString() {

		return map.toString();

	}



	public String[] toArray() {

		ArrayList<String> s = new ArrayList<>();

		for (Map.Entry<String, String> entry: map.entrySet()) {

			s.add(entry.getValue());

		}

		String[] ans = new String[s.size()];

		for (int i = 0; i < s.size(); i++) {

			ans[i] = s.get(i);

		}

		

		return ans;

	}

}