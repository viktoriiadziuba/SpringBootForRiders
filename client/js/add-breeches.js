$(document).ready(function () {
 let token = window.localStorage.getItem('auth_token');
           if (token) {
                $('#loginButton').hide();
               $.ajaxSetup({
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
               })
                addBreeches();
               
               $('#log-out').on('click',function(){
              logout();
            });
        
               
            } else  {
        console.log('Token not exists');
        $(location).attr('href', 'login.html');
   }
});
function addBreeches(){
    $('#createBreechesForm').submit(function(e){
        e.preventDefault();
        let size = $('#sizeBreeches').val();
        let color = $('#colorBreeches').val();
        let description = $('#descriptionBreeches').val();
        let imageUrl = $('#photoBreeches').val();
        let price = $('#priceBreeches').val();  
        
        let breeches = {
            size: size,
            color: color,
            description: description,
            imageUrl: imageUrl,
            price: price
        };
        $.ajax({
            url: 'http://localhost:7777/breeches/',
            method: 'POST',
             dataType: 'json',
             contentType: 'application/json',
             data: JSON.stringify(breeches),
             complete: function (data) {
                 console.log(data);
            if (data.status == 500) {
              console.log('Error happened');
            }
             if (data.status == 201) {
               $('#createBreechesForm')[0].reset();
            }
        }
        });
    });
} 

function logout(){
         window.localStorage.removeItem('auth_token' );
             window.localStorage.removeItem('auth_role' );
             $(location).attr('href' , 'login.html')

       }