/*
    
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.model;

import com.assignment2.beans.Study;
import com.assignment2.util.DBUtil;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author varanasiavinash
 */
public class StudyDB {

    private static String path;

    public StudyDB(String path) {
        this.path = path + "/Study.xml";
    }

    public StudyDB() {
        this.path = "";
    }

    public Study getStudy(String studyCode) throws IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Study study=null;
        ArrayList<String> tempList = new ArrayList();
        String query="SELECT S.StudyID,S.StudyName,S.Description,S.Username,S.DateCreated,S.ReqParticipants,S.ActParticipants,S.SStatus,Q.Question,Q.AnswerType,Q.NOOFANSWERS,Q.Option1,Q.Option2,Q.Option3,Q.Option4,Q.Option5,S.ImageURL,Q.QuestionID FROM Study S,Question Q WHERE S.StudyID=Q.StudyID AND S.StudyID=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, studyCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                study=new Study();
                study.setCode(rs.getString(1));
                study.setName(rs.getString(2));
                study.setDescription(rs.getString(3));
                study.setEmail(rs.getString(4));
                study.setDateCreated(rs.getString(5));
                study.setRequestedParticipants(rs.getString(6));
                study.setNumOfParticipants(rs.getString(7));
                study.setStatus(rs.getString(8));
                study.setQuestion(rs.getString(9));
                study.setAnswerType(rs.getString(10));
                study.setNumOfAnswers(rs.getString(11));
                int NoOfAnswers=Integer.parseInt(rs.getString(11));
                for(int i=12;i<12+NoOfAnswers;i++){
                    tempList.add(rs.getString(i));
                }
                study.setAnswersList(tempList); 
                Blob blob=rs.getBlob(17);
                int blobLength = (int) blob.length();  
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();
                study.setImageURL(blobAsBytes);
                study.setQuestionId(rs.getString(18));
                
                
            }
        }catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

        return study;
           
        
        
    }

    /* public ArrayList<Study> getStudies() throws IOException {
        List<Study> lst = null;
        StudyLists sl = null;
        Study temp =null;
        try {

            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(StudyLists.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            sl = (StudyLists) jaxbUnmarshaller.unmarshal(file);
            //System.out.println(study);
            lst = sl.getStudy();
           
        } catch (JAXBException e) { 
            e.printStackTrace();
        }

        return lst;
    }
     */
     public ArrayList<Study> getParticipatedStudies(String email) throws IOException {
        ArrayList<Study> studies = new ArrayList<Study>();
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Study study=null;
        ArrayList<String> tempList = new ArrayList();
          String query="SELECT A.* FROM(SELECT S.StudyID,S.StudyName,S.Description,S.Username,S.DateCreated,S.ReqParticipants,S.ActParticipants,S.SStatus,Q.Question,Q.AnswerType,Q.NOOFANSWERS,Q.Option1,Q.Option2,Q.Option3,Q.Option4,Q.Option5,S.ImageURL,Q.QuestionID FROM Study S,Question Q WHERE S.StudyID=Q.StudyID AND S.SStatus='Start' AND S.Username<> ?)A WHERE A.QuestionID NOT IN (SELECT QuestionID FROM Answer WHERE UserName<> A.Username)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                study=new Study();
                study.setCode(rs.getString(1));
                study.setName(rs.getString(2));
                study.setDescription(rs.getString(3));
                study.setEmail(rs.getString(4));
                study.setDateCreated(rs.getString(5));
                study.setRequestedParticipants(rs.getString(6));
                study.setNumOfParticipants(rs.getString(7));
                study.setStatus(rs.getString(8));
                study.setQuestion(rs.getString(9));
                study.setAnswerType(rs.getString(10));
                study.setNumOfAnswers(rs.getString(11));
                int NoOfAnswers=Integer.parseInt(rs.getString(11));
                for(int i=12;i<12+NoOfAnswers;i++){
                    tempList.add(rs.getString(i));
                }
                study.setAnswersList(tempList);
                Blob blob=rs.getBlob(17);
                int blobLength = (int) blob.length();  
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();
                study.setImageURL(blobAsBytes);
                study.setQuestionId(rs.getString(18));
                
                studies.add(study);
            }
        }catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

        return studies;
           
        
        
    }


     public int addStudy(Study study,InputStream inputStream) throws IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps1= null;
         String query,query1;
        int count=-1;
        int key=-1;
        
        query= "INSERT INTO Study " + "(StudyName,Description,Username,DateCreated,ImageURL,ReqParticipants,ActParticipants,SStatus) "
                + "VALUES (?,?,?,NOW(),?,?,?,?)";
        query1="INSERT INTO Question " + "(StudyID,Question,AnswerType,NOOFANSWERS,Option1,Option2,Option3,Option4,Option5) " 
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, study.getName());
            ps.setString(2, study.getDescription());
            ps.setString(3, study.getEmail());
            ps.setBlob(4, inputStream);
            ps.setInt(5, Integer.parseInt(study.getRequestedParticipants()));
            ps.setInt(6, Integer.parseInt(study.getNumOfParticipants()));
            ps.setString(7, study.getStatus());
           count= ps.executeUpdate();
           if(count==1){
               count=-1;
               ResultSet genKeys=ps.getGeneratedKeys();
                if(genKeys.next()){
                    key=genKeys.getInt(1);
                }
           
           ps1 = connection.prepareStatement(query1);
           ps1.setString(1, String.valueOf(key));
            ps1.setString(2, study.getQuestion());
            ps1.setString(3, study.getAnswerType());
            ps1.setInt(4,Integer.parseInt(study.getNumOfAnswers() ));
            int noOfAnswers=Integer.parseInt(study.getNumOfAnswers());
            for(int i=1;i<=noOfAnswers;i++){
                    
                 ps1.setString(4+i, study.getAnswersList().get(i-1));
                    
                }
            for(int i=1;i<=5-noOfAnswers;i++){
            ps1.setString(4+noOfAnswers+i,null);
            }
           count= ps1.executeUpdate();
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
     
      public int updateStudy(Study study,InputStream inputStream) throws IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps1= null;
         String query,query1;
        int count=-1;
        int key=-1;
        
        query= "UPDATE Study SET ImageURL=?,ReqParticipants=?,Description=? WHERE StudyID=? AND StudyName=?";
        query1="UPDATE Question SET Question=?,AnswerType=?,NOOFANSWERS=?,Option1=?,Option2=?,Option3=?,Option4=?,Option5=? WHERE StudyID=?";
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query);
            ps.setBlob(1, inputStream);
            ps.setInt(2, Integer.parseInt(study.getRequestedParticipants()));
            ps.setString(3, study.getDescription());
            ps.setString(4, study.getCode());
           
            ps.setString(5, study.getName());
            
            
           // ps.setInt(6, Integer.parseInt(study.getNumOfParticipants()));
            //ps.setString(7, study.getStatus());
           count= ps.executeUpdate();
           if(count==1) {
               count=-1;
//               ResultSet genKeys=ps.getGeneratedKeys();
//                if(genKeys.next()){
//                    key=genKeys.getInt(1);
//                }
           
           ps1 = connection.prepareStatement(query1);
          // ps1.setString(1, String.valueOf(key));
            ps1.setString(1, study.getQuestion());
            ps1.setString(2, study.getAnswerType());
            ps1.setInt(3,Integer.parseInt(study.getNumOfAnswers() ));
            int noOfAnswers=Integer.parseInt(study.getNumOfAnswers());
            for(int i=0;i<noOfAnswers;i++){
                    
                 ps1.setString(4+i, study.getAnswersList().get(i));
                    
                }
            for(int i=0;i<5-noOfAnswers;i++){
            ps1.setString(4+noOfAnswers+i,null);
            }
            ps1.setString(9, study.getCode());
           count= ps1.executeUpdate();
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
     
     
     
     
     
     
     public Study getStudyEmail(String studyCode,String email) throws IOException {
       ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Study study=null;
        ArrayList<String> tempList = new ArrayList();
        String query="SELECT S.StudyID,S.StudyName,S.Description,S.Username,S.DateCreated,S.ReqParticipants,S.ActParticipants,S.SStatus,Q.Question,Q.AnswerType,Q.NOOFANSWERS,Q.Option1,Q.Option2,Q.Option3,Q.Option4,Q.Option5,S.ImageURL,Q.QuestionID FROM Study S,Question Q WHERE S.StudyID=Q.StudyID AND S.Username=? AND S.StudyID=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, studyCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                study=new Study();
                study.setCode(rs.getString(1));
                study.setName(rs.getString(2));
                study.setDescription(rs.getString(3));
                study.setEmail(rs.getString(4));
                study.setDateCreated(rs.getString(5));
                study.setRequestedParticipants(rs.getString(6));
                study.setNumOfParticipants(rs.getString(7));
                study.setStatus(rs.getString(8));
                study.setQuestion(rs.getString(9));
                study.setAnswerType(rs.getString(10));
                study.setNumOfAnswers(rs.getString(11));
                int NoOfAnswers=Integer.parseInt(rs.getString(11));
                for(int i=12;i<12+NoOfAnswers;i++){
                    tempList.add(rs.getString(i));
                }
                study.setAnswersList(tempList);
                Blob blob=rs.getBlob(17);
                int blobLength = (int) blob.length();  
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();
                study.setImageURL(blobAsBytes);
                study.setQuestionId(rs.getString(18));
                
                
            }
        }catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

        return study;
           
        
        
    }
     
     
