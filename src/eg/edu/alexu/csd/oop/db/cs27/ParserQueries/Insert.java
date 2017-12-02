package eg.edu.alexu.csd.oop.db.cs27.ParserQueries;

import java.util.ArrayList;
import java.util.HashMap;

import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimit;
import eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional.WhereOrderLimitData;
import eg.edu.alexu.csd.oop.db.cs27.ParserTools.TextTools;

public class Insert implements SingleQuery{
    
    private Error error;
    private TextTools text;
    private String query;
    private int stat;   //the stat of the query //zero for not prepared //one for prepared
    private String arr[];
    private ArrayList<String> parameters;
    private HashMap<String,String> cotHash;
    private int id;
    
    public Insert(){
	this.error = new Error();
	this.parameters = new ArrayList<String>();
	this.stat = 0;
	this.text = new TextTools();
	this.cotHash = new HashMap<>();
	this.id = 9;
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
	    validateInsert();
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
    
    private void validateInsert() throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	if(arr[0].toLowerCase().equals("insert"))
	    if(arr[1].toLowerCase().equals("into"))
		stage9(2);
	    else {}
	//	error.reportError();
	else {}
	 //   error.reportError();
    }
    
    private void stage9(int pos)  throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
        parameters.add(arr[pos]);
        stage10(pos+1);
    }

    private void stage10(int pos)  throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{

	if(arr[pos].toLowerCase().equals("values")){
	    stage11(pos+1);
	    return;
	}
	String param;	//(,,,)
	String values;	//(,,,)
	int j;

	param = "";
	for(j=pos;j<arr.length;j++){
	    if(arr[j].toLowerCase().equals("values"))
		break;
	    param += " " + arr[j];
	}
	
	values = arr[j+1];
	for(j = j+2;j<arr.length;j++)
	    values += " " + arr[j];
	
	System.out.println(param);
	System.out.println(values);
	
	ArrayList<String> paramFinal = convertListToString(param);
	ArrayList<String> valuesFinal = convertListToString(values);

	if(paramFinal == null || valuesFinal == null){
	  //  error.reportError("error in your syntax");
	    return;
	}
	
	if(checkEmptyRepition(paramFinal)){
	  //  error.reportError("your columns name contains repeated or empty names");
	    return;
	}
	
	if(valuesFinal.size() != paramFinal.size()){
	  //  error.reportError("Number of Cols "+ paramFinal.size() +" doesn't equal number of data " + valuesFinal.size());
	    return;
	}
	
	parameters.add(valuesFinal.size()+"");
	for(int i=0;i<valuesFinal.size();i++){
	    if(paramFinal.get(i).split(" ").length > 1){
		//error.reportError("param values can't have spaces");
		return;
	    }
	    if(!valuesFinal.get(i).contains("\"")){
		if(valuesFinal.get(i).split(" ").length > 1){
		 //   error.reportError("integers values can't have spaces");
		    return;  
		}
		if(valuesFinal.get(i).length() < 1){
		   // error.reportError("Database doesn't support null data");
		    return;
		}

	    }else{
		if(valuesFinal.get(i) == null){
		   // error.reportError("Syntax error " + valuesFinal.get(i));
		    return;
		}
		valuesFinal.set(i, valuesFinal.get(i));
	    }
	    parameters.add(paramFinal.get(i));
	    parameters.add(valuesFinal.get(i));
	}
    }
    
    private void stage11(int pos){
	
	this.id = 15;
	
	String values;	//(,,,)
	int j;
	
	values = arr[pos];
	for(j = pos+1;j<arr.length;j++)
	    values += " " + arr[j];
	
	System.out.println(values);
	
	ArrayList<String> valuesFinal = convertListToString(values);

	if(valuesFinal == null){
	   // error.reportError("error in your syntax");
	    return;
	}

	parameters.add(valuesFinal.size()+"");
	for(int i=0;i<valuesFinal.size();i++){
	    if(!valuesFinal.get(i).contains("\"")){
		if(valuesFinal.get(i).split(" ").length > 1){
		   // error.reportError("integers values can't have spaces");
		    return;  
		}
	    }else{
		if(valuesFinal.get(i) == null){
		  //  error.reportError("Syntax error " + valuesFinal.get(i));
		    return;
		}
		valuesFinal.set(i, valuesFinal.get(i));
	    }
	    parameters.add(valuesFinal.get(i));
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

    private ArrayList<String> convertListToString(String x)  throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
    
	String paramterms[] = x.split(",");
	for(int i=0;i<paramterms.length;i++){
	    paramterms[i] = paramterms[i].trim();
	}
	//check for ( AND )
	if(paramterms[0].charAt(0) != '(' || paramterms[paramterms.length-1].charAt(paramterms[paramterms.length-1].length()-1) != ')' ){
	   // error.reportError("Syntax error , bracket not found");
	    return null;
	}
	
	//edit the first and last one
	paramterms[0] = paramterms[0].substring(1).trim();
	paramterms[paramterms.length-1] = paramterms[paramterms.length-1].substring(0, paramterms[paramterms.length-1].length()-1).trim();
	
	ArrayList<String> toreturn = new ArrayList<String>();
	
	for(int i=0;i<paramterms.length;i++)
	    toreturn.add(paramterms[i].trim());
	
	return toreturn;
    }

    
    public static void main(String args[]){
	SingleQuery x = new Insert();
	x.setQuery("INSERT INTO table_name4(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', '2011-01-25', 4)");
	System.out.println(x.isValid());
	ArrayList<String> y = x.parseIt();
	System.out.println(y);
    }
}
