package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.SearchPageInfoViewInterface;


/**
 * 名称：SearchPageInfoViewImpl
 * 作者：zhaopl 时间: 15/9/6.
 * 实现的主要功能：
 */
@SuppressWarnings("ALL")
public class SearchPageInfoViewImpl extends RelativeLayout implements SearchPageInfoViewInterface {

    private LinearLayout searchHistoryLl;

    private ListView searchHistoryGridView;

    private GridView hotSearchGridView;

    @SuppressWarnings("FieldCanBeLocal")
    private LinearLayout hotSearchLl;

    private RelativeLayout cleanHistoryBtn;

    @SuppressWarnings("FieldCanBeLocal")
    private View searchResultLayout;

    @SuppressWarnings("FieldCanBeLocal")
    private View searchHistoryLayout;

    private TextView cleanBtn;

    public SearchPageInfoViewImpl(Context context) {
        super(context);

        initView(context);
    }

    public SearchPageInfoViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SearchPageInfoViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_page_view, this);

        searchHistoryGridView = (ListView) findViewById(R.id.search_history_grid_view);

        searchHistoryLl = (LinearLayout) findViewById(R.id.search_history_ll);

        hotSearchGridView = (GridView) findViewById(R.id.hot_search_grid_view);

        hotSearchLl = (LinearLayout) findViewById(R.id.hot_search_ll);
        cleanHistoryBtn = (RelativeLayout) findViewById(R.id.clear_search_history_ll);

        cleanBtn = (TextView) findViewById(R.id.clean_search_tv);
        searchHistoryLayout = findViewById(R.id.search_history_layout);

        searchResultLayout = findViewById(R.id.search_result_layout);
    }

    @Override
    public void initHotSearch(BaseAdapter hotSearchAdapter) {

        if (hotSearchGridView.getAdapter() == null) {
            hotSearchGridView.setAdapter(hotSearchAdapter);
        } else {
            hotSearchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initSearchHistory(BaseAdapter searchHistoryAdapter) {

        if (searchHistoryGridView.getAdapter() == null) {
            searchHistoryGridView.setAdapter(searchHistoryAdapter);
        } else {
            searchHistoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void clickHotSearchItem(final ClickHotSearchItemListener clickHotSearchItemListener) {

        hotSearchGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickHotSearchItemListener.onClickHotSearchItem(position);
            }
        });
    }

    @Override
    public void clickSearchHistoryItem(final ClickSearchHistoryItemListener clickSearchHistoryItemListener) {
        searchHistoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickSearchHistoryItemListener.onClickSearchHistoryItem(position);
            }
        });

    }

    @Override
    public void hideSearchHistory(boolean flag) {
        if (flag) {
            searchHistoryLl.setVisibility(VISIBLE);
            cleanHistoryBtn.setVisibility(VISIBLE);
        } else {
            searchHistoryLl.setVisibility(GONE);
            cleanHistoryBtn.setVisibility(GONE);
        }
    }

    @Override
    public void clickCleanHistorySearchBtn(final CleanHistorySearchListener cleanHistorySearchListener) {
        cleanBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                cleanHistorySearchListener.cleanHidtorySearch();
            }
        });
    }


}
