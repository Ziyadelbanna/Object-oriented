package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.*;
import java.util.*;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Line extends DrawShape {

	private double length, bs = 0.0;
	private Point p1, p2;
	Map<String, Double> line;
	Graphics2D g2;
	

	public Line(int x,int y, int x2, int y2) {
		line = new HashMap<String, Double>();
		line.put("x1", 20.0);// (double) p1.x);
		line.put("y1", 20.0);// (double) p2.y);
		line.put("x2", 300.0);// (double) p1.x);
		line.put("y2", 300.0);// (double) p2.y);
		line.put("length", length);
		line.put("fontsize", 10.0);
		p1 = new Point (x,y);
		p2 = new Point (x2,y2);
	}
	public void setColor(Color color) {
		currentcolor = color;
	}

	public void setProperties(Map<String, Double> properties) {
		line = new HashMap<String, Double>(properties);
	}

	public Map<String, Double> getProperties() {
		return line;
	}

	public void draw(Graphics canvas) {
		// draw
		g2 = (Graphics2D) canvas;
//		float fs = (line.get("fontsize")).floatValue();
//		BasicStroke B = new BasicStroke(fs);
//		g2.setStroke(B);
		g2.setColor(currentcolor);
		g2.drawLine(p1.x, p1.y, p2.x, p2.y);

	}

	public Object clone() throws CloneNotSupportedException {
		Point p2 = new Point();
		Map clone = new HashMap<String, Double>(line);
		p2.x = p.x + 1;
		p2.y = p.y + 1;
		Shape sh = new Line(p1.x,p1.y,p2.x,p2.y);
		sh.setPosition(p2);
		sh.setColor(currentcolor);
		sh.setFillColor(fillcolor);

		sh.setProperties(clone);

		return sh;
	}

}
