package com.example.myadou.widet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.myadou.R;


/**
 * Created by 张晓辉 on 2018/1/16.
 */

public class BottomSwitchLayout extends FrameLayout implements View.OnClickListener {
    private LayoutInflater inflater;
    private ImageView iv_switch_chat;
    private ImageView iv_switch_close;
    public ImageView iv_switch_gift;
    OnswitchListener mListener;



    public BottomSwitchLayout(@NonNull Context context) {
        super(context);
        inflater=LayoutInflater.from(context);
        init();
    }

    public BottomSwitchLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater=LayoutInflater.from(context);
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_switch_chat:
                //切换到聊天布局
                if (mListener!=null){
                    mListener.onChat();
                }
                break;
            case R.id.iv_switch_close:
                //退出直播
                if (mListener!=null){
                    mListener.onClose();
                }
                break;
            case R.id.iv_switch_gift:
                //掉起发送礼物的dialog
                if (mListener!=null){
                    mListener.onGift();
                }
                break;
            default:
                break;
        }

    }
    public  void  init(){
        //导入布局
        View v = inflater.inflate(R.layout.bottom_char_or_close, this, true);
        iv_switch_chat = v.findViewById(R.id.iv_switch_chat);
        iv_switch_gift = v.findViewById(R.id.iv_switch_gift);
        iv_switch_close = v.findViewById(R.id.iv_switch_close);
        iv_switch_chat.setOnClickListener(this);
        iv_switch_gift.setOnClickListener(this);
        iv_switch_close.setOnClickListener(this);
    }

    public void setOnSwitchListener(OnswitchListener listener){
        mListener=listener;
    }
    public interface OnswitchListener{
        void onChat();
        void onClose();
        //发送礼物
        void onGift();
    }
}
