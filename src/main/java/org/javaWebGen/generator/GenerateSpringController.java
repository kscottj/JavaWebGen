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

import java.sql.Types;
import java.util.*;
import java.io.*;

import org.apache.commons.text.StrSubstitutor;
 

import org.javaWebGen.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Generated Web Controller Objects from the database config creates a
 * WebController and a implementation class. The implementation class will be
 * overwritten if it exist
 * 
 * @author Kevin Scott
 * @version $Revision: 1.2 $
 * 
 */
public class GenerateSpringController extends CodeGenerator {
	private final static Logger log = LoggerFactory.getLogger(GenerateSpringController.class);
	public static final String VERSION = "GenerateSpringController v2_021";

	private String className = null;
	private String subClassName = null;

	/*not used
	 * private String xmlConfig = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<beans xmlns=\"http://www.springframework.org/schema/beans\"\n"
			+ "		xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n "
			+ "		xmlns:aop=\"http://www.springframework.org/schema/aop\"\n "
			+ "		xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\">\n"
			+ "     <context:component-scan base-package=\"org.javaWebGen\"/>\n"
			+ "</beans>\n";
*/
	private String classTemplate = "/*\n"
			+ "Copyright (c) 2012-2017 Kevin Scott All rights  reserved.\n"
			+ " Permission is hereby granted, free of charge, to any person obtaining a copy of \n"
			+ " this software and associated documentation files (the \"Software\"), to deal in \n"
			+ " the Software without restriction, including without limitation the rights to \n"
			+ " use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies\n"
			+ "of the Software, and to permit persons to whom the Software is furnished to do \n"
			+ " so.\n"
			+ "\n"
			+ " THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR \n"
			+ " IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, \n"
			+ " FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE \n"
			+ " AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER \n"
			+ " LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, \n"
			+ " OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE \n"
			+ " SOFTWARE.\n "
			+ "*/ \n"
			+ "/* data Acees Object talks to DB */"
			+ "\n"
			+ "package org.javaWebGen.controller;\n"
			+ "import javax.servlet.http.*;\n"
			+ "import java.util.*;\n"
			+ "import org.javaWebGen.data.bean.*;\n"
			+ "import org.javaWebGen.WebController;\n"
			+ "import org.javaWebGen.util.HtmlUtil;\n"
			+ "import org.javaWebGen.util.StringUtil;\n"
			+ "import org.javaWebGen.exception.*;\n"
			+ "import org.javaWebGen.model.*;\n"
			+ "import org.javaWebGen.config.ConfigConst;\n"
			+ "import org.javaWebGen.config.WebConst;\n"
			+ "import org.javaWebGen.JavaWebGenContext;\n"
			+ "import org.slf4j.Logger;\n"
			+ "import org.slf4j.LoggerFactory;\n"
			+ "import org.springframework.web.bind.annotation.ModelAttribute;\n"
			+ "import org.springframework.web.bind.annotation.RequestMapping;\n"
			+ "import org.springframework.ui.Model;\n"
			+ "import org.springframework.validation.BindingResult;\n"
			+ "import org.springframework.validation.BindingResult;\n"
			+ "/******************************************************************************\n"
			+ "* WARNING this class is generated by "
			+ VERSION
			+ " based on Database schema     \n"
			+ "* This class should not be modified!\nIt will be regenerated by the code generator "
			+ "* when the database schema is modified\n"
			+ "* If you need to change the this code you should override main class with what you do not need.\n"
			+ "*******************************************************************************/\n"
			+ "public abstract class ${javaWebGen.className} extends WebController { \n"
			+ "@SuppressWarnings(\"unused\")\n"
			+ "private static final Logger log=LoggerFactory.getLogger(${javaWebGen.className}.class);"
			+ "//begin private Vars\n"
			+ "${javaWebGen.vars}\n"
			+ "//begin update(store)\n"
			+ "${javaWebGen.update}\n"
			+ "//begin delete(store)\n"
			+ "${javaWebGen.delete}\n"
			+ "//begin listAll)\n"
			+ "//${javaWebGen.listAll}\n"
			+ "//begin getDataBean\n"
			+ "${javaWebGen.dataBean}\n"
			+ "${javaWebGen.model}\n"
			+ " //end getModel\n"
			+ "//begin action\n"
			+ "${javaWebGen.action}\n"
			+ "//end action\n"
			+ "}//end impl class\n";

