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

import java.util.*;
 

import java.io.*;

import org.apache.commons.text.StringSubstitutor;
import org.javaWebGen.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generated DAO objects from the Torque XML 
 * Will use JDO API to talk to the datastore
 * @author Kevin scott
 * @version $Revision: 1.2 $
 *
 */
public class GenerateJDO extends CodeGenerator {
	private final static Logger log= LoggerFactory.getLogger(GenerateJDO.class);
    public static final String VERSION="GenerateJDO v1_18";
    private String className=null;
    private String subClassName=null;   
    

    private String classTemplate=
        "/*\n"+
        "Copyright (c) 2018 Kevin Scott All rights  reserved.\n"+
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
        " SOFTWARE.*/\n "+
        "package org.javaWebGen.data.dao;\n\n"+
        "import javax.jdo.PersistenceManager;\n"+
        "import javax.jdo.Query;\n"+
        "import java.util.*;\n" +
        "import javax.annotation.Generated;\n"+
        "import org.javaWebGen.data.bean.*;\n"+
        "import org.javaWebGen.exception.DBException;\n"+
        "import org.javaWebGen.data.JdoDao;\n"+
       
        "/******************************************************************************\n"+
        "* WARNING this class is generated by "+VERSION+" based on torque schema     \n"+
        "* This class should not be modified, but may be extended.\n" +
        "* This class will be regenerated if the database schema is changed. \n"+
        "* Note, you should only use one primary key per data object\n"+
        "* <ul>\n"+
        "* <li>GAE only supports one Primary keys directly without custom code</li>\n"+
        "* <li>GAE only supports  Primary keys of Long or String</li>\n"+
        "* <li>this framework does not handle String Primary keys yet\n</li>\n"+
        "* <li>this framework does not handle BLOBS yet</li>\n</li>\n"+
        "* </ul>\n"+
        "* @author Kevin Scott                                                        \n"+
        "*******************************************************************************/\n"+
        "@Generated(value = { \"org.javaWebGen.generator.GenerateJDO \" })\n"+
        "public abstract class ${javaWebGen.className} extends JdoDao { \n"+
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
        "import javax.annotation.Generated;\n"+
        "/******************************************************************************\n"+
        "* This class is generated by "+VERSION+" based on torque xml schema     \n"+
        "* This class <b>should</b> be modified.  In general your Finder methods go \n"+
        "* in this class and any required db operations.  This class will not get\n"+
        "* regenerated and is just generated as a place holder.\n"+
        "* @author Kevin Scott                                                        \n"+
        "*******************************************************************************/\n" +
        "@Generated(value = { \"org.javaWebGen.generator.GenerateJDO \"})\n"+
        "public class ${javaWebGen.subClassName} extends ${javaWebGen.className} { \n"+
        "}\n";
        private String pmfClass=
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
            " SOFTWARE.*/\n "+
           
        	"package org.javaWebGen.data.dao;\n"+
			"\n"+
			"import javax.annotation.Generated;\n"+
			"import javax.jdo.JDOHelper;\n"+
			"import javax.jdo.PersistenceManagerFactory;\n"+
			"\n"+
			"/**\n"+
			"* WARNING this class is generated by "+VERSION+" based on torque schema     \n"+
			"*Used to get an instance of the JDO Persistence Manager class\n"+
			"*/\n"+
			"@Generated(value = { \"org.javaWebGen.generator.GenerateJDO\" })\n" +
			"public final class PMF {\n"+
			"    private static final PersistenceManagerFactory pmfInstance =\n"+
			"        JDOHelper.getPersistenceManagerFactory(\"transactions-optional\");\n"+
			"\n"+
			"    public PMF() {}\n"+
			"\n"+
			"    /**" +
			"    *@return  instance of the JDO Persistence Manager \n"+
			"    */\n"+
			"    protected static PersistenceManagerFactory get() {\n"+
			"        return pmfInstance;\n"+
			"    }\n"+
			"    /**" +
			"    *@return  instance of the JDO Persistence Manager \n"+
			"    */\n"+
			"    public PersistenceManagerFactory createInstance() {\n"+
			"        return JDOHelper.getPersistenceManagerFactory(\"transactions-optional\");\n"+
			"    }\n"+
			"}\n"+
			"";
    
    
    
    /**
    *
    */
    public void init(){
    	classTemplate=getTemplate(classTemplate);
    	subClassTemplate=getTemplate(subClassTemplate);
    	
        }

