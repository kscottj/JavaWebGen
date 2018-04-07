package org.javaWebGen.generator;

import java.util.ArrayList;

import org.javaWebGen.config.WebConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateSpringView extends GenerateView{

	 private static final Logger log=LoggerFactory.getLogger(GenerateSpringView.class);
	 
	 
     /**
      * generate script functions for update and delete actions. prompts uses to confirm deletes
      * @param cols
      * @param types
      * @return
      */
	 @Override
     protected String makeJavascript(String[] cols, int[] types){
     	String beanName=DataMapper.formatClassName(getTableName() );
     		String text=
     		
         "function deleteClick(dataForm){\n" +
         "\talert('Are you sure you want to delete this record?');\n" +
         "\tdataForm.action='/admin/"+beanName+"/"+WebConst.DELETE_CMD+".htm';\n"+
         "\tdataForm.submit();\n" +
		 "} //end delete function\n" +
		 
         "function updateClick(dataForm){\n" +
         //"alert('Are you sure you want to delete this record?');\n" +
         "\tdataForm.action='/admin/"+beanName+"/"+WebConst.SAVE_CMD+".htm';\n"+
         "\tdataForm.submit();\n" +
		 "} //end update function\n";

     	return text;
      }
     
     /**
      * build nave menu left hand
      * @return
      * @throws Exception
      */
	 @Override
     protected String makeNav()throws Exception{
      	//String beanName=DataMapper.formatClassName(getTableName() );
       
 
    	StringBuffer buffer=new StringBuffer("<ul class=\"nav nav-pills nav-stacked\">\n");
		for( String table:getEntityNames() ){
			String entity=DataMapper.formatClassName(table);
			buffer.append("<li><a href='"+entity+"/list.htm'> List "+entity+"</a></li>\n");
		}
		buffer.append("</ul>\n");
		return buffer.toString();
     }
	 /**
	  * generate html form
	  * @param cols
	  * @param types
	  * @return form in html
	  * @throws Exception
	  */
	  @Override
	  protected String makeForm(String[] cols, int[] types)throws Exception{
	     	String beanName=DataMapper.formatClassName(getTableName());
	     	 
	     	ArrayList <String> primaryKeys= getPrimaryKeys();
	         String text=
	          "<div class='"+style.getRow()+" "+style.getFormGroup()+"'><a href='"+adminPrefix+"/index.jsp'>Admin Menu</a>\n"
	         +"<a href='"+adminPrefix+"/"+beanName+"/"+WebConst.LIST_CMD+".htm'>Back to List Menu</a>"
	         +"</div>\n"
	     	+"<form id='dataFormId' name='dataForm' action ='"+adminPrefix+"/"+beanName+"/save.htm' METHOD='post'>\n";
	         for(String col:cols){
	         	String var=DataMapper.formatVarName(col);
	         	boolean isPrimary= false;
	         	for(String pkey:primaryKeys){
	         		if(pkey.endsWith(col)){
	         			isPrimary=true;
	         		}
	         		if(isPrimary){
	         			text+=
	         			 "<div class='"+style.getFormGroup()+"'>\n"
	         			+"<div class='"+style.getColSm2()+"'>"
	         					+DataMapper.formatClassName(col)+"</div><div class='"+style.getColSm10()+"'>${form."+var+".value}</div>\n"
	         			+"</div>\n"
	         			+"<input type='hidden' name='"+var+"' value='${form."+var+".value}'>\n";
	         		}else{
	         			text+="${form."+var+".divTag}\n";
	         		}
	         	}
	         }
	         text+="${form.csrf.divTag}\n";
	     	if(primaryKeys.size()==1) { 
	         text+=
	 	 		 "<button id='button.update' class='"+style.getSubmitButton()+"' name='updateBut' onClick='updateClick(dataForm)' ><fmt:message key=\"dialog.save\" bundle=\"${msg}\" /></button>\n"
	 	 		+"<button id='button.delete' class='"+style.getWarnButton()+"' name='deleteBut' onClick='deleteClick(dataForm)'><fmt:message key=\"dialog.delete\" bundle=\"${msg}\" /></button>\n"
	 	        +"</form>\n";
	     	}else {
	     		text+="<!--update and delete not supported  --></form>\n";
	     	}
	        
	         return text;

	     }
	     /**
	      * 
	      * @param cols
	      * @param types
	      * @return create html
	      */
	    @Override
	 	protected String makeCreateForm(String[] cols, int[] types) {
	     	String beanName=DataMapper.formatClassName(getTableName());
	    	 
	     	ArrayList <String> primaryKeys= getPrimaryKeys();
	         String text=
	         	 "<div class='row'><a href='/admin/index.jsp'>Admin Menu</a>\n"
	         	+"<a href='"+adminPrefix+"/"+beanName+"/"+WebConst.LIST_CMD+".htm'>Back to List Menu</a>"
	         	+"</div>\n"
	         	+"<form id='dataFormId' name='dataForm' action ='"+adminPrefix+"/"+beanName+"/save.htm' METHOD='post'>\n";
	         for(String col:cols){
	         	String var=DataMapper.formatVarName(col);
	         	boolean isPrimary= false;
	         	for(String pkey:primaryKeys){
	         		if(pkey.endsWith(col)){
	         			isPrimary=true;
	         		}
	         		if(isPrimary){
	         			text+="<!-- primary key -->\n";
	         			text+="<input type='hidden' name='"+var+"' value='${form."+var+".value}'>\n";
	  
	         		}else{
	         			text+="${form."+var+".divTag}\n";
	         		}
	         	}
	         }
	         text+="${form.csrf.divTag}\n";
	         if(primaryKeys.size()==1) { 
	         	text+="<button id='button.update' type='submit' class='"+style.getSubmitButton()+"'><fmt:message key=\"dialog.add\" bundle=\"${msg}\" /></button>\n"
	         		+"</form>\n";
	         }else {
	         	text+="<!--update not supported -->\n"
	             	+"</form>\n";	
	         }   

	 		return text;
	 	}
	    /**
	     * make a html list
	     * @param cols
	     * @param types
	     * @return html list table
	     * @throws Exception
	     */
	    @Override
         protected String makeList(String[] cols, int[] types)throws Exception{
         	String beanName=DataMapper.formatClassName(getTableName() );
         	ArrayList <String> primaryKeys= getPrimaryKeys();
         	 //String text="<b>No Records found</b>";
         	 String text=
         		 "<p><a href='"+adminPrefix+"/"+beanName+"/create.htm'>Add New Record</a></p>"
    		     +"<c:choose>\n" 	
    		 	 +"   <c:when test=\"${!empty  dataBeanList}\">\n"
    		     +" <table class='"+style.getStripedTable()+"'>\n     <tr>";
         	 for(int i=0;i<2;i++){
         		 text+="<th>"+cols[i]+"</th>";
         	 }
         	 text+="<th>Action</th></tr>\n"
         	 	+"  <c:forEach var=\"bean\" items=\"${dataBeanList}\"> \n"
         	 	+"    <tr>";
         	 for(int i=0;i<2;i++){
         		 text+=
         		 "<td>${bean."+DataMapper.formatVarName(cols[i])+"}</td>";
         	 }
     		text+=	 
     			 "<td><a href='"+adminPrefix+"/"+beanName+"/detail.htm";
     		if(primaryKeys.size()==1){
     			text+="?"+DataMapper.formatVarName(primaryKeys.get(0) )+"=${bean."+DataMapper.formatVarName(primaryKeys.get(0) )+"}";
     		}
     		if(primaryKeys.size()>1){
    	 		for(int i=1;i<primaryKeys.size();i++){
    	 			text+="&"+DataMapper.formatVarName(primaryKeys.get(i) )+"=${bean."+DataMapper.formatVarName(primaryKeys.get(i) )+"}";
    	 		}
     		}
     		text+=
     			"'>Edit</a></td></tr>\n" 
     			+"</c:forEach>\n"
     			+"</table>\n"
     			+"</c:when>\n"
     			+"  <c:otherwise>\n"
     		 	+"    <i>no Data Found</i>\n"
     		 	+"  </c:otherwise>\n"
     		 	+"</c:choose>\n";
             return text;

         }

	/**
    *
    */
    public static void main(String[] args) {
        try{ 	
        	GenerateSpringView app = new GenerateSpringView();
        	app.setCmdParms(args);
        	app.init();
            app.processXmlFile(app.getFileName());
            log.info("finshed generating view v"+VERSION);
       	}catch(Exception e){
       		log.error("generic error",e);
       	}
    }

	public void useage() {
        System.err.println("GenerateView based on schema");
        System.err.println("<schema> torque XML with tables definitions");
        System.err.println("<path> destination file path");
        System.err.println("-template optional filename of template to use for code generator");
        System.err.println("USAGE Generate <schema> <path> [-template fileName] ");
 
	    System.exit(1);
	}
}
