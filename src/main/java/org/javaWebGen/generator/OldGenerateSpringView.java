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

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;

import org.apache.commons.text.StrSubstitutor;
import org.javaWebGen.config.MsgConst;
import org.javaWebGen.exception.UtilException;
import org.javaWebGen.form.BootstrapStyle;
import org.javaWebGen.form.StyleAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generated view(JSP)s in this case from the database schema.
 * This class will <b>NOT</b> be overwritten.
 * @author Kevin scott
 * @version $Revision: 2.167 $
 *
 */
public class OldGenerateSpringView extends CodeGenerator {
	 private final static Logger log= LoggerFactory.getLogger(OldGenerateSpringView.class);
     public static final String VERSION="v1_22";
     //public static final String DTD="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n";
     public static final String DTD="<!DOCTYPE html>\r\n" ;
     
     private String detailTemplate=null;
     private String listTemplate=null;
     private String styleTemplate=null;
     private String indexTemplate=null;
	 private StyleAware style=new BootstrapStyle();
     private String jqueryjs=
    		   "<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->\r\n" + 
    		   "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js\"></script>  \r\n" + 
    		   "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>\r\n" + 
    		   "<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.min.js'></script>\r\n" ; 
     private String jquerycss=""
     		+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\r\n" + 
     		"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">\r\n" + 
     		"<link rel=\"stylesheet\" href=\"/web/css/bootstrap-datepicker.min.css\" >\r\n" + 
     		"<link rel=\"stylesheet\" href=\"/web/css/bootstrap-timepicker.min.css\" >";

