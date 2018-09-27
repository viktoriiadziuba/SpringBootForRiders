$(document).ready(function(){
  let token = window.localStorage.getItem('auth_token');
    if(token) {
         $('#loginButton').hide();
        $.ajaxSetup({
            headers: {
                'Autorization': 'Bearer ' + token
            }
        })
     getBreeches(); 
        
         $(document).on('change', '#allBreechesTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let breechesId = elementId.split('-')[1];
            uploadFile(breechesId);
        
    });
    
         $('#log-out').on('click',function(){
              logout();
            });
        
    } else{
        console.log('Token not exists');
        $('.container').hide();
        $(location).attr('href', 'login.html');
    }
    
    $(document).on('click', '#allBreechesTable tbody button.delete', function (e) {
                    console.log(e.target.id);
                    let elementId = e.target.id;
                    let breechesId = elementId.split('-')[1];
                    console.log(breechesId);
                    
                    let deleteItem = confirm("Ви хочете видалити товар?");
                    if(deleteItem){
                        deleteBreeches(breechesId);
                    }
                });
});

function deleteBreeches(breechesId) {
    console.log('delete: '+breechesId);
$.ajax({
    url: 'http://localhost:7777/breeches/'+ breechesId,
    type: 'DELETE',
    success: function(result) {
        $('#allBreechesTable tbody').empty();
        getBreeches();
    }
});
}

function uploadFile(breechesId){
    let formData = new FormData();
    formData.append('image', $('#image-' + breechesId)[0].files[0]);
    
      $.ajax({
                url: 'http://localhost:7777/breeches/image/' + breechesId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allBreechesTable tbody').empty();
                    getBreeches();
                }
            })

}

function logout(){
         window.localStorage.removeItem('auth_token' );
             window.localStorage.removeItem('auth_role' );
             $(location).attr('href' , 'login.html')

       }

function getBreeches() {
    $.ajax({
       url: 'http://localhost:7777/breeches',
        method: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function(response){
            console.log(response);
            $.each(response, function(key, value){
                   $('#allBreechesTable tbody').append(
                `
    <tr>
    <td> <img src="${value.imageUrl}" width="150px">
    </td>
     <td><input type="file" id="image-${value.breechesId}"></td>
    <td>${value.size}</td>
    <td>${value.color}</td>
    <td>${value.description}</td>
    <td>${value.price}</td>
    <td><p data-placement="top" data-toggle="tooltip" title="Delete"><button id="breeches-${value.breechesId}" class="btn btn-danger btn-xs delete" data-title="Delete"><span class="glyphicon glyphicon-trash"></span></button></p></td>
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


 $(document).on('change', '#allBreechesTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let breechesId = elementId.split('-')[1];
        
    function uploadFile(breechesId){
    let formData = new FormData();
    formData.append('image' + $('#image-' + breechesId)[0].files[0]);
    
      $.ajax({
                url: serverUrl + 'breeches/image/' + breechesId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allBreechesTable tbody').empty();
                    getBreeches();
                }
            })

}

    });
