package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.DuoBaoRecordsBean;
import com.shinc.duobaohui.event.DuoBaoAllFragmentEvent;
import com.shinc.duobaohui.event.DuoBaoRecrodEvent;
import com.shinc.duobaohui.event.DuoBaoWaitFragmentEvent;
import com.shinc.duobaohui.event.DuoBaoYetFragmentEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/7.
 */
public class DuoBaoReCrodRequestImpl implements HttpSendInterFace {


    private String flag = "";

    public DuoBaoReCrodRequestImpl(String flag) {
        this.flag = flag;
    }

    @Override
    public void sendPostRequest(final RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {

            DuoBaoRecordsBean winnerRecrodNumBean = null;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                winnerRecrodNumBean = GsonUtil.json2Bean(context, responseInfo.result, DuoBaoRecordsBean.class);
                //  flag: ""master  0:全部  1:进行中  2:已揭晓
                switch (flag) {
                    case "":
                        EventBus.getDefault().post(new DuoBaoRecrodEvent(winnerRecrodNumBean));
                        break;
                    case "0":
                        EventBus.getDefault().post(new DuoBaoAllFragmentEvent(winnerRecrodNumBean));

                        break;
                    case "1":
                        EventBus.getDefault().post(new DuoBaoWaitFragmentEvent(winnerRecrodNumBean));

                        break;
                    case "2":
                        EventBus.getDefault().post(new DuoBaoYetFragmentEvent(winnerRecrodNumBean));
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                switch (flag) {
                    case "":
                        EventBus.getDefault().post(new DuoBaoRecrodEvent(winnerRecrodNumBean));
                        break;
                    case "0":
                        EventBus.getDefault().post(new DuoBaoAllFragmentEvent(winnerRecrodNumBean));
                        break;
                    case "1":
                        EventBus.getDefault().post(new DuoBaoWaitFragmentEvent(winnerRecrodNumBean));

                        break;
                    case "2":
                        EventBus.getDefault().post(new DuoBaoYetFragmentEvent(winnerRecrodNumBean));

                        break;
                }
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}