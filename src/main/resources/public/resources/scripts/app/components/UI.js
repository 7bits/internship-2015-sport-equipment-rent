var formUI = flight.component(function() {
        this.showErrors = function(e, data){
            var theTemplateScript = $("#statusBar").html();        
            var theTemplate = Handlebars.compile(theTemplateScript);
            var errors = data.errors;
            var statusBar = {errors};
            for (var i in errors){
                $('input[name='+errors[i].key+']').addClass("js-input--with-error");
            }
            var theCompiledHtml = theTemplate(statusBar);
            $('.js-status-bar').append(theCompiledHtml);
            $('.js-status-bar').addClass("js-status-bar--error");
            $('.js-status-bar').show();
        }

        this.showSuccess = function(e, data){
            var theTemplateScript = $("#statusBar").html();        
            var theTemplate = Handlebars.compile(theTemplateScript);
            var statusBar = { errors:[{value: 'Товар забронирован!'}]};  
            var theCompiledHtml = theTemplate(statusBar);
            $('.js-status-bar').html(theCompiledHtml);
            $('.js-status-bar').show();
        }

  // initialize
    this.after('initialize', function() {
    this.on('showErrors', this.showErrors);
    this.on('showSuccess', this.showSuccess);
  });
});