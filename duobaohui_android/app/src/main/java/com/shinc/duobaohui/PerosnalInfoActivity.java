package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.TokenBean;
import com.shinc.duobaohui.bean.UpdateHeadBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;
import com.shinc.duobaohui.event.HttpTokenEvent;
import com.shinc.duobaohui.event.HttpUpdateHeadEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.http.TokenRequestImpl;
import com.shinc.duobaohui.model.UpadteHeadModelInterface;
import com.shinc.duobaohui.model.impl.UpdateHeadModel;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.PictureActivityUtil;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by yangtianhe on 15/10/9.
 * //个人资料
 */
public class PerosnalInfoActivity extends BaseActivity {

    private ImageView backImg;
    private Activity mActivity;
    private RelativeLayout imageview;
    private RelativeLayout id;
    private RelativeLayout accountNumber;
    private RelativeLayout nickName;
    private RelativeLayout phonenumber;
    private RelativeLayout address;
    private ImageView headimageview;
    private TextView nickName_et;
    private TextView id_et;
    private TextView accountNumber_et;
    private SharedPreferencesUtils spUtils;
    private String token;
    private String path;
    private String url;
    private UpadteHeadModelInterface upadteHeadModel;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        EventBus.getDefault().register(this);
        setContentView(R.layout.perosnal_info_layout);
        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
        upadteHeadModel = new UpdateHeadModel(mActivity);
        initView();
        initListener();
    }

    private void initView() {
        loadingDialog = new LoadingDialog(mActivity, R.style.dialog);
        backImg = (ImageView) findViewById(R.id.person_info_layout_img_back);
        imageview = (RelativeLayout) findViewById(R.id.person_info_layout_id_imageview);
        id = (RelativeLayout) findViewById(R.id.person_info_layout_id);
        accountNumber = (RelativeLayout) findViewById(R.id.person_info_layout_accountNumber);
        accountNumber_et = (TextView) findViewById(R.id.person_info_layout_accountNumber_et);
        nickName = (RelativeLayout) findViewById(R.id.person_info_layout_nickName);
        phonenumber = (RelativeLayout) findViewById(R.id.person_info_layout_phonenumber);
        address = (RelativeLayout) findViewById(R.id.person_info_layout_address);
        headimageview = (ImageView) findViewById(R.id.headimageview);
        nickName_et = (TextView) findViewById(R.id.person_info_layout_nickName_et);
        nickName_et.setText(spUtils.get(Constant.SP_NICK_NAME, ""));
        accountNumber_et.setText(spUtils.get(Constant.SP_TEL, ""));
        id_et = (TextView) findViewById(R.id.person_info_layout_id_et);
        id_et.setText(spUtils.get(Constant.SP_USER_ID, ""));
        if (!TextUtils.isEmpty(spUtils.get(Constant.HEAD_PIC, ""))) {
            ImageLoader.getInstance().displayImage(spUtils.get(Constant.HEAD_PIC, ""), headimageview, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    headimageview.setImageResource(R.drawable.icon_head_small);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                    headimageview.setImageResource(R.drawable.icon_head_small);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                    headimageview.setImageResource(R.drawable.icon_head_small);
                }
            });
        }
    }

    private void initListener() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mActivity, ModifyPictureActivity.class);
//                // Intent intent = new Intent(mActivity, ComputionalDetailsActivity.class);
//                startActivity(intent);
                loadingDialog.show();
                Intent bap = new Intent(mActivity,
                        ModifyPictureActivity.class);
                bap.putExtra("extra", 1);
                startActivityForResult(bap, 1);
            }
        });
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        accountNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, PerosnalChangeNicknameActivity.class);
                // Intent intent = new Intent(mActivity, ComputionalDetailsActivity.class);
                startActivity(intent);
            }
        });
        phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AddressListActivity.class);
                // Intent intent = new Intent(mActivity, ComputionalDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Intent data1 = data.getParcelableExtra("data");
            if (data1 != null) {
                path = PictureActivityUtil
                        .getCropPath(data1);
                getToken();

            }
        } else {
            loadingDialog.hideLoading();
        }

    }

    private void getToken() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("image", "imgs");
        httpUtils.sendHttpPost(requestParams, ConstantApi.GET_TOKEN, new TokenRequestImpl(), this);
    }

    /**
     * 获取token回调
     *
     * @param event
     */
    public void onEventMainThread(HttpTokenEvent event) {
        TokenBean bean = event.getBean();
        if (bean != null) {
            if ("1".equals(bean.getCode())) {
                token = bean.getData().getToken();
                Bitmap userPhoto = BitmapFactory.decodeFile(path);
                headimageview.setImageBitmap(userPhoto);
                url = spUtils.get(Constant.SP_USER_ID, "") + System.currentTimeMillis() + ".jpg";

                // 上传图片
                UploadManager uploadManager = new UploadManager();
                // ToastUtils.show(getApplicationContext(), userhead);
                uploadManager.put(path, url,
                        token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key,
                                                 ResponseInfo info, JSONObject response) {

                                upadteHeadModel.sendurl("http://7xlbf0.com1.z0.glb.clouddn.com/" + url);

                            }
                        }, null);

            } else {
                Toast.makeText(this, bean.getMsg(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "修改头像失败,请稍后再试", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param
     */
    public void onEventMainThread(HttpUpdateHeadEvent httpUpdateHeadEvent) {
        if (httpUpdateHeadEvent.getUpdateHeadBean() != null) {
            UpdateHeadBean updateHeadBean = httpUpdateHeadEvent.getUpdateHeadBean();
            if ("1".equals(updateHeadBean.getCode())) {
                //todo 请求成功，进行成功后的操作；
                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                spUtils.add(Constant.HEAD_PIC, "http://7xlbf0.com1.z0.glb.clouddn.com/" + url);
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
            }
        }
        loadingDialog.hideLoading();
    }

    public void onEventMainThread(UtilsEvent utilsEvent) {
        if ("RELOAD_HEAD".equals(utilsEvent.getFlag())) {
            //todo 重新载入头像信息；
            nickName_et.setText(spUtils.get(Constant.SP_NICK_NAME, ""));
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
