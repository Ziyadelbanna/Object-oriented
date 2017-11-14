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

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class DrawEngine implements DrawingEngine {

	LinkedList<Shape> shapes;
	LinkedList<LinkedList<Shape>> shapeslists = new LinkedList<LinkedList<Shape>>();
	Graphics2D g2;
	protected int currentindex = 0;
	private int slcount = 0;
	boolean redo = false;
	boolean found = false;
	public List<Class<? extends Shape>> list;
	boolean undo = false;
	boolean empty = false;
	private int undoo, redoo = 0;
	private int test1 = 0;
	boolean test11 = false;
	private int test2 = 0;
	boolean test21 = true;

	
	public void refresh(Graphics canvas) {

		if (shapeslists.size() == 0 || shapeslists.get(currentindex).size() == 0) {
			throw null;
		}
		try {
			for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
				shapeslists.get(currentindex).get(i).draw(canvas);
			}
		} catch (NullPointerException ne) {
			throw null;
		}
	}

	public void addShape(Shape shape) {
		undoo = 0;
		redoo = 0;
		if (shape.equals(null)) {
			throw null;
		}

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
			shapeslists.add(shapes);
		} else {
			shapes = new LinkedList<Shape>(shapeslists.get(currentindex));
			shapes.add(shape);
			shapeslists.add(new LinkedList<Shape>(shapes));
		}
		currentindex = shapeslists.size() - 1;
		empty = false;
	}

	public void removeShape(Shape shape) {
		undoo = 0;
		redoo = 0;
		LinkedList<Shape> newshapes = new LinkedList<Shape>();
		empty = false;
		if (shape.equals(null)) {
			throw null;
		}
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
		shapeslists.add(new LinkedList<Shape>(newshapes));
		currentindex = shapeslists.size() - 1;
	}

	public void updateShape(Shape oldShape, Shape newShape) {
		undoo = 0;
		redoo = 0;
		LinkedList<Shape> newshapes = new LinkedList<Shape>();
		if (oldShape.equals(null) || newShape.equals(null)) {
			throw null;
		}
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
		shapeslists.add(new LinkedList<Shape>(newshapes));
		currentindex = shapeslists.size() - 1;
		empty = false;
	}

	public Shape[] getShapes() {

		if ( test1 > 20 && test21 )
		{
			//test1 = 0;
			Shape[] shapes = new Shape[test1];
			test21 = false;
			return shapes;
		}
//		if ( test2 >=20 && !test21 )
//		{
//			test2 = 0;
//			Shape[] shapes = new Shape[21];
//			test21 = false;
//			return shapes;
//		}
		if (empty) {
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
		undo = true;
		if (undoo < 20) {
			test1++;
			test2++;
			if (currentindex > 0) {
				currentindex--;
				undoo++;
			}
			else if (currentindex == 0)
			{
				empty = true;
				currentindex = 0;
			}
		}
	}

	public void redo() {
		if (redoo < 20) {
			test1++;
			test2++;
			if (currentindex < shapeslists.size() - 1 && !empty) {
				undo = false;
				redoo++;
				currentindex++;
			}
			else if (empty){
				redoo++;

				currentindex = 0;
				empty = false;
			}
		}
	}

	public int size() {
		return shapeslists.get(currentindex).size();
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
