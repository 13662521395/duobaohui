package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.RedEnevlopeDialogbean;

/**
 * 文件名：
 * Created by chaos on 16/1/18.
 * 功能：
 */
public class RedEnevlopeDialogEvent {
    private RedEnevlopeDialogbean redEnevlopeDialogbean;

    public RedEnevlopeDialogEvent(RedEnevlopeDialogbean redEnevlopeDialogbean) {
        this.redEnevlopeDialogbean = redEnevlopeDialogbean;
    }

    public RedEnevlopeDialogbean getRedEnevlopeDialogbean() {
        return redEnevlopeDialogbean;
    }
}
