package org.javaWebGen.form;

import org.junit.Assert;
import org.junit.Test;

public class TestCustomForm {
	
	@Test
	public void testCustomForm() {
		ContactForm form = new ContactForm();
		form.getFrom().setValue("g@g.com");
		HtmlField capta=form.getCapta();
		capta.setValue(ContactForm.NOT_A_ROBOT);		
		//Assert.assertTrue("form not valid",form.isValid() );
		capta.setValue("no");
		Assert.assertFalse("form is valid",form.isValid() );
		Assert.assertTrue("capt.err="+capta.getErrorMessage(),capta.getErrorMessage()!=null );
		Assert.assertTrue("has an form error=",form.getErrors().size()>0 );
		Assert.assertTrue("has an field error=",form.getFieldErrors().size()>0 );
		//field error?
		
		
	}
	@Test
	public void testDefaultErrors() {
		ContactForm form = new ContactForm();
		form.getFrom().setValue("g@g.com");
		HtmlField capta=form.getCapta();
		capta.setValue(ContactForm.NOT_A_ROBOT);		
		Assert.assertTrue("form not valid",form.isValid() );

	}
	@Test
	public void testInvalidDefaultData() {
		ContactForm form = new ContactForm();
		form.getFrom().setValue("g@ ");
		HtmlField capta=form.getCapta();
		capta.setValue(ContactForm.NOT_A_ROBOT);		
		Assert.assertFalse("form not valid",form.isValid() );
		form.getFrom().setValue("");
		Assert.assertFalse("form not valid",form.isValid() );
		form.getFrom().setValue("g@g.com");
		Assert.assertFalse("form not valid",form.isValid() );
	}
}
