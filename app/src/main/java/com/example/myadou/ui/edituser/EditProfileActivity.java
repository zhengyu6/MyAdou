package com.example.myadou.ui.edituser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myadou.MainActivity;
import com.example.myadou.R;
import com.example.myadou.app.QiniuConfig;
import com.example.myadou.bean.AdouTimUserProfile;
import com.example.myadou.engine.PicChooseHelper;
import com.example.myadou.qiniu.QiniuUploadHelper;
import com.example.myadou.utils.ToastUtils;
import com.example.myadou.widet.EditProfileAvatarDialog;
import com.example.myadou.widet.EditProfileDialog;
import com.example.myadou.widet.EditProfileDialog2;
import com.example.myadou.widet.EditProfileItem;
import com.example.myadou.widet.EditProfile_Gender_Dialog;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendGenderType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;

import org.json.JSONObject;

import java.io.File;


public class EditProfileActivity extends Activity implements EditProfileContract.View, View.OnClickListener {
    private Toolbar toolbar;
    private EditProfileItem ep_avatar;
    private EditProfileItem ep_nickname;
    private EditProfileItem ep_adouid;
    private EditProfileItem ep_gender;
    private EditProfileItem ep_xingzuo;
    private EditProfileItem ep_active_area;
    private EditProfileItem ep_signature;
    private EditProfileItem ep_home_country;
    private EditProfileItem ep_job;
    private EditProfileContract.Presenter presenter;

