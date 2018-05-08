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
	
	$.get('VideoPageServlet',{'id':id},function(data){
		video.attr("src",data.videos.videoURL+"?rel=0&autoplay=1");
		videoName.text(data.videos.nazivVideo);
		pregled.text(data.videos.brojPregleda + " views");
		like.text(data.videos.brojLike);								
		dislike.text(data.videos.brojDislike);
		
		slika.attr("src",data.videos.vlasnik.slicica);
		vlasnikIme.text(data.videos.vlasnik.korisnickoIme);
		vlasnikIme.attr("href", "user.html?korisnickoIme="+data.videos.vlasnik.korisnickoIme)
		datum.text("Published: " + data.videos.datumKreiranja);
		opiss.text(data.videos.opis);
		
		//kad ucita stranicu
		if(data.isSubscribe == false){
			subscribe.text("Subscribe");
		}else{
			subscribe.text("Unsubscribe");
		}
		
		for(i in data.komentarii){
			if(data.komentarii[i].vlasnik != null){
				divSaKomentarima.append("<div class='allComment'>" +
											"<div class='oneC'>" +
												"<p><img id='slika' src="+data.komentarii[i].vlasnik.slicica+"></p>" +
												"<a id='korImeKom' href='user.html?korisnickoIme="+data.komentarii[i].vlasnik.korisnickoIme+"'>"+data.komentarii[i].vlasnik.korisnickoIme+"</a>" +
											"</div>" +
											"<div class='twoCom'>" +
												"<p>Creation date: "+data.komentarii[i].datumKreiranja+"</p>" +
												"<p id='problem'>"+data.komentarii[i].sadrzaj+"</p>" +
											"<div class='likeX'>" +
												"<i class='fa fa-thumbs-o-up'></i>" +
												"<p id='likeNumber'>"+data.komentarii[i].brojLike+"</p>" +
												"<i class='fa fa-thumbs-o-down'></i>" +
												"<p id='dislikeNumber'>"+data.komentarii[i].brojDislike+"</p>" +
											"</div>" +
											"</div>" +
										"</div>");
			}
		}
		
		if(data.logovani != null){
			nav.append("<a class='active' href='pocetna.html'><i class='fa fa-home'></i> Home</a>" +
					"<a href='user.html?korisnickoIme="+data.logovani.korisnickoIme+"'><i class='fa fa-user-o'></i> Profile</a>" +  
					"<a id='editt' href='editVideo.html?id="+data.videos.id+"'><i class='fa fa-edit'></i> Edit video</a>");  
			
			if(data.logovani.blokiran == true){
				$('#editt').hide();
			}
			
			addKomentar.on('click',function(event){
				
				var komentar=$('#commentContext').val();
				
				$.post('KomentarServlet',{'id':data.videos.id,'komentar':komentar},function(data){
					divSaKomentarima.append("<div class='allComment'>" +
							"<div class='oneC'>" +
								"<p><img id='slika' src="+data.komentarii.vlasnik.slicica+"></p>" +
								"<a id='korImeKom' href='user.html?korisnickoIme="+data.komentarii.vlasnik.korisnickoIme+"'>"+data.komentarii.vlasnik.korisnickoIme+"</a>" +
							"</div>" +
							"<div class='twoCom'>" +
								"<p>Creation date: "+data.komentarii.datumKreiranja+"</p>" +
								"<p id='problem'>"+data.komentarii.sadrzaj+"</p>" +
							"<div class='likeX'>" +
								"<i class='fa fa-thumbs-o-up'></i>" +
								"<p id='likeNumber'>"+data.komentarii.brojLike+"</p>" +
								"<i class='fa fa-thumbs-o-down'></i>" +
								"<p id='dislikeNumber'>"+data.komentarii.brojDislike+"</p>" +
							"</div>" +
							"</div>" +
						"</div>");
					
					komentar.val("");
							
			});		
				event.preventDefault();
				return false;
				
			});
			
			lajkic.on('click',function(event){		
				$.get('LikeDislikeServlet',{'id':data.videos.id},function(data){
						like.text(data.brojLajka);
						dislike.text(data.brojDislajka);			
				});		
					event.preventDefault();
					return false;
				});
			
			dislajkic.on('click',function(event){		
				$.post('LikeDislikeServlet',{'id':data.videos.id},function(data){
						like.text(data.brojLajka);
						dislike.text(data.brojDislajka);			
				});		
					event.preventDefault();
					return false;
				});
			
			$('#subscribe').on("click",function(event){
				var korisnik = data.videos.vlasnik.korisnickoIme;
				var subskrajber = data.logovani.korisnickoIme;
				$.get('SubscribeServlet',{'korisnik':korisnik,'subskrajber':subskrajber},function(data){
						if(data.status == "Unsubscribe"){
							$('#subscribe').text("Subscribe");
						}else{
							$('#subscribe').text("Unsubscribe");
						}
						
				});
				event.preventDefault;
				return false;
			});
		}
		
		if(data.logovani == null){
			nav.append('<a class="active" href="pocetna.html"><i class="fa fa-home"></i> Home</a>');

		}
		
	});
});
