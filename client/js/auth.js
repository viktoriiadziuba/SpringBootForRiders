
$(document).ready(function(){
    let token = window.localStorage.getItem('auth_token');
    if(token) {
        $.ajaxSetup({
            headers: {
                'Autorization': 'Bearer' + token
            }
        })
    } else  {
        console.log('Token not exists');
         $(location).attr('href', 'login.html');
    }
    
});
