$(document).ready(function() {
	var userNameInput = $('#userNameInput');
	var passwordInput = $('#passwordInput');
	var message = $('#provera');
	
	
	$('#loginSubmit').on('click', function(event) { 
		var userName = userNameInput.val();
		var password = passwordInput.val();
		console.log('userName: ' + userName);
		console.log('password: ' + password);
		
		if(userName=="" || password =="")
			message.text("You need to fill in all fields!");
		
		$.post('LoginServlet', {'korisnickoIme': userName, 'lozinka': password}, function(data) {
			console.log('stigao odgovor');
			console.log(data);
			if (data.status == 'success') {
				window.location.replace('user.html?korisnickoIme='+userName);
			}
			if (data.status == 'failure') {
				message.text("Selected incorrect data!");
			}
		});
		console.log('poslat zahtev');
		event.preventDefault();
		return false;
	});
});