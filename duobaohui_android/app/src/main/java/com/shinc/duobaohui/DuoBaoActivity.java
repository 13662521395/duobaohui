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
import com.shinc.duobaohui.event.DuoBaoNumberEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.fragment.DuoBaoAllFragment;
import com.shinc.duobaohui.fragment.DuoBaoWaitFragment;
import com.shinc.duobaohui.fragment.DuoBaoYetFragment;
import com.shinc.duobaohui.model.DuoBaoRecrodModelInterface;
import com.shinc.duobaohui.model.impl.DuoBaoRecrodModelImpl;
import com.shinc.duobaohui.utils.CodeVerifyUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/11/4.
 * 夺宝纪录 （dev）
 */
public class DuoBaoActivity extends BaseFragmentActivity {
    private FragmentActivity fragmentActivity;

    private ImageView imgBack;
    /*全部*/
    private RelativeLayout allLayout;
    private CustomTextView allTextWord;
    private View allV;
    /*待揭晓*/
    private RelativeLayout waitLayout;
    private CustomTextView waitTextWord;
    private View waitV;
    /*已揭晓*/
    private RelativeLayout yetLayout;
    private CustomTextView yetTextWord;
    private View yetV;

    private DuoBaoAllFragment allFragment;
    private DuoBaoWaitFragment waitFragment;
    private DuoBaoYetFragment yetFragment;

    private List<Fragment> fragmentList;

    private DuoBaoRecrodModelInterface anInterface;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duobao_layout);
        EventBus.getDefault().register(this);
        fragmentActivity = DuoBaoActivity.this;
        initView();
        initFragment();
        intiListener();
        initModel();
    }

    private void initModel() {
        anInterface = new DuoBaoRecrodModelImpl(fragmentActivity);
        anInterface.getDuoBaoTadNumber();
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.duobao_back);
        allLayout = (RelativeLayout) findViewById(R.id.duobao_layout_allLayout);
        allTextWord = (CustomTextView) findViewById(R.id.duobao_layout_allTvWord);
        allV = findViewById(R.id.duobao_v0);

        waitLayout = (RelativeLayout) findViewById(R.id.duobao_layout_linearLayoutWait);
        waitTextWord = (CustomTextView) findViewById(R.id.duobao_layout_toBeConFfrimeedTvWord);
        waitV = findViewById(R.id.duobao_v1);

        yetLayout = (RelativeLayout) findViewById(R.id.duobao_layout_linearLayoutOffTheStocks);
        yetTextWord = (CustomTextView) findViewById(R.id.duobao_layout_offTheStocksTvWord);
        yetV = findViewById(R.id.duobao_v2);
        pager = (ViewPager) findViewById(R.id.duobao_layout_viewpage);
    }

    private void initFragment() {

        allFragment = new DuoBaoAllFragment();
        waitFragment = new DuoBaoWaitFragment();
        yetFragment = new DuoBaoYetFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(allFragment);
        fragmentList.add(waitFragment);
        fragmentList.add(yetFragment);
        pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        pager.addOnPageChangeListener(new MyDuoBaoOnPageChangeListener());
        pager.setCurrentItem(0);
        allTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
        allV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
        waitTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
        waitV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
        yetTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
        yetV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));

    }


    private void intiListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        allLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
                allTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                allV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));

                waitTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                waitV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));

                yetTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                yetV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));

            }
        });

        waitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
                allTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                allV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));

                waitTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                waitV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));

                yetTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                yetV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));


            }
        });

        yetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
                allTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                allV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));

                waitTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                waitV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));

                yetTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                yetV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));


            }
        });

    }

    public void onEventMainThread(DuoBaoNumberEvent duoBaoNumberEvent) {
        if (duoBaoNumberEvent.getDuoBaoTabNumberBean() == null) {

        } else {
            if (CodeVerifyUtils.verifyCode(duoBaoNumberEvent.getDuoBaoTabNumberBean().getCode())) {
                CodeVerifyUtils.verifySession(DuoBaoActivity.this);
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
                finish();
            } else {
                //
                if (duoBaoNumberEvent.getDuoBaoTabNumberBean().getCode().equals("1")) {
                    allTextWord.setText("全部 (" + duoBaoNumberEvent.getDuoBaoTabNumberBean().getData().getAll() + ")");
                    waitTextWord.setText("待揭晓 (" + duoBaoNumberEvent.getDuoBaoTabNumberBean().getData().getRun() + ")");
                    yetTextWord.setText("已揭晓 (" + duoBaoNumberEvent.getDuoBaoTabNumberBean().getData().getFinish() + ")");
                } else {
                    allTextWord.setText("全部 (0)");
                    waitTextWord.setText("待揭晓 (0)");
                    yetTextWord.setText("已揭晓 (0)");
                }
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

    public class MyDuoBaoOnPageChangeListener implements ViewPager.OnPageChangeListener {

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
                    allTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    allV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    waitTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    waitV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                    yetTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    yetV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                    break;
                case 1:
                    allTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    allV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));

                    waitTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    waitV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));

                    yetTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    yetV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));
                    break;
                case 2:
                    allTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    allV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));

                    waitTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_afafaf));
                    waitV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_efefef));

                    yetTextWord.setTextColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    yetV.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.c_ff5a5a));
                    break;
            }
        }
    }


}
