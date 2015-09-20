(function($) {
$(document).ready(function(){
	$(".b-set-fields .b-photo").each(function() {
		var fileVal = $(this).children('.b-download-mask__real-file-input').find('input').val();
		if (fileVal==undefined){
			$(this).children(".b-photo__download-mask").find('.b-delete-photo__input-delete').hide();
			$(this).children(".b-photo__download-mask").find('.b-delete-photo').hide();
		}
	})
	})

})(jQuery);