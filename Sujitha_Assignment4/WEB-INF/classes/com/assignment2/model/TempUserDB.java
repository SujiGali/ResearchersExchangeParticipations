/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.model;

import com.assignment2.beans.User;
import com.assignment2.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author sujitha
 */
public class TempUserDB {
    
    public void addTempUser(String name,String email,String password,String token){
    //myList.add(theUser);
         ConnectionPool pool = ConnectionPool.getInstance();
         Connection connection = pool.getConnection();
         PreparedStatement ps = null;
         //ResultSet rs = null;
         String query = "insert into TempUser(UName,Email,Password,IssueDate,Token) values(?,?,?,NOW(),?)";
            try {
                ps = connection.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password); 
                ps.setString(4, token); 
                ps.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            finally {
            //DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        }
    
    public User getuser(String token){
        User currentUser = new User();
        ConnectionPool pool = ConnectionPool.getInstance();
         Connection connection = pool.getConnection();
         PreparedStatement pst = null; 
        if (connection != null) {
            try {
                String query = "select * from  TempUser where Token=?";
                pst = connection.prepareStatement(query);
                pst.setString(1, token); 
                ResultSet data = pst.executeQuery();
                while(data.next()){
                    currentUser.setEmail(data.getString("Email"));
                    currentUser.setName(data.getString("UName"));
                    currentUser.setPassword(data.getString("Password"));
                }
                //System.out.println("im here");
            } catch (SQLException ex) {
                System.out.println(ex);
             }
        }
        return currentUser;
    }
    
    public void deleteUser(String token){
        
       ConnectionPool pool = ConnectionPool.getInstance();
         Connection connection = pool.getConnection();
         PreparedStatement pst = null; 
        if (connection != null) {
            try {
                String query = "delete from TempUser where Token=?";
                pst = connection.prepareStatement(query);
                pst.setString(1, token); 
                pst.executeUpdate();
                
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    
}
