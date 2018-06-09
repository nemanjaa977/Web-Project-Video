$(document).ready(function(){
	var video=$('#myVideo');
	var videoName=$('#videoName');
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	var pregled = $('#numberVievs');
	var like = $('#likeNumber');
	var dislike = $('#dislikeNumber');
	var lajkic = $('#lajkic');
	var dislajkic = $('#dislajkic');
	var datum = $('#date');
	var vlasnikIme = $('#username');
	var opiss = $('#description');
	var nav = $(".navBar");
	var slika = $("#slika");
	var subscribe = $('#subscribe');
	var divSaKomentarima = $('#divKomentari');
	var addKomentar=$('#comButton');
	var lajkKomentar = $("#lajkKomentarr");
	var dislajkKomentar = $("#dislajkKomentarr");
	var ispisLajkKomentar = $("#lajkKomentaraa");
	var ispisDislajkKomentar = $("#dislajkKomentaraa");	
	
	$.get('VideoPageServlet',{'id':id},function(data){
//		video.attr("src",data.videos.videoURL+"?rel=0&autoplay=1");
		videoName.text(data.videos.nazivVideo);
		pregled.text(data.videos.brojPregleda + " views");
		like.text(data.videos.brojLike);	
		dislike.text(data.videos.brojDislike);
		slika.attr("src",data.videos.vlasnik.slicica);
		vlasnikIme.text(data.videos.vlasnik.korisnickoIme);
		vlasnikIme.attr("href", "user.html?korisnickoIme="+data.videos.vlasnik.korisnickoIme)
		datum.text("Published: " + data.videos.datumKreiranja);
		opiss.text(data.videos.opis);
		
		if(data.videos.blokiran == false){
			video.attr("src",data.videos.videoURL+"?rel=0&autoplay=1");
		}else{
			video.attr("src",data.videos.videoURL);
		}
			
		
		if(data.videos.rejtingVidljivost == false){
			like.hide();
			dislike.hide();
			lajkic.hide();
			dislajkic.hide();
		}
		
		if(data.videos.dozvoljeniKomentari == false){
			$('#sortDiv').hide();
			$('#divKomentari').hide();
			$('.comentar').hide();
		}
		
		
		//kad ucita stranicu
		if(data.isSubscribe == false){
			subscribe.val("Subscribe");
		}else{
			subscribe.val("Unsubscribe");
		}
		
		for(i in data.komentarii){
			if(data.komentarii[i].vlasnik != null){
				divSaKomentarima.append("<div class='allComment'>" +
											"<div class='oneC'>" +
												"<p><img id='slika' src="+data.komentarii[i].vlasnik.slicica+"></p>" +
												"<a id='korImeKom' href='user.html?korisnickoIme="+data.komentarii[i].vlasnik.korisnickoIme+"'>"+data.komentarii[i].vlasnik.korisnickoIme+"</a>" +
											"</div>" +
											"<div class='twoCom' id='"+data.komentarii[i].id+"'>" +
												"<p id='datummKreiranja'>Creation date: "+data.komentarii[i].datumKreiranja+"</p>" +
												"<p id='problem'>"+data.komentarii[i].sadrzaj+"</p>" +
											"<div class='likeX'>" +
												"<button name='likeCoom' value="+data.komentarii[i].id+"><i class='fa fa-thumbs-o-up' id='lajkKomentarr'></i></button>" +
												"<p id='lajkKomentaraa' class="+data.komentarii[i].id+">"+data.komentarii[i].brojLike+"</p>" +
												"<button name='dislikeCoom' value="+data.komentarii[i].id+"><i class='fa fa-thumbs-o-down' id='dislajkKomentarr'></i></button>" +
												"<p id='dislajkKomentaraa' class="+data.komentarii[i].id+">"+data.komentarii[i].brojDislike+"</p>" +
											"</div>" +
											"</div>" +
											"<div id='deleteCom'>" +
												"<input type='button' value='Edit' id='izmenaComentar' name="+data.komentarii[i].id+"><br>" +
												"<input type='button' value='Delete' id='brisanjeComentar' name="+data.komentarii[i].id+">" +
											"</div>" +
										"</div>");
			
			}
		}
		
		if(data.logovani != null){
			nav.append("<a class='active' href='pocetna.html'><i class='fa fa-home'></i> Home</a>" +
					"<a href='user.html?korisnickoIme="+data.logovani.korisnickoIme+"'><i class='fa fa-user-o'></i> Profile</a>" +  
					"<a id='editt' href='editVideo.html?id="+data.videos.id+"'><i class='fa fa-edit'></i> Edit video</a>" +
					"<a href='LogOutServlet'><i class='fa fa-angle-double-down'></i> Log Out </a>");  
			
			if(data.logovani.blokiran == true){
				$('#editt').hide();
			}
			
			if(data.logovani.korisnickoIme != data.videos.vlasnik.korisnickoIme){
				$('#editt').hide();
			}
			
			if(data.logovani.korisnickoIme == data.videos.vlasnik.korisnickoIme){
				$('#subscribe').hide();
				like.show();	
				dislike.show();
				lajkic.show();
				dislajkic.show();
				$('#sortDiv').show();
				$('#divKomentari').show();
				$('.comentar').show();
			}
			
			if(data.logovani.uloga == "ADMINISTRATOR"){
				like.show();	
				dislike.show();
				lajkic.show();
				dislajkic.show();
				$('#sortDiv').show();
				$('#divKomentari').show();
				$('.comentar').show();
				$('#editt').show();
				$('.photosSub').show();
				$('.dataVideo').show();
				$('#videoPlayer').show();
			}
			
			addKomentar.on('click',function(event){
				if(data.videos.blokiran == true){
					if(data.logovani.korisnickoIme == data.videos.vlasnik.korisnickoIme){
						alert("You are blocked!")
						event.preventDefault();
						return false;
					}else{
						var komentar=$('#commentContext').val();
						
						$.post('KomentarServlet',{'id':data.videos.id,'komentar':komentar,'status':"dodavanje"},function(data){
							divSaKomentarima.append("<div class='allComment'>" +
									"<div class='oneC'>" +
										"<p><img id='slika' src="+data.komentarii.vlasnik.slicica+"></p>" +
										"<a id='korImeKom' href='user.html?korisnickoIme="+data.komentarii.vlasnik.korisnickoIme+"'>"+data.komentarii.vlasnik.korisnickoIme+"</a>" +
									"</div>" +
									"<div class='twoCom' id='"+data.komentarii.id+"'>" +
										"<p id='datummKreiranja'>Creation date: "+data.komentarii.datumKreiranja+"</p>" +
										"<p id='problem'>"+data.komentarii.sadrzaj+"</p>" +
									"<div class='likeX'>" +
									"<button name='likeCoom' value="+data.komentarii.id+"><i class='fa fa-thumbs-o-up' id='lajkKomentarr'></i></button>" +
										"<p id='lajkKomentaraa' class="+data.komentarii.id+">"+data.komentarii.brojLike+"</p>" +
										"<button nanme='dislikeCoom' value="+data.komentarii.id+"><i class='fa fa-thumbs-o-down' id='dislajkKomentarr'></i></button>" +
										"<p id='dislajkKomentaraa' class="+data.komentarii.id+">"+data.komentarii.brojDislike+"</p>" +
									"</div>" +
									"</div>" +
									"<div id='deleteCom'>" +
										"<input type='button' value='Edit' id='izmenaComentar' name="+data.komentarii.id+"><br>" +
										"<input type='button' value='Delete' id='brisanjeComentar' name="+data.komentarii.id+">" +
									"</div>" +
								"</div>");
									
					});		
						event.preventDefault();
						return false;
					}
				}else{
					var komentar=$('#commentContext').val();
					
					$.post('KomentarServlet',{'id':data.videos.id,'komentar':komentar,'status':"dodavanje"},function(data){
						divSaKomentarima.append("<div class='allComment'>" +
								"<div class='oneC'>" +
									"<p><img id='slika' src="+data.komentarii.vlasnik.slicica+"></p>" +
									"<a id='korImeKom' href='user.html?korisnickoIme="+data.komentarii.vlasnik.korisnickoIme+"'>"+data.komentarii.vlasnik.korisnickoIme+"</a>" +
								"</div>" +
								"<div class='twoCom' id='"+data.komentarii.id+"'>" +
									"<p id='datummKreiranja'>Creation date: "+data.komentarii.datumKreiranja+"</p>" +
									"<p id='problem'>"+data.komentarii.sadrzaj+"</p>" +
								"<div class='likeX'>" +
								"<button name='likeCoom' value="+data.komentarii.id+"><i class='fa fa-thumbs-o-up' id='lajkKomentarr'></i></button>" +
									"<p id='lajkKomentaraa' class="+data.komentarii.id+">"+data.komentarii.brojLike+"</p>" +
									"<button nanme='dislikeCoom' value="+data.komentarii.id+"><i class='fa fa-thumbs-o-down' id='dislajkKomentarr'></i></button>" +
									"<p id='dislajkKomentaraa' class="+data.komentarii.id+">"+data.komentarii.brojDislike+"</p>" +
								"</div>" +
								"</div>" +
								"<div id='deleteCom'>" +
									"<input type='button' value='Edit' id='izmenaComentar' name="+data.komentarii.id+"><br>" +
									"<input type='button' value='Delete' id='brisanjeComentar' name="+data.komentarii.id+">" +
								"</div>" +
							"</div>");
								
				});		
					event.preventDefault();
					return false;
				}
				
			});
			
//			$("#overlay").append("<p id='titleEdit'>Edit description</p>" +
//									"<input type='text' id='editDescription'>" +
//									"<input type='button' value='Ok' id='okEditButton'>" +
//									"<input type='button' value='Cancel' id='cancelEditDescription'>");
			
			if(data.videos.blokiran == true){
				$('.dataVide').hide();
				$('.photoSub').hide();
				$('.comentar').hide();
				$('#sortDiv').hide();
				$('#divKomentari').hide();
				$('#overlayBlocked').fadeIn();
				$('.photosSub').hide();
				$('.dataVideo').hide();
				$('#videoPlayer').hide();
				
			}
			
			if(data.videos.vlasnik.blokiran == true && data.logovani.uloga != "ADMINISTRATOR"){
				$('#overlayBlocked').fadeIn();
				$('.dataVide').hide();
				$('.photoSub').hide();
				$('.comentar').hide();
				$('#sortDiv').hide();
				$('#divKomentari').hide();
				$('.photosSub').hide();
				$('.dataVideo').hide();
				$('#videoPlayer').hide();
				video.attr("src",data.videos.videoURL);
			}
			
			if(data.videos.blokiran == true){
				if(data.logovani != null){
					if(data.logovani.korisnickoIme == data.videos.vlasnik.korisnickoIme || data.logovani.uloga == 'ADMINISTRATOR'){
						$('#overlayBlocked').hide();
						like.show();	
						dislike.show();	
						lajkic.show();
						dislajkic.show();
						$('#sortDiv').show();
						$('#divKomentari').show();
						$('.comentar').show();
						
						$('.photosSub').show();
						$('.dataVideo').show();
						$('#videoPlayer').show();
					}
				}
			}
			
			if(data.videos.blokiran == true){
				if(data.logovani.uloga != "ADMINISTRATOR"){
					$("#izmenaComentar").hide();
					$("#brisanjeComentar").hide();
				}
			}
			
			$('input[type=button]#izmenaComentar').on('click',function(event){
				var id = $(this).attr('name');
				var selectDescription = '#' + id + ' #problem';
				console.log(id);
				console.log(selectDescription);
				var selectDate = '#' + id + ' #datummKreiranja';
				var oldText = $(selectDescription).val();
				$("#editDescription").val(oldText);
				$("#overlay").fadeIn();
				
				
				$('#overlay #okEditButton').on('click', function(event){
					var textUpdate = $("#editDescription").val();
					$.post('KomentarServlet',{'id':id, 'status':"izmena", 'textUpdate':textUpdate},function(data){
						if(data.status == 'success'){
							var oldtext = $(selectDescription).text(textUpdate);
							var oldDate = $(selectDate).text(data.noviDatum);
							$('#overlay').fadeOut();
							location.reload();
						}
					});
				});
				
				$("#overlay #cancelEditDescription").on('click', function(event){
					$('#overlay').fadeOut();
				});
				
				event.preventDefault();
				return false;
			});
			
			
			lajkic.on('click',function(event){		
				if(data.videos.blokiran == true){
					if(data.logovani.korisnickoIme == data.videos.vlasnik.korisnickoIme){
						alert("You are blocked!");
						
					}
					else{
						console.log("Zahtev otisao");
						$.get('LikeDislikeServlet',{'id':data.videos.id},function(data){
							like.text(data.brojLajka);
							dislike.text(data.brojDislajka);			
					});		
						
					}
				}else{
					console.log("Zahtev otisao");
					$.get('LikeDislikeServlet',{'id':data.videos.id},function(data){
						like.text(data.brojLajka);
						dislike.text(data.brojDislajka);			
				});		
					
				}
				event.preventDefault();
				return false;
				});
				
				
			
			dislajkic.on('click',function(event){
				if(data.videos.blokiran == true){
					if(data.logovani.korisnickoIme == data.videos.vlasnik.korisnickoIme){
						alert("You are blocked!");
						
						event.preventDefault();
						return false;
					}else{
						$.post('LikeDislikeServlet',{'id':data.videos.id},function(data){
							like.text(data.brojLajka);
							dislike.text(data.brojDislajka);			
					});		
						event.preventDefault();
						return false;
					}
				}else{
					$.post('LikeDislikeServlet',{'id':data.videos.id},function(data){
						like.text(data.brojLajka);
						dislike.text(data.brojDislajka);			
				});		
					event.preventDefault();
					return false;
				}
				});
			
			$('button').on('click',function(event){
				if(data.videos.blokiran == true){
					if(data.logovani.korisnickoIme == data.videos.vlasnik.korisnickoIme){
						alert("You are blocked!");
						
					}else{
						var id=$(this).val();
						var name = $(this).attr("name");
						if(name == 'likeCoom'){
										
							$.get('KomentarLikeDislikeServlet',{'id':id},function(data){
								var selectLike='#lajkKomentaraa.'+id;
								var selectDislike='#dislajkKomentaraa.'+id;
								console.log(selectLike);
								$(selectLike).text(data.brojLike);
								$(selectDislike).text(data.brojDislike);
							});
							event.preventDefault();
							return false;
						}else{				
						$.post('KomentarLikeDislikeServlet',{'id':id},function(data){
							var selectLike='#lajkKomentaraa.'+id;
							var selectDislike='#dislajkKomentaraa.'+id;
							console.log(selectLike);
							$(selectLike).text(data.brojLike);
							$(selectDislike).text(data.brojDislike);
						});
						event.preventDefault();
						return false;
						}
					}
				}else{
					var id=$(this).val();
					var name = $(this).attr("name");
					if(name == 'likeCoom'){
									
						$.get('KomentarLikeDislikeServlet',{'id':id},function(data){
							var selectLike='#lajkKomentaraa.'+id;
							var selectDislike='#dislajkKomentaraa.'+id;
							console.log(selectLike);
							$(selectLike).text(data.brojLike);
							$(selectDislike).text(data.brojDislike);
						});
						event.preventDefault();
						return false;
					}else{				
					$.post('KomentarLikeDislikeServlet',{'id':id},function(data){
						var selectLike='#lajkKomentaraa.'+id;
						var selectDislike='#dislajkKomentaraa.'+id;
						console.log(selectLike);
						$(selectLike).text(data.brojLike);
						$(selectDislike).text(data.brojDislike);
					});
					event.preventDefault();
					return false;
					}
				}
					
			});
			
			$('#subscribe').on("click",function(event){
				var korisnik = data.videos.vlasnik.korisnickoIme;
				var subskrajber = data.logovani.korisnickoIme;
				$.get('SubscribeServlet',{'korisnik':korisnik,'subskrajber':subskrajber},function(data){
						if(data.status == "Unsubscribe"){
							$('#subscribe').val("Subscribe");
						}else{
							$('#subscribe').val("Unsubscribe");
						}
						
				});
				event.preventDefault;
				return false;
			});
			
			if(data.logovani.blokiran == false){
				$('#deleteCom #brisanjeComentar').on('click',function(event){	
					var id = $(this).attr('name');
					
					$.post('KomentarServlet',{'id':id,'status':"brisanje"},function(data){
						window.location.reload(true);
					});
					event.preventDefault();
					return false;
				});
			}
			
		}
		
		if(data.logovani == null){
			nav.append('<a class="active" href="pocetna.html"><i class="fa fa-home"></i> Home</a>');
			
			$(document).on('click',"#lajkic", function(event){
				alert("First, you must sign up!")
			});
			$(document).on('click',"#dislajkic", function(event){
				alert("First, you must sign up!")
			});
			$(document).on('click',"#subscribe", function(event){
				alert("First, you must sign up!")
			});
			$(document).on('click',"#izmenaComentar", function(event){
				alert("First, you must sign up!")
			});
			$(document).on('click',"#brisanjeComentar", function(event){
				alert("First, you must sign up!")
			});
			
			$('.comentar').hide();
		}
		
	});
});

