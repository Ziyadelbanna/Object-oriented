package eg.edu.alexu.csd.oop.db.cs27.ParserQueriesOptional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import eg.edu.alexu.csd.oop.db.cs27.ParserTools.TextTools;


public class WhereOrderLimit implements WhereOrderLimitData , WhereOrderLimitOperations{
    
    //Tools
    private Error error;
    private TextTools text;
    
    //Main for processing
    private String query;
    private int stat;   //the stat of the query //zero for not prepared //one for prepared
    private String arr[];
    private HashMap<String,String> cotHash;
    
    //Where Order Limit locations
    private int wherelocation;
    private int limitlocation;
    private int orderlocation;
    private ArrayList<String> whereList;
    private ArrayList<String> orderList;
    private ArrayList<String> limitList;    
    
    public WhereOrderLimit(){
	
    }
    
    private void initialize(){
	error = new Error();
	text = new TextTools();
	stat = 0;
	wherelocation = -1;
	limitlocation = -1;
	orderlocation = -1;
	whereList = new ArrayList();
	orderList = new ArrayList();
	limitList = new ArrayList();
    }
    
    /*
	Setters
    */
    public void SetQuery(String query){
	//initialize the variables
	initialize();	
	
	//remove multiple spaces or tabs
	this.query = text.trim(query.trim());

	arr = this.query.split(" ");
	stat = 1;
    }
    
    public void setHash(HashMap<String,String> x){
	this.cotHash = x;
    }
    
    public void execute(int pos){
	if(stat == 0)
	    return;
	stage8(pos);
    }
    
    
    /*
	Getter
    */
    public boolean isError(){
	return ((WhereOrderLimitData) error).isError();
    }
    
    public boolean isWhere(){
	if(wherelocation != -1)
	    return true;
	return false;
    }
    
    public boolean islimit(){
	if(limitlocation != -1)
    	    return true;
	return false;
    }
    
