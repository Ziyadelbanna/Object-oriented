package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Circle extends DrawShape {

	private double radius = 0;
	Graphics2D g2;
	Map circle;
	int px,py,pw,ph;
	int x,y,x2,y2;

	public Circle ()
	{
		circle = new HashMap<String, Double>();
		circle.put("width", radius);
	}
	public Circle(int x , int y , int x2, int y2) {
		
		 px = Math.min(x, x2);
		 py = Math.min(y, y2);
		 pw = Math.abs(x - x2);
		 ph = Math.abs(y - y2);
		 this.x=x;
		 this.x2=x2;
		 this.y=y;
		 this.y2=y2;
		 
	}

	public void setProperties(Map<String, Double> properties) {

		circle = new HashMap<String, Double>(properties);
	}

	public Map<String, Double> getProperties() {

		return circle;
	}
	public void setColor(Color color) {
		currentcolor = color;
	}


	public void draw(Graphics canvas) {

		g2 = (Graphics2D) canvas;
		g2.setColor(getFillColor());
		g2.fillOval(px,py,pw,pw);
		g2.setColor(getColor());
		g2.drawOval(px,py,pw,pw);
		
	
		
	}

	public Object clone() throws CloneNotSupportedException {
		Point p2 = new Point();
		Map clone = new HashMap<String, Double>(circle);
		p2.x = p.x + 1;
		p2.y = p.y + 1;
		Shape c = new Circle(x,y,x2,y2);
		c.setPosition(p2);
		c.setColor(currentcolor);
		c.setFillColor(fillcolor);

		c.setProperties(clone);

		return c;
	}

}
