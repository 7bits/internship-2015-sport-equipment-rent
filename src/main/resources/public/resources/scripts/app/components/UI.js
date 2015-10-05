var formUI = flight.component(function() {
        
        this.showErrors = function(e, data){
            for (var i in data.errors){
                var buf = data.errors[i];
                    $('input[name='+buf.key+']').addClass("js-input--with-error");
                    $('.js-error').text(buf.value);
            }
        $('.js-errors').show();}

        this.showSuccess = function(e, data){
            // Grab the template script
            var theTemplateScript = $("#statusBar").html();
            // Compile the template
            var theTemplate = Handlebars.compile(theTemplateScript);
            // Define our data object
            var statusBar = { status: 'Это успех!'};  
            // Pass our data to the template
            var theCompiledHtml = theTemplate(statusBar);
            // Add the compiled html to the page
            $('.js-status-bar').html(theCompiledHtml);
            $('.js-status-bar').show();
        }
  // initialize
    this.after('initialize', function() {
    this.on('showErrors', this.showErrors);
    this.on('showSuccess', this.showSuccess);
  });
});