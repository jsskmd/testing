function getExampleNumber(e, r, n) {
    try {
        var t = i18n.phonenumbers.PhoneNumberUtil.getInstance(), o = t.getExampleNumberForType(e, n), u = r ? i18n.phonenumbers.PhoneNumberFormat.NATIONAL : i18n.phonenumbers.PhoneNumberFormat.INTERNATIONAL;
        return t.format(o, u)
    } catch (i) {
        return""
    }
}
function formatNumberByType(e, r, n) {
    try {
        var t = i18n.phonenumbers.PhoneNumberUtil.getInstance(), o = t.parseAndKeepRawInput(e, r);
        return n = "undefined" == typeof n ? i18n.phonenumbers.PhoneNumberFormat.E164 : n, t.format(o, n)
    } catch (u) {
        return""
    }
}
function isValidNumber(e, r) {
    try {
        var n = i18n.phonenumbers.PhoneNumberUtil.getInstance(), t = n.parseAndKeepRawInput(e, r);
        return n.isValidNumber(t)
    } catch (o) {
        return!1
    }
}
function getValidationError(e, r) {
    try {
        var n = i18n.phonenumbers.PhoneNumberUtil.getInstance(), t = n.parseAndKeepRawInput(e, r);
        return n.isPossibleNumberWithReason(t)
    } catch (o) {
        return o == i18n.phonenumbers.Error.INVALID_COUNTRY_CODE ? i18n.phonenumbers.PhoneNumberUtil.ValidationResult.INVALID_COUNTRY_CODE : o == i18n.phonenumbers.Error.NOT_A_NUMBER ? 4 : o == i18n.phonenumbers.Error.TOO_SHORT_AFTER_IDD || o == i18n.phonenumbers.Error.TOO_SHORT_NSN ? i18n.phonenumbers.PhoneNumberUtil.ValidationResult.TOO_SHORT : o == i18n.phonenumbers.Error.TOO_LONG ? i18n.phonenumbers.PhoneNumberUtil.ValidationResult.TOO_LONG : -99
    }
}
function formatNumber(e, r, n, t, o) {
    try {
        var u, i = e.replace(/\D/g, ""), m = new i18n.phonenumbers.AsYouTypeFormatter(r), l = "", a = " ext. ";
        "+" == e.substr(0, 1) && (i = "+" + i);
        for (var b = 0; b < i.length; b++) {
            if (u = m.inputDigit(i.charAt(b)), t && l && u.length <= l.length && -1 == u.indexOf(" ")) {
                u = -1;
                break
            }
            l = u
        }
        if (" " == l.charAt(l.length - 1) && (l = l.substr(0, l.length - 1)), n && !e.split(a)[1]) {
            var p = m.inputDigit("5");
            " " == p.charAt(p.length - 1) && (p = p.substr(0, p.length - 1));
            var s = p.substr(p.length - 2, 1);
            if (isNaN(parseFloat(s)))return p.substr(0, p.length - 1);
            if (t && l && p.length <= l.length && -1 == p.indexOf(" ") && !o)return l + a
        }
        return-1 == u && (l += a + i.substring(b, i.length)), l
    } catch (N) {
        return e
    }
}
function getNumberType(e, r) {
    try {
        var n = i18n.phonenumbers.PhoneNumberUtil.getInstance(), t = n.parseAndKeepRawInput(e, r);
        return n.getNumberType(t)
    } catch (o) {
        return-99
    }
}
goog.require("i18n.phonenumbers.AsYouTypeFormatter"), goog.require("i18n.phonenumbers.PhoneNumberFormat"), goog.require("i18n.phonenumbers.PhoneNumberUtil");
var numberType = {FIXED_LINE: 0, MOBILE: 1, FIXED_LINE_OR_MOBILE: 2, TOLL_FREE: 3, PREMIUM_RATE: 4, SHARED_COST: 5, VOIP: 6, PERSONAL_NUMBER: 7, PAGER: 8, UAN: 9, VOICEMAIL: 10, UNKNOWN: -1}, validationError = {IS_POSSIBLE: 0, INVALID_COUNTRY_CODE: 1, TOO_SHORT: 2, TOO_LONG: 3, NOT_A_NUMBER: 4}, numberFormat = {E164: 0, INTERNATIONAL: 1, NATIONAL: 2, RFC3966: 3};
goog.exportSymbol("intlTelInputUtils", {}), goog.exportSymbol("intlTelInputUtils.formatNumber", formatNumber), goog.exportSymbol("intlTelInputUtils.formatNumberByType", formatNumberByType), goog.exportSymbol("intlTelInputUtils.getExampleNumber", getExampleNumber), goog.exportSymbol("intlTelInputUtils.getNumberType", getNumberType), goog.exportSymbol("intlTelInputUtils.getValidationError", getValidationError), goog.exportSymbol("intlTelInputUtils.isValidNumber", isValidNumber), goog.exportSymbol("intlTelInputUtils.numberType", numberType), goog.exportSymbol("intlTelInputUtils.validationError", validationError), goog.exportSymbol("intlTelInputUtils.numberFormat", numberFormat);