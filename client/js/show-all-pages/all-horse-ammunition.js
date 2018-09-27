$(document).ready(function(){
 let token = window.localStorage.getItem('auth_token');
    if(token) {
         $('#loginButton').hide();
        $.ajaxSetup({
            headers: {
                'Autorization': 'Bearer ' + token
            }
        })
     getHorseAmmunition(); 
        
         $('#log-out').on('click',function(){
              logout();
            });
        
         $(document).on('change', '#allHorseAmmunitionTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let horseAmmunitionId = elementId.split('-')[1];
            uploadFile(horseAmmunitionId);
        
    });
        
    } else{
        console.log('Token not exists');
        $('.container').hide();
        $(location).attr('href', 'login.html');
    }
    
     $(document).on('click', '#allHorseAmmunitionTable tbody button.delete', function (e) {
                    console.log(e.target.id);
                    let elementId = e.target.id;
                    let horseAmmunitionId = elementId.split('-')[1];
                    console.log(horseAmmunitionId);
                    
                    let deleteItem = confirm("Ви хочете видалити товар?");
                    if(deleteItem){
                        deleteHorseAmmunition(horseAmmunitionId);
                    }
                });
});

function deleteHorseAmmunition (horseAmmunitionId) {
    console.log('delete: '+horseAmmunitionId);
$.ajax({
    url: 'http://localhost:7777/horse_ammunition/'+ horseAmmunitionId,
    type: 'DELETE',
    success: function(result) {
        $('#allHorseAmmunitionTable tbody').empty();
        getHorseAmmunition();
    }
});
}

function uploadFile(horseAmmunitionId){
    let formData = new FormData();
    formData.append('image', $('#image-' + horseAmmunitionId)[0].files[0]);
    
      $.ajax({
                url: 'http://localhost:7777/horse_ammunition/image/' + horseAmmunitionId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allHorseAmmunitionTable tbody').empty();
                    getHorseAmmunition();
                }
            })

}

function logout(){
         window.localStorage.removeItem('auth_token' );
             window.localStorage.removeItem('auth_role' );
             $(location).attr('href' , 'login.html')

       }


function getHorseAmmunition() {
    $.ajax({
       url: 'http://localhost:7777/horse_ammunition',
        method: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function(response){
            console.log(response);
            $.each(response, function(key, value){
                   $('#allHorseAmmunitionTable tbody').append(
                `
    <tr>
    <td><img src="${value.imageUrl}" width="150px">
    </td>
     <td><input type="file" id="image-${value.horseAmmunitionId}"></td>
    <td>${value.type}</td>
    <td>${value.size}</td>
    <td>${value.color}</td>
    <td>${value.description}</td>
    <td>${value.price}</td>
    <td><p data-placement="top" data-toggle="tooltip" title="Delete"><button id="horseAmmunition-${value.horseAmmunitionId}" class="btn btn-danger btn-xs delete" data-title="Delete"><span class="glyphicon glyphicon-trash"></span></button></p></td>
     <td>${value.user.firstName} 
        <p>${value.user.lastName}</p>
        <p>${value.user.email}</p>
        <p>${value.user.phoneNumber}</p></td>
    </tr>
                `
            
            )});
        }
    });
}

 $(document).on('change', '#allHorseAmmunitionTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let horseAmmunitionId = elementId.split('-')[1];
        
    function uploadFile(horseAmmunitionId){
    let formData = new FormData();
    formData.append('image' + $('#image-' + horseAmmunitionId)[0].files[0]);
    
      $.ajax({
                url: serverUrl + 'horse_ammunition/image/' + horseAmmunitionId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allHorseAmmunitionTable tbody').empty();
                    getHorseAmmunition();
                }
            })

}

    });

