
/**  
 * Y-PictureEditing
 * 基于Jcrop的图像裁剪上传插件
 * Version: 0.1
 * Compatible browser:IE7+ Chrome Firefox ..
 * Author:jyh <i@hirsinkai.com>
 * Date:2016-08-22
 *
 * Y-PictureEditing v0.1版本实现的功能
 * 1.从电脑中选取图片(支持实时预览)
 * 2.默认图片选取
 * 3.图片等比例裁剪
 * 4.HTML5 摄像（去掉了!因为HTML5对老版本的浏览器支持较低,此版本就不加上去了,考虑下版本使用flash实现。）
 * 
 * 0.1版本满足头像上传的基本需要 兼容IE8及以上所有浏览器
 * 因为开发时间较短,才四五天的时间,代码相对较乱,没怎么重构,下个版本会考虑优化。
 * 最初版本的功能虽然简单了一点,不过相对比较稳定,兼容性也支持绝大多数浏览器。
 * 图片裁剪因为低版本浏览器的原因,暂无法做到前端裁剪。
 * 网上通用的方法都是先上传图片至服务器,然后返回路径,再然后把路径和裁剪坐标传到后台,后台处理完成后在返回处理好的数据。
 * 个人感觉上面的方法比较麻烦,flash也不考虑了。大家都懂的(其实主要原因是因为..俺不会╥﹏╥...)
 * 这里采用的也是后台处理,不过只传一次数据到后台。相对减少了前后的访问次数。
 * 
 * 下个版本会考虑增加的功能
 * 1.对拍照功能的实现
 * 2.支持IE7
 *
 */

var objs = { state: 0, obj: {} };

