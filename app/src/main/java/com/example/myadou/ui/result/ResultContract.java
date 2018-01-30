package com.example.myadou.ui.result;

/**
 * Created by 张晓辉 on 2018/1/1.
 */

public interface ResultContract {
    interface  View{
        //注册成功
        void registSuccess();
        //注册失败
        void registError();
        //注册信息为空
        void registInfoEmpty();
        //注册信息不足8位
        void registInfoError();
        //两次输入密码不一致
        void registConfirmPassError();
    }
    interface Presenter{
        void regist(String acount, String pass, String confirmPass);
    }
}
