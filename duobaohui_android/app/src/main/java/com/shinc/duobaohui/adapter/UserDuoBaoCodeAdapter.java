package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shinc.duobaohui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugaopo on 15/10/8.
 */
public class UserDuoBaoCodeAdapter extends BaseAdapter {
    private List<String> list;
    private Activity mActivity;

    public UserDuoBaoCodeAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        list = new ArrayList<>();
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    UserDuoBaoCodeAdapter.ViewHolder viewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.duobao_code_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.code.setText(list.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView code;

        public ViewHolder(View view) {
            this.code = (TextView) view.findViewById(R.id.duobao_code_item_tv);
        }
    }
}
