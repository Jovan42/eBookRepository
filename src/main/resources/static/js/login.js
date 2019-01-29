$(document).ready(function() {
	$.get("/loggedIn").done(function(data) {
		window.location.href = './menu.html'
	}).fail(function(xhr, status, error) {
	});

});

function login(event) {

	$.ajax({
		url : '/login',
		type : 'POST',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify({
			username : $('#username').val(),
			userPassword : $('#userPassword').val()
		}),
		success : function(response) {
			window.location.href = './menu.html';
		},
		error : function(request, message, error) {
			$('#logginError').show();
		}
	});
	event.preventDefault();
}