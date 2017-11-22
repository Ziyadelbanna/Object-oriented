package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBContext;
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

public class DrawEngine implements DrawingEngine {

	LinkedList<Shape> shapes;
	LinkedList<LinkedList<Shape>> shapeslists = new LinkedList<LinkedList<Shape>>();
	Graphics2D g2;
	protected int currentindex = 0;
	public List<Class<? extends Shape>> list;
	LinkedList<LinkedList<Shape>> l = new LinkedList<LinkedList<Shape>>();
	LinkedList<LinkedList<Shape>> r = new LinkedList<LinkedList<Shape>>();

	public void refresh(Graphics canvas) {
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

	public void save(String path) {

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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void load(String path) {

		shapeslists = new LinkedList<LinkedList<Shape>>();
		shapeslists.add(new LinkedList<Shape>());
		l = new LinkedList<LinkedList<Shape>>();
		r = new LinkedList<LinkedList<Shape>>();
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
	}

}
