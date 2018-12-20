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

	private int a,b = 0;
	Graphics2D g2;
	public Ellipse ()
	{
		properties = new HashMap<String, Double>();
		properties.put("a", 0.0);
		properties.put("b", 0.0);
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {

		this.properties.put("a", properties.get("a"));
		this.properties.put("b", properties.get("b"));
		this.a= Math.max(properties.get("a").intValue(), properties.get("b").intValue());
		this.b=  Math.min(properties.get("a").intValue(), properties.get("b").intValue());
	}
	
	@Override
	public void draw(Graphics canvas) {

		g2 = (Graphics2D) canvas;
		g2.setColor(getFillColor());
		g2.fillOval(position.x,position.y,a,b);
		g2.setColor(getColor());
		g2.drawOval(position.x,position.y,a,b);
		
	
		
	}


}
