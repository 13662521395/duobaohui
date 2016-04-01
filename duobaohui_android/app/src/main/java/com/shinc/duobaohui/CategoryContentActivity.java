package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.adapter.CategoryContentGridViewAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.ProductBean;
import com.shinc.duobaohui.bean.ProductListBean;
import com.shinc.duobaohui.customview.SlidingMenuView;
import com.shinc.duobaohui.customview.imp.LoadMoreGridView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.CategoryContentEvent;
import com.shinc.duobaohui.model.impl.CategoryContentModelImpl;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * 名称：CategoryContentActivity
 * 作者：zhaopl 时间: 15/11/17.
 * 实现的主要功能：
 */
public class CategoryContentActivity extends BaseActivity {


    private Activity mActivity;

    private ImageView backImg;

    private TextView titleText;

    private PtrClassicFrameLayout ptrFrameLayout;

    private CategoryContentGridViewAdapter adapter;

    private int page = 1;

    private SlidingMenuView slidingMenuView;

    private DrawerLayout drawerLayout;

    private ProductListBean productListBean;

    private ArrayList<ProductBean> list;

    private boolean isRefresh;

    private RelativeLayout noWeb;

    private CategoryContentModelImpl categoryContentModel;

    private LoadMoreGridView gridView;

    private WaitLoadingUtils loadingUtils;

    private String categoryId;

    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_content_layout);
        mActivity = this;
        EventBus.getDefault().register(this);
        loadingUtils = new WaitLoadingUtils(this);
        loadingUtils.show();

        if (getIntent() != null && getIntent().getExtras() != null) {
            categoryId = getIntent().getStringExtra("CATEGORYID");
            categoryName = getIntent().getStringExtra("CATEOGORYNAME");
        }

        initView();
        initData();
        initListener();
    }

    private void initView() {

        getWindow().setBackgroundDrawable(null);
        backImg = (ImageView) findViewById(R.id.back_img);

        titleText = (TextView) findViewById(R.id.title_tv);
        if (!TextUtils.isEmpty(categoryName)) {
            titleText.setText(categoryName);
        }
        ptrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame_layout);
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        gridView = (LoadMoreGridView) findViewById(R.id.category_product_grid_view);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        slidingMenuView = (SlidingMenuView) findViewById(R.id.sliding_view);

        noWeb = (RelativeLayout) findViewById(R.id.no_web);
    }

    private void initData() {
        categoryContentModel = new CategoryContentModelImpl(mActivity);
        categoryContentModel.getProductInfo(categoryId, "", page);
    }

    private void initListener() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 侧滑开启按钮；
                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        slidingMenuView.setOnTitleClickListener(new SlidingMenuView.onTitleClickListener() {
            @Override
            public void onTitleClick() {
                //todo 当用户点击所有商品的时候，跳转到首页；
                mActivity.finish();
            }
        });

        slidingMenuView.setOnListItemClickListener(new SlidingMenuView.OnListItemClickListener() {
            @Override
            public void onLiteItemClick(int position, String id, String catName) {
                //todo 点击menu上的menu按钮的时候进行的操作；
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                //todo 进行页面刷新；
                categoryId = id;
                categoryName = catName;
                page = 1;
                gridView.setLoadReset();
                categoryContentModel.getProductInfo(id, "", page);
                titleText.setText(categoryName);
                loadingUtils.show();
            }
        });

        noWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击无数据进行数据的重新加载；
                if (categoryContentModel == null) {
                    categoryContentModel.getProductInfo("", "", page);
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo 点击某一个item，进行跳转，跳到商品详情页面；
                Intent intent = new Intent(mActivity, ProductDetailActivity.class);
                intent.putExtra("PRODUCTID", list.get(position).getPeriod_id());
                startActivity(intent);
            }
        });


        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                isRefresh = true;
                gridView.setLoadReset();
                categoryContentModel.getProductInfo(categoryId, "", page);
            }
        });

        gridView.setLoadMoreListener(new LoadMoreGridView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                isRefresh = true;

                categoryContentModel.getProductInfo(categoryId, "", page);
            }
        });

    }


    /**
     * 获取分类数据；
     *
     * @param event
     */
    public void onEventMainThread(CategoryContentEvent event) {
        loadingUtils.disable();
        if (event.getProductListBean() != null) {

            productListBean = event.getProductListBean();

            if (productListBean != null && productListBean.getCode() != null && "1".equals(productListBean.getCode())) {
                noWeb.setVisibility(View.GONE);
                //todo 得到商品列表的数据，进行数据赋值；

                if (productListBean.getData() != null) {
                    if (adapter == null) {
                        adapter = new CategoryContentGridViewAdapter(mActivity);
                        gridView.setAdapter(adapter);
                    }
                    if (page > 1) {
                        list.addAll(productListBean.getData());
                    } else {
                        list = productListBean.getData();
                    }
                    adapter.setListChildDatas(list);

                    adapter.notifyDataSetChanged();
                    if (page == 1 && list.size() > 0) {
                        gridView.smoothScrollToPosition(0);
                    }
                } else {
                    gridView.setLoadComplete();
                    page--;
                }
            } else {
                noWeb();
            }
            if (ptrFrameLayout != null && isRefresh) {
                ptrFrameLayout.refreshComplete();
                isRefresh = false;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void noWeb() {
        loadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                loadingUtils.haveWeb();
                loadingUtils.show();
                page = 1;
                categoryContentModel.getProductInfo(categoryId, "", page);
            }
        });
    }
}
