package eg.edu.alexu.csd.oop.db.cs27;



import java.sql.SQLException;



public class LessThan implements Operator {



	private String symbol = "<";

	private Operator next  = null;

	

	@Override

	public Operator match(String symbol) {



		if (this.symbol.equals(symbol)) {

			return this;

		} else if (next != null) {

			return next.match(symbol);

		}

		return null;

	}



	@Override

public boolean compare(Row row, Table table, String column, Object comparedValue) throws SQLException {

		

		String type = table.getMap().get(column);	

		if(type == null){

			return false ; 

		}

		if (type.equalsIgnoreCase("int")) {

			int value ;

			try{

				 value = Integer.parseInt(row.getAllAttributes().get(column));				

			}catch(Exception e){

				return false ;

			}

			String compared = (String) comparedValue ;

			compared = compared.trim() ; 

			//System.out.println(compared);

			Integer tmp ;

			try{

				tmp = Integer.parseInt(compared) ;

			}catch(Exception e){

				return false ; 

			}

			return Integer.compare(value, tmp) == -1;

		} else if (type.equalsIgnoreCase("varchar")) {

			String value = String.valueOf(row.getAllAttributes().get(column));

			String compared = (String) comparedValue;

			return value.compareTo(compared) == -1;

		}

		

		return false ; 

	}



	@Override

	public void setNextInChain(Operator next) {

		this.next = next;

	}



}