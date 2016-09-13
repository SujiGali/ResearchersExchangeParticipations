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

<%-- Section tag is used to write description  --%>
<section class="main">
    <h3>About us</h3>
    <p>Researchers Exchange Participations is a platform for researchers 
        to exchange participations</p>
    <p>The aim of this platform is to encourage researchers participate in each others
        user studies. Moreover, recruiting serious participants has been always a burden on
        a researcher's shoulder, thus, this platform gives researchers the opportunity
        to do that effectively and in a suitable time.</p>
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>