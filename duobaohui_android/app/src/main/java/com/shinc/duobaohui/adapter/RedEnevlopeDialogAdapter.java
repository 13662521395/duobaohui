package com.shinc.duobaohui.adapter;

import android.text.Html;
import android.text.TextPaint;
  import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.RedEnevlopeDialogbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugaopo on 15/12/18.
 *
 */
public class RedEnevlopeDialogAdapter extends BaseAdapter {
    private BaseActivity mActivity;
    private List<RedEnevlopeDialogbean.RedEnevlopeDialogChildBean> dialogbeanList;
    private RedEnevlopeViewHolder holder;

    public RedEnevlopeDialogAdapter(BaseActivity mActivity) {
        this.mActivity = mActivity;
        this.dialogbeanList = new ArrayList<>();
    }

    public List<RedEnevlopeDialogbean.RedEnevlopeDialogChildBean> getDialogbeanList() {
        return dialogbeanList;
    }

    public void setDialogbeanList(List<RedEnevlopeDialogbean.RedEnevlopeDialogChildBean> dialogbeanList) {
        this.dialogbeanList.clear();
        this.dialogbeanList.addAll(dialogbeanList);
    }

    @Override
    public int getCount() {
        return getDialogbeanList().size();
    }

    @Override
    public Object getItem(int position) {
        return getDialogbeanList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.red_enevlope_item_layout,  parent, false);
            holder = new RedEnevlopeViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RedEnevlopeViewHolder) convertView.getTag();
        }
        RedEnevlopeDialogbean.RedEnevlopeDialogChildBean redEnevlopeDialogChildBean = getDialogbeanList().get(position);

        holder.name.setText(redEnevlopeDialogChildBean.getRed_money_name());
        holder.imgPrice.setText(Html.fromHtml("<b>" + redEnevlopeDialogChildBean.getPrice().substring(0, 1) + "</b>"));
        holder.deadline.setText(redEnevlopeDialogChildBean.getSurplus());
        holder.effectiveTime.setText(redEnevlopeDialogChildBean.getOverdue_time());
        holder.applicableTypes.setText(redEnevlopeDialogChildBean.getConsumption());
        if (getDialogbeanList().get(position).getIs_default() != null && getDialogbeanList().get(position).getIs_default().equals("1")) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckbox(position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckbox(position);
            }

        });
        return convertView;
    }

    public void setCheckbox(int i) {
        for (int j = 0; j < getDialogbeanList().size(); j++) {

            if (j == i) {

                getDialogbeanList().get(j).setIs_default("1");

            } else {
                getDialogbeanList().get(j).setIs_default("0");
            }
        }

        notifyDataSetChanged();
    }

    class RedEnevlopeViewHolder {
        private CheckBox checkBox;
        private ImageView img;
        private TextView imgPrice;
        private TextView name;
        private TextView deadline;
        private TextView effectiveTime;
        private TextView applicableTypes;
        private TextView type;

        public RedEnevlopeViewHolder(View view) {
            checkBox = (CheckBox) view.findViewById(R.id.red_enevlope_team_check_box);
            img = (ImageView) view.findViewById(R.id.red_enevlope_team_img);
            imgPrice = (TextView) view.findViewById(R.id.red_enevlope_team_img_tv);
            TextPaint tp = imgPrice.getPaint();
            tp.setFakeBoldText(true);
            name = (TextView) view.findViewById(R.id.red_enevlope_team_name);
            deadline = (TextView) view.findViewById(R.id.red_enevlope_team_deadLine);
            effectiveTime = (TextView) view.findViewById(R.id.red_enevlope_team_effectiveTime);
            applicableTypes = (TextView) view.findViewById(R.id.red_enevlope_team_applicableTypes);
            type = (TextView) view.findViewById(R.id.red_enevlope_team_type);
        }
    }
}
