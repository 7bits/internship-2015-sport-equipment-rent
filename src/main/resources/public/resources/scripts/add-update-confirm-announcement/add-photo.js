(function($) {
    $('.b-download-mask__real-file-input').on('mouseenter mouseleave', function(){
    	$(this).prev('.b-download-mask').find('.b-photo').toggleClass('hovered');
    });
    $(document).ready(function(){
    	$('.b-download-mask__real-file-input').on('change', function() {
    		fileVal=$(this).prev('.b-download-mask').find('.b-download-mask__input-file-name').val();
    		realVal = $(this).val();
    		lastIndex = realVal.lastIndexOf('\\') + 1;
    		if(realVal!=="") {
    			realVal = realVal.substr(lastIndex);
    			$(this).prev('.b-download-mask').find('.b-download-mask__input-file-name').val(realVal);
    			$(this).prev('.b-download-mask').find('.b-photo-image img').detach();

				$(this).prev('.b-download-mask').find('.b-photo-image-default img').hide();

    			$(this).prev('.b-download-mask').find('.b-delete-photo__input-delete').show();
				$(this).prev('.b-download-mask').find('.b-delete-photo').show();
    		}
    		if (realVal==""){
    			if (fileVal!=''){
    				$(this).prev('.b-download-mask').find('.b-download-mask__input-file-name').val(fileVal);
    				$(this).val(fileVal);
    			}
    		}
    	});
		$('.b-download-mask').on('click', '.b-delete-photo__input-delete',  function() {
		    $(this).closest('.b-download-mask').find('.b-download-mask__input-file-name').val('');

		    $(this).closest('.b-download-mask').find('.b-photo-image-default img').show();

            $(this).closest('.b-download-mask').find('.b-photo-image img').detach();

            $(this).closest('.b-download-mask').find('.b-delete-photo__input-delete').hide();
			$(this).closest('.b-download-mask').find('.b-delete-photo').hide();
			$(this).closest('.b-photo').children('.b-download-mask__real-file-input').val('');
        });
	});
})(jQuery);

