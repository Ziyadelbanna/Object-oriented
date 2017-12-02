package eg.edu.alexu.csd.oop.db.cs27;



import java.sql.SQLException;



public interface Expression {

	/*

	 * return true if the input matches the regular expression in the

	 * implementation class

	 */

	public Expression match(String input);



	/*

	 * executes the SQL command

	 */

	public int interpret(String input) throws SQLException ;

	

	public void setNextInChain(Expression next);

}