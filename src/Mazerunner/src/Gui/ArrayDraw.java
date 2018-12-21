package Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import controller.Control;
import mazeGeneration.mazeGenerate;
import models.AbstractFactory;
import models.Bullet;
import models.DeadState;
import models.FactoryOfImages;
import models.FactoryProducer;
import models.Gate;
import models.GiftOfBullets;
import models.GiftOfHealth;
import models.GreenBomb;
import models.Human;
import models.RedBomb;
import models.Road;
import models.StoneWall;
import models.TreeWall;
import models.explosion;
import models.mazeTimer;

public class ArrayDraw extends JPanel {

	AbstractFactory factoryImg = FactoryProducer.getFactory("image");
	public static final Logger log = Logger.getLogger(ArrayDraw.class);

	private ImageIcon bomb = factoryImg.getImage("greenbomb");
	private ImageIcon tnt = factoryImg.getImage("redbomb");

	private ImageIcon gift = factoryImg.getImage("gift");
	private ImageIcon gun = factoryImg.getImage("gun");

	private ImageIcon tree = factoryImg.getImage("tree");
	private ImageIcon brickWall = factoryImg.getImage("brick");

	private ImageIcon man = factoryImg.getImage("upman");
	private ImageIcon door = factoryImg.getImage("door");

	private ImageIcon rightMan = factoryImg.getImage("rightman");
	private ImageIcon leftMan = factoryImg.getImage("leftman");
	private ImageIcon upMan = factoryImg.getImage("upman");
	private ImageIcon downMan = factoryImg.getImage("downman");

	private ImageIcon spiderman = new ImageIcon("res/spiderman-icon.png");
	private ImageIcon ironman = new ImageIcon("res/ironman-icon.png");
	private ImageIcon hulk = new ImageIcon("res/hulk-icon.png");
	private ImageIcon america = new ImageIcon("res/america-icon.png");
	private ImageIcon ball = new ImageIcon("res/Ball-icon.png");

	private int x = 200, y = 10, velx, vely = 0;

	private int imageWidth = 24;
	private int iamgeHeigth = 24;
	private int leftStart = 5;
	private int upStart = 10;

	private int width;
	private int heigth;
	private long startTime;

	public static mazeTimer timeElapsed = new mazeTimer();
	Control control = new Control();
	private Object[][] maze;

	public void setDimension(String heigth, String width) {

		this.width = Integer.parseInt(width);
		this.heigth = Integer.parseInt(heigth);
		if (this.width == 0 || this.heigth == 0) {
			log.debug("Cannot create a maze of size 0");
			log.error("Game cannot start");
		}
		if (this.width % 2 == 0) {
			this.width++;
		}
		if (this.heigth % 2 == 0) {
			this.heigth++;
		}
		this.start();
	}

	public void start() {
		control.setDimentions(this.heigth, this.width);
		maze = control.getMazeComp();
		timeElapsed.start();
		repaint();
	}

	public void drawing() {
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int i = this.leftStart;
		int temp = this.leftStart;
		int j = this.upStart;

		for (int m = 0; m < maze.length; m++) {
			for (int n = 0; n < maze[0].length; n++) {
				if (maze[m][n] instanceof GreenBomb) {
					ImageObserver observer = null;
					g.drawImage(bomb.getImage(), i, j, this.imageWidth, this.iamgeHeigth, observer);
					// bomb.paintIcon(this, g, i, j);
				}
				if (maze[m][n] instanceof TreeWall) {
					ImageObserver observer = null;
					g.drawImage(tree.getImage(), i, j, this.imageWidth, this.iamgeHeigth, observer);
					// tree.paintIcon(this, g, i, j);
				}
				if (maze[m][n] instanceof GiftOfHealth) {
					ImageObserver observer = null;
					g.drawImage(gift.getImage(), i, j, this.imageWidth, this.iamgeHeigth, observer);
					// gift.paintIcon(this, g, i, j);
				}
				if (maze[m][n] instanceof GiftOfBullets) {
					ImageObserver observer = null;
					g.drawImage(gun.getImage(), i, j, this.imageWidth, this.iamgeHeigth, observer);
					// gun.paintIcon(this, g, i, j);
				}
				if (maze[m][n] instanceof StoneWall) {
					ImageObserver observer = null;
					g.drawImage(brickWall.getImage(), i, j, this.imageWidth, this.iamgeHeigth, observer);
					// brickWall.paintIcon(this, g, i, j);
				}
				if (maze[m][n] instanceof RedBomb) {
					ImageObserver observer = null;
					g.drawImage(tnt.getImage(), i, j, this.imageWidth, this.iamgeHeigth, observer);
					// tnt.paintIcon(this, g, i, j);
				}
				if (maze[m][n] instanceof Human) {
					ImageObserver observer = null;
					g.drawImage(man.getImage(), i, j, this.imageWidth, this.iamgeHeigth, observer);
					// man.paintIcon(this, g, i, j);
				}
				if (maze[m][n] instanceof Gate) {
					ImageObserver observer = null;
					g.drawImage(door.getImage(), i, j, this.imageWidth, this.iamgeHeigth, observer);
					// door.paintIcon(this, g, i, j);
				}
				if (maze[m][n] instanceof Bullet) {

					ImageObserver observer = null;
					g.drawImage(ball.getImage(), i, j, this.imageWidth, this.iamgeHeigth, observer);
				}
				if (maze[m][n] instanceof explosion) {
					ImageObserver observer = null;
					g.drawImage(ball.getImage(), i, j, this.imageWidth, this.iamgeHeigth, observer);
				}
				i = i + this.imageWidth;
			}
			j = j + this.iamgeHeigth;
			i = temp;
		}
	}

