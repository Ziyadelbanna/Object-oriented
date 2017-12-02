package eg.edu.alexu.csd.oop.db.cs27.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import eg.edu.alexu.csd.oop.db.cs27.ParserQueries.SingleQuery;
import eg.edu.alexu.csd.oop.db.cs27.ParserQueries.SupportedQueries;
import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimitData;
import eg.edu.alexu.csd.oop.db.cs27.ParserTools.TextTools;


public class ValidateQuery implements Query{
    
    private String query;   //the query String
    private int stat;   //the stat of the query //zero for not prepared //one for prepared
    private String arr[];
    private ArrayList<String> parameters;
    private int queryType;
    private int wherelocation;
    private int limitlocation;
    private int orderlocation;
    private ArrayList<String> whereList;
    private ArrayList<String> orderList;
    private ArrayList<String> limitList;
    private Error error;
    private TextTools text;
    
    
    //New Design
    private SupportedQueries supportedQueries;
    private SingleQuery queryObject;
    
    
    public ValidateQuery(){
	whereList = new ArrayList<>();
	orderList = new ArrayList<>();
	limitList = new ArrayList<>();
	queryType = -1;
	stat = 0;
	error = new Error();
	supportedQueries = new SupportedQueries();
	queryObject = null;
	text = new TextTools();
    }
    
    //set the query string
    public void setQuery(String query){
	
	//define the current state
	stat = 2;
	
	//initialize the variables
	initialize();	
	
	//remove multiple spaces or tabs
	this.query = text.trim(query.trim());
    }
    
    //get the query string
    public String getQuery(){
	return query;
    }
    
    public boolean isValid(){
	
	if(stat == 4)
	    return false;
	if(stat == 3)
	    return true;
	
	if(supportedQueries.isSupported(this.query)){
	    queryObject = supportedQueries.getTheQueryObject();
	    this.queryType = queryObject.getId();
	    
	    if(keepLooking()){
		stat = 3;
		return true;
	    }
	    
	    stat = 4;
	    return false;
	}
	    stat = 4;
	    return false;
    }
    
    private boolean keepLooking(){
	
	//keep the in-Class vlidation
	
	if(!processWhereOrderLimit())
	    return false;
	
	
	return true;
    }
    
    public ArrayList<String> parseIt() throws RuntimeException{
	if(stat == 3){
	    return queryObject.parseIt();
	}else
	    throw new RuntimeException("can't parse invalid query");
    }
    
    public int getQueryType(){
	return this.queryType;
    }
    
    public boolean isWhere(){
	    if(isValid())
		if(whereList.size() != 0)
		    return true;
	return false;
    }
    
    public boolean islimit(){
	if(this.queryType == 6)
	    if(isValid())
		if(limitList.size() != 0)
		    return true;
	return false;
    }
    
    public boolean isOrder(){
	if(this.queryType == 6)
	    if(isValid())
		if(orderList.size() != 0)
		    return true;
	return false;
    }
    
    public ArrayList<String> whereData(){
	if(isWhere())
	    return whereList;
	return null;
    }
   
    public ArrayList<String> LimitData(){
	if(islimit())
	    return limitList;
	return null;
    }
    
    public ArrayList<String> orderData(){
	if(isOrder())
	    return orderList;
	return null;
    }
    
    private boolean processWhereOrderLimit(){
	
	try{
	    
	    WhereOrderLimitData wolData = queryObject.whereOrderLimit();
	    
	    if(wolData.isError())
		return false;
	    
	    if(wolData.isWhere())
		whereList = wolData.whereData();
	    
	    if(wolData.islimit())
		limitList = wolData.LimitData();
	    
	    if(wolData.isOrder())
		orderList = wolData.orderData();
	    
	}catch(RuntimeException e){
	}
	
	return true;
    }
    
    private void initialize(){
	this.parameters = new ArrayList<String>();
	this.queryType = -1;
	this.error = new Error();
	this.stat = this.stat >1 ?1:0;
	this.limitList = new ArrayList<String>();
	this.orderList = new ArrayList<String>();
	this.whereList = new ArrayList<>();
	supportedQueries = new SupportedQueries();
	queryObject = null;
    }

    public static void main(String args[]){
	ValidateQuery x = new ValidateQuery();
	x.setQuery("ALTER TABLE table_name13 ADD COLUMN column_name4 date");
	//x.setQuery("ALTER TABLE Xxx DROP column yyy;");
	System.out.println(x.isValid());
	System.out.println(x.parseIt());
	System.out.println(x.islimit());
	System.out.println(x.isWhere());
	System.out.println(x.whereData());
    }
}
