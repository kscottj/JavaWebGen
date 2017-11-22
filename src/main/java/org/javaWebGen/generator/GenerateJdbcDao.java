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

import org.apache.commons.text.StrSubstitutor;
import org.javaWebGen.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generated DAO objects from the database config
 * @author Kevin scott
 * @version $Revsion: 1.2 $
 * Database classes generated directly by this class are not longer used or tested but should work.
 * Now uses Apache Commons
 * DBUtil library
 * @see GenerateSpringJdbcDao
 */

public class GenerateJdbcDao extends CodeGenerator {
    
    public static final String VERSION="GenerateJdbcDao v1_50";
    private static final Logger log = LoggerFactory.getLogger(GenerateJdbcDao.class);
    private String className=null;
    private String subClassName=null;   
    

    private String classTemplate=
        "/*\n"+
        "Copyright (c) 2017 Kevin Scott All rights  reserved."+
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
        "import java.sql.*;\n"+
        "import java.util.*;\n"+
        "import javax.naming.Context;\n"+
        "import javax.naming.NamingException;\n"+
        "import javax.naming.InitialContext;\n"+
        "import javax.sql.DataSource;\n"+
        "import java.math.BigDecimal;\n"+
        "import org.apache.commons.dbutils.QueryRunner;\n"+
        "import org.apache.commons.dbutils.ResultSetHandler;\n"+
        "import org.apache.commons.dbutils.handlers.BeanListHandler;\n"+
        "import org.apache.commons.dbutils.handlers.ScalarHandler;\n"+
        "import org.javaWebGen.data.bean.*;\n"+
        "import org.javaWebGen.exception.DBException;\n"+
        "import org.javaWebGen.data.DAO;\n"+
        "import org.javaWebGen.config.Conf;\n"+
        "import org.javaWebGen.util.SQLHelper;\n"+
        "/******************************************************************************\n"+
        "* WARNING this class is generated by "+VERSION+" based on Database schema     \n"+
        "* This class should not be modified, but may be extended.\n" +
        "  This class will be regenerated if the database schema is changed. \n"+
        "* @author Kevin Scott                                                        \n"+
        "* @version $Revision: 1.00 $                                               \n"+
        "*******************************************************************************/\n"+
        "public abstract class ${javaWebGen.className} extends DAO { \n"+
    	"\tpublic ${javaWebGen.className}(){\n"+
        "\tContext initContext;\n"+
		"\ttry {\n"+
		"\t	initContext = new InitialContext();\n"+
		"\t	this.setDataSource( (DataSource) initContext.lookup(Conf.getConfig().getProperty(DAO.DB_JNDI, \"jdbc.testDB\") ) );\n"+
		"\t	\n"+
		"\t  } catch (NamingException e) {\n"+
		"\t	   throw new RuntimeException(e);\n"+
		"\t  }\n"+
		"\t}\n"+
        "//begin private Vars\n"+
        "${javaWebGen.vars}\n"+
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
        "//DBUtil ResultSetHandler \n"+
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

        "/** data Acees Object talks to DB **/\n"+
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
        ArrayList <String> primaryKeys=getPrimaryKeys();
        int[] primaryKeysTypes=getPrimaryKeyTypes();
        String text=
        "\n\t/**\n"+
        "\t* Warning Generated method. get a DataBean with table data in it\n"+
        "\t* Warning can not handle primary keys that are decimal values\n"+
        "\t* Warning may not handle date primary key correctly yet\n"+
        "\t* @return DataBean with data\n"+
        "\t*/\n"+
        "\tpublic "+beanName+" findByPrimaryKey("+getVars(primaryKeys,primaryKeysTypes)+") throws DBException,SQLException{\n"+
        "\t\tString sql="+beanName+".getSelectSQL()+\" "+getWhereClause(primaryKeys,primaryKeysTypes)+";\n"+
       
        "\t\tQueryRunner run = new QueryRunner(this.getDataSource());\n"+
        "\t\tResultSetHandler<List<"+beanName+"> >  rh = new DataBeanResultSetHandler() ;\n"+
        "\t\tObject[] parms=new Object["+primaryKeys.size()+"];\n";
        for(int i=0;i<primaryKeys.size();i++){
        	String key=primaryKeys.get(i);
        	text+="\t\tparms["+i+"]="+DataMapper.formatVarName(key)+";\n";
        	
        }
        text+="\t\tSQLHelper.dump(sql,parms);\n"+
              "\t\tList<"+beanName+"> list =  run.query(sql, rh,parms );\n"+

