package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Ellipse extends DrawShape {

	Map ellipse;
	Graphics2D g2;
	private double diameter1, diameter2 = 0;
	private int x, y ,x2,y2= 0;
	int px,py,pw,ph;

	public Ellipse()
	{
		ellipse = new HashMap<String, Double>();
		ellipse.put("width", diameter1);
		ellipse.put("height", diameter2);
		ellipse.put("x", x);
		ellipse.put("y", y);
	}
	public Ellipse(int x , int y, int x2 , int y2) {
		
		this.x2=x2;
		this.y2=y2;
		 px = Math.min(x, x2);
		 py = Math.min(y, y2);
		 pw = Math.abs(x - x2);
		 ph = Math.abs(y - y2);
	}
	public void setColor(Color color) {
		currentcolor = color;
	}

	public void setProperties(Map<String, Double> properties) {

		ellipse = new HashMap<String, Double>(properties);
	}

	public Map<String, Double> getProperties() {

		return ellipse;
	}

	public void draw(Graphics canvas) {
		
		g2 = (Graphics2D) canvas;
		g2.setColor(fillcolor);
		g2.fillOval(px,py,pw,ph);
		g2.setColor(currentcolor);
		g2.drawOval(px,py,pw,ph);
		

	}

	public Object clone() throws CloneNotSupportedException {
		Point p2 = new Point();
		Map clone = new HashMap<String, Double>(ellipse);
		p2.x = p.x + 1;
		p2.y = p.y + 1;
		Shape c = new Ellipse(x,y,x2,y2);
		c.setPosition(p2);
		c.setColor(currentcolor);
		c.setFillColor(fillcolor);

		c.setProperties(clone);

		return c;
	}

}
