package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.util.LinkedList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class MyDrawEngine implements DrawingEngine {

	LinkedList<LinkedList<Shape>> shapeslists = new LinkedList<LinkedList<Shape>>();
	Graphics2D g2;
	protected int currentindex = 0;
	public List<Class<? extends Shape>> classeslist = new ArrayList<>();

	public MyDrawEngine() {

		shapeslists.add(new LinkedList<Shape>());
		classeslist.add(Line.class);
		classeslist.add(Square.class);
		classeslist.add(Ellipse.class);
		classeslist.add(Triangle.class);
		classeslist.add(Rectangle.class);
		classeslist.add(Circle.class);

	}

	public void refresh(Graphics canvas) {
		this.g2 = (Graphics2D) canvas;
		g2.clearRect(0, 0, 1000, 10000);

		if (!shapeslists.isEmpty()) {
			for (int i = 0; i < shapeslists.getLast().size(); i++) {
				shapeslists.getLast().get(i).draw(g2);
				// System.out.println(shapeslists.get(currentindex).get(i));
			}
		}

	}

	public void addShape(Shape shape) {
		if (shape.equals(null)) {
			throw null;
		}
		LinkedList<Shape> temp;

		temp = new LinkedList<>(shapeslists.get(currentindex));
		temp.add(shape);
		for (int i = shapeslists.size() - 1; i > currentindex; i--)
			shapeslists.remove(i);
		shapeslists.add(new LinkedList<Shape>(temp));
		currentindex++;

	}

	public void removeShape(Shape shape) {
		LinkedList<Shape> newshapes = new LinkedList<Shape>();
		if (shape.equals(null)) {
			throw null;
		}
		for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
			if (shapeslists.get(currentindex).get(i).equals(shape)) {
				continue;
			} else {
				newshapes.add(shapeslists.get(currentindex).get(i));
			}
		}
		shapeslists.add(new LinkedList<Shape>(newshapes));
		currentindex++;
		refresh(g2);
	}

	public void updateShape(Shape oldShape, Shape newShape) {
		LinkedList<Shape> newshapes = new LinkedList<Shape>();
		if (oldShape.equals(null) || newShape.equals(null)) {
			throw null;
		}
		for (int i = 0; i < shapeslists.get(currentindex).size(); i++) {
			if (shapeslists.get(currentindex).get(i).equals(oldShape)) {
				newshapes.add(newShape);
			} else {
				newshapes.add(shapeslists.get(currentindex).get(i));
			}
		}
		shapeslists.add(new LinkedList<Shape>(newshapes));
		currentindex++;
		refresh(g2);
	}

	public Shape[] getShapes() {
		if (shapeslists.size() == 0) {
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

		return classeslist;
	}

	public void undo() {
		if (currentindex != 0) {
			currentindex--;
			refresh(g2);
		}
	}

	public void redo() {
		if (currentindex != shapeslists.size() - 1) {
			currentindex++;
			refresh(g2);

		}
	}

	public void save(String path) {
		if (path.contains(".xml")) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			File fos = new File(path);
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.newDocument();
				Element root = doc.createElement("CurrentShapes");
				doc.appendChild(root);
				for (Shape s : shapeslists.getLast()) {
					Element shape = doc.createElement("shape");
					shape.appendChild(doc.createTextNode(String.valueOf(s)));
					root.appendChild(shape);
				}
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer t = tf.newTransformer();
				DOMSource src = new DOMSource(doc);
				StreamResult res = new StreamResult(fos);
				t.transform(src, res);

				JAXBContext jaxbContext = JAXBContext.newInstance(Shapes.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
//				Shapes s = (Shapes) unmarshaller.unmarshal(f);
//				for (DrawShape c : s.getshapes()) {
//					shapeslists.getLast().add((Shape) c);
//				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (path.contains(".json")) {
			SaveAndLoad save = new SaveAndLoad();
			save.save(path, shapeslists);
		}
	}

	public void load(String path) {
		if (path.contains(".xml")) {
			shapeslists = new LinkedList<LinkedList<Shape>>();
			shapeslists.add(new LinkedList<Shape>());
			File f = new File(path);

			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Shapes.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				Shapes s = (Shapes) unmarshaller.unmarshal(f);
				for (DrawShape c : s.getshapes()) {
					shapeslists.getLast().add((Shape) c);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (path.contains(".json")) {
			SaveAndLoad load = new SaveAndLoad();
			this.shapeslists = load.load(path);
		}
	}

	public void clear() {
		shapeslists.clear();
		shapeslists.add(new LinkedList<Shape>());
		currentindex = 0;
		refresh(g2);
	}

}
