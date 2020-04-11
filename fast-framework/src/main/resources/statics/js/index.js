/*!
 * Copyright (c) 2020-Now http://www.j2eefast.com All rights reserved.
 *
 * index 主页菜单、按钮事件
 * @author zhouzhou
 * @data 2020-02-20
 *       2020-04-10 优化国际化参数
 * @version 1.0.11
 */
//菜单添加事件
+function ($) {

    //初始化i18n插件
    $.i18n.properties({
        path: baseURL + 'i18n/',//这里表示访问路径
        name: 'i18n',//文件名开头
        language: _lang,//文件名语言 例如en_US
        cache: true,
        mode: 'map'//默认值
    });

    var Default = {
        base: 'statics/js/',
        elem: '.larry-tab-box',
        element: null
    };

    var ClassName = {
        classType: '.sidebar-menu',
        treeview: '.treeview'
    };

    var menu = function () {
        //this.init();
        this._setUpListeners();
        this._home();
        this._remember();
    };

    //首页
    menu.prototype._home = function(){
        var data = {
            href: 'main',
            icon: 'fa fa-home',
            title: $.i18n.prop('首页'),
            id: '0'
        };
        opt.navTabAdd(data);
    };

    //菜单记忆功能
    menu.prototype._remember = function(){
        var that = this;
        //用户是否勾选TAB记忆功能
        var _tab = opt.storage.get("_Tab");
        var hash = location.hash;
        if(_tab === "1" || _unlock === "1"){ //或者用户为屏保解锁还原页面
            //打开用户设置的菜单
            var menus = opt.storage.get("menu");
            if(menus && hash == '' ){
                menus = JSON.parse(menus);
                for(var i=0; i<menus.length; i++){
                    $('.sidebar-menu').children('.treeview').each(function () {
                        if($(this).find('ul').length > 0){
                            $(this).find('ul').children('li').each(function () {
                                var $a = $(this).children('a');
                                var id = $a.data('id');
                                var title = $a.children('span:first').text();
                                if(id === menus[i].id){
                                    menus[i].title = title;
                                }
                            })
                        }
                    });
                    if(menus[i].href === "sys/user/profile/info"){
                        menus[i].title = $.i18n.prop('个人中心');
                    }
                    if(menus[i].href === "sys/user/profile/password"){
                        menus[i].title = $.i18n.prop('修改密码');
                    }
                    opt.navTabAdd(menus[i]);
                }
            };
        }else{
            //没有记忆功能则清除本地缓存
            opt.storage.set("menu","");
        }

        if(hash!=''){
            var tms = hash.substring(1, hash.length).split("#");
            var url = tms[0];
            var $a = $('a[data-url="' + url + '"][data-module="'+tms[1]+'"]');
            var data;
            if($a.length >0 ){
                var href = $a.data('url');
                var id = $a.data('id');
                var module = $a.data('module');
                var icon = $a.children('i:first').data('icon');
                var title = $a.children('span:first').text();
                data = {
                    href: href,
                    icon: icon,
                    title: title,
                    module:module,
                    id: id
                }
            }else{
                if(url === "sys/user/profile/info"){
                    data = {
                        href: 'sys/user/profile/info',
                        icon: 'fa fa-address-card',
                        title: $.i18n.prop('个人中心'),
                        id: '-1',
                        module:'_sysInfo'
                    };
                }
                if(url === "sys/user/profile/password"){
                    data = {
                        href: 'sys/user/profile/password',
                        icon: 'fa fa-shield',
                        title: $.i18n.prop('修改密码'),
                        id: '-2',
                        module:'_sysInfo'
                    };
                }
            }
            //清除TBA记忆
            opt.storage.set("menu","");
            opt.navTabAdd(data);
        }
    };

    // menu.prototype.init = function(){
    //     //
    // };
    menu.prototype.setIframeUrl = function (href,module) {
        var nowUrl = window.location.href;
        var newUrl = nowUrl.substring(0, nowUrl.indexOf("#"));
        window.location.href = newUrl + "#" + href+"#"+module;
    };
    //菜单点击事件实现方法
    menu.prototype.toggle = function (link, event) {
        var $a = link.children('a');
        var _target = $a.data('target');
        var href = $a.data('url');
        var module = $a.data('module');
        if(_target === "_tab" || _target == "" || _target ==="_fullscreen"){ //TAB打开
            var id = $a.data('id');
            var icon = $a.children('i:first').data('icon');
            var title = $a.children('span:first').text();
            var data = {
                href: href,
                icon: icon,
                title: title,
                module:module,
                id: id
            }
            opt.navTabAdd(data);
            if(_target == "_fullscreen"){ //全屏
                var target = $('iframe[data-id="' + id + '"]');
                target.fullScreen(true);
            }
        }

        if(_target === "_blank"){ //新窗口打开
            window.open(href);
        }

        if(_target === "_alert"){ //新窗口打开
            var icon = $a.children('i:first').data('icon');
            var title = $a.children('span:first').text();
            opt.layer.open({
                type: 2,
                maxmin: true,
                shadeClose: true,
                title: '<i class="'+icon+'"></i>&nbsp;'+title,
                area: [($(window).width() - 100)+'px',
                    ($(window).height() - 100) + 'px'],
                content:href
            });
        }
    };
    //监听TAB切换事件
    menu.prototype._tabSwitch = function(){
        var that = this;
        Default.element.on("tab(main-tab)",function (data) {
            //跳转指定TBA
            opt.scrollToTab(this);
            var target = $('iframe[data-id="' + $(this).attr("lay-id") + '"]');
            var url = target.attr('src');
            if(url != "main" && ($(this).attr("lay-id")+"").length != 16){
                that.setIframeUrl(url,target.attr('data-module'));
            }
            /**********************左侧菜单同步展开 顶部变动 ******************************/

            var _id = $(this).attr('lay-id');
            var _module =$(this).children('em').data('module');
            if(_id !== '0' && _module !== '_sysInfo'){
                $("#leftMenu > ul").addClass('hide');
                $('#topMenu li').each(function () {
                    if($(this).children('a').data('code') === _module){
                        $(this).addClass('active');
                    }else{
                        $(this).removeClass('active');
                    }
                });
                $('#leftMenu-' + _module).removeClass('hide');
                $('#leftMenu-' + _module).children('.treeview').each(function (i) {
                    $(this).removeClass("menu-open");
                    if($(this).find('ul').length > 0){
                        $(this).find('ul').css("display","none");
                        $(this).find('ul').children('li').each(function () {
                            var $a = $(this).children('a');
                            if( _id != 0 && $a.data('id') == _id){
                                $(this).parent('.treeview-menu').parent(".treeview").addClass("menu-open");
                                $(this).parent('.treeview-menu').css("display","block");
                                $(this).addClass("active");
                            }else{
                                $(this).removeClass("active");
                            }
                        });
                    }
                });
            }
            /*********************TAB刷新功能***************************/
            if($(this).attr("lay-id") === opt.variable._tabIndex +""){
                opt.variable._tabIndex = $(this).attr("lay-id");
            }else{
                //切换
                if(opt.storage.get("_tabRef") === "1"){
                    opt.block("数据加载中，请稍后...",'#content-main')
                    target.attr('src', url).on("load",function () {
                        setTimeout(function(){
                            opt.unblock('#content-main')
                        }, 50);
                    });
                }
                opt.variable._tabIndex = $(this).attr("lay-id");
            }
            /************************************************/
        });
    }
    //设置菜单监听事件
    menu.prototype._setUpListeners = function () {
        var that = this;
        $(ClassName.classType).children(ClassName.treeview).each(function (i) {
            if($(this).find('ul').length > 0){
                $(this).find('ul').children('li').each(function () {
                    $(this).on('click',function (event) {
                        that.toggle($(this), event);
                    });
                })
            }
        })
        that._tabSwitch();
    };

    function Plugin(option) {
        if (window.layui !== undefined) {
            layui.config({
                base: Default.base,
            });
            layui.use(['navtab'], function(){
                Default.element = layui.element;
                opt.variable.navtab = layui.navtab({
                    elem: Default.elem,
                    closed:false
                });
                new menu();
            });
        }
    };

    $.fn.menu             = Plugin;
    $.fn.menu.Constructor = menu;

    $(function () {
        Plugin();
    })
}(jQuery);
//右键菜单
$(function () {

    // 右键菜单实现
    $.contextMenu({
        selector: ".layui-tab-card>.layui-tab-title li",
        trigger: 'right',
        autoHide: true,
        items: {
            "refresh": {
                name: $.i18n.prop("刷新当前"),
                icon: "fa-refresh",
                callback: function(key, opt) {
                    if (!$(this).hasClass('layui-this')) {
                        window.opt.navToTab($(this).attr("lay-id"));
                    }
                    var target = $('iframe[data-id="' + $(this).attr("lay-id") + '"]');
                    var url = target.attr('src');
                    window.opt.block('数据加载中，请稍后...','#content-main');
                    target.attr('src', url).on("load",function () {
                        setTimeout(function(){
                            window.opt.unblock('#content-main')
                        }, 50);
                    });
                }
            },
            "close_current": {
                name: $.i18n.prop("关闭当前"),
                icon: "fa-close",
                callback: function(key, opt) {
                    if($(this).attr("lay-id") != 0){
                        opt.$trigger.find('i').trigger("click");
                    }
                }
            },
            "close_other": {
                name: $.i18n.prop("关闭其他"),
                icon: "fa-window-close-o",
                callback: function(key, opt) {
                    if (!$(this).hasClass('layui-this')) {
                        window.opt.navToTab($(this).attr("lay-id"));
                    }
                    $(".layui-tab-title li").each(function(){
                        if($(this).attr("lay-id") != 0  && !$(this).hasClass("layui-this")){
                            $(this).children('i.layui-tab-close[data-id="' + $(this).attr("lay-id") + '"]').trigger("click");
                            return;
                        }
                    })
                    window.opt.scrollToTab(this);
                }
            },
            "close_all": {
                name: $.i18n.prop("关闭全部"),
                icon: "fa-window-close",
                callback: function(key, opt) {
                    if($(".layui-tab-title li").length > 1){
                        $(".layui-tab-title li").each(function(){
                            if($(this).attr("lay-id") != 0){
                                $(this).children('i.layui-tab-close[data-id="' + $(this).attr("lay-id") + '"]').trigger("click");
                            }
                        })
                    }
                }
            },
            "step1": "---------",
            "close_left": {
                name: $.i18n.prop("关闭左侧"),
                icon: "fa-reply",
                callback: function(key, opt) {
                    if (!$(this).hasClass('layui-this')) {
                        window.opt.navToTab($(this).attr("lay-id"));
                    }
                    this.prevAll('li').not(":last").each(function() {
                        $(this).children('i.layui-tab-close[data-id="' + $(this).attr("lay-id") + '"]').trigger("click");
                    });
                    window.opt.scrollToTab(this);
                }
            },
            "close_right": {
                name: $.i18n.prop("关闭右侧"),
                icon: "fa-share",
                callback: function(key, opt) {
                    if (!$(this).hasClass('layui-this')) {
                        window.opt.navToTab($(this).attr("lay-id"));
                    }
                    this.nextAll('li').each(function() {
                        $(this).children('i.layui-tab-close[data-id="' + $(this).attr("lay-id") + '"]').trigger("click");
                    });
                    window.opt.scrollToTab(this);
                }
            },
            "step": "---------",
            "full": {
                name: $.i18n.prop("全屏显示"),
                icon: "fa-arrows-alt",
                callback: function(key, opt) {
                    if (!$(this).hasClass('layui-this')) {
                        window.opt.navToTab($(this).attr("lay-id"));
                    }
                    var target = $('iframe[data-id="' + $(this).attr("lay-id") + '"]');
                    target.fullScreen(true);
                }
            },
            "open": {
                name: $.i18n.prop("新窗口打开"),
                icon: "fa-link",
                callback: function(key, opt) {
                    var target = $('iframe[data-id="' + $(this).attr("lay-id") + '"]');
                    window.open(target.attr('src'));
                }
            },
        }
    });
})

