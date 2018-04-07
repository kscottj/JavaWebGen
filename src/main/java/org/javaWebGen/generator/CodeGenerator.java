/*
 * =================================================================== *
 * Copyright (c) 2006 Kevin Scott All rights  reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 * if any, must include the following acknowledgment:
 * "This product includes software developed by "Kevin Scott"
 * Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Kevin Scott must not be used to endorse or promote products
 * derived from this software without prior written permission. For
 * written permission, please contact kevscott_tx@yahoo.com
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL KEVIN SCOTT BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.javaWebGen.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.javaWebGen.exception.UtilException;
import org.javaWebGen.exception.WebAppException;
import org.javaWebGen.util.ColMeta;
import org.javaWebGen.xml.DOMHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
/**
 * Base Code generator class that handles common functions among the code generators
 *
 * @author Kevin Scott
 *
 */
public abstract class CodeGenerator {
	private final static Logger log= LoggerFactory.getLogger(CodeGenerator.class);
	private String templateName = "";
	private String schemaXml= "";
	private String filepath = "";

	//private final static String SQL_SELECT = "Select * from ";

	private String[] colNames;

	private int[] colTypes;
	
	private static boolean[] isKeyArray=null;

	private ArrayList <String> primaryKeys=new ArrayList<String>();
	//private ArrayList <ColMeta> colList=new ArrayList<ColMeta>();;
	private List<String> tableList =new ArrayList<String>();
	private int[] primaryKeyTypes;
	private String tableName="";
	private HashMap<String, String> typeMap;
	private HashMap<String, String> sizeMap;
	/**TAB spacer (IE 4 spaces */ 
	public static final String TS="    "; 
	/**
	 * 
	 * @param templateName template name
	 */
	public void setTemplateName(String templateName){
		this.templateName=templateName;
	}
	/**
	 * 
	 * @param filepath
	 */
	public void setFilePath(String filepath){
		this.filepath=filepath;
	}
	/**
	 * Map data types from current table using JDBC MetaData
	 * no longer used due to torque xml file that allows JDO or JDBC
	 * @throws SQLException
	 */
	@Deprecated
	protected void mapData() throws SQLException {
		log.error(this + ".+mapData()");
		throw new SQLException("not implemented anymore and code untested now");
		/*
		Object[] parms = new Object[1];
		parms[0] = this.tableName;
		String sql =SQL_SELECT+getTableName();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = getDataManager().getConnection();
			int cols = 0;

			//RCollection list = getResultSet(con,SQL);

			rs = getResultSet(con, sql);
			ResultSetMetaData meta = rs.getMetaData();
			cols = meta.getColumnCount();

			Util.debug("found columns=" + cols);
			colNames = new String[cols];
			colTypes = new int[cols];

			for (int i = 0; i < cols; i++) {
				colNames[i] = meta.getColumnName(i + 1);
				if (meta.isCurrency(i + 1)) {
					colTypes[i] = Types.DECIMAL;
				} else {
					colTypes[i] = meta.getColumnType(i + 1);
					Util.debug("col Type =" + this.colTypes[i]);
				}
			}
			primaryKeys = DataMapper.getPrimaryKeys(con, tableName);
			primaryKeyTypes = DataMapper.getPrimaryType(primaryKeys, colNames,
					colTypes);
		} catch (SQLException se) {
			Util
					.error("Unable to map table (did it have any colums or is it a sequence table?");
			Util.debug("SQL|" + sql);
			throw se;
		} finally {
			try {
				//if (rs != null)
				//	rs.close();
				if (con != null)
					close(con);
			} catch (Exception ce) {
				Util.error("Trying to close db resource", ce);
			}
		}
		
		Util.leave(this + ".mapData()");*/
	}
	/**
	 * Map data types from current table element 
	 * @param table
	 * @throws UtilException generic error
	 */
	protected void mapXmlData(Element table) throws UtilException {
		log.debug(this + ".+mapXmlData()");
		//Object[] parms = new Object[1];
		//parms[0] = this.tableName;
		//String sql =SQL_SELECT+getTableName();
		//Connection con = null;
		//ResultSet rs = null;
	
		try {
			List<Element> colList = DOMHelper.getElements(table,"column");

			log.debug("found columns=" + colList.size());
			colNames = new String[colList.size()];
			colTypes = new int[colList.size()];
			typeMap =new HashMap<String,String>();
			sizeMap=new HashMap<String,String>();
			isKeyArray= new boolean[colList.size()];
			primaryKeys=new ArrayList<String>();
			
			ArrayList<Integer> pkeyTypeList  = new ArrayList<Integer>();
			//primaryKeys = DataMapper.getPrimaryKeys(con, tableName);
			for (int i = 0; i < colList.size(); i++) {
				ColMeta meta=new ColMeta();
				Element colEl=  colList.get(i);
				colNames[i] = DOMHelper.getAttribute(colEl,"name");
				meta.colName=DOMHelper.getAttribute(colEl,"name");
				
				String type= DOMHelper.getAttribute(colEl,"type");
				String name=DOMHelper.getAttribute(colEl,"name");
				typeMap.put( name, type);
				String size=DOMHelper.getAttribute(colEl,"size") ;
				if(size!=null){
					log.debug("sizMap.name="+name+" value="+size+"=");
					sizeMap.put(name, size);
				}
				String pkey=DOMHelper.getAttribute(colEl,"primaryKey");
				
				if(pkey!=null && pkey.equals("true") ){
					meta.isKey=true;
					isKeyArray[i]=true;
					
					primaryKeys.add(DOMHelper.getAttribute(colEl,"name") );
					//only String and Long allowed
					if ( type.equals("VARCHAR") ){
						pkeyTypeList.add(Types.VARCHAR);
					}else if( type.equals("BIGINT") ){
						pkeyTypeList.add(Types.BIGINT);
					}else if( type.equals("INTEGER") ){
						pkeyTypeList.add(Types.INTEGER);
					}else{
						throw new UtilException(UtilException.CODE_GENERATOR_INIT, "primary key not a int, long, or String");
					}
	
				}
				if ( type.equals("BIGINT") ){
					colTypes[i]=Types.BIGINT;
				}else if ( type.equals("VARCHAR") ){
					colTypes[i]=Types.VARCHAR;
				}else if( type.equals("INTEGER") ){
					colTypes[i]=Types.INTEGER;
				}else if( type.equals("DECIMAL") ){
					colTypes[i]=Types.DECIMAL;
				}else if( type.equals("FLOAT") ){
					colTypes[i]=Types.FLOAT;
				}else if(type.equals("DATE") ){
					colTypes[i]=Types.DATE;
				}else if(type.equals("TIMESTAMP") ){
					colTypes[i]=Types.TIMESTAMP;
					
				}else{
					throw new UtilException(UtilException.CODE_GENERATOR_INIT, "uknown Db field in xml");
				}
				meta.type=colTypes[i];
			}
			
			primaryKeyTypes=new int[pkeyTypeList.size()];
			//primaryKeyTypes = DataMapper.getPrimaryType(primaryKeys, colNames,colTypes);
			for (int i=0;i<pkeyTypeList.size();i++){
				Integer intValue= pkeyTypeList.get(i);
				primaryKeyTypes[i]= intValue.intValue();	
			}
			
			
		} catch (Exception se) {
			log.error("Unable to map table (did it have any colums or is it a sequence table?",se);
			throw new UtilException(UtilException.CODE_GENERATOR_EXEC, "uknown Error",se);
		} finally {
		
		}
		log.trace(this + ".mapData()");
	}
	public List<String> getTableList() {
		return tableList;
	}
	public void setTableList(List<String> tableList) {
		this.tableList = tableList;
	}
	public HashMap<String, String> getTypeMap() {
		return typeMap;
	}
	public void setTypeMap(HashMap<String, String> typeMap) {
		this.typeMap = typeMap;
	}
	public HashMap<String, String> getSizeMap() {
		return sizeMap;
	}
	public void setSizeMap(HashMap<String, String> sizeMap) {
		this.sizeMap = sizeMap;
	}
	/**
	 * Is key(primary key) or not
	 * @return true if it is primary key
	 */
	protected boolean[] getIsKeyArray(){
		return isKeyArray;
	}
	/**
	 * process table list 
	 * 
	 * @param fileName input file name id table list
	 * @throws IOException error reading the file
	 */
	protected void processFile(String fileName) throws IOException,
			UtilException {
		throw new IOException("not used anymore untested code");
		/*String currentTableName = null;
		FileReader fr = new FileReader(fileName);
		BufferedReader in = new BufferedReader(fr);
		currentTableName = in.readLine();

		while (currentTableName != null) {
			try {
				setTableName(currentTableName);
				Util.debug("work on table="+currentTableName );
				//mapData();
				execute();
				currentTableName = in.readLine();
			} catch (Exception e) {
				throw new UtilException(UtilException.CODE_GENERATOR_EXEC, e);
			}
		}
		postExecute();*/
	}

