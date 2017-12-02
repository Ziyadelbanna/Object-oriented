package eg.edu.alexu.csd.oop.db.cs27.Parser;

import java.util.ArrayList;

public interface Query{
    
    //Set the query
    public void setQuery(String query);
    
    //Get the edited query
    public String getQuery();
	    
    //is the query valid ?
    public boolean isValid();
    
    //return Array of Strings of the validated >> Query <<
    public ArrayList<String> parseIt() throws RuntimeException;
    
    //return the Query type to know wich query was validated
    public int getQueryType();
    
    //is there WHERE conditions ?
    public boolean isWhere();
    
    //is there LIMIT conditions ?
    public boolean islimit();
    
    //is there ORDER BY conditions ?
    public boolean isOrder();
    
    //return data about WHERE conditions
    public ArrayList<String> whereData();
    
    //return data about LIMIT conditions
    public ArrayList<String> LimitData();
    
    //return data about ORDER conditions
    public ArrayList<String> orderData();
}