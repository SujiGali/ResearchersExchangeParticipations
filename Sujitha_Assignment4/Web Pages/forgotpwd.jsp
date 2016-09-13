<%-- 
    Document   : forgotpwd
    Created on : Apr 17, 2016, 1:38:47 PM
    Author     : sujitha
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="header.jsp" %>
<%-- Section to input login details --%>
<br/>
<%-- Code to create login form--%>
<form id="forgot_form" class="form-horizontal" action="UserController?action=forgotpwd" method="post">

    <input type="hidden" name="action" value="forgotpwd">
    <c:if test="${not empty msg}">
        <div class="form-group" style="color:red" >
            <label class="col-sm-4 control-label" >*</label>
            <div class="col-sm-4">
                <c:out value="${msg}"></c:out>
                </div>
            </div>
    </c:if>

    <div class="form-group">
        <label class="col-sm-4 control-label" >Email Address *</label>
        <div class="col-sm-4">
            <input type="email"  class="form-control" name="email" required/>
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
