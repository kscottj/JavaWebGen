package org.javaWebGen.form.test;

import java.math.BigDecimal;

import org.javaWebGen.data.DataBean;

/**
 * Mack databean used by UNIT tests
 * @author scotkevi
 *
 */
public class MockDataBean implements DataBean{
	private String text = null;
	private String hidden = null;
	private Long number = null;
	private java.util.Date date=null;
	private BigDecimal decimal=null;
	private BigDecimal currency=null;
	private String email=null;
	
	
	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toJSON()  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getData()  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(Object[] parms){
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getDataTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public BigDecimal getDecimal() {
		return decimal;
	}

	public void setDecimal(BigDecimal decimal) {
		this.decimal = decimal;
	}

	public BigDecimal getCurrency() {
		return currency;
	}

	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
