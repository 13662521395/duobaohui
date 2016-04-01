package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.DuoBaoRecordsBean;

/**
 * Created by liugaopo on 15/11/6. *
 * 夺宝纪录－完成
 */
public class DuoBaoYetFragmentEvent {

    private DuoBaoRecordsBean duobaoBean;

    public DuoBaoRecordsBean getDuobaoBean() {
        return duobaoBean;
    }

    public DuoBaoYetFragmentEvent(DuoBaoRecordsBean duobaoBean) {
        this.duobaoBean = duobaoBean;
    }
}
