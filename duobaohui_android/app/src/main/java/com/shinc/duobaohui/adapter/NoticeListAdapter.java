package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.NoticeListBean;

import java.util.List;

/**
 * Created by yangtianhe on 15/11/20.
 *  通知的 adapter
 */
public class NoticeListAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<NoticeListBean.NoticeChildListBean> listChildBeans;
    private LayoutInflater inflater;

    public NoticeListAdapter(Activity mActivity, List<NoticeListBean.NoticeChildListBean> listChildBeans) {
        this.mActivity = mActivity;
        this.listChildBeans = listChildBeans;
//        this.inflater = LayoutInflater.from(mActivity);
    }

    @Override
    public int getCount() {
        return listChildBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return listChildBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_notice, null);
//            convertView = inflater.inflate(R.layout.item_notice, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.time.setText(listChildBeans.get(position).getCreate_time());
        viewHolder.title.setText(listChildBeans.get(position).getTitle());

        return convertView;

    }

    class ViewHolder {
        private TextView title;
        private TextView time;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.notice_title);
            time = (TextView) view.findViewById(R.id.notice_time);
        }
    }

}
