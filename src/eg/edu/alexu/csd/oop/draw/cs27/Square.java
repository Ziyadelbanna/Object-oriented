package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Square extends DrawShape {

	Graphics2D g2;
	Map square;
	private int length,x,y,x2,y2,px,py,ph,pw ;

	public Square()
	{
		square = new HashMap<String, Double>();
		square.put("length", length);
		square.put("x", x);
		square.put("y", y);
	}
	public Square( int x, int y , int x2, int y2) {
		
		 px = Math.min(x, x2);
		 py = Math.min(y, y2);
		 pw = Math.abs(x - x2);
		 ph = Math.abs(y - y2);

	}
	public void setColor(Color color) {
		currentcolor = color;
	}


	public void setProperties(Map<String, Double> properties) {
		square = new HashMap<String, Double>(properties);
	}

	@Override
	public Map<String, Double> getProperties() {
		return square;
	}

	@Override
	public void draw(Graphics canvas) {

		g2 = (Graphics2D) canvas;
		g2.setColor(getFillColor());
		g2.fillRect(px,py,pw,pw);
		g2.setColor(getColor());
		g2.drawRect(px,py,pw,pw);
		
		
	}

	public Object clone() throws CloneNotSupportedException {
		
		Point p2 = new Point();
		Map clone = new HashMap<String, Double>(square);
		p2.x = p.x + 1;
		p2.y = p.y + 1;
		Shape c = new Square(x,y,x2,y2);
		c.setPosition(p2);
		c.setColor(currentcolor);
		c.setFillColor(fillcolor);

		c.setProperties(clone);

		return c;
	}

}