	/**
	 * search cmd args for 
	 * @param cmdArgs command arguments array from main 
	 * @param cmdSwitch command line switch you are looking for
	 * @return cmdSwitch value from command line null of not found
	 */
	public String searchCmdParm(String[] cmdArgs, String cmdSwitch){
		
		for(int i=0;i<cmdArgs.length;i++){
			if(cmdSwitch.equals(cmdArgs[i])){
				if(i+1<cmdArgs.length){
					return cmdArgs[i+1];
				}else{
					return "";
				}
			}

		} 
		return null;
	}

	/**
	 * get the params that where passed in on the command line and set the correct vars
	 * 
	 * @param args cmd line parms
	 * @return true if right number of parms passed
	 * @throws Exception 
	 */
	protected boolean setCmdParms(String[] args) throws Exception {
		System.out.println("ARGS=" + args.length);

		if (args.length == 2) {
			schemaXml = args[0];			
			filepath = args[1];

		} else if (args.length == 4 && args[2].equals("-template")) {
			schemaXml = args[0];
			filepath = args[1];
			templateName = args[3];
			log.debug("templateName=" + templateName);
			log.debug("filePath=" + filepath);

		} else {
			useage();
			return false;
		}
		return true;
	}
	protected String getTemplate(String filename,String defaultTemplate) {
		filename=filename+".txt";
		File file = new File(filename);
		if(file.exists()){
			log.info("Using Class Template "+filename);
			FileReader fr;
			BufferedReader in =null;
			try {
				fr = new FileReader(file);
				in = new BufferedReader(fr);
				StringBuffer inputBuffer = new StringBuffer();
				String input=null;
				if( (input=in.readLine()) !=null ){
					inputBuffer.append(input);
				}
			
				return inputBuffer.toString();
			} catch (Exception e) {
				log.warn("Unable to read in template "+filename+" will use the default Template instead");
			}finally{
				try {
					if(in!=null)
						in.close();
					
				} catch (IOException e) {
					log.error("error closing file",e);
				}
			}
		}	
		return defaultTemplate;
	}
	/**
	 * Get the template  from a file if it exists if not 
	 * return the default template
	 *  
	 * @param defaultTemplate text
	 * @return class template text
	 */
	protected String getTemplate(String defaultTemplate) {
		 
	
		return getTemplate(getScemaXml(),defaultTemplate);
	}

	
	/**
	 * Return the type of variable. In other words, a Integer SQL TYPE would
	 * return a int
	 * 
	 * @param keys column names
	 * @param types sql type
	 * @return java object that matches sql type
	 */
	protected String getVars(List <String> keys, int[] types) {
		String text = "";
		int size = keys.size();

		for (int i = 0; i < size; i++) {

			if (i >= 1) {
				//Util.debug("add , ");
				text += ",";
			}

			String name = DataMapper.formatVarName(keys.get(i).toString());

			int type = types[i];
			log.debug("**keys=" + keys.get(i) + " type=" + type);
			String javaType=DataMapper.getJavaTypeFromSQLType(type);
			text += javaType+" "+name;
		}// end for loop
		return text;
	}
	