	private String subClassTemplate = "/*\n"
			+ " THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR \n"
			+ "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, \n"
			+ " FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE \n"
			+ " AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER \n"
			+ " LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, \n"
			+ " OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE \n"
			+ " SOFTWARE.\n "
			+ " */\n"
			+ "/* */\n"
			+ "package org.javaWebGen.controller;\n\n"
			+ "import javax.servlet.http.*;\n"
			+ "import javax.servlet.ServletException;\n"
			+ "//import org.javaWebGen.config.*;\n"
			+

			"//import org.slf4j.Logger;\n"
			+ "//import org.slf4j.LoggerFactory;\n"
			+ "import org.springframework.stereotype.Controller;\n"
			+ "import org.springframework.web.bind.annotation.RequestMapping;\n"
			+ "/******************************************************************************\n"
			+ "* This class is generated by "
			+ VERSION
			+ " based on Database schema     \n"
			+ "* This class <b>should</b> be modified.   This class will <b>NOT</b> get\n"
			+ "* regenerated and is just generated as a place holder.\n"
			+ "* @author Kevin Scott                                                        \n"
			+ "* @version $Revision: 1.00 $                                               \n"
			+ "*******************************************************************************/\n"
			+ "@Controller\n"
			+ "@RequestMapping(\"/admin\")\n"
			+ "public class ${javaWebGen.subClassName} extends ${javaWebGen.className} { \n"
			+

			"\t//private final Logger log=LoggerFactory.getLogger(${javaWebGen.subClassName}.class);\n"
			+ "${javaWebGen.json}\n" 
			+"//end doJSON\n" 
			+ "${javaWebGen.soap}\n" 
			+ "//end doSOAP\n"
			+ "}//\n";

	public static final String BEAN_PACKAGE = "org.javaWebGen.data.bean.";

	/**
    *
    */
	public void init() {
		classTemplate = getTemplate(classTemplate);
		subClassTemplate = getTemplate(subClassTemplate);
	}

	/************************************
	 * gen vars
	 *************************************/
	private String makeVars(String[] cols, int[] types) {
		// String beanName=DataMapper.formatClassName(tableName);
		String modelName = DataMapper.formatClassName(getTableName()) + "Model";
		String text = "\t\n/** model for this object **/;\n" +
		// "\tprivate "+beanName+" dataBean;\n"+
				"\tprivate " + modelName + " model= null;\n";

		return text;
	}

	/************************************
	 * gen find by primary key
	 *************************************/
	private String makeFindByPrimaryKey(String[] keys, int[] types) {
		String beanName = DataMapper.formatClassName(getTableName());
		ArrayList<String> primaryKeys = getPrimaryKeys();
		int[] primaryKeyTypes = getPrimaryKeyTypes();

		if (primaryKeyTypes.length != 1) {
			return "//only on primary keys supported can not generated getById method\n";
		}

		if (primaryKeys.size() == 1) {
			String javaType = DataMapper
					.getJavaTypeFromSQLType(primaryKeyTypes[0]);
			String text = "\n\t/***************************************************\n"
					+ "\t*Warning Generated method. get a DataBean with table data in it.\n"
					+ "\t* for one row in the database\n"
					+ "\t*@return DataBean with data from the Model\n"
					+ "\t******************************************************/\n"
					+ "\tprotected "
					+ beanName
					+ " getById("
					+ javaType
					+ " id) throws WebAppException{\n";

			text += TS + TS + beanName + " bean =getModel().getById(id);\n";
			// for(int i=1;i<primaryKeys.size();i++){
			// text+=","+DataMapper.formatVarName(primaryKeys.get(i).toString()
			// );
			// }

			text += TS + TS + "return bean;\n" + TS
					+ "} //find by primary key\n";

			return text;
		}
		return "";
	}

	/************************************
	 * gen create(INSERT)
	 *************************************/
	private String makeInsert(String[] cols, int[] types) {
		String beanName = BEAN_PACKAGE
				+ DataMapper.formatClassName(getTableName());
		String text = "\n\t/***************************************************\n"
				+ "\t*Warning Generated method inserts new data based on the\n"
				+ "\t*values in a DataBean. \n"
				+ "\t*\n"
				+ "\t*@param bean data Bound JavaBean\n"
				+ "\t*@see org.javaWebGen.data.DataBean\n"
				+ "\t******************************************************/\n"
				+ "\tprotected void create("
				+ beanName
				+ " bean) throws WebAppException{\n" +

				"\t\tmodel.create(bean);\n" + "\t} //end create\n";
		return text;
	}

