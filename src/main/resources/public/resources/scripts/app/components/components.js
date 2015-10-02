var submit = flight.component(function() {
  // click event handler
  this.onClick = function(event) {
    var sendInfo = {
                form: $('.js-from').val(),
                to: $('.js-to').val()
            }
            /*isSuccess=true https://api.myjson.com/bins/2mree
            isSucces=false  https://api.myjson.com/bins/4d4rq*/
            $.ajax({
                url: "https://api.myjson.com/bins/4d4rq",
                dataType: 'json',
                type: 'GET', /*POST*/
                data: sendInfo,
                /*headers: {'X-CSRF-TOKEN': $('meta[name = _csrf]').attr('content') }, */
                success: function(data, textStatus, jqXHR) {
                    if (data.isSuccess)
                        $('.js-status-bar').show();
                    else
                        for (var i in data.errors){
                            var buf = data.errors[i];
                                $('input[name='+buf.key+']').addClass("js-input--with-error");
                                $('.js-error').text(buf.value);
                        }
                    $('.js-errors').show();
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