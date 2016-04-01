package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.HotSearchBean;

import java.util.ArrayList;

/**
 * 名称：CategoryListAdapter
 * 作者：zhaopl 时间: 15/8/19.
 * 实现的主要功能：
 * 搜索历史Adapter；
 */
public class HotSearchGridAdapter extends BaseAdapter {

    private ArrayList<HotSearchBean.HotSearch> list;

    private Activity mActivity;

    public HotSearchGridAdapter(ArrayList<HotSearchBean.HotSearch> categoryList, Activity activity) {

        this.list = categoryList;
        this.mActivity = activity;
    }

    public ArrayList<HotSearchBean.HotSearch> getList() {
        return list;
    }

    public void setList(ArrayList<HotSearchBean.HotSearch> list) {
        this.list = list;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            final Context contextThemeWrapper = new ContextThemeWrapper(mActivity, R.style.AlertDialog_AppCompat);

            LayoutInflater inflater = LayoutInflater.from(mActivity).cloneInContext(contextThemeWrapper);

            convertView = inflater.inflate(R.layout.search_grid_item_view, null);

            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }


        vh.hotSearchName.setText(list.get(position).getSearchName());//设置数据；
        vh.hotSearchName.setGravity(Gravity.CENTER);
        return convertView;
    }


    class ViewHolder {

        TextView hotSearchName;
        LinearLayout itemContainer;
        View convertView;

        public ViewHolder(View view) {
            this.convertView = view;

            hotSearchName = (TextView) convertView.findViewById(R.id.search_page_item_text);
            itemContainer = (LinearLayout) convertView.findViewById(R.id.item_container);
        }

    }
}