	/************************************
	 * gen update(store)
	 *************************************/
	private String makeUpdate(String[] cols, int[] types) {
		String beanName = DataMapper.formatClassName(getTableName());
		String text = "\n\t/***************************************************\n"
				+ "\t*Warning Generated method updates the database with values "
				+ "\t*in a DataBean \n"
				+ "\t*@param bean data Bound  JavaBean\n"
				+ "\t*@see org.javaWebGen.data.DataBean\n"
				+ "\t******************************************************/\n"
				+ "\tprotected void update("
				+ beanName
				+ " bean) throws WebAppException{\n"
				+ "\t\tgetModel().save(bean);\n" + "\t} //end update\n";
		return text;
	}

	/**
	 * 
	 * @param cols
	 * @param types
	 * @return
	 */
	private String makeAction(String[] cols, int[] types) {
		log.debug("");
		String beanName = DataMapper.formatClassName(getTableName());
		ArrayList<String> primaryKeys = getPrimaryKeys();
		String varName = DataMapper.formatVarName(getTableName()) ;
		String text =
		 "\t/**\n\t*\n\t*/\n"
		+"\t@RequestMapping(\"/"+beanName+"/list.htm\")\n"
		+ "\tpublic String list(Model uiModel) throws WebAppException {\n"
		+ "\t\tList<" + beanName + "> list =getModel().list();\n"
		+ "\t\tuiModel.addAttribute(WebConst.DATA_BEAN_LIST, list);\n"
		// "\t\treq.setAttribute(WebConst.DATA_BEAN_LIST,list);\n"+
		+ "\t\treturn \"/admin/"+beanName+"List\";\n"
		+"} \n";
				
		if (primaryKeys.size() == 1) {
			String javaType = DataMapper.getJavaTypeFromSQLType(this.getPrimaryKeyTypes()[0]);
			//String keyField = DataMapper.formatVarName(this.getPrimaryKeys().get(0));
			String keyGetter = "get"+DataMapper.formatClassName(this.getPrimaryKeys().get(0))+"()";
			
			String webParm= "@ModelAttribute(\""+varName+"\") "+beanName+" "+varName+", BindingResult result, Model uiModel";
			text +=  "\t/**\n\t*\n\t*/\n"
					+"\t@RequestMapping(\"/"	+beanName+ "/detail.htm\")\n"			
					+ "\tpublic String detail("+webParm+") throws WebAppException {\n"
					+ "\t\t"+ beanName+ " dataBean =getModel().getById("+varName+"."+keyGetter+");\n"
					+ "\t\tuiModel.addAttribute(WebConst.FORM_BEAN,dataBean ); \n"
					+ "\t\treturn \"/admin/"+ beanName+ "Detail\";\n"
					+ "\t}\n"
					
					+ "\t/**\n\t*\n\t*/\n"
					+ "\t@RequestMapping(\"/"+beanName+ "/create.htm\")\n"
					+ "\tpublic String create("+webParm+") throws WebAppException {\n"
					+ "\t\t"+ javaType+ " id =this.getModel().create("+varName+");\n"
					+ "\t\t"+ beanName+ " dataBean =getModel().getById(id);\n"
					+ "\t\tuiModel.addAttribute(WebConst.FORM_BEAN,dataBean ); \n"
					+ "\t\treturn \"/admin/"+ beanName+ "Create\";\n"
					+ "\t}\n"

					+ "\t/**\n\t*\n\t*/\n"
					+ "\t@RequestMapping(\"/"+ beanName	+ "/newEntity.htm\")\n"
					+ "\tpublic String newEntity("+webParm+") throws WebAppException {\n"
					+ "\t\t"
					+ "\t\treturn \"admin/"+beanName+"Create\";\n"
					+ "\t}\n"

					+ "\t/**\n\t*\n\t*/\n"
					+ "\t@RequestMapping(\"/"+ beanName	+ "/save.htm\")\n"
					+ "\tpublic String save("+webParm+") throws WebAppException {\n"
					+ "\t\t"+ beanName+ " dataBean="+varName+";\n"
					+ "\t\tthis.getModel().save(dataBean);\n"
					+ "\t\treturn \"redirect:/admin/"+ beanName+ "/list.htm\";\n"
					+ "\t}\n"
					+ "\t/**\n\t*\n\t*/\n"
					+ "\t@RequestMapping(\"/"+ beanName	+ "/delete.htm\")\n"
					+ "\tpublic String delete("+webParm+") throws WebAppException {\n"
					+ "\t\t" + beanName + " dataBean= "+varName+";\n"
					+ "\t\tthis.getModel().remove(dataBean);\n"
					+ "\t\treturn \"redirect:/admin/"+ beanName + "/list.htm\";\n" + "\t}\n";
		}

		return text;
	}

