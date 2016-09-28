(function () {
    $.fn.popbox = function (o) {
        var e = $.extend({selector: this.selector, open: ".open", box: ".box", arrow: ".arrow", arrow_border: ".arrow-border", close: ".close"}, o), t = {open: function (o) {
            o.preventDefault();
            var n = $(this), c = $(this).parent().find(e.box);
            c.find(e.arrow).css({left: c.width() / 1 - 40}), c.find(e.arrow_border).css({left: c.width() / 1 - 40}), "block" == c.css("display") ? t.close() : c.css({display: "block", top: 50, left: 20, "left6/16/2014": n.parent().width() / 1 - c.width() / 1})
        }, close: function () {
            $(e.box).fadeOut("fast")
        }};
        return $(document).bind("keyup", function (o) {
            27 == o.keyCode && t.close()
        }), $(document).bind("click", function (o) {
            $(o.target).closest(e.selector).length || t.close()
        }), this.each(function () {
            $(this).css({width: $(e.box).width()}), $(e.open, this).bind("click", t.open), $(e.open, this).parent().find(e.close).bind("click", t.close)
        })
    }
}).call(this);