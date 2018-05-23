/*
Copyright (c) 2015-2017 Kevin Scott

Permission is hereby granted, free of charge, to any person obtaining a copy of 
this software and associated documentation files (the "Software"), to deal in 
the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE.
*/

package org.javaWebGen.generator;

import java.sql.*;
import java.util.*;
import java.io.*;

import org.apache.commons.text.StringSubstitutor;

import org.javaWebGen.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Generated DAO objects from the database configuration(torque)XML. 
 * @author Kevin scott
 * Database classes generated directly by this class are not tested and may not work.  
 * Will use NATIVE GAE datastore API.  Can only handle a single key(primary) due to
 * the way tables are setup in GAE.
 * @see CodeGenerator
 */
public class GenerateGaeDao extends CodeGenerator {
    
    public static final String VERSION="GenerateGaeDao v0_55";
    private static final Logger log = LoggerFactory.getLogger(GenerateGaeDao.class);
    private String className=null;
    private String subClassName=null;   
    

    private String classTemplate=
        "/*\n"+
        "Copyright (c) 2018 Kevin Scott All rights  reserved."+
        " Permission is hereby granted, free of charge, to any person obtaining a copy of \n"+
        " this software and associated documentation files (the \"Software\"), to deal in \n"+
        " the Software without restriction, including without limitation the rights to \n"+
        " use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies\n"+
         "of the Software, and to permit persons to whom the Software is furnished to do \n"+
        " so.\n"+
        "\n"+
       " THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR \n"+
       " IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, \n"+
       " FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE \n"+
       " AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER \n"+
       " LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, \n"+
       " OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE \n"+
       " SOFTWARE.\n "+
       "*/ \n"+
        "/** data Object talks to DB **/\n"+
        "package org.javaWebGen.data.dao;\n\n"+
        "import java.util.*;\n"+
        "import javax.annotation.Generated;\n"+
        "import com.google.appengine.api.datastore.DatastoreService;\r\n" + 
        "import com.google.appengine.api.datastore.DatastoreServiceFactory;\r\n" + 
        "import com.google.appengine.api.datastore.Entity;\r\n"+
        "import com.google.appengine.api.datastore.EntityNotFoundException;\n" + 
        "import com.google.appengine.api.datastore.PreparedQuery;\r\n" + 
        "import com.google.appengine.api.datastore.Query;\r\n"+
        "import com.google.appengine.api.datastore.Key;\r\n" + 
        "import com.google.appengine.api.datastore.KeyFactory;" + 
        "import org.javaWebGen.data.bean.*;\n"+
        "import org.javaWebGen.exception.DBException;\n"+
        "import org.javaWebGen.data.FormBeanAware;\n"+
        "import org.javaWebGen.data.DaoAware;\n"+
        "import org.javaWebGen.data.GAEMappingAware;\n"+

        "/******************************************************************************\n"+
        "* WARNING this class is generated by "+VERSION+" based on Database schema     \n"+
        "* This class should not be modified, but may be extended.\n" +
        "  This class will be regenerated if the database schema is changed. \n"+
        "* @author Kevin Scott                                                        \n"+
        "*******************************************************************************/\n"+
        "@Generated(value = { \"org.javaWebGen.generator.GenerateGaeDao\" })\n" +
        "public abstract class ${javaWebGen.className} implements DaoAware, GAEMappingAware { \n"+
        "\tpublic static final int PAGE_SIZE=100;\n "+
    	"//begin private Vars\n"+
        "${javaWebGen.vars}\n"+
        "\tpublic ${javaWebGen.className}(){}\n"+
        "//find by Primary Key\n"	 +
        "${javaWebGen.finder}\n"+
        "//begin insert (create)\n"+
        "${javaWebGen.insert}\n"+
        "//begin update(store)\n"+
        "${javaWebGen.update}\n"+
        "//begin delete\n"+
        "${javaWebGen.delete}\n"+
        "//begin find All \n"+
        "${javaWebGen.findAll}\n"+
        "//GAE ResultSetHandler \n"+
        "${javaWebGen.resultHandle}\n"+
        "}\n";
        
