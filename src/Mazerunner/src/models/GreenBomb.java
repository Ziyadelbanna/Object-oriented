package models;
public class GreenBomb extends Bomb {

private static GreenBomb instance = new GreenBomb();
	
	private GreenBomb(){}
	
	public static GreenBomb getInstance() {
		return instance;
	}
	
	public int touchHuman(int score){
		score = score - 20;
		return score;
	}
}
