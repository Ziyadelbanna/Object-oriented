package eg.edu.alexu.csd.oop.db.cs27.ModifyXmlFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class AndOrCondition {

	public ArrayList<String> getConditions(String sentString) {

		Stack<String> evaluatorStack = new Stack<String>();

		StringBuilder getcondition = new StringBuilder();

		ArrayList<String> conditions = new ArrayList<String>();

		sentString = sentString.replace("(", " ( ");
		sentString = sentString.replace(")", " ) ");
		sentString = sentString.trim().replaceAll("\\s+", " ");
		sentString.toLowerCase();

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
					evaluatorStack.push(getcondition.toString());
					getcondition.delete(0, getcondition.length());
				}
			} else if (sentString.charAt(i) == 'o' || sentString.charAt(i) == 'O') {
				getcondition.append(sentString.charAt(i));
				getcondition.append(sentString.charAt(i + 1));

				if (getcondition.toString().toLowerCase().equals("or")) {
					while (evaluatorStack.size() != 0 && evaluatorStack.peek().toString() == "and")
						conditions.add(evaluatorStack.pop().toString());
					evaluatorStack.push(getcondition.toString());
					getcondition.delete(0, getcondition.length());
				}
			}
		}
		while (evaluatorStack.size() != 0)
			conditions.add(evaluatorStack.pop().toString());
		return conditions;

	}
	
	
	public static void main(String args[]){
	    AndOrCondition y = new AndOrCondition();
	   // System.out.println(x.getConditions("'Ahmed = mohamed' AND ox = \"ot\""));
	    
	    String setters = "(id =       name) AND    (      name = \"      FARES\")";
	    //String setters = "id =       name AND          name = \"      FARES\"";
	    
	    HashMap<String,String> x = new HashMap<String,String>();
	    int start = 0;
	    String curr = "";
	    for(int i=0;i<setters.length();i++){
		if(setters.charAt(i) == '\"'){
		    start = start==0?1:0;
		}else{
		    if(start == 1){
			curr += setters.charAt(i);
		    }
		}
		if(start == 0){
		    if(curr.length() > 0){
			setters = setters.replace(curr, i+"");
			x.put("\"" + i + "\"", curr);
			System.out.println(curr);
		    }
		}
		if(start == 0)
		    curr = "";
	    }
	    
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
	    for(int i=0;i<arr.length;i++){
		//validate the condition
		String cond = arr[i].trim();
		if(cond.split("=").length + cond.split(">").length + cond.split("<").length != 2){
		    //error
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
		    //error
		}
		
		
		
		//change the condition
		setters = setters.replace(arr[i].trim(), "'" + arr[i].trim() + "'");
	    }
	    
	    System.out.println(setters);
	    System.out.println(toget);
	}
}