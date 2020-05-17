/*!
 * Copyright (c) 2020-Now http://www.j2eefast.com All rights reserved.
 *
 * navtab 定义Tab
 * @author ZhouHuan
 * @data 2020-02-20
 * @version 1.0.10
 */
layui.define(['element'], function(exports){
		var  element = layui.element,
        $ = layui.jquery,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		module_name = 'navtab',
		globalTabIdIndex = 0,
		LarryTab = function(){
	          this.config ={
	          	  elem: undefined,
				  closed: true
	          };
		};
    var ELEM = {};
    /**
     * [参数设置 options]
     */
    LarryTab.prototype.set = function(options){
          var _this = this;
          $.extend(true, _this.config, options);
          return _this;
    };
    /**
     * [init 对象初始化]
     * @return {[type]} [返回对象初始化结果]
     */
    LarryTab.prototype.init  = function(){
         var _this = this;
         var _config = _this.config;
         if(typeof(_config.elem) !== 'string' && typeof(_config.elem) !== 'object') {
		       layer.alert('Tab选项卡错误提示: elem参数未定义或设置出错，具体设置格式请参考文档API.');
	     }
	     var $container;
	     if(typeof(_config.elem) === 'string') {
		     $container = $('' + _config.elem + '');
		     //console.log($container);
	     }
	     if(typeof(_config.elem) === 'object') {
		     $container = _config.elem;
	     }
	     if($container.length === 0) {
		     layer.alert('Tab选项卡错误提示:找不到elem参数配置的容器，请检查.');
	     }
	     var filter = $container.attr('lay-filter');
	     if(filter === undefined || filter === '') {
		      layer.alert('Tab选项卡错误提示:请为elem容器设置一个lay-filter过滤器');
	     }
	     _config.elem = $container;
	     ELEM.titleBox = $container.children('ul.layui-tab-title');
	     ELEM.contentBox = $container.children('div.layui-tab-content');
	     ELEM.tabFilter = filter;
	     return _this;
    };
    /**
     * [exists 在layui-tab中检查对应layui-tab-title是否存在，如果存在则返回索引值，不存在返回-1]
     * @param  {[type]} title [description]
     * @return {[type]}       [description]
     */
    LarryTab.prototype.exists = function(id,url,module){
        var _this = ELEM.titleBox === undefined ? this.init() : this,
		    tabIndex = -1;
		ELEM.titleBox.find('li').each(function(i, e) {
		    var $em = $(this).children('em');
		    if(($(this).attr("lay-id") == id || $em.attr("data-url") == url) && module == $em.attr("data-module")) {
			      tabIndex = $(this).attr("lay-id");
		    };
	    });
	    return tabIndex;
    };


	/**
     * [tabAdd 增加选项卡，如果已存在则增加this样式]
     * @param  {[type]} data [description]
     * @return {[type]}      [description]
     */
    LarryTab.prototype.tabAdd = function(data){
        var _this = this;
	    var tabIndex = _this.exists(data.id,data.href,data.module);
	    // 若不存在
	    if(tabIndex === -1){
			opt.block('数据加载中，请稍后...','#content-main');
	    	var content = '<iframe width="100%" height="100%" src="' + data.href + '" data-id="' + data.id + '" data-module="'+data.module+'" class="larry-iframe"></iframe>';
		    var title = '';
		    // 若icon有定义
		    if(typeof(data.icon) !== "undefined"){
                // if(data.icon.indexOf('fa') != -1) {
				title += '<i class="' + data.icon + '"></i>';
			    // } else {
			    // 	title += '<i class="layui-icon ">' + data.icon + '</i>';
			    // }
		    }

		    if(typeof(data.panel) !== "undefined"){
				//title += '<em data-url="'+data.href+'" panel-id="'+data.panel+'">&nbsp;' + (data.title.length > 12?(data.title.substr(0, 12) + "..."):data.title) + '</em>';
				title += '<em data-url="'+data.href+'" panel-id="'+data.panel+'"';
			}else{
				//title += '<em data-url="'+data.href+'">&nbsp;' + (data.title.length > 12?(data.title.substr(0, 12) + "..."):data.title) + '</em>';
				title += '<em data-url="'+data.href+'"';
			}

			if(typeof(data.module) !== "undefined"){
				title += ' data-module="'+data.module+'"';
			}

			title += 'title="'+data.title+'">&nbsp;' + (data.title.length > 12?(data.title.substr(0, 12) + "..."):data.title) + '</em>';


		    //_this.config.closed &&
		    if(data.id !== '0') {
			    //title += '<i class="layui-icon layui-unselect layui-tab-close" data-id="' + data.id + '">&#x1006;</i>';
				title += '<i class="fa fa-times-circle-o layui-tab-close" data-id="' + data.id + '"></i>';
		    }


		    //添加tab
		    element.tabAdd(ELEM.tabFilter, {
			    title: title,
			    content: content,
			    id: data.id
		    });

			opt.variable._tabIndex = data.id;

		    // //iframe 自适应
		    // ELEM.contentBox.find('iframe[data-id=' + globalTabIdIndex + ']').each(function() {
		    // 	$(this).height(ELEM.contentBox.height());
		    // });
			//
			//
		    //适应屏幕高度
		    //var $content = $('#larry-tab .layui-tab-content');
	        // var length = $(window).height() - 195;
	        // $content.height(length);
	        //$content.find('iframe').each(function () {
			$("#content-main .layui-tab-item").css('height', $(window).height()-$('.main-footer').outerHeight()- $('.main-header').outerHeight() - 40);
	        //});

	        //添加TBA关闭事件
			ELEM.titleBox.find('li').children('i.layui-tab-close[data-id="' + data.id + '"]').on('click', function() {

				var menus =  opt.storage.get("menu");
				if(menus){
					menus = JSON.parse(menus);
					for(var i=0; i< menus.length; i++){
						if(menus[i].id == $(this).attr("data-id")) {
							menus.splice(i, 1);
						}
					}
					opt.storage.set("menu",JSON.stringify(menus));
				}
				// for(var i=0;typeof (myMenu) !== 'undefined' && i< myMenu.length; i++){
				// 	if(myMenu[i].id == $(this).attr("data-id")) {
				// 		myMenu.splice(i, 1);
				// 	}
				// }

				//store("menu",JSON.stringify(myMenu));

				//删除的窗口是否有父窗口有父窗口跳转父窗口
				var $f = false, $index;
				if(ELEM.titleBox.find('li[lay-id="'+$(this).attr("data-id")+'"]').children('em').attr("panel-id") != "undefined"){
					$f = true;
					$index = ELEM.titleBox.find('li[lay-id="'+$(this).attr("data-id")+'"]').children('em').attr("panel-id");
				}

				var $t = ELEM.titleBox.find('li[lay-id="'+$(this).attr("data-id")+'"]').prev();

				element.tabDelete(ELEM.tabFilter, $(this).attr("data-id")).init();

				//解决TBA关闭 失去焦点导致页面空白问题================
				var flag = true, $j = true;

				ELEM.titleBox.find('li').each(function(){
					if($(this).hasClass('layui-this')){
						flag = false;
					}
					if($f){
						if($(this).attr("lay-id") == $index){
							$j = false;
						}
					}
				});

				if(!$j){ //关闭的是有父窗口 优先跳转
					element.tabChange(ELEM.tabFilter, $index);
					try{
						var $win = $('iframe[data-id="' + $index + '"]')[0].contentWindow;
						if($win.opt.table.options.type == opt.variable.table_type.bootstrapTable){
							$win.$.table.refresh();
						}else if ($win.opt.table.options.type == opt.variable.table_type.bootstrapTreeTable) {
							$win.$.treeTable.refresh();
						}
					}catch (e) {
					}
				}else{
					if(flag){
						element.tabChange(ELEM.tabFilter, $t.attr("lay-id"));
					}
				}
				////////////////////////=========================================
			});

			//双击 关闭选项卡 \ 全屏
			ELEM.titleBox.find('li[lay-id="'+data.id+'"]').on('dblclick',function(){
				// var target = $('iframe[data-id="' + $(this).attr("lay-id") + '"]');
				// target.fullScreen(true);
				$(this).children('i.layui-tab-close[data-id="' + $(this).attr("lay-id") + '"]').trigger("click");
			});

			// ELEM.titleBox.find('li[lay-id=' + data.id + ']').on('click', function() {
			// 	var _id = $(this).attr('lay-id');
			// 	$('.sidebar-menu').children('.treeview').each(function (i) {
			// 		$(this).removeClass("menu-open");
			// 		if($(this).find('ul').length > 0){
			// 			$(this).find('ul').css("display","none");
			// 			$(this).find('ul').children('li').each(function () {
			// 				var $a = $(this).children('a');
			// 				if($a.data('id') == _id){
			// 					$(this).parent('.treeview-menu').parent(".treeview").addClass("menu-open");
			// 					$(this).parent('.treeview-menu').css("display","block");
			// 					$(this).addClass("active");
			// 				}else{
			// 					$(this).removeClass("active");
			// 				}
			// 			});
			// 		}
			// 	});
			// });

		    //console.log("iframe 自适应"); //_this.config.closed
		    /*
		    if(_this.config.closed) {
			//监听关闭事件
		    	console.log("监听关闭事件");
			    ELEM.titleBox.find('li').children('i.layui-tab-close[data-id=' + globalTabIdIndex + ']').on('click', function() {
			    	console.log("关闭事件发生:"+$(this).parent('li').index());
			    	element.tabDelete(ELEM.tabFilter, $(this).parent('li').index()).init();
			    });
		    };
		    */
			/***/
			// if($(".layui-tab-title li[lay-id='"+data.id+"']").unbind("mousedown").bind("mousedown", function (event) {
			// 	if (event.which == 1) {
			// 		alert("click left！");
			// 	}else if(event.which == 2){
			// 		alert("click roll！");
			// 	} else if(event.which == 3){
			// 		alert("click right！");
			// 	}}));
			$("iframe[data-id='"+data.id+"']").on("load",function () {
				setTimeout(
					function () {
						opt.unblock('#content-main');
					}
					, 50);
			});

			//切换到当前打开的选项卡
			element.tabChange(ELEM.tabFilter, data.id);

			if((typeof(data.panel) == "undefined") && data.id !== '0'){
				//保存用户操作菜单
				// myMenu.push(data);
				var menus =  opt.storage.get("menu");
				if(menus){
					menus = JSON.parse(menus);
					menus.push(data);
					opt.storage.set("menu",JSON.stringify(menus));
				}else{
					var _m = [];
					_m.push(data);
					opt.storage.set("menu",JSON.stringify(_m));
				}
			}

		}else {
			element.tabChange(ELEM.tabFilter, tabIndex);
		}
    };
    var navtab = new LarryTab();
    exports(module_name, function(options) {
		return navtab.set(options);
	});
});