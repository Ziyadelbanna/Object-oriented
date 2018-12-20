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

	private int width = 0;
	Graphics2D g2;
	public Square ()
	{
		properties = new HashMap<String, Double>();
		properties.put("width", 0.0);
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {

		this.properties.put("width", properties.get("width"));
		this.width= properties.get("width").intValue(); 
	}
	
	@Override
	public void draw(Graphics canvas) {

		g2 = (Graphics2D) canvas;
		g2.setColor(getFillColor());
		g2.fillRect(position.x, position.y, width,width);
		g2.setColor(getColor());
		g2.drawRect(position.x, position.y,width,width);
	
	
		
	}
}
