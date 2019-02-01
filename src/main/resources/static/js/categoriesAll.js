$(document).ready(function() {
	show = ' style="display:none;" ';
	$.get("/loggedIn").done(function(data) {
		if(data['type'] == 'ADMIN');
			show = ''
		showBooks(show)
	}).fail(function(xhr, status, error) {
		showBooks(show);
	});

});

function showBooks(show){
	console.log(show)
	$.get("/categories/").done(function(data) {
		console.log(data)
		usersDiv = $('#users');
		$.each(data, function(i, item) {
            user = 	 '<div class="w3-container w3-center w3-margin" style=" display: inline-block;" >' +
					 '<div class="w3-card-4 w3-dark-grey" style=" max-width: 18rem;" >' +
					 '	<div class="w3-container w3-center w3-hover-shadow">' +
					 '			<h1>{{name}}</h1>' +
					 '			<button class="w3-button w3-green w3-margin-bottom" data-id ="{{id}}" {{show}} onclick="editUser(event, this)">Edit</button>' +
                     '			<button class="w3-button w3-red w3-margin-bottom" data-id ="{{id}}" {{show}} onclick="deleteUser(event, this)">Delete</button>' +
                     '          <div class="w3-container w3-center ">' +  
                     '<button class="w3-button w3-yellow w3-margin-bottom" data-id ="{{id}}" onclick="viewEBooks(event, this)">View eBooks</button>' + 
                     '          </div>'
					 '	</div>' +
					 '</div>' +
					 '</div>';
            user = user.replace('{{name}}', item['name']).replace('{{id}}', item['id']).replace('{{id}}', item['id']).replace('{{id}}', item['id']).replace('{{show}}', show).replace('{{show}}', show);
            usersDiv.append($.parseHTML(user));
		});
	}).fail(function(xhr, status, error) {
		alert('U have no rights to access this page');
	});
	
}
function editUser(event, div) {
    window.location.href = '/app/categories/edit.html?id=' + $(div).attr("data-id");
    event.preventDefault();
}

function viewEBooks(event, div) {
    window.location.href = '/app/categories/books.html?id=' + $(div).attr("data-id");
    event.preventDefault();
}

function deleteUser(event, div) {
	$.ajax({
		url : '/categories/' + $(div).attr("data-id"),
		type : 'DELETE',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(response) {
			 window.location.href = '/app/categories/all.html'
		},
		error : function(request, message, error) {
			
		}
	});
	event.preventDefault();
    
}


