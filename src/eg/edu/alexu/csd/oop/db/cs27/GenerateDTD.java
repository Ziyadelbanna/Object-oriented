package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.io.IOException;

import java.io.PrintWriter;



public class GenerateDTD {

	public void generateDTD(Folder folder) throws Exception {

		String path = folder.getPath() + File.separator +"table.dtd";

		File newDTD = new File(path) ;

		try {

			newDTD.createNewFile() ;

		} catch (IOException e) {

			e.printStackTrace();

		}

		PrintWriter out = new PrintWriter(newDTD);

		out.print(getValidation());

		out.close();

		

	}

	

	

	private String getValidation() {

		String ans = "";

		ans += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

		ans += '\n';

		

		ans += "<!ELEMENT table (columns,row*)>";

		ans += '\n';

		

		ans += "<!ELEMENT columns (col*)>";

		ans += '\n';

		

		ans += "<!ELEMENT col EMPTY>";

		ans += '\n';

		

		ans += "<!ELEMENT row (col*)>";

		ans += '\n';

		

		ans += "<!ATTLIST col\n key CDATA #REQUIRED\n value CDATA #REQUIRED\n>";

		

		return ans;

	}



}