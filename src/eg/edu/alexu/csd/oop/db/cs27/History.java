package eg.edu.alexu.csd.oop.db.cs27;



import java.util.LinkedList;



public class History {



	private LinkedList<Object[]> history;

	private int sizeofrow;

	private String[] names;

	private String[] types;



	public String[] getNames() {

		return names;

	}



	public String[] getTypes() {

		return types;

	}



	public void setNames(String[] names) {

		this.names = names;

	}



	public void setTypes(String[] types) {

		this.types = types;

	}



	public History(int size) {

		history = new LinkedList<Object[]>();

		sizeofrow = size;

	}



	public void setHistory(Object[][] arr) {

		history = new LinkedList<>();

		for (Object[] a : arr) {

			history.add(a);

		}

	}



	public void insert(Object[] a) {

		if (a.length == names.length)

			history.add(a);

		else {

			Object[] o = new Object[names.length];

			for (int i = 0; i < a.length; i++)

				o[i] = a[i];

			history.add(o);

		}

	}



	public Object[][] get(int idx, String compare, char c) {

		LinkedList<Object[]> values = new LinkedList<Object[]>();

		Command operation = new Condition();

		for (Object[] a : history) {

			if (operation.execute((String) a[idx], compare, c))

				values.add(a);

		}

		Object[][] rvalues = new Object[values.size()][names.length];

		int e = 0;

		for (Object[] a : values) {

			int x = 0;

			for (Object b : a) {

				if (types[x].equals("int"))

					rvalues[e][x] = Integer.parseInt(b.toString());

				else

					rvalues[e][x] = b;

				x++;

			}

			e++;

		}

		return rvalues;

	}



	public Object[][] get(int[] specificcol, int idx, String compare, char c) {

		LinkedList<Object[]> values = new LinkedList<Object[]>();

		Command operation = new Condition();

		for (Object[] a : history) {

			if (operation.execute((String) a[idx], compare, c)) {

				Object[] arr = new Object[specificcol.length];

				int e = 0;

				for (int k : specificcol) {

					if (types[k].equals("int"))

						arr[e++] = Integer.parseInt(a[k].toString());

					else

						arr[e++] = a[k];

				}

				values.add(arr);

			}

		}

		Object[][] rvalues = new Object[values.size()][names.length];

		int e = 0;

		for (Object[] a : values) {

			rvalues[e++] = a;

		}

		return rvalues;

	}



	public Object[][] get() {

		Object[][] values = new Object[history.size()][names.length];

		int e = 0;

		for (Object[] a : history) {

			int x = 0;

			for (Object b : a) {

				if (types[x].equals("int")) {

					values[e][x] = Integer.parseInt(b.toString());

				} else

					values[e][x] = b;

				x++;

			}

			e++;

		}

		return values;

	}



	public Object[][] get(int[] idx) {

		Object[][] values = new Object[history.size()][idx.length];

		int e = 0;

		for (Object[] a : history) {

			Object[] c = new Object[idx.length];

			int f = 0;

			for (int k : idx) {

				if (types[k].equals("int"))

					c[f++] = Integer.parseInt(a[k].toString());

				else

					c[f++] = a[k];

			}

			values[e++] = c;

		}

		return values;

	}



	public int delete(int idx, String compare, char c) {

		LinkedList<Object[]> values = new LinkedList<Object[]>();

		Command operation = new Condition();

		int count = 0;

		for (Object[] a : history) {

			if (!operation.execute(a[idx].toString(), compare, c))

				values.add(a);

			else

				count++;

		}

		history = values;

		System.out.println(history.size());

		return count;

	}



	public int update(int[] indecies, String[] values, int colidx, String value, char operator) {

		Command operation = new Condition();

		// for(int a:indecies)System.out.println(a);

		// for(String a:values)System.out.println(a);

		// System.out.println(colidx+" "+value+" "+operator);

		int cnt = 0;

		for (Object[] a : history) {

			if (operation.execute(a[colidx].toString(), value, operator)) {

				cnt++;

				int g = 0;

				for (int x : indecies) {

					a[x] = values[g++];

				}

			}

		}

		return cnt;

	}



	public int updateall(int[] indecies, String[] values) {

		int cnt = 0;

		for (Object[] a : history) {

			int g = 0;

			for (int x : indecies) {

				a[x] = values[g++];

			}

			cnt++;

		}

		return cnt;

	}



	public LinkedList<Object[]> getHistory() {

		return history;

	}



	public int getSizeofrow() {

		return sizeofrow;

	}



	public void setHistory(LinkedList<Object[]> history) {

		this.history = history;

	}



	public void setSizeofrow(int sizeofrow) {

		this.sizeofrow = sizeofrow;

	}



	public int getSizeofRow() {

		return sizeofrow;

	}



	public int getNumofRows() {

		return history.size();

	}

}