package com.example.myadou.ui.createlive;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.myadou.app.AdouApplication;
import com.example.myadou.bean.AdouTimUserProfile;
import com.example.myadou.bean.CreateliveInfo;
import com.example.myadou.httputils.BaseOnRequuestComplete;
import com.example.myadou.httputils.Constants;
import com.example.myadou.httputils.OkHttpHelper;
import com.example.myadou.ui.ZzZhibo.HostLiveActivity;
import com.tencent.TIMUserProfile;

import java.util.HashMap;


/**
 * Created by 张晓辉 on 2018/1/11.
 */

public class CreateLivePresenter implements CreateLiveContract.Presenter {
    public CreateLiveContract.View view;
    public CreateZhiBoActivity mActivity;
    private static final String TAG = "CreateLivePresenter";

    public CreateLivePresenter(CreateLiveContract.View view) {
        this.view = view;
        mActivity = (CreateZhiBoActivity) view;
    }

    @Override
    public void createLive(String url, String liveName) {
        //创建直播
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(liveName)) {
            view.onCreateFailed();
        } else {
            //尝试去创建
            requestRoomId(url, liveName);
        }
    }

    //获得房间的ID
    private void requestRoomId(String cover, final String liveName) {
        HashMap<String, String> map = new HashMap<>();
        AdouTimUserProfile profile = AdouApplication.getApp().getAdouTimUserProfile();
        TIMUserProfile profile1 = profile.getProfile();

        map.put("action", "create");//动作
        map.put("userId", profile1.getIdentifier());//主播Id
        map.put("userAvatar", profile1.getFaceUrl());//主播头像
        map.put("userName", profile1.getNickName());//主播昵称
        map.put("liveTitle", liveName);//直播标题
        map.put("liveCover", cover);
        OkHttpHelper.getInstance().postObject(Constants.HOST, map, new BaseOnRequuestComplete<CreateliveInfo>() {
            @Override
            public void onSuccess(CreateliveInfo createliveInfo) {
                if (createliveInfo != null) {
                    int roomId = createliveInfo.getData().getRoomId();
                    if (roomId != 0) {
                        Intent intent = new Intent(mActivity, HostLiveActivity.class);
                        intent.putExtra("roomId", roomId);
                        mActivity.startActivity(intent);

                    }
                } else {
                    onEmpty();
                }
            }
        }, CreateliveInfo.class);
    }

}
