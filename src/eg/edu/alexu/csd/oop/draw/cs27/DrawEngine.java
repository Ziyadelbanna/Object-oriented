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

	LinkedList<Shape> shapes;
	LinkedList<LinkedList<Shape>> shapeslists = new LinkedList<LinkedList<Shape>>();
	Graphics2D g2;
	LinkedList<Integer> indices = new LinkedList();
	private int currentindex = 0;
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
		redoo = false;
		currentindex = shapeslists.size() - 1;
	}

	public void removeShape(Shape shape) {
		LinkedList<Shape> newshapes = new LinkedList<Shape>();
		for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
			if (shapeslists.get(currentindex).get(i).equals(shape)) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw null;
		}
		else {found = false;}
		if (currentindex != shapeslists.size() - 1) {
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
		redoo = false;

	}

	public void updateShape(Shape oldShape, Shape newShape) {
		for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
			if (shapeslists.get(currentindex).get(i).equals(oldShape)) {

				found = true;break;
			}
		}
		if (!found) {
			throw null;
		}
		else {
			found = false;
		}

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
		shapeslists.add(new LinkedList<Shape>(newshapes));
		currentindex = shapeslists.size() - 1;
		redoo = false;
	}

	public Shape[] getShapes() {
		if (shapeslists.get(currentindex).size() == 0) {
			return null;
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

		if (undo + redo <= 20 && currentindex >= 0) {
			currentindex--;
			undo++;
		}
		redoo = true;
	}

	public void redo() {

		if (redo + undo <= 20 && currentindex < shapeslists.size() && redoo) {
			currentindex++;
			redo++;
		}
	}

	public int size() {
		return shapeslists.get(currentindex).size();
	}

	public void save(String path) {

		if (path.toLowerCase().contains(".xml")) {
			try {
				File file = new File(path);
				FileOutputStream fos = new FileOutputStream(file);
				XMLEncoder en = new XMLEncoder(fos);

				for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
					en.writeObject(shapeslists.get(currentindex).get(i));
				}
				en.close();
				fos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else if (path.toLowerCase().contains(".json")) {
			try (FileWriter file = new FileWriter(path)) {

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			throw null;
		}

	}

	public void load(String path) {
		if (path.toLowerCase().contains(".xml")) {
			Shape[] sh = new Shape[shapeslists.get(currentindex).size()];
			try {
				File file = new File(path);
				FileInputStream fis = new FileInputStream(file);
				XMLDecoder de = new XMLDecoder(fis);

				for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
					sh[i] = (Shape) de.readObject();
				}
				de.close();
				fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		else if (path.toLowerCase().contains(".json")) {
			try (FileWriter file = new FileWriter(path)) {

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			throw null;
		}

	}

}
