jQuery(function ($) {
    $(document).ready(function(){
        var tabs = document.getElementsByTagName("table");
            for (var e = 0; e < tabs.length; e++)
                if (tabs[e].className == tabClass)
                    {
                        var rows = tabs[e].getElementsByTagName("tr");
                            for (var i = 0; i < rows.length; i++) // строки нумеруются с 0
                                rows[i].className += ((i % 2) == 0 ? " oddrows" : " evenrows");
                    }
    }
});