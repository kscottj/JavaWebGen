/*
Copyright (c) 2003 Kevin Scott

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


import java.io.*;
import java.util.HashMap;

import org.apache.commons.text.StringSubstitutor;
import org.javaWebGen.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generated database aware JavaBean JTO objects based on database configuration(torque)XML. 
 * Generated objects are generic.
 * @author Kevin scott
 *
 */
public class GenerateDataBean extends CodeGenerator {
	@SuppressWarnings("unused")
	private boolean useJDO=true;


   
	public static final String VERSION="GenerateDataBean v1_01";
		private final static Logger log= LoggerFactory.getLogger(GenerateDataBean.class);
     	private String classTemplate=
         "/*\n"+
        "Copyright (c) 2018 Kevin Scott\n"+
        "Permission is hereby granted, free of charge, to any person obtaining a copy of \n"+
        "this software and associated documentation files (the \"Software\"), to deal in \n"+
        "the Software without restriction, including without limitation the rights to \n"+
        "use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies\n"+
        "of the Software, and to permit persons to whom the Software is furnished to do \n"+
        "so, subject to the following conditions:\n"+
        "\n"+
        "The above copyright notice and this permission notice shall be included in all \n"+
        "copies or substantial portions of the Software.\n"+
        "\n"+
        " THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR \n"+
        " IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, \n"+
        " FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE \n"+
        " AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER \n"+
        " LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, \n"+
        " OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE \n"+
        " SOFTWARE.\n "+
        "*/ \n"+
     "package org.javaWebGen.data.bean;\n\n"+
     "import java.text.*;\n"+
     "import java.math.*;\n"+
     "import java.io.*;\n"+
     "import java.util.ArrayList;\n"+
     "import org.javaWebGen.data.FormBeanAware;\n"+
     "import org.javaWebGen.util.StringUtil;\n"+
     "import org.json.*;\n"+
     "import javax.annotation.Generated;\n"+
      "/******************************************************************************\n"+
      "* WARNING this JTO object is generated by "+VERSION+" based on Database schema\n"+
      "* This class should not be modified, but, may be extended.\n" +
      "* It will be regenerated when the database schema changes.\n"+ 
      "*******************************************************************************/\n"+	
      "@SuppressWarnings({ \"unused\", \"serial\" })\n"+ //@TODO generated should be smarter than this
      "@Generated(value = { \"org.javaWebGen.generator.GenerateDataBean \" })\n"+
      "public class ${javaWebGen.className} implements FormBeanAware, Serializable{ \n"+
      "//begin private Vars\n"+
      "private static int[] types=null;\n"+
      "${javaWebGen.vars}\n"+
      "//begin getters and setters\n"+
      "${javaWebGen.getsSets}\n"+
     "//begin make Type \n"	 +         
     "${javaWebGen.types}\n"+
     "//make JSON text \n"	 +
     "${javaWebGen.json}\n"+
     "//begin toXML()\n"+
     "${javaWebGen.xml}\n"+
     "// make  Overrides \n"	 +         
     "${javaWebGen.Overrides}\n"+    
     "}//end Generated class\n";


    /**
    *
    */
    public void init(){

    	classTemplate=getTemplate(classTemplate);
    }

    /************************
    * list of list of column types
    ****************************/
    protected String makeTypes(String[] cols, int[] types){
        String text =
        "\t/*****************************************************\n"+
        "\t*Get an array of column types that match DB table\n"+
        "\t*@return array of Types\n"+
        "\t******************************************************/\n"+
        "\tpublic int[] getDataTypes(){\n"+
        "\t\tif(types == null){\n"+
        "\t\t types = new int["+types.length+"];\n";
            for(int i=0;i<types.length;i++){
                    text+="\t\t\t types["+i+"]="+types[i]+";\n";
            }
            text+="\t\t} //end if\n";
    
            text+="\treturn types;\n";
            text+="\t}\n\n";
            return text;
    }

