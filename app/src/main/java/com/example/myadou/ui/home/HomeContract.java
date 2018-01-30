package com.example.myadou.ui.home;

import com.example.myadou.adapter.HomeRoomInfoAdapter;

/**
 * Created by 张晓辉 on 2018/1/18.
 */

public interface HomeContract {

    interface View {
        void setHomeRoomInfoAdapter(HomeRoomInfoAdapter adapter);

        void updataHomeRoomInfoAdapter(HomeRoomInfoAdapter adapter);

    }

    interface Presenter {
        void getHomeLiveList(int page);
    }
}
