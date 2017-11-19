package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import eg.edu.alexu.csd.oop.draw.Shape;
@XmlRootElement(name = "shape")

public class Triangle extends DrawShape {
	Graphics2D g2;
	Map triangle;
	private int x1, x2, x3, y1, y2, y3, px, py, pmx, pmy, ph, pw = 0;
	private double length, width = 0;
	int x, y;

	public Triangle()
	{
		triangle = new HashMap<String, Double>();
		triangle.put("x1", x1);
		triangle.put("x2", x2);
		triangle.put("x3", x3);
		triangle.put("y1", y1);
		triangle.put("y2", y2);
		triangle.put("y3", y3);
	}
	public Triangle(int x, int y, int x2, int y2) {
		
		px = Math.min(x, x2);
		py = Math.min(y, y2);
		pmx = Math.max(x, x2);
		pmy = Math.max(y, y2);
		pw = Math.abs(x - x2);
		ph = Math.abs(y - y2);
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;

	}
	public void setColor(Color color) {
		currentcolor = color;
	}


	public void setProperties(Map<String, Double> properties) {
		triangle = new HashMap<String, Double>(properties);
	}

	@Override
	public Map<String, Double> getProperties() {
		return triangle;
	}

	public void draw(Graphics canvas) {

		g2 = (Graphics2D) canvas;
		int[] xt = { (int) px, (int) pmx, (int) (px - Math.abs(px - pmx)) };
		int[] yt = { (int) py, (int) pmy, (int) pmy };
		g2.setColor(fillcolor);
		g2.fillPolygon(xt, yt, 3);
		g2.setColor(currentcolor);
		g2.drawPolygon(xt, yt, 3);
		

	}

	public Object clone() throws CloneNotSupportedException {

		Point p2 = new Point();
		Map clone = new HashMap<String, Double>(triangle);
		p2.x = p.x + 1;
		p2.y = p.y + 1;
		Shape c = new Triangle(x, y, x2, y2);
		c.setPosition(p2);
		c.setColor(currentcolor);
		c.setFillColor(fillcolor);

		c.setProperties(clone);

		return c;
	}

}