        private String subClassTemplate=
        "/*\n"+
        " THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR \n"+
        " IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, \n"+
        " FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE \n"+
        " AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER \n"+
        " LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, \n"+
        " OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE \n"+
        " SOFTWARE.\n "+
        "package org.javaWebGen.data.dao;\n\n"+
       
        "/******************************************************************************\n"+
        "* This class is generated by "+VERSION+" based on Database schema     \n"+
        "* This class <b>should</b> be modified.  In general your Finder methods go \n"+
        "* in this class and any required db operations.  This class will not get\n"+
        "* regenerated and is just generated as a place holder.\n"+
        "* @author Kevin Scott                                                        \n"+
        "* @version $Revision: 1.00 $                                               \n"+
        "*******************************************************************************/\n"+
        "public class  ${javaWebGen.subClassName} extends ${javaWebGen.className} { \n"+
        "}\n";
    
    
    
    /**
    *
    */
    public void init(){
    	classTemplate=getTemplate(classTemplate);
    	subClassTemplate=getTemplate(subClassTemplate);
    	
        }

    /************************************
    *gen privateVars
    *************************************/
    protected String makeVars(String[] cols, int[] types){
        
        String text="";
            

        return text;
    }  
    /************************************
    *gen find by primarykey
    *************************************/
    protected String makeFindByPrimaryKey(String[] keys, int[] types){
        String beanName=DataMapper.formatClassName(getTableName() );
       // String varName=DataMapper.formatVarName(getTableName() )+"Id";
       // ArrayList <String> primaryKeys=getPrimaryKeys();
        //int[] primaryKeysTypes=getPrimaryKeyTypes();
        String text=
        "\n\t/***********************************************************************************************\n"+
        "\t* Get a data bound JavaBean populated with data from table/kind "+beanName+"\n"+
        "\t* @param key id to find\n"+		
        "\t* @return data bound JavaBean with data populated\n"
        +"\t**************************************************************************************************/\n"+
        "\tpublic "+beanName+" findByPrimaryKey(Long id) throws DBException{\n"+
        "\t\tif(id==null){throw new DBException(DBException.DAO_ERROR,\""+beanName+" id=null\");}\n"+
        "\t\tDatastoreService datastore = DatastoreServiceFactory.getDatastoreService();\n" +        
        "\t\tEntity entity=null;\n"+
        "\t\ttry {\r\n" + 
        "\t\t\tKey key = KeyFactory.createKey(\""+beanName+"\",id);\n" +  
        "\t\t\tentity =datastore.get(key);\n" +
        "\t\t} catch (EntityNotFoundException e) {\r\n" + 
        "\t\t\t	throw new DBException(DBException.NOT_FOUND_ERROR,e);\r\n" + 
        "\t\t}\r\n" + 
        "\t\treturn this.mapRow(entity);\r\n" + 
        "\t} //find by primary key\n";
        
        return text;
    }
    
