package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.shinc.duobaohui.ProductDetailActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.ProductBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.utils.ImageLoad;

import java.util.ArrayList;

/**
 * 名称：CategoryListAdapter
 * 作者：zhaopl 时间: 15/8/19.
 * 实现的主要功能：
 * 实时搜索结果列表的Adapter;
 */
public class SearchRealTimeResultAdapter extends BaseAdapter {

    private ArrayList<ProductBean> list;


    private Activity mActivity;

    public SearchRealTimeResultAdapter(ArrayList<ProductBean> categoryList, Activity activity) {

        this.list = categoryList;
        this.mActivity = activity;
    }

    public ArrayList<ProductBean> getList() {
        return list;
    }

    public void setList(ArrayList<ProductBean> list) {
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

            convertView = View.inflate(mActivity, R.layout.search_real_time_result_item_view,
                    null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final ProductBean productBean = list.get(position);

        vh.productName.setText(productBean.getGoods_name());

        if (!TextUtils.isEmpty(productBean.getGoods_img().trim())) {
            ImageLoad.getInstance(mActivity).setImageToView(productBean.getGoods_img(), vh.productImg);
        } else {
            vh.productImg.setImageResource(R.drawable.pic_listnopic);
        }
        vh.needTime.setText("总需 " + productBean.getReal_need_times());

        int needNum = Integer.parseInt(productBean.getReal_need_times()) - Integer.parseInt(productBean.getCurrent_times());
        vh.lastTime.setText("剩余 " + needNum);

        vh.progressBar.setProgress(Integer.parseInt(productBean.getRate()));

        vh.gotoCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击跳转到正在进行的页面；
                Intent intent = new Intent(mActivity, ProductDetailActivity.class);
                intent.putExtra("PRODUCTID", productBean.getPeriod_id());
                intent.putExtra("TYPE", "SHOWCOMMITORDER");//传递此参数跳转到详情页面，加载完数据后，立即显示购买数量dialog;
                mActivity.startActivity(intent);
            }
        });

        return convertView;
    }


    class ViewHolder {

        CustomTextView productName;
        ImageView productImg;
        CustomTextView needTime;
        CustomTextView lastTime;
        ProgressBar progressBar;
        CustomTextView gotoCurrent;
        View convertView;

        public ViewHolder(View view) {
            this.convertView = view;
            productName = (CustomTextView) convertView.findViewById(R.id.search_real_time_result_left);
            productImg = (ImageView) convertView.findViewById(R.id.search_result_img);
            needTime = (CustomTextView) convertView.findViewById(R.id.search_result_need_time);
            lastTime = (CustomTextView) convertView.findViewById(R.id.search_result_last_time);
            progressBar = (ProgressBar) convertView.findViewById(R.id.active_progress);
            gotoCurrent = (CustomTextView) convertView.findViewById(R.id.goto_current);

        }

    }
}
