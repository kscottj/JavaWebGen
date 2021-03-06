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
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.text.StringSubstitutor;
import org.javaWebGen.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generated database aware JavaBean objects based on database config
 * @author Kevin scott
 * @version $Revision: 1.2 $
 *
 */
public class GenerateJDBCTestCase extends TestCaseHelpers {
	 private final static Logger log= LoggerFactory.getLogger(GenerateJDBCTestCase.class);
     public static final String VERSION="GenerateTestCase v2_05";
     public static final int DEFAULT_PRIMARYKEY_ID=-1;
     public static final String DEFAULT_PRIMARYKEY_VALUE="default";
     private String className=null;

     private String classTemplate=
         "/*\n"+
         "Copyright (c) 2018 Kevin Scott\n"+
         "Permission is hereby granted, free of charge, to any person obtaining a copy of \n"+
        " this software and associated documentation files (the \"Software\"), to deal in \n"+
        " the Software without restriction, including without limitation the rights to \n"+
        " use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies\n"+
         "of the Software, and to permit persons to whom the Software is furnished to do \n"+
        " so, subject to the following conditions:\n"+
        "\n"+
        " The above copyright notice and this permission notice shall be included in all \n"+
        " copies or substantial portions of the Software.\n"+
        "\n"+
        " THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR \n"+
         "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, \n"+
        " FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE \n"+
        " AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER \n"+
        " LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, \n"+
        " OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE \n"+
        " SOFTWARE.\n "+
        "*/ \n"+
     "package org.javaWebGen.test;\n\n"+
     "import java.sql.Connection;\n"+
     "import java.sql.Statement;\n"+
     "import java.util.*;\n"+
     "import javax.naming.Context;\n"+
     "import javax.naming.InitialContext;\n"+
     "import javax.sql.DataSource;\n"+
     "import org.junit.Assert;\n"+
     "import org.junit.BeforeClass;\n"+
     "import org.junit.Test;\n"+
     "import org.javaWebGen.data.bean.*;\n"+
     "import org.javaWebGen.data.DAO;\n"+
     "import org.javaWebGen.util.TestJndiSetup;\n"+
     "import org.javaWebGen.model.*;\n"+
     "import org.javaWebGen.config.Conf;\n"+
 

      "/******************************************************************************\n"+
      "* WARNING this class is generated by "+VERSION+" based on Database schema     \n"+
      "* This class will <b>NOT</b> be regenerated if the database schema changes. \n"+
      "It is only a place holder\n" +
      "* It should be used by adding addition test cases for the business logic\n"+
      "* in the model layer.  It will not test constraints in the data base other\n"+
      "* than the PRIMARY KEY!"+
      "*******************************************************************************/\n"+
      "public class ${javaWebGen.className} extends MockRequestTestCase{ \n"+
      "//begin private Vars\n"+
      "${javaWebGen.vars}\n"+
      "//begin testCRUD()\n"+
      "${javaWebGen.crud}\n"+
      "${javaWebGen.create}\n"+
     "}//end Generated class\n";
     