    /************************************
    *gen create(INSERT)
    *************************************/
    protected String makeInsert(String[] cols, int[] types){
        String beanName=DataMapper.formatClassName(getTableName() );
        int[] keyTypes=this.getPrimaryKeyTypes();
        String javaType=DataMapper.getJavaTypeFromSQLType(keyTypes[0]);
        //ArrayList <String> primaryKeys=getPrimaryKeys();
        String text=null;
        if(keyTypes.length==1) {
	        text=
	        "\n\t/***************************************************************\n"+
	        "\t* Insert data into Datastore with kind/table "+beanName+"\n"+
	        "\t* @param bean data bound JavaBean to stored in the Datastore \n"+
	        "\t* @return key/id of created row\n"+
	        "\t******************************************************************/\n"+
	        "\tpublic "+javaType+" insert("+beanName+" bean) throws DBException{\n"+
	        "\t\tDatastoreService datastore = DatastoreServiceFactory.getDatastoreService();\r\n"+
	        "\t\tLong id=null;\r\n" +
			"\t\ttry{\n" + 
	        "\t\t\tEntity entity = new Entity(\""+beanName+"\");\n"+
	        "\t\t\tmapEntity(entity,bean);\n"+
	        "\t\t\tdatastore.put(entity);\n"+
	        "\t\t\tid=entity.getKey().getId() ;\n"+
	        "\t\t\tentity.setProperty(\""+cols[0]+"\", id);\n"+    
	        "\t\t\tdatastore.put(entity);\n" +
			"\t\t}catch(Exception e){\n"+
		    "\t\t\tthrow new DBException(DBException.DAO_ERROR,e);\n"+
			"\t\t}\n" + 
	        "\t\t\treturn id;\n"+
	        "\t}\n" ;
        }else {
        	text="\tpublic "+javaType+" insert("+beanName+" bean) throws DBException{"
        			+ "\tthrow new DBException(DBException.DAO_ERROR,\"Can only use mapping with on key found "+keyTypes.length+"\");\n "
        			+ ""
        			+ "\t}\n"
        			+ "}\n";
        }
        return text;
    }
    /*******************************************
    *get column type 
    *@param collumn name
    *@return SQL Type
    ********************************************/
   /* private int getColTypeByName(String colName){
    	String colNames[]=getColNames();
    	int[] colTypes = getColTypes();
        for(int i=0 ;i< colNames.length;i++){
            if(colNames[i].equals(colName)){
                return colTypes[i];
            }
        }
        return -1;
    }
    */
    
