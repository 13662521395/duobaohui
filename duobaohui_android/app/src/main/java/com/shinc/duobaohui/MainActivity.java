package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.lidroid.xutils.http.RequestParams;
import com.shihe.shincdatastatisticssdk.ShiNcAgent;
import com.shinc.duobaohui.adapter.MyFragmentPagerAdapter;
import com.shinc.duobaohui.base.BaseFragmentActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.bean.FeedbackBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.SlidingMenuView;
import com.shinc.duobaohui.event.HttpCheckNoticeEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.event.WecomeEvent;
import com.shinc.duobaohui.fragment.DeprivePreciousFragment;
import com.shinc.duobaohui.fragment.MineFragment;
import com.shinc.duobaohui.fragment.ShowOrderFragment;
import com.shinc.duobaohui.fragment.WaitingPublishFragment;
import com.shinc.duobaohui.http.CheckNoticeRequestImpl;
import com.shinc.duobaohui.http.WecomeRequest;
import com.shinc.duobaohui.utils.ImageLoad;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;


public class MainActivity extends BaseFragmentActivity {

    private FragmentActivity mActivity;
    private Fragment fragment;
    private android.support.v4.app.FragmentManager fragmentManager;
    private RelativeLayout btn_deprive;//课程推荐
    private RelativeLayout btn_cart;//二维码
    private RelativeLayout btn_mine;//离线下载
    private RelativeLayout btn_wait;

    private ImageView deprive_img;
    private TextView deprive_tv;

    private ImageView cart_img;
    private ImageView mine_img;
    private TextView mine_tv;

    private ImageView wait_img;
    private TextView wait_tv;


    private DeprivePreciousFragment deprivePreciousFragment;
    private ShowOrderFragment showOrderFragment;
    private WaitingPublishFragment newPublishFragment;

    private MineFragment mineFragment;
    private TextView cart_text;

    private static ViewPager mPager;
    private ViewSwitcher indexLaunchViewSwitcher;
    private boolean isFirst = true;
    private static boolean isPreparedExit;

    private int currentItem = 0;
    private Timer timer;
    private List<Fragment> fragmentList;

    private DrawerLayout drawerLayout;
    private SlidingMenuView slidingView;

    private boolean isClickSlidingMenu;

    private SharedPreferencesUtils cdUtils;

    private ImageView msgNoticePoint;

    private String categoryId;

    private String categoryName;

    private FeedbackBean bean;
    //splash_activity_layout_inclue
    public Handler mhandler;

    private ImageView mImgView;

//    private SignGuideView signDialog;

    private ImageView signOne;

//    private View targetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mActivity = MainActivity.this;
        mhandler = new MainHandler();
        //setImmerseLayout(findViewById(R.id.splash_activity));
        //顶端侵染；由于多页面的title栏不一致，不建议添加；
        cdUtils = new SharedPreferencesUtils(mActivity, Constant.COMMON_DATA);
        EventBus.getDefault().register(this);
        initView();
        initFragment();
        initListener();

        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        testStatices();
    }

    private void testStatices() {
        ShiNcAgent.initShincAgent(this, "this is test device id");
        ShiNcAgent.showDebug();
        ShiNcAgent.setLocation(this, "中国", "北京", "北京", "朝阳－三里屯－优衣库");
        ShiNcAgent.bindUserIdentifier(this, "test user id");
    }

    /**
     * 初始化View;
     */
    private void initView() {
        getWindow().setBackgroundDrawable(null);
        mImgView = (ImageView) findViewById(R.id.splash_activity_layout_img);
        initWecomeHttpImg();
        btn_deprive = (RelativeLayout) findViewById(R.id.main_deprive_btn);
        btn_cart = (RelativeLayout) findViewById(R.id.main_cart_btn);
        btn_mine = (RelativeLayout) findViewById(R.id.main_mine_btn);
        btn_wait = (RelativeLayout) findViewById(R.id.main_wait_publish);

        deprive_img = (ImageView) findViewById(R.id.main_deprive_img);
        deprive_tv = (TextView) findViewById(R.id.main_deprive_tv);

        cart_img = (ImageView) findViewById(R.id.main_cart_img);
        cart_text = (TextView) findViewById(R.id.main_cart_text);

        mine_img = (ImageView) findViewById(R.id.main_mine_img);
        mine_tv = (TextView) findViewById(R.id.main_mine_tv);

        wait_img = (ImageView) findViewById(R.id.wait_publish_img);
        wait_tv = (TextView) findViewById(R.id.wait_publish_tv);

        mPager = (ViewPager) findViewById(R.id.frag_fragment);
        indexLaunchViewSwitcher = (ViewSwitcher) findViewById(R.id.main_layout);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        slidingView = (SlidingMenuView) findViewById(R.id.sliding_view);
        msgNoticePoint = (ImageView) findViewById(R.id.system_msg_notify_point);


        signOne = (ImageView) findViewById(R.id.sign_one);


        intiViewSwitcher();

        initGuideDialog();


    }

