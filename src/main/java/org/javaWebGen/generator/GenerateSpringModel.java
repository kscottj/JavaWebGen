/*
 * =================================================================== *
 * Copyright (c) 2017 Kevin Scott All rights  reserved.
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


import java.util.*;
import java.io.*;

import org.apache.commons.text.StrSubstitutor;
import org.javaWebGen.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * create Models 
 * Generated Web Model(Service) Objects from the database config
 * creates a Model class and a implementing class
 * This class Will be overwriten!
 * @author Kevin scott
 * @version $Revision: 1.2 $
 *
 */
public class GenerateSpringModel extends GenerateModel {
    
    public static final String VERSION="GenerateSpringModel v1_05";
	private String className=null;
	private String subClassName=null;
	private final static Logger log= LoggerFactory.getLogger(GenerateSpringModel.class);
	   private String modelFactoryTemplate=
		        "/*\n"+
		        "Copyright (c) 2012-2013 Kevin Scott All rights  reserved."+
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
		       "package org.javaWebGen.model;\n\n"+
		       "/******************************************************************************\n"+
		        "* WARNING this class is generated by "+VERSION+" based on Database schema     \n"+
		        "* This class should not be modified, but may be extended.\n" +
		        "  This class will be regenerated if the database schema is changed. \n"+
		        "* @author Kevin Scott                                                        \n"+
		        "* @version $Revision: 1.00 $                                               \n"+
		        "*******************************************************************************/\n"+
		        "public  class ServiceFactory{ \n"+
		        "//begin private Vars\n"+
		        "${javaWebGen.vars}\n"+
		        "//begin getters and setters\n"+
		        "${javaWebGen.getsSets}\n"+
		        "}\n";
    private String classTemplate=
        "/*\n"+
        " Copyright (c) 2012-2013 Kevin Scott All rights  reserved.\n"+
        " Permission is hereby granted, free of charge, to any person obtaining a copy of \n"+
        " this software and associated documentation files (the \"Software\"), to deal in \n"+
        " the Software without restriction, including without limitation the rights to \n"+
        " use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies\n"+
        " of the Software, and to permit persons to whom the Software is furnished to do \n"+
        " so.\n"+
        "\n"+
        " THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR \n"+
        " IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, \n"+
        " FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE \n"+
        " AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER \n"+
        " LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, \n"+
        " OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE \n"+
        " SOFTWARE.\n "+
        "*/ \n"+            "/** data Acees Object talks to DB **/\n"+
        "package org.javaWebGen.model;\n"+
        "import java.util.*;\n"+
        "import org.javaWebGen.data.bean.*;\n"+
        "import org.javaWebGen.*;\n"+
        "import org.javaWebGen.exception.WebAppException;\n"+
        "import org.javaWebGen.data.dao.*\n;"+
        "import org.javaWebGen.JavaWebGenContext\n;"+
        "import org.springframework.stereotype.Service;\n"+
        "/******************************************************************************\n"+
        "* WARNING this class is generated by "+VERSION+" based on Database schema     \n"+
        "* This class should not be modified, but may be extended to add required logic \n"+
        "* It will be regenerated if the database schema changes \n"+
        "*******************************************************************************/\n"+
        "@Service\n"+
        "public abstract class ${javaWebGen.className} implements Model { \n"+
        "//begin private Vars\n"+
        "${javaWebGen.vars}\n"+
        "//find by Primary Key\n"	 +
        "${javaWebGen.finder}\n"+
        "//begin insert (create)\n"+
        "${javaWebGen.insert}\n"+
        "//begin update(store)\n"+
        "${javaWebGen.update}\n"+
        "//begin delete(store)\n"+
        "${javaWebGen.delete}\n"+
        "//begin listAll)\n"+
        " ${javaWebGen.list}\n"+
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

           "*/ \n"+
        "package org.javaWebGen.model;\n\n"+


        "import org.javaWebGen.config.ConfigConst;\n"+
		"import org.slf4j.Logger;\n"+
		"import org.slf4j.LoggerFactory;\n"+

