package com.example.myadou.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myadou.R;
import com.example.myadou.ui.result.RegistActivity;
import com.example.myadou.utils.ToastUtils;

/**
 * Created by 张晓辉 on 2018/1/1.
 */

public class LoginActivity extends Activity implements LoginContract.View, View.OnClickListener {
    private EditText et_username;
    private EditText et_pass;
    private Button bt_login;
    private TextView tv_regist;

    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
        initPresenter();
    }
    private void initview() {
        et_username = (EditText) findViewById(R.id.ed_username);
        et_pass = (EditText) findViewById(R.id.et_pass);
        bt_login = (Button) findViewById(R.id.bt_regist);
        tv_regist = (TextView) findViewById(R.id.tv_info);
        bt_login.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
    }
    private void initPresenter() {
        this.presenter=new LoginPresenter(this);
    }
    /*
    登陆成功
     */
    @Override
    public void loginSuccess() {
        ToastUtils.show("登录成功");

    }

    @Override
    public void loginFailed(int errcode, String errmsg) {

    }

    /**
     * 登录失败
     */


    /**
     * 用户名密码为空
     */
    @Override
    public void loginInfoEmpty() {
        ToastUtils.show("账号密码不能为空");

    }

    /**
     * 用户名密码长度不足
     */

    @Override
    public void loginInfoError() {
        ToastUtils.show("账号密码的长度不足8位");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_regist:
                String acount = et_username.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                presenter.login(acount, pass);
                break;
            case R.id.tv_info:
                Intent in = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(in);
                break;
        }
    }
}
