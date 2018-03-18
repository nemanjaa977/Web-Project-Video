$(document).ready(function(e) {
	var korisnickoIme = window.location.search.slice(1).split('&')[0].split('=')[1];
	var nav = $(".navBar");
	var row = $(".row");
	
	nav.append('<a href="pocetna.html"><i class="fa fa-home"></i> Home</a> <a href="user.html?korisnickoIme='+korisnickoIme+'"><i class="fa fa-user-o"></i> Profile </a>');
	
	$.get('AdminServlet',{},function(data){
		for(i in data.korisnici){
			row.append("<div class='column'> " + 
    				"<div class='card'>" +
      					"<img src="+'photos/Zac.jpg'+">" + 
      					"<div class='container'>" + 
        					"<a id='username' href='user.html?korisnickoIme="+data.korisnici[i].korisnickoIme+"'>"+data.korisnici[i].korisnickoIme+"</a>" +
        					"<p id='name'>Name: "+data.korisnici[i].ime+"</p>" + 
        					"<p id='surname'>Surname: "+data.korisnici[i].prezime+"</p>" +
        					"<p id='email'>Email: "+data.korisnici[i].email+"</p>" +
        					"<p id='role'>Role: "+data.korisnici[i].uloga+"</p>" +
        					"<p><button class='button'>Delete</button></p>" +
        					"<p><button class='button'>Blocked</button></p>" +
      					"</div>" +
    				"</div>" +
  				"</div>");
		}
				
	});		
	
});

function f(){
	var input = $('#search').val().toUpperCase();
	$(".column").each(function(){
		  if($(this).html().toUpperCase().includes(input)){
		    $(this).show();
		  }
		  else{
			$(this).hide();
		  }
	});
}