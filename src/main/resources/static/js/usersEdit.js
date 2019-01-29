$(document).ready(function() {
	if(getUrlParameter('username') != null) {
		$.get("/users/" + getUrlParameter('username')).done(function(data) {
			$('#firstname').val(data['firstname']);
			$('#lastname').val(data['lastname']);
			$('#username').val(data['username']);
			$('#password').hide();
			if(data['type'] == 'ADMIN')
				$('#type').val('2').prop('selected', true);
			$('#add').html('Save');
		}).fail(function(xhr, status, error) {
			alert('U have no rights to access this data');
		});
	}
});


function addUser(event) {	
	var userType = 'SUB';
	if($('#type').find(":selected").text() == 'Administrator')
		userType = 'ADMIN';
	if(getUrlParameter('username') == null) {
		$.ajax({
			url : '/users',
			type : 'POST',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : JSON.stringify({
				username : $('#username').val(),
				firstname : $('#firstname').val(),
				lastname : $('#lastname').val(),
				userPassword : $('#password').val(),
				type : userType
			}),
			success : function(response) {
				window.location.href = '/app/users/all.html';
			},
			error : function(request, message, error) {
				$('#logginError').show();
			}
		});
		
	} else {
		$.ajax({
			url : '/users/' + getUrlParameter('username') ,
			type : 'PUT',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : JSON.stringify({
				username : $('#username').val(),
				firstname : $('#firstname').val(),
				lastname : $('#lastname').val(),
				type : userType
			}),
			success : function(response) {
				alert('USer data changed');
				window.location.href = '/app/users/edit.html?username=' + getUrlParameter('username');
			},
			error : function(request, message, error) {
				$('#logginError').show();
			}
		});
	}
	event.preventDefault();
}


var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
};