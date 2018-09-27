$(document).ready(function () {
 let token = window.localStorage.getItem('auth_token');
           if (token) {
                $('#loginButton').hide();
               $.ajaxSetup({
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
               })
                addRiderAmmunition();
               
                $('#log-out').on('click',function(){
              logout();
            });
               
            } else  {
        console.log('Token not exists');
        $(location).attr('href', 'login.html');
   }
});
function addRiderAmmunition(){
    $('#createRiderAmmunitionForm').submit(function(e){
        e.preventDefault();
        let type = $('#typeRiderAmmunition').val();
        let size = $('#sizeRiderAmmunition').val();
        let color = $('#colorRiderAmmunition').val();
        let description = $('#descriptionRiderAmmunition').val();
        let imageUrl = $('#photoRiderAmmunition').val();
        let price = $('#priceRiderAmmunition').val();  
        
        let breeches = {
            type: type,
            size: size,
            color: color,
            description: description,
            imageUrl: imageUrl,
            price: price
        };
        $.ajax({
            url: 'http://localhost:7777/rider_ammunition/',
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
               $('#createRiderAmmunitionForm')[0].reset();
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