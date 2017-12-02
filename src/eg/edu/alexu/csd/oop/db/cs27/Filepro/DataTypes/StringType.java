package eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes;

public class StringType extends DataType{
   public StringType(){
	   super();
	   setname("String");
  }

	@Override
	public boolean validate(String data) {
		if(data.equals("Null")||data.equals("null")) return true ;
		if(data.isEmpty()){
			  System.out.println("Error Empty String");
			  return false ;
		}
		return true ;
	}

	@Override
	public boolean compare(String data1, String data2, String cmp) {
		if(!validate(data1)) return false ;
		if(!validate(data2)) return false ;
		if(data1.equalsIgnoreCase("null") || data2.equalsIgnoreCase("null")) return false ;
		int diff = data1.compareTo(data2);
		if(cmp.equals("=")) return diff==0 ;
		if(cmp.equals(">")) return diff>0;
		if(cmp.equals("!=")) return diff!=0 ;
		if(cmp.equals("<")) return diff<0 ;
		return false ;
	}

	@Override
	public Object getValue(String val) {
		return val ;
	}
	
}