	public void up() {
		this.setUpImage();
		control.clickTop();
		this.maze = control.getMazeComp();
		repaint();
	}

	public void down() {
		this.setDownImage();
		control.clickBottom();
		this.maze = control.getMazeComp();
		repaint();
	}

	public void right() {

		this.setRightImage();
		control.clickRight();
		this.maze = control.getMazeComp();

		repaint();
	}

	public void left() {
		this.setLeftImage();
		control.clickLeft();
		this.maze = control.getMazeComp();
		repaint();
	}

	public void fireR() {
		((Human) control.human).decreaseBullets();
		this.setRightImage();
		this.maze = control.getMazeComp();

		int x = (int) control.person.getX();
		int y = (int) control.person.getY();
		int xend = 0, yend = 0;
		if (x < control.columnes - 1) {

			for (int i = x + 1; i < control.columnes; i++) {

				if (maze[y][i] instanceof Road) {
					if (i == x + 1) {
						maze[y][i] = null;
						maze[y][i] = control.bullet;

						repaint();
					} else {
						maze[y][i] = null;
						maze[y][i] = control.bullet;
						maze[y][i - 1] = null;
						maze[y][i - 1] = control.road;

						repaint();
					}
					xend = i;
					yend = y;
				} else if (maze[y][i] instanceof RedBomb) {

					RedBomb r = (RedBomb) maze[y][i];

					if (i == x + 1) {
						maze[y][i] = null;
						maze[y][i] = control.greenBomb;

						repaint();
					} else {
						maze[y][i] = null;
						maze[y][i] = control.greenBomb;
						maze[y][i - 1] = null;
						maze[y][i - 1] = control.road;

						repaint();
					}
					xend = i;
					yend = y;

					break;
				} else if (maze[y][i] instanceof GreenBomb) {
					if (i == x + 1) {
						maze[y][i] = null;
						maze[y][i] = control.explosion;

						repaint();
					} else {
						maze[y][i] = null;
						maze[y][i] = control.explosion;
						maze[y][i - 1] = null;
						maze[y][i - 1] = control.road;

						repaint();
					}
					xend = i;
					yend = y;
					break;
				} else if (maze[y][i] instanceof GiftOfBullets || maze[y][i] instanceof GiftOfHealth) {
					if (i == x + 1) {
						maze[y][i] = null;
						maze[y][i] = control.explosion;

						repaint();
					} else {
						maze[y][i] = null;
						maze[y][i] = control.explosion;
						maze[y][i - 1] = null;
						maze[y][i - 1] = control.road;

						repaint();
					}
					xend = i;
					yend = y;
					break;
				} else if (maze[y][i] instanceof TreeWall) {
					if (i == x + 1) {
						maze[y][i] = null;
						maze[y][i] = control.explosion;

						repaint();
					} else {
						maze[y][i] = null;
						maze[y][i] = control.explosion;
						maze[y][i - 1] = null;
						maze[y][i - 1] = control.road;

						repaint();

					}
					xend = i;
					yend = y;
					break;
				} else if (maze[y][i] instanceof StoneWall || maze[y][i] instanceof Gate) {
					xend = i;
					yend = y;
					break;
				}
			}

			if (maze[yend][xend] instanceof explosion) {
				maze[yend][xend] = null;
				maze[yend][xend] = control.road;
				repaint();
			} else if (!(maze[yend][xend - 1] instanceof Human)) {
				maze[yend][xend - 1] = null;
				maze[yend][xend - 1] = control.road;
				repaint();
			}

			control.setMazeObj(maze);
		}
	}

