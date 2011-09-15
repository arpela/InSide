$(function() {  
	if ($('#overlay').length > 0) {   
		$('#overlay').fadeIn('fast',function(){
			$('#box').animate({'top':'100px'},500);
		});   
		$('.popcerrar').click(function(){
			$('#box').animate({'top':'-600px'},500,function(){
				$('#overlay').fadeOut('fast');
				$('#pop_cmd').load('deshabilitar_popup.php');
			});
		});
		$('#reg').click(function(){
			$('#box').animate({'top':'-600px'},500,function(){
				$('#overlay').fadeOut('fast');
				$('#pop_cmd').load('deshabilitar_popup.php?email='+$('#pop_email').val());
			});
		});		
	}
});
 
