
function change(event) {	

	$.ajax({
		url : '/users/changeData',
		type : 'PUT',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify({
			
			firstname : $('#firstname').val(),
			lastname : $('#lastname').val(),
		}),
		success : function(response) {
			alert('Personal  data changed');
			window.location.href = '/app/users/edit.html?username=' + getUrlParameter('username');
		},
		error : function(request, message, error) {
			$('#logginError').show();
		}
	});
	event.preventDefault();
}