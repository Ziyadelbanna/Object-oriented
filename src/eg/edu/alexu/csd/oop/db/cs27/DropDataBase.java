package eg.edu.alexu.csd.oop.db.cs27;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

public class DropDataBase {
	public void dropTable(String dbPath, String tablePath) throws SQLException {
		dbPath = dbPath.toLowerCase();
		MyDatabase.tables = new HashMap<>();
		File db = new File(dbPath);
		File table = null;
		if (db.exists() && db.isDirectory()) {
			deletefiles(db.listFiles());
			db.delete();
		} else {
			throw null;
			//throw new SQLException(dbPath + " , " + tablePath);
		}

	}

	private void deletefiles(File[] filesToBeFiltered) {
		for (File f : filesToBeFiltered) {
			f.delete();
		}

	}
}