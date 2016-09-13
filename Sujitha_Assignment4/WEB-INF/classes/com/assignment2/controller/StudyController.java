/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.controller;

import com.assignment2.beans.Answer;
import com.assignment2.beans.Report;
import com.assignment2.beans.Study;
import com.assignment2.beans.User;
import com.assignment2.model.AnswerDB;
import com.assignment2.model.ReportDB;
import com.assignment2.model.StudyDB;
//import com.assignment2.model.UserDB;
import com.assignment2.util.CommonUtil;
//import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
//import javax.xml.bind.JAXBException;

/**
 *
 * @author varanasiavinash
 */
@WebServlet(name = "StudyController", urlPatterns = {"/StudyController"})
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50
                 )   // 50MB
public class StudyController extends HttpServlet {
   // private static final String SAVE_DIR = "images";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
        
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
        String action = request.getParameter("action");
        //String path=System.getenv("OPENSHIFT_DATA_DIR");
         //String path = getServletContext().getRealPath("/WEB-INF");
        String url = "";
        HttpSession session = request.getSession();
        if (action != null && action.equals("")) {

            if (session.getAttribute("theUser") != null) {
                url = "/main.jsp";
            } else if (session.getAttribute("theAdmin") != null) {

                url = "/admin.jsp";
            } else {
                url = "/home.jsp";
            }

        } else if (action.equals("participate")) {

            if (session.getAttribute("theUser") != null) {
                StudyDB studyDB = new StudyDB();
                if (request.getParameter("StudyCode") != null) {
                    String studyCode = request.getParameter("StudyCode");
                    Study study = studyDB.getStudy(studyCode);
                    request.setAttribute("study", study);
                    url = "/question.jsp";

                } else {

                   // List<Study> dataList = new ArrayList<>();
                    String email = ((User)session.getAttribute("theUser")).getEmail();
                    ArrayList<Study> studies = studyDB.getParticipatedStudies(email);
                    /*for (Study study : studies) {
                        String status = study.getStatus();
                        if (status.equals("start")) {
                            dataList.add(study);
                        }
                    }*/
                    request.setAttribute("studies", studies);
                    url = "/participate.jsp";

                }
            } else {
                url = "/login.jsp";

            }

        } else if (action.equals("edit")) {

            if (session.getAttribute("theUser") != null) {
                StudyDB studyDB = new StudyDB();

                String studyCode = request.getParameter("StudyCode");
                String email = ((User)session.getAttribute("theUser")).getEmail();
                Study study = studyDB.getStudyEmail(studyCode, email);
                request.setAttribute("study", study);
                url = "/editstudy.jsp";

            } else {
                url = "/login.jsp";

            }

        } else if (action.equals("report")) {

            if (session.getAttribute("theUser") != null) {
                if (request.getParameter("StudyCode") != null) {   
                    if (request.getParameter("ReporterEmail") != null) {
                        String StudyCode = request.getParameter("StudyCode");
                        String ReporterEmail = request.getParameter("ReporterEmail");
                        String QuestionId=request.getParameter("QuestionId");
                       // Date date = new Date();
                        //StudyDB studyDB = new StudyDB();
                        //String CurrentDate = new SimpleDateFormat("dd-MM-YYYY").format(date);
                        /*String ReportedQuestion = studyDB.getStudy(StudyCode).getQuestion();
                        Report report = new Report(StudyCode, ReporterEmail,
                                CurrentDate, ReportedQuestion, "Pending");*/
                        Report report=new Report();
                        report.setStudyCode(StudyCode);
                        report.setQuestionId(QuestionId);
                        report.setEmail(ReporterEmail);
                        report.setReportStatus("Pending");
                        ReportDB reportDB = new ReportDB();
                        int count=reportDB.addReport(report);
                        String msg="";
                        if(count==1){
                            msg="Question Reported";
                        }else if(count==0){
                            msg="Question is already Reported";
                        }else{
                        msg="Error";
                        }
                        request.setAttribute("msg",msg);
                        url="/confirmrep.jsp";

                    } }else {
                        ReportDB reportDB = new ReportDB();
                        String ReporterEmail = ((User)session.getAttribute("theUser")).getEmail();
                        List<Report> report = reportDB.getReports(ReporterEmail);
                        request.setAttribute("reports", report);
                        url = "/reporth.jsp";
                    }

                } else {
                    url = "/login.jsp";
                }
            
        }else if(action.equals("approve")){
            if(session.getAttribute("theAdmin")!= null) {
                if(request.getParameter("StudyCode") != null) {
                    String studyCode=request.getParameter("StudyCode");
                    String QuestionId=request.getParameter("QuestionId");
                    ReportDB reportDB=new ReportDB();
//                    Report report=reportDB.getReport(studyCode);
//                    report.setReportStatus("approve");
                    int count=reportDB.updateReportRecord(studyCode, QuestionId,"approve");
                    String msg="Error";
                    if(count==1){
                        msg="Reported Question is Approved..";
                    }
                    List<Report> reports=reportDB.getReportedQues("Pending");
                    
                    request.setAttribute("reports", reports);
                    
                    request.setAttribute("msg", msg);
                    url="/reportques.jsp";  
                }else{
                url="/login.jsp"; 
            }}
        }else if(action.equals("disapprove")){
            if(session.getAttribute("theAdmin")!= null){
                if(request.getParameter("StudyCode") != null){
                    String studyCode=request.getParameter("StudyCode");
                    String QuestionId=request.getParameter("QuestionId");
                    ReportDB reportDB=new ReportDB();
//                    Report report=reportDB.getReport(studyCode);
//                    report.setReportStatus("approve");
                    int count=reportDB.updateReportRecord(studyCode, QuestionId,"disapprove");
                    String msg="Error";
                    if(count==1){
                        msg="Reported Question is Removed..";
                    }
                    List<Report> reports=reportDB.getReportedQues("Pending");
                    
                    request.setAttribute("reports", reports);
                    
                    request.setAttribute("msg", msg);
                    url="/reportques.jsp";  
                }else{
                url="/login.jsp"; 
            }}
        } else if(action.equals("newStudy")){
            if(session.getAttribute("theUser")!=null){
               url="/newstudy.jsp";
            }else{
                url="/login.jsp";
            }
        }
        
        
        
        
        
        
        