    public boolean isOrder(){
	if(orderlocation != -1)
    	    return true;
	return false;
    }
    
    
    public ArrayList<String> whereData(){
	
	for(int i=0;i<whereList.size();i++){
	    whereList.set(i, text.replaceHashQuot(whereList.get(i), cotHash));
	}
	
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
    
    
    /*
	Process
    */
    
    //handle WHERE LIMIT ORDERBY
    private void stage8(int pos)  throws ArrayIndexOutOfBoundsException , StringIndexOutOfBoundsException{	
	//check if the pos inside the array
	if(pos >= arr.length)
	    return;
	
	//collect the rest of the sentence to one string
	String str = collectorToEnd(pos, arr);
	
	str = str.replaceAll("\\("," ( ");
	str = str.replaceAll("\\)"," ) ");
	str = str.replaceAll("="," = ");
	str = str.replaceAll(">"," > ");
	str = str.replaceAll("<"," < ");
	str = text.trim(str);
	
	String warr[] = str.split(" ");
	
	//check if there is only one where and know it's location
	if(contianOnce(str, "where")){
	    wherelocation = findIndex(0, warr, "where");
	}else
	    return;

	//check if there is only one limit and know it's location
	if(contianOnce(str, "limit")){
	    limitlocation = findIndex(0, warr, "limit");
	}else
	    return;
	
	//check if there is only one order and know it's location
	if(contianOnce(str, "order by")){
	   orderlocation = findIndex(0, warr, "order" , "by");
	}else
	    return;
	
	if(!checkOrder()){
	    return;
	}

	//build the string of WHERE
	String conditions = buildWhere(warr);
    System.out.println("L"+conditions);

	//build the string of LIMIT
	String limits = buildLimit(warr);
	
	//build the string of ORDERBY
	String orders = buildOrder(warr);
		
	//process the limit sting as x y
	if(!processLimit(limits))
	    return;
	
	//process the order string as XXXX
	if(!processOrder(orders))
	    return;
	
	//process where string
	if(!processWhere(conditions))
	    return;
	
    }
    
    private boolean checkOrder(){
	//check order WHERE then ORDER then limit and thier existing
	if(orderlocation != -1 && wherelocation != -1 && limitlocation != -1 ){
	    if(wherelocation > orderlocation || orderlocation > limitlocation ||
		    wherelocation > orderlocation){
	//	error.reportError("Syntax error, WHEN ORDER LIMIT order wrong");
		return false;
	    }
	}else if(wherelocation != -1 && orderlocation != -1){
	    if(wherelocation > orderlocation){
	//	error.reportError("Syntax error, WHEN ORDER order wrong");
		return false;
	    }
	}else if(wherelocation != -1 && limitlocation != -1){
	    if(wherelocation > limitlocation){
	//	error.reportError("Syntax error, WHEN LIMIT order wrong");
		return false;
	    }
	}else if(orderlocation != -1 && limitlocation != -1){
	    if(orderlocation > limitlocation){
	//	error.reportError("Syntax error, ORDER LIMIT order wrong");
		return false;
	    }
	}else if(orderlocation == -1 && limitlocation == -1 && wherelocation == -1){
	 //   error.reportError("Syntax error, NO WHERE, ORDER, LIMIT after the query");
	    return false;
	}
	return true;
    }

    
    private boolean processWhere(String conditions){
	if(wherelocation != -1){
	    conditions = wherePrepare(conditions);
	    System.out.println("C"+conditions);
	    if(validateWhereQuery(conditions)){
	    System.out.println("CC"+conditions);
		getConditions(conditions);
	    }else{
	//	error.reportError("during parsing of where queries");
		return false;
	    }
	}
	return true;
    }
    
    private void getConditions(String sentString) {
		System.out.println("F"+sentString);
		
		Stack<String> evaluatorStack = new Stack<String>();

		StringBuilder getcondition = new StringBuilder();

		ArrayList<String> conditions = new ArrayList<String>();

		sentString = sentString.replace("(", " ( ");
		sentString = sentString.replace(")", " ) ");

		for (int i = 0; i < sentString.length(); i++) {
			if (sentString.charAt(i) == '(') {
				evaluatorStack.push("(");
			} else if (sentString.charAt(i) == ')') {

				while (evaluatorStack.size() != 0) {
					String temp = evaluatorStack.pop().toString();
					if (temp != "(")
						conditions.add(temp);
					else
						break;
				}
			} else if (sentString.charAt(i) == ' ')
				continue;
			else if (sentString.substring(i, i + 1).equals("'")) {
				i++;
				while ((i) < sentString.length() && !sentString.substring(i, i + 1).equals("'")) {
					getcondition.append(sentString.charAt(i));
					i++;
				}
				conditions.add(getcondition.toString());
				getcondition.delete(0, getcondition.length());

			} else if (sentString.charAt(i) == 'a' || sentString.charAt(i) == 'A') {
				getcondition.append(sentString.charAt(i));
				getcondition.append(sentString.charAt(i + 1));
				getcondition.append(sentString.charAt(i + 2));

				if (getcondition.toString().toLowerCase().equals("and")) {
					evaluatorStack.push(getcondition.toString().toLowerCase());
					getcondition.delete(0, getcondition.length());
				}
			} else if (sentString.charAt(i) == 'o' || sentString.charAt(i) == 'O') {
				getcondition.append(sentString.charAt(i));
				getcondition.append(sentString.charAt(i + 1));

				if (getcondition.toString().toLowerCase().equals("or")) {
					while (evaluatorStack.size() != 0 && evaluatorStack.peek().toString() == "and")
						conditions.add(evaluatorStack.pop().toString());
					evaluatorStack.push(getcondition.toString().toLowerCase());
					getcondition.delete(0, getcondition.length());
				}
			}
		}
		while (evaluatorStack.size() != 0)
			conditions.add(evaluatorStack.pop().toString());
		
		whereList = conditions;

    }

    
    private String wherePrepare (String setters){
	
	    //make sure it's surrounding with spaces
	    setters = setters.replaceAll("="," = ");
	    setters = setters.replaceAll(">"," > ");
	    setters = setters.replaceAll("<"," < ");
	    setters = setters.replaceAll("\\("," ( ");
	    setters = setters.replaceAll("\\)"," ) ");

	    //remove unnesessary spaces
	    setters = setters.replaceAll("\\s+"," ");
	    setters = setters.replaceAll("\\t+"," ");
	    
	    //cut most of the spaces
	    setters = setters.replaceAll(" = ","=");
	    setters = setters.replaceAll(" > ",">");
	    setters = setters.replaceAll(" < ","<");
	    setters = setters.trim();
	    
	    String toget;
	    toget = setters.replaceAll("\\(","");
	    toget = toget.replaceAll("\\)","");
	    toget = toget.trim();
	    
	    String arr[] = toget.split("and|anD|aNd|aND|And|AnD|ANd|AND|or|oR|Or|OR");
	    for(int i=0;i<arr.length;i++)
		System.out.println(arr[i]);
	    //change the condition
	    for(int i=0;i<arr.length;i++)
		setters = setters.replaceFirst(arr[i].trim(), "'" + arr[i].trim() + "'");
	    
	    return setters;
    }
    
    private boolean validateWhereQuery(String x){
	    x = x.replaceAll("\\(","");
	    x = x.replaceAll("\\)","");
	    String arr[] = x.split("and|anD|aNd|aND|And|AnD|ANd|AND|or|oR|Or|OR");
	    for(int i=0;i<arr.length;i++){
		//validate the condition
		String cond = arr[i].trim();
		cond = cond.trim().substring(1,cond.length()-1).trim();
		if(cond.split("=").length + cond.split(">").length + cond.split("<").length != 4){
		    return false;
		}
		
		String p[] = new String[2];
		if(cond.split("=").length == 2)
		    p = cond.split("=");
		if(cond.split(">").length == 2)
		    p = cond.split(">");
		if(cond.split("<").length == 2)
		    p = cond.split("<");

		if(p[0].split(" ").length != 1 || p[1].split(" ").length != 1)
		{
		    return false;
		}

		if(!text.isNumeric(p[1].trim()) && !text.checkString(p[1].trim())){
		    return false;
		}
	    }
	return true;
    }
    
    
    private boolean processLimit(String limits){
	if(limitlocation != -1){
	    if(limits.split(",").length == 2){
		if(text.isNumeric(limits.split(",")[0]) && text.isNumeric(limits.split(",")[1])){
		    limitList.add(limits.split(",")[0].trim());
		    limitList.add(limits.split(",")[1].trim());
		}else{
		  //  error.reportError("Syntax error in limits , Intergers only");
		    return false;    
		}
	    }else{
		//error.reportError("Syntax error in limits");
		return false;
	    }
	}
	return true;
    }
    
    private boolean processOrder(String orders){
	if(orderlocation != -1){
	    String[] ordList = orders.split(",");
	    for(int i=0;i<ordList.length;i++){
		orders = ordList[i].trim();
		if(orders.trim().split(" ").length > 2){
		//    error.reportError("Syntax error in orders");
		    return false;
		}else if(orders.trim().split(" ").length == 2){
		    if(!orders.split(" ")[1].trim().toLowerCase().equals("asc") && !orders.split(" ")[1].trim().toLowerCase().equals("desc")){
			//error.reportError("order must be ASC or DESC Only");
			return false;
		    }
		    orderList.add(orders.trim().split(" ")[0].trim());
		    orderList.add(orders.trim().split(" ")[1].trim().toLowerCase());
		}else if(orders.trim().split(" ").length == 1 && !orders.trim().split(" ")[0].isEmpty()){
		    orderList.add(orders.trim().split(" ")[0].trim());
		}else{
		   // error.reportError("No parameters given to ORDER BY");
		    return false;
		}
	    }
	}
	return true;
    }
    
    private String buildLimit(String warr[]){
	String limits = "";
	if(limitlocation != -1){
	    for(int i=limitlocation;i<warr.length;i++)
		limits += " "+warr[i];
	    limits = limits.trim();
	}
	return limits;
    }
    
    private String buildOrder(String warr[]){
	String orders = "";
	if(orderlocation != -1){
	    for(int i=orderlocation+1;i<warr.length;i++){
		if(i == limitlocation-1){
		    break;
		}
		orders += " "+warr[i];
	    }
	    orders = orders.trim();
	}
	return orders;
    }
    
    private String buildWhere(String warr[]){
	String conditions = "";
	if(wherelocation != -1){
	    for(int i=wherelocation;i<warr.length;i++){
		if(i == orderlocation-2 || i == limitlocation-1){
		    break;
		}
		if(warr[i].toLowerCase().equals("and") || warr[i].toLowerCase().equals("or"))
		    conditions += " "+warr[i].toLowerCase();
		else
		    conditions += " "+warr[i];
	    }
	    conditions = conditions.trim();
	}
	
	return conditions;
    }
    
    private String collectorToEnd(int pos,String arr[]){
	
	String str = "";
	for(int i=pos;i<arr.length;i++){
	    if(arr[i].toLowerCase().equals("where") || arr[i].toLowerCase().equals("limit"))
		str += " " + arr[i].toLowerCase();
	    else if(arr[i].toLowerCase().equals("order")){
		if(i+1 < arr.length){
		    if(arr[i+1].toLowerCase().equals("by"))
			str += " " + "order by";
		}
	    }
	    else
		str += " " + arr[i];
	}
	return str;
    }
    
    private int findIndex(int pos, String warr[],String str1 , String str2){
	    for(int i=pos;i<warr.length;i++){
		if(warr[i].toLowerCase().equals(str1)){
		    if(warr[i+1].toLowerCase().equals(str2)){
			return i+2;
		    }
		}
	    }
	    return -1;
    }
    
    private boolean contianOnce(String str , String pattern){
	if(str.contains(pattern) && str.replace(pattern, " ").contains(pattern)){
	   // error.reportError("contians "+pattern+" two times");
	    return false;
	}
	return true;
    }
    
    private int findIndex(int pos, String warr[],String pattern){
	    for(int i=pos;i<warr.length;i++){
		if(warr[i].toLowerCase().equals(pattern)){
		    return i+1;
		}
	    }
	    return -1;
    }
    
}