    EditProfileDialog editProfileDialog;
    EditProfileDialog2 activeDialog;
    EditProfileDialog2 signatrueDialog;
    EditProfile_Gender_Dialog genderDialog;
    EditProfileAvatarDialog avatarDialog;
    private Uri outUri;
    private Button bt_save_profile;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sp = getSharedPreferences("isfirstenter", MODE_PRIVATE);
        edit = sp.edit();
        edit.putBoolean("isfirst", false);
        edit.commit();
        initView();
        initListener();
        initPresenter();
    }

    private void initPresenter() {
        this.presenter = new EditProfilePresenter(this);
        presenter.getUserInfo();
    }

    private void initListener() {
        //设置导航按钮点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ep_avatar.setOnClickListener(this);
        ep_nickname.setOnClickListener(this);
        ep_gender.setOnClickListener(this);
        ep_xingzuo.setOnClickListener(this);
        ep_active_area.setOnClickListener(this);
        ep_signature.setOnClickListener(this);
        ep_home_country.setOnClickListener(this);
        ep_job.setOnClickListener(this);
        bt_save_profile.setOnClickListener(this);
        ep_active_area.setOnClickListener(this);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        ep_avatar = findViewById(R.id.ep_avatar);
        ep_nickname = findViewById(R.id.ep_nickname);
        ep_adouid = findViewById(R.id.ep_adouid);
        ep_gender = findViewById(R.id.ep_gender);
        ep_xingzuo = findViewById(R.id.ep_xingzuo);
        ep_active_area = findViewById(R.id.ep_active_area);
        ep_signature = findViewById(R.id.ep_signature);
        ep_home_country = findViewById(R.id.ep_home_country);
        ep_job = findViewById(R.id.ep_job);
        bt_save_profile = findViewById(R.id.bt_save_profile);

    }


    @Override
    public void updateView(AdouTimUserProfile profile) {
        if (profile != null) {
            TIMUserProfile userProfile = profile.getProfile();
            String nickName = userProfile.getNickName();
            String faceUrl = userProfile.getFaceUrl();
            String identifier = userProfile.getIdentifier();
            TIMFriendGenderType gender = userProfile.getGender();
            String location = userProfile.getLocation();
            String selfSignature = userProfile.getSelfSignature();
            if (!TextUtils.isEmpty(nickName)) {
                ep_nickname.setValue(nickName);
            }
            if (!TextUtils.isEmpty(identifier)) {
                ep_adouid.setValue(identifier);
            }
            if (!TextUtils.isEmpty(location)) {
                ep_active_area.setValue(location);
            }
            if (!TextUtils.isEmpty(selfSignature)) {
                ep_signature.setValue(selfSignature);
            }
            if (gender == TIMFriendGenderType.Male) {
                ep_gender.setValue("男");
            } else if (gender == TIMFriendGenderType.Female) {
                ep_gender.setValue("女");
            } else {
                ep_gender.setValue("未知");
            }
            //自定义信息
            String xingzuo=profile.getXingzuo();
            if (!TextUtils.isEmpty(xingzuo)){
                ep_xingzuo.setValue(xingzuo);
            }


        }
    }

    @Override
    public void onGetInfoFFailed() {

    }

    @Override
    public void updateInffoError() {
        ToastUtils.show("更新信息失败，请重试");
    }

    @Override
    public void updateInfoSuccess() {
        ToastUtils.show("更新信息成功");
        presenter.getUserInfo();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ep_avatar:
                showSelectAvatarDialog();
                break;
            case R.id.ep_nickname:
                showEditNickNameDialog();
                break;
            case R.id.ep_gender:
                showEditGenderDialog();
                break;
            case R.id.ep_home_country:
                break;
            case R.id.ep_job:
                break;
            case R.id.ep_signature:
                showEditSignatureDialog();
                break;
            case R.id.ep_xingzuo:
                break;
            case R.id.bt_save_profile:
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                //添加标记 说明是从这里跳过取得
                intent.putExtra("from", "EditProfileActivity");
                startActivity(intent);
                break;
            case R.id.ep_active_area:
                showEditActivityDialog();
                break;
            default:
                break;

        }
    }

    private void showEditSignatureDialog() {
        signatrueDialog = new EditProfileDialog2(this, new EditProfileDialog2.OnProfileChangedListener() {
            @Override
            public void onChangeSuccess(String value) {
                TIMFriendshipManager.getInstance().setSelfSignature(value, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onSuccess() {
                        signatrueDialog.hide();
                        signatrueDialog = null;
                        presenter.onUpdateInfoSuccess();
                    }
                });
            }

            @Override
            public void onChangeError() {
            }
        });
        signatrueDialog.setTitleAndIcon("请输入您的个性签名", R.mipmap.male);
        signatrueDialog.show();
    }

    private void showEditActivityDialog() {

        activeDialog = new EditProfileDialog2(this, new EditProfileDialog2.OnProfileChangedListener() {
            @Override
            public void onChangeSuccess(final String value) {
                //先通过api 设置location
                String value2 = value;
                TIMFriendshipManager.getInstance().setLocation(value2, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        updateInffoError();
                    }

                    @Override
                    public void onSuccess() {
                        //再展示新的信息
//                         ep_active_area.setValue(value2);
                        activeDialog.hide();
                        activeDialog = null;
                        presenter.onUpdateInfoSuccess();

                    }
                });
                //更新应用缓存
            }

            @Override
            public void onChangeError() {
                activeDialog.hide();
                activeDialog = null;
                updateInffoError();

            }
        });
        activeDialog.setTitleAndIcon("请输入您的活跃地带：", R.mipmap.female);
        activeDialog.show();
    }

    private void showEditGenderDialog() {
        genderDialog = new EditProfile_Gender_Dialog(this, new EditProfile_Gender_Dialog.OnProfileChangedListener() {
            @Override
            public void onChangeSuccess(String value) {
                TIMFriendGenderType type;
                if ("男生".equals(value)) {
                    type = TIMFriendGenderType.Male;
                } else if ("女生".equals(value)) {
                    type = TIMFriendGenderType.Female;
                } else {
                    type = TIMFriendGenderType.Unknow;
                }
                TIMFriendshipManager.getInstance().setGender(type, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess() {
                        genderDialog.hide();
                        genderDialog = null;
                        presenter.onUpdateInfoSuccess();
                    }
                });
            }

            @Override
            public void onChangeError() {

            }
        });
        genderDialog.setTitleAndIcon("请选择您的性别：", R.mipmap.female);
        genderDialog.show();
    }

    private void showEditNickNameDialog() {
        editProfileDialog = new EditProfileDialog(this, new EditProfileDialog.OnEditChangedListener() {

            @Override
            public void onChanged(String value) {
                //更改服务器上内容
                TIMFriendshipManager.getInstance().setNickName(value, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        //更新信息失败
                        updateInffoError();
                    }

                    @Override
                    public void onSuccess() {
                        //更新信息成功
                        presenter.onUpdateInfoSuccess();

                        editProfileDialog.dismiss();
                        editProfileDialog = null;
                    }
                });
            }

            @Override
            public void onContentEmpty() {
                editProfileDialog.dismiss();
                editProfileDialog = null;
                updateInffoError();
            }
        });
        editProfileDialog.setTitleAndIcon("请输入您的昵称：", R.mipmap.ic_launcher);
        editProfileDialog.show();
    }

    private void showSelectAvatarDialog() {
        avatarDialog = new EditProfileAvatarDialog(this, R.style.common_dialog_style);
        avatarDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PicChooseHelper.getInstance(this).onActivityResult(requestCode, resultCode, data, PicChooseHelper.CropType.Avatar, new PicChooseHelper.OnPicReadyListener() {
            @Override
            public void onReady(Uri outUri) {
                ep_avatar.setAvatar(outUri);
                avatarDialog.dismiss();
                //需要把路径传到服务器
                String path = outUri.getPath();
                File file = new File(path);
                String absolutePath = file.getAbsolutePath();
                String name = file.getName();
                try {
                    QiniuUploadHelper.uploadPic(absolutePath, name, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info.isOK()){
                                Logger.i("qiniu","upLoad Success");
                                updateNetAcatarInfo(QiniuConfig.QINIU_HOST+key);

                            }else{
                                Logger.i("qiniu","upLoad Fail");
                            }
                            Log.i("qiniu",key+",\\r\\n \" + info + \",\\r\\n "+response);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void updateNetAcatarInfo(String url){
        //需要把图片同步到服务器
        TIMFriendshipManager.getInstance().setFaceUrl(url, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                updateInffoError();
            }

            @Override
            public void onSuccess() {
              updateInfoSuccess();
            }
        });
    }
}
