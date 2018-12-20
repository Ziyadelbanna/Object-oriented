package eg.edu.alexu.csd.oop.draw.cs27;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

import eg.edu.alexu.csd.oop.draw.Shape;

@XmlRootElement(name ="CurrentShapes")
@XmlAccessorType (XmlAccessType.FIELD)

public class Shapes {

	@XmlElement(name ="shape")
	private List<DrawShape>shapes = null;
	
	public List <DrawShape> getshapes()
	{
		return shapes;
	}
	public void setshapes (List <DrawShape> shapes)
	{
		this.shapes = shapes;
	}
}