        "\t\tif (list!=null && list.size()>0){\n"+
        "\t\t\t return list.get(0);\n"+
        "\t\t}else{\n"+
        "\t\t\t//throw error so a NullPointer does not get used in the model layer\n"+
        "\t\t\t throw new DBException(DBException.WRONG_NUMBER_OF_ROWS,\"no rows returned on find by primary key\");\n"+    
       // "\t\t\t return null;//might need to throw an exception!\n"+
        "\t\t}\n"+
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
        
        String text=
        "\n\t/**\n"+
        "\t* Warning Generated method inserts new DataBean \n"+
        "\t* @param entity DataAware Javabean to store to DB \n"+
        "\t* @return primary key=0\n"+
        "\t*/\n"+
        "\tpublic "+javaType+" insert("+beanName+" entity) throws DBException,SQLException{\n";
        String sql="String sql=\"INSERT INTO "+beanName+"";
      
       
        String parmStr="";
        boolean isFirst=true;
        sql+="(";
        int parmNum=0;
        for(int i=0;i<cols.length;i++){
        	String getter=".get"+DataMapper.formatMethodName(cols[i]); 
        	if(this.getIsKeyArray()[i]){
        		parmStr+="\t\t // leave key field alone entity"+getter+"() );\n";
        	}else{
        		if(isFirst){
        			isFirst=false;
        		}else{
        			sql+=",";
        		}
        		parmStr+="\t\tparms["+parmNum+"]=entity"+getter+"() ;\n";
        		sql+=cols[i]+" ";
        		parmNum++;
        	}
        }
        sql+=")";
        text+="\t\t\tObject[] parms= new Object["+parmNum+"];\n";
 
        sql+=" VALUES(";
        isFirst=true;
        for(int i=0;i<parmNum;i++){
    		if(isFirst){
    			isFirst=false;
    		}else{	
    			sql+=",";
    		}
        	sql+="?";
        }
        sql+=")\";\n";
        text+= parmStr
        	+ "\t\t"+sql  +"\n"
        	+ "\t\tQueryRunner run = new QueryRunner(this.getDataSource() );\n"
        	+ "\t\tSQLHelper.dump(sql,parms);\n"
        	+ "\t\tLong id =run.insert(sql,new ScalarHandler<BigDecimal>(), parms).longValue();\n" 
	        + "\t\treturn id;\n"
        	+ "\t}\n";
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
        ArrayList <String> primaryKeys=getPrimaryKeys();
        //int[] primaryKeysTypes=getPrimaryKeyTypes();
        String text=
        "\n\t/**\n"+
        "\t* Warning Generated method updates the database with a DataBean \n"+
        "\t* @param entity Databean to update DB with\n"+		
        "\t* @return number row changed\n"+
        "\t*/\n"+
        "\tpublic int update("+beanName+" entity) throws DBException,SQLException{\n";
        	 
        String sql="String sql=\"UPDATE "+beanName+" SET ";
        int parmNum=0;
        boolean isFirst=true;
        String parmStr="";
        
