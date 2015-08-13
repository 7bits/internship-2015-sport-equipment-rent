(function($) {
    $('.custom-file-input').on('mouseenter mouseleave', function(){
    	$(this).prev('.download-mask').find('.send-file').toggleClass('hovered');
    });
    $(document).ready(function(){
    	$('.custom-file-input').on('change', function() {
    		realVal = $(this).val();
    		lastIndex = realVal.lastIndexOf('\\') + 1;
    		if(lastIndex !== -1) {
    			realVal = realVal.substr(lastIndex);
    			$(this).prev('.download-mask').find('.fileInputText').val(realVal);
    		}
    	});
    });
})(jQuery);
