$(document).ready(function () {
 let token = window.localStorage.getItem('auth_token');
           if (token) {
                $('#loginButton').hide();
               $.ajaxSetup({
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
               });
                addBoots();
               
                $('#log-out').on('click',function(){
              logout();
            });
               
            } else  {
        console.log('Token not exists');
        $(location).attr('href', 'login.html');
   }
});
//                $('#email').keyup(function () {
//                    console.log($(this).val());
//                    let email = $(this).val();
//                    $.get(serverUrl + 'authors/check-email?email=' + email, function (data, status) {
//                        console.log(data);
//                    });
//                })
function addBoots(){
    $('#createBootsForm').submit(function(e){
        e.preventDefault();
        let type = $('#typeBoots').val();
        let size = $('#sizeBoots').val();
        let color = $('#colorBoots').val();
        let description = $('#descriptionBoots').val();
        let imageUrl = $('#photoBoots').val();
        let price = $('#priceBoots').val();  
        
        let breeches = {
            type: type,
            size: size,
            color: color,
            description: description,
            imageUrl: imageUrl,
            price: price
        };
        $.ajax({
            url: 'http://localhost:7777/boots/',
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
               $('#createBootsForm')[0].reset();
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