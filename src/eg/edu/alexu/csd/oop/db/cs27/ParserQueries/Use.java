package eg.edu.alexu.csd.oop.db.cs27.ParserQueries;

import java.util.ArrayList;
import java.util.HashMap;

import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimit;
import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimitData;
import eg.edu.alexu.csd.oop.db.cs27.ParserTools.TextTools;

public class Use implements SingleQuery{
    
    private Error error;
    private TextTools text;
    private String query;
    private int stat;   //the stat of the query //zero for not prepared //one for prepared
    private String arr[];
    private ArrayList<String> parameters;
    private HashMap<String,String> cotHash;
    private int id;
    
    public Use(){
	this.id = 1;
	this.error = new Error();
	this.parameters = new ArrayList<String>();
	this.stat = 0;
	text = new TextTools();
	cotHash = new HashMap<>();
    }
    
    @Override
    public int getId(){
	return id;
    }
    
    public WhereOrderLimit whereOrderLimit(){
	throw new RuntimeException("Not supported Method");
    }
    
    private void initialize(){
	this.error = new Error();
	this.parameters = new ArrayList<String>();
	this.stat = this.stat >1 ?1:0;
    }
    
    @Override
    public void setQuery(String query){
	
	//initialize the variables
	initialize();	
	
	//remove multiple spaces or tabs
	this.query = text.trim(query.trim());
	this.query = text.hashQuot(cotHash, this.query);
	/*
	try{
	    //check for existing of semicolon
	    if(this.query.charAt(this.query.length()-1) == ';'){
		this.query = this.query.substring(0,this.query.length()-1);
		this.query = this.query.trim();
	    }else{
		error.reportError("Your query must end with semicolon");
		stat = 0;
		return; 
	    }
	}catch(StringIndexOutOfBoundsException e){
	    error.reportError("empty String");
	    return;
	}
	*/
	arr = this.query.split(" ");
	stat = 1;
    }
    
    @Override
    public boolean isValid(){
	
	if(stat == 0)
	    return false;
	
	//initialize the variables
	initialize();
	
	//start the process steps
	try{
	    validateUse();
	}
	catch(ArrayIndexOutOfBoundsException e){
	  //  error.reportError("Inclompete query");
	    stat = 2;
	    return false;
	}
	catch(StringIndexOutOfBoundsException e){
	 //   error.reportError("Inclompete query");
	    stat = 2;
	    return false;
	}
	//check if it's valid or not
	if(((WhereOrderLimitData) error).isError()){
	    stat = 2;
	    return false;
	}else{
	    stat = 3;
	    return true;
	}
    }
    
    @Override
    public ArrayList<String> parseIt(){
	
	//replace the hashValues
	for(int i = 0;i<parameters.size();i++)
	    parameters.set(i, text.replaceHashQuot(parameters.get(i), cotHash));
	
	if(stat == 3)
	    return this.parameters;
	else
	    throw new RuntimeException("can't parse invalid query");
    }
    
    private void validateUse() throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	if(arr[0].toLowerCase().equals("use"))
	    stage2(1);
	else {}
	//    error.reportError();
    }
    
    //USE DATABASE
    private void stage2(int pos) throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	if(pos != arr.length-1)
	{
	  //  error.reportError("USE works only with one database");
	    return;
	}
	parameters.add(arr[pos]);
    }
}