     public String mockRequest=
    	"package org.javaWebGen.test;\r\n" + 
    	"\r\n" + 
    	"import static org.mockito.Matchers.anyString;\r\n" + 
    	"import static org.mockito.Mockito.mock;\r\n" + 
    	"import static org.mockito.Mockito.when;\r\n" + 
    	"\r\n" + 
    	"import java.util.Map;\r\n" + 
    	"import java.util.Vector;\r\n" + 
    	"import java.util.concurrent.ConcurrentHashMap;\r\n" + 
    	"\r\n" + 
    	"import javax.servlet.http.HttpServletRequest;\r\n" + 
    	"import javax.servlet.http.HttpServletResponse;\r\n" + 
    	"import javax.servlet.http.HttpSession;\r\n" + 
    	"\r\n" + 
    	"import org.javaWebGen.WebSession;\r\n" + 
    	"import org.javaWebGen.config.WebConst;\r\n" + 
    	"import org.junit.Before;\r\n" + 
    	"import org.mockito.Mockito;\r\n" + 
    	"import org.mockito.invocation.InvocationOnMock;\r\n" + 
    	"import org.mockito.stubbing.Answer;\r\n" + 
    	"\r\n" + 
        "/******************************************************************************\n"+
        "* WARNING this class is generated by "+VERSION+"   \n"+
        "* This class will <b>NOT</b> be regenerated. if the database schema changes. \n"+
        "* This class will mock a @see HttpServletRequest.  Used for testing controller(Action) classes"
        + "and verifying WebForm values\n"+
        "*******************************************************************************/\n"+
    	"public class MockRequestTestCase {\r\n" + 
    	"	\r\n" + 
    	"	/** Mock request. */\r\n" + 
    	"	private HttpServletRequest request;\r\n" + 
    	"\r\n" + 
    	"	/** Mock response. */\r\n" + 
    	"	private HttpServletResponse response;\r\n" + 
    	"\r\n" + 
    	"	/** Mock session. */\r\n" + 
    	"	private HttpSession session;\r\n" + 
    	"\r\n" + 
    	"	/** Session's attribute map. */\r\n" + 
    	"	// Collection to store attributes keys/values\r\n" + 
    	"	private Map<String, Object> attributes = null;\r\n" + 
    	"	\r\n" + 
    	"	private Vector<String> reqNames=null;\r\n" + 
    	"	\r\n" + 
    	"	@Before\r\n" + 
    	"	public  void setup() throws Exception{\r\n" + 
    	"		mockSetup();\r\n" + 
    	"		reqNames=new Vector<String>();\r\n" + 
    	"	}\r\n" + 
    	"\r\n" + 
    	"	private void mockSetup() {\r\n" + 
    	"	    attributes = new ConcurrentHashMap<String, Object>();   \r\n" + 
    	"	   \r\n" + 
    	"        request = mock(HttpServletRequest.class);\r\n" + 
    	"        response = mock(HttpServletResponse.class);\r\n" + 
    	"        session = mock(HttpSession.class);\r\n" + 
    	"        session.setAttribute(WebConst.WEB_SESSION, new WebSession() ); \r\n" + 
    	"        when(request.getSession()).thenReturn(session);\r\n" + 
    	"\r\n" + 
    	"        Mockito.doAnswer(new Answer<Void>() {\r\n" + 
    	"            @Override\r\n" + 
    	"            public Void answer(InvocationOnMock invocation) throws Throwable {\r\n" + 
    	"                Object[] args=invocation.getArguments();\r\n" + 
    	"                String key = (String) args[0];\r\n" + 
    	"                Object value= args[1];\r\n" + 
    	"                attributes.put(key, value);\r\n" + 
    	"                System.out.println(\"put attribute key=\"+key+\", value=\"+value);\r\n" + 
    	"                return null;\r\n" + 
    	"            }\r\n" + 
    	"        }).when(request).setAttribute(Mockito.anyString(), Mockito.anyObject());\r\n" + 
    	"\r\n" + 
    	"        // Mock getAttribute\r\n" + 
    	"        Mockito.doAnswer(new Answer<Object>() {\r\n" + 
    	"            @Override\r\n" + 
    	"            public Object answer(InvocationOnMock invocation) throws Throwable {\r\n" + 
    	"            	 Object[] args=invocation.getArguments();\r\n" + 
    	"                String key =(String)   args[0];\r\n" + 
    	"                Object value = attributes.get(key);\r\n" + 
    	"                System.out.println(\"get attribute value for key=\"+key+\" : \"+value);\r\n" + 
    	"                return value;\r\n" + 
    	"            }\r\n" + 
    	"        }).when(request).getAttribute(Mockito.anyString());\r\n" + 
    	"        when(session.getAttribute(anyString())).thenAnswer(new Answer() {\r\n" + 
    	"            /**\r\n" + 
    	"             * @see org.mockito.stubbing.Answer#answer(org.mockito.invocation.InvocationOnMock)\r\n" + 
    	"             */\r\n" + 
    	"            @Override\r\n" + 
    	"            public Object answer(InvocationOnMock aInvocation) throws Throwable {\r\n" + 
    	"                String key = (String) aInvocation.getArguments()[0];\r\n" + 
    	"                return attributes.get(key);\r\n" + 
    	"            }\r\n" + 
    	"        });\r\n" + 
    	"       \r\n" + 
    	"		\r\n" + 
    	"	}\r\n" + 
    	"	public HttpServletRequest setRequestParm(HttpServletRequest req,String name, String value) {\r\n" + 
    	"		when(req.getParameter(name) ).thenReturn( value);\r\n" + 
    	"		reqNames.addElement(name);\r\n" + 
    	"		\r\n" + 
    	"		when(req.getParameterNames() ).thenReturn(reqNames.elements());\r\n" + 
    	"		return req;\r\n" + 
    	"	}\r\n" + 
    	"\r\n" + 
    	"	public HttpServletRequest getRequest() {\r\n" + 
    	"		return request;\r\n" + 
    	"	}\r\n" + 
    	"\r\n" + 
    	"	public HttpServletResponse getResponse() {\r\n" + 
    	"		return response;\r\n" + 
    	"	}\r\n" + 
    	"\r\n" + 
    	"	public HttpSession getSession() {\r\n" + 
    	"		return session;\r\n" + 
    	"	}\n}";	


/**
 * make test Insert TestCase
 * @param cols
 * @param types
 * @return text
 * @throws Exception 
 */
    public String makeCRUD(String[] cols,int[] types) throws Exception{
    	String modelName=DataMapper.formatClassName(getTableName() )+"Model";
    	String beanName=DataMapper.formatClassName(getTableName() )+"";
    	int[] keyTypes=this.getPrimaryKeyTypes();
    	String javaType=DataMapper.getJavaTypeFromSQLType(keyTypes[0]);
    	
    	String findByKey="\t\t"+javaType+" id=model.create(bean);\n";
    	if(keyTypes.length==1){ //only if one primary key
    		findByKey+=
    		"\t\t//test by primary key\n"+		
            "\t\t"+beanName+"  entity=model.getById(id);\n"+
            "\t\tAssert.assertTrue(\"nothing found!\",entity!=null); \n"+
            "\t\tmodel.save(entity);\n";
    	}else{
    		findByKey+=
    	            "\t\t//can only handle one primary key you will need to write a manual testcase!";
    	}
    	String text=
        "\n\t/********************************************\n"+
        "\t*Warning Generated method runs test on generated CRUD operations \n"+       
        "\t************************************************\n"+
        "\t*/ \n"+
        "\t@Test \n"+
        "\tpublic void testCRUD() throws Exception{\n"+
        "\t\t"+modelName+" model = new "+modelName+"();\n"+
        "\t\t"+beanName+" bean = new "+beanName+"();\n"+ 
        findByKey+
        "\t\tList<"+beanName+"> list=model.list();\n"+
        "\t\tAssert.assertTrue(\"nothing in List!\",list.size()>0); \n"+
        "\t\tmodel.remove(entity);\n"+
        "\t}\n";       
        return text;
    }


/**
 * make test Insert TestCase
 * @param cols
 * @param types
 * @return text
 * @throws Exception 
 */
    public String makeTable(String[] cols,int[] types) throws Exception{
    	//String modelName=DataMapper.formatClassName(getTableName() )+"Model";
    	//String beanName=DataMapper.formatClassName(getTableName() )+"";
    	//int[] keyTypes=this.getPrimaryKeyTypes();
    	ArrayList<String> primaryKeys=this.getPrimaryKeys();
    	//String javaType=DataMapper.getJavaTypeFromSQLType(keyTypes[0]);
    	String sql="\"CREATE TABLE "+getTableName()+" (";
    	boolean isFirst=true;
    	for(int i=0;i<cols.length;i++){
    		if(isFirst){
    			isFirst=false;
    		}else{
    			sql+=",";
    		}
    		sql+=cols[i]+ " "+this.getSqlTypeMap().get(cols[i]);
    		if(this.getSizeMap().get(cols[i])!=null){
    			sql+="("+this.getSizeMap().get(cols[i])+")";
    		}
    		boolean isKey=false;
    		for (String keyName:primaryKeys){
    			if(keyName.equals(cols[i])){
    				isKey=true;
    			}
    		}
    		if(isKey){
    			sql+=" NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ";
    		} 

    	
    	}
    	sql+=")\";\n";
    	String text=""
    	 + "\t@BeforeClass\n"
    	 + "\tpublic static void setUpClass() throws Exception {\n"
    	 + "\t\tTestJndiSetup.doSetup(\"testDB\");\n"
    	 + "\t\tcreateTable();\n"
    	 + "\t}\n\n"
       	 + "\tprivate static void createTable() throws Exception{\n"
       	 + "\t  Context ctx = new InitialContext();\n"
		 + "\t  DataSource ds = (DataSource)ctx.lookup( Conf.getConfig().getProperty(DAO.DB_JNDI) );\n"
         + "\t  try(Connection con=ds.getConnection()){	\n" 
         + "\t    String sql="+sql
   		 + "\t    Statement stmt =con.createStatement();\n"
         + "\t    stmt.execute(sql);\n"
      	 + "\t    con.commit(); \n" 
      	+  "\t  } \n"
    	 + "\t}\n";
        return text;
    }
    /************************************************8**
    *build class based on template
    ********************************************************/
    protected String buldClass() throws Exception{
    	String[] colNames=getColNames();
    	int[] colTypes=getColTypes();
    	//String tableName = getTableName();
    	
        if( colNames!=null &&  colTypes !=null){
            String crud = makeCRUD(colNames, colTypes);
            /*String[] parm = new String[7];
            parm[0]= className;       
            parm[1]="";
            parm[2]=crud;*/
            HashMap<String,String> valueMap = new HashMap<String,String>();
            valueMap.put("javaWebGen.className", className);
            valueMap.put("javaWebGen.vars", "");
            valueMap.put("javaWebGen.crud", crud);
            valueMap.put("javaWebGen.create", this.makeTable(colNames, colTypes) ) ;
            StringSubstitutor sub= new StringSubstitutor(valueMap);
            //String classText = this.getClassText(classTemplate,parm);
            return sub.replace(classTemplate);
        }else{
            return "";
        }
        
    }

/**
 * main writing loop called by processFile
 */
    protected void execute() throws UtilException{
    	
        try {
        	className=DataMapper.formatClassName(getTableName() )+"TestCase";
        	
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
       super.postExecute();
    	
    }
    /**
    *Write out generated class
    *
    *@param text
    */
    private void writeJavaClass(String text) throws IOException{
    	//String tableName=getTableName();
	    	
        String fileName=getFilePath()+File.separator+className+".java";
        File file = new File(fileName);
       if(!file.exists() && getPrimaryKeys().size() >0 ){  //only write if file does not exist
        //if(true){  //only write if file does not exist
        	PrintWriter out = new PrintWriter(file);
	        out.print(text);
	        out.flush();
	        out.close();
	        
	        log.debug("---Write TestCase "+className);
        }

    }

	/**
    * main
    */
    public static void main(String[] args) {
        try{ 	
        	GenerateJDBCTestCase app = new GenerateJDBCTestCase();
        	app.setCmdParms(args);
        	app.init();
            app.processXmlFile(app.getFileName());
       	}catch(Exception e){
       		log.error("main error",e);
       		System.exit(911);
       	}
    }
    /**********************************************************
     *generate a string to convert from a string to correct
     *Object type
     *@param primaryKey
     *@param SQL type
     *@return text for generated class
     **********************************************************/
    /* private String getVarDeclaration(String key, int type) {
         	String text="";
         	String name=DataMapper.formatVarName(key.toString() );
         	
         	Util.debug("pkey="+name +"|type="+type);
             switch (type){
                 case Types.CHAR:
                 case Types.VARCHAR:
                 case Types.LONGVARCHAR:
                 	text+="String "+name+"= \""+DEFAULT_PRIMARYKEY_VALUE+"\";\n"; 
                     break;
                 case Types.DATE:
                 case Types.TIMESTAMP:
                  	text+="java.util.Date "+name+"= new java.util.Date();//use current Date\n"; 
                     break;
                 case Types.BIT:
                 case Types.BOOLEAN:
                	 text+="boolean "+name+"= false;\n";
                	 break;
                 case Types.TINYINT:
                	 text+="byte "+name+"= 0;\n";
                	 break;
                	 
                 case Types.SMALLINT:
                	 text+="short "+name+"= -1;\n";
                	 break;
                	 
                 case Types.INTEGER:
                 	text+="int "+name+"= -1;\n"; 
                 break;
                 case Types.BIGINT:    
                 	text+="long "+name+" = -1L;\n"; 
                 	break;
                 case Types.REAL:    
                  	text+="float "+name+" = -1.0F;\n"; 
                  	break;
                 
                 case Types.FLOAT:
                 case Types.DOUBLE:
                  	text+="double "+name+" = -1.0;\n"; 
                 	break;
                 case Types.NUMERIC:
                 case Types.DECIMAL:
                 	text+="BigDecimal "+name+"= new BigDecimal(\"-1.00\");\n"; 
                     break;
                 case Types.LONGVARBINARY:
                 case Types.BINARY:    
                 case Types.BLOB:  
                 case Types.JAVA_OBJECT :  
                 case Types.CLOB : 
                 case Types.OTHER :
                     Util.warn("can not use a BLOB as a key=");
                     break;
             }//end switch                
             
         return text;
     }*/
     /**
      * need to set primary keys to something
      * @param type
      * @return
      */
     /*private String getDefaultValues(String key,int type){
         String text="";
    	 switch (type){
         
	         
	         case Types.CHAR:
	         case Types.VARCHAR:
	         case Types.LONGVARCHAR:
	
	        	 text+="bean.set"+DataMapper.formatClassName(key)+"("+DataMapper.formatVarName(key)+");\n";
	         break;
	         case Types.DATE:
	         case Types.TIMESTAMP:
	        	 text+="bean.set"+DataMapper.formatClassName(key)+"("+DataMapper.formatVarName(key)+");\n";   
	         break;
	         case Types.BIT:
	         case Types.TINYINT:
	         case Types.SMALLINT:
	         case Types.INTEGER:
	         case Types.BIGINT:    
	         case Types.FLOAT:
	         case Types.DOUBLE:
	         case Types.DECIMAL:
	        	 text+="bean.set"+DataMapper.formatClassName(key)+"("+DataMapper.formatVarName(key)+");\n";   
	        	
	         break;
    	 }
    	 return text;
     }*/
/**
 * 
 */
	public void useage() {
        System.out.println("To Process all tables in a text file");
        System.out.println("USAGE GenerateTestCase -f <filename> <path>");
        System.exit(1);
	}

@Override
public void init() throws UtilException {
	// noop
	
}

}