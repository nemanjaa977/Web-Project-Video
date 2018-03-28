$(document).ready(function() {
	var userNameInput = $('#userNameInput');
	var passwordInput = $('#passwordInput');
	var nameInput = $('#nameInput');
	var surnameInput = $('#surnameInput');
	var emailInput = $('#emailInput');
	var descripInput = $("#descriptionlInput");
	var message = $('#message');
	
	$('#registerSubmit').on('click', function(event) {
		var userName = userNameInput.val();
		var password = passwordInput.val();
		var name = nameInput.val();
		var surname = surnameInput.val();
		var email = emailInput.val();
		var description = descripInput.val();
		
		if(userName == "" || password == "" || email == ""){
			message.text("Fields with a star are required!");
			return false;
		}
	
		$.post('RegisterServlet', {'korisnickoIme': userName, 'lozinka': password,  'ime': name, 'prezime': surname, 'email': email, 'opis': description}, function(data) {
			console.log(data);
			if (data.status == 'success') {
				window.location.replace('pocetna.html');
			}
			
		});
		
		event.preventDefault();
		return false;
	});
});