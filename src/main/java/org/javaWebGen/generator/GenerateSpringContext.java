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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import org.javaWebGen.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.text.StringSubstitutor;
/**
 * Generate Spring Context from the database configuration(torque)XML. 
 * creates context xml file JavaWebGenContext.xml and a JavaClass to lookup Spring objects.
 * @author Kevin scott
 *
 */
public class GenerateSpringContext extends CodeGenerator{
	public boolean useJDO=true;
    public static final String VERSION="GenerateSpringContext v1_07";
    private static final Logger log = LoggerFactory.getLogger(GenerateSpringContext.class);
    private String springContextTemplate="/*\n"+
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
           " SOFTWARE.\n "+
           "*/ \n"+
           
            "package org.javaWebGen;\n\n"+
            "import javax.annotation.Generated;\n"+
            "import org.slf4j.Logger;\n"+
            "import org.slf4j.LoggerFactory;\n"+
            "import org.springframework.beans.BeansException;\n"+
            "import org.springframework.context.ApplicationContext;\n"+
            "import org.springframework.context.ApplicationContextAware;\n"+
            "import org.springframework.context.support.ClassPathXmlApplicationContext;\n" +
            "import org.javaWebGen.data.dao.DaoFactory;\n" +
            "import org.javaWebGen.model.ServiceFactory;\n"+
            "/******************************************************************************\n"+
            " This class is generated by "+VERSION+" based on Database schema     \n"+
            "  This class will NOT be regenerated when the database schema is changed. \n"+
            "  @author Kevin Scott                                                        \n"+
            "*******************************************************************************/\n"+
        	"@Generated(value = { \"org.javaWebGen.generator.GenerateSpringController\" })\n" +
            "public class JavaWebGenContext implements  ApplicationContextAware{ \n"+
            "//begin private Vars\n"+
            "   private static  ApplicationContext ctx=null;\n" +
            "   public final static String SPRING_CONTEXT=\"JavaWebGenContext.xml\";\n"+
            "   public final static Logger log = LoggerFactory.getLogger(JavaWebGenContext.class);\n"+
        	"   @Override\n"+
        	"   public void setApplicationContext(ApplicationContext appContext) throws BeansException {\n"+
        	"	   ctx=appContext;\n"+
        	"   }\n"+  
	       	"   private static synchronized ApplicationContext getSpringContext(){\n"+
			"     if(ctx==null){ //if not set load it from classpath\n"+	
			"	      ctx= new ClassPathXmlApplicationContext(SPRING_CONTEXT);\n"+
			"	     log.warn(\"Using default SpringContext should only see this in Unit testing=\"+ctx);\n"+
			"     }\n"+
			"    return ctx;\n"+
    		"  }\n"+
        	"  public static DaoFactory getDao(){\n"+        	
        	"	  return (DaoFactory)  getSpringContext().getBean(\"DaoFactory\"); \n"+
        	"  }\n"+
        	"  public static ServiceFactory getModel(){\n"+        	
        	"	  return (ServiceFactory)  getSpringContext().getBean(\"ServiceFactory\"); \n"+
        	"  }\n"+
        	"  public static ServiceFactory getService(){\n"+        	
        	"	  return (ServiceFactory)  getSpringContext().getBean(\"ServiceFactory\"); \n"+
        	"  }\n"+
            "}\n";
    public String busFactory=
    		"package org.javaWebGen.model;\n"+
    		"public class ServiceFactory{}";
    public String daoFactory=
    		"package org.javaWebGen.data.dao;\n"+
    		"public class DaoFactory{}";
    public String jdbcDataSource="bean class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">\n "+
    	    "    <property name=\"locations\">\n "+
    	    "        <list>\n "+
    	    "           <value>classpath:db.properties</value> \n "+   
    	    "        </list>\n "+
    	    "    </property>\n "+
    	    "</bean>\n "+
    	    "<bean id=\"dataSource\" destroy-method=\"close\" class=\"org.apache.commons.dbcp.BasicDataSource\"> \n"+
    	    "    <property name=\"driverClassName\" value=\"${jdbc.driverClassName}\"/> \n"+
    	    "    <property name=\"url\" value=\"${jdbc.url}\"/> \n"+
    	    "    <property name=\"username\" value=\"${jdbc.username}\"/> \n"+
    	    "    <property name=\"password\" value=\"${jdbc.password}\"/> \n"+
    	    "</bean>\n";
    public String jdopmf=
			"<bean id=\"jdopmf\" class=\"org.javaWebGen.data.dao.PMF\" /> \n"+
			"<bean id=\"pmf\" class=\"javax.jdo.PersistenceManagerFactory\" \n"+
		    "	factory-bean=\"jdopmf\" \n"+
		    "	 factory-method=\"createInstance\" >\n"+
		    "</bean>  \n"+
		    "<bean id=\"jdoTransactionManager\" class=\"org.springframework.orm.jdo.JdoTransactionManager\">\n"+
		    "   <property name=\"persistenceManagerFactory\">\n"+
		    "       <ref local=\"pmf\"/>\n"+
		    "  </property>\n"+
		    "</bean>\n"; 

 	
    public String xmlTempate=""+
    	    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
    	    "<beans xmlns=\"http://www.springframework.org/schema/beans\"\n"+
    	    "   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"+
    	    "   xmlns:context=\"http://www.springframework.org/schema/context\"\n "+
    	    "   xsi:schemaLocation=\"http://www.springframework.org/schema/beans \n "+
    	    "       http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\n "+
    	    "       http://www.springframework.org/schema/context\n "+
    	    "       http://www.springframework.org/schema/context/spring-context-3.0.xsd\" default-lazy-init=\"true\" >\n "+
    	    "           \n "+
    	    //" <context:component-scan base-package=\"org.javaWebGen\"/>\n "+ //VERY slow starup not fit for production cloud
    	    "<!-- Generated by"+VERSION+"-->\n"+
    		" <!-- define daos-->\n" + 	     
    		" \n${javaWebGen.daos}\n"+
    		" <!-- set daofactory-->\n" +
    		"<bean id=\"DaoFactory\" class=\"org.javaWebGen.data.dao.DaoFactory\" >\n"+
    		" ${javaWebGen.daoFactory}\n"+
    		"</bean>\n"+
    		" <!-- define model-->\n" +
    		" ${javaWebGen.modelBeans}\n"+
    		" <!-- set modelFactory-->\n" +
    		"<bean id=\"ServiceFactory\" class=\"org.javaWebGen.model.ServiceFactory\" >\n"+
    		" ${javaWebGen.modelFactoryBeans}\n"+
    	
