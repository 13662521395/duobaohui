package com.shinc.duobaohui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shinc.duobaohui.adapter.MyFragmentPagerAdapter;
import com.shinc.duobaohui.base.BaseFragmentActivity;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.event.CouponsEnableNumEvent;
import com.shinc.duobaohui.fragment.CouponsCanUseFragment;
import com.shinc.duobaohui.fragment.CouponsOverdueFragment;
import com.shinc.duobaohui.fragment.CouponsUsedFragment;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @作者: efort
 * @日期: 15/12/17 - 18:42
 * @工程: duobaohui
 * @类简介:
 */
public class MyCouponsActivity extends BaseFragmentActivity {
    private FragmentActivity fragmentActivity;

    private ImageView imgBack;
    /*可用*/
    private RelativeLayout useAblell;
    private CustomTextView useAbleTextView;
    private View useAbleV;
    private CustomTextView useAbleNum;
    /*已用／*/
    private RelativeLayout unablell;
    private CustomTextView unableTextWord;
    private View unableV;
    private CustomTextView unableNum;
    /*
    过期
     */
    private RelativeLayout overduell;
    private CustomTextView overdueTextWord;
    private View overdueV;
    private CustomTextView overdueNum;

    private CouponsCanUseFragment couponsCanUseFragment;
    private CouponsUsedFragment couponsUsedFragment;
    private CouponsOverdueFragment couponsOverdueFragment;

    private List<Fragment> fragmentList;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupons);
        EventBus.getDefault().register(this);
        fragmentActivity = MyCouponsActivity.this;
        initView();
        initFragment();
        intiListener();
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.coupons_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        useAblell = (RelativeLayout) findViewById(R.id.coupons_able_layout);
        useAbleTextView = (CustomTextView) findViewById(R.id.coupons_able_tv);
        useAbleV = findViewById(R.id.coupons_v1);
        useAbleNum = (CustomTextView) findViewById(R.id.coupons_able_num);

        unablell = (RelativeLayout) findViewById(R.id.coupons_unable_layout);
        unableTextWord = (CustomTextView) findViewById(R.id.coupons_unable_tv);
        unableV = findViewById(R.id.coupons_v2);
        unableNum = (CustomTextView) findViewById(R.id.coupons_unable_num);

        overduell = (RelativeLayout) findViewById(R.id.coupons_overdue_layout);
        overdueTextWord = (CustomTextView) findViewById(R.id.coupons_overdue_tv);
        overdueV = findViewById(R.id.coupons_v3);
        overdueNum = (CustomTextView) findViewById(R.id.coupons_overdue_num);

        pager = (ViewPager) findViewById(R.id.coupons_layout_viewpage);
    }

    private void initFragment() {
        //可用
        couponsCanUseFragment = new CouponsCanUseFragment();
        //已用
        couponsUsedFragment = new CouponsUsedFragment();
        //过期
        couponsOverdueFragment = new CouponsOverdueFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(couponsCanUseFragment);
        fragmentList.add(couponsUsedFragment);
        fragmentList.add(couponsOverdueFragment);

        pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        pager.addOnPageChangeListener(new MyCouponsPagerChangeListener());
        pager.setCurrentItem(0);

        useAbleTextView.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
        useAbleV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
        useAbleNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));

        unableTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
        unableV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
        unableNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

        overdueTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
        overdueV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
        overdueNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

    }

    /**
     * 实时更改可用红包数
     *
     * @param couponsEvent
     */
    public void onEventMainThread(CouponsEnableNumEvent couponsEvent) {
        if (couponsEvent != null) {
            useAbleNum.setText("(" + couponsEvent.getCouponsNumBean().getCanUseNum() + ")");
            unableNum.setText("(" + couponsEvent.getCouponsNumBean().getUserdNum() + ")");
            overdueNum.setText("(" + couponsEvent.getCouponsNumBean().getOverdueNum() + ")");
        }
    }

    private void intiListener() {
        useAblell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
                useAbleTextView.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                useAbleV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                useAbleNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));

                unableTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                unableV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                unableNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

                overdueTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                overdueV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                overdueNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

            }
        });

        unablell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
                useAbleTextView.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                useAbleV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                useAbleNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

                unableTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                unableV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                unableNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));

                overdueTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                overdueV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                overdueNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
            }
        });

        overduell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
                useAbleTextView.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                useAbleV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                useAbleNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

                unableTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                unableV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                unableNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

                overdueTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                overdueV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                overdueNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
            }
        });
    }

    public class MyCouponsPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 0:
                    useAbleTextView.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    useAbleV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    useAbleNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));

                    unableTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    unableV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                    unableNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

                    overdueTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    overdueV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                    overdueNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

                    break;
                case 1:
                    useAbleTextView.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    useAbleV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                    useAbleNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

                    unableTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    unableV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    unableNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));

                    overdueTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    overdueV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                    overdueNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    break;
                case 2:
                    useAbleTextView.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    useAbleV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                    useAbleNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

                    unableTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    unableV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                    unableNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));

                    overdueTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    overdueV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    overdueNum.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
