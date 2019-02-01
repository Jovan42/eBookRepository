function loutUser(event) {
    event.preventDefault();
    console.log('aaaaaaaaaaaaaaaa')
    $.ajax({
		url : '/logout',
		type : 'POST',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify({
		}),
		success : function(response) {
            window.location.href = '/app/login.html';
		},
		error : function(request, message, error) {
            window.location.href = '/app/login.html';
		}
	});
   
    event.preventDefault();
}