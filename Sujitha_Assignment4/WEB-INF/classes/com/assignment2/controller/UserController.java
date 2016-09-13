   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.controller;

import com.assignment2.beans.Mail;
import com.assignment2.beans.User;
import com.assignment2.model.EmailDB;
import com.assignment2.model.TempUserDB;
import com.assignment2.model.UserDB;
import com.assignment2.util.CommonUtil;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author varanasiavinash
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {
    Cookie hostcookie;
    Cookie portcookie;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
        
        
        
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String str = String.valueOf(request.getServerPort());
        hostcookie = new Cookie("Host",request.getServerName());
        portcookie = new Cookie("Port",str);
        
        response.addCookie(hostcookie);
        response.addCookie(portcookie);
        
        
        String action = request.getParameter("action");
        //String path=System.getenv("OPENSHIFT_DATA_DIR");
        //String path = getServletContext().getRealPath("/WEB-INF");
        String url = "/home.jsp";
        HttpSession session = request.getSession();
        if (action != null && action.equals("")){
        url = "/home.jsp";    
        }
        else if (action.equals("login")) {
        UserDB userDB=new UserDB();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String saltvalue= userDB.getUserSaltValue(email);
            try {
                password = hashPassword(password+saltvalue);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        User user=new User();
        user.setEmail(email);
            user=userDB.Validateuser(user,password);
            if(user!=null){
                String userType=user.getType();
                if(userType.equals("Participant")){
                    session.setAttribute("theUser", user);
                    session.setAttribute("coins", user.getCoins());
                    session.setAttribute("NumParticipation", user.getParticipants());
                    url="/main.jsp";
                }
                if(userType.equals("Admin")){
                    session.setAttribute("theAdmin", user);
                    session.setAttribute("coins", user.getCoins());
                    session.setAttribute("NumParticipation", user.getParticipants());
                    url="/admin.jsp";
                }
            }else{
                request.setAttribute("msg","Invalid User Name/Password");
                url="/login.jsp";
            }
    }

        else if(action.equals("create")) {
            String Name=CommonUtil.checkNull(request.getParameter("name"));
            String Email=CommonUtil.checkNull(request.getParameter("email"));
            String password=CommonUtil.checkNull(request.getParameter("password"));
            String confirmPassword=CommonUtil.checkNull(request.getParameter("confirm_password"));
            if(!Email.equals("") && password.equals(confirmPassword)){
                String token = UUID.randomUUID().toString();
                TempUserDB newuser = new TempUserDB();
                newuser.addTempUser(Name,Email,password,token);
                
                String myEmail = "researchparticipant09@gmail.com";
                String subject  = "Activate your account";
                String Password = "poilkjmnb";
                String reqUrl= null;
                reqUrl=request.getRequestURL()+ "?action=activate&token="+token;
                boolean count=EmailDB.sendmail(Email, myEmail, subject, reqUrl, Password);
//                String toName = request.getParameter("study_name");
                            
//                User user=new User();
//                user.setName(Name);
//                user.setEmail(Email);
//                user.setType("Participant");
//                user.setPassword(password);
//                user.setCoins(0);
//                user.setParticipants(0);
//                user.setPostedstudies(0);
//                UserDB userDB=new UserDB();
//                User checkUser=userDB.getUser(Email);
//                if(checkUser == null){
//                    userDB.addUser(user);
//                    session.setAttribute("theUser", user);
//                    session.setAttribute("coins", user.getCoins());
//                    session.setAttribute("NumParticipation", user.getParticipants());
//                    url="/main.jsp";
//                }
//                else{
//                    request.setAttribute("name",Name);
//                    request.setAttribute("email",Email);
//                    request.setAttribute("password",password);
//                    request.setAttribute("confirmPassword",confirmPassword);
//                    if(!Name.equals("") || !Email.equals("") || !password.equals("") || !confirmPassword.equals("") ){
//                        request.setAttribute("msg","User already Registered");
//                    }
//                    url="/signup.jsp";
//                }
                if(count)
                {
                    url="/login.jsp";
                }
            }
            else {
                String message = "Password and confirm password did not match";
                request.setAttribute("message", message);
                url = "/signup.jsp";
            }
//            else{
//                request.setAttribute("name",Name);
//                request.setAttribute("email",Email);
//                request.setAttribute("password",password);
//                request.setAttribute("confirmPassword",confirmPassword);
//                if(!Name.equals("") || !Email.equals("") || !password.equals("") || !confirmPassword.equals("") ){
//                    request.setAttribute("msg","Please fill all the details correctly");
//                }
//                url="/signup.jsp";
//            }
        }
        else if(action.equals("how")){
            if(session.getAttribute("theUser")!=null){
                url="/main.jsp";
            }else if(session.getAttribute("theAdmin")!=null){
                url="/admin.jsp";
            }else{
                url="/how.jsp";
            }
        }else if(action.equals("about")){
            if(session.getAttribute("theUser")!=null || session.getAttribute("theAdmin")!=null){
                url="/aboutl.jsp";
            }else{
                url="/about.jsp";
            }
        }else if(action.equals("home")){
            if(session.getAttribute("theUser")!=null){
                url="/main.jsp";
            }else if(session.getAttribute("theAdmin")!=null){
                url="/admin.jsp";
            }
            else{
                url="/home.jsp";
            }
        }else if(action.equals("main")){
            if(session.getAttribute("theUser")!=null){
                url="/main.jsp";
            }else if(session.getAttribute("theAdmin")!=null){
                url="/admin.jsp";
            }else{
                url="/login.jsp";
            }
        }else if(action.equals("logout")) {
            if(session.getAttribute("theUser")!=null || session.getAttribute("theAdmin")!=null)
                session.invalidate();
            url="/home.jsp";
        } 
        else if(action.equals("recommend")) {
                            String toName = request.getParameter("study_name");
                            String toMail = request.getParameter("friend_email");
                            String message = request.getParameter("message");
                            String subject = "Research Exchange Participations ";
                            Mail mail =  new Mail();
                            mail.setReceiverEmail(toMail);
                            mail.setReceiverName(toName);
                            mail.setMessage(message);
                            mail.setSubject(subject);
                            mail.setMailType("recommend");
                            boolean count = EmailDB.sendMessage(mail);
                            if(count){
                                url = "/confirmr.jsp";
                            }
                            
                        }
        
         else if(action.equals("contact")) {
                            String toName = request.getParameter("study_name");
                            String toMail = request.getParameter("email");
                            String message = request.getParameter("message");
                            String subject = "Research Exchange Participations";
                            Mail mail =  new Mail();
                            mail.setReceiverEmail(toMail);
                            mail.setReceiverName(toName);
                            mail.setMessage(message);
                            mail.setSubject(subject);
                            mail.setMailType("contact");
                            boolean count = EmailDB.sendMessage(mail);
                            if(count){
                                url = "/confirmc.jsp";
                            }
                            
                        }
         else if(action.equals("forgotpwd"))
                {
                    String email= request.getParameter("email");
                    UserDB userdb=new UserDB();
                    User checkUser=userdb.getUser(email);
                    if(checkUser!=null)
                    {
                         String token = UUID.randomUUID().toString();
                        String link = request.getRequestURL().toString() + "?" + "action=resetpassword&token="+ token + "&email=" +email;                       
                        Mail mail = new Mail();
                                mail.setReceiverEmail(email);
                                mail.setMessage(link);
                                mail.setMailType("forgotpassword");
                                mail.setSubject("Forgot password link");
                                boolean isMessageSent=EmailDB.sendMessage(mail);
                                if(isMessageSent)
                                {
                                    request.setAttribute("msg", "Please check your mail for forgot password link.");
                                    url="/login.jsp";
                                }
                                else
                                {
                                    request.setAttribute("msg", "Error in sending mail");
                                    url= "/login.jsp";
                                }
                               // UserDB user=new UserDB();
                                UserDB.forgotpassword(email, token);
                       
                    }
                    else
                    {
                        request.setAttribute("msg","User does not exist with this emailID" );
                    }
                    
                }
                else if(action.equals("resetpassword"))   
                {
                 String email= request.getParameter("email");
                  String token= request.getParameter("token");
                  try {
                UserDB userdb = new UserDB();
                int count=userdb.getForgotPasswordRecord(email,token);
                if(count==1){
                    if ((null != email && null != token) && (!("").equalsIgnoreCase(email) && !("").equalsIgnoreCase(token))) {
                    request.setAttribute("email", email);
                    url = "/resetpwd.jsp";
                } else {
                    request.setAttribute("msg", "The link seems to be invalid. Please try again");
                    url = "/login.jsp";
                }
                }else {
                    request.setAttribute("msg", "The link seems to be invalid. Please try again");
                    url = "/login.jsp";
                }
                
            } catch (Exception e) {
                request.setAttribute("msg", "The link seems to be invalid. Please try again");
                url = "/login.jsp";
                e.printStackTrace();
            }
         
         }      
                
         else if(action.equals("newpassword"))   
                {
                 String email= request.getParameter("email");
                  String password= request.getParameter("password");
                  String confirmpassword= request.getParameter("cpassword");
                  if(password.equals(confirmpassword))
                  {
                  User user=UserDB.getUser(email);
                  String saltvalue = getSalt();
                String hashedPassword="";
                    try {
                        hashedPassword = hashPassword(password+saltvalue);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                user.setPassword(hashedPassword);
                user.setSalt(saltvalue);
                int count=UserDB.updateUser(user);
                
                if(count==1){
                request.setAttribute("msg", "New password updated succesfully. Enter your new credentials");
                url = "/login.jsp";
                }
                
                else{
                request.setAttribute("msg", "Error in updating password");
                url = "/newpassword.jsp";
                }
                  }
                  else{
                      request.setAttribute("msg", "new password and confirm password did not match.Please try again");
                url = "/newpassword.jsp";
                  
                  }
                }
         
         
         else if(action.equals("activate")){
            String token = request.getParameter("token");
            User currentUser = new User();
            UserDB userdb = new UserDB();
            TempUserDB searchUser = new TempUserDB();
            currentUser =  searchUser.getuser(token);
            if (currentUser.getEmail()!=null) {                             
                     
                try {
                    currentUser.setCoins(0);
                    currentUser.setParticipants(0);
                    currentUser.setPostedstudies(0);
                    String saltvalue = getSalt();
                    currentUser.setPassword(hashPassword(currentUser.getPassword()+saltvalue));
                    User user=new User();
                    user.setName(currentUser.getName());
                    user.setEmail(currentUser.getEmail());
                    user.setType("Participant");
                    user.setPassword(currentUser.getPassword());
                    user.setCoins(0);
                    user.setParticipants(0);
                    user.setPostedstudies(0);
                    userdb.addUser(user,saltvalue);
                    searchUser.deleteUser(token);
                    HttpSession thisSession = request.getSession(true);
                    thisSession.setAttribute("theUser", currentUser);
                    RequestDispatcher rd = request.getRequestDispatcher("/main.jsp");
                    rd.forward(request, response);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                }else {
                    request.setAttribute("msg", "Invalid activation link");
                    RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
                    rd.forward(request, response);
                }
}
        
        else{
            url="/home.jsp";   
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);    
    }
    
   protected String getSalt(){
        Random r= new SecureRandom();
        byte[] saltbytes = new byte[32];
        r.nextBytes(saltbytes);
        return Base64.encodeBase64String(saltbytes);    
    }
    
    protected String hashPassword(String pwd) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(pwd.getBytes());
        byte[] mdarray = md.digest();
        StringBuilder sb = new StringBuilder(mdarray.length*2);
        for(byte b:mdarray){
            int v=b & 0xff;
            if(v<16){
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }
    

}
    
