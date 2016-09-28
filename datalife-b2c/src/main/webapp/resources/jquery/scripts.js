$(function () {
    var s = $(".overlapblackbg, .slideLeft"), n = $(".wsmenucontent"), e = function () {
        $(s).removeClass("menuclose").addClass("menuopen")
    }, l = function () {
        $(s).removeClass("menuopen").addClass("menuclose")
    };
    $("#navToggle").click(function () {
        $(n.hasClass("menuopen") ? l : e)
    }), n.click(function () {
        n.hasClass("menuopen") && $(l)
    }), $("#navToggle,.overlapblackbg").on("click", function () {
        $(".wsmenucontainer").toggleClass("mrginleft")
    }), $("nav a").on("click", function () {
        $(n.hasClass("menuopen") ? l : e)
    }), n.click(function () {
        n.hasClass("menuopen") && $(l)
    }), $(".wsmenu-list li").has(".wsmenu-submenu, .wsmenu-submenu-sub, .wsmenu-submenu-sub-sub").prepend('<span class="wsmenu-click"><i class="wsmenu-arrow fa fa-angle-down"></i></span>'), $(".wsmenu-list li").has(".megamenu").prepend('<span class="wsmenu-click"><i class="wsmenu-arrow fa fa-angle-down"></i></span>'), $(".wsmenu-mobile").click(function () {
        $(".wsmenu-list").slideToggle("slow")
    }), $(".wsmenu-click").click(function () {
        $(this).siblings(".wsmenu-submenu").slideToggle("slow"), $(this).children(".wsmenu-arrow").toggleClass("wsmenu-rotate"), $(this).siblings(".wsmenu-submenu-sub").slideToggle("slow"), $(this).siblings(".wsmenu-submenu-sub-sub").slideToggle("slow"), $(this).siblings(".megamenu").slideToggle("slow")
    })
});

/*

//////////F12 disable code////////////////////////
document.onkeypress = function (event) {
    event = (event || window.event);
    if (event.keyCode == 123) {
    //alert('No F-12');
    return false;
    }
    }
document.onmousedown = function (event) {
    event = (event || window.event);
    if (event.keyCode == 123) {
    //alert('No F-keys');
    return false;
    }
    }
document.onkeydown = function (event) {
    event = (event || window.event);
    if (event.keyCode == 123) {
    //alert('No F-keys');
    return false;
    }
    }
/////////////////////end///////////////////////


//Disable right click script
//visit http://www.rainbow.arch.scriptmania.com/scripts/
var message="Sorry, right-click has been disabled";
///////////////////////////////////
function clickIE() {if (document.all) {(message);return false;}}
function clickNS(e) {if
    (document.layers||(document.getElementById&&!document.all)) {
    if (e.which==2||e.which==3) {(message);return false;}}}
if (document.layers)
{document.captureEvents(Event.MOUSEDOWN);document.onmousedown=clickNS;}
else{document.onmouseup=clickNS;document.oncontextmenu=clickIE;}
document.oncontextmenu=new Function("return false")*/
