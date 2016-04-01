package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.DuoBaoRecordsBean;

/**
 * Created by liugaopo on 15/11/6.
 * <p>
 * 夺宝纪录－全部
 */
public class DuoBaoAllFragmentEvent {
    private DuoBaoRecordsBean duobaoBean;

    public DuoBaoRecordsBean getDuobaoBean() {
        return duobaoBean;
    }

    public DuoBaoAllFragmentEvent(DuoBaoRecordsBean duobaoBean) {
        this.duobaoBean = duobaoBean;
    }
}
