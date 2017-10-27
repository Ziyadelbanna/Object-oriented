package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

import eg.edu.alexu.csd.oop.draw.Shape;

public class DrawShape implements Shape {

	protected Point p;
	Color currentcolor;
	private double length, bs = 0.0;
	private float fs = 10;
	Color fillcolor;
	Graphics2D g2;

	public void setPosition(Point position) {

		p = position;
	}

	public Point getPosition() {
		return p;
	}

	public void setProperties(Map<String, Double> properties) {

	}

	public Map<String, Double> getProperties() {
		return null;
	}

	public void setColor(Color color) {
		currentcolor = color;
	}

	public Color getColor() {
		return currentcolor;
	}

	public void setFillColor(Color color) {
		fillcolor = color;
	}

	public Color getFillColor() {

		return fillcolor;
	}

	public void draw(Graphics canvas) {
		g2 = (Graphics2D) canvas;
		BasicStroke B = new BasicStroke(fs);
		g2.setStroke(B);
		g2.setColor(getColor());
		g2.drawLine(10, 10, 20, 20);
	}

	public Object clone() throws CloneNotSupportedException {
		return null;
	}

}
