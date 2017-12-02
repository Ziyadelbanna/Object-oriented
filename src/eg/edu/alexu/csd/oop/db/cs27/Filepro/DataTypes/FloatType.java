package eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes;

public class FloatType extends DataType{

	public FloatType(){
		   super();
		   setname("Float");
	}
	
	@Override
	public boolean validate(String data) {
		if(data.equals("Null")||data.equals("null")) return true ;
		try{
			float value = Float.valueOf(data);
			return true;
		}
		catch(NumberFormatException ex){
			System.out.println("Error dataType must be Float...");
			return false ;
		}
	}

	@Override
	public boolean compare(String data1, String data2, String cmp) {
		if(!validate(data1)) return false ;
		if(!validate(data2)) return false ;
		if(data1.equalsIgnoreCase("null") || data2.equalsIgnoreCase("null")) return false ;
		float num1 = Float.valueOf(data1);
		float num2 = Float.valueOf(data2);
		if(cmp.equals("=")) return num1==num2 ;
		if(cmp.equals(">")) return num1>num2 ;
		if(cmp.equals("!=")) return num1!=num2 ;
		if(cmp.equals("<")) return num1<num2 ;
		return false ;
	}

	@Override
	public Object getValue(String val) {
		return Float.valueOf(val);
	}

}