public ArrayList<Study> getStudiesforAdd(String email) throws IOException {
        ArrayList<Study> studies = new ArrayList<Study>();
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Study study=null;
        ArrayList<String> tempList = new ArrayList();
          String query="SELECT S.StudyID,S.StudyName,S.Description,S.Username,S.DateCreated,S.ReqParticipants,S.ActParticipants,S.SStatus,Q.Question,Q.AnswerType,Q.NOOFANSWERS,Q.Option1,Q.Option2,Q.Option3,Q.Option4,Q.Option5,S.ImageURL,Q.QuestionID FROM Study S,Question Q WHERE S.StudyID=Q.StudyID AND S.Username=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                study=new Study();
                study.setCode(rs.getString(1));
                study.setName(rs.getString(2));
                study.setDescription(rs.getString(3));
                study.setEmail(rs.getString(4));
                study.setDateCreated(rs.getString(5));
                study.setRequestedParticipants(rs.getString(6));
                study.setNumOfParticipants(rs.getString(7));
                study.setStatus(rs.getString(8));
                study.setQuestion(rs.getString(9));
                study.setAnswerType(rs.getString(10));
                study.setNumOfAnswers(rs.getString(11));
                int NoOfAnswers=Integer.parseInt(rs.getString(11));
                for(int i=12;i<12+NoOfAnswers;i++){
                    tempList.add(rs.getString(i));
                }
                study.setAnswersList(tempList);
                Blob blob=rs.getBlob(17);
                int blobLength = (int) blob.length();  
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();
                study.setImageURL(blobAsBytes);
                study.setQuestionId(rs.getString(18));
                
                studies.add(study);
            }
        }catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

        return studies;
           
        
        
    }


   public int updateStudyRecordStatus(String studyCode, String Status) {
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count=-1;
        String query="UPDATE Study SET SStatus=? WHERE StudyID=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Status);
            ps.setString(2,studyCode);
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
