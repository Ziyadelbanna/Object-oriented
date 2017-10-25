package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class DrawEngine implements DrawingEngine {

	// saveandload sl = new saveandload();
	LinkedList<Shape> shapes;
	LinkedList<LinkedList<Shape>> shapeslists = new LinkedList<LinkedList<Shape>>();
	Graphics2D g2;
	LinkedList<Integer> indices = new LinkedList();
	protected int currentindex = 0;
	private int undo, redo = 0;
	boolean redoo = false;
	boolean found = false;

	public void refresh(Graphics canvas) {

		if (canvas == null) {
			throw null;
		} else {
			for (Shape l : shapeslists.get(currentindex)) {
				l.draw(canvas);
			}
		}

	}

	public void addShape(Shape shape) {
		if (currentindex < shapeslists.size() - 1) {
			for (int i = currentindex + 1; i < shapeslists.size(); i++) {
				shapeslists.remove(i);
				i--;
			}
			currentindex = shapeslists.size() - 1;
		}
		if (shapeslists.size() == 0) {
			shapes = new LinkedList<Shape>();
			shapes.add(shape);
			shapeslists.add(new LinkedList<Shape>(shapes));

		} else {
			shapes = new LinkedList<Shape>(shapeslists.get(currentindex));
			shapes.add(shape);
			shapeslists.add(new LinkedList<Shape>(shapes));
		}
		if (shapeslists.size() > 21) {
			shapeslists.remove(0);
		}
		redoo = false;
		redo = 0;
		undo = 0;
		currentindex = shapeslists.size() - 1;

	}

	public void removeShape(Shape shape) {
		LinkedList<Shape> newshapes = new LinkedList<Shape>();
		if (currentindex < shapeslists.size() - 1) {
			for (int i = currentindex + 1; i < shapeslists.size(); i++) {
				shapeslists.remove(i);
				i--;
			}
			currentindex = shapeslists.size() - 1;
		}

		for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
			if (shapeslists.get(currentindex).get(i).equals(shape)) {
				continue;
			} else {
				newshapes.add(shapeslists.get(currentindex).get(i));
			}
		}
		if (shapeslists.size() > 21) {
			shapeslists.remove(0);
		}
		shapeslists.add(new LinkedList<Shape>(newshapes));
		currentindex = shapeslists.size() - 1;
		redoo = false;

		redo = 0;
		undo = 0;

	}

	public void updateShape(Shape oldShape, Shape newShape) {
		LinkedList<Shape> newshapes = new LinkedList<Shape>();
		if (currentindex < shapeslists.size() - 1) {
			for (int i = currentindex + 1; i < shapeslists.size(); i++) {
				shapeslists.remove(i);
				i--;
			}
			currentindex = shapeslists.size() - 1;
		}

		for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
			if (shapeslists.get(currentindex).get(i).equals(oldShape)) {

				newshapes.add(newShape);
			} else {
				newshapes.add(shapeslists.get(currentindex).get(i));
			}
		}
		if (shapeslists.size() > 21) {
			shapeslists.remove(0);
		}
		shapeslists.add(new LinkedList<Shape>(newshapes));
		currentindex = shapeslists.size() - 1;
		redoo = false;

		redo = 0;
		undo = 0;
	}

	public Shape[] getShapes() {
		if (currentindex == -1) {
			Shape[] shapes = new Shape[0];
			return shapes;

		}

		Shape[] shapes = new Shape[shapeslists.get(currentindex).size()];

		for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
			shapes[i] = shapeslists.get(currentindex).get(i);
		}
		return shapes;
	}

	public List<Class<? extends Shape>> getSupportedShapes() {

		return null;
	}

	public void undo() {

		// if (undo <= 20) {
		currentindex--;
		undo++;
		redoo = true;
		// } else {
		// }

	}

	public void redo() {

		// if (redo <= 20) {
		if (redoo) {
			currentindex++;
			redo++;
		}
		// } else {
		// }
	}

	public int size() {
		return shapeslists.get(currentindex).size();
	}

	public void save(String path) {

		// sl.save(path);

	}

	public void load(String path) {
		// sl.load(path);

	}
}
