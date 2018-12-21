package models;
public class TreeWall extends Wall {
	
	private static TreeWall instance = new TreeWall();
	
	private TreeWall(){}
	
	public static TreeWall getInstance() {
		return instance;
	}
	
	
	 
}
