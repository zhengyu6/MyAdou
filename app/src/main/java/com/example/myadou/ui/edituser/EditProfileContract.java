package com.example.myadou.ui.edituser;

import com.example.myadou.bean.AdouTimUserProfile;

/**
 * Created by 张晓辉 on 2018/1/3.
 */

public interface EditProfileContract {
    interface  View{
        void updateView(AdouTimUserProfile profile);
        void onGetInfoFFailed();
        void updateInffoError();
        void updateInfoSuccess();

    }
    interface  Presenter{
        void getUserInfo();
        void onUpdateInfoSuccess();

    }
}
