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
	$.get('/eBooks/cat/' + getUrlParameter('id') + '/').done(function(data) {
		console.log(data)
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
					 '	</div>' +
					 '</div>' +
					 '</div>';
            user = user.replace('{{title}}', item['title']).replace('{{author}}', item['author']).replace('{{publicationyear}}', item['publicationyear']).replace('{{id}}', item['id']).replace('{{id}}', item['id']).replace('{{show}}', show).replace('{{show}}', show);
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