        "/******************************************************************************\n"+
        "* This class is generated by "+VERSION+" based on Database schema     \n"+
        "* This class <b>should</b> be modified.  Business Logic goes here.\n" +
        "* The Implementation class that this class extends will handle all\n " +
        "* CRUD operations(Create,Update,Delete), and getById, and list all/\n "+
        "* This class will not get\n"+
        "* regenerated and is just generated as a place holder.\n"+
        "* @author Kevin Scott                                                        \n"+
        "* @version $Revision: 1.00 $                                               \n"+
        "*******************************************************************************/\n"+
        "public class ${javaWebGen.subClassName} extends ${javaWebGen.className} { \n"+
        "\t/** Model Logger */\n" +
        "@SuppressWarnings(\"unused\")\n"+
        "\tprivate final Logger log = LoggerFactory.getLogger(ConfigConst.MODEL_LOG);\n"+
        "}\n";

    
    /**
    *
    */
    public void init(){
    	classTemplate=getTemplate(classTemplate);
    	subClassTemplate=getTemplate(subClassTemplate);     
    }

    /************************************
    *gen class vars
    *************************************/
    private String makeVars(String[] cols, int[] types){
       // String beanName=DataMapper.formatClassName(tableName);
        String daoName=DataMapper.formatClassName(getTableName() )+"DAO";
        String text=
            "\t\n/** data bean for this object **/;\n"+
//            "\tprivate "+beanName+" dataBean;\n"+
        	"\tprivate "+daoName+" dao= new "+daoName+"();\n";

        return text;
    }  
    /***************************************************************************
	 * gen find by primary key
	 **************************************************************************/
    @Override
	protected String makeFindByPrimaryKey(String[] keys, int[] types) {
		String beanName = DataMapper.formatClassName(getTableName() );
		//ArrayList <String> primaryKeys = getPrimaryKeys();
		int[] primaryKeyTypes=getPrimaryKeyTypes();
		String javaType=DataMapper.getJavaTypeFromSQLType(primaryKeyTypes[0]);
		
		
		String text =null;
		text = "\n\t/***************************************************\n"
				+ "\t* Generated method. get a Databean with table data in it\n"
				+ "\t* @return databean with data\n"
				+ "\t******************************************************/\n"
				+ "\tpublic "
				+ beanName
				+ " getById("+javaType+" id) throws WebAppException{\n"
				+ "\t\ttry{\n"
				+ "\t\t\t"+beanName + " bean=JavaWebGenContext.getDao().get"+beanName+"Dao().findByPrimaryKey(id);\n"			
				+ "\t\t\t return bean;\n"
				+ "\t\t}catch(Exception e){\n"
				+ "\t\t\tthrow new WebAppException(WebAppException.APP_ERROR,e);\n"
				+ "}\n"
				+ "\t} //end getById\n"
				+ "\n\t/***************************************************\n"
				+ "\t*Generated method. get a Databean with table data in it\n"
				+ "\t*method takes string parms and converts them the correct data type\n"
				+ "\t* Warning Dates fields must be in a format that the Java DateFormatter can use!\n"
				+ "\t* @return databean with data\n"
				+ "\t******************************************************/\n"
				+ "\tpublic " +  beanName + " getByIdParm(String idparm) throws WebAppException{\n"
				+ "\t\t try{\n"
				+ "\t\t\t"+javaType+" id =new "+javaType+"(idparm);\n"
				+ "\t\t\treturn getById(id);\n"
				+ "\t\t}catch(Exception e){\n"
				+ "\t\t\tthrow new WebAppException(WebAppException.GENERIC,e);\n"
				+ "\t\t}\n"
				+ "\t}\n";
		return text;
	}
    
    /***************************************************************************
	 * gen create(INSERT)
	 **************************************************************************/
	private String makeInsert(String[] cols, int[] types) {
		String beanName = DataMapper.formatClassName(getTableName() );
		String javaType=DataMapper.getJavaTypeFromSQLType(this.getPrimaryKeyTypes()[0]);
		String text = 
			"\n\t/***************************************************\n"
				+ "\t*Warning Generated method inserts new Databean \n"
				+ "\t*\n"
				+ "\t******************************************************/\n"
				+ "\tpublic "+javaType+" create("
				+ beanName
				+ " bean) throws WebAppException{\n"
				+ "\t\ttry{\n"
				+ "\t\t\treturn JavaWebGenContext.getDao().get"+beanName+"Dao().insert(bean);\n"
				+ "\t\t}catch(Exception e){\n"
				+ "\t\t\tthrow new WebAppException(WebAppException.APP_ERROR,e);\n"
				+ "\t\t}\n" + "\t} //end create\n";
		return text;
	}

