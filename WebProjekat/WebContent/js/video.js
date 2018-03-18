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
	
	$.get('VideoPageServlet',{'id':id},function(data){
		video.attr("src",data.videos.videoURL+"?rel=0&autoplay=1");
		videoName.text(data.videos.nazivVideo);
		pregled.text(data.videos.brojPregleda + " views");
		like.text(data.videos.brojLike);
		dislike.text(data.videos.brojDislike);
		
		vlasnikIme.text(data.videos.vlasnik.korisnickoIme);
		datum.text("Published: " + data.videos.datumKreiranja);
		opiss.text(data.videos.opis);
	});
});
