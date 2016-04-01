package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.WaitPublishBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.customview.counttime.CountdownView;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.utils.ImageLoad;

import java.util.List;

/**
 * Created by liugaopo on 15/9/29.
 * 等待揭晓的适配器
 */
public class WaitPublishGridListAdapter extends BaseAdapter {
    private Activity mActivity;
    private OpenedItemViewHolder openedItemViewHolder;

    private WaitingActiveHolder waitingActiveHoler;
    private List<WaitPublishBean> list;
    private DisplayImageOptions options;


    public List<WaitPublishBean> getList() {
        return list;
    }

    public void setList(List<WaitPublishBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public WaitPublishGridListAdapter(Activity mActivity, List list) {
        this.mActivity = mActivity;
        this.list = list;
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
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(list.get(position).getLeft_second())) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case 0:
                    convertView = mActivity.getLayoutInflater().inflate(R.layout.waiting_lottery_grid_item, null);
                    waitingActiveHoler = new WaitingActiveHolder(convertView);
                    convertView.setTag(waitingActiveHoler);
                    break;
                case 1:
                    convertView = mActivity.getLayoutInflater().inflate(R.layout.waiting_lottery_opened_grid_item, null);
                    openedItemViewHolder = new OpenedItemViewHolder(convertView);
                    convertView.setTag(openedItemViewHolder);
                    break;
            }
        } else {
            switch (type) {
                case 0:
                    waitingActiveHoler = (WaitingActiveHolder) convertView.getTag();
                    break;
                case 1:
                    openedItemViewHolder = (OpenedItemViewHolder) convertView.getTag();
                    break;
            }
        }

        final WaitPublishBean waitPublishBean = list.get(position);
        switch (type) {
            case 0:
                waitingActiveHoler.proName.setText("第" + waitPublishBean.getPeriod_number() + "期" + " " + waitPublishBean.getGoods_name());//设置数据；

                if (!TextUtils.isEmpty(waitPublishBean.getGoods_img())) {
                    ImageLoad.getInstance(mActivity).setImageToView(waitPublishBean.getGoods_img() + Constant.QINIUSCALE75, waitingActiveHoler.prodImg);
                } else {
                    waitingActiveHoler.prodImg.setImageResource(R.drawable.pic_listnopic);
                }

                waitingActiveHoler.time.setTag(position);

                if (waitPublishBean.isEnd()) {
                    waitingActiveHoler.endTimeLayout.setVisibility(View.VISIBLE);
                    waitingActiveHoler.countDownLayout.setVisibility(View.GONE);
                } else {
                    Long lastTime = System.currentTimeMillis();
                    waitingActiveHoler.time.start(Long.parseLong(waitPublishBean.getEndTime()) - lastTime);
//                    waitingActiveHoler.time.setEndTime(Long.parseLong(waitPublishBean.getEndTime()));

                    if (waitingActiveHoler.time.getHour() > 0) {
                        waitingActiveHoler.time.customTimeShow(false, true, true, true, false);
                    } else {
                        waitingActiveHoler.time.customTimeShow(false, false, true, true, true);
                    }
                    waitingActiveHoler.endTimeLayout.setVisibility(View.GONE);
                    waitingActiveHoler.countDownLayout.setVisibility(View.VISIBLE);

                    waitingActiveHoler.time.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                        @Override
                        public void onEnd(CountdownView cv) {
                            Object tag = cv.getTag();
                            if (null != tag) {
                                waitPublishBean.setIsEnd(true);
                                notifyDataSetChanged();
                            }
                        }
                    });
                }

                break;
            case 1:

                if (!TextUtils.isEmpty(waitPublishBean.getGoods_img().trim())) {
                    ImageLoader.getInstance().displayImage(waitPublishBean.getGoods_img() + Constant.QINIU320_220, openedItemViewHolder.productImg, options);

                } else {
                    openedItemViewHolder.productImg.setImageResource(R.drawable.pic_listnopic);
                }
                openedItemViewHolder.productName.setText("第" + waitPublishBean.getPeriod_number() + "期" + " " + waitPublishBean.getGoods_name());
                if (waitPublishBean.getUser() != null) {
                    if (waitPublishBean.getUser().getTimes() != null) {
                        openedItemViewHolder.takePartTimes.setText(waitPublishBean.getUser().getTimes());
                    }
                    if (waitPublishBean.getUser().getNick_name() != null) {
                        openedItemViewHolder.winnerName.setText(waitPublishBean.getUser().getNick_name());
                    }
                    if (waitPublishBean.getLuck_code() != null) {
                        openedItemViewHolder.luckCode.setText(waitPublishBean.getLuck_code());
                    }
                    if (waitPublishBean.getPre_luck_code_create_time() != null) {
                        openedItemViewHolder.openTime.setText(waitPublishBean.getPre_luck_code_create_time());
                    }
                }
                break;


        }

        return convertView;
    }

    class OpenedItemViewHolder {
        CustomTextView productName;
        CustomTextView takePartTimes;
        CustomTextView winnerName;
        CustomTextView luckCode;
        CustomTextView openTime;
        ImageView productImg;


        OpenedItemViewHolder(View view) {

            productImg = (ImageView) view.findViewById(R.id.product_img);
            productName = (CustomTextView) view.findViewById(R.id.product_name);
            winnerName = (CustomTextView) view.findViewById(R.id.winner_name);
            takePartTimes = (CustomTextView) view.findViewById(R.id.take_part_times);
            luckCode = (CustomTextView) view.findViewById(R.id.winner_luck_code);
            openTime = (CustomTextView) view.findViewById(R.id.open_time);

        }
    }

    class WaitingActiveHolder {
        CustomTextView proName;
        ImageView prodImg;
        CountdownView time;
        RelativeLayout countDownLayout;
        RelativeLayout endTimeLayout;

        WaitingActiveHolder(View view) {
            proName = (CustomTextView) view.findViewById(R.id.product_name);

            prodImg = (ImageView) view.findViewById(R.id.product_img);

            time = (CountdownView) view.findViewById(R.id.count_down_time);


            countDownLayout = (RelativeLayout) view.findViewById(R.id.time_count_down_layout);

            endTimeLayout = (RelativeLayout) view.findViewById(R.id.end_time_layout);
        }

    }
}
