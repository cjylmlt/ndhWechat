/**
var oPopupAdd = new PopupAdd({
    data: 初始数据
    ok: Function, 发布成功后的回调
});
 */
(function (window) {
    var PopupAddPicReply = Base.createClass('main.component.PopupAddPicReply');
    var Popup = Base.getClass('main.component.Popup');
    var Component = Base.getClass('main.component.Component');
    var Util = Base.getClass('main.base.Util');

    Base.mix(PopupAddPicReply, Component, {
        _tpl: [
            '<div class="zh-add-question-form">',
                '<div class="zg-section-big clearfix">',
                    '<div class="zg-form-text-input add-question-title-form" style="position: relative;">',
                        '<input type="text" class="js-key zg-editor-input zu-seamless-input-origin-element" placeholder="写下你的关键词" style="height:22px;min-height:auto;"></textarea>',
                    '</div>',
                '</div>',
                '<div class="zg-section-big clearfix">',
                '<div class="zg-form-text-input add-question-title-form" style="position: relative;">',
                    '<input type="text" class="js-value zg-editor-input zu-seamless-input-origin-element" placeholder="写下你的封面标题" style="height:22px;min-height:auto;"></textarea>',
                '</div>',
                '</div>',
                '<div class="zg-section-big clearfix">',
                '<div class="zg-form-text-input add-question-title-form" style="position: relative;">',
                    '<input type="text" class="js-picUrl zg-editor-input zu-seamless-input-origin-element" placeholder="写下你的封面图片地址" style="height:22px;min-height:auto;"></textarea>',
                '</div>',
                '</div>',
                '<div class="zg-section-big clearfix">',
                '<div class="zg-form-text-input add-question-title-form" style="position: relative;">',
                    '<input type="text" class="js-url zg-editor-input zu-seamless-input-origin-element" placeholder="写下你的跳转地址" style="height:22px;min-height:auto;"></textarea>',
                '</div>',
                '</div>',
            '</div>'].join(''),
        listeners: [{
            name: 'render',
            type: 'custom',
            handler: function () {
                var that = this;
                var oConf = that.rawConfig;
                var oEl = that.getEl();
                that.key = oEl.find('.js-key');
                that.value = oEl.find('.js-value');
                that.picUrl = oEl.find('.js-picUrl');
                that.url = oEl.find('.js-url');
                // 还原值
                oConf.data && that.val(oConf.data);
            }
        }],
        show: fStaticShow
    }, {
        initialize: fInitialize,
        val: fVal
    });

    function fStaticShow(oConf) {
        var that = this;
        var oAdd = new PopupAddPicReply(oConf);
        var bSubmit = false;
        var oPopup = new Popup({
            width: 540,
            title: '关键词添加',
            okTxt: '发布',
            content: oAdd.html(),
            ok: function () {
                var that = this;
                var oData = oAdd.val();
                var url = window.location.href;
                var arrUrl = url.split("/");
                oData.id = arrUrl[arrUrl.length-1];
                if (!oData.key) {
                    that.error('请填关键词');
                    return true;
                }
                if (!oData.value) {
                    that.error('请填封面标题');
                    return true;
                }
                if (!oData.picUrl) {
                    that.error('请填图片地址');
                    return true;
                }
                if (!oData.url) {
                    that.error('请填跳转地址');
                    return true;
                }
                // 避免重复提交
                if (bSubmit) {
                    return true;
                }
                bSubmit = true;
                // 提交内容
                $.ajax({
                    url: '/definedReply/addPicReply',
                    type: 'post',
                    data: oData,
                    dataType: 'json'
                }).done(function (oResult) {
                    // 未登陆，跳转到登陆页面
                    if (oResult.code === 999) {
                        window.location.href = '/reglogin?next=' + window.encodeURIComponent(window.location.href);
                    } else {
                        oConf.ok && oConf.ok.call(that);
                        oAdd.emit('ok');
                    }
                }).fail(function () {
                    alert('出现错误，请重试');
                }).always(function () {
                    bSubmit = false;
                });
                // 先不关闭
                return true;
            },
            listeners: {
                destroy: function () {
                    oAdd.destroy();
                }
            }
        });
        oAdd._popup = oPopup;
        Component.setEvents();
    }

    function fInitialize(oConf) {
        var that = this;
        delete oConf.renderTo;
        PopupAddPicReply.superClass.initialize.apply(that, arguments);
    }

    function fVal(oData) {
        var that = this;
        if (arguments.length === 0) {
            return {
                key: $.trim(that.key.val()),
                value: $.trim(that.value.val()),
                picUrl: $.trim(that.picUrl.val()),
                url: $.trim(that.url.val())
            };
        } else {
            oData = oData || {};
            that.titleIpt.val($.tirm(oData.title));
            that.contentIpt.val($.trim(oData.content));
        }
    }

})(window);