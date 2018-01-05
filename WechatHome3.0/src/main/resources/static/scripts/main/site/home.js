(function (window, undefined) {
    var PopupAdd = Base.getClass('main.component.PopupAdd');
    var PopupMsg = Base.getClass('main.component.PopupMsg');
    var PopupAddPicReply = Base.getClass('main.component.PopupAddPicReply');
    Base.ready({
        initialize: fInitialize,
        binds: {
            'click #zu-top-add-question': fClickAdd,
            'click #zh-top-nav-count-wrap': fClickMsg,
            'click #zu-top-add-picReply': fClickAddPicReply
        }
    });

    function fInitialize() {
        var that = this;
    }

    function fClickAdd() {
        var that = this;
        PopupAdd.show({
            ok: function () {
            	var url = window.location.href;
                var arrUrl = url.split("/");
                var id = arrUrl[arrUrl.length-1];
                window.location.replace("/user/"+id);
            }
        });
    }

    function fClickAddPicReply() {
        var that = this;
        PopupAddPicReply.show({
            ok: function () {
            	var url = window.location.href;
                var arrUrl = url.split("/");
                var id = arrUrl[arrUrl.length-1];
                window.location.replace("/user/"+id);
            }
        });
    }
    
    function fClickMsg() {
            var that = this;
            PopupMsg.show({
                ok: function () {
                    window.location.replace("/msg/list");
                }
            });
        }

})(window);