package org.javaWebGen.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaWebGen.ServerAction;
import org.javaWebGen.WebController;
import org.javaWebGen.data.bean.Book;
import org.javaWebGen.exception.WebAppException;

public class TestAction extends WebController{
	
	public static final String TEST_MSG="testText";
	
	
	public ServerAction json(HttpServletRequest req, HttpServletResponse res) throws WebAppException{
		Book book =new Book();
		return ServerAction.jsonService(book.toJSON() );
	}
	
	public ServerAction xml(HttpServletRequest req, HttpServletResponse res) throws WebAppException{
		Book book =new Book();
		return ServerAction.xmlService(book.toXML() );
	}
	public ServerAction resp(HttpServletRequest req, HttpServletResponse res) throws WebAppException{
		 
		return ServerAction.xmlService(TEST_MSG );
	}
	
	public ServerAction update(HttpServletRequest req, HttpServletResponse res) throws WebAppException{
		 
		return ServerAction.updateAction("/test");
	}

	public ServerAction view(HttpServletRequest req, HttpServletResponse res) throws WebAppException{
		 
		return ServerAction.viewAction("/test.jsp");
	}

}
