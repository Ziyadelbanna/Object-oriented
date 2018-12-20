package eg.edu.alexu.csd.oop.calculator.cs27;

public class main {

	public static void main(String[] args) {

		CalculatorUnit calc = new CalculatorUnit();

		calc.input("5-4");
		calc.input("5-33");
		calc.input("3*5");
		calc.input("57+3");
		
		calc.save();
		
		calc.input("5-3");
		calc.input("5*3");
		calc.input("5/3");
		
		calc.load();
			
					
		String r  = calc.getResult();
		System.out.println(r);


	}

}
