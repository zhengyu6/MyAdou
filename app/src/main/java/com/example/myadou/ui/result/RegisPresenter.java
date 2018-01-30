package com.example.myadou.ui.result;

import android.text.TextUtils;

import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

/**
 * Created by 张晓辉 on 2018/1/1.
 */

public class RegisPresenter implements ResultContract.Presenter {
    private ResultContract.View mview;
    private RegistActivity activity;

    public RegisPresenter(ResultContract.View mview) {
        this.mview = mview;
       activity = (RegistActivity) mview;
    }

    @Override
    public void regist(String acount, String pass, String confirmPass) {
        if (TextUtils.isEmpty(acount) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirmPass)) {
            mview.registInfoEmpty();
            return;
        }
        if (acount.length() < 8 || pass.length() < 8 || confirmPass.length() < 8) {
            mview.registInfoError();
            return;
        }
        if (!pass.equals(confirmPass)) {
            mview.registConfirmPassError();
            return;
        }
        realRegist(acount, pass);

    }

    private void realRegist(String acount, String pass) {
        //直播处理
        ILiveLoginManager.getInstance().tlsRegister(acount, pass, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                mview.registSuccess();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                mview.registError();
            }
        });
    }
}
