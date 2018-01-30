package com.example.myadou.ui.ZzZhibo;

/**
 * Created by 张晓辉 on 2018/1/15.
 */

public interface HostLiveContract {
    interface View{

    }
    interface  Presenter{
        void createHost(int roomId);
        void quitHost(int roomId);
    }
}
