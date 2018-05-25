$(document).ready(function(e) {
	//url username
	var korisnickoIme = window.location.search.slice(1).split('&')[0].split('=')[1];
	var glavniDiv = $(".glavniDiv");
	var userDiv = $(".column");
	var blok = $(".block");
	var linkNav = $("#linkovi");
	var bars = $('#myDropdown');
	var buttonAdd = $('.new');
	var subDiv = $("#subProfile");
	var izaberiSub = $('#naslovSubb');
	var closeSubb = $('#closeSub');
	var returnHome = $('#returnHome');
	
	$.get('KorisnikServlet',{'korisnickoIme':korisnickoIme},function(data){
		for(i in data.videos){
			
			
		glavniDiv.append("<div class='maliDiv'> " + 
      					"<div>" + 
      					"<p><img src="+data.videos[i].slicica+"></p>" +
      					"<a href='video.html?id="+data.videos[i].id+"' style='border:none; background:none; cursor:pointer'><p id='videoName'>"+data.videos[i].nazivVideo+"</p></a>" +
      					"<p id='brPregleda'>"+data.videos[i].brojPregleda+" views</p>" +
      					"<p id='dateOfCreateUser'>Date: "+data.videos[i].datumKreiranja+"</p>" +
      					"</div>" +
      					"<div class='brisanje'>" +
      					"<button class='butDelete' name="+data.videos[i].id+"><i class='fa fa-trash'></i> Delete</button>"+
      					"</div>" +
	  			"</div>");
		$('.brisanje').hide();
		}
		
		buttonAdd.hide();
			
		userDiv.append("<p><img id='slika' src="+data.vlasnik.slicica+"></p>" + 
				"<a class='username' href='user.html?username="+data.vlasnik.korisnickoIme+"'><p>"+data.vlasnik.korisnickoIme+"<p></a>" +
				"<div class='subscribe'>" +
					"<button id='btnsub'></button>" +
				"</div>" +
				"<div class='pregled'>" +
					"<p id='subFollow'>Number of followers: "+data.vlasnik.brojPratioca+"</p>" +
					"<p class='dateR'>Registered: "+data.vlasnik.datumRegistracije+"</p>" +
				"</div>");
		
		$("#kaoOpis").append("Description: "+data.vlasnik.opis+"");
		
		
		if(data.vlasnik.blokiran == true){
			blok.text("Blocked");
		}else{
			blok.text("Not blocked");
		}
		
		//kad ucita stranicu
		if(data.isSubscribe == false){
			$('#btnsub').text("Subscribe");
		}else{
			$('#btnsub').text("Unsubscribe");
		}
		
		for(j in data.subskrajberi){
			subDiv.append("<div class='subb'>" +
										"<p><img src="+data.subskrajberi[j].slicica+"></p>" +
										"<a class='subUser' href='user.html?korisnickoIme="+data.subskrajberi[j].korisnickoIme+"'><p>"+data.subskrajberi[j].korisnickoIme+"</p></a>" +
										"<p class='viewsSub'>Subscriber's: "+data.subskrajberi[j].brojPratioca+"</p>" +
									"</div>");
		}
		
		izaberiSub.on("click", function(event){
			subDiv.show();
			closeSubb.show();
		});
		
		closeSubb.on("click", function(event){
			subDiv.hide();	
			closeSubb.hide();
		});	
		
			
		if(data.logovani != null){
			linkNav.append('<a href="pocetna.html"><i class="fa fa-home"></i> Home</a> <a href="LogOutServlet"><i class="fa fa-angle-double-down"></i> Log Out </a> <a href="user.html?korisnickoIme='+data.logovani.korisnickoIme+'"><i class="fa fa-user-o"></i> Profile </a>');
			bars.append(
					"<a href='editUser.html?korisnickoIme="+data.vlasnik.korisnickoIme+"'><i class='fa fa-edit'></i> Edit profile</a>" +
					"<a href='addVideo.html?korisnickoIme="+data.logovani.korisnickoIme+"'><i class='fa fa-plus-square-o'></i> Add video</a>");
			
			if(data.logovani.uloga == "ADMINISTRATOR"){
				bars.append("<a id='adminPage' href='admin.html?korisnickoIme="+data.logovani.korisnickoIme+"'><i class='fa fa-user-o'></i> Admin page</a>");
			}
			
			if (data.logovani.uloga == "ADMINISTRATOR" || data.logovani.korisnickoIme == data.vlasnik.korisnickoIme){
				buttonAdd.show();
				$('.brisanje').show();
				$('.maliDiv button').on('click',function(event){
					
					var videoId=$(this).attr('name');
					$.post('VideoPageServlet',{'status':'brisanje','videoId':videoId},function(data){
						window.location.reload(true);
					});
					event.preventDefault;
					return false;
				});
			}
			
			$('#btnsub').on("click",function(event){
				var korisnik=data.vlasnik.korisnickoIme;
				var subskrajber=data.logovani.korisnickoIme;
				$.get('SubscribeServlet',{'korisnik':korisnik,'subskrajber':subskrajber},function(data){
						$('#subFollow').text("Number of followers:" + data.brojSubova);
						//kad klikne na dugme
						if(data.status == "Unsubscribe"){
							$('#btnsub').text("Subscribe");
						}else{
							$('#btnsub').text("Unsubscribe");
						}
						
				});
				event.preventDefault;
				return false;
			});
			
			if(data.logovani.blokiran == true){
				buttonAdd.hide();
				$('.brisanje').hide();
			}
			
			if(data.vlasnik.blokiran == true){
				$('.column').hide();
				$('#kaoOpis').hide();
				$('.glavniDiv').hide();
				$("#overlay").fadeIn();
				$('#subProfile').hide();
				$('#subMargin').hide();
				$('#sortDiv').hide();
				$('.block').hide();
			}
			
			if( data.vlasnik.blokiran == true){
				if(data.logovani != null){
					if(data.logovani.korisnickoIme == data.vlasnik.korisnickoIme || data.logovani.uloga == "ADMINISTRATOR"){
						$('.column').show();
						$('#kaoOpis').show();
						$('.glavniDiv').show();
						$("#overlay").hide();
						returnHome.hide();
					}
				}
			}
		}
		
		if(data.logovani == null){
			linkNav.append('<a href="pocetna.html"><i class="fa fa-home"> Home </a>');
			
			if(data.vlasnik.blokiran == true){
				$('.column').hide();
				$('#kaoOpis').hide();
				$('.glavniDiv').hide();
				$("#overlay").fadeIn();
				returnHome.hide();
			}
			
			$(document).on('click',"#btnsub", function(event){
				alert("First, you must sign up!")
			});
			
			$('#subMargin').hide();
		}
		
	});
	
$(document).on('click',"#okSort", function(event){
		
		var ascDesc=$(".ascDesc").val();
		var sortBy=$(".nameSort").val();
		console.log(ascDesc);
		console.log(sortBy);
		if(ascDesc =="Ascending"){
			if(sortBy == "brojPregleda"){
				sortBrojPregledaA();
			}else if(sortBy == "date"){
				sortDatumKreiranjaA();
			}
		}else{
			if(sortBy == "brojPregleda"){
				sortBrojPregledaD();
			}else if(sortBy == "date"){
				sortDatumKreiranjaD();
			}	
		}
		event.preventDefault;
		return false;
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

function sortBrojPregledaA(){
	 $('.maliDiv').sort(function(a, b) {
		 if (parseInt($(a).find('#brPregleda').text()) < parseInt($(b).find('#brPregleda').text())) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.glavniDiv');
}

function sortBrojPregledaD(){
	 $('.maliDiv').sort(function(a, b) {
		 if (parseInt($(a).find('#brPregleda').text()) > parseInt($(b).find('#brPregleda').text())) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.glavniDiv');
}

function sortDatumKreiranjaA(){
	 $('.maliDiv').sort(function(a, b) {
		  if (Date($(a).find('#dateOfCreateUser').text()) < Date($(b).find('#dateOfCreateUser').text())) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.glavniDiv');
}

function sortDatumKreiranjaD(){
	 $('.maliDiv').sort(function(a, b) {
		  if (Date($(a).find('#dateOfCreateUser').text()) > Date($(b).find('#dateOfCreateUser').text())) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.glavniDiv');
}