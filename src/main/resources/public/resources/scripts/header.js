(function($) {
$(document).ready(function(){
    $('.js-dropdown-ico').click(function(){
        $('.js-dropdown-menu').slideToggle();
        $('.js-dropdown-menu').find('ul').slideToggle();
    });
});
})(jQuery);