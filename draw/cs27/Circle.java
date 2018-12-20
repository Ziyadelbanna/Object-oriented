package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Circle extends DrawShape {

	private int radius = 0;
	Graphics2D g2;
	public Circle ()
	{
		properties = new HashMap<String, Double>();
		properties.put("radius", 0.0);
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {

		this.properties.put("radius", properties.get("radius"));
		this.radius= properties.get("radius").intValue(); 
	}
	
	@Override
	public void draw(Graphics canvas) {

		g2 = (Graphics2D) canvas;
		g2.setColor(getFillColor());
		g2.fillOval(position.x,position.y,radius,radius);
		g2.setColor(getColor());
		g2.drawOval(position.x,position.y,radius,radius);
		
	
		
	}

	
}
