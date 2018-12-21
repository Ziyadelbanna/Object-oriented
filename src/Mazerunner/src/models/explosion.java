package models;

public class explosion {
	private static explosion instance = new explosion();
	
	private explosion(){}
	
	public static explosion getInstance(){
		return instance;
	}

}
