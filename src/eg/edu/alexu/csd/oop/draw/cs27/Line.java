package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Line extends DrawShape {

	private int x,y = 0;
	Graphics2D g2;
	public Line ()
	{
		properties = new HashMap<String, Double>();
		properties.put("x", 0.0);
		properties.put("y", 0.0);
		
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {

		this.properties.put("x", properties.get("x"));
		this.x= properties.get("x").intValue()-position.x; 
		this.properties.put("y", properties.get("y"));
		this.y= properties.get("y").intValue()-position.y; 
	}
	
	@Override
	public void draw(Graphics canvas) {

		g2 = (Graphics2D) canvas;
		g2.setColor(getColor());
		g2.drawLine(position.x+x, position.y-y, position.x, position.y);	
		
	
		
	}

	
}
