var __slice = Array.prototype.slice;
!function (t) {
    var o;
    return t.fn.sketch = function () {
        var e, n, i;
        return n = arguments[0], e = 2 <= arguments.length ? __slice.call(arguments, 1) : [], this.length > 1 && t.error("Sketch.js can only be called on one element at a time."), i = this.data("sketch"), "string" == typeof n && i ? i[n] ? "function" == typeof i[n] ? i[n].apply(i, e) : 0 === e.length ? i[n] : 1 === e.length ? i[n] = e[0] : void 0 : t.error("Sketch.js did not recognize the given command.") : i ? i : (this.data("sketch", new o(this.get(0), n)), this)
    }, o = function () {
        function o(o, e) {
            this.el = o, this.canvas = t(o), this.options = t.extend({toolLinks: !0, defaultTool: "marker", defaultColor: "#000000", defaultSize: 5}, e), this.painting = !1, this.color = this.options.defaultColor, this.size = this.options.defaultSize, this.tool = this.options.defaultTool, this.actions = [], this.action = [], this.canvas.bind("click mousedown mouseup mousemove mouseleave mouseout touchstart touchmove touchend touchcancel", this.onEvent), this.options.toolLinks && t("body").delegate('a[href="#' + this.canvas.attr("id") + '"]', "click", function (o) {
                var e, n, i, s, a, r, h;
                for (n = t(this), e = t(n.attr("href")), s = e.data("sketch"), h = ["color", "size", "tool"], a = 0, r = h.length; r > a; a++)i = h[a], n.attr("data-" + i) && s.set(i, t(this).attr("data-" + i));
                return t(this).attr("data-download") && s.download(t(this).attr("data-download")), !1
            })
        }

        return o.prototype.download = function (t) {
            var o;
            return t || (t = "png"), "jpg" === t && (t = "jpeg"), o = "image/" + t, window.open(this.el.toDataURL(o))
        }, o.prototype.set = function (t, o) {
            return this[t] = o, this.canvas.trigger("sketch.change" + t, o)
        }, o.prototype.startPainting = function () {
            return this.painting = !0, this.action = {tool: this.tool, color: this.color, size: parseFloat(this.size), events: []}
        }, o.prototype.stopPainting = function () {
            return this.action && this.actions.push(this.action), this.painting = !1, this.action = null, this.redraw()
        }, o.prototype.onEvent = function (o) {
            return o.originalEvent && o.originalEvent.targetTouches && (o.pageX = o.originalEvent.targetTouches[0].pageX, o.pageY = o.originalEvent.targetTouches[0].pageY), t.sketch.tools[t(this).data("sketch").tool].onEvent.call(t(this).data("sketch"), o), o.preventDefault(), !1
        }, o.prototype.redraw = function () {
            var o;
            return this.el.width = this.canvas.width(), this.context = this.el.getContext("2d"), o = this, t.each(this.actions, function () {
                return this.tool ? t.sketch.tools[this.tool].draw.call(o, this) : void 0
            }), this.painting && this.action ? t.sketch.tools[this.action.tool].draw.call(o, this.action) : void 0
        }, o
    }(), t.sketch = {tools: {}}, t.sketch.tools.marker = {onEvent: function (t) {
        switch (t.type) {
            case"mousedown":
            case"touchstart":
                this.startPainting();
                break;
            case"mouseup":
            case"mouseout":
            case"mouseleave":
            case"touchend":
            case"touchcancel":
                this.stopPainting()
        }
        return this.painting ? (this.action.events.push({x: t.pageX - this.canvas.offset().left, y: t.pageY - this.canvas.offset().top, event: t.type}), this.redraw()) : void 0
    }, draw: function (t) {
        var o, e, n, i, s;
        for (this.context.lineJoin = "round", this.context.lineCap = "round", this.context.beginPath(), this.context.moveTo(t.events[0].x, t.events[0].y), s = t.events, n = 0, i = s.length; i > n; n++)o = s[n], this.context.lineTo(o.x, o.y), e = o;
        return this.context.strokeStyle = t.color, this.context.lineWidth = t.size, this.context.stroke()
    }}, t.sketch.tools.eraser = {onEvent: function (o) {
        return t.sketch.tools.marker.onEvent.call(this, o)
    }, draw: function (o) {
        var e;
        return e = this.context.globalCompositeOperation, this.context.globalCompositeOperation = "copy", o.color = "rgba(0,0,0,0)", t.sketch.tools.marker.draw.call(this, o), this.context.globalCompositeOperation = e
    }}
}(jQuery);