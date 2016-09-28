!function (e) {
    "function" == typeof define && define.amd ? define(["jquery", "jquery-mousewheel", "date-functions"], e) : "object" == typeof exports ? module.exports = e : e(jQuery)
}(function (e) {
    "use strict";
    function i(e, t, a) {
        this.date = e, this.desc = t, this.style = a
    }

    var t = {i18n: {ar: {months: ["كانون الثاني", "شباط", "آذار", "نيسان", "مايو", "حزيران", "تموز", "آب", "أيلول", "تشرين الأول", "تشرين الثاني", "كانون الأول"], dayOfWeekShort: ["ن", "ث", "ع", "خ", "ج", "س", "ح"], dayOfWeek: ["الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت", "الأحد"]}, ro: {months: ["Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie", "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"], dayOfWeekShort: ["Du", "Lu", "Ma", "Mi", "Jo", "Vi", "Sâ"], dayOfWeek: ["Duminică", "Luni", "Marţi", "Miercuri", "Joi", "Vineri", "Sâmbătă"]}, id: {months: ["Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"], dayOfWeekShort: ["Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab"], dayOfWeek: ["Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"]}, is: {months: ["Janúar", "Febrúar", "Mars", "Apríl", "Maí", "Júní", "Júlí", "Ágúst", "September", "Október", "Nóvember", "Desember"], dayOfWeekShort: ["Sun", "Mán", "Þrið", "Mið", "Fim", "Fös", "Lau"], dayOfWeek: ["Sunnudagur", "Mánudagur", "Þriðjudagur", "Miðvikudagur", "Fimmtudagur", "Föstudagur", "Laugardagur"]}, bg: {months: ["Януари", "Февруари", "Март", "Април", "Май", "Юни", "Юли", "Август", "Септември", "Октомври", "Ноември", "Декември"], dayOfWeekShort: ["Нд", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"], dayOfWeek: ["Неделя", "Понеделник", "Вторник", "Сряда", "Четвъртък", "Петък", "Събота"]}, fa: {months: ["فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"], dayOfWeekShort: ["یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه", "شنبه"], dayOfWeek: ["یک‌شنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنج‌شنبه", "جمعه", "شنبه", "یک‌شنبه"]}, ru: {months: ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"], dayOfWeekShort: ["Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"], dayOfWeek: ["Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"]}, uk: {months: ["Січень", "Лютий", "Березень", "Квітень", "Травень", "Червень", "Липень", "Серпень", "Вересень", "Жовтень", "Листопад", "Грудень"], dayOfWeekShort: ["Ндл", "Пнд", "Втр", "Срд", "Чтв", "Птн", "Сбт"], dayOfWeek: ["Неділя", "Понеділок", "Вівторок", "Середа", "Четвер", "П'ятниця", "Субота"]}, en: {months: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"], dayOfWeekShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"], dayOfWeek: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"]}, el: {months: ["Ιανουάριος", "Φεβρουάριος", "Μάρτιος", "Απρίλιος", "Μάιος", "Ιούνιος", "Ιούλιος", "Αύγουστος", "Σεπτέμβριος", "Οκτώβριος", "Νοέμβριος", "Δεκέμβριος"], dayOfWeekShort: ["Κυρ", "Δευ", "Τρι", "Τετ", "Πεμ", "Παρ", "Σαβ"], dayOfWeek: ["Κυριακή", "Δευτέρα", "Τρίτη", "Τετάρτη", "Πέμπτη", "Παρασκευή", "Σάββατο"]}, de: {months: ["Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"], dayOfWeekShort: ["So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"], dayOfWeek: ["Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"]}, nl: {months: ["januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december"], dayOfWeekShort: ["zo", "ma", "di", "wo", "do", "vr", "za"], dayOfWeek: ["zondag", "maandag", "dinsdag", "woensdag", "donderdag", "vrijdag", "zaterdag"]}, tr: {months: ["Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"], dayOfWeekShort: ["Paz", "Pts", "Sal", "Çar", "Per", "Cum", "Cts"], dayOfWeek: ["Pazar", "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi"]}, fr: {months: ["Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"], dayOfWeekShort: ["Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam"], dayOfWeek: ["dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"]}, es: {months: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"], dayOfWeekShort: ["Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"], dayOfWeek: ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"]}, th: {months: ["มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"], dayOfWeekShort: ["อา.", "จ.", "อ.", "พ.", "พฤ.", "ศ.", "ส."], dayOfWeek: ["อาทิตย์", "จันทร์", "อังคาร", "พุธ", "พฤหัส", "ศุกร์", "เสาร์", "อาทิตย์"]}, pl: {months: ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"], dayOfWeekShort: ["nd", "pn", "wt", "śr", "cz", "pt", "sb"], dayOfWeek: ["niedziela", "poniedziałek", "wtorek", "środa", "czwartek", "piątek", "sobota"]}, pt: {months: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"], dayOfWeekShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"], dayOfWeek: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"]}, ch: {months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"], dayOfWeekShort: ["日", "一", "二", "三", "四", "五", "六"]}, se: {months: ["Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"], dayOfWeekShort: ["Sön", "Mån", "Tis", "Ons", "Tor", "Fre", "Lör"]}, kr: {months: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"], dayOfWeekShort: ["일", "월", "화", "수", "목", "금", "토"], dayOfWeek: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"]}, it: {months: ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"], dayOfWeekShort: ["Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab"], dayOfWeek: ["Domenica", "Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato"]}, da: {months: ["January", "Februar", "Marts", "April", "Maj", "Juni", "July", "August", "September", "Oktober", "November", "December"], dayOfWeekShort: ["Søn", "Man", "Tir", "Ons", "Tor", "Fre", "Lør"], dayOfWeek: ["søndag", "mandag", "tirsdag", "onsdag", "torsdag", "fredag", "lørdag"]}, no: {months: ["Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"], dayOfWeekShort: ["Søn", "Man", "Tir", "Ons", "Tor", "Fre", "Lør"], dayOfWeek: ["Søndag", "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag"]}, ja: {months: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"], dayOfWeekShort: ["日", "月", "火", "水", "木", "金", "土"], dayOfWeek: ["日曜", "月曜", "火曜", "水曜", "木曜", "金曜", "土曜"]}, vi: {months: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"], dayOfWeekShort: ["CN", "T2", "T3", "T4", "T5", "T6", "T7"], dayOfWeek: ["Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"]}, sl: {months: ["Januar", "Februar", "Marec", "April", "Maj", "Junij", "Julij", "Avgust", "September", "Oktober", "November", "December"], dayOfWeekShort: ["Ned", "Pon", "Tor", "Sre", "Čet", "Pet", "Sob"], dayOfWeek: ["Nedelja", "Ponedeljek", "Torek", "Sreda", "Četrtek", "Petek", "Sobota"]}, cs: {months: ["Leden", "Únor", "Březen", "Duben", "Květen", "Červen", "Červenec", "Srpen", "Září", "Říjen", "Listopad", "Prosinec"], dayOfWeekShort: ["Ne", "Po", "Út", "St", "Čt", "Pá", "So"]}, hu: {months: ["Január", "Február", "Március", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December"], dayOfWeekShort: ["Va", "Hé", "Ke", "Sze", "Cs", "Pé", "Szo"], dayOfWeek: ["vasárnap", "hétfő", "kedd", "szerda", "csütörtök", "péntek", "szombat"]}, az: {months: ["Yanvar", "Fevral", "Mart", "Aprel", "May", "Iyun", "Iyul", "Avqust", "Sentyabr", "Oktyabr", "Noyabr", "Dekabr"], dayOfWeekShort: ["B", "Be", "Ça", "Ç", "Ca", "C", "Ş"], dayOfWeek: ["Bazar", "Bazar ertəsi", "Çərşənbə axşamı", "Çərşənbə", "Cümə axşamı", "Cümə", "Şənbə"]}, bs: {months: ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"], dayOfWeekShort: ["Ned", "Pon", "Uto", "Sri", "Čet", "Pet", "Sub"], dayOfWeek: ["Nedjelja", "Ponedjeljak", "Utorak", "Srijeda", "Četvrtak", "Petak", "Subota"]}, ca: {months: ["Gener", "Febrer", "Març", "Abril", "Maig", "Juny", "Juliol", "Agost", "Setembre", "Octubre", "Novembre", "Desembre"], dayOfWeekShort: ["Dg", "Dl", "Dt", "Dc", "Dj", "Dv", "Ds"], dayOfWeek: ["Diumenge", "Dilluns", "Dimarts", "Dimecres", "Dijous", "Divendres", "Dissabte"]}, "en-GB": {months: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"], dayOfWeekShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"], dayOfWeek: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"]}, et: {months: ["Jaanuar", "Veebruar", "Märts", "Aprill", "Mai", "Juuni", "Juuli", "August", "September", "Oktoober", "November", "Detsember"], dayOfWeekShort: ["P", "E", "T", "K", "N", "R", "L"], dayOfWeek: ["Pühapäev", "Esmaspäev", "Teisipäev", "Kolmapäev", "Neljapäev", "Reede", "Laupäev"]}, eu: {months: ["Urtarrila", "Otsaila", "Martxoa", "Apirila", "Maiatza", "Ekaina", "Uztaila", "Abuztua", "Iraila", "Urria", "Azaroa", "Abendua"], dayOfWeekShort: ["Ig.", "Al.", "Ar.", "Az.", "Og.", "Or.", "La."], dayOfWeek: ["Igandea", "Astelehena", "Asteartea", "Asteazkena", "Osteguna", "Ostirala", "Larunbata"]}, fi: {months: ["Tammikuu", "Helmikuu", "Maaliskuu", "Huhtikuu", "Toukokuu", "Kesäkuu", "Heinäkuu", "Elokuu", "Syyskuu", "Lokakuu", "Marraskuu", "Joulukuu"], dayOfWeekShort: ["Su", "Ma", "Ti", "Ke", "To", "Pe", "La"], dayOfWeek: ["sunnuntai", "maanantai", "tiistai", "keskiviikko", "torstai", "perjantai", "lauantai"]}, gl: {months: ["Xan", "Feb", "Maz", "Abr", "Mai", "Xun", "Xul", "Ago", "Set", "Out", "Nov", "Dec"], dayOfWeekShort: ["Dom", "Lun", "Mar", "Mer", "Xov", "Ven", "Sab"], dayOfWeek: ["Domingo", "Luns", "Martes", "Mércores", "Xoves", "Venres", "Sábado"]}, hr: {months: ["Siječanj", "Veljača", "Ožujak", "Travanj", "Svibanj", "Lipanj", "Srpanj", "Kolovoz", "Rujan", "Listopad", "Studeni", "Prosinac"], dayOfWeekShort: ["Ned", "Pon", "Uto", "Sri", "Čet", "Pet", "Sub"], dayOfWeek: ["Nedjelja", "Ponedjeljak", "Utorak", "Srijeda", "Četvrtak", "Petak", "Subota"]}, ko: {months: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"], dayOfWeekShort: ["일", "월", "화", "수", "목", "금", "토"], dayOfWeek: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"]}, lt: {months: ["Sausio", "Vasario", "Kovo", "Balandžio", "Gegužės", "Birželio", "Liepos", "Rugpjūčio", "Rugsėjo", "Spalio", "Lapkričio", "Gruodžio"], dayOfWeekShort: ["Sek", "Pir", "Ant", "Tre", "Ket", "Pen", "Šeš"], dayOfWeek: ["Sekmadienis", "Pirmadienis", "Antradienis", "Trečiadienis", "Ketvirtadienis", "Penktadienis", "Šeštadienis"]}, lv: {months: ["Janvāris", "Februāris", "Marts", "Aprīlis ", "Maijs", "Jūnijs", "Jūlijs", "Augusts", "Septembris", "Oktobris", "Novembris", "Decembris"], dayOfWeekShort: ["Sv", "Pr", "Ot", "Tr", "Ct", "Pk", "St"], dayOfWeek: ["Svētdiena", "Pirmdiena", "Otrdiena", "Trešdiena", "Ceturtdiena", "Piektdiena", "Sestdiena"]}, mk: {months: ["јануари", "февруари", "март", "април", "мај", "јуни", "јули", "август", "септември", "октомври", "ноември", "декември"], dayOfWeekShort: ["нед", "пон", "вто", "сре", "чет", "пет", "саб"], dayOfWeek: ["Недела", "Понеделник", "Вторник", "Среда", "Четврток", "Петок", "Сабота"]}, mn: {months: ["1-р сар", "2-р сар", "3-р сар", "4-р сар", "5-р сар", "6-р сар", "7-р сар", "8-р сар", "9-р сар", "10-р сар", "11-р сар", "12-р сар"], dayOfWeekShort: ["Дав", "Мяг", "Лха", "Пүр", "Бсн", "Бям", "Ням"], dayOfWeek: ["Даваа", "Мягмар", "Лхагва", "Пүрэв", "Баасан", "Бямба", "Ням"]}, "pt-BR": {months: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"], dayOfWeekShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"], dayOfWeek: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"]}, sk: {months: ["Január", "Február", "Marec", "Apríl", "Máj", "Jún", "Júl", "August", "September", "Október", "November", "December"], dayOfWeekShort: ["Ne", "Po", "Ut", "St", "Št", "Pi", "So"], dayOfWeek: ["Nedeľa", "Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok", "Sobota"]}, sq: {months: ["Janar", "Shkurt", "Mars", "Prill", "Maj", "Qershor", "Korrik", "Gusht", "Shtator", "Tetor", "Nëntor", "Dhjetor"], dayOfWeekShort: ["Die", "Hën", "Mar", "Mër", "Enj", "Pre", "Shtu"], dayOfWeek: ["E Diel", "E Hënë", "E Martē", "E Mërkurë", "E Enjte", "E Premte", "E Shtunë"]}, "sr-YU": {months: ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"], dayOfWeekShort: ["Ned", "Pon", "Uto", "Sre", "čet", "Pet", "Sub"], dayOfWeek: ["Nedelja", "Ponedeljak", "Utorak", "Sreda", "Četvrtak", "Petak", "Subota"]}, sr: {months: ["јануар", "фебруар", "март", "април", "мај", "јун", "јул", "август", "септембар", "октобар", "новембар", "децембар"], dayOfWeekShort: ["нед", "пон", "уто", "сре", "чет", "пет", "суб"], dayOfWeek: ["Недеља", "Понедељак", "Уторак", "Среда", "Четвртак", "Петак", "Субота"]}, sv: {months: ["Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"], dayOfWeekShort: ["Sön", "Mån", "Tis", "Ons", "Tor", "Fre", "Lör"], dayOfWeek: ["Söndag", "Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag"]}, "zh-TW": {months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"], dayOfWeekShort: ["日", "一", "二", "三", "四", "五", "六"], dayOfWeek: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"]}, zh: {months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"], dayOfWeekShort: ["日", "一", "二", "三", "四", "五", "六"], dayOfWeek: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"]}, he: {months: ["ינואר", "פברואר", "מרץ", "אפריל", "מאי", "יוני", "יולי", "אוגוסט", "ספטמבר", "אוקטובר", "נובמבר", "דצמבר"], dayOfWeekShort: ["א'", "ב'", "ג'", "ד'", "ה'", "ו'", "שבת"], dayOfWeek: ["ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת", "ראשון"]}, hy: {months: ["Հունվար", "Փետրվար", "Մարտ", "Ապրիլ", "Մայիս", "Հունիս", "Հուլիս", "Օգոստոս", "Սեպտեմբեր", "Հոկտեմբեր", "Նոյեմբեր", "Դեկտեմբեր"], dayOfWeekShort: ["Կի", "Երկ", "Երք", "Չոր", "Հնգ", "Ուրբ", "Շբթ"], dayOfWeek: ["Կիրակի", "Երկուշաբթի", "Երեքշաբթի", "Չորեքշաբթի", "Հինգշաբթի", "Ուրբաթ", "Շաբաթ"]}, kg: {months: ["Үчтүн айы", "Бирдин айы", "Жалган Куран", "Чын Куран", "Бугу", "Кулжа", "Теке", "Баш Оона", "Аяк Оона", "Тогуздун айы", "Жетинин айы", "Бештин айы"], dayOfWeekShort: ["Жек", "Дүй", "Шей", "Шар", "Бей", "Жум", "Ише"], dayOfWeek: ["Жекшемб", "Дүйшөмб", "Шейшемб", "Шаршемб", "Бейшемби", "Жума", "Ишенб"]}}, value: "", rtl: !1, format: "Y/m/d H:i", formatTime: "H:i", formatDate: "Y/m/d", startDate: !1, step: 60, monthChangeSpinner: !0, closeOnDateSelect: !1, closeOnTimeSelect: !0, closeOnWithoutClick: !0, closeOnInputClick: !0, timepicker: !0, datepicker: !0, weeks: !1, defaultTime: !1, defaultDate: !1, minDate: !1, maxDate: !1, minTime: !1, maxTime: !1, disabledMinTime: !1, disabledMaxTime: !1, allowTimes: [], opened: !1, initTime: !0, inline: !1, theme: "", onSelectDate: function () {
    }, onSelectTime: function () {
    }, onChangeMonth: function () {
    }, onChangeYear: function () {
    }, onChangeDateTime: function () {
    }, onShow: function () {
    }, onClose: function () {
    }, onGenerate: function () {
    }, withoutCopyright: !0, inverseButton: !1, hours12: !1, next: "xdsoft_next", prev: "xdsoft_prev", dayOfWeekStart: 0, parentID: "body", timeHeightInTimePicker: 25, timepickerScrollbar: !0, todayButton: !0, prevButton: !0, nextButton: !0, defaultSelect: !0, scrollMonth: !0, scrollTime: !0, scrollInput: !0, lazyInit: !1, mask: !1, validateOnBlur: !0, allowBlank: !0, yearStart: 1950, yearEnd: 2050, monthStart: 0, monthEnd: 11, style: "", id: "", fixed: !1, roundTime: "round", className: "", weekends: [], highlightedDates: [], highlightedPeriods: [], disabledDates: [], disabledWeekDays: [], yearOffset: 0, beforeShowDay: null, enterLikeTab: !0, showApplyButton: !1}, a = "en", r = "en";
    e.datetimepicker = {setLocale: function (e) {
        r = t.i18n[e] ? e : a, Date.monthNames = t.i18n[r].months, Date.dayNames = t.i18n[r].dayOfWeek
    }}, window.getComputedStyle || (window.getComputedStyle = function (e) {
        return this.el = e, this.getPropertyValue = function (t) {
            var a = /(\-([a-z]){1})/g;
            return"float" === t && (t = "styleFloat"), a.test(t) && (t = t.replace(a, function (e, t, a) {
                return a.toUpperCase()
            })), e.currentStyle[t] || null
        }, this
    }), Array.prototype.indexOf || (Array.prototype.indexOf = function (e, t) {
        var a, r;
        for (a = t || 0, r = this.length; r > a; a += 1)if (this[a] === e)return a;
        return-1
    }), Date.prototype.countDaysInMonth = function () {
        return new Date(this.getFullYear(), this.getMonth() + 1, 0).getDate()
    }, e.fn.xdsoftScroller = function (t) {
        return this.each(function () {
            var n, o, s, d, f, a = e(this), r = function (e) {
                var a, t = {x: 0, y: 0};
                return"touchstart" === e.type || "touchmove" === e.type || "touchend" === e.type || "touchcancel" === e.type ? (a = e.originalEvent.touches[0] || e.originalEvent.changedTouches[0], t.x = a.clientX, t.y = a.clientY) : ("mousedown" === e.type || "mouseup" === e.type || "mousemove" === e.type || "mouseover" === e.type || "mouseout" === e.type || "mouseenter" === e.type || "mouseleave" === e.type) && (t.x = e.clientX, t.y = e.clientY), t
            }, u = 100, l = !1, m = 0, c = 0, h = 0, g = !1, p = 0, k = function () {
            };
            return"hide" === t ? void a.find(".xdsoft_scrollbar").hide() : (e(this).hasClass("xdsoft_scroller_box") || (n = a.children().eq(0), o = a[0].clientHeight, s = n[0].offsetHeight, d = e('<div class="xdsoft_scrollbar"></div>'), f = e('<div class="xdsoft_scroller"></div>'), d.append(f), a.addClass("xdsoft_scroller_box").append(d), k = function (e) {
                var t = r(e).y - m + p;
                0 > t && (t = 0), t + f[0].offsetHeight > h && (t = h - f[0].offsetHeight), a.trigger("scroll_element.xdsoft_scroller", [u ? t / u : 0])
            }, f.on("touchstart.xdsoft_scroller mousedown.xdsoft_scroller", function (i) {
                o || a.trigger("resize_scroll.xdsoft_scroller", [t]), m = r(i).y, p = parseInt(f.css("margin-top"), 10), h = d[0].offsetHeight, "mousedown" === i.type ? (document && e(document.body).addClass("xdsoft_noselect"), e([document.body, window]).on("mouseup.xdsoft_scroller", function n() {
                    e([document.body, window]).off("mouseup.xdsoft_scroller", n).off("mousemove.xdsoft_scroller", k).removeClass("xdsoft_noselect")
                }), e(document.body).on("mousemove.xdsoft_scroller", k)) : (g = !0, i.stopPropagation(), i.preventDefault())
            }).on("touchmove", function (e) {
                g && (e.preventDefault(), k(e))
            }).on("touchend touchcancel", function () {
                g = !1, p = 0
            }), a.on("scroll_element.xdsoft_scroller", function (e, t) {
                o || a.trigger("resize_scroll.xdsoft_scroller", [t, !0]), t = t > 1 ? 1 : 0 > t || isNaN(t) ? 0 : t, f.css("margin-top", u * t), setTimeout(function () {
                    n.css("marginTop", -parseInt((n[0].offsetHeight - o) * t, 10))
                }, 10)
            }).on("resize_scroll.xdsoft_scroller", function (e, t, r) {
                var i, l;
                o = a[0].clientHeight, s = n[0].offsetHeight, i = o / s, l = i * d[0].offsetHeight, i > 1 ? f.hide() : (f.show(), f.css("height", parseInt(l > 10 ? l : 10, 10)), u = d[0].offsetHeight - f[0].offsetHeight, r !== !0 && a.trigger("scroll_element.xdsoft_scroller", [t || Math.abs(parseInt(n.css("marginTop"), 10)) / (s - o)]))
            }), a.on("mousewheel", function (e) {
                var t = Math.abs(parseInt(n.css("marginTop"), 10));
                return t -= 20 * e.deltaY, 0 > t && (t = 0), a.trigger("scroll_element.xdsoft_scroller", [t / (s - o)]), e.stopPropagation(), !1
            }), a.on("touchstart", function (e) {
                l = r(e), c = Math.abs(parseInt(n.css("marginTop"), 10))
            }), a.on("touchmove", function (e) {
                if (l) {
                    e.preventDefault();
                    var t = r(e);
                    a.trigger("scroll_element.xdsoft_scroller", [(c - (t.y - l.y)) / (s - o)])
                }
            }), a.on("touchend touchcancel", function () {
                l = !1, c = 0
            })), void a.trigger("resize_scroll.xdsoft_scroller", [t]))
        })
    }, e.fn.datetimepicker = function (a) {
        var _, W, n = 48, o = 57, s = 96, d = 105, f = 17, u = 46, l = 13, m = 27, c = 8, h = 37, g = 38, p = 39, k = 40, y = 9, x = 116, b = 65, v = 67, T = 86, D = 90, S = 89, O = !1, w = e.isPlainObject(a) || !a ? e.extend(!0, {}, t, a) : e.extend(!0, {}, t), M = 0, F = function (e) {
            e.on("open.xdsoft focusin.xdsoft mousedown.xdsoft", function t() {
                e.is(":disabled") || e.data("xdsoft_datetimepicker") || (clearTimeout(M), M = setTimeout(function () {
                    e.data("xdsoft_datetimepicker") || _(e), e.off("open.xdsoft focusin.xdsoft mousedown.xdsoft", t).trigger("open.xdsoft")
                }, 100))
            })
        };
        return _ = function (t) {
            function K() {
                var a, e = !1;
                return w.startDate ? e = R.strToDate(w.startDate) : (e = w.value || (t && t.val && t.val() ? t.val() : ""), e ? e = R.strToDateTime(e) : w.defaultDate && (e = R.strToDateTime(w.defaultDate), w.defaultTime && (a = R.strtotime(w.defaultTime), e.setHours(a.getHours()), e.setMinutes(a.getMinutes())))), e && R.isValidDate(e) ? M.data("changed", !0) : e = "", e || 0
            }

            var I, Y, L, V, B, R, M = e('<div class="xdsoft_datetimepicker xdsoft_noselect"></div>'), _ = e('<div class="xdsoft_copyright"><a target="_blank" href="http://xdsoft.net/jqplugins/datetimepicker/">xdsoft.net</a></div>'), W = e('<div class="xdsoft_datepicker active"></div>'), F = e('<div class="xdsoft_mounthpicker"><button type="button" class="xdsoft_prev"></button><button type="button" class="xdsoft_today_button"></button><div class="xdsoft_label xdsoft_month"><span></span><i></i></div><div class="xdsoft_label xdsoft_year"><span></span><i></i></div><button type="button" class="xdsoft_next"></button></div>'), C = e('<div class="xdsoft_calendar"></div>'), A = e('<div class="xdsoft_timepicker active"><button type="button" class="xdsoft_prev"></button><div class="xdsoft_time_box"></div><button type="button" class="xdsoft_next"></button></div>'), P = A.find(".xdsoft_time_box").eq(0), J = e('<div class="xdsoft_time_variant"></div>'), j = e('<button type="button" class="xdsoft_save_selected blue-gradient-button">Save Selected</button>'), z = e('<div class="xdsoft_select xdsoft_monthselect"><div></div></div>'), N = e('<div class="xdsoft_select xdsoft_yearselect"><div></div></div>'), H = !1, E = 0, q = 0;
            w.id && M.attr("id", w.id), w.style && M.attr("style", w.style), w.weeks && M.addClass("xdsoft_showweeks"), w.rtl && M.addClass("xdsoft_rtl"), M.addClass("xdsoft_" + w.theme), M.addClass(w.className), F.find(".xdsoft_month span").after(z), F.find(".xdsoft_year span").after(N), F.find(".xdsoft_month,.xdsoft_year").on("mousedown.xdsoft", function (t) {
                var o, s, a = e(this).find(".xdsoft_select").eq(0), r = 0, i = 0, n = a.is(":visible");
                for (F.find(".xdsoft_select").hide(), R.currentTime && (r = R.currentTime[e(this).hasClass("xdsoft_month") ? "getMonth" : "getFullYear"]()), a[n ? "hide" : "show"](), o = a.find("div.xdsoft_option"), s = 0; s < o.length && o.eq(s).data("value") !== r; s += 1)i += o[0].offsetHeight;
                return a.xdsoftScroller(i / (a.children()[0].offsetHeight - a[0].clientHeight)), t.stopPropagation(), !1
            }), F.find(".xdsoft_select").xdsoftScroller().on("mousedown.xdsoft", function (e) {
                e.stopPropagation(), e.preventDefault()
            }).on("mousedown.xdsoft", ".xdsoft_option", function () {
                (void 0 === R.currentTime || null === R.currentTime) && (R.currentTime = R.now());
                var a = R.currentTime.getFullYear();
                R && R.currentTime && R.currentTime[e(this).parent().parent().hasClass("xdsoft_monthselect") ? "setMonth" : "setFullYear"](e(this).data("value")), e(this).parent().parent().hide(), M.trigger("xchange.xdsoft"), w.onChangeMonth && e.isFunction(w.onChangeMonth) && w.onChangeMonth.call(M, R.currentTime, M.data("input")), a !== R.currentTime.getFullYear() && e.isFunction(w.onChangeYear) && w.onChangeYear.call(M, R.currentTime, M.data("input"))
            }), M.setOptions = function (a) {
                var r = {}, _ = function (e) {
                    try {
                        if (document.selection && document.selection.createRange) {
                            var t = document.selection.createRange();
                            return t.getBookmark().charCodeAt(2) - 2
                        }
                        if (e.setSelectionRange)return e.selectionStart
                    } catch (a) {
                        return 0
                    }
                }, C = function (e, t) {
                    if (e = "string" == typeof e || e instanceof String ? document.getElementById(e) : e, !e)return!1;
                    if (e.createTextRange) {
                        var a = e.createTextRange();
                        return a.collapse(!0), a.moveEnd("character", t), a.moveStart("character", t), a.select(), !0
                    }
                    return e.setSelectionRange ? (e.setSelectionRange(t, t), !0) : !1
                }, J = function (e, t) {
                    var a = e.replace(/([\[\]\/\{\}\(\)\-\.\+]{1})/g, "\\$1").replace(/_/g, "{digit+}").replace(/([0-9]{1})/g, "{digit$1}").replace(/\{digit([0-9]{1})\}/g, "[0-$1_]{1}").replace(/\{digit[\+]\}/g, "[0-9_]{1}");
                    return new RegExp(a).test(t)
                };
                w = e.extend(!0, {}, w, a), a.allowTimes && e.isArray(a.allowTimes) && a.allowTimes.length && (w.allowTimes = e.extend(!0, [], a.allowTimes)), a.weekends && e.isArray(a.weekends) && a.weekends.length && (w.weekends = e.extend(!0, [], a.weekends)), a.highlightedDates && e.isArray(a.highlightedDates) && a.highlightedDates.length && (e.each(a.highlightedDates, function (t, a) {
                    var o, n = e.map(a.split(","), e.trim), s = new i(Date.parseDate(n[0], w.formatDate), n[1], n[2]), d = s.date.dateFormat(w.formatDate);
                    void 0 !== r[d] ? (o = r[d].desc, o && o.length && s.desc && s.desc.length && (r[d].desc = o + "\n" + s.desc)) : r[d] = s
                }), w.highlightedDates = e.extend(!0, [], r)), a.highlightedPeriods && e.isArray(a.highlightedPeriods) && a.highlightedPeriods.length && (r = e.extend(!0, [], w.highlightedDates), e.each(a.highlightedPeriods, function (t, a) {
                    var n, o, s, d, f, u, l;
                    if (e.isArray(a))n = a[0], o = a[1], s = a[2], l = a[3]; else {
                        var m = e.map(a.split(","), e.trim);
                        n = Date.parseDate(m[0], w.formatDate), o = Date.parseDate(m[1], w.formatDate), s = m[2], l = m[3]
                    }
                    for (; o >= n;)d = new i(n, s, l), f = n.dateFormat(w.formatDate), n.setDate(n.getDate() + 1), void 0 !== r[f] ? (u = r[f].desc, u && u.length && d.desc && d.desc.length && (r[f].desc = u + "\n" + d.desc)) : r[f] = d
                }), w.highlightedDates = e.extend(!0, [], r)), a.disabledDates && e.isArray(a.disabledDates) && a.disabledDates.length && (w.disabledDates = e.extend(!0, [], a.disabledDates)), a.disabledWeekDays && e.isArray(a.disabledWeekDays) && a.disabledWeekDays.length && (w.disabledWeekDays = e.extend(!0, [], a.disabledWeekDays)), !w.open && !w.opened || w.inline || t.trigger("open.xdsoft"), w.inline && (H = !0, M.addClass("xdsoft_inline"), t.after(M).hide()), w.inverseButton && (w.next = "xdsoft_prev", w.prev = "xdsoft_next"), w.datepicker ? W.addClass("active") : W.removeClass("active"), w.timepicker ? A.addClass("active") : A.removeClass("active"), w.value && (R.setCurrentTime(w.value), t && t.val && t.val(R.str)), w.dayOfWeekStart = isNaN(w.dayOfWeekStart) ? 0 : parseInt(w.dayOfWeekStart, 10) % 7, w.timepickerScrollbar || P.xdsoftScroller("hide"), w.minDate && /^[\+\-](.*)$/.test(w.minDate) && (w.minDate = R.strToDateTime(w.minDate).dateFormat(w.formatDate)), w.maxDate && /^[\+\-](.*)$/.test(w.maxDate) && (w.maxDate = R.strToDateTime(w.maxDate).dateFormat(w.formatDate)), j.toggle(w.showApplyButton), F.find(".xdsoft_today_button").css("visibility", w.todayButton ? "visible" : "hidden"), F.find("." + w.prev).css("visibility", w.prevButton ? "visible" : "hidden"), F.find("." + w.next).css("visibility", w.nextButton ? "visible" : "hidden"), w.mask && (t.off("keydown.xdsoft"), w.mask === !0 && (w.mask = w.format.replace(/Y/g, "9999").replace(/F/g, "9999").replace(/m/g, "19").replace(/d/g, "39").replace(/H/g, "29").replace(/i/g, "59").replace(/s/g, "59")), "string" === e.type(w.mask) && (J(w.mask, t.val()) || t.val(w.mask.replace(/[0-9]/g, "_")), t.on("keydown.xdsoft", function (a) {
                    var M, W, r = this.value, i = a.which;
                    if (i >= n && o >= i || i >= s && d >= i || i === c || i === u) {
                        for (M = _(this), W = i !== c && i !== u ? String.fromCharCode(i >= s && d >= i ? i - n : i) : "_", i !== c && i !== u || !M || (M -= 1, W = "_"); /[^0-9_]/.test(w.mask.substr(M, 1)) && M < w.mask.length && M > 0;)M += i === c || i === u ? -1 : 1;
                        if (r = r.substr(0, M) + W + r.substr(M + 1), "" === e.trim(r))r = w.mask.replace(/[0-9]/g, "_"); else if (M === w.mask.length)return a.preventDefault(), !1;
                        for (M += i === c || i === u ? 0 : 1; /[^0-9_]/.test(w.mask.substr(M, 1)) && M < w.mask.length && M > 0;)M += i === c || i === u ? -1 : 1;
                        J(w.mask, r) ? (this.value = r, C(this, M)) : "" === e.trim(r) ? this.value = w.mask.replace(/[0-9]/g, "_") : t.trigger("error_input.xdsoft")
                    } else if (-1 !== [b, v, T, D, S].indexOf(i) && O || -1 !== [m, g, k, h, p, x, f, y, l].indexOf(i))return!0;
                    return a.preventDefault(), !1
                }))), w.validateOnBlur && t.off("blur.xdsoft").on("blur.xdsoft", function () {
                    if (w.allowBlank && !e.trim(e(this).val()).length)e(this).val(null), M.data("xdsoft_datetime").empty(); else if (Date.parseDate(e(this).val(), w.format))M.data("xdsoft_datetime").setCurrentTime(e(this).val()); else {
                        var t = +[e(this).val()[0], e(this).val()[1]].join(""), a = +[e(this).val()[2], e(this).val()[3]].join("");
                        e(this).val(!w.datepicker && w.timepicker && t >= 0 && 24 > t && a >= 0 && 60 > a ? [t, a].map(function (e) {
                            return e > 9 ? e : "0" + e
                        }).join(":") : R.now().dateFormat(w.format)), M.data("xdsoft_datetime").setCurrentTime(e(this).val())
                    }
                    M.trigger("changedatetime.xdsoft")
                }), w.dayOfWeekStartPrev = 0 === w.dayOfWeekStart ? 6 : w.dayOfWeekStart - 1, M.trigger("xchange.xdsoft").trigger("afterOpen.xdsoft")
            }, M.data("options", w).on("mousedown.xdsoft", function (e) {
                return e.stopPropagation(), e.preventDefault(), N.hide(), z.hide(), !1
            }), P.append(J), P.xdsoftScroller(), M.on("afterOpen.xdsoft", function () {
                P.xdsoftScroller()
            }), M.append(W).append(A), w.withoutCopyright !== !0 && M.append(_), W.append(F).append(C).append(j), e(w.parentID).append(M), I = function () {
                var t = this;
                t.now = function (e) {
                    var r, i, a = new Date;
                    return!e && w.defaultDate && (r = t.strToDateTime(w.defaultDate), a.setFullYear(r.getFullYear()), a.setMonth(r.getMonth()), a.setDate(r.getDate())), w.yearOffset && a.setFullYear(a.getFullYear() + w.yearOffset), !e && w.defaultTime && (i = t.strtotime(w.defaultTime), a.setHours(i.getHours()), a.setMinutes(i.getMinutes())), a
                }, t.isValidDate = function (e) {
                    return"[object Date]" !== Object.prototype.toString.call(e) ? !1 : !isNaN(e.getTime())
                }, t.setCurrentTime = function (e) {
                    t.currentTime = "string" == typeof e ? t.strToDateTime(e) : t.isValidDate(e) ? e : t.now(), M.trigger("xchange.xdsoft")
                }, t.empty = function () {
                    t.currentTime = null
                }, t.getCurrentTime = function () {
                    return t.currentTime
                }, t.nextMonth = function () {
                    (void 0 === t.currentTime || null === t.currentTime) && (t.currentTime = t.now());
                    var r, a = t.currentTime.getMonth() + 1;
                    return 12 === a && (t.currentTime.setFullYear(t.currentTime.getFullYear() + 1), a = 0), r = t.currentTime.getFullYear(), t.currentTime.setDate(Math.min(new Date(t.currentTime.getFullYear(), a + 1, 0).getDate(), t.currentTime.getDate())), t.currentTime.setMonth(a), w.onChangeMonth && e.isFunction(w.onChangeMonth) && w.onChangeMonth.call(M, R.currentTime, M.data("input")), r !== t.currentTime.getFullYear() && e.isFunction(w.onChangeYear) && w.onChangeYear.call(M, R.currentTime, M.data("input")), M.trigger("xchange.xdsoft"), a
                }, t.prevMonth = function () {
                    (void 0 === t.currentTime || null === t.currentTime) && (t.currentTime = t.now());
                    var a = t.currentTime.getMonth() - 1;
                    return-1 === a && (t.currentTime.setFullYear(t.currentTime.getFullYear() - 1), a = 11), t.currentTime.setDate(Math.min(new Date(t.currentTime.getFullYear(), a + 1, 0).getDate(), t.currentTime.getDate())), t.currentTime.setMonth(a), w.onChangeMonth && e.isFunction(w.onChangeMonth) && w.onChangeMonth.call(M, R.currentTime, M.data("input")), M.trigger("xchange.xdsoft"), a
                }, t.getWeekOfYear = function (e) {
                    var t = new Date(e.getFullYear(), 0, 1);
                    return Math.ceil(((e - t) / 864e5 + t.getDay() + 1) / 7)
                }, t.strToDateTime = function (e) {
                    var r, i, a = [];
                    return e && e instanceof Date && t.isValidDate(e) ? e : (a = /^(\+|\-)(.*)$/.exec(e), a && (a[2] = Date.parseDate(a[2], w.formatDate)), a && a[2] ? (r = a[2].getTime() - 6e4 * a[2].getTimezoneOffset(), i = new Date(t.now(!0).getTime() + parseInt(a[1] + "1", 10) * r)) : i = e ? Date.parseDate(e, w.format) : t.now(), t.isValidDate(i) || (i = t.now()), i)
                }, t.strToDate = function (e) {
                    if (e && e instanceof Date && t.isValidDate(e))return e;
                    var a = e ? Date.parseDate(e, w.formatDate) : t.now(!0);
                    return t.isValidDate(a) || (a = t.now(!0)), a
                }, t.strtotime = function (e) {
                    if (e && e instanceof Date && t.isValidDate(e))return e;
                    var a = e ? Date.parseDate(e, w.formatTime) : t.now(!0);
                    return t.isValidDate(a) || (a = t.now(!0)), a
                }, t.str = function () {
                    return t.currentTime.dateFormat(w.format)
                }, t.currentTime = this.now()
            }, R = new I, j.on("click", function (e) {
                e.preventDefault(), M.data("changed", !0), R.setCurrentTime(K()), t.val(R.str()), M.trigger("close.xdsoft")
            }), F.find(".xdsoft_today_button").on("mousedown.xdsoft", function () {
                M.data("changed", !0), R.setCurrentTime(0), M.trigger("afterOpen.xdsoft")
            }).on("dblclick.xdsoft", function () {
                var a, r, e = R.getCurrentTime();
                e = new Date(e.getFullYear(), e.getMonth(), e.getDate()), a = R.strToDate(w.minDate), a = new Date(a.getFullYear(), a.getMonth(), a.getDate()), a > e || (r = R.strToDate(w.maxDate), r = new Date(r.getFullYear(), r.getMonth(), r.getDate()), e > r || (t.val(R.str()), t.trigger("change"), M.trigger("close.xdsoft")))
            }), F.find(".xdsoft_prev,.xdsoft_next").on("mousedown.xdsoft", function () {
                var t = e(this), a = 0, r = !1;
                !function i(e) {
                    t.hasClass(w.next) ? R.nextMonth() : t.hasClass(w.prev) && R.prevMonth(), w.monthChangeSpinner && (r || (a = setTimeout(i, e || 100)))
                }(500), e([document.body, window]).on("mouseup.xdsoft", function n() {
                    clearTimeout(a), r = !0, e([document.body, window]).off("mouseup.xdsoft", n)
                })
            }), A.find(".xdsoft_prev,.xdsoft_next").on("mousedown.xdsoft", function () {
                var t = e(this), a = 0, r = !1, i = 110;
                !function n(e) {
                    var o = P[0].clientHeight, s = J[0].offsetHeight, d = Math.abs(parseInt(J.css("marginTop"), 10));
                    t.hasClass(w.next) && s - o - w.timeHeightInTimePicker >= d ? J.css("marginTop", "-" + (d + w.timeHeightInTimePicker) + "px") : t.hasClass(w.prev) && d - w.timeHeightInTimePicker >= 0 && J.css("marginTop", "-" + (d - w.timeHeightInTimePicker) + "px"), P.trigger("scroll_element.xdsoft_scroller", [Math.abs(parseInt(J.css("marginTop"), 10) / (s - o))]), i = i > 10 ? 10 : i - 10, r || (a = setTimeout(n, e || i))
                }(500), e([document.body, window]).on("mouseup.xdsoft", function o() {
                    clearTimeout(a), r = !0, e([document.body, window]).off("mouseup.xdsoft", o)
                })
            }), Y = 0, M.on("xchange.xdsoft", function (t) {
                clearTimeout(Y), Y = setTimeout(function () {
                    (void 0 === R.currentTime || null === R.currentTime) && (R.currentTime = R.now());
                    for (var o, u, l, m, c, h, g, k, v, T, t = "", i = new Date(R.currentTime.getFullYear(), R.currentTime.getMonth(), 1, 12, 0, 0), n = 0, s = R.now(), d = !1, f = !1, p = [], y = !0, x = "", b = ""; i.getDay() !== w.dayOfWeekStart;)i.setDate(i.getDate() - 1);
                    for (t += "<table><thead><tr>", w.weeks && (t += "<th></th>"), o = 0; 7 > o; o += 1)t += "<th>" + w.i18n[r].dayOfWeekShort[(o + w.dayOfWeekStart) % 7] + "</th>";
                    for (t += "</tr></thead>", t += "<tbody>", w.maxDate !== !1 && (d = R.strToDate(w.maxDate), d = new Date(d.getFullYear(), d.getMonth(), d.getDate(), 23, 59, 59, 999)), w.minDate !== !1 && (f = R.strToDate(w.minDate), f = new Date(f.getFullYear(), f.getMonth(), f.getDate())); n < R.currentTime.countDaysInMonth() || i.getDay() !== w.dayOfWeekStart || R.currentTime.getMonth() === i.getMonth();)p = [], n += 1, l = i.getDay(), m = i.getDate(), c = i.getFullYear(), h = i.getMonth(), g = R.getWeekOfYear(i), T = "", p.push("xdsoft_date"), k = w.beforeShowDay && e.isFunction(w.beforeShowDay.call) ? w.beforeShowDay.call(M, i) : null, d !== !1 && i > d || f !== !1 && f > i || k && k[0] === !1 ? p.push("xdsoft_disabled") : -1 !== w.disabledDates.indexOf(i.dateFormat(w.formatDate)) ? p.push("xdsoft_disabled") : -1 !== w.disabledWeekDays.indexOf(l) && p.push("xdsoft_disabled"), k && "" !== k[1] && p.push(k[1]), R.currentTime.getMonth() !== h && p.push("xdsoft_other_month"), (w.defaultSelect || M.data("changed")) && R.currentTime.dateFormat(w.formatDate) === i.dateFormat(w.formatDate) && p.push("xdsoft_current"), s.dateFormat(w.formatDate) === i.dateFormat(w.formatDate) && p.push("xdsoft_today"), (0 === i.getDay() || 6 === i.getDay() || -1 !== w.weekends.indexOf(i.dateFormat(w.formatDate))) && p.push("xdsoft_weekend"), void 0 !== w.highlightedDates[i.dateFormat(w.formatDate)] && (u = w.highlightedDates[i.dateFormat(w.formatDate)], p.push(void 0 === u.style ? "xdsoft_highlighted_default" : u.style), T = void 0 === u.desc ? "" : u.desc), w.beforeShowDay && e.isFunction(w.beforeShowDay) && p.push(w.beforeShowDay(i)), y && (t += "<tr>", y = !1, w.weeks && (t += "<th>" + g + "</th>")), t += '<td data-date="' + m + '" data-month="' + h + '" data-year="' + c + '" class="xdsoft_date xdsoft_day_of_week' + i.getDay() + " " + p.join(" ") + '" title="' + T + '"><div>' + m + "</div></td>", i.getDay() === w.dayOfWeekStartPrev && (t += "</tr>", y = !0), i.setDate(m + 1);
                    if (t += "</tbody></table>", C.html(t), F.find(".xdsoft_label span").eq(0).text(w.i18n[r].months[R.currentTime.getMonth()]), F.find(".xdsoft_label span").eq(1).text(R.currentTime.getFullYear()), x = "", b = "", h = "", v = function (t, a) {
                        var i, n, r = R.now(), o = w.allowTimes && e.isArray(w.allowTimes) && w.allowTimes.length;
                        r.setHours(t), t = parseInt(r.getHours(), 10), r.setMinutes(a), a = parseInt(r.getMinutes(), 10), i = new Date(R.currentTime), i.setHours(t), i.setMinutes(a), p = [], (w.minDateTime !== !1 && w.minDateTime > i || w.maxTime !== !1 && R.strtotime(w.maxTime).getTime() < r.getTime() || w.minTime !== !1 && R.strtotime(w.minTime).getTime() > r.getTime()) && p.push("xdsoft_disabled"), (w.minDateTime !== !1 && w.minDateTime > i || w.disabledMinTime !== !1 && r.getTime() > R.strtotime(w.disabledMinTime).getTime() && w.disabledMaxTime !== !1 && r.getTime() < R.strtotime(w.disabledMaxTime).getTime()) && p.push("xdsoft_disabled"), n = new Date(R.currentTime), n.setHours(parseInt(R.currentTime.getHours(), 10)), o || n.setMinutes(Math[w.roundTime](R.currentTime.getMinutes() / w.step) * w.step), (w.initTime || w.defaultSelect || M.data("changed")) && n.getHours() === parseInt(t, 10) && (!o && w.step > 59 || n.getMinutes() === parseInt(a, 10)) && (w.defaultSelect || M.data("changed") ? p.push("xdsoft_current") : w.initTime && p.push("xdsoft_init_time")), parseInt(s.getHours(), 10) === parseInt(t, 10) && parseInt(s.getMinutes(), 10) === parseInt(a, 10) && p.push("xdsoft_today"), x += '<div class="xdsoft_time ' + p.join(" ") + '" data-hour="' + t + '" data-minute="' + a + '">' + r.dateFormat(w.formatTime) + "</div>"
                    }, w.allowTimes && e.isArray(w.allowTimes) && w.allowTimes.length)for (n = 0; n < w.allowTimes.length; n += 1)b = R.strtotime(w.allowTimes[n]).getHours(), h = R.strtotime(w.allowTimes[n]).getMinutes(), v(b, h); else for (n = 0, o = 0; n < (w.hours12 ? 12 : 24); n += 1)for (o = 0; 60 > o; o += w.step)b = (10 > n ? "0" : "") + n, h = (10 > o ? "0" : "") + o, v(b, h);
                    for (J.html(x), a = "", n = 0, n = parseInt(w.yearStart, 10) + w.yearOffset; n <= parseInt(w.yearEnd, 10) + w.yearOffset; n += 1)a += '<div class="xdsoft_option ' + (R.currentTime.getFullYear() === n ? "xdsoft_current" : "") + '" data-value="' + n + '">' + n + "</div>";
                    for (N.children().eq(0).html(a), n = parseInt(w.monthStart, 10), a = ""; n <= parseInt(w.monthEnd, 10); n += 1)a += '<div class="xdsoft_option ' + (R.currentTime.getMonth() === n ? "xdsoft_current" : "") + '" data-value="' + n + '">' + w.i18n[r].months[n] + "</div>";
                    z.children().eq(0).html(a), e(M).trigger("generate.xdsoft")
                }, 10), t.stopPropagation()
            }).on("afterOpen.xdsoft", function () {
                if (w.timepicker) {
                    var e, t, a, r;
                    J.find(".xdsoft_current").length ? e = ".xdsoft_current" : J.find(".xdsoft_init_time").length && (e = ".xdsoft_init_time"), e ? (t = P[0].clientHeight, a = J[0].offsetHeight, r = J.find(e).index() * w.timeHeightInTimePicker + 1, r > a - t && (r = a - t), P.trigger("scroll_element.xdsoft_scroller", [parseInt(r, 10) / (a - t)])) : P.trigger("scroll_element.xdsoft_scroller", [0])
                }
            }), L = 0, C.on("click.xdsoft", "td", function (a) {
                a.stopPropagation(), L += 1;
                var r = e(this), i = R.currentTime;
                return(void 0 === i || null === i) && (R.currentTime = R.now(), i = R.currentTime), r.hasClass("xdsoft_disabled") ? !1 : (i.setDate(1), i.setFullYear(r.data("year")), i.setMonth(r.data("month")), i.setDate(r.data("date")), M.trigger("select.xdsoft", [i]), t.val(R.str()), w.onSelectDate && e.isFunction(w.onSelectDate) && w.onSelectDate.call(M, R.currentTime, M.data("input"), a), M.data("changed", !0), M.trigger("xchange.xdsoft"), M.trigger("changedatetime.xdsoft"), (L > 1 || w.closeOnDateSelect === !0 || w.closeOnDateSelect === !1 && !w.timepicker) && !w.inline && M.trigger("close.xdsoft"), void setTimeout(function () {
                    L = 0
                }, 200))
            }), J.on("click.xdsoft", "div", function (t) {
                t.stopPropagation();
                var a = e(this), r = R.currentTime;
                return(void 0 === r || null === r) && (R.currentTime = R.now(), r = R.currentTime), a.hasClass("xdsoft_disabled") ? !1 : (r.setHours(a.data("hour")), r.setMinutes(a.data("minute")), M.trigger("select.xdsoft", [r]), M.data("input").val(R.str()), w.onSelectTime && e.isFunction(w.onSelectTime) && w.onSelectTime.call(M, R.currentTime, M.data("input"), t), M.data("changed", !0), M.trigger("xchange.xdsoft"), M.trigger("changedatetime.xdsoft"), void(w.inline !== !0 && w.closeOnTimeSelect === !0 && M.trigger("close.xdsoft")))
            }), W.on("mousewheel.xdsoft", function (e) {
                return w.scrollMonth ? (e.deltaY < 0 ? R.nextMonth() : R.prevMonth(), !1) : !0
            }), t.on("mousewheel.xdsoft", function (e) {
                return w.scrollInput ? !w.datepicker && w.timepicker ? (V = J.find(".xdsoft_current").length ? J.find(".xdsoft_current").eq(0).index() : 0, V + e.deltaY >= 0 && V + e.deltaY < J.children().length && (V += e.deltaY), J.children().eq(V).length && J.children().eq(V).trigger("mousedown"), !1) : w.datepicker && !w.timepicker ? (W.trigger(e, [e.deltaY, e.deltaX, e.deltaY]), t.val && t.val(R.str()), M.trigger("changedatetime.xdsoft"), !1) : void 0 : !0
            }), M.on("changedatetime.xdsoft", function (t) {
                if (w.onChangeDateTime && e.isFunction(w.onChangeDateTime)) {
                    var a = M.data("input");
                    w.onChangeDateTime.call(M, R.currentTime, a, t), delete w.value, a.trigger("change")
                }
            }).on("generate.xdsoft", function () {
                w.onGenerate && e.isFunction(w.onGenerate) && w.onGenerate.call(M, R.currentTime, M.data("input")), H && (M.trigger("afterOpen.xdsoft"), H = !1)
            }).on("click.xdsoft", function (e) {
                e.stopPropagation()
            }), V = 0, B = function () {
                var n, t = M.data("input").offset(), a = t.top + M.data("input")[0].offsetHeight - 1, r = t.left, i = "absolute";
                "rtl" == M.data("input").parent().css("direction") && (r -= M.outerWidth() - M.data("input").outerWidth()), w.fixed ? (a -= e(window).scrollTop(), r -= e(window).scrollLeft(), i = "fixed") : (a + M[0].offsetHeight > e(window).height() + e(window).scrollTop() && (a = t.top - M[0].offsetHeight + 1), 0 > a && (a = 0), r + M[0].offsetWidth > e(window).width() && (r = e(window).width() - M[0].offsetWidth)), n = M[0];
                do if (n = n.parentNode, "relative" === window.getComputedStyle(n).getPropertyValue("position") && e(window).width() >= n.offsetWidth) {
                    r -= (e(window).width() - n.offsetWidth) / 2;
                    break
                } while ("HTML" !== n.nodeName);
                M.css({left: r, top: a, position: i})
            }, M.on("open.xdsoft", function (t) {
                var a = !0;
                w.onShow && e.isFunction(w.onShow) && (a = w.onShow.call(M, R.currentTime, M.data("input"), t)), a !== !1 && (M.show(), B(), e(window).off("resize.xdsoft", B).on("resize.xdsoft", B), w.closeOnWithoutClick && e([document.body, window]).on("mousedown.xdsoft", function r() {
                    M.trigger("close.xdsoft"), e([document.body, window]).off("mousedown.xdsoft", r)
                }))
            }).on("close.xdsoft", function (t) {
                var a = !0;
                F.find(".xdsoft_month,.xdsoft_year").find(".xdsoft_select").hide(), w.onClose && e.isFunction(w.onClose) && (a = w.onClose.call(M, R.currentTime, M.data("input"), t)), a === !1 || w.opened || w.inline || M.hide(), t.stopPropagation()
            }).on("toggle.xdsoft", function () {
                M.trigger(M.is(":visible") ? "close.xdsoft" : "open.xdsoft")
            }).data("input", t), E = 0, q = 0, M.data("xdsoft_datetime", R), M.setOptions(w), R.setCurrentTime(K()), t.data("xdsoft_datetimepicker", M).on("open.xdsoft focusin.xdsoft mousedown.xdsoft", function () {
                t.is(":disabled") || t.data("xdsoft_datetimepicker").is(":visible") && w.closeOnInputClick || (clearTimeout(E), E = setTimeout(function () {
                    t.is(":disabled") || (H = !0, R.setCurrentTime(K()), M.trigger("open.xdsoft"))
                }, 100))
            }).on("keydown.xdsoft", function (t) {
                var r, i = (this.value, t.which);
                return-1 !== [l].indexOf(i) && w.enterLikeTab ? (r = e("input:visible,textarea:visible"), M.trigger("close.xdsoft"), r.eq(r.index(this) + 1).focus(), !1) : -1 !== [y].indexOf(i) ? (M.trigger("close.xdsoft"), !0) : void 0
            })
        }, W = function (t) {
            var a = t.data("xdsoft_datetimepicker");
            a && (a.data("xdsoft_datetime", null), a.remove(), t.data("xdsoft_datetimepicker", null).off(".xdsoft"), e(window).off("resize.xdsoft"), e([window, document.body]).off("mousedown.xdsoft"), t.unmousewheel && t.unmousewheel())
        }, e(document).off("keydown.xdsoftctrl keyup.xdsoftctrl").on("keydown.xdsoftctrl", function (e) {
            e.keyCode === f && (O = !0)
        }).on("keyup.xdsoftctrl", function (e) {
            e.keyCode === f && (O = !1)
        }), this.each(function () {
            var r, t = e(this).data("xdsoft_datetimepicker");
            if (t) {
                if ("string" === e.type(a))switch (a) {
                    case"show":
                        e(this).select().focus(), t.trigger("open.xdsoft");
                        break;
                    case"hide":
                        t.trigger("close.xdsoft");
                        break;
                    case"toggle":
                        t.trigger("toggle.xdsoft");
                        break;
                    case"destroy":
                        W(e(this));
                        break;
                    case"reset":
                        this.value = this.defaultValue, this.value && t.data("xdsoft_datetime").isValidDate(Date.parseDate(this.value, w.format)) || t.data("changed", !1), t.data("xdsoft_datetime").setCurrentTime(this.value);
                        break;
                    case"validate":
                        r = t.data("input"), r.trigger("blur.xdsoft")
                } else t.setOptions(a);
                return 0
            }
            "string" !== e.type(a) && (!w.lazyInit || w.open || w.inline ? _(e(this)) : F(e(this)))
        })
    }, e.fn.datetimepicker.defaults = t
});''