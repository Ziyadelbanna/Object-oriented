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
			parser = new CreateDatabase();
			break;
		case "DropDB":
			parser = new DropDatabase();
			break;
		case "DropTable":
			parser = new DropTable();
			break;
		case "Delete":
			parser = new Delete();
			break;
		case "Update":
			parser = new Update();
			break;
		}
		return parser;
	}
}