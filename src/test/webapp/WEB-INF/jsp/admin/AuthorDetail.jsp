<%@taglib prefix="t" tagdir="/WEB-INF/tags/admin/" %> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="messages" var="msg" />
 <t:base> 
   <jsp:attribute name="script"> 
       function deleteClick(dataForm){
	alert('Are you sure you want to delete this record?');
	dataForm.action='/admin/Author/delete.htm';
	dataForm.submit();
} //end delete function
function updateClick(dataForm){
	dataForm.action='/admin/Author/save.htm';
	dataForm.submit();
} //end update function

  </jsp:attribute>  
  <jsp:attribute name="bodyScript">
     ${form.JQueryScript}
  </jsp:attribute>   
  <jsp:attribute name="title">
     Author  </jsp:attribute>   
  <jsp:body>
  <div class='col-m'>	
     <h1>Detail Author</h1>
      <div class='row form-group'><a href='/admin/index.jsp'>Admin Menu</a>
<a href='/admin/Author/list.htm'>Back to List Menu</a></div>
<form id='dataFormId' name='dataForm' action ='/admin/Author/save.htm' METHOD='post'>
<div class='form-group'>
<div class='col-sm-2'>AuthorId</div><div class='col-sm-10'>${form.authorId.value}</div>
</div>
<input type='hidden' name='authorId' value='${form.authorId.value}'>
${form.firstName.divTag}
${form.lastName.divTag}
${form.csrf.divTag}
<button id='button.update' class='btn btn-primary  btn-large' name='updateBut' onClick='updateClick(dataForm)' ><fmt:message key="dialog.save" bundle="${msg}" /></button>
<button id='button.delete' class='btn btn-warning  btn-large' name='deleteBut' onClick='deleteClick(dataForm)'><fmt:message key="dialog.delete" bundle="${msg}" /></button>
</form>

  </div>  </jsp:body>
</t:base>
<!--END DETAIL Page Generated by GenerateView 4_17 -->
