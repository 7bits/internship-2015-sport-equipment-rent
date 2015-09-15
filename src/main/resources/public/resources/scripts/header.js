(function($) {
$(document).ready(function(){
    $("ul").hide();
    $(this).find("h1 img").click(function(){
        $(this).parent().next().slideToggle();
    });
});
})(jQuery);