//设置监听事件
$(function () {

    /*$(window).on("load",function () {
        setTimeout(function(){
            opt.unblock(window)
        }, 50);
    });*/

    //校验修改密码
    $("#form-user-updatePass").validate({
        rules:{
            newPassword:{
                required:true,
                minlength: 6,
                maxlength: 15
            },
        },
        focusCleanup: true
    });

    //个人中心
    $('[click-id="userInfo"]').each(function () {
        $(this).on('click', function () {
            var data = {
                href: 'sys/user/profile/info',
                icon: 'fa fa-address-card',
                title: $.i18n.prop('个人中心'),
                id: '-1',
                module:'_sysInfo'
            };
            opt.navTabAdd(data);
            return;
        });
    });

    //主题
    $('#switchSkin').on('click', function () {
        opt.layer.open({
            type : 2,
            shadeClose : true,
            title : $.i18n.prop("切换主题"),
            area : ["530px", "386px"],
            content : ["sys/switchSkin", 'no']
        })
    });

    //修改密码
    $('#updatePassword').on('click', function () {
        opt.layer.open({
            type: 1,
            // skin: 'layui-layer-molv',
            title: $.i18n.prop('修改密码'),
            area: ['550px', '280px'],
            // 弹层外区域关闭
            shadeClose: true,
            content: jQuery("#passwordLayer"),
            btn: [$.i18n.prop('确定'), $.i18n.prop('取消')],
            btn1: function (index) {
                if($("#form-user-updatePass").validate().form()){
                    var data =$("#form-user-updatePass").serializeArray();
                    $.ajax({
                        type: "POST",
                        url: "sys/user/updatePass",
                        data: data,
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 0) {
                                opt.layer.close(index);
                                opt.layer.alert('修改成功', function (index) {
                                    location.reload();
                                });
                            } else {
                                opt.error(result.msg);
                            }
                        }
                    });
                }
            }
        })
    });

    //退出
    $('[click-id="logout"]').each(function () {
        $(this).on('click', function () {
            opt.modal.confirm($.i18n.prop('确定要退出系统吗?'),function () {
                window.location.href= "logout";
                return;
            })
        });
    });

    //左滑动
    $('#scrollTabLeft').on('click', opt.scrollTabLeft);

    //右滑动
    $('#scrollTabRight').on('click', opt.scrollTabRight);

    // 全屏显示
    $('#fullScreen').on('click', function () {
        $(document).toggleFullScreen();
    });

    // 系统锁屏
    $('#lockOs').on('click', function () {
        window.location.href  = baseURL + "Account/Lock?" + Math.random();
        return;
    });

    // 左侧收缩栏
    $('[data-toggle="control-sidebar"]').controlSidebar();
    $('[data-toggle="push-menu"]').pushMenu();
    opt.variable.pushMenu = $('[data-toggle="push-menu"]').data('lte.pushmenu');
    var $controlSidebar = $('[data-toggle="control-sidebar"]').data('lte.controlsidebar');
    var $layout = $('body').data('lte.layout');
    $(window).on('load', function() {
        opt.variable.pushMenu = $('[data-toggle="push-menu"]').data('lte.pushmenu')
        $controlSidebar = $('[data-toggle="control-sidebar"]').data('lte.controlsidebar')
        $layout = $('body').data('lte.layout');
    })

    var temp = opt.storage.get("pushMenu");
    if(temp){
        if(temp === "1"){
            opt.sidebarCollapse();
        }
    }

    window.onhashchange = function() {
        var hash = location.hash;
        var url = hash.substring(1, hash.length);
        $('em[data-url$="' + url + '"]').click();
    };


    //////////////////////////////////////////////////////////////////

});
+function ($) {
    'use strict';

    var DataKey = 'lte.layout';

    var Default = {
        slimscroll : true,
        resetHeight: true
    };

    var Selector = {
        wrapper       : '.wrapper',
        contentWrapper: '.content-wrapper',
        layuibody     : '.content-wrapper .layui-body',
        layoutBoxed   : '.layout-boxed',
        mainFooter    : '.main-footer',
        mainHeader    : '.main-header',
        sidebar       : '.sidebar',
        controlSidebar: '.control-sidebar',
        fixed         : '.fixed',
        sidebarMenu   : '.sidebar-menu',
        logo          : '.main-header .logo'
    };

    var ClassName = {
        fixed         : 'fixed',
        holdTransition: 'hold-transition'
    };

    var Layout = function (options) {
        this.options      = options;
        this.bindedResize = false;
        this.activate();
    };

    Layout.prototype.activate = function () {
        this.fix();
        this.fixSidebar();

        $('body').removeClass(ClassName.holdTransition);

        if (this.options.resetHeight) {
            $('body, html, ' + Selector.wrapper).css({
                'height'    : '100%',
                'min-height': '100%'
            });
        }

        if (!this.bindedResize) {
            $(window).resize(function () {
                this.fix();
                this.fixSidebar();

                $(Selector.logo + ', ' + Selector.sidebar).one('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function () {
                    this.fix();
                    this.fixSidebar();
                }.bind(this));
            }.bind(this));

            this.bindedResize = true;
        }

        $(Selector.sidebarMenu).on('expanded.tree', function () {
            this.fix();
            this.fixSidebar();
        }.bind(this));

        $(Selector.sidebarMenu).on('collapsed.tree', function () {
            this.fix();
            this.fixSidebar();
        }.bind(this));
    };

    Layout.prototype.fix = function () {
        // Remove overflow from .wrapper if layout-boxed exists
        $(Selector.layoutBoxed + ' > ' + Selector.wrapper).css('overflow', 'hidden');

        // Get window height and the wrapper height
        var footerHeight = $(Selector.mainFooter).outerHeight() || 0;
        var headerHeight  = $(Selector.mainHeader).outerHeight() || 0;
        var neg           = headerHeight + footerHeight;
        var windowHeight  = $(window).height();
        var sidebarHeight = $(Selector.sidebar).height() || 0;
        if ($('body').hasClass(ClassName.fixed)) {
            $(Selector.contentWrapper).css('min-height', windowHeight -  footerHeight);
            $(Selector.layuibody).css('min-height', windowHeight - headerHeight -footerHeight);
            $("#content-main").css('min-height', windowHeight - headerHeight -footerHeight - 40);
            $("#content-main .layui-tab-item").css('height', windowHeight - headerHeight -footerHeight - 40);
        } else {
            var postSetHeight;
            if (windowHeight >= sidebarHeight + headerHeight) {
                postSetHeight = windowHeight - neg;
                $(Selector.contentWrapper).css('min-height', postSetHeight.toFixed(2));
                var $content = $('#larry-tab .layui-tab-content');
                $content.find('iframe').each(function () {
                    $(this).height(postSetHeight-45);
                });

            } else {
                $(Selector.contentWrapper).css('min-height', sidebarHeight.toFixed(2));
                postSetHeight = sidebarHeight;
            }

            // Fix for the control sidebar height
            var $controlSidebar = $(Selector.controlSidebar);
            if (typeof $controlSidebar !== 'undefined') {
                if ($controlSidebar.height() > postSetHeight){
                    $(Selector.contentWrapper).css('min-height', $controlSidebar.height().toFixed(2));
                    var $content = $('#larry-tab .layui-tab-content');
                    $content.find('iframe').each(function () {
                        $(this).height($controlSidebar.height()-45);
                    });
                    //console.log($controlSidebar.height());
                }
            }
        }
    };

    Layout.prototype.fixSidebar = function () {
        // Make sure the body tag has the .fixed class
        if (!$('body').hasClass(ClassName.fixed)) {
            if (typeof $.fn.slimScroll !== 'undefined') {
                $(Selector.sidebar).slimScroll({ destroy: true }).height('auto');
            }
            return;
        }

        // Enable slimscroll for fixed layout
        if (this.options.slimscroll) {
            if (typeof $.fn.slimScroll !== 'undefined') {
                // Destroy if it exists
                // $(Selector.sidebar).slimScroll({ destroy: true }).height('auto')

                // Add slimscroll
                $(Selector.sidebar).slimScroll({
                    height: ($(window).height() - $(Selector.mainHeader).height()) + 'px',
                    opacity: .4, //滚动条透明度

                });
            }
        }
    };

    // Plugin Definition
    // =================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this);
            var data  = $this.data(DataKey);

            if (!data) {
                var options = $.extend({}, Default, $this.data(), typeof option === 'object' && option);
                $this.data(DataKey, (data = new Layout(options)));
            }

            if (typeof option === 'string') {
                if (typeof data[option] === 'undefined') {
                    throw new Error('No method named ' + option);
                }
                data[option]();
            }
        });
    }

    var old = $.fn.layout;

    $.fn.layout            = Plugin;
    $.fn.layout.Constuctor = Layout;

    // No conflict mode
    // ================
    $.fn.layout.noConflict = function () {
        $.fn.layout = old;
        return this;
    };

    // Layout DATA-API
    // ===============
    $(window).on('load', function () {
        Plugin.call($('body'));
    });
}(jQuery);
/* ControlSidebar()
 * ===============
 * Toggles the state of the control sidebar
 *
 * @Usage: $('#control-sidebar-trigger').controlSidebar(options)
 *         or add [data-toggle="control-sidebar"] to the trigger
 *         Pass any option as data-option="value"
 */
