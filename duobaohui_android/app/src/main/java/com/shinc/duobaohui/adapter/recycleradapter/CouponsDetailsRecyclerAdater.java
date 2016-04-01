package com.shinc.duobaohui.adapter.recycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.CouponsDetailsHttpBean;
import com.shinc.duobaohui.customview.recycler.CouponsDetailsHeader;
import com.shinc.duobaohui.utils.ImageLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者: efort
 * @日期: 15/12/21 - 15:03
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsDetailsRecyclerAdater extends RecyclerView.Adapter {
    private Context mContext;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private int header = 0;

    private ArrayList<CouponsDetailsHttpBean.CouponsDetialsBean.CouponsProductBean> itemList;
    private CouponsDetailsHeader headerView;
    private ItemClickListner itemClick;

    public CouponsDetailsRecyclerAdater(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return itemList == null ? header : itemList.size() + header;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            return new MyHeaderViewHolder(headerView);
        } else if (viewType == TYPE_ITEM) {
            return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.coupons_details_recycler_item, parent, false));
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    // TODO: 15/12/21 逻辑
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder myHolder, final int position) {
        if (myHolder instanceof MyHeaderViewHolder) {

        } else if (myHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) myHolder;

            myViewHolder.productImg.setImageResource(R.drawable.icon_nopic);
            if (!TextUtils.isEmpty(itemList.get(position - header).getGoods_img()))
                ImageLoad.getInstance(mContext).setImageToView(itemList.get(position - header).getGoods_img(), myViewHolder.productImg);

            myViewHolder.productName.setText(itemList.get(position - header).getGoods_name());

            myViewHolder.productRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onItemClick(itemList.get(position - header).getPeriod_id());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void setItemList(List<CouponsDetailsHttpBean.CouponsDetialsBean.CouponsProductBean> couponsList) {
        if (couponsList == null) {
            return;
        }
        if (itemList == null) {
            itemList = new ArrayList<CouponsDetailsHttpBean.CouponsDetialsBean.CouponsProductBean>();
        }
        itemList.clear();
        itemList.addAll(couponsList);
        notifyDataSetChanged();
    }

    public void addItemList(List<CouponsDetailsHttpBean.CouponsDetialsBean.CouponsProductBean> couponsList) {
        if (couponsList == null) {
            return;
        }
        if (itemList == null) {
            itemList = new ArrayList<CouponsDetailsHttpBean.CouponsDetialsBean.CouponsProductBean>();
        }
        itemList.addAll(couponsList);
        notifyDataSetChanged();
    }

    /**
     * 添加头布局
     *
     * @param recyclerHeader
     */
    public void addHeaderView(CouponsDetailsHeader recyclerHeader) {
        header++;
        this.headerView = recyclerHeader;
    }

    public void setItemClick(ItemClickListner itemClick) {
        this.itemClick = itemClick;
    }

    // item 的viewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImg;
        private TextView productName;
        private RelativeLayout productRl;

        public MyViewHolder(View itemView) {
            super(itemView);
            productImg = (ImageView) itemView.findViewById(R.id.coupons_product_item_img);
            productName = (TextView) itemView.findViewById(R.id.coupons_product_item_name);
            productRl = (RelativeLayout) itemView.findViewById(R.id.coupons_details_item_rl);
        }
    }

    //header  头布局，因为我这里是我已经自定义好了头布局，并且我会设置好事件，那么直接用原来的view就可以了；不需要在viewholder中在做什么操作了
    private class MyHeaderViewHolder extends RecyclerView.ViewHolder {
        public MyHeaderViewHolder(CouponsDetailsHeader itemView) {
            super(itemView);
        }
    }

    public interface ItemClickListner {
        void onItemClick(String productId);
    }
}
