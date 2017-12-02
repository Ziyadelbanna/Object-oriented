package eg.edu.alexu.csd.oop.db.cs27;



import java.sql.SQLException;



public interface ISelect {

	

	public void setNextInChain(ISelect next);

	public ISelect match(String input);

	public Object[][] interpret(String input) throws SQLException;

}