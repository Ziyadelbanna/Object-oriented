package eg.edu.alexu.csd.oop.db.cs27.ParserQueries;

import java.util.ArrayList;
import java.util.HashMap;

import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimit;
import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimitData;
import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimitOperations;
import eg.edu.alexu.csd.oop.db.cs27.ParserTools.TextTools;

public class Update implements SingleQuery{
    
    private Error error;
    private TextTools text;
    private String query;
    private int stat;   //the stat of the query //zero for not prepared //one for prepared
    private String arr[];
    private ArrayList<String> parameters;
    private HashMap<String,String> cotHash;
    private int id;
    private int lastPos;
    
    public Update(){
	this.error = new Error();
	this.parameters = new ArrayList<String>();
	this.stat = 0;
	this.text = new TextTools();
	this.cotHash = new HashMap<>();
	this.id = 7;
    }
    
    @Override
    public int getId(){
	return id;
    }
    
    public WhereOrderLimitData whereOrderLimit(){
	if(stat != 3)
	    throw new RuntimeException("the query must be valid first");
	
	WhereOrderLimitOperations wol = new WhereOrderLimit();
	wol.SetQuery(query);
	wol.execute(this.lastPos+1);
	wol.setHash(cotHash);

	return (WhereOrderLimitData) wol;
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
	System.out.println("33"+this.query);


	
	System.out.println("FULL-AFTER"+this.query);
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
	    validateUpdate();
	}
	catch(ArrayIndexOutOfBoundsException e){
	   // error.reportError("Inclompete query");
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
    
    private void validateUpdate() throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	if(arr[0].toLowerCase().equals("update"))
	    stage11(1);
	else {}
	   // error.reportError();
    }
    
    private void stage11(int pos) throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	parameters.add(arr[pos]);
	if(arr[pos+1].toLowerCase().equals("set")){
	    stage12(pos+2);
	}else{
	  //  error.reportError("\"SET\" wasn't found in the query");
	    return;
	}
    }
    
    private void stage12(int pos)  throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	
	String setters;
	setters = arr[pos];
	int k;
	for(k=pos+1; k<arr.length;k++){
	    if(!arr[k].toLowerCase().equals("where"))
		setters += " " + arr[k];
	    else
		break;
	}
	
	String sets[] = setters.trim().split(",");
	String values = "";
	ArrayList<String> cols = new ArrayList<String>();
	
	for(int i=0;i<sets.length;i++){
	    String halfs[] = sets[i].split("=");
	    if(halfs.length == 2){
		if(halfs[0].trim().split(" ").length == 1 && halfs[1].trim().split(" ").length == 1){
		    if(!text.checkString(halfs[1].trim()) && !text.isNumeric(halfs[1].trim())){
			//error.reportError("set parameter should only be string or integer");
			return;
		    }
		}else{	
		   // error.reportError("error in set parameters");
		    return;
		}
	    }else{
	        //error.reportError("error in set parameters , should always be x = y");
	        return;
	    }
	    cols.add(halfs[0].trim());
	    parameters.add(halfs[0].trim());
	    parameters.add(halfs[1].trim());
	}
	if(checkEmptyRepition(cols)){
	 //   error.reportError("names of your columns contains repeated or empty ones");
	    return;
	}
	lastPos = k-1;
    }

    private boolean checkEmptyRepition(ArrayList<String> x){
	
	//check for being empty
	for(int i=0;i<x.size();i++){
	    if(x.get(i).trim().isEmpty())
		return true;
	}
	
	//check for repitions
	for(int i=0;i<x.size();i++){
	    for(int j=i+1;j<x.size();j++){
		if(x.get(i).equals(x.get(j)))
		    return true;
	    }
	}
	
	return false;
    }    
    
    
    public static void main(String args[]){
	SingleQuery x = new Update();
	x.setQuery("update mmm set x = \"ddsad  ssady\" ,  z = 3345;");
	System.out.println(x.isValid());
	ArrayList<String> y = x.parseIt();
	System.out.println(y);
    }
}
