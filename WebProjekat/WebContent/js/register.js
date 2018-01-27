$(document).ready(function() {
	var userNameInput = $('#userNameInput');
	var passwordInput = $('#passwordInput');
	var nameInput = $('#nameInput');
	var surnameInput = $('#surnameInput');
	var emailInput = $('#emailInput');
	
	$('#registerSubmit').on('click', function(event) {
		var userName = userNameInput.val();
		var password = passwordInput.val();
		var name = nameInput.val();
		var surname = surnameInput.val();
		var email = emailInput.val();
	
		$.post('RegisterServlet', {'korisnickoIme': userName, 'lozinka': password,  'ime': name, 'prezime': surname, 'email': email}, function(data) {
			console.log(data);
			if (data.status == 'success') {
				window.location.replace('pocetna.html');
			}
		});
		event.preventDefault();
		return false;
	});
});