	/**
	 * Not working yet. generates the source code for a WebSevice response
	 * formatted in JSON The implementing class will need to provide security
	 * 
	 * @param cols
	 * @param types
	 * @return method source code
	 */
	private String makeJSONWebService(String[] cols, int[] types) {
		log.trace(">>> enter makeJSONWebService");
		//String beanName = DataMapper.formatClassName(getTableName());
		ArrayList<String> primaryKeys = getPrimaryKeys();
		if (primaryKeys.size() <= 0)
			return "//primaryKeys.size()=" + primaryKeys.size() + "\n";//can not do this without a primary key 
		
		String text = "\n/***************************************************\n"
				+ "* Generated JSON web service results  \n"
				+ "* If table had a primary key supplied in database table\n"
				+ "* GENERATE list, and findById commands\n"
				+ "*\n"
				+ "*@return result JSON text\n"
				+ "*@Exception ServletException throw a http500 error to user\n"
				+ "******************************************************/\n"
				+ "\tpublic String doJSON(HttpServletRequest req) throws ServletException{return \"\";}\n";
		/*
		 * "\t\ttry{\n"+ "\t\t\tif(getCmd(req)==null) {\n"+
		 * "\t\t\t\t throw new WebAppException(WebAppException.VALIDATE_ERROR,\"No command passed on URL\");\n"
		 * +
		 * "\t\t\t}else if(WebController.getCmd(req).equals(WebConst.LIST_CMD) ){\n"
		 * + "\t\t\t\t\t"+beanName+"[] list =getModel().list();\n" +
		 * "\t\t\t\t\tStringBuffer buffer = new StringBuffer(\"[\");\n" +
		 * "\t\t\t\t\tint size=list.length;\n"+
		 * "\t\t\t\t\t  for (int i=0;i<size;i++){;\n"+
		 * "\t\t\t\t\t    "+beanName+" bean = ("+beanName+") list[i];\n"+
		 * "\t\t\t\t\t	   buffer.append(bean.toJSON()+\",\");\n"+
		 * "\t\t\t\t\t  }\n"+
		 * "\t\t\t\t\tJSONArray jarray=new JSONArray(buffer.toString()+\"]\" );\n"
		 * + "\t\t\t\t\treturn jarray.toString();\n"+ "\t\t\t}\n"+
		 * "\t\t\telse if(WebController.getCmd(req).equals(WebConst.DETAIL_CMD) ){\n"
		 * + getWebServiceDetailText(beanName)+
		 * 
		 * "\t\t\t\t\t return dataBean.toJSON();\n"+ "\t\t\t}\n"+
		 * "\t\t\tthrow new WebAppException(WebAppException.VALIDATE_ERROR,\"No VALID command passed on URL\");\n"
		 * + "\t\t}catch(Exception e){\n" +
		 * "\t\t\tlog.error (\"JSON error\",e);\n" +
		 * "\t\t\tthrow new ServletException(\"JSON error\",e);\n"+ "\t\t} \n" +
		 * "\t} \n" ;
		 */
		log.trace("<<< leave makeJSONWebService");
		return text;
	}

