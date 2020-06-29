$(function () {//页面加载完成
    function lightCategory(){
        var  falg = true;
        var href = window.location.href;
        var n = href.substr(href.length-6,1);
        $(".nav li").each(function(){
            var val = $(this).val();
            if(val == n){
                $(this).addClass("active");
                falg =false;
            }
        });
        if(falg == true){
            $(".nav li:first-child").addClass("active");
        }

        var urlval = getQueryVariable("pagen");
        if(urlval == false){
            $(".pagenumber").eq(1).parent().addClass("active");
        }
        $(".pagenumber").each(function(){
            var val = $(this).text();
            if(urlval == val){
                $(this).parent().addClass("active");
            }
        });
    }
});

var options = {
    html : true,
}
$(".well").hover(function(){
    $(this).popover(options);
    $(this).popover('show')
},function(){
    $(this).popover('hide')
});

$(".pagination li").click(function(){
    var host = window.location.pathname;
    var pagen = $(this).text();
    if(pagen == "«"){
        var prepagen = getQueryVariable("pagen");
        if(prepagen == false){
            return;
        }
        if(prepagen == "1"){
            return;
        }
        pagen = prepagen-1;
    }
    if(pagen == "»"){
        var prepagen = getQueryVariable("pagen");
        if(prepagen == false){
            prepagen = 1;
        }
        prepagen = parseInt(prepagen);
        pagen =  prepagen+1;
    }
    var search = getQueryVariable("search");
    if(search){
        window.location.assign(host+"?pagen="+pagen+"&search="+search)
    }
    else {
        window.location.assign(host+"?pagen="+pagen);
    }
});

function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}