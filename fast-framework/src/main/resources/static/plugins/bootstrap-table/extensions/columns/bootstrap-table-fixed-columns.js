/**
 * @author zhixin wen <wenzhixin2010@gmail.com>
 * @version: v1.0.1
 * Modificated 16.08.16 by Aleksej Tokarev (Loecha)
 *  - Sorting Problem solved
 *  - Recalculated Size of fixed Columns
 */
/**
 * J2eeFAST 二次修改
 */
(function ($) {
    'use strict';

    $.extend($.fn.bootstrapTable.defaults, {
        fixedColumns: false,
        fixedNumber: 1,
        rightFixedColumns: false,
        rightFixedNumber: 1
    });

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initHeader = BootstrapTable.prototype.initHeader,
        _initBody = BootstrapTable.prototype.initBody,
        _resetView = BootstrapTable.prototype.resetView;

    BootstrapTable.prototype.initFixedColumns = function () {
        this.timeoutHeaderColumns_ = 0;
        this.timeoutBodyColumns_ = 0;

        if(this.options.cardView){
            if(this.options.fixedColumns){
                if($("#"+this.options.id + "-left-fixed-table-columns")){
                    $("#"+this.options.id + "-left-fixed-table-columns").hide();
                }
                if($("#"+this.options.id + "-left-fixed-body-columns")){
                    $("#"+this.options.id + "-left-fixed-body-columns").hide();
                }
            }
            if(this.options.rightFixedColumns){
                if($("#"+this.options.id + "-right-fixed-table-columns")){
                    $("#"+this.options.id + "-right-fixed-table-columns").hide();
                }
            }
            return;
        }
        if (this.options.fixedColumns) {

            var $leftFixedHeader = this.$tableContainer.find('.left-fixed-table-columns');
            if(!$leftFixedHeader.length){
                this.$fixedHeader = $([
                    '<div id="'+this.options.id+'-left-fixed-table-columns" class="left-fixed-table-columns">',
                    '<table>',
                    '<thead></thead>',
                    '</table>',
                    '</div>'].join(''));

                this.$fixedHeader.find('table').attr('class', this.$el.attr('class'));
                this.$fixedHeaderColumns = this.$fixedHeader.find('thead');
                this.$tableHeader.before(this.$fixedHeader);

                this.$fixedBody = $([
                    '<div id="'+this.options.id+'-left-fixed-body-columns" class="left-fixed-body-columns">',
                    '<table>',
                    '<tbody></tbody>',
                    '</table>',
                    '</div>'].join(''));

                this.$fixedBody.find('table').attr('class', this.$el.attr('class'));
                this.$fixedBodyColumns = this.$fixedBody.find('tbody');
                this.$tableBody.before(this.$fixedBody);
            }
        }else{
            $("#"+this.options.id + "-left-fixed-table-columns").show();
            $("#"+this.options.id + "-left-fixed-body-columns").show();
        }
        if (this.options.rightFixedColumns) {
            var $rightFixedHeader = this.$tableContainer.find('.right-fixed-table-columns');
            if(!$rightFixedHeader.length){
                this.$rightfixedBody = $([
                    '<div id="'+this.options.id+'-right-fixed-table-columns" class="right-fixed-table-columns">',
                    '<table>',
                    '<thead></thead>',
                    '<tbody style="background-color: #fff;"></tbody>',
                    '</table>',
                    '</div>'].join(''));
                this.$rightfixedBody.find('table').attr('class', this.$el.attr('class'));
                this.$rightfixedHeaderColumns = this.$rightfixedBody.find('thead');
                this.$rightfixedBodyColumns = this.$rightfixedBody.find('tbody');
                this.$tableBody.before(this.$rightfixedBody);
                if (this.options.fixedColumns) {
                    $('.right-fixed-table-columns').attr('style','right:0px;');
                }
            }else{
                $("#"+this.options.id + "-right-fixed-table-columns").show();
            }
        }
    };

    BootstrapTable.prototype.initHeader = function () {

        _initHeader.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.fixedColumns && !this.options.rightFixedColumns){
            return;
        }

        this.initFixedColumns();

        if(this.options.cardView){
            return;
        }

        var $ltr = this.$header.find('tr:eq(0)').clone(true),
            that = this,
            $rtr = this.$header.find('tr:eq(0)').clone(),
            $thisRths = this.$header.find('tr:eq(0)').find('th'),
            $lths = $ltr.clone(true).find('th'),
            $rths = $rtr.clone().find('th');
        $ltr.html('');
        $rtr.html('');
        //右边列冻结
        if (this.options.rightFixedColumns) {
            // var $trs = this.$header.find('tr').clone(true); //Fix: Aleksej "clone()" mit "clone(true)" ersetzt
            // $trs.each(function () {
            //     // This causes layout problems:
            //     //$(this).find('th:gt(' + (that.options.fixedNumber -1) + ')').remove(); // Fix: Aleksej "-1" hinnzugefügt. Denn immer eine Spalte Mehr geblieben ist
            //     $(this).find('th:gt(' + that.options.fixedNumber + ')').remove();
            // });

            for (var i = 0; i < this.options.rightFixedNumber; i++) {
                $rtr.append($rths.eq($rths.length - this.options.rightFixedNumber + i).clone());
                $thisRths.eq($rths.length - this.options.rightFixedNumber + i).children().each(function(){
                    $(this).css("visibility" ,"hidden");
                });
            }
            this.$rightfixedHeaderColumns.html('').append($rtr);
        }

        //左边列冻结
        if (this.options.fixedColumns) { //left-fixed-table-columns
            for (var i = 0; i < this.options.fixedNumber; i++) {
                $ltr.append($lths.eq(i).clone(true));

            }
            this.$fixedHeaderColumns.html('').append($ltr);
            this.$selectAll = $ltr.find('[name="btSelectAll"]');
            this.$selectAll.on('click', function () {
                var checked = $(this).prop('checked');
                that.$fixedBodyColumns.find("input[name=btSelectItem]").filter(':enabled').prop('checked', checked);
                that.$fixedBodyColumns.find("input[name=btSelectItem]").closest('tr')[checked ? 'addClass' : 'removeClass']('selected');
            });
        }


    };

    BootstrapTable.prototype.initBody = function () {
        _initBody.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.fixedColumns && !this.options.rightFixedColumns) {
            return;
        }

        if(this.options.cardView){
            return;
        }

        var that = this;
        if (this.options.fixedColumns) {
            //页面新增左侧表格内容
            this.$fixedBodyColumns.html('');
            this.$body.find('> tr[data-index]').each(function () {
                var $tr = $(this).clone(true),
                    $tds = $tr.clone(true).find('td');
                $tr.html('');
                for (var i = 0; i < that.options.fixedNumber; i++) {
                    var $ftd = $tds.eq(i).clone(true);
                    var $select = $ftd.find('input[name="btSelectItem"]');
                    if($select.length){
                        $ftd.css("width","36px").css("padding","8px 0");
                    }
                    $tr.append($ftd);
                }
                that.$fixedBodyColumns.append($tr);
            });
        }
        if (this.options.rightFixedColumns) {
            //页面新增右侧表格内容
            this.$rightfixedBodyColumns.html('');
            this.$body.find('> tr[data-index]').each(function () { //遍历行
                var $thisTr = $(this);
                var $tr = $(this).clone(), //当前行
                    $tds = $tr.clone().find('td'), //列
                    $thisTds =  $thisTr.find('td');
                $tr.html('');
                for (var i = 0; i < that.options.rightFixedNumber; i++) {
                    var indexTd = $tds.length - that.options.rightFixedNumber + i;
                    var oldTd = $tds.eq(indexTd);
                    var fixTd = oldTd.clone();
                    var buttons = fixTd.find('button');
                    //事件转移：冻结列里面的事件转移到实际按钮的事件
                    buttons.each(function (key, item) {
                        $(item).click(function () {
                            that.$body.find("tr[data-index=" + $tr.attr('data-index') + "] td:eq(" + indexTd + ") button:eq(" + key + ")").click();
                        });
                    });
                    $tr.append(fixTd);
                    $thisTds.eq(indexTd).children().each(function(){
                        $(this).css("visibility" ,"hidden");
                    });
                }
                that.$rightfixedBodyColumns.append($tr);
            });
        }
    };

    BootstrapTable.prototype.resetView = function () {
        _resetView.apply(this, Array.prototype.slice.apply(arguments));

        console.log("------------>>>>>>>>>>>>>>>>>>>")
        if (!this.options.fixedColumns && !this.options.rightFixedColumns) {
            return;
        }

        if(this.options.cardView){
            // $("#"  + that.options.id).removeAttr("style");
            // console.log("--->>"+$("#"  + that.options.id).attr("style"))
            return;
        }

        clearTimeout(this.timeoutHeaderColumns_);
        this.timeoutHeaderColumns_ = setTimeout($.proxy(this.fitHeaderColumns, this), this.$el.is(':hidden') ? 100 : 0);

        clearTimeout(this.timeoutBodyColumns_);
        this.timeoutBodyColumns_ = setTimeout($.proxy(this.fitBodyColumns, this), this.$el.is(':hidden') ? 100 : 0);
    };

    BootstrapTable.prototype.fitHeaderColumns = function () {
        var that = this,
            visibleFields = this.getVisibleFields(),
            headerWidth = 0;
        if (that.options.fixedColumns) {
            this.$body.find('tr:first-child:not(.no-records-found) > *').each(function (i) {
                var $this = $(this),
                    index = i;

                if (i >= that.options.fixedNumber) {
                    return false;
                }

                if (that.options.detailView && !that.options.cardView) {
                    index = i - 1;
                }
                that.$fixedHeader.find('thead th[data-field="' + visibleFields[index] + '"]')
                    .find('.fht-cell').width($this.innerWidth());
                headerWidth += $this.outerWidth();
            });
            this.$fixedHeader.width(headerWidth + 2).show();
        }
        if (that.options.rightFixedColumns) {
            headerWidth = 0;
            var totalLength = $("#" + that.options.id).find('th').length;
            this.$body.find('tr:first-child:not(.no-records-found) > *').each(function (i) {
                var $this = $(this),
                    index = totalLength - i - 1;

                if (i >= that.options.rightFixedNumber) {
                    return false;
                }
                headerWidth += $("#"  + that.options.id).find("tr:first-child>th").eq(index).width();
            });
            this.$rightfixedBody.width(headerWidth - 1).show();
        }
    };

    BootstrapTable.prototype.fitBodyColumns = function () {
        var that = this,
            top = -(parseInt(this.$el.css('margin-top'))),
            height = this.$tableBody.height();

        if (that.options.fixedColumns) {
            if (!this.$body.find('> tr[data-index]').length) {
                this.$fixedBody.hide();
                return;
            }
            //
            if (!this.options.height) {
                top = this.$fixedHeader.height()- 1;
                height = height - top;
            }

            this.$fixedBody.css({
                width: this.$fixedHeader.width(),
                height: height,
                top: top + 1
            }).show();
            // //
            // var bsHeight = $("#" + that.options.id).find("tr").eq(1).height();
            // var fixedHeight = $("#" + that.options.id).parent().prev().find("tr").eq(1).height();
            // var resizeHeight = bsHeight > fixedHeight ? bsHeight: fixedHeight;
            // this.$body.find('> tr').each(function (i) {
            //
            //     that.$fixedBody.find('tr:eq(' + i + ')').height(i == 0 ? resizeHeight - 1 : resizeHeight);
            //     //$("#" + that.options.id).find('tbody>tr:eq(' + i + ')').height(resizeHeight);
            //     // var thattds = this;
            //     // that.$fixedBody.find('tr:eq(' + i + ')').find('td').each(function (j) {
            //     //     $(this).width($($(thattds).find('td')[j]).width() + 1);
            //     // });
            // });

            this.$body.find('> tr').each(function (i) {
                that.$fixedBody.find('tr:eq(' + i + ')').height($(this).height());

                // var $tr = $(this), //当前行
                //     $tds = $tr.clone().find('td'); //列
                // for (var i = 0; i < that.options.rightFixedNumber; i++) {
                //     var indexTd = $tds.length - that.options.rightFixedNumber + i;
                //     var oldTd = $tds.eq(indexTd);
                //     oldTd.css();
                // }
            });

            // $("#" + that.options.id).on("check.bs.table uncheck.bs.table", function (e, rows, $element) {
            //     var index= $element.data('index');
            //     $(this).find('.bs-checkbox').find('input[data-index="' + index + '"]').prop("checked", true);
            //     var selectFixedItem = $('.left-fixed-body-columns input[name=btSelectItem]');
            //     var checkAll = selectFixedItem.filter(':enabled').length &&
            //         selectFixedItem.filter(':enabled').length ===
            //         selectFixedItem.filter(':enabled').filter(':checked').length;
            //     $(".left-fixed-table-columns input[name=btSelectAll]").prop('checked', checkAll);
            //     $('.fixed-table-body input[name=btSelectItem]').closest('tr').removeClass('selected');
            // });
            //
            // $("#" + that.options.id).off('click', '.fixed-table-body').on('click', '.th-inner', function (event) {
            //     $.each(that.$fixedHeader.find('th'), function (i, th) {
            //         $(th).find('.sortable').removeClass('desc asc').addClass('both');
            //     });
            // });
            //
            // // events
            // this.$fixedHeader.off('click', '.th-inner').on('click', '.th-inner', function (event) {
            //     var target = $(this);
            //     var $this = event.type === "keypress" ? $(event.currentTarget) : $(event.currentTarget).parent(), $this_ = that.$header.find('th').eq($this.index());
            //
            //     var sortOrder = "";
            //     if (table.options.sortName === $this.data('field')) {
            //         sortOrder = table.options.sortOrder === 'asc' ? 'desc' : 'asc';
            //     } else {
            //         sortOrder = $this.data('order') === 'asc' ? 'desc' : 'asc';
            //     }
            //     table.options.sortOrder = sortOrder;
            //     var sortName = $this.data('sortName') ? $this.data('sortName') : $this.data('field');
            //     if (target.parent().data().sortable) {
            //         $.each(that.$fixedHeader.find('th'), function (i, th) {
            //             $(th).find('.sortable').removeClass('desc asc').addClass(($(th).data('field') === sortName || $(th).data('sortName') === sortName) ? sortOrder : 'both');
            //         });
            //     }
            // });
            //
            // this.$tableBody.on('scroll', function () {
            //     that.$fixedBody.find('table').css('top', -$(this).scrollTop());
            // });
            // this.$body.find('> tr[data-index]').off('hover').hover(function () {
            //     var index = $(this).data('index');
            //     that.$fixedBody.find('tr[data-index="' + index + '"]').addClass('hover');
            // }, function () {
            //     var index = $(this).data('index');
            //     that.$fixedBody.find('tr[data-index="' + index + '"]').removeClass('hover');
            // });
            // this.$fixedBody.find('tr[data-index]').off('hover').hover(function () {
            //     var index = $(this).data('index');
            //     that.$body.find('tr[data-index="' + index + '"]').addClass('hover');
            // }, function () {
            //     var index = $(this).data('index');
            //     that.$body.find('> tr[data-index="' + index + '"]').removeClass('hover');
            // });
        }
        if (that.options.rightFixedColumns) {
            if (!this.$body.find('> tr[data-index]').length) {
                this.$rightfixedBody.hide();
                return;
            }

            this.$body.find('> tr').each(function (i) {
                that.$rightfixedBody.find('tbody tr:eq(' + i + ')').height($(this).height());
            });

            //// events
            this.$tableBody.on('scroll', function () {
                that.$rightfixedBody.find('table').css('top', -$(this).scrollTop());
            });
            this.$body.find('> tr[data-index]').off('hover').hover(function () {
                var index = $(this).data('index');
                that.$rightfixedBody.find('tr[data-index="' + index + '"]').addClass('hover');
            }, function () {
                var index = $(this).data('index');
                that.$rightfixedBody.find('tr[data-index="' + index + '"]').removeClass('hover');
            });
            this.$rightfixedBody.find('tr[data-index]').off('hover').hover(function () {
                var index = $(this).data('index');
                that.$body.find('tr[data-index="' + index + '"]').addClass('hover');
            }, function () {
                var index = $(this).data('index');
                that.$body.find('> tr[data-index="' + index + '"]').removeClass('hover');
            });
        }
        /* 页面大小变动适配*/
        var w = 0;
        for(var i=0; i<that.options.columns[0].length;i++ ){
            if(that.options.columns[0][i].width == undefined || that.options.columns[0][i].width == null || that.options.columns[0][i].width.length == 0){
                w += 120;
            }else{
                w += that.options.columns[0][i].width;
            }
        }
        if($("#"  + that.options.id).outerWidth() < w){
            $("#"  + that.options.id).width(w+"px");
        }
        if($("#"  + that.options.id).parent(".fixed-table-body").outerWidth() > w){
            $("#"  + that.options.id).width($("#"  + that.options.id).parent(".fixed-table-body").outerWidth()+"px");
        }
    };

})(jQuery);