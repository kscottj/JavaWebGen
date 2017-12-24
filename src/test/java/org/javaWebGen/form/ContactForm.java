package org.javaWebGen.form;

 
import org.javaWebGen.form.HtmlCheckboxField;
import org.javaWebGen.form.HtmlEmailField;
import org.javaWebGen.form.HtmlField;
import org.javaWebGen.form.HtmlHiddenField;
import org.javaWebGen.form.HtmlTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactForm extends HtmlForm{
	
	private HtmlField from = new HtmlEmailField("from",false);
	private HtmlField subject = new HtmlTextField("subject",false);
	private HtmlField message = new HtmlTextField("message",false);
	private HtmlField capta = new HtmlCheckboxField("capta",true);
	private HtmlField emailHash = new HtmlHiddenField("hash",false);
	public static final String NOT_A_ROBOT="not a robot";
	/**
	 * 
	 */
	private static final long serialVersionUID = -515146338453729548L;
	private static final Logger log=LoggerFactory.getLogger(ContactForm.class); 
 
	public ContactForm( ) {
	 
		addFields();
		
	}
	private void addFields() {
		this.addField(from );
		this.addField(subject );
		this.addField(message );
		capta.setValue("no");
		this.addField(capta );
		this.addField(emailHash );
	}
	

	/**
	 * Perform custom validation a data bound form.  Subclasses should override this
	 * method if they need to perform custom validation.
	 * @return true if form is valid
	 */
	public boolean validate() {
		boolean valid=super.validate();
		
		if(capta.getValue()!=null && capta.getValue().equals(NOT_A_ROBOT)) {

		} else {
			capta.setErrorMessage( "Required Field");
			valid=false;
		}
		
	 
		log.debug("end validate:"+valid);
		return valid;
	}
	
	public HtmlField getFrom() {
		return from;
	}
	public HtmlField getSubject() {
		return subject;
	}
	public HtmlField getMessage() {
		return message;
	}
	public HtmlField getCapta() {
		return capta;
	}
	public HtmlField getHash() {
		return emailHash;
	}	
	

}
