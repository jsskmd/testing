var validator = function (e) {
    function t(e, t) {
        return"tel" == e && (v = v || "phone"), e && "password" != e && "tel" != e && "search" != e && "file" != e || (e = "text"), s[e] ? s[e](t, !0) : !0
    }

    function a(t) {
        d = e(t), d.data("valid", !0), d.data("type", d.attr("type")), v = d.attr("pattern")
    }

    function n(e) {
        return a(this), e.charCode ? t(this.type, this.value) : void 0
    }

    function i() {
        if ("hidden" != this.type && e(this).is(":hidden"))return!0;
        if (a(this), d.data("val", d[0].value.replace(/^\s+|\s+$/g, "")), b = d.data(), g = r[d.prop("name")] || r.invalid, "select" === d[0].nodeName.toLowerCase() && (b.type = "select"), "textarea" === d[0].nodeName.toLowerCase() && (b.type = "text"), h = b.validateWords || 0, p = b.validateLengthRange ? (b.validateLengthRange + "").split(",") : [1], f = b.validateLength ? (b.validateLength + "").split(",") : !1, c = b.validateMinmax ? (b.validateMinmax + "").split(",") : "", b.valid = s.hasValue(b.val), d.hasClass("optional") && !b.valid && (b.valid = !0), b.valid)if (s.sameAsPlaceholder(d) && (g = r.empty, b.valid = !1), b.validateLinked) {
            var n = e(0 == b.validateLinked.indexOf("#") ? b.validateLinked : ":input[name=" + b.validateLinked + "]");
            b.valid = s.linked(b.val, n.val())
        } else(b.valid || "select" == b.type) && (b.valid = t(b.type, b.val));
        return b.valid ? u(d) : (o(d, g), submit = !1), b.valid
    }

    function l(t) {
        if (t = e(t), 0 == t.length)return console.warn("element not found"), !1;
        var a = !0, n = t.find(":input").filter("[required=required], .required, .optional").not("[disabled=disabled]");
        return n.each(function () {
            a *= i.apply(this)
        }), !!a
    }

    var r, s, i, o, u, d, c, m, h, p, f, v, g, b, y = /[\(\)\<\>\,\;\:\\\/\"\[\]]/, w = /^.+@.+\..{2,3}$/;
    return r = {invalid: "invalid input", empty: "please put something here", min: "input is too short", max: "input is too long", number_min: "too low", number_max: "too high", url: "invalid URL", number: "not a number", email: "email address is invalid", email_repeat: "emails do not match", password_repeat: "passwords do not match", repeat: "no match", complete: "input is not complete", select: "Please select an option"}, window.console || (console = {}, console.log = console.warn = function () {
    }), m = {alerts: !0, classes: {item: "item", alert: "alert", bad: "bad"}}, s = {sameAsPlaceholder: function (t) {
        return e.fn.placeholder && void 0 !== t.attr("placeholder") && b.val == t.prop("placeholder")
    }, hasValue: function (e) {
        return e ? !0 : (g = r.empty, !1)
    }, linked: function (e, t) {
        return t != e ? (g = r[b.type + "_repeat"] || r.no_match, !1) : !0
    }, email: function (e) {
        return!w.test(e) || e.match(y) ? (g = e ? r.email : r.empty, !1) : !0
    }, text: function (e, t) {
        if (h) {
            var a = e.split(" "), n = function (e) {
                for (var t = a.length; t--;)if (a[t].length < e)return!1;
                return!0
            };
            return a.length < h || !n(2) ? (g = r.complete, !1) : !0
        }
        if (!t && p && e.length < p[0])return g = r.min, !1;
        if (p && p[1] && e.length > p[1])return g = r.max, !1;
        if (f && f.length)for (; f.length;)if (f.pop() == e.length)return g = r.complete, !1;
        if (v) {
            var i, l;
            switch (v) {
                case"alphanumeric":
                    i = /^[a-z0-9]+$/i;
                    break;
                case"numeric":
                    i = /^[0-9]+$/i;
                    break;
                case"phone":
                    i = /^\+?([0-9]|[-|' '])+$/i;
                    break;
                default:
                    i = v
            }
            try {
                if (l = new RegExp(i).test(e), e && !l)return!1
            } catch (s) {
                return console.log(s, d, "regex is invalid"), !1
            }
        }
        return!0
    }, number: function (e) {
        return isNaN(parseFloat(e)) && !isFinite(e) ? (g = r.number, !1) : p && e.length < p[0] ? (g = r.min, !1) : p && p[1] && e.length > p[1] ? (g = r.max, !1) : c[0] && (0 | e) < c[0] ? (g = r.number_min, !1) : c[1] && (0 | e) > c[1] ? (g = r.number_max, !1) : !0
    }, date: function (e) {
        var t, a, n = e.split(/[-./]/g);
        if (d[0].valueAsNumber)return!0;
        for (a = n.length; a--;)if (isNaN(parseFloat(e)) && !isFinite(e))return!1;
        try {
            return t = new Date(n[2], n[1] - 1, n[0]), t.getMonth() + 1 == n[1] && t.getDate() == n[0] ? t : !1
        } catch (i) {
            return console.log("date test: ", err), !1
        }
    }, url: function (e) {
        function t(e) {
            return/^(https?:\/\/)?([\w\\-_]+\.+[A-Za-z]{2,})+\/?/.test(e)
        }

        return t(e) ? !0 : (g = e ? r.url : r.empty, !1)
    }, hidden: function (e) {
        if (p && e.length < p[0])return g = r.min, !1;
        if (v) {
            var t;
            if ("alphanumeric" == v && (t = /^[a-z0-9]+$/i, !t.test(e)))return!1
        }
        return!0
    }, select: function (e) {
        return s.hasValue(e) ? !0 : (g = r.select, !1)
    }}, o = function (t, a) {
        if (!a || !t || !t.length)return!1;
        var n, i = t.closest("." + m.classes.item);
        i.hasClass(m.classes.bad) ? m.alerts && i.find("." + m.classes.alert).html(a) : m.alerts && (n = e('<div class="' + m.classes.alert + '">').html(a), i.append(n)), i.removeClass(m.classes.bad), setTimeout(function () {
            i.addClass(m.classes.bad)
        }, 0)
    }, u = function (e) {
        return e && e.length ? void e.closest("." + m.classes.item).removeClass(m.classes.bad).find("." + m.classes.alert).remove() : (console.warn('no "field" argument, null or DOM object not found'), !1)
    }, {defaults: m, checkField: i, keypress: n, checkAll: l, mark: o, unmark: u, message: r, tests: s}
}(jQuery);