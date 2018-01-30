package com.example.myadou.ui.home;

import android.content.Intent;
import android.util.Log;

import com.example.myadou.adapter.HomeRoomInfoAdapter;
import com.example.myadou.bean.AllHomeInfo;
import com.example.myadou.httputils.BaseOnRequuestComplete;
import com.example.myadou.httputils.Constants;
import com.example.myadou.httputils.OkHttpHelper;
import com.example.myadou.ui.watch.WatchActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 张晓辉 on 2018/1/18.
 */

public class HomePresenter implements HomeContract.Presenter {

    HomeContract.View view;
    HomeFragment homeFragment;
    private ArrayList<AllHomeInfo.DataBean> data = new ArrayList<>();

    public HomePresenter(HomeContract.View view) {
        this.view = view;
        homeFragment = (HomeFragment) view;
    }

    @Override
    public void getHomeLiveList(final int page) {

        String url = Constants.HOST;

        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getList");
        map.put("pageIndex", page + "");

        //获取首页直播列表
        OkHttpHelper.getInstance().postObject(url, map, new BaseOnRequuestComplete<AllHomeInfo>() {
            private HomeRoomInfoAdapter homeRoomInfoAdapter;

            @Override
            public void onSuccess(AllHomeInfo baseBean) {
                Logger.e(baseBean.getCode());
                if (baseBean.getData() != null) {
                    //第一次获取数据时直接赋值给list
                    data = baseBean.getData();

//                    if (data==null||page==0){
//                        data = baseBean.getData();
                    homeRoomInfoAdapter = new HomeRoomInfoAdapter(homeFragment.getActivity(), data);
                    homeRoomInfoAdapter.setOnMyClickListener(new HomeRoomInfoAdapter.OnMyClickListener() {
                        @Override
                        public void OnMyClick(AllHomeInfo.DataBean bean) {
                            Logger.e("data0" + bean.toString());
                            //做相应的操作：跳转到直播页面 观看直播
                            Intent intent = new Intent(homeFragment.getActivity(), WatchActivity.class);
                            intent.putExtra("roomId", bean.getRoomId());
                            intent.putExtra("hostId", bean.getUserId());
                            homeFragment.getActivity().startActivity(intent);
                        }
                    });
//
                    view.setHomeRoomInfoAdapter(homeRoomInfoAdapter);
//                    }//当data中有数据，或者page不等于0
//                    else{
//                        //往集合中添加数据
//                        ArrayList<AllHomeInfo.DataBean> data1 = baseBean.getData();
////                        int startIndex = data.size();
////                        int endIndex=  startIndex+ data1.size()-1;
//
//                        HomePresenter.this.data.addAll(0,data1);
//                        homeRoomInfoAdapter = new HomeRoomInfoAdapter(homeFragment.getActivity(), data);
////                        homeRoomInfoAdapter.notifyItemRangeInserted(startIndex,endIndex);
//                        view.updataHomeRoomInfoAdapter(homeRoomInfoAdapter);
//
//                    }
                } else {
                    //报数据为空
                    onEmpty();
                }

                //停止刷新
                homeFragment.srl.setRefreshing(false);


            }

            @Override
            public void onEmpty() {
                super.onEmpty();
                //停止刷新
                homeFragment.srl.setRefreshing(false);
            }

            @Override
            public void onError() {
                super.onError();
                //停止刷新
                homeFragment.srl.setRefreshing(false);
            }

            @Override
            public void onFailed() {
                super.onFailed();
                //停止刷新
                homeFragment.srl.setRefreshing(false);
            }
        }, AllHomeInfo.class);

    }
}
