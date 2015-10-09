var formData = flight.component(function() {
    this.onClick = function(event) {
        var id=$('.js-announcement-id').val();
        var sendInfo = {
            announcementId: id,
            from: $('.js-from').val(),
            to: $('.js-to').val()
        }
        /*структура json  https://api.myjson.com/bins/256wy*/
        $.ajax({
            url: "/getIt",
            dataType: 'json',
            type: 'POST',
            data: sendInfo,
            headers: {'X-CSRF-TOKEN': $('meta[name = _csrf]').attr('content') }, 
            success: function(data, serverResponse, textStatus, jqXHR) {
                if (data.success)
                    $('.js-status-bar').trigger('showSuccess', "");
                else
                    $('.js-status-bar').trigger('showErrors', {errors: data.errors});
            }
        }).fail(function($xhr){
                var data = $xhr.responseJSON;
                console.log(data);
            });
    };
    
    this.after('initialize', function(){
        this.on('click', this.onClick);
    });
});