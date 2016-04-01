package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.WinnerRecrodNumBean;

/**
 * Created by liugaopo on 15/10/6.
 */
public class WinnerRecrodNumEvent {
    private WinnerRecrodNumBean winnerRecrodNumBean;

    public WinnerRecrodNumBean getWinnerRecrodNumBean() {
        return winnerRecrodNumBean;
    }

    public WinnerRecrodNumEvent(WinnerRecrodNumBean winnerRecrodNumBean) {
        this.winnerRecrodNumBean = winnerRecrodNumBean;
    }
}