	/**
	 * Not working yet. generates the source code for a WebSevice response
	 * formatted in JSON The implementing class will need to provide security
	 * 
	 * @param cols
	 * @param types
	 * @return method source code
	 */
	private String makeSOAPWebService(String[] cols, int[] types) {
		log.trace(">>> enter makeSOAPWebService");
		//String beanName = DataMapper.formatClassName(getTableName());
		ArrayList<String> primaryKeys = getPrimaryKeys();
		if (primaryKeys.size() <= 0) //can not do this without a primary key 
			return "//primaryKeys.size()=" + primaryKeys.size() + "\n";
		String text = "\n/***************************************************\n"
				+ "* This method is experimental!\n"
				+ "* Generated SOAP web service results  \n"
				+ "* If table had a primary key supplied in database table\n"
				+ "* GENERATE list, and findById commands\n"
				+ "*\n"
				+ "*@return result SOAP text\n"
				+ "*@Exception ServletException throw a http500 error to user\n"
				+ "******************************************************/\n"
				+ "\tpublic String doSOAP(HttpServletRequest req) throws ServletException{return \"\";}\n";
		/*
		 * "\t\ttry{\n"+ "\t\t\tif(getCmd(req)==null) {\n"+
		 * "\t\t\t\t throw new WebAppException(WebAppException.VALIDATE_ERROR,\"No command passed on URL\");\n"
		 * +
		 * "\t\t\t}else if(WebController.getCmd(req).equals(WebConst.LIST_CMD) ){\n"
		 * + "\t\t\t\t\t"+beanName+"[] list =getModel().list();\n" +
		 * "\t\t\t\t\tStringBuffer buffer = new StringBuffer();\n" +
		 * "\t\t\t\t\tint size=list.length;\n"+
		 * "\t\t\t\t\t  for (int i=0;i<size;i++){;\n"+
		 * "\t\t\t\t\t    "+beanName+" bean = ("+beanName+") list[i];\n"+
		 * "\t\t\t\t\t	   buffer.append(bean.toXML()+\"\");\n"+
		 * "\t\t\t\t\t  }\n"+
		 * "\t\t\t\t\treturn WebController.SOAP_HEADER+buffer.toString()+SOAP_FOOTER;\n"
		 * + "\t\t\t}\n"+
		 * "\t\t\telse if(WebController.getCmd(req).equals(WebConst.DETAIL_CMD) ){\n"
		 * + getWebServiceDetailText(beanName)+
		 * "\t\t\t\t\treturn WebController.SOAP_HEADER+dataBean.toXML()+SOAP_FOOTER;\n"
		 * + "\t\t\t}\n"+
		 * "\t\t\tthrow new WebAppException(WebAppException.VALIDATE_ERROR,\"No VALID command passed on URL\");\n"
		 * + "\t\t}catch(Exception e){\n" +
		 * "\t\t\tlog.error (\"SOAP error\",e);\n" +
		 * "\t\t\tthrow new ServletException(\"SOAP error\",e);\n"+ "\t\t} \n" +
		 * "\t} \n" ;
		 */
		log.trace("<<< leave makeSOAPWebService");
		return text;
	}

	/**
	 * get Detail section for a web service method
	 * 
	 * @param beanName
	 * @return
	 * 
	 */
	/*private String getWebServiceDetailText(String beanName) {

		ArrayList<String> primaryKeys = getPrimaryKeys();
		String text =

		"\t\t\t\t\t" + beanName
				+ " dataBean= getModel().getByIdParm(req.getParameter(\""
				+ DataMapper.formatVarName(primaryKeys.get(0).toString())
				+ "\"";
		for (int i = 1; i < primaryKeys.size(); i++) {
			text += "),req.getParameter(\""
					+ DataMapper.formatVarName(primaryKeys.get(i).toString())
					+ "\"";
		}
		text += ") );\n";

		return text;
	}*/

	/**
	 * get Detail section of exec method
	 * 
	 * @param beanName
	 * @return
	 * 
	 */
	/*private String getDetailText(String beanName) {

		ArrayList<String> primaryKeys = getPrimaryKeys();
		String text = "\t\t\t\ttry{\n" + "\t\t\t\t\t" + beanName
				+ " dataBean= getModel().getByIdParm(req.getParameter(\""
				+ DataMapper.formatVarName(primaryKeys.get(0).toString())
				+ "\" ";
		for (int i = 1; i < primaryKeys.size(); i++) {
			text += "),req.getParameter(\""
					+ DataMapper.formatVarName(primaryKeys.get(i).toString())
					+ "\"\n";
		}
		text += ") );\n";

		text += "\t\t\t\treq.setAttribute(WebConst.FORM_BEAN,dataBean ) ; \n"
				+ "\t\t\t\treturn ServerAction.viewAction(\""
				+ beanName
				+ "Detail.jsp\");\n"
				+ "\t\t\t}catch(WebAppException wae){\n"
				+ "\t\t\t\tlog.error(\"exec action\",wae);\n"
				+ "\t\t\t\twebSession.setError(webSession.getMessage(MsgConst.ERROR_DETAIL,\"Error getting Record\"),wae );\n"
				+

				"\t\t\t\treq.setAttribute(WebConst.FORM_BEAN, new " + beanName
				+ "() ) ;//need to set it to somthing \n"
				+ "\t\t\t\treturn ServerAction.viewAction(\"" + beanName
				+ "Detail.jsp\");\n" + "\t\t\t}\n";
		return text;
	}*/