	public HashMap<String,String> getSqlTypeMap(){
		return this.typeMap;
	}
	
	   /**********************************************************
	    *generate a string to convert from a string to correct
	    *Object type
	    *@param key primary key
	    *@param type SQL type
	    *@return text for generated class
	    **********************************************************/
	    protected String getPrimayParmCast(String key, int type) {
	        	String text="";
	        	String name=DataMapper.formatVarName(key.toString() );
	        	
	        	log.debug("pkey="+name +"|type="+type);
	            switch (type){
	                case Types.CHAR:
	                case Types.VARCHAR:
	                case Types.LONGVARCHAR:
	                	text+=name; 
	                    break;
	                case Types.DATE:
	                case Types.TIMESTAMP:
	                	text+="java.text.DateFormat.getInstance().parse("+name+")"; 
	                    break;
	    			case Types.BOOLEAN:
	    			case Types.BIT:
	    				text += "new Boolean.parseBoolean(" + name+")";
	    				break;
	    			case Types.TINYINT:
	    				text+="Byte.parseByte("+name+")"; 
	    				break;
	                	
	                case Types.SMALLINT:
	                	text+="Short.parseShort("+name+")"; 
	                	break;
	                case Types.INTEGER:
	                	text+="Integer.parseInt("+name+")"; 
	                break;
	                case Types.BIGINT:    
	                	text+="Long.parseLong("+name+")"; 
	                	break;
	    			case Types.REAL:
	                	text+="Float.parseFloat("+name+")"; 
	    				break;
	    			case Types.FLOAT:
	    			case Types.DOUBLE:
	                	text+="Double.parseDouble("+name+")"; 
	    				break;
	    			case Types.NUMERIC:
	                case Types.DECIMAL:
	                	text+="new BigDecimal("+name+")"; 
	                    break;
	                case Types.LONGVARBINARY:
	                case Types.BINARY:    
	                case Types.BLOB:  
	                case Types.JAVA_OBJECT :  
	                case Types.CLOB : 
	                case Types.OTHER :
	                    log.warn("can not use a BLOB as a key=");
	                    break;
	            }//end switch                
	            
	        return text;
	    }
		/**
		 * parse XML config file
		 * currently does not do anything reserved for future use
		 * @param xmlfile
		 * @return document 
		 * @throws WebAppException
		 */
		protected Document parseXml(String xmlfile) throws WebAppException,
				IOException {
			Document doc;
			try {
				doc = DOMHelper.readFromFile(xmlfile);
			} catch (SAXException e) {
				throw new WebAppException(WebAppException.GENERIC,e);
			}
		
			 
			return doc;
			
		}	
		/**
		 * process table list 	
		 * @param fileName input file name id table list
		 * @throws IOException
		 * @throws UtilException
		 * @throws WebAppException
		 */
		protected void processXmlFile(String fileName) throws IOException,UtilException, WebAppException {
			String currentTableName = null;
			//Document root=parseXml("torque.xml");
			Document root=parseXml(fileName);
			Element database=root.getDocumentElement();
			List<Element> tableElements=DOMHelper.getElements(database, "table");
			
			int size=tableElements.size();
			for (int i=0;i<size;i++){
				Element table=  tableElements.get(i);

				try {
					currentTableName=DOMHelper.getAttribute(table, "name");
					setTableName(currentTableName);
					tableList.add(currentTableName);
					log.debug("work on table="+currentTableName );
					mapXmlData(table);
					execute();
					//currentTableName = in.readLine();
				} catch (Exception e) {
					throw new UtilException(UtilException.CODE_GENERATOR_EXEC, e);
				}
			}
			postExecute();
		}
		
