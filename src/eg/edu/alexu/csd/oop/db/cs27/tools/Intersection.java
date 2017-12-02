package eg.edu.alexu.csd.oop.db.cs27.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intersection {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map<String,String>> ret = new ArrayList();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map<String,String>> a1Only = new ArrayList();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map<String,String>> a2Only = new ArrayList();
	
	public Intersection() {
		// TODO Auto-generated constructor stub
		this.ret.clear();this.a1Only.clear();this.a2Only.clear();
	}
	
	@SuppressWarnings({ "rawtypes" })
	private void inter(List<Map<String,String>> A1 , List<Map<String,String>> A2){
		
		for(int i = 0; i < A1.size(); i++){
			if(A2.contains(A1.get(i)))
				ret.add((Map) A1.get(i));
			else
				a1Only.add((Map) A1.get(i));
		}
		for(int i = 0; i < A2.size(); i++){
			if(!A1.contains(A2.get(i)))
				a2Only.add((Map) A2.get(i));
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map<String,String>> intersect(List<Map<String,String>> A1 , List<Map<String,String>> A2){
		inter(A1,A2);
		return ret;
	}
	
	@SuppressWarnings("rawtypes")
	protected List<Map<String,String>> Arr1Only(List<Map<String,String>> A1 , List<Map<String,String>> A2){
		return a1Only;
	}
	
	@SuppressWarnings("rawtypes")
	protected List<Map<String,String>> Arr2Only(List<Map<String,String>> A1 , List<Map<String,String>> A2){
		return a2Only;
	}
//	@SuppressWarnings({ "rawtypes", "unchecked" })
/*	public static void main(String[] args) {
		Union u = new Union();
		ArrayList<Map>a1 = new ArrayList();
		ArrayList<Map>a2 = new ArrayList();
		Map x1 = new HashMap();
		Map x2 = new HashMap();
		Map x3 = new HashMap();
		Map x4 = new HashMap();
		x1.put("Mohamed", 23);x1.put("Yehia", "orange");x1.put("Nourhan", 17);x1.put("Mariem", 12);
		x2.put("Mohamed", 23);x2.put("Yehia", "Apple");x2.put("Nourhan", 17);x2.put("Mariem", 12);
		x3.put("Mohamed", 53);x3.put("Yehia", "YELLOw");x3.put("Nourhan", 37);x3.put("Mariem", 32);
		x4.put("Mohamed", 73);x4.put("Yehia", "APPjw");x4.put("Nourhan", 47);x4.put("Mariem", 42);
		a1.add(x1);a1.add(x3);
		a2.add(x2);a2.add(x4);
		//a1 = i.intersect(a1, a2);
		a1 = u.union(a1, a2);
		//a1 = l.limit(a1, 0, 3);
		Sort d = new Sort();
		a1 = d.sortByKey(a1, "Yehia" , "ascending");
		System.out.println(a1.get(0).get("Yehia"));
	}**/

}
