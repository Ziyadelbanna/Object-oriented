package models;

import javax.swing.ImageIcon;

public class FactoryOfImages extends AbstractFactory {
	public  ImageIcon getImage(String imageType) {
		
		if(imageType.equalsIgnoreCase("GIFT")) {
			return new ImageIcon("res/Gift-icon.png");
		}
		if(imageType.equalsIgnoreCase("gun")) {
			return new ImageIcon("res/Gun-icon.png");
		}
		if(imageType.equalsIgnoreCase("Door")) {
			return new ImageIcon("res/door-icon.png");
		}
		if(imageType.equalsIgnoreCase("rightman")) {
			return new ImageIcon("res/Man-icon.png");
		}
		if(imageType.equalsIgnoreCase("leftman")) {
			return new ImageIcon("res/left-icon.png");
		}
		if(imageType.equalsIgnoreCase("upman")) {
			return new ImageIcon("res/up-icon.png");
		}
		if(imageType.equalsIgnoreCase("downman")) {
			return new ImageIcon("res/down-icon.png");
		}
		if(imageType.equalsIgnoreCase("Brick")) {
			return new ImageIcon("res/brick-wall-icon.png");
		}
		if(imageType.equalsIgnoreCase("TREE")) {
			return new ImageIcon("res/Tree-icon.png");
		}
		if(imageType.equalsIgnoreCase("greenBomb")) {
			return new ImageIcon("res/Bomb-icon.png");
		}
		if(imageType.equalsIgnoreCase("REDBOMB")) {
			return new ImageIcon("res/TNT-icon.png");
		}
		else return null;
		
	}

	@Override
	public Object getModel(String modelType) {
		return null;
	}
}
