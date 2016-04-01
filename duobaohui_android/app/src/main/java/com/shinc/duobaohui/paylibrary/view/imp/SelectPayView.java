package com.shinc.duobaohui.paylibrary.view.imp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.BannerBean;
import com.shinc.duobaohui.bean.IndexBannerBean;
import com.shinc.duobaohui.bean.ProductDetail;
import com.shinc.duobaohui.bean.ProductDetailBean;
import com.shinc.duobaohui.customview.IndexBannerViewInterface;
import com.shinc.duobaohui.paylibrary.bean.PayEntity;
import com.shinc.duobaohui.paylibrary.customview.imp.PayChildView;
import com.shinc.duobaohui.paylibrary.view.SelectPayViewInterface;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liugaopo on 15/8/12.
 */
public class SelectPayView implements SelectPayViewInterface {

    private Activity mActivity;

    private PayChildView alipayChildView;

    private PayChildView wxpayChildView;

    private TextView numMin;
    private TextView numAdd;
    private IndexBannerViewInterface indexBannerViewImpl;
    private TextView productInfo;
    private ProgressBar productProgressBar;
    private TextView needNum;
    private TextView surplusNum;
    private EditText buyNumEdit;
    private RelativeLayout wchatPay;
    private RelativeLayout alipay;
    private CheckBox weixinCheckBox;
    private CheckBox alipayCheckBox;
    private TextView payCommit;
    private int type = 0;
    private ImageView backImg;

    private CheckBox duoBaoBiCheckbox;
    private RelativeLayout duobaobiLayout;
    private TextView tvBiWord;

    WaitLoadingUtils loadingUtils;

//  private PayChildView payChildView;

    public SelectPayView(Activity mActivity) {
        this.mActivity = mActivity;
        initView();
        initEvent();
    }