+function ($) {
    'use strict';

    var DataKey = 'lte.controlsidebar';

    var Default = {
        slide: true
    };

    var Selector = {
        sidebar: '.control-sidebar',
        data   : '[data-toggle="control-sidebar"]',
        open   : '.control-sidebar-open',
        bg     : '.control-sidebar-bg',
        wrapper: '.wrapper',
        content: '.content-wrapper',
        boxed  : '.layout-boxed'
    };

    var ClassName = {
        open : 'control-sidebar-open',
        fixed: 'fixed'
    };

    var Event = {
        collapsed: 'collapsed.controlsidebar',
        expanded : 'expanded.controlsidebar'
    };

    // ControlSidebar Class Definition
    // ===============================
    var ControlSidebar = function (element, options) {
        this.element         = element;
        this.options         = options;
        this.hasBindedResize = false;

        this.init();
    };

    ControlSidebar.prototype.init = function () {
        // Add click listener if the element hasn't been
        // initialized using the data API
        if (!$(this.element).is(Selector.data)) {
            $(this).on('click', this.toggle);
        }

        this.fix();
        $(window).resize(function () {
            this.fix();
        }.bind(this));
    };

    ControlSidebar.prototype.toggle = function (event) {
        if (event) event.preventDefault();

        this.fix();

        if (!$(Selector.sidebar).is(Selector.open) && !$('body').is(Selector.open)) {
            this.expand();
        } else {
            this.collapse();
        }
    };

    ControlSidebar.prototype.expand = function () {
        if (!this.options.slide) {
            $('body').addClass(ClassName.open);
        } else {
            $(Selector.sidebar).addClass(ClassName.open);
        }

        $(this.element).trigger($.Event(Event.expanded));
    };

    ControlSidebar.prototype.collapse = function () {
        $('body, ' + Selector.sidebar).removeClass(ClassName.open);
        $(this.element).trigger($.Event(Event.collapsed));
    };

    ControlSidebar.prototype.fix = function () {
        if ($('body').is(Selector.boxed)) {
            this._fixForBoxed($(Selector.bg));
        }
    };

    // Private

    ControlSidebar.prototype._fixForBoxed = function (bg) {
        bg.css({
            position: 'absolute',
            height  : $(Selector.wrapper).height()
        });
    };

    // Plugin Definition
    // =================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this);
            var data  = $this.data(DataKey);

            if (!data) {
                var options = $.extend({}, Default, $this.data(), typeof option == 'object' && option);
                $this.data(DataKey, (data = new ControlSidebar($this, options)));
            }

            if (typeof option == 'string') data.toggle();
        });
    }

    var old = $.fn.controlSidebar;

    $.fn.controlSidebar             = Plugin;
    $.fn.controlSidebar.Constructor = ControlSidebar;

    // No Conflict Mode
    // ================
    $.fn.controlSidebar.noConflict = function () {
        $.fn.controlSidebar = old;
        return this;
    };

    // ControlSidebar Data API
    // =======================
    $(document).on('click', Selector.data, function (event) {
        if (event) event.preventDefault();
        Plugin.call($(this), 'toggle');
    });

}(jQuery);

