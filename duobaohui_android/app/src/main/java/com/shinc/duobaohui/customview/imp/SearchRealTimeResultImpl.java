package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.SearchRealTimeResultInterface;


/**
 * 名称：SearchRealTimeResultImpl
 * 作者：zhaopl 时间: 15/9/6.
 * 实现的主要功能：
 */
public class SearchRealTimeResultImpl extends RelativeLayout implements SearchRealTimeResultInterface {


    private Context context;
    private ListView listView;

    private TextView title;

    public SearchRealTimeResultImpl(Context context) {
        super(context);
        this.context = context;

        initView(context);
    }

    public SearchRealTimeResultImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public SearchRealTimeResultImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_real_time_view, this);

        listView = (ListView) findViewById(R.id.search_real_time_result_list_view);

        title = (TextView) findViewById(R.id.search_result_title);

    }

    @Override
    public void setShowOrHide(boolean flag) {

        if (flag) {
            this.setVisibility(VISIBLE);
        } else {
            this.setVisibility(GONE);
        }

    }

    @Override
    public void clickItem(final ClickItemListener clickItemListener) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickItemListener.onClickItem(position);
            }
        });
    }

    @Override
    public void setRealTimeSearchData(BaseAdapter baseAdapter) {
        if (listView.getAdapter() == null) {
            listView.setAdapter(baseAdapter);
        } else {
            baseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setScrollListener(final ScrollToBottomListener scrollToBottomListener) {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    View lastItemView = listView.getChildAt(listView.getChildCount() - 1);
                    if (lastItemView != null && (listView.getBottom()) == lastItemView.getBottom()) {
                        //滑动到底；

                        scrollToBottomListener.scrollToBottom();
                    }
                }
            }
        });
    }

    @Override
    public void setTitle(String str) {
        title.setText(str);
    }


}
