(function($) {
    $('.custom-file-input').on('mouseenter mouseleave', function(){
    	$(this).prev('.download-mask').find('.foto-block').toggleClass('hovered');
    });
    $(document).ready(function(){
    	$('.custom-file-input').on('change', function() {
    		realVal = $(this).val();
    		lastIndex = realVal.lastIndexOf('\\') + 1;
    		if(lastIndex !== -1) {
    			realVal = realVal.substr(lastIndex);
    			$(this).prev('.download-mask').find('.fileInputText').val(realVal);
    			$(this).prev('.download-mask').find('.one-foto-box-ico').toggleClass('set-anvisible');
    			$(this).prev('.download-mask').find('.one-foto-box-ico img').toggleClass('set-anvisible');
    			$(this).prev('.download-mask').find('.one-foto-box-photo').toggleClass('set-anvisible');
    			$(this).prev('.download-mask').find('.one-foto-box-photo img').toggleClass('set-anvisible');
    		}
    	});
    });
})(jQuery);
