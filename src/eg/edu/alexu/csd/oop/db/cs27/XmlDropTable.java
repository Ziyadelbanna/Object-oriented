package eg.edu.alexu.csd.oop.db.cs27;

import java.io.File;

public class XmlDropTable {
	public void dropTable(String dbPath, String tablePath) {
		// File db = new File(dbPath);
		/*
		 * File table = null; if (db.exists() && db.isDirectory()) { String
		 * extendedTable = new String(tablePath); if
		 * (!extendedTable.endsWith(".xml")) { extendedTable =
		 * extendedTable.concat(".xml"); table = filterFiles(db.listFiles(),
		 * extendedTable); } else { table = filterFiles(db.listFiles(),
		 * extendedTable); } table.delete(); } else { throw null; }
		 */
		MyDatabase.tables.put(tablePath, null);

	}

	private File filterFiles(File[] filesToBeFiltered, String seekTable) {
		for (File f : filesToBeFiltered) {
			if (f.getName().equals(seekTable) && f.isFile()) {
				return f;
			}
		}
		throw null;
	}
}