<%-- 
    Document   : resetpwd
    Created on : Apr 17, 2016, 2:13:46 PM
    Author     : apoorva
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="header.jsp" %>
<%-- Section to input login details --%>
<br/>
    <%-- Code to create login form--%>
    <form id="resetpwd_form" class="form-horizontal" action="UserController?action=newpassword" method="post">
    
        <input type="hidden" name="action" value="newpassword">
        <c:if test="${not empty msg}">
                <div class="form-group" style="color:red" >
       		 	 <label class="col-sm-4 control-label" >*</label>
        	<div class="col-sm-4">
                    <c:out value="${msg}"></c:out>
        	</div>
       		</div>
            </c:if>
        <div class="form-group">
        	<label class="col-sm-4 control-label" >Email</label>
        <div class="col-sm-4">
        <input type="email"  class="form-control" name="email" required value="<c:out value="${email}"></c:out>" readonly />
        </div>
        </div>
        
        		<div class="form-group">
        	<label class="col-sm-4 control-label" >New Password *</label>
        <div class="col-sm-4">
        	<input class="form-control" type="password" name="password" required/>
        </div>
        </div>
     	<div class="form-group">
        	<label class="col-sm-4 control-label" >Confirm Password *</label>
        <div class="col-sm-4">
        	<input class="form-control" type="password" name="cpassword" required/>
        </div>
        </div>
         <div class="form-group">
    	<div class="col-sm-offset-4 col-sm-10">
        <input type="submit" value="Submit" class="btn btn-primary" >
		</div>
		</div>
    </form>
<br/>
<br/>
<br/>

    <%-- Include tag is used to import footer page --%>
<%@include file="footer.jsp" %>
