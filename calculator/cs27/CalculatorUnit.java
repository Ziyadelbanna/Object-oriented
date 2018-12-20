package eg.edu.alexu.csd.oop.calculator.cs27;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import eg.edu.alexu.csd.oop.calculator.Calculator;

public class CalculatorUnit implements Calculator {

	LinkedList<String> operationArr = new LinkedList();
	LinkedList<String> operationNew;
	private double num1, num2 = 0;
	private char op;
	private String result;
	private double res;
	private int last = -1;
	private int size = 0;
	private int current = -1;
	private int slcount = 0;
	

	
	@Override
	public void input(String s) {

		operationArr.add(s);
		size = operationArr.size();
		last = size - 1;
		current = last;
		if (operationArr.size() > 5) {
			operationNew = new LinkedList();
			for (int i = last, u = 0; i >= 0 && u <= 4; i--, u++) {
				operationNew.add(operationArr.get(i));
			}
			operationArr = new LinkedList();

			for (int i = operationNew.size() - 1; i >= 0; i--) {

				operationArr.add(operationNew.get(i));
			}
		}
		size = operationArr.size();
		last = size - 1;
		current = last;

	}

	@Override
	public String getResult() {

		if (size == 0) {
			return null;
		}

		num1 = 0;
		num2 = 0;
		boolean visited = false;
		boolean opp = false;

		for (int i = 0; i < operationArr.get(current).length(); i++) {
			if (Character.getNumericValue(operationArr.get(current).charAt(i)) != -1 && !visited) {
				num1 = num1 * 10 + (Character.getNumericValue(operationArr.get(current).charAt(i)));
			} else if (operationArr.get(current).charAt(i) == '.' && !visited) {
				int u = 1;
				while (Character.getNumericValue(operationArr.get(current).charAt(i + 1)) != -1) {
					num1 = num1
							+ (Character.getNumericValue(operationArr.get(current).charAt(i + 1)) * Math.pow(10, -u));
					u++;
					i++;
				}
				visited = true;
			} else if (Character.getNumericValue(operationArr.get(current).charAt(i)) == -1 && !opp) {
				op = operationArr.get(current).charAt(i);
				opp = true;
				visited = true;
			} else if (Character.getNumericValue(operationArr.get(current).charAt(i)) != -1 && visited) {
				num2 = num2 * 10 + (Character.getNumericValue(operationArr.get(current).charAt(i)));
			} else if (operationArr.get(current).charAt(i) == '.') {
				i++;
				int u = 1;
				while (i < operationArr.get(current).length()) {
					num2 = num2 + (Character.getNumericValue(operationArr.get(current).charAt(i)) * Math.pow(10, -u));
					u++;
					i++;
				}
			}
		}

		if (op == '+') {
			res = num1 + num2;
		} else if (op == '-') {
			res = num1 - num2;
		} else if (op == '*') {
			res = num1 * num2;
		} else if (op == '/') {
			if (num2 == 0) {
				throw null;
			} else {
				res = num1 / num2;
			}
		}
		result = String.valueOf(res);

		return result;
	}

	@Override
	public String current() {

		String operation;
		try {
			operation = operationArr.get(current);
		} catch (IndexOutOfBoundsException ioe) {

			return null;

		}

		return operation;
	}

	@Override
	public String prev() {
		if (current - 1 < 0) {
			return null;
		}

		else {
			current--;

			return operationArr.get(current);
		}
	}

	@Override
	public String next() {
		if (current + 1 == operationArr.size()) {
			return null;
		}
		current++;
		return operationArr.get(current);
	}

	@Override
	public void save() {
		slcount = 0;
		try {
			// opens file
			FileOutputStream saveFile = new FileOutputStream("SavedObj.sav");

			// Create an ObjectOutputStream to put objects into save file.
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			for (int i = 0; i < operationArr.size(); i++) {
				save.writeObject(operationArr.get(i));
			}
			slcount = current;

			// closes save
			save.close();
		} catch (Exception exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}

	}

	@Override
	public void load() {

		operationNew = new LinkedList();

		try {
			// Open file .
			FileInputStream loadfile = new FileInputStream("SavedObj.sav");

			// Create an ObjectInputStream to get objects from load file.
			ObjectInputStream load = new ObjectInputStream(loadfile);

			for (int i = 0; i <= slcount; i++) {
				operationNew.add((String) load.readObject());
			}
			operationArr = new LinkedList();

			for (int i = 0; i < operationNew.size(); i++) {

				operationArr.add(operationNew.get(i));
			}
			last = operationArr.size() - 1;
			current = slcount;

			load.close();
		} catch (Exception exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}
		

	}

}
