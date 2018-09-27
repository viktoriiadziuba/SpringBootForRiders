$(document).ready(function () {
 let token = window.localStorage.getItem('auth_token');
           if (token) {
                $('#loginButton').hide();
               $.ajaxSetup({
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
               })
                addHelmet();
               
               $('#log-out').on('click',function(){
              logout();
            });
               
            } else  {
        console.log('Token not exists');
        $(location).attr('href', 'login.html');
   }
});
function addHelmet(){
    $('#createHelmetForm').submit(function(e){
        e.preventDefault();
        let size = $('#sizeHelmet').val();
        let color = $('#colorHelmet').val();
        let description = $('#descriptionHelmet').val();
        let imageUrl = $('#photoHelmet').val();
        let price = $('#priceHelmet').val();  
        
        let breeches = {
            size: size,
            color: color,
            description: description,
            imageUrl: imageUrl,
            price: price
        };
        $.ajax({
            url: 'http://localhost:7777/helmets/',
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
               $('#createHelmetForm')[0].reset();
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
