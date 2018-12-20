package practise;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Base extends JPanel {
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.setColor(Color.RED);
		int[] xPoints={100,200,300};
		int[] yPoints={100,100,300};
		((Graphics2D)g).drawPolygon(xPoints, yPoints, 3);
		g.drawArc(0, 0, 100, 50, 0, 360);
	}
	
	
}