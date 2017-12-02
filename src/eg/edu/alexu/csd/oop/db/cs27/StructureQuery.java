package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.io.IOException;

import java.sql.SQLException;

import java.util.LinkedList;

import java.util.regex.Matcher;

import java.util.regex.Pattern;



public class StructureQuery {


	private Validator valid=new Validator();

	public boolean excute(String query,String currentdata) throws SQLException{

		if (valid.isCreateDatabase(query)) {

			Pattern p = Pattern.compile("([a-z0-9A-Z-_]{1,})$");

			Matcher m = p.matcher(query);

			m.find();

			String filename = m.group().trim();

			File newfolder = new File( filename);

			if (newfolder.exists() == false) {

				if (newfolder.mkdir() == false) {

					return false;

				}

			}

			currentdata=filename;

			return true;

		} else if (valid.isDropDatabase(query)) {

			Pattern p = Pattern.compile("([a-z0-9A-Z-_]{1,})$");

			Matcher m = p.matcher(query);

			m.find();

			String filename = m.group().trim();

			File newfolder = new File( filename);

			if (newfolder.exists()) {

				String[] entries = newfolder.list();

				for (String a : entries) {

					File curfile = new File(newfolder.getPath(), a);

					curfile.delete();

				}

				newfolder.delete();

			}

			else return false;

			return true;

		} else if (valid.isCreateTable(query)) {

			if(currentdata==null)

				throw new SQLException();

			Pattern p = Pattern.compile("([a-z0-9A-Z-_]{1,}\\s+(varchar|int))");

			Matcher m = p.matcher(query);

			LinkedList<String> arr = new LinkedList<String>();

			while (m.find())

				arr.add(m.group().trim());

			String[] type = new String[arr.size()];

			String[] names = new String[arr.size()];

			int idx = 0;

			for (String a : arr) {

				Pattern p2 = Pattern.compile("([a-z0-9A-Z-_]{1,} )");

				Matcher m2 = p2.matcher(a);

				Pattern p3 = Pattern.compile("(varchar|int)");

				Matcher m3 = p3.matcher(a);

				m2.find();

				m3.find();

				type[idx] = m3.group().trim();

				names[idx++] = m2.group().trim();

			}

			Pattern p1=Pattern.compile("create table ([a-z0-9A-Z-_]{1,})");

			Matcher m1=p1.matcher(query);

			m1.find();

			String tablename=m1.group().trim();

			p1=Pattern.compile("([a-z0-9A-Z-_]{1,})$");

			m1=p1.matcher(tablename);

			m1.find();

			tablename=m1.group().trim();

			History history = new History(arr.size());

			File file=new File(currentdata+"\\"+tablename+".xml");

			if(file.exists()==false){

				try {

					file.createNewFile();

				} catch (IOException e) {

					// TODO Auto-generated catch block

					return false;

				}

			}

			else return false;

			history.setNames(names);

			history.setTypes(type);

			return true;

		} else if (valid.isDropTable(query)) {

			if(currentdata==null)

				throw new SQLException();

			Pattern p1=Pattern.compile("([a-z0-9A-Z-_]{1,})$");

			Matcher m1=p1.matcher(query);

			m1.find();

			String tablename=m1.group().trim();

			File file=new File(currentdata+"\\"+tablename+".xml");

			if(file.exists()){

				file.delete();

			}

			else return false;

			return true;

			

		} else {

			throw new SQLException();

		}

	}

	

}