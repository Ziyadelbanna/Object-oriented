package eg.edu.alexu.csd.oop.jdbc.cs27;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.io.File;
import java.lang.*;
import java.util.logging.Logger;

public class MyDriver implements java.sql.Driver{	
	private boolean approve=false;
	public boolean acceptsURL(String url) throws SQLException {
		
		if(url.equals("jdbc:xmldb://localhost")){
			approve=true;
			return true;
			
		}

		return false;
	}
	
	public Connection connect(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		
		//if(approve){
			File file = (File) info.get("path");
			//MyConnection con = (MyConnection) connectionManager.getConnection(file.getAbsolutePath());
			ConnectionImp con =new ConnectionImp();
			//MyStatement.DB.dir = info.getProperty("path");
			
			con.db.dir = file.getAbsolutePath();
			con.db.first = false;				
		                                                                      //--->	con.db.createDatabase("database" , false);
			//throw new SQLException(url +"\n" + info);
			return con;
		/*}else{
			throw null;
		}*/
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		
		Driver thisDrive = DriverManager.getDriver(url);
		
		DriverPropertyInfo[] x = thisDrive.getPropertyInfo(url, info);
		return x; 
	}
	/** ******************************************END*****************************************************************************/

	/*@Override
	public boolean acceptsURL(String url) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}*/

	/*@Override
	public Connection connect(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/*@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return null;
		// TODO Auto-generated method stub

	}*/

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	

}