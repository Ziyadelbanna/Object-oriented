package eg.edu.alexu.csd.oop.db.cs27.Filepro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Record {
      private List<Column> cols ;
      public Record(List<Column> cols){
    	   this.cols = cols ;
      }
      public void setcols(List<Column> cols){
    	  this.cols = cols ;
      }
      public List<Column> getcols(){
    	  return cols ;
      }
      public boolean validateColnames(List<String> colnames) throws SQLException{
    	     if(colnames==null) return true ;
    	     for(String name : colnames){
    	    	   if(name == null ){ 
    	    		   throw new SQLException();
    	    	   }
    	    	   int flag = 0 ;
    	    	   for(Column col : cols){
    	    		     if(col.getname().equals(name)){
    	    		    	 flag = 1;
    	    		    	 break ;
    	    		     }
    	    	   }
    	    	   
    	    	   if(flag == 0){
    	    		   throw new SQLException();
    	    	   }
    	     }
    	     return true ;
      }
      
      public boolean validateRecord(List<String> newrow) throws SQLException{
    	   if(newrow.size() != cols.size()) throw new SQLException();
    	   for(int i=0 ; i<cols.size() ; i++){
    		    if(!cols.get(i).getdatatype().validate(newrow.get(i))) throw new SQLException();
    	   }
    	   return true ;
      }
      
      public List<String> getRecord(List<String> colnames , List<String> colvalues) throws SQLException{
    	   List<String> newRecord = new ArrayList<>();
    	   for(Column col : cols){
    		   String colName = col.getname();
    		   if(!colnames.contains(colName)) newRecord.add("Null");
    		   else{
    			   String value = colvalues.get(colnames.indexOf(colName)) ;
    			   if(!col.getdatatype().validate(value)){
    				   throw new SQLException();
    			   }
    			   newRecord.add(value);   
    		   }
    	   }
    	   return newRecord ;
      }
      
      
      
      
      public List<String> extractRecord(Node currnode){
    	  List<String> newRecord = new ArrayList<>();
    	  Element currElement = (Element) currnode ;
    	  for(Column col : cols){
    		   String colName = col.getname();
    		   newRecord.add(currElement.getElementsByTagName(colName).item(0).getTextContent().toString());
    	  }
    	  return newRecord ;
      }
      
      public boolean compareRecord(List<String> record , List<String> colnames , List<String> relation , List<String> values){
    	  if(colnames==null && relation == null && values == null) return true ;
    	  boolean flag = true ;  
    	  for(int i=0; i<cols.size() ; i++){
    	    	 String name = cols.get(i).getname();
    	    	 if(colnames.contains(name)){
    	    		 int index = colnames.indexOf(name);
    	    		 flag &= cols.get(i).getdatatype().compare(record.get(i), values.get(index) , relation.get(index)) ;
    	    	 }			 
    	     }
    	  return flag ;
      }
      
      public List<String> updateRecord(List<String> oldrecord , List<String> newrecord){
    	     for(int i=0 ; i< oldrecord.size() ; i++){
    	    	  if(!newrecord.get(i).equals("null") && !newrecord.get(i).equals("Null")){
    	    		  oldrecord.set(i, newrecord.get(i));
    	    	  }
    	     }
    	     return oldrecord ;
      }
      
      
      public Map<String , String> recordtomap(List<String> record){
    	    Map<String , String> mp = new HashMap<>();
    	    for(int i=0;i<cols.size() ; i++){
    	    	mp.put(cols.get(i).getname(), record.get(i));
    	    }
    	    return mp ;
      }
}
