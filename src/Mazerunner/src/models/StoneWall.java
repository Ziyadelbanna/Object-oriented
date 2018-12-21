package models;
public class StoneWall {

	private static StoneWall instance = new StoneWall();
	
	private StoneWall(){}
	
	public static StoneWall getInstance() {
		return instance;
	}
}
