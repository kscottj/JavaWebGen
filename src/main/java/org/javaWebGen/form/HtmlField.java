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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.javaWebGen.util.HtmlUtil;
import org.javaWebGen.util.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java bean that is aware of how to render itself using as a HTML field.  IE <INPUT> tag
 * @author Kevin Scott
 *
 */
public abstract class HtmlField implements  HtmlFieldAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6827077450538291650L;
	@SuppressWarnings("unused")
	private static final Logger log=LoggerFactory.getLogger(HtmlField.class);
	public static final String MESSAGE_BUNDLE="messages";
	private static final PropertiesReader reader=PropertiesReader.getReader(MESSAGE_BUNDLE);
	public static final String EMPTY="";
	/**field name default attribute*/
	private String name=null;
	private boolean required=false;
	protected boolean isFieldValid=true;
	/**field value default attribute*/
	private String value=null;
	//protected String cssClass=BootstrapStyle.FORM_FIELD;
	private String javascript="";
	
	private String formHtml=null;
	private String tooltip=null; //aka placeholder
	private String label=null;  
	private String size=null;
	/**field id default attribute*/
	private String htmlid=null;
	protected String extraAttributes="";
	private String errorMessage="";
	protected StyleAware style=new BootstrapStyle();
	private boolean isViewOnly;
	

	//id="exampleInputEmail1" placeholder="Enter email"
	/**
	 * 
	 * @param name field name
	 */
	public HtmlField(String name){
		this.name=name;
		this.htmlid=name;
		
	}
	/**
	 * Is this field required to be valid to submit a HTML form
	 * @param name field name
	 * @param required true of false
	 */
	public HtmlField(String name,boolean required){
		this.name=name;
		this.required=required;
		this.htmlid=name;	
	}
	/**
	 * 
	 * @param name of field
	 * @param required true of false
	 * @param label
	 */
	public HtmlField(String name,boolean required,String label){
		this.name=name;
		this.required=required;
		this.htmlid=name;	
		this.label=label;
	}
	/**
	 * 
	 * @param name of field
	 * @param required true of false
	 * @param label for field
	 * @param attributes extra HTML attributes to add to the input field
	 */
	public HtmlField(String name,boolean required,String label,String attributes){
		this.name=name;
		this.required=required;
		this.htmlid=name;	
		this.label=label;
		this.extraAttributes=attributes;
	}


	/**
	 * HTML label text
	 * @return label text
	 */
	public String getLabel(){
		String newLable=this.label;
		if(newLable==null){
			//newLable= DataMapper.formatClassName(this.name);
			newLable=StringUtils.capitalize(this.name);
		}else{
			newLable= this.label;
		}
		if(this.required){
			newLable=newLable+" *";
		}

		return newLable;
		
	}
	
	/**
	 * Size attribute in element
	 * @param sizeStr Size of field
	 */
	public void setSize(String sizeStr){
		this.size=sizeStr;
	}
	/**
	 * Size attribute in element
	 * @param size as an field
	 */
	public void setSize(int size){
		this.size=""+size;
	}
	/**
	 * Size attribute in element
	 * @return size of HTML form field
	 */
	public String getSize(){
	 
		return size;
	}
	/**
	 * Get HTML field size attribute
	 * size="10"
	 * @return size attribute
	 */	
	public String getSizeAttribute(){
		if(size!=null){
			return "size=\""+size+"\" ";
		}else{
			return "";
		}
	}
	/**
	 * id="name"
	 * @param id value
	 */ 
	public void setHtmlId(String id){
		this.htmlid=id;
	}
	/**
	 * If null (unset) return the value of the name field.  Need a unique value for JQuery JavaScript to hook
	 * id="name"
	 * @return id attribute
	 */ 
	public String getHtmlId(){
		
		if(this.htmlid==null){
			return this.getName();
		}
		return this.htmlid; 
	}
	/**
	 * Get HTML id attribute
	 * If null returns empty string
	 * id="name"
	 * @return id Attribute
	 */ 
	public String getIdAttribute(){
		if(htmlid!=null){
			return "id=\""+htmlid+"\" ";
		}else{
			return EMPTY;
		}
	}
	/**
	 * name="style"
	 * @return name Attribute
	 */
	public String getNameAttribute(){
		if(this.getName()!=null){
			return "name=\""+name+"\" ";
		}else{
			return EMPTY;
		}
	}
	/**
	 * class="style"
	 * @return class Attribute
	 */
	public String getClassAttribute(){
		if(this.style.getFormField()!=null){
			return " class=\""+this.style.getFormField()+"\" ";
		}else{
			return EMPTY;
		}
	}
	/**
	 * get value attrib of input field. Note ='null' it returns empty string
	 * value="1"
	 * @return class Attribute empty string if null
	 */
	public String getValueAttribute(){
		if(this.value==null ){
			return EMPTY;
		}else{
			return " value=\""+this.value+"\" ";
			
		}
	}
	/**
	 * return common attributes for all HTML fields:
	 * 1) id=''
	 * 2) style=''
	 * 3) name=''
	 * 4) size=''
	 * @return default HTML Attributes shared by all fields
	 */
	protected String getDefaultAttributes(){
		
		//id='"+this.htmlid+"' class='"+this.style.getFormField()+"' "+INPUT_TYPE+" name='"+this.getName()+"'"
		StringBuffer buff= new StringBuffer();
		buff.append(getIdAttribute() );
		buff.append(this.getNameAttribute() );
		buff.append(getClassAttribute() );
		buff.append(getSizeAttribute() );
		buff.append(getValueAttribute() );
		buff.append(" "+this.extraAttributes+" " );
		
		return buff.toString();
	}
	/**
	 * set the Labels text
	 * @param label text
	 */
	public void setLabel(String label){
		this.label=label;
	}
	/**
	 * set the CSS style defaults to bootstrap
	 * @param style
	 */
	public void setStyle(StyleAware style){
		this.style=style;
	}
	public String getProps(String key, String dafaultValue){
		/*if(reader==null){
			try{
				reader=PropertiesReader.getReader(MESSAGE_BUNDLE);
			}catch(MissingResourceException e){
				return dafaultValue;
			}
		}*/
		return reader.getProperty(key,dafaultValue);
	}

	/**
	 * 
	 * @return current Properties reader
	 */
	public PropertiesReader getProps(){
		return reader;
	}
	/**
	 * get HTML5 tool tip.  IE the placeholder in bootstrap
	 * @return placeholder text
	 */
	public String getToolTip() { //aka placeholder in bootstrap
		if(this.tooltip==null){
			return "";
		}else{
			return tooltip;
		}
		
	}
	/**
	 * Set HTML5 tool tip.  IE the placeholder in bootstrap
	 * @param tooltip text
	 */
	public void setToolTip(String tooltip){//aka placeholder in bootstrap
		this.tooltip=tooltip;
	}
	
	/**
	 * Add a custom error to the Field
	 * @param error message
	 */
	public void addCustomError(String error){
		this.isFieldValid=false;
		this.setErrorMessage(error);
	}
	@Override
	public void setViewOnly(boolean isViewOnly){
		this.isViewOnly=isViewOnly;
	}
	/**
	 * Should the field be rendered as view only with hidden fields to pass values
	 * @return true is is view only
	 */
	public boolean isViewOnly(){
		return this.isViewOnly;
	}
	
	/**
	 * Renders field in HTML
	 * @see HtmlField#getValue() getValue method returns a text value without HTML formating
	 */
	@Override
	public String toString(){
		
		return getField();
	
	}
	/**
	 * Compares the current name and value in a HTML field
	 * 
	 * @param obj to compare value field to
	 * @return true field getValue match
	 */	
	public boolean equals(Object obj){
		if(obj==null ||this.toString()==null){
			return false;
		}
		if (obj instanceof HtmlField ){
			HtmlField newField=(HtmlField) obj;
			if(this.getName().equals(newField.getName() ) ){
				if(this.getValue().equals(newField.getValue() ) ){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Return the hash code of the real value of the field.
	 * If null returns 0
	 */
	@Override
	public int hashCode(){
		if(this.getValue()!=null&&!this.getValue().equals(EMPTY)){
			return this.getValue().hashCode();
		}else{
			return 0;
		}
		
	}
	
 
	/**
	 * Get value from request object after striping tags
	 * @param req
	 */
	//@TODO is just stripping all tags enough? Should it at least escape them maybe instead of expecting the JSP to do it.
	public void setRequestData(HttpServletRequest req){
		this.value=HtmlUtil.stripTags(req.getParameter(this.name ) );
	}
	/**
	 * Get value from a request object after stripping any evil tags found.
	 * WARNING be sure you validate any data returned
	 */
	public void setRequestDatawithTags(HttpServletRequest req){
		this.value=HtmlUtil.stripEvilTags(req.getParameter(this.name ) );
	}
 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	/**
	 * Get field value
	 * @return actual field value
	 */
	public String getValue() {
		/*if(this.value==null || this.value.equals("null") ){
			return EMPTY;
		}else{
			return this.value;
		}*/
		return this.value;	

	}
	/**
	 * Set field value
	 * @param value
	 */
	public void setValue(String value) {
		this.reset();

		this.value = value;
	}
	
	/**
	 * get any JQuery JavaScript needed by this form
	 * @return Javascript for form fields.
	 */
	public String getJQueryscript() {
		return javascript;
	}
	/**
	 * set any JQuery javaScript needed by this form
	 * @param javascript for form fields
	 */
	public void setJQueryScript(String javascript) {
		this.javascript = javascript;
	}
	 
	public String getFormHtml() {
		return formHtml;
	}
	public void setFormHtml(String formHtml) {
		this.formHtml = formHtml;
	}
	/**
	 * Clean value and validate it.
	 * Subclasses are responsible for cleaning, validating and 
	 * setting any errors in error Map
	 * @return true if field is valid
	 */
	public boolean isValid() {

		if(this.isViewOnly){ //do not validate view only
			return true; 
		}

		/*if(this.getValue()!=null && this.getValue().trim().length()>0){		 
			this.isFieldValid= validate(this.getValue());
				//this.errorMessage="Required Field";
		}*/

	
		//log.debug(this.getName()+".isValid()"+this.isFieldValid);

		//log.debug(this.getName()+".isValid="+isFieldValid);
		return isFieldValid;
	}
	/**
	 * 
	 */
	public boolean validate(String value) {
		log.debug("value="+value);
		this.cleanField();
		if(this.required){ 
			if(this.getValue()==null || this.getValue().trim().length()<1 ){
				this.errorMessage="Required Field";
				isFieldValid=false;
				return isFieldValid;
			}
			
		}
		return true;
	}
	
	/**
	 * group CSS and any errors
	 * @return CSS for HTML field groups
	 */
	public String getGroupCss(){
		if(this.isFieldValid){
			return style.getFormGroup();
		}else{
			return style.getFormGroup()+" "+style.getFormError();
		}
	}
	/**
	 * err CSS style
	 * @return error CSS
	 */
	public String getErrorStyle(){
		if(this.isFieldValid ){
			return "";
		}else{
			return style.getFormError() ;
		}
	}
    /**
     * Add extra HTML attributes to field IE extra styles
     * @param htmlAttributes attributes in name=value format
     */
	public void addAttributes(String htmlAttributes){
		this.extraAttributes=htmlAttributes;
	}
    /**
     * reset (clear) any errors
     */
	public void reset(){
		 this.isFieldValid=true;
 
	}
	/**
	 * set an custom error in field
	 * If no errors returns an empty string
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * set an custom error in field
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
 
		this.isFieldValid=false;
		this.errorMessage = errorMessage;
	}

	@Override
	public String getDivTag(){
		StringBuffer htmlBuffer=new StringBuffer();
		 
		htmlBuffer.append("<div class='"+this.getGroupCss()+"'>" );
		htmlBuffer.append("  <label for='"+this.htmlid+"' class='"+style.getControlLabel()+" "+style.getColSm2()+"'>"+this.getLabel()+"</label>");
		htmlBuffer.append("  <div class='"+style.getColSm10()+"'>");
		htmlBuffer.append(getField() );
		htmlBuffer.append("</div></div>\n");
 
		return htmlBuffer.toString();
		 
	}
	@Override
	public String getTable(){
		StringBuffer tableBuffer=new StringBuffer();
		
		tableBuffer.append("<tr class='"+this.getGroupCss()+"'><td><label for='"+this.htmlid+"'>"+this.getLabel()+"</label></td>");
		tableBuffer.append("<td>"+this.getField()+"</td>");
		tableBuffer.append("<td></td>");
		/*if(this.isFieldValid){
			tableBuffer.append("<td></td>");
		}else{
			tableBuffer.append("<td><label class='"+style.getFieldError()+"' for='"+this.getName()+"'> "+this.getErrorMessage()+"</label>");
			 
		}*/
		tableBuffer.append("</tr>\n"); 
		return tableBuffer.toString();
		 
	}
	
	public boolean hasErrors() {
		return this.isFieldValid;
	}
	
}
