(function ($) {
    'use strict';

    $.fn.bootstrapTable.locales['zh-CN'] = {
        formatLoadingMessage: function () {
            return  $.i18n.prop('数据加载中，请稍后...');
        },
        formatRecordsPerPage: function (pageNumber) {
            return pageNumber + ' 条记录每页';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            return '第 ' + pageFrom + ' 到 ' + pageTo + ' 条，共  ' + totalRows + ' 条记录。';
        },
        formatSearch: function () {
            return  $.i18n.prop('查询');
        },
        formatNoMatches: function () {
            return  $.i18n.prop('没有找到匹配的记录');
        },
        formatPaginationSwitch: function () {
            return  $.i18n.prop('隐藏/显示分页');
        },
        formatRefresh: function () {
            return $.i18n.prop('刷新');
        },
        formatToggle: function () {
            return $.i18n.prop('切换');
        },
        formatColumns: function () {
            return $.i18n.prop('列');
        },
        formatExport: function () {
            return $.i18n.prop('导出数据');
        },
        formatClearFilters: function () {
            return $.i18n.prop('清空过滤');
        }
    };

    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

})(jQuery);