/* PushMenu()
 * ==========
 * Adds the push menu functionality to the sidebar.
 *
 * @usage: $('.btn').pushMenu(options)
 *          or add [data-toggle="push-menu"] to any button
 *          Pass any option as data-option="value"
 */
+function ($) {
    'use strict';

    var DataKey = 'lte.pushmenu';

    var Default = {
        collapseScreenSize   : 767,
        expandOnHover        : false,
        expandTransitionDelay: 200
    };

    var Selector = {
        collapsed     : '.sidebar-collapse',
        open          : '.sidebar-open',
        mainSidebar   : '.main-sidebar',
        contentWrapper: '.content-wrapper',
        searchInput   : '.sidebar-form .form-control',
        button        : '[data-toggle="push-menu"]',
        mini          : '.sidebar-mini',
        expanded      : '.sidebar-expanded-on-hover',
        layoutFixed   : '.fixed'
    };

    var ClassName = {
        collapsed    : 'sidebar-collapse',
        open         : 'sidebar-open',
        mini         : 'sidebar-mini',
        expanded     : 'sidebar-expanded-on-hover',
        expandFeature: 'sidebar-mini-expand-feature',
        layoutFixed  : 'fixed'
    };

    var Event = {
        expanded : 'expanded.pushMenu',
        collapsed: 'collapsed.pushMenu'
    };

    // PushMenu Class Definition
    // =========================
    var PushMenu = function (options) {
        this.options = options;
        this.init();
    };

    PushMenu.prototype.init = function () {
        /*if (this.options.expandOnHover
          || ($('body').is(Selector.mini + Selector.layoutFixed))) {
          this.expandOnHover();
          $('body').addClass(ClassName.expandFeature);
        }*/

        $(Selector.contentWrapper).click(function () {
            // Enable hide menu when clicking on the content-wrapper on small screens
            if ($(window).width() <= this.options.collapseScreenSize && $('body').hasClass(ClassName.open)) {
                this.close();
            }
        }.bind(this));

        // __Fix for android devices
        $(Selector.searchInput).click(function (e) {
            e.stopPropagation();
        });
    };

    PushMenu.prototype.toggle = function () {
        var windowWidth = $(window).width();
        var isOpen      = !$('body').hasClass(ClassName.collapsed);

        if (windowWidth <= this.options.collapseScreenSize) {
            isOpen = $('body').hasClass(ClassName.open);
        }

        if (!isOpen) {
            this.open();
        } else {
            this.close();
        }
    };

    PushMenu.prototype.open = function () {
        var windowWidth = $(window).width();

        if (windowWidth > this.options.collapseScreenSize) {
            $('body').removeClass(ClassName.collapsed)
                .trigger($.Event(Event.expanded));
        }
        else {
            $('body').addClass(ClassName.open)
                .trigger($.Event(Event.expanded));
        }
    };

    PushMenu.prototype.close = function () {
        var windowWidth = $(window).width();
        if (windowWidth > this.options.collapseScreenSize) {
            $('body').addClass(ClassName.collapsed)
                .trigger($.Event(Event.collapsed));
        } else {
            $('body').removeClass(ClassName.open + ' ' + ClassName.collapsed)
                .trigger($.Event(Event.collapsed));
        }
    };

    PushMenu.prototype.expandOnHover = function () {
        $(Selector.mainSidebar).hover(function () {
            if ($('body').is(Selector.mini + Selector.collapsed)
                && $(window).width() > this.options.collapseScreenSize) {
                this.expand();
            }
        }.bind(this), function () {
            if ($('body').is(Selector.expanded)) {
                this.collapse();
            }
        }.bind(this));
    };

    PushMenu.prototype.expand = function () {
        setTimeout(function () {
            $('body').removeClass(ClassName.collapsed)
                .addClass(ClassName.expanded);
        }, this.options.expandTransitionDelay);
    };

    PushMenu.prototype.collapse = function () {
        setTimeout(function () {
            $('body').removeClass(ClassName.expanded)
                .addClass(ClassName.collapsed);
        }, this.options.expandTransitionDelay);
    };

    // PushMenu Plugin Definition
    // ==========================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this);
            var data  = $this.data(DataKey);

            if (!data) {
                var options = $.extend({}, Default, $this.data(), typeof option == 'object' && option);
                $this.data(DataKey, (data = new PushMenu(options)));
            }

            if (option === 'toggle') data.toggle();
        });
    }

    var old = $.fn.pushMenu;

    $.fn.pushMenu             = Plugin;
    $.fn.pushMenu.Constructor = PushMenu;

    // No Conflict Mode
    // ================
    $.fn.pushMenu.noConflict = function () {
        $.fn.pushMenu = old;
        return this;
    };

    // Data API
    // ========
    $(document).on('click', Selector.button, function (e) {
        e.preventDefault();
        Plugin.call($(this), 'toggle');
    });
    $(window).on('load', function () {
        Plugin.call($(Selector.button));
    });
}(jQuery);
+function ($) {
    'use strict';

    var DataKey = 'lte.tree';

    var Default = {
        animationSpeed: 500,
        accordion     : true,
        followLink    : false,
        trigger       : '.treeview a'
    };

    var Selector = {
        tree        : '.tree',
        treeview    : '.treeview',
        treeviewMenu: '.treeview-menu',
        open        : '.menu-open, .active',
        li          : 'li',
        data        : '[data-widget="tree"]',
        active      : '.active'
    };

    var ClassName = {
        open: 'menu-open',
        tree: 'tree'
    };

    var Event = {
        collapsed: 'collapsed.tree',
        expanded : 'expanded.tree'
    };

    // Tree Class Definition
    // =====================
    var Tree = function (element, options) {
        this.element = element;
        this.options = options;

        $(this.element).addClass(ClassName.tree);

        $(Selector.treeview + Selector.active, this.element).addClass(ClassName.open);

        this._setUpListeners();
    };

    Tree.prototype.toggle = function (link, event) {
        var treeviewMenu = link.next(Selector.treeviewMenu);
        var parentLi     = link.parent();
        var isOpen       = parentLi.hasClass(ClassName.open);

        if (!parentLi.is(Selector.treeview)) {
            return;
        }

        if (!this.options.followLink || link.attr('href') === '#') {
            event.preventDefault();
        }

        if (isOpen) {
            this.collapse(treeviewMenu, parentLi);
        } else {
            this.expand(treeviewMenu, parentLi);
        }
    };

    Tree.prototype.expand = function (tree, parent) {
        var expandedEvent = $.Event(Event.expanded);

        if (this.options.accordion) {
            var openMenuLi = parent.siblings(Selector.open);
            var openTree   = openMenuLi.children(Selector.treeviewMenu);
            this.collapse(openTree, openMenuLi);
        }

        parent.addClass(ClassName.open);
        tree.slideDown(this.options.animationSpeed, function () {
            $(this.element).trigger(expandedEvent);
        }.bind(this));
    };

    Tree.prototype.collapse = function (tree, parentLi) {
        var collapsedEvent = $.Event(Event.collapsed);

        //tree.find(Selector.open).removeClass(ClassName.open);
        parentLi.removeClass(ClassName.open);
        tree.slideUp(this.options.animationSpeed, function () {
            //tree.find(Selector.open + ' > ' + Selector.treeview).slideUp();
            $(this.element).trigger(collapsedEvent);
        }.bind(this));
    };

    // Private

    Tree.prototype._setUpListeners = function () {
        var that = this;

        $(this.element).on('click', this.options.trigger, function (event) {
            that.toggle($(this), event);
        });
    };

    // Plugin Definition
    // =================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this);
            var data  = $this.data(DataKey);

            if (!data) {
                var options = $.extend({}, Default, $this.data(), typeof option == 'object' && option);
                $this.data(DataKey, new Tree($this, options));
            }
        });
    }

    var old = $.fn.tree;

    $.fn.tree             = Plugin;
    $.fn.tree.Constructor = Tree;

    // No Conflict Mode
    // ================
    $.fn.tree.noConflict = function () {
        $.fn.tree = old;
        return this;
    };

    // Tree Data API
    // =============
    $(function () {
        $(Selector.data).each(function () {
            Plugin.call($(this));
        });
    })

}(jQuery);