package eg.edu.alexu.csd.oop.db.cs27;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "Row")
@XmlAccessorType(XmlAccessType.FIELD)

public class Row {
	@XmlElement(name = "Record")
	public Map<String, Object> record = new HashMap<String, Object>();

	public void setrecord(Map m) {
		if (record.size() != 0) {
			record.clear();
		}
		this.record = m;
	}

}
