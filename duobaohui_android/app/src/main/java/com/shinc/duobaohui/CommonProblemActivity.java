package com.shinc.duobaohui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.shinc.duobaohui.adapter.CommonProblemAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.CommonProblemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugaopo on 15/10/22.
 * 时时彩～出错时的问题详情页
 */
public class CommonProblemActivity extends BaseActivity {

    private Activity mActivity;
    private ImageView imgBack;
    private ListView listView;
    private CommonProblemAdapter commonProblemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_roblem_layout);
        initView();
        initListener();
        initData();
    }

    private void initData() {
        List<CommonProblemBean> commonProblemBeans = new ArrayList<>();
        commonProblemBeans.add(new CommonProblemBean("怎样参加夺宝", Html.fromHtml("......").toString()));
        commonProblemAdapter.setProblemBeans(commonProblemBeans);
    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.common_problem_layout_imgBack);
        listView = (ListView) findViewById(R.id.common_problem_layout_listView);
        commonProblemAdapter = new CommonProblemAdapter(mActivity);
        listView.setAdapter(commonProblemAdapter);
    }
}
