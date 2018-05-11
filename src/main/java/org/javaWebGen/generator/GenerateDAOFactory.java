package org.javaWebGen.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class GenerateDAOFactory {    
		public static final String VERSION="GenerateDAOFactory v0_13";
		
		private static final Logger log = LoggerFactory.getLogger(GenerateDAOFactory.class);
		private static String daoFactoryTemplate=
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
		       "package org.javaWebGen.data.dao;\n\n"+
		       "import javax.annotation.Generated;\n"+
		       "/******************************************************************************\n"+
		        "* WARNING this class is generated by "+VERSION+" based on Database schema     \n"+
		        "* This class should not be modified, but may be extended.\n" +
		        "*  This class will be regenerated if the database schema is changed. \n"+
		        "* @author Kevin Scott                                                        \n"+
		        "*******************************************************************************/\n"+
		        "@Generated(value = { \"org.javaWebGen.generator.GenerateDAOFactory\" })\n"+
		        "public  class DaoFactory{ \n"+
		        "//begin private Vars\n"+
		        "${javaWebGen.vars}\n"+
		        "//begin getters and setters\n"+
		        "${javaWebGen.getsSets}\n"+
		        "}\n";
		   private static String makeFactoryPrivateVars(List<String> tableNames){
		    	StringBuffer buffer = new StringBuffer();
		    	for(String tableName:tableNames){
		    		buffer.append("private "+DataMapper.formatClassName(tableName)+"DAO "+DataMapper.formatVarName(tableName)+"Dao=null;\n");
		    	}
		    	return buffer.toString();
		    }
		    private static String makeFactoryGetterAndSetters(List<String> tableNames){

		    	StringBuffer buffer = new StringBuffer();
		    	for(String tableName:tableNames){
		    		buffer.append("/********************************************\n");
		    		buffer.append("*DataBase Access Object\n");
		    		buffer.append("*@return dao set by Spring Context\n");
		    		buffer.append("**********************************************/\n");
		    		buffer.append("public "+DataMapper.formatClassName(tableName)+"DAO get"+DataMapper.formatClassName(tableName)+"Dao(){\n") ;
		    		buffer.append("    return "+DataMapper.formatVarName(tableName)+"Dao;\n}\n");
		    		buffer.append("/********************************************\n");
		    		buffer.append("*DataBase Access Object\n");
		    		buffer.append("*@Param dao set by Spring Context\n");
		    		buffer.append("***********************************************/\n");
		    		buffer.append("public void set"+DataMapper.formatClassName(tableName)+"Dao("+DataMapper.formatClassName(tableName)+"DAO "+DataMapper.formatClassName(tableName)+"Dao){\n") ;
		    		buffer.append("    this."+DataMapper.formatVarName(tableName)+"Dao = "+DataMapper.formatClassName(tableName)+"Dao;\n}\n");
		    	}
		    	return buffer.toString();
		    }
	    protected static void writeFactory(String filePath,List<String> tableNames ) throws IOException{
		    /*    String[] p = new String[2];
	        p[0]= makeFactoryPrivateVars(tableNames);
	        p[1]= makeFactoryGetterAndSetters(tableNames);*/

	       // String text = StringUtil.replace(daoFactoryTemplate,p);
	    	HashMap<String,String>valueMap = new HashMap<String,String>();
	     	valueMap.put("javaWebGen.vars", makeFactoryPrivateVars(tableNames) );
	     	valueMap.put("javaWebGen.getsSets", makeFactoryGetterAndSetters(tableNames) );
	  	
	     	StringSubstitutor sub = new StringSubstitutor(valueMap);
	     	
	        String fileName=filePath+File.separator+"DaoFactory.java";
	    	File file=new File(fileName);

	        FileWriter fw = new FileWriter(file);
	        PrintWriter out = new PrintWriter(fw);
	        out.print(sub.replace(daoFactoryTemplate));
	        out.flush();
	        out.close();
	        log.debug("---write file="+fileName+"---");

	    }
}
