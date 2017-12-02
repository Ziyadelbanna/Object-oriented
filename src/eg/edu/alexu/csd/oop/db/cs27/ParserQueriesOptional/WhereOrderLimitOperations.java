package eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional;

import java.util.HashMap;

public interface WhereOrderLimitOperations{
    
	//set the query you want to get data from
        public void SetQuery(String query);
	
	//execute the data providing the starting position
	public void execute(int pos);
	
	//give it the hashed map
	public void setHash(HashMap<String,String> x);

}