package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.WinnerRecrodNumBean;
import com.shinc.duobaohui.event.WinnerRecrodNumEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/6.
 * 中奖记录～／ http 各个数量的总值
 */
public class WinnerReCrodNumRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(final RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            WinnerRecrodNumBean winnerRecrodNumBean;

            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {
                winnerRecrodNumBean = GsonUtil.json2Bean(context, responseInfo.result, WinnerRecrodNumBean.class);

                EventBus.getDefault().post(new WinnerRecrodNumEvent(winnerRecrodNumBean));

            }

            @Override
            public void onFailure(HttpException e, String s) {

                EventBus.getDefault().post(new WinnerRecrodNumEvent(winnerRecrodNumBean));
            }
        });

    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}