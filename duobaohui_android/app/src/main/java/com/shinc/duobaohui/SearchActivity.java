package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.shinc.duobaohui.adapter.HotSearchGridAdapter;
import com.shinc.duobaohui.adapter.SearchHistoryGridAdapter;
import com.shinc.duobaohui.adapter.SearchRealTimeResultAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.HotSearchBean;
import com.shinc.duobaohui.bean.ProductBean;
import com.shinc.duobaohui.bean.SearchResultBean;
import com.shinc.duobaohui.customview.SearchViewInterface;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;
import com.shinc.duobaohui.customview.imp.SearchViewImpl;
import com.shinc.duobaohui.event.HttpHotSearchEvent;
import com.shinc.duobaohui.event.SearchProductEvent;
import com.shinc.duobaohui.event.HttpSearchHistoryEvent;
import com.shinc.duobaohui.model.SearchModelInterface;
import com.shinc.duobaohui.model.impl.SearchModelImpl;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 名称：SearchActivity
 * 作者：zhaopl 时间: 15/11/18.
 * 实现的主要功能：
 */
public class SearchActivity extends BaseActivity {

    private Activity mActivity;

    private SearchViewInterface searchViewImpl;

    private DbUtils dbUtils;

    private SearchModelInterface searchModelImpl;

    private ArrayList<SearchResultBean.HotSearch> searchHistoryList;

    private ArrayList<HotSearchBean.HotSearch> hotSearchList;

    private ArrayList<ProductBean> realTimeSearchList = new ArrayList<>();

    private SearchHistoryGridAdapter searchHistoryGridAdapter;

    private HotSearchGridAdapter hotSearchGridAdapter;

    private SearchRealTimeResultAdapter searchRealTimeResultAdapter;

    private LoadingDialog loadingDialog;

    private int page = 1;

    private String keyword;

    private InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mActivity = this;
        EventBus.getDefault().register(this);
        dbUtils = DbUtils.create(this);

        imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);

        initView();

        initData();

        initListener();

    }

    /**
     * 初始化页面；
     */
    private void initView() {

        getWindow().setBackgroundDrawable(null);
        searchViewImpl = new SearchViewImpl(mActivity);

        loadingDialog = new LoadingDialog(mActivity, R.style.dialog);
    }

    /**
     * 获取数据；
     */
    private void initData() {
        searchModelImpl = new SearchModelImpl(mActivity);
        searchModelImpl.getHistorySearchData(dbUtils);
        searchModelImpl.getHotSearchData();

    }

    /**
     * 设置监听；
     */
    private void initListener() {

        searchViewImpl.back(new SearchViewInterface.BackListener() {
            @Override
            public void onClickBack() {
                //todo 点击返回按钮的响应事件；
                mActivity.finish();
            }
        });
        //还没有进行开发，暂时保留；
        searchViewImpl.onItemClick(new SearchViewInterface.ItemClickListner() {
            @Override
            public void clickHotSearchItem(int position) {
                //点击热搜item；
                if (hotSearchList.size() > 0) {
                    keyword = hotSearchList.get(position).getSearchName();
                    searchViewImpl.setSearchText(keyword);
                    if (!TextUtils.isEmpty(searchViewImpl.getSearchEditText())) {
                        addToDatabase(searchViewImpl.getSearchEditText());
                    } else {
                        addToDatabase(hotSearchList.get(position).getSearchName());
                    }
                    searchModelImpl.search(hotSearchList.get(position).getSearchName(), page);
                }
            }

            @Override
            public void clickSearchRealTimeResultItem(int position) {
                //点击实时搜索的结果；
                if (realTimeSearchList.size() > 0) {
                    //searchModelImpl.search(realTimeSearchList.get(position).getSearchName());
                    if (!TextUtils.isEmpty(searchViewImpl.getSearchEditText())) {
                        addToDatabase(searchViewImpl.getSearchEditText());
                    } else {
                        addToDatabase(realTimeSearchList.get(position).getGoods_name());
                    }

                    if (realTimeSearchList.get(position).getPeriod_id() != null) {
                        Intent intent = new Intent(SearchActivity.this, ProductDetailActivity.class);
                        intent.putExtra("PRODUCTID", realTimeSearchList.get(position).getPeriod_id());
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void clickSearchHistoryItem(int position) {

                //点击历史搜索页面；
                if (searchHistoryList.size() > 0) {
                    loadingDialog.showLoading();

                    keyword = searchHistoryList.get(searchHistoryList.size() - position - 1).getSearchName();
                    clearSearchData();
                    page = 1;
                    searchViewImpl.setSearchText(keyword);
                    imm.hideSoftInputFromWindow(searchViewImpl.getSearchEdit().getWindowToken(), 0);
                    searchModelImpl.search(searchHistoryList.get(searchHistoryList.size() - position - 1).getSearchName(), page);
                }

            }
        });


        searchViewImpl.clickSearchBtn(new SearchViewInterface.ClickSearchBtnListener() {
            @Override
            public void onClickSearchBtn(String searchWord) {
                loadingDialog.showLoading();
                keyword = searchWord;
                clearSearchData();
                addToDatabase(searchWord);
                page = 1;
                searchModelImpl.search(searchWord, page);

            }
        });


        searchViewImpl.clickCleanHistoryBtn(new SearchViewInterface.CleanSearchHistoryDeliver() {
            @Override
            public void cleanHistory() {
                //清除历史记录；
                searchModelImpl.cleanSearchHistory(dbUtils);
            }
        });


        searchViewImpl.setScrollListener(new SearchViewInterface.ListScrollToBottom() {
            @Override
            public void scrollBottom() {
                //todo 当用户滑动到list低端的时候；

                loadingDialog.showLoading();
                page++;
                searchModelImpl.search(keyword, page);
            }
        });

        searchViewImpl.setTextChange(new SearchViewInterface.EditTextChangeListener() {
            @Override
            public void textChange(String str) {
                if (TextUtils.isEmpty(str)) {
                    searchModelImpl.getHistorySearchData(dbUtils);
                }
            }
        });

    }

    /**
     * 清除adapter之前的存留数据；
     */
    private void clearSearchData() {
        if (searchRealTimeResultAdapter != null) {
            searchRealTimeResultAdapter.getList().clear();
            searchRealTimeResultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 向数据库添加搜索数据；
     *
     * @param keyword
     */
    private void addToDatabase(String keyword) {
        SearchResultBean.HotSearch hotSearch = new SearchResultBean.HotSearch();
        hotSearch.setSearchName(keyword);
        hotSearch.setHotId("history");

        //保存前先删除掉大于5的数据；
        verifyKeyword(keyword);//对数据库中的数据进行处理；

        try {
            dbUtils.saveOrUpdate(hotSearch);
        } catch (DbException e) {
            e.printStackTrace();
        }


    }

    /**
     * 校验重复的数据；
     *
     * @param keyword
     * @return
     */
    private void verifyKeyword(String keyword) {
        try {
            //首先，取出所有的保存数据；
            List<SearchResultBean.HotSearch> hotSearches = dbUtils.findAll(Selector.from(SearchResultBean.HotSearch.class).where("hotId", "=", "history"));

            //判断，如果有重复的，删除相应的数据；
            if (hotSearches != null && hotSearches.size() > 0) {

                for (int i = 0; i < hotSearches.size(); i++) {
                    if (keyword.equals(hotSearches.get(i).getSearchName())) {
                        dbUtils.deleteById(Selector.from(SearchResultBean.HotSearch.class).getEntityType(), hotSearches.get(i).getId());//删除相同的数据；
                    }
                }
            }
            //重新读取数据；
            hotSearches = dbUtils.findAll(Selector.from(SearchResultBean.HotSearch.class).where("hotId", "=", "history"));//取出数据；
            //如果数据大于5个的话，清除掉第一个数据；
            if (hotSearches != null && hotSearches.size() > 5) {
                clearSearchData(hotSearches);//清除数据的操作；
            }


        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    /**
     * 清除大于5条的数据；
     *
     * @param hotSearches
     * @return
     */
    private void clearSearchData(List<SearchResultBean.HotSearch> hotSearches) {

        try {
            hotSearches = dbUtils.findAll(Selector.from(SearchResultBean.HotSearch.class).where("hotId", "=", "history"));
            dbUtils.deleteById(Selector.from(SearchResultBean.HotSearch.class).getEntityType(), hotSearches.get(0).getId());
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取历史数据；
     *
     * @param httpSearchHistoryEvent
     */
    public void onEventMainThread(HttpSearchHistoryEvent httpSearchHistoryEvent) {
        if (httpSearchHistoryEvent.getHotSearchBean() != null)
            searchHistoryList = httpSearchHistoryEvent.getHotSearchBean().getHotSearchList();
        if (searchHistoryList == null || searchHistoryList.size() == 0) {
            //如果查询结果为null
            searchViewImpl.setHistorySearchShowState(false);
            if (searchHistoryGridAdapter != null && searchHistoryGridAdapter.getList() != null && searchHistoryGridAdapter.getList().size() != 0) {
                searchViewImpl.setSearchHistoryData(searchHistoryGridAdapter);
                searchHistoryGridAdapter.getList().clear();
                searchHistoryGridAdapter.notifyDataSetChanged();
            }
        } else {
            //如果查询结果不为null;
            if (searchHistoryGridAdapter == null) {
                searchHistoryGridAdapter = new SearchHistoryGridAdapter(httpSearchHistoryEvent.getHotSearchBean().getHotSearchList(), mActivity);
                searchViewImpl.setSearchHistoryData(searchHistoryGridAdapter);
            } else {
                searchHistoryGridAdapter.getList().clear();
                searchHistoryGridAdapter.setList(searchHistoryList);
            }

            searchHistoryGridAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取实时反馈数据的；
     *
     * @param event
     */
    public void onEventMainThread(SearchProductEvent event) {
        loadingDialog.hideLoading();
        if (event.getProductListBean() != null && "1".equals(event.getProductListBean().getCode())) {
            //搜索成功，展示数据；
            if (event.getProductListBean().getData() != null && event.getProductListBean().getData().size() > 0) {

                if (searchRealTimeResultAdapter == null) {
                    searchRealTimeResultAdapter = new SearchRealTimeResultAdapter(realTimeSearchList, mActivity);
                    searchViewImpl.setSearchRealTimeResultData(searchRealTimeResultAdapter);
                }
                if (page == 1) {
                    realTimeSearchList = event.getProductListBean().getData();
                } else {
                    realTimeSearchList.addAll(event.getProductListBean().getData());
                }
                searchRealTimeResultAdapter.getList().clear();
                searchRealTimeResultAdapter.getList().addAll(realTimeSearchList);
                searchViewImpl.setSearchResultTitle("搜索“" + keyword + "”的结果：");
                searchViewImpl.setSearchResultShowState(true);
                searchViewImpl.setHistorySearchShowState(false);
                searchRealTimeResultAdapter.notifyDataSetChanged();
            } else {
                if (page == 1) {
                    Toast.makeText(mActivity, "没有搜到相关产品！", Toast.LENGTH_SHORT).show();
                    //如果没有搜到数据我们就显示搜索历史，摒弃显示搜索结果；
                    searchModelImpl.getHistorySearchData(dbUtils);
                    searchViewImpl.setSearchResultShowState(false);
                    searchViewImpl.setHistorySearchShowState(true);
                } else {
                    page--;
                    Toast.makeText(mActivity, "没有更多数据！", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(mActivity, "搜索失败，请检查网络，重新搜索！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取热搜数据；
     *
     * @param httpHotSearchEvent
     */
    public void onEventMainThread(HttpHotSearchEvent httpHotSearchEvent) {
        if (httpHotSearchEvent.getHotSearchBean() != null) {
            hotSearchList = httpHotSearchEvent.getHotSearchBean().getHotSearchList();
            if (hotSearchGridAdapter == null) {
                hotSearchGridAdapter = new HotSearchGridAdapter(httpHotSearchEvent.getHotSearchBean().getHotSearchList(), mActivity);

            } else {
                hotSearchGridAdapter.setList(httpHotSearchEvent.getHotSearchBean().getHotSearchList());
            }
            searchViewImpl.setHotSearchData(hotSearchGridAdapter);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
