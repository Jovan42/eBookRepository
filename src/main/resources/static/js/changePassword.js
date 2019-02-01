
function change(event) {	
	if($('#password').val() == $('#password_confirmation').val()) {
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
	} else {
		alert('Passwords must match');
	}
	 
	event.preventDefault();
}