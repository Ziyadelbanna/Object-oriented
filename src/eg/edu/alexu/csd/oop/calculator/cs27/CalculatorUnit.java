package eg.edu.alexu.csd.oop.calculator.cs27;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.calculator.Calculator;

public class CalculatorUnit implements Calculator {

	LinkedList<String> operationArr = new LinkedList();
	private double num1, num2 = 0;
	private char op;
	private String result;
	private double res;
	private int last;
	private int size;

	@Override
	public void input(String s) {

		operationArr.add(s);
		size = operationArr.size();
		last = size - 1;

	}

	@Override
	public String getResult() {
		boolean visited = false;
		boolean opp = false;

		for (int i = 0, v = 0; i < operationArr.getLast().length(); i++) {
			if (Character.getNumericValue(operationArr.getLast().charAt(i)) != -1 && !visited) {
				num1 = num1 * 10 + (Character.getNumericValue(operationArr.getLast().charAt(i)));
			} else if (operationArr.getLast().charAt(i) == '.' && !visited) {
				int u = 1;
				while (Character.getNumericValue(operationArr.getLast().charAt(i + 1)) != -1) {
					num1 = num1 + (Character.getNumericValue(operationArr.getLast().charAt(i + 1)) * Math.pow(10, -u));
					u++;
					i++;
				}
				visited = true;
			} else if (Character.getNumericValue(operationArr.getLast().charAt(i)) == -1 && !opp) {
				op = operationArr.getLast().charAt(i);
				opp = true;
				visited = true;
			} else if (Character.getNumericValue(operationArr.getLast().charAt(i)) != -1 && visited) {
				num2 = num2 * 10 + (Character.getNumericValue(operationArr.getLast().charAt(i)));
			} else if (operationArr.getLast().charAt(i) == '.') {
				i++;
				int u = 1;
				while (i < operationArr.getLast().length()) {
					num2 = num2 + (Character.getNumericValue(operationArr.getLast().charAt(i)) * Math.pow(10, -u));
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

		return operationArr.getLast();
	}

	@Override
	public String prev() {

		return operationArr.get(last - 1);
	}

	@Override
	public String next() {
		return operationArr.get(last - 2);
	}

	@Override
	public void save() {

		try {
			// opens file
			FileOutputStream saveFile = new FileOutputStream("SavedObj.sav");

			// Create an ObjectOutputStream to put objects into save file.
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			save.writeObject(operationArr);
			// closes save
			save.close();
		} catch (Exception exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}

	}

	@Override
	public void load() {

	}

}
