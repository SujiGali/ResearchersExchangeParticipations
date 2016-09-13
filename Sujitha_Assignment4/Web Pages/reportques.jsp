<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to go back to Main page  --%>
<br>
<h3><span id="studies">Reported Questions</span></h3><br/>
<a href="UserController?action=home" id="back_to_page">&laquo;Back to the Main Page</a><br/>
<br/><br/><br/>


<!-- TODO: Add more code to get the table here.
  -->
  <c:if test="${not empty msg}">
      <div class="col-sm-8 col-sm-offset-2" style="text-align:center">${msg}</div>
  </c:if>
  <c:if test="${reports != null && not empty reports}">
  <div class="table-responsive">
  <table class="table" >
        <%--Column Names --%>
        <tr>
            <th>Question</th>
            <th>Number of Participants Reported</th>
            <th>Action</th>		
        </tr>
       <c:forEach var="report" items="${reports}">
        <tr>
            <%-- First study details --%>
            <td><c:out value="${report.getReportedQuestion()}"></c:out></td>
            <td><c:out value="${report.getNumberOfParticipants()}"></c:out></td>
            <td>
            <form method="post">
            <input type="hidden" name="StudyCode" value="${report.getStudyCode()}"/>
             <input type="hidden" name="QuestionId" value="${report.getQuestionId()}"/>
            <input type="hidden" name="ReporterEmail" value="${report.getEmail()}"/> 
            <input type="submit" class="btn btn-primary" formaction="StudyController?action=approve"  value="Approve">
            <input type="submit" class="btn btn-primary" formaction="StudyController?action=disapprove"  value="Dispprove">
            </form>
           </td>
            

        </tr>
        </c:forEach>
        </table>
        </div>
        </c:if>
  <c:if test="${reports == null || empty reports}">
      
      <div class="col-sm-8 col-sm-offset-2" style="text-align: center"> No pending reported records </div> </c:if>
  
  
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>