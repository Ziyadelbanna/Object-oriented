package eg.edu.alexu.csd.oop.db.cs27;

import java.io.File;
import java.io.FileNotFoundException;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs27.parse.Adapter;
import eg.edu.alexu.csd.oop.db.cs27.parse.ParserFilter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class MyDatabase implements Database {
	// MainController control = new MainController();
	public static Map<String, String> tables = new HashMap<>();
	static ParserFilter parser = new ParserFilter();
	static Adapter ad = new Adapter();
	public static StringBuilder cd = new StringBuilder();
	public String currentDB = null;
	public Map<String, Object> str = new HashMap<String, Object>();
	public boolean first = true;
	public String dir = null;

	public Object getOutput(String quary) throws SQLException {
		// throw new RuntimeException(quary);
		cd.append(quary + "\n");
		if (first) {
			first = false;
			dir =System.getProperty("user.dir")+File.separator+"Databases";
		}
		if (!quary.contains(";")) {
			quary = quary.concat(";");
		}
		quary = quary.toLowerCase();
		str = parser.getInfo(quary);
		str.put("DBName", currentDB);
		if (((String) str.get("Operation")).equals("CreateDB") || ((String) str.get("Operation")).equals("DropDB")) {
			String temp = (String) str.get("DataBaseName");
			temp = dir + File.separator+ temp;
			str.put("DataBaseName", temp);
		}
		Object output = null;
		try {
			output = ad.getOutput(str);
		} catch (FileNotFoundException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (((String) str.get("Operation")).equals("CreateDB")) {
			currentDB = (String) output;
		}
		////////////////
		if (((String) str.get("Operation")).equals("DropDB")) {
			currentDB = null;
		}
		////////////////////
		return output;
	}

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		databaseName = databaseName.toLowerCase();
		if (first) {
			first = false;
			File db = new File("databases");
			dir = db.getAbsolutePath();
		}
		File db = new File(dir + System.getProperty("file.separator") + databaseName);
		if (db.exists() && db.isDirectory()) {
			if (dropIfExists) {
				for (File f : db.listFiles()) {
					f.delete();
				}
			}
		}
		// dtd.saveDTD(database+"\\"+name);
		db.mkdirs();
		currentDB = db.getAbsolutePath();
		str.put("DBName", db.getAbsolutePath());
		// throw new RuntimeException(db.getAbsolutePath().concat("\\"));
		/*
		 * String q = "create database "; q += databaseName; q += ";";
		 * 
		 * try { executeStructureQuery(q); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		return db.getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {

		// TODO Auto-generated method stub
		try {
			getOutput(query);
			return true;
		} catch (IndexOutOfBoundsException e) {
			//throw null;
			throw new SQLException();
		} catch (NullPointerException e) {
			return false;
			// throw null;
		}

		// return true;

		// return false;
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		// TODO Auto-generated method stub

		return (Object[][]) getOutput(query);
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		// TODO Auto-generated method stub
		return (int) getOutput(query);
	}
	
	public ArrayList<String> getColumnsNames()
	{
		ArrayList<String> result= new ArrayList<>();
		try {
			InputStream inputStream;
			Reader reader = null;
			try {
				inputStream = new FileInputStream(str.get("DBName")+File.separator+str.get("TableName") + ".xml");
				try {
					reader = new InputStreamReader(inputStream, "ISO-8859-1");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			InputSource is = new InputSource(reader);
			is.setEncoding("ISO-8859-1");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.parse(is);

			// System.out.println("khk"+MyDatabase.tables.get(name));
			NodeList list = doc.getElementsByTagName("row");
			Element dummy = (Element) list.item(0);
			
			NodeList children= dummy.getChildNodes();
			for(int i=0;i<children.getLength();i++)
			{
				if(children.item(i).getNodeType() == Node.ELEMENT_NODE)
				{
					Element col = (Element) children.item(i);
					
					result.add(col.getTextContent());
				}
					
			}
		}
		catch (Exception e)
		{
			throw null;
		}
		return result;
	}

}