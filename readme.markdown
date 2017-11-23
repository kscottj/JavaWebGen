# JavaWebGen

## Webform

This classes where inspired by the Python Django Project.  It will create a web form that provide client(JQuery validate) and Server validation. 
In Addition, it can rener itself as valid HTML5 using a bootstrap based sytlesheet by default.

###Java WebController or Servlet code

Example
```
BookForm form= new BookForm(req);
Book databean=getDataBean(req);
form.setData(databean,req);
req.setAttribute(WebConst.FORM, form);
if(form.isValid() ){  //check for valid input
    busRule.save( (Book)form.getData() );
    return ServerAction.updateAction("/admin/Book/list");
}else{
    return ServerAction.viewAction("/admin/BookDetail.jsp");
```

###Simple WebForm Model

This class will provide the following features without adding extra code to your project:
* JavaScript Client validations(using JQuery validate)
* Server Validation (using Apache Commons validate)
* Checks if a field is Required (Client and servers baseed)
* renders a DatePicker(uses bootstrap-datepicker)
* renders a TimePicker(uses bootstrap-timepicker)
* renders Read only fields

```
public class BookForm extends HtmlForm{
/*form fields*/
private HtmlNumberField  bookId= new HtmlNumberField("bookId" ,false);
private HtmlTextField  isbn= new HtmlTextField("isbn" ,false);
private HtmlDateTimeField  createDate= new HtmlDateTimeField("createDate" ,false);
private HtmlDateTimeField  updateDate= new HtmlDateTimeField("updateDate" ,false);
private HtmlTextField  updateBy= new HtmlTextField("updateBy" ,false);

/**constructor that builds form*/
public BookForm(){
    this.addField(bookId);
    this.addField(isbn);
    this.addField(createDate);
    this.addField(updateDate);
    this.addField(updateBy);
}
```

    
###Base custom TAG(WEB-INF/tags/base.tag)

Example Custom TAG(The Java way to setup a page template)
```
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="style" fragment="true" %>
<%@attribute name="script" fragment="true" %>
<%@attribute name="initJSFunction" fragment="true" %>
<%@attribute name="bodyScript" fragment="true" %>
<%@attribute name="title" fragment="true" %>
<html lang="en">
<head>
<!-- Bootstrap -->
<link href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.css'	rel='stylesheet'>
<link href='<c:url value="/static/css/bootstrap-datepicker.min.css"/>'	rel='stylesheet'>
<link href='<c:url value="/static/css/bootstrap-timepicker.min.css"/>' rel='stylesheet'>
<link href='<c:url value="/static/css/webApp.css"/>' rel='stylesheet'>
<script>
function init(){
    <jsp:invoke fragment='initJSFunction'/>
}
</script>
 <script><jsp:invoke fragment='script' /></script>
<title><jsp:invoke fragment='title'/></title> 
</head>
<body onLoad="init()">
<div class='container'>
<div class='col-m'>	
	<div class='webApp'>
 <jsp:doBody/>
 </div>	 
</div>	
</div><!-- /.container --> `
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.js"></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.min.js'></script>
<script src='<c:url value="/static/js/bootstrap-datepicker.min.js"/>'>
</script><script src='<c:url value="/static/js/bootstrap-timepicker.min.js"/>'></script><script>
<jsp:invoke fragment='bodyScript'/> 
</script>
</body>
</html>
```

###basic JSP

Example Form page notice not much to it.  the `${form} will render the form as HTML.

```
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Detail Book</h1>
${form}
</div> 
</jsp:body>
</t:base>
```
 
###Basic DIV TAG using Boostrap CSS

Allows more control.  You provide the `<form>` tag it rendes the fields as bootscap `<div>` tags.
```
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Detail Book</h1>
<form id='dataFormId' name='dataForm' action ='/admin/Book/update' METHOD='post'>
<div class='form-group'>
${form.isbn.divTag}
${form.createDate.divTag}
${form.updateDate.divTag}
${form.updateBy.divTag}
<button id='button.update' class='btn btn-primary  btn-large' name='updateBut' Save</button>
</form>
</form-group>
</jsp:body>
</t:base>
```

###Basic Table Form

Allows more control.  You provide the `<form>` tag and `<table>` tag.  The form will render page with `<tr><td>` tags.
```
<%@taglib prefix="t" tagdir="/WEB-INF/tags/admin/" %> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Detail Book</h1>
<form id='dataFormId' name='dataForm' action ='/admin/Book/update' METHOD='post'>
<table>
${form.isbn.divTag}
${form.createDate.tableTag}
${form.updateDate.tableTag}
${form.updateBy.tableTag}
</table>
<button id='button.update' class='btn btn-primary  btn-large' name='updateBut' >Save</button>
</form></jsp:body>
</t:base>
```

##Code Generator 

Collection of Ant build script that can generate a compled CRUD application including webforms clases based on a Torque XML file.

###Simplistic Config



###XMLHelper

Helps deal with XML DOM API.  IMHO Java should provide somthing like this.

###Simple embeded Web framework

Servlet Filter will route traffic to correct WebController method based on the URI.  Simple and initializes fast.  
I wrote this because Spring MVC takes too long to start in a cloud envirement that has dynamic instances.