    		"</bean>\n"+
    		"</beans>\n";
    
    /**
     * main table loop called after each table
     */
	@Override
	protected void execute() throws UtilException {
		// noting to do here
		
	}

	@Override
	protected void postExecute() throws UtilException {
		log.info("postExecute()>>>");
    	try {
		 
			writeContext();
			writeSpringXML();
			log.info("<<<postExecute()");
		} catch (IOException e) {
			throw new UtilException(UtilException.CODE_GENERATOR_EXEC,e);
		}
		
	}

	@Override
	public void useage() {
        System.err.println("GenerateSpringContext based on schema");
        System.err.println("<schema> torque XML with tables definitions");
        System.err.println("<path> destination file path");
        System.err.println("-template optional filename of template to use for code generator");
        System.err.println("-jdo generate JDO context code instead of JDBC");
        System.err.println("USAGE GenerateController <schema xml> <path> [-template fileName] [-jdo]");
        System.exit(1);

	}

	/**
	 * Init templates
	 */
	@Override
	public void init() throws UtilException {
		xmlTempate=getTemplate(xmlTempate);
		springContextTemplate=getTemplate(springContextTemplate);
		
	}
	
	
	  /**
     * 
     * @throws IOException
     */
    protected void writeSpringXML() throws IOException{
    	List<String> tableNames=this.getEntityNames();
    	
    	/*String[] p= new String[5];
     	p[0]=makeDaoBeans(tableNames);
     	p[1]=this.makeDaoFactoryBeans(tableNames);

     	p[2]=makeModelBeans(tableNames);
     	p[3]=makeModelFactoryBeans(tableNames);
    	
     	if(this.useJDO){
     		p[4]=this.jdopmf;
     	}else{
     		p[4]=this.jdbcDataSource;
     	}*/
     	
     	HashMap<String,String>valueMap = new HashMap<String,String>();
     	valueMap.put("javaWebGen.daos", makeDaoBeans(tableNames) );
     	//valueMap.put("javaWebGen.daoBeans", makeDaoBeans(tableNames) );
     	valueMap.put("javaWebGen.daoFactory", makeDaoFactoryBeans(tableNames) );
     	valueMap.put("javaWebGen.modelBeans", makeModelBeans(tableNames) );
     	valueMap.put("javaWebGen.modelFactoryBeans", makeModelFactoryBeans(tableNames) );
     	

     	
     	if(this.useJDO){
     		valueMap.put("javaWebGen.doaBeans", jdopmf );
     		valueMap.put("javaWebGen.doaBeans", "" );
     	}else{
     		valueMap.put("javaWebGen.doaBeans", "" );
     		valueMap.put("javaWebGen.doaBeans", jdbcDataSource );
     	}
     	
     	StringSubstitutor sub = new StringSubstitutor(valueMap);
     	String text=sub.replace(xmlTempate);
     	
     	 
        String fileName=getFilePath()+"/../../../resources/JavaWebGenContext.xml";
    	File file=new File(fileName);
    	// if(!file.exists() ){ //always write it
	        FileWriter fw = new FileWriter(file);
	        PrintWriter out = new PrintWriter(fw);
	        out.print(text);
	        out.flush();
	        out.close();
	        log.info("---write file="+fileName+"---");
    	// }
    }
    protected void writeContext() throws IOException{
    	
     	String text=this.daoFactory;
        String fileName=getFilePath()+File.separator+"data/dao/DaoFactory.java";
    	File file=new File(fileName);
        if(!file.exists() ){
	        FileWriter fw = new FileWriter(file);
	        PrintWriter out = new PrintWriter(fw);
	        out.print(text);
	        out.flush();
	        out.close();
	        log.info("---write file="+fileName+"---");
        }
        //just to make the first from scratch compile easy
     	text=this.busFactory;
        fileName=getFilePath()+File.separator+"model/ServiceFactory.java";
    	file=new File(fileName);
        if(!file.exists() ){
	        FileWriter fw = new FileWriter(file);
	        PrintWriter out = new PrintWriter(fw);
	        out.print(text);
	        out.flush();
	        out.close();
	        log.info("---write file="+fileName+"---");
        }
    	
     	text=this.springContextTemplate;
        fileName=getFilePath()+File.separator+"JavaWebGenContext.java";
    	file=new File(fileName);
        if(!file.exists() ){
	        FileWriter fw = new FileWriter(file);
	        PrintWriter out = new PrintWriter(fw);
	        out.print(text);
	        out.flush();
	        out.close();
	        log.info("---write file="+fileName+"---");
        }
    	
    }
    private String makeDaoBeans(List<String> tableNames){

    	StringBuffer buffer = new StringBuffer();
    	buffer.append("\n<!--define generated DAO beans -->\n");
    	for(String tableName:tableNames){
    		
    		buffer.append("<bean id=\""+DataMapper.formatClassName(tableName)+"Dao\" class=\"org.javaWebGen.data.dao."+DataMapper.formatClassName(tableName)+"DAO\"> \n");
    		if (this.useJDO){
    			//now writes a static PMF object so spring does not need to set this anymore
    			//buffer.append("   <property name=\"persistenceManagerFactory\" ref=\"pmf\" />\n");
    		}else{
    			buffer.append("   <property name=\"dataSource\" ref=\"dataSource\" />\n");
    		}
    		buffer.append("</bean>\n");
    		 
      	}
    	return buffer.toString();
    }
    private String makeModelBeans(List<String> tableNames){
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("\n<!--define generated model beans -->\n");
    			
    	for(String tableName:tableNames){
    		buffer.append("<bean id=\""+DataMapper.formatClassName(tableName)+"Model\" class=\"org.javaWebGen.model."+DataMapper.formatClassName(tableName)+"Model\"> \n");
    		buffer.append("</bean>\n");
      	}
    	return buffer.toString();
    }
    private String makeDaoFactoryBeans(List<String> tableNames){

    	StringBuffer buffer = new StringBuffer();
    	for(String tableName:tableNames){
    		buffer.append("   <property  name=\""+DataMapper.formatClassName(tableName)+"Dao\" ref=\""+DataMapper.formatClassName(tableName)+"Dao\" />\n");
      	}
    	return buffer.toString();
    }
    private String makeModelFactoryBeans(List<String> tableNames){
    	StringBuffer buffer = new StringBuffer();
    	for(String tableName:tableNames){
    		buffer.append("   <property  name=\""+DataMapper.formatClassName(tableName)+"Model\" ref=\""+DataMapper.formatClassName(tableName)+"Model\" />\n");
      	}
    	return buffer.toString();
    }
  	/**
     * main
     */
     public static void main(String[] args) {
         try{ 	
        	 GenerateSpringContext app = new GenerateSpringContext();
        	 String template=app.searchCmdParm(args, "-template");
        	 
        	 if(template!=null){
        		 app.setTemplateName(template);
        		 log.debug("template="+template);
        		 
        	 }
        	 String jdoSwitch=app.searchCmdParm(args, "-jdo");
        	 if(jdoSwitch!=null){
        		 app.useJDO=true;
        		 log.warn("useJDO=true");
        	 }else{
        		 app.useJDO=false; //use jdbc context
        		 log.info("default to JDBC context");
        	 }
        	
        	 if(args.length > 1 && args.length <6 ){
     			app.setScemaXml(args[0]);
     			app.setFilePath(args[1]);
	         	app.init();
	         	app.processXmlFile(app.getFileName());
        	 }else{
        		 app.useage();
        	 }
            
        }catch(Exception e){
        	log.error("main",e);
          
        }
   }
}
