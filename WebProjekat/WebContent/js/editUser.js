$(document).ready(function(){
	var korisnickoIme = window.location.search.slice(1).split('&')[0].split('=')[1];
	var nav = $('.navBar');
	var name = $('#nameInput');
	var surname = $('#surnameInput');
	var password = $('#passwordInput');
	var description = $('#descriptionlInput');
	var korisnik = $('#korisnikId');
	var admin = $('#adminId');
	var labPass = $('#labPass');
	
	nav.append('<a href="pocetna.html"><i class="fa fa-home"></i> Home</a> <a href="LogOutServlet"><i class="fa fa-angle-double-down"></i> Log Out </a> <a href="user.html?korisnickoIme='+korisnickoIme+'"><i class="fa fa-user-o"></i> Profile</a>');
	
	$.get('KorisnikServlet',{'korisnickoIme':korisnickoIme},function(data){
		
		if(data.logovani.uloga != "ADMINISTRATOR"){
			$(".roleDiv").hide();
		}else {
			labPass.hide();
			password.hide();
		}
		
//		if(data.logovani.uloga == "ADMINISTRATOR"){
//			labPass.show();
//			password.show();
//		}
		
		name.val(data.vlasnik.ime);
		surname.val(data.vlasnik.prezime);
		password.val(data.vlasnik.lozinka);
		description.val(data.vlasnik.opis);
		if(data.vlasnik.uloga == "ADMINISTRATOR"){
			admin.prop('checked',true);
		}else{
			korisnik.prop('checked',true);
		}
	});
	
	$('#editSubmit').on('click',function(event){
		var editedName=name.val();
		var editedPassword=password.val();
		var editedSurname=surname.val();
		var editedDescription=description.val();
		var role="KORISNIK";
		if(admin.is(':checked')){
			role="ADMINISTRATOR";
		}
		
		var params={
				'status':'izmena',
				'editedName':editedName,
				'editedPassword':editedPassword,
				'editedSurname':editedSurname,
				'editedDescription':editedDescription,
				'role':role,
				'korIme':korisnickoIme
		};
		
		$.post('KorisnikServlet',params,function(data){
			if(data.status=="success"){
				var location="user.html?korisnickoIme="+korisnickoIme;
				window.location.replace(location);
			}
		});

		event.preventDefault();
		return false;
	});

});
