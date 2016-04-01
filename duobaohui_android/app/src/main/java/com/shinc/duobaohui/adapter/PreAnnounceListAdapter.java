package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shinc.duobaohui.ProductDetailActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.UserMainPageActivity;
import com.shinc.duobaohui.bean.PreAnnounceBean;
import com.shinc.duobaohui.bean.TakePartBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.customview.imp.CircleImageView;
import com.shinc.duobaohui.customview.imp.CustomTextView;

import java.util.List;

/**
 * Created by liugaopo on 15/9/29.
 * 往期揭晓页面listView的 适配器
 */
public class PreAnnounceListAdapter extends BaseAdapter {
    private Activity mActivity;
    private AddressItemViewHolder addressItemViewHolder;

    private WaitingActiveHoler waitingActiveHoler;
    private List<PreAnnounceBean> list;

    private DisplayImageOptions options;

    public List<PreAnnounceBean> getList() {
        return list;
    }

    public void setList(List<PreAnnounceBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public PreAnnounceListAdapter(Activity mActivity, List<PreAnnounceBean> list) {
        this.mActivity = mActivity;
        this.list = list;
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_head_small)
                .showStubImage(R.drawable.icon_head_small)
                .showImageForEmptyUri(R.drawable.icon_head_small)
                .showImageOnFail(R.drawable.icon_head_small)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)//设置为RGB565比起默认的ARGB_8888要节省大量的内存
                .delayBeforeLoading(100)//载入图片前稍做延时可以提高整体滑动的流畅度
                .build();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PreAnnounceBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {

        if ("0".equals(list.get(position).getIs_winninghistory())) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {

            switch (type) {
                case 0:
                    convertView = mActivity.getLayoutInflater().inflate(R.layout.pre_announce_item_header, null);
                    waitingActiveHoler = new WaitingActiveHoler(convertView);
                    convertView.setTag(waitingActiveHoler);
                    break;
                case 1:
                    convertView = mActivity.getLayoutInflater().inflate(R.layout.pre_announce_item, null);
                    addressItemViewHolder = new AddressItemViewHolder(convertView);
                    convertView.setTag(addressItemViewHolder);
                    break;
            }
        } else {

            switch (type) {
                case 0:
                    waitingActiveHoler = (WaitingActiveHoler) convertView.getTag();
                    break;
                case 1:
                    addressItemViewHolder = (AddressItemViewHolder) convertView.getTag();
                    break;
            }
        }

        final PreAnnounceBean preAnnounceBean = list.get(position);
        switch (type) {
            case 0:

                waitingActiveHoler.waitActiveId.setText("第" + preAnnounceBean.getPeriod_number() + "期");//设置数据；

                waitingActiveHoler.gotoCurrent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo
                        Intent intent = new Intent(mActivity, ProductDetailActivity.class);
                        intent.putExtra("PRODUCTID", list.get(position).getSh_activity_period_id());
                        mActivity.startActivity(intent);
                    }
                });
                break;
            case 1:

                addressItemViewHolder.line.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                if (!TextUtils.isEmpty(preAnnounceBean.getHead_pic().trim())) {//判断是否为null,或者是“”；
//                    ImageLoad.getInstance(mActivity).setImageToView(preAnnounceBean.getHead_pic() + Constant.QINIU80_80, addressItemViewHolder.winnerHeader);
                    ImageLoader.getInstance().displayImage(preAnnounceBean.getHead_pic() + Constant.QINIU80_80, addressItemViewHolder.winnerHeader, options);
                } else {
                    addressItemViewHolder.winnerHeader.setImageResource(R.drawable.icon_head_small);
                }
                addressItemViewHolder.nickName.setText(preAnnounceBean.getNick_name());
                addressItemViewHolder.winnerUserId.setText(preAnnounceBean.getUser_id() + "(唯一不变标识)");
                addressItemViewHolder.winnerLuckCode.setText(preAnnounceBean.getLuck_code());
                addressItemViewHolder.winnerTakePartNum.setText(preAnnounceBean.getTimes());
                addressItemViewHolder.openTime.setText("揭晓时间：" + preAnnounceBean.getPre_luck_code_create_time());
                addressItemViewHolder.activeNum.setText("第" + preAnnounceBean.getPeriod_number() + "期");

                addressItemViewHolder.gotoCurrent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo 调转到哪一期；
                        Intent intent = new Intent(mActivity, ProductDetailActivity.class);
                        intent.putExtra("PRODUCTID", list.get(position).getSh_activity_period_id());
                        mActivity.startActivity(intent);
                    }
                });

                addressItemViewHolder.gotoTa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo 跳转到ta的主页；
                        Intent intent = new Intent();
                        intent.setClass(mActivity, UserMainPageActivity.class);
                        TakePartBean bean = new TakePartBean(preAnnounceBean.getUser_id(), preAnnounceBean.getNick_name(), preAnnounceBean.getTimes(), preAnnounceBean.getPre_luck_code_create_time(), preAnnounceBean.getHead_pic(), preAnnounceBean.getSh_activity_period_id(), preAnnounceBean.getIp(), preAnnounceBean.getIp_address());
                        intent.putExtra("takePart", bean);
                        mActivity.startActivity(intent);
                    }
                });
                break;

        }

        return convertView;
    }

    class AddressItemViewHolder {
        CustomTextView nickName;
        CustomTextView winnerUserId;
        CustomTextView winnerLuckCode;
        CustomTextView winnerTakePartNum;
        CustomTextView openTime;
        CustomTextView activeNum;
        CircleImageView winnerHeader;
        LinearLayout gotoCurrent;
        RelativeLayout gotoTa;
        View line;


        AddressItemViewHolder(View view) {
            winnerHeader = (CircleImageView) view.findViewById(R.id.pre_announce_user_header);
            winnerUserId = (CustomTextView) view.findViewById(R.id.winner_user_id);
            winnerLuckCode = (CustomTextView) view.findViewById(R.id.winner_user_luck_code);
            winnerTakePartNum = (CustomTextView) view.findViewById(R.id.winner_user_take_part_num);
            nickName = (CustomTextView) view.findViewById(R.id.winner_nick_name);
            openTime = (CustomTextView) view.findViewById(R.id.open_time);
            activeNum = (CustomTextView) view.findViewById(R.id.active_id);
            gotoCurrent = (LinearLayout) view.findViewById(R.id.goto_current_layout);
            gotoTa = (RelativeLayout) view.findViewById(R.id.goto_Ta);
            line = view.findViewById(R.id.cut_line);
        }
    }

    class WaitingActiveHoler {
        CustomTextView waitActiveId;
        LinearLayout gotoCurrent;

        WaitingActiveHoler(View view) {
            waitActiveId = (CustomTextView) view.findViewById(R.id.active_id);
            gotoCurrent = (LinearLayout) view.findViewById(R.id.goto_current_layout);

        }
    }
}
