$(document).ready(function(){
    let token = window.localStorage.getItem('auth_token');
    
    if(token) {
         $('#loginButton').hide();
        $.ajaxSetup({
            headers: {
                'Autorization': 'Bearer ' + token
            }
        })
     getHorses();
        
        $(document).on('change', '#allHorsesTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let horseId = elementId.split('-')[1];
            uploadFile(horseId);
        
    });
        
        $('#log-out').on('click',function(){
              logout();
            });
        
    } else{
        console.log('Token not exists');
        $('.container').hide();
        $(location).attr('href', 'login.html');
    }
    
    $(document).on('click', '#allHorsesTable tbody button.delete', function (e) {
                    console.log(e.target.id);
                    let elementId = e.target.id;
                    let horseId = elementId.split('-')[1];
                    console.log(horseId);
                    
                    let deleteItem = confirm("Ви хочете видалити товар?");
                    if(deleteItem){
                        deleteHorse(horseId);
                    }
                });
    });



function deleteHorse(horseId) {
    console.log('delete: '+horseId);
$.ajax({
    url: 'http://localhost:7777/horses/'+ horseId,
    type: 'DELETE',
    success: function(result) {
        $('#allHorsesTable tbody').empty();
        getHorses();
    }
});
}

function logout(){
         window.localStorage.removeItem('auth_token' );
             window.localStorage.removeItem('auth_role' );
             $(location).attr('href' , 'login.html')

       }

function uploadFile(horseId){
    let formData = new FormData();
    formData.append('image', $('#image-' + horseId)[0].files[0]);
    
      $.ajax({
                url: 'http://localhost:7777/horses/image/' + horseId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allHorsesTable tbody').empty();
                    getHorses();
                }
            })

}

function getHorses() {
    $.ajax({
       url: 'http://localhost:7777/horses',
        method: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function(response){
            console.log(response);
             
            $.each(response, function(key, value){
                   $('#allHorsesTable tbody').append(
                `
    <tr>
    <td>
    <img src="${value.imageUrl}" width="150px">
    </td>
     <td><input type="file" id="image-${value.horseId}"></td>
    <td>${value.name}</td>
    <td>${value.breed}</td>
    <td>${value.height}</td>
    <td>${value.coatColour}</td>
    <td>${value.yearOfBirth}</td>
    <td>${value.description}</td>
    <td>${value.price}</td>
    <td><p data-placement="top" data-toggle="tooltip" title="Delete"><button id="horse-${value.horseId}" class="btn btn-danger btn-xs delete" data-title="Delete"  ><span class="glyphicon glyphicon-trash"></span></button></p></td>
     <td>${value.user.firstName} 
        <p>${value.user.lastName}</p>
        <p>${value.user.email}</p>
        <p>${value.user.phoneNumber}</p>
    </td>
    </tr>
                `
            //data-toggle="modal" data-target="#delete"
            )});
        }
    });
}
