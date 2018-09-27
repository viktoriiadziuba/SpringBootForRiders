$(document).ready(function(){
    let token = window.localStorage.getItem('auth_token');
    if(token) {
        $('#loginButton').hide();
         $('#log-out').on('click',function(){
              logout();
            });

    } else{
        $('#log-out-menu').hide();
    }
});


 function logout(){
         window.localStorage.removeItem('auth_token' );
             window.localStorage.removeItem('auth_role' );
             $(location).attr('href' , 'login.html')

       }