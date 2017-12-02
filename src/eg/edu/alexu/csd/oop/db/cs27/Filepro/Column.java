package eg.edu.alexu.csd.oop.db.cs27.Filepro;

import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.DataType;

public class Column {
         private String name ;
         private DataType datatype; 
         private Column(){
        	 
         }
         
       public Column(String name , DataType datatype){
        	 setname(name) ;
        	 setdatatype(datatype);
         }
         
       public void setname(String name){
        	  this.name = name ;
         }
       public String getname(){
       	  return  this.name ;
        }
         
       public void setdatatype(DataType datatype){
       	  this.datatype = datatype ;
         }
        
       public DataType getdatatype(){
      	  return  this.datatype ;
        }
}
