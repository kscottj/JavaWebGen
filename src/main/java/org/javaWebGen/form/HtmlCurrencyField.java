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


import org.apache.commons.validator.routines.CurrencyValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
/**
 * field to handle money formats.  So it understands  , .
 * @author scotkevi
 *
 */
public class HtmlCurrencyField extends HtmlTextField{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 5420957254673391794L;
	@SuppressWarnings("unused")
	private static final Logger log=LoggerFactory.getLogger(HtmlCurrencyField.class); 
	public static final String INVALID_NUMBER_MESSAGE="Enter a valid Currency Amount";
	public static final String INVALID_NUMBER_KEY="form.error.currency";
	//private static final NumberFormat moneyFormat=NumberFormat.getCurrencyInstance();
	public HtmlCurrencyField(String fieldName){
		super(fieldName);
		
	}
	public HtmlCurrencyField(String fieldName,boolean required){
		super(fieldName,required);
		
	}
	
	public HtmlCurrencyField(String fieldName,boolean required,String label){
		super(fieldName,required,label);
	}
	public HtmlCurrencyField(String fieldName,boolean required,String label,String attributes){
		super(fieldName,required,label,attributes);
	}
	

	@Override
	public boolean validate(String value){
		boolean val=super.validate(value);
		if(val&&value!=null&&value.length()>0) { 
			val=CurrencyValidator.getInstance().isValid(value);
			if(!val){
				this.setErrorMessage(this.getProps(INVALID_NUMBER_KEY, INVALID_NUMBER_MESSAGE) ); 
				this.isFieldValid=false;
			}
		}

		return val;
	}
	/**
	 * Attempts to return a formatted currency string
	 * return raw string if it can not be converted
	 * @return currency
	 */
	@Override
	public String getValue(){
		String moneyStr=super.getValue();
		/*try {
			Number num =  moneyFormat.parse(moneyStr);
			return moneyFormat.format(num);
		} catch (ParseException e) {
			log.warn("unable to form money "+moneyStr+" e="+e.getMessage() );
			return this.getValue();
		}*/
		return moneyStr;
	}
	@Override
	public void cleanField() {
		//TODO convert to plain number without formmating
		if(this.getValue()!=null){
				this.setValue(this.getValue().trim() );
			
		}
		
	}

}
