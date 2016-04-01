package com.shinc.duobaohui.customview.share;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;

/**
 * Created by liugaopo on 15/11/4.
 */
public interface ShareOncilckListener {

    void onComplete(SHARE_MEDIA platform, int stCode, SocializeEntity entity);
}
