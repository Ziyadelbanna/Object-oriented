package models;

public class DeadState implements HumanState {

	@Override
	public void doAction(Human human) {
		human.alive = false;
		
	}

	@Override
	public String show() {
		// TODO Auto-generated method stub
		return "deadstate";
	}

}
