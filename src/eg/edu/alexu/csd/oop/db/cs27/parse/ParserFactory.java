package eg.edu.alexu.csd.oop.db.cs27.parse;

public class ParserFactory {
	public Parser NewInstance(String operation) {
		
		Parser parser = null;
		switch (operation) {
		case "Select":
			parser = new Select();
			break;
		case "Insert":
			parser = new Insert();
			break;
		case "CreateTable":
			parser = new CreateTable();
			break;
		case "CreateDB":
			parser = new CreateDB();
			break;
		case "DropDB":
			parser = new DropDB();
			break;
		case "DropTable":
			parser = new DropTable();
			break;
		case "Delete":
			parser = new Delete();
			break;
		case "Update":
			parser = new update();
			break;
		}
		return parser;
	}
}