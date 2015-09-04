(function($) {
$(document).ready(function(){
	$(".b-set-fields .b-photo").each(function() {
		var fileVal = $(this).children('.b-download-mask_real-file-input').find('input').val();
		if (fileVal==undefined){
			$(this).children(".b-download-mask").find('.b-delete-photo_input-delete').hide();
			$(this).children(".b-download-mask").find('.b-delete-photo').hide();
		}
	})
	})

})(jQuery);