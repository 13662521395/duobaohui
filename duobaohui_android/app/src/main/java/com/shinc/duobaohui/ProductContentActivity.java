package com.shinc.duobaohui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.Toast;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.ProductContentBean;
import com.shinc.duobaohui.customview.web.ProductContentViewInterface;
import com.shinc.duobaohui.customview.web.impl.ProductContentViewImpl;
import com.shinc.duobaohui.event.HttpProductContentEvent;
import com.shinc.duobaohui.model.ProductContentModelInterface;
import com.shinc.duobaohui.model.impl.ProductContentModelImpl;

import de.greenrobot.event.EventBus;

/**
 * 名称：文章内容页面；
 * 作者：zpl ;  时间：15-07-01
 * 作用：文章内容模块起始页面；
 */
public class ProductContentActivity extends BaseActivity {


    /**
     * 文章内容页面的View；
     */
    private ProductContentViewInterface newsContentViewImpl;

    private Activity mActivity;

    private LayoutInflater inflater;

    private ProductContentModelInterface newsContentModelImpl;

    private ProductContentBean articleInfoBean;

    /**
     * Intent传递过来的ArticleId；
     */
    private String articleId;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mActivity = this;
        inflater = (LayoutInflater) mActivity.getSystemService(LAYOUT_INFLATER_SERVICE);

        getIntentExtras();

        /**
         * 读取页面配置信息；
         */
        initSettingInfo();

        /**
         * 加载View层和Model层；
         */
        settingView();

        /**
         * 注册EventBus；
         */
        EventBus.getDefault().register(this);

        /**
         * 设置点击的响应事件；
         */
        setClickListener();

        setEventBusReceive();

    }

    private void setEventBusReceive() {
    }

    /**
     * 得到Intent中传递过来的数据；
     */
    private void getIntentExtras() {

        try {
            articleId = getIntent().getStringExtra("good_id");
            //todo 暂时将articleId 的值设为1；
            if (TextUtils.isEmpty(articleId)) {

                articleId = "1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置点击的响应事件；
     */
    private void setClickListener() {

        //设置title的点击事件；
        setTitleClickListener();

        //设置底部(添加评论)的点击事件；
        setButtonClickListener();

    }

    /**
     * 设置底部点击的响应事件；
     */
    private void setButtonClickListener() {

    }


    /**
     * 方法作用：用于根据页面配置信息，配置
     */
    private void setTitleClickListener() {

        //重新加载数据的动作；
        newsContentViewImpl.reloadData(new ProductContentViewInterface.NewsContentReloadData() {
            @Override
            public void reloadData() {
                newsContentViewImpl.hideReloadView();
                newsContentModelImpl.initData(articleId);
            }
        });

    }

    /**
     * 方法作用：用于根据页面配置信息，配置
     */
    private void settingView() {

        //初始化View层对象；
        newsContentViewImpl = new ProductContentViewImpl(this);
        //todo 初始化Model层对象；articleId暂时为1；
        newsContentModelImpl = new ProductContentModelImpl(this, articleId);
        newsContentViewImpl.showLoadingView();

    }

    /**
     * 方法作用：用于读取页面配置信息；
     */
    private void initSettingInfo() {
        //todo 解析配置文件的操作；
    }


    /**
     * 用来接收文章信息http请求后的数据
     *
     * @param httpProductContentEvent http请求后返回的数据
     */
    public void onEventMainThread(HttpProductContentEvent httpProductContentEvent) {

        newsContentViewImpl.hideLoadingView();

        articleInfoBean = httpProductContentEvent.getProductContentBean();

        if ("1".equals(articleInfoBean.getCode())) {
            //得到初始化数据；
            initDate(httpProductContentEvent);
            newsContentViewImpl.hideReloadView();
        } else {
            newsContentViewImpl.showReloadView();
            Toast.makeText(getApplicationContext(), articleInfoBean.getMsg(), Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * 初始化数据的操作；
     */
    private void initDate(HttpProductContentEvent httpProductContentEvent) {
        //todo 初始化数据后对view层的操作；
        newsContentViewImpl.setNewsContentDate(httpProductContentEvent.getProductContentBean());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        newsContentModelImpl = null;
        WebView webView = newsContentViewImpl.getWebView();
        webView.removeAllViews();
        webView.destroy();
    }
}
