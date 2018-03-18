$(document).ready(function() {
	var userName = window.location.search.slice(1).split('&')[0].split('=')[1];
	var row = $('.row');
	var nav = $('.navBar');
	
	$.get('VideoServlet',{},function(data){
		for(i in data.videos){
			row.append("<div class='column' id='column'> " + 
  					"<p><img src="+data.videos[i].slicica+"></p>" + 
  					"<a href='user.html?username="+data.videos[i].vlasnik.korisnickoIme+"'><p id='usernamee'>"+data.videos[i].vlasnik.korisnickoIme+"</p></a>" +
  					"<a href='video.html?videoName="+data.videos[i].id+"' style='border:none; color:#00004d; background:none; cursor:pointer'><p id='videoName'>"+data.videos[i].nazivVideo+"</p></a>" +
  					"<p id='views'>"+data.videos[i].brojPregleda+"  views</p>" +
  					"<p>Date: "+data.videos[i].datumKreiranja+"</p>" +
  				"</div>");
		}
	});
	
	$.get('KorisnikServlet', {}, function(data){
		
		if(data.logovani != null){
			nav.append('<a href="pocetna.html"><i class="fa fa-home"></i> Home </a> <a href="user.html?userName='+data.logovani.korisnickoIme+'"><i class="fa fa-user-o"></i> Profile </a> <a href="LogOutServlet"><i class="fa fa-angle-double-down"></i> Log Out </a> <a class="logo">loVIDEO</a>');
		}
		
		if(data.logovani == null){
			nav.append('<a href="pocetna.html"><i class="fa fa-home"></i> Home </a> <a href="login.html"><i class="fa fa-unlock-alt"></i> Log in</a> <a href="register.html"><i class="fa fa-user"></i> Register</a> <a class="logo">loVIDEO</a>');
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