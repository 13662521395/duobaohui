package com.shinc.duobaohui.customview.imp;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.SearchPageInfoViewInterface;
import com.shinc.duobaohui.customview.SearchRealTimeResultInterface;
import com.shinc.duobaohui.customview.SearchViewInterface;


/**
 * 名称：SearchViewImpl
 * 作者：zhaopl 时间: 15/9/6.
 * 实现的主要功能：
 */
public class SearchViewImpl implements SearchViewInterface {

    private Activity mActivity;

    private ImageView backImg;

    private TextView searchBtn;

    private AutoClearEditText searchEdit;

    private SearchPageInfoViewInterface searchPageInfoViewImpl;

    private SearchRealTimeResultInterface searchRealTimeResultImpl;

    public SearchViewImpl(Activity mActivity) {

        this.mActivity = mActivity;
        mActivity.setContentView(R.layout.activity_search_layout);
        initView();

    }

    /**
     * 初始化页面；
     */
    private void initView() {

        backImg = (ImageView) mActivity.findViewById(R.id.back_img);

        searchBtn = (TextView) mActivity.findViewById(R.id.product_search);

        searchEdit = (AutoClearEditText) mActivity.findViewById(R.id.product_search_edit_text);

        searchPageInfoViewImpl = (SearchPageInfoViewInterface) mActivity.findViewById(R.id.search_page_info_view_impl);

        searchRealTimeResultImpl = (SearchRealTimeResultInterface) mActivity.findViewById(R.id.search_real_time_result_impl);
    }


    @Override
    public void back(final BackListener backListener) {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
                backListener.onClickBack();
            }
        });
    }


    @Override
    public void setHotSearchData(BaseAdapter hotSearchAdapter) {
        searchPageInfoViewImpl.initHotSearch(hotSearchAdapter);
    }

    @Override
    public void setSearchRealTimeResultData(BaseAdapter searchRealTimeResultAdapter) {

        searchRealTimeResultImpl.setRealTimeSearchData(searchRealTimeResultAdapter);

    }

    @Override
    public void setSearchHistoryData(BaseAdapter searchHistoryAdapter) {

        searchPageInfoViewImpl.initSearchHistory(searchHistoryAdapter);
    }


    @Override
    public void onItemClick(final ItemClickListner itemClickListner) {

        searchPageInfoViewImpl.clickHotSearchItem(new SearchPageInfoViewInterface.ClickHotSearchItemListener() {
            @Override
            public void onClickHotSearchItem(int position) {
                itemClickListner.clickHotSearchItem(position);
            }
        });

        searchPageInfoViewImpl.clickSearchHistoryItem(new SearchPageInfoViewInterface.ClickSearchHistoryItemListener() {
            @Override
            public void onClickSearchHistoryItem(int position) {
                itemClickListner.clickSearchHistoryItem(position);
            }
        });
        searchRealTimeResultImpl.clickItem(new SearchRealTimeResultInterface.ClickItemListener() {
            @Override
            public void onClickItem(int position) {
                itemClickListner.clickSearchRealTimeResultItem(position);
            }
        });

        //todo 还有一个是点击实时搜索结果的item；
    }

    @Override
    public void setSearchEditHintText(String text) {
        searchEdit.setHint(text);
    }

    @Override
    public void setSearchEditTextChange(final SearchEditTextChangeListener searchEditTextChangeListener) {

    }

    @Override
    public void setSearchResultShowState(boolean flag) {
        searchRealTimeResultImpl.setShowOrHide(flag);
    }

    @Override
    public void clickSearchBtn(final ClickSearchBtnListener clickSearchBtnListener) {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        searchEdit.setOnEditorActionListener(new AutoClearEditText.OnEditorActionListener() {
            @Override
            public boolean onEditAction() {

                if (TextUtils.isEmpty(searchEdit.getText().toString())) {
                    Toast.makeText(mActivity, "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    clickSearchBtnListener.onClickSearchBtn(searchEdit.getText().toString());
                    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
                    return true;
                }
            }
        });
    }

    @Override
    public void setHistorySearchShowState(boolean flag) {
        searchPageInfoViewImpl.hideSearchHistory(flag);
    }

    @Override
    public String getSearchEditText() {
        return searchEdit.getText().toString().trim();
    }

    @Override
    public void clickCleanHistoryBtn(final CleanSearchHistoryDeliver cleanSearchHistoryDeliver) {
        searchPageInfoViewImpl.clickCleanHistorySearchBtn(new SearchPageInfoViewInterface.CleanHistorySearchListener() {
            @Override
            public void cleanHidtorySearch() {
                cleanSearchHistoryDeliver.cleanHistory();
            }
        });
    }

    @Override
    public void setScrollListener(final ListScrollToBottom listScrollToBottom) {
        searchRealTimeResultImpl.setScrollListener(new SearchRealTimeResultInterface.ScrollToBottomListener() {
            @Override
            public void scrollToBottom() {
                listScrollToBottom.scrollBottom();
            }
        });
    }

    @Override
    public void setSearchText(String str) {
        searchEdit.setSearchText(str);
    }

    @Override
    public View getSearchEdit() {
        return searchEdit;
    }

    @Override
    public void setSearchResultTitle(String str) {
        searchRealTimeResultImpl.setTitle(str);
    }

    @Override
    public void setTextChange(final EditTextChangeListener editTextChangeListener) {
        searchEdit.setEditTextChangeListener(new AutoClearEditText.EditTextChangeListener() {
            @Override
            public void textChange(String str) {
                editTextChangeListener.textChange(str);
            }
        });
    }


}
