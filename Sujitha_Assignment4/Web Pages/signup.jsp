<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%-- Include tag is used to import header page --%>
<%@include file="header.jsp" %>

<%--Code to signup form --%>
<section>
<br/><br/><br/>
        <form class="form-horizontal" action="UserController?action=create" id="signup_page_form" method="post">
        
<!--            <input type="hidden" name="action" value="signup" />-->
<c:if test="${not empty msg}">
                <div class="form-group"style="color:red" >
       		 	<label class="col-sm-4 control-label" >*</label>
        	<div class="col-sm-4">
                    <c:out value="${msg}"></c:out>
        	</div>
       		</div>
            </c:if>

            <div class="form-group">
            <label class="col-sm-4 control-label">Name *</label>
            <div class="col-sm-4">
            <input type="text" class="form-control" name="name" id="name" required value="${name}"/>
            </div>
            </div>
            <div class="form-group">
            <label class="col-sm-4 control-label">Email *</label>
            <div class="col-sm-4">
            <input type="email" class="form-control" name="email" id="email" required value="${email}"/>
            </div>
            </div>
            <div class="form-group">
            <label class="col-sm-4 control-label">Password *</label>
            <div class="col-sm-4">
            <input type="password" class="form-control" name="password" id="password" required/>
            </div>
            </div>
            <div class="form-group">
            <label class="col-sm-4 control-label">Confirm Password *</label>
            <div class="col-sm-4">
            <input type="password" class="form-control" name="confirm_password" id="cpassword" required />
            </div>
			</div>
			<div class="form-group">
    <div class="col-sm-offset-5">
            <input type="submit" id="signup_page_button" value="Create Account" class="btn btn-primary">
            </div>
            </div>
            <br><br/><br/>
        </form>
        </section>
  
<%-- Include tag is used to import footer page --%>
<%@include file="footer.jsp" %>