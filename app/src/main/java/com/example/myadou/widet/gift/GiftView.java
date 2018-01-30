package com.example.myadou.widet.gift;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myadou.R;
import com.example.myadou.bean.GiftMsgInfo;

import java.util.LinkedList;

/**
 * Created by 张晓辉 on 2018/1/30.
 */

public class GiftView extends LinearLayout {
    LayoutInflater inflater;
    private GiftItem giftItem0;
    private GiftItem giftItem1;
    private LinkedList<GiftMsgInfo>linkedList;
    public GiftView(Context context) {
        super(context);
        inflater=LayoutInflater.from(context);
        init();
    }

    private void init() {
        View view = inflater.inflate(R.layout.giftview,this,true);
        giftItem0=view.findViewById(R.id.giftItem0);
        giftItem1=view.findViewById(R.id.giftItem1);
    }
private void setDefault(){
        setOrientation(VERTICAL);
        giftItem0.setVisibility(View.INVISIBLE);
        giftItem1.setVisibility(View.INVISIBLE);
}
    public GiftView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater=LayoutInflater.from(context);
        init();
    }
    //提供获得可用弹幕item的方法
    public GiftItem getAvailableGiftItem(){
        //判断是否连发的同一个礼物
        return giftItem0;
    }
    //提供外界添加弹幕的消息
    public void addgift(GiftMsgInfo info,OnGiftViewAvaliable mOnAvaliable){
        //先拿到可用的弹幕，然后设置Info
        final  GiftItem availableGiftItem = getAvailableGiftItem();
        //先设置数据
        if (availableGiftItem!=null){
            //监控可用  执行动画
            if (mOnAvaliable!=null){
                mOnAvaliable.onAviable(availableGiftItem);
            }
        }else {
            //怎么办 缓存起来 等弹幕能用的时候再发出来  用什么存 先进先出 queue
            if (linkedList==null){
                linkedList=new LinkedList<GiftMsgInfo>();
            }
            //把消息加进来
            linkedList.add(info);
        }
    }
   private void showCacheMsg(GiftItem availableGift){
        if (linkedList!=null&&!linkedList.isEmpty()){
            GiftMsgInfo giftMsgInfo=linkedList.removeFirst();
            //缓存礼物可用  执行动画
        }
   }


    public  interface  OnGiftViewAvaliable{
        void onAviable(GiftItem availableDanmuItem);
    }
}
