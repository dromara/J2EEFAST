/* ================================================
 * bootstrap-tooltip-custom-class.js v0.0.1
 *
 * Extend Bootstrap 3 Tooltip plugin by adding custom classes.
 * Custom classes can be added by using `customClass` parameter or via `data-custom-class` attribute.
 * There are 5 predefined custom classes in CSS: .tooltip-primary, .tooltip-success, .tooltip-info, .tooltip-warning, .tooltip-danger.
 * ============================================= */

(function ($) {

    if (typeof $.fn.tooltip.Constructor === 'undefined') {
        throw new Error('Bootstrap Tooltip must be included first!');
    }

    var Tooltip = $.fn.tooltip.Constructor;

    $.extend( Tooltip.DEFAULTS, {
        customClass: ''
    });

    var _show = Tooltip.prototype.show;

    Tooltip.prototype.show = function () {

        _show.apply(this,Array.prototype.slice.apply(arguments));

        if ( this.options.customClass ) {
            var $tip = this.tip();
            $tip.addClass(this.options.customClass);
        }

    };

})(window.jQuery);
