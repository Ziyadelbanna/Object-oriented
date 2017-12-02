package eg.edu.alexu.csd.oop.db.cs27.ParserQueries;

import java.util.ArrayList;

public class SupportedQueries{
    
    ArrayList<SingleQuery> queries;
    boolean curr;
    int currData;
    
    public SupportedQueries(){
	queries = new ArrayList<>();
	defineQueries();
	currData = -1;
	curr = false;
    }
    
    public ArrayList<SingleQuery> getQueries(){
	return queries;
    }
    
    public boolean isSupported(String x){
	
	//reset somedata
	currData = -1;
	curr = false;
	
	for(int i=0;i<queries.size();i++){
	    
	    //set the query
	    queries.get(i).setQuery(x);
	    
	    //check if the query is valid or not
	    if(queries.get(i).isValid()){
		currData = i;
		return true;
	    }
	    
	}
	return false;
    }
    
    public SingleQuery getTheQueryObject(){
	if(currData == -1)
	    throw new RuntimeException("No valid query to get the data");
	else
	    return queries.get(currData);
    }
    
    private void defineQueries(){
	queries.add(new CreateDB());
	queries.add(new Use());
	queries.add(new CreateTable());
	queries.add(new DropDB());
	queries.add(new DropTable());
	queries.add(new Update());
	queries.add(new Select());
	queries.add(new Insert());
	queries.add(new Delete());
	queries.add(new ShowTables());
	queries.add(new DescribeTable());
	queries.add(new AlterAddCol());
	queries.add(new AlterDropCol());
    }
    
    public static void main(String args[]){
	SupportedQueries x = new SupportedQueries();
	System.out.println(x.isSupported("SELECT * FROm xxx WHERE id = 5;"));
	System.out.println(x.getTheQueryObject().parseIt());
    }
}