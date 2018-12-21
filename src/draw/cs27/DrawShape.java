package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import eg.edu.alexu.csd.oop.draw.Shape;

public class DrawShape implements Shape {
Graphics2D g2;
Map<String, Double> properties;
Point position;
Color fillcolor;
Color bordercolor;
public DrawShape()
{
	properties = new HashMap<String, Double>();
	position = new Point(0, 0);
}
	@Override
	public void setPosition(Point position) {
		this.position=position;
		// TODO Auto-generated method stub

	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		


	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		return properties;
	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		this.bordercolor=color;

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return bordercolor;
	}

	@Override
	public void setFillColor(Color color) {
		// TODO Auto-generated method stub
		this.fillcolor=color;
	}

	@Override
	public Color getFillColor() {
		// TODO Auto-generated method stub
		return fillcolor;
	}

	@Override
	public void draw(Graphics canvas) {
		// TODO Auto-generated method stub
		

	}
	@Override 
	public Object clone() throws CloneNotSupportedException
	{
		Point p2 = new Point();
		Map clone = new HashMap<String, Double>(properties);
		p2.x = position.x + 1;
		p2.y = position.y + 1;
		Shape c = new Circle();
		c.setPosition(p2);
		c.setColor(bordercolor);
		c.setFillColor(fillcolor);

		c.setProperties(clone);

		return c;
	}
}
