package com.example.myadou.widet.danmu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.example.myadou.R;
import com.example.myadou.engine.DanmuMsgInfo;

import java.util.LinkedList;

/**
 * Created by 张晓辉 on 2018/1/18.
 */

public class DanmuView extends LinearLayout {
    LayoutInflater inflater;
    private DanmuItemView danmuItemView0;
    private DanmuItemView danmuItemView1;
    private Animation animation;
    private int screenWidth;
    private LinkedList<DanmuMsgInfo> linkedList;

    public DanmuView(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();
    }

    public void init() {
        View view=inflater.inflate(R.layout.danmuview,this,true);
        danmuItemView0=view.findViewById(R.id.danmuitem0);
        danmuItemView1=view.findViewById(R.id.danmuitem1);
        setDefault();
        //测量屏幕高度
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay=windowManager.getDefaultDisplay();
        screenWidth=defaultDisplay.getWidth();
    }
    private void setDefault() {
        setOrientation(VERTICAL);
        danmuItemView0.setVisibility(View.INVISIBLE);
        danmuItemView1.setVisibility(View.INVISIBLE);
    }
    public DanmuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater=LayoutInflater.from(getContext());
        init();
    }
    //提供获得可用弹幕item的方法
    public DanmuItemView  getAvailableDanmuItem(){
        //如果第一个弹幕的状态是隐藏状态，那么他就可用
        if (danmuItemView0.getVisibility()==View.INVISIBLE){
            return danmuItemView0;
        }
        else if (danmuItemView1.getVisibility()==View.INVISIBLE){
            return danmuItemView1;
        }else{
            return null;
        }
    }

    private void dandan(DanmuMsgInfo info, final DanmuItemView danmuItemView) {
        danmuItemView.bindData(info);
        //测量弹幕itemview的高度
        int w = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        danmuItemView.measure(w,h);
        final  int width=danmuItemView.getMeasuredWidth();
        //让弹幕动起来  让动画在主线程执行
        post(new Runnable() {
            @Override
            public void run() {
             //属性动画
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(danmuItemView,"translationX",screenWidth,-width);
                objectAnimator.setDuration(5000);
                //加速器差值器  默认是匀速差值器
                objectAnimator.setInterpolator(new LinearInterpolator());
                //先要把控件展示出来
                danmuItemView.setVisibility(View.VISIBLE);
                //启动动画
                objectAnimator.start();

                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //通知结束 ，当动画执行完成之后，再置为可用
                        danmuItemView.setVisibility(View.INVISIBLE);
                        //然后开始去缓存队列中取信息，并展示
                        showCacheMsg(danmuItemView);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }
                });
            }
        });
    }
    private void showCacheMsg(DanmuItemView availableDanmuItem) {
        if (linkedList!=null&&!linkedList.isEmpty()){
            DanmuMsgInfo danmuMsgInfo = linkedList.removeFirst();
            dandan(danmuMsgInfo,availableDanmuItem);
        }
    }
  //提供 外界添加弹幕的消息
    public void addDanmu(DanmuMsgInfo danmuMsgInfo) {
   //先拿到可用的弹幕,然后设置info
        final DanmuItemView danmuItemView = getAvailableDanmuItem();
        //先设置数据
        if (danmuItemView!=null){
            dandan(danmuMsgInfo,danmuItemView);
        }else {
            //怎么办 缓存起来 等弹幕能用的时候再发出来,用什么存  先进先出 queue
            if (linkedList==null){
                linkedList=new LinkedList<DanmuMsgInfo>();
            }
            //把消息加进来
            linkedList.add(danmuMsgInfo);
        }
    }
}
