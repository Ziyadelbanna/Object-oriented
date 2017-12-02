package eg.edu.alexu.csd.oop.db.cs27.Filepro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.Dbms;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.DataType;
public class Controller implements Dbms{
   
	private Map<String,Integer> dbnames ;
	private List<Database> databases ;
	
	public Controller() throws SQLException {
				dbnames = new HashMap<>();
				databases = new ArrayList<>();
				 //LoadAllDB();
	}
	
	
	public void LoadAllDB() throws SQLException{
				File file = new File("database");
			    String[] names = file.list();
			    for(String name : names)
			    {    
			    
			        if (new File("database"+File.separator + name).isDirectory())
						try {
							{
							    Database db = new Database(name , true);
							    databases.add(db);
							    dbnames.put(name,1);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    }
	}
	
	@Override
	public void CreateDB(String dbname) throws SQLException{
		         dbname = dbname.toLowerCase() ;
				
							 if(dbnames.containsKey(dbname)){
								  throw new SQLException();
							  }
							  Path path = Paths.get("database"+File.separator+dbname);
					          try {
								Files.createDirectories(path);
							} catch (IOException e) {
								throw new SQLException();
							}		
							  Database Database = new Database(dbname , false);
							  databases.add(Database);
							  dbnames.put(dbname, 1);
		   	      
	}

	@Override
	public void CreateTable(String tbname, String dbname, int colnum, List<String> colnames, List<DataType> coltypes , FileOperations filehandler)throws SQLException{
				
	    tbname = tbname.toLowerCase();
				dbname = dbname.toLowerCase();
		        Database db = getDb(dbname);
				if(db == null ) return ;
			    if(db.TableExist(tbname)){
			    	   throw new SQLException("Table existed");
			    } 
			    
		        db.addTable(tbname, colnum, colnames, coltypes , filehandler);
	}

	
	@Override
	public void AddRow(String tbname, String dbname, List<String> Row , List <String> values , FileOperations filehandler)  throws SQLException{
		tbname = tbname.toLowerCase();
		dbname = dbname.toLowerCase();
				Database db = getDb(dbname);
				if(db == null ) return ;
			    if(!db.TableExist(tbname)){
			    	throw new SQLException();
			    }     
				db.Addrow(tbname, Row , values , filehandler);

	}

	
	@Override
	public int ModifyRow(String tbname, String dbname, List<String> col , List<String> relation , List<String> data , List<String> cols , List<String> newValues , FileOperations filehandler) throws SQLException{
		tbname = tbname.toLowerCase();
		dbname = dbname.toLowerCase();
				Database db = getDb(dbname);
				if(db == null ) return 0;
			    if(!db.TableExist(tbname)){
			    	throw new SQLException();    
				} 
				return db.modifyrow(tbname, col, relation, data, cols , newValues , filehandler);
	}

	@Override
	public int DeleteRow(String tbname, String dbname,List<String> col , List<String> relation , List<String> data , FileOperations filehandler)throws SQLException{
		tbname = tbname.toLowerCase();
		dbname = dbname.toLowerCase();
		       Database db = getDb(dbname);
			   if(db == null ) return 0;
		       if(!db.TableExist(tbname)){
		    	   throw new SQLException();
		       } 
		     return   db.deleteRow(tbname, col, relation, data ,filehandler);
	}
	
	@Override
	public List<Map<String, String>> Select(String tbname, String dbname, List<String> col, List<String> relation,List<String> data , FileOperations filehandler) throws SQLException{
		tbname = tbname.toLowerCase();
		dbname = dbname.toLowerCase();		
		Database db = getDb(dbname);
				if(db == null) return null;
				if(!db.TableExist(tbname)){
			    	throw new SQLException();
			     } 
				return db.Select(tbname , col,relation,  data , filehandler);
	}
	
	@Override
	public void DropDB(String dbname) throws SQLException{
		
		dbname = dbname.toLowerCase();		
		Database db = getDb(dbname);
				if(db==null) return ;
				dbnames.remove(dbname);
				databases.remove(db);
				db.DeleteDB("database"+File.separator+dbname);
	}


	@Override
	public void DropTable(String dbname, String tbname , FileOperations filehandler) throws SQLException{
		tbname = tbname.toLowerCase();
		dbname = dbname.toLowerCase();
				Database db = getDb(dbname);
				if(db==null) return ;
			    if(!db.TableExist(tbname)){
			    	throw new SQLException();
			     } 
				db.DeleteTable(tbname , filehandler);
	}
	
	 @Override
	public boolean validateTable(String dbname , String tbname , FileOperations filehandler) throws SQLException{
		    tbname = tbname.toLowerCase();
			dbname = dbname.toLowerCase();
				Database db = getDb(dbname);
				if(db==null) return false;
			    if(!db.TableExist(tbname)){
			    	   System.out.println("this table doesn't Exist in " + dbname);
			    	   return false;
			     } 
			    return db.validTable(tbname , filehandler) ;
	}  
	
	private Database getDb(String dbname) throws SQLException{
		
		dbname = dbname.toLowerCase();
			    for(Database DB : databases){
			    	  if(DB.getName().equals(dbname)){
			    		   return DB ;
			    	  }
			    }
			    throw new SQLException();
	}


	@Override
	public List<Map<String, String>> showTables(String dbname , FileOperations filehandler) throws SQLException{
		dbname = dbname.toLowerCase();
		Database db = getDb(dbname);
		if(db==null) 
	    	throw new SQLException();
		return db.showTables(filehandler);
	}


	@Override
	public List<Map<String, String>> describeTable(String dbname, String tbname , FileOperations filehandler) throws SQLException{
		tbname = tbname.toLowerCase();
		dbname = dbname.toLowerCase();
		Database db = getDb(dbname);
		if(db==null) throw new SQLException();
		return db.describeTable(tbname,filehandler);
	}


	@Override
	public void AlterDrop(String tbname, String dbname, String colname , FileOperations filehandler) throws SQLException{
		tbname = tbname.toLowerCase();
		dbname = dbname.toLowerCase();
		Database db = getDb(dbname);
		if(db==null) return;
		if(!db.TableExist(tbname)){
			throw new SQLException();
	     } 
		db.alterdrop( tbname ,  colname , filehandler);
	}

	@Override
	public void AlterAdd(String tbname, String dbname, String colname , DataType datatype , FileOperations filehandler) throws SQLException{
		tbname = tbname.toLowerCase();
		dbname = dbname.toLowerCase();
		Database db = getDb(dbname);
		if(db==null) return;
		if(!db.TableExist(tbname)){
			throw new SQLException();
	     } 
		db.alteradd( tbname ,  colname , datatype , filehandler);
	}


	@Override
	public Set<String> Distinct(String tbname, String dbname, String colname, List<String> col, List<String> relation,
			List<String> data , FileOperations filehandler) throws SQLException{
		
		tbname = tbname.toLowerCase();
		dbname = dbname.toLowerCase();
		Database db = getDb(dbname);
		if(db==null) return null ;
		if(!db.TableExist(tbname)){
			throw new SQLException();
	     } 
		 return db.distinct(tbname, colname, col, relation, data , filehandler);
	}


	@Override
	public String getType(String dbname, String tbname, String colname , FileOperations filehandler) throws SQLException{
		tbname = tbname.toLowerCase();
		dbname = dbname.toLowerCase();
		Database db = getDb(dbname);
		if(db==null) return null ;
		if(!db.TableExist(tbname)){
			throw new SQLException();
	     } 
		return db.getType( tbname, colname , filehandler);
	}
	
	
}