//    private void initEventLogin() {
//
//        if (getIntent().getStringExtra("Event") != null && getIntent().getStringExtra("Event").equals("LoginInfo")) {
//            Log.e("asaa", "sadafå");
//        }
//    }

    /**
     * 初始化引导dialog;
     */
    private void initGuideDialog() {

//        guideViewOne = mActivity.getLayoutInflater().inflate(R.layout.guide_sign_one_layout, null);
//
//        targetView = guideViewOne.findViewById(R.id.target_layout);
    }

    /*
    *欢迎界面
    */
    private void initWecomeHttpImg() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        httpUtils.sendHttpPost(requestParams, ConstantApi.INDEXWECOME, new WecomeRequest(), mActivity);
    }

    public void onEventMainThread(WecomeEvent wecomeEvent) {
        if (wecomeEvent.getComeBean() == null) {
//            print("网络链接错误，请检查网络链接");
        } else {
            mImgView.setVisibility(View.VISIBLE);
            if (wecomeEvent.getComeBean().getCode().equals("1") && !TextUtils.isEmpty(wecomeEvent.getComeBean().toString())) {
                ImageLoad.getInstance(mActivity).setImageToView(wecomeEvent.getComeBean().getData().getUrl(), mImgView);
            } else {
                mImgView.setImageResource(R.drawable.splash_page);
            }
        }
    }

    /**
     * 初始化闪屏页面；
     */
    private void intiViewSwitcher() {

        Animation exit_anim = AnimationUtils.loadAnimation(this, R.anim.zoom_exit);

        indexLaunchViewSwitcher.setOutAnimation(exit_anim);

        indexLaunchViewSwitcher.showPrevious();//显示；
        indexLaunchViewSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirst) {
                    indexLaunchViewSwitcher.showNext();
                    isFirst = false;
                } else {
                    indexLaunchViewSwitcher.setEnabled(false);
                }
            }
        });

        //使用timer来进行自动消失的时间控制；
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (isFirst) {
                            indexLaunchViewSwitcher.showNext();
                            indexLaunchViewSwitcher.setEnabled(false);
                            timer.cancel();
                            timer = null;
                        }

                        //todo show guide dialog;
                        if (TextUtils.isEmpty(cdUtils.get(Constant.IFFIRST, ""))) {
                            signOne.setVisibility(View.VISIBLE);
                        } else {
                            signOne.setVisibility(View.GONE);
                        }

                    }
                });

            }
        }, 3000);

    }


    //初始化Fragment;
    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        deprivePreciousFragment = new DeprivePreciousFragment();
        newPublishFragment = new WaitingPublishFragment();
        showOrderFragment = new ShowOrderFragment();
        mineFragment = new MineFragment();
        fragmentList.add(deprivePreciousFragment);
        fragmentList.add(newPublishFragment);
        fragmentList.add(showOrderFragment);
        fragmentList.add(mineFragment);

        mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(new MyFragmentPagerAdapter(fragmentManager, fragmentList));

        mPager.setCurrentItem(0);//设置当前显示标签页为第一页
        tabChange(0);
        mPager.addOnPageChangeListener(onPageChangeListener);
    }


    /**
     * 监听
     */
    private void initListener() {

        slidingView.setOnTitleClickListener(new SlidingMenuView.onTitleClickListener() {
            @Override
            public void onTitleClick() {
                //todo 当点击titel的时候进行的操作；
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
            }
        });

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (isClickSlidingMenu) {
                    Intent intent = new Intent(mActivity, CategoryContentActivity.class);
                    intent.putExtra("CATEGORYID", categoryId + "");
                    intent.putExtra("CATEOGORYNAME", categoryName + "");
                    startActivity(intent);
                }
                isClickSlidingMenu = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //设置侧拉菜单按钮的响应事件；
        deprivePreciousFragment.setOnClickSlidingMenuListener(new DeprivePreciousFragment.OnClickSlidingMenuListener() {
            @Override
            public void onClickSlidingMenu() {
                //收到它的响应进行回调；
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        slidingView.setOnListItemClickListener(new SlidingMenuView.OnListItemClickListener() {
            @Override
            public void onLiteItemClick(int position, String id, String catName) {
                //todo 点击slidingmenu中list item 进行的操作；
                isClickSlidingMenu = true;
                categoryId = id;
                categoryName = catName;

                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
            }
        });

        btn_deprive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 切换tab;
                mPager.setCurrentItem(0);
                tabChange(0);
            }
        });
        btn_wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(1);
                tabChange(1);
            }
        });

        btn_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 切换到mine界面；
                mPager.setCurrentItem(3);
                tabChange(3);

                signOne.setVisibility(View.GONE);
                if (TextUtils.isEmpty(cdUtils.get(Constant.IFFIRST, ""))) {
                    EventBus.getDefault().post(new UtilsEvent("guide"));
                }
                spUtils.add(Constant.IFFIRST, "true");
            }
        });

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(2);
                tabChange(2);
            }
        });
    }


    //页面监听
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            switch (position) {
                case 0:
                    //todo 切换tab;
                    mPager.setCurrentItem(0);
                    tabChange(0);
                    break;
                case 1:
                    mPager.setCurrentItem(1);
                    tabChange(1);
                    break;

                case 2:
                    mPager.setCurrentItem(2);
                    tabChange(2);
                    break;
                case 3:
                    mPager.setCurrentItem(3);
                    tabChange(3);
                    //切换到我的模块的时候，隐藏提示；

                    signOne.setVisibility(View.GONE);
                    if (TextUtils.isEmpty(cdUtils.get(Constant.IFFIRST, ""))) {
                        EventBus.getDefault().post(new UtilsEvent("guide"));
                    }
                    cdUtils.add(Constant.IFFIRST, "true");
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * tab切换函数；
     *
     * @param flag
     */
    private void tabChange(int flag) {

        switch (flag) {
            case 0:
                deprive_img.setImageResource(R.drawable.tab_index_sel);
                deprive_tv.setTextColor(getResources().getColor(R.color.c_ff5a5a));

                mine_img.setImageResource(R.drawable.tab_my_nor);
                mine_tv.setTextColor(getResources().getColor(R.color.c_afafaf));


                cart_img.setImageResource(R.drawable.tab_show_nor);
                cart_text.setTextColor(getResources().getColor(R.color.c_afafaf));

                wait_img.setImageResource(R.drawable.tab_new_nor);
                wait_tv.setTextColor(getResources().getColor(R.color.c_afafaf));
                break;
            case 1:

                wait_img.setImageResource(R.drawable.tab_new_sel);
                wait_tv.setTextColor(getResources().getColor(R.color.c_ff5a5a));

                mine_img.setImageResource(R.drawable.tab_my_nor);
                mine_tv.setTextColor(getResources().getColor(R.color.c_afafaf));

                cart_img.setImageResource(R.drawable.tab_show_nor);
                cart_text.setTextColor(getResources().getColor(R.color.c_afafaf));

                deprive_img.setImageResource(R.drawable.tab_index_nor);
                deprive_tv.setTextColor(getResources().getColor(R.color.c_afafaf));


                break;
            case 2:

                mine_img.setImageResource(R.drawable.tab_my_nor);
                mine_tv.setTextColor(getResources().getColor(R.color.c_afafaf));

                cart_img.setImageResource(R.drawable.tab_show_sel);
                cart_text.setTextColor(getResources().getColor(R.color.c_ff5a5a));

                deprive_img.setImageResource(R.drawable.tab_index_nor);
                deprive_tv.setTextColor(getResources().getColor(R.color.c_afafaf));

                wait_img.setImageResource(R.drawable.tab_new_nor);
                wait_tv.setTextColor(getResources().getColor(R.color.c_afafaf));

                break;
            case 3:

                deprive_img.setImageResource(R.drawable.tab_index_nor);
                deprive_tv.setTextColor(getResources().getColor(R.color.c_afafaf));

                cart_img.setImageResource(R.drawable.tab_show_nor);
                cart_text.setTextColor(getResources().getColor(R.color.c_afafaf));

                mine_img.setImageResource(R.drawable.tab_my_sel);
                mine_tv.setTextColor(getResources().getColor(R.color.c_ff5a5a));

                wait_img.setImageResource(R.drawable.tab_new_nor);
                wait_tv.setTextColor(getResources().getColor(R.color.c_afafaf));

                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (!isPreparedExit) {
                Toast.makeText(mActivity, "再次点击退出程序", Toast.LENGTH_SHORT).show();
                isPreparedExit = true;
                mhandler.sendEmptyMessageDelayed(
                        Constant.UNPREPAREDEXIT, 3000);
                return true;
            } else {
                super.onBackPressed();
                // 退出应用程序；
                LinkedList<Activity> activities = (LinkedList<Activity>) MyApplication.getInstance().getList();
                try {
                    for (Activity activity : activities) {
                        if (activity != null) {
                            activity.finish();
                            System.exit(0);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 工具Event的传递；
     *
     * @param utilsEvent
     */
    public void onEventMainThread(UtilsEvent utilsEvent) {
        String flag = utilsEvent.getFlag();
        if (!TextUtils.isEmpty(flag)) {
            switch (flag) {
                case "main":
                    if (mPager != null) {
                        mPager.setCurrentItem(0, false);
                    }
                    break;
                case "notice":
                    //当用户点击进入消息中心后,会自动隐藏掉红点提示；
                    try {
                        msgNoticePoint.setVisibility(View.GONE);
                        mineFragment.setMsgNoticeState(false);
                        //同时对保存的数据进行赋值
                        cdUtils.add(Constant.MESSAGE_NOTICE, bean.getData().getLatest_date());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }

    /**
     * 检查消息的时间戳，是否有更新；
     */
    public void getMessageNotice() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
//        requestParams.addBodyParameter("userId", spUtils.get(Constant.SP_USER_ID, ""));

        httpUtils.sendHttpPost(requestParams, ConstantApi.CHECK_MSG_NOTICE, new CheckNoticeRequestImpl(), mActivity);
    }

    public void onEventMainThread(HttpCheckNoticeEvent event) {

        String msgNotice = cdUtils.get(Constant.MESSAGE_NOTICE, "");
        //todo 获取消息更新的数据进行操作；
        bean = event.getBean();
        if (bean != null && bean.getData() != null) {
            if (TextUtils.isEmpty(msgNotice)) {
                cdUtils.add(Constant.MESSAGE_NOTICE, bean.getData().getLatest_date());
            } else {
                if (!msgNotice.equals(bean.getData().getLatest_date())) {
                    //如果存储的和得到的数据不相同，这个时候，认为有新的消息；
                    msgNoticePoint.setVisibility(View.VISIBLE);
                    mineFragment.setMsgNoticeState(true);
                }
            }

            if (!TextUtils.isEmpty(bean.getData().getRedpacket())) {
                mineFragment.setRedNumer(Integer.parseInt(bean.getData().getRedpacket()));
            } else {
                mineFragment.setRedNumer(0);
            }
        } else {
            mineFragment.setRedNumer(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //每次读取message notice;
        getMessageNotice();
//        initEventLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPager.getCurrentItem() == 3) {
            mineFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (fragmentList != null) {
            fragmentList = null;
        }
        ShiNcAgent.onDestory(this);
    }

    private class MainHandler extends Handler {

        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {

                case Constant.UNPREPAREDEXIT:
                    isPreparedExit = false;
                    break;

                default:
                    break;
            }
        }
    }

}