    /** 
    *
    */
    public void init(){
    	styleTemplate=jquerycss;

    	
    	indexTemplate=DTD +
    		"<html lang=\"en\">\n"+	
			"<%@page import=\""+
            "java.util.*,\n"+
            "java.text.*,\n"+
            "java.math.*,\n"+
            "org.javaWebGen.data.DataBean,\n"+
            "org.javaWebGen.data.bean.*,\n" +
            "org.javaWebGen.util.StringUtil,\n" +
            "org.javaWebGen.config.*,\n" +
            "org.javaWebGen.web.*,\n" +
            "org.javaWebGen.*\"%>\n"+
             "<%/****************************************************************************\n"+
             "* WARNING this JSP is generated by GenerateSpringView "+VERSION+" based on Database schema\n"+
             "* This is only a place holder.  It will NOT be overwritten.  Customize as needed \n"+
             "*******************************************************************************/%>\n"+
    		 "<jsp:useBean id=\"ws\" scope=\"request\" class=\"org.javaWebGen.WebSession\" />\n"+
    		 "<head>"+
			 "<title>Admin index do not deploy to production</title>" +
			 "</head>" +
			 "<body >\n" +
			 "<h1>Admin action index do not deploy to prod</h1>"+
			 "<p>This File <b>Will not</b> get Regenerated. So you may modify it to suit your needs.<p>\n"+
		  	 "<p>This file just a place holder. It was gererated by "+VERSION+"</p>\n"+
			 "<table>" +
			 "<tr><td><h4>Entity\n" +
			 "${JavaWebGen.list}" +
			 "</td>" +
			 "<td><div id='content'></div></td></tr>" +
			 "</table>" +
			
			 "</body></html>";
	
        detailTemplate=DTD+
        	"<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>\n"+
        	"<%@ taglib uri=\"http://java.sun.com/jsp/jstl/fmt\" prefix=\"fmt\" %>\n"+
            "<fmt:setLocale value = \"en\"/>"+
            "<fmt:setBundle basename = \"messages\" var = \"msg\"/>\n"+
            "<%@page import=\""+
            "java.util.*,\n"+
            "java.text.*,\n"+
            "java.math.*,\n"+
            "org.javaWebGen.data.DataBean,\n"+
            "org.javaWebGen.data.bean.*,\n" +
            "org.javaWebGen.util.StringUtil,\n" +
            "org.javaWebGen.config.*,\n" +
            "org.javaWebGen.web.*,\n" +
            "org.javaWebGen.*\"%>\n"+
             "<%/****************************************************************************\n"+
             "* WARNING this JSP is generated by GenerateSpringView "+VERSION+" based on Database schema\n"+
             "* This is only a place holder.  It will NOT be overwritten.  Customize as needed \n"+
             "*******************************************************************************/\n"+
    		 "//private vars%>\n${javaWebGen.vars}\n" +
    		 "\n"+
    		 "<jsp:useBean id=\"ws\" scope=\"request\" class=\"org.javaWebGen.WebSession\" />"+
             "<html lang=\"en\"><head>\n" +
    	     "<script>\n" +
    	     "${javaWebGen.script}\n" +
             "</script>\n" +
             "<title> ${javaWebGen.className} Detail</title>" +
             styleTemplate+
      		 "</head>\n"+
             "<body>\n" +
             "<div class='container'><!--end pageContainer-->\n"+
             "\t<div id='header'>\n"+
             "\t<!--begin header-->\n   " +
             "\t\t${javaWebGen.header}" +
             "\t\t<!--end header-->\n"+
             "\t</div>"+
             "\t<div id='mainMenu'>\n"+ 
    		 "\t\t<!--begin main menu-->\n${javaWebGen.menu}<!--end main menu-->\n"+
    		 "\t</div>\n"+
    		 "\t<div id='navMenu'>\n"+
    		 "\t\t<!--begin NavMenu-->\n${javaWebGen.nav}<!--end navMenu-->\n"+
    		 "\t</div>\n"+
    		 "\t<div id='rightnav'>"+
    		 "\t</div>\n"+
    		 
    		 "\t<div ><!--begin mainContent-->\n"+
    		 "\t\t${javaWebGen.mainContent}\n"+
    		 "\t</div><!--end main mainContent-->\n"+
    		 "\t\t<div id='errorMsg'><%=ws.getError()%></div>\n"+
    		 "\t</div><!--end section-->\n"+
    		 "\t<div id='footer'>\n"+
    		 "\t\t<!--begin footer-->\n${javaWebGen.footer}\n<!--end footer-->\n"+
    		 "\t</div>\n"+
    		 "</div> <!--end pageContainer-->\n"+
    		 jqueryjs+
             "</body>\n" +
             "</html>\n" ;
             

            listTemplate=DTD+   
            	"<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>"+
                "<%@page import=\""+
                "java.math.*,\n"+
                "java.util.*,\n"+
                "org.javaWebGen.exception.DBException,\n"+
                "org.javaWebGen.data.bean.*," +
                "org.javaWebGen.data.DataBean,\n"+
                "org.javaWebGen.config.*,\n" +
                "org.javaWebGen.*\"%>\n"+
                 "<%/******************************************************************************\n"+
                 "* WARNING this JSP is generated by "+VERSION+" based on Database schema     \n"+
                 "* This is only a place holder it will not be overwritten.                  \n"+
                 "*******************************************************************************/\n"+
        		 "//private vars%>\n${javaWebGen.vars}\n" +
        		 "\n"+
        		 "<jsp:useBean id=\"ws\" scope=\"request\" class=\"org.javaWebGen.WebSession\" />\n"+
        		 "<jsp:useBean id=\"dataBeanList\" scope=\"request\" class=\"java.util.ArrayList\" />\n"+
                 "<html><head>\n" +               
                 styleTemplate+
                 "<title>${javaWebGen.className} List</title></head>\n"+
                 "<body>\n" +
                 "<div id='pageContainer'>\n"+
                 "<div id='section'>        \n"+
                 "\t<div id='header'>\n"+
                 "<!--begin header-->\n   " +
                 "     ${javaWebGen.header}" +
                 "<!--end header-->\n"+
                 "\t</div>"+
        		 "\t<div id='section'>\n"+
                 "\t<div id='mainMenu'>\n"+
        		 "<!--begin main menu-->\n${javaWebGen.menu}\n<!--End main menu-->\n"+
        		 "\t</div>\n"+
        		 "\t<div id='navMenu'>\n"+
        		 "<!--begin NavMenu-->\n${javaWebGen.nav}\n<!--end navMenu-->\n"+
        		 "\t</div>\n"+
        		 "\t<div id='rightnav'></div>\n"+

        		 "\t<div id='mainContent'><!--begin mainContent-->\n"+
        		 "        ${javaWebGen.mainContent}\n"+
        		 "\t</div><!--end main mainContent-->\n"+
        		 "\t</div><!--end section-->\n"+
        		 "<div id='footer'>\n"+
        		 "<!--begin footer-->\n${javaWebGen.footer}\n<!--end footer-->\n"+
        		 "</div>"+
        		 "</div><!--end pageContainer-->\n"+
        		 jqueryjs+
                 "</body>\n" +
                 "</html>\n" +
                 "<!--end Generated View-->\n";
        detailTemplate=getTemplate(detailTemplate);
        listTemplate=getTemplate(listTemplate);
        
    }

