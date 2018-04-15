package org.javaWebGen.form.test;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MockFilter implements FilterChain{

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1)
			throws IOException, ServletException {
		//process next filter for testing this is not needed.
		
	}

}
