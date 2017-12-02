package eg.edu.alexu.csd.oop.db.cs27.Filepro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.DataType;

import org.w3c.dom.Node;

public class Table {
	 private String tablename;
	 private int colnum ;
	 private List<Column> columns ;
	 private List<String> colnames ;
	 private List<DataType> coltypes ;
	 private File XmlFile ;
	 private File dtdFile ;
	 private boolean update;
	 private boolean delete ;
	 private boolean select ;
	 private boolean allrows;
	 private FileOperations filehandler  ;
	 private String Tbpath ;
	 private Record record ;
	 private int rowaffected ;
     public Table(String tablename , int num , List<String> names , List<DataType> types , String path , FileOperations filehandler){   	     
	 this.tablename = tablename ;
	    	     this.colnum = num ;
	    	     this.colnames = names;
	    	     this.coltypes = types;
	    	     Tbpath = path + File.separator + tablename ;

	    	     this.filehandler = filehandler ;
	             columns = new ArrayList<>();
	             record = new Record(columns);
		     System.out.println(names);
		     System.out.println();
	             filehandler.settablename(tablename);
	             filehandler.setpath(Tbpath);
	             filehandler.setcols(columns);
	             filehandler.setrecord(record);
	             filehandler.setDecritionFile();
	             filehandler.setDataFile();
	             filehandler.createDescreptionFile(names, types) ;
	             filehandler.createDataFile() ;
	             createtable();
	
     }
     
     public Table(String tnname , String currdb) throws SQLException{

		    	 this.tablename = tnname ;
		    	 update = false ; delete  = false ; select = false ; allrows = false ;
		    	 columns = new ArrayList<>();
	             record = new Record(columns);
		    	 colnum = 0 ;
		    	 Tbpath = "database"+File.separator+currdb+File.separator+tablename ;
		    	 colnames = new ArrayList<>();
		    	 coltypes = new ArrayList<>();
		    	 LoadTable();
      }
     
     public String getName(){
    	         return tablename;
     }
     
     private void createtable(){
			    	 for(int i=0;i<colnum;i++){
			    		columns.add(new Column(colnames.get(i) , coltypes.get(i) )) ;
			    	 }
			    	 record.setcols(columns);

     }
     
     
  public void Addrow(List<String> row ,  List <String> values , FileOperations filehandler ) throws SQLException{                
      if(filehandler.getClass() != this.filehandler.getClass()) throw new SQLException();
	                if(row == null) {
	                	  row = new ArrayList<>();
	                	  for(String s : colnames){
	                		  row.add(s);
	                	  }
	                }
	                rowaffected = 0 ;

    		        if(row==null || values==null) {
    		        	throw new SQLException();
    		        }

    		        //if(!validTable(filehandler)) return ; 
			        if((row.size() != colnum && colnum != 0) ||  row.size()!=values.size()){
			        	throw new SQLException();
			    	  } 	

			        if(!record.validateColnames(row)) throw new SQLException();
				

			        List<String> newrow = record.getRecord(row, values);
			        if(newrow == null) return ; 

				filehandler.addrow(newrow);
			        rowaffected = 1 ;
}

     
     