$(document).on('click',"#okSort", function(event){
	
	var ascDesc=$(".ascDesc").val();
	var sortBy=$(".nameSort").val();
	console.log(ascDesc);
	console.log(sortBy);
	if(ascDesc =="Ascending"){
		if(sortBy == "viewsCom"){
			sortBrojLajkovaA();
		}else if(sortBy == "disCom"){
			sortBrojDisLajkovaA()
		}else if(sortBy == "dateCom"){
			sortDatumKreiranjaA()
		}
	}else{
		if(sortBy == "viewsCom"){
			sortBrojLajkovaD();
		}else if(sortBy == "disCom"){
			sortBrojDisLajkovaD()
		}else if(sortBy == "dateCom"){
			sortDatumKreiranjaD()
		}	
	}
	event.preventDefault;
	return false;
});

function sortBrojLajkovaA(){
	 $('.allComment').sort(function(a, b) {
		  if (parseInt($(a).find('#lajkKomentaraa').text()) < parseInt($(b).find('#lajkKomentaraa').text())) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('#divKomentari');
}

function sortBrojLajkovaD(){
	 $('.allComment').sort(function(a, b) {
		  if (parseInt($(a).find('#lajkKomentaraa').text()) > parseInt($(b).find('#lajkKomentaraa').text())) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('#divKomentari');
}

function sortBrojDisLajkovaA(){
	 $('.allComment').sort(function(a, b) {
		  if (parseInt($(a).find('#dislajkKomentaraa').text()) < parseInt($(b).find('#dislajkKomentaraa').text())) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.#divKomentari');
}

function sortBrojDisLajkovaD(){
	 $('.allComment').sort(function(a, b) {
		  if (parseInt($(a).find('#dislajkKomentaraa').text()) > parseInt($(b).find('#dislajkKomentaraa').text())) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('.#divKomentari');
}

function sortDatumKreiranjaA(){
	 $('.allComment').sort(function(a, b) {
		  if (Date($(a).find('#datummKreiranja').text()) < Date($(b).find('#datummKreiranja').text())) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('#divKomentari');
}

function sortDatumKreiranjaD(){
	 $('.allComment').sort(function(a, b) {
		  if (Date($(a).find('#datummKreiranja').text()) > Date($(b).find('#datummKreiranja').text())) {
		    return -1;
		  } else {
		    return 1;
		  }
	}).appendTo('#divKomentari');
}
