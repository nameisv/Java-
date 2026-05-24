'use strict';

(function ($) {

    $(document).on('click', '.layout-builder .layout-builder-toggle', function () {
        $('.layout-builder').toggleClass('show');
    });

    $(window).on('load', function () {
        $('.layout-builder').removeClass('show');
    });

    var site_layout = localStorage.getItem('site_layout');
    site_layout = $.trim(site_layout);

    $('body').addClass(site_layout)
        .append(`
    <div class="layout-builder show">
        <div class="layout-builder-toggle shw">
            <i class="ti-settings"></i>
        </div>
        <div class="layout-builder-toggle hdn">
            <i class="ti-close"></i>
        </div>
        <div class="layout-builder-body">
            <h5>主题</h5>
            <div class="mb-3">
                <p>配色</p>
                <div>
                    <div class="custom-control custom-radio ` + (site_layout == 'gradient-theme-1' ? 'active' : '') + `">
                      <input type="radio" class="custom-control-input"  name="layout" id="gradient-theme-1" data-layout="gradient-theme-1">
                      <label class="custom-control-label" for="gradient-theme-1">Color 1</label>
                    </div>
                    <div class="custom-control custom-radio ` + (site_layout == 'gradient-theme-2' ? 'active' : '') + `">
                      <input type="radio" class="custom-control-input" name="layout" id="gradient-theme-2" data-layout="gradient-theme-2">
                      <label class="custom-control-label" for="gradient-theme-2">Color 2</label>
                    </div>
                    <div class="custom-control custom-radio ` + (site_layout == 'gradient-theme-3' ? 'active' : '') + `">
                      <input type="radio" class="custom-control-input" name="layout" id="gradient-theme-3" data-layout="gradient-theme-3">
                      <label class="custom-control-label" for="gradient-theme-4">Color 2</label>
                    </div>
                    <div class="custom-control custom-radio ` + (site_layout == 'gradient-theme-4' ? 'active' : '') + `">
                      <input type="radio" class="custom-control-input" name="layout" id="gradient-theme-4" data-layout="gradient-theme-4">
                      <label class="custom-control-label" for="gradient-theme-4">Color 4</label>
                    </div>
                </div>
            </div>
            <div class="clearfix"></div>
            <button id="btn-layout-builder-reset" class="btn btn-danger btn-uppercase">重置</button>
        </div>
    </div>`);

    $('.layout-builder .layout-builder-body .custom-radio').click(function () {
        var class_names = $(this).find('input[type="radio"]').data('layout');
        localStorage.setItem('site_layout', class_names);
        window.location.href = (window.location.href).replace('#', '');
    });

    $(document).on('click', '#btn-layout-builder-reset', function () {
        localStorage.removeItem('site_layout');
        localStorage.removeItem('site_layout_dark');

        window.location.href = (window.location.href).replace('#', '');
    });

    $(window).on('load', function () {
        if ($('body').hasClass('horizontal-side-menu') && $(window).width() > 768) {
            setTimeout(function () {
                $('.side-menu .side-menu-body > ul').append('<li><a href="#"><span>Other</span></a><ul></ul></li>');
            }, 100);
            $('.side-menu .side-menu-body > ul > li').each(function () {
                var index = $(this).index(),
                    $this = $(this);
                if (index > 7) {
                    setTimeout(function () {
                        $('.side-menu .side-menu-body > ul > li:last-child > ul').append($this.clone());
                        $this.addClass('d-none');
                    }, 100);
                }
            });
        }
    });

    $(document).on('click', '[data-attr="layout-builder-toggle"]', function () {
        $('.layout-builder').toggleClass('show');
        return false;
    });

    // 日期初始化
    $('.datePick').daterangepicker({
        autoUpdateInput: false,
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
            "format": "YYYY-MM-DD",
            applyLabel: '确定',
            cancelLabel: '取消',
            fromLabel: '起始时间',
            toLabel: '结束时间',
            daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月','九月', '十月', '十一月', '十二月'],
            firstDay: 6
        }
    });


    //网站顶部菜单切换
    var currentLocation = window.location.href;
    var as = $('.side-menu-body').find('a');
    $.each(as, function (i, a) {  //遍历二维数组
        var href = $(a).attr('href');
        if(href != '#'){
            if (currentLocation.indexOf(href) > 0) {
                console.log('currentLocation',currentLocation);
                $(a).addClass('active');
                if($(a).parent().parent().siblings().length > 0){
                    $(a).parent().parent().parent().addClass('open');
                }
            }
        }

    });


})(jQuery);


//获取url参数
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var index = query.indexOf("?");
    query = query.substr(index + 1, query.length);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return false;
}