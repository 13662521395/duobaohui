package com.shinc.duobaohui.event;

import com.tencent.mm.sdk.modelbase.BaseResp;

/**
 * Created by liugaopo on 15/11/19.
 */
public class BaseRespPayEvent {
    private BaseResp baseResp;

    public BaseRespPayEvent(BaseResp baseResp) {
        this.baseResp = baseResp;
    }

    public BaseResp getBaseResp() {
        return baseResp;
    }
}
