package eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateType extends DataType{

	public DateType(){
		   super();
		   setname("Date");
	}
	
	@Override
	public boolean validate(String data) {
		if(data.equals("Null")||data.equals("null")) return true ;
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setLenient(false);
	        Date parsed = format.parse(data);
	        /*java.sql.*/Date sql = new /*java.sql.*/Date(parsed.getTime());
	   
	        return true ;
		}catch(ParseException ex){
			return false ;
		}
	}

	
	@Override
	public boolean compare(String data1, String data2, String cmp) {
		if(!validate(data1)) return false ;
		if(!validate(data2)) return false ;
		if(data1.equalsIgnoreCase("null") || data2.equalsIgnoreCase("null")) return false ;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try {
			Date d1 = format.parse(data1);
			Date d2 = format.parse(data2);
			if(cmp.equals("=")) return d1.compareTo(d2)==0;
			if(cmp.equals(">")) return d1.compareTo(d2)>0 ;
			if(cmp.equals("!=")) return d1.compareTo(d2)!=0 ;
			if(cmp.equals("<")) return d1.compareTo(d2)<0 ;
			return false ;
		} catch (ParseException e) {
			return false ;
		}
	}

	@Override
	public Object getValue(String val) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		try {
			return format.parse(val);
		} catch (ParseException e) {
			return null ;
		}
	}
        
}
