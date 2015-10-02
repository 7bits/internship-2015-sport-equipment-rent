(function($) {
	$(document).ready(function(){
		$('.js-sublit').click(function(){
			var data = {
				form: $('.js-from').val(),
				to: $('.js-to').val()
			}
			$.ajax({
			  url: $('.js-form').attr('action'),
			  dataType: 'json',
			  type: 'POST',
			  data: data,
			  success: callback
			});
		});	
	});
})(jQuery);