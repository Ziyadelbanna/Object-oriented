package models;

import javax.swing.ImageIcon;

public class FactoryOfModels extends AbstractFactory {
	
	public  Object getModel(String modelType) {
		if(modelType.equalsIgnoreCase("HUMAN")) {
			return Human.getInstance();
		}
		if(modelType.equalsIgnoreCase("GIFTOFBULLET")) {
			return GiftOfBullets.getInstance();
		}
		if(modelType.equalsIgnoreCase("GIFTOFHEALTH")) {
			return GiftOfHealth.getInstance();
		}
		if(modelType.equalsIgnoreCase("GATE")) {
			return Gate.getInstance();
		}
		if(modelType.equalsIgnoreCase("ROAD")) {
			return Road.getInstance();
		}
		if(modelType.equalsIgnoreCase("BULLET")) {
			return Bullet.getInstance();
		}
		if(modelType.equalsIgnoreCase("STONEWALL")) {
			return StoneWall.getInstance();
		}
		if(modelType.equalsIgnoreCase("TREEWALL")) {
			return TreeWall.getInstance();
		}
		if(modelType.equalsIgnoreCase("GREENBOMB")) {
			return GreenBomb.getInstance();
		}
		if(modelType.equalsIgnoreCase("EXPLOSION")) {
			return explosion.getInstance();
		}
		if(modelType.equalsIgnoreCase("REDBOMB")) {
			return RedBomb.getInstance();
		}
		else return null;
		
	}

	@Override
	public ImageIcon getImage(String imageType) {
		return null;
	}

}
