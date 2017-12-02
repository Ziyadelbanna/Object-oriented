package eg.edu.alexu.csd.oop.db.cs27;



public class FolderBuilder {

	private static  Folder folder;

	

	

	public static void  create(String path) {

		folder = new Folder(path);

	}

	

	public static Folder getFolder() {

		return folder;

	}

	public static void setFolder(Folder f){

		folder = f ; 

	}

}