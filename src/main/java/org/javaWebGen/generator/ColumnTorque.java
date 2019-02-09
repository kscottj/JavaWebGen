package org.javaWebGen.generator;

/**
 * Meta data for a data source in torque.xml file SEE
 * hhttps://db.apache.org/ddlutils/schema/database.dtd.org.html
 * 
 * @author kevin
 *
 */
public class ColumnTorque {

	public int type;
	public boolean isRequired = false;;
	public boolean isPrimaryKey = false;
	public String sqlType;
	public String size;
	public String colName;
	private String description;
	public boolean isKey;

	public String getColName() {
		return colName;
	}

	public void setColName(String name) {
		this.colName = name;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isKey() {
		return isKey;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

}
