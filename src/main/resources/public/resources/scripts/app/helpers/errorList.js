Handlebars.registerHelper('list', function(items, options) {
	var out = "<ul>";
	for(var i=0, l=items.length; i<l; i++) {
		out = out + "<li>" + "<p>" + options.fn(items[i]) + "</p>" + "</li>";
	}
	return out + "</ul>";
});