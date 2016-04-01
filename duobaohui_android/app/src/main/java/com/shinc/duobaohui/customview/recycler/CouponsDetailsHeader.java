package com.shinc.duobaohui.customview.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;

/**
 * @作者: efort
 * @日期: 15/12/21 - 11:51
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsDetailsHeader extends LinearLayout {
    private Context context;
    private ImageView headerImg;
    private TextView couponsName;
    private TextView couponsDeadline;
    private TextView couponsValue;
    private TextView couponsTopRange;
    private TextView couponsMoney;
    private ImageView couponsEnable;
    private ImageView couponsUnable;
    private TextView couponsType;

    public CouponsDetailsHeader(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    private TextView couponsRange;

    public CouponsDetailsHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public CouponsDetailsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.coupons_details_header, this);

        couponsMoney = (TextView) view.findViewById(R.id.coupons_details_money);
        couponsName = (TextView) view.findViewById(R.id.coupons_details_name);
        couponsDeadline = (TextView) view.findViewById(R.id.coupons_details_deadline);
        couponsValue = (TextView) view.findViewById(R.id.coupons_details_value);
        couponsRange = (TextView) view.findViewById(R.id.coupons_details_range);
        couponsTopRange = (TextView) view.findViewById(R.id.coupons_details_top_range);

        couponsEnable = (ImageView) view.findViewById(R.id.coupons_details_img);
        couponsUnable = (ImageView) view.findViewById(R.id.coupons_item_img2);
        couponsType = (TextView) view.findViewById(R.id.coupons_details_type);
    }

    public void initData(int type, String money, String couponsNameText, String couponsDeadlineText, String couponsValueText, String couponsRangeTopText, String couponsRangeText) {

        couponsMoney.setText(money);

        couponsName.setText(couponsNameText);
        couponsDeadline.setText(couponsDeadlineText);
        couponsValue.setText(couponsValueText);
        couponsTopRange.setText(couponsRangeTopText);

        couponsRange.setText(couponsRangeText);
        switch (type) {
            case 0:

                break;
            case 1:
                couponsMoney.setTextColor(context.getResources().getColor(R.color.c_efe4b6));
                couponsEnable.setVisibility(GONE);
                couponsUnable.setVisibility(VISIBLE);
                couponsType.setVisibility(VISIBLE);
                couponsType.setText("已使用");
                break;
            case 2:
                couponsMoney.setTextColor(context.getResources().getColor(R.color.c_efe4b6));
                couponsEnable.setVisibility(GONE);
                couponsUnable.setVisibility(VISIBLE);
                couponsType.setVisibility(VISIBLE);
                couponsType.setText("已过期");
                break;
        }
    }
}
