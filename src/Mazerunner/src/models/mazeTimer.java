package models;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.omg.CORBA.PRIVATE_MEMBER;

import Gui.StartWindow;

public class mazeTimer implements Runnable{

	
	private  static boolean  running = true;
	private static long currentMintues = 0;
	private static long currentSeconds = 0;
	private Thread timerThread;
	
	@Override	
	public void run() {
		final long startTime = System.currentTimeMillis();
		
		while(running){
			currentSeconds = ((System.currentTimeMillis() - startTime)/1000)%60;
			currentMintues  = (((System.currentTimeMillis() - startTime)/(1000*60)))%60;
			StartWindow.lblTime.setText(currentMintues + " : " + currentSeconds);
		}
	}
	
	
	public void start(){
		timerThread = new Thread(this);
		timerThread.start();
	}
	
	
	
	

	public static int getMinutes(){
		return (int)currentMintues;
	}
	
	public static int getSeconds(){
		return (int)currentSeconds;
	}
	
	public void setTerminator(boolean running){
		this.running = running;
	}
	
	
	
}