    /************************************
    *gen update(store)
    *************************************/
    protected String makeUpdate(String[] cols, int[] types){
         String beanName=DataMapper.formatClassName(getTableName() );
         //String getter=".get"+beanName+"Id()";
        //ArrayList <String> primaryKeys=getPrimaryKeys();
        int[] primaryKeysTypes=getPrimaryKeyTypes();
        String text=null;
        if(primaryKeysTypes.length==1) {
        	//String method=DataMapper.formatMethodName(cols[0]);
        	String getter="get"+DataMapper.formatMethodName(cols[0])+"()";
	        text=
	        "\n\t/**********************************************************\n"+
	        "\t* Updates the Datastore with a bound JavaBean with a kind/table of "+beanName+" \n"+
	        "\t* @param  bean data bound JavaBean to update kind/table\n"+		
	        "\t* @return number row changed\n"+
	        "\t**************************************************************/\n"+
	        "\tpublic int update("+beanName+" bean) throws DBException{\n"+
			"\t\tDatastoreService datastore = DatastoreServiceFactory.getDatastoreService();\r\n"+       
	        "\t\tKey key = KeyFactory.createKey(\""+beanName+"\", bean."+getter+" );\r\n" + 
	        "\t\tEntity entity;\r\n" + 
	        "\t\ttry {\r\n" + 
	        "\t\t\tentity = datastore.get(key);\r\n" +
	        "\t\t} catch (EntityNotFoundException e) {\r\n" + 
	        "\t\t\t	throw new DBException(DBException.NOT_FOUND_ERROR,e);\r\n" + 
	        "\t\t}\r\n" + 
	        "\t\tdatastore.put(entity);\n"
	        + "\t\treturn 1;\n"+
	        ""+
	        "\t}\n" ;

        }else {
        	text="\tpublic int update("+beanName+" bean) throws DBException{"
    			+ "\tthrow new DBException(DBException.DAO_ERROR,\"Can only use mapping with one key per kind/table "+primaryKeysTypes.length+"\");\n "
    			+ ""
    			+ "\t}\n"
    			+ "}\n";
        }
        return text;
    }
    /************************************
     *gen delete
     *************************************/
    protected String makeDelete(String[] cols, int[] types){
         String beanName=DataMapper.formatClassName(getTableName() );
        // ArrayList <String> primaryKeys=getPrimaryKeys();
         int[] primaryKeysTypes=getPrimaryKeyTypes();
         
         String text= null;
         if(primaryKeysTypes.length==1) {
        	 //String method=DataMapper.formatMethodName(cols[0]);
        	 String getter="get"+DataMapper.formatMethodName(cols[0])+"()";
	         text=
	         "\n\t/*********************************************************\n"+
	         "\t* Deletes an entity from  a table/kind "+beanName+" from Datastore\n"+
	         "\t* @param bean data bound JavaBean with data to delete\n"+		 
	         "\t* @return number of rows changed\n"+
	         "\t*************************************************************/\n"+
	         "\tpublic int delete("+beanName+" bean) throws DBException{\n"+
	         "\t\tDatastoreService datastore = DatastoreServiceFactory.getDatastoreService();\r\n"+    
		        "\t\tKey key = KeyFactory.createKey(\""+beanName+"\", bean."+getter+" );\n" + 
		        "\t\tEntity entity;\r\n" + 
		        "\t\ttry {\r\n" + 
		        "\t\t\tentity = datastore.get(key);\r\n" +
		        "\t\t\tdatastore.delete(entity.getKey() );\n" +
		        "\t\t} catch (EntityNotFoundException e) {\r\n" + 
		        "\t\t\t	throw new DBException(DBException.NOT_FOUND_ERROR,e);\r\n" + 
		        "\t\t}\r\n" + 
	 		 "\t\treturn 1;\n"+
	         "\t}\n";
         }else {
         	text="\tpublic int delete(\"+beanName+\" bean) throws DBException{"
        			+ "\tthrow new DBException(DBException.DAO_ERROR,\"Can only use mapping with on key found "+primaryKeysTypes.length+"\");\n "
        			+ ""
        			+ "\t}\n"
        			+ "}\n";
         }

         return text;
     }
     /************************************
      *gen findAll
      *************************************/
    protected String makeFindAll(String[] cols, int[] types){
          String beanName=DataMapper.formatClassName(getTableName() );

          String text=
          "\n\t/***************************************************\n"+
          "\t*List all data with data populated from table/kind "+beanName+"\n"+
          "\t*@return list of data bound JavaBeans with data\n"+
          "\t******************************************************/\n"+
          "\tpublic List<"+beanName+"> findAll() throws DBException{\n"+
	  	  "		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();\r\n" + 
	  	  "		List<"+beanName+"> list =  new LinkedList<"+beanName+">();\r\n" + 
	  	  "		Query q = new Query(\""+beanName+"\");\r\n" + 
	  	  "		PreparedQuery pq = datastore.prepare(q);\r\n" + 
	  	  "		for(Entity entity:pq.asIterable() ) {\r\n" + 
	  	  "			list.add(mapRow(entity) );\r\n" + 
	  	  "		}\r\n" + 
	  	  "		return list;\n"+
	  	  "\t} //findAll\n";
          return text;
      }
    /**
     * 
     * @param keys
     * @param types
     * @return primary key variables
     */
    protected String makePkeyVars(List <String> keys, int[] types){
        String text="";
        if(keys.size() >0){
            for(int i=0;i<keys.size();i++){
                //int type =getColTypeByName(keys.get(i).toString() );
                int type=types[i];
                String name=DataMapper.formatVarName(keys.get(i).toString() );
                switch (type){
                    case Types.CHAR:
                    case Types.VARCHAR:
                    case Types.LONGVARCHAR:
                        text+="\t\tString "+name+"=dataBean.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.DATE:
                    case Types.TIMESTAMP:
                        text+="\t\tjava.util.Date "+name+"=dataBean.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.BIT:
                        text+="\t\tboolean "+name+"=dataBean.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.TINYINT:
                        text+="\t\tbyte "+name+"=dataBean.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.SMALLINT:
                        text+="\t\tshort "+name+"=dataBean.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.INTEGER:
                        text+="\t\tint "+name+"=dataBean.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.BIGINT:
                        text+="\t\tlong "+name+"=dataBean.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.REAL:
                    	text+="\t\tfloat "+name+"=dataBean.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                    	break;
                    case Types.FLOAT:
                    case Types.DOUBLE:
                        text+="\t\tdouble "+name+"=dataBean.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.NUMERIC:
                    case Types.DECIMAL:                 
                        text+="\t\tBigDecimal "+name+"=dataBean.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                    break;
                }//end switch                
                
            }
        }else{
            return "";
        }
        return text;
    }
    
