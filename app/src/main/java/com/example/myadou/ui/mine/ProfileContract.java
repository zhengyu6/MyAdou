package com.example.myadou.ui.mine;

import com.example.myadou.bean.AdouTimUserProfile;

/**
 * Created by 张晓辉 on 2018/1/2.
 */

public interface ProfileContract {
    interface View{
        //加载个人信息成功
        void updateProfile(AdouTimUserProfile profile);
        //加载失败
        void updateProfileError();
    }
    interface Presenter{
        void getUserProfile();
    }
}
