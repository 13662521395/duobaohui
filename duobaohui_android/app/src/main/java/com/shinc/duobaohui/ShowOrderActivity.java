package com.shinc.duobaohui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.bean.TokenBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.event.HttpAddShowOrderEvent;
import com.shinc.duobaohui.event.HttpTokenEvent;
import com.shinc.duobaohui.event.HttpUploadImgEvent;
import com.shinc.duobaohui.event.YaSuoEvent;
import com.shinc.duobaohui.http.AddShowOrderHttpRequestImpl;
import com.shinc.duobaohui.http.TokenRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.shinc.duobaohui.utils.WaitLoadingUtils;
import com.shinc.duobaohui.utils.icon.DynamicPostInterface;
import com.shinc.duobaohui.utils.icon.LocalImageHelper;
import com.shinc.duobaohui.utils.icon.common.ImageUtils;
import com.shinc.duobaohui.utils.icon.ui.DynamicPost;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by efort on 15/10/9.
 * 我要晒单
 */
public class ShowOrderActivity extends BaseActivity {
    private TextView productName;
    private TextView num;
    private TextView productNum;
    private TextView time;

    private EditText commendValue;
    private EditText commendTitle;
    private DynamicPost commendImg;

    private TextView submit;
    /**
     * 图片地址集合
     */
    List<String> urlPath;
    List<String> imageNames;

    String title;
    String value;

