<%--
	Document: aboutl.jsp
	Created On: Feb 4, 2016
	Authors: Deepak Rohan, Abhishek

 --%>
<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<script type="text/javascript" src="js/editstudy.js">
</script>
<%-- Code to display Page Name --%>
<h3 id="page_name">Editing a study</h3>
<%-- Code to go back to Main page  --%>
<a href="UserController?action=home" id="back_to_page">&laquo;Back to the Main Page</a>
<%-- Section to input study details --%>
<section>
    <form class="form-horizontal" enctype="multipart/form-data" action="StudyController?action=update" method="post">
    
    	<div class="form-group">
        <label class="col-sm-4 control-label">Study Name *</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" name="study_name_temp" readonly value="<c:out value="${study.getName()}"></c:out>"/>
            <input id="study_name" type="hidden" class="form-control" name="study_name" value="<c:out value="${study.getName()}"></c:out>"/>
            <input id="study_code" type="hidden" class="form-control" name="study_code" value="<c:out value="${study.getCode()}"></c:out>"/>
            <input id="Numofpartipants" type="hidden" class="form-control" name="Numofparticipants" value="<c:out value="${study.getNumOfParticipants()}"></c:out>" />
            <input id="Status" type="hidden" class="form-control" name="Status" value="<c:out value="${study.getStatus()}"></c:out>" />
         </div>
            </div>
        
        <div class="form-group">
        <label class="col-sm-4 control-label">Question Text *</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="question_text" name="question_text" required value=<c:out value="${study.getQuestion()}"></c:out>
         </div>
            </div>
        
        
        <%-- Img tag is used to import image --%>
        <div class="form-group">
        <label class="col-sm-4 control-label">Image *</label>
        <div class="col-sm-4">
        <img src="<c:out value="${study.getImageURL()}"></c:out> " class="img-responsive" height="50" width="75" alt="Edit"/>
        <input type="file" name="file" accept="image/*">
         </div>
            </div>
        
        
        <div class="form-group">
        <label class="col-sm-4 control-label"># Participants *</label>
         <div class="col-sm-4"> 
             <input type="text" class="form-control" id="participants" name="participants" required value=<c:out value="${study.getRequestedParticipants()}"></c:out>
         </div>
            </div>
        
        <div class="form-group">
        <label class="col-sm-4 control-label"># Answers *</label>
        <div class="col-sm-4">
        <select name="answers" class="form-control" id="study_answers">
            <option value="3" <c:if test="${study.getNumOfAnswers()=='3'}">selected</c:if> >3</option>
            <option value="4" <c:if test="${study.getNumOfAnswers()=='4'}">selected</c:if>>4</option>
            <option value="5" <c:if test="${study.getNumOfAnswers()=='5'}">selected</c:if>>5</option>
        </select> 
         </div>
            </div>
        
        
        <div id="TextBoxContainer1">
            <c:forEach begin="1" end="${study.getNumOfAnswers()}" varStatus="loopCount">
            <div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">Answer&nbsp;<c:out value="${loopCount.count}"></c:out> *</label>
                    <div class="col-sm-4">
                        <input id="Answer<c:out value="${loopCount.count}"></c:out>" name="Answer<c:out value="${loopCount.count}">
                           
                           </c:out>" class="form-control" type="text" required="" value="${study.getAnswersList().get(loopCount.index -1)}">
                    </div>
                </div>
            </div>
        </c:forEach>
    <!--Textboxes will be added here -->
		</div>
       
       
       <div class="form-group">
        <label class="col-sm-4 control-label">Description *</label>
         <div class="col-sm-4"> 
        <textarea name="description" class="form-control" required><c:out value="${study.getDescription()}"></c:out></textarea>
         </div>
            </div>
        
        <div class="form-group">
        <div class="col-sm-offset-5 col-sm-4">
        <button id="upload_button" type="submit"  class="btn btn-primary">Update</button>
         </div>
            </div>
            <br/><br/><br/>
    </form>
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>