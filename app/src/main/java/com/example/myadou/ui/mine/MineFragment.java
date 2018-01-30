package com.example.myadou.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myadou.MainActivity;
import com.example.myadou.R;
import com.example.myadou.bean.AdouTimUserProfile;
import com.example.myadou.ui.edituser.EditProfileActivity;
import com.example.myadou.utils.ImageViewUtils;
import com.example.myadou.utils.ToastUtils;
import com.tencent.TIMFriendGenderType;

/**
 * Created by 张晓辉 on 2018/1/8.
 */

public class MineFragment extends Fragment implements ProfileContract.View {
    private ConstraintLayout cl_profile;
    private ProfileContract.Presenter presenter;

    private ImageView iv_avatar;
    private TextView tv_nickname;
    private TextView tv_acount_id;
    private ImageView iv_gender;
    private TextView tv_grade;
    private MainActivity mainActivity;
    private View view;
    private TextView tv_send;
    private TextView tv_receive;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, null, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListenter();
        initPresenter();
        mainActivity = (MainActivity) getActivity();
        mainActivity.initToolbar("我");
    }

    private void initListenter() {
        cl_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initPresenter() {
        presenter = new ProfilePresenter(this);
    }

    private void initView(View view) {
        cl_profile = view.findViewById(R.id.cl_profile);
        cl_profile = view.findViewById(R.id.cl_profile);
        iv_avatar = view.findViewById(R.id.iv_avatar);
        tv_nickname = view.findViewById(R.id.tv_nickname);
        tv_acount_id = view.findViewById(R.id.tv_acount_id);
        iv_gender = view.findViewById(R.id.iv_gender);
        tv_grade = view.findViewById(R.id.tv_grade);
        //送出(关注)
        tv_send = view.findViewById(R.id.tv_send);
        //收到(粉丝)
        tv_receive = view.findViewById(R.id.tv_receive);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getUserProfile();
    }

    @Override
    public void updateProfile(AdouTimUserProfile profile) {
        if (!TextUtils.isEmpty(profile.getProfile().getFaceUrl())) {
            ImageViewUtils.getInstance().loadCircle(profile.getProfile().getFaceUrl(), iv_avatar);
        } else {
            ImageViewUtils.getInstance().loadCircle(R.mipmap.ic_launcher, iv_avatar);
        }
        //昵称
        if (!TextUtils.isEmpty(profile.getProfile().getNickName())) {
            tv_nickname.setText(profile.getProfile().getNickName());
        } else {
            tv_nickname.setText("暂无昵称");
        }
        //阿斗号
        if (!TextUtils.isEmpty(profile.getProfile().getIdentifier())) {
            tv_acount_id.setText("阿斗号:" + profile.getProfile().getIdentifier());
        }
        //性别
        if (profile.getProfile().getGender() == TIMFriendGenderType.Male) {
            iv_gender.setBackgroundResource(R.mipmap.male);
        } else if (profile.getProfile().getGender() == TIMFriendGenderType.Female) {
            iv_gender.setBackgroundResource(R.mipmap.female);
        } else {
            //默认是男
            iv_gender.setBackgroundResource(R.mipmap.male);
        }

        tv_grade.setText(profile.getGrade() + "");
        //送出(关注)
        tv_send.setText(profile.getFork() + "");
        //收到(粉丝)
        tv_receive.setText(profile.getFans() + "");

    }

    @Override
    public void updateProfileError() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show("拉取信息失败");
            }
        });
    }
}
