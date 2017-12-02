package eg.edu.alexu.csd.oop.db.cs27;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Filter {

	public boolean ColumnValid(Element dummy, ArrayList<String> columnsName) {
		NodeList columns = dummy.getChildNodes();
		ArrayList<String> col = new ArrayList<>();
		for (int i = 0; i < columns.getLength(); i++) {
			if (columns.item(i).getNodeType() == Node.ELEMENT_NODE) {
				col.add(columns.item(i).getNodeName());
			}

		}
		for (int k = 0; k < columnsName.size(); k++) {

			if (!col.contains(columnsName.get(k)))

			{
				// System.out.println(columns.item(k).getNodeName());
				return false;
			}

		}

		return true;
	}

	public boolean ValueValid(Element dummy, ArrayList<String> NewValue, ArrayList<String> columnsName) {
		NodeList columns = dummy.getChildNodes();
		for (int k = 0; k < columns.getLength(); k++) {
			if (columns.item(k).getNodeType() == Node.ELEMENT_NODE) {
				int index = columnsName.indexOf(columns.item(k).getNodeName());
				if (index != -1) {
					if (columns.item(k).getTextContent().equals("integer")) {
						if (!isNumber(NewValue.get(index)))
							return false;
					} else {
						if (!(NewValue.get(index).startsWith("'") || NewValue.get(index).startsWith("\"")))
							return false;
						if (!(NewValue.get(index).endsWith("'") || NewValue.get(index).endsWith("\"")))
							return false;
					}

				}
			}

		}
		return true;

	}

	private boolean isNumber(String num) {
		try {
			int temp = Integer.parseInt(num);

		} catch (NumberFormatException e) {
			// TODO: handle exception
			return false;
		}
		return true;

	}
}