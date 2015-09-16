(function($) {
$(document).ready(function(){
    $("ul").hide();
    $(this).find("h1 img").click(function(){
        $(this).closest('.b-header').next('.b-header__dropdown-menu').show();
        $(this).closest('.b-header').next('.b-header__dropdown-menu').find('ul').slideToggle();
    });
    $(this).find("h1 img").mouseenter(function(){
        $(this).closest('.b-header').next('.b-header__dropdown-menu').show();
        $(this).closest('.b-header').next('.b-header__dropdown-menu').find('ul').slideToggle();
    });
});
})(jQuery);