	private void setTableName(String currentTableName) {
			this.tableName=currentTableName;
			
	}
	protected String getTableName() {
		return this.tableName;
		
	}
	/**
	 * @return list of current entity names (tables) in torque XML file
	 */
	public List<String> getEntityNames(){
		return tableList;
	}

	/**
	 * return file path ie where to write the output java source
	 * 
	 * @return file path
	 */
	protected String getFilePath() {
		return filepath;

	}

	/**
	 * get current table name
	 * 
	 * @return table name
	 */
	protected String getScemaXml() {
		return schemaXml;
	}
	/**
	 * get current torque schema XML file
	 * 
	 * @return schema XML file
	 */
	protected String getFileName() {
		return schemaXml;
	}

	/**
	 * sets the current table name
	 * 
	 * @param table
	 */
	protected void setScemaXml(String table) {
		schemaXml = table;
	}

	/**
	 * 
	 * @return col names
	 */
	protected String[] getColNames() {
		return colNames;
	}

	/**
	 * 
	 * @return col SQL types
	 */
	protected int[] getColTypes() {
		return colTypes;
	}

	/**
	 * 
	 * @return list of primary keys col names
	 */
	protected ArrayList <String> getPrimaryKeys() {
		return  primaryKeys ;
	}

	/**
	 * 
	 * @return primary key SQL types
	 */
	protected int[] getPrimaryKeyTypes() {
		return primaryKeyTypes;
	}

	/**
    *Called for each table.  This is expeceted to write java class for each table in config.

	 * @throws UtilException
	 */
	protected abstract void execute() throws UtilException;

	/**
    *Called after looping though all tables-columns
    *Good place to generate files that are not needed for every table
    *IE PMF etc...
	 * @throws UtilException
	 */
	protected abstract void postExecute() throws UtilException;


	/**
	 * print instruction on how to object from the command line to sysout
	 *
	 */
	public abstract void useage();

	/**
	 * run init code ie stuff to setup templates 
	 * @throws UtilException
	 */
	public abstract void init() throws UtilException;
}