    /***************************************************************************
	 * gen update(store)
	 **************************************************************************/
    private String makeUpdate(String[] cols, int[] types) {
		String beanName = DataMapper.formatClassName(getTableName() );
		String text = "\n\t/***************************************************\n"
				+ "\t* Warning Generated method updates the database with a Databean \n"
				+ "\t* \n"
				+ "\t******************************************************/\n"
				+ "\tpublic void save("
				+ beanName
				+ " bean) throws WebAppException{\n"
				+ "\t\ttry{\n"
				+ "\t\t\tJavaWebGenContext.getDao().get"+beanName+"Dao().update(bean);\n" 
				+ "\t\t}catch(Exception e){\n"
				+ "\t\t\tthrow new WebAppException(WebAppException.APP_ERROR,e);\n"
				+ "\t\t}\n" 
				+ "\t} //end update\n";
		return text;
	}
    /***************************************************************************
	 * gen delete
	 **************************************************************************/
	private String makeDelete(String[] cols, int[] types) {
		String beanName = DataMapper.formatClassName(getTableName() );
		String text = "\n\t/***************************************************\n"
				+ "\t* Warning Generated method deletes record from the database based on a Databean \n"
				+ "\t* \n"
				+ "\t******************************************************/\n"
				+ "\tpublic void remove("
				+ beanName
				+ " bean) throws WebAppException{\n"
				+ "\t\ttry{\n"
				+ "\t\t\tJavaWebGenContext.getDao().get"+beanName+"Dao().delete(bean);\n"
				+ "\t\t}catch(Exception e){\n"
				+ "\t\t\tthrow new WebAppException(WebAppException.APP_ERROR,e);\n"
				+ "\t\t}\n"
				+"\t} //end delete\n";
		return text;
	}   
     private String makeList(String[] colNames2, int[] colTypes2) {
		String beanName = DataMapper.formatClassName(getTableName() );
		String text = "\n\t/***************************************************\n"
				+ "\t* Warning Generated method list of all records in a table \n"
				+ "\t* @return array of DataBeans\n"
				+ "\t******************************************************/\n"
				+ "\tpublic List<"+beanName+"> list() throws WebAppException{\n"
				+ "\t\tList<"+ beanName+"> dataBeans =null;\n" 
				+ " \t\ttry{\n"
				+ "\t\tdataBeans = JavaWebGenContext.getDao().get"+beanName+"Dao().findAll();\n"
				+ "\t\t}catch(Exception e){\n"
				+ "\t\t\tthrow new WebAppException(WebAppException.APP_ERROR,e);\n"
				+ "\t\t}\n\t\treturn dataBeans;\n\t} //end list\n";
		return text;
	}

  
    
    /***************************************************************************
	 * build class based on template
	 **************************************************************************/
    protected String buldClass() throws Exception{
    	String[] colNames=getColNames();
    	int[] colTypes=getColTypes();

    		
        String vars = makeVars(colNames,colTypes);
        String finder = makeFindByPrimaryKey(colNames,colTypes);
        String insert = makeInsert(colNames,colTypes);
        String update = makeUpdate(colNames,colTypes);
        String delete = makeDelete(colNames,colTypes);
        String list = makeList(colNames,colTypes);
        /*String[] p = new String[7];
        p[0]= className;
        p[1]= vars;
        p[2]=finder;
        p[3]=insert;
        p[4]=update;
        p[5]=delete;
        p[6]=list;*/
                 
        HashMap<String,String> valMap = new HashMap<String,String>();
        valMap.put("javaWebGen.className", className);
        valMap.put("javaWebGen.vars", vars);
        valMap.put("javaWebGen.finder", finder);
        valMap.put("javaWebGen.insert", insert);
        valMap.put("javaWebGen.update", update);
        valMap.put("javaWebGen.delete", delete);
        valMap.put("javaWebGen.list", list);
        StrSubstitutor sub = new  StrSubstitutor(valMap);
        log.debug("buldClass");
        return sub.replace(classTemplate);
    }
    /**
     * write model factory class that will be used by spring context
     * @throws IOException
     */
    protected void writeFactory() throws IOException{
        List<String> tableNames=this.getEntityNames();
        
        /*String[] p = new String[2];
        p[0]= makeFactoryPrivateVars(tableNames);
        p[1]= makeFactoryGetterAndSetters(tableNames);*/
        HashMap<String,String> valMap = new HashMap<String,String>();
        valMap.put("javaWebGen.vars", makeFactoryPrivateVars(tableNames));
        valMap.put("javaWebGen.getsSets",makeFactoryGetterAndSetters(tableNames));
        StrSubstitutor sub = new  StrSubstitutor(valMap);
       // String text = StringUtil.replace(modelFactoryTemplate,p);

        String fileName=getFilePath()+File.separator+"ServiceFactory.java";
    	File file=new File(fileName);

        FileWriter fw = new FileWriter(file);
        PrintWriter out = new PrintWriter(fw);
        out.print(sub.replace(modelFactoryTemplate));
        out.flush();
        out.close();
        log.debug("---write file="+fileName+"---");
    }
    /*
     * generate private variables in modelfactory
     */
    private String makeFactoryPrivateVars(List<String> tableNames){
    	StringBuffer buffer = new StringBuffer();
    	for(String tableName:tableNames){
    		buffer.append("private "+DataMapper.formatClassName(tableName)+"Model "+DataMapper.formatVarName(tableName)+"Model=null;\n");
    	}
    	return buffer.toString();
    }
    /*
     * generate getters and setters in modelfactory
     * setters will be injected by springContext at run time
     */
    private String makeFactoryGetterAndSetters(List<String> tableNames){

    	StringBuffer buffer = new StringBuffer();
    	for(String tableName:tableNames){
    		buffer.append("/** DataBase Access Object\n");
    		buffer.append("*@return model set by Spring Context*/\n");
    		buffer.append("public "+DataMapper.formatClassName(tableName)+"Model get"+DataMapper.formatClassName(tableName)+"Model(){\n") ;
    		buffer.append("    return "+DataMapper.formatVarName(tableName)+"Model;\n}\n");
    		buffer.append("/**Spring Context will set these at init time\n");
    		buffer.append("*@Param model injected by Spring Context*/\n");
    		buffer.append("public void set"+DataMapper.formatClassName(tableName)+"Model("+DataMapper.formatClassName(tableName)+"Model "+DataMapper.formatClassName(tableName)+"Model){\n") ;
    		buffer.append("    this."+DataMapper.formatVarName(tableName)+"Model = "+DataMapper.formatClassName(tableName)+"Model;\n}\n");
    	}
    	return buffer.toString();
    }