    protected String makeVars(String[] cols, int[] types){
        //String beanName=DataMapper.formatClassName(getTableName() );
        String text="\n";
            

        return text;
    }  
    /************************************
    *gen find by primarykey
    *************************************/
    protected String makeFindByPrimaryKey(String[] keys, int[] types){
        String beanName=DataMapper.formatClassName(getTableName() );
        //ArrayList <String> primaryKeys=getPrimaryKeys();
        int[] primaryKeysTypes=getPrimaryKeyTypes();
        //String javaType=DataMapper.getJavaTypeFromSQLType(primaryKeysTypes[0]);
        String javaType="Long"; //JDO requires this
        if(primaryKeysTypes.length!=1){
        	return "//can only generate code for one Primary key per table \n";
        }
        String text=
        "\n\t/****************************************************************\n"+
        "\t*Get a bound DataBean with table data in it by primary key\n"+
        "\t*@param JDO persist \n"+
        "\t*@param primary key or id for entity \n"+
        "\t*@return DataBean with data\n"+
        "\t*******************************************************************/\n"+
        "\tprotected "+beanName+" findByPrimaryKey(PersistenceManager pm,Long id) throws DBException{\n"+
        "\t\tif (id==null){\n"+
        "\t\t\tthrow new DBException(DBException.DAO_ERROR,\""+beanName+" id=null\");\n"+
        "\t\t}\n"+
        "\t\ttry{\n"+               
        "\t\t\treturn pm.getObjectById("+beanName+".class,id);\n"+
        "\t\t}catch(Exception ex){\n"+
        "\t\t\tthrow new DBException(DBException.DAO_ERROR,ex);\n"+
        "\t\t}\n"+
        "\t} //find by primary key\n"+
        "\n\t/**************************************************************************\n"+
        "\t*Get a DataBean with table data in it\n"+
        "\t*@param primary key or id for entity\n"+
        "\t*@return DataBean with data\n"+
        "\t*******************************************************************************/\n"+
        "\tpublic "+beanName+" findByPrimaryKey("+javaType+" id) throws DBException{\n"+
    	"\t\tPersistenceManager pm = PMF.get().getPersistenceManager();\n"+
    	"\t\t"+beanName+" entity=null;\n"+
    	"\t\ttry{\n"+    			
    	"\t\t\tentity = findByPrimaryKey(pm,id);\n"+
    	"\t\t}catch(Exception ex){\n"+
    	"\t\t\tthrow new DBException(DBException.DAO_ERROR,ex);\n"+
    	"\t\t}finally{\n"+
    	"\t\t\tpm.close();\n"+
    	"\t\t}\n"+
    	"\t\treturn entity;\n"+
    	"\t}\n";
        return text;
    }
    
