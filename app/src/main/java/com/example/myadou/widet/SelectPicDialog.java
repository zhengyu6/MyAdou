package com.example.myadou.widet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myadou.R;
import com.example.myadou.engine.PicChooseHelper;

/**
 * Created by 张晓辉 on 2018/1/11.
 */

public class SelectPicDialog implements View.OnClickListener {
    private TextView tv_photo;
    private TextView tv_camera;
    private LinearLayout ll_cancel;

    Activity activity;
    LayoutInflater inflater;
    private View v;
    Dialog dialog;
    private WindowManager wm;
    private Display display;
    private ViewGroup.LayoutParams layoutParams;



    public SelectPicDialog(Activity activity) {
        this.activity = activity;
        dialog=new Dialog(activity);
        init();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_photo:
                PicChooseHelper.getInstance(activity).startPhotoSelectIntent();
                break;
            case R.id.tv_camera:
                PicChooseHelper.getInstance(activity).startCameraIntent();
                break;
            case R.id.iv_cancel:
                dialog.dismiss();
                break;
        }

    }
    private  void init(){
        wm= (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        inflater=LayoutInflater.from(activity);
        v=inflater.inflate(R.layout.dialog_edit_profile_avatar,null,false);
        tv_photo =v.findViewById(R.id.tv_photo);
        tv_camera=v.findViewById(R.id.tv_camera);
        ll_cancel = v.findViewById(R.id.iv_cancel);

        tv_camera.setOnClickListener(this);
        tv_photo.setOnClickListener(this);
        ll_cancel.setOnClickListener(this);
        dialog.setContentView(v);
        //通过Window设置dialog的宽高和位置
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width=display.getWidth();
        attributes.height=WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.gravity= Gravity.BOTTOM;
        window.setAttributes(attributes);
    }

    public SelectPicDialog(Activity activity,int themeResId){
        this.activity=activity;
        //把Dialog实例化
        dialog=new Dialog(activity,themeResId);
        init();
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
