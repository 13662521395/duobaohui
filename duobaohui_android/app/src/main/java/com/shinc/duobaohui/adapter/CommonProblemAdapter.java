package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.CommonProblemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugaopo on 15/10/22.
 */
public class CommonProblemAdapter extends BaseAdapter {
    private List<CommonProblemBean> problemBeans;
    private Activity mAcivity;

    public List<CommonProblemBean> getProblemBeans() {
        return problemBeans;
    }

    public void setProblemBeans(List<CommonProblemBean> problemBeans) {
        this.problemBeans.clear();
        this.problemBeans.addAll(problemBeans);
        notifyDataSetChanged();
    }

    public CommonProblemAdapter(Activity mAcivity) {
        this.mAcivity = mAcivity;
        this.problemBeans = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return getProblemBeans().size();
    }

    @Override
    public Object getItem(int position) {
        return getProblemBeans().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder viewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = mAcivity.getLayoutInflater().inflate(R.layout.common_problem_item_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(getProblemBeans().get(position).getTitle());
        viewHolder.tvInfo.setText(getProblemBeans().get(position).getInfo());


        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
        TextView tvInfo;

        public ViewHolder(View view) {
            this.tvTitle = (TextView) view.findViewById(R.id.comment_problem_item_layout_tvTitle);
            this.tvInfo = (TextView) view.findViewById(R.id.comment_problem_item_layout_tvInfo);
        }
    }

}
