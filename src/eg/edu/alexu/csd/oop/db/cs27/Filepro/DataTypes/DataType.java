package eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes;

public abstract class DataType {
     private String name ;
     public DataType(){
    	   
     }
     
     public void setname(String name){
    	 this.name = name ;
     }
     public String getname(){
    	 return this.name; 
     }   
     public abstract boolean validate(String datatype);
     public abstract boolean compare(String data1 , String data2 , String cmp);
     public abstract Object getValue(String val);
     
}

