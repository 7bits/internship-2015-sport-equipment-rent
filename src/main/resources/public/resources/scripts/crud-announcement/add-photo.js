(function($) {
    $('.js-real-file-input').on('mouseenter mouseleave', function(){
        $(this).prev('.js-download-mask').find('.js-photo').toggleClass('hovered');
    });

    $(document).ready(function(){
        $('.js-real-file-input').on('change', function() {
            fileVal=$(this).prev('.js-download-mask').find('.js-input-file-name').val();
            realVal = $(this).val();
            lastIndex = realVal.lastIndexOf('\\') + 1;
            if(realVal!=='') {
                realVal = realVal.substr(lastIndex);
                $(this).prev('.js-download-mask').find('.js-input-file-name').val(realVal);
                $(this).prev('.js-download-mask').find('.js-product-image img').detach();

                $(this).prev('.js-download-mask').find('.js-default-image img').hide();

                $(this).prev('.js-download-mask').find('.js-input-delete').show();
                $(this).prev('.js-download-mask').find('.js-backet').show();
            }
            if (realVal==""){
                if (fileVal!=''){
                    $(this).prev('.js-download-mask').find('.js-input-file-name').val(fileVal);
                    $(this).val(fileVal);
                }
            }
        });
        
        $('.js-download-mask').on('click', '.js-input-delete',  function() {
            $(this).closest('.js-download-mask').find('.js-input-file-name').val('');

            $(this).closest('.js-download-mask').find('.js-default-image img').show();

            $(this).closest('.js-download-mask').find('.js-product-image img').detach();

            $(this).closest('.js-download-mask').find('.js-input-delete').hide();
            $(this).closest('.js-download-mask').find('.js-backet').hide();
            $(this).closest('.js-photo').children('.js-real-file-input').val('');
        });
    });
})(jQuery);