package eg.edu.alexu.csd.oop.draw;

import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name = "shape")
@XmlAccessorType (XmlAccessType.FIELD)

public interface Shape {
	

	public void setPosition(java.awt.Point position);

	public java.awt.Point getPosition();

	/* update shape specific properties (e.g., radius) */
	public void setProperties(java.util.Map<String, Double> properties);

	public java.util.Map<String, Double> getProperties();

	public void setColor(java.awt.Color color);

	public java.awt.Color getColor();

	public void setFillColor(java.awt.Color color);

	public java.awt.Color getFillColor();

	/* redraw the shape on the canvas */
	public void draw(java.awt.Graphics canvas);

	/* create a deep clone of the shape */
	public Object clone() throws CloneNotSupportedException;
}
