package practise;

import java.awt.image.BufferedImage;

public class main{
	public static void main(String[] args) {
		BufferedImage circleImg = new BufferedImage(90, 90, BufferedImage.TYPE_INT_ARGB);
		
		System.out.println(circleImg.getClass().equals(BufferedImage.class));
	}
	
}