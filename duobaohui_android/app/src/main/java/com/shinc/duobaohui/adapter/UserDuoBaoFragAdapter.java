package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shinc.duobaohui.DuoBaoRecrodDetailActivity;
import com.shinc.duobaohui.ProductDetailActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.UserDuoBaoBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.customview.imp.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugaopo on 15/9/29.
 * 夺宝纪录
 */
public class UserDuoBaoFragAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<UserDuoBaoBean.UserDuoBaoChildData> crodChildBeans;
    final int VIEW_TYPE = 2;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    private DuoBaoRecordViewHolder duoBaoRecordViewHolder1;
    private DuoBaoRecordViewHolder2 duoBaoRecordViewHolder2;
    private DisplayImageOptions options;

    public UserDuoBaoFragAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        this.crodChildBeans = new ArrayList<>();
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

    public List<UserDuoBaoBean.UserDuoBaoChildData> getCrodChildBeans() {
        return crodChildBeans;
    }

    public void setCrodChildBeans(List<UserDuoBaoBean.UserDuoBaoChildData> crodChildBeans) {
        this.crodChildBeans.clear();
        this.crodChildBeans.addAll(crodChildBeans);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.getCrodChildBeans().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        if (getCrodChildBeans().get(position).getResult_user_id() == null) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        if (convertView == null) {

            switch (type) {
                case TYPE_1:
                    convertView = mActivity.getLayoutInflater().inflate(R.layout.user_main_page_duobao_frag_item_layout, null);
                    duoBaoRecordViewHolder1 = new DuoBaoRecordViewHolder(convertView);
                    convertView.setTag(duoBaoRecordViewHolder1);
                    break;
                case TYPE_2:
                    convertView = mActivity.getLayoutInflater().inflate(R.layout.user_main_page_duobao_frag_itemb_layout, null);
                    duoBaoRecordViewHolder2 = new DuoBaoRecordViewHolder2(convertView);
                    convertView.setTag(duoBaoRecordViewHolder2);
                    break;
            }
        } else {
            switch (type) {
                case TYPE_1:
                    duoBaoRecordViewHolder1 = (DuoBaoRecordViewHolder) convertView.getTag();
                    break;
                case TYPE_2:
                    duoBaoRecordViewHolder2 = (DuoBaoRecordViewHolder2) convertView.getTag();
                    break;
            }
        }

        switch (type) {
            case TYPE_1:
                if (TextUtils.isEmpty(getCrodChildBeans().get(position).getGoods_img())) {
                    duoBaoRecordViewHolder1.imgUrl.setImageResource(R.drawable.icon_nopic);
                } else {
                    ImageLoader.getInstance().displayImage(getCrodChildBeans().get(position).getGoods_img() + Constant.QINIU270_270, duoBaoRecordViewHolder1.imgUrl, options);
                }
                duoBaoRecordViewHolder1.name.setText("(第" + getCrodChildBeans().get(position).getPeriod_number() + "期)" + getCrodChildBeans().get(position).getGoods_name());
                duoBaoRecordViewHolder1.lienerLaoutFrequency.setText(Html.fromHtml("<font color=red >" + getCrodChildBeans().get(position).getTimes() + "</font><font color=#666666 > 人次</font>"));
                duoBaoRecordViewHolder1.layouCutNum.setText("剩余:" + getCrodChildBeans().get(position).getRemain_times());
                duoBaoRecordViewHolder1.layouTotalNum.setText("共需: " + getCrodChildBeans().get(position).getReal_need_times());
                duoBaoRecordViewHolder1.progressBar.setProgress(Integer.parseInt(getCrodChildBeans().get(position).getProgress()));

                if (Integer.parseInt(getCrodChildBeans().get(position).getProgress()) == 100) {
                    duoBaoRecordViewHolder1.beingRevealed.setVisibility(View.VISIBLE);
                    duoBaoRecordViewHolder1.addMai.setBackgroundResource(R.drawable.red_gray_btn);
                    duoBaoRecordViewHolder1.addMai.setEnabled(false);
                } else {
                    duoBaoRecordViewHolder1.beingRevealed.setVisibility(View.GONE);
                    duoBaoRecordViewHolder1.addMai.setEnabled(true);
                }

                duoBaoRecordViewHolder1.searchDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mActivity, DuoBaoRecrodDetailActivity.class);
                        intent.putExtra("user_id", getCrodChildBeans().get(position).getUser_id());
                        intent.putExtra("Goods_name", getCrodChildBeans().get(position).getGoods_name());
                        intent.putExtra("Times", getCrodChildBeans().get(position).getTimes());
                        intent.putExtra("sh_activity_period_id", getCrodChildBeans().get(position).getPeriod_id());
                        mActivity.startActivity(intent);
                    }
                });
                duoBaoRecordViewHolder1.imgUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mActivity, ProductDetailActivity.class);
                        intent.putExtra("PRODUCTID", getCrodChildBeans().get(position).getPeriod_id());
                        mActivity.startActivity(intent);
                    }
                });
                duoBaoRecordViewHolder1.addMai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mActivity, ProductDetailActivity.class);
                        intent.putExtra("PRODUCTID", getCrodChildBeans().get(position).getPeriod_id());
                        intent.putExtra("TYPE", "SHOWCOMMITORDER");//传递此参数跳转到详情页面，加载完数据后，立即显示购买数量dialog;
                        mActivity.startActivity(intent);
                    }
                });
                break;
            case TYPE_2:
                if (TextUtils.isEmpty(getCrodChildBeans().get(position).getGoods_img())) {
                    duoBaoRecordViewHolder2.imgUrl.setImageResource(R.drawable.icon_nopic);
                } else {
                    ImageLoader.getInstance().displayImage(getCrodChildBeans().get(position).getGoods_img() + Constant.QINIU270_270, duoBaoRecordViewHolder2.imgUrl, options);
                }
                duoBaoRecordViewHolder2.name.setText("(第" + getCrodChildBeans().get(position).getPeriod_number() + "期)" + getCrodChildBeans().get(position).getGoods_name());
                duoBaoRecordViewHolder2.lienerLaoutFrequency.setText(Html.fromHtml("<font color=red >" + getCrodChildBeans().get(position).getTimes() + "</font><font color=#666666 > 人次</font>"));
                duoBaoRecordViewHolder2.layouTotalNum.setText("共需: " + getCrodChildBeans().get(position).getReal_need_times());
                duoBaoRecordViewHolder2.winneNumr.setText(getCrodChildBeans().get(position).getResult_user_name());
                duoBaoRecordViewHolder2.periodInM.setText(getCrodChildBeans().get(position).getResult_user_sum());
                duoBaoRecordViewHolder2.lunckyNumber.setText(getCrodChildBeans().get(position).getLuck_code());
                duoBaoRecordViewHolder2.outOfTime.setText(getCrodChildBeans().get(position).getLuck_code_create_time());
                duoBaoRecordViewHolder2.searchDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mActivity, DuoBaoRecrodDetailActivity.class);
                        intent.putExtra("user_id", getCrodChildBeans().get(position).getUser_id());
                        intent.putExtra("Goods_name", getCrodChildBeans().get(position).getGoods_name());
                        intent.putExtra("Times", getCrodChildBeans().get(position).getTimes());
                        intent.putExtra("sh_activity_period_id", getCrodChildBeans().get(position).getPeriod_id());
                        mActivity.startActivity(intent);
                    }
                });
                duoBaoRecordViewHolder2.imgUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mActivity, ProductDetailActivity.class);
                        intent.putExtra("PRODUCTID", getCrodChildBeans().get(position).getPeriod_id());
                        mActivity.startActivity(intent);
                    }
                });
                break;
        }


        return convertView;
    }

    //进行中
    class DuoBaoRecordViewHolder {
        ImageView imgUrl;//图片
        CustomTextView name;//名字
        ProgressBar progressBar;//进度
        CustomTextView layouTotalNum;//共需名额
        CustomTextView layouCutNum;//剩余名额
        CustomTextView lienerLaoutFrequency;//参与次数
        CustomTextView searchDetail;
        CustomTextView addMai;
        CustomTextView beingRevealed;

        public DuoBaoRecordViewHolder(View view) {
            imgUrl = (ImageView) view.findViewById(R.id.user_main_page_duobao_frag_itema_layout_img);
            name = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itema_layout_name);
            progressBar = (ProgressBar) view.findViewById(R.id.user_main_page_duobao_frag_itema_layout_progressbar);
            layouTotalNum = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itema_layout_layoutTotalNum);
            layouCutNum = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itema_layout_layoutCutNum);
            lienerLaoutFrequency = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itema_layout_lienerLaoutFrequency);
            searchDetail = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itema_layout_lienerLaoutSearchInfo);
            addMai = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itema_layout_addMai);
            beingRevealed = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itema_layout_Beingrevealed);

        }
    }

    //已完成
    class DuoBaoRecordViewHolder2 {
        ImageView imgUrl;//图片
        CustomTextView name;//名字
        CustomTextView layouTotalNum;//共需名额
        CustomTextView lienerLaoutFrequency;//参与次数
        CustomTextView searchDetail;
        CustomTextView winneNumr;
        CustomTextView periodInM;
        CustomTextView lunckyNumber;
        CustomTextView outOfTime;

        public DuoBaoRecordViewHolder2(View view) {
            imgUrl = (ImageView) view.findViewById(R.id.user_main_page_duobao_frag_itemb_layout_img);
            name = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itemb_layout_name);
            layouTotalNum = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itemb_layout_layoutTotalNum);
            lienerLaoutFrequency = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itemb_layout_lienerLaoutFrequency);
            searchDetail = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itemb_layout_lienerLaoutSearchInfo);
            winneNumr = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itemb_layout_winner);
            periodInM = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itemb_layout_periodInM);
            lunckyNumber = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itemb_layout_lunckyNumber);
            outOfTime = (CustomTextView) view.findViewById(R.id.user_main_page_duobao_frag_itemb_layout_outLunckyNumber);
        }
    }
}
