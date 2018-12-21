package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Triangle extends DrawShape {

	private int x2,y2,x1,y1 = 0;
	Graphics2D g2;
	public Triangle ()
	{
		properties = new HashMap<String, Double>();
		properties.put("y1", 0.0);
		properties.put("x1", 0.0);
		properties.put("y2", 0.0);
		properties.put("x2", 0.0);
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {

		this.properties.put("x1", properties.get("x1"));
		this.x1= properties.get("x1").intValue();
		this.properties.put("x2", properties.get("x2"));
		this.x2= properties.get("x2").intValue();
		this.properties.put("y1", properties.get("y1"));
		this.y1= properties.get("y1").intValue();
		this.properties.put("y2", properties.get("y2"));
		this.y2= properties.get("y2").intValue();
	}
	
	@Override
	public void draw(Graphics canvas) {

		g2 = (Graphics2D) canvas;
		int[] xt = { position.x, x1, x2 };
		int[] yt = { position.y, y1, y2 };
		g2.setColor(fillcolor);
		g2.fillPolygon(xt, yt, 3);
		g2.setColor(bordercolor);
		g2.drawPolygon(xt, yt, 3);
		
	
		
	}

	
}
