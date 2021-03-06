!function (e) {
    "use strict";
    var i = {pips: function (i) {
        function l(i) {
            var l, s, t, a, n, r = [], d = 0;
            if (m && m.length) {
                for (t = u.element.slider("values"), a = e.map(t, function (e) {
                    return Math.abs(e - i)
                }), n = Math.min.apply(Math, a), l = 0; l < a.length; l++)a[l] === n && r.push(l);
                for (d = r[0], s = 0; s < r.length; s++)u._lastChangedValue === r[s] && (d = r[s]);
                u.options.range && 2 === r.length && (i > t[1] ? d = r[1] : i < t[0] && (d = r[0]))
            }
            return d
        }

        function s() {
            u.element.off(".selectPip").on("mousedown.slider", u.element.data("mousedown-original")).removeClass("ui-slider-pips").find(".ui-slider-pip").remove()
        }

        function t(i) {
            if (!u.option("disabled")) {
                var s = e(i).data("value"), t = l(s);
                m && m.length ? u.element.slider("values", t, s) : u.element.slider("value", s), u._lastChangedValue = t
            }
        }

        function a(i) {
            var l, s, t = i, a = "ui-slider-pip", r = "";
            "first" === i ? t = 0 : "last" === i && (t = g);
            var d = c + u.options.step * t, o = d.toString().replace(".", "-");
            if (l = "array" === e.type(y.labels) ? y.labels[t] || "" : "object" === e.type(y.labels) ? "first" === i ? y.labels.first || "" : "last" === i ? y.labels.last || "" : "array" === e.type(y.labels.rest) ? y.labels.rest[t - 1] || "" : d : d, "first" === i ? (s = "0%", a += " ui-slider-pip-first", a += "label" === y.first ? " ui-slider-pip-label" : "", a += !1 === y.first ? " ui-slider-pip-hide" : "") : "last" === i ? (s = "100%", a += " ui-slider-pip-last", a += "label" === y.last ? " ui-slider-pip-label" : "", a += !1 === y.last ? " ui-slider-pip-hide" : "") : (s = (100 / g * i).toFixed(4) + "%", a += "label" === y.rest ? " ui-slider-pip-label" : "", a += !1 === y.rest ? " ui-slider-pip-hide" : ""), a += " ui-slider-pip-" + o, m && m.length) {
                for (n = 0; n < m.length; n++)d === m[n] && (a += " ui-slider-pip-initial-" + (n + 1), a += " ui-slider-pip-selected-" + (n + 1));
                u.options.range && d > m[0] && d < m[1] && (a += " ui-slider-pip-inrange")
            } else d === v && (a += " ui-slider-pip-initial", a += " ui-slider-pip-selected"), u.options.range && ("min" === u.options.range && v > d || "max" === u.options.range && d > v) && (a += " ui-slider-pip-inrange");
            return r = "horizontal" === u.options.orientation ? "left: " + s : "bottom: " + s, '<span class="' + a + '" style="' + r + '"><span class="ui-slider-line"></span><span class="ui-slider-label" data-value="' + d + '">' + y.formatLabel(l) + "</span></span>"
        }

        var n, r, d, o, p, u = this, f = "", c = u._valueMin(), h = u._valueMax(), v = u._value(), m = u._values(), g = (h - c) / u.options.step, b = u.element.find(".ui-slider-handle"), y = {first: "label", last: "label", rest: "pip", labels: !1, prefix: "", suffix: "", step: g > 100 ? Math.floor(.05 * g) : 1, formatLabel: function (e) {
            return this.prefix + e + this.suffix
        }};
        if ("object" !== e.type(i) && "undefined" !== e.type(i))return void("destroy" === i && s());
        e.extend(y, i), u.options.pipStep = Math.round(y.step), u.element.off(".selectPip").addClass("ui-slider-pips").find(".ui-slider-pip").remove();
        var x = {single: function (i) {
            this.resetClasses(), p.filter(".ui-slider-pip-" + this.classLabel(i)).addClass("ui-slider-pip-selected"), u.options.range && p.each(function (l, s) {
                var t = e(s).children(".ui-slider-label").data("value");
                ("min" === u.options.range && i > t || "max" === u.options.range && t > i) && e(s).addClass("ui-slider-pip-inrange")
            })
        }, range: function (i) {
            for (this.resetClasses(), n = 0; n < i.length; n++)p.filter(".ui-slider-pip-" + this.classLabel(i[n])).addClass("ui-slider-pip-selected-" + (n + 1));
            u.options.range && p.each(function (l, s) {
                var t = e(s).children(".ui-slider-label").data("value");
                t > i[0] && t < i[1] && e(s).addClass("ui-slider-pip-inrange")
            })
        }, classLabel: function (e) {
            return e.toString().replace(".", "-")
        }, resetClasses: function () {
            var e = /(^|\s*)(ui-slider-pip-selected|ui-slider-pip-inrange)(-{1,2}\d+|\s|$)/gi;
            p.removeClass(function (i, l) {
                return(l.match(e) || []).join(" ")
            })
        }};
        for (f += a("first"), d = 1; g > d; d++)d % u.options.pipStep === 0 && (f += a(d));
        for (f += a("last"), u.element.append(f), p = u.element.find(".ui-slider-pip"), o = e._data(u.element.get(0), "events").mousedown && e._data(u.element.get(0), "events").mousedown.length ? e._data(u.element.get(0), "events").mousedown : u.element.data("mousedown-handlers"), u.element.data("mousedown-handlers", o.slice()), r = 0; r < o.length; r++)"slider" === o[r].namespace && u.element.data("mousedown-original", o[r].handler);
        u.element.off("mousedown.slider").on("mousedown.selectPip", function (i) {
            var s = e(i.target), a = l(s.data("value")), n = b.eq(a);
            if (n.addClass("ui-state-active"), s.is(".ui-slider-label"))t(s), u.element.one("mouseup.selectPip", function () {
                n.removeClass("ui-state-active").focus()
            }); else {
                var r = u.element.data("mousedown-original");
                r(i)
            }
        }), u.element.on("slide.selectPip slidechange.selectPip", function (i, l) {
            var s = e(this), t = s.slider("value"), a = s.slider("values");
            l && (t = l.value, a = l.values), a && a.length ? x.range(a) : x.single(t)
        })
    }, "float": function (i) {
        function l() {
            a.element.off(".sliderFloat").removeClass("ui-slider-float").find(".ui-slider-tip, .ui-slider-tip-label").remove()
        }

        function s(i) {
            var l = [], s = e.map(i, function (e) {
                return Math.ceil((e - n) / a.options.step)
            });
            if ("array" === e.type(f.labels))for (t = 0; t < i.length; t++)l[t] = f.labels[s[t]] || i[t]; else if ("object" === e.type(f.labels))for (t = 0; t < i.length; t++)i[t] === n ? l[t] = f.labels.first || n : i[t] === r ? l[t] = f.labels.last || r : "array" === e.type(f.labels.rest) ? l[t] = f.labels.rest[s[t] - 1] || i[t] : l[t] = i[t]; else for (t = 0; t < i.length; t++)l[t] = i[t];
            return l
        }

        var t, a = this, n = a._valueMin(), r = a._valueMax(), d = a._value(), o = a._values(), p = [], u = a.element.find(".ui-slider-handle"), f = {handle: !0, pips: !1, labels: !1, prefix: "", suffix: "", event: "slidechange slide", formatLabel: function (e) {
            return this.prefix + e + this.suffix
        }};
        if ("object" !== e.type(i) && "undefined" !== e.type(i))return void("destroy" === i && l());
        if (e.extend(f, i), n > d && (d = n), d > r && (d = r), o && o.length)for (t = 0; t < o.length; t++)o[t] < n && (o[t] = n), o[t] > r && (o[t] = r);
        if (a.element.addClass("ui-slider-float").find(".ui-slider-tip, .ui-slider-tip-label").remove(), f.handle)for (p = s(o && o.length ? o : [d]), t = 0; t < p.length; t++)u.eq(t).append(e('<span class="ui-slider-tip">' + f.formatLabel(p[t]) + "</span>"));
        f.pips && a.element.find(".ui-slider-label").each(function (i, l) {
            var t, a, n = e(l), r = [n.data("value")];
            t = f.formatLabel(s(r)[0]), a = e('<span class="ui-slider-tip-label">' + t + "</span>").insertAfter(n)
        }), "slide" !== f.event && "slidechange" !== f.event && "slide slidechange" !== f.event && "slidechange slide" !== f.event && (f.event = "slidechange slide"), a.element.off(".sliderFloat").on(f.event + ".sliderFloat", function (i, l) {
            var t = "array" === e.type(l.value) ? l.value : [l.value], a = f.formatLabel(s(t)[0]);
            e(l.handle).find(".ui-slider-tip").html(a)
        })
    }};
    e.extend(!0, e.ui.slider.prototype, i)
}(jQuery);