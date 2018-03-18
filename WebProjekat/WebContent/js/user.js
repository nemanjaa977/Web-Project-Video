$(document).ready(function(e) {
	//url username
	var korisnickoIme = window.location.search.slice(1).split('&')[0].split('=')[1];
	var glavniDiv = $(".glavniDiv");
	var userDiv = $(".column");
	var blok = $(".block");
	var linkNav = $("#linkovi");
	var bars = $('#myDropdown');
	var buttonAdd = $('.new');
	
	$.get('KorisnikServlet',{'korisnickoIme':korisnickoIme},function(data){
		for(i in data.videos){
			
			
		glavniDiv.append("<div class='maliDiv'> " + 
      					"<div>" + 
      					"<p><img src="+data.videos[i].slicica+"></p>" +
      					"<a href='video.html?videoName="+data.videos[i].id+"' style='border:none; background:none; cursor:pointer'><p id='videoName'>"+data.videos[i].nazivVideo+"</p></a>" +
      					"<p>"+data.videos[i].brojPregleda+" views</p>" +
      					"<p>Date: "+data.videos[i].datumKreiranja+"</p>" +
      					"</div>" +
      					"<div class='brisanje'>" +
      						"<button name="+data.videos[i].id+"><i class='fa fa-trash'></i> Delete</button>" +
      					"</div>" +
	  				"</div>");
		}
			
		userDiv.append("<p><img id='slika' src="+''+"></p>" +
				"<a class='username' href='user.html?username="+data.vlasnik.korisnickoIme+"'><p>"+data.vlasnik.korisnickoIme+"<p></a>" +
				"<div class='subscribe'>" +
					"<button id='btnsub'>Subscribe</button>" +
				"</div>" +
				"<div class='pregled'>" +
					"<p>Number of followers: "+data.vlasnik.brojPratioca+"</p>" +
					"<p class='dateR'>Registered: "+data.vlasnik.datumRegistracije+"</p>" +
				"</div>");
	
		
		if(data.vlasnik.blokiran == true){
			blok.text("Blocked");
		}else{
			blok.text("Not blocked");
		}
			
		if(data.logovani != null){
			linkNav.append('<a href="pocetna.html"><i class="fa fa-home"></i> Home</a> <a href="LogOutServlet"><i class="fa fa-angle-double-down"></i> Log Out </a> <a href="user.html?korisnickoIme='+data.logovani.korisnickoIme+'"><i class="fa fa-user-o"></i> Profile </a>');
			bars.append(
					"<a href='addVideo.html?korisnickoIme="+data.logovani.korisnickoIme+"'><i class='fa fa-plus-square-o'></i> Add video</a>");
			if(data.logovani.uloga == "ADMINISTRATOR"){
				bars.append("<a id='adminPage' href='admin.html?korisnickoIme="+data.logovani.korisnickoIme+"'><i class='fa fa-user-o'></i> Admin page</a>");
			}
			if (data.logovani.uloga == "ADMINISTRATOR" || data.logovani.korisnickoIme == data.vlasnik.korisnickoIme){
				$('.maliDiv button').on('click',function(event){
					
					var videoId=$(this).attr('name');
					$.post('VideoPageServlet',{'status':'brisanje','videoId':videoId},function(data){
						window.location.reload(true);
					});
					event.preventDefault;
					return false;
				});
			}
			
		}
		
		if(data.logovani == null){
			linkNav.append('<a href="pocetna.html"><i class="fa fa-home"> Home </a>');
			buttonAdd.hide();
		}
		
	});
	
});

function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function(event) {
	  if (!event.target.matches('.dropbtn')) {

	    var dropdowns = document.getElementsByClassName("dropdown-content");
	    var i;
	    for (i = 0; i < dropdowns.length; i++) {
	      var openDropdown = dropdowns[i];
	      if (openDropdown.classList.contains('show')) {
	        openDropdown.classList.remove('show');
	      }
	    }
	  }
	}