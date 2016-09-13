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



public class User implements Serializable{
    
    private String name;
    private String email;
    private String type;
    private int coins;
    private int participants;
    private int postedstudies;
    private String password;
    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
     public User(){  
         
         
        name="";
        email="";
        type="";
        coins=0;
        participants=0;
        postedstudies=0;
              
            
    }

    public User(String name, String email, String type, int coins, int participants, int postedstudies) {
        this.name = name;
        this.email = email;
        this.type = type;
        this.coins = coins;
        this.participants = participants;
        this.postedstudies = postedstudies;
    }
    
     

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getPostedstudies() {
        return postedstudies;
    }

    public void setPostedstudies(int postedstudies) {
        this.postedstudies = postedstudies;
    }
    
    
   
    
}
