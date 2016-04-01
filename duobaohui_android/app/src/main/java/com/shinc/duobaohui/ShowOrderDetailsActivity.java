package com.shinc.duobaohui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.ReportReasonBean;
import com.shinc.duobaohui.bean.ShaiDanBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;
import com.shinc.duobaohui.customview.dialog.ReportAlertDialog;
import com.shinc.duobaohui.customview.imp.MyImageView;
import com.shinc.duobaohui.event.HttpReportShareOrderEvent;
import com.shinc.duobaohui.http.ReportShareOrderRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;

import de.greenrobot.event.EventBus;


/**
 * Created by efort on 15/10/12.
 * 晒单详情
 */
public class ShowOrderDetailsActivity extends BaseActivity {

    TextView title;
    TextView userName;
    TextView time;
    TextView product;
    TextView canyuNum;
    TextView publishNumber;
    TextView publishTime;
    TextView value;
    LinearLayout imgs;
    ImageView reportBtn;
    private Activity mActivity;

    private ReportAlertDialog reportAlertDialog;

    private String[] items = {"淫秽暴力", "反动语言", "造谣欺诈", "侵权", "其它"};

    private int singleSelectedId;

    private ShaiDanBean.ShaiDanItem shaiDanItem;

    private SharedPreferencesUtils spUtils;

    private LoadingDialog loadingDialog;

    private TextView userIpAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order_details);
        EventBus.getDefault().register(this);
        this.mActivity = this;
        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
        initView();
        initListener();
        Bundle bundle = getIntent().getExtras();
        shaiDanItem = (ShaiDanBean.ShaiDanItem) bundle.get("ORDER_INFO");
        setData(shaiDanItem);
    }

    /**
     * 初始化触发事件；
     */
    private void initListener() {
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击进行举报的操作；
//                showReportDialog();
                if (reportAlertDialog == null) {
                    reportAlertDialog = new ReportAlertDialog(mActivity);
                } else {
                    if (!reportAlertDialog.isShow()) {
                        reportAlertDialog.showDialog();
                    }
                }
                setReportReasonClickListener(reportAlertDialog);
            }
        });
    }

    /**
     * 设置举报点击事件；
     */
    private void setReportReasonClickListener(ReportAlertDialog reportAlertDialog) {

        reportAlertDialog.setDialogButtonClickListener(new ReportAlertDialog.DialogButtonClickListener() {
            @Override
            public void onConfirmClick(ReportReasonBean reportReasonBean) {
                //todo 进行举报操作；
                loadingDialog.showLoading();
                HttpUtils httpUtils = new HttpUtils();

                RequestParams requestParams = new RequestParams();
                requestParams.addBodyParameter("report_id", reportReasonBean.getId());

                requestParams.addBodyParameter("shaidan_id", shaiDanItem.getId());
                //判断用户是否登录，如果登录就携带userId;如果没有就不进行操作；
                String userId = spUtils.get(Constant.SP_USER_ID, "");
                if (!TextUtils.isEmpty(userId)) {

                    requestParams.addBodyParameter("user_id", userId);

                }

                httpUtils.sendHttpPost(requestParams, ConstantApi.REPROT_SHARE_ORDER, new ReportShareOrderRequestImpl(), mActivity);

            }

            @Override
            public void onCancelClick() {
                //todo 进行取消的时候进行相应的操作；
            }
        });

    }


    /**
     * 举报的反馈事件接收；
     *
     * @param event
     */
    public void onEventMainThread(HttpReportShareOrderEvent event) {

        loadingDialog.hideLoading();
        if (event.getGetVerifyCodeBean() != null) {

            if ("1".equals(event.getGetVerifyCodeBean().getCode())) {
                Toast.makeText(mActivity, "举报成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, event.getGetVerifyCodeBean().getMsg(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mActivity, "举报失败，请重试！", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(final ShaiDanBean.ShaiDanItem shaiDanItem) {

        title.setText(shaiDanItem.getTitle());
        userName.setText(shaiDanItem.getUserInfo().getNick_name());
        time.setText(shaiDanItem.getOrderInfo().getA_code_create_time());

        product.setText("(第" + shaiDanItem.getOrderInfo().getPeriod_number() + "期) " + shaiDanItem.getOrderInfo().getGoods_name());
        canyuNum.setText(shaiDanItem.getOrderInfo().getTimes());
        publishNumber.setText(shaiDanItem.getOrderInfo().getLuck_code());
        publishTime.setText(shaiDanItem.getOrderInfo().getLuck_code_create_time());

        if (!TextUtils.isEmpty(shaiDanItem.getOrderInfo().getIp())) {
            userIpAddress.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(shaiDanItem.getOrderInfo().getIp_address())) {
                userIpAddress.setText("(" + shaiDanItem.getOrderInfo().getIp() + ")");
            } else {
                userIpAddress.setText("(" + shaiDanItem.getOrderInfo().getIp_address() + " " + shaiDanItem.getOrderInfo().getIp() + ")");
            }
        } else {
            userIpAddress.setVisibility(View.GONE);
        }
        value.setText(shaiDanItem.getContent());

        int padding = (int) getResources().getDimension(R.dimen.padding_10);
//
        for (int i = 0; i < shaiDanItem.getImg().size(); i++) {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.topMargin = padding;
//            ImageView imageView = new ImageView(this);
//            ImageLoad.getInstance(this).setImageToView(shaiDanItem.getImg().get(i), imageView);
//            imageView.setLayoutParams(params);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            MyImageView imageView = new MyImageView(this);
            imgs.addView(imageView);
            setImage(imageView, shaiDanItem.getImg().get(i));
        }
    }

    private void setImage(final MyImageView imgs, final String shaiDanItem) {
        new Runnable() {
            @Override
            public void run() {
                imgs.setUrl(shaiDanItem, ShowOrderDetailsActivity.this);
            }
        }.run();
    }

    private void initView() {
        getWindow().setBackgroundDrawable(null);
        ImageView back = (ImageView) findViewById(R.id.show_order_img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title = (TextView) findViewById(R.id.show_details_title);
        userName = (TextView) findViewById(R.id.show_details_username);
        time = (TextView) findViewById(R.id.show_details_time);

        product = (TextView) findViewById(R.id.show_product_info);
        canyuNum = (TextView) findViewById(R.id.show_canyu_num);
        publishNumber = (TextView) findViewById(R.id.show_product_number);
        publishTime = (TextView) findViewById(R.id.show_time);

        value = (TextView) findViewById(R.id.show_details_value);

        imgs = (LinearLayout) findViewById(R.id.show_details_images);

        reportBtn = (ImageView) findViewById(R.id.report_btn);
        loadingDialog = new LoadingDialog(mActivity, R.style.dialog);

        userIpAddress = (TextView) findViewById(R.id.show_details_user_ip_address);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imgs.clearDisappearingChildren();
        imgs = null;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        setContentView(R.layout.release_memory_layout);
        if (!this.isFinishing()) {
            this.finish();
        }
//        ImageLoad.getInstance(this).clearMemoryCache();
        System.gc();
    }

    private void showReportDialog() {
        singleSelectedId = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("举报原因");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                singleSelectedId = which;
                Toast.makeText(mActivity, "你选择的ID为：" + which, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if (singleSelectedId >= 0) {
                    Toast.makeText(mActivity, "你选择的ID为：" + singleSelectedId, Toast.LENGTH_SHORT).show();
                } else {
                    singleSelectedId = 0;
                    // 业务逻辑
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.create().show();
    }
}
