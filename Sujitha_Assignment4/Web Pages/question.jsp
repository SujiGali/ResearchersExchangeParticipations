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
<%-- Code to Display Question--%>
<section class="question_section">
    <h3><span class="label label-default" >Question</span></h3>
    <%-- Img tag to display image--%>
    <img src="<c:out value="${study.getImageURL()}"></c:out>" class="img-responsive" height="250" width="250" alt="<c:out value="${study.getName()}"></c:out>"/>


<%--Code to rating the Question --%>
    <p class="text-left"><c:out value="${study.getQuestion()}"></c:out></p>

        <form action="StudyController?action=answer" method="post">
            <input type="hidden" name="StudyCode" value="<c:out value="${study.getCode()}"></c:out>"> 
        <input type="hidden" name="QuestionId" value="<c:out value="${study.getQuestionId()}"></c:out>" />
        <c:forEach var="answer" items="${study.getAnswersList()}">
            <div class="radio">
                <input type="radio" name="choice" value="<c:out value="${answer}"></c:out>" required>
            <c:out value="${answer}"></c:out>
            </div>
        </c:forEach>  
    
<%-- Code to submit the Rating  --%>
    
         <div class="form-group">
        <div class="col-sm-offset-3 col-sm-4">
        <button type="submit"  class="btn btn-primary">Submit</button>
         </div>
            </div>
            <br/><br/><br/>   
        </form>
        
    
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>