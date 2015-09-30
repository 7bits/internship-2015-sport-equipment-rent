(function($) {
    $(document).ready(function(){
        $('.js-submit').click(function(){
            var sendInfo = {
                form: $('.js-from').val(),
                to: $('.js-to').val()
            }
            $.ajax({
                url: "https://api.myjson.com/bins/4vbv2",
                dataType: 'json',
                type: 'GET', /*POST*/
                data: sendInfo,
                /*headers: {'X-CSRF-TOKEN': $('meta[name = _csrf]').attr('content') }, */
                success: function(data, textStatus, jqXHR) {
                    $('.js-status-bar').show("slow");            
                }
            }).fail(function($xhr) {
                var data = $xhr.responseJSON;
                console.log(data);
            });
        });
    });
})(jQuery);