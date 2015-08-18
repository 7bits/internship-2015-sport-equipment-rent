(function($){
    $('.photo-inventory img).each(
        function(){
            var $this=$(this);
            var h=$this.height();
            var w=$this.width();
            var nh=147;   //высота до которой нужно масштабировать
            var nw=147;  //ну и ширина
            var k1=nh/nw;
            var k2=h/w;

            if (k1>k2)
            {
                h=h*(nw/w);
                w=nw;
            } else {
                w=w*(nh/h);
                h=nh;
            }
            $this.width(w);
            $this.height(h);
            if (h<nh) //Если картинка была слишком широкой, то при пропорциональном сжатии теряем в высоте, знач нужно выровнять по высоте
            {
                $this.css('margin-top',(nh-h)/2);
            }
        }
    );
})(jQuery);