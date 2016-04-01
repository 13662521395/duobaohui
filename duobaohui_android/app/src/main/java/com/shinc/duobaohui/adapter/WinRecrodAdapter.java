package com.shinc.duobaohui.adapter;

import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.WinListBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugaopo on 15/11/17.
 */
public class WinRecrodAdapter extends BaseAdapter {
    private BaseActivity mActivity;
    private ViewUserWinHolder winHolder;
    private List<WinListBean.WinListChildData> winListBeans;

    private DisplayImageOptions options;

    public List<WinListBean.WinListChildData> getWinListBeans() {
        return winListBeans;
    }

    public void setWinListBeans(List<WinListBean.WinListChildData> winListBeans) {
        this.winListBeans.clear();
        this.winListBeans.addAll(winListBeans);
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.pic_listnopic)
                .showStubImage(R.drawable.pic_listnopic)
                .showImageForEmptyUri(R.drawable.pic_listnopic)
                .showImageOnFail(R.drawable.pic_listnopic)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)//设置为RGB565比起默认的ARGB_8888要节省大量的内存
                .delayBeforeLoading(100)//载入图片前稍做延时可以提高整体滑动的流畅度
                .build();
    }

    public WinRecrodAdapter(BaseActivity mActivity) {
        this.mActivity = mActivity;
        this.winListBeans = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return getWinListBeans().size();
    }

    @Override
    public Object getItem(int position) {
        return getWinListBeans().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = mActivity.getLayoutInflater().inflate(R.layout.win_recrod_item_layout, null);
            winHolder = new ViewUserWinHolder(convertView);
            convertView.setTag(winHolder);
        } else {
            winHolder = (ViewUserWinHolder) convertView.getTag();
        }
        WinListBean.WinListChildData childData = getWinListBeans().get(position);

        if (TextUtils.isEmpty(childData.getGoods_img())) {
            winHolder.img.setImageResource(R.drawable.icon_nopic);
        } else {
//            ImageLoad.getInstance(mActivity).setImageToView(childData.getGoods_img(), winHolder.img);
            ImageLoader.getInstance().displayImage(childData.getGoods_img(), winHolder.img, options);
        }
        winHolder.statue.setText(childData.getStatus());
        if (TextUtils.isEmpty(childData.getGoods_name())) {
            winHolder.name.setText("");
        } else {
            winHolder.name.setText("(第" + childData.getPeriod_number() + "期)"+childData.getGoods_name());
        }
        if (TextUtils.isEmpty(childData.getReal_need_times())) {
            winHolder.total_num.setText("共需人次: ");
        } else {
            winHolder.total_num.setText(Html.fromHtml("共需人次: <font color='#ff5a5a'>" + childData.getReal_need_times() + "</font>"));
        }
        if (TextUtils.isEmpty(childData.getTotal_times())) {
            winHolder.period_num.setText("本期参与: ");
        } else {
            winHolder.period_num.setText(Html.fromHtml("本期参与: <font color='#ff5a5a'>" + childData.getTotal_times() + "</font>"));
        }
        if (TextUtils.isEmpty(childData.getLuck_code())) {
            winHolder.luncky_code.setText("幸运号码: ");
        } else {
            winHolder.luncky_code.setText(Html.fromHtml("幸运号码: <font color='#ff5a5a'>" + childData.getLuck_code() + "</font>"));
        }
        if (TextUtils.isEmpty(childData.getLuck_code_create_time())) {
            winHolder.time.setText("揭晓时间: ");
        } else {
            winHolder.time.setText("揭晓时间: " + childData.getLuck_code_create_time());
        }
        return convertView;
    }

    class ViewUserWinHolder {
        ImageView img;
        CustomTextView statue;
        CustomTextView name;
        CustomTextView total_num;
        CustomTextView period_num;
        CustomTextView luncky_code;
        CustomTextView time;

        ViewUserWinHolder(View view) {

            img = (ImageView) view.findViewById(R.id.win_recrod_item_layout_img);
            statue = (CustomTextView) view.findViewById(R.id.win_recrod_item_layout_statue);
            name = (CustomTextView) view.findViewById(R.id.win_recrod_item_layout_name);
            total_num = (CustomTextView) view.findViewById(R.id.win_recrod_item_layout_total_num);
            period_num = (CustomTextView) view.findViewById(R.id.win_recrod_item_layout_period_num);
            luncky_code = (CustomTextView) view.findViewById(R.id.win_recrod_item_layout_luncky_code);
            time = (CustomTextView) view.findViewById(R.id.win_recrod_item_layout_time);
        }

    }
}
