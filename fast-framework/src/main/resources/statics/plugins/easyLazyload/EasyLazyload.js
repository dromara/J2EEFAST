/**
 * Created by channng on 16/12/5.
 */
;(function($){

    var defaults = {
        coverColor:"#dfdfdf",
        coverDiv:"",
        showTime:300,
        offsetBottom:0,
        offsetTopm:50,
        onLoadBackEnd:function(index,dom){},
        onLoadBackStart:function(index,dom){}
    }

    //所有待load src
    var srcList = []

    var lazyLoadCoutn = 0;
    var windowHeight = $(window).height();
    var windowWidth = $(window).width();
    var lazyImgList = $("img[data-lazy-src]");
    var default_base64_src ="data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";

    //获取img
    function getImgNaturalDimensions(src, callback,lazyLoadCoutn) {
        var nWidth, nHeight

        var image = new Image()
        image.src = src;
        image.onload = function() {
            callback(image.width, image.height);
            defaults.onLoadBackStart(lazyLoadCoutn,$("img[data-lazy-src]:eq("+lazyLoadCoutn+")"));
        }
        return [nWidth, nHeight];
    }

    function showImg(lazyLoadCoutn,callback){

        var src =  lazyImgList.eq(lazyLoadCoutn).attr("data-lazy-src")
        getImgNaturalDimensions(src, function () {
            try {

            meng($("img[data-lazy-src]:eq("+lazyLoadCoutn+")") ,lazyLoadCoutn,callback)
                }catch(error) {

                }
        },lazyLoadCoutn)
    }

    function meng(jDom,lazyLoadCoutn,callback){
        if(jDom.attr("data-comp")){
            return;
        }
        jDom.css("visibility","hidden");
        jDom.attr("src",jDom.attr("data-lazy-src"))
        var w = jDom.width();
        var h = jDom.height();
        var offsetTop = jDom.offset().top;
        var offsetLeft = jDom.offset().left;

        jDom.css("visibility","visible");
        $("body").append("<div class='meng-lazy-div"+lazyLoadCoutn+"' style='background-color: "+defaults.coverColor+";position:absolute;width:"+w+"px;height:"+h+"px;top:"+offsetTop+"px;left:"+offsetLeft+"px;z-index:500'>"+defaults.coverDiv+"</div>");
        noneM(lazyLoadCoutn,callback,jDom);
        jDom.attr("data-comp","true");
    }

    function noneM(lazyLoadCoutn,callback,jDom){
        if(true){
            $(".meng-lazy-div"+lazyLoadCoutn).animate({opacity:"0"},defaults.showTime,function(){
               $(this).remove();
                defaults.onLoadBackEnd(lazyLoadCoutn,jDom)
                callback();
            });
        }
    }

    function checkOffset(){
        var scrollTop = $(document).scrollTop();
        var onlazyList = [];
        lazyImgList.each(function(index){
            var dom = $(this);
            if(!dom.attr("data-comp")){
                if(dom.offset().top-scrollTop+defaults.offsetTopm>=0&&dom.offset().top-scrollTop<(windowHeight+defaults.offsetBottom)){
                    onlazyList.push(index);
                }
            }
        })

        if(onlazyList.length!=0){
            showImg(onlazyList[0],function(){
                checkOffset();
            });
        }
    }

    function range(){
        checkOffset();

    }

    function init(setting){
        defaults = $.extend(defaults,setting);

        lazyImgList.each(function(){
            var sr = $(this).attr("data-lazy-src");
            srcList.push(sr);

            $(this).attr("src",default_base64_src);
        });
        range();
        window.onscroll=function(){
            range()
        }
    }

    window.lazyLoadInit = init;


})($)