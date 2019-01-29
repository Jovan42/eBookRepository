$(document).ready(function() {
	$.get("/loggedIn").done(function(data) {
        $('#user').html(data['username']);
        $('#role').html(data['type']);
        
        if(data['type'] == 'ADMIN') {
        	 $('#admin').show();
        	 $('#sub').show();
        } else if(data['type'] == 'SUB') {
        	 $('#sub').show();
        }
	}).fail(function(xhr, status, error) {
	});

});