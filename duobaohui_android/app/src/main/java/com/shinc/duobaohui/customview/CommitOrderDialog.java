package com.shinc.duobaohui.customview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.utils.DensityUtil;

/**
 * 名称：CommitOrderDialog
 * 作者：zhaopl 时间: 15/11/4.
 * 实现的主要功能：
 */
@SuppressWarnings("ALL")
public class CommitOrderDialog extends Dialog {

    private LayoutInflater inflater;

    private CommitOrderListener commitOrderListener;

    public void setCommitOrderListener(CommitOrderListener commitOrderListener) {
        this.commitOrderListener = commitOrderListener;
    }

    private View view;

    private CustomTextView addBtn;

    private CustomTextView minBtn;

    private EditText buyNum;

    private int maxNum;

    private CustomTextView commitOrderBtn;


    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public void setBuyNum(String num) {
        if (buyNum != null) {
            if ("0".equals(num)) {
                buyNum.setEnabled(false);
                buyNum.setText("0");
            } else {
                buyNum.setText(num);
                buyNum.setSelection(buyNum.getText().toString().length());
            }
        }
    }

    public CommitOrderDialog(Context context, int maxNum, String productId) {
        super(context);
        this.maxNum = maxNum;


        initView(context);

        if (maxNum < 1) {
            buyNum.setEnabled(false);
            buyNum.setText("0");
        } else {
            buyNum.setEnabled(true);
        }
    }

    @SuppressLint("InflateParams")
    private void initView(Context context) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.commit_order_dialog_layout, null);

        minBtn = (CustomTextView) view.findViewById(R.id.num_min);
        addBtn = (CustomTextView) view.findViewById(R.id.num_add);
        buyNum = (EditText) view.findViewById(R.id.buy_num);
        setBuyNum("1");
        commitOrderBtn = (CustomTextView) view.findViewById(R.id.commit_order_btn);

        intListener();


        Window win = this.getWindow();
        Display display = win.getWindowManager().getDefaultDisplay();
        win.requestFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        win.setBackgroundDrawable(new ColorDrawable(R.color.white));
        WindowManager.LayoutParams lp = win.getAttributes();
        //noinspection deprecation
        lp.width = display.getWidth();
        lp.height = DensityUtil.dip2px(context, 125);
        lp.gravity = Gravity.BOTTOM;
        win.setAttributes(lp);

    }

    private void intListener() {


        commitOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 首先判断用户是否登录了，如果登录后才可以进行订单添加的操作；
                if (commitOrderListener != null) {
                    commitOrderListener.commit(buyNum.getText().toString().trim());
                }
            }
        });

        minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 减小购买的数据；

                int i;
                if (!TextUtils.isEmpty(buyNum.getText().toString().trim())) {
                    i = Integer.parseInt(buyNum.getText().toString().trim());
                } else {
                    i = 0;
                }
                if (i > 1) {
                    i--;
                    buyNum.setText(i + "");
                    buyNum.setSelection(buyNum.getText().toString().length());
                    addBtn.setEnabled(true);
//                        numAdd.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
                } else {
                    //Toast.makeText(mActivity, "购买数量不能小于1", Toast.LENGTH_SHORT).show();
                    minBtn.setEnabled(false);
                    buyNum.setText(i + "");
//                        numMin.setBackgroundColor(mActivity.getResources().getColor(R.color.c_efefef));
                }

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 增加购买的数量；
                int i;
                if (!TextUtils.isEmpty(buyNum.getText().toString().trim())) {
                    i = Integer.parseInt(buyNum.getText().toString().trim());

                } else {
                    i = 0;
                }

                if (i < maxNum)

                {
                    i++;
                    buyNum.setText(i + "");
                    buyNum.setSelection(buyNum.getText().toString().length());
                    minBtn.setEnabled(true);
//                            numMin.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
                } else

                {

                    addBtn.setEnabled(false);
//                            numAdd.setBackgroundColor(mActivity.getResources().getColor(R.color.c_efefef));
                    // Toast.makeText(mActivity, "购买数量不能超过剩余数量！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        buyNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = buyNum.getText().toString().trim();
                //noinspection StatementWithEmptyBody

                if (!TextUtils.isEmpty(value) && maxNum > 1) {

                    if (Integer.parseInt(value) < 1) {
                        setBuyNum("1");
                    } else if (Integer.parseInt(value) > maxNum) {
                        setBuyNum(maxNum + "");
                        addBtn.setEnabled(false);
                        minBtn.setEnabled(true);
                    } else {
                        addBtn.setEnabled(true);
                        minBtn.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                buyNum.setSelection(buyNum.getText().toString().length());
            }
        });
    }


    public interface CommitOrderListener {

        void commit(String num);//提交；

    }

}
