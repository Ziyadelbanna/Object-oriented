package models;

public class GiftOfBullets {

	private static GiftOfBullets instance = new GiftOfBullets();
	
	private GiftOfBullets(){}
	
	public static GiftOfBullets getInstance() {
		return instance;
	}
	
	public int touchHuman(int score, int time, int gifts, int lives)

	{ 
		score = score + (1/time + 4 * gifts + 4 * lives);
		return score;
	}
}
