package eg.edu.alexu.csd.oop.db.cs27.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Limit {
	@SuppressWarnings({ "rawtypes" })
	private ArrayList<Map<String,String>> ret = new ArrayList<Map<String,String>>();
	
	public Limit() {
		// TODO Auto-generated constructor stub
		this.ret.clear();
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map<String,String>> limit (List<Map<String,String>> display , int from , int to){
		for(int i = from; i < to; i++){
			if(i <= display.size()-1 )
				ret.add(display.get(i));
			else break;
		}
		return ret;
	}
}
