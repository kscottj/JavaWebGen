/*
Copyright (c) 203-2006 Kevin Scott

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

 

import java.util.*;
import java.io.*;

import org.apache.commons.text.StrSubstitutor;
import org.javaWebGen.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 


/**
 * Generated DAO objects from the database config
 * @author Kevin scott
 * @version $Revision: 1.2 $
 *
 */
public class GenerateSpringJdbcDao extends GenerateJdbcDao {
    
    public static final String VERSION="GenerateSpringDAO v1_04";
    private static final Logger log = LoggerFactory.getLogger(GenerateSpringJdbcDao.class);
    private String className=null;
    private String subClassName=null;   

    
    private String classTemplate =
            "/*\n"+
            "Copyright (c) 2012-2017 Kevin Scott All rights  reserved."+
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
           "package org.javaWebGen.data.dao;\n\n"+
           "import org.springframework.jdbc.core.RowMapper;\n"+
           "import java.sql.ResultSet;\n"+
           "import java.sql.SQLException;\n"+
           "import java.util.*;\n"+
           "import java.math.BigDecimal;\n"+
           "import org.apache.commons.dbutils.QueryRunner;\n"+
           "import org.apache.commons.dbutils.handlers.ScalarHandler;\n"+
           "import org.javaWebGen.data.bean.*;\n"+
           "import org.javaWebGen.util.SQLHelper;\n"+
           "import org.javaWebGen.exception.DBException;\n"+
           "import org.javaWebGen.data.SpringJdbcDao;\n"+
   		   "import org.javaWebGen.data.DaoAware;\n"+
            "/******************************************************************************\n"+
            "* WARNING this class is generated by "+VERSION+" based on Database schema     \n"+
            "* This class should not be modified, but may be extended.\n" +
            "  This class will be regenerated if the database schema is changed. \n"+
            "* @author Kevin Scott                                                        \n"+
            "* @version $Revision: 1.00 $                                               \n"+
            "*******************************************************************************/\n"+
            "public abstract class ${javaWebGen.className} extends SpringJdbcDao implements DaoAware{ \n"+
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
           
            "/*spring datamapper*/\n"+
        	"${javaWebGen.dataMapper}\n"+
 		"}\n";
    
            
    private String subClassTemplate=
    "/*\n"+
    " THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR \n"+
    " IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, \n"+
    " FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE \n"+
    " AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER \n"+
    " LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, \n"+
    " OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE \n"+
    " SOFTWARE.\n */\n "+
    "package org.javaWebGen.data.dao;\n\n"+

    "/******************************************************************************\n"+
    "* This class is generated by "+VERSION+" based on torque xml schema     \n"+
    "* This class <b>should</b> be modified.  In general your Finder methods go \n"+
    "* in this class and any required db operations.  This class will not get\n"+
    "* regenerated and is just generated as a place holder.\n"+
    "* @author Kevin Scott                                                        \n"+
    "* @version $Revision: 1.00 $                                               \n"+
    "*******************************************************************************/\n"+
    "public class ${javaWebGen.subClassName} extends ${javaWebGen.className} { \n"+
    "}\n";

    /** springContext XML file*/
    public String xmlTempate=""+
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
	"<beans xmlns=\"http://www.springframework.org/schema/beans\"\n" +
	"		xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n "+
	"		xmlns:aop=\"http://www.springframework.org/schema/aop\"\n "+
	"		xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\">\n"+
	
    "<bean id=\"dataSource\" destroy-method=\"close\" class=\"org.apache.commons.dbcp.BasicDataSource\"> \n"+
    "    <property name=\"driverClassName\" value=\"${jdbc.driverClassName}\"/> \n"+
    "    <property name=\"url\" value=\"${jdbc.url}\"/> \n"+
    "    <property name=\"username\" value=\"${jdbc.username}\"/> \n"+
    "    <property name=\"password\" value=\"${jdbc.password}\"/> \n"+
    "</bean>\n"+
	" <!-- define daos-->\n" +
	" ${javaWebGen.daoBeans}\n"+
	" <!-- set daofactory-->\n" +
	"<bean id=\"DaoFactory\" class=\"org.javaWebGen.data.dao.DaoFactory\" >\n"+
	" ${javaWebGen.springBeans}\n"+
	"</bean>\n"+
	"</beans>\n";
	    
