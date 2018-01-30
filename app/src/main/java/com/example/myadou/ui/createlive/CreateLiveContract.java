package com.example.myadou.ui.createlive;

/**
 * Created by 张晓辉 on 2018/1/11.
 */

public interface CreateLiveContract {
    interface View{
        void onCreateSuccess();
        void onCreateFailed();
    }
    interface Presenter{
        void createLive(String url,String liveName);
    }
}
