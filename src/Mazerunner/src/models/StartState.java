package models;

public class StartState implements HumanState{

	@Override
	public void doAction(Human human) {
		human.life = 6;
		human.bullets = 6;
		human.gifts = 0;
		human.score = 50;
		human.alive = true;
	}

	@Override
	public String show() {
		// TODO Auto-generated method stub
		return "startstate";
	}
	
	

}