	/**
	 * get update section of exec method
	 * 
	 * @param beanName
	 * @return
	 */
	/*private String getUpdateCmd(String beanName) {
		ArrayList<String> primaryKeys = getPrimaryKeys();
		String text = "\t\t\t\ttry{\n"
				+ "\t\t\t\t\t"
				+ beanName
				+ " databean =getDataBean(req);\n"
				+ "\t\t\t\t\tgetModel().save(databean);\n"
				+ "\t\t\t\t\treturn ServerAction.updateAction(\"Controller?page="
				+ beanName
				+ "&cmd="
				+ WebConst.LIST_CMD
				+ "\");\n"
				+ "\t\t\t\t}catch(WebAppException wae){\n"
				+ "\t\t\t\t\tlog.error(\"exec update\",wae);\n"
				+ "\t\t\t\t\twebSession.setError(webSession.getMessage(MsgConst.ERROR_DETAIL,\"Error getting Record\"),wae );\n"
				+ "\t\t\t\t\t" + beanName
				+ " dataBean= getModel().getByIdParm(req.getParameter(\""
				+ DataMapper.formatClassName(primaryKeys.get(0).toString())
				+ "\" ";
		for (int i = 1; i < primaryKeys.size(); i++) {
			text += ",req.getParameter(\""
					+ DataMapper.formatClassName(primaryKeys.get(i).toString())
					+ "\"\n";
		}
		text += ") );\n";
		text += "\t\t\t\t\treq.setAttribute(WebConst.FORM_BEAN, dataBean);//need to set it to somthing \n"
				+ "\t\t\t\t\twebSession.setError(webSession.getMessage(MsgConst.ERROR_SAVE,\"Invalid Data Unable to Save\"),wae);\n"
				+ "\t\t\t\t\treturn ServerAction.viewAction(\""
				+ beanName
				+ "Detail.jsp\");\n" + "\t\t\t\t}\n";
		return text;
	}*/

	/**
	 * get delete section for exec method
	 * 
	 * @param beanName
	 * @return
	 */
	/*private String getDeleteCmd(String beanName) {
		ArrayList<String> primaryKeys = getPrimaryKeys();
		String text = "\t\t\t\ttry{\n"
				+ "\t\t\t\t\t"
				+ beanName
				+ " databean =getDataBean(req);\n"
				+ "\t\t\t\t\tgetModel().remove(databean);\n"
				+ "\t\t\t\t\treturn ServerAction.updateAction(\"Controller?page="
				+ beanName
				+ "&cmd="
				+ WebConst.LIST_CMD
				+ "\");\n"
				+ "\t\t\t\t}catch(WebAppException wae){\n"
				+ "\t\t\t\t\tlog.error(\"exec update\",wae);\n"
				+ "\t\t\t\t\twebSession.setError(webSession.getMessage(MsgConst.ERROR_DETAIL,\"Error getting Record\"),wae );\n"
				+ "\t\t\t\t\t" + beanName
				+ " dataBean= getModel().getByIdParm(req.getParameter(\""
				+ DataMapper.formatClassName(primaryKeys.get(0).toString())
				+ "\" ";
		for (int i = 1; i < primaryKeys.size(); i++) {
			text += ",req.getParameter(\""
					+ DataMapper.formatClassName(primaryKeys.get(i).toString())
					+ "\"\n";
		}
		text += ") );\n";
		text += "\t\t\t\t\treq.setAttribute(WebConst.FORM_BEAN,dataBean );//need to set it to somthing \n"
				+ "\t\t\t\t\twebSession.setError(webSession.getMessage(MsgConst.ERROR_DELETE,\"Unable to Delete\"),wae);\n"
				+ "\t\t\t\t\treturn ServerAction.viewAction(\""
				+ beanName
				+ "Detail.jsp\");\n" + "\t\t\t\t}\n";
		return text;
	}*/

	/**
	 * get delete section for exec method
	 * 
	 * @param beanName
	 * @return
	 */
	/*private String getCreateCmd(String beanName) {
		String text = "\t\t\t\ttry{\n"
				+ "\t\t\t\t\t"
				+ beanName
				+ " databean =getDataBean(req);\n"
				+ "\t\t\t\t\tgetModel().create(databean);\n"
				+ "\t\t\t\t\treturn ServerAction.updateAction(\"Controller?page="
				+ beanName
				+ "&cmd="
				+ WebConst.LIST_CMD
				+ "\");\n"
				+ "\t\t\t\t}catch(WebAppException wae){\n"
				+ "\t\t\t\t\tlog.error(\"exec create\",wae);\n"
				+ "\t\t\t\t\twebSession.setError(webSession.getMessage(MsgConst.ERROR_CREATE,\"Unable to Create\"),wae);\n"
				+ "\t\t\t\t\treturn ServerAction.viewAction(\"" + beanName
				+ "Create.jsp\");\n" + "\t\t\t\t}\n";
		return text;
	}*/

