var formUI = flight.component(function() {
        this.showErrors = function(e, data){
            for (var i in data.errors){
                var buf = data.errors[i];
                    $('input[name='+buf.key+']').addClass("js-input--with-error");
                    $('.js-error').text(buf.value);
            }
        $('.js-errors').show();}

        this.showSuccess = function(e, data){
            $('.js-status-bar').show();
        }
  // initialize
    this.after('initialize', function() {
    this.on('showErrors', this.showErrors);
  });
});