        else if (action.equals("add")) {
            if (session.getAttribute("theUser") != null)  {
                
               // String appPath = request.getServletContext().getRealPath("");
                //String savePath = appPath + File.separator + SAVE_DIR;
                Study study = new Study();
                String StudyName = request.getParameter("study_name");
                System.out.println("session is" +session.getAttribute("theUser"));
                System.out.println("STudy name is" +StudyName);
                //String StudyCode = request.getParameter("study_name");
                Date date=new Date();
                String DateCreated = new SimpleDateFormat("dd-MM-YYYY").format(date);
                String Email = ((User)session.getAttribute("theUser")).getEmail();
                String Question = request.getParameter("question_text");
                String Requestedparticipants = request.getParameter("participant_text");
                String Noofparticipants = "0";
                  String Description = request.getParameter("description");
                String Status = "start";
                String AnswerType = request.getParameter("Answer1");
                try{
                    Integer.parseInt(AnswerType);
                    AnswerType=CommonUtil.Answer_Type_Numeric;
                }
                catch(NumberFormatException e){
                    AnswerType="Text";
                }
                String NoOfAnswers = request.getParameter("answers");
                ArrayList<String> Answers = new ArrayList<>();
                if (AnswerType.equals(CommonUtil.Answer_Type_Numeric)) {
                    for (int i = 1; i <= Integer.parseInt(NoOfAnswers); i++) {
                        Answers.add((request.getParameter("Answers" + i)));
                    }
                } else {
                    for (int i = 1; i <= Integer.parseInt(NoOfAnswers); i++) {
                        Answers.add((request.getParameter("Answers" + i)));
                    }
                }
                study.setName(StudyName);
                //study.setCode(StudyCode);
                study.setDateCreated(DateCreated);
                study.setEmail(Email);
                study.setQuestion(Question);
                study.setRequestedParticipants(Requestedparticipants);
                study.setNumOfParticipants(Noofparticipants);
                study.setDescription(Description);
                study.setStatus(Status);
                study.setAnswerType(AnswerType);
                study.setNumOfAnswers(NoOfAnswers);
                study.setAnswersList(Answers);
                StudyDB studyDB = new StudyDB();
               // Part filePart=request.getPart("file"); 
            
                //filePart.write(StudyCode+".jpg");
                Part filePart=request.getPart("file"); 
                InputStream inputStream = null;
                if (filePart != null) {
                    inputStream = filePart.getInputStream();
                }
                int count=studyDB.addStudy(study,inputStream);
                
                
                //study = null;
                //study = studies.get(Email);
                if(count==1){
                    List<Study> studies = studyDB.getStudiesforAdd(Email);
                    request.setAttribute("studies", studies);
                url = "/studies.jsp";
                }
                
            else {
                url = "/login.jsp";
            }

        } }
        else if (action.equals("update")) {
            if (session.getAttribute("theUser") != null) {
                StudyDB studyDB = new StudyDB();
               // String appPath = request.getServletContext().getRealPath("");
                //String savePath = appPath + File.separator + SAVE_DIR;
                Study study = new Study();
                String StudyName = request.getParameter("study_name");
               String StudyCode = request.getParameter("study_code");
                System.out.println("STudy name is" +StudyName);
                Date date=new Date();
                String DateCreated = new SimpleDateFormat("dd-MM-YYYY").format(date);
                String Email = ((User)session.getAttribute("theUser")).getEmail();
                String Question = request.getParameter("question_text");
                String Requestedparticipants = request.getParameter("participants");
                String Noofparticipants = "0";
                String Description = request.getParameter("description");
                String Status = "start";
                String AnswerType = request.getParameter("Answer1");
                try{
                    Integer.parseInt(AnswerType);
                    AnswerType=CommonUtil.Answer_Type_Numeric;
                }
                catch(NumberFormatException e){
                    AnswerType="Text";
                }
                String NoOfAnswers = request.getParameter("answers");
                ArrayList<String> Answers = new ArrayList<>();
                if (AnswerType.equals(CommonUtil.Answer_Type_Numeric)) {
                    for (int i = 1; i <= Integer.parseInt(NoOfAnswers); i++) {
                        Answers.add((request.getParameter("Answer" + i)));
                    }
                } else {
                    for (int i = 1; i <= Integer.parseInt(NoOfAnswers); i++) {
                        Answers.add((request.getParameter("Answer" + i)));
                    }
                }
                study.setName(StudyName);
                study.setCode(StudyCode);
                study.setDateCreated(DateCreated);
                study.setEmail(Email);
                study.setQuestion(Question);
                study.setRequestedParticipants(Requestedparticipants);
                study.setNumOfParticipants(Noofparticipants);
                study.setDescription(Description);
                study.setStatus(Status);
                study.setAnswerType(AnswerType);
                study.setNumOfAnswers(NoOfAnswers);
                study.setAnswersList(Answers);
                
               // Part filePart=request.getPart("file"); 
            
                //filePart.write(StudyCode+".jpg");
                Part filePart=request.getPart("file"); 
                InputStream inputStream = null;
                if (filePart != null) {
                    inputStream = filePart.getInputStream();
                }
                int count=studyDB.updateStudy(study,inputStream);
                
                
                //study = null;
                //study = studies.get(Email);
                if(count==1){
                    List<Study> studies = studyDB.getStudiesforAdd(Email);
                    request.setAttribute("studies", studies);
                url = "/studies.jsp";
                }
                
            else {
                url = "/login.jsp";
            }

        } 
        } else if (action.equals("start")) {

            if (session.getAttribute("theUser") != null) {
                if (request.getParameter("StudyCode") != null) {
                    String studyCode = request.getParameter("StudyCode");
                    StudyDB studyDB = new StudyDB();
                    
                    
                    studyDB.updateStudyRecordStatus(studyCode, "start");
                    String Email = ((User)session.getAttribute("theUser")).getEmail();
                    List<Study> studies = studyDB.getStudiesforAdd(Email);
                    request.setAttribute("studies", studies);
                    
                    url = "/studies.jsp";

                } else {
                    url = "/login.jsp";

                }

            }

        } else if (action.equals("stop")) {

            if (session.getAttribute("theUser") != null) {
                if (request.getParameter("StudyCode") != null) {
                   String studyCode = request.getParameter("StudyCode");
                    StudyDB studyDB = new StudyDB();
                    
                    
                    int count=studyDB.updateStudyRecordStatus(studyCode, "stop");
                    String Email = ((User)session.getAttribute("theUser")).getEmail();
                    List<Study> studies = studyDB.getStudiesforAdd(Email);
                    request.setAttribute("studies", studies);
                    
                    url = "/studies.jsp";

                } else {
                    url = "/login.jsp";

                }
            }

        }else if(action.equals("reportques")){
            if(session.getAttribute("theAdmin")!=null){
                    ReportDB reportDB=new ReportDB();
                    
                    List<Report> reports=reportDB.getReportedQues("Pending");
                request.setAttribute("reports", reports);
                url="/reportques.jsp";
            }else{
                url="/login.jsp"; 
            }
        }
        else if(action.equals("answer")){
            if(session.getAttribute("theUser")!=null){
                if(request.getParameter("StudyCode")!=null
                        && 
                   request.getParameter("choice")!=null
                  ) {
                    String studyCode=request.getParameter("StudyCode");
                    String QuestionId=request.getParameter("QuestionId");
                    String Email=((User)session.getAttribute("theUser")).getEmail();
                    String choice=request.getParameter("choice");
//                    Date date=new Date();
//                    String SubmissionDate=new SimpleDateFormat("dd-MM-YYYY").format(date);
                    AnswerDB answerDB=new AnswerDB();
                    Answer answer=new Answer();
                    answer.setQuestionId(QuestionId);
                    answer.setStudyCode(studyCode);
                    answer.setEmail(Email);
                    answer.setChoice(choice);
                    //answer.setSubmissionDate(SubmissionDate);
                   
                    answerDB.addAnswer(answer);
                    
//                    UserDB userDB=new UserDB(path);
//                    User user=userDB.getUser(Email);
                    
                    String Coins=session.getAttribute("coins")==null?
                            "0":session.getAttribute("coins").toString();
                  User user=(User)session.getAttribute("theUser");
                    user.setCoins(Integer.parseInt(Coins)+1);
                    String NumParticipation=session.getAttribute("NumParticipation")==null?
                           "0":session.getAttribute("NumParticipation").toString();
                   user.setParticipants(Integer.parseInt(NumParticipation)+1);
                    session.setAttribute("theUser", user);
                    
                    
//                    userDB.updateUser(Email,user);
//                    session.setAttribute("coins", String.valueOf(Integer.parseInt(Coins)+1));
//                   session.setAttribute("NumParticipation", String.valueOf(Integer.parseInt(NumParticipation)+1));
                    StudyDB studyDB=new StudyDB();
                    List<Study> studies=studyDB.getParticipatedStudies(Email);
//                    ArrayList<Study> dataList=new ArrayList<>();
//                    for(Study study:studies){
//                        String status=study.getStatus();
//                        if(status.equals("start")){
//                            dataList.add(study);
//                        }   
                        
                    
                    request.setAttribute("studies", studies);
                }
                url="/participate.jsp";
                
            }else{
                url="/login.jsp"; 
            }
        }
        
        
        else if (action.equals("studies")) {
            if (session.getAttribute("theUser") != null) {
                String Email = ((User)session.getAttribute("theUser")).getEmail();
                StudyDB studyDB = new StudyDB();
                List<Study> studies = studyDB.getStudiesforAdd(Email);
                request.setAttribute("studies", studies);
                url = "/studies.jsp";
            } else {
                url = "/login.jsp";
            }
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response); 
    }
    
     
    }

   

