package models;
public class GiftOfHealth {

	private static GiftOfHealth instance = new GiftOfHealth();
	
	private GiftOfHealth(){}
	
	public static GiftOfHealth getInstance() {
		return instance;
	}
	
	public int touchHuman(int score,int time,int gifts,int lives){

		score = score + (1/time + 4 * gifts + 4 * lives);

		return score;
	}
}
