package eg.edu.alexu.csd.oop.db.cs27.Filepro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.DataType;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.DateType;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.FloatType;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.IntType;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.StringType;

import org.w3c.dom.Node;

public class XmlFileHandler extends FileOperations{
  
	  public XmlFileHandler(){
		    super();
	  }
	  
	  @Override
	  public boolean createDescreptionFile(List<String> colnames , List<DataType> coltype){
		  String fileName = path+File.separator+tablename+".dtd" ;
		  try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            if(colnames.isEmpty()){
          	  bufferedWriter.write("<!ELEMENT table EMPTY>");
            }
            else{
                	 bufferedWriter.write("<!ELEMENT table (row*)>");
	                 bufferedWriter.newLine();
	                 bufferedWriter.write("<!ELEMENT row (");
	                 bufferedWriter.write(colnames.get(0));
	                 for(int i=1 ; i< colnames.size() ; i++){
	                	 bufferedWriter.write(","+colnames.get(i));
	                 }
	                 bufferedWriter.write(")>");
	                 bufferedWriter.newLine();
	                 for(int i=0;i<colnames.size();i++){
	                	 bufferedWriter.write("<!ELEMENT "+colnames.get(i)+" (#PCDATA)>");
		                 bufferedWriter.newLine();
	                 }
	                 for(int i=0;i<colnames.size();i++){
	                	 bufferedWriter.write("<!ATTLIST "+colnames.get(i)+" type NMTOKEN #FIXED \""+coltype.get(i).getname()+"\">");
	                	 bufferedWriter.newLine();
	                 }
            }
            bufferedWriter.close();
            return true ;
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            return false ;
        }
  	  
	  }
	  
	@Override
    public boolean createDataFile(){
		
  	     try{
  	    	 
  	    	 DocumentBuilderFactory dbFactory =
  	    	         DocumentBuilderFactory.newInstance();
  	    	         DocumentBuilder dBuilder = 
  	    	            dbFactory.newDocumentBuilder();
  	    	         Document doc = dBuilder.newDocument();          
  	    	         Element rootNode = doc.createElement("table");
  	    	         doc.appendChild(rootNode);
  	    	 
		         Transformer transformer = TransformerFactory.newInstance().newTransformer();
		         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		         transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		         transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM , tablename+".dtd"); 
		         DOMSource source = new DOMSource(doc);
		         StreamResult result = new StreamResult(new File(path+File.separator+tablename+".xml"));
		         transformer.transform(source, result);
		       /*  StreamResult consoleResult =  new StreamResult(System.out);
		         transformer.transform(source, consoleResult);  */
		  	          return true ;
		            } catch (Exception e) {
		   	           e.printStackTrace();
		   	           return false ;
		  	      }
	  }
    
	@Override
	public void deletefiles() {
		descriptionFile.delete();
		dataFile.delete();
	}
    private boolean flagg = true ;
    
    @Override
    public boolean validate(){
  	    String xmlFile  = path+File.separator+tablename+".xml";
  	    try{
		    	       DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    	  	   domFactory.setValidating(true);
		    	  	   DocumentBuilder builder = domFactory.newDocumentBuilder();
		    	  	   builder.setErrorHandler(new ErrorHandler() {
		    	  	       @Override
		    	  	       public void error(SAXParseException exception) throws SAXException {
		    	  	           exception.printStackTrace();
		    	  	           flagg  = false ;
		    	  	       }
		    	  	       @Override
		    	  	       public void fatalError(SAXParseException exception) throws SAXException {
		    	  	    	   exception.printStackTrace();
		    	  	    	   flagg = false ;
		    	  	       }
		
		    	  	       @Override
		    	  	       public void warning(SAXParseException exception) throws SAXException {
		    	  	    	   System.out.println("Invalid Xml file....7");
		    	  	    	   flagg = false ;
		    	  	       }
		    	  	   });
		    	  	   if(!flagg) return false ;
		    	       org.w3c.dom.Document doc = builder.parse(xmlFile);
		    	       return true ;
  	    }catch(ParserConfigurationException e1){
  	    	       System.out.println("Invalid Xml file....1");
  	    	       return false ;
  	    }
          catch(SAXException e2){
          	       System.out.println("Invalid Xml file....2");
          	       return false ;
  	    }
          catch(IOException e3){
          	       e3.printStackTrace();
          	       return false ;
  	    }
    }

	@Override
	public void setDecritionFile() {
		descriptionFile = new File(path+File.separator+tablename+".dtd");
	}

	@Override
	public void setDataFile() {
		dataFile = new File(path+File.separator+tablename+".xml");
		System.out.println(dataFile);
	}

	@Override
	public void writeFile(Object obj) {
		try{
			Document doc = (Document) obj ;
		    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	         transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	         transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM ,gettablename()+".dtd");  
	         DOMSource source = new DOMSource(doc);
	         StreamResult result = new StreamResult(getDataFile());
	         transformer.transform(source, result);
	  }catch(TransformerFactoryConfigurationError e){
		   System.out.println(e.getMessage());
	  }catch(TransformerException e){
		   System.out.println(e.getMessage());
	  }
	}
	
	@Override
	public void addrow(List<String> newrow) {
	    		dataFile = new File(path+File.separator+tablename+".xml");

        Document doc = getDoc(dataFile);

        Element newRow = recordtoelement(newrow, doc) ;

        Element root  = doc.getDocumentElement() ;

        root.appendChild(newRow);
        writeFile(doc);

	}
	

	@Override
	public int modify(List<String> col, List<String> relation, List<String> data, List<String> newrecord) {
	
		int upd = 0 ;
		Document doc = getDoc(dataFile);
         Element rootNode = doc.getDocumentElement();
         NodeList nList = doc.getElementsByTagName("row");
         for(int i=0;  i < nList.getLength() ; i++){
		       Node currnode = nList.item(i);
		       List<String> xmlrecord = record.extractRecord(currnode);
		       if(record.compareRecord(xmlrecord, col, relation, data)){
		    	   xmlrecord = record.updateRecord(xmlrecord, newrecord);
		    	   Element newelement = recordtoelement(xmlrecord, doc);
		    	   rootNode.replaceChild(newelement, currnode);
		    	   upd++;
		       }
         }
         writeFile(doc);
         return upd ;
	}
	
	
	@Override
	public void select(List<String> col, List<String> relation, List<String> data, List<Map<String, String>> selected) {
		Document doc = getDoc(dataFile);
        Element rootNode = doc.getDocumentElement();
        NodeList nList = doc.getElementsByTagName("row");
        for(int i=0;  i < nList.getLength() ; i++){
		       Node currnode = nList.item(i);
		       
		       List<String> xmlrecord = record.extractRecord(currnode);
		       if(record.compareRecord(xmlrecord, col, relation, data)){
		    	     selected.add(record.recordtomap(xmlrecord)) ;
		       }
        }
	}
	
	
	@Override
	public Set<String> distinct(String colname, List<String> col, List<String> relation, List<String> data) {
		Set<String> distinctRecords = new HashSet<>();
		List<Map<String, String>> selected = new ArrayList<>();
		select(col,relation , data , selected);
		for(Map<String,String> mp : selected){
			distinctRecords.add(mp.get(colname));
		}
		return distinctRecords ;
	}
	
	
	@Override
	public int delete(List<String> col, List<String> relation, List<String> data) {
		List<Node> todelete = new ArrayList<>();
	    Document doc = getDoc(dataFile);
        Element rootNode = doc.getDocumentElement();
        NodeList nList = doc.getElementsByTagName("row");
        for(int i=0;  i < nList.getLength() ; i++){
		       Node currnode = nList.item(i);
		       List<String> xmlrecord = record.extractRecord(currnode);
		       if(record.compareRecord(xmlrecord, col, relation, data)){
		    	   todelete.add(currnode);
		       }
        }
        for(Node node : todelete){
       	 rootNode.removeChild(node) ;
        }
        writeFile(doc); 
        return todelete.size();
	} 

	@Override
	public void alteradd( String colname , String datatype , List<String> colnames, List<DataType> coltypes) {
		Document doc = getDoc(dataFile);
        Element rootNode = doc.getDocumentElement();
        NodeList nList = doc.getElementsByTagName("row");
         for(int i=0;  i < nList.getLength() ; i++){
		       Node currnode = nList.item(i);
		       Element newcol = doc.createElement(colname);
		       Attr attrType = doc.createAttribute("type");
	  		       attrType.setValue(datatype);
	  		       newcol.setAttributeNode(attrType);
	  		       newcol.appendChild(doc.createTextNode("Null"));
	  		       currnode.appendChild(newcol);
         }
         writeFile(doc); 
         createDescreptionFile( colnames , coltypes );
	}
	
	
	@Override
	public void alterdrop(String colname, List<String> colnames, List<DataType> coltypes) {
		Document doc = getDoc(dataFile);
        Element rootNode = doc.getDocumentElement();
        NodeList nList = doc.getElementsByTagName("row");
         for(int i=0;  i < nList.getLength() ; i++){
		       Node currnode = nList.item(i);
		       NodeList nList2 = currnode.getChildNodes();
		       for(int j=0 ; j<nList2.getLength() ; j++){
		    	   Node innercol = nList2.item(j) ;
		           String currname = innercol.getNodeName() ;
		           if(currname.equals(colname)){
		        	   currnode.removeChild(innercol);
		        	   break ;
		           }
		       }    
         }
         writeFile(doc); 
         createDescreptionFile( colnames , coltypes );
	}
	
	
	private  Element recordtoelement(List<String> record , Document doc){
 	   Element newrecord = doc.createElement("row");
        for(int i=0; i<cols.size() ; i++){
     	    Column col = cols.get(i);
     	    String colname = col.getname() ;
     	    String coltype = col.getdatatype().getname();
     	    Element subelement = doc.createElement(colname);

	  		    Attr attrType = doc.createAttribute("type");
	  		    attrType.setValue(coltype);
	  		    subelement.setAttributeNode(attrType);
	  		    subelement.appendChild(doc.createTextNode(record.get(i)));
	  		    newrecord.appendChild(subelement);

        }
 	   return newrecord ;
   }
	
	private Document getDoc(File inputFile){
		try {
				 DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder;
			     dBuilder = dbFactory.newDocumentBuilder();
				 Document doc = dBuilder.parse(inputFile);

				 doc.getDocumentElement().normalize();
		         return doc ;
		} catch (ParserConfigurationException e) {
			     e.printStackTrace();
			     return null ;
		}
         catch (SAXException | IOException e) {
			     e.printStackTrace();
			     return null ;
		}    
    }

	@Override
	public int loadTable(List<String> cols , List<DataType> types  , List<Column> columns) {
         int colnum = 0 ;
        try{
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(dataFile);
         doc.getDocumentElement().normalize();
         NodeList nList = doc.getElementsByTagName("row");
         for (int temp = 0; temp < nList.getLength() && temp < 1; temp++) {
        	 Node nNode = nList.item(temp);
        	 NodeList subnodes = nNode.getChildNodes() ;
		     int childsnum = subnodes.getLength();
		     for(int i=1 ; i<childsnum ; i+=2){
		    	  Node currnode =  subnodes.item(i);
		    	  Element eElement = (Element) currnode;
		    	  String name = eElement.getNodeName();
	        	  String type = eElement.getAttribute("type");
	        	  cols.add(name);
	        	  DataType datatype = gettype(type);
	        	  types.add(datatype);
	        	  columns.add(new Column(name , datatype));
	        	  colnum ++ ;
		     }
          }
		         return colnum;
        }catch(ParserConfigurationException e){
	 		   System.out.println(e.getMessage());
	 		   return 0 ;
	     }catch(SAXException e){
		       System.out.println(e.getMessage());
		       return 0 ;
	    }catch(IOException e){
		       System.out.println(e.getMessage());
		       return 0 ;
	    }
	}
	
	private DataType gettype(String st){
		   if(st.equals("Integer")) return new IntType();
		   else if(st.equals("String")) return new StringType();
		   else if(st.equals("Float")) return new FloatType();
		   else  return new DateType();
	}
        
	
}