var jcropApi;
var Ype_Event = {

    /* 初始化 */
    init: function () {
        Ype_Event.obj;
        Ype_Event.config();
        Ype_Event.but();
    },

    /* 变量 */
    obj: {
        pUrl: null,                                                 /* 图片展示地址 */
        para: { top: 0, left: 0, width: 0, height: 0, angle: 0 },   /* 图片截取参数 */
        wm: { wX: 395, wY: 340 },                                   /* 设置展示外边框尺寸 */
        xz: { pX: 140, pY: 140 },                                   /* 默认选择框尺寸 */
        type: 1,                                                    /* 1:从电脑上传 2:服务器文件 3:摄像拍照*/
        sj: { sX: 0, sY: 0 },
        sw: { Jw: null, Jh: null, Jw2: null, Jh2: null }

    },

    /* 按钮事件 */
    but: function () {

        $("#idLeft").click(function (e) {
            Ype_Event.imgRotate(-90);
            return false;
        });

        $("#idRight").click(function (e) {
            Ype_Event.imgRotate(90);
            return false;
        });

        $("#idBig").click(function (e) {
            Ype_Event.imgToSize(20);
            return false;
        });

        $("#idSmall").click(function (e) {
            Ype_Event.imgToSize(-20);
            return false;
        });

        $("#rootInPC").click(function (e) {
            Ype_Event.setInPC();
            return false;
        });

        $("#rootInFlash").click(function (e) {
            Ype_Event.setMedia();
        });

        $("#idSel").click(function () {
            Ype_Event.openBrowse();
            return false;
        });

        $("#cutSub").click(function () {
            Ype_Event.cutSub();
            return false;
        });

        $(".tab_span>span.headtab").click(function () {
            Ype_Event.headTab(this);
            return false;
        });

        $("#cancel").click(function () {
            Ype_Event.cancel();
            return false;
        });

    },

    /* 关闭窗体 */
    cancel: function () {
        /* 异常返回状态 */
        objs.state = 0;
        objs.obj = {};
        $(parent.window.document).find('[name=' + window.name + ']').closest('div.layui-layer ').children('span').children()[0].click()
    },

    /* Tab切换 */
    headTab: function (_this) {

        $(_this).siblings(".headtab").removeClass("cur");
        $(_this).addClass("cur");

        if ($(_this).attr('tabindex') == 1) {
            $('.custom').css("display", "block");
            $('.mapDepot').css("display", "none");

            Ype_Event.config();

            /* Ype_Event.obj.type = 1;表示为从电脑中选取的图片 */
            Ype_Event.obj.type = 1;

        } else if ($(_this).attr('tabindex') == 2) {
            $('.custom').css("display", "none");
            $('.mapDepot').css("display", "block");

            /* Ype_Event.obj.type = 2;表示为从服务器选取的图片 */
            Ype_Event.obj.type = 2;

            /* 加载全部图片 */
            Ype_Event.getDefMap(0);
        }
    },

    /* 裁剪上传 */
    cutSub: function () {

        var type = Ype_Event.obj.type;

        /* 1.从电脑中选取 */
        if (type == 1) {

            /* 判断file中是否存在图片 */
            var tmp = document.getElementById("imageUpload").value;
            var tmpStr = tmp.substring(tmp.lastIndexOf('.') + 1, tmp.length).toLowerCase();
            if (tmpStr == "") {
                layer.msg("请选择自己的图片哦(●'◡'●)~~", { icon: 5 });
                return false;
            }

            /* 判断选择的是否是图片 */
            var photoExt = tmp.substr(tmp.lastIndexOf(".")).toLowerCase();//获得文件后缀名
            if (!/\.jpg$|\.png$|\.jpeg/i.test(photoExt)) {
                alert('请上传格式为"jpg,jpeg,png"的照片!');
                return false;
            }

            var para = Ype_Event.obj.para;
            var xz = Ype_Event.obj.xz;
            var img = $('.pre-1>img');

            var sj = Ype_Event.obj.sj;
            bw = sj.sX / img.width();
            bh = sj.sY / img.height();

            para.left = 0 - Math.round(img.css('margin-left').replace('px', '') * bw);
            para.top = 0 - Math.round(img.css('margin-top').replace('px', '') * bh);
            para.width = Math.round(xz.pX * bw);
            para.height = Math.round(xz.pY * bw);

            $.ajaxFileUpload({
                url: "uploadImg.action",
                secureuri: false,
                fileElementId: "imageUpload",
                dataType: "json",
                cache: false,
                ifModified: true,
                beforeSend: function (xmlHttp) { xmlHttp.setRequestHeader("If-Modified-Since", "0"); xmlHttp.setRequestHeader("Cache-Control", "no-cache"); },
                success: function (data, status) {
                    if (status == "success") {

                        /* 正常返回状态 */
                        objs.state = 1;
                        objs.obj = data;
                        $(parent.window.document).find('[name=' + window.name + ']').closest('div.layui-layer ').children('span').children()[0].click()
                        parent.personalEvent.getUserData(userInfo);
                    }
                },
                error: function () {
                    /* 异常返回状态 */
                    objs.state = -1;
                    objs.obj = {};

                    $(parent.window.document).find('[name=' + window.name + ']').closest('div.layui-layer ').children('span').children()[0].click()
                }
            });
        }
            /* 2.摄像拍照 */
        else if (type == 3) {

        }
            /* 3.从图库中选取 */
        else if (type == 2) {
            var img = $(".portrait-li-cur");
            if (img.length < 1) {
                layer.msg("要先选择头像哦~(*^__^*)~", { icon: 5 });
                return false;
            }
            //my-img
            var url = $(img).children().find('img').attr('src');
            $.ajax({
    			type : 'post',
    			url : '${pageContext.request.contextPath}/updateImg.action?filePath='+url,
    			dataType : 'json',
    			success : function(data) {
					alert(data.message);
    				if(data.state == "0"){
    					 /* 正常返回状态 */
    		            objs.state = 1;
    		            objs.obj = url;
    		            $(parent.window.document).find('[name=' + window.name + ']').closest('div.layui-layer ').children('span').children()[0].click()
        				//parent.personalEvent.getUserData(parent.userInfo);
    				}
    			},
    			error:function(){
    				alert("上传失败!");
    			}
    		})
        }
    },

    /* 显示从电脑中选取 */
    setInPC: function () {

        /* Ype_Event.obj.type = 1;表示为从电脑中选取的图片 */
        Ype_Event.obj.type = 1;

        Ype_Event.config();

        $("#sPhoto").css("display", "block");
        $("#media").css("display", "none");
    },

    /* HTML5 GetUserMedia 摄像头*/
    setMedia: function () {

        /* Ype_Event.obj.type = 3;表示为摄像拍照选取的图片 */
        Ype_Event.obj.type = 3;

        Ype_Event.config();

        var video = document.querySelector('video');
        var canvas = document.querySelector('canvas');
        var ctx = canvas.getContext('2d');
        var img = document.querySelector('img');
        var localMediaStream = null;

        var snapshot = function () {
            if (localMediaStream) {
                ctx.drawImage(video, 0, 0);
                $("#sPhoto").css("display", "block");
                $("#media").css("display", "none");
                Ype_Event.obj.pUrl = canvas.toDataURL('image/png');
                Ype_Event.config();
            }
        };

        var sizeCanvas = function () {
            setTimeout(function () {
                canvas.width = video.videoWidth;
                canvas.height = video.videoHeight;
                img.width = video.videoWidth;
                img.height = video.videoHeight;
            }, 100);
        };

        var btnCapture = document.getElementById('cutOut');
        btnCapture.addEventListener('click', snapshot, false);

        navigator.webkitGetUserMedia(
            { video: true },
            function (stream) {
                video.src = window.URL.createObjectURL(stream);
                localMediaStream = stream;
                sizeCanvas();
            },
            function () {
                alert('your browser does not support getUserMedia');
            }
        );

        /* 显示摄像头 */
        $("#sPhoto").css("display", "none");
        $("#media").css("display", "block");
    },

    /* 从电脑中选取图片 */
    openBrowse: function () {

        $("#sPhoto").css("display", "block");
        $("#media").css("display", "none");

        var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;
        if (ie) {
            document.getElementById("imageUpload").click();
        } else {
            var a = document.createEvent("MouseEvents");//FF的处理 
            a.initEvent("click", true, true);
            document.getElementById("imageUpload").dispatchEvent(a);
        }
    },

    /* 设置图片 */
    setImg: function (t) {

        /* 设定提示信息 */
        var tip = "Expect jpg or png or jpeg!";
        var sw = Ype_Event.obj.sw;

        /* html5方案 */
        if (window.FileReader) {
            for (var i = 0, f; f = t.files[i]; i++) {
                var fr = new FileReader();
                fr.onload = function (e) {
                    var src = e.target.result;
                    if (!Ype_Event.validateImg(src)) {
                        alert(tip)
                    } else {
                        Ype_Event.showPrvImg(src);
                    }
                }
                fr.readAsDataURL(f);
            }
        } else { /* 降级处理 */

            if (!/\.jpg$|\.png$|\.jpeg/i.test(t.value)) {
                alert(tip);
            } else {
                Ype_Event.showPrvImg(t.value);
            }
        }

        /* 动态加载选择框 */
        jcropApi.enable();
        jcropApi.animateTo([sw.Jw, sw.Jh, sw.Jw2, sw.Jh2]);
    },

    /* 显示图片 */
    showPrvImg: function (src) {
        Ype_Event.obj.pUrl = src;
        Ype_Event.config();
    },

    /* 验证图片 */
    validateImg: function (data) {

        var filters = {
            "jpeg": "/9j/4",
            "gif": "R0lGOD",
            "png": "iVBORw"
        };

        var pos = data.indexOf(",") + 1;

        for (var e in filters) {
            if (data.indexOf(filters[e]) === pos) {
                return e;
            }
        }
        return null;
    },

    /* 获取地址 */
    getPath: function (obj) {

        //记录
        //alert(window.navigator.userAgent);
        if (obj) {
            if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
                obj.select(); return document.selection.createRange().text;
            }
            else if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
                if (obj.files) {
                    return obj.files.item(0).getAsDataURL();
                }
                return obj.value;
            }
            return obj.value;
        }
    },

    /* 图片旋转 */
    imgRotate: function (deg) {
        var img1 = $(".jcrop_w>img"),
       _data = parseInt($(".jc-demo-box").attr("data"));
        if ($.browser.version == 8.0 || $.browser.version == 7.0 || $.browser.version == 6.0) {
            var sin = Math.sin(Math.PI / 180 * (_data + deg)), cos = Math.cos(Math.PI / 180 * (_data + deg));
            var _filter = "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + "," + "M12=" + (-sin)
                + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')";
            img1.css({
                filter: _filter
            });
            $('.pre-1 img,.pre-2 img,.pre-3 img').css({
                filter: _filter
            });

        } else {
            var _deg = deg + _data;
            var _val = "rotate(" + _deg + "deg)";
            img1.css({
                "-webkit-transform": _val,
                "-moz-transform": _val,
                "-ms-transform": _val,
                "-o-transform": _val,
                "transform": _val
            });
            $('.pre-1 img,.pre-2 img,.pre-3 img').css({
                "-webkit-transform": _val,
                "-moz-transform": _val,
                "-ms-transform": _val,
                "-o-transform": _val,
                "transform": _val
            });
        }

        var wm = Ype_Event.obj.wm;
        var xz = Ype_Event.obj.xz;

        var fiw = $('.jcrop_w>img').width(),
                fih = $('.jcrop_w>img').height(),
                ow = Math.floor((wm.wX - fiw) / 2),
                oh = Math.floor((wm.wY - fih) / 2),
                cx = $("#small").position().left,
                cy = $("#small").position().top,
                rx = (xz.pX) / $("#small").width(),
                ry = (xz.pY) / $("#small").height(),
                rx1 = (xz.pX / 2) / $("#small").width(),
                ry1 = (xz.pY / 2) / $("#small").height(),
                rx2 = (xz.pX / 4) / $("#small").width(),
                ry2 = (xz.pY / 4) / $("#small").height();



        if ($.browser.version == 8.0 || $.browser.version == 7.0 || $.browser.version == 6.0) {
            Ype_Event.pre_img2($('.pre-1 img'), rx, fih, ry, fiw, cx, cy, ow, oh);
            Ype_Event.pre_img2($('.pre-2 img'), rx1, fih, ry1, fiw, cx, cy, ow, oh);
            Ype_Event.pre_img2($('.pre-3 img'), rx2, fih, ry2, fiw, cx, cy, ow, oh);
        } else {
            Ype_Event.pre_img2($('.pre-1 img'), rx, fiw, ry, fih, cx, cy, ow, oh);
            Ype_Event.pre_img2($('.pre-2 img'), rx1, fiw, ry1, fih, cx, cy, ow, oh);
            Ype_Event.pre_img2($('.pre-3 img'), rx2, fiw, ry2, fih, cx, cy, ow, oh);
        }

        $(".jcrop_w img").css({
            left: ow,
            top: oh
        });

        if (deg > 0) {
            if (_data == 270) {
                _data = 0;
            } else {
                _data = _data + 90;
            }
        } else {
            if (_data == 0) {
                _data = 270;
            } else {
                _data = _data - 90;
            }
        }
        $("#d").val(_data);
        $(".jc-demo-box").attr("data", _data);

        /* 顺时针旋转 */
        Ype_Event.obj.para.angle = _data;
    },

    /* 图片缩放 */
    imgToSize: function (size) {

        var iw = $('.jcrop_w>img').width(),
      ih = $('.jcrop_w>img').height(),
      _data = $(".jc-demo-box").attr("data"),
      _w = Math.round(iw + size),
      _h = Math.round(((iw + size) * ih) / iw);

        if (($.browser.version == 8.0 || $.browser.version == 7.0 || $.browser.version == 6.0) && (_data == 90 || _data == 270)) {
            $('.jcrop_w>img').width(_h).height(_w);
        } else {
            $('.jcrop_w>img').width(_w).height(_h);
        }

        var wm = Ype_Event.obj.wm;
        var xz = Ype_Event.obj.xz;

        var fiw = $('.jcrop_w>img').width(),
                fih = $('.jcrop_w>img').height(),
                ow = (wm.wX - fiw) / 2,
                oh = (wm.wY - fih) / 2,
                cx = $("#small").position().left,
                cy = $("#small").position().top,
                rx = (xz.pX) / $("#small").width(),
                ry = (xz.pY) / $("#small").height(),
                rx1 = (xz.pX / 2) / $("#small").width(),
                ry1 = (xz.pY / 2) / $("#small").height(),
                rx2 = (xz.pX / 4) / $("#small").width(),
                ry2 = (xz.pY / 4) / $("#small").height();

        if (($.browser.version == 8.0 || $.browser.version == 7.0 || $.browser.version == 6.0) && (_data == 90 || _data == 270)) {
            Ype_Event.pre_img2($('.pre-1 img'), rx, fih, ry, fiw, cx, cy, ow, oh);
            Ype_Event.pre_img2($('.pre-2 img'), rx1, fih, ry1, fiw, cx, cy, ow, oh);
            Ype_Event.pre_img2($('.pre-3 img'), rx2, fih, ry2, fiw, cx, cy, ow, oh);
        } else {
            Ype_Event.pre_img2($('.pre-1 img'), rx, fiw, ry, fih, cx, cy, ow, oh);
            Ype_Event.pre_img2($('.pre-2 img'), rx1, fiw, ry1, fih, cx, cy, ow, oh);
            Ype_Event.pre_img2($('.pre-3 img'), rx2, fiw, ry2, fih, cx, cy, ow, oh);
        }
        $(".jcrop_w img").css({
            left: ow,
            top: oh
        });
    },

    /* 缩放处理 */
    pre_img2: function (obj, rx, iw, ry, ih, cx, cy, ow, oh) {
        obj.css({
            width: (rx * iw).toFixed(2) + 'px',
            height: (ry * ih).toFixed(2) + 'px'
        });

        if (cy >= oh && cx >= ow) {
            obj.css({
                marginLeft: '-' + (rx * (cx - ow)).toFixed(2) + 'px',
                marginTop: '-' + (ry * (cy - oh)).toFixed(2) + 'px'
            });
        } else if (cy <= oh && cx >= ow) {
            obj.css({
                marginLeft: "-" + (rx * (cx - ow)).toFixed(2) + 'px',
                marginTop: (ry * (oh - cy)).toFixed(2) + 'px'
            });
        } else if (cy >= oh && cx <= ow) {
            obj.css({
                marginLeft: (rx * (ow - cx)).toFixed(2) + 'px',
                marginTop: '-' + (ry * (cy - oh)).toFixed(2) + 'px'
            });
        } else if (cy <= oh && cx <= ow) {
            obj.css({
                marginLeft: (rx * (ow - cx)).toFixed(2) + 'px',
                marginTop: (ry * (oh - cy)).toFixed(2) + 'px'
            });
        }
    },

    /* 默认位置 */
    cutImage: function (obj) {
        var wm = Ype_Event.obj.wm;
        var w = wm.wX,
                h = wm.wY,
                iw = obj.width(),
                ih = obj.height()
        if (iw > w || ih > h) {
            if (iw / ih > w / h) {
                obj.css({
                    width: w,
                    height: w * ih / iw,
                    top: (h - (w * ih / iw)) / 2,
                    left: 0
                });
            } else {
                obj.css({
                    height: h,
                    width: h * iw / ih,
                    top: 0,
                    left: (w - (h * iw / ih)) / 2
                });
            }
        } else {
            obj.css({
                left: (w - iw) / 2,
                top: (h - ih) / 2
            });
        }
    },

    /* 显示预览 */
    showPreview: function (c) {

        if (Ype_Event.obj.pUrl != null) {

            var wm = Ype_Event.obj.wm;
            var xz = Ype_Event.obj.xz;

            /* 主图片大小 */
            var iw = $('.jcrop_w>img').width(),
            ih = $('.jcrop_w>img').height(),

            /* 生成计算右侧预览框 */
            ow = (wm.wX - iw) / 2,
            oh = (wm.wY - ih) / 2,
            rx = (xz.pX) / c.w,
            ry = (xz.pY) / c.h,
            rx1 = (xz.pX / 2) / c.w,
            ry1 = (xz.pY / 2) / c.h,
            rx2 = (xz.pX / 4) / c.w,
            ry2 = (xz.pY / 4) / c.h,
            _data = $(".jc-demo-box").attr("data");

            /* iE 6.0 7.0 8.0 
               注:jQuery v1.9.0 以上版本不支持$.browser 和 $.browser.version 代替请使用 $.support
               图片缩放右侧预览   
            */
            if (($.support.version == 8.0 || $.support.version == 7.0 || $.support.version == 6.0) && (_data == 90 || _data == 270)) {
                Ype_Event.pre_img2($('.pre-1 img'), rx, ih, ry, iw, c.x, c.y, ow, oh);
                Ype_Event.pre_img2($('.pre-2 img'), rx1, ih, ry1, iw, c.x, c.y, ow, oh);
                Ype_Event.pre_img2($('.pre-3 img'), rx2, ih, ry2, iw, c.x, c.y, ow, oh);
            } else {
                Ype_Event.pre_img2($('.pre-1 img'), rx, iw, ry, ih, c.x, c.y, ow, oh);
                Ype_Event.pre_img2($('.pre-2 img'), rx1, iw, ry1, ih, c.x, c.y, ow, oh);
                Ype_Event.pre_img2($('.pre-3 img'), rx2, iw, ry2, ih, c.x, c.y, ow, oh);
            }
        }
    },

    /* 加载图包 */
    getDefMap: function (porttype) {
        var emp = eval(imgdata);
            var gettpl1 = $('#headImages').html();
            laytpl(gettpl1).render(emp, function (html) { $('#headImageBox').html(html); });

            /* 绑定选中图片事件 */
            $(".portrait-li").click(function () {
                Ype_Event.bindPortrait(this);
            });
        // $.ajax({
        //     type: "get",
        //     url: "/url.html",
        //     data: { porttype: porttype },
        //     success: function (data) {
                
        //     }
        // })

    },

    /* 选中图片操作 */
    bindPortrait: function () {
        $(arguments[0]).siblings(".portrait-li").removeClass("portrait-li-cur");
        $(arguments[0]).addClass("portrait-li-cur");

        var url = $(arguments[0]).children().find('img').attr('src');

        $('.preview-container .y-picture-url').attr("src", url);

        $('.preview-container .y-picture-url').css({
            "width": function () { return $(this).parent().width() },
            "height": function () { return $(this).parent().width() },
            "margin-left": "0px",
            "margin-top": "0px"
        });
    },

    /* 图库配置 */
    mapConfig: function () {

        /*选项卡切换*/
        $(".piclist-tab-a").click(function () {
            $(this).siblings(".piclist-tab-a").removeClass("piclist-tab-cur");
            $(this).addClass("piclist-tab-cur");

            /* 根据切换加载图包 */
            Ype_Event.getDefMap($(this).attr('porttype'));
        });
    },


    /**  
     *
     * @desc 图片预加载
     * @author yz
     * @param maxWidth 显示框的最大宽度
     * @param maxHeight 显示框的最大高度
     * @param objImg 图片对象
     *
     * usage: 
     * var _this=document.getElementById('target'); 
     * AutoResizeImage(340, 340, _this); 
     */
    loadImage: function (maxWidth, maxHeight, objImg, callback) {
        var img = new Image();
        img.onload = function () {
            img.onload = null;//gif图片在ie下会循环请求
            callback(img);
        }
        img.src = objImg.src;
        var hRatio, wRatio, Ratio = 1;
        var w = img.width;
        var h = img.height;
        wRatio = maxWidth / w;
        hRatio = maxHeight / h;
        if (maxWidth == 0 && maxHeight == 0) {
            Ratio = 1;
        } else if (maxWidth == 0) {//
            if (hRatio < 1) Ratio = hRatio;
        } else if (maxHeight == 0) {
            if (wRatio < 1) Ratio = wRatio;
        } else if (wRatio < 1 || hRatio < 1) {
            Ratio = (wRatio <= hRatio ? wRatio : hRatio);
        }
        if (Ratio < 1) {
            w = w * Ratio;
            h = h * Ratio;
        }
        objImg.height = h
        objImg.width = w
        callback(objImg)
    },

    /* 设置默认头像 */
    setDefImg: function () {

        var yUrl = getQueryString('yUrl');

        if (yUrl != null && yUrl != undefined) {

            $('.preview-container .y-picture-url').attr("src", yUrl);

            $('.preview-container .y-picture-url').css({
                "width": function () { return $(this).parent().width() },
                "height": function () { return $(this).parent().width() },
                "margin-left": "0px",
                "margin-top": "0px"
            });
        } else {

            $('.preview-container .y-picture-url').attr("src", "images/list_mr_pic.png");

            $('.preview-container .y-picture-url').css({
                "width": function () { return $(this).parent().width() },
                "height": function () { return $(this).parent().width() },
                "margin-left": "0px",
                "margin-top": "0px"
            });
        }

    },

    /* 配置 */
    config: function () {

        /*图库配置 */
        Ype_Event.mapConfig();

        var xz = Ype_Event.obj.xz;
        var pUrl = Ype_Event.obj.pUrl;
        var wm = Ype_Event.obj.wm;
        var sj = Ype_Event.obj.sj;
        var sw = Ype_Event.obj.sw;

        /* 设置预览框大小 */
        $('.pre-1').css({ 'width': xz.pX, 'height': xz.pY });
        $('.pre-2').css({ 'width': xz.pX / 2, 'height': xz.pY / 2 });
        $('.pre-3').css({ 'width': xz.pX / 4, 'height': xz.pY / 4 });
        $('.val-1').html(xz.pX + "x" + xz.pY);
        $('.val-2').html(xz.pX / 2 + "x" + xz.pY / 2);
        $('.val-3').html(xz.pX / 4 + "x" + xz.pY / 4);

        var oriUrl = null;

        if (pUrl == null) {

            oriUrl = "frame/PictureEditing/images/A7.png";

            /* 指示图片加载 */
            $('#target>img').attr("src", oriUrl);

            /* 头像预加载 */
            Ype_Event.setDefImg();

        } else {

            oriUrl = pUrl;

            /* 设置默认图片 */
            $('.y-picture-url').attr("src", pUrl);
        }


        /* 获取图片原始尺寸 */
        imgReady(oriUrl, function () {

            /* 获取上传的图片 */
            var _this = this;

            sj.sX = _this.width;
            sj.sY = _this.height;

            /* 设置等比例缩小后的尺寸 */
            var img = document.getElementById('Zpic');
            //img.onload = AutoResizeImage(wm.wX, wm.wY, _this, function (width, height) {
            //    img.width = width;
            //    img.height = height;
            //});

            /* 图片预加载 */
            Ype_Event.loadImage(wm.wX, wm.wY, _this, function () {
                img.src = _this.src;
                img.width = _this.width;
                img.height = _this.height;
            });

            /* 默认图像居中显示 按比例缩小(获取待上传图片大小)*/
            Ype_Event.cutImage($(".jcrop_w>img"));

            /* 参数 */
            sw.Jw = ($("#target").width() - xz.pX) / 2,
            sw.Jh = ($("#target").height() - xz.pY) / 2;
            sw.Jw2 = ($("#target").width() - xz.pX) / 2 + xz.pX;
            sw.Jh2 = ($("#target").height() - xz.pY) / 2 + xz.pY;

            /* 图片裁剪配置 */
            $('#target').Jcrop({
                //setSelect: [_Jw, _Jh, _Jw2, _Jh2],  /* 创建选框，参数格式为：[x, y, x2, y2] */
                onChange: Ype_Event.showPreview,    /* 选框改变时的事件 */
                onSelect: Ype_Event.showPreview,    /* 选框选定时的事件 */
                aspectRatio: xz.pX / xz.pY,         /* 选框宽高比。说明：width/height */
                bgOpacity: 0.5,                     /* 背景透明度 */
                bgColor: "white",                   /* 背景颜色。颜色关键字、HEX、RGB 均可。 */
                bgFade: true,                       /* 使用背景过渡效果 */
                keySupport: true                    /* 支持键盘控制。按键列表：上下左右（移动选框）、Esc（取消选框） */
            }, function () {
                jcropApi = this;
                /* 禁用/启用 Jcrop */
                if (pUrl == null) {
                    jcropApi.disable();
                } else {
                    jcropApi.enable();
                }
            });
        });

        /* 判断浏览器是否支持HTML5 */
        if (navigator.webkitGetUserMedia) {
            $("#rootInFlash").css("display", "block");
        }

        
    }
}

