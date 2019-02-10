show = ' style="display:none;" ';
download = ' style="display:none;" ';
$(document).ready(function() {
	
	$.get("/loggedIn").done(function(data) {
		if(data['type'] == 'ADMIN') {
			show = '';
			download = '';
		}
		if(data['type'] == 'SUB') {
			download = '';
		}
		showBooks(show, download)
	}).fail(function(xhr, status, error) {
		showBooks(show, download);
	});

});

function showBooks(show, download){
	console.log(show)
	$.get("/eBooks").done(function(data) {
		usersDiv = $('#users');
		$.each(data, function(i, item) {
            user = 	 '<div class="w3-container w3-center w3-margin" style=" display: inline-block;" >' +
					 '<div class="w3-card-4 w3-dark-grey" style=" max-width: 18rem;" >' +
					 '	<div class="w3-container w3-center w3-hover-shadow">' +
					 '			<h3>{{title}}</h3>' +
					 '			<h5>{{author}}</h5>' +
					 '			<p>{{publicationyear}}</p>' +
					 '			<button class="w3-button w3-green w3-margin-bottom" data-id ="{{id}}" {{show}} onclick="editUser(event, this)">Edit</button>' +
					 '			<button class="w3-button w3-red w3-margin-bottom" data-id ="{{id}}" {{show}} onclick="deleteUser(event, this)">Delete</button>' +
					 '			<button class="w3-button w3-yellow w3-margin-bottom" data-id ="{{id}}" {{download}} onclick="downloadBook(event, this)">Download</button>' +
					 '	</div>' +
					 '</div>' +
					 '</div>';
			user = user.replace('{{title}}', item['title']).replace('{{author}}', item['author'])
			.replace('{{publicationyear}}', item['publicationyear']).replace('{{id}}', item['id'])
			.replace('{{id}}', item['id']).replace('{{id}}', item['id']).replace('{{show}}', show)
			.replace('{{show}}', show).replace('{{download}}', download);
            usersDiv.append($.parseHTML(user));
		});
	}).fail(function(xhr, status, error) {
		alert('U have no rights to access this page');
	});
	
}
function editUser(event, div) {
    window.location.href = '/app/eBooks/edit.html?id=' + $(div).attr("data-id");
    event.preventDefault();
}

function downloadBook(event, div) {
    window.location.href = '/eBooks/'+ $(div).attr("data-id") + '/download';
    event.preventDefault();
}

function deleteUser(event, div) {
	$.ajax({
		url : '/eBooks/' + $(div).attr("data-id"),
		type : 'DELETE',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(response) {
			 window.location.href = '/app/eBooks/all.html'
		},
		error : function(request, message, error) {
			
		}
	});
	event.preventDefault();
    
}
function searchText(event) {
	usersDiv.html('');
    $.ajax({
		url : '/eBooks/searchText/' + $('#text').val(),
		type : 'GET',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			$.each(data, function(i, item) {
				user = 	 '<div class="w3-container w3-center w3-margin" style=" display: inline-block;" >' +
						 '<div class="w3-card-4 w3-dark-grey" style=" max-width: 18rem;" >' +
						 '	<div class="w3-container w3-center w3-hover-shadow">' +
						 '			<h3>{{title}}</h3>' +
						 '			<h5>{{author}}</h5>' +
						 '			<p>{{publicationyear}}</p>' +
						 '			<button class="w3-button w3-green w3-margin-bottom" data-id ="{{id}}" {{show}} onclick="editUser(event, this)">Edit</button>' +
						 '			<button class="w3-button w3-red w3-margin-bottom" data-id ="{{id}}" {{show}} onclick="deleteUser(event, this)">Delete</button>' +
						 '			<button class="w3-button w3-yellow w3-margin-bottom" data-id ="{{id}}" {{download}} onclick="downloadBook(event, this)">Download</button>' +
						 '	</div>' +
						 '</div>' +
						 '</div>';
				user = user.replace('{{title}}', item['title']).replace('{{author}}', item['author'])
				.replace('{{publicationyear}}', item['publicationyear']).replace('{{id}}', item['id'])
				.replace('{{id}}', item['id']).replace('{{id}}', item['id']).replace('{{show}}', show)
				.replace('{{show}}', show).replace('{{download}}', download);
				usersDiv.append($.parseHTML(user));
			});
		},
		error : function(request, message, error) {
			
		}
	});
	event.preventDefault();
}

function searchTitle(event) {
	usersDiv.html('');
	$.ajax({
		url : '/eBooks/searchTitle/' + $('#text').val(),
		type : 'GET',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			$.each(data, function(i, item) {
				user = 	 '<div class="w3-container w3-center w3-margin" style=" display: inline-block;" >' +
						 '<div class="w3-card-4 w3-dark-grey" style=" max-width: 18rem;" >' +
						 '	<div class="w3-container w3-center w3-hover-shadow">' +
						 '			<h3>{{title}}</h3>' +
						 '			<h5>{{author}}</h5>' +
						 '			<p>{{publicationyear}}</p>' +
						 '			<button class="w3-button w3-green w3-margin-bottom" data-id ="{{id}}" {{show}} onclick="editUser(event, this)">Edit</button>' +
						 '			<button class="w3-button w3-red w3-margin-bottom" data-id ="{{id}}" {{show}} onclick="deleteUser(event, this)">Delete</button>' +
						 '			<button class="w3-button w3-yellow w3-margin-bottom" data-id ="{{id}}" {{download}} onclick="downloadBook(event, this)">Download</button>' +
						 '	</div>' +
						 '</div>' +
						 '</div>';
				user = user.replace('{{title}}', item['title']).replace('{{author}}', item['author'])
				.replace('{{publicationyear}}', item['publicationyear']).replace('{{id}}', item['id'])
				.replace('{{id}}', item['id']).replace('{{id}}', item['id']).replace('{{show}}', show)
				.replace('{{show}}', show).replace('{{download}}', download);
				usersDiv.append($.parseHTML(user));
			});
		},
		error : function(request, message, error) {
			
		}
	});
	event.preventDefault();
}
