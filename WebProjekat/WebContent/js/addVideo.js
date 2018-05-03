$(document).ready(function(){
	var korisnickoIme = window.location.search.slice(1).split('&')[0].split('=')[1];
	var nav = $('.navBar');
	var submit = $('#addSubmit');
	var message = $('#message');
	var url = $('#videoUrl');
	var name = $('#videoName');
	var description = $('#descrip');
	var visibilityPrivate=$('input[value="private"]');
	var visibilityUnlisted=$('input[value="unlisted"]');
	var allowRatingNo = $('input[value="noRat"]');
	var allowCommNo = $('input[value="noComm"]');

	nav.append('<a href="user.html?korisnickoIme='+korisnickoIme+'"><i class="fa fa-user-o"></i> Profile</a> <a href="pocetna.html"><i class="fa fa-home"></i> Home</a>');
	
	submit.on('click',function(event){
		var URLValue = url.val();
		var NAMEValue = name.val();
		var DESValue = description.val();
		var allowRating=true;
		var allowComment=true;
		var visibility = "Public";
		
		if(visibilityPrivate.is(':checked') == true ){
			visibility="Private";
		}
		
		if(visibilityUnlisted.is(':checked') == true ){
			visibility="Unlisted";
		}
		
		if(allowRatingNo.is(':checked') == true){
			allowRating = false;
		}
		
		if(allowCommNo.is(':checked') == true){
			allowComment = false;
		}
		
		if(URLValue == "" || NAMEValue == ""){
			message.text("Url and name can't be empty!");
			return false;
		}
		
		var params={
				'URLValue':URLValue,
				'NAMEValue':NAMEValue,
				'DESValue':DESValue,
				'allowRating':allowRating,
				'allowComment':allowComment,
				'visibility':visibility,
				'status':'dodavanje'
		};
		
		$.post('VideoPageServlet',params,function(data){
			if(data.status == "success"){
				var location="user.html?korisnickoIme="+korisnickoIme;
				window.location.replace(location);
			}
		});
		
		event.preventDefault();
		return false;
	});
});