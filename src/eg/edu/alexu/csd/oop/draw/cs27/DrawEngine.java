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
	private int undo, redo = 0;
	boolean redoo = false;
	boolean found = false;
	public List<Class<? extends Shape>> list;
	boolean emptyshapes = false;
	Shape[] getshapes;

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
		if (shape.equals(null)) {
			throw null;
		}
		if (currentindex < shapeslists.size() - 1 && !emptyshapes) {
			for (int i = currentindex + 1; i < shapeslists.size(); i++) {
				shapeslists.remove(i);
				i--;
			}
			currentindex = shapeslists.size() - 1;
		}
		if (shapeslists.size() == 0 || emptyshapes) {
			shapes = new LinkedList<Shape>();
			shapes.add(shape);
			shapeslists.add(new LinkedList<Shape>(shapes));
			emptyshapes = false;

		} else {
			shapes = new LinkedList<Shape>(shapeslists.get(currentindex));
			shapes.add(shape);
			shapeslists.add(new LinkedList<Shape>(shapes));
		}
		if (shapeslists.size() > 20) {
			shapeslists.remove(0);
		}

		currentindex = shapeslists.size() - 1;

	}

	public void removeShape(Shape shape) {
		LinkedList<Shape> newshapes = new LinkedList<Shape>();
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
		if (shapeslists.size() > 20) {
			shapeslists.remove(0);
		}
		currentindex = shapeslists.size() - 1;
	}

	public void updateShape(Shape oldShape, Shape newShape) {
		emptyshapes = false;
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
		if (shapeslists.size() > 20) {
			shapeslists.remove(0);
		}
		currentindex = shapeslists.size() - 1;
		redoo = false;

	}

	public Shape[] getShapes() {
		if (emptyshapes) {
			return getshapes;

		}

		getshapes = new Shape[shapeslists.get(currentindex).size()];

		for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
			getshapes[i] = shapeslists.get(currentindex).get(i);
		}
		return getshapes;
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

		if (currentindex > 0) {
			currentindex--;
		}
		if (currentindex == 0) {
			getshapes = new Shape[0];
			emptyshapes = true;
		}
	}

	public void redo() {

		if (emptyshapes) {
			currentindex = 0;
			emptyshapes = false;
		} else if (currentindex < shapeslists.size() - 1) {
			currentindex++;
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
		}
		/*
		 * else if (path.toLowerCase().contains(".json")) {
		 *
		 * JSONArray list = new JSONArray(); JSONObject obj = new JSONObject(); for (int
		 * i = 0; i < shapeslists.get(currentindex).size(); i++) {
		 * list.add(shapeslists.get(currentindex).get(i)); } obj.put("Shapes", list);
		 *
		 * try (FileWriter file = new FileWriter(path)) {
		 *
		 * file.write(obj.toString()); file.flush();
		 *
		 * } catch (IOException ex) { ex.printStackTrace(); }
		 */
	}

	public void load(String path) {
		if (path.toLowerCase().contains(".xml")) {
			LinkedList<Shape> loaded = new LinkedList<Shape>();
			try {
				File file = new File(path);
				FileInputStream fis = new FileInputStream(file);
				XMLDecoder de = new XMLDecoder(fis);

				for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
					loaded.add((Shape) de.readObject());
				}
				shapeslists = new LinkedList<LinkedList<Shape>>();
				shapeslists.add(new LinkedList<Shape>(loaded));
				de.close();
				fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// /*
		// * else if (path.toLowerCase().contains(".json")) { JSONParser parser = new
		// * JSONParser(); try (FileWriter file = new FileWriter(path)) { Object obj =
		// * parser.parse(new FileReader(path)); JSONObject jsonObject = (JSONObject)
		// obj;
		// * JSONArray shapes = (JSONArray) jsonObject.get("Shapes");
		// *
		// * Iterator<String> iterator = shapes.iterator();
		// *
		// * LinkedList<Shape> loaded = new LinkedList<Shape>(); // while
		// * (iterator.hasNext()) { // loaded.add((Shape) iterator.next()); // }//
		// *
		// * } catch (FileNotFoundException ex) { ex.printStackTrace(); } catch
		// * (IOException ex) { ex.printStackTrace(); } catch (Exception ex) {
		// * ex.printStackTrace(); } } else { throw null; }
		// */
		//
	}
}
