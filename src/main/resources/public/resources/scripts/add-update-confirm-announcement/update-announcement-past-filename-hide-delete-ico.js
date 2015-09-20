(function($) {
$(document).ready(function(){
    $(".b-set-fields .b-photo").each(function() {
        var bufImgSrc = $(this).children('.b-photo__download-mask').find('.b-photo__product-image img').attr("src");
        if (bufImgSrc=="resources/images/photo-ico.png"){
            $(this).children('.b-photo__download-mask').find('.b-delete-photo__input-delete').hide();
            $(this).children('.b-photo__download-mask').find('.b-delete-photo').hide();
            $(this).children('.b-photo__download-mask').find('.b-photo__product-image img').hide();
        }
        else{
            $(this).children('.b-download-mask__real-file-input').attr("value", bufImgSrc);
        }
    })
    })

})(jQuery);