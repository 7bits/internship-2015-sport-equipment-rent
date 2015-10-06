var formInput = flight.component(function() {
    
    this.onChange = function(event) {
        this.$node.removeClass('js-input--with-error');
    };
    
    this.after('initialize', function(){
        this.on('change', this.onChange);
    });
});