$(document).ready(function(){
  let token = window.localStorage.getItem('auth_token');
    if(token) {
         $('#loginButton').hide();
        $.ajaxSetup({
            headers: {
                'Autorization': 'Bearer ' + token
            }
        })
     getHalfChaps(); 
        
         $(document).on('change', '#allHalfChapsTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let halfChapsId = elementId.split('-')[1];
            uploadFile(halfChapsId);
        
    });
        
        $('#log-out').on('click',function(){
              logout();
            });
    
    } else{
        console.log('Token not exists');
        $('.container').hide();
        $(location).attr('href', 'login.html');
    }
    
    
    $(document).on('click', '#allHalfChapsTable tbody button.delete', function (e) {
                    console.log(e.target.id);
                    let elementId = e.target.id;
                    let halfChapsId = elementId.split('-')[1];
                    console.log(halfChapsId);
                    
                    let deleteItem = confirm("Ви хочете видалити товар?");
                    if(deleteItem){
                        deleteHalfChaps(halfChapsId);
                    }
                });
});

function deleteHalfChaps(halfChapsId) {
    console.log('delete: '+ halfChapsId);
$.ajax({
    url: 'http://localhost:7777/halfChaps/'+ halfChapsId,
    type: 'DELETE',
    success: function(result) {
        $('#allHalfChapsTable tbody').empty();
        getHalfChaps();
    }
});
}

function uploadFile(halfChapsId){
    let formData = new FormData();
    formData.append('image', $('#image-' + halfChapsId)[0].files[0]);
    
      $.ajax({
                url: 'http://localhost:7777/halfChaps/image/' + halfChapsId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allHalfChapsTable tbody').empty();
                    getHalfChaps();
                }
            })

}

function logout(){
         window.localStorage.removeItem('auth_token' );
             window.localStorage.removeItem('auth_role' );
             $(location).attr('href' , 'login.html')

       }

function getHalfChaps() {
    $.ajax({
       url: 'http://localhost:7777/halfChaps',
        method: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function(response){
            console.log(response);
            $.each(response, function(key, value){
                   $('#allHalfChapsTable tbody').append(
                `
    <tr>
    <td><img src="${value.imageUrl}" width="150px">
    </td>
     <td><input type="file" id="image-${value.halfChapsId}"></td>
    <td>${value.size}</td>
    <td>${value.color}</td>
    <td>${value.description}</td>
    <td>${value.price}</td>
    <td><p data-placement="top" data-toggle="tooltip" title="Delete"><button id="halfChaps-${value.halfChapsId}" class="btn btn-danger btn-xs delete" data-title="Delete"><span class="glyphicon glyphicon-trash"></span></button></p></td>
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


 $(document).on('change', '#allHalfChapsTable tbody input',function(e){
       console.log(e.target.id); 
        let elementId = e.target.id;
        let halfChapsId = elementId.split('-')[1];
        
    function uploadFile(halfChapsId){
    let formData = new FormData();
    formData.append('image' + $('#image-' + halfChapsId)[0].files[0]);
    
      $.ajax({
                url: serverUrl + 'halfChaps/image/' + halfChapsId,
                method: 'POST',
                contentType: false,
                data: formData,
                processData: false,
                complete: function (data) {
                    $('#allHalfChapsTable tbody').empty();
                    getHalfChaps();
                }
            })

}

    });
