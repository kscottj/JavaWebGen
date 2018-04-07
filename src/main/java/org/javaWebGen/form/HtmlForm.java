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
package org.javaWebGen.form;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.javaWebGen.config.ConfigConst;
import org.javaWebGen.data.FormBeanAware;
import org.javaWebGen.util.HtmlUtil;
import org.javaWebGen.util.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * EXAMPLE:
 * <PRE>
 * public class AuthorForm extends HtmlForm{
* 		private static final long serialVersionUID = 453453453788212L;
* 	    //form fields
*	    private HtmlNumberField  authorId= new HtmlNumberField("authorId" ,false);
*		private HtmlTextField  firstName= new HtmlTextField("firstName" ,false);
*		private HtmlTextField  lastName= new HtmlTextField("lastName" ,false);
* 
*  //constructor that builds form and registers fields
*  	public AuthorForm(){
*		this.addField(authorId);
*		this.addField(firstName);
*		this.addField(lastName);
*	}
* 
* 	
*	public String getWebFormName(){
* 		return "AuthorForm";
* 	}
* 
*	public HtmlNumberField getAuthorId(){
*		 return authorId;
*	}
*	public HtmlTextField getFirstName(){
*		 return firstName;
*	}
*	public HtmlTextField getLastName(){
*		 return lastName;
*	}
*}
*</PRE>
* This class would generate the following HTML currently this HTML5 and JQuery  friendly using the bootstrap CSS
* <PRE>
* <form action ='/Controller?page=Author' name='AuthorForm' method='POST'>
*    <input id='text' class='form-control' type='text' name='authorId' value='' placeholder=''/>
* 	 <input id='text' class='form-control' type='text' name='firstName' value='' placeholder=''/>
*    <input id='number' class='form-control' type='number' name='lastName' value='' placeholder=''/>
* </form>
* </PRE>
 * @author home
 *
 */
public class HtmlForm implements  WebFormAware, Serializable{

	private static final long serialVersionUID = -2989734665486513842L;
	public static final String DATE_PATTERN="MM/dd/yyyy";
	private static final Logger log=LoggerFactory.getLogger(HtmlForm.class); 
	//private static final DateFormat dateFormat=DateFormat.getDateInstance(DateFormat.SHORT);
	private static DateConverter dateConverter = null ;
	private FormBeanAware databean=null;
	private String action=null;
	private String htmlName="dataFormId";
	private boolean isFormValid=true; 
	private String buttonLabel="Submit";
	private HashMap<String,String> reqMap=null;
	private LinkedHashMap<String,HtmlField> fieldMap=new LinkedHashMap<String,HtmlField>();
	private HashMap<String,String> errorMap =new HashMap<String,String>();
	private List<String> formErrors =new ArrayList<String>();
	private String contextPath="";
	private StyleAware style= new BootstrapStyle();
	private String formName="dataForm";
	private HtmlSubmitButton customSubmitButton=null;
	private static final PropertiesReader  messages=PropertiesReader.getReader(ConfigConst.MESSAGE);
 
	
	/**
	 * 
	 * register converter classes for the beanUtils for entire VM
	 * @TODO move this somewhere else like the framework controller
	 * */
	static{
		
		dateConverter = new DateConverter(null);
		dateConverter.setPattern(DATE_PATTERN);
		ConvertUtils.register(dateConverter, java.util.Date.class);
		ConvertUtils.register(new org.apache.commons.beanutils.converters.BigDecimalConverter(null), BigDecimal.class);
		ConvertUtils.register(new org.apache.commons.beanutils.converters.LongConverter(null), Long.class);
		ConvertUtils.register(new org.apache.commons.beanutils.converters.IntegerConverter(null), Integer.class);	}
	/**
	 * construct object that can generate HTML based on data
	 */
	public HtmlForm(){}
	/**
	 * 
	 * @param path root of web IE web context
	 */
	public HtmlForm(String path){
		this.setContextPath(path);
		
	}
	/**
	 * Construct object that can generate HTML based on data
	 * @param bean to populate form with by matching names
	 */
	public HtmlForm(FormBeanAware bean){
	 
		this.databean=bean;
		setData(bean);
	}

