package com.shinc.duobaohui.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.shihe.shincdatastatisticssdk.ShiNcAgent;
import com.umeng.analytics.MobclickAgent;

/**
 * 文件名：
 * Created by chaos on 15/7/8.
 * 功能：加入友盟统计部分代码；其中有一个抽象方法；就是或去当前fragment的名称；必须实现
 */
public abstract class BaseFragment extends Fragment {

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getFragmentName());
        ShiNcAgent.onFragmentResume(getActivity(), getFragmentName());
    }

    public void onPause() {
        super.onPause();
        // 保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息
        MobclickAgent.onPageEnd(getFragmentName());

        ShiNcAgent.onFragmentPause(getActivity(), getFragmentName());
    }

    public abstract String getFragmentName();

    public void print(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
