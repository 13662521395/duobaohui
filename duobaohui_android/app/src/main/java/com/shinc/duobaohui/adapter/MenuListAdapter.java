package com.shinc.duobaohui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.MenuListBean;
import com.shinc.duobaohui.utils.ImageLoad;

import java.util.ArrayList;

/**
 * 名称:BuyNumTwoLineAdapter
 * Created by chaos on 15/9/30.
 * 功能:详情页购买号码
 */

@SuppressWarnings("ALL")
public class MenuListAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<MenuListBean.CategoryMenu> list = new ArrayList<>();

    public MenuListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(ArrayList<MenuListBean.CategoryMenu> list) {
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

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.menu_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();

        if (!TextUtils.isEmpty(list.get(position).getImg_url().trim())) {
            ImageLoad.getInstance(mContext).setImageToView(list.get(position).getImg_url(), viewHolder.menuImg);
        } else {
            viewHolder.menuImg.setImageResource(R.drawable.pic_listnopic);

        }

        viewHolder.menuName.setText(list.get(position).getCat_name());
        return convertView;
    }


    class ViewHolder {
        private ImageView menuImg;
        private TextView menuName;

        public ViewHolder(View view) {
            menuImg = (ImageView) view.findViewById(R.id.menu_icon);
            menuName = (TextView) view.findViewById(R.id.menu_name);
        }
    }
}
