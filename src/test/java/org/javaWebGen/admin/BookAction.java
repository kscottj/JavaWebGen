/*
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 SOFTWARE.
  */
/* */
package org.javaWebGen.admin;

import java.util.*;
import javax.annotation.Generated;
import org.javaWebGen.ServerAction;
import org.javaWebGen.data.bean.*;
import org.javaWebGen.webform.*;
import org.javaWebGen.config.*;
import org.javaWebGen.exception.*;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/******************************************************************************
* This class is generated by GenerateController v4_07 based on Database schema     
* This class <b>should</b> be modified.   This class will <b>NOT</b> get
* regenerated and is just generated as a place holder.
* @author Kevin Scott                                                        
* @version $Revision: 1.00 $                                               
*******************************************************************************/
@Generated(value = { "org.javaWebGen.generator.GenerateController" })
public class BookAction extends BookActionImpl { 
@SuppressWarnings("unused")
 	private final Logger log=LoggerFactory.getLogger(BookActionImpl.class);//begin exec
	/*****************************************************************
	* retrieves all data from a table and displays it using a JSP 
	*
	*@return page(controller) or URI to jump to
	*********************************************************************/
	public ServerAction list(HttpServletRequest req, HttpServletResponse res) throws WebAppException{
			List<Book> list =getModel().list();
					req.setAttribute(WebConst.DATA_BEAN_LIST,list);
	return ServerAction.viewAction("/admin/BookList.jsp");
	}
			//database table has primary KEY in it
	/******************************************************************************
*	 retrieves all data from a datastore forwards to a view page to display it 
*	
*	@return page(controller) or URI to jump to
*	******************************************************************************/
	public ServerAction detail(HttpServletRequest req, HttpServletResponse res) throws WebAppException{
			Book dataBean= getModel().getByIdParm(req.getParameter("bookId") );
			BookForm  form= new BookForm(req);
			form.setData(dataBean,req);
			req.setAttribute(WebConst.FORM,form ) ; 
			return ServerAction.viewAction("/admin/BookDetail.jsp");
		}
	/*******************************************************************************
	* Generated method  
	* retrieves all data from a datastore forwards to a view page to display it 
	********************************************************************************
	*@return page(controller) or URI to jump to
	*/
	public ServerAction update(HttpServletRequest req, HttpServletResponse res) throws WebAppException{
			BookForm form= new BookForm(req);
		Book databean=getDataBean(req);
		form.setData(databean,req);
		req.setAttribute(WebConst.FORM, form);
		if(form.isValid() ){
			getModel().save( (Book)form.getData() );
			return ServerAction.updateAction("/admin/Book/list");
		}else{
			return ServerAction.viewAction("/admin/BookDetail.jsp");
		}
	}
	/**********************************************************************************
	* Retrieves all data from a datastore forwards to a view page to display it 
	*
	*@return page(controller) or URI to jump to
	************************************************************************************/
	public ServerAction delete(HttpServletRequest req, HttpServletResponse res) throws WebAppException{
		Book databean =getDataBean(req);
		getModel().remove(databean);
		return ServerAction.updateAction("/admin/Book/list");
	}
	/***************************************************************************************************
	* Creates a new record 
	*
	*@return page(controller) or URI to jump to
	****************************************************************************************************/
	public ServerAction create(HttpServletRequest req, HttpServletResponse res) throws WebAppException{
		BookForm form = new BookForm(req);
		form.setData(new Book(), req);
		if( form.isValid() ){;
			getModel().create( (Book) form.getData() );
			return ServerAction.updateAction("/admin/Book/list");
		}else{
			req.setAttribute(WebConst.FORM,form);
			return ServerAction.viewAction("/admin/BookCreate.jsp");
		}
	}//end create
	/**********************************************************************************************
	* Generated method  
	* displays the CreateForm 
	*
	*@return page(controller) or URI to jump to
	***********************************************************************************************/
	public ServerAction add(HttpServletRequest req, HttpServletResponse res) throws WebAppException{
		BookForm form = new BookForm(req);
		form.setData(req);
		form.setAction("/admin/Book/create");
		req.setAttribute(WebConst.FORM, form);
	return ServerAction.viewAction("/admin/BookCreate.jsp");
	}
//end exec

/***************************************************
* Generated JSON web service results  
* If table had a primary key supplied in database table
* GENERATE list, and findById commands
*
*@return result JSON text
*@Exception ServletException throw a http500 error to user
******************************************************/
	public String doJSON(HttpServletRequest req) throws ServletException{return "";}

//end doJSON

/***************************************************
* This method is experimental!
* Generated SOAP web service results  
* If table had a primary key supplied in database table
* GENERATE list, and findById commands
*
*@return result SOAP text
*@Exception ServletException throw a http500 error to user
******************************************************/
	public String doSOAP(HttpServletRequest req) throws ServletException{return "";}

//end doSOAP
}//
