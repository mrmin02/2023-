
/** 헤더 hover **/
$(document).ready(function () {
    $(".header_in .menu_div").on("mouseover",function(){
        $(".header_on").fadeIn(300);
        $("div.header").css("background-color","#eee").animate({opacity:1});
    });
    $(".header_on").on("mouseleave",function(){
        $(".header_on").fadeOut(300);
        $("div.header").css("background-color","#2E332B").animate({opacity:1});
    });
});