package com.shinc.duobaohui.utils.icon;

import android.content.Intent;

/**
 * Created by liugaopo on 15/8/21.
 */
public interface DynamicPostInterface {
    void onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void setVisible(onViewVisible onViewVisible);

    void setGonepostremain(boolean flag);

    interface onViewVisible {
        void onViewVisible(int isShow);
    }


}
