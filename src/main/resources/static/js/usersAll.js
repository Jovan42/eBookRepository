$(document).ready(function() {
	$.get("/users").done(function(data) {
		console.log(data)
		usersDiv = $('#users');
		$.each(data, function(i, item) {
            user = 	 '<div class="w3-container w3-center w3-margin" style=" display: inline-block;" >' +
					 '<div class="w3-card-4 w3-dark-grey" style=" max-width: 18rem;" >' +
					 '	<div class="w3-container w3-center w3-hover-shadow">' +
					 '			<h3>{{username}}</h3>' +
					 '			<h5>{{name}}</h5>' +
					 '		<p>{{role}}</p>' +
					 '			<button class="w3-button w3-green w3-margin-bottom" data-username ="{{username}}" onclick="editUser(event, this)">Edit</button>' +
					 '			<button class="w3-button w3-red w3-margin-bottom" data-username ="{{username}}" onclick="deleteUser(event, this)">Delete</button>' +
					 '	</div>' +
					 '</div>' +
					 '</div>';
            user = user.replace('{{username}}', item['username']).replace('{{name}}', item['firstname'] + ' ' + item['lastname']).replace('{{role}}', item['type']).replace('{{username}}', item['username']).replace('{{username}}', item['username']);
            usersDiv.append($.parseHTML(user));
		});
	}).fail(function(xhr, status, error) {
		alert('U have no rights to access this page');
	});
});

function editUser(event, div) {
    window.location.href = '/app/users/edit.html?username=' + $(div).attr("data-username");
    event.preventDefault();
}

function deleteUser(event, div) {
	 console.log('/users/' + $(div).attr("data-username"));
	$.ajax({
		url : '/users/' + $(div).attr("data-username"),
		type : 'DELETE',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(response) {
			 window.location.href = '/app/users/all.html'
		},
		error : function(request, message, error) {
			
		}
	});
	event.preventDefault();
    
}


