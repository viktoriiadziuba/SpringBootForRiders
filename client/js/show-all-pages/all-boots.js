$(document).ready(function(){
 let token = window.localStorage.getItem('auth_token');
 let role = window.localStorage.getItem('auth_role');
    console.log(role);
    if(token) {
         $('#loginButton').hide();
        $.ajaxSetup({
            headers: {
                'Autorization': 'Bearer ' + token
            }
        })
        
     getBoots(); 

         $(document).on('change', '#allBootsTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let bootsId = elementId.split('-')[1];
            uploadFile(bootsId);
        
    });
        
         $('#log-out').on('click',function(){
              logout();
            });
    
         } else {
        $('#log-out-menu').hide();
        console.log('Token not exists');
        $('.container').hide();
        $(location).attr('href', 'login.html');
    }
    

    $(document).on('click', '#allBootsTable tbody button.delete', function (e) {
                    console.log(e.target.id);
                    let elementId = e.target.id;
                    let bootsId = elementId.split('-')[1];
                    console.log(bootsId);
                    
                    let deleteItem = confirm("Ви хочете видалити товар?");
                    if(deleteItem){
                        deleteBoots(bootsId);
                    }
                });
    
    });

function deleteBoots(bootsId) {
    console.log('delete: '+ bootsId);
$.ajax({
    url: 'http://localhost:7777/boots/'+ bootsId,
    type: 'DELETE',
    success: function(result) {
        $('#allBootsTable tbody').empty();
        getBoots();
    }
});
}

function logout(){
         window.localStorage.removeItem('auth_token' );
             window.localStorage.removeItem('auth_role' );
             $(location).attr('href' , 'login.html')

       }

function uploadFile(bootsId){
    let formData = new FormData();
    formData.append('image', $('#image-' + bootsId)[0].files[0]);
    
      $.ajax({
                url: 'http://localhost:7777/boots/image/' + bootsId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allBootsTable tbody').empty();
                    getBoots();
                }
            })

}

function getBoots() {
    $.ajax({
       url: 'http://localhost:7777/boots',
        method: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function(response){
            console.log(response);
            $.each(response, function(key, value){
                   $('#allBootsTable tbody').append(
                `
    <tr>
    <td>
    <img src="${value.imageUrl}" width="150px">
    </td>
     <td><input type="file" id="image-${value.bootsId}"></td>
    <td>${value.type}</td>
    <td>${value.size}</td>
    <td>${value.color}</td>
    <td>${value.description}</td>
    <td>${value.price}</td>
    <td><p data-placement="top" data-toggle="tooltip" title="Delete"><button id="boots-${value.bootsId}"  class="btn btn-danger btn-xs delete" data-title="Delete"><span class="glyphicon glyphicon-trash"></span></button></p></td>
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

$(document).on('change', '#allBootsTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let bootsId = elementId.split('-')[1];
        
    function uploadFile(bootsId){
    let formData = new FormData();
    formData.append('image' + $('#image-' + bootsId)[0].files[0]);
    
      $.ajax({
                url: serverUrl + 'boots/image/' + bootsId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allBootsTable tbody').empty();
                    getBoots();
                }
            })

}

    });

 $('#email').keyup(function(){
        console.log($(this).val());
        let email = $(this).val();
        
        $.get('http://localhost:7777/' + 'boots/check-email?email=' + email, function(data, status){
            console.log(data);
        });
    })
