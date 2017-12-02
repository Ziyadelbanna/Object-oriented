package eg.edu.alexu.csd.oop.db.cs27.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Union extends Intersection {
	
	@SuppressWarnings({ "rawtypes" })
	private List<Map<String,String>> ret = new ArrayList<>();
	@SuppressWarnings({ "rawtypes" })
	private List<Map<String,String>> a1 = new ArrayList<>();
	@SuppressWarnings({ "rawtypes" })
	private List<Map<String,String>> a2 = new ArrayList<>();
	
	public Union() {
		// TODO Auto-generated constructor stub
		this.ret = null;
		this.a1 = null;
		this.a2 = null;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public List<Map<String,String>> union(List<Map<String,String>> A1 , List<Map<String,String>> A2){
		Intersection I = new Intersection();
		ret = I.intersect(A1, A2);
		a1 = I.Arr1Only(A1, A2);
		a2 = I.Arr2Only(A1, A2);
		ret.addAll(a1);
		ret.addAll(a2);
		return ret;
	}
}
