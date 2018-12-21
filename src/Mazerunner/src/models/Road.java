package models;

public class Road {
	private static Road instance = new Road();
	
	private Road(){}
	
	public static Road getInstance(){
		return instance;
	}
}
