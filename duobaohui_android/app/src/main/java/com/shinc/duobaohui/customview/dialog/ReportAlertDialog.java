package com.shinc.duobaohui.customview.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.ReportReasonBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.utils.DensityUtil;

import java.util.ArrayList;

/**
 * 名称：ReportAlertDialog
 * 作者：zhaopl 时间: 15/11/18.
 * 实现的主要功能：
 */
public class ReportAlertDialog {

    private Activity mActivity;

    private View view;

    private TextView confirmBtn;
    private TextView cancelBtn;
    private ListView reportReasonListView;

    private ReportReasonAdapter reportReasonAdapter;

    private AlertDialog alertDialog;


    private DialogButtonClickListener dialogButtonClickListener;


    public void setDialogButtonClickListener(DialogButtonClickListener dialogButtonClickListener) {
        this.dialogButtonClickListener = dialogButtonClickListener;
    }

    private ArrayList<ReportReasonBean> reasonList;

    public ReportAlertDialog(Activity mActivity) {
        this.mActivity = mActivity;
        initReasonData();
        initContentView();
        initAlertDialog();

        initListener();
    }

    private void initListener() {
//        reportReasonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //todo 点击举报原因；
//                for (int i = 0; i < reasonList.size(); i++) {
//
//                    if (i == position) {
//                        reasonList.get(i).setIsChecked("1");
//                    } else {
//                        reasonList.get(i).setIsChecked("0");
//                    }
//                }
//
//                reportReasonAdapter.notifyDataSetChanged();
//
//            }
//        });
    }

    /**
     * 初始化举报原因的数据；
     */
    private void initReasonData() {
        if (reasonList == null || reasonList.size() == 0) {
            reasonList = new ArrayList<>();
            reasonList.add(new ReportReasonBean("1", "0", "暴力色情"));
            reasonList.add(new ReportReasonBean("2", "0", "谣言欺诈"));
            reasonList.add(new ReportReasonBean("3", "0", "反动言论"));
            reasonList.add(new ReportReasonBean("4", "0", "恶意辱骂"));
            reasonList.add(new ReportReasonBean("5", "0", "侵权违规"));
        }


        reportReasonAdapter = new ReportReasonAdapter();

    }

    /**
     * 初始化View；
     */
    private void initContentView() {

        LayoutInflater inflater = mActivity.getLayoutInflater();
        view = inflater.inflate(R.layout.report_dialog_layout, null);

        confirmBtn = (TextView) view.findViewById(R.id.confirm_btn);
        cancelBtn = (TextView) view.findViewById(R.id.cancel_btn);
        reportReasonListView = (ListView) view.findViewById(R.id.report_reason_list);
        reportReasonListView.setFocusable(true);

        reportReasonListView.setAdapter(reportReasonAdapter);


    }

    public boolean isShow() {
        if (alertDialog != null) {

            return alertDialog.isShowing();
        } else {
            initAlertDialog();
            return true;
        }
    }


    public void showDialog() {
        if (alertDialog != null) {
            alertDialog.show();
        } else {
            initAlertDialog();
        }
    }

    /**
     * 初始化AlertDialog;
     */
    private void initAlertDialog() {

        alertDialog = new AlertDialog.Builder(mActivity)
                .create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams params = alertDialog.getWindow()
                .getAttributes();

//        params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;

//        int w = DensityUtil.dip2px(mActivity, 240);
//        int h = DensityUtil.dip2px(mActivity, 325);
        params.width = DensityUtil.dip2px(mActivity, 240);
//        params.height =;

        alertDialog.getWindow().setAttributes(params);
        window.setContentView(view);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 确定；

                if (verifyReport()) {
                    if (dialogButtonClickListener != null) {

                        dialogButtonClickListener.onConfirmClick(getConfirmReason());
                    }

                    if (alertDialog != null) {


                        alertDialog.dismiss();
                        resetReason();
                    }
                } else {
                    Toast.makeText(mActivity, "请选择原因", Toast.LENGTH_SHORT).show();
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 取消；

                if (dialogButtonClickListener != null) {
                    dialogButtonClickListener.onCancelClick();
                }
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    resetReason();
                }
            }
        });
    }

    /**
     * 获取到选择的原因Bean;
     *
     * @return
     */
    private ReportReasonBean getConfirmReason() {
        for (int i = 0; i < reasonList.size(); i++) {
            if ("1".equals(reasonList.get(i).getIsChecked())) {
                return reasonList.get(i);
            }
        }
        return null;
    }

    /**
     * 判断是否选择了原因；
     *
     * @return
     */
    private boolean verifyReport() {

        for (int i = 0; i < reasonList.size(); i++) {

            if ("1".equals(reasonList.get(i).getIsChecked())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 复位信息；
     */
    private void resetReason() {

        for (int i = 0; i < reasonList.size(); i++) {
            reasonList.get(i).setIsChecked("0");
        }
    }


    class ReportReasonAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return reasonList.size();
        }

        @Override
        public Object getItem(int position) {
            return reasonList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mActivity.getLayoutInflater().inflate(R.layout.report_reason_list_item_layout, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if ("1".equals(reasonList.get(position).getIsChecked())) {
                viewHolder.checkBox.setVisibility(View.VISIBLE);
                viewHolder.reasonBg.setBackground(convertView.getResources().getDrawable(R.drawable.report_reason_bg_select));
                viewHolder.reason.setTextColor(convertView.getResources().getColor(R.color.white));
            } else {
                viewHolder.checkBox.setVisibility(View.GONE);
                viewHolder.reasonBg.setBackground(convertView.getResources().getDrawable(R.drawable.report_reason_bg));
                viewHolder.reason.setTextColor(convertView.getResources().getColor(R.color.c_666666));
            }

            viewHolder.reason.setText(reasonList.get(position).getName());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo 点击举报原因；
                    for (int i = 0; i < reasonList.size(); i++) {

                        if (i == position) {
                            reasonList.get(i).setIsChecked("1");
                        } else {
                            reasonList.get(i).setIsChecked("0");
                        }
                    }

                    reportReasonAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            CustomTextView reason;
            ImageView checkBox;
            RelativeLayout reasonBg;

            ViewHolder(View view) {
                reason = (CustomTextView) view.findViewById(R.id.reason_text);
                checkBox = (ImageView) view.findViewById(R.id.report_check_box);
                reasonBg = (RelativeLayout) view.findViewById(R.id.reason_bg);
            }
        }
    }


    public interface DialogButtonClickListener {

        void onConfirmClick(ReportReasonBean reportReasonBean);

        void onCancelClick();
    }

}
