package eg.edu.alexu.csd.oop.db.cs27;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Table")
@XmlAccessorType(XmlAccessType.FIELD)
public class Table {

	@XmlElement(name = "Row")
	public List<Row> records = new LinkedList<Row>();
	
	public List<String> columnsAvailable = new ArrayList<String>();
	
	public void settable(List<Row> rows) {
		this.records = rows;
	} 	

	public List<Row> gettable() {
		return records;
	}
	
	
}
