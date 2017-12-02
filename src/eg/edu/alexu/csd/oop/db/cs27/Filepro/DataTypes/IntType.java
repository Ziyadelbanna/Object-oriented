package eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes;

public class IntType extends DataType{
     public IntType(){
    	   super();
    	   setname("Integer");
     }

	@Override
	public boolean validate(String data) {
		if(data.equals("Null")||data.equals("null")) return true ;
		try{
			int value = Integer.valueOf(data);
			return true;
		}
		catch(NumberFormatException ex){
			System.out.println("Error dataType must be Integer...");
			return false ;
		}
	}

	@Override
	public boolean compare(String data1, String data2, String cmp) {
		if(!validate(data1)) return false ;
		if(!validate(data2)) return false ;
		if(data1.equalsIgnoreCase("null") || data2.equalsIgnoreCase("null")) return false ;
		int num1 = Integer.valueOf(data1);
		int num2 = Integer.valueOf(data2);
		if(cmp.equals("=")) return num1==num2 ;
		if(cmp.equals(">")) return num1>num2 ;
		if(cmp.equals("!=")) return num1!=num2 ;
		if(cmp.equals("<")) return num1<num2 ;
		return false ;
	}

	@Override
	public Object getValue(String val) {
		return Integer.valueOf(val);
	}
	
     
}
