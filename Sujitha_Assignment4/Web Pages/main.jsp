<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%-- Code to display items in List --%>
<nav id="menu">
<c:if test="${theUser != null}">
    <ul>
        <li>Coins (<span class="count"><c:out value="${theUser.getCoins()}"></c:out></span>) </li>
        <li>Participants (<span class="count"><c:out value="${theUser.getPostedstudies()}"></c:out></span>) </li>
        <li>Participation (<span class="count"><c:out value="${theUser.getParticipants()}"></c:out></span>) </li>
        <li><br></li>
        <li><a href="UserController?action=home">Home</a></li>
        <li><a href="StudyController?action=participate">Participate</a></li>
        <li><a href="StudyController?action=studies">My Studies</a></li>
        <li><a href="recommend.jsp?action=recommend">Recommend</a></li>
        <li><a href="contact.jsp?action=contact">Contact</a></li>
    </ul>
</c:if>
<c:if test="${theAdmin !=null}">
		<li><a href="UserController?action=home">Home</a></li>
        <li><a href="StudyController?action=approve">Reported Questions</a></li>
</c:if>
</nav>
<%-- Section tag is used to write description  --%>
<section class="main">
    <h3>How it Works</h3>
    <p>This site was built to help researchers conduct their user studies</p>
    <p>1 participation = 1 coin</p>
    <p><b>To participate,</b> go to "Participate" section and choose a study to complete</p>
    <p><b>To get participants,</b> submit your study here to start getting Participations. Inorder to do so, you must have enough coins in Your account</p>

</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>