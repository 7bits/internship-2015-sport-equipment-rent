var formUI = flight.component(function() {
        
        this.showErrors = function(e, data){
            var theTemplateScript = $("#statusBar").html();
            // Compile the template
            var theTemplate = Handlebars.compile(theTemplateScript);
            // This is the default context, which is passed to the template
            var errors = data.errors;
            var statusBar = {errors};
            for (var i in errors){
                $('input[name='+errors[i].key+']').addClass("js-input--with-error");
            }
            // Pass our data to the template
            var theCompiledHtml = theTemplate(statusBar);
            // Add the compiled html to the page
            $('.js-status-bar').append(theCompiledHtml);
            $('.js-status-bar').show();
        }

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