    /************************************
    *gen create(INSERT)
    *************************************/
    protected String makeInsert(String[] cols, int[] types){
        String beanName=DataMapper.formatClassName(getTableName() );
        String idMethod=".get"+DataMapper.formatMethodName(getPrimaryKeys().get(0))+"() ";
        int[] primaryKeysTypes=getPrimaryKeyTypes();
        //String javaType=DataMapper.getJavaTypeFromSQLType(primaryKeysTypes[0]);
        String javaType="Long"; //JDO requires this
        if(primaryKeysTypes.length!=1){
        	return "//Can only generate code for one Primary key per table \n//You need to write custom code to handle this\n";
        }
       
        String text=
        "\n\t/************************************************************\n"+
        "\t*Inserts new bound DataBean into data store\n"+
        "\t*@param DabaBean with entity data in it\n"+
        "\t*@return id inserted into datastore\n"+
        "\t****************************************************************/\n"+
        "\tpublic Long insert("+beanName+" entity) throws DBException{\n"+
        "\t\tPersistenceManager pm = PMF.get().getPersistenceManager();\n"+
        "\t\t"+javaType+" id=null;\n"+
        "\t\ttry{\n"+
        "\t\t\tentity = pm.makePersistent(entity);\n"+
        "\t\t\tid=entity"+idMethod+";\n"+
        "\t\t}catch(Exception e){\n"+
        "\t\t\tthrow new DBException(DBException.DAO_ERROR,e);\n"+        
        "\t\t}finally{\n" +
        "\t\t\tpm.close();\n"+
        "\t\t}\n"+
        "\t\treturn id;\n"+
        "\t} //end insert\n";
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
       // ArrayList <String> primaryKeys=getPrimaryKeys();
       
        String idMethod=DataMapper.formatMethodName(getPrimaryKeys().get(0))+" ";
        int[] primaryKeysTypes=getPrimaryKeyTypes();
        //String javaType=DataMapper.getJavaTypeFromSQLType(primaryKeysTypes[0]);
       // String javaType="Long"; //JDO requires this
        if(primaryKeysTypes.length!=1){
        	return "//can only generate code for one Primary key per table \n";
        }
        String text=
	        "\n\t/*********************************************************\n"+
	        "\t*Updates the data store with a bound DataBean \n"+
	        "\t*@param DabaBean with entity data in it"+
	        "\t*\n"+
	        "\t************************************************************/\n"+
	        "\tpublic void update("+beanName+" entity) throws DBException{\n"+
	        "\t\tPersistenceManager pm = PMF.get().getPersistenceManager();\n"+
	        "\t\ttry{\n"+
	        "\t\t\t"+beanName+" e=findByPrimaryKey(pm,entity.get"+idMethod+"() );\n";
        for(int i=0;i<cols.length;i++){
        	idMethod=DataMapper.formatMethodName(cols[i])+" ";
        	if(this.getIsKeyArray()[i]){
        		text+="\t\t\t // leave key field alone e.set"+idMethod+"(entity.get"+idMethod+"() );\n";
        	}else{
        		text+="\t\t\te.set"+idMethod+"(entity.get"+idMethod+"() );\n";
        	}
        }
        text+="\t\t\tpm.makePersistent(e);\n"+
        	 "\t\t}catch(Exception e){\n"+
        	 "\t\t\tthrow new DBException(DBException.DAO_ERROR,e);\n"+     
        	 "\t\t}finally{\n"+
        	 "\t\t\tpm.close();\n"+
        	 "\t\t}\n"+
        	 "\t} //end update by primary key\n";
        return text;
    }
    /************************************
     *gen delete
     *************************************/
     protected String makeDelete(String[] cols, int[] types){
         String beanName=DataMapper.formatClassName(getTableName() );
        // ArrayList <String> primaryKeys=getPrimaryKeys();
         
         String idMethod="get"+DataMapper.formatMethodName(getPrimaryKeys().get(0))+"() ";
         int[] primaryKeysTypes=getPrimaryKeyTypes();
         //String javaType=DataMapper.getJavaTypeFromSQLType(primaryKeysTypes[0]);
        // String javaType="Long"; //JDO requires this
         if(primaryKeysTypes.length!=1){
         	return "//can only generate code for one Primary key per table \n";
         }
         String text=
         "\n\t/**************************************************************\n"+
         "\t*Remove bound databean from data store \n"+
         "\t*@param DabaBean with entity data in it\n"+
         "\t*****************************************************************/\n"+
         "\tpublic void delete("+beanName+" entity) throws DBException{\n"+
         "\t\tPersistenceManager pm = PMF.get().getPersistenceManager();\n"+
         "\t\ttry{\n"+
         "\t\t\t"+beanName+" e=findByPrimaryKey(pm,entity."+idMethod+" );\n"+
         "\t\t\tpm.deletePersistent(e);\n"+
         "\t\t}catch(Exception e){\n"+
         "\t\t\tthrow new DBException(DBException.DAO_ERROR,e);\n"+
         "\t\t}finally{\n"+
         "\t\t\tpm.close();\n"+
         "\t\t}\n"+
         "\t} // delete by primary key\n";
         return text;
     }
     /************************************
      *gen findAll
      *************************************/
      protected String makeFindAll(String[] cols, int[] types){
          String beanName=DataMapper.formatClassName(getTableName() );

          String text=
          "\n\t/***********************************************************\n"+
          "\t*Get a list of all matching DataBeans from data store\n"+
          "\t*@param data bound Bean\n"+
          "\t*@return list of all DataBean in data store\n"+
          "\t**************************************************************/\n"+
          "\t@SuppressWarnings(\"unchecked\")\n"+
          "\tpublic List<"+beanName+"> findAll() throws DBException{\n"+
          "\t\tPersistenceManager pm = PMF.get().getPersistenceManager();\n"+
          "\t\tList<"+beanName+"> results=null;\n"+
          "\t\ttry{\n"+
          "\t\t\tQuery query =pm.newQuery("+beanName+".class);\n"+
          "\t\t\tresults=(List<"+beanName+">) query.execute();\n"+   
          "\t\t}catch(Exception e){\n"+
          "\t\t\tthrow new DBException(DBException.DAO_ERROR,e);\n"+
          "\t\t}finally{\n"+
          "\t\t\tpm.close();\n"+
          "\t\t}\n"+        
          "\t\treturn new ArrayList<"+beanName+">(results);\n"+
          "\t} //findAll\n";
          return text;
      }
   /* private String makePkeyVars(List <String> keys, int[] types){
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
                        text+="\t\tString "+name+"=data.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.DATE:
                    case Types.TIMESTAMP:
                        text+="\t\tjava.util.Date "+name+"=data.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.BIT:
                        text+="\t\tboolean "+name+"=data.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.TINYINT:
                        text+="\t\tbyte "+name+"=data.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.SMALLINT:
                        text+="\t\tshort "+name+"=data.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.INTEGER:
                        text+="\t\tint "+name+"=data.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.BIGINT:
                        text+="\t\tlong "+name+"=data.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.REAL:
                    	text+="\t\tfloat "+name+"=data.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                    	break;
                    case Types.FLOAT:
                    case Types.DOUBLE:
                        text+="\t\tdouble "+name+"=data.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                        break;
                    case Types.NUMERIC:
                    case Types.DECIMAL:                 
                        text+="\t\tBigDecimal "+name+"=data.get"+DataMapper.formatMethodName(keys.get(i).toString() )+"();\n";
                    break;
                }//end switch                
                
            }
        }else{
            return "";
        }
        return text;
    }*/
    
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
        
