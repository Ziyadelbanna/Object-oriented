package eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional;

import java.util.ArrayList;

public interface WhereOrderLimitData{
    
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

    public boolean isError();
}