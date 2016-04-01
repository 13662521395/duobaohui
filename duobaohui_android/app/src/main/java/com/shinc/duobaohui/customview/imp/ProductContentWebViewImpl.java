package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.customview.ProductContentWebViewInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

/**
 * 名称：NewsContentWebView
 * 作者：zhaopl 时间: 15/7/1.
 * 实现的主要功能：
 * 用于承载新闻页面的主体内容；
 */
public class ProductContentWebViewImpl extends WebView implements ProductContentWebViewInterface {

    /**
     * webview控件；
     */
    private WebView webView;


    /**
     * 由webView得到的WebSetting的对象；
     */
    private WebSettings settings;

    /**
     * 从配置中读取用户设置；
     */
    private int textSizeSetValue;

    public ProductContentWebViewImpl(Context context) {
        super(context);
        initView(context, null);

    }

    public ProductContentWebViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }


    public ProductContentWebViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * 方法作用：
     * 初始化自定义View的布局页面；
     */
    private void initView(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.product_content_webview, this);

        webView = (WebView) findViewById(R.id.news_content_webview);

        settings = webView.getSettings();
        //todo 在初始化的时候，设置它应该显示的字体大小；
        initTextSize(context.getSharedPreferences("SETTING", Context.MODE_PRIVATE), "TEXTSIZE");

    }


    /**
     * 对新闻正文文字大小进行初始化；
     */
    @Override
    public void initTextSize(SharedPreferences sp, String key) {

        textSizeSetValue = getTextSetting(sp, key);

        setWebTextSize(textSizeSetValue);

    }

    /**
     * 初始化新闻内容的方法；
     *
     * @param content 内容参数；
     */
    @Override
    public void initWebContent(String content) {

        if (settings != null) {

            String html = setSetting(content);


            if (content.startsWith("http://")) {
                //加载网页；
                webView.loadUrl(content);
            } else {
                int a = 0;
//                html = "<html> \n" +
//                        "<head> \n" +
//                        "<meta name=\"viewport\" content=\"width=device-width\" /> \n" +
//                        "<meta name=\"viewport\" content=\"target-densitydpi=device-dpi\" /> \n" +
//                        "<style type=\"text/css\"> \n" +
//                        "* {text-align:justify;line-height: " + (a + 1) + "em}\n" +
//                        "</style> \n" +
//                        "</head> \n" +
//                        "<body>" + EncodingUtils.getString(html.getBytes(), "UTF-8") + "</body> \n </html>";
//
//                //以上对文章内容的样式的调整；
                webView.setFocusable(false);
                webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);


            }

            webView.setWebViewClient(new webViewClient());

        }

    }

    @Override
    public WebView getWebView() {
        return this;
    }

    /**
     * 设置WebView的一些属性；
     */
    private String setSetting(String content) {

        int version = Integer.valueOf(android.os.Build.VERSION.SDK);

//        if (version >= 19) {
        Document doc_Dis = Jsoup.parse(content);
        Elements ele_head = doc_Dis.getElementsByTag("head");
        Tag tag = Tag.valueOf("meta");
        ele_head.get(0).appendElement(tag.getName());

        Elements ele_meta = ele_head.get(0).getElementsByTag("meta");

        ele_meta.get(0).attr("name", "viewport");
        ele_meta.get(0).attr("content", "width=device-width");
        Elements ele_Img = doc_Dis.getElementsByTag("img");
        if (ele_Img.size() != 0) {
            for (Element e_Img : ele_Img) {
                e_Img.attr("style", "width:100%");
            }
        }
        content = doc_Dis.toString();
//        }
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);// 设置支持缩放；
        settings.setSupportMultipleWindows(true); // 设置支持多窗口；

        return content;
    }


    /**
     * 获取用户设置内容；
     */
    private int getTextSetting(SharedPreferences sp, String key) {

//        int textSizeValue = sp.getInt(key, Constant.TEXT_SIZE_MIDDLE);
        return sp.getInt(key, Constant.TEXT_SIZE_MIDDLE);
    }

    /**
     * 设置用户的文字大小；
     */
    private void setWebTextSize(int value) {

        switch (value) {
            case Constant.TEXT_SIZE_SMALL:
                settings.setTextSize(WebSettings.TextSize.SMALLER);
                break;
            case Constant.TEXT_SIZE_MIDDLE:
                settings.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case Constant.TEXT_SIZE_BIG:
                settings.setTextSize(WebSettings.TextSize.LARGER);

                break;
            default:
                break;
        }

    }


    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