    /**
    * gen xml for databean
    */
    protected String makeXML(String tableName, String[] cols, int[] types) throws Exception{

        String text=
        "\n\t/********************************************\n"+
        "\t*Builds an XML String based on object data\n"+
        "\t*@return XML value of bound data\n"+
        "\t***********************************************/\n"+
        "\tpublic String toXML(){\n"+
        "\t\tString xml=\"<"+tableName+" ";
        for( int i=0;i<cols.length;i++){
        	text+=
        		DataMapper.formatVarName(cols[i])+"=\\\""+"\"+get"+
        		DataMapper.formatMethodName(cols[i])+"()+\"\\\" ";
//        		DataMapper.formatVarName(cols[i])+">\"+\n";
        }
        //text+="\t\t\t\t\"</"+tableName+">\\n\";\n"+
        text+="/>\\n\";\n";
        text+="\t\treturn xml;\n\t}//end toXML()\n";
        return text;
    }


    /**
    *gen insert sql
    */
    public String makeInsertSQL(String[] cols){
        String sql=
        "\n\t/****************************************************\n"+
        "\t*Build SQL insert statement without a where clause \n"+
        "\t*@return sqle  \n"+       
        "\t*\n"+
        "\t*******************************************************/ \n"+
        "\tpublic static final String getInsertSQL(){\n"+
        "\t\tString sql = \"";
        if(DataMapper.useUpCaseTableName){
        	sql+=DataMapper.mapInsertSQL(cols,getTableName().toUpperCase() )+"\";\n";
        }else{
        	sql+=DataMapper.mapInsertSQL(cols,getTableName() )+"\";\n";
        }
        sql+="\t\treturn sql;\n";
        sql+="\t}\n";
        return sql;
    }

    /************************************************8**
    *build class based on template
    ********************************************************/
    protected String buldClass() throws Exception{
		System.out.println("start buildClass");
    	String[] colNames=getColNames();
    	int[] colTypes=getColTypes();
    	
        if( colNames!=null &&  colTypes !=null){
            String xml = makeXML(DataMapper.formatClassName(getTableName() ), colNames, colTypes);
            String getsSets = DataMapper.makeGettersSetters( colNames, colTypes);
           // getsSets+=DataMapper.makeOverloadSetters( colNames, colTypes);
            //Object[] keys= this.getPrimaryKeys().toArray();
            //int[] types=this.getPrimaryKeyTypes();

            String vars = DataMapper.makeJDBCPrivateVars( colNames,colTypes);
            /*String[] p = new String[12];
            p[0]= DataMapper.formatClassName(getTableName() );
            p[1]=vars;
            p[2]=getsSets;
            p[3]=xml;
            p[4]=makeGetData(colNames,colTypes);
            p[5]=makeSetData(colNames,colTypes);
            p[6]=makeInsertSQL(colNames);
            p[7]=makeUpdateSQL(colNames);
            p[8]=makeSelectSQL(colNames);
            p[9]=makeLoad(colNames);
            p[10]=makeTypes(colNames,colTypes);
            p[11]=makeJSON(colNames,colTypes);*/
            
            HashMap<String,String> valueMap = new HashMap<String,String>();
            valueMap.put("javaWebGen.className",DataMapper.formatClassName(getTableName() ));
            valueMap.put("javaWebGen.vars",vars);
            valueMap.put("javaWebGen.getsSets",getsSets);
            valueMap.put("javaWebGen.xml",xml);
            valueMap.put("javaWebGen.types",makeTypes(colNames,colTypes) );
            valueMap.put("javaWebGen.json",makeJSON(colNames,colTypes) );
            valueMap.put("javaWebGen.Overrides",makeOverides(colNames,colTypes) );
            
            StringSubstitutor sub = new StringSubstitutor(valueMap);
            
           // String classText = getClassText(classTemplate,p);
            return sub.replace(classTemplate);
        }else{
            return "";
        }
        
    }

    protected String makeOverides(String[] colNames, int[] colTypes) {
		String text=
				  "\t@Override\n"
				+ "\t/***********************************************\n"
				+ "\t* Check value of data bound object.   \n"
				+ "\t*@param object to check value of\n"
				+ "\t************************************************/\n"
				+ "\tpublic boolean equals(Object o){\n"
				+ "\t\tif( o!=null && o instanceof FormBeanAware){\n"
				+ "\t\t\tFormBeanAware bean =(FormBeanAware) o;\n"
				+ "\t\t\treturn bean.toXML().equals(bean.toXML() );\n"
				+ "\t\t}else{\n"
				+ "\t\t\treturn false;\n"
				+ "\t\t}\n"
				+ "\t}\n";
		return text;
	}

