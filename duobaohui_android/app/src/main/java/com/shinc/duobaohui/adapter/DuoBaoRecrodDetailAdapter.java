package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shinc.duobaohui.DuoBaoCodeActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.LotteryTimesListbyPeriodidBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugaopo on 15/10/8.
 * 夺宝详情 适配器
 */
public class DuoBaoRecrodDetailAdapter extends BaseAdapter {
    private Activity mActivity;

    private List<LotteryTimesListbyPeriodidBean.Data> dataList;
    private String name;
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DuoBaoRecrodDetailAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        this.dataList = new ArrayList<>();
    }

    public List<LotteryTimesListbyPeriodidBean.Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<LotteryTimesListbyPeriodidBean.Data> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return getDataList().size();
    }

    @Override
    public Object getItem(int position) {
        return getDataList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder viewHolder;
    LotteryTimesListbyPeriodidBean.Data data;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.duobao_recrod_detail_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = getDataList().get(position);

        if (TextUtils.isEmpty(data.getTimes())) {
            viewHolder.personNum.setText("");
        } else {
            viewHolder.personNum.setText(data.getTimes());
        }
        if (TextUtils.isEmpty(data.getCreate_time())) {
            viewHolder.time.setText("");
        } else {
            viewHolder.time.setText(data.getCreate_time());
        }
        viewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivity, DuoBaoCodeActivity.class);
                intent.putExtra("user_id", getDataList().get(position).getUser_id());
                intent.putExtra("sh_activity_period_id", getDataList().get(position).getSh_activity_period_id());
                intent.putExtra("sh_period_user_id", getDataList().get(position).getSh_period_user_id());
                intent.putExtra("goodNumber", getDataList().get(position).getTimes());
                intent.putExtra("goodName", getName());
                mActivity.startActivity(intent);
            }
        });


        return convertView;
    }

    class ViewHolder {
        CustomTextView time;
        CustomTextView personNum;
        CustomTextView tvBtn;

        ViewHolder(View view) {
            time = (CustomTextView) view.findViewById(R.id.duobao_record_detail_layout_time);
            personNum = (CustomTextView) view.findViewById(R.id.duobao_record_detail_layout_personNumber);
            tvBtn = (CustomTextView) view.findViewById(R.id.duobao_record_detail_layout_tvBtn);
        }
    }

}
