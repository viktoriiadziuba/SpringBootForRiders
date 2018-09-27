$(document).ready(function () {
 let token = window.localStorage.getItem('auth_token');
           if (token) {
                $('#loginButton').hide();
               $.ajaxSetup({
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
               })
                addHorseAmmunition();
               
               $('#log-out').on('click',function(){
              logout();
            });
               
            } else  {
        console.log('Token not exists');
        $(location).attr('href', 'login.html');
   }
});
function addHorseAmmunition(){
    $('#createHorseAmmunitionForm').submit(function(e){
        e.preventDefault();
        let type = $('#typeHorseAmmunition').val();
        let size = $('#sizeHorseAmmunition').val();
        let color = $('#colorHorseAmmunition').val();
        let description = $('#descriptionHorseAmmunition').val();
        let imageUrl = $('#photoHorseAmmunition').val();
        let price = $('#priceHorseAmmunition').val();  
        
        let breeches = {
            type: type,
            size: size,
            color: color,
            description: description,
            imageUrl: imageUrl,
            price: price
        };
        $.ajax({
            url: 'http://localhost:7777/horse_ammunition/',
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
               $('#createHorseAmmunitionForm')[0].reset();
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