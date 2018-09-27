$(document).ready(function () {
    let token = window.localStorage.getItem('auth_token');
           if (token) {
                $('#loginButton').hide();
               $.ajaxSetup({
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
               })
                addHorses();
               
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
function addHorses(){
    $('#createHorseForm').submit(function(e){
        e.preventDefault();
        let name = $('#horseName').val();
        let breed = $('#breedHorse').val();
        let height = $('#heightHorse').val();
        let coatColour = $('#coatColorHorse').val();
        let yearOfBirth = $('#yearOfBirthHorse').val();
        let description = $('#descriptionHorse').val();
        let imageUrl = $('#photoHorse').val();
        let price = $('#priceHorse').val(); 
        
        let horse = {
            name: name,
            breed: breed,
            height: height,
            coatColour: coatColour,
            yearOfBirth: yearOfBirth,
            description: description,
            imageUrl: imageUrl,
            price: price,
        };
        $.ajax({
            url: 'http://localhost:7777/horses/',
            method: 'POST',
             dataType: 'json',
             contentType: 'application/json',
             data: JSON.stringify(horse),
             complete: function (data) {
                 console.log(data);
            if (data.status == 500) {
              console.log('Error happened');
            }
             if (data.status == 201) {
               $('#createHorseForm')[0].reset();
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
