/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.model;

import com.assignment2.beans.Report;
import com.assignment2.util.DBUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author varanasiavinash
 */
public class ReportDB {
    
    
    public int addReport(Report report) throws IOException {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps1= null;
        boolean isReported = false;
         String query,query1;
        int count=-1;
        ResultSet rs = null;
        query= "SELECT QuestionID,StudyID,Date,Status,Username FROM Reported WHERE QuestionID=? AND StudyID=? AND Username=?";
        query1="INSERT INTO Reported(QuestionID,StudyID,Date,Status,USERNAME) VALUES (?,?,NOW(),?,?)";
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query);
            ps.setString(1, report.getQuestionId());
            ps.setString(2, report.getStudyCode()); 
            ps.setString(3, report.getEmail());
            
           rs = ps.executeQuery();
           if (rs.next()) {
                isReported = true;
            }
            if (!isReported) {
            ps1 = connection.prepareStatement(query1);
           ps1.setString(1, report.getQuestionId());
            ps1.setString(2, report.getStudyCode());
            ps1.setString(3, report.getReportStatus());
            ps1.setString(4,report.getEmail());
            count= ps1.executeUpdate();
            }else{
                count=0;
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
            pool.freeConnection(connection);
        }
        return count;
    }
    
    public ArrayList<Report> getReportedQues(String status) {
        ArrayList<Report> reports = new ArrayList<Report>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Report report=null;
        String query="SELECT Q.Question,COUNT(R.USERNAME) NumParticipants,R.StudyID,Q.QuestionID FROM Reported R,Question Q WHERE R.StudyID=Q.StudyID AND R.QuestionID=Q.QuestionID AND R.Status=? GROUP BY Q.Question,R.StudyID,Q.QuestionID";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, status);
            rs = ps.executeQuery();
            while (rs.next()) {
                report = new Report();
                report.setReportedQuestion(rs.getString(1));
                report.setNumberOfParticipants(rs.getInt(2));
                report.setStudyCode(rs.getString(3));
                report.setQuestionId(rs.getString(4));
                reports.add(report);
            }
        }catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

        return reports;     
              
    }
    
    
    
    
     
    
   public ArrayList<Report> getReports(String eMail) {
      ArrayList<Report> reports = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Report report=null;
        String query="SELECT R.Date,Q.Question,R.Status FROM Reported R,Question Q WHERE R.QuestionID=Q.QuestionID AND R.StudyID=Q.StudyID AND R.USERNAME=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, eMail);
            rs = ps.executeQuery();
            while (rs.next()) {
                report = new Report();
                report.setReportDate(rs.getString(1));
          
                report.setReportedQuestion(rs.getString(2));
                report.setReportStatus(rs.getString(3));
                reports.add(report);
            }
        }catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

        return reports;     
              
    }

    public int updateReportRecord(String studyCode, String QuestionId, String Status) {
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count=-1;
        String query="UPDATE Reported SET Status=? WHERE StudyID=? AND QuestionID=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Status);
            ps.setString(2,studyCode);
            ps.setString(3,QuestionId);
            count=ps.executeUpdate();
            
        }catch (SQLException e) {
            System.out.println(e);
            count=-1;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return count;

        
    }
    }
    
    
    
    
    
    
    
    
    
    
    
    

