package models;

public class Gate {
	private static Gate instance = new Gate();
	private Gate(){}
	
	public static Gate getInstance() {
		return instance;
	}

}
