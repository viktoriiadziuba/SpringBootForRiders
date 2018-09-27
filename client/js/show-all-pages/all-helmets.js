$(document).ready(function(){
  let token = window.localStorage.getItem('auth_token');
    if(token) {
         $('#loginButton').hide();
        $.ajaxSetup({
            headers: {
                'Autorization': 'Bearer ' + token
            }
        })
     getHelmets(); 
        
        $(document).on('change', '#allHelmetsTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let helmetId = elementId.split('-')[1];
            uploadFile(helmetId);
        
    });
        
        $('#log-out').on('click',function(){
              logout();
            });
    
    } else{
        console.log('Token not exists');
        $('.container').hide();
        $(location).attr('href', 'login.html');
    }
    
    $(document).on('click', '#allHelmetsTable tbody button.delete', function (e) {
                    console.log(e.target.id);
                    let elementId = e.target.id;
                    let helmetId = elementId.split('-')[1];
                    console.log(helmetId);
                    
                    let deleteItem = confirm("Ви хочете видалити товар?");
                    if(deleteItem){
                        deleteHelmet(helmetId);
                    }
                });
});

function deleteHelmet(helmetId) {
    console.log('delete: '+ helmetId);
$.ajax({
    url: 'http://localhost:7777/helmets/'+ helmetId,
    type: 'DELETE',
    success: function(result) {
        $('#allHelmetsTable tbody').empty();
        getHelmets();
    }
});
}

function uploadFile(helmetId){
    let formData = new FormData();
    formData.append('image', $('#image-' + helmetId)[0].files[0]);
    
      $.ajax({
                url: 'http://localhost:7777/helmets/image/' + helmetId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allHelmetsTable tbody').empty();
                    getHelmets();
                }
            })

}

function logout(){
         window.localStorage.removeItem('auth_token' );
             window.localStorage.removeItem('auth_role' );
             $(location).attr('href' , 'login.html')

       }


function getHelmets() {
    $.ajax({
       url: 'http://localhost:7777/helmets',
        method: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function(response){
            console.log(response);
            $.each(response, function(key, value){
                   $('#allHelmetsTable tbody').append(
                `
    <tr>
    <td><img src="${value.imageUrl}" width="150px">
    </td>
     <td><input type="file" id="image-${value.helmetId}"></td>
    <td>${value.size}</td>
    <td>${value.color}</td>
    <td>${value.description}</td>
    <td>${value.price}</td>
    <td><p data-placement="top" data-toggle="tooltip" title="Delete"><button id="helmet-${value.helmetId}" class="btn btn-danger btn-xs delete" data-title="Delete"><span class="glyphicon glyphicon-trash"></span></button></p></td>
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

 $(document).on('change', '#allHelmetsTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let helmetId = elementId.split('-')[1];
        
    function uploadFile(helmetId){
    let formData = new FormData();
    formData.append('image' + $('#image-' + helmetId)[0].files[0]);
    
      $.ajax({
                url: serverUrl + 'helmets/image/' + helmetId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allHelmetsTable tbody').empty();
                    getHelmets();
                }
            })

}

    });

