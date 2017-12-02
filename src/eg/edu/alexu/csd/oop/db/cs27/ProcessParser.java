package eg.edu.alexu.csd.oop.db.cs27;

public class ProcessParser {
	private String CommandLine;

	public ProcessParser(String commandLine) throws Exception {
		CommandLine = commandLine;
		if (CommandLine.toLowerCase().contains("select")) {
			// Select x = new Select(CommandLine);
		} else if (CommandLine.toLowerCase().contains("update")) {

		} else if (CommandLine.toLowerCase().contains("insert")) {
			// Insert x = new Insert(CommandLine);
			// DB_XML c = new DB_XML();
			// c.insertInto("C:\\Users\\Ahmed\\Documents\\New folder",
			// "table.xml", x.getInputs(), x.getValues());
		} else if (CommandLine.toLowerCase().contains("create")) {

		} else {
			throw null;

		}
	}

}