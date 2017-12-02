package eg.edu.alexu.csd.oop.db.cs27.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Sort {
	
	private String k;
	private boolean flag;
	public Sort() {
		// TODO Auto-generated constructor stub4
		this.k = null;
		this.flag = false;
	}
	
	@SuppressWarnings("rawtypes")
	private Comparator<Map> Compare = new Comparator<Map>() {
		public int compare(Map m1, Map m2) {
		String key1 = m1.get(k).toString().toUpperCase();
		String key2 = m2.get(k).toString().toUpperCase();
		
		//ascending order
		if(flag)
			return key1.compareTo(key2);
		//descending order
		else
			return key2.compareTo(key1);
		}
	};

	@SuppressWarnings("rawtypes")
	public List<Map<String,String>> sortByKey(List<Map<String,String>> A , String key , String order){
		Sort s = new Sort();
		s.k = key;
		if(order.equals("asc"))
			s.flag = true;
		else 
			s.flag = false;
		
		Collections.sort(A , s.Compare);
		return A;
		
	}
}
