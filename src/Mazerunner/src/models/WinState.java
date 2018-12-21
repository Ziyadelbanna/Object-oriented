package models;

public class WinState implements HumanState {

	@Override
	public void doAction(Human human) {
		human.alive = true;
		
	}

	@Override
	public String show() {
		// TODO Auto-generated method stub
		return "winstate";
	}

}
