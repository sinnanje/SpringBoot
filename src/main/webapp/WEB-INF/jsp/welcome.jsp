<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Book Maintenance System</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<h1>Welcome to Books System</h1>
<a href="/books/insertBooks">INSERT BOOKS ON SHELF - FIRST TIME INSERTION</a>
<h4>---------------------------------------------------------</h4>
<a href="/books">GET ALL BOOKS ON THE SHELF</a>
<h4>---------------------------------------------------------</h4>
<label>ISBN Number: </label>
<input id="ISBN" type="text"></input>
<br/><br/>
<a href='' onclick="this.href='/books/getBookByISBN?ISBN='+document.getElementById('ISBN').value">GET BOOK BY ISBN FROM SHELF - ENTER ISBN</a>
<h4>---------------------------------------------------------</h4>
<label>ISBN Number: </label>
<input id="ISBN1"></input>
<br/><br/>
<a href='' id="removeLink">REMOVE BOOK BY ISBN FROM SHELF - ENTER ISBN</a>
<h4>---------------------------------------------------------</h4>

<form id="createBookSubmit">
  <label>ISBN Number: </label>
  <input type="text" name="bookISBN" />
  <label>Book Title: </label>
  <input type="text" name="bookTitle" />
   <label>Book Author: </label>
  <input type="text" name="bookAuthor" />
  <br/><br/>
 <input id='createBtn' type='button' value='Create a New Book on Shelf'/>
 <input id='updateBtn' type='button' value='Update an Exisiting Book on Shelf'/>
</form>


<script>
$('#createBtn').click(function(e) {

	var form = $('#createBtn').closest("form");
	// for stopping the default action of element
    e.preventDefault();
    var formData = {}
    
    $.each(form[0], function(i, v){
       var input = $(v);
       formData[input.attr("name")] = input.val();
    });
    $.ajax({
    	type: 'POST',
        url: '/books/createBook',
        data : JSON.stringify(formData),
        contentType: 'application/JSON',
        cache:false,
        success: function (data) {
            alert(data);
        },
        error: function () {
            alert('Error');
        }
        
    });
});

$('#updateBtn').click(function(e) {
	var form = $('#updateBtn').closest("form");
	e.preventDefault();
	var formData = {} 
	$.each(form[0], function(i, v){
	var input = $(v);
	formData[input.attr("name")] = input.val();
	});
	$.ajax({
		type: 'PUT',
		url: '/books/updateBook',
		data : JSON.stringify(formData),
		contentType: 'application/JSON',
		cache:false,
		success: function (data) {
		    alert(data);
		},
		error: function () {
		    alert('Error');
		}

	});

});

$('#removeLink').click(function(){
	$.ajax({
		type: 'DELETE',
		url: '/books/removeBook/' + $("#ISBN1").val(),
		data : JSON.stringify($("#ISBN1").val()),
		contentType: 'application/JSON',
		cache:false,
		success: function (data) {
		    alert(data);
		},
		error: function () {
		    alert('Error');
		}

	});
});



</script>
<h4>-----------------------------------------------------------</h4>

</body>
</html>