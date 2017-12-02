package eg.edu.alexu.csd.oop.jdbc.cs27;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.db.cs27.join.tester;

public class DriverData implements java.sql.Driver {

	int XmlJson = -1;

	@Override
	public boolean acceptsURL(String url) throws SQLException {

		// String []temp=url.split(":");
		// temp[0]=temp[0]+":";
		// temp[2]=":"+temp[2];
		// if (temp.length>2||url.contains(" ")){return false;}
		// if
		// (temp[0].equals("jdbc:")&&(temp[1].equals("xmldb")||temp[1].equals("altdb"))
		// &&temp[2].equals("://localhost")){
		// return true;
		// }
		if (url.equals("jdbc:xmldb://localhost")) {
			XmlJson = 1;
			return true;
		} else if (url.equals("jdbc:altdb://localhost")) {
			XmlJson = 2;
			return true;
		} else
			return false;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		if (acceptsURL(url)) {
			String path = info.get("path").toString();
			tester x = new tester();
			if (XmlJson == 1)
				x.setFileType("xml");
			else
				x.setFileType("json");

			ConnctionData coData = new ConnctionData(path, x);
			return coData;
		}
		return null;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}
}
