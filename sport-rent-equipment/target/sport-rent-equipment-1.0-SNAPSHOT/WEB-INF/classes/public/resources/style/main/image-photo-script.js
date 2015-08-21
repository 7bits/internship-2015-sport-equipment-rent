jQuery(function ($) {
    function fix_size() {
        var images = $('.one-foto-box-photo img');
        images.each(setsize);

        function setsize() {
            var img = $(this),
                img_dom = img.get(0),
                container = img.parents('.one-foto-box-photo');
            if (img_dom.complete) {
                resize();
            } else img.one('load', resize);

            function resize() {
                if ((container.width() / container.height()) < (img_dom.width / img_dom.height)) {
                    img.width('147');
                    img.height('auto');
                    return;
                }
                img.height('100');
                img.width('auto');
            }
        }
    }
    $(window).on('resize', fix_size);
    fix_size();
});