    /**************************************************
    *build class based on template
    ********************************************************/
    protected String buldClass() throws Exception{

        
        String[] colNames=getColNames();
        int[] colTypes= getColTypes();
        
        String vars = makeVars(colNames,colTypes);
        String finder = makeFindByPrimaryKey(colNames,colTypes);
        String insert = makeInsert(colNames,colTypes);
        String update = makeUpdate(colNames,colTypes);
        String delete = this.makeDelete(colNames,colTypes);
        String findAll = this.makeFindAll(colNames,colTypes);
        String resultHandle = this.makeResultHandle(colNames,colTypes);
 
    	HashMap<String,String>valueMap = new HashMap<String,String>();
     	valueMap.put("javaWebGen.className", className );
     	valueMap.put("javaWebGen.vars", vars );
     	valueMap.put("javaWebGen.finder", finder );
     	valueMap.put("javaWebGen.insert", insert );
     	valueMap.put("javaWebGen.update", update );
     	valueMap.put("javaWebGen.delete", delete );
     	valueMap.put("javaWebGen.findAll", findAll );
     	valueMap.put("javaWebGen.resultHandle", resultHandle );
     	StringSubstitutor sub = new StringSubstitutor(valueMap);

        return sub.replace(classTemplate);
    }
    private String makeResultHandle(String[] colNames, int[] colTypes) {
    	String beanName=DataMapper.formatClassName(getTableName() );
    	 
		String text=
			  ""
			  + "\t/*****************************************************\n"
			  + "\t*Map entity to JTO DataBean\n"
			  + "\t*@param entity GAE entity aka table\n"
			  + "\t*@return  bean data bean that maps to entity\n"
			  + "\t******************************************************/\n"
			  + "\t@Override\n"
			  + "\tpublic "+beanName+" mapRow(Entity entity) {\r\n"  
			  +"\t\t"+beanName+" bean = new "+beanName+"();\r\n"  
			  +"\t\tMap<String, Object> map = entity.getProperties();\r\n";
			for(int i=0;i<colNames.length;i++) {
                int type=colTypes[i];
                String name=DataMapper.formatClassName(colNames[i].toString() );
            	String varValue=DataMapper.formatVarName(colNames[i]); //need to use this to match JDO nameing standards
                String dataTypeClass=null;
               

                switch (type){
                    case Types.CHAR: 
                    case Types.VARCHAR:
                    case Types.LONGVARCHAR:
                    	dataTypeClass="String";
                        break;
                    case Types.DATE:
                    case Types.TIMESTAMP:
                    	dataTypeClass="java.util.Date";
                        break;
                    case Types.BIT:
                    case Types.TINYINT:
                    case Types.SMALLINT:
                    case Types.INTEGER:
                    case Types.BIGINT:
                    case Types.NUMERIC:
                    	dataTypeClass="Long";
                    	break;
                    case Types.REAL:
                    case Types.FLOAT:
                    case Types.DOUBLE:
                    case Types.DECIMAL:                 
                    	dataTypeClass="Double";
                    	break;
                }//end switch 
                boolean isKey=false;
                for(String keyName:this.getPrimaryKeys()) {
                	if(keyName.equals(colNames[i]) ){
                		isKey=true;
                	}
                }

                if(isKey) {					

                	text+="\t\tLong keyId=entity.getKey().getId();\n";
                	text+="\t\tbean.set"+name+"(keyId);\n";
                }else {
	                text+="\t\tbean.set"+name+"( ("+dataTypeClass+") map.get(\""+varValue+"\") );\n";
	            }
			}
		
			
			text+=
			   
			    "\t\treturn bean;\r\n"
			  + "\t}\n"
			  + "\t/*************************************************************\n"
			  + "\t*map entity to JTO DataBean\n"
			  + "\t*@param entity GAE entity aka table\n"
			  + "\t*@param bean data bound bean with data\n"
			  + "\t**************************************************************/\n"
			  + "\t@Override\n"
			  + "\tpublic void mapEntity(Entity entity,FormBeanAware jtoBean) {\r\n"
			  + "\t\t"+beanName+" bean = ("+beanName+") jtoBean;\n";
				
				for(int i=0;i<colNames.length;i++) {
					String name=DataMapper.formatClassName(colNames[i].toString() );
					String varValue=DataMapper.formatVarName(colNames[i]); //need to use this to match JDO nameing standards
					if(this.getIsKeyArray()[i]) {
						text+="\t\t//key "+varValue+" no need to update value\n";
					}else {
						text+="\t\tentity.setProperty(\""+varValue+"\",bean.get"+name+"() );\r\n";
					}
					
	                
	  			  
				}
			  
  
			  text+="\t}\n" 	;	
			 

		 
		return text;
	}

