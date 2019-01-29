
function change(event) {	

	$.ajax({
		url : '/changePassword',
		type : 'PUT',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify({	
			userPassword : $('#password').val(),
		}),
		success : function(response) {
			alert('Password changed');
		},
		error : function(request, message, error) {
		}
	});
	 
	event.preventDefault();
}