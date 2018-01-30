package com.example.myadou.ui.edituser;

import com.example.myadou.bean.AdouTimUserProfile;
import com.example.myadou.engine.TimProfileHelper;

/**
 * Created by 张晓辉 on 2018/1/3.
 */

public class EditProfilePresenter implements EditProfileContract.Presenter {
    EditProfileActivity activity;
    EditProfileContract.View view;
    public EditProfilePresenter(EditProfileContract.View view) {
        this.view = view;
        activity= (EditProfileActivity) view;
    }


    @Override
    public void getUserInfo() {
        //app拿 如果没有再自己去获取
        TimProfileHelper.getInstance().getSelfProfile(activity, new TimProfileHelper.OnProfileGet() {
            @Override
            public void onGet(AdouTimUserProfile mProfile) {
                //更新view成功的
                view.updateView(mProfile);
            }

            @Override
            public void noGet() {
                //更新view失败
                view.onGetInfoFFailed();

            }
        });
    }

    @Override
    public void onUpdateInfoSuccess() {
        //更新到Application信息
        TimProfileHelper.getInstance().resetApplicationProfile(new TimProfileHelper.OnProfileGet() {
            @Override
            public void onGet(AdouTimUserProfile mProfile) {
                view.updateView(mProfile);
            }

            @Override
            public void noGet() {

            }
        });

    }
}
