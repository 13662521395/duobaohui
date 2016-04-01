package com.shinc.duobaohui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.shinc.duobaohui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称:BuyNumTwoLineAdapter
 * Created by chaos on 15/9/30.
 * 功能:详情页购买号码
 */

public class BuyNumTwoLineAdapter extends BaseAdapter {
    private Context context;
    private List<String> buynumList = new ArrayList<>();
    private LayoutInflater inflater;

    public BuyNumTwoLineAdapter(Context context, List<String> buynumList, GridView gv) {
        this.context = context;
        this.buynumList = buynumList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return buynumList.size();
    }

    @Override
    public Object getItem(int i) {
        return buynumList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BuyNumTwoLineViewHolder buyNumTwoLineViewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_buynum_twoline_productdetail, viewGroup, false);
            buyNumTwoLineViewHolder = new BuyNumTwoLineViewHolder(view);
            view.setTag(buyNumTwoLineViewHolder);
        } else {
            buyNumTwoLineViewHolder = (BuyNumTwoLineViewHolder) view.getTag();
        }
        if (buynumList.size() > 6) {
            if (i == 0) {
                buyNumTwoLineViewHolder.buyNum.setText("夺宝号码:");
            }
            if (i == buynumList.size() - 1) {
                buyNumTwoLineViewHolder.buyNum.setText("查看更多");
                buyNumTwoLineViewHolder.buyNum.setTextColor(context.getResources().getColor(R.color.c_00b7ee));
            } else {
                buyNumTwoLineViewHolder.buyNum.setText(buynumList.get(i));
            }
        } else {
            buyNumTwoLineViewHolder.buyNum.setText(buynumList.get(i));
        }

        return view;
    }

    static class BuyNumTwoLineViewHolder {
        private TextView buyNum;

        public BuyNumTwoLineViewHolder(View view) {
            buyNum = (TextView) view.findViewById(R.id.num_item_buynum_twoline_productdetail);
        }
    }
}
