package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Rectangle extends DrawShape {
	Graphics2D g2;
	Map rectangle;
	private int x, y;
	int px, py, pw, ph;

	public Rectangle ()
	{
		rectangle = new HashMap<String, Double>();
		rectangle.put("length", ph);
		rectangle.put("width", pw);
	}
	public Rectangle(int x, int y, int x2, int y2) {
		px = Math.min(x, x2);
		py = Math.min(y, y2);
		pw = Math.abs(x - x2);
		ph = Math.abs(y - y2);
		
		this.x = x;
		this.y = y;

	}

	public void setColor(Color color) {
		currentcolor = color;
	}

	public void setProperties(Map<String, Double> properties) {
		rectangle = new HashMap<String, Double>(properties);
	}

	@Override
	public Map<String, Double> getProperties() {
		return rectangle;
	}

	public void draw(Graphics canvas) {

		g2 = (Graphics2D) canvas;
		g2.setColor(getFillColor());
		g2.fillRect(px, py, pw, ph);
		g2.setColor(currentcolor);
		g2.drawRect(px, py, pw, ph);
	}

	public Object clone() throws CloneNotSupportedException {

		Point p2 = new Point();
		Map clone = new HashMap<String, Double>(rectangle);
		p2.x = p.x + 1;
		p2.y = p.y + 1;
		Shape c = new Rectangle(p2.x, p2.y, p2.x, p2.y);
		c.setPosition(p2);
		c.setColor(currentcolor);
		c.setFillColor(fillcolor);

		c.setProperties(clone);

		return c;
	}

}
