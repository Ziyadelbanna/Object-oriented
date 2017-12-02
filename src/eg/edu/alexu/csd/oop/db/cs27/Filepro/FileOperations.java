package eg.edu.alexu.csd.oop.db.cs27.Filepro;

import java.io.File;
import java.util.List;

import eg.edu.alexu.csd.oop.db.FileHandler;

public abstract class FileOperations implements FileHandler {

	    public  File descriptionFile ;
	    public  File dataFile ;
        public String path ;
        public  List<Column> cols ;
        public String tablename ;
        public Record record ;
	    public FileOperations(){
	    	
	    }
	    

	    public void setrecord(Record record){
	    	 this.record = record ;
	    }
	    public abstract void setDecritionFile();
	    public File getDecritionFile(){
	    	  return descriptionFile ;
	    }
	    
	    public abstract void setDataFile();
	    public  File getDataFile(){
	    	  return dataFile ;
	    }

        public void setpath(String path){
        	this.path = path ;
        }
        
        public String getpath(){
        	return this.path ;
        }
        
        public void settablename(String name){
        	this.tablename  = name;
        }
        public String gettablename(){
        	return this.tablename ;
        }
        
        public void setcols(List<Column> cols){
        	this.cols = cols ;
        }
}
