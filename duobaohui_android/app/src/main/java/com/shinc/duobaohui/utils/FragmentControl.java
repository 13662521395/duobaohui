package com.shinc.duobaohui.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.shinc.duobaohui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugaopo on 15/9/16.
 */
public class FragmentControl {

    public static List<Fragment> fragments = new ArrayList<>();

    public static void startFragment(BaseFragment fragment, FragmentManager manager, String fragmentName) {
        FragmentTransaction beginTransaction = manager.beginTransaction();
        if (!fragments.contains(fragment)) {
            fragments.add(fragment);
        }
        if (fragment != null && fragment.isAdded()) {
            beginTransaction.show(fragment);

        } else {
            fragments.remove(fragment);
            try {
                Class<?> forName = Class.forName("com.shinc.duobaohui.fragment." + fragmentName);
                fragment = (BaseFragment) forName.newInstance();

                fragments.add(fragment);
                beginTransaction.show(fragment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < fragments.size(); i++) {
            Fragment hidaFragment = fragments.get(i);
            if (hidaFragment != null && hidaFragment.isAdded()) {
                if (!hidaFragment.equals(fragment)) {
                    beginTransaction.hide(hidaFragment);
                }

            }
        }
        beginTransaction.commitAllowingStateLoss();
//        IndexViewImpl.currentFragment = fragment;
        Log.i("msg", fragments.size() + "ragments.size()");
    }

    public static void rmFragment(BaseFragment fragment, FragmentManager manager) {
        FragmentTransaction beginTransaction = manager.beginTransaction();
        if (fragments.contains(fragment)) {
            fragments.remove(fragment);
        }
        if (fragment != null && fragment.isAdded()) {
            beginTransaction.remove(fragment);

        }
        beginTransaction.commitAllowingStateLoss();
    }
}
