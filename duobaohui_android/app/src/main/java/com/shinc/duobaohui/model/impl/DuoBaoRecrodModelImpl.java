package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.DuoBaoReCrodRequestImpl;
import com.shinc.duobaohui.http.DuoBaoTabNumberHttpImpl;
import com.shinc.duobaohui.model.DuoBaoRecrodModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/10/7.
 */
public class DuoBaoRecrodModelImpl implements DuoBaoRecrodModelInterface {
    private Activity mActivity;

    private HttpUtils httpUtils;

    public DuoBaoRecrodModelImpl(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }

    /*master版本*/
    @Override
    public void getDuoBaoDate(int page) {

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("page", page + "");
        requestParams.addBodyParameter("length", "20");
        httpUtils.sendHttpPost(requestParams, ConstantApi.RECROD, new DuoBaoReCrodRequestImpl(""), mActivity);
    }

    /**
     * @param flag 0 全部，1 进行时，2 已揭晓
     * @visition dev V1.1版
     */
    @Override
    public void getDuoBaoRecrod(String flag, int page) {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("page", page + "");
        requestParams.addBodyParameter("length", "20");
        requestParams.addBodyParameter("flag", flag);
        httpUtils.sendHttpPost(requestParams, ConstantApi.records, new DuoBaoReCrodRequestImpl(flag), mActivity);
    }

    @Override
    public void getDuoBaoTadNumber() {
        RequestParams requestParams = new RequestParams();
        httpUtils.sendHttpPost(requestParams, ConstantApi.recordsNum, new DuoBaoTabNumberHttpImpl(), mActivity);
    }


}
