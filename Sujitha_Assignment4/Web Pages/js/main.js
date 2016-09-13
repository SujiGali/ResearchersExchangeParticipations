$(document).ready(function() {
    $('#login_button').on('click',function(e){
       e.preventDefault();
       var email=$('#email').val().trim();
       var password=$('#password').val().trim();
       if(!isEmail(email)){
           $('#email').focus();
           $('#email').addClass('error');
           return false;
       }else if(password===''){
           $('#password').focus();
           $('#password').addClass('error');
           return false;
       }
       else{
           $('#login_form').submit();
       }
    });
    $('#signup_page_button').on('click',function(e){
       e.preventDefault();
       var name=$('#name').val().trim();
       var email=$('#email').val().trim();
       var password=$('#password').val().trim();
       var cpassword=$('#cpassword').val().trim();
       if(name === ''){
           $('#name').focus();
           $('#name').addClass('error');
           return false;
       }else if(!isEmail(email)){
           $('#email').focus();
           $('#email').addClass('error');
           return false;
       }else if(password === ''){
           $('#password').focus();
           $('#password').addClass('error');
           return false;
       }else if(cpassword === ''){
           $('#cpassword').focus();
           $('#cpassword').addClass('error');
           return false;
       }else if(password !== cpassword){
           $('#cpassword').focus();
           $('#cpassword').addClass('error')
           alert('Passwords does not match');
           return false;
       }
       else{
           $('#signup_page_form').submit();
       }
    });
    function isEmail(email){
        return /^([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22))*\x40([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d))*$/.test( email );
    }
    
    $('#browse_button').on('click',function() {
       $('#browse_file').click();
    });
    $('#browse_file').on('change',function(){
       $('#imageNameholder').text($('#browse_file').val());
    });
    $('#submit_button').on('click',function(e) {
       e.preventDefault();
       $('input').removeClass('error');
       if($('#study_name').val()===''){
           $('#study_name').focus();
           $('#study_name').addClass('error');
           return false;
       }else if($('#question_text').val()===''){
           $('#question_text').focus();
           $('#question_text').addClass('error');
           return false;
       }else if($('#browse_file').val()===''){
           $('#browse_button').focus();
           return false;
       }else if($('#participants').val()===''){
           $('#participants').focus();
           $('#participants').addClass('error');
           return false;
       }else if($('#new_study_answers').val()===''){
           $('#new_study_answers').focus();
           return false;
       }else if($('#new_study_answers').val()>0) {
           var noOfAnswers=$('#new_study_answers').val();
           for(var i=1;i<=noOfAnswers;i++){
               if($('#Answer'+i).val()===''){
                   $('#Answer'+i).focus();
                   $('#Answer'+i).addClass('error');
                   break;
                   return false;
               }
           }
           if($('#description').val()===''){
            $('#description').focus();
            $('#description').addClass('error');
            return false;
           }else{
            $('#studies_form').submit();
           }
       }
    });
    
});