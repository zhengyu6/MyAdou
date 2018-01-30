package com.example.myadou.httputils;


import com.example.myadou.utils.ToastUtils;

/**
 * Created by 张晓辉 on 2018/1/15.
 */

public abstract class BaseOnRequuestComplete<T> implements OkHttpHelper.OnRequestComplete<T>{
    @Override
    public void onEmpty() {
        ToastUtils.show("数据为空");
    }
    @Override
    public void onError() {
        ToastUtils.show("访问数据失败，请稍后重试");
    }
    @Override
    public void onFailed() {
        ToastUtils.show("请检查您的网络（服务器异常）");
    }

    @Override
    public void onDataComplete() {
        ToastUtils.show("数据已加载完毕");

    }

}
