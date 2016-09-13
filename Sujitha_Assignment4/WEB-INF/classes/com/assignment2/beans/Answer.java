/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.beans;

import java.io.Serializable;


/**
 *
 * @author varanasiavinash
 */


public class Answer implements Serializable {

    private String StudyCode;
    private String Email;
    private String Choice;
    private String SubmissionDate;
    private String QuestionId;
    
    public Answer() {
        StudyCode="";
        QuestionId="";
        Email = "";
        Choice = "";
        SubmissionDate = "";
    }

    public Answer(String StudyCode,String Email,String QuestionID, String Choice, String SubmissionDate) {
        this.StudyCode=StudyCode;
        this.Email = Email;
        this.Choice = Choice;
        this.SubmissionDate = SubmissionDate;
        this.QuestionId=QuestionID;
    }

    public String getStudyCode() {
        return StudyCode;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String QuestionId) {
        this.QuestionId = QuestionId;
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

    public String getChoice() {
        return Choice;
    }

    public void setChoice(String Choice) {
        this.Choice = Choice;
    }

    public String getSubmissionDate() {
        return SubmissionDate;
    }

    public void setSubmissionDate(String SubmissionDate) {
        this.SubmissionDate = SubmissionDate;
    }
    
}