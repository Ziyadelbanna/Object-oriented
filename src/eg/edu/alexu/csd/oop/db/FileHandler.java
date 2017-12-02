package eg.edu.alexu.csd.oop.db;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eg.edu.alexu.csd.oop.db.cs27.Filepro.Column;
import eg.edu.alexu.csd.oop.db.cs27.Filepro.DataTypes.DataType;

public interface FileHandler {
	public boolean createDescreptionFile(List<String> colnames , List<DataType> coltype);
	public boolean createDataFile();
	public boolean validate() ;
    public void writeFile(Object obj);
    public int modify(List<String> col , List<String> relation , List<String> data , List<String> newrecord);
    public void addrow(List<String> newrow);
    public Set<String> distinct(String colname , List<String> col , List<String> relation , List<String> data);
    public void select(List<String> col, List<String> relation, List<String> data , List<Map<String, String>> selected);
    public int delete(List<String> col, List<String> relation, List<String> data);
    public void alteradd(String colname , String datatype , List<String> colnames , List<DataType> coltypes);
    public void alterdrop(String colname , List<String> colnames , List<DataType> coltypes);
    public void deletefiles();
    public int loadTable(List<String> cols , List<DataType> types  , List<Column> columns);
}




