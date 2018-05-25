$(document).ready(function(e) {
	var korisnickoIme = window.location.search.slice(1).split('&')[0].split('=')[1];
	var nav = $(".navBar");
	var row = $(".row");
	var blok = $(".buttonn");
	
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
        					"<p><button class='button' name="+data.korisnici[i].korisnickoIme+"><i class='fa fa-trash'></i> Delete</button></p>" +
        					"<p><button class='buttonn' name="+data.korisnici[i].korisnickoIme+">Block</button></p>" +
        					"<p><button class='buttonnn' name="+data.korisnici[i].korisnickoIme+">Unblock</button></p>" +
      					"</div>" +
    				"</div>" +
  				"</div>");
			var name=data.korisnici[i].korisnickoIme;
			if(data.korisnici[i].blokiran == true){
				$("button.buttonn[name="+name+"]").hide();
			}else{
				$("button.buttonnn[name="+name+"]").hide();
			}
		}
		
		
				
	});		
	
	$(document).on('click', ".container .button",function(event){	
		var korisnikIme = $(this).attr('name');
		$.post('KorisnikServlet',{'status':'brisanje','korisnikIme':korisnikIme},function(data){
			window.location.reload(true);
		});
		event.preventDefault;
		return false;
	});
	
	$(document).on('click', ".container .buttonn",function(event){	
		var korIme = $(this).attr('name');
		$.post('KorisnikServlet',{'status':'blokiranje','korIme':korIme},function(data){
			window.location.reload(true);
		});
		event.preventDefault;
		return false;
	});
	
	$(document).on('click', ".container .buttonnn",function(event){	
		var korIme = $(this).attr('name');
		$.post('KorisnikServlet',{'status':'odblokiranje','korIme':korIme},function(data){
			window.location.reload(true);
		});
		event.preventDefault;
		return false;
	});
	
	$(document).on('click',"#okSort", function(event){
		
		var ascDesc=$(".ascDesc").val();
		var sortBy=$(".nameSort").val();
		console.log(ascDesc);
		console.log(sortBy);
		if(ascDesc =="Ascending"){
			if(sortBy == "username"){
				sortUsernameA();
			}else if(sortBy == "name"){
				sortNameA();
			}else if(sortBy == "surname"){
				sortSurnameA();
			}else if(sortBy == "email"){
				sortEmailA();
			}else if(sortBy == "role"){
				sortRoleA();
			}
		}else{
			if(sortBy == "username"){
				sortUsernameD();
			}else if(sortBy == "name"){
				sortNameD();
			}else if(sortBy == "surname"){
				sortSurnameD();
			}else if(sortBy == "email"){
				sortEmailD();
			}else if(sortBy == "role"){
				sortRoleD();
			}
			
		}
		event.preventDefault;
		return false;
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

function sortUsernameA(){
	 $('.column').sort(function(a, b) {
		  if ($(a).find('#username').text() < $(b).find('#username').text()) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.row');
}

function sortNameA(){
	 $('.column').sort(function(a, b) {
		  if ($(a).find('#name').text() < $(b).find('#name').text()) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.row');
}

function sortSurnameA(){
	 $('.column').sort(function(a, b) {
		  if ($(a).find('#surname').text() < $(b).find('#surname').text()) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.row');
}

function sortEmailA(){
	 $('.column').sort(function(a, b) {
		  if ($(a).find('#email').text() < $(b).find('#email').text()) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.row');
}

function sortRoleA(){
	 $('.column').sort(function(a, b) {
		  if ($(a).find('#role').text() < $(b).find('#role').text()) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.row');
}

function sortUsernameD(){
	 $('.column').sort(function(a, b) {
		  if ($(a).find('#username').text() > $(b).find('#username').text()) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.row');
}

function sortNameD(){
	 $('.column').sort(function(a, b) {
		  if ($(a).find('#name').text() > $(b).find('#name').text()) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.row');
}

function sortSurnameD(){
	 $('.column').sort(function(a, b) {
		  if ($(a).find('#surname').text() > $(b).find('#surname').text()) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.row');
}

function sortEmailD(){
	 $('.column').sort(function(a, b) {
		  if ($(a).find('#email').text() > $(b).find('#email').text()) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.row');
}

function sortRoleD(){
	 $('.column').sort(function(a, b) {
		  if ($(a).find('#role').text() > $(b).find('#role').text()) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.row');
}