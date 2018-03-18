$(document).ready(function(){
	var korisnickoIme = window.location.search.slice(1).split('&')[0].split('=')[1];
	var nav = $('.navBar');
	
	nav.append('<a href="user.html?korisnickoIme='+korisnickoIme+'"><i class="fa fa-user-o"></i> Profile</a> <a href="pocetna.html"><i class="fa fa-home"></i> Home</a>');
});