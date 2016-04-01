package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;
import com.shihe.shincdatastatisticssdk.ShiNcAgent;
import com.shinc.duobaohui.adapter.BuyNumTwoLineAdapter;
import com.shinc.duobaohui.adapter.TakePartRecordListAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.AddOrderBean;
import com.shinc.duobaohui.bean.BannerBean;
import com.shinc.duobaohui.bean.IndexBannerBean;
import com.shinc.duobaohui.bean.LuckUserBean;
import com.shinc.duobaohui.bean.ProductDetail;
import com.shinc.duobaohui.bean.ProductDetailBean;
import com.shinc.duobaohui.bean.TakePartBean;
import com.shinc.duobaohui.bean.TakePartListBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.CommitOrderDialog;
import com.shinc.duobaohui.customview.CreateOrderDialog;
import com.shinc.duobaohui.customview.banner.ConvenientBanner;
import com.shinc.duobaohui.customview.banner.NetworkImageHolderView;
import com.shinc.duobaohui.customview.banner.holder.CBViewHolderCreator;
import com.shinc.duobaohui.customview.counttime.CountdownView;
import com.shinc.duobaohui.customview.imp.CircleImageView;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.AddOrderEvent;
import com.shinc.duobaohui.event.HttpProductDetailEvent;
import com.shinc.duobaohui.event.HttpTakePartListEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.http.AddOrderRequestImpl;
import com.shinc.duobaohui.model.impl.ProductDetailImpl;
import com.shinc.duobaohui.utils.CodeVerifyUtils;
import com.shinc.duobaohui.utils.ImageLoad;
import com.shinc.duobaohui.utils.WaitLoadingUtils;
import com.shinc.duobaohui.utils.web.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 名称: ProductDetailActivity
 * Created by chaos on 15/9/29.
 * 功能:商品详情页面
 */

public class ProductDetailActivity extends BaseActivity {
    //商品轮播图
    private ConvenientBanner indexBanner;

    private TextView productName;
    private IndexBannerBean indexBannerBean;
    private Activity mActivity;

    private GridView buyNumTwoLineGv;

    private PtrClassicFrameLayout ptrClassicFrameLayout;

    private TextView needNum;


    private TextView lastNum;

    private TextView participateNum;

    private RelativeLayout selltypeProductDetail;

    /**
     * 进度；
     */
    private ProgressBar pbFragmentDowing;
    /**
     * 返回按钮；
     */
    private ImageView backImg;

    /**
     * 参与记录；
     */
    private LoadMoreListView takePartRecordListView;

    /**
     * 等待揭晓的layout；
     */
    private RelativeLayout waitingOpen;

    private CountdownView countDownTime;

    /**
     * 该商品的揭晓状态布局；
     */
    private RelativeLayout takePartLayout;

    /**
     * 已经揭晓的布局页面；
     */
    private RelativeLayout winnerInfoLayout;

    private RelativeLayout winnerLayout;

    private CustomTextView winnerNickName;

    private CircleImageView winnerHeadImg;


    private CustomTextView winnerAddress;

    private CustomTextView winnerUserId;

    private CustomTextView winnerTakePartNum;

    private CustomTextView winnerTime;

    private CustomTextView luckNum;

    /**
     * 跳转到图文详情；
     */
    private RelativeLayout gotoProductDetail;


    /**
     * 跳转到往期揭晓页面；
     */
    private RelativeLayout gotoPreAnnounce;

    /**
     * 跳转到晒单分享的页面；
     */
    private RelativeLayout gotoShareShow;

    /**
     * 没有参与的布局；
     */
    private CustomTextView noTakePartIn;

    /**
     * 该用户参与了该次活动的布局；
     */
    private RelativeLayout userTakePartLayout;
    /**
     * 加载页
     */
    WaitLoadingUtils loadingUtils;
    /**
     * 跳转到计算详情页面；
     */
    private CustomTextView gotoCalculateDetail;

    /**
     * 跳转到计算详情页面；
     */
    private CustomTextView toCalculateDetail;


    private RelativeLayout warningLayout;

    /**
     * 用于展示跳转到下一页的提示语；
     */
    private CustomTextView gotoNewActiveTv;

