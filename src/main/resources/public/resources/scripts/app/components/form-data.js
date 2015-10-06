var formData = flight.component(function() {
    this.onClick = function(event) {
        var sendInfo = {
            form: $('.js-from').val(),
            to: $('.js-to').val()
        }
        /*isSuccess=true https://api.myjson.com/bins/2pylm
        isSucces=false  https://api.myjson.com/bins/256wy*/
        $.ajax({
            url: "https://api.myjson.com/bins/256wy",
            dataType: 'json',
            type: 'GET', /*POST*/
            data: sendInfo,
            /*headers: {'X-CSRF-TOKEN': $('meta[name = _csrf]').attr('content') }, */
            success: function(data, textStatus, jqXHR) {
                if (data.isSuccess)
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