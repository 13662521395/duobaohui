package com.shinc.duobaohui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.adapter.RedEnevlopeDialogAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.event.RedEnevlopeDialogEvent;
import com.shinc.duobaohui.model.impl.RedEnevlopeDialogModel;
import com.umeng.socialize.utils.Log;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/12/18.
 * 红包Dialog
 */

public class RedEnevlopeDialogActivity extends BaseActivity {

    private BaseActivity mActivity;
    private TextView noUser;
    private TextView confrim;
    private TextView number;
    private TextView noData;
    private ListView listView;
    private View vFinish;
    private RelativeLayout relativeLayout;

    private RedEnevlopeDialogAdapter adapter;
    private RedEnevlopeDialogModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = RedEnevlopeDialogActivity.this;
        EventBus.getDefault().register(this);
        setContentView(R.layout.red_enevlope_layout);
        initView();
        initListener();
        initModel();
    }

    private void initModel() {
        model = new RedEnevlopeDialogModel(mActivity);

        model.getRequestDate(getIntent().getStringExtra("activityId"), getIntent().getStringExtra("NUM"));
    }

    private void initListener() {
        noUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.putExtra("redEnevlope", "no");
                // 设置结果，并进行传送
                setResult(RESULT_OK, mIntent);
                finish();
                finish();
            }
        });
        vFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        else if (Integer.parseInt(adapter.getDialogbeanList().get(j).getConsumption().substring(0, 1)) == Integer.parseInt(getIntent().getStringExtra("NUM"))) {
//            print("该红包等于支付金额，不可用");
//        }
        confrim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int j = 0; j < adapter.getDialogbeanList().size(); j++) {
                    if (adapter.getDialogbeanList().get(j).getIs_default() != null && adapter.getDialogbeanList().get(j).getIs_default().equals("1")) {
                        if (Integer.parseInt(adapter.getDialogbeanList().get(j).getConsumption().substring(5, adapter.getDialogbeanList().get(j).getConsumption().indexOf("夺"))) > Integer.parseInt(getIntent().getStringExtra("NUM"))) {
                            print("该红包不满足支付条件，不可用");
                        } else {
                            Intent mIntent = new Intent();
                            mIntent.putExtra("redEnevlope", "yes");
                            mIntent.putExtra("redpacket_id", adapter.getDialogbeanList().get(j).getId());
                            mIntent.putExtra("price", adapter.getDialogbeanList().get(j).getPrice().substring(0, 1));
                            // 设置结果，并进行传送
                            setResult(RESULT_OK, mIntent);
                            finish();
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        vFinish = findViewById(R.id.v_bttomdp);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_red_enevlope_layout);
        relativeLayout.setVisibility(View.GONE);
        noData = (TextView) findViewById(R.id.red_enevlope_no_data);
        noUser = (TextView) findViewById(R.id.re_eneclope_layout_noUser);
        confrim = (TextView) findViewById(R.id.re_eneclope_layout_confrim);
        listView = (ListView) findViewById(R.id.re_eneclope_layout_listview);
        adapter = new RedEnevlopeDialogAdapter(mActivity);

        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.red_enevlope_head_layout, null);
        number = (TextView) layout.findViewById(R.id.re_enevlope_layout_number);
        listView.addHeaderView(layout);
        listView.setAdapter(adapter);
    }

    public void onEventMainThread(RedEnevlopeDialogEvent redEnevlopeDialogEvent) {
        relativeLayout.setVisibility(View.VISIBLE);
        if (redEnevlopeDialogEvent.getRedEnevlopeDialogbean() == null) {
            print("网络链接出错，请检查网络");
        } else {
            if (redEnevlopeDialogEvent.getRedEnevlopeDialogbean().getCode().equals("1")) {
                noData.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                adapter.setDialogbeanList(redEnevlopeDialogEvent.getRedEnevlopeDialogbean().getData());
                adapter.notifyDataSetChanged();
                number.setText(adapter.getCount() + "个红包可用");
            } else {
                noData.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
