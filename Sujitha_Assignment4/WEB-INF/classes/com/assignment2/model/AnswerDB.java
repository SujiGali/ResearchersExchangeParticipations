/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.model;

import com.assignment2.beans.Answer;
import com.assignment2.util.DBUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author varanasiavinash
 */
public class AnswerDB {
     
    
    public int addAnswer(Answer answer) throws IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps1= null;
         PreparedStatement ps2= null;
         String query,query1,query2;
        int count=-1;       
        query= "INSERT INTO Answer(StudyID,QuestionID,UserName,Choice,DateSubmitted) VALUES (?,?,?,?,NOW())";
        query1="UPDATE User SET Participation=Participation+1,Coins=Coins+1 WHERE Username=?";
        query2="UPDATE Study SET ActParticipants=ActParticipants+1 WHERE StudyID=?";
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query);
            ps.setString(1, answer.getStudyCode());
            ps.setString(2, answer.getQuestionId());
            ps.setString(3, answer.getEmail());
            ps.setString(4, answer.getChoice());
            
           count= ps.executeUpdate();
           if(count==1){
               count=-1;          
           ps1 = connection.prepareStatement(query1);
           ps1.setString(1, answer.getEmail());
           count= ps1.executeUpdate();
           }
           if(count==1){
               count=-1;
             ps2 = connection.prepareStatement(query2);
           ps2.setString(1, answer.getStudyCode());
            
           count= ps2.executeUpdate();
           }
            
        } catch (SQLException e) {
            System.out.println(e);
            
        } finally {
            try {
                if(count==1)
                    connection.commit();
                else
                    connection.rollback();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            DBUtil.closePreparedStatement(ps);
            DBUtil.closePreparedStatement(ps1);
            DBUtil.closePreparedStatement(ps2);
            pool.freeConnection(connection);
        }
        return count;

    }

    
    
    
    
    
    
}
