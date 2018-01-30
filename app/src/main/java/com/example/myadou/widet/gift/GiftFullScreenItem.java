package com.example.myadou.widet.gift;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.myadou.R;

/**
 * Created by 张晓辉 on 2018/1/30.
 * 自定义FragmentLayout布局
 * 展示跑车
 *
 */

public class GiftFullScreenItem extends FrameLayout {
    private LayoutInflater inflater;
    private ImageView porche_back;
    private ImageView porche_front;
    private AnimationDrawable porche_back_anim;
    private AnimationDrawable porche_back_front;
    private FrameLayout porche;


    private Animation animation;
    private Animation animationstay;
    private Animation animationout;
    public GiftFullScreenItem(@NonNull Context context) {
        super(context);
        init();
    }
    public void init(){
        inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.gift_full_screen,this,true);
        porche_back=view.findViewById(R.id.porche_back);
        porche_front=view.findViewById(R.id.porche_front);
        porche_back_anim= (AnimationDrawable) porche_back.getBackground();
        porche_back_front= (AnimationDrawable) porche_front.getBackground();
        porche_back_anim.start();
        porche_back_front.start();
        initAnimation();
    }

    private void initAnimation() {
        animation= AnimationUtils.loadAnimation(getContext(),R.anim.gift_full_screen_in);
        animationstay=AnimationUtils.loadAnimation(getContext(),R.anim.gift_full_screen_stay);
        animationout=AnimationUtils.loadAnimation(getContext(),R.anim.gift_full_screen_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //此操作等同于创建出一个主线程handler 然后handler 去发送handler
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      startAnimation(animationout);
                    }
                },2000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //最后一个移除动画
        animationout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
              setVisibility(View.INVISIBLE);
              //从消息缓存队列中取出消息 由于消息属于giftFullScreenView持有  所以现在需要回调
              mOnCompleted.onCompleted();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    public GiftFullScreenItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public void cancleDrawableAnim(){
        porche_back_anim.stop();
        porche_back_front.stop();
    }
  //保时捷跑起来
    public void porcheGo(){
        startAnimation(animation);
    }
    OnGiftAnimationCompleted mOnCompleted;
    public void setOnGiftAnimationCommleted(OnGiftAnimationCompleted oo){
        mOnCompleted=oo;
    }


    public interface  OnGiftAnimationCompleted{
        //当当前动画执行完成后  去加载缓存数据
        void onCompleted();
    }

}
