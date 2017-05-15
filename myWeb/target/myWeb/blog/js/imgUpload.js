// var index = parent.layer.getFrameIndex(window.name);
var imgUpload = {
    open: function (options) {
        options = options || {};

        /* 弹窗左上角标题 */
        options.title = options.title || false;

        /* 基本层类型 */
        options.type = options.type || 2;

        /* 弹窗位置 */
        options.offset = options.offset || 'auto';

        /* 是否最大最小化 */
        options.maxmin = options.maxmin || false;

        /* 是否固定 */
        options.fix = options.fix || false;

        /* 弹窗动画 */
        options.shift = options.shift || 0;

        /* 弹窗大小 */
        options.area = options.area || ['610px', '500px'];

        /* 皮肤 */
        options.skin = options.skin || 'layui-layer-molv';

        /* 路由地址 */
        options.ly_url = options.ly_url || "blog/uploadImage.html";

        imgUpload.config(options, imgUpload.formatParams(options));
    },

    /* 配置 */
    config: function () {
        var _options = arguments[0];
        return parent.layer.open({
            title: false,
            type: _options.type,
            maxmin: _options.maxmin,
            fix: _options.fix,
            offset: _options.offset,
            shift: _options.shift,
            area: _options.area,
            skin: _options.skin,
            content: _options.ly_url + "?" + arguments[1],
             cancel: function (i, v) {
                  var index = parent.layer.getFrameIndex(window.name);
                  var annexList = parent.window[v.find('iframe')[0]['name']].objs;
                  //annexList.obj != null ? annexList.obj.replace('/data', '') : null
                  if (annexList.state == 1) {
                      _options.success(annexList.state, null);
                  } else {
                      _options.success(annexList.state, null);
                  }
             }
        })
    },

    /* 参数化 */
    formatParams: function () {
        var arr = [];
        for (var name in arguments[0].data) {
            arr.push(encodeURIComponent(name) + "=" + encodeURIComponent(arguments[0].data[name]));
        }
        arr.push(("v=" + Math.random()).replace(",", ""));
        return arr.join("&");
    }
}
