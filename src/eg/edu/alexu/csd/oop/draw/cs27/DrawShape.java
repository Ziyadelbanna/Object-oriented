package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.*;
import java.util.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.swing.JPanel;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import eg.edu.alexu.csd.oop.draw.Shape;

@XmlRootElement(name = "shape")
@XmlAccessorType (XmlAccessType.FIELD)

public class DrawShape implements Shape {
	
	protected Point p;
	Color currentcolor;
	private double length, bs = 0.0;
	private float fs = 10;
	Color fillcolor;
	protected static Graphics2D g2;

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
