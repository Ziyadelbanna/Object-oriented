package eg.edu.alexu.csd.oop.db.cs27.ParserQueries;

import java.util.ArrayList;
import java.util.HashMap;

import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimit;
import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimitData;
import eg.edu.alexu.csd.oop.db.cs27.ParserTools.TextTools;

public class AlterDropCol implements SingleQuery{
    
    private Error error;
    private TextTools text;
    private String query;
    private int stat;   //the stat of the query //zero for not prepared //one for prepared
    private String arr[];
    private ArrayList<String> parameters;
    private HashMap<String,String> cotHash;
    private int id;
    
    public AlterDropCol(){
	this.id = 13;
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
	    validateAlterDropCol();
	}
	catch(ArrayIndexOutOfBoundsException e){
	 //   error.reportError("Inclompete query");
	    stat = 2;
	    return false;
	}
	catch(StringIndexOutOfBoundsException e){
	  //  error.reportError("Inclompete query");
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
    
    private void validateAlterDropCol() throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	if(arr[0].toLowerCase().equals("alter"))
	    if(arr[1].toLowerCase().equals("table"))
	         stage4(2);
	    else {}
	//	error.reportError();
	else {}
	  //  error.reportError();
    }
    
    private void stage4(int pos) throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	parameters.add(arr[pos]);
	stage5(pos+1);
    }
    
    private void stage5(int pos){
	
	if(!arr[pos].toLowerCase().equals("drop")){
	  //  error.reportError();
	    return;
	}
	
	if(pos+3 != arr.length){
	  ///  error.reportError("you should write only the column name ");
	    return;
	}
	
	if(!arr[pos+1].toLowerCase().equals("column")){
	   // error.reportError("It should be written as \"drop column\"");
	    return;
	}
	
	parameters.add(arr[pos+2]);
    }
    
    public static void main(String args[]){
	SingleQuery x = new AlterDropCol();
	x.setQuery("alter table xxx drop column yyyy;");
	System.out.println(x.isValid());
	ArrayList<String> y = x.parseIt();
	System.out.println(y);
    }
}