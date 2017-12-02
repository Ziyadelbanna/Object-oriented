package eg.edu.alexu.csd.oop.jdbc.cs27;



import java.io.File;

import java.sql.Connection;

import java.sql.Driver;

import java.sql.DriverPropertyInfo;

import java.sql.SQLException;

import java.sql.SQLFeatureNotSupportedException;

import java.util.Properties;

import java.util.logging.Logger;





public class DriverImpl implements Driver {

	

	public DriverImpl(){

		//OnlineDebuger.logln("=====================================", false);

	//	OnlineDebuger.logln("new DriverImpl", false);

	}

	

	@Override

	public boolean acceptsURL(String url) throws SQLException {

		return true;

	}



	@Override

	public Connection connect(String url, Properties info) throws SQLException {

		File dir = (File) info.get("path");

		if (acceptsURL(url) && dir != null){

			return new ConnectionImp(dir.getAbsolutePath());

		} else 

			return null;

	}

	

	@Override

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {

		DriverPropertyInfo[] retArr = new DriverPropertyInfo[1];

		retArr[0] = new DriverPropertyInfo("path", info.getProperty("path"));

		return retArr;

	}

	

	@Override

	public int getMajorVersion() {

		throw new UnsupportedOperationException();

	}



	@Override

	public int getMinorVersion() {

		throw new UnsupportedOperationException();

	}



	@Override

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {

		throw new UnsupportedOperationException();

	}



	@Override

	public boolean jdbcCompliant() {

		throw new UnsupportedOperationException();

	}



}