	public void fireL() {
		((Human) control.human).decreaseBullets();
		this.setLeftImage();
		this.maze = control.getMazeComp();

		int x = (int) control.person.getX();
		int y = (int) control.person.getY();
		int xend = 0, yend = 0;

		if (x > 0) {
			for (int i = x - 1; i >= 0; i--) {

				if (maze[y][i] instanceof RedBomb) {

					RedBomb r = (RedBomb) maze[y][i];

					if (i == x - 1) {
						maze[y][i] = null;
						maze[y][i] = control.greenBomb;

						repaint();
					} else {
						maze[y][i] = null;
						maze[y][i] = control.greenBomb;

						maze[y][i + 1] = null;
						maze[y][i + 1] = control.road;
						repaint();
					}
					xend = i;
					yend = y;

					break;
				} else if (maze[y][i] instanceof GreenBomb) {
					if (i == x - 1) {
						maze[y][i] = null;
						maze[y][i] = control.explosion;
						repaint();
					} else {
						maze[y][i] = null;
						maze[y][i] = control.explosion;
						maze[y][i + 1] = null;
						maze[y][i + 1] = control.road;
						repaint();
					}
					xend = i;
					yend = y;

					break;
				} else if (maze[y][i] instanceof GiftOfBullets || maze[y][i] instanceof GiftOfHealth) {
					if (i == x - 1) {
						maze[y][i] = null;
						maze[y][i] = control.explosion;
						repaint();
					} else {
						maze[y][i] = null;
						maze[y][i] = control.explosion;
						maze[y][i + 1] = null;
						maze[y][i + 1] = control.road;
						repaint();
					}
					xend = i;
					yend = y;

					break;
				} else if (maze[y][i] instanceof TreeWall) {
					if (i == x - 1) {
						maze[y][i] = null;
						maze[y][i] = control.explosion;
						repaint();
					} else {
						maze[y][i] = null;
						maze[y][i] = control.explosion;
						maze[y][i + 1] = null;
						maze[y][i + 1] = control.road;
						repaint();
					}
					xend = i;
					yend = y;

					break;
				} else if (maze[y][i] instanceof StoneWall || maze[y][i] instanceof Gate) {
					xend = i;
					yend = y;
					break;
				}
			}
			if (maze[yend][xend] instanceof explosion) {
				maze[yend][xend] = null;
				maze[yend][xend] = control.road;
				repaint();
			} else if (!(maze[yend][xend + 1] instanceof Human)) {
				maze[yend][xend + 1] = null;
				maze[yend][xend + 1] = control.road;
				repaint();
			}
			control.setMazeObj(maze);
		}
	}

	public void fireU() {
		((Human) control.human).decreaseBullets();
		this.setUpImage();
		this.maze = control.getMazeComp();

		int x = (int) control.person.getX();
		int y = (int) control.person.getY();
		int xend = 0, yend = 0;
		if (y > 0) {
			for (int i = y - 1; i >= 0; i--) {
				if (maze[i][x] instanceof RedBomb) {

					RedBomb r = (RedBomb) maze[i][x];

					if (i == y - 1) {
						maze[i][x] = null;
						maze[i][x] = control.greenBomb;

						repaint();
					} else {
						maze[i][x] = null;
						maze[i][x] = control.greenBomb;

						maze[i + 1][x] = null;
						maze[i + 1][x] = control.road;
						repaint();
					}
					xend = i;
					yend = x;

					break;
				} else if (maze[i][x] instanceof GreenBomb) {
					if (i == y - 1) {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						repaint();
					} else {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						maze[i + 1][x] = null;
						maze[i + 1][x] = control.road;
						repaint();
					}
					xend = i;
					yend = x;

					break;
				} else if (maze[i][x] instanceof GiftOfBullets || maze[i][x] instanceof GiftOfHealth) {
					if (i == y - 1) {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						repaint();
					} else {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						maze[i + 1][x] = null;
						maze[i + 1][x] = control.road;
						repaint();
					}
					xend = i;
					yend = x;

					break;
				} else if (maze[i][x] instanceof TreeWall) {
					if (i == y - 1) {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						repaint();
					} else {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						maze[i + 1][x] = null;
						maze[i + 1][x] = control.road;
						repaint();
					}
					xend = i;
					yend = x;

					break;
				} else if (maze[i][x] instanceof StoneWall || maze[i][x] instanceof Gate) {
					xend = i;
					yend = x;
					break;
				}
			}
			if (maze[xend][yend] instanceof explosion) {
				maze[xend][yend] = null;
				maze[xend][yend] = control.road;
				repaint();
			} else if (!(maze[xend + 1][yend] instanceof Human)) {
				maze[xend + 1][yend] = null;
				maze[xend + 1][yend] = control.road;
				repaint();
			}
			control.setMazeObj(maze);
		}
	}

