/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.beans;

/**
 *
 * @author varanasiavinash
 */
public class Mail {
    
    private String receiverName;
    private String message;
    private String receiverEmail;
    private String subject;
    private String mailType;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
    
    public Mail(){
        
    }
 
    
    public Mail(String receiverName, String message, String receiverEmail){
       this.receiverName = receiverName;
       this.message = message;
       this.receiverEmail = receiverEmail;
       
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }
    
    
    

    
}
