package eg.edu.alexu.csd.oop.db.cs27.ParserTools;


public class Error{
    int error = 0;
    
    public Error(){
	
    }
    
    //report single problem to the console or return it later
    public void reportError(String x){
	error = 1;
	System.out.println(x);
    }
    
    public void reportError(){
	error = 1;
    }
    
    //return if there is error in occurs or not
    public boolean isError(){
	if(error == 0)
	    return false;
	return true;
    }
    
    public boolean isValid(){
	if(error == 0)
	    return true;
	return false;
    }
}