	public void fireD() {
		((Human) control.human).decreaseBullets();
		this.setDownImage();
		this.maze = control.getMazeComp();

		int x = (int) control.person.getX();
		int y = (int) control.person.getY();
		int xend = 0, yend = 0;
		if (y < control.rows - 1) {
			for (int i = y + 1; i < control.rows; i++) {

				if (maze[i][x] instanceof RedBomb) {

					RedBomb r = (RedBomb) maze[i][x];

					if (i == y + 1) {
						maze[i][x] = null;
						maze[i][x] = control.greenBomb;
						repaint();
					} else {
						maze[i][x] = null;
						maze[i][x] = control.greenBomb;
						maze[i - 1][x] = null;
						maze[i - 1][x] = control.road;
						repaint();
					}
					xend = i;
					yend = x;

					break;
				} else if (maze[i][x] instanceof GreenBomb) {
					if (i == y + 1) {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						repaint();
					} else {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						maze[i - 1][x] = null;
						maze[i - 1][x] = control.road;
						repaint();
					}

					xend = i;
					yend = x;

					break;
				} else if (maze[i][x] instanceof GiftOfBullets || maze[i][x] instanceof GiftOfHealth) {
					if (i == y + 1) {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						repaint();
					} else {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						maze[i - 1][x] = null;
						maze[i - 1][x] = control.road;
						repaint();
					}

					xend = i;
					yend = x;

					break;
				} else if (maze[i][x] instanceof TreeWall) {
					if (i == y + 1) {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						repaint();
					} else {
						maze[i][x] = null;
						maze[i][x] = control.explosion;
						maze[i - 1][x] = null;
						maze[i - 1][x] = control.road;
						repaint();
					}

					xend = i;
					yend = x;

					break;
				} else if (maze[i][x] instanceof StoneWall || maze[i][x] instanceof Gate) {
					xend = i;
					yend = x;
					break;
				}
			}
			if (maze[xend][yend] instanceof explosion) {
				maze[xend][yend] = null;
				maze[xend][yend] = control.road;
				repaint();
			} else if (!(maze[xend - 1][yend] instanceof Human)) {
				maze[xend - 1][yend] = null;
				maze[xend - 1][yend] = control.road;
				repaint();
			}
			control.setMazeObj(maze);
		}
	}

	private void setLeftImage() {
		this.man = this.leftMan;
	}

	private void setUpImage() {
		this.man = this.upMan;
	}

	private void setDownImage() {
		this.man = this.downMan;
	}

	private void setRightImage() {
		this.man = this.rightMan;
	}

	public String getScore() {
		return Integer.toString(Human.getInstance().getScore());

	}

	public String getLife() {
		return Integer.toString(Human.getInstance().getLife());

	}

	public String getbullet() {
		return Integer.toString(Human.getInstance().getBullets());

	}

	public void lose() {

		if (Human.getInstance().getState().equalsIgnoreCase("DeadState")) {
			JOptionPane.showMessageDialog(null, "YOU LOST !!");
			timeElapsed.setTerminator(false);
			System.exit(0);
		} else if (Human.getInstance().getState().equalsIgnoreCase("WinState")) {
			JOptionPane.showMessageDialog(null, "You won the game, Congratulations");
			timeElapsed.setTerminator(false);
			System.exit(0);

		}
	}

}