$(function () {

    /* 加载主程序 */
    Ype_Event.init();
});

/**  
 *
 * @desc 等比例缩小图片
 * @author jyh
 * @param maxWidth 显示框的最大宽度
 * @param maxHeight 显示框的最大高度
 * @param objImg 图片对象
 *
 * usage: 
 * var _this=document.getElementById('target'); 
 * AutoResizeImage(340, 340, _this); 
 */
function AutoResizeImage(maxWidth, maxHeight, objImg, callback) {

    var img = new Image();
    img.src = objImg.src;
    var hRatio, wRatio, Ratio = 1;
    var w = img.width;
    var h = img.height;
    wRatio = maxWidth / w;
    hRatio = maxHeight / h;
    if (maxWidth == 0 && maxHeight == 0) {
        Ratio = 1;
    } else if (maxWidth == 0) {
        if (hRatio < 1) Ratio = hRatio;
    } else if (maxHeight == 0) {
        if (wRatio < 1) Ratio = wRatio;
    } else if (wRatio < 1 || hRatio < 1) {
        Ratio = (wRatio <= hRatio ? wRatio : hRatio);
    }
    if (Ratio < 1) {
        w = w * Ratio;
        h = h * Ratio;
    }
    objImg.height = h
    objImg.width = w

    if (img.complete) {
        callback(img.width, img.height);
    } else {
        img.onload = function () {
            callback(img.width, img.height);
            img.onload = null;
        };
    };
}


/**  
 * @desc:动态获取图片的宽度和高度的像素值
 * @author:jyh
 * @param url 图片的url 
 * @param Callback 回调函数，可以获取图片尺寸。 
 *  
 * usage: 
 * var url = "http://imglf1.ph.126.net/DImm3T3FF_8VEJW807brfg==/2831638265727151933.jpg"; 
 * imgReady(url, function () {alert('size ready: width=' + this.width + '; height=' + this.height);}); 
 */
var imgReady = (function () {
    var list = [], intervalId = null,

	/* 用来执行队列 */
	tick = function () {
	    var i = 0;
	    for (; i < list.length; i++) {
	        list[i].end ? list.splice(i--, 1) : list[i]();
	    };
	    !list.length && stop();
	},

	/* 停止所有定时器队列 */
	stop = function () {
	    clearInterval(intervalId);
	    intervalId = null;
	};

    return function (url, ready, load, error) {
        var onready, width, height, newWidth, newHeight,
		img = new Image();
        img.src = url;
        /*  如果图片被缓存，则直接返回缓存数据 */
        if (img.complete) {
            ready.call(img);
            load && load.call(img);
            return;
        };
        width = img.width;
        height = img.height;

        /*  加载错误后的事件 */
        img.onerror = function () {
            error && error.call(img);
            onready.end = true;
            img = img.onload = img.onerror = null;
        };

        /* 图片尺寸就绪 */
        onready = function () {
            newWidth = img.width;
            newHeight = img.height;
            if (newWidth !== width || newHeight !== height || newWidth * newHeight > 1024) {
                ready.call(img);
                onready.end = true;
            };
        };

        onready();

        /* 完全加载完毕的事件 */
        img.onload = function () {

            /* onload在定时器时间差范围内可能比onready快 */
            /* 这里进行检查并保证onready优先执行 */
            !onready.end && onready();
            load && load.call(img);

            /*  IE gif动画会循环执行onload，置空onload即可 */
            img = img.onload = img.onerror = null;
        };

        /* 加入队列中定期执行 */
        if (!onready.end) {
            list.push(onready);
            /* 无论何时只允许出现一个定时器，减少浏览器性能损耗 */
            if (intervalId === null) intervalId = setInterval(tick, 40);
        };
    };
})();


/*
 * 将文件转成base64 
 * @param obj 
 * @param callback 
 * @returns {boolean} 
 */
var fileUpload = function (obj, callback) {
    var file = obj;
    //判断类型是不是图片  
    if (!/image\/\w+/.test(file.type)) {
        alert("请确保文件为图像类型");
        return false;
    }

    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function (e) {
        var img = new Image,
        width = 95,    //图片resize宽度  
        quality = 1.0,  //图像质量  
        canvas = document.createElement("canvas"),
        drawer = canvas.getContext("2d");
        img.src = this.result;
        canvas.width = width;
        canvas.height = width * (img.height / img.width);
        drawer.drawImage(img, 0, 0, canvas.width, canvas.height);
        img.src = canvas.toDataURL();
        var image_base64 = img.src.replace("data:image/png;base64,", "");
        // 调用回调  
        callback && callback(image_base64);
    }

}


function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
