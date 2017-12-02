package eg.edu.alexu.csd.oop.db.cs27;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.validation.*;

public class DTDCreation {
	private static StringWriter stringWriter;
	private static XMLOutputFactory xMLOutputFactory;
	private static XMLStreamWriter xMLStreamWriter;

	public DTDCreation() {
		if (stringWriter == null) {
			stringWriter = new StringWriter();
			xMLOutputFactory = XMLOutputFactory.newInstance();
			try {
				xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);
			} catch (XMLStreamException e) {
				throw null;
			}
		}
	}

	public void addDTDElement(String e) {
		try {
			xMLStreamWriter.writeDTD("<!ELEMENT " + e + " ");
		} catch (XMLStreamException e1) {
			throw null;
		}
	}

	public void addDTDChildOrDT(String c, boolean lastChild) {
		String temp = stringWriter.toString();
		if (temp.charAt(temp.length() - 1) == ' ') {
			try {
				if (!lastChild)
					xMLStreamWriter.writeDTD("(" + c + ",");
				else
					xMLStreamWriter.writeDTD("(" + c + ")>\n");
			} catch (XMLStreamException e1) {
				throw null;
			}
		} else {
			try {

				if (!lastChild)
					xMLStreamWriter.writeDTD(c + ",");
				else
					xMLStreamWriter.writeDTD(c + ")>\n");
			} catch (XMLStreamException e1) {
				throw null;
			}
		}
	}

	public void addAttr(String e, String attr, String dType) {
		try {
			xMLStreamWriter.writeDTD("<!ATTLIST " + e + " " + attr + " (" + dType + ") #REQUIRED>\n");
		} catch (XMLStreamException e1) {
			throw null;
		}
	}

	public void saveDTD(String path) {
		try {
			xMLStreamWriter.flush();
			xMLStreamWriter.close();
			if (!path.endsWith(".dtd"))
				path = path.concat(".dtd");
			// System.out.println(path);
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
			bw.write(stringWriter.getBuffer().toString());
			bw.flush();
			bw.close();
		} catch (XMLStreamException | IOException e) {
			throw null;
		}

	}
}