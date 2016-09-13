<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%-- Section to display description --%>
<section class="copyright">
    <c:forEach var="cookies" items="${cookie}">

    <c:if test="${cookies.key == 'Host' || cookies.key == 'Port'}"><strong><c:out value=
        "${cookies.key}"/></strong>:<c:out value="${cookies.value.value}"/></c:if>
        
</c:forEach>
    &copy; Researchers Exchange Participations
</section>
</body>
</html>

