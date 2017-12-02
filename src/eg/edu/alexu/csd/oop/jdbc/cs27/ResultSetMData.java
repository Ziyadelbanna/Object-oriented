package eg.edu.alexu.csd.oop.jdbc.cs27;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class ResultSetMData implements java.sql.ResultSetMetaData{
    
    
      private Map<String,Integer> colNameToId;
      private Map<Integer,String> colIdToName;
      private Map<String,String> colNameToType;
      private Map<Integer,String> colIdToType;
      private String tableName;
      
      
	public ResultSetMData(Map<String,Integer> colNameToId,
		Map<Integer,String> colIdToName,
		Map<String,String> colNameToType,
		Map<Integer,String> colIdToType,
		String tablaName){
	    this.colNameToId = colNameToId;
	    this.colIdToName = colIdToName;
	    this.colNameToType = colNameToType;
	    this.colIdToType = colIdToType;
	    this.tableName = tableName;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getColumnCount() throws SQLException {
		return colNameToId.size();
	}

	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int isNullable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		if(column < 1 || column > colIdToName.size())
		    throw new SQLException("Error in number of the column");
		
		return colIdToName.get(column);
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		if(column < 1 || column > colIdToName.size())
		    throw new SQLException("Error in number of the column");
		
		return colIdToName.get(column);
	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPrecision(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScale(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTableName(int column) throws SQLException {
		return tableName;
	}

	@Override
	public String getCatalogName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		if(column < 1 || column > colIdToName.size())
		    throw new SQLException("Error in number of the column");		
		
		switch(colIdToType.get(column)){
		    case "int":
			return 4;
			
		    case "string":
			return 12;
			
		    case "float":
			return 6;
			
		    case "date":
			return 91;
			
		    default:
			return 0;
		}
	}

	@Override
	public String getColumnTypeName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}