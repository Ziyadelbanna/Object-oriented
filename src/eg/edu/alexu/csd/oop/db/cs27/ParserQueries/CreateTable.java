package eg.edu.alexu.csd.oop.db.cs27.ParserQueries;

import java.util.ArrayList;
import java.util.HashMap;

import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimit;
import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimitData;
import eg.edu.alexu.csd.oop.db.cs27.ParserTools.TextTools;
import eg.edu.alexu.csd.oop.db.cs27.ParserTypes.SupportedTypes;

public class CreateTable implements SingleQuery{
    
    private Error error;
    private TextTools text;
    private String query;
    private int stat;   //the stat of the query //zero for not prepared //one for prepared
    private String arr[];
    private ArrayList<String> parameters;
    private HashMap<String,String> cotHash;
    private SupportedTypes supportedTypes;
    private int id;
    
    public CreateTable(){
	this.error = new Error();
	this.parameters = new ArrayList<String>();
	this.stat = 0;
	text = new TextTools();
	cotHash = new HashMap<>();
	supportedTypes = new SupportedTypes();
	this.id = 3;
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
	    validateCreateTable();
	}
	catch(ArrayIndexOutOfBoundsException e){
	   // error.reportError("Inclompete query");
	    stat = 2;
	    return false;
	}
	catch(StringIndexOutOfBoundsException e){
	   // error.reportError("Inclompete query");
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
    
    private void validateCreateTable() throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	if(arr[0].toLowerCase().equals("create"))
	    if(arr[1].toLowerCase().equals("table"))
	         stage3(2);
	    else {}
		//error.reportError();
	else {}
	   // error.reportError();
    }
    
    private void stage3(int pos) throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	parameters.add(arr[pos]);
        stage5(pos+1);
    }
    private void stage5(int pos) throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	
	//construct String with (x y,z m,n v)
	String table = new String();
	table = arr[pos];
	for(int i=pos+1;i<arr.length;i++)
	    table += " " + arr[i];
	table = table.trim();
	
	if(table.charAt(0) != '(' || table.charAt(table.length()-1) != ')'){
	  //  error.reportError("missing bracket");
	    return;
	}
	table = table.substring(1, table.length()-1).trim();
	
	//construct "x" y
	//	    "z" m
	//	    "n" v
	String terms[] = table.split(",");
	for(int i=0;i<terms.length;i++)
	    terms[i] = terms[i].trim();
	
	//check the string and the datatype
	if(!checkForColNamesAndDataType(terms))
	    return;
	
	//construct string as
	//	x
	//	int
	//	yy
	//	varchar
	ArrayList<String> colsName = new ArrayList<String>();
	
	for(int i=0;i<terms.length;i++){
	    colsName.add(terms[i].split(" ")[0]);
	    parameters.add(terms[i].split(" ")[0]);
	    parameters.add(terms[i].split(" ")[1]);
	}
	if(checkEmptyRepition(colsName)){
	  //  error.reportError("repeated col name");
	    return;
	}
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

    private boolean checkForColNamesAndDataType(String terms[]){
	for(int i=0;i<terms.length;i++){
	    
	    //check for name and type
	    String temp[] = terms[i].split(" ");
	    if(temp.length != 2){
		//error.reportError(" Syntax error in \"" + terms[i] + "\"");
		return false;
	    }
	    
	    //check for datatype
	    if(!supportedTypes.isSupported(temp[1])){
		//error.reportError("Not supported datatype in \"" + temp[1] + "\"");
		return false;
	    }
	    
	}
	return true;
    }

    
    public static void main(String args[]){
	SingleQuery x = new CreateTable();
	x.setQuery("Create table mmm (xx int,\"yy\" date);");
	x.setQuery("Create table xXx (\"yYy\" int , \"name\" boolean);");
	x.setQuery("Create table yyy (\"yyy\" int);");
	System.out.println(x.isValid());
	ArrayList<String> y = x.parseIt();
	System.out.println(y);
    }

}
