/*!
 * Copyright (c) 2020-Now http://www.j2eefast.com All rights reserved.
 * 基于webuploader UI页面封装
 * @author J2eeFAST
 * @version v1.0.1
 */
if (typeof jQuery === "undefined") {
    throw new Error("fastJS JavaScript requires jQuery")
}
(function($) {
    "use strict";

    var webUpload = function(el, option){
        var options = $.extend({}, $.fn.webUploadex.defaults, option || {});
        var upId = options.id,
            imageFlag = "image" == options.uploadType,
            $upDiv = $("#" + upId + "_Uploader"), // 上传控件
            $fileLists = $upDiv.find("#" + upId + "_fileLists"), // 上传文件表格
            $i18n = function(txt){
                if($.i18n === "undefined"){
                    return txt;
                }
                return $.i18n.prop(txt);
            };

        //如果是只上传图片
        if(imageFlag){
            $fileLists.appendTo($upDiv.find(".queueList"));
        }else{
            //设置表头
            var html = '<thead id="'+upId+'_thead" style="display: none"><tr style="border-top: 1px solid rgb(221, 221, 221);"><th style="width: 300px;max-width: 400px; text-align: left;">文件名称</th><th style="width: 100px;text-align: center">文件大小</th><th style="text-align: center">上传进度</th><th style="text-align: center">信息</th><th style="text-align: center; width: 200px;">操作</th></tr></thead>';
            $fileLists.before(html);
        }
        var $bar = $upDiv.find(".statusBar"), //顶部上传信息bar
            $info = $bar.find(".info"), //上传信息说明
            $uploadBtn = $upDiv.find(".uploadBtn"), //顶部上传按钮
            $placeholder = $upDiv.find(".placeholder"), //顶部上传进度动画
            $progress = $bar.find(".progress").hide(),//底部上传进度 初始隐藏
            $thead = $("#"+upId + "_thead"),
            upNum = 0, //上传数量
            fileSize = 0, //总大小
            devPixe = window.devicePixelRatio || 1, //设备像素
            Sxt = 110 * devPixe,
            Tst = 110 * devPixe,
            stauts = 'pedding', //上传信息状态
            data = {};
        devPixe = function() {
            try {
                var nav = navigator.plugins["Shockwave Flash"];
                nav = nav.description
            } catch(d) {
                try {
                    nav = (new ActiveXObject("ShockwaveFlash.ShockwaveFlash")).GetVariable("$version")
                } catch(G) {
                    nav = "0.0"
                }
            }
            nav = nav.match(/\d+/g);
            return parseFloat(nav[0] + "." + nav[1], 10);
        } ();

        var transition = function() {
                var p = document.createElement("p").style;
                return "transition" in p || "WebkitTransition" in p || "MozTransition" in p || "msTransition" in p || "OTransition" in p
            } (),
            w = [],
            P = [],
            x = [],
            A = [];

        if("false" === options.preview){ //是否显示预览按钮
            options.preview = "";
        }
        //是否支持flash
        if (WebUploader.browser.ie && !WebUploader.Uploader.support("flash")){
            devPixe ? function(tx){
                window.expressinstallcallback = function(tx) {
                    switch (tx) {
                        case "Download.Cancelled":
                            alert(f("安装失败!"));
                            break;
                        case "Download.Failed":
                            alert(f("安装失败!"));
                            break;
                        default:
                            alert(f("安装已成功，请刷新!"))
                    }
                    delete window.expressinstallcallback
                };
                var data = baseURL + '/static/plugins/webuploader/0.1.5/expressInstall.swf',
                    obj = '<object type="application/x-shockwave-flash" data="' +  data+ '" ';
                WebUploader.browser.ie && (obj += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ');
                tx.html(obj + ('width="100%" height="100%" style="outline:0"><param name="movie" value="' + data +
                    '" /><param name="wmode" value="transparent" /><param name="allowscriptaccess" value="always" /></object>'))
            }($upDiv) : $upDiv.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0">' +
                '<img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
        }else{
            //html5 加载控件
            if(typeof WebUploader === "undefined"){
                throw new Error("WebUploader 对象为空,请检查是否引入!");
            }

            if (WebUploader.Uploader.support()) {
                el = {
                    disableGlobalDnd: true, // 是否禁掉整个页面的拖拽功能，如果不禁用，图片拖进来的时候会默认被浏览器打开。
                    swf: baseURL + '/static/plugins/webuploader/0.1.5/Uploader.swf',
                    server: options.service.upload, //上传地址
                    id: upId,
                    imageFlag: imageFlag,
                    formData: {
                        bizId: options.bizId,
                        bizType: options.bizType,
                        uploadType: options.uploadType,
                        imageMaxWidth: options.imageMaxWidth,
                        imageMaxHeight: options.imageMaxHeight,
                        chunkSize: options.chunkSize,
                        __ajax: "json"
                    },
                    chunked: options.chunked,
                    chunkSize: options.chunkSize,
                    threads: options.threads,
                    fileNumLimit: options.maxUploadNum,
                    fileSingleSizeLimit: options.maxFileSize,
                    compress: false
                };

                $.each(options.extendParams,function(index,data){
                    option.formData["extend[" + index + "]"] = data;
                });

                //设置控件上传文件类型
                options.readonly || (option = $.extend(true, {},
                    {
                        pick: {
                            id: "#" + upId + "_filePicker",
                            label: $i18n("点击选择文件"),
                            multiple: 1 < options.maxUploadNum
                        },
                        dnd: "#" + upId + "_Uploader .queueList",
                        paste: "#" + upId + "_Uploader .queueList"
                    }, el),
                    //如果是图片 设置指定接受哪些类型的文件
                    "image" == options.uploadType ? (option.pick.label = $i18n("点击选择图片"),
                        option.accept = {
                            title: "Images",
                            extensions: options.imageAllowSuffixes.replace(/\./g, ""),
                            mimeTypes: "image/*"
                        }) : "media" == options.uploadType ? (option.pick.label = $i18n("点击选择视频"),
                        option.accept = {
                            title: "Medias",
                            extensions: options.mediaAllowSuffixes.replace(/\./g, ""),
                            mimeTypes: "*/*"
                        }) : option.accept = "file" == options.uploadType ? {
                        title: "Files",
                        extensions: options.fileAllowSuffixes.replace(/\./g, ""),
                        mimeTypes: "*/*"
                    }: {
                        title: "All",
                        extensions: (options.imageAllowSuffixes + "," + options.mediaAllowSuffixes + "," + options.fileAllowSuffixes).replace(/\./g, ""),
                        mimeTypes: "*/*"
                    });

                el.uploaderOptions = option;
                if("function" == typeof options.callback){
                    options.callback(options.id, "create", el);
                }
                console.log("数据:" + el.uploaderOptions);
                var upload = WebUploader.create(el.uploaderOptions);
                window.webuploader || (window.webuploader = []);
                window.webuploader.push(upload);
                el.uploader = upload;
                el.options = options;
                window.webuploaderRefresh || (window.webuploaderRefresh = function() {
                    setTimeout(function() {
                            window.webuploader || (window.webuploader = []);
                            for (var index in window.webuploader) window.webuploader[index].refresh();
                        },
                        200)
                });

                //添加上传按钮
                upload.addButton({
                    id: "#" + upId + "_filePicker2",
                    label: $i18n("继续添加")
                })

                if(options.isLazy){
                    $uploadBtn.hide();
                }

                //上传过程中触发，携带上传进度。
                upload.on("uploadProgress",function(file,percentage){
                    // 是否图片
                    if(imageFlag){
                        var fileList = $("#" + upId + file.id);
                        var $percent = fileList.find(".progress span");
                        $percent.css("width", 100 * percentage + "%");
                    }else{
                        var fileList = $("#" + upId + file.id).find(".prog_bar");
                        var $percent = fileList.find(".progress-bar");
                        $percent.css("width", Math.round(100 * percentage) + "%"),
                            $percent.text(Math.round(100 * percentage) + "%");
                    }
                    //存放文件大小- 与进度
                    if("undefined" == typeof data[file.id]){
                        data[file.id] = [file.size,0];
                    }
                    data[file.id][1] = percentage;
                    setPercentage();
                });

                //当文件被加入队列之前触发，此事件的handler返回值为false，则此文件不会被添加进入队列。
                upload.on("beforeFileQueued",function(file){
                    if(upNum > options.maxUploadNum){
                        opt.error($i18n("您只能上传"+options.maxUploadNum+"个文件"));
                        return false;
                    }
                });

                //当文件被移除队列后触发。
                upload.on("fileDequeued",function(file) {
                    removeFileList(file);
                });

                //当某个文件上传到服务端响应后，会派送此事件来询问服务端响应是否有效
                upload.on("uploadAccept", function (obj, ret ,headers) {
                    obj = $("#" + upId + obj.file.id);
                    try {
                        var data = JSON.parse(ret._raw || ret);
                        opt.variable.web_status.SUCCESS != data.code && headers(data.code);
                        if (imageFlag)
                            $('<p class="'+(opt.variable.web_status.SUCCESS != data.code?"error":"success") +'" title="' + data.msg + '">' + data.msg + "</p>").appendTo(obj);
                        else {
                            var css = opt.variable.web_status.SUCCESS == data.code ? "success": "danger";
                            obj.find(".msg").html('<span class="label label-sm label-' + css + '" title="' + data.msg + '">' + data.msg + "</span>")
                        }
                    } catch(e) {
                        imageFlag ? $('<p class="error">' + $i18n("服务器返回出错") + "</p>").appendTo(obj) : obj.find(".msg").html('<span class="label label-sm label-danger">' + $i18n("服务器返回出错") + "</span>")
                    }
                });

                //当文件上传出错时触发。
                upload.on("uploadError", function(file, reason) {
                    console.log(file.fileUpload);
                    file.fileUpload && (0 < $fileLists.find("[fileMd5=" + file.md5 + "]").length ? imageFlag
                        ? $fileLists.find("#" + upId + file.id + " .error").html($i18n("不要选择重复文件")) :
                        $fileLists.find("#" + upId + file.id + " .msg .label").html($i18n("不要选择重复文件")) : (upload.removeFile(file),
                        el.refreshFileList([file.fileUpload], false)))
                });

                //当文件被加入队列以后触发。
                upload.on("fileQueued",function (file) {
                    //上传数量增加
                    upNum ++;
                    fileSize += file.size;
                    console.log(upNum);
                    //状态栏显示
                    if(0 !== upNum || options.readonly){
                        $bar.show();
                    }
                    var html = "";
                    if(imageFlag){ //图片
                        html = $('<li id="' + upId + file.id + '"><p class="title">' + file.name + '</p><p class="imgWrap"></p><p class="progress"><span></span></p><p class="error"></p><div class="file-panel"><span class="cancel">' + $i18n("删除") + "</span></div></li>");
                        var  filePabel = html.find("div.file-panel"),
                            pBar = html.find("p.progress-bar"),
                            img = html.find("p.imgWrap"),
                            errorHtml = html.find("p.error");
                        if("invalid" === file.getStatus()){
                            fileUpInfo(errorHtml, file.statusText);
                        }else{
                            errorHtml.text($i18n("等待上传"));
                            img.text($i18n("预览生成中"));
                            upload.makeThumb(file, function(error, ret) {
                                if(error){
                                    img.text($i18n("不能预览"));
                                }else{
                                    error = $('<img src="' + ret+ '">');
                                    img.empty().append(error);
                                }
                            });
                            data[file.id] = [file.size, 0];
                            file.rotation = 0;
                        }

                        file.on("statuschange",function(err,state){
                            "progress" === state && pBar.hide().width(0);
                            "error" === err || "invalid" === err ? (fileUpInfo(errorHtml, file.statusText), data[file.id][1] = 1) : "interrupt" === err ? fileUpInfo(errorHtml, "interrupt") :
                                "queued" === err ? data[file.id][1] = 0 : "progress" === err && (errorHtml.text($i18n("正在上传") + "..."), pBar.css("display", "block"));
                            html.removeClass("state-" + state).addClass("state-" + err)
                        });

                        html.on("mouseenter", function(){
                            filePabel.stop().animate({
                                height: 30
                            })
                        });

                        html.on("mouseleave", function() {
                            filePabel.stop().animate({
                                height: 0
                            })
                        });

                        filePabel.on("click", "span", function() {
                            switch ($(this).index()) {
                                case 0:
                                    var $this = $(this);
                                    opt.modal.confirm($i18n("确定删除该图片吗？"),
                                        function() {
                                            delFile($this);
                                            upload.removeFile(file)
                                        });
                                    return;
                                case 1:
                                    file.rotation += 90;
                                    break;
                                case 2:
                                    file.rotation -= 90
                            }
                            if (transition) {
                                var d = "rotate(" + file.rotation + "deg)";
                                img.css({
                                    "-webkit-transform": d,
                                    "-mos-transform": d,
                                    "-o-transform": d,
                                    transform: d
                                })
                            } else{
                                img.css("filter", "progid:DXImageTransform.Microsoft.BasicImage(rotation=" + ~~ (file.rotation / 90 % 4 + 4) % 4 + ")")
                            }
                        })
                    }else{
                        $thead.show();
                        html = $('<tr id="' + upId + file.id + '" class="template-upload">' +
                            '<td class="name"><i class="fa fa-file-text-o"></i>' + file.name + '</td>' +
                            '<td class="size">' + WebUploader.formatSize(file.size) + '</td>' +
                            '<td class="prog_bar"><p class="progress"><span class="progress-bar">0%</span></p></td>' +
                            '<td class="msg"></td><td class="btncancel"><a class="btn btn-default btn-xs yellow delete"><i class="fa fa-ban"></i> ' + $i18n("删除") + " </a></td>" +
                            "</tr>");
                        var  btncancel = html.find("td.btncancel");
                        var   pbar = html.find("td.progress-bar");
                        var   msg = html.find("td.msg");
                        "invalid" === file.getStatus() ? fileUpInfo(msg, file.statusText) : (msg.text($i18n("等待上传")), data[file.id] = [file.size, 0], file.rotation = 0);
                        html.on("statuschange",
                            function(a, c) {
                                "error" === a || "invalid" === a ? (fileUpInfo(msg, file.statusText), data[b.id][1] = 1, pbar.text("0%").css("width", "0%")) :
                                    "interrupt" === a ? (fileUpInfo(msg, "interrupt"), pbar.text("0%").css("width", "0%")) : "progress" === a && (msg.text(f("正在上传") + "..."), pbar.css("display", "block"));
                                html.removeClass("state-" + c).addClass("state-" + a)
                            }),
                            btncancel.on("click", "a.delete",
                                function() {
                                    var $this = $(this);
                                    opt.modal.confirm($i18n("确定删除该文件吗?"),
                                        function() {
                                            delFile($this);
                                            upload.removeFile(file)
                                        })
                                });
                    }

                    html.appendTo($fileLists);
                    synStatus("ready");
                    setPercentage();
                    //是否默认上传
                    if(options.isLazy){
                        upload.upload()
                    }
                });

                //当文件上传成功时触发。
                upload.on("uploadSuccess", function(f, res) {
                    var file = $("#" + upId + f.id);
                    var bar = file.find(".progress-bar");
                    try {
                        var res = JSON.parse(res._raw || res);
                        if (opt.variable.web_status.SUCCESS == res.code) { //上传成功
                            var //l = g.fileUpload,
                                src = options.service.download+"?fileId=" + res.id,
                                k = options.returnPath ? "": options.service.download+"?fileId=" + res.id,
                                vUrl = baseURL+"/sys/comm/fileUploadView" +"?fileId="+ res.id;
                            imageFlag ? (file.find(".imgWrap img").attr("src", src), file.find(".file-panel .cancel").attr("fileUploadId", res.id).attr("fUrl",vUrl).attr("fileUrl", src).attr("fileName", res.fileName).attr("fileSize", res.fileSize).attr("fileMd5", res.fileMd5)) :
                                ("" != options.preview ? file.find(".name").html('<i class="fa fa-file-text-o"></i><a class="preview" href="javascript:">' + res.fileName + "</a>") :
                                    file.find(".name").html('<i class="fa fa-file-text-o"></i><a target="_blank" href="' + k + '">' + res.fileName + "</a>"),
                                    file.find(".btncancel").empty()
                                        .append(
                                            ("" != options.preview ? '<a class="btn btn-default btn-xs preview" href="javascript:"><i class="fa fa-eye"></i> ' + $i18n("预览") + "</a> &nbsp;": "") +
                                            '<a class="btn btn-danger btn-xs delete" fileUploadId="' + res.id + '" fileUrl="' + ""
                                            + '" fileName="' + res.fileName + '" fileSize="' + f.size +'" fUrl="' + vUrl + '" fileMd5="' + res.fileMd5 + '"><i class="fa fa-trash-o"></i> ' + $i18n("删除") + '</a> &nbsp;<a class="btn btn-info btn-xs" target="_blank" href="' + k + '" ><i class="fa fa-download"></i> ' + $i18n("下载") + "</a>"));
                            retunfun(res.id, res.path, res.fileName);
                        } else{
                            bar.css("width", "0%").text("0%")
                        }
                    } catch(e) {
                        bar.css("width", "0%").text("0%");
                        //error(e);
                        console.log(e);
                    }
                    setStats();
                });

                //可以捕获到此类错误，目前有以下错误会在特定的情况下派送错来。
                upload.on("error", function(type) {
                    console.log("ERROR:" + type);
                    switch (type) {
                        case "Q_TYPE_DENIED":
                            type = $i18n("文件类型不对");
                            break;
                        case "F_EXCEED_SIZE":
                            type = $i18n("文件大小超出");
                            break;
                        case "F_DUPLICATE":
                            type = $i18n("不要选择重复文件");
                            break;
                        case "Q_EXCEED_NUM_LIMIT":
                            type = $i18n("您只能上传"+options.maxUploadNum+"个文件");
                            break;
                        case "Q_EXCEED_SIZE_LIMIT":
                            type = $i18n("文件大小超出");
                            break;
                        default:
                            type = $i18n("上传失败，请重试")
                    }
                    opt.modal.error(type);
                });

                //控制所有触发事件回调
                upload.on("all", function(type, arg1) {
                    switch (type) {
                        case "uploadFinished":
                            synStatus("confirm");
                            break;
                        case "startUpload":
                            synStatus("uploading");
                            break;
                        case "stopUpload":
                            synStatus("paused")
                    }
                });

                //上传按钮点击事件
                $uploadBtn.on("click",function() {
                    if ($(this).hasClass("disabled")){
                        return false;
                    }
                    "ready" === stauts ? upload.upload() : "paused" === stauts ? upload.upload() : "uploading" === stauts && upload.stop()
                });

                //重新上传
                $info.on("click", ".retry", function() {
                    upload.retry();
                    return false;
                });

                //忽略错误文件
                $info.on("click", ".ignore", function() {
                    var file, files = upload.getFiles("error");
                    for (var i = 0; file = files[i++];) {
                        upload.removeFile(file);
                    }
                    synStatus("finish");
                    setPercentage();
                    return false;
                });


                $fileLists.sortable && (imageFlag ? $fileLists.sortable({
                    connectWith: "li",
                    handle: ".imgWrap img",
                    placeholder: "sort-highlight",
                    forcePlaceholderSize: !0,
                    zIndex: 999999,
                    update: function(b, a) {
                        rerdFile($fileLists.find("li .file-panel .cancel"))
                    }
                }) : $fileLists.sortable({
                    connectWith: "tr",
                    handle: ".name i",
                    placeholder: "sort-highlight",
                    forcePlaceholderSize: !0,
                    zIndex: 999999,
                    update: function(b, a) {
                        rerdFile($fileLists.find("tr .btncancel .delete"))
                    }
                }));

                if("true" == options.preview){
                    $fileLists.on("click", ".imgWrap img, a.preview",
                        function() {
                            var div = $(this);

                            div = "IMG" == div.prop("tagName") ?
                                div.closest("li").find(".file-panel .cancel") :
                                div.closest("tr").find(".btncancel .delete");

                            //预览
                            var url = baseURL + "sys/component/fileViwe?fileName="+ opt.common.encodeUrl(div.attr("fileName")) +
                                "&fileUrl=" + opt.common.encodeUrl(div.attr("fUrl"));
                            opt.layer? opt.layer.open({
                                type: 2,
                                maxmin: false,
                                shadeClose: true,
                                title: false,
                                area: [$(top.window).width() - 200 + "px", $(top.window).height() - 50 + "px"],
                                content: url
                            }):opt.modal.windowOpen(url);
                        });
                }else if(imageFlag){
                    $fileLists.on("click", ".imgWrap img",
                        function() {
                            var src = $(this).attr("src");
                            0 == $("#outerdiv").length && $('<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:99999;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:fixed;"><img id="bigimg" style="border:2px solid #fff;" src="" /></div></div>').appendTo(document.body);
                            $("#bigimg").attr("src", src);
                            $("<img/>").attr("src", src).load(function() {
                                var b = $(window).width(),
                                    c = $(window).height(),
                                    e = this.width,
                                    g = this.height;
                                if (g > .8 * c) {
                                    var f = .8 * c;
                                    var h = f / g * e;
                                    h > .8 * b && (h = .8 * b)
                                } else e > .8 * b ? (h = .8 * b, f = h / e * g) : (h = e, f = g);
                                $("#bigimg").css("width", h);
                                b = (b - h) / 2;
                                c = (c - f) / 2;
                                $("#innerdiv").css({
                                    top: c,
                                    left: b
                                });
                                $("#outerdiv").fadeIn("fast")
                            });
                            $("#outerdiv").click(function() {
                                $(this).fadeOut("fast")
                            })
                        });
                }

                el.refreshFileList = function(files, e) {
                    e && ($fileLists.empty(), fileSize = upNum = 0, w = [], x = [], A = []);
                    if (files && 0 < files.length)
                        for (e = 0; e < files.length; e++) {
                            var file = files[e],
                                url = file.filePath,
                                dUrl = options.returnPath ? url: options.service.download +"?fileId="+ file.id,
                                vUrl = baseURL+"sys/comm/fileUploadView" +"?fileId="+ file.id;
                            if (imageFlag) { //图片

                                var $li = $('<li id="' + file.id + '"><p class="title"><a target="_blank" href="' + dUrl +
                                    '">' + file.fileName + '</a></p><p class="imgWrap"><img src="' + dUrl +
                                    '"/></p><p class="progress"><span></span></p><div class="file-panel"><span class="cancel '
                                    + (options.readonly ? "hide" : "") + '" fileUploadId="' + file.id + '" fileUrl="' + url +'" fUrl="' + vUrl +
                                    '" fileName="' + file.fileName + '" fileSize="' + file.fileSize + '" fileMd5="'
                                    + file.fileMd5 + '">' + $i18n("删除") + "</span></div>" + (file.message ? file.message : "")
                                    + "</li>");
                                $li.on("mouseenter",
                                    function () {
                                        var index = $(this).index();
                                        $fileLists.find(".file-panel").eq(index).stop().animate({
                                            height: 30
                                        })
                                    });
                                $li.on("mouseleave",
                                    function () {
                                        var index = $(this).index();
                                        $fileLists.find(".file-panel").eq(index).stop().animate({
                                            height: 0
                                        })
                                    });
                                $li.on("click", "span",
                                    function () {
                                        var b = $(this),
                                            d = $(this).closest("li"),
                                            e = $(this).parent().data("fileRotation");
                                        e || (e = 0);
                                        switch (b.index()) {
                                            case 0:
                                                if (!options.readonly) {
                                                    var g = $(this);
                                                    opt.modal.confirm($i18n("确定删除该图片吗？"),
                                                        function (b) {
                                                            delFile(g);
                                                            options.returnPath ? removeFileList({
                                                                id: 0,
                                                                size: 0
                                                            }) : (b = g.attr("fileSize"), removeFileList({
                                                                id: 0,
                                                                size: b
                                                            }));
                                                            d.remove()
                                                        })
                                                }
                                                return;
                                            case 1:
                                                e += 90;
                                                break;
                                            case 2:
                                                e -= 90
                                        }
                                        b = $(this).parent().parent().index();
                                        b = $fileLists.find(".imgWrap :eq(" + b + ")");
                                        if (transition) {
                                            var h = "rotate(" + e + "deg)";
                                            b.css({
                                                "-webkit-transform": h,
                                                "-mos-transform": h,
                                                "-o-transform": h,
                                                transform: h
                                            })
                                        } else b.css("filter", "progid:DXImageTransform.Microsoft.BasicImage(rotation=" + ~~(e / 90 % 4 + 4) % 4 + ")");
                                        $(this).parent().data("fileRotation", e)
                                    });
                            } else{
                                var $li = $('<tr id="' + file.id + '" class="template-upload"><td class="name"><i class="fa fa-file-text-o"></i>'
                                    + file.fileName + '</td><td class="size">' + (options.returnPath ? "": WebUploader.formatSize(file.fileSize)) +
                                    '</td><td class="prog_bar">' + (file.progress ? file.progress: file.createBy ? file.createBy: "") +
                                    '</td><td class="msg">' + (file.message ? file.message: file.createTime ? file.createTime: "") +
                                    '</td><td class="btncancel">' + ("" != options.preview ? '<a class="btn btn-default btn-xs preview" href="javascript:"><i class="fa fa-eye"></i> '
                                        + $i18n("预览") + "</a> &nbsp;": "") + '<a class="btn btn-danger btn-xs delete' + (options.readonly ? " hide": "") +
                                    '" fileUploadId="' + file.id + '" fileUrl="' + url +'" fUrl="' + vUrl + '" fileName="' + file.fileName + '" fileSize="' + file.fileSize
                                    + '" fileMd5="' + file.fileMd5 + '"><i class="fa fa-trash-o"></i> ' + $i18n("删除") + " </a>" +
                                    (options.readonly ? "": " &nbsp;") + '<a class="btn btn-info btn-xs blue" onclick="opt.common.downLoadFile(\'' +dUrl+ '\')" href="javascript:"><i class="fa fa-download"></i> ' + $i18n("下载") +
                                    " </a></td></tr>");
                                "" != options.preview ? $li.find(".name").html('<i class="fa fa-file-text-o"></i><a class="preview" href="javascript:">'
                                    + file.fileName + "</a>") : $li.find(".name").html('<i class="fa fa-file-text-o"></i><a  onclick="opt.common.downLoadFile(\'' +dUrl+ '\')" href="javascript:">' +
                                    file.fileName + "</a>");
                                $li.on("click", "a.delete",
                                    function() {
                                        var b = $(this),
                                            d = $(this).closest("tr");
                                        opt.modal.confirm($i18n("确定删除该文件吗？"),
                                            function(a) {
                                                delFile(b);
                                                options.returnPath ? removeFileList({
                                                    id: 0,
                                                    size: 0
                                                }) : (a = b.attr("fileSize"), removeFileList({
                                                    id: 0,
                                                    size: a
                                                }));
                                                d.remove()
                                            })
                                    });
                            }
                            upNum++;
                            fileSize += file.fileSize;
                            $fileLists.append($li);
                            retunfun(file.id, url, file.fileName);
                        }
                    if (0 < w.length || 0 < x.length)
                        options.readonly || $bar.show(),
                            synStatus("ready");
                    setPercentage();
                    "function" == typeof options.callback && options.callback(options.id, "ready", el);
                };
                //回显业务类型
                el.refreshFileListByBizData = function() {
                    console.log("--URL:" + options.service.fileList + ( - 1 == options.service.fileList.indexOf("?") ? "?": "&") + "__t=" + (new Date).getTime())
                    $.ajax({
                        url: options.service.fileList + ( - 1 == options.service.fileList.indexOf("?") ? "?": "&") + "__t=" + (new Date).getTime(),
                        data: {
                            bizId: options.bizId,
                            bizType: options.bizType
                        },
                        xhrFields: {
                            withCredentials: !0
                        },
                        dataType: "json",
                        success: function(res) {
                            opt.variable.web_status.SUCCESS === res.code && el.refreshFileList(res.fileList, true);
                        }
                    })
                };
                //如果业务主键ID与业务类型都有则回显文件
                "" != options.bizId && "" != options.bizType ? el.refreshFileListByBizData() : options.returnPath && el.refreshFileListByPath();
                return el;
            }else{
                opt.modal.error($i18n("文件上传组件不支持您的浏览器，请使用高版本浏览器!"))
            }

            /**
             * 设置状态
             */
            function setStats(){
                var stats = upload.getStats();
                var html = "";
                if ("confirm" === stauts && stats.uploadFailNum){
                    html = stats.uploadFailNum + $i18n(imageFlag ? "张图片": "个文件") + $i18n("上传失败") + ', <a class="retry" href="#">' + $i18n("重新上传") + "</a>" + $i18n("或") + '<a class="ignore" href="#">' + $i18n("忽略") + "</a>";
                } else{
                    "confirm" === stauts || "ready" === stauts ? html = $i18n("总共") + upNum + $i18n(imageFlag ? "张图片": "个文件") +
                        (0 >= fileSize ? "": "（" + WebUploader.formatSize(fileSize) + "）") : (html = $i18n("已上传") + upNum + $i18n(imageFlag ? "张图片": "个文件") +
                        (0 >= fileSize ? "": "（" + WebUploader.formatSize(fileSize) + "）"), stats.uploadFailNum && (html += ", " + $i18n("失败"+stats.uploadFailNum + "个")));
                }
                $info.html(html);
                upNum < options.maxUploadNum ? $("#" + upId + "_filePicker2").show() : $("#" + upId + "_filePicker2").hide();
                window.webuploaderRefresh();
            }

            /**
             * 同步状态
             * @param str
             */
            function synStatus(str) {
                if (str !== stauts) {
                    $uploadBtn.removeClass("state-" + stauts);
                    $uploadBtn.addClass("state-" + str);
                    stauts = str;
                    switch (stauts) {
                        case "pedding":
                            $placeholder.removeClass("element-invisible");
                            $fileLists.hide();
                            $thead.hide();
                            $bar.addClass("element-invisible");
                            break;
                        case "ready":
                            $placeholder.addClass("element-invisible");
                            $("#" + upId + "_filePicker2").removeClass("element-invisible");
                            $fileLists.show();
                            $thead.show();
                            $bar.removeClass("element-invisible");
                            break;
                        case "uploading":
                            $("#" + upId + "_filePicker2").addClass("element-invisible");
                            $progress.show();
                            $uploadBtn.text($i18n("暂停上传"));
                            break;
                        case "paused":
                            $progress.show();
                            $uploadBtn.text($i18n("继续上传"));
                            break;
                        case "confirm":
                            $progress.hide();
                            $("#" + upId + "_filePicker2").removeClass("element-invisible");
                            $uploadBtn.text($i18n("开始上传"));
                            str = upload.getStats();
                            if (str.successNum && !str.uploadFailNum) {
                                synStatus("finish");
                                return
                            }
                            break;
                        case "finish":
                            str = upload.getStats(),
                            str.successNum || (stauts = "done")
                    }
                    setStats()
                }
            }


            function retunfun(id, path, fileName) {
                if ("" != options.bizType) {
                    w.push(id);
                    $("#" + upId).val(w.join(",")).change();
                    try {
                        $("#" + upId).resetValid()
                    } catch(H) {}
                }
                if (options.returnPath) {
                    x.push(id);
                    A.push(fileName);
                    $("#" + options.filePathInputId).val(x.join("|")).change();
                    $("#" + options.fileNameInputId).val(A.join("|")).change();
                    try {
                        $("#" + options.filePathInputId).resetValid()
                    } catch(H) {}
                    try {
                        $("#" + options.fileNameInputId).resetValid()
                    } catch(H) {}
                }
                "function" == typeof options.callback && options.callback(options.id, "addFile", el, id, path, fileName);
            }

            /**
             * 设置文件进度
             */
            function setPercentage(){
                var data1 = 0,
                    data3 = 0,
                    data0 = 0,
                    progr = $progress.children();
                $.each(data,
                    function(index, value) {
                        data0 += value[0];
                        data1 += value[0] * value[1]
                    });
                data3 = data0 ? data1 / data0: 0;
                progr.eq(0).text(Math.round(100 * data3) + "%");
                progr.eq(1).css("width", Math.round(100 * data3) + "%");
                setStats();
            }

            function delFile(el0) {
                var fileId = el0.attr("fileUploadId"), G = el0.attr("fileUrl");
                el0 = el0.attr("fileName");
                if (fileId && null != fileId) {
                    if ("" != options.bizType) {
                        w.splice($.inArray(fileId, w), 1);
                        P.push(fileId);
                        $("#" + upId).val(w.join(","));
                        $("#" + upId + "__del").val(P.join(","));
                        try {
                            $("#" + upId).resetValid()
                        } catch(H) {}
                        try {
                            $("#" + upId + "__del").resetValid()
                        } catch(H) {}
                    }
                    if (options.returnPath) {
                        x.splice($.inArray(G, x), 1);
                        A.splice($.inArray(el0, A), 1);
                        $("#" + options.filePathInputId).val(x.join("|"));
                        $("#" + options.fileNameInputId).val(A.join("|"));
                        try {
                            $("#" + options.filePathInputId).resetValid()
                        } catch(H) {}
                        try {
                            $("#" + options.fileNameInputId).resetValid()
                        } catch(H) {}
                    }
                }
                "function" == typeof options.callback && options.callback(options.id, "delFile", el, fileId, G, el0)
            }


            function rerdFile(el0) {
                if ("" != options.bizType) {
                    w = [];
                    el0.each(function() {
                        w.push(a(this).attr("fileUploadId"))
                    });
                    $("#" + upId).val(w.join(",")).change();
                    try {
                        $("#" + upId).resetValid()
                    } catch(d) {}
                }
                if (options.returnPath) {
                    x = [];
                    A = [];
                    el0.each(function() {
                        x.push(a(this).attr("fileUrl"));
                        A.push(a(this).attr("fileName"))
                    });
                    $("#" + options.filePathInputId).val(x.join("|")).change();
                    $("#" + options.fileNameInputId).val(A.join("|")).change();
                    try {
                        a("#" + options.filePathInputId).resetValid()
                    } catch(d) {}
                    try {
                        a("#" + options.fileNameInputId).resetValid()
                    } catch(d) {}
                }
            }

            function removeFileList(file) {
                upNum--;
                upNum || synStatus("pedding");
                fileSize -= file.size;
                delete data[file.id];
                setPercentage();
                //移除ui
                imageFlag ? $("#" + upId + file.id).off().find(".file-panel").off().end().remove() : $("#" + upId + file.id).off().find(".btncancel").off().end().remove();
            }

            function fileUpInfo(el, txt) {
                switch (txt) {
                    case "exceed_size":
                        txt = $i18n("文件大小超出");
                        break;
                    case "interrupt":
                        txt = $i18n("文件传输中断");
                        break;
                    case "http":
                        txt = $i18n("HTTP请求错误");
                        break;
                    case "not_allow_type":
                        txt = $i18n("文件格式不允许");
                        break;
                    default:
                        txt = $i18n("上传失败，请重试")
                }

                if(txt != null){
                    imageFlag ? el.text(txt) : el.html('<span class="label label-sm label-danger">' + a + "</span>");
                }
            }

        }
    };

    window.webuploaderRegister || (window.webuploaderRegister = !0, WebUploader.Uploader.register({
            name: "fileupload",
            "before-send-file": "beforeSendFile",
            "before-send": "beforeSend",
            "after-send-file": "afterSendFile"
        },
        {
            beforeSendFile: function(e) {
                var g = this.owner,
                    k = WebUploader.Deferred();
                g.md5File(e, 0, 10485760).then(function(n) {
                    e.md5 = n;
                    k.resolve();
                });
                return k.promise()
            },
            beforeSend: function(a) {
                var e = this.owner;
                a = a.file;
                console.log("fileMd5:" + a.md5);
                e.options.formData.fileMd5 = a.md5;
                e.options.formData.fileName = a.name;
            },
            afterSendFile: function(a) {}
        }), $("textarea").each(function() {
        var e = $(this);
        e.data("x", e.outerWidth());
        e.data("y", e.outerHeight());
        e.mouseup(function() {
            var e = $(this);
            e.outerWidth() == e.data("x") && e.outerHeight() == e.data("y") || window.webuploaderRefresh();
            e.data("x", e.outerWidth());
            e.data("y", e.outerHeight())
        })
    }));


    $.fn.webUploadex = function(option, param) {
        var value, $this = $(this),
            data = $this.data("webuploader.ui.ex"),
            options = "object" === typeof option && option;
        data || (data = new webUpload($this,options), $this.data("webuploader.ui.ex", data));
        "string" === typeof option &&
        "function" === typeof data[option] &&
        (value = param instanceof Array ? data[options].apply(data, param) : data[option](param))
        return value;
    };

    $.fn.webUploadex.defaults = {
        id: "",             //上传控件ID
        bizId: "",          //业务主键
        bizType: "",        //业务类型
        readonly: false,    //是否只读模式，只读模式下为查看模式，只允许下载
        returnPath: false,  //是否是返回文件路径到输入框（默认false），可将路径直接保存到某个字段里
        filePathInputId: "",//设置文件URL存放的输入框的ID，当returnPath为true的时候，返回文件URL到这个输入框
        fileNameInputId: "",// 设置文件名称存放的输入框的ID，当returnPath为true的时候，返回文件名称到这个输入框
        uploadType: "",     // 上传文件类型 all , file, image
        imageAllowSuffixes: ".gif,.bmp,.jpeg,.jpg,.ico,.png,.tif,.tiff,",
        mediaAllowSuffixes: ".flv,.swf,.mkv,webm,.mid,.mov,.mp3,.mp4,.m4v,.mpc,.mpeg,.mpg,.swf,.wav,.wma,.wmv,.avi,.rm,.rmi,.rmvb,.aiff,.asf,.ogg,.ogv,",
        fileAllowSuffixes: ".doc,.docx,.rtf,.xls,.xlsx,.csv,.ppt,.pptx,.pdf,.vsd,.txt,.md,.xml,.rar,.zip,7z,.tar,.tgz,.jar,.gz,.gzip,.bz2,.cab,.iso,",
        maxFileSize: 104857600, //当个文件最大字符
        maxUploadNum: 300,      // 上传文件最大总数量
        imageMaxWidth: 1024,    // 图片最大宽度
        imageMaxHeight: 768,    // 图片高度
        service: {              // 服务器接收端地址
            upload: baseURL + "/sys/comm/upload",
            download: baseURL + "/sys/comm/download",
            fileList: baseURL + "/sys/comm/fileList"
        },
        extendParams: {}, //提交的上传扩展参数
        chunked: false, //
        chunkSize: 10485760,
        threads: 3,
        isLazy: false, //是否自动上传
        preview: "" // 是否显示预览按钮
    }
})(jQuery);