    /************************
    * list of list of column types
    ****************************/
    protected String makeTypes(String[] cols, int[] types){
        String text =
        "\t/**********************************************\n"+
        "\t*get an array of collumn types that match DB table\n"+
        "\t*@return array of Types\n"+
        "\t************************************************/\n"+
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
	 * gen all private vars
	 */
	protected String makeVars(String[] cols, int[] types) throws Exception {
		String beanName = DataMapper.formatClassName(getTableName());
		// String daoName=DataMapper.formatClassName(tableName)+"DAO";
		String text = "<jsp:useBean id=\"formBean\" scope=\"request\" class=\"org.javaWebGen.data.bean." + beanName
				+ "\" />\n";
		return text;
	}

	/**
	 * gen all private vars
	 */
	protected String makeCreateVars(String[] cols, int[] types) throws Exception {
		String beanName = DataMapper.formatClassName(getTableName());
		// String daoName=DataMapper.formatClassName(tableName)+"DAO";
		String text = "<jsp:useBean id=\"formBean\" scope=\"request\" class=\"org.javaWebGen.data.bean." + beanName
				+ "\" />\n";
		return text;
	}

	private String cOut(String name) {
		return cOut("formBean", name);
	}

	private String cOut(String beanPrefix, String name) {
		// return "<c:out value=\"${"+beanPrefix+"."+name+"}\"/>";
		return "<c:out value=\"${" + beanPrefix + "." + DataMapper.formatVarName(name) + "}\"/>";
	}
	

  
/**
 * make a html list
 * @param cols
 * @param types
 * @return html list table
 * @throws Exception
 */
     protected String makeList(String[] cols, int[] types)throws Exception{
     	 String beanName=DataMapper.formatClassName(getTableName() );
     	 
     	 ArrayList <String> primaryKeys= getPrimaryKeys();
     	 //String text="<b>No Records found</b>";
       	 	String text="<h1>"+beanName+"</h1>"+
       	 	"<table class=\"table table-striped\"><%\n"+
     	 	
     	 	"if (dataBeanList.size()==0){%>\n"+
     	 	"\t<TR id='listingHeading'><TD>\n"+
     	 	"\t<%=ws.getMessage(MsgConst.ERROR_NO_DATA,\"No data found\")%></TD></TR>\n"+
     	 	"<%}else{%>\n";
     	 	text+="<tr>\n";
            for( int i=0;i<primaryKeys.size();i++){  
    		   text+=" <th>"+DataMapper.formatMethodName(primaryKeys.get(i).toString() )+"</th>";
            }
            if(cols.length >= primaryKeys.size()){
            	 text+=" <th>"+DataMapper.formatMethodName(cols[primaryKeys.size()])+"</th>";
            }
            text+="<th>Action</th>\n";
        	text+="</tr>\n";
     	 	text+="<c:forEach var=\"dataBean\" items=\"${dataBeanList}\">\n";
			 
 
     	 text+="<tr>\n";
         for( int i=0;i<primaryKeys.size();i++){  
		   text+=" <td>"+cOut("dataBean",primaryKeys.get(i) )+"</td>";
         }
         if(cols.length >= primaryKeys.size()+1){
         	text+=" <td>"+cOut("dataBean",cols[primaryKeys.size()] )+"</td>\n";
         }
         text+= "<td>";
         if(primaryKeys.size()>=1){
	        text+="<A HREF='/admin/"+beanName+"/detail.htm?xp=1";
	         
	         for( int i=0;i<primaryKeys.size();i++){  //pass key ids
	        	 String keyName=DataMapper.formatVarName(primaryKeys.get(i) );
	  		   text+="&"+keyName+"="+cOut("dataBean",primaryKeys.get(i) ) +"";
	         }
	         
	         text+="'>Details</A>" ;
         }
         text+="</td>\n	 ";
         	
         text+="</tr>\n </c:forEach>\n<%}%>\n</table>\n";
        
         if(primaryKeys.size()>=1){
			text+="<form action='/admin/"+beanName+"/create.htm' name='dataForm' method='POST'>"+
			"<button id='"+beanName+".submit' type='submit'  class='btn btn-primary'  >Add New</button>\n" +
			"</form>\n";
         }
         return text;

     }

     /**
      * gerenate have script functions for update and delete actions. prompts uses to confirm deletes
      * @param cols
      * @param types
      * @return
      */
     private String makeJavascript(String[] cols, int[] types){
     	String beanName=DataMapper.formatClassName(getTableName() );
     		String text=
     		
         "function deleteClick(dataForm){\n" +
         "\talert('Are you sure you want to delete this record?');\n" +
         "\tdataForm.action='/admin/"+beanName+"/delete.htm';\n"+
         "\tdataForm.submit();\n" +
		 "} //end delete function\n" +
		 
         "function updateClick(dataForm){\n" +
         //"alert('Are you sure you want to delete this record?');\n" +
         "\tdataForm.action='/admin/"+beanName+"/save.htm';\n"+
         "\tdataForm.submit();\n" +
		 "} //end update function\n";

     	return text;
     	
     	
     }


    /**************************************************
    *build class based on template
    ********************************************************/
    protected String buldDetail() throws Exception{
    	String[] colNames = getColNames();
    	int[] colTypes = getColTypes();
        if( colNames!=null &&  colTypes !=null){
            
            String header =makeHeader(colNames, colTypes);
            String footer =makeFooter(colNames,colTypes);
            String form =makeForm(colNames, colTypes);
            String nav =makeNav(colNames, colTypes);
            String menu =makeMenu(colNames, colTypes);
            String vars = makeVars( colNames, colTypes);
            String script =makeJavascript( colNames, colTypes);
            
            /*String[] p = new String[8];
            p[0]= vars ;
            p[1]=DataMapper.formatClassName(getTableName() )+" Detail";
            p[2]=header;
            p[3]=menu;
            p[4]=nav;
            p[5]=form;
            p[6]=footer;
            p[7]=script;*/   
            HashMap<String,String> valueMap = new HashMap<String,String>();
            valueMap.put("javaWebGen.vars", vars);
            valueMap.put("javaWebGen.className", DataMapper.formatClassName(getTableName() ) );
            valueMap.put("javaWebGen.header", header);
            valueMap.put("javaWebGen.menu", menu);
            valueMap.put("javaWebGen.nav", nav);
           // valueMap.put("javaWebGen.form", form);
            valueMap.put("javaWebGen.mainContent", form);
            
            valueMap.put("javaWebGen.footer", footer);
            valueMap.put("javaWebGen.script", script);
            
            StrSubstitutor sub = new StrSubstitutor(valueMap);
           // String classText = StringUtil.replace(detailTemplate,p);
            return sub.replace(detailTemplate);
        }else{
            return "";
        }
        
    }
    /************************************************8**
     *build class based on template
     ********************************************************/
     protected String buldList() throws Exception{
     	String[] colNames = getColNames();
    	int[] colTypes = getColTypes();

         if( colNames!=null &&  colTypes !=null){
             
             String header =makeHeader(colNames, colTypes);
             String footer =makeFooter(colNames, colTypes);
             String form =makeList(colNames, colTypes);
             String nav =makeNav(colNames, colTypes);
             String menu =makeMenu(colNames, colTypes);
             String vars = "";
             
             String[] p = new String[7];
     
             p[0]= vars ;
             p[1]=DataMapper.formatClassName(getTableName() )+" List";
             p[2]=header;
             p[3]=menu;
             p[4]=nav;
             p[5]=form;
             p[6]=footer;
             HashMap<String,String> valueMap = new HashMap<String,String>();
             valueMap.put("javaWebGen.vars", vars);
             valueMap.put("javaWebGen.className", DataMapper.formatClassName(getTableName() ) );
             valueMap.put("javaWebGen.header", header);
             valueMap.put("javaWebGen.menu", menu);
             valueMap.put("javaWebGen.nav", nav);
             //valueMap.put("javaWebGen.form", form);
             valueMap.put("javaWebGen.mainContent", form);
             valueMap.put("javaWebGen.footer", footer);
             
             
             StrSubstitutor sub = new StrSubstitutor(valueMap);
             //String classText =getClassText(listTemplate,p);
             return sub.replace(listTemplate);
         }else{
             return "";
         }
         
     }
    /**
	 * @param strings
	 * @param is
	 * @return
	 */
	private String makeMenu(String[] strings, int[] is) {
		String text="<jsp:include page='/WEB-INF/jsp/admin/theme/default/menu.jsp' flush='false'/>\n";

		return text;
	}

	/**
	 * @param strings
	 * @param is
	 * @return
	 */
	private String makeNav(String[] strings, int[] is) {
		String text="<jsp:include page='/WEB-INF/jsp/admin/theme/default/nav.jsp' flush='false'/>\n";
		return text;
	}

	/**
	 * @param strings
	 * @param is
	 * @return
	 */
	private String makeHeader(String[] strings, int[] is) {
		String text="<jsp:include page='/WEB-INF/jsp/admin/theme/default/header.jsp\' flush='false'/>\n";
		
		return text;
	}
    /**
	 * @param strings
	 * @param is
	 * @return
	 */
	private String makeFooter(String[] strings, int[] is) {
		String text="<jsp:include page='/WEB-INF/jsp/admin/theme/default/footer.jsp' flush='false'/>\n";
		return text;
	}
	/**
    *
    */
    protected void execute() throws UtilException{
        writeJavaClass( );

    }

    /**
    *Write out generated class
     * @throws Exception
    */
    private void writeJavaClass() throws UtilException{
    	try{
	    	String detail=buldDetail();
	    	String list = buldList();
	       	String create=buldCreate();
	       	String filePath=this.getFilePath();
	    	if(this.getPrimaryKeys().size()>0){
		        
		        String name = DataMapper.formatClassName(getTableName() );
	
		        File file=new File(filePath+File.separator+name+"Detail.jsp");
		        //if( !file.exists() ){
			       
			        FileWriter dfo = new FileWriter(file);
			        PrintWriter dout = new PrintWriter(dfo);
			        dout.print(detail);
			        dout.flush();
			        dout.close();
			        log.info("---Wrote File "+filePath+File.separator+name+"Detail.jsp");

		        //}
		        
		        name = DataMapper.formatClassName(getTableName() );
		        file=new File(filePath+File.separator+name+"List.jsp");
		       // if(!file.exists() ){

		        	FileWriter lo = new FileWriter(file);
		        	PrintWriter lout = new PrintWriter(lo);
			       // = new PrintWriter(fo);
			        lout.print(list);
			        lout.flush();
			        lout.close();
		        	log.info("---Wrote File "+filePath+File.separator+name+"List.jsp");
		        //}
		       
		        name = DataMapper.formatClassName(getTableName() );
		        file=new File(filePath+File.separator+name+"Create.jsp");
		       // if(!file.exists() ){

			        FileWriter co = new FileWriter(file);
			        PrintWriter cout = new PrintWriter(co);
			        cout.print(create);
			        cout.flush();
			        cout.close();
			        log.info("---Wrote File "+filePath+File.separator+name+"Create.jsp");
		       // }

			}
    	}catch(Exception e){
    		throw new UtilException(UtilException.CODE_GENERATOR_EXEC,e);
    	}
    	
	        
    }

    private String buldCreate() throws Exception{
        if( getColNames()!=null &&  getColTypes() !=null){
         	String[] colNames = getColNames();
        	int[] colTypes = getColTypes();
           
            String header =makeHeader(colNames, colTypes);
            String footer =makeFooter(colNames, colTypes);
            String form =makeCreateForm(colNames, colTypes);
            String nav =makeNav(colNames, colTypes);
            String menu =makeMenu(colNames, colTypes);
            String vars = makeCreateVars(colNames, colTypes);
            String script =makeCreateJavascript( colNames, colTypes);
            
            /*String[] p = new String[8];
            p[0]= vars ;
            p[1]=DataMapper.formatClassName(getTableName() )+" Detail";
            p[2]=header;
            p[3]=menu;
            p[4]=nav;
            p[5]=form;
            p[6]=footer;
            p[7]=script;*/   
            
            HashMap<String,String> valueMap = new HashMap<String,String>();
            valueMap.put("javaWebGen.vars", vars);
            valueMap.put("javaWebGen.className", DataMapper.formatClassName(getTableName() ) );
            valueMap.put("javaWebGen.header", header);
            valueMap.put("javaWebGen.menu", menu);
            valueMap.put("javaWebGen.nav", nav);
            valueMap.put("javaWebGen.mainContent", form);
            valueMap.put("javaWebGen.footer", footer);
            valueMap.put("javaWebGen.script", script);
            
            StrSubstitutor sub = new StrSubstitutor(valueMap);
           // String classText =getClassText(detailTemplate,p);
            return sub.replace(detailTemplate);
        }else{
            return "";
        }
	}

	private String makeCreateJavascript(String[] cols, int[] types) {
		String beanName=DataMapper.formatClassName(getTableName() );
        String text=
	     "function updateClick(dataForm){\n" +
	     //"alert('Are you sure you want to delete this record?');\n" +
	     "\tdataForm.action='/admin/"+beanName+"/save.htm';\n"+
        "\tdataForm.submit();\n" +
		 "} //end update function\n";

    	return text;
    	
	}
	/**
	 * generate html form
	 * @param cols
	 * @param types
	 * @return form in html
	 * @throws Exception
	 */
    protected String makeForm(String[] cols, int[] types)throws Exception{
    	String beanName=DataMapper.formatClassName(getTableName());
    	ArrayList <String> primaryKeys= getPrimaryKeys();
   
        String text="<h1>"+beanName+" Detail</h1>"+
    	"\n<form name='dataForm'  class=\"form-horizontal\" action ='/admin/"+beanName+"/save.htm' method='post'>\n";
     
//        for( int i=0;i<cols.length;i++){//is primary key?
//        	fieldName=DataMapper.formatVarName(cols[i]);
//        	text+="<div class=\"row form-group\">\n";
//        	if(this.getIsKeyArray()[i] ){     		
//        		text+="\t<div class='col-xs-2'><label for='"+DataMapper.formatMethodName(cols[i])+"Id'>"+DataMapper.formatMethodName(cols[i])+":</label></div>\n\t<div class='col-xs-10'>\n"+
//        				cOut(cols[i])+"<input TYPE='hidden' id="+DataMapper.formatMethodName(cols[i])+"Id name='"+
//            	DataMapper.formatMethodName(cols[i])+"'"+
//				   " value='" +cOut(cols[i])+"' />"+
//			//	   "<%=formBean.get"+DataMapper.formatMethodName(cols[i])+"()%>'/>\n"+			  		
//			// 		"<input type='hidden' name='"+
//	        //   	DataMapper.formatMethodName(cols[i])+"PrimaryKey'"+
//			//		   " value='<%=formBean.get"+DataMapper.formatMethodName(cols[i])+"()%>'/>\n"+				   
//		           "</div>\n";
//        	}else{
//	            switch (types[i]){
//	                case Types.CHAR:
//	                case Types.VARCHAR:
//	                case Types.LONGVARCHAR:
//	                  	text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+":"+"</label></div>\n"+
//	                        	"\t<div class='col-xs-10' ><input  class='form-control' type='text' id='"+beanName+"."+fieldName+"' name='"+    	                		
//	    	            		   DataMapper.formatMethodName(cols[i])+"' "+
//	    	            		   "\t value='"+cOut(cols[i])+"'/>"+
//						   "\t</div>\n"+
//						   "\n";
//	                    break;
//	                case Types.BIT:
//	                  	text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+"':"+"</label></div>\n"+
//	                        	"\t<div class='col-xs-10' ><input  class='form-control' id='"+beanName+"."+fieldName+"' type='date' name='"+
//	    	            		   DataMapper.formatMethodName(cols[i])+"' "+
//						   "value='<%if (formBean.get"+DataMapper.formatMethodName(cols[i])+"() )==1){%>\n"+
//						   "\t cheched ='true';\n{"+
//						   "<%}else{%>\n"+
//						   "\t cheched ='false';\n{\n"+
//						   "<%}\n%>"+
//				           "></div>\n";
//	                    break;
//	                case Types.FLOAT:
//	                case Types.DOUBLE:    
//	                case Types.TINYINT:
//	                case Types.SMALLINT:
//	                case Types.INTEGER:
//	                case Types.BIGINT:
//	                  	text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+":"+"</label></div>\n"+
//	                        	"\t<div class='col-xs-10' ><input  class='form-control' id='"+beanName+"."+fieldName+"' type='number' name='"+
//	    	            		   DataMapper.formatMethodName(cols[i])+"' "+
//						   " value='"+cOut(cols[i])+"'/>"+
//				           "</div>\n";
//	                    break;	    
//	                case Types.DECIMAL:
//	                  	text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+":"+"</label></div>\n"+
//	                        	"\t<div class='col-xs-10' ><input  class='form-control' id='"+beanName+"."+fieldName+"' type='number' name='"+
//	    	            		   DataMapper.formatMethodName(cols[i])+"' "+
//						   " value='"+cOut(cols[i])+"'/>"+
//						   //   "<%=formBean.get"+DataMapper.formatMethodName(cols[i])+"()%>"+
//				           //"<%}%>" +
//				           "</div>\n";
//	                    break;
//	                case Types.DATE:
//	                  	text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+":"+"</label></div>\n"+
//	                        	"\t<div class='col-xs-10' ><input  class='form-control' id='"+beanName+"."+fieldName+"' type='date' name='"+
//	    	            		   DataMapper.formatMethodName(cols[i])+"' "+
//						   " value='<%if(formBean.get"+DataMapper.formatMethodName(cols[i])+"()!=null){%>"+
//						      "<%=StringUtil.formatDate(formBean.get"+DataMapper.formatMethodName(cols[i])+"() )%>"+
//				           "<%}%>'></div>\n";
//	                	break;
//	                case Types.TIMESTAMP:
//	                  	text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+"':"+"</label></div>\n"+
//	                        	"\t<div class='col-xs-10' ><input  class='form-control' id='"+beanName+"."+fieldName+"' type='time' name='"+
//	    	            		   DataMapper.formatMethodName(cols[i])+"' "+
//						   " value='<%if(formBean.get"+DataMapper.formatMethodName(cols[i])+"()!=null){%>"+
//						      "<%=StringUtil.formatTime(formBean.get"+DataMapper.formatMethodName(cols[i])+"() )%>"+
//				           "<%}%>'></div>\n";
//	                    break;
//	                case Types.LONGVARBINARY:
//	                case Types.BINARY:    
//	                case Types.BLOB:  
//	                case Types.JAVA_OBJECT :  
//	                case Types.CLOB : 
//	                case Types.OTHER :
//	                	text+="<%/*BLOB not supported*/%>\n";
//	            }//end switch
//            
//        	}//end if primary key
//        	text+="</div>\n";
//        }
        for(String col:cols){
        	String var=DataMapper.formatVarName(col);
        	boolean isPrimary= false;
        	for(String pkey:primaryKeys){
        		if(pkey.endsWith(col)){
        			isPrimary=true;
        		}
        		if(isPrimary){
        			text+=
        			 "<div class='"+this.style.getRow()+" "+this.style.getFormGroup()+"'>\n"
        			+"<div class='"+this.style.getColSm2()+"'>"
        					+DataMapper.formatClassName(col)+"</div><div class='"+style.getColSm10()+"'>${form."+var+".value}</div>\n"
        			+"</div>\n"
        			+"<input type='hidden' name='"+var+"' value='${form."+var+".value}'>\n";
        		}else{
        			text+="${form."+var+".divTag}\n";
        		}
        	}
        }
    	if(primaryKeys.size()==1) { 
        text+=
        	"<button id='button.update' name='updateBut'  class='btn btn-primary'  onClick='updateClick(dataForm)' ><fmt:message key = \""+MsgConst.DIALOG_SAVE+"\" bundle = \"${msg}\"/></button>\n"+
        	"<button id='button.delete' name='deleteBut' class='btn btn-danger' onClick='deleteClick(dataForm)'><fmt:message key = \""+MsgConst.DIALOG_DELETE+"\" bundle = \"${msg}\"/></button>\n"+
	        "</form>\n";
    	}else {
    		text+="<!--update and delete not supported  --></form>\n";
    	}
       
        text+=    
         "\n</form>\n";
       
        return text;

    }
	private String makeCreateForm(String[] cols, int[] types) {
    	String beanName=DataMapper.formatClassName(getTableName() );
        String text="<h1>Create New "+beanName+"</h1>"+
     
        "<form name='dataForm' action ='/admin/"+beanName+"/save.htm' method='post' class='form-horizontal' >\n";
       
        String fieldName=null;
        for( int i=0;i<cols.length;i++){
        	fieldName=DataMapper.formatVarName(cols[i]);
        	text+="<div class='row form-group'>\n";
        	if(this.getIsKeyArray()[i]){
        		text+="\t<div class='col-xs-2'><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+":</label></div>   \n"+
        			"\t<div class='col-xs-10'>"+cOut(cols[i])+"<input  TYPE='hidden' name='"+
            	DataMapper.formatMethodName(cols[i])+"'"+
				   " value='" +cOut(cols[i])+"' /></div>\n";
        	}else{
	            switch (types[i]){
	                case Types.CHAR:
	                case Types.VARCHAR:
	                case Types.LONGVARCHAR:
	                    text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+":</label></div>   \n"+
	                       
	                       "\t<div class='col-xs-10' ><input  class='form-control' type='text' id='"+beanName+"."+fieldName+"'"+
						   "\tname='"+DataMapper.formatMethodName(cols[i])+"' \n"+
	                       "\tvalue='"+cOut(cols[i])+"'/>";
		//				   "<%\n"+
		//				   "\tif (formBean.get"+DataMapper.formatMethodName(cols[i])+"()!=null){%>\n"+
		//				   "\t\t value='<%=formBean.get"+DataMapper.formatMethodName(cols[i])+"()%>'>\n"+
		//				   "\t<%}%>\n";
					 
	                    break;
	                case Types.BIT:
	                    text+="<--Warn experimental code-->"+
	                    	"\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+":"+"</label></div>\n"+
	                    	"\t<div class='col-xs-10' ><input  class='form-control' type='text'id='"+beanName+"."+fieldName+"' name='"+
						   DataMapper.formatMethodName(cols[i])+"'/>"+
						   "value='<%if (formBean.get"+DataMapper.formatMethodName(cols[i])+"() )==1){%>\n"+
						   "\t checked ='true';\n{"+
						   "<%}else{%>\n"+
						   "\t checked ='false';\n{\n"+
						   "<%}\n%>"+
				           "/></div>\n";
	                    break;
	                case Types.TINYINT:
	                case Types.SMALLINT:
	                case Types.INTEGER:
	                case Types.BIGINT:
	                	text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+":"+"</label></div>\n"+
                    	"\t<div class='col-xs-10'><input  class='form-control' id='"+beanName+"."+fieldName+"' type='number' name='"+
	            		   DataMapper.formatMethodName(cols[i])+"' "+
						   " value='"+cOut(cols[i])+"' />\n"+
				           "</div>\n";
	                    break;
	                case Types.FLOAT:
	                case Types.DOUBLE:
	                	text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+"':"+"</label></div>\n"+
	                        	"\t<div class='col-xs-10' ><input  class='form-control' id='"+beanName+"."+fieldName+"' type='number' name='"+
	    	            		   DataMapper.formatMethodName(cols[i])+"' "+
	                	"value='"+cOut(cols[i])+"'/>\n"+	
						  // " value='<c:out value=\"${formBean."+DataMapper.formatMethodName(cols[i])+"}\"/>'"+
				           "\t</div>\n";
	                    break;
	    
	                case Types.DECIMAL:
	                  	text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+":</label></div>\n"+
	                        	"\t<div class='col-xs-10' ><input  class='form-control' id='"+beanName+"."+fieldName+"' type='number' name='"+
	    	            		   DataMapper.formatMethodName(cols[i])+"' "+

	                		"value='"+cOut(cols[i])+"'/>\n"+
						   "\t</div>\n";
	                    break;
	                case Types.DATE:
	                
	                  	text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+":"+"</label></div>\n"+
	                        	"\t<div class='col-xs-10' ><input  class='form-control' id='"+beanName+"."+fieldName+"' type='date' name='"+
	    	            		   DataMapper.formatMethodName(cols[i])+"' "+
						   " value='<%if(formBean.get"+DataMapper.formatMethodName(cols[i])+"()!=null){%>"+
						      "<%=DateFormat.getDateInstance(DateFormat.MEDIUM).format(formBean.get"+DataMapper.formatMethodName(cols[i])+"() )%>"+
				           "<%}%>'/>\n</div>\n";
	                    break;
	                case Types.TIMESTAMP:
	                  	text+="\t<div class='col-xs-2' ><label for='"+beanName+"."+fieldName+"'>"+DataMapper.formatMethodName(cols[i])+":>"+"</label></div>\n"+
	                        	"\t<div class='col-xs-10' ><input  class='form-control' id='"+beanName+"."+fieldName+"' type='time' name='"+
	    	            		   DataMapper.formatMethodName(cols[i])+"' "+
					   " value='<%if(formBean.get"+DataMapper.formatMethodName(cols[i])+"()!=null){%>"+
					      "<%=DateFormat.getDateInstance(DateFormat.MEDIUM).format(formBean.get"+DataMapper.formatMethodName(cols[i])+"() )%>"+
			           "<%}%>'/>\n</div>\n";	                	
	                case Types.LONGVARBINARY:
	                case Types.BINARY:    
	                case Types.BLOB:  
	                case Types.JAVA_OBJECT :  
	                case Types.CLOB : 
	                case Types.OTHER :
	                	text+="<%/*BLOB not supported*/%>\n";
	            }
        	} //end if not primary key
            text+="</div><!--end row-->\n";

        }
        text+="<input type='hidden' id='button' name='nextCommand' value='update'/>\n"+
 		"\n"+
		"<button id='button.next' class='btn btn-primary' name='saveBut' onClick='updateClick(dataForm)'>Create</button><br>\n" +
 		"</form>\n";
        
		return text;
	}


	/**
	 * Gernerate admin index  
	 * 
	 */
	private void generateIndex(){
		log.trace(">>>generateIndex");
		String filePath=this.getFilePath();
		try{
	        File file=new File(filePath+"/../../../admin/index.jsp");
	        log.info("abs.path="+file.getAbsolutePath());
	        //if(!file.exists() ){
	        
		        FileWriter fo;
		        //String[] parms=new String[1];
				fo = new FileWriter(file);
				List<String> tlist=getEntityNames();
				StringBuffer buffer=new StringBuffer("<ul>\n");
				for( String table:tlist){
					String entity=DataMapper.formatClassName(table);
					buffer.append("<li><a href='/admin/"+entity+"/list.htm'>List "+entity+"</a></li>\n");
				}
				buffer.append("</ul>\n");
				//parms[0]=buffer.toString();
				HashMap<String,String> valueMap = new HashMap<String,String>();
				StrSubstitutor sub = new StrSubstitutor(valueMap);
				//String text =StringUtil.replace(indexTemplate, parms);
				valueMap.put("JavaWebGen.list",buffer.toString() );
		        PrintWriter out = new PrintWriter(fo);
		        out.print(sub.replace(indexTemplate));
		        out.flush();
		        out.close();
		        log.info("---Wrote File "+"admin/index.jsp");	
		        
	        //}
		}catch(IOException ioe){
			log.error("error write file"+ioe.getMessage());
		}finally{
			
		}
		log.trace("<<<generateIndex");

	}
	/**
	 * write out files after looping though table and column list
	 * this method is only call once
	 */
	@Override
	protected void postExecute() throws UtilException {
		String filePath=this.getFilePath();
		try{
	        File file=new File(filePath+File.separator+"dojoInc.jsp");
	        if(!file.exists() ){
	
		        FileWriter fo = new FileWriter(file);
		        PrintWriter out = new PrintWriter(fo);
		        out.print(styleTemplate);
		        out.flush();
		        out.close();
		        log.info("---Wrote File "+filePath+File.separator+"dojoInc.jsp");
	        } 
		        //create theme includes
		       // File dir=new File(filePath+File.separator+"/theme");
		       // dir.mkdir();
		        
		         
		       /* File dir=new File(filePath+File.separator+"theme");
		        dir.mkdir();
		        dir=new File(filePath+File.separator+"theme"+File.separator+"default");
		        dir.mkdir();*/
		        String theme=filePath+File.separator+"theme"+File.separator+"default";
		        log.info("write theme dir"+filePath+File.separator+"theme"+File.separator+"default");
		        file=new File(theme+File.separator+"/nav.jsp");
		        FileWriter fo = null;
		        PrintWriter out =null;
		        //if(!file.exists() ){
			        fo = new FileWriter(file);
			        out = new PrintWriter(fo);
			        out.print("<%//nav will not be regenerated "+VERSION+"%>\n");
			        out.flush();
			        out.close();
			        log.info("---Wrote File "+theme+File.separator+"/nav.jsp");
		       // }
		        file=new File(theme+File.separator+"/menu.jsp");
		        if (!file.exists()){
			        fo = new FileWriter(file);
			        out = new PrintWriter(fo);
			        out.print("<%//will not be regenerated footer "+VERSION+"%>\n" +
			        		"<a href='/admin/index.jsp'>Admin Home</a>");
			        out.flush();
			        out.close();
			        log.info("---Wrote File "+theme+File.separator+"/menu.jsp");
		        }
		        file=new File(theme+File.separator+"/header.jsp");
		        if (!file.exists() ){
			        fo = new FileWriter(file);
			        out = new PrintWriter(fo);
			        out.print("<%//will not be regenerated header "+VERSION+"%>\n");
			        out.flush();
			        out.close();
			        log.info("---Wrote File "+theme+File.separator+"/header.jsp");
		        }
		        file=new File(theme+File.separator+"/footer.jsp");
		        if (!file.exists()){
			        fo = new FileWriter(file);
			        out = new PrintWriter(fo);
			        DateFormat dateFormat = new SimpleDateFormat();
			        java.util.Date date = new java.util.Date();
 
			        
			        out.print("Autogenerated by JavaWebGen "+VERSION+" on "+dateFormat.format(date) );
			        out.flush();
			        out.close();
			        log.info("---Wrote File "+theme+File.separator+"/footer.jsp");
			       
		        }
		        generateIndex();
	       
		}catch(Exception e){
			throw new UtilException(UtilException.GENERIC,e);
		}

		
	}
	/**
    *
    */
    public static void main(String[] args) {
        try{ 	
        	OldGenerateSpringView app = new OldGenerateSpringView();
        	app.setCmdParms(args);
        	app.init();
            app.processXmlFile(app.getFileName());
            log.info("finshed generating view "+VERSION);
       	}catch(Exception e){
       		log.error("main ERROR",e);
       	}
    }

	public void useage() {

	    System.out.println("To Process all tables in a text file");
	    System.out.println("USAGE GenerateView -f <filename> <path>");
	    System.exit(1);
	}
}