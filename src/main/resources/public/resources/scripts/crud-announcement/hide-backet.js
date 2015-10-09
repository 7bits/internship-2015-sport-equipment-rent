(function($) {
    $(document).ready(function(){
        $('.js-set-fields .js-photo').each(function() {
            var bufImgSrc = $(this).children('.js-download-mask').find('.js-product-image img').attr("src");
            if (bufImgSrc==undefined){
                $(this).children('.js-download-mask').find('.js-input-delete').hide();
                $(this).children('.js-download-mask').find('.js-backet').hide();
                $(this).children('.js-download-mask').find('.js-product-image img').hide();
            }
            else{
                $(this).children('.js-real-file-input').find('input').attr('value', bufImgSrc);
            }
        })
    })
})(jQuery);