	protected String makeSetData(String[] colNames, int[] colTypes) {
		String text=
	        "\n\t/*****************************************************************\n"+
	         "\t*Populates object with data\n"+
	         "\t*@param data matching the data from a table\n"+
	         "\t*@see org.javaWebGen.data.DAO\n"+
	         "\t*@see org.javaWebGen.data.DbResult\n"+
	         "\t*******************************************************************/\n"+
	         "\tpublic void setData(Object[] data) throws IllegalArgumentException{\n"+
	         "\t\tif( data.length != "+colNames.length+"){\n"+
	         "\t\t\tthrow new IllegalArgumentException(\"query return wrong number of rows \"+data.length);\n"+
	         "\t\t} //end if\n\n"+       
             DataMapper.makeSetDataMethod(colNames,colTypes)+
            
             "\t}//end setData\n";

			
		return text;
	}

	protected String makeGetData(String[] colNames, int[] colTypes) {
        String text=
            "\n\t/************************************************\n"+
            "\t*Get all data Objects bound to data bean  \n"+
            "\t*@return data from object as array of objects \n"+
            "\t****************************************************/\n"+
            "\tpublic Object[] getData()"+
            "{\n"+
            "\t\tObject[] data = new Object["+colNames.length+"];\n"+
            DataMapper.mapGetDataMethod(colNames,colTypes)+
            "\t\treturn data;\n"+
            "\t} //end getData\n";
		return text;
	}
	   /**
	    * gen JSON for databean
	    */
	    protected String makeJSON( String[] cols, int[] types) throws Exception{
	    	String beanName = DataMapper.formatClassName(getTableName() );
	        String text=
	        "\n\t/*********************************************\n"+
	        "\t*Builds a JSON String based on object data\n"+
	        "\t*@return JSON text string\n"+
	        "\t************************************************/\n"+
	        "\tpublic String toJSON() {\n"+
	        "\n\t\tJSONObject jo = new JSONObject();\n"+
	        "\t\ttry{\n";

	        for( int i=0;i<cols.length;i++){
		        text+="\t\t\tjo.append(\""+DataMapper.formatVarName(cols[i])+"\",get"+DataMapper.formatMethodName(cols[i])+"() );\n"; 
	        }
	        text+=
    		"\t\t\treturn jo.toString();\n "+
           "\t\t}catch(JSONException je){\n"+
     	   "\t\t\treturn \" "+beanName+"{exception:'\"+je.getMessage()+\"}'\";\n "+
    	   "\t\t}\n"+
    	   "\t} //end to Json\n";
	      
	       
	        return text;
	    }
	
	/**
    * exec write Java class
    * 
    */
    @Override
    protected void execute() throws UtilException{
        try {
			writeJavaClass(buldClass() );
		} catch (Exception e) {
			throw new UtilException(UtilException.CODE_GENERATOR_EXEC,e);
		}
    }
    /**
     * process stuff after main execute loop has finished 
     * processing tables
     */
    @Override
    protected void postExecute() throws UtilException {
    	 
    	
    }
    /**
    *Write out generated class
    */
   
    protected void writeJavaClass(String text) throws IOException{
        String name = DataMapper.formatClassName(getTableName() );
        String fileName=getFilePath()+File.separator+name+".java";
        FileWriter file = new FileWriter(fileName);
        PrintWriter out = new PrintWriter(file);
        out.print(text);
        out.flush();
        out.close();
        log.info("---wrote file="+fileName+"---");
    }

	/**
     * main
     */
     public static void main(String[] args) throws Exception {
        
         	GenerateDataBean app = new GenerateDataBean();
         	app.setCmdParms(args);
         	app.init();
            app.processXmlFile(app.getFileName());
        	
     }
     
 
 /**
  * 
  */
     @Override
 	public void useage() {
         System.out.println("To Process all tables in a text file");
         System.out.println("USAGE GenerateJDBCDataBean  <schema XML> <path>");
         System.exit(1);
 	}

}