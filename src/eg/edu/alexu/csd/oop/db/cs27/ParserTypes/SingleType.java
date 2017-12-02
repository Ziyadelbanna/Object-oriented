package eg.edu.alexu.csd.oop.db.cs27.ParserTypes;

public class SingleType{
    
    String Type;
    
    public SingleType(){
    }
    
    public SingleType(String x){
	this.Type = x.toLowerCase().trim();
    }
    
    public void setTypeName(String x){
	this.Type = x.toLowerCase().trim();

    }
    
    public boolean validateTypeName(String x){
	if(x.toLowerCase().trim().equals(this.Type))
	    return true;
	return false;
    }
}