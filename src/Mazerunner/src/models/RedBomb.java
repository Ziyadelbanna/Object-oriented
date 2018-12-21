package models;
public class RedBomb extends Bomb{

//	private int x = 2;
	private static RedBomb instance = new RedBomb();
	
	private RedBomb(){}
	
	public static RedBomb getInstance() {
		return instance;
	}
	
	public int touchHuman(int score){
		score = score - 30;
		return score;
	}
	
//	public boolean getObj(){
//		
//		if(this.x > 0){
//			return false;
//		}
//		else{
//			return true;
//		}
//	}
}