    private void initEvent() {

        numMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 减小购买的数据；

                int i;
                if (!TextUtils.isEmpty(buyNumEdit.getText().toString().trim())) {
                    i = Integer.parseInt(buyNumEdit.getText().toString().trim());
                } else {
                    i = 0;
                }
                if (i > 1) {
                    i--;
                    buyNumEdit.setText(i + "");
                    buyNumEdit.setSelection(buyNumEdit.getText().toString().length());
                    numAdd.setEnabled(true);
//                        numAdd.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
                } else {
                    //Toast.makeText(mActivity, "购买数量不能小于1", Toast.LENGTH_SHORT).show();
                    numMin.setEnabled(false);
                    buyNumEdit.setText(i + "");
//                        numMin.setBackgroundColor(mActivity.getResources().getColor(R.color.c_efefef));
                }

            }
        });

        wchatPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击微信付款的时候进行的操作；
                weixinCheckBox.setChecked(true);
                alipayCheckBox.setChecked(false);
                duoBaoBiCheckbox.setChecked(false);
                type = 2;
            }
        });
        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击支付宝的时候进行的操作；
                weixinCheckBox.setChecked(false);
                duoBaoBiCheckbox.setChecked(false);
                alipayCheckBox.setChecked(true);
                type = 1;
            }
        });
        alipayCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weixinCheckBox.setChecked(false);
                duoBaoBiCheckbox.setChecked(false);
                alipayCheckBox.setChecked(true);
                type = 1;
            }
        });


        duobaobiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duoBaoBiCheckbox.setChecked(true);
                weixinCheckBox.setChecked(false);
                alipayCheckBox.setChecked(false);
                type = 3;
            }
        });

        duoBaoBiCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duoBaoBiCheckbox.setChecked(true);
                weixinCheckBox.setChecked(false);
                alipayCheckBox.setChecked(false);
                type = 3;
            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });


    }

    public void initView() {
        this.mActivity.setContentView(R.layout.select_payment_method_layout);
//        LinearLayout parentLiear = (LinearLayout) mActivity.findViewById(R.id.layout_item_add);
//        alipayChildView = new PayChildView(mActivity);
//        parentLiear.addView(alipayChildView);
//        wxpayChildView = new PayChildView(mActivity);
//        parentLiear.addView(wxpayChildView);
        loadingUtils = new WaitLoadingUtils(mActivity);
        loadingUtils.show();

        numMin = (TextView) mActivity.findViewById(R.id.num_min);
        numAdd = (TextView) mActivity.findViewById(R.id.num_add);
        buyNumEdit = (EditText) mActivity.findViewById(R.id.buy_num);
        indexBannerViewImpl = (IndexBannerViewInterface) mActivity.findViewById(R.id.pay_activity_banner);
        productInfo = (TextView) mActivity.findViewById(R.id.product_info);
        productProgressBar = (ProgressBar) mActivity.findViewById(R.id.product_progress);
        productProgressBar.setProgress(0);
        needNum = (TextView) mActivity.findViewById(R.id.active_needed_num);
        surplusNum = (TextView) mActivity.findViewById(R.id.surplus_num);

        alipay = (RelativeLayout) mActivity.findViewById(R.id.alipay_rl);
        wchatPay = (RelativeLayout) mActivity.findViewById(R.id.wChat_pay_rl);
        weixinCheckBox = (CheckBox) mActivity.findViewById(R.id.weixin_check_box);
        alipayCheckBox = (CheckBox) mActivity.findViewById(R.id.check_box);
        payCommit = (TextView) mActivity.findViewById(R.id.pay_commit);
        backImg = (ImageView) mActivity.findViewById(R.id.select_pay_main_view_title_icon);

        duobaobiLayout = (RelativeLayout) mActivity.findViewById(R.id.bi_pay_rl);
        duoBaoBiCheckbox = (CheckBox) mActivity.findViewById(R.id.bi_check_box);
        tvBiWord = (TextView) mActivity.findViewById(R.id.bi_layout_tv_word);
        duoBaoBiCheckbox.setChecked(true);
        type = 3;
        buyNumEdit.setSelection(buyNumEdit.getText().toString().length());
    }


    @Override
    public void setDate(List<PayEntity> entityList) {
        alipayChildView.setIcon(entityList.get(0).getIconUrl(), R.drawable.icon_cashier_alipay);
        alipayChildView.setTv_word(entityList.get(0).getTv_word());
        alipayChildView.setOnClickListener(entityList.get(0).getOnClickListener());

        wxpayChildView.setIcon(entityList.get(1).getIconUrl(), R.drawable.weixin);
        wxpayChildView.setTv_word(entityList.get(1).getTv_word());
        wxpayChildView.setOnClickListener(entityList.get(1).getOnClickListener());
    }

    @Override
    public String getNum() {
        return buyNumEdit.getText().toString().trim();
    }

    @Override
    public void setNum(String payNum) {
        buyNumEdit.setText(payNum);
        buyNumEdit.setSelection(buyNumEdit.getText().toString().length());
    }

    @Override
    public void setBannerData(IndexBannerBean indexBannerBean, final SetBannerOnPageClick pageClick) {

        indexBannerViewImpl.setData(0, indexBannerBean, new IndexBannerViewInterface.SetOnPageClick() {
            @Override
            public void onClick(String id, String type, String linkUrl) {
                pageClick.onClick(id, type);
            }
        });
    }


    @Override
    public void payCommit(final CommitListener commitListener) {

        payCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    Toast.makeText(mActivity, "请选择支付方式", Toast.LENGTH_SHORT).show();
                } else {
                    //todo 进行支付提交的操作；
                    if (TextUtils.isEmpty(getNum()) || "0".equals(getNum())) {
                        Toast.makeText(mActivity, "请选择数量！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    commitListener.commit(type, buyNumEdit.getText().toString().trim());
                }
            }
        });
    }

    public void loadingDisable() {
        loadingUtils.disable();
    }

    public void showLoading() {
        loadingUtils.show();
    }

    public void haveWeb() {
        loadingUtils.haveWeb();
    }

    public void isNoWeb(WaitLoadingUtils.OnNoWebClick onNoWebClick) {
        loadingUtils.isNoWeb(onNoWebClick);
    }

    @Override
    public void initData(ProductDetailBean productDetailBean) {
        if ("1".equals(productDetailBean.getCode())) {
            //todo 成功，设置页面信息；
            ArrayList<BannerBean> bannerList = productDetailBean.getData().getGoods_pic();
            if (bannerList.size() > 0) {
                indexBannerViewImpl.setData(1, new IndexBannerBean("1", "", bannerList), new IndexBannerViewInterface.SetOnPageClick() {
                    @Override
                    public void onClick(String id, String type, String linkUrl) {
                        //todo 点击banner图的时候进行跳转；
                    }
                });
            }

            final ProductDetail productDetail = productDetailBean.getData();

            //设置商品的名称；
            initProductName(productDetail.getGoods_name(), productDetail.getStatus());

            productProgressBar.setProgress(Integer.parseInt(productDetail.getRate()));//设置进度；

            needNum.setText("总需" + productDetail.getReal_need_times() + "人次");
            surplusNum.setText((Integer.parseInt(productDetail.getReal_need_times()) - Integer.parseInt(productDetail.getCurrent_times())) + "");

            numAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo 增加购买的数量；
                    int i;
                    if (!TextUtils.isEmpty(buyNumEdit.getText().toString().trim())) {
                        i = Integer.parseInt(buyNumEdit.getText().toString().trim());

                    } else {
                        i = 0;
                    }

                    if (i < (Integer.parseInt(productDetail.getReal_need_times()) - Integer.parseInt(productDetail.getCurrent_times())))

                    {
                        i++;
                        buyNumEdit.setText(i + "");
                        buyNumEdit.setSelection(buyNumEdit.getText().toString().length());
                        numMin.setEnabled(true);
//                            numMin.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
                    } else

                    {

                        numAdd.setEnabled(false);
//                            numAdd.setBackgroundColor(mActivity.getResources().getColor(R.color.c_efefef));
                        // Toast.makeText(mActivity, "购买数量不能超过剩余数量！", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            buyNumEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(getNum())) {

                    } else if (getNum().equals("-")) {
                        //  setNum("0");
                    } else if (Integer.parseInt(getNum()) < 1) {
                        // setNum("0");
                    } else if (Integer.parseInt(getNum()) > (Integer.parseInt(productDetail.getReal_need_times()) - Integer.parseInt(productDetail.getCurrent_times()))) {
                        //   Log.e("购买", "购买数量大于剩余数量>");
                        setNum(Integer.parseInt(productDetail.getReal_need_times()) - Integer.parseInt(productDetail.getCurrent_times()) + "");
                        //Toast.makeText(mActivity, "购买数量不能大于剩余数量！", Toast.LENGTH_SHORT).show();
                        numAdd.setEnabled(false);
//                        numAdd.setBackgroundColor(mActivity.getResources().getColor(R.color.c_efefef));
                        numMin.setEnabled(true);
//                        numMin.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
                    } else if (Integer.parseInt(getNum()) < (Integer.parseInt(productDetail.getReal_need_times()) - Integer.parseInt(productDetail.getCurrent_times()))) {
//                        Log.e("购买", "购买数量小于于剩余数量<");
                        numAdd.setEnabled(true);
//                        numAdd.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
                        numMin.setEnabled(true);
//                        numMin.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
                        //setNum(Integer.parseInt(productDetail.getReal_need_times()) - Integer.parseInt(productDetail.getCurrent_times()) + "");
                        //Toast.makeText(mActivity, "购买数量不能大于剩余数量！", Toast.LENGTH_SHORT).show();
                    } else {
                        buyNumEdit.setSelection(buyNumEdit.getText().toString().length());
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    public void setSubmitBtnStatus(Boolean flag) {

        payCommit.setEnabled(flag);
    }

    @Override
    public void getMoeny(String moeny) {
        tvBiWord.setText("夺宝币支付(余额: " + moeny + ")");
    }

    private void initProductName(String str, String type) {
        Bitmap b = null;

        switch (Integer.parseInt(type)) {
            case 0:
                b = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.icon_going);
                break;
            case 1:
                b = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.icon_countdown);
                break;
            case 2:
                b = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.daojishi);
                break;
        }

        ImageSpan imgSpan = new ImageSpan(mActivity, b);
        SpannableString spanString = new SpannableString("icon");
        spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        productInfo.setText(spanString);
        productInfo.append("  " + str);


        SpannableString textColorSpan = new SpannableString("颜色随机");
        textColorSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 0, textColorSpan.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        //productInfo.append(textColorSpan);
    }

}