	/**************************************************
    *build class based on template
    ********************************************************/
    protected String buldSubClass() throws Exception{
    
        /* String[] p = new String[3];
        p[0]= subClassName;
        p[1]= className;
        p[2]= subClassName;*/
        HashMap<String,String> valMap = new HashMap<String,String>();
        valMap.put("javaWebGen.subClassName",subClassName);
        valMap.put("javaWebGen.className",className);
        StrSubstitutor sub = new  StrSubstitutor(valMap);
       // String classText = StringUtil.replace(subClassTemplate,p);

        return sub.replace(subClassTemplate);
    }
    
    /**
    *
    */
    @Override
    protected void execute() throws UtilException{
    	try{
    		className=DataMapper.formatClassName(getTableName() )+"ModelImpl";
    		subClassName=DataMapper.formatClassName(getTableName() )+"Model";
	        writeJavaClass(buldClass() );
	        writeSubClass(buldSubClass() );
    	}catch(Exception e){
    		throw new UtilException(UtilException.CODE_GENERATOR_EXEC,e);
    	}
    }
    
    /**
    *Called after looping though all tables-columns
    *Good place to generate files that are not needed for every table
    *IE PMF etc...
    */
    @Override
    protected void postExecute() throws UtilException{
    	try{
    		className=DataMapper.formatClassName(getTableName() )+"ModelImpl";
    		subClassName=DataMapper.formatClassName(getTableName() )+"Model";
	        writeJavaClass(buldClass() );
	        writeSubClass(buldSubClass() );
	        this.writeFactory();
    	}catch(Exception e){
    		throw new UtilException(UtilException.CODE_GENERATOR_EXEC,e);
    	}
    }
    
    /**
    *Write out generated class
    */
    private void writeJavaClass(String text) throws IOException{
        String name = className;
        ArrayList <String> primaryKeys=getPrimaryKeys();
        String fileName=getFilePath()+File.separator+name+".java";
        
      
        if(primaryKeys.size() >0){       
        	File file=new File(fileName);        
            FileWriter fw = new FileWriter(file);
            PrintWriter out = new PrintWriter(fw);
            out.print(text);
            out.flush();
            out.close();
            log.info("---write file="+fileName+"---");
        }
    }

    /**
    *Write out generated class
    */
    private void writeSubClass(String text) throws IOException{
        String name = subClassName;
        ArrayList <String> primaryKeys=getPrimaryKeys();
        String fileName=getFilePath()+File.separator+name+".java";
        if(primaryKeys.size() >0){
            File file = new File(fileName);
            if(!file.exists() ){
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
         	GenerateSpringModel app = new GenerateSpringModel();
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
 	public void useage() {
         System.out.println("To Process all tables in a text file");
         System.out.println("USAGE GenerateModel -f <filename> <path>");
         System.exit(1);
 	}

}

