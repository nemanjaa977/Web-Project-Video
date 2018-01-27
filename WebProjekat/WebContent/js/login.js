$(document).ready(function() {
	var userNameInput = $('#userNameInput');
	var passwordInput = $('#passwordInput');
	
	
	$('#loginSubmit').on('click', function(event) { // izvršava se na klik na dugme
		var userName = userNameInput.val();
		var password = passwordInput.val();
		console.log('userName: ' + userName);
		console.log('password: ' + password);
	
		// kontrola toka se račva na 2 grane
		$.post('LoginServlet', {'korisnickoIme': userName, 'lozinka': password}, function(data) {
			// tek kada stigne odgovor izvršiće se ova anonimna funkcija
			console.log('stigao odgovor');
			console.log(data);
			if (data.status == 'success') {
				window.location.replace('pocetna.html');
			}
		});
		// program se odmah nastavlja dalje, pre nego što stigne odgovor
		console.log('poslat zahtev');
	
		// zabraniti da browser obavi podrazumevanu akciju pri događaju
		event.preventDefault();
		return false;
	});
});