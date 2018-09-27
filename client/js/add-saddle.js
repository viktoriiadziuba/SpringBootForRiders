$(document).ready(function () {
 let token = window.localStorage.getItem('auth_token');
           if (token) {
                $('#loginButton').hide();
               $.ajaxSetup({
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
               })
                addSaddle();
               
                $('#log-out').on('click',function(){
              logout();
            });
               
            } else  {
        console.log('Token not exists');
        $(location).attr('href', 'login.html');
   }
});
function addSaddle(){
    $('#createSaddleForm').submit(function(e){
        e.preventDefault();
        let brand = $('#brandSaddle').val();
        let size = $('#sizeSaddle').val();
        let color = $('#colorSaddle').val();
        let description = $('#descriptionSaddle').val();
        let imageUrl = $('#photoSaddle').val();
        let price = $('#priceSaddle').val();  
        
        let breeches = {
            brand: brand,
            size: size,
            color: color,
            description: description,
            imageUrl: imageUrl,
            price: price
        };
        $.ajax({
            url: 'http://localhost:7777/saddles/',
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
               $('#createSaddleForm')[0].reset();
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