        HashMap<String,String> valueMap= new HashMap<String,String>();
        valueMap.put("javaWebGen.className", className);
        valueMap.put("javaWebGen.vars", vars);
        valueMap.put("javaWebGen.finder", finder);
        valueMap.put("javaWebGen.insert", insert);
        valueMap.put("javaWebGen.update", update);
        valueMap.put("javaWebGen.delete", delete);
        valueMap.put("javaWebGen.findAll", findAll);
        StringSubstitutor sub = new StringSubstitutor(valueMap);
        String classText = sub.replace(classTemplate);
       
        return classText;
    }
    /**************************************************
    *build class based on template
    ********************************************************/
    protected String buldSubClass() throws Exception{
    
        String[] p = new String[2];
        p[0]= subClassName;
        p[1]= className;
        
        HashMap<String,String> valueMap = new HashMap<String,String>();
        valueMap.put("javaWebGen.subClassName",subClassName);
        valueMap.put("javaWebGen.className",className);
        StringSubstitutor sub = new StringSubstitutor(valueMap); 
        String classText = sub.replace(subClassTemplate);
        return classText;
    }  
 
	@Override
	protected void postExecute() throws UtilException {
		try {
			writePMFClass();
			GenerateDAOFactory.writeFactory(this.getFilePath(),this.getEntityNames() );
			//writeContext();
			//writeSpringXML();
			 
		} catch (IOException e) {
			throw new UtilException(UtilException.CODE_GENERATOR_EXEC,e);
		}
		
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
    Write PMF class out if it does not exist
    this prevents library problems by caused by using PMF in another project
    that may have different JDO libraries.  In addition datanucleus will
    check for dupe classes.  So it is safest to just generate if it does not exist
    */
    private void writePMFClass() throws IOException{
    	String text=pmfClass;
        String fileName=getFilePath()+File.separator+"PMF.java";
    	File file=new File(fileName);
        if(!file.exists() ){
	        FileWriter fw = new FileWriter(file);
	        PrintWriter out = new PrintWriter(fw);
	        out.print(text);
	        out.flush();
	        out.close();
	        log.debug("---write file="+fileName+"---");
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
     public static void main(String[] args) throws Exception  {
       
      	GenerateJDO app = new GenerateJDO();
     	app.setCmdParms(args);
     	app.init();
        app.processXmlFile(app.getFileName());
		

     }
	 /**
	  * 
	  */
 	public void useage() {
        System.err.println("Generate JDO DAO object based on schema");
        System.err.println("<schema> torque XML with tables definitions");
        System.err.println("<path> destination file path");
        System.err.println("-template optional filename of template to use for code generator");
        System.err.println("-jdo generate JDO context code instead of JDBC");
        System.err.println("USAGE GenerateJDO <schema xml> <path> [-template fileName] [-jdo]");
        System.exit(1);
 	}


}