$(document).ready(function() {
	if(getUrlParameter('id') != null) {
		$.get("/eBooks/" + getUrlParameter('id')).done(function(data) {
			$('#title').val(data['title']);
			$('#author').val(data['author']);
			$('#publicationyear').val(data['publicationyear']);
			$('#filename').val(data['filename']);
			$('#add').html('Save');
		}).fail(function(xhr, status, error) {
			alert('U have no rights to access this data');
		});
	}
	$.get("/categories/").done(function(data) {
		console.log(data)
		$.each(data, function(i, item) {
			
			$('#categories').append($('<option>', {value:i, text:item['name'], 'data-id': item['id']}));
		})
	}).fail(function(xhr, status, error) {
		alert('U have no rights to access this data');
	})
	
});


function addBook(event) {	
	if(getUrlParameter('id') == null) {
		$.ajax({
			url : '/eBooks/',
			type : 'POST',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : JSON.stringify({
				title : $('#title').val(),
				author : $('#author').val(),
				publicationyear : $('#publicationyear').val(),
				filename : $('#filename').val(),
				category: $('#categories').find(":selected").attr("data-id")
			}),
			success : function(response) {
				window.location.href = '/app/ebooks/all.html';
			},
			error : function(request, message, error) {
				$('#logginError').show();
			}
		});
		
	} else {
		$.ajax({
			url : '/eBooks/' + getUrlParameter('id') ,
			type : 'PUT',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : JSON.stringify({
				title : $('#title').val(),
				author : $('#author').val(),
				publicationyear : $('#publicationyear').val(),
				filename : $('#filename').val(),
				category: $('#categories').find(":selected").attr("data-id")
			}),
			success : function(response) {
				alert('eBook data changed');
				window.location.href = '/app/eBooks/edit.html?id=' + getUrlParameter('id');
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