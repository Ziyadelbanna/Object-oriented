package models;

public class Bullet {
	
	private static Bullet instance = new Bullet();
	
	private Bullet(){}
	
	public static Bullet getInstance(){
		return instance;
	}

}
