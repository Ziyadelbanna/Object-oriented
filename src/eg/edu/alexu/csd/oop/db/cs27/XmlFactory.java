package eg.edu.alexu.csd.oop.db.cs27;


public class XmlFactory {

	public Object getXmlInstance(String name) {
		if (name.equals("Select")) {
			return new XMLSelect();
		} else if (name.equals("Insert")) {
			return new XMLinsert();
		} else if (name.equals("Update")) {
			return new DomUpdater();
		} else if (name.equals("Delete")) {
			return new DeleteDOM();
		} else if (name.equals("CreateTable")) {
			return new xmlWriter();
		} else if (name.equals("CreateDB")) {
			return new CreateDatabase();
		} else if (name.equals("DropDB")) {
			return new DropDataBase();
		} else if (name.equals("DropTable")) {
			return new XmlDropTable();
		}

		return null;
	}
}