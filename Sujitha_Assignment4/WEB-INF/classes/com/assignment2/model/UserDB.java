/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.model;

import com.assignment2.beans.User;
import com.assignment2.util.DBUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author varanasiavinash
 */
public class UserDB {
    
    
    public User Validateuser (User user,String password){
    
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user1=null;
        String query = "SELECT * from User where Username = ? and Password = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                user1=new User();
                user1.setName(rs.getString("Name"));
                user1.setEmail(rs.getString("Username"));
                user1.setPassword(rs.getString("Password"));
                user1.setType(rs.getString("Type"));
                user1.setParticipants(rs.getInt("Participation"));
                user1.setPostedstudies(rs.getInt("Studies"));
                user1.setCoins(rs.getInt("Coins"));               
            }
        }catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

        return user1;
           
        
        
        
    }
    
    public static int updateUser(User user) {
       ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "Update User set Password=?,salt =? where Username=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getSalt());
            ps.setString(3, user.getEmail());
            
            
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
     public static User getUser (String email){
    
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user=null;
        String query = "SELECT * from User where Username = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                user=new User();
                user.setName(rs.getString("Name"));
                user.setEmail(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setType(rs.getString("Type"));
                user.setParticipants(rs.getInt("Participation"));
                user.setPostedstudies(rs.getInt("Studies"));
                user.setCoins(rs.getInt("Coins"));               
            }
        }catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

        return user;
           
    }
     public void addUser(User user, String saltvalue) throws IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO User "
                + "(Username, Password, Type, Studies, Participation, Coins, Name, salt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getType());
            ps.setInt(4, user.getPostedstudies());
            ps.setInt(5, user.getParticipants());
            ps.setInt(6, user.getCoins());
            ps.setString(7, user.getName());
            ps.setString(8, saltvalue);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e);
            
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }    }
     
     
     public static int forgotpassword(String emailid, String token) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO forgotpwd (Email,Token,datecreated)"
                + "VALUES (?, ?, NOW())";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, emailid);
            ps.setString(2, token);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }  
    public static int getForgotPasswordRecord(String email,String token) {
   ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count =0;
        String query = "SELECT ifnull (count(*),0) FROM forgotpwd WHERE Email=? AND Token=? AND datecreated =(SELECT MAX(datecreated) FROM forgotpwd WHERE Email=?)";
        try {
            ps = connection.prepareStatement(query);
             ps.setString(1, email);
            ps.setString(2, token);
            ps.setString(3, email);
            rs = ps.executeQuery();
           // User user = null;
            if (rs.next()) {
               count=rs.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
     
     
//     
//     public void userDetails(String email,String token){
//        ConnectionPool pool = ConnectionPool.getInstance();
//         Connection connection = pool.getConnection();
//         PreparedStatement ps = null;     
//         String query = "insert into forgotpwd(Email,Token,Expirydate) values(?,?,DATE_ADD(CURDATE(), INTERVAL 10 DAY))";
//        try {
//               
//                ps = connection.prepareStatement(query);
//                ps.setString(1, email);
//                ps.setString(2, token);
//                //pst.setString(3, password); 
//               // pst.setString(4, token); 
//                ps.executeUpdate();
//                //System.out.println("im here");
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            finally {
//            //DBUtil.closeResultSet(rs);
//            DBUtil.closePreparedStatement(ps);
//            pool.freeConnection(connection);
//        }
//        } 
//    
//    public String retrieveUser(String token){
//        ConnectionPool pool = ConnectionPool.getInstance();
//         Connection connection = pool.getConnection();
//         PreparedStatement pst = null; 
//        String email="token not found";
//        java.util.Date datetoday = new java.util.Date();
//        java.util.Date dateexpire = new java.util.Date();
//        if (connection != null) {
//            try {
//                String query = "select Email,Expirydate from forgotpwd where Token=?";
//                pst = connection.prepareStatement(query);
//                pst.setString(1, token);
//                ResultSet r = pst.executeQuery();
//                if(r.next()){
//                    email=r.getString("Email");
//                    dateexpire = r.getDate("Expirydate");
//                }else{
//                    return email;
//                }
//
//                if(datetoday.before(dateexpire)){
//                    return email;
//                }else{
//                    return "token expired";
//                }
//                    
//                
//            } catch (SQLException ex) {
//            System.out.println(ex);
//            }
//                
//        }
//        return email;
//    }
//    
//    public void deleteForgotUser(String token){
//        
//        ConnectionPool pool = ConnectionPool.getInstance();
//         Connection connection = pool.getConnection();
//         PreparedStatement pst = null; 
//        if (connection != null) {
//            try {
//                String query = "delete from forgotpwd where Token=?";
//                pst = connection.prepareStatement(query);
//                pst.setString(1, token); 
//                pst.executeUpdate();
//                
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
//    }
//    
//     public void updatePassword(String email,String password){
//        
//        ConnectionPool pool = ConnectionPool.getInstance();
//         Connection connection = pool.getConnection();
//         PreparedStatement pst = null; 
//        if (connection != null) {
//            try {
//                String query = "update User set Password=? where Email=?";
//                pst = connection.prepareStatement(query);
//                pst.setString(1, password); 
//                pst.setString(2, email);
//                pst.executeUpdate();
//                
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
//    }
      
      public String getUserSaltValue(String email){
        
        String saltvalue="";
        ConnectionPool pool = ConnectionPool.getInstance();
         Connection connection = pool.getConnection();
         PreparedStatement ps = null; 
         ResultSet rs = null;
         String query = "select salt from User where Username=?";
           try {
                
                ps = connection.prepareStatement(query);
                ps.setString(1, email);
               rs = ps.executeQuery();
                if (rs.next()) {
                    saltvalue = rs.getString("salt");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return saltvalue;
}
   
}