     public  List<Map<String, String>> SelectRows(List<String> col, List<String> relation, List<String> data , FileOperations filehandler)throws SQLException{
         if(filehandler.getClass() != this.filehandler.getClass()) throw new SQLException();

    	                     rowaffected = 0 ;
		    		         if(!record.validateColnames(col)) throw new SQLException();
		    		         //if(!validTable(filehandler)) throw new SQLException();
		    		           List<Map<String, String>> selected = new ArrayList<>();
					    	   filehandler.select(col, relation, data, selected);
					    	   rowaffected = selected.size();
					    	   return selected ;
		    	
     }
     
     
     public int ModifyRow(List<String> col , List<String> relation , List<String> data ,  List<String> cols , List<String> newValues ,FileOperations filehandler) throws SQLException{
                            if(filehandler.getClass() != this.filehandler.getClass()) throw new SQLException();

			    	        if(!record.validateColnames(col))  throw new SQLException();
			    	        if(!record.validateColnames(cols)) throw new SQLException();
			    	        //if(!validTable(filehandler)) return 0 ;
			    	        List<String> newrecord = record.getRecord(cols, newValues);
					    	return filehandler.modify(col, relation, data, newrecord);
     }
     
 
     public Set<String> distinct(String colname, List<String> col, List<String> relation, List<String> data , FileOperations filehandler) throws SQLException{
					    	 if(!colnames.contains(colname)){
					    		 throw new SQLException();
					        }
					    	if(!record.validateColnames(col)) throw new SQLException();
					    	//if(!validTable(filehandler)) throw new SQLException();
					        return filehandler.distinct(colname, col, relation, data) ;  					    	 
     }
     
     
     public int DeleteRow(List<String> col , List<String> relation , List<String> data , FileOperations filehandler) throws SQLException{
                                 if(filehandler.getClass() != this.filehandler.getClass()) throw new SQLException();

		    		             if(!record.validateColnames(col))  throw new SQLException();
		    		 			 //if(!validTable(filehandler)) throw new SQLException();
		    		 			 return filehandler.delete(col, relation, data);
     }
     
	 public void alterdrop(String colname , FileOperations filehandler) throws SQLException{
                                if(filehandler.getClass() != this.filehandler.getClass()) throw new SQLException();

		                        if(!colnames.contains(colname)){
		                        	throw new SQLException();
		                        }
		                        int index = colnames.indexOf(colname);
		                        colnames.remove(index); coltypes.remove(index);  columns.remove(index);   /// if record Affected !!!!!! ??
		                        filehandler.alterdrop(colname, colnames, coltypes);
	 }
	 
	 public void alteradd(String colname , DataType datatype  , FileOperations filehandler) throws SQLException{
                               if(filehandler.getClass() != this.filehandler.getClass()) throw new SQLException();

		                        if(colnames.contains(colname)){
									throw new SQLException();
						        }
								columns.add(new Column(colname , datatype));colnames.add(colname); coltypes.add(datatype);
                                filehandler.alteradd(colname, datatype.getname(), colnames, coltypes);
	 }
	 
	 
     private void LoadTable() throws SQLException{  /// in progress 
		    
					        boolean checkValid = filehandler.validate();
					    	 if(!checkValid){
					    		 throw new SQLException();
					    	 }  
					         
					    	 colnum = filehandler.loadTable(colnames , coltypes , columns);
			
     }
     
     
     public void DeleteTable(String path , FileOperations filehandler) throws SQLException{
         if(filehandler.getClass() != this.filehandler.getClass()) throw new SQLException();

    	 boolean checkValid = filehandler.validate();
    	 if(!checkValid){
    		 throw new SQLException();
    	 }  
    	  File file3 = new File(path);
    	  filehandler.deletefiles();
    	  file3.delete();
}



     
    
      public  boolean validTable(FileOperations filehandler) throws SQLException{
          if(filehandler.getClass() != this.filehandler.getClass()) throw new SQLException();

    	         return filehandler.validate();
     } 
      
      public List<Map<String , String>>  describeTable(FileOperations filehandler) throws SQLException{
          if(filehandler.getClass() != this.filehandler.getClass()) throw new SQLException();

    	  List<Map<String , String>>  lstmp = new ArrayList<>();
    	  Map<String , String> mp = new HashMap<>();
    	  for(int i=0; i<colnames.size() ; i++){
    		   mp.put(colnames.get(i), coltypes.get(i).getname());
    	  }
    	  lstmp.add(mp);
    	  return lstmp ;
      }
      
      public String getType(String colname , FileOperations filehandler) throws SQLException{
          if(filehandler.getClass() != this.filehandler.getClass()) throw new SQLException();
 
    	  if(!colnames.contains(colname)){
    		   throw new SQLException();
    	   }
    	   for(int i=0 ; i<colnames.size() ; i++){
    		    if(colnames.get(i).equals(colname)) return coltypes.get(i).getname();
    	   }
    	   return "" ;
      }
    
      public int getrowaffect(){
    	   return rowaffected ;
      }
}
