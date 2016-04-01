package com.shinc.duobaohui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.adapter.ShareShowOrderAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.ShaiDanBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.HttpGetShowOrderEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.http.GetShowOrderHttpRequestImpl;
import com.shinc.duobaohui.utils.CodeVerifyUtils;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by efort on 15/10/10.
 * 我的晒单
 */
public class ShareShowOrderActivity extends BaseActivity {

    RelativeLayout title;
    ImageView back;
    TextView titleText;
    LoadMoreListView listView;
    WaitLoadingUtils waitLoadingUtils;
    private int state;
    private String user_id;

    PtrClassicFrameLayout ptrFrameLayout;
    private int page = 1;
    private boolean isRefresh;

    ShareShowOrderAdapter adapter;
    private RelativeLayout layoutNoData;
    private ImageView imgNoData;
    private TextView tvNoData;
    private TextView noDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_show_order);
        EventBus.getDefault().register(this);

        initView();
        initAdapter();
        waitLoadingUtils.show();
        state = getIntent().getIntExtra("STATE", 1);
        SharedPreferencesUtils spUtils = new SharedPreferencesUtils(this, Constant.SP_LOGIN);
        user_id = spUtils.get(Constant.SP_USER_ID, "");

        getData(1);
        initPullToRefresh();
    }

    private void initView() {
        getWindow().setBackgroundDrawable(null);
        title = (RelativeLayout) findViewById(R.id.share_show_title);
        back = (ImageView) findViewById(R.id.add_show_order_layout_img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleText = (TextView) findViewById(R.id.share_show_title_text);
        //如果是由首页点击进来那么我们的title是有变化的

        waitLoadingUtils = new WaitLoadingUtils(this);

        ptrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame_layout);
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        listView = (LoadMoreListView) findViewById(R.id.share_show_list_view);
        layoutNoData = (RelativeLayout) findViewById(R.id.activity_share_show_order_no_data);
        layoutNoData.setVisibility(View.GONE);
        noDataBtn = (TextView) findViewById(R.id.goto_duobao);
        noDataBtn.setEnabled(false);
        noDataBtn.setText("去夺宝");
        imgNoData = (ImageView) findViewById(R.id.no_date_layout_icon);
        imgNoData.setImageResource(R.drawable.icon_norecord_3);
        tvNoData = (TextView) findViewById(R.id.no_date_layout_tv);
        tvNoData.setText("暂时还没有晒单记录哦");
    }

    private void getData(int page) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        switch (state) {
            case 1:
                //我的晒单
                requestParams.addBodyParameter("user_id", user_id);
                break;
            case 2:
                //商品晒单
                requestParams.addBodyParameter("goods_id", getIntent().getStringExtra("GOODS_ID"));
                break;
            case 3:
                break;
        }
        requestParams.addBodyParameter("page", page + "");
        httpUtils.sendHttpPost(requestParams, ConstantApi.GET_SHAI_DAN, new GetShowOrderHttpRequestImpl("1"), this);
    }

    public void onEventMainThread(HttpGetShowOrderEvent getShowOrderEvent) {
        ShaiDanBean shaiDanBean = getShowOrderEvent.getShaiDanBean();
        if (shaiDanBean == null || shaiDanBean.getCode() == null) {
            waitLoadingUtils.disable();
            if (page == 1) {
                noWeb();
                if (adapter.getList() != null) {
                    adapter.getList().clear();
                    adapter.notifyDataSetChanged();
                }
            } else {
                page--;
            }
        } else {
            if (CodeVerifyUtils.verifyCode(getShowOrderEvent.getShaiDanBean().getCode())) {
                CodeVerifyUtils.verifySession(this);
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
                finish();
            } else {
                if ("1".equals(shaiDanBean.getCode())) {
                    //todo 成功，设置页面信息；
                    layoutNoData.setVisibility(View.GONE);
                    if (page == 1) {
                        if (adapter.getList()!=null&&adapter.getList().size() > 0) {
                            adapter.getList().clear();
                        }
                        adapter.setList(getShowOrderEvent.getShaiDanBean().getData());
                    } else {
                        adapter.getList().addAll(shaiDanBean.getData());
                    }
                } else {
                    if (adapter.getList().size() > 0) {
                        page--;
                        listView.setLoadComplete();
                        layoutNoData.setVisibility(View.GONE);
                        noDataBtn.setEnabled(false);
                        ptrFrameLayout.refreshComplete();
                    } else {
                        ptrFrameLayout.refreshComplete();
                        layoutNoData.setVisibility(View.VISIBLE);
                        noDataBtn.setVisibility(View.VISIBLE);
                        noDataBtn.setEnabled(true);
                    }
                }
            }
        }
        waitLoadingUtils.disable();
        ptrFrameLayout.refreshComplete();
        adapter.notifyDataSetChanged();

    }

    /**
     * 上下拉
     */
    private void initPullToRefresh() {


        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                listView.setLoadReset();
                isRefresh = true;
                getData(page);
            }
        });


        listView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                isRefresh = true;
                getData(page);
            }
        });

        noDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 去首页；
                Intent intent = new Intent(ShareShowOrderActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                EventBus.getDefault().post(new UtilsEvent("main"));
            }
        });
    }

    private void initAdapter() {
        adapter = new ShareShowOrderAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        adapter = null;
    }

    private void noWeb() {
        waitLoadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                waitLoadingUtils.haveWeb();
                waitLoadingUtils.show();
                getData(1);
            }
        });
    }
}