	/************************************
	 * gen delete
	 *************************************/
	private String makeDelete(String[] cols, int[] types) {
		String beanName = DataMapper.formatClassName(getTableName());
		String text = "\n\t/***************************************************\n"
				+ "\t*Warning Generated method updates the database with a Databean \n"
				+ "\t*@param bean data bound JavaBean\n"
				+ "\t*@see org.javaWebGen.data.DataBean\n"
				+ "\t*\n"
				+ "\t******************************************************/\n"
				+ "\tprotected void delete("
				+ beanName
				+ " bean) throws WebAppException{\n"
				+ "\t\tgetModel().remove(bean);\n" + "\t} //end delete\n";
		return text;
	}

	/**************************************************
	 * build class based on template
	 ********************************************************/
	protected String buldClass() throws Exception {
		String[] colNames = this.getColNames();
		int[] colTypes = getColTypes();

		String vars = makeVars(colNames, colTypes);
		String finder = makeFindByPrimaryKey(colNames, colTypes);
		String insert = makeInsert(colNames, colTypes);
		String update = makeUpdate(colNames, colTypes);
		String delete = makeDelete(colNames, colTypes);
		String list = makeList(colNames, colTypes);
		// String modelName=DataMapper.formatClassName(tableName)+"Model";
		String dataBean = makeDataBean(colNames, colTypes);
		String model = makeModel(colNames, colTypes);
		/*String[] p = new String[10];
		p[0] = className;
		p[1] = vars;
		p[2] = finder;
		p[3] = insert;
		p[4] = update;
		p[5] = delete;
		p[6] = list;
		p[7] = dataBean;
		p[8] = model;
		p[9] = makeAction(colNames, colTypes);*/
		// debug(classTemplate);
		//String classText = StringUtil.replace(classTemplate, p);
		HashMap<String,String> valMap= new HashMap<String,String>();
		valMap.put("javaWebGen.className",className);
		valMap.put("javaWebGen.vars",vars);
		valMap.put("javaWebGen.finder",finder);
		valMap.put("javaWebGen.insert",insert);
		valMap.put("javaWebGen.update",update);
		valMap.put("javaWebGen.delete",delete);
		valMap.put("javaWebGen.list",list);
		valMap.put("javaWebGen.dataBean",dataBean);
		valMap.put("javaWebGen.model",model);
		valMap.put("javaWebGen.action",makeAction(colNames, colTypes) );
		
		StrSubstitutor sub = new StrSubstitutor(valMap);
		
		return sub.replace(classTemplate);
	}

	/**
	 * @param colNames2
	 * @param colTypes2
	 * @return
	 */
	private String makeList(String[] colNames2, int[] colTypes2) {
		String beanName = DataMapper.formatClassName(getTableName());
		String text = "\n\t/***************************************************\n"
				+ "\t*Warning Generated method list all rows in table \n"
				+ "\t*@return list of databeans\n"
				+ "\t******************************************************/\n"
				+ "\tprotected List <"
				+ beanName
				+ "> list() throws WebAppException{\n"
				+ "\t\treturn getModel().list();\n" + "\t} //end list\n";
		return text;
	}

	/**
	 * @param colNames2
	 * @param colTypes2
	 * @return
	 */
	private String makeDataBean(String[] colNames, int[] colTypes) {
		String beanName = DataMapper.formatClassName(getTableName());
		String text = "\t/************************************\n"
				+ "\t*fills in a databean based on data in a request\n"
				+ "\t************************************/\n"
				+ "\tprotected static "
				+ beanName
				+ " getDataBean(HttpServletRequest req) throws WebAppException{\n"
				+

				"\t\t\t" + beanName + " dataBean=new " + beanName + "();\n"
				+ "\t\ttry{\n";
		for (int i = 0; i < colNames.length; i++) {
			text += "\t\t\tdataBean.set"
					+ DataMapper.formatMethodName(colNames[i]);
			switch (colTypes[i]) {
			case Types.DATE:
				text += "(StringUtil.convertToDate(req.getParameter(\""
						+ DataMapper.formatMethodName(colNames[i])
						+ "\") ) );\n";
				break;
			case Types.TIMESTAMP:
				text += "(StringUtil.convertToTime(req.getParameter(\""
						+ DataMapper.formatMethodName(colNames[i])
						+ "\") ) );\n";
				break;
			default:
				text += "(HtmlUtil.stripTags(req.getParameter(\""
						+ DataMapper.formatMethodName(colNames[i])
						+ "\") ) );\n";
			}

		}
		text += "\t\t}catch(Exception e){\n"
				+ "\t\t\tthrow new WebAppException(WebAppException.APP_ERROR,e);\n"
				+ "}";
		text += "\t\treturn dataBean;\n\t}//end getDataBean\n\n";
		text += "\t/************************************\n"
				+ "\t*Strips tags from strings\n"
				+ "\t************************************/\n"
				+ "\tpublic static "
				+ beanName
				+ " cleanDataBean("+beanName + " dataBean ) {\n";
				 
		for (int i = 0; i < colNames.length; i++) {
					
			switch (colTypes[i]) {
				case Types.CHAR:
				case Types.VARCHAR:
				case Types.LONGNVARCHAR: 
				case Types.CLOB: 
				text += "\t\tdataBean.set"+DataMapper.formatMethodName(colNames[i])+
					"(HtmlUtil.stripTags(dataBean.get"+DataMapper.formatMethodName(colNames[i])+"() ) );\n";
				break;
			}

		}

		text += "\t\treturn dataBean;\n\t}//end cleanDataBean\n\n";		
		return text;
	}

