<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@taglib prefix="t" tagdir="/WEB-INF/tags/admin/" %> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <t:base> 
 <jsp:body>
  <div class='col-m'>	
   </div>
  <div class='col-m'>	
  <h1> Author List</h1>
         <p><a href='/admin/Author/add'>Add New Record</a></p><c:choose>
   <c:when test="${!empty  dataBeanList}">
 <table class='table table-striped table-bordered table-condensed'>
     <tr><th>author_id</th><th>first_name</th><th>Action</th></tr>
  <c:forEach var="bean" items="${dataBeanList}"> 
    <tr><td>${bean.authorId}</td><td>${bean.firstName}</td><td><a href='/admin/Author/detail?authorId=${bean.authorId}'>Edit</a></td></tr>
</c:forEach>
</table>
</c:when>
  <c:otherwise>
    <i>no Data Found</i>
  </c:otherwise>
</c:choose>

  </div> 
 </jsp:body>
 </t:base>
<!--END DETAIL Page Generated by GenerateView 4_17 -->
