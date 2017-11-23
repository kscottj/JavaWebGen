package org.javaWebGen.form;


import org.apache.commons.validator.routines.PercentValidator;
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
		//log.info(this+".validate("+value+")");
		/*try{
			new BigDecimal(this.getValue() );
		}catch(NumberFormatException e){ //not a number
			this.setErrorMessage(this.getProps(INVALID_NUMBER_KEY, INVALID_NUMBER_MESSAGE) ); 
			 
			return false;
		}*/
		/*boolean isValid=BigDecimalValidator.getInstance().isValid(value);
		if(!isValid){
			this.setErrorMessage(this.getProps(INVALID_NUMBER_KEY, INVALID_NUMBER_MESSAGE) ); 
		}
		
		return isValid;*/
	 
		boolean val=PercentValidator.getInstance().isValid(value);
		if(!val){
			this.setErrorMessage(this.getProps(INVALID_NUMBER_KEY, INVALID_NUMBER_MESSAGE) );  
		}
		log.trace(this.getName()+".isValid()"+val);
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
