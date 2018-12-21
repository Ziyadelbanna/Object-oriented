package mazeGeneration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import Gui.Main;
/* welcome to our project :D
 Ziyad el banna
 Khalil ismail
 Abdelrahman torki
 Abdelrahman saeed */

public class mazeGenerate {

	public static final Logger log = Logger.getLogger(mazeGenerate.class);

	int recnum = 0;
	// the maze of the game
	int[][] maze;
	// attributes of the maze
	int height, width;
	// starting and ending point
	Map<Character, Integer> startpoint = new HashMap();
	Map<Character, Integer> endpoint = new HashMap();

	public void generateMaze(int height, int width) {

		if (height == 0 || width == 0) {
			log.debug("height = 0 , width = 0");
			log.error("Cannot create an empty maze");
		}

		if (height % 2 == 0) {
			height++;
		}
		if (width % 2 == 0) {
			width++;
		}
		this.height = height;
		this.width = width;

		maze = new int[this.height][this.width];
		// Initialize , set all the maze to walls
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				maze[i][j] = 1;

		Random rand = new Random();
		// r for row
		// c for column
		// Generate random r
		int r = rand.nextInt(height);
		while (r % 2 == 0) {
			r = rand.nextInt(height);
		}
		// Generate random c
		int c = rand.nextInt(width);
		while (c % 2 == 0) {
			c = rand.nextInt(width);
		}
		// Starting cell
		// set starting cell to path
		maze[r][c] = 0;
		// System.out.println(r);
		// System.out.println(c);

		// Allocate the maze with recursive method
		recursion(r, c);
		// starting point = '2'
		startpoint.put('x', r);
		startpoint.put('y', c);
		maze[r][c] = 2;

		// ending point = '8'
		maze[endpoint.get('x')][endpoint.get('y')] = 8;

		 for (int i = 0; i < maze.length; i++) {
		 for (int u = 0; u < maze[i].length; u++) {
		 System.out.print(maze[i][u] + " ");
		 }
		 System.out.println(" ");
		 }
		 System.out.println();
		 System.out.println("-------After adding attributes -------");
		 System.out.println();
		
		generateGameAttributes();
		
		 for (int i = 0; i < maze.length; i++) {
		 for (int u = 0; u < maze[i].length; u++) {
		 System.out.print(maze[i][u] + " ");
		 }
		 System.out.println(" ");
		 }

	}

	// r for row
	// c for column
	public void recursion(int r, int c) {
		// 4 random directions
		Integer[] randDirs = generateRandomDirections();
		// Examine each direction
		boolean found = true;

		for (int i = 0; i < randDirs.length; i++) {
			switch (randDirs[i]) {
			case 1: // Up
				// Whether 2 cells up is out or not
				if (r - 2 <= 0) {
					found = false;
					continue;
				}

				if (maze[r - 2][c] != 0) {
					maze[r - 2][c] = 0;
					maze[r - 1][c] = 0;
					recursion(r - 2, c);
				}
				break;
			case 2: // Right
				// Whether 2 cells to the right is out or not
				if (c + 2 >= width - 1) {
					found = false;
					continue;
				}
				if (maze[r][c + 2] != 0) {
					maze[r][c + 2] = 0;
					maze[r][c + 1] = 0;
					recursion(r, c + 2);
				}
				break;
			case 3: // Down
				// Whether 2 cells down is out or not
				if (r + 2 >= height - 1) {
					found = false;
					continue;
				}
				if (maze[r + 2][c] != 0) {
					maze[r + 2][c] = 0;
					maze[r + 1][c] = 0;
					recursion(r + 2, c);

				}
				break;
			case 4: // Left
				// Whether 2 cells to the left is out or not
				if (c - 2 <= 0) {
					found = false;
					continue;
				}
				if (maze[r][c - 2] != 0) {
					maze[r][c - 2] = 0;
					maze[r][c - 1] = 0;
					recursion(r, c - 2);
				}
				break;
			}
		}
		if (!found && recnum < 1) {
			recnum++;
			// System.out.println(r);
			// System.out.println(c);
			endpoint.put('x', r);
			endpoint.put('y', c);

		}

	}

	public void generateGameAttributes() {
		LinkedList<Map> treesmaps = new LinkedList();
		LinkedList<Map> bombs = new LinkedList();
		LinkedList<Map> gifts = new LinkedList();
		int mapsize = maze.length;

		// add trees to the game
		// tree has number '3'
		// add trees to the game
		// tree has number '3'
		for (int i = 0; i < height; i++) {
			for (int u = 0; u < width; u++) {
				if (i == 0)
					continue;
				if (u == 0 || u == width - 1 || i == height - 1)
					continue;
				if (maze[i][u] == 1) {
					Map<Character, Integer> coordinatetrees = new HashMap();
					coordinatetrees.put('x', i);
					coordinatetrees.put('y', u);
					treesmaps.add(coordinatetrees);
				}
			}
		}
		Collections.shuffle(treesmaps);
		for (int i = 0; i < treesmaps.size() / 2; i++) {
			Map currentmap = treesmaps.get(i);
			maze[(int) currentmap.get('x')][(int) currentmap.get('y')] = 3;
		}

		// add bombs to the game
		// bombs are also path for the player
		// bombs have two kinds which have numbers '4' & '5'

		for (int i = 0; i < height; i++) {
			for (int u = 0; u < width; u++) {
				// check if its a path
				if (maze[i][u] == 0) {
					Map<Character, Integer> coordinatebombs = new HashMap();
					coordinatebombs.put('x', i);
					coordinatebombs.put('y', u);
					bombs.add(coordinatebombs);
				}
			}
		}
		Collections.shuffle(bombs);
		for (int i = 0; i < bombs.size() / 5; i++) {
			Map currentmap = bombs.get(i);
			if (i < bombs.size() / 10)
				maze[(int) currentmap.get('x')][(int) currentmap.get('y')] = 4;
			else
				maze[(int) currentmap.get('x')][(int) currentmap.get('y')] = 5;
		}

		// add gifts to the game
		// bifts are also path for the player
		// gifts have two kinds which have numbers '6' & '7'

		for (int i = 0; i < height; i++) {
			for (int u = 0; u < width; u++) {
				// check if its a path
				if (maze[i][u] == 0 || maze[i][u] == 4 || maze[i][u] == 5) {
					Map<Character, Integer> coordinategifts = new HashMap();
					coordinategifts.put('x', i);
					coordinategifts.put('y', u);
					gifts.add(coordinategifts);
				}
			}
		}
		Collections.shuffle(gifts);
		for (int i = 0; i < gifts.size() / 8; i++) {
			Map currentmap = gifts.get(i);
			if (i < gifts.size() / 16)
				maze[(int) currentmap.get('x')][(int) currentmap.get('y')] = 6;
			else
				maze[(int) currentmap.get('x')][(int) currentmap.get('y')] = 7;
		}
	}

	/**
	 * Generate an array with random directions 1-4
	 * 
	 * @return Array containing 4 directions in random order...
	 */
	public Integer[] generateRandomDirections() {
		ArrayList<Integer> randoms = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++)
			randoms.add(i + 1);
		Collections.shuffle(randoms);
		// for (Integer i : randoms)
		// {
		// System.out.print(i);
		// }
		// System.out.println(" ");

		return randoms.toArray(new Integer[4]);
	}

	public void init(int height, int width) {
		generateMaze(height, width);
	}

	public int[][] getMazeMap(int height, int width) {
		init(height, width);
		return maze;
	}
}