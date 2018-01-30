package com.example.myadou.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.myadou.MainActivity;
import com.example.myadou.engine.TimProfileHelper;
import com.example.myadou.ui.edituser.EditProfileActivity;
import com.example.myadou.ui.mine.MineFragment;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

/**
 * Created by 张晓辉 on 2018/1/1.
 */

public class LoginPresenter implements LoginContract.Presenter {
    //持有view的引用
    LoginContract.View mView;
    LoginActivity loginActivity;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        loginActivity = (LoginActivity) mView;
    }

    //登录
    @Override
    public void login(String acount, String pass) {
        //为空判断
        if (TextUtils.isEmpty(acount) || TextUtils.isEmpty(pass)) {
            mView.loginInfoEmpty();
            return;
        }
        if (acount.length() < 8 || pass.length() < 8) {
            mView.loginInfoError();
            return;
        }
        realLogin(acount, pass);

    }

    //真正的登录
    private void realLogin(String acount, String pass) {
        ILiveLoginManager.getInstance().tlsLoginAll(acount, pass, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                mView.loginSuccess();
                getUserInfo();
                //判断是否第一次进入编辑中心
                SharedPreferences sharedPreferences = loginActivity.getSharedPreferences("isfirstenter", Context.MODE_PRIVATE);
                boolean isfirst = sharedPreferences.getBoolean("isfirst", true);
                Intent intent;
                if (isfirst) {
                    intent = new Intent(loginActivity, EditProfileActivity.class);

                } else {
                    intent = new Intent(loginActivity, MainActivity.class);
                }
                loginActivity.startActivity(intent);

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                mView.loginFailed(errCode, errMsg);

            }
        });
    }

    //获取用户的个人信息
    public void getUserInfo() {
        new TimProfileHelper().getSelfProfile(loginActivity, null);
    }
}
