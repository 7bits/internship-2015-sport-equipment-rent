(function($) {
    $(document).ready(function(){
        $('.js-datatime-from').click(function(event){
            $('.js-datatime-from').datetimepicker();
            $('.js-datatime-from').datetimepicker.setLocale('ru');
        });

        $('.js-datatime-to').click(function(event){
            $('.js-datatime-to').datetimepicker();
        });
    });
})(jQuery);