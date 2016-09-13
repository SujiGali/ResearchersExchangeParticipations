/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author varanasiavinash
 */


public class Report {
    
    private String StudyCode;
    private String Email;
    private String ReportDate;
    private String ReportedQuestion;
    private String ReportStatus;
    private String QuestionId;
    private int NumberOfParticipants;
    
    public Report(){
        this.StudyCode="";
        this.QuestionId="";
        this.Email="";
        this.ReportDate="";
        this.ReportedQuestion="";
        this.ReportStatus="";
        this.NumberOfParticipants=0;
    }
    
   /* public Report(String StudyCode,String Email,String ReportDate,
            String ReportedQuestion,String ReportStatus){
        this.StudyCode=StudyCode;
        this.Email=Email;
        this.ReportDate=ReportDate;
        this.ReportedQuestion=ReportedQuestion;
        this.ReportStatus=ReportStatus;
    }*/

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String QuestionId) {
        this.QuestionId = QuestionId;
    }

    public int getNumberOfParticipants() {
        return NumberOfParticipants;
    }

    public void setNumberOfParticipants(int NumberOfParticipants) {
        this.NumberOfParticipants = NumberOfParticipants;
    }
    
    

    public String getStudyCode() {
        return StudyCode;
    }

    public void setStudyCode(String StudyCode) {
        this.StudyCode = StudyCode;
    }

    public String getEmail() {
        return Email;
    }

    
    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getReportDate() {
        return ReportDate;
    }

   
    public void setReportDate(String ReportDate) {
        this.ReportDate = ReportDate;
    }

    public String getReportedQuestion() {
        return ReportedQuestion;
    }

   
    public void setReportedQuestion(String ReportedQuestion) {
        this.ReportedQuestion = ReportedQuestion;
    }

    public String getReportStatus() {
        return ReportStatus;
    }

   
    public void setReportStatus(String ReportStatus) {
        this.ReportStatus = ReportStatus;
    }
    
    
    
    
    
}
