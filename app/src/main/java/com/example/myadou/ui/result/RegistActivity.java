package com.example.myadou.ui.result;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myadou.R;
import com.example.myadou.utils.ToastUtils;


/**
 * Created by 张晓辉 on 2018/1/1.
 */

public class RegistActivity extends Activity implements ResultContract.View, View.OnClickListener {
    private EditText et_regist_acount;
    private EditText et_regist_pass;
    private EditText et_regist_confirm;
    private TextView info;
    private Button regist;
    private Toolbar toolbar;

    private RegisPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        this.presenter = new RegisPresenter(this);
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle("注册");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        et_regist_acount = (EditText) findViewById(R.id.et_regist_acount);
        et_regist_pass = (EditText) findViewById(R.id.et_regist_pass);
        et_regist_confirm = (EditText) findViewById(R.id.et_regist_confirm);
        regist = (Button) findViewById(R.id.bt_regist);
        info = (TextView) findViewById(R.id.tv_info);
        regist.setOnClickListener(this);
        info.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_regist:
                String acount = et_regist_acount.getText().toString().trim();
                String pass = et_regist_pass.getText().toString().trim();
                String confirm = et_regist_confirm.getText().toString().trim();
               presenter.regist(acount,pass,confirm);

                Log.e("ssss","sss"+acount+pass+confirm);
                break;
            case R.id.tv_info:
                break;
            default:
                break;
        }
    }

    @Override
    public void registSuccess() {
        ToastUtils.show("注册成功");
    }

    @Override
    public void registError() {

    }

    @Override
    public void registInfoEmpty() {
        ToastUtils.show("用户名密码为空");
    }

    @Override
    public void registInfoError() {
        ToastUtils.show("用户名密码长度不足8位");

    }

    @Override
    public void registConfirmPassError() {
        ToastUtils.show("两次密码输入不一致");

    }
}
