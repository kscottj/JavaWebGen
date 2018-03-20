package org.javaWebGen.form.test;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.mockito.Mockito;

public class MockRequestDispatcher implements RequestDispatcher{

	@SuppressWarnings("unused")
	private String url;

	@Override
	public void forward(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
			HttpServletRequest mockreq = Mockito.mock(HttpServletRequest.class);
			Mockito.when("getRequestURI").thenReturn("/");
			this.url=mockreq.getRequestURI();
		
	}

	@Override
	public void include(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
