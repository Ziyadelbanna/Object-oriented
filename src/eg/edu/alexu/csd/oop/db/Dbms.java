package eg.edu.alexu.csd.oop.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.cs27.Filepro.FileOperations;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.DataType;

public interface Dbms {
     public void CreateDB(String dbname)throws SQLException;
     public void CreateTable(String tbname , String dbname , int colnum , List<String> colnames ,  List<DataType> coltypes , FileOperations filehandler) throws SQLException;
     public void AddRow(String tbname , String dbname ,  List<String> Row ,List <String> values ,  FileOperations filehandler) throws SQLException ;  // update 
     public int ModifyRow(String tbname, String dbname, List<String> col , List<String> relation , List<String> data , List<String> cols , List<String> newValues , FileOperations filehandler) throws SQLException;
     public int DeleteRow(String tbname , String dbname ,List<String> col , List<String> relation , List<String> data , FileOperations filehandler)throws SQLException;
     public void AlterDrop(String tbname , String dbname , String colname , FileOperations filehandler)throws  SQLException;
     public void AlterAdd(String tbname, String dbname, String colname , DataType datatype , FileOperations filehandler) throws SQLException;
     public List<Map<String , String>> Select(String tbname , String dbname ,List<String> col , List<String> relation , List<String> data , FileOperations filehandler) throws SQLException;
     public Set<String> Distinct(String tbname , String dbname , String colname , List<String> col , List<String> relation , List<String> data , FileOperations filehandler) throws SQLException;
     public void DropDB(String dbname) throws SQLException;
     public void DropTable(String dbname , String tbname , FileOperations filehandler) throws SQLException;
     public boolean validateTable(String dbname , String tbname , FileOperations filehandler) throws SQLException;
     public List<Map<String , String>> showTables(String dbname , FileOperations filehandler)throws SQLException;
     public List<Map<String , String>> describeTable(String dbname , String tbname , FileOperations filehandler)throws SQLException;
     public String getType(String dbname , String tbname , String colname , FileOperations filehandler) throws SQLException;
}
