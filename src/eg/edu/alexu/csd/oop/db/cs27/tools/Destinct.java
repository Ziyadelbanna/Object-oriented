package eg.edu.alexu.csd.oop.db.cs27.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Destinct {

	private List<Map<String,String>> records;
	public Destinct(){
		this.records = new ArrayList<Map<String,String>>();
	}
	public List<Map<String,String>> getDestinctData(List<Map<String,String>> rec){
		this.records = rec;
		for(int i = 0; i < this.records.size(); i++){
			for(int j = i+1; j < this.records.size(); j++){
				if(this.records.get(i).equals(this.records.get(j))){
					this.records.remove(j--);
				}
			}
		}
		return this.records;
	}
	
	public static void main(String[] args){
		
		Map<String,String> m = new HashMap<String,String>();
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		m.put("id", "1");
		m.put("name", "Fares");
		m.put("age", "21");
		l.add(m);
		 m = new HashMap<String,String>();
		m.put("id", "2");
		m.put("name", "khaled");
		m.put("age", "21");
		l.add(m);
		 m = new HashMap<String,String>();
		m.put("id", "1");
		m.put("name", "Fares");
		m.put("age", "21");
		l.add(m);
		 m = new HashMap<String,String>();
		m.put("id", "2");
		m.put("name", "khaled");
		m.put("age", "21");
		l.add(m);
		 m = new HashMap<String,String>();
			m.put("id", "3");
			m.put("name", "khaled");
			m.put("age", "21");
			l.add(m);
			Destinct d = new Destinct();
		
		// m = new HashMap<String,String>();
		System.out.println(d.getDestinctData(l).size());
		
	}

}
