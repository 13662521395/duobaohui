package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.DuoBaoRecordsBean;

/**
 * Created by liugaopo on 15/11/6.
 *  夺宝纪录－进行时
 */
public class DuoBaoWaitFragmentEvent {
    private DuoBaoRecordsBean duobaoBean;

    public DuoBaoRecordsBean getDuobaoBean() {
        return duobaoBean;
    }

    public DuoBaoWaitFragmentEvent(DuoBaoRecordsBean duobaoBean) {
        this.duobaoBean = duobaoBean;
    }
}
