package models;
public class Human {
	
	private HumanState humanState;
	public int score;
	public int life;
	public int bullets;
	public int gifts;
	public int x, y;
	boolean alive;
	private static Human instance = new Human();
	
	public void setState(HumanState state) {
		humanState = state;
		humanState.doAction(this);
	}
	public String getState() {
		return humanState.show();
	}
	
	private Human(){}
	
	public static Human getInstance(){
		return instance;
	}
	
	public void setPosition(int a, int b) {
		x = a;
		y = b;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void fire(Bomb bomb) {
		if (bullets > 0) {
			bullets--;	
			bomb.hp--;
		}
		else return;
	}
	public void decreaseBullets(){
		this.bullets --;
	}
	
	public void decreaseLives(){
		this.life --;
	}
	
	public void increaseBullets(){
		this.bullets ++;
	}
	
	public void increaseLives(){
		this.life ++;
	}
	
	public void decreaseGifts(){
		this.gifts --;
	}
	
	public void increaseGifts(){
		this.bullets ++;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public int getLife(){
		return this.life;
	}
	
	public int getBullets(){
		return this.bullets;
	}
}
