package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.HotSearchBean;
import com.shinc.duobaohui.event.HttpHotSearchEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/6.
 * 分类数据 请求
 */
public class SearchHotRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true,false, context, new MyHttpUtils.MyRequestCallBack() {
            HotSearchBean searchResultBean;

            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {
                try {
                    searchResultBean = GsonUtil.json2Bean(context, responseInfo.result, HotSearchBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new HttpHotSearchEvent(searchResultBean));
                }

                EventBus.getDefault().post(new HttpHotSearchEvent(searchResultBean));

            }

            @Override
            public void onFailure(HttpException e, String s) {
                /**
                 *  todo 假数据，用于测试使用；
                 */
                ArrayList<HotSearchBean.HotSearch> lists = new ArrayList<HotSearchBean.HotSearch>();
                lists.add(new HotSearchBean.HotSearch(1,"hah"));
                lists.add(new HotSearchBean.HotSearch(2,"he"));
                lists.add(new HotSearchBean.HotSearch(3,"sdf"));
                lists.add(new HotSearchBean.HotSearch(4, "爱风"));
                lists.add(new HotSearchBean.HotSearch(5,"en"));

                searchResultBean = new HotSearchBean("1","success",lists);

                EventBus.getDefault().post(new HttpHotSearchEvent(searchResultBean));
            }
        });

    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}