(function($) {
    $('.custom-file-input').on('mouseenter mouseleave', function(){
    	$(this).prev('.download-mask').find('.foto-block').toggleClass('hovered');
    });
    $(document).ready(function(){
    	$('.custom-file-input').on('change', function() {
    		fileVal=$(this).prev('.download-mask').find('.fileInputText').val();
    		realVal = $(this).val();
    		lastIndex = realVal.lastIndexOf('\\') + 1;
    		if(realVal!=="") {
    			realVal = realVal.substr(lastIndex);
    			$(this).prev('.download-mask').find('.fileInputText').val(realVal);
    			$(this).prev('.download-mask').find('.one-foto-box-photo img').detach();

				$(this).prev('.download-mask').find('.one-foto-box-ico img').hide();

    			$(this).prev('.download-mask').find('.input-delete').show();
				$(this).prev('.download-mask').find('.icon-delete-photo').show();
    		}
    		if (realVal==""){
    			if (fileVal!=''){
    				$(this).prev('.download-mask').find('.fileInputText').val(fileVal);
    				$(this).val(fileVal);
    			}
    		}
    	});

	$('.download-mask').on('click', '.input-delete',  function() {
		    $(this).closest('.download-mask').find('.fileInputText').val('');

		    $(this).closest('.download-mask').find('.one-foto-box-ico img').show();

            $(this).closest('.download-mask').find('.one-foto-box-photo img').detach();

            $(this).closest('.download-mask').find('.input-delete').hide();
			$(this).closest('.download-mask').find('.icon-delete-photo').hide();

            });


	});


$(document).ready(function(){
	$(".help-foto-block .foto-block").each(function() {
		var bufImgSrc = $(this).children(".download-mask").find('.one-foto-box-photo img').attr("src");
		if (bufImgSrc=="resources/images/photo-ico.png"){
			$(this).children(".download-mask").find('.input-delete').hide();
			$(this).children(".download-mask").find('.icon-delete-photo').hide();
			$(this).children(".download-mask").find('.one-foto-box-photo img').hide();
		}
		else{
		$(this).children(".custom-file-input").val(bufImgSrc);
		}
	})
	})

})(jQuery);

