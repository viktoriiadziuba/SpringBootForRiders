$(document).ready(function(){
 let token = window.localStorage.getItem('auth_token');
    if(token) {
         $('#loginButton').hide();
        $.ajaxSetup({
            headers: {
                'Autorization': 'Bearer ' + token
            }
        })
     getRiderAmmunition(); 
        
         $('#log-out').on('click',function(){
              logout();
            });
        
         $(document).on('change', '#allRiderAmmunitionTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let riderAmmunitionId = elementId.split('-')[1];
            uploadFile(riderAmmunitionId);
        
    });

    } else{
        console.log('Token not exists');
        $('.container').hide();
        $(location).attr('href', 'login.html');
    }
    
     $(document).on('click', '#allRiderAmmunitionTable tbody button.delete', function (e) {
                    console.log(e.target.id);
                    let elementId = e.target.id;
                    let riderAmmunitionId = elementId.split('-')[1];
                    console.log(riderAmmunitionId);
                    
                    let deleteItem = confirm("Ви хочете видалити товар?");
                    if(deleteItem){
                        deleteRiderAmmunition(riderAmmunitionId);
                    }
                });
});

function deleteRiderAmmunition(riderAmmunitionId) {
    console.log('delete: '+ riderAmmunitionId);
$.ajax({
    url: 'http://localhost:7777/rider_ammunition/'+ riderAmmunitionId,
    type: 'DELETE',
    success: function(result) {
        $('#allRiderAmmunitionTable tbody').empty();
        getRiderAmmunition();
    }
});
}

function uploadFile(riderAmmunitionId){
    let formData = new FormData();
    formData.append('image', $('#image-' + riderAmmunitionId)[0].files[0]);
    
      $.ajax({
                url: 'http://localhost:7777/rider_ammunition/image/' + riderAmmunitionId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allRiderAmmunitionTable tbody').empty();
                    getRiderAmmunition();
                }
            })

}

function logout(){
         window.localStorage.removeItem('auth_token' );
             window.localStorage.removeItem('auth_role' );
             $(location).attr('href' , 'login.html')

       }

function getRiderAmmunition() {
    $.ajax({
       url: 'http://localhost:7777/rider_ammunition',
        method: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function(response){
            console.log(response);
            $.each(response, function(key, value){
                   $('#allRiderAmmunitionTable tbody').append(
                `
    <tr>
    <td><img src="${value.imageUrl}" width="150px">
    </td>
     <td><input type="file" id="image-${value.riderAmmunitionId}"></td>
    <td>${value.type}</td>
    <td>${value.size}</td>
    <td>${value.color}</td>
    <td>${value.description}</td>
    <td>${value.price}</td>
    <td><p data-placement="top" data-toggle="tooltip" title="Delete"><button id="riderAmmunition-${value.riderAmmunitionId}" class="btn btn-danger btn-xs delete" data-title="Delete"><span class="glyphicon glyphicon-trash"></span></button></p></td>
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

 $(document).on('change', '#allRiderAmmunitionTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let riderAmmunitionId = elementId.split('-')[1];
        
    function uploadFile(riderAmmunitionId){
    let formData = new FormData();
    formData.append('image' + $('#image-' + riderAmmunitionId)[0].files[0]);
    
      $.ajax({
                url: serverUrl + 'rider_ammunition/image/' + riderAmmunitionId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allRiderAmmunitionTable tbody').empty();
                    getRiderAmmunition();
                }
            })

}

    });

