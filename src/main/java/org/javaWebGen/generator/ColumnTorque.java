package org.javaWebGen.generator;

public class ColumnTorque {


 private String type;
 private boolean isRequired=false;;
 private boolean isPrimaryKey=false;
 private String sqlType;
 private String size;
 public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
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
private String description;
 
 
}