    /**
    *
    */
    public void init(){

    	classTemplate=getTemplate(classTemplate);
    	subClassTemplate=getTemplate(subClassTemplate);
    	
        }
    private String makeRowMappper(String[] colNames,int[] colTypes){
    	String dataBean=DataMapper.formatClassName(getTableName() );
    	String text=
    		"static final class EntityMapper implements RowMapper<"+dataBean+">{ \n"+

        	"    public "+dataBean+" mapRow(ResultSet rs, int rowNum) throws SQLException { \n"+
        	"        "+dataBean+" entity = new "+dataBean+"(); \n"+
       		"        Object[] data = new Object["+colTypes.length+"];\n";

		for(int index=0;index<colTypes.length;index++){
			int dbIndex=index+1;
			text+="        data["+index+"]=rs.getObject("+dbIndex+"); \n";
		}
		text+=
			"        entity.setData(data); \n"+
        	"        return entity; \n"+
        	"    } \n"+
        	"} \n";
    		
        	return text;
    }
    /************************************
     *gen find by primarykey
     *************************************/
    @Override
     protected String makeFindByPrimaryKey(String[] keys, int[] types){
         String beanName=DataMapper.formatClassName(getTableName() );
         ArrayList <String> primaryKeys=getPrimaryKeys();
         int[] primaryKeysTypes=getPrimaryKeyTypes();
         String text=
         "\n\t/***************************************************\n"+
         "\t*Warning Generated method.\n" +
         "\t*Get a DataBean with table data in it\n"+
         "\t@return DataBean with data\n"+
         "\t******************************************************/\n"+
         "\tpublic "+beanName+" findByPrimaryKey("+getVars(primaryKeys,primaryKeysTypes)+") throws DBException,SQLException{\n"+
         "\t\tString sql="+beanName+".getSelectSQL()+\" "+getWhereClause(primaryKeys,primaryKeysTypes)+";\n"+
         "\t\tList<"+beanName+"> list =this.getJdbcTemplate().query(sql,new EntityMapper() );\n"+
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
     *gen findAll
     *************************************/
    @Override
   protected String makeFindAll(String[] cols, int[] types){
         String beanName=DataMapper.formatClassName(getTableName() );

         String text=
         "\n\t/***************************************************\n"+
         "\t*Warning Generated method.\n" +
         "\t Get a javabean with table data in it\n"+
         "\t@return List DataBean with data\n"+
         "\t******************************************************/\n"+
         "\tpublic List<"+beanName+"> findAll() throws DBException,SQLException{\n"+
         "\t\tString sql="+beanName+".getSelectSQL();\n"+
         "\t\treturn this.getJdbcTemplate().query(sql,new EntityMapper() );\n"+
         "\t} //findAll\n";
         return text;
     }   
    
    /**************************************************
    *build class based on template
    ********************************************************/
    
    protected String buldClass() throws Exception{

        
        String[] colNames=getColNames();
        int[] colTypes= getColTypes();
        //findbugs does not like this though I just wanted to see if it was null not content
        //log.info("colNames"+colNames+" colTypes=" );
        String vars = makeVars(colNames,colTypes);
        String finder = makeFindByPrimaryKey(colNames,colTypes);
        String insert = makeInsert(colNames,colTypes);
        String update = makeUpdate(colNames,colTypes);
        String delete = this.makeDelete(colNames,colTypes);
        String findAll = this.makeFindAll(colNames,colTypes);
        HashMap<String,String>valueMap = new HashMap<String,String>();
 
        valueMap.put("javaWebGen.className", className);
        valueMap.put("javaWebGen.vars", vars);
        valueMap.put("javaWebGen.finder", finder);
        valueMap.put("javaWebGen.insert", insert);
        valueMap.put("javaWebGen.update", update);
        valueMap.put("javaWebGen.delete", delete);
        valueMap.put("javaWebGen.findAll", findAll); 
        valueMap.put("javaWebGen.dataMapper", makeRowMappper(colNames,colTypes) );
        //debug(classTemplate);
        StrSubstitutor sub = new StrSubstitutor(valueMap);
        String classText = sub.replace(classTemplate);

        return classText;
    }
    /**************************************************
    *build class based on template
    ********************************************************/
    protected String buldSubClass() throws Exception{
        HashMap<String,String> valueMap= new HashMap<String,String>();
        valueMap.put("javaWebGen.subClassName", subClassName);
        valueMap.put("javaWebGen.className", className);
        StrSubstitutor sub =new StrSubstitutor(valueMap);
        String classText=sub.replace(subClassTemplate);

        return classText;
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
    @Override
    protected void postExecute() throws UtilException {
    	log.info("super.postExecute()>>>");
    	//super.postExecute();
     
    	try {
			GenerateDAOFactory.writeFactory(this.getFilePath(),this.getEntityNames() );
			//writeContext();
			//writeSpringXML();
			log.info("<<<postExecute()");
		} catch (IOException e) {
			throw new UtilException(UtilException.CODE_GENERATOR_EXEC,e);
		}
    	
    	
    }  
    /**
    *Write out generated class
    */
 
    private void writeJavaClass(String text) throws IOException{
        String name = className;

        ArrayList <String> primaryKeys=getPrimaryKeys();
        if(primaryKeys.size() >0){
            String fileName=getFilePath()+File.separator+name+".java";
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
        String name = subClassName;

        ArrayList <String> primaryKeys=getPrimaryKeys();

        if(primaryKeys.size() >0){
        	String fileName = getFilePath()+File.separator+name+".java";
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
 

    /**
     * main
     */
     public static void main(String[] args) {
         try{ 	
        	 GenerateSpringJdbcDao app = new GenerateSpringJdbcDao();
         	app.setCmdParms(args);
         	app.init();
         	app.processXmlFile(app.getFileName());
            
        }catch(Exception e){
        		log.error("Main Error",e);
        		System.exit(911);
        	}
     }
 /**
  * 
  */
     @Override
 	public void useage() {
         System.out.println("To Process all tables in a text file");
         System.out.println("USAGE GenerateSpringJdbcDAO -f <filename> <path>");
         System.exit(1);
 	}



}