    private View gotoNewLayout;

    private CustomTextView gotoNewActive;

    private CustomTextView allTakePartInfo;


    private BuyNumTwoLineAdapter buyNumTwoLineAdapter;

    private ProductDetailImpl productDetailImpl;

    private String period_id;

    private TakePartRecordListAdapter takePartRecordListAdapter;

    private int page = 1;

    private int endId = 0;

    private boolean isRefresh = false;

    private String countTime;

    private Long lastTime;

    private boolean isFirst = false;

    private boolean isResume = false;

    private String banner;

    private boolean isLoad = false;

    private boolean isBanner = false;

    private ProductDetail productDetail;

    private List<TakePartBean> list = new ArrayList();

    private CommitOrderDialog dialog;

    private String type;

    private boolean commitTag = false;

    private static String num;

    private CreateOrderDialog loadingDialog;

    private View view;

    //标示该商品是否正在进行中的标示，如果正在进行中，返回到该页面进行自动刷新；
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getExtraData();
        this.mActivity = this;
        //setImmerseLayout(findViewById(R.id.detail_layout));
        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_productdetail);
        loadingUtils = new WaitLoadingUtils(this);
        loadingUtils.show();
        initView();
        initPullToRefresh();
        initData();
        initEvent();

        testPageStay();

    }

    private void testPageStay() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("base_url", "test base url");
        params.put("page_type", "index");
        params.put("page_from", "index_main test");
        params.put("page_from_id", "test from id");
        params.put("page_key", "test news id");
        params.put("in_time", System.currentTimeMillis() - 5000 + "");
        params.put("out_time", System.currentTimeMillis() + "");
        params.put("read_status", "0.76");
        params.put("stay_time", "5000");

        ShiNcAgent.onEvent(this, "page_stay", params);
    }

    /**
     * 获取传递过来的数据；
     */
    private void getExtraData() {
        if (getIntent() != null && getIntent().getExtras() != null) {

            banner = getIntent().getStringExtra("BANNER");

            type = getIntent().getStringExtra("TYPE");

            if (banner != null && "banner".equals(banner)) {
                isBanner = true;
            }

            if (!TextUtils.isEmpty(type) && "SHOWCOMMITORDER".equals(type)) {
                commitTag = true;
            }

            period_id = getIntent().getStringExtra("PRODUCTID");

        }
    }

    /**
     * 初始化数据；
     */
    private void initView() {
        getWindow().setBackgroundDrawable(null);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame_layout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        view = mActivity.getLayoutInflater().inflate(R.layout.product_detail_info_layout, null);
        initProductInfoView(view);

        backImg = (ImageView) findViewById(R.id.detail_back_img);
        takePartRecordListView = (LoadMoreListView) findViewById(R.id.take_part_record);
        takePartRecordListView.addHeaderView(view);

        initList();

        gotoNewActiveTv = (CustomTextView) findViewById(R.id.goto_new_active_tv);
        gotoNewLayout = findViewById(R.id.goto_new_layout);
        gotoNewActive = (CustomTextView) findViewById(R.id.goto_new_active);
        allTakePartInfo = (CustomTextView) findViewById(R.id.all_take_part_info);

        resetLoginState();
    }

    /**
     * 设置当前用户的登陆状态；
     */
    private void resetLoginState() {
        loadingDialog = new CreateOrderDialog(mActivity, R.style.blackDialog);
        if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {

            noTakePartIn.setText("点击登录，查看购买信息~");
            noTakePartIn.setBackground(getResources().getDrawable(R.drawable.grey_corner_selector));
            noTakePartIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo 点击跳转到登陆页面；
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            noTakePartIn.setText("您没有参与该期夺宝哦~");
            noTakePartIn.setBackground(getResources().getDrawable(R.drawable.grey_line_white_bg));
        }
    }

    private void initProductInfoView(View view) {
        //商品轮播图
        indexBanner = (ConvenientBanner) view.findViewById(R.id.banner_productdetail);
        productName = (TextView) view.findViewById(R.id.productname_productdetail);
        buyNumTwoLineGv = (GridView) view.findViewById(R.id.buy_numtwoline_productdetail_gv);
        needNum = (TextView) view.findViewById(R.id.need_num);
        lastNum = (TextView) view.findViewById(R.id.progress_num_currentnum);
        participateNum = (TextView) view.findViewById(R.id.participate_num);
        selltypeProductDetail = (RelativeLayout) view.findViewById(R.id.selltype_productdetail);
        pbFragmentDowing = (ProgressBar) view.findViewById(R.id.pb_fragment_dowing);
        waitingOpen = (RelativeLayout) view.findViewById(R.id.waiting_open_layout);
        countDownTime = (CountdownView) view.findViewById(R.id.count_down_time);
        takePartLayout = (RelativeLayout) view.findViewById(R.id.take_part_layout);
        winnerInfoLayout = (RelativeLayout) view.findViewById(R.id.winner_info_layout);
        winnerLayout = (RelativeLayout) view.findViewById(R.id.winner_layout);
        winnerNickName = (CustomTextView) view.findViewById(R.id.winner_nick_name);
        winnerHeadImg = (CircleImageView) view.findViewById(R.id.winner_head_img);
        winnerAddress = (CustomTextView) view.findViewById(R.id.winner_address);
        winnerUserId = (CustomTextView) view.findViewById(R.id.winner_userId);
        winnerTakePartNum = (CustomTextView) view.findViewById(R.id.winner_take_part_num);
        winnerTime = (CustomTextView) view.findViewById(R.id.winner_time);
        luckNum = (CustomTextView) view.findViewById(R.id.lunky_num);
        gotoProductDetail = (RelativeLayout) view.findViewById(R.id.goto_product_detail_layout);
        gotoPreAnnounce = (RelativeLayout) view.findViewById(R.id.goto_pre_announce_layout);
        gotoShareShow = (RelativeLayout) view.findViewById(R.id.goto_share_show_layout);
        noTakePartIn = (CustomTextView) view.findViewById(R.id.no_take_part_in);
        userTakePartLayout = (RelativeLayout) view.findViewById(R.id.take_part_ll);
        gotoCalculateDetail = (CustomTextView) view.findViewById(R.id.goto_calculate_detail);
        toCalculateDetail = (CustomTextView) view.findViewById(R.id.to_calculate_detail);
        warningLayout = (RelativeLayout) view.findViewById(R.id.warning_layout);
    }

    /**
     * 加载数据；
     */
    private void initData() {

        isFirst = true;
        isResume = false;
        if (productDetailImpl == null) {

            productDetailImpl = new ProductDetailImpl(this);
        }
        if (!TextUtils.isEmpty(period_id)) {
            productDetailImpl.getProductInfo(period_id, isBanner);
            productDetailImpl.getTakePartRecord(period_id, "1", endId);

        }
    }

    /**
     * 初始化参与记录列表；
     */
    private void initList() {
        takePartRecordListAdapter = new TakePartRecordListAdapter(this);
        takePartRecordListView.setAdapter(takePartRecordListAdapter);
    }

    private void initPullToRefresh() {


        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                takePartRecordListView.setLoadReset();
                list.clear();
                if (takePartRecordListAdapter != null) {
                    takePartRecordListAdapter.notifyDataSetChanged();
                }
                endId = 0;
                isLoad = true;
                isRefresh = true;
                if (productDetail != null) {
                    isBanner = false;
                }
                productDetailImpl.getTakePartRecord(period_id, page + "", endId);
                productDetailImpl.getProductInfo(period_id, isBanner);
            }
        });

        takePartRecordListView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                if (list != null && list.size() > 1) {
                    if (!TextUtils.isEmpty(list.get(list.size() - 1).getId())) {
                        endId = Integer.parseInt(list.get(list.size() - 1).getId());
                    }
                }
                isRefresh = true;
                productDetailImpl.getTakePartRecord(period_id, page + "", endId);
            }
        });
    }

    /**
     * 设置用户的参与时的夺宝号展示；
     *
     * @param list
     * @param productDetail
     */

    private void initBuyNumTwoLine(final ArrayList<String> list, final ProductDetail productDetail) {

        buyNumTwoLineAdapter = new BuyNumTwoLineAdapter(this, list, buyNumTwoLineGv);
        buyNumTwoLineGv.setAdapter(buyNumTwoLineAdapter);
        buyNumTwoLineGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击列表的数据；

                if (position == list.size() - 1) {

                    //todo 跳转到新的页面进行操作；

                    Intent intent = new Intent(mActivity, UserDuoBaoCodeActivity.class);
                    intent.putExtra("period_id", productDetail.getPeriod_id());
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 设置商品的名称的函数；
     *
     * @param str
     * @param type
     */
    private void initProductName(String period_id, String str, String type) {
        Bitmap b = null;
        switch (Integer.parseInt(type)) {
            case 0:
                b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_going);
                break;
            case 1:
                b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_countdown);
                break;
            case 2:
                b = BitmapFactory.decodeResource(getResources(), R.drawable.daojishi);
                break;
        }

        ImageSpan imgSpan = new ImageSpan(this, b);
        SpannableString spanString = new SpannableString("icon");
        spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        productName.setText(spanString);
        productName.append("  " + "(第" + period_id + "期) " + str);

        SpannableString textColorSpan = new SpannableString("颜色随机");
        textColorSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 0, textColorSpan.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        //productName.append(textColorSpan);
    }

    /**
     * 初始化点击事件；
     */

    private void initEvent() {

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击返回按钮，关闭该页面；
                finish();
            }
        });

        gotoCalculateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转到计算详情页面；
                Intent intent = new Intent(mActivity, ComputionalDetailsActivity.class);
                intent.putExtra("id", period_id);
                startActivity(intent);
            }
        });

        toCalculateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转到计算详情页面；
                Intent intent = new Intent(mActivity, ComputionalDetailsActivity.class);
                intent.putExtra("id", period_id);
                startActivity(intent);
            }
        });

        gotoProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转到商品图文详情页面；

                if (productDetail != null && !TextUtils.isEmpty(productDetail.getGoods_id())) {
                    Intent intent = new Intent(mActivity, ProductContentActivity.class);
                    intent.putExtra("good_id", productDetail.getGoods_id());
                    startActivity(intent);
                }
            }
        });


        gotoPreAnnounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转到往期揭晓页面；
                if (productDetail == null || productDetail.getId() == null) {

                } else {
                    Intent intent = new Intent(mActivity, PreAnnounceActivity.class);
                    intent.putExtra("ACTIVEID", productDetail.getId());
                    startActivity(intent);
                }
            }
        });

        gotoShareShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productDetail == null || productDetail.getGoods_id() == null) {

                } else {
                    Intent intent = new Intent(mActivity, ShareShowOrderActivity.class);
                    intent.putExtra("STATE", 2);
                    intent.putExtra("GOODS_ID", productDetail.getGoods_id() + "");
                    startActivity(intent);
                }
            }
        });

        takePartRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(mActivity, UserMainPageActivity.class);
                intent.putExtra("takePart", takePartRecordListAdapter.getList().get(position - 1));
                startActivity(intent);
            }
        });
    }


    /**
     * 获取商品详情数据；
     *
     * @param httpProductDetailEvent
     */
    public void onEventMainThread(HttpProductDetailEvent httpProductDetailEvent) {

        final ProductDetailBean productDetailBean = httpProductDetailEvent.getProductDetailBean();
        if (productDetailBean == null) {
            Toast.makeText(mActivity, "网络链接出错，请检查网络", Toast.LENGTH_LONG).show();
            noWeb();
        } else if ("1".equals(productDetailBean.getCode())) {
            resetLoginState();
            period_id = productDetailBean.getData().getPeriod_id();
            //保存form_token;
            if (productDetailBean.getData() != null && productDetailBean.getData().getForm_token() != null) {
                Constant.FORM_TOKEN = productDetailBean.getData().getForm_token();

            }
            /**
             * 设置banner的数据；
             */
            setBanner(productDetailBean);

            productDetail = productDetailBean.getData();

            //设置商品的名称；
            initProductName(productDetail.getPeriod_number(), productDetail.getGoods_name(), productDetail.getStatus());
            /**
             * 设置商品信息；
             */
            setProductInfo();
            //设置用户参与信息；
            setUserInfo();

            //根据(接口)状态设置view;
            switch (Integer.parseInt(productDetail.getStatus())) {
                case 0:
                    //todo 尚未开奖；
                    underWay(productDetailBean);
                    isRunning = true;
                    break;
                case 1:
                    //todo  等待开奖；
                    WaitingOpen();
                    isRunning = false;
                    break;
                case 2:
                    //todo  已经开奖；
                    AlreadyOpen();
                    isRunning = false;
                    break;
                case 3:
                    // todo 出现异常；
                    HaveException();
                    break;
            }
        }
        isFirst = false;
        loadingUtils.disable();
        if (commitTag) {
            if ("0".equals(productDetail.getStatus())) {
                //todo 当尚未开奖的时候，才对dialog进行操作；（让其显示出来）；
                if (dialog != null) {

                    if ((Integer.parseInt(productDetailBean.getData().getReal_need_times()) - Integer.parseInt(productDetailBean.getData().getCurrent_times())) < 1) {
                        dialog.setBuyNum("0");
                    } else {
                        dialog.setBuyNum("1");
                    }
                    dialog.show();
                } else {
                    dialog = new CommitOrderDialog(mActivity, Integer.parseInt(productDetailBean.getData().getReal_need_times()) - Integer.parseInt(productDetailBean.getData().getCurrent_times()), "1");
                    dialog.show();
                    dialog.setCommitOrderListener(new CommitOrderDialog.CommitOrderListener() {

                        @Override
                        public void commit(String num) {
                            //进行网络通信，关闭该dialog；
                            if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
                                Intent intent = new Intent(mActivity, FastLoginActivity.class);
                                startActivity(intent);
                            } else {
                                if ((Integer.parseInt(productDetailBean.getData().getReal_need_times()) - Integer.parseInt(productDetailBean.getData().getCurrent_times())) < 1) {
                                    Toast.makeText(ProductDetailActivity.this, "对不起，该商品已经到达购买上线，敬请期待下一期。", Toast.LENGTH_SHORT).show();
                                } else {
                                    loadingDialog.showLoading();
                                    HttpUtils httpUtils = new HttpUtils();
                                    RequestParams requestParams = new RequestParams();
                                    requestParams.addBodyParameter("period_id", period_id);
                                    requestParams.addBodyParameter("num", num);
                                    httpUtils.sendHttpPost(requestParams, ConstantApi.ADD_ORDER, new AddOrderRequestImpl(), mActivity);
                                    ProductDetailActivity.num = num;
                                    dialog.dismiss();
                                }
                            }
                        }
                    });
                }
                commitTag = false;
            } else {
                Toast.makeText(mActivity, "该期已经结束购买，点击去新的一期购买吧！", Toast.LENGTH_SHORT).show();
            }
        }

        if (ptrClassicFrameLayout != null && isRefresh) {
            ptrClassicFrameLayout.refreshComplete();
            isRefresh = false;
        }
    }

    /**
     * 设置商品信息；
     */
    private void setProductInfo() {
        pbFragmentDowing.setProgress(Integer.parseInt(productDetail.getRate()));//设置进度；

        needNum.setText("总需" + productDetail.getReal_need_times() + "人次");
        int needNum = Integer.parseInt(productDetail.getReal_need_times()) - Integer.parseInt(productDetail.getCurrent_times());
        lastNum.setText(needNum + "");

        //设置起始时间：
        allTakePartInfo.setText("(自" + productDetail.getCreate_time() + "开始)");
    }

    /**
     * 设置banner的数据；
     *
     * @param productDetailBean
     */
    private void setBanner(ProductDetailBean productDetailBean) {
        if (!isResume) {
            if (!isLoad) {
                ArrayList<BannerBean> bannerList = productDetailBean.getData().getGoods_pic();
                if (bannerList.size() > 0) {
//                    indexBanner.setData(1, new IndexBannerBean("1", "", bannerList), new IndexBannerViewInterface.SetOnPageClick() {
//                        @Override
//                        public void onClick(String id, String type, String linkUrl) {
//                            //todo 点击banner图的时候进行跳转；
//                        }
//                    });

                    ArrayList<String> localImages = new ArrayList<>();
                    for (int position = 0; position < bannerList.size(); position++)
                        localImages.add(bannerList.get(position).getPic_url());

                    indexBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {

                        @Override
                        public NetworkImageHolderView createHolder() {
                            return new NetworkImageHolderView("1");
                        }
                    }, localImages)//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                            .setPageIndicator(new int[]{R.drawable.icon_picdefault_sel, R.drawable.icon_picdefault_nor})
                                    //设置指示器的方向
                            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
                }
                isLoad = false;
            }
        }
    }

    /**
     * 活动正在进行；
     *
     * @param productDetailBean
     */
    private void underWay(final ProductDetailBean productDetailBean) {
        waitingOpen.setVisibility(View.GONE);
        winnerInfoLayout.setVisibility(View.GONE);
        warningLayout.setVisibility(View.GONE);
        selltypeProductDetail.setVisibility(View.VISIBLE);
        gotoNewActiveTv.setText("点击参与夺宝~~~");
        gotoNewActive.setText("参与夺宝");
        gotoNewActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog != null) {
                    if ((Integer.parseInt(productDetailBean.getData().getReal_need_times()) - Integer.parseInt(productDetailBean.getData().getCurrent_times())) < 1) {
                        dialog.setBuyNum("0");
                    } else {
                        dialog.setBuyNum("1");
                    }
                    dialog.setMaxNum(Integer.parseInt(productDetailBean.getData().getReal_need_times()) - Integer.parseInt(productDetailBean.getData().getCurrent_times()));
                    dialog.show();
                } else {
                    dialog = new CommitOrderDialog(mActivity, Integer.parseInt(productDetailBean.getData().getReal_need_times()) - Integer.parseInt(productDetailBean.getData().getCurrent_times()), "1");
                    dialog.show();
                    dialog.setCommitOrderListener(new CommitOrderDialog.CommitOrderListener() {
                        @Override
                        public void commit(String num) {
                            //进行网络通信，关闭该dialog；

                            if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
                                Intent intent = new Intent(mActivity, FastLoginActivity.class);
                                startActivity(intent);
                            } else {
                                //Log.e("need__current", productDetailBean.getData().getReal_need_times() + "-----" + productDetailBean.getData().getCurrent_times());
                                if ((Integer.parseInt(productDetailBean.getData().getReal_need_times()) - Integer.parseInt(productDetailBean.getData().getCurrent_times())) < 1) {
                                    Toast.makeText(ProductDetailActivity.this, "对不起，该商品已经到达购买上线，敬请期待下一期。", Toast.LENGTH_SHORT).show();
                                } else {
                                    loadingDialog.showLoading();
                                    HttpUtils httpUtils = new HttpUtils();
                                    RequestParams requestParams = new RequestParams();
                                    requestParams.addBodyParameter("period_id", period_id);
                                    requestParams.addBodyParameter("num", num);
                                    httpUtils.sendHttpPost(requestParams, ConstantApi.ADD_ORDER, new AddOrderRequestImpl(), mActivity);
                                    ProductDetailActivity.num = num;
                                    dialog.dismiss();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 设置用户信息；
     */
    private void setUserInfo() {
        if (productDetail.getLogin_user() != null) {

            userTakePartLayout.setVisibility(View.VISIBLE);
            noTakePartIn.setVisibility(View.GONE);
            if (productDetail.getLogin_user().getCode() != null) {
                initBuyNumTwoLine(productDetail.getLogin_user().getCode(), productDetail);
            }
            if (productDetail.getLogin_user().getTimes() != null) {
                participateNum.setText(productDetail.getLogin_user().getTimes());
            }
        } else {
            noTakePartIn.setVisibility(View.VISIBLE);
            userTakePartLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 出现异常；
     */
    private void HaveException() {
        countDownTime.allShowZero();
        waitingOpen.setVisibility(View.VISIBLE);
        winnerInfoLayout.setVisibility(View.GONE);
        warningLayout.setVisibility(View.VISIBLE);
        selltypeProductDetail.setVisibility(View.GONE);
        if (TextUtils.isEmpty(productDetail.getNext_period_id())) {
            //todo 没有下一期，隐藏入口；
            gotoNewActiveTv.setText("该活动已下架...");
            gotoNewActive.setVisibility(View.GONE);
        } else {
            //todo 下一期入口；
            gotoNewActive.setVisibility(View.VISIBLE);
            gotoNewActiveTv.setText("第" + productDetail.getCurrent_period() + "期正在火热进行中...");
            gotoNewActive.setText("立即前往");
            gotoNewActive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // todo  点击跳转到新的页面；
                    Intent intent = new Intent(mActivity, ProductDetailActivity.class);
                    intent.putExtra("PRODUCTID", productDetail.getNext_period_id());
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    /**
     * 已经开奖；
     */
    private void AlreadyOpen() {
        waitingOpen.setVisibility(View.GONE);
        selltypeProductDetail.setVisibility(View.GONE);
        winnerInfoLayout.setVisibility(View.VISIBLE);
        warningLayout.setVisibility(View.GONE);
        selltypeProductDetail.setVisibility(View.GONE);
        final LuckUserBean luckUserBean = productDetail.getLuck_user();

        if (!TextUtils.isEmpty(luckUserBean.getHead_pic())) {
            ImageLoad.getInstance(mActivity).setImageToView(luckUserBean.getHead_pic() + Constant.QINIU30_30, winnerHeadImg);
        } else {
            winnerHeadImg.setImageResource(R.drawable.icon_head_small);
        }
        winnerNickName.setText(luckUserBean.getNick_name());
        winnerUserId.setText(luckUserBean.getUser_id() + "(唯一不变标识)");
        winnerTakePartNum.setText(Html.fromHtml("<font color=red >" + luckUserBean.getTimes() + "</font><font color=#666666 > 人次</font>"));
        winnerTime.setText(productDetail.getLuck_code_create_time());
        luckNum.setText(luckUserBean.getLuck_code());

        winnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击跳转到TA的主页；
                Intent intent = new Intent();
                intent.setClass(mActivity, UserMainPageActivity.class);
                TakePartBean bean = new TakePartBean(luckUserBean.getUser_id(), luckUserBean.getNick_name(), luckUserBean.getTimes(), luckUserBean.getCreate_time(), luckUserBean.getHead_pic(), luckUserBean.getUser_id(), luckUserBean.getIp(), luckUserBean.getIp_address());
                intent.putExtra("takePart", bean);
                startActivity(intent);
            }
        });


        if (TextUtils.isEmpty(luckUserBean.getIp_address())) {
            winnerAddress.setVisibility(View.GONE);
        } else {
            winnerAddress.setVisibility(View.VISIBLE);
            winnerAddress.setText("(" + luckUserBean.getIp_address() + ")");
        }
        if (TextUtils.isEmpty(productDetail.getNext_period_id())) {
            //todo 没有下一期，隐藏入口；
            gotoNewActiveTv.setText("该活动已下架...");
            gotoNewActive.setVisibility(View.GONE);
        } else {
            //todo 下一期入口；
            gotoNewActive.setVisibility(View.VISIBLE);
            gotoNewActiveTv.setText("第" + productDetail.getCurrent_period() + "期正在火热进行中...");
            gotoNewActive.setText("立即前往");
            gotoNewActive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // todo  点击跳转到新的页面；
                    Intent intent = new Intent(mActivity, ProductDetailActivity.class);
                    intent.putExtra("PRODUCTID", productDetail.getNext_period_id());
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    /**
     * 等待开奖；
     */
    private void WaitingOpen() {

        waitingOpen.setVisibility(View.VISIBLE);
        winnerInfoLayout.setVisibility(View.GONE);
        warningLayout.setVisibility(View.GONE);
        selltypeProductDetail.setVisibility(View.GONE);
        countTime = productDetail.getLeft_second();


        lastTime = System.currentTimeMillis() + (Long.parseLong(countTime) * 10);

        countDownTime.start(Long.parseLong(countTime) * 10);

        countDownTime.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                initData();
            }
        });

        if (TextUtils.isEmpty(productDetail.getNext_period_id())) {
            //todo 没有下一期，隐藏入口；
            gotoNewLayout.setVisibility(View.GONE);
        } else {
            //todo 下一期入口；
            gotoNewLayout.setVisibility(View.VISIBLE);
            gotoNewActiveTv.setText("第" + productDetail.getCurrent_period() + "期正在火热进行中...");
            gotoNewActive.setText("立即前往");
            gotoNewActive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // todo  点击跳转到新的页面；
                    Intent intent = new Intent(mActivity, ProductDetailActivity.class);
                    intent.putExtra("PRODUCTID", productDetail.getNext_period_id());
                    startActivity(intent);
                    finish();
                }
            });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        indexBanner.startTurning(3000);
        if (!isFirst) {
            isResume = true;
            //当period_id不为0的时候，并且当前活动正在进行的时候进行操作。否则不进行刷新的操作；
            if (!TextUtils.isEmpty(period_id) && isRunning) {
                if (productDetail != null) {
                    isBanner = false;
                    initData();
                }
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        indexBanner.stopTurning();
    }

    /**
     * 获取参与记录的数据；
     *
     * @param httpTakePartListEvent
     */
    public void onEventMainThread(HttpTakePartListEvent httpTakePartListEvent) {

        TakePartListBean takePartListBean = httpTakePartListEvent.getTakePartListBean();
        if (takePartListBean != null) {
            if ("1".equals(takePartListBean.getCode())) {
                //todo 成功，设置页面信息；
                if (takePartListBean.getData() != null) {
                    if (takePartRecordListAdapter == null) {
                        initList();
                    }
                    if (page == 1) {
                        list.clear();
                        list.addAll(takePartListBean.getData());
                    } else {
                        list.addAll(takePartListBean.getData());
                    }
                    takePartRecordListAdapter.setList((ArrayList) list);
                } else {
                    if (page > 1) {
                        takePartRecordListView.setLoadComplete();
                        page--;
                    }
                }
            }

        }

        if (ptrClassicFrameLayout != null && isRefresh) {
            ptrClassicFrameLayout.refreshComplete();
            isRefresh = false;
        }
        loadingUtils.disable();
    }

    /**
     * 获取添加订单后返回的数据；
     *
     * @param addOrderEvent
     */
    public void onEventMainThread(AddOrderEvent addOrderEvent) {

        loadingDialog.hideLoading();
        AddOrderBean addOrderBean = addOrderEvent.getAddOrderBean();

        if (addOrderBean == null) {

            Toast.makeText(mActivity, "添加订单失败，请重试！", Toast.LENGTH_SHORT).show();
        } else {
            if (CodeVerifyUtils.verifyCode(addOrderBean.getCode())) {
                CodeVerifyUtils.verifySession(mActivity);
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
            } else {
                if ("1".equals(addOrderBean.getCode())) {
                    //todo 获取数据成功,跳转到订单详情页面；
                    Intent intent = new Intent(mActivity, PayFastionOrderActivity.class);
                    intent.putExtra("ORDERINFO", addOrderBean.getData());
                    intent.putExtra("NUM", num);
                    intent.putExtra("PEROIDID", productDetail.getPeriod_id());
                    startActivity(intent);
                } else {
                    Toast.makeText(mActivity, addOrderBean.getData().getMsg(), Toast.LENGTH_SHORT).show();
                    initData();
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (indexBanner != null) {
            //indexBanner.onDestroyHandler();
            indexBanner = null;
        }

        testStatices();

        buyNumTwoLineAdapter = null;
        takePartRecordListAdapter = null;
        productDetailImpl = null;
        list = null;
        spUtils = null;
        loadingUtils = null;
        if (!this.isFinishing()) {
            this.finish();
        }
        System.gc();

    }

    private void testStatices() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("base_url", "test11");
        params.put("page_from", "index");
        params.put("page_from_id", "test21");
        params.put("page_type", "news");
        params.put("page_key", "34534");
        params.put("in_time", System.currentTimeMillis() - 901000 + "");
        params.put("out_time", System.currentTimeMillis() + "");
        params.put("read_status", "0.54");
        params.put("stay_time", "901");

        ShiNcAgent.onEvent(this, "page_stay", params);
    }

    private void noWeb() {
        loadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                loadingUtils.haveWeb();
                loadingUtils.show();
                initData();
            }
        });
    }

}
