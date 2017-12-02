package eg.edu.alexu.csd.oop.db.cs27.ParserTypes;

import java.util.ArrayList;


public class SupportedTypes{
    
    ArrayList<SingleType> types;
    
    public SupportedTypes(){
	types = new ArrayList<>();
	defineTypes();
    }
    
    public ArrayList<SingleType> getTypes(){
	return types;
    }
    
    public boolean isSupported(String x){
	for(int i=0;i<types.size();i++)
	    if(types.get(i).validateTypeName(x))
		return true;
	return false;
    }
    
    private void defineTypes(){
	types.add(new SingleType("int"));
	types.add(new SingleType("varchar"));
	types.add(new SingleType("date"));
	types.add(new SingleType("float"));
    }
}

//ALTER TABLE xxx ADD colnam coldatatype
//ALTER TABLE xxx DROP COLUMN colnam
//SELECT DISTINCT colname FROM xxx where order limit