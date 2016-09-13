<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to display items in List --%>
<c:if test="${theUser != null}">
<nav id="menu">
    <ul>
        <li>Coins (<span class="count"><c:out value="${theUser.getCoins()}"></c:out></span>) </li>
        <li>Participants (<span class="count"><c:out value="${theUser.getPostedstudies()}"></c:out></span>) </li>
        <li>Participation (<span class="count"><c:out value="${theUser.getParticipants()}"></c:out></span>) </li>
        <li><br></li>
        <li><a href="UserController?action=home">Home</a></li>
        <li><a href="StudyController?action=participate">Participate</a></li>
        <li><a href="StudyController?action=studies">My Studies</a></li>
        <li><a href="recommend.jsp?user=Hello,Kim">Recommend</a></li>
        <li><a href="contact.jsp?user=Hello,Kim">Contact</a></li>
    </ul>
</nav>            
</c:if>
<c:if test="${theAdmin !=null}">
    <nav id="menu">
    <ul>
        <li><a href="UserController?action=home">Home</a></li>
        <li><a href="StudyController?action=approve">Reported Questions</a></li>
    </ul>
    </nav>
</c:if>
<%-- Section to display studies and participate in that study--%>
<div>
   
     <h3 class="text-left"><span class="label label-default " >Studies</span>
     <span ><a class="label label-default" href="StudyController?action=report">Report History</a></span></h3>
     </div>
    <c:if test="${studies!=null}">
    <%-- Display the studies in the table --%>
    <%-- Clicking on Participate button displays Question.jsp page where 
            you can rate the question--%>
     <div class="table-responsive">
    <table class="table" >
        <%--Column Names --%>
        <tr>
            <th>Study Name</th>
            <th>Image</th>      
            <th>Question</th>
            <th>Action</th>
            <th>Report</th>
        </tr>
        <c:forEach var="study" items="${studies}">
          <tr>
            <%-- First study details --%>
            <td><c:out value="${study.getName()}"></c:out></td>
            <td><img src="<c:out value="${study.getImageURL()}"></c:out>" alt="<c:out value="${study.getName()}"></c:out>"></td>
            <td><c:out value="${study.getQuestion()}"></c:out></td>
            <td>
                <form action="StudyController?action=participate" method="post">
                    <input type="hidden" name="StudyCode" value="<c:out value="${study.getCode()}"></c:out>" />
                    <input type="submit" class="participate_button" value="Participate" />
                </form>
            </td>
            <td>
                <form action="StudyController?action=report" method="post">
                    <input type="hidden" name="StudyCode" value="<c:out value="${study.getCode()}"></c:out>" />
                <input type="hidden" name="QuestionId" value="<c:out value="${study.getQuestionId()}"></c:out>" />
                    <input type="hidden" name="ReporterEmail" value="<c:out value="${theUser.getEmail()}"></c:out>" />
                    <input type="submit" class="participate_button" value="Report" />
                </form>
            </td>  
          </tr> 
        </c:forEach>
    </table>
    </div>
    </c:if>

<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>