var formData = flight.component(function() {
  this.onClick = function(event) {
    var sendInfo = {
                form: $('.js-from').val(),
                to: $('.js-to').val()
            }
            /*isSuccess=true https://api.myjson.com/bins/4fihu
            isSucces=false  https://api.myjson.com/bins/10wwy*/
            $.ajax({
                url: "https://api.myjson.com/bins/4fihu",
                dataType: 'json',
                type: 'GET', /*POST*/
                data: sendInfo,
                /*headers: {'X-CSRF-TOKEN': $('meta[name = _csrf]').attr('content') }, */
                success: function(data, textStatus, jqXHR) {
                    if (data.isSuccess==true)
                        $('.js-status-bar').trigger('showSuccess', "");
                    else
                        $('.js-errors').trigger('showErrors', {errors: data.errors});          
                }
            }).fail(function($xhr) {
                var data = $xhr.responseJSON;
                console.log(data);
            });
        };
  
  // initialize
  this.after('initialize', function() {
    this.on('click', this.onClick);
  });
});