	/**
	 * 
	 * @param bean overrides any data in request
	 * @param req uses request to populate the form by matching field names 
	 */
	public HtmlForm(FormBeanAware bean, HttpServletRequest req){
		this.databean=bean;
		//this.formName="dataForm";
		setData(bean,req);
		 
	}
	/**
	 * 
	 * @param req uses request to populate the form by matching field names 
	 */
	public HtmlForm(  HttpServletRequest req){
		setData(null,req);
	}
	/**
	 * Get a specific field
	 * @param name of field
	 * @return from field
	 */
	public HtmlField getField(String name){
		return fieldMap.get(name);
		 
	}
	/**
	 * Adds (registers) a HTMLField field on a form 
	 * @param field HtmlField
	 */
	public void addField(HtmlField field){
		if(field!=null && field.getName()!=null) {
			
			fieldMap.put(field.getName(),field);
		} 
	}
	/**
	 * ServletContextPath used in request the form is processing.
	 * This is used as a prefix for the action field.  Will be set to emptyString if a null 
	 * is passed in.
	 * 
	 * @param path
	 */
	public void setContextPath(String path){
		if(path!=null){
			this.contextPath=path;
		}else{
			this.contextPath="";
		}
	}
	/**
	 * Set values based on what is passed in the request
	 * this will by default attempt to sanitize all input by
	 * removing all tags an links.  If you do not want this (should always be used like 99% of the time) you will need 
	 * manually pull the data out of the HTTP request
	 * @param req
	 */
	private void setValuesFromRequest(HttpServletRequest req)  {
		
		if(req==null){
			log.error("req=[NULL] exiting");
			return;
		}
		 log.debug(">>>+setValuesFromRequest:"+req.getContextPath() );
		 setContextPath(req.getContextPath() );
		 reqMap = new HashMap<String,String>();
	
		Enumeration<String> names = req.getParameterNames();
		 
	    while (names.hasMoreElements()) {
	       String name = names.nextElement();
	       String cleanParm = HtmlUtil.stripTags( req.getParameter(name) );
	       reqMap.put(name, cleanParm );
	       //log.debug(" req key="+name+" value="+cleanParm+"|" );   	 
	    }
	    
	    try {
	    	 if(databean!=null){
	    		 BeanUtils.populate(databean, reqMap);
	    		 this.setFieldMap(databean);
	    	 }
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException("error setting bean from request",e);
		}
	    Iterator<String> i= reqMap.keySet().iterator();
	    while( i.hasNext()){
	    	String key=i.next();
	    	HtmlField field=fieldMap.get(key);
	    	if(field!=null){
	    		field.setValue(reqMap.get(key));
	    		log.debug("===>set "+field.getClass()+field.getName()+".value="+reqMap.get(key) );
	    	}
	    }	 
	}
	/**
	 * Set form values based on field names in a FormBeanAware JavaBean
	 * @param bean with getters that match field names
	 */
	public void setData(FormBeanAware bean){
		
		setData(bean,null);
		
	}

	/**
	 * Set form values based on bound FormBeanAware JavaBean.  Will override form values based values passed on the request
	 * 
	 * <ul>works in this order
	 * <li>clear form data
	 * <li>populate form with data from FormBeanAware JavaBean
	 * <li>populate form with data from request 
	 * </ul>
	 * So the request data will override anything in set by the bean
	 * @param bean populates registered fields with data based on field name
	 * @param req populates registered fields with request data based on field name
	 */
	public void setData(FormBeanAware bean,HttpServletRequest req){
		this.reset(); 
		this.databean=bean;
		this.clean();
		//this.validate(errorMap);
		if(bean!=null){
			setFieldMap(bean);
		}
		if(req!=null){
			setValuesFromRequest(req);
		}
		
	}
	/**
	 * Set form values based values passed on the request
	 * WARNING This should should only be used with forms that will ARE not bound to a bean.
	 * This method will clear any bound data.
	 * In other words, the getData  method will return a null after this method is called.
	 * 
	 * @param req servlet request 
	 */
	public void setData(HttpServletRequest req){
		this.reset(); 
		
		this.databean=null;
		this.clean();
		//this.validate(errorMap);
		//setFieldMap(bean);
		if(req!=null){
			this.setContextPath(req.getContextPath() );
			log.debug("req.getRequestURI()"+req.getRequestURI() );
			setValuesFromRequest(req);
		}
		
	}
	/**
	 * Set form values based values passed on the request
	 * WARNING This should should only be used with forms that will ARE not bound to a bean.
	 * This method will clear any bound data.
	 * In other words, the getData  method will return a null after this method is called.
	 * @see #setData
	 * @param req  
	 */
	public void setRequest(HttpServletRequest req){
		setData(req);
	}
	/**
	 * Add Form Error Message.  This is error is not related to a specific field on this page
	 * @param errorMessage error message to display 
	 */
	public void addError(String errorMessage){
		 
		this.formErrors.add(errorMessage);
	}
	/**
	 *  Add Form Error Message.  This is error is not related to a specific field on this page
	 * @param messageKey key to look up error in message bundle
	 * @param defaultError default error message if key not found in message bundle 
	 */
	public void addError(String messageKey,String defaultError){
		String msgStr=messages.getProperty(messageKey,defaultError);
		this.formErrors.add(msgStr);
	}
	/**
	 * Add field level error Message
	 * @param fieldName for JavaScript reference
	 * @param message of error
	 */
	public void addFieldErrors(String fieldName,String message){
		this.errorMap.put(fieldName, message);
	}
	/**
	 * return html error 
	 */
	public String getHtmlErrors() {
		List<String> list=this.getErrors();
		StringBuffer buffer= new StringBuffer("<ul class='hasErrors'>");
		for(String error:list) {
			buffer.append("<li>"+error+"</li>");
		}
		buffer.append("</ul>");
		return buffer.toString();
	}
	/**
	 * return a list of error messages that are not related to a specific bound field
	 * @return list of generic form errors
	 */
	public List<String> getErrors(){
		return this.formErrors;
	}
	
	/**
	 * return a list of error messages that are not related to a specific bound field
	 * @see HtmlForm.getErrors
	 * @return list of generic form errors
	 */
	public List<String> getFormErrors(){
		return getErrors();
	}
	/**
	 * Get error messages that bound to a field on the form. IE To be able to highlight the error field in the JSP
	 * return a map of errors with the key set to the field id name on the form
	 * @return error map empty if no errors where found
	 */
	public HashMap<String,String> getFieldErrors(){
		return errorMap;
	}

