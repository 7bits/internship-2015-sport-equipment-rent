jQuery(function ($) {
    function fix_size() {
        var images = $('.b-photo-image img');
        images.each(setsize);

        function setsize() {
            var img = $(this),
                img_dom = img.get(0),
                container = img.parents('.b-photo-image');
            if (img_dom.complete) {
                resize();
            } else img.one('load', resize);

            function resize() {
                if ((container.width() / container.height()) < (img_dom.width / img_dom.height)) {
                    img.width('110');
                    img.height('auto');
                    return;
                }
                img.height('125');
                img.width('auto');
            }
        }
    }
    $(window).on('resize', fix_size);
    fix_size();
});