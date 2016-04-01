package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.LotteryTimesListbyPeriodidBean;

/**
 * Created by liugaopo on 15/10/8.
 */
public class DuoBaoDetailEvent {
    private LotteryTimesListbyPeriodidBean lotteryTimesListbyPeriodidBean;

    public LotteryTimesListbyPeriodidBean getLotteryTimesListbyPeriodidBean() {
        return lotteryTimesListbyPeriodidBean;
    }

    public DuoBaoDetailEvent(LotteryTimesListbyPeriodidBean lotteryTimesListbyPeriodidBean) {
        this.lotteryTimesListbyPeriodidBean = lotteryTimesListbyPeriodidBean;
    }
}
