/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.beans;

import java.io.Serializable;
import java.util.ArrayList;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

/**
 *
 * @author varanasiavinash
 */

public class Study implements Serializable {
    
    private String name;
    private String code;
    private String dateCreated;
    private String email;
    private String question;
    private byte[] imageURL;
    private String requestedParticipants;
    private String numOfParticipants;
    private String description;
    private String status;
    private String answerType;
    private String NumOfAnswers;
    private ArrayList<String> answersList;
    private String questionId;

    public String getQuestionId() {
        return questionId;
    }

   public String getImageURL(){
        StringBuilder stb = new StringBuilder();
        stb.append("data:image/jpeg;base64,");
        stb.append(StringUtils.newStringUtf8(Base64.encodeBase64(imageURL, false)));
        return stb.toString();
    }
    
    public void setImageURL(byte[] ImageURL) {
        this.imageURL = ImageURL;
    }
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getNumOfAnswers() {
        return NumOfAnswers;
    }

    public void setNumOfAnswers(String NumOfAnswers) {
        this.NumOfAnswers = NumOfAnswers;
    }

    public ArrayList<String> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(ArrayList<String> answersList) {
        this.answersList = answersList;
    }
    
public Study(){

        name = "";
        code = "";
        dateCreated = "";
        email="";
        question="";
        imageURL=null;
        requestedParticipants="";
        numOfParticipants="";
        description="";
        status="";
        answerType="";
        NumOfAnswers="";
        answersList=null;
}
public Study(String StudyName, String StudyCode, String DateCreated,String Email,
            String Question,String Requestedparticipants,String Numofparticipants,String Description,
            String Status,String AnswerType,String NoOfAnswers,ArrayList<String> Answers) {
        this.name = StudyName;
        this.code = StudyCode;
        this.dateCreated = DateCreated;
        this.email=Email;
        this.question=Question;
        this.requestedParticipants=Requestedparticipants;
        this.numOfParticipants=Numofparticipants;
        this.description=Description;
        this.status=Status;
        this.answerType=AnswerType;
        this.NumOfAnswers=NoOfAnswers;
        this.answersList=Answers;
    }




    public String getName() {
        return name;
    }
  
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
   
    public void setCode(String code) {
        this.code = code;
    }
   
    public String getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getEmail() {
        return email;
    }
   
    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    

//    public String getImageURL() {
//        return "upload/"+code+".jpg";
//    }

    public String getRequestedParticipants() {
        return requestedParticipants;
    }
    
    public void setRequestedParticipants(String requestedParticipants) {
        this.requestedParticipants = requestedParticipants;
    }

    public String getNumOfParticipants() {
        return numOfParticipants;
    }
   
    public void setNumOfParticipants(String numOfParticipants) {
        this.numOfParticipants = numOfParticipants;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }
  
    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnswerType() {
        return answerType;
    }
    
    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }
    public Double getAverage() {
        return 0.0;
    }

    public Double getMinimum() {
        return 0.0;
    }
    public Double getMaximum() {
        return 0.0;
    }
    public Double getSD() {
        return 0.0;
    }
    
}
