package com.example.myadou.widet.gift;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myadou.R;
import com.example.myadou.bean.GiftMsgInfo;
import com.example.myadou.utils.ImageViewUtils;

import java.util.LinkedList;

/**
 * Created by 张晓辉 on 2018/1/30.
 * 展示送出礼物的logo
 * 展示
 */

public class GiftFullScreenView extends FrameLayout {
    LinkedList<GiftMsgInfo>msgs = new LinkedList<>();
    private LayoutInflater inflater;
    private GiftFullScreenItem item;
    private Animation animationin;
    private Animation animationstay;
    private Animation animationout;
    private FrameLayout porche;
    private ImageView iv_sender;
    private TextView tv_sender_id;
    private WindowManager windowManager;
    private int screenWidth;
    private int screenHeight;



    public GiftFullScreenView(@NonNull Context context) {
        super(context);
        init();
    }
    private void init(){
        inflater=LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.gift_full_screen_view,this,true);
        windowManager= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        screenWidth = defaultDisplay.getWidth();
        screenHeight=defaultDisplay.getHeight();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screenWidth,screenHeight);
        v.setLayoutParams(layoutParams);
        //去沾满屏幕
        iv_sender=v.findViewById(R.id.iv_sender);
        tv_sender_id=v.findViewById(R.id.tv_sender_id);
        v.setBackgroundColor(getResources().getColor(R.color.transprant));
        item=v.findViewById(R.id.giftfullscreenitem);
        item.setVisibility(View.INVISIBLE);
        //给Item提供当动画完成之后的接口
        item.setOnGiftAnimationCommleted(new GiftFullScreenItem.OnGiftAnimationCompleted() {
            @Override
            public void onCompleted() {
                //把自己设置为隐藏
                setVisibility(View.INVISIBLE);
                //取消息 展示
                if (!msgs.isEmpty()){
                    GiftMsgInfo giftMsgInfo = msgs.removeFirst();
                    //礼物item可用
                    bindData(giftMsgInfo);
                    setVisibility(View.VISIBLE);
                    //开始动画
                    item.porcheGo();
                }
            }
        });
    }
    //获得可用的条目
    public GiftFullScreenItem getAvaliableItem(){
        if (item.getVisibility()==View.INVISIBLE){
            return item;
        }else {
            return  null;
        }
    }
    //外界调用展示礼物的方法
    public void showFullScreenGift(GiftMsgInfo info){
        //先设置自己显示出来
        GiftFullScreenItem avaliableItem = getAvaliableItem();
        if (avaliableItem!=null){
            //礼物item可用
            bindData(info);
            //开始动画
            setVisibility(View.VISIBLE);
            avaliableItem.porcheGo();
        }else {
            //正在动画当中 需要缓存
            msgs.add(info);
        }
    }

    private void bindData(GiftMsgInfo MsgInfo) {
        if (MsgInfo!=null){
            if (!TextUtils.isEmpty(MsgInfo.getAvatar())){
                ImageViewUtils.getInstance().loadCircle(MsgInfo.getAvatar(),iv_sender);
            }else {
                ImageViewUtils.getInstance().loadCircle(R.mipmap.ic_launcher,iv_sender);
            }

            if (!TextUtils.isEmpty(MsgInfo.getAdouID())){
                tv_sender_id.setText(MsgInfo.getAdouID());
            }else {
                tv_sender_id.setText("阿斗号");
            }
        }
    }

    public GiftFullScreenView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
}
