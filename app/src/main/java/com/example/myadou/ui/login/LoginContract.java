package com.example.myadou.ui.login;

/**
 * Created by 张晓辉 on 2018/1/1.
 */

public interface LoginContract {
    interface View {
        //登录成功
        void loginSuccess();
        //登录失败
        void loginFailed(int errcode,String errmsg);
        //信息为空
        void loginInfoEmpty();
        //信息不足8位
        void loginInfoError();
    }

    interface Presenter {
        void login(String acount, String pass);
    }
}