    WaitLoadingUtils waitLoadingUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        EventBus.getDefault().register(this);
        initView();
        initClick();
        setDate();
    }

    private void initClick() {
        //提交晒单
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = commendTitle.getText().toString().trim();
                value = commendValue.getText().toString().trim();

                if (TextUtils.isEmpty(user_id)) {
                    Toast.makeText(ShowOrderActivity.this, "未登录", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(ShowOrderActivity.this, "请输入晒单标题！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (title.length() < 6) {
                    Toast.makeText(ShowOrderActivity.this, "晒单标题不能少于6字！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(value)) {
                    Toast.makeText(ShowOrderActivity.this, "请输入评价！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value.length() < 20) {
                    Toast.makeText(ShowOrderActivity.this, "评价不能少于20字！", Toast.LENGTH_SHORT).show();
                    return;
                }

                submit.setClickable(false);
                waitLoadingUtils.show();
                if (newUrlPath != null && newUrlPath.size() > 0) {
                    getToken();
                } else {
                    uploadCommend();
                }
            }
        });
        commendImg.setDeleteListenner(new DynamicPost.DeleteListener() {
            @Override
            public void onDelete(int index) {
                if (newUrlPath.size() > index) {
                    newUrlPath.remove(index);
                }
            }
        });
    }

    /**
     * 上传图片
     *
     * @param newUrlPath
     */
    private void uploadImg(List<String> newUrlPath) {
        imageNames = getImageName(newUrlPath);
        tempNum = 0;
        Toast.makeText(this, "正在上传图片，请稍后！", Toast.LENGTH_SHORT).show();
        uploadImage(newUrlPath.get(tempNum), imageNames.get(tempNum));
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
                uploadImg(newUrlPath);
            } else {
                waitLoadingUtils.disable();
                submit.setClickable(true);
                Toast.makeText(this, bean.getMsg(), Toast.LENGTH_LONG).show();
            }
        } else {
            waitLoadingUtils.disable();
            submit.setClickable(true);
            Toast.makeText(this, "晒单失败,请稍后再试", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 上传图片回调
     *
     * @param event
     */
    public void onEventMainThread(HttpUploadImgEvent event) {
        if (tempNum != -1 && tempNum < newUrlPath.size()) {
            uploadImage(newUrlPath.get(tempNum), imageNames.get(tempNum));
        } else if (event.getNum() == newUrlPath.size()) {
            //todo: 上传晒单
            uploadCommend();
        }
    }

    /**
     * 晒单回调
     *
     * @param event
     */
    public void onEventMainThread(HttpAddShowOrderEvent event) {
        submit.setClickable(true);
        waitLoadingUtils.disable();
        submit.setClickable(true);
        if (event.getShaiDanBean() != null) {
            if ("1".equals(event.getShaiDanBean().getCode())) {
                Toast.makeText(this, "晒单成功", Toast.LENGTH_LONG).show();
                EventBus.getDefault().post("winnerDetail");//发送EventBus 中奖纪录刷新
                finish();
            } else {
                Toast.makeText(this, event.getShaiDanBean().getMsg(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "晒单失败,请稍后再试", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * 取时间搓作为前置名 图片名称为后缀合成新的名字
     *
     * @param urlPath
     */
    private List<String> getImageName(List<String> urlPath) {
        List<String> imageNames = new ArrayList<>();
        for (int i = 0; i < urlPath.size(); i++) {
            imageNames.add(new File(urlPath.get(i)).getName().trim());
        }
        return imageNames;
    }

    private void initView() {
        ImageView back = (ImageView) findViewById(R.id.add_show_order_layout_img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        productName = (TextView) findViewById(R.id.show_product_info);
        num = (TextView) findViewById(R.id.show_canyu_num);
        productNum = (TextView) findViewById(R.id.show_product_number);
        time = (TextView) findViewById(R.id.show_time);

        commendTitle = (EditText) findViewById(R.id.show_commend_title);
        commendValue = (EditText) findViewById(R.id.show_commend_value);
        commendImg = (DynamicPost) findViewById(R.id.show_commend_image_show);
        commendImg.setVisibility(View.VISIBLE);

        final RelativeLayout title = (RelativeLayout) findViewById(R.id.title);
        final RelativeLayout show_commit_rl = (RelativeLayout) findViewById(R.id.show_commit_rl);
        final LinearLayout product_info = (LinearLayout) findViewById(R.id.product_info);
        final LinearLayout comment = (LinearLayout) findViewById(R.id.comment);


        commendImg.setVisible(new DynamicPostInterface.onViewVisible() {
            @Override
            public void onViewVisible(int isShow) {
                if (isShow == 1) {
                    title.setVisibility(View.VISIBLE);
                    show_commit_rl.setVisibility(View.VISIBLE);
                    product_info.setVisibility(View.VISIBLE);
                    comment.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.GONE);
                    show_commit_rl.setVisibility(View.GONE);
                    product_info.setVisibility(View.GONE);
                    comment.setVisibility(View.GONE);
                }
            }
        });

        submit = (TextView) findViewById(R.id.show_commit);

        waitLoadingUtils = new WaitLoadingUtils(this);
    }

    String user_id;

    /**
     * 设置数据  就是上面的中奖信息
     */
    public void setDate() {
        productName.setText(getIntent().getStringExtra("NAME"));
        num.setText(getIntent().getStringExtra("NUM"));
        productNum.setText(getIntent().getStringExtra("PRODUCTNUM"));
        time.setText(getIntent().getStringExtra("TIME"));
        //        requestParams.addBodyParameter("order_id", getIntent().getStringExtra("ORDER_ID"));
//        requestParams.addBodyParameter("goods_id", getIntent().getStringExtra("GOODS_ID"));

        SharedPreferencesUtils spUtils = new SharedPreferencesUtils(this, Constant.SP_LOGIN);
        user_id = spUtils.get(Constant.SP_USER_ID, "");
    }

    @Override
    public void onBackPressed() {
        commendImg.onBackPressed();
    }

    private String token = "h591Hrv-oh3BornRVEQqlDE7IJQYFgM-dkA44tKM:FoI5RCIV6EN6Ea-Z8fqwoU8N2ds=:eyJzY29wZSI6ImltZ3MiLCJkZWFkbGluZSI6MTQ0NDQ4ODI5MH0=";
    private int tempNum = 0;
    public List<String> qiniuUrls = new ArrayList<>();

    /**
     * 上传图片所需token
     */
    private void getToken() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("image", "imgs");
        httpUtils.sendHttpPost(requestParams, ConstantApi.GET_TOKEN, new TokenRequestImpl(), this);
    }

    /**
     * 上传图片
     */
    public void uploadImage(String path, final String headName) {

        UploadManager uploadManager = new UploadManager();

        uploadManager.put(path, headName, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo responseInfo, JSONObject jsonObject) {
                //todo:  拼接地址；
                String url = "http://7xlbf0.com1.z0.glb.clouddn.com/"
                        + headName;
                qiniuUrls.add(url);
                tempNum++;
                EventBus.getDefault().post(new HttpUploadImgEvent(tempNum));
            }
        }, null);
    }

    /**
     * 晒单
     */
    public void uploadCommend() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();

        requestParams.addBodyParameter("user_id", user_id);
        requestParams.addBodyParameter("title", title);
        requestParams.addBodyParameter("content", value);
        requestParams.addBodyParameter("order_id", getIntent().getStringExtra("ORDER_ID"));
        requestParams.addBodyParameter("goods_id", getIntent().getStringExtra("GOODS_ID"));
        if (newUrlPath.size() > 0) {
            for (int i = 0; i < newUrlPath.size(); i++) {
                requestParams.addBodyParameter("img[" + i + "]", qiniuUrls.get(i));
            }
        }

        httpUtils.sendHttpPost(requestParams, ConstantApi.ADD_SHAI_DAN, new AddShowOrderHttpRequestImpl(), this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.ImageViewNum = 9;
        MyApplication.dyNamicNum = 0;
        LocalImageHelper.getInstance().setCurrentSize(0);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        commendImg.onActivityResult(requestCode, resultCode, data);
        urlPath = commendImg.getUrlPath();
        Log.e("urlPath", urlPath.toString());

        if (urlPath != null && urlPath.size() > 0) {
            if (newUrlPath == null) {
                Log.e("newUrlPath", newUrlPath.toString());
                yasuoimage(1);
            } else if (urlPath.size() > newUrlPath.size()) {
                waitLoadingUtils.show();
                submit.setClickable(false);
                yasuoimage(newUrlPath.size() + 1);
            } else {
                waitLoadingUtils.disable();
                submit.setClickable(true);
            }

        }
    }

    List<String> newUrlPath = new ArrayList<>();

    private void yasuoimage(int i) {
        Log.e("newUrlPathi", i + "");
        newUrlPath.add(ImageUtils.getSmallImagePath(this, urlPath.get(i - 1), i));
    }

    /**
     * 上传图片回调
     *
     * @param event
     */
    public void onEventMainThread(YaSuoEvent event) {
        if (event.getCode() != -1 && event.getCode() < urlPath.size()) {
            yasuoimage(event.getCode() + 1);
        } else if (event.getCode() == -1 && event.getCode() < urlPath.size()) {
            newUrlPath.remove(newUrlPath.size() - 1);
            yasuoimage(event.getCode() + 1);
        } else if (event.getCode() == -1 && event.getCode() == urlPath.size()) {
            newUrlPath.remove(newUrlPath.size() - 1);
        } else if (event.getCode() == urlPath.size()) {
            waitLoadingUtils.disable();
            submit.setClickable(true);
        }
    }

}
