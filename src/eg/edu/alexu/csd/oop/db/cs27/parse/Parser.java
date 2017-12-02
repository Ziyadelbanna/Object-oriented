package eg.edu.alexu.csd.oop.db.cs27.parse;

import java.util.HashMap;
import java.util.Map;

public interface Parser {
	/* public Map<String,Object> collected = new HashMap<String,Object>(); */
	public Map<String, Object> getMap(String quary);
}