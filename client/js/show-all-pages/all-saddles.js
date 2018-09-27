$(document).ready(function(){
    const token = window.localStorage.getItem('auth_token');
    if(token) {
         $('#loginButton').hide();
        $.ajaxSetup({
            headers: {
                'Autorization': 'Bearer ' + token
            }
        })
     getSaddle(); 
        
        $(document).on('change', '#allSaddlesTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let saddleId = elementId.split('-')[1];
            uploadFile(saddleId);
        
    });
        
         $('#log-out').on('click',function(){
              logout();
            });
    
    } else{
        console.log('Token not exists');
        $('.container').hide();
        $(location).attr('href', 'login.html');
    }
    
    $(document).on('click', '#allSaddlesTable tbody button.delete', function (e) {
                    console.log(e.target.id);
                    let elementId = e.target.id;
                    let saddleId = elementId.split('-')[1];
                    console.log(saddleId);
                    
                    let deleteItem = confirm("Ви хочете видалити товар?");
                    if(deleteItem){
                        deleteSaddle(saddleId);
                    }
                });
});

function deleteSaddle(saddleId) {
    console.log('delete: '+ saddleId);
$.ajax({
    url: 'http://localhost:7777/saddles/'+ saddleId,
    type: 'DELETE',
    success: function(result) {
        $('#allSaddlesTable tbody').empty();
        getSaddle();
    }
});
}

function uploadFile(saddleId){
    let formData = new FormData();
    formData.append('image', $('#image-' + saddleId)[0].files[0]);
    
      $.ajax({
                url: 'http://localhost:7777/saddles/image/' + saddleId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allSaddlesTable tbody').empty();
                    getSaddle();
                }
            })

}

function logout(){
         window.localStorage.removeItem('auth_token' );
             window.localStorage.removeItem('auth_role' );
             $(location).attr('href' , 'login.html')

       }

function getSaddle() {
    $.ajax({
       url: 'http://localhost:7777/saddles',
        method: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function(response){
            console.log(response);
            $.each(response, function(key, value){
                   $('#allSaddlesTable tbody').append(
                `
    <tr>
    <td><img src="${value.imageUrl}" width="150px">
    </td>
     <td><input type="file" id="image-${value.saddleId}"></td>
    <td>${value.brand}</td>
    <td>${value.size}</td>
    <td>${value.color}</td>
    <td>${value.description}</td>
    <td>${value.price}</td>
    <td><p data-placement="top" data-toggle="tooltip" title="Delete"><button id="saddle-${value.saddleId}" class="btn btn-danger btn-xs delete" data-title="Delete"><span class="glyphicon glyphicon-trash"></span></button></p></td>
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


 $(document).on('change', '#allSaddlesTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let saddleId = elementId.split('-')[1];
        
    function uploadFile(saddleId){
    let formData = new FormData();
    formData.append('image' + $('#image-' + saddleId)[0].files[0]);
    
      $.ajax({
                url: serverUrl + 'saddles/image/' + saddleId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allSaddlesTable tbody').empty();
                    getHorses();
                }
            })

}

    });
