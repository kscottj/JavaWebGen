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

import java.util.HashMap;

import org.apache.commons.text.StrSubstitutor;



/**
 * Generated database aware JavaBean objects based on database config
 * @author Kevin scott
 * @version $Revision: 1.2 $
 *
 */
public class GenerateJDODataBean extends GenerateJDBCDataBean {
	@SuppressWarnings("unused")
	private boolean useJDO=true;


   
	public static final String VERSION="GenerateJDODataBean v2_10";

     	private String classTemplate=
         "/*\n"+
        "Copyright (c) 2012-2013 Kevin Scott\n"+
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
     "import javax.jdo.annotations.*;\n"+
     "import org.javaWebGen.exception.DBException;\n"+
     "import org.javaWebGen.data.DbResult;\n"+
     "import org.javaWebGen.data.DataBean;\n"+
     "import org.javaWebGen.util.StringUtil;\n"+
     "import org.json.*;\n"+
      "/******************************************************************************\n"+
      "* WARNING this class is generated by "+VERSION+" based on Database schema\n"+
      "* This class should not be modified, but may be extended.\n" +
      "* It will be regenerated when the database schema changes.\n"+ 
      "*******************************************************************************/\n"+	
      "@PersistenceCapable(identityType = IdentityType.APPLICATION)\n"+
      "@SuppressWarnings({ \"serial\", \"unused\" }) //TODO generator should be smarter about this\n"+
      "public class ${javaWebGen.className} implements DataBean, Serializable{ \n"+
      "//begin private Vars\n"+
      "private static int[] types=null;\n"+
      "${javaWebGen.intTypes}\n"+
      "//begin getters and setters\n"+
      "${javaWebGen.getter.setter}\n"+
      "//begin toXML()\n"+
      "${javaWebGen.xml}\n"+
      "//begin getData\n"+
      "${javaWebGen.databean}\n"+
      "//begin setData\n"+
      "${javaWebGen.setData}\n"+
      "//begin get Insert\n"+
      "${javaWebGen.insert}\n"+
      "//begin get update\n"+
      "${javaWebGen.update}\n"+
      "//begin get select\n"	 +
      "${javaWebGen.select}\n"+
      "//begin get makeLoad\n" +
      "${javaWebGen.makeLoad}\n"+
     "//begin make Type \n"	 +         
     "${javaWebGen.makeType}\n"+
     "//make JSON text \n"	 +         
     "${javaWebGen.jasonText}\n"+
     
     "}//end Generated class\n";




    /**
    *
    */
     @Override
    public void init(){

    	classTemplate=getTemplate(classTemplate);
    }




 


    /************************************************8**
    *build class based on template
    ********************************************************/
    @Override
    protected String buldClass() throws Exception{
		System.out.println("start buildClass");
    	//String[] makeUpdateSQL=getColNames();
    	int[] colTypes=getColTypes();
    	String[] colNames=getColNames();
    	
        if( colNames!=null &&  colTypes !=null){
            String xml = makeXML(DataMapper.formatClassName(getTableName() ), colNames, colTypes);
            String getsSets = DataMapper.makeGettersSetters( colNames, colTypes);
            getsSets+=DataMapper.makeOverloadSetters( colNames, colTypes);
           // Object[] keys= this.getPrimaryKeys().toArray();
            //int[] types=this.getPrimaryKeyTypes();

            String vars = DataMapper.makeJDOPrivateVars( colNames,colTypes,this.getIsKeyArray() );
            //String[] p = new String[12];
            HashMap<String,String> valuesMap=new HashMap<String,String>();
            valuesMap.put("javaWebGen.className",DataMapper.formatClassName(getTableName() ) );
            valuesMap.put("javaWebGen.intTypes",vars );
            valuesMap.put("javaWebGen.getter.setter",getsSets );
            valuesMap.put("javaWebGen.xml",xml );
            valuesMap.put("javaWebGen.databean",makeGetData(colNames,colTypes) );
            valuesMap.put("javaWebGen.setData",makeSetData(colNames,colTypes) );
            valuesMap.put("javaWebGen.insert",makeInsertSQL(colNames) );
            valuesMap.put("javaWebGen.update",makeUpdateSQL(colNames) );
            valuesMap.put("javaWebGen.select",makeSelectSQL(colNames) );
            valuesMap.put("javaWebGen.makeLoad",makeLoad(colNames) );
            valuesMap.put("javaWebGen.makeType",makeTypes(colNames,colTypes) );
            valuesMap.put("javaWebGen.jasonText",makeJSON(colNames,colTypes) );        
            
           StrSubstitutor sub = new StrSubstitutor(valuesMap);
           /*
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
            p[11]=makeJSON(colNames,colTypes);
            */
           
            //String classText = getClassText(classTemplate,p);
           String classText = sub.replace(classTemplate);
            return classText;
        }else{
            return "";
        }
        
    }


	/**
     * main
     */
     public static void main(String[] args) throws Exception {
        
    	 	GenerateJDODataBean app = new GenerateJDODataBean();
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
         System.out.println("USAGE GenerateJDODataBean -f <filename> <path>");
         System.exit(1);
 	}

}