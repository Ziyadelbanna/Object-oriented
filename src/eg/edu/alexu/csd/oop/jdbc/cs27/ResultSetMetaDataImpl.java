package eg.edu.alexu.csd.oop.jdbc.cs27;



import java.sql.ResultSetMetaData;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.database.cs27.ResultDataWarpper;



public class ResultSetMetaDataImpl implements ResultSetMetaData {



	// private static final int VARCHAR = 12;

	// private static final int INTEGER = 4;

	private String tableName;

	private String[] colDataTypes;

	private String[] colNames;



	public ResultSetMetaDataImpl(ResultDataWarpper warpper) {

		setTableName(warpper.getTableName());

		setColDataTypes(warpper.getColTypes());

		setColNames(warpper.getColNames());

	}



	private void setColNames(String[] colNames) {

		this.colNames = colNames;

	}



	private void setTableName(String tableName) {

		this.tableName = tableName;

	}



	private void setColDataTypes(String[] colDataTypes) {

		this.colDataTypes = colDataTypes;

	}



	@Override

	public int getColumnCount() throws SQLException {

		if (colNames[0] == null && colNames.length == 1) {

			throw new SQLException();

		}

		return colNames.length;

	}



	@Override

	public String getColumnLabel(int colIndex) throws SQLException {

		if (colIndex > 0 && colIndex <= getColumnCount()) {

			return getColumnName(colIndex);

		} else {

			throw new SQLException();

		}

	}



	@Override

	public String getColumnName(int colIndex) throws SQLException {

		if (colIndex > 0 && colIndex <= getColumnCount()) {

			return colNames[colIndex - 1];

		} else {

			throw new SQLException();

		}

	}



	private String getStringColumnType(int colIndex) {

		return colDataTypes[colIndex - 1];

	}



	@Override

	public int getColumnType(int colIndex) throws SQLException {

		if (colIndex > 0 && colIndex <= getColumnCount()) {

			if (getStringColumnType(colIndex).equalsIgnoreCase("varchar")) {

				return java.sql.Types.VARCHAR;

			} else if (getStringColumnType(colIndex).equalsIgnoreCase("int")

					|| getStringColumnType(colIndex).equalsIgnoreCase("integer")) {

				return java.sql.Types.INTEGER;

			} else {

				throw new SQLException();

			}

		} else {

			throw new SQLException();

		}

	}



	@Override

	public String getTableName(int arg0) throws SQLException {

		if (colNames == null) {

			return "";

		}

		return tableName;

	}



	@Override

	public boolean isWrapperFor(Class<?> iface) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public <T> T unwrap(Class<T> iface) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public String getCatalogName(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public String getColumnClassName(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public int getColumnDisplaySize(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public String getColumnTypeName(int arg0) throws SQLException {

		throw new UnsupportedOperationException();



	}



	@Override

	public int getPrecision(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public int getScale(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public String getSchemaName(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public boolean isAutoIncrement(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public boolean isCaseSensitive(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public boolean isCurrency(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public boolean isDefinitelyWritable(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public int isNullable(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public boolean isReadOnly(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public boolean isSearchable(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public boolean isSigned(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



	@Override

	public boolean isWritable(int arg0) throws SQLException {

		throw new UnsupportedOperationException();

	}



}