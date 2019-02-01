$(document).ready(function() {
	if(getUrlParameter('id') != null) {
		$.get("/categories/" + getUrlParameter('id')).done(function(data) {
			$('#name').val(data['name']);
			
			$('#add').html('Save');
		}).fail(function(xhr, status, error) {
			alert('U have no rights to access this data');
		});
	}
});


function addCat(event) {	
	if(getUrlParameter('id') == null) {
		$.ajax({
			url : '/categories/',
			type : 'POST',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : JSON.stringify({
				name : $('#name').val(),
			}),
			success : function(response) {
				window.location.href = '/app/categories/all.html';
			},
			error : function(request, message, error) {
				$('#logginError').show();
			}
		});
		
	} else {
		$.ajax({
			url : '/categories/' + getUrlParameter('id') ,
			type : 'PUT',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : JSON.stringify({
				name : $('#name').val(),
			}),
			success : function(response) {
				alert('eBook data changed');
				window.location.href = '/app/categories/edit.html?id=' + getUrlParameter('id');
			},
			error : function(request, message, error) {
				$('#logginError').show();
			}
		});
	}
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