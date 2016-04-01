package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.SearchResultBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;

import java.util.ArrayList;

/**
 * 名称：CategoryListAdapter
 * 作者：zhaopl 时间: 15/8/19.
 * 实现的主要功能：
 * 搜索历史Adapter；
 */
public class SearchHistoryGridAdapter extends BaseAdapter {

    private ArrayList<SearchResultBean.HotSearch> list;

    private Activity mActivity;

    public SearchHistoryGridAdapter(ArrayList<SearchResultBean.HotSearch> categoryList, Activity activity) {

        this.list = categoryList;
        this.mActivity = activity;
    }

    public ArrayList<SearchResultBean.HotSearch> getList() {
        return list;
    }

    public void setList(ArrayList<SearchResultBean.HotSearch> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(list.size() - position - 1);
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
            // inflater.cloneInContext(contextThemeWrapper);

            convertView = inflater.inflate(R.layout.search_grid_item_view, null);
//            convertView = View.inflate(mActivity,R.layout.product_category_grid_item,
//                    null);

            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.hotSearchName.setText(list.get(list.size() - position - 1).getSearchName());//设置数据；
        return convertView;
    }


    class ViewHolder {

        CustomTextView hotSearchName;
        View convertView;

        public ViewHolder(View view) {
            this.convertView = view;

            hotSearchName = (CustomTextView) convertView.findViewById(R.id.search_page_item_text);
        }

    }
}