	/**************************************************
    *build class based on template
    ********************************************************/
    protected String buldSubClass() throws Exception{
    
        String[] p = new String[2];
        p[0]= subClassName;
        p[1]= className;

        //String classText = StringUtil.replace(subClassTemplate,p);
    	HashMap<String,String>valueMap = new HashMap<String,String>();
     	valueMap.put("javaWebGen.subClassName", subClassName );
     	valueMap.put("javaWebGen.className", className );

  	
     	StringSubstitutor sub = new StringSubstitutor(valueMap);
        return sub.replace(subClassTemplate);
    }  
    /**
    *
    */
    protected void execute() throws UtilException{
    	try{
    		className=DataMapper.formatClassName(getTableName() )+"DAOImpl";
    		subClassName=DataMapper.formatClassName(getTableName() )+"DAO";
	        writeJavaClass(buldClass() );
	        writeSubClass(buldSubClass() );
    	}catch(Exception e){
    		throw new UtilException(UtilException.CODE_GENERATOR_EXEC,e);
    	}
    }
    
    /**
    *Write out generated class
    */
    private void writeJavaClass(String text) throws IOException{
      

        ArrayList <String> primaryKeys=getPrimaryKeys();
        if(primaryKeys.size() >0){
            String fileName=getFilePath()+File.separator+className+".java";
        	File file=new File(fileName);
	        //if(!file.exists() ){
	            FileWriter fw = new FileWriter(file);
	            PrintWriter out = new PrintWriter(fw);
	            out.print(text);
	            out.flush();
	            out.close();
                log.info("---write file="+fileName+"---");
            //}
        }
    }

    /**
    *Write out generated class
    */
    private void writeSubClass(String text) throws IOException{
       

        ArrayList <String> primaryKeys=getPrimaryKeys();

        if(primaryKeys.size() >0){
        	String fileName = getFilePath()+File.separator+subClassName+".java";
            File file = new File(fileName);
            if(!file.exists() ){
                //String fileName=filePath+File.separator+name+".java";
                FileWriter fw = new FileWriter(file);
                PrintWriter out = new PrintWriter(fw);
                out.print(text);
                out.flush();
                out.close();
                log.info("---write file="+fileName+"---");
            }
        }
    }

 
 
    
    /**********************************************************
    *
    **********************************************************/
    protected String getWhereClause(List <String> keys, int[] types) {
        String sql=" WHERE ";
        boolean isFirst=true;
        for(String keyName:keys){
	        if(keys.size() >0){
	        	if(isFirst){
	        		
	        	}else{
	        		isFirst=false;
	        		sql+=",";
	        	}
	        	sql+=keyName+"=?";
	        }
        }
        return sql+"\";";
    }

	/**
     * main
     */
     public static void main(String[] args) {
         try{ 	
         	GenerateGaeDao app = new GenerateGaeDao();
         	app.setCmdParms(args);
         	app.init();
            app.processXmlFile(app.getFileName());
        	}catch(Exception e){
        		log.error("main",e);
        		System.exit(911);
        	}
     }
 /**
  * 
  */
     @Override
 	public void useage() {
         System.out.println("To Process all tables in a text file");
         System.out.println("USAGE GenerateJDBC -f <filename> <path>");
         System.exit(1);
 	}

@Override
protected void postExecute() throws UtilException {
	//no extra files need for simple jdbc with dbUtils
	
}

}