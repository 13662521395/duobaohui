package com.shinc.duobaohui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.adapter.MyFragmentPagerAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.TakePartBean;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.event.UserPageTabNumberEvent;
import com.shinc.duobaohui.fragment.UseWinRecrodFragment;
import com.shinc.duobaohui.fragment.UserDuoBaoRecrodFragment;
import com.shinc.duobaohui.fragment.UserShowOrderRecrodFragment;
import com.shinc.duobaohui.http.UserPageTabNumberRequestNumber;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.ImageLoad;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/11/17.
 * TA的主页
 */
public class UserMainPageActivity extends BaseActivity {

    private BaseActivity mActivity;
    private ImageView imgBack;
    private ImageView userIcon;
    private CustomTextView userName;
    private CustomTextView userId;
    private RelativeLayout duoBaoRecrod;
    private View v0;
    private RelativeLayout winRecrod;
    private View v1;
    private RelativeLayout showOrderRecrod;
    private View v2;
    private ViewPager viewPager;

    private UserDuoBaoRecrodFragment duoBaoRecrodFragment;
    private UseWinRecrodFragment useWinRecrodFragment;
    private UserShowOrderRecrodFragment showOrderRecrodFragment;
    private List<Fragment> fragmentList;
    private CustomTextView userDuoBao;
    private CustomTextView userDuoBaoNumber;
    private CustomTextView userWin;
    private CustomTextView userWinNumber;
    private CustomTextView userShowOrder;
    private CustomTextView userSHowOrderNumber;
    private TakePartBean takePartBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main_page_layout);
        mActivity = UserMainPageActivity.this;
        EventBus.getDefault().register(this);
        initView();
        initData();
        initFragment();
        initListener();
    }

    private void initData() {
        takePartBean = (TakePartBean) getIntent().getSerializableExtra("takePart");
        if (!TextUtils.isEmpty(takePartBean.getHead_pic())) {
            ImageLoad.getInstance(mActivity).setImageToView(takePartBean.getHead_pic(), userIcon);
        }

        userName.setText(takePartBean.getNick_name());
        userId.setText("ID: " + takePartBean.getUser_id());
        HttpUtils httpUtils = new HttpUtils();

        RequestParams params = new RequestParams();
        params.addBodyParameter("user_id", takePartBean.getUser_id());
        httpUtils.sendHttpPost(params, ConstantApi.tauser_GetSum, new UserPageTabNumberRequestNumber(), mActivity);
    }

    public void onEventMainThread(UserPageTabNumberEvent event) {
        if (event.getBean() == null) {
            print("网络链接错误，请检查");
        } else {
            if (event.getBean().getCode().equals("1")) {
                userDuoBaoNumber.setText("(" + event.getBean().getData().getDuobao_num() + ")");
                userWinNumber.setText("(" + event.getBean().getData().getWinning_num() + ")");
                userSHowOrderNumber.setText("(" + event.getBean().getData().getShaidan_num() + ")");
            }
        }
    }

    private void initFragment() {

        Bundle b = new Bundle();
        b.putString("userId", takePartBean.getUser_id());
        duoBaoRecrodFragment = new UserDuoBaoRecrodFragment();
        duoBaoRecrodFragment.setArguments(b);
        useWinRecrodFragment = new UseWinRecrodFragment();
        useWinRecrodFragment.setArguments(b);
        showOrderRecrodFragment = new UserShowOrderRecrodFragment();
        showOrderRecrodFragment.setArguments(b);
        fragmentList = new ArrayList<>();
        fragmentList.add(duoBaoRecrodFragment);
        fragmentList.add(useWinRecrodFragment);
        fragmentList.add(showOrderRecrodFragment);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setCurrentItem(0);
        v0.setBackgroundColor(getResources().getColor(R.color.c_ff5a5a));
        v1.setBackgroundColor(getResources().getColor(R.color.c_efefef));
        v2.setBackgroundColor(getResources().getColor(R.color.c_efefef));

    }

    private void initView() {
        getWindow().setBackgroundDrawable(null);
        imgBack = (ImageView) findViewById(R.id.user_page_title_img);
        userIcon = (ImageView) findViewById(R.id.user_main_page_layout_userIcon);
        userName = (CustomTextView) findViewById(R.id.user_main_page_layout_userName);
        userId = (CustomTextView) findViewById(R.id.user_main_page_layout_userID);
        duoBaoRecrod = (RelativeLayout) findViewById(R.id.user_main_page_layout_userDuoBaoRecrod);
        userDuoBao = (CustomTextView) findViewById(R.id.user_main_page_layout_tvDuobaorecrod);
        userDuoBaoNumber = (CustomTextView) findViewById(R.id.user_main_page_layout_tvDuobaorecrodNumber);
        v0 = findViewById(R.id.user_main_page_layout_userDuoBao_v);
        winRecrod = (RelativeLayout) findViewById(R.id.user_main_page_layout_userWinRecrod);
        userWin = (CustomTextView) findViewById(R.id.user_main_page_layout_userWin);
        userWinNumber = (CustomTextView) findViewById(R.id.user_main_page_layout_userWinNumber);
        v1 = findViewById(R.id.user_main_page_layout_userWin_v);
        showOrderRecrod = (RelativeLayout) findViewById(R.id.user_main_page_layout_userShowOrder);
        userShowOrder = (CustomTextView) findViewById(R.id.user_main_page_layout_userShowOrdertV);
        userSHowOrderNumber = (CustomTextView) findViewById(R.id.user_main_page_layout_userShowOrdertVNUmber);
        v2 = findViewById(R.id.user_main_page_layout_userShowOrder_v);
        viewPager = (ViewPager) findViewById(R.id.user_main_page_layout_viewpage);

        userDuoBao.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
        userDuoBaoNumber.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
        userWin.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
        userWinNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
        userShowOrder.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
        userSHowOrderNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        duoBaoRecrod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                v0.setBackgroundColor(getResources().getColor(R.color.c_ff5a5a));
                v1.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                v2.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                userDuoBao.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
                userDuoBaoNumber.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
                userWin.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                userWinNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                userShowOrder.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                userSHowOrderNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
            }
        });
        winRecrod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                v0.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                v1.setBackgroundColor(getResources().getColor(R.color.c_ff5a5a));
                v2.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                userDuoBao.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                userDuoBaoNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                userWin.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
                userWinNumber.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
                userShowOrder.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                userSHowOrderNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
            }
        });
        showOrderRecrod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
                v0.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                v1.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                v2.setBackgroundColor(getResources().getColor(R.color.c_ff5a5a));
                userDuoBao.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                userDuoBaoNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                userWin.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                userWinNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                userShowOrder.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
                userSHowOrderNumber.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
            }

        });
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

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
                    v0.setBackgroundColor(getResources().getColor(R.color.c_ff5a5a));
                    v1.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                    v2.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                    userDuoBao.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
                    userDuoBaoNumber.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
                    userWin.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    userWinNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    userShowOrder.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    userSHowOrderNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    break;
                case 1:
                    v0.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                    v1.setBackgroundColor(getResources().getColor(R.color.c_ff5a5a));
                    v2.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                    userDuoBao.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    userDuoBaoNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    userWin.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
                    userWinNumber.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
                    userShowOrder.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    userSHowOrderNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    break;
                case 2:
                    v0.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                    v1.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                    v2.setBackgroundColor(getResources().getColor(R.color.c_ff5a5a));
                    userDuoBao.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    userDuoBaoNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    userWin.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    userWinNumber.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
                    userShowOrder.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
                    userSHowOrderNumber.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
