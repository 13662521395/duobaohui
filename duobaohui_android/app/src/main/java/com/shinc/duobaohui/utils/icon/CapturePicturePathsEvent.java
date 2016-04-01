package com.shinc.duobaohui.utils.icon;

/**
 * Created by vince on 15/9/15.
 */
public class CapturePicturePathsEvent {
    CapturePicturePathsBean capturePicturePathsBean;

    public CapturePicturePathsEvent(CapturePicturePathsBean capturePicturePathsBean) {
        this.capturePicturePathsBean = capturePicturePathsBean;
    }

    public CapturePicturePathsBean getCapturePicturePathsBean() {
        return capturePicturePathsBean;
    }
}
