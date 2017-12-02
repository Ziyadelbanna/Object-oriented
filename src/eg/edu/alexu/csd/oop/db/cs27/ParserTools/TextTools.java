package eg.edu.alexu.csd.oop.db.cs27.ParserTools;

import static java.lang.reflect.Array.set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TextTools{
    public TextTools(){
	
    }
    
    //trim the word with respect to the double coutation
    public String trim(String x){
	
	int word  = 0;
	boolean working = true;
	String toreturn = "";
	
	for(int i=0;i<x.length();i++){
	    switch(x.charAt(i)){
		case '\t':
		case ' ':
		    if(working){
			if(word == 0){
			    toreturn += " ";
			    word = 1;
			}
		    }else
			toreturn += " ";
		    break;
		case '\"':
		    toreturn += x.charAt(i);
		    working = working==true?false:true;
		    word = 0;
		    break;
		default:
			toreturn += x.charAt(i);
			word = 0;
		    break;
	    }
	}
	return toreturn;
    }

    //from (x,y,z,w) to x y z w
    public ArrayList<String> convertListToString(String x,Error error)  throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
    
	//split by ,
	String paramterms[] = x.split(",");
	for(int i=0;i<paramterms.length;i++){
	    paramterms[i] = paramterms[i].trim();
	}
	
	
	//check for ( AND )
	if(paramterms[0].charAt(0) != '(' || paramterms[paramterms.length-1].charAt(paramterms[paramterms.length-1].length()-1) != ')' ){
	    error.reportError("Syntax error , bracket not found");
	    return null;
	}
	
	//edit the first and last one
	paramterms[0] = paramterms[0].substring(1).trim();
	paramterms[paramterms.length-1] = paramterms[paramterms.length-1].substring(0, paramterms[paramterms.length-1].length()-1).trim();
	
	ArrayList<String> toreturn = new ArrayList<>();
	
	for(int i=0;i<paramterms.length;i++)
	    toreturn.add(paramterms[i].trim());
	
	return toreturn;
    }
    
    //clean the given word form "  '  `
    public String clean(String x)  throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{
	boolean flag = true;
	while(flag){
	    if(x.charAt(x.length()-1) == x.charAt(0)){
		switch(x.charAt(0)){
		    case '\"':
		    case '\'':
		    case '`':
			x = x.substring(1, x.length()-1);
			break;
		    default:
			flag = false;
			break;
		}
	    }
	    flag = false;
	}
	
	return x;
    }
    
    //check if the string inside double cot or not
    public boolean checkString(String x){
	if(this.clean(x).equals(x)){
	    return false;
	}else
	    return true;
    }

    
    //return if the given word contain only of numbers or not
    public boolean isNumeric(String x){
	x = x.trim();
	
	for(int i=0;i<x.length();i++){
	    switch(x.charAt(i)){
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		    break;
		default:
		    return false;
	    }
	}
	return true;
    }
    
    //replace the words between double coutation with hash value
    public String hashQuot (HashMap<String,String> x , String setters){
	    int start = 0;
	    String curr = "";
	    
	    for(int i=0;i<setters.length();i++){
		
		if(setters.charAt(i) == '\"'){
		    curr += '"';
		    start = start==0?1:0;
		}else{
		    if(start == 1){
			curr += setters.charAt(i);
		    }
		}
		
		if(start == 0){
		    if(curr.length() > 0){
			x.put("\"" + i+"i" + "\"", curr);
		    }
		}
		
		if(start == 0)
		    curr = "";
	    }
	    
	    start = 0;
	    curr = "";
	    
	    for(int i=0;i<setters.length();i++){
		
		if(setters.charAt(i) == '\''){
		    curr += '\'';
		    start = start==0?1:0;
		}else{
		    if(start == 1){
			curr += setters.charAt(i);
		    }
		}
		
		if(start == 0){
		    if(curr.length() > 0){
			x.put("\"" + i +"m" + "\"", curr);
		    }
		}
		
		if(start == 0)
		    curr = "";
	    }
	    
	    Set<String> keys =x.keySet();
	    for(String y : keys){
		setters = setters.replaceFirst(x.get(y), y);
	    }
	    
	    setters = prepareText(setters);
	    setters = trim(setters);
	    
	    return setters;
    }
    
    public String prepareText(String x){
	x = x.replace("(", " ( ");
	x = x.replace(")", " ) ");
	x = x.toLowerCase();
	return x;
    }
    
    //look for the given hashValues and reset them to the old values
    public String replaceHashQuot (String con , HashMap<String,String> x){
	Set<String> y = x.keySet();
	for(String s : y){
	    //System.out.println("REPLACE "+s+" TO "+x.get(s));
	    con = con.replaceFirst(s, clean(x.get(s)));
	}
	return con;
    }
}