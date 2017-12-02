package eg.edu.alexu.csd.oop.db.cs27.join;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FillMap {
	List<Map<String, String>> l = new ArrayList<>();
	List<String> s = new ArrayList<>();
	Map<String, String> m1;

	public List<Map<String, String>> fill() {

		m1 = new HashMap<>();
		m1.put("Name", "Abobakr");
		m1.put("age", "21");
		l.add(m1);

		m1 = new HashMap<>();
		m1.put("Name", "Ali");
		m1.put("age", "23");
		l.add(m1);

		m1 = new HashMap<>();
		m1.put("Name", "fares");
		m1.put("age", "20");
		l.add(m1);

		m1 = new HashMap<>();
		m1.put("Name", "Khaled");
		m1.put("age", "20");
		l.add(m1);

		return l;
	}

	public List<String> g() {
		s.add("Name");
		s.add("age");
		return s;
	}
}
