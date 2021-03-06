/*
Copyright (c) 2012-2017 Kevin Scott All rights  reserved.
 Permission is hereby granted, free of charge, to any person obtaining a copy of 
 this software and associated documentation files (the "Software"), to deal in 
 the Software without restriction, including without limitation the rights to 
 use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do 
 so.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 SOFTWARE.
 */ 
/* data Acees Object talks to DB */
package org.javaWebGen.controller;
import javax.servlet.http.*;
import java.util.*;
import org.javaWebGen.data.bean.*;
import org.javaWebGen.WebController;
import org.javaWebGen.util.HtmlUtil;
import org.javaWebGen.util.StringUtil;
import org.javaWebGen.exception.*;
import org.javaWebGen.model.*;
import org.javaWebGen.config.ConfigConst;
import org.javaWebGen.config.WebConst;
import org.javaWebGen.JavaWebGenContext;
import org.javaWebGen.webform.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
/******************************************************************************
* WARNING this class is generated by GenerateSpringController v2_022 based on Database schema     
* This class should not be modified!
It will be regenerated by the code generator * when the database schema is modified
* If you need to change the this code you should override main class with what you do not need.
*******************************************************************************/
public abstract class BookActionImpl extends WebController { 
@SuppressWarnings("unused")
private static final Logger log=LoggerFactory.getLogger(BookActionImpl.class);//begin private Vars
	
/** model for this object **/;
	private BookModel model= null;

//begin update(store)

//begin delete(store)

	/***************************************************
	*Warning Generated method updates the database with a Databean 
	*@param bean data bound JavaBean
	*@see org.javaWebGen.data.DataBean
	*
	******************************************************/
	protected void delete(Book bean) throws WebAppException{
		getModel().remove(bean);
	} //end delete

//begin listAll)
//${javaWebGen.listAll}
//begin getDataBean
	/************************************
	*fills in a databean based on data in a request
	************************************/
	public static Book getDataBean(HttpServletRequest req) throws WebAppException{
			Book dataBean=new Book();
		try{
			dataBean.setBookId(HtmlUtil.stripTags(req.getParameter("BookId") ) );
			dataBean.setTitle(HtmlUtil.stripTags(req.getParameter("Title") ) );
			dataBean.setIsbn(HtmlUtil.stripTags(req.getParameter("Isbn") ) );
			dataBean.setPublisherId(HtmlUtil.stripTags(req.getParameter("PublisherId") ) );
			dataBean.setAuthorId(HtmlUtil.stripTags(req.getParameter("AuthorId") ) );
			dataBean.setCreateDate(StringUtil.convertToDate(req.getParameter("CreateDate") ) );
		}catch(Exception e){
			throw new WebAppException(WebAppException.APP_ERROR,e);
}		return dataBean;
	}//end getDataBean

	/************************************
	*Strips tags from strings
	************************************/
	public static Book cleanDataBean(Book dataBean ) {
		dataBean.setTitle(HtmlUtil.stripTags(dataBean.getTitle() ) );
		dataBean.setIsbn(HtmlUtil.stripTags(dataBean.getIsbn() ) );
		return dataBean;
	}//end cleanDataBean


	/************************************
	*Generated method
	*get the correct model class
	*@return model class
	************************************/
	protected BookModel getModel() throws WebAppException{
		return JavaWebGenContext.getModel().getBookModel();
	}

 //end getModel
//begin action
	/**
	*
	*/
	@RequestMapping("/Book/list.htm")
	public String list(Model uiModel) throws WebAppException {
		List<Book> list =getModel().list();
		uiModel.addAttribute(WebConst.DATA_BEAN_LIST, list);
		return "/admin/BookList";
} 
	/**show detail
	*@param result spring result
	*@param uiModel ui model
	* @param req http request
	* @return next action
	* @throws WebAppException web error
	*/
	@RequestMapping("/Book/detail.htm")
	public String detail(@ModelAttribute("book") Book book, BindingResult result, Model uiModel, final HttpServletRequest req ) throws WebAppException {
		Book dataBean =getModel().getById(book.getBookId());
		BookForm form = new BookForm(req);
		form.setData(dataBean);
		uiModel.addAttribute(WebConst.FORM_BEAN,dataBean ); 
		uiModel.addAttribute(WebConst.FORM,form ); 
		return "/admin/BookDetail";
	}
	/**create
	* @param result spring result
	* @param uiModel ui model
	* @param req http request
	* @return next action
	* @throws WebAppException web error
	*/
	@RequestMapping("/Book/create.htm")
	public String create(@ModelAttribute("book") Book book, BindingResult result, Model uiModel, final HttpServletRequest req ) throws WebAppException {
		Long id =this.getModel().create(book);
		Book dataBean =getModel().getById(id);
		BookForm form = new BookForm(req);
		form.setData(dataBean,req);
		uiModel.addAttribute(WebConst.FORM_BEAN,dataBean ); 
		uiModel.addAttribute(WebConst.FORM,form ); 
			return "/admin/BookCreate";
	}
	/**create new
	* @param result spring result
	* @param uiModel ui model
	* @param req http request
	* @return next action
	* @throws WebAppException web error
	*/
	@RequestMapping("/Book/newEntity.htm")
	public String newEntity(@ModelAttribute("book") Book book, BindingResult result, Model uiModel, final HttpServletRequest req ) throws WebAppException {
				return "admin/BookCreate";
	}
	/** save Form
	* @param result spring result
	* @param uiModel ui model
	* @param req http request
	* @return next action
	* @throws WebAppException web error
	*/
	@RequestMapping("/Book/save.htm")
	public String save(@ModelAttribute("book") Book book, BindingResult result, Model uiModel, final HttpServletRequest req ) throws WebAppException {
		Book dataBean=book;
		BookForm form = new BookForm(req);
		uiModel.addAttribute(WebConst.FORM_BEAN,dataBean ); 
		uiModel.addAttribute(WebConst.FORM,form ); 
		form.setData(dataBean,req);
		if(form.isValid() ){
			this.getModel().save(dataBean);
			return "redirect:/admin/Book/list.htm";
		}else{
			log.warn("invalid form redisplay");
			return "/admin/BookDetail";
 		}
	}
/**delete
	* @param result spring result
	* @param uiModel ui model
	* @param req http request
	* @return next action
	* @throws WebAppException web error
	*/
	@RequestMapping("/Book/delete.htm")
	public String delete(@ModelAttribute("book") Book book, BindingResult result, Model uiModel, final HttpServletRequest req ) throws WebAppException {
		Book dataBean= book;
		this.getModel().remove(dataBean);
		return "redirect:/admin/Book/list.htm";
	}

//end action
}//end impl class
