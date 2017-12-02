package eg.edu.alexu.csd.oop.db.cs27.ParserQueries;

import java.util.ArrayList;
import java.util.HashMap;

import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimit;
import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimitData;
import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimitOperations;
import eg.edu.alexu.csd.oop.db.cs27.ParserTools.TextTools;

public class Select implements SingleQuery{
    
    private Error error;
    private TextTools text;
    private String query;
    private int stat;   //the stat of the query //zero for not prepared //one for prepared
    private String arr[];
    private ArrayList<String> parameters;
    private HashMap<String,String> cotHash;
    private int id;
    private int lastPos;
    
    public Select(){
	this.error = new Error();
	this.parameters = new ArrayList<String>();
	this.stat = 0;
	this.text = new TextTools();
	this.cotHash = new HashMap<>();
	this.id = 6;
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
	System.out.println("FULL"+this.query);


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
	    validateSelect();
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
    
    private void validateSelect() throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	if(arr[0].toLowerCase().equals("select")){
		stage13(1);
	}else {}
	   // error.reportError();
    }
    
    private String replace(String x,String original){
	
	x = x.toLowerCase().trim();
	original = original.trim();
	
	String inarr[] = original.split(" ");
	String toReturn = "";
	
	for(int i=0;i<inarr.length;i++){
	    if(inarr[i].toLowerCase().equals(x))
		toReturn += " " + x;
	    toReturn += " " + inarr[i];
	}
	    
	return toReturn.trim();
    }
    
    private void stage13(int pos) throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{	
	
	//if it's distinct
	if(arr[pos].toLowerCase().equals("distinct")){
	    this.id = 14;
	    pos++;
	}
	//else
	int k;
	String stables = arr[pos];
	for(k =pos+1;k<arr.length;k++){
	    if(!arr[k].toLowerCase().equals("from"))
		stables += " " + arr[k];
	    else
		break;
	}
	
	String tables[] = stables.split(",");
	String result = "";
	ArrayList<String> cols = new ArrayList<String>();
	
	for(int i=0;i<tables.length;i++){
	     
	    tables[i] = tables[i].trim();
	    if(tables[i].split(" ").length != 1){
		//error.reportError("Syntax error "+ tables[i]);
		return;
	    }
	    cols.add(tables[i]);
	    result += " " + tables[i];
	}
	
	if(checkEmptyRepition(cols)){
	   // error.reportError("collumns contains repeated names or empty ones");
	    return;
	}
	
	result = result.trim();
	parameters.add(result);	
	stage7(k+1);
    }
    
    private void stage7(int pos) throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	    parameters.add(arr[pos]);
	    this.lastPos = pos;
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
	SingleQuery x = new Select();
	x.setQuery("SELECT * FROM table_name13 ORDER BY column_name2 ASC, COLUMN_name3 DESC;");
	System.out.println(x.isValid());
	ArrayList<String> y = x.parseIt();
	System.out.println(y);
    }
}
