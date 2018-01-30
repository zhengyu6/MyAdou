package com.example.myadou.ui.mine;

import com.example.myadou.bean.AdouTimUserProfile;
import com.example.myadou.engine.TimProfileHelper;
import com.example.myadou.ui.mine.MineFragment;
import com.example.myadou.ui.mine.ProfileContract;

/**
 * Created by 张晓辉 on 2018/1/2.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    ProfileContract.View view;
    MineFragment fragment;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
        fragment = (MineFragment) view;
    }

    @Override
    public void getUserProfile() {
            new TimProfileHelper().getSelfProfile(fragment.getActivity(), new TimProfileHelper.OnProfileGet() {
                @Override
                public void onGet(AdouTimUserProfile mProfile) {
                    view.updateProfile(mProfile);
                }

                @Override
                public void noGet() {
                    //没有获取到
                    view.updateProfileError();

                }
            });
        }
    }