	/**
	 * @param colNames2
	 * @param colTypes2
	 * @return
	 */
	private String makeModel(String[] colNames, int[] colTypes2) {
		String beanName = DataMapper.formatClassName(getTableName());
		String text = "\t/************************************\n"
				+ "\t*Generated method\n" + "\t*get the correct model class\n"
				+ "\t*@return model class\n"
				+ "\t************************************/\n"
				+ "\tprotected " + beanName+"Model getModel() throws WebAppException{\n"
				+ "\t\treturn JavaWebGenContext.getModel().get"+beanName+"Model();\n" 
				+ "\t}\n" ;
				

		return text;
	}

	/**************************************************
	 * build class based on template
	 ********************************************************/
	protected String buldSubClass() throws Exception {

		/*String[] p = new String[6];
		p[0] = subClassName;
		p[1] = className;
		p[3] = subClassName;
		p[4] = this.makeJSONWebService(getColNames(), getColTypes());
		p[5] = this.makeSOAPWebService(getColNames(), getColTypes());*/

		//String classText = StringUtil.replace(subClassTemplate, p);
		HashMap<String,String> valMap= new HashMap<String,String>();
		valMap.put("javaWebGen.subClassName",subClassName);
		valMap.put("javaWebGen.className",className);
		valMap.put("javaWebGen.soap",this.makeSOAPWebService(getColNames(), getColTypes()) );
		valMap.put("javaWebGen.json",this.makeJSONWebService(getColNames(), getColTypes()) );
		
		
		StrSubstitutor sub = new StrSubstitutor(valMap);

		return sub.replace(subClassTemplate);
	}

	/**
    *
    */
	protected void execute() throws UtilException {
		try {
			className = DataMapper.formatClassName(getTableName())
					+ "ActionImpl";
			subClassName = DataMapper.formatClassName(getTableName())
					+ "Action";
			writeJavaClass(buldClass());
			writeSubClass(buldSubClass());
		} catch (Exception e) {
			throw new UtilException(UtilException.CODE_GENERATOR_EXEC, e);
		}

	}

	/**
	 * process stuff after main execute loop has finished processing tables
	 */
	@Override
	protected void postExecute() throws UtilException {

	}

	/**
	 * Write out generated class
	 */
	private void writeJavaClass(String text) throws IOException {
		String name = className;
		String fileName = getFilePath() + File.separator + name + ".java";
		if (getPrimaryKeys().size() > 0) {

			FileWriter file = new FileWriter(fileName);
			PrintWriter out = new PrintWriter(file);
			out.print(text);
			out.flush();
			out.close();
			log.info("---wrote out sub class file=" + fileName + "---");
		}
	}

	/**
	 * Write out generated class
	 */
	private void writeSubClass(String text) throws IOException {
		String name = subClassName;
		String fileName = getFilePath() + File.separator + name + ".java";
		if (getPrimaryKeys().size() > 0) {
			File file = new File(fileName);
			if (!file.exists()) {
				// String fileName=filePath+File.separator+name+".java";
				FileWriter fw = new FileWriter(file);
				PrintWriter out = new PrintWriter(fw);
				out.print(text);
				out.flush();
				out.close();
				log.debug("---wrote out class file=" + fileName + "---");
			}
		}
	}

	/**
	 * main
	 */
	public static void main(String[] args) {
		try {
			GenerateSpringController app = new GenerateSpringController();
			app.setCmdParms(args);
			app.init();
			app.processXmlFile(app.getFileName());
		} catch (Exception e) {
			log.error("main",e);
			System.exit(911);
		}
	}

	/**
  * 
  */
	public void useage() {
		System.out.println("To Process all tables in a text file");
		System.out.println("USAGE GenerateController -f <filename> <path>");
		System.exit(1);
	}

}