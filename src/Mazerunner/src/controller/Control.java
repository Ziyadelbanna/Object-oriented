package controller;
import java.awt.Point;
import java.awt.Toolkit;

import Gui.ArrayDraw;
import mazeGeneration.mazeGenerate;
import models.AbstractFactory;
import models.Bullet;
import models.DeadState;
import models.FactoryOfModels;
import models.FactoryProducer;
import models.Gate;
import models.GiftOfBullets;
import models.GiftOfHealth;
import models.GreenBomb;
import models.Human;
import models.RedBomb;
import models.Road;
import models.StartState;
import models.StoneWall;
import models.TreeWall;
import models.WinState;
import models.explosion;
import models.mazeTimer;



public class Control {

	public int rows;
	public int columnes;
	private int time = 0;
	
	private int[][] maze; 
	private Object[][] mazeComp;
	public Point person = new Point();
	private boolean cheak = false;
	mazeGenerate m = new mazeGenerate();
	
	AbstractFactory factory = FactoryProducer.getFactory("model");
	public Object human = factory.getModel("human");
	public Object redBomb = factory.getModel("redbomb");
	public Object greenBomb = factory.getModel("greenbomb");
	public Object bullet = factory.getModel("bullet");
	public Object treeWall = factory.getModel("treeWall");
	public Object stoneWall = factory.getModel("stonewall");
	public Object gate = factory.getModel("gate");
	public Object giftOfHealth = factory.getModel("giftOfhealth");
	public Object giftOfBullet = factory.getModel("giftOfBullet");
	public Object explosion = factory.getModel("explosion");
	public Object road = factory.getModel("road");

	
	public void setDimentions(int rows,int columnes){
		this.rows = rows;
		this.columnes = columnes;
		
		createMaze();
		createMazeComp();
	}
	
	public void createMaze(){
		maze = m.getMazeMap(rows, columnes);
	
		for(int i=0;i<maze.length;i++){
			for(int j=0;j<maze[0].length;j++){
				if(maze[i][j] == 2){
					person.setLocation(j, i);
				}
			}
		}
		
	}
	
	public void createMazeComp() {
		mazeComp = new Object[rows][columnes];
		
		for(int i=0;i<rows;i++){
			for(int j=0;j<columnes;j++){
				if(maze[i][j] == 0){ 
					mazeComp[i][j] = road;
				}
				else if(maze[i][j] == 1){
					
					mazeComp[i][j] = stoneWall; 
				}
				
				else if(maze[i][j] == 2){
					mazeComp[i][j] = human;
					((Human) human).setState(new StartState());
					((Human) human).setPosition(j, i);
				}
				
				else if(maze[i][j] == 3){
					
					mazeComp[i][j] = treeWall;
				}
				else if(maze[i][j] == 4){
					
					mazeComp[i][j] = redBomb;
				}
				else if(maze[i][j] == 5){
					
					mazeComp[i][j] = greenBomb;
					
				}
				else if(maze[i][j] == 6){
					
					mazeComp[i][j] = giftOfHealth;
				}
				else if(maze[i][j] == 7){
					
					mazeComp[i][j] = giftOfBullet;
				}
				else if(maze[i][j] == 8){
					mazeComp[i][j] = gate;
				}
			}
		}
	}
	
	
	
	public Object[][] getMazeComp(){
		
		
		return mazeComp;
	}
	
	public void clickRight(){
		
		if(person.getX() < columnes-1){
			Object o = mazeComp[(int) person.getY()][(int) (person.getX()+1)];
			if(!(o instanceof StoneWall || o instanceof TreeWall)){
				person.x++;
				update(person.x, person.y);
			}
		}
	}
	
	public void clickLeft(){
		if(person.getX() > 0){
			Object o = mazeComp[(int) person.getY()][(int) (person.getX()-1)];
			if(!(o instanceof StoneWall || o instanceof TreeWall)){
				person.x--;
				update(person.x, person.y);
			}
		}
	}
	
	public void clickTop(){
		if(person.getY() > 0){
			Object o = mazeComp[(int) person.getY()-1][(int) (person.getX())];
			if(!(o instanceof StoneWall || o instanceof TreeWall)){
				person.y--;
				update(person.x, person.y);
			}
		}
	}
	
	public void clickBottom(){
		if(person.getY() < rows-1){
			Object o = mazeComp[(int) person.getY()+1][(int) (person.getX())];
			if(!(o instanceof StoneWall || o instanceof TreeWall)){
				person.y++;
				update(person.x, person.y);
			}
		}
	}
	
	public void update(int x,int y){
	
		int preX = 0,preY = 0;
		for(int i=0;i<mazeComp.length;i++){
			for(int j=0;j<mazeComp[0].length;j++){
				if(mazeComp[i][j] instanceof Human){
				//	Human h = (Human) mazeComp[i][j];
					preX = ((Human) human).getX();
					preY = ((Human)human).getY();
				}
			}
		}
		
		
		
		if(mazeComp[y][x] instanceof GreenBomb){
			
			((Human)human).score = ((GreenBomb) greenBomb).touchHuman(((Human)human).score);
			Toolkit.getDefaultToolkit().beep();
		}
		else if(mazeComp[y][x] instanceof RedBomb){
			
			((Human) human).decreaseLives();
			((Human)human).score = ((RedBomb) redBomb).touchHuman(((Human)human).score);
			Toolkit.getDefaultToolkit().beep();
		}
		else if(mazeComp[y][x] instanceof GiftOfHealth){
			
			time = mazeTimer.getMinutes() * 60 + mazeTimer.getSeconds();
			((Human)human).increaseLives();
			((Human)human).score = ((GiftOfHealth) giftOfHealth).touchHuman(((Human)human).score,this.time,((Human)human).gifts,((Human)human).life);
		}
		else if(mazeComp[y][x] instanceof GiftOfBullets){
			time = mazeTimer.getMinutes() * 60 + mazeTimer.getSeconds();
			((Human)human).increaseBullets();
			((Human)human).score = ((GiftOfBullets) giftOfBullet).touchHuman(((Human)human).score,this.time,((Human)human).gifts,((Human)human).life);
		}
		else if(mazeComp[y][x] instanceof Gate){
			((Human) human).setState(new WinState());
		}
		
		
		
		((Human)human).setPosition(x, y);
		mazeComp[y][x] = null;
		mazeComp[y][x] = human;
		
		mazeComp[preY][preX] = null;
		mazeComp[preY][preX] = road;
		if(Human.getInstance().life==0){
			((Human) human).setState(new DeadState());
			System.out.println(Human.getInstance().getState());
		}
	}
	
	public void setMazeObj(Object[][] m){
		this.mazeComp = m;
	}
	
	
	
}
