package eg.edu.alexu.csd.oop.db.cs27.main;

import java.io.File;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.jdbc.cs27.ConnctionData;
import eg.edu.alexu.csd.oop.jdbc.cs27.DbmsStatement;
import eg.edu.alexu.csd.oop.jdbc.cs27.DriverData;

public class Runner{
    
    private static String tmp = System.getProperty("java.io.tmpdir");
    
    public Runner(){
	
    }
    
    public static void main() throws SQLException{
	
	DriverData runTime = new DriverData();
	runTime.acceptsURL("jdbc:xmldb://localhost");
	Properties info = new Properties();
	
	File dbDir = new File(tmp + File.separator + "jdbc" + File.separator + Math.round((((float) Math.random()) * 100000)));
	info.put("path", dbDir.getAbsoluteFile());
	ConnctionData conn = (ConnctionData) runTime.connect("jdbc:xmldb://localhost", info);
	DbmsStatement run = (DbmsStatement) conn.createStatement();
	
	while(true){
	    
	    Scanner x = new Scanner(System.in);
	    String query = x.nextLine();
	    run.execute(query);
	}
    }
}