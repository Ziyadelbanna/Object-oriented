package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class DrawEngine implements DrawingEngine {

	LinkedList<Shape> shapes;
	LinkedList<LinkedList<Shape>> shapeslists = new LinkedList<LinkedList<Shape>>();
	Graphics2D g2;
	protected int currentindex = 0;
	private int slcount = 0;
	boolean redo = false;
	public List<Class<? extends Shape>> list;
	boolean undolimit = false;
	boolean empty = true;
	LinkedList<LinkedList<Shape>> l = new LinkedList<LinkedList<Shape>>();
	LinkedList<LinkedList<Shape>> r = new LinkedList<LinkedList<Shape>>();

	public void refresh(Graphics canvas) {

//		if (shapeslists.size() == 0 || shapeslists.get(currentindex).size() == 0) {
//			throw null;
//		}
		try {
			for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
				shapeslists.get(currentindex).get(i).draw(canvas);
			}
		} catch (NullPointerException ne) {
			throw null;
		}
	}

	public void addShape(Shape shape) {
		if (shape.equals(null)) {
			throw null;
		}
		if (shapeslists.size() == 0) {
			shapes = new LinkedList<Shape>();
			shapes.add(shape);
			shapeslists.add(shapes);
		} else {
			shapes = new LinkedList<Shape>(shapeslists.getLast());
			shapes.add(shape);
			shapeslists.add(new LinkedList<Shape>(shapes));
		}
		if (r.size() > 0) {
			r = new LinkedList<LinkedList<Shape>>();
		}
		if (l.size() < 20) {
			l.add(new LinkedList<Shape>(shapes));
		} else {
			l.removeFirst();
			l.add(new LinkedList(shapes));
		}
		empty = false;
		currentindex = shapeslists.size() - 1;
	}

	public void removeShape(Shape shape) {

		LinkedList<Shape> newshapes = new LinkedList<Shape>();
		if (shape.equals(null)) {
			throw null;
		}
		for (int i = 0; i < shapeslists.getLast().size(); i++) {
			if (shapeslists.getLast().get(i).equals(shape)) {
				continue;
			} else {
				newshapes.add(shapeslists.getLast().get(i));
			}
		}
		shapeslists.add(new LinkedList<Shape>(newshapes));
		if (r.size() > 0) {
			r = new LinkedList<LinkedList<Shape>>();

		}
		if (l.size() < 20) {
			l.add(new LinkedList<Shape>(newshapes));
		} else {
			l.removeFirst();
			l.add(new LinkedList<Shape>(newshapes));
		}
		empty = false;
		currentindex = shapeslists.size() - 1;
	}

	public void updateShape(Shape oldShape, Shape newShape) {
		LinkedList<Shape> newshapes = new LinkedList<Shape>();
		if (oldShape.equals(null) || newShape.equals(null)) {
			throw null;
		}
		for (int i = 0; i < shapeslists.getLast().size(); i++) {
			if (shapeslists.getLast().get(i).equals(oldShape)) {
				newshapes.add(newShape);
			} else {
				newshapes.add(shapeslists.getLast().get(i));
			}
		}
		shapeslists.add(new LinkedList<Shape>(newshapes));

		if (r.size() > 0) {
			r = new LinkedList<LinkedList<Shape>>();

		}
		if (l.size() < 20) {
			l.add(new LinkedList<Shape>(newshapes));
		} else {
			l.removeFirst();
			l.add(new LinkedList<Shape>(newshapes));
		}
		empty = false;
		currentindex = shapeslists.size() - 1;
	}

	public Shape[] getShapes() {
		if (shapeslists.size() == 0) {
			Shape[] shapes = new Shape[0];
			return shapes;
		}
		Shape[] shapes = new Shape[shapeslists.getLast().size()];
		for (int i = 0; i < shapeslists.getLast().size(); i++) {
			shapes[i] = shapeslists.getLast().get(i);
		}
		return shapes;
	}

	public List<Class<? extends Shape>> getSupportedShapes() {

		list = new LinkedList<Class<? extends Shape>>();

		list.add(Line.class);
		list.add(Square.class);
		list.add(Ellipse.class);
		list.add(Triangle.class);
		list.add(Rectangle.class);
		list.add(Circle.class);
		return list;
	}

	public void undo() {
		if (l.size() > 0) {
			shapeslists.removeLast();
			r.add(new LinkedList<Shape>(l.removeLast()));
			currentindex--;
		}
	}

	public void redo() {
		if (r.size() > 0) {
			LinkedList<Shape> n = new LinkedList<Shape>();
			n = r.removeLast();
			shapeslists.add(new LinkedList<Shape>(n));
			l.add((new LinkedList<Shape>(n)));
		}
	}

	public int size() {
		return shapeslists.getLast().size();
	}

	public int current() {
		return currentindex;
	}

	public void save(String path) {

		if (path.toLowerCase().contains(".xml")) {
		}
	}

	public void load(String path) {
		if (path.toLowerCase().contains(".xml")) {
			LinkedList<Shape> loaded = new LinkedList<Shape>();

		}
	}

}
