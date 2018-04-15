$(document).ready(function(){
	var video=$('#myVideo');
	var videoName=$('#videoName');
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	var pregled = $('#numberVievs');
	var like = $('#likeNumber');
	var dislike = $('#dislikeNumber');
	var datum = $('#date');
	var vlasnikIme = $('#username');
	var opiss = $('#description');
	var nav = $(".navBar");

	
	$.get('VideoPageServlet',{'id':id},function(data){
		video.attr("src",data.videos.videoURL+"?rel=0&autoplay=1");
		videoName.text(data.videos.nazivVideo);
		pregled.text(data.videos.brojPregleda + " views");
		like.text(data.videos.brojLike);
		dislike.text(data.videos.brojDislike);
		
		vlasnikIme.text(data.videos.vlasnik.korisnickoIme);
		datum.text("Published: " + data.videos.datumKreiranja);
		opiss.text(data.videos.opis);
		
		if(data.logovani != null){
			nav.append("<a class='active' href='pocetna.html'><i class='fa fa-home'></i> Home</a>" +
					"<a href='user.html?korisnickoIme="+data.logovani.korisnickoIme+"'><i class='fa fa-user-o'></i> Profile</a>" +  
					"<a id='editt' href='editVideo.html?id="+data.videos.id+"'><i class='fa fa-edit'></i> Edit video</a>");  
			
			if(data.logovani.blokiran == true){
				$('#editt').hide();
			}
		}
		
		if(data.logovani == null){
			nav.append('<a class="active" href="pocetna.html"><i class="fa fa-home"></i> Home</a>');

		}
		
	});
});