	/**
	 * Set registered fields with values from the data bound bean that is passed in
	 * @param bean
	 */
	private void setFieldMap(FormBeanAware bean){
		Collection <HtmlField> c=fieldMap.values();
		for(HtmlField field:c){
			try {
				Object value=PropertyUtils.getProperty(bean, field.getName() );
				if(value!=null){
					if(value instanceof java.util.Date && field instanceof DateFieldAware ){
						log.debug("set date field "+field.getName() +" value="+value);
						//PropertyUtils.setProperty(field,"value",(java.util.Date) value);
						PropertyUtils.setProperty(field,"date",(java.util.Date) value);
						log.debug("after date field "+field.getName() +" value="+field.getValue() );
					}else{
						field.setValue(value.toString() );
					}
					//PropertyUtils.setProperty(field,"setValue",value);
				}else{
					field.setValue(null);
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				//throw new IllegalArgumentException("could not find"+databean+".get"+field.getName(),e);
				//log.debug("could not find "+databean+".get"+field.getName()+":"+e.getMessage() );
		
			}
		}
 	}
	/**
	 * Set default submit button label
	 * @param label
	 */
	public void setButtonLabel(String label){
		this.buttonLabel=label;
	}
	

	/**
	 * Get FormBeanAware JavaBean that populated the form 
	 * @return dataBean that poulated the form
	 * @throws IllegalArgumentException if a JavaBean was not passed to the form's .setData method.  IE no data to pass back
	 */
	public FormBeanAware getData(){
		if(this.databean==null){
			throw new IllegalArgumentException("No DataBean was passed on the form's .setData method");
		}else{
			return this.databean;
		}
	}
	/**
	 * Set the HTML action attribute IE action="" on the HTML form
	 */
	public void setAction(String action) {
		this.action=action;
	}
	
	/**
	 * Form submit button with current CSS style 
	 * @return default submit button
	 */
	public String getSubmit() {
		StringBuffer buffer=new StringBuffer();
		 
		buffer.append("<div class='"+style.getColSm2()+" "+style.getColSm10()+"'>");

		if(this.customSubmitButton==null){
			buffer.append("  <button type='submit' class='"+style.getPrimaryButton()+"'>"+buttonLabel+"</button>");
		}else{
			buffer.append( customSubmitButton.getField() );
		}
		buffer.append("</div>\n");
		return buffer.toString();	

	}

	/**
	 * Get form as HTML with StyleAware CSS formating using <DIV> tags.  The default will be Bootstrap CSS style.
	 * @return HTML including <FORM> tag and any JQuery JavaScript needed to validate the field
	 */
	@Override
	public String getForm(){
		StringBuffer buffer=new StringBuffer();

		buffer.append("<form id='"+this.htmlName+"' class='"+style.getForm()+"' action='"+contextPath+action+"' name='"+this.formName+"' method='post' role='form' >");
		List<String> errList=this.getErrors();
		for(String errStr:errList){
			String errmsg=messages.getProperty(errStr, errStr);  //try to lookup error message in bundle else assume it is a simple string
			buffer.append("<div class=\""+style.getFormError()+"\"><label class=\""+style.getControlLabel()+"\">"+errmsg+"</label></div>\n");
		}

		
		Collection <HtmlField> c=fieldMap.values();
		for(HtmlField field:c){
			buffer.append(field.getDivTag() );
		}
		buffer.append(this.getSubmit() );
		buffer.append("</form>\n");
		return buffer.toString();	
	}
	/**
	 * set all fields to read only 
	 * @param isViewOnly true or false
	 */
	public void setIsViewOnly(boolean isViewOnly){
		Collection <HtmlField> c=fieldMap.values();
		for(HtmlField field:c){
			field.setViewOnly(isViewOnly);
			
		}
	}
	/**
	 * Change the default bootstrap CSS style
	 */
	public void setStyle(StyleAware style){
		this.style=style;
		Collection <HtmlField> c=fieldMap.values();
		for(HtmlField field:c){
			field.setStyle(style);

		}	
	}
	/**
	 * Renders form as a HTML table.  Does not include the <FORM> tag.  Does not Include the <TABLE> tag.
	 * Just includes <TR> and <TD> format tags.
	 * @return HTML of form  fields
	 */
	public String getTable(){
		StringBuffer buffer=new StringBuffer();
	
		Collection <HtmlField> c=fieldMap.values();
		for(HtmlField field:c){
			buffer.append(field.getTable() );
			
		}
	
		return buffer.toString();	
	}
	/**
	 * Renders form as a HTML table.  Does not include the <FORM> tag.
	 * @return HTML of form  fields
	 */
	public String getDivTag(){
		StringBuffer buffer=new StringBuffer();
	
		Collection <HtmlField> c=fieldMap.values();
		for(HtmlField field:c){
			buffer.append(field.getDivTag() );
			
		}
	
		return buffer.toString();	
	}
	/**
	 * return form's data as a HTML form.  Includes <FORM> tag .
	 * @return HTML fragment
	 */
	@Override
	public String toString(){
		return getForm();
	}
	
	/**
	 * Check the String value to two HTMLForm classes for equality
	 * @return true HTML String is the same
	 */
	@Override
	public boolean equals(Object obj){
		if(obj==null ||this.toString()==null){
			return false;
		}else if (obj instanceof HtmlForm){
			HtmlForm form=(HtmlForm) obj;
			return this.getData().equals(form.getData());
		}else{
			return false;
		}
		
	}
	@Override
	public int hashCode() {
		if(this.getData()!=null){
			return getData().hashCode();
		}else{
			return 0;
		}
	}
	
	/**
	 * Returns JavaScript for the form.  Includes date picker and form validation
	 * @return JQuery JavaScript
	 */
	public String getJQueryScript() {
		StringBuffer js=new StringBuffer("");
		Collection <HtmlField> c=fieldMap.values();
		for(HtmlField field:c){
			js.append(field.getJQueryFieldScript());
		}
		return js.toString()+getJQueryValidate();
	}
	/**
	 * Generate validation JavaScript for each field in the data.
	 * Includes JQuery Validate and Date and Time pickers if needed.
	 * @return JQuery based validation JavaScript
	 */
	public String getJQueryValidate() {
		StringBuffer json=new StringBuffer("");
		String validatejs= 
		"$('#"+this.htmlName+"').validate({\n"+
		"	errorClass: 'help-block has-error',\n";
		//"};\n";
		json.append(validatejs);
		validatejs="  rules: {\n";
		json.append(validatejs);
		Collection <HtmlField> c=fieldMap.values();
		for(HtmlField field:c){
			if(! field.isViewOnly() ){ //do not valid view only field
				json.append(field.getJQueryFieldValidate());
			}
		}
		json.append("}, //end rules\n");
		validatejs=
		"  highlight: function(element) {\n"+
		"    $(element).closest('.form-group').removeClass('has-success').addClass('has-error');\n"+
		"  },\n"+
		"  success: function(element) {\n"+
		"    element.text('OK').addClass('valid').closest('.form-group').removeClass('has-error').addClass('has-success');\n"+
		"  },\n"+
		"}); //end validate\n"+
		"console.log('end jquery validate');\n";
	
		json.append(validatejs);
		return json.toString();

	}
	/**
	 * Is this form valid
	 * @return true if no validations errors are found in any field
	 */
	public boolean isValid(){
		if(isFormValid){
			this.isFormValid=this.validate();
		}
		if(errorMap.size()>0 || this.formErrors.size()>0){
			this.isFormValid= false;
			log.debug("form.err"+formErrors.size());
			if(this.formErrors.size()>0) {
				log.debug("form.err"+formErrors.get(0));
			}
			log.debug("field.err"+errorMap.size());
			
		}
		return this.isFormValid;
	}
	/**
	 * Perform custom validation a data bound form.  Subclasses should override this
	 * method if they need to perform custom validation.
	 * @return true if form is valid
	 */
	public boolean validate() {
		log.debug(">validate()");
		this.clean();
		boolean valid=true;
			
		Collection <HtmlField> c=fieldMap.values();
		for(HtmlField field:c){
			valid=field.validate(field.getValue() );
			if(!valid){
				log.warn("invalid web form field="+field.getName()+" value="+field.getValue() );
				this.addFieldErrors(field.getName(), field.getErrorMessage());
				//errorMap.put(field.getName(), field.getErrorMessage() );
				 
			}
			log.debug(field.getName()+".isValid:"+valid );
		}
		

		log.debug("<validate():"+valid);
		return valid;
	}
	/**
	 * clean the data that will be displayed
	 */
	public void clean() {
		return;	
	}
	/**
	 * clear any errors
	 */
	public void reset(){
		errorMap.clear();
	}
	@Override
	public String getWebFormName() {
		return this.formName;
	}
	
	public void setWebFormName(String name) {
		this.formName=name;
	}
	
	@Override
	public String getHtmlId() {
		return this.htmlName;
	}
	public void setHtmlId(String id) {
		this.htmlName=id;
	}
	/**
	 * Replace default submit button
	 * @param customSubmitButton submit button
	 */
	public void setSubmitButton(HtmlSubmitButton customSubmitButton){
		this.customSubmitButton=customSubmitButton;
	}
	 
}
