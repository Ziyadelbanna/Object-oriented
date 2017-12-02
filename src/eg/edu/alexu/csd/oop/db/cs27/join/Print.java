package eg.edu.alexu.csd.oop.db.cs27.join;

import java.util.List;
import java.util.Map;

public class Print {
	public void getOutput(List<String> columnsNames, List<Map<String, String>> tableData) {
		// TODO Auto-generated method stub
		int max = getMaximumLength(tableData, columnsNames);
		System.out.format("+");
		for (int i = 0; i < columnsNames.size(); i++) {
			for (int h = 0; h < max + 8; h++) {
				System.out.format("-");
			}
			System.out.format("+");
		}
		System.out.println();
		for (int y = 0; y < columnsNames.size(); y++) {
			System.out.format("| ");
			System.out.format(columnsNames.get(y));
			for (int j = 0; j < (max + 8 - columnsNames.get(y).length() - 1); j++) {
				System.out.format(" ");
			}
		}
		System.out.format("|");
		System.out.println();

		for (int k = 0; k < tableData.size(); k++) {
			System.out.format("+");
			for (int i = 0; i < columnsNames.size(); i++) {
				for (int e = 0; e < max + 8; e++) {
					System.out.format("-");
				}
				System.out.format("+");
			}
			System.out.println();
			for (int i = 0; i < columnsNames.size(); i++) {
				System.out.format("| ");
				System.out.format(tableData.get(k).get(columnsNames.get(i)));
				for (int j = 0; j < (max + 8 - tableData.get(k).get(columnsNames.get(i)).length() - 1); j++) {
					System.out.format(" ");
				}
			}
			System.out.format("|");
			System.out.println("");
		}
		System.out.format("+");
		for (int i = 0; i < columnsNames.size(); i++) {
			for (int t = 0; t < max + 8; t++) {
				System.out.format("-");
			}
			System.out.format("+");
		}
	}

	private int getMaximumLength(List<Map<String, String>> tableData, List<String> columnsNames) {
		// TODO Auto-generated method stub
		int max = columnsNames.get(0).length();
		for (int y = 1; y < columnsNames.size(); y++) {
			int temp = columnsNames.get(y).length();
			if (max < temp) {
				max = temp;
			}
		}
		for (int j = 0; j < tableData.size(); j++) {
			for (int i = 0; i < columnsNames.size(); i++) {
				int temp = tableData.get(j).get(columnsNames.get(i)).length();
				if (max < temp) {
					max = temp;
				}
			}
		}
		return max;
	}
}
