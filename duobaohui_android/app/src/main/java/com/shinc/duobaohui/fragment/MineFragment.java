package com.shinc.duobaohui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.shinc.duobaohui.AddressListActivity;
import com.shinc.duobaohui.DuoBaoActivity;
import com.shinc.duobaohui.FastLoginActivity;
import com.shinc.duobaohui.MyCouponsActivity;
import com.shinc.duobaohui.NoticeActivity;
import com.shinc.duobaohui.PerosnalInfoActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.RechargeActivity;
import com.shinc.duobaohui.RechargeRecrodActivity;
import com.shinc.duobaohui.SettingActivity;
import com.shinc.duobaohui.ShareShowOrderActivity;
import com.shinc.duobaohui.WebActiveActivity;
import com.shinc.duobaohui.WinRecrodActivity;
import com.shinc.duobaohui.base.BaseFragment;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.dialog.SignDialog;
import com.shinc.duobaohui.customview.imp.CircleImageView;
import com.shinc.duobaohui.customview.imp.ObservableScrollView;
import com.shinc.duobaohui.event.UserInfoEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.model.MineUserInModelfoInterface;
import com.shinc.duobaohui.model.impl.MineUserModelImpl;
import com.shinc.duobaohui.utils.Blur;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.shinc.duobaohui.utils.umeng.ShareUtils;

import de.greenrobot.event.EventBus;

/**
 * 名称：MineFragment
 * 作者：zhaopl 时间: 15/9/29.
 * 实现的主要功能：
 * 个人中心页面；
 */
public class MineFragment extends BaseFragment implements ObservableScrollView.Callbacks {

    private Activity mActivity;
    private ImageView settingImg;
    private CircleImageView userPhotoImg;
    private TextView loginBtn;
    private TextView registerBtn;
    private TextView nickName;
    private TextView userId;
    private TextView userBalance;
    private LinearLayout depriveRecord;
    private LinearLayout winRecord;
    private LinearLayout addressManager;
    private SharedPreferencesUtils spUtils;
    private LinearLayout unLogin;
    private LinearLayout logined;
    private TextView gotoRecharge;
    private LinearLayout showOrder;

    private LinearLayout rechargeOrder;

    private LinearLayout inviteFriend;

    private ImageView mineHeaderBg;

    private MineUserInModelfoInterface modelfoInterface;

    private ShareUtils instance;
    private ImageView msgImg;
    private ImageView msgNoticePoint;
    private RelativeLayout myCoupons;
    private TextView myCouponsNum;

    // TODO: 15/12/22 滑动特效生成
    private ObservableScrollView mScrollview;
    private RelativeLayout mineTitle;
    private TextView mineTitleText;
    private TextView gotoSignIn;
    private View guideViewTwo;

    private ImageView signTwo;
    private SignDialog signDialog;

    @Override
    public String getFragmentName() {
        return MineFragment.class.getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_layout, null);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.mActivity = getActivity();
        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
        initView(view);
        initModel();
        initData();
        initEvent();

