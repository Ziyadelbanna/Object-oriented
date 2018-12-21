package models;

import javax.swing.ImageIcon;

public abstract class AbstractFactory {
	
	public abstract ImageIcon getImage(String imageType);
	public abstract Object getModel(String modelType);

}
