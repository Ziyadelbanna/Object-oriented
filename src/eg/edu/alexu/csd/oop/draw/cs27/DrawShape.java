package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

import eg.edu.alexu.csd.oop.draw.Shape;

public class DrawShape implements Shape {

	protected Point p;
	Color currentcolor;
	Color fillcolor;

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
	}

	public Object clone() throws CloneNotSupportedException {
		return null;
	}

}