        for(int i=0;i<cols.length;i++){
        	String getter=".get"+DataMapper.formatMethodName(cols[i]); 
        	if(this.getIsKeyArray()[i]){
 
        		parmStr+="\t\t // leave key field alone entity"+getter+"() );\n";
        	}else{
        		
        		if(isFirst){
        			isFirst=false;
        		}else{
        			
        			sql+=",";
        		}
        		
        		parmStr+="\t\tparms["+parmNum+"]=entity"+getter+"() ;\n";
        		sql+=cols[i]+"=? ";
        		parmNum++;
        	}
        }
    	for(String keyName:primaryKeys){
    		String getter=".get"+DataMapper.formatMethodName(keyName); 
    		
    		parmStr+="\t\tparms["+parmNum+"]=entity"+getter+"() ;\n";
    		parmNum++;
    	}
        text+="\t\t\tObject[] parms= new Object["+parmNum+"];\n";		
        text+= parmStr
        	+ "\t\t"+sql+getWhereClause(primaryKeys,types) +"\n"
        	+ "\t\tQueryRunner run = new QueryRunner(this.getDataSource() );\n"
        	+ "\t\tSQLHelper.dump(sql,parms);\n"
        	+ "\t\tint rows=run.update(sql,parms);\n" 
	        + "\t\treturn rows; //rows updated\n"
        	+ "\t}\n";
        return text;
    }
    /************************************
     *gen delete
     *************************************/
    protected String makeDelete(String[] cols, int[] types){
         String beanName=DataMapper.formatClassName(getTableName() );
         ArrayList <String> primaryKeys=getPrimaryKeys();
         int[] primaryKeysTypes=getPrimaryKeyTypes();
         String text=
         "\n\t/**\n"+
         "\t* Warning Generated method updates the database with a DataBean \n"+
         "\t* @param entity DataAware javabean with data to delete\n"+		 
         "\t* @return number row changed\n"+
         "\t*/\n"+
         "\tpublic int delete("+beanName+" entity) throws DBException,SQLException{\n"+
         "\t\tObject[] parms=new Object["+primaryKeys.size()+"];\n";
         for(int i=0;i<primaryKeys.size();i++){
         	String key=primaryKeys.get(i);
         	text+="\t\tparms["+i+"]=entity.get"+DataMapper.formatMethodName(key)+"();\n";
         	
         }
         text+="\t\tString sql=\"DELETE FROM "+getTableName()+" "+getWhereClause(primaryKeys,primaryKeysTypes)+";\n"+
 		 "\t\tQueryRunner run = new QueryRunner(this.getDataSource() );\n"+
 		 "\t\tSQLHelper.dump(sql,parms);\n"+
 		 "\t\tint rows=run.update(sql,parms);\n"+
         "\t\treturn rows; \n"+
         "\t} // by primary key\n";
         return text;
     }
     /************************************
      *gen findAll
      *************************************/
    protected String makeFindAll(String[] cols, int[] types){
          String beanName=DataMapper.formatClassName(getTableName() );

          String text=
          "\n\t/***************************************************\n"+
          "\t*Warning Generated method. get a DataBean with table data in it\n"+
          "\t@return DataBean with data\n"+
          "\t******************************************************/\n"+
          "\tpublic List<"+beanName+"> findAll() throws DBException,SQLException{\n"+
          "\t\tString sql="+beanName+".getSelectSQL();\n"+
          "\t\tQueryRunner run = new QueryRunner(this.getDataSource() );\n"+
          "\t\tResultSetHandler<List<"+beanName+"> > h = new BeanListHandler<"+beanName+">("+beanName+".class);\n"+
          "\t\tSQLHelper.dump(sql);\n"+
          "\t\tList<"+beanName+"> list =  run.query(sql, h); \n"+

          "\t\t return list;\n"+
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
     	StrSubstitutor sub = new StrSubstitutor(valueMap);

        return sub.replace(classTemplate);
    }
    private String makeResultHandle(String[] colNames, int[] colTypes) {
    	String beanName=DataMapper.formatClassName(getTableName() );
		String text=
			  "\tprivate final static class DataBeanResultSetHandler implements ResultSetHandler<List<"+beanName+"> > {\n"
			+"\n"	
			+ "\t\t@Override\n" 
			+ "\t\tpublic List<"+beanName+"> handle(ResultSet rs) throws SQLException {	\n" 		
			+ "\t\t\t	List<"+beanName+"> list = new ArrayList<"+beanName+">();\n"
			+ "\t\t\twhile (rs.next()) {\n"
			+ "\t\t\t   "+beanName+" entity = new "+beanName+"();\n"
			+ "\t\t\t	  ResultSetMetaData meta = rs.getMetaData();\n"
			+ "\t\t\t	  int cols = meta.getColumnCount();\n"
			+ "\t\t\t	  Object[] result = new Object[cols];\n"
			+ "\t\t\t	  for (int i = 0; i < cols; i++) {\n"
			+ "\t\t\t      result[i] = rs.getObject(i + 1);\n"
			+ "\t\t\t	  }\n"
			+ "\t\t\t	entity.setData(result);\n"
			+ "\t\t\t	list.add(entity);\n"
			+ "\t\t\t}\n"
			+ "\t\t\treturn list;\n"
			+ "\t\t}\n"
			+ "\t}\n";
		 
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

  	
     	StrSubstitutor sub = new StrSubstitutor(valueMap);
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
                log.debug("---write file="+fileName+"---");
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
         	GenerateJdbcDao app = new GenerateJdbcDao();
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