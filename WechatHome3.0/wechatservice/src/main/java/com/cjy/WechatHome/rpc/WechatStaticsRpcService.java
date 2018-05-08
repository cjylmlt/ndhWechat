package com.cjy.WechatHome.rpc;

import com.cjy.wechathome.data.web.WechatStatics;

import java.util.List;

public interface WechatStaticsRpcService {
    List<WechatStatics> selectFirstPage();
}
