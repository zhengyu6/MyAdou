package com.example.myadou.ui.ZzZhibo;

import com.example.myadou.app.AdouApplication;
import com.example.myadou.engine.live.LiveHelper;
import com.example.myadou.httputils.BaseOnRequuestComplete;
import com.example.myadou.httputils.Constants;
import com.example.myadou.httputils.OkHttpHelper;
import com.tencent.TIMUserProfile;

import java.util.HashMap;

/**
 * Created by 张晓辉 on 2018/1/15.
 */

public class HostPresenter implements HostLiveContract.Presenter {
    public HostPresenter(HostLiveContract.View view) {
        this.view = view;
        hostLiveActivity = (HostLiveActivity) view;
    }

    HostLiveContract.View view;
    HostLiveActivity hostLiveActivity;
    @Override
    public void createHost(int roomId) {
        LiveHelper.getInstance(hostLiveActivity).createRoom(roomId+"");
    }

    @Override
    public void quitHost(int roomId) {
        //获取userId 和房间Id 然后退出
        TIMUserProfile profile = AdouApplication.getApp().getAdouTimUserProfile().getProfile();
        String userId = profile.getIdentifier();
        HashMap<String, String> map = new HashMap<>();
        map.put("action","quit");
        map.put("userId",userId);
        map.put("roomId",roomId+"");


        OkHttpHelper.getInstance().postString(Constants.HOST,map,new BaseOnRequuestComplete<String>(){
            @Override
            public void onSuccess(String s) {
                hostLiveActivity.finish();
            }
        });
    }

}
