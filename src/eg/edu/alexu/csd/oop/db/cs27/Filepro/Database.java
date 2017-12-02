package eg.edu.alexu.csd.oop.db.cs27.Filepro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.DataType;
public class Database {
	
	
	  private String dbname ;
	  private List<Table> tables ;
	  private Map<String , Integer> tablenames ;
	  private boolean firstCall ;
	  private String DBpath ;
	  
	  
      public Database(String name , boolean load) throws SQLException{
    	       DBpath = "database"+File.separator+name ;
	    	   this.dbname = name ;
	    	   tables = new ArrayList<>();
	    	   tablenames = new HashMap<>();
	    	   if(load) LoadDB();
      }

      public String getName(){
    	       return dbname ;
      }
      
      public boolean TableExist(String tablename){
	    	   if(tablenames.containsKey(tablename)) return true ;
	    	   return false ;
      }
     
      public void addTable(String tbname , int colnum, List<String> colnames, List<DataType> coltypes , FileOperations filehandler){
	    	 try{
		     
			    	  tablenames.put(tbname, 1);
			    	  String path_ = DBpath+File.separator+tbname ;
			    	  Path path = Paths.get(path_);
			   	      Files.createDirectories(path);
			    	  Table newtable = new Table( tbname , colnum, colnames, coltypes ,DBpath ,  filehandler);
			    	  tables.add(newtable);
	    	 }catch(IOException e){
	    		 System.out.println(e.getMessage());
	    	 }
      }
      
      public int deleteRow(String tbname , List<String> col , List<String> relation , List<String> data ,  FileOperations filehandler) throws SQLException{
	    	   for(Table tb : tables){
	    		     if(tb.getName().equals(tbname)){
	    		    	  return tb.DeleteRow(col, relation, data ,filehandler );
	    		     }
	    	   }
	    	   return 0 ;
      }
      
      public void Addrow(String tbname, List<String> Row , List <String> values  , FileOperations filehandler) throws SQLException{
	  for(Table tb : tables){
 		     if(tb.getName().equals(tbname)){
 		    	  tb.Addrow(Row , values , filehandler);
 		    	  break;
 		     }
 	      }

      }
      
      public int modifyrow(String tbname  , List<String> col , List<String> relation , List<String> data ,  List<String> cols , List<String> newValues , FileOperations filehandler) throws SQLException{
    	  for(Table tb : tables){
  		     if(tb.getName().equals(tbname)){
  		    	  return tb.ModifyRow(col, relation, data, cols, newValues , filehandler);
  		    	 
  		     }
  	      }
    	  return 0 ;
      }
      
      public List<Map<String, String>> Select(String tbname, List<String> col, List<String> relation, List<String> data , FileOperations filehandler) throws SQLException{
    	  for(Table tb : tables){
   		     if(tb.getName().equals(tbname)){
   		    	  return tb.SelectRows(col,relation,data , filehandler);
   		     }
   	      }    
    	  return null ;
      }
      
      public void LoadDB() throws SQLException{
    	File file = new File(DBpath);
  	    String[] names = file.list();
  	    for(String name : names)
  	    {  
 
  	        if (new File(DBpath+File.separator+name).isDirectory())
  	        {
  	              Table tb = new Table(name,dbname);
  	              tables.add(tb);
  	              tablenames.put(name,1);
  	        }
  	    }
  		
      }
      
      public void DeleteTable(String tbname , FileOperations filehandler) throws SQLException{
    	       Table deleted =null;
    	       for(Table tb : tables){
    	    	    if(tb.getName().equals(tbname)){
    	    	    	 deleted = tb ;
    	    	    	 break ;
    	    	    }
    	       }
    	       tablenames.remove(tbname);
    	       tables.remove(deleted);
    	       deleted.DeleteTable(DBpath+File.separator+tbname , filehandler);
      }
      
      public void DeleteDB(String path){
    	  File tbfolder = new File(path);
    	  if(tbfolder.isDirectory()){
    		    for(File innerFile : tbfolder.listFiles() ){
    		    	DeleteDB(path+File.separator+innerFile.getName());
    		    }
    	  }
    	  tbfolder.delete();  
      }
      
     
      public boolean validTable(String tbname , FileOperations filehandler) throws SQLException{
	    	   Table validated  =null;
	    	   if(tables.isEmpty()) return false ;
		       for(Table tb : tables){
		    	    if(tb.getName().equals(tbname)){
		    	    	validated = tb ;
		    	    	 break ;
		    	    }
		       }
	    	   return validated.validTable(filehandler) ;
      }  
     
     
      
      public List<Map<String , String>> showTables(FileOperations filehandler) throws SQLException{
    	      if(tablenames.size()==0){
    	    	  throw new SQLException();
    	      }
    	      List<Map<String , String>> lstmp  = new ArrayList<>();
    	      Map<String , String> mp = new HashMap<>();
    	      for(String s : tablenames.keySet()){
    	    	  mp.put("1" , s);
    	      }
    	       lstmp.add(mp) ;
    	       return lstmp ;
   }
      
      public List<Map<String , String>> describeTable(String tbname , FileOperations filehandler) throws SQLException{
    	      if(!TableExist(tbname)) return null ;
    	      Table tb = null ;
    	      for(Table tt : tables){
    	    	  if(tt.getName().equals(tbname)) {
    	    		  tb = tt ;
    	    		  break ;
    	    	  }
    	      }
    	      
    	      return tb.describeTable(filehandler);
      }
      
      public void alterdrop(String tbname , String colname , FileOperations filehandler) throws SQLException{
    	  Table tb = null ;
	      for(Table tt : tables){
	    	  if(tt.getName().equals(tbname)) {
	    		  tb = tt ;
	    		  break ;
	    	  }
	      }
	      tb.alterdrop(colname , filehandler);
      }
      
      public void alteradd(String tbname , String colname , DataType datatype , FileOperations filehandler) throws SQLException{
    	  Table tb = null ;
	      for(Table tt : tables){
	    	  if(tt.getName().equals(tbname)) {
	    		  tb = tt ;
	    		  break ;
	    	  }
	      }
	      tb.alteradd(colname , datatype , filehandler);
      }
      
      public Set<String> distinct(String tbname , String colname, List<String> col, List<String> relation, List<String> data , FileOperations filehandler) throws SQLException{
    	  Table tb = null ;
	      for(Table tt : tables){
	    	  if(tt.getName().equals(tbname)) {
	    		  tb = tt ;
	    		  break ;
	    	  }
	      }
	      return tb.distinct(colname, col, relation, data , filehandler);
      }
      
      public String getType(String tbname, String colname , FileOperations filehandler) throws SQLException{
    	  Table tb = null ;
	      for(Table tt : tables){
	    	  if(tt.getName().equals(tbname)) {
	    		  tb = tt ;
	    		  break ;
	    	  }
	      }
    	  return tb.getType(colname , filehandler);
      }
}