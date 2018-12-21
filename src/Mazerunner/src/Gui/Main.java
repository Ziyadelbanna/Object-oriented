package Gui;
import org.apache.log4j.*;
public class Main {

	   
	public static final Logger log = Logger.getLogger(Main.class);
	public static final Logger logging = Logger.getLogger(ArrayDraw.class);
	
	public static void main(String[] args) {   
		// TODO Auto-generated method stub
		StartWindow s = new StartWindow();
		s.mainGui();
//		log.setLevel(Level.DEBUG);
		log.debug("Debug Message");
		log.info("Info Message");
		log.warn("Warn Message");
		log.error("Error Message");
		log.fatal("Fatal Message");
		
		logging.debug("message");
		
	}

}
