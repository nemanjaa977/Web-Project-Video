$(document).ready(function(){
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	var nav = $('.navBar');
	var blockDiv = $('.blockVideo');
	var descript = $('#descrip');
	var checkPublic = $('#checkPublic');
	var checkPrivate = $('#checkPrivate');
	var checkUnlisted = $('#checkUnlisted');
	var yesComm = $('#yesComm');
	var noComm = $('#noComm');
	var yesRating = $('#yesRating');
	var noRating = $('#noRating');
	var yesBlock = $('#yesBlock');
	var noBlock = $('#noBlock');
	
	$.get('VideoPageServlet',{'id':id},function(data){
		
		console.log(data);

		nav.append('<a href="pocetna.html"><i class="fa fa-home"></i> Home</a> <a href="user.html?korisnickoIme='+data.logovani.korisnickoIme+'"><i class="fa fa-user-o"></i> Profile</a>');
		
		if(data.logovani.uloga != 'ADMINISTRATOR'){
			blockDiv.hide();
		}
		
		if(data.videos.vidljivost == "PUBLIC"){
			checkPublic.prop("checked",true);
		}else if(data.videos.vidljivost == "PRIVATE"){
			checkPrivate.prop("checked",true);
		}else{
			checkUnlisted.prop("checked",true);
		}
		
		if(data.videos.dozvoljeniKomentari == true){
			yesComm.prop("checked",true);
		}else{
			noComm.prop("checked",false);
		}
		
		if(data.videos.rejtingVidljivost == true){
			yesRating.prop("checked",true);
		}else{
			noRating.prop("checked",false);
		}
		
		if(data.videos.blokiran == true){
			yesBlock.prop("checked",true);
		}else{
			noBlock.prop("checked",false);
		}
		
		descript.val(data.videos.opis);
		
		$('#editSubmitVideo').on('click',function(event){

			var editedDescription = descript.val();
			var vid = "PUBLIC";
			if(checkPrivate.is(':checked')){
				vid = "PRIVATE";
			}else if(checkUnlisted.is(':checked')){
				vid = "UNLISTED"
			}
			
			var comments = true;
			if(noComm.is(':checked')){
				comments = false;
			}
			
			var rating = true;
			if(noRating.is(':checked')){
				rating = false;
			}
			
			var block = true;
			if(noBlock.is(':checked')){
				block = false;
			}

			var params={
					'status':'izmena',
					'editedDescription':editedDescription,
					'id': id,
					'vid': vid,
					'comments': comments,
					'rating': rating,
					'block': block
			};
			
			$.post('VideoPageServlet',params,function(data){
				if(data.status=="success"){
					var location="video.html?id="+id;
					window.location.replace(location);
				}
			});
			event.preventDefault();
			return false;
		});
		
	});

});