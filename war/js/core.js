$(document).ready(function(){
	trademarkThisBitch();
	
	$('.menu a, .submenu a').hover(function (){
        $(this).css("text-decoration", "underline");
    },function(){
        $(this).css("text-decoration", "none");
    }
);
});

function trademarkThisBitch()
{
	var page = $('.page');
	page.html(page.html().replace(/\[tm]/g, '&#8482;'));
	
	var header = $('.pageHeader');
	header.html(header.html().replace(/\[tm]/g, '&#8482;'));
	
	var headerDivider = $('.pageHeader-divider');
	headerDivider.html(headerDivider.html().replace(/\[tm]/g, '&#8482;'));
}