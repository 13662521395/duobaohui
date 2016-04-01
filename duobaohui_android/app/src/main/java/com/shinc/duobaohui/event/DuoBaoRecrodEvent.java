package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.DuoBaoRecordsBean;

/**
 * Created by liugaopo on 15/10/7.
 *    夺宝纪录 Event
 */
public class DuoBaoRecrodEvent {
    private DuoBaoRecordsBean duobaoBean;

    public DuoBaoRecordsBean getDuobaoBean() {
        return duobaoBean;
    }

    public DuoBaoRecrodEvent(DuoBaoRecordsBean duobaoBean) {
        this.duobaoBean = duobaoBean;
    }
}