        return view;
    }

    /**
     * 初始化Model层；
     */

    private void initModel() {
        modelfoInterface = new MineUserModelImpl(mActivity);
    }


    /**
     * 初始化view控件；
     *
     * @param view
     */
    private void initView(View view) {
        msgImg = (ImageView) view.findViewById(R.id.msg_icon);
        settingImg = (ImageView) view.findViewById(R.id.setting_icon);
        userPhotoImg = (CircleImageView) view.findViewById(R.id.mine_user_img);
        nickName = (TextView) view.findViewById(R.id.user_nick_name);
        userId = (TextView) view.findViewById(R.id.user_id);
        loginBtn = (TextView) view.findViewById(R.id.login_btn);
        registerBtn = (TextView) view.findViewById(R.id.register_btn);
        winRecord = (LinearLayout) view.findViewById(R.id.win_record);
        depriveRecord = (LinearLayout) view.findViewById(R.id.deprive_record);
        addressManager = (LinearLayout) view.findViewById(R.id.address_manager);
        unLogin = (LinearLayout) view.findViewById(R.id.mine_user_unlogin);
        logined = (LinearLayout) view.findViewById(R.id.mine_user_logined);
        showOrder = (LinearLayout) view.findViewById(R.id.show_order);

        rechargeOrder = (LinearLayout) view.findViewById(R.id.recharge_record);

        inviteFriend = (LinearLayout) view.findViewById(R.id.invite_friend);

        gotoRecharge = (TextView) view.findViewById(R.id.goto_recharge);

        userBalance = (TextView) view.findViewById(R.id.remaining_balance);
        mineHeaderBg = (ImageView) view.findViewById(R.id.mine_user_bg);

        msgNoticePoint = (ImageView) view.findViewById(R.id.msg_notice_point);

        instance = ShareUtils.getInstance(mActivity);
        initHeader();
        //todo: 我的红包
        myCoupons = (RelativeLayout) view.findViewById(R.id.my_coupons);
        myCouponsNum = (TextView) view.findViewById(R.id.my_coupons_num);
        myCouponsNum.setVisibility(View.GONE);

        mineTitleText = (TextView) view.findViewById(R.id.mine_fragment_title_text);

        mScrollview = (ObservableScrollView) view.findViewById(R.id.mine_fragment_scrollview);
        mScrollview.setCallbacks(this);
        mineTitle = (RelativeLayout) view.findViewById(R.id.mine_fragment_title);
        gotoSignIn = (TextView) view.findViewById(R.id.goto_signin);
        guideViewTwo = mActivity.getLayoutInflater().inflate(R.layout.guide_sign_two_layout, null);

        signTwo = (ImageView) view.findViewById(R.id.sign_two);

    }


    /**
     * 初始化头像信息；
     */
    private void initHeader() {
        if (!TextUtils.isEmpty(spUtils.get(Constant.HEAD_PIC, ""))) {

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                    .bitmapConfig(Bitmap.Config.ARGB_8888)
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡
                    .build();
            //todo 对头像进行赋值；

            ImageLoader.getInstance().loadImage(spUtils.get(Constant.HEAD_PIC, "") + Constant.QINIU160_160, defaultOptions, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    userPhotoImg.setImageResource(R.drawable.icon_head_big);
                    mineHeaderBg.setImageResource(R.drawable.user_bg);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                    userPhotoImg.setImageResource(R.drawable.icon_head_big);
                    mineHeaderBg.setImageResource(R.drawable.user_bg);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                    if (bitmap != null) {
                        userPhotoImg.setImageBitmap(bitmap);
                        initHeaderBg(bitmap);
                    }
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                    userPhotoImg.setImageResource(R.drawable.icon_head_big);
                    mineHeaderBg.setImageResource(R.drawable.user_bg);
                }
            });
        } else {
            userPhotoImg.setImageResource(R.drawable.icon_head_big);
            mineHeaderBg.setImageResource(R.drawable.user_bg);
        }
    }

    /**
     * 对用户头像背景进行处理；
     */
    private void initHeaderBg(final Bitmap bitmap) {

        new Thread(new Runnable() {

            @Override
            public void run() {

                final Bitmap newImg = Blur.fastblur(mActivity, bitmap, 12);
                getActivity().runOnUiThread(new Runnable() {

                    public void run() {
                        mineHeaderBg.setImageBitmap(newImg);
                    }
                });

            }
        }).start();
    }

    private void initData() {

        if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
            unLogin.setVisibility(View.VISIBLE);
            logined.setVisibility(View.GONE);
            userPhotoImg.setEnabled(false);

        } else {

            unLogin.setVisibility(View.GONE);
            logined.setVisibility(View.VISIBLE);
            nickName.setText("用户昵称：" + spUtils.get(Constant.SP_NICK_NAME, ""));
            nickName.setShadowLayer(5F, 1F, 1F, Color.GRAY);//设置阴影效果；第一个参数为模糊半径，越大越模糊。 第二个参数是阴影离开文字的x横向距离。 第三个参数是阴影离开文字的Y横向距离。 第四个参数是阴影颜色。
            userId.setText("用户ID：" + spUtils.get(Constant.SP_USER_ID, ""));
            userId.setShadowLayer(5F, 1F, 1F, Color.GRAY);
            userBalance.setText("余额：" + spUtils.get(Constant.MONEY, "0"));
            userBalance.setShadowLayer(5F, 1F, 1F, Color.GRAY);
            userPhotoImg.setEnabled(true);
        }
    }


    private void initEvent() {
        msgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到消息页面；
                //当用户点击进入了消息中心后，发送数据对红点提示进行操作；
                EventBus.getDefault().post(new UtilsEvent("notice"));
                Intent intent = new Intent(mActivity, NoticeActivity.class);
                startActivity(intent);
            }
        });

        settingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到设置页面；
                Intent intent = new Intent(mActivity, SettingActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到登陆页面；
                Intent intent = new Intent(mActivity, FastLoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册页面；
                Intent intent = new Intent(mActivity, FastLoginActivity.class);
                intent.putExtra("isFlag", 1);
                startActivity(intent);
            }
        });

        depriveRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  跳转到夺宝记录页面；

                if (!TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
                    Intent intent = new Intent(mActivity, DuoBaoActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        winRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到中奖记录；
                if (!TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {

                    Log.e("userId", spUtils.get(Constant.SP_USER_ID, ""));
                    Intent intent = new Intent(mActivity, WinRecrodActivity.class);

                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        addressManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到地址管理页面；
                if (!TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
                    Intent intent = new Intent(mActivity, AddressListActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        userPhotoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到用户信息页面；
                Intent intent = new Intent(mActivity, PerosnalInfoActivity.class);
                startActivity(intent);
            }
        });

        gotoRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到充值页面；
                if (!TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
                    Intent intent = new Intent(mActivity, RechargeActivity.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                    //如果走到了这个流程，证明出现了充值显示但是用户数据被删除的事件；
                    initHeader();
                    initData();
                }
            }
        });

        showOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 我的晒单；
                if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, ShareShowOrderActivity.class);
                    intent.putExtra("STATE", 1);
                    startActivity(intent);
                }
            }
        });

        rechargeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 充值记录
                if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, RechargeRecrodActivity.class);

                    startActivity(intent);
                }
            }
        });

        inviteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 邀请好友；
                if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                } else {
                    instance.shareProduct(v, "一元参与夺豪礼，小伙伴们，还在等什么，快来加入！" + ConstantApi.SHARE_HOST + "system/download/share?userId=" + spUtils.get(Constant.SP_USER_ID, ""), "夺宝会APP", "http://7xnef1.com1.z0.glb.clouddn.com/icon_app.png", ConstantApi.SHARE_HOST + "system/download/share?userId=" + spUtils.get(Constant.SP_USER_ID, ""), "");
                }
            }
        });
        // TODO: 15/12/17 addClick
        myCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, MyCouponsActivity.class);
                    startActivity(intent);
                }
            }
        });

        /**
         * todo: 16/01/12 add goto signIn page;
         */

        gotoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (signTwo.getVisibility() == View.VISIBLE) {

                    signTwo.setVisibility(View.GONE);
                }

                if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, WebActiveActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("url", ConstantApi.SIGNIN_URL + "?userId=" + spUtils.get(Constant.SP_USER_ID, ""));
                    startActivity(intent);
                }
            }
        });
    }

    public void onEventMainThread(UserInfoEvent event) {
        if (event.getDuoBaoBiPayBean() == null) {
            print("同步用户数据失败！");
        } else {
            if (event.getDuoBaoBiPayBean().getCode().equals("1")) {
                spUtils.add(Constant.MONEY, event.getDuoBaoBiPayBean().getData().getMoney());
                initData();
            }
        }
    }

    public void onEventMainThread(UtilsEvent utilsEvent) {

        if (utilsEvent != null) {

            switch (utilsEvent.getFlag()) {
                case "RELOAD_HEAD"://重新载入头像，并对数据进行重新赋值；
                    initHeader();
                    initData();
                    break;
                case "RELOAD_BALANCE"://重新赋值余额；不通过请求网络进行数据获取；
                    initData();
                    break;
                case "LOAD_INFO":
                    modelfoInterface.getMineUserInfoData();//网络获取用户余额信息，进行重新赋值；
                    break;
                case "guide":
                    //todo 弹出引导签到的引导蒙层；

                    if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {

                        if (signDialog == null) {
                            signDialog = new SignDialog(mActivity, R.style.blackDialog);

                            signDialog.setDialogBtnListener(new SignDialog.DialogBtnListener() {
                                @Override
                                public void positiveClick() {
                                    signDialog.dismiss();
                                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void navigateClick() {
                                    signDialog.dismiss();

                                }
                            });
                        }
                        signDialog.show();

                        //当用户未登陆的时候；
                    } else {
                        //todo 当用户登陆的时候;
                        signTwo.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }


    /**
     * 设置消息提醒的显示隐藏；
     *
     * @param flag
     */
    public void setMsgNoticeState(boolean flag) {
        if (flag) {
            msgNoticePoint.setVisibility(View.VISIBLE);
        } else {
            msgNoticePoint.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareUtils.getInstance(mActivity).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onScrollChanged(int scrollY) {

//        Log.e("scrollY:" + px2dip(scrollY), "headerH:" + px2dip(headerH) + "-- photoH" + px2dip(photoH) + "--valueH:" + px2dip(valueH));

        if (scrollY <= dip2px(R.dimen.s_180dp)) {
            mineHeaderBg.setVisibility(View.VISIBLE);
            mineTitle.setVisibility(View.GONE);
            mineTitleText.setVisibility(View.GONE);
            mineHeaderBg.setAlpha(1f);
        } else if (scrollY >= dip2px(R.dimen.s_247dp)) {
            mineHeaderBg.setVisibility(View.GONE);
            mineTitle.getBackground().setAlpha(255);
            mineHeaderBg.setAlpha(0f);
            mineTitleText.setAlpha(1f);
        } else {
            mineTitle.setVisibility(View.VISIBLE);
            mineTitleText.setVisibility(View.VISIBLE);
            mineHeaderBg.setVisibility(View.VISIBLE);

            mineHeaderBg.setAlpha(((dip2px(R.dimen.s_260dp) - (float) scrollY) / dip2px(R.dimen.s_60dp)));
            mineTitle.getBackground().setAlpha(255 - (int) (((dip2px(R.dimen.s_247dp) - (float) scrollY) / dip2px(R.dimen.s_67dp)) * 255));
            mineTitleText.setAlpha((1f - ((dip2px(R.dimen.s_247dp) - (float) scrollY) / dip2px(R.dimen.s_67dp))));
        }

//        Log.e("scrollY:" + scrollY + "--  --" + (((float) scrollY / dip2px(100.0f))), "headerH:" + headerH + "-- photoH" + photoH + "--valueH:" + valueH);

        mineHeaderBg.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDimensionPixelSize(R.dimen.s_295dp) - scrollY / 2.5)));
    }

    /**
     * dp2px
     */
    public float dip2px(int dpId) {
//        final float scale = getActivity().getResources().getDisplayMetrics().density;
        return getResources().getDimensionPixelSize(dpId);
    }

    /**
     * px2dp
     */
    public float px2dip(float pxValue) {
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    public void setRedNumer(int i) {
        if (i < 1) {
            myCouponsNum.setVisibility(View.GONE);
        } else {
            myCouponsNum.setVisibility(View.VISIBLE);
            myCouponsNum.setText(i + "");
        }
    }
}
