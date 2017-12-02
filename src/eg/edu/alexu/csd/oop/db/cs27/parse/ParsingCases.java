package eg.edu.alexu.csd.oop.db.cs27.parse;

public class ParsingCases {
	public IParser NewInstance(String operation) {

		IParser parser = null;
		switch (operation) {
		case "Select":
			parser = new SelectFromTable();
			break;
		case "Insert":
			parser = new InserttoTable();
			break;
		case "CreateTable":
			parser = new TableCreation();
			break;
		case "CreateDB":
			parser = new DatabaseCreation();
			break;
		case "DropDB":
			parser = new DropDataBase();
			break;
		case "DropTable":
			parser = new DropTable();
			break;
		case "Delete":
			parser = new DeleteFromTable();
			break;
		case "Update":
			parser = new UpdateTable();
			break;
		}
		return parser;
	}
}