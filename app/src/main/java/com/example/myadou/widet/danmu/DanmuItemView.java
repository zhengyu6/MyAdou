package com.example.myadou.widet.danmu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myadou.R;
import com.example.myadou.engine.DanmuMsgInfo;
import com.example.myadou.utils.ImageViewUtils;

/**
 * Created by 张晓辉 on 2018/1/18.
 */

public class DanmuItemView extends FrameLayout {
    private LayoutInflater inflater;
    private ImageView iv_danmu_avatar;
    private TextView tv_danmu_adouid;
    private TextView tv_danmu_msg;
    public DanmuItemView(@NonNull Context context) {
        super(context);
        inflater=LayoutInflater.from(getContext());
        init();
    }
    public void init(){
        //导入弹幕条目布局
        View view = inflater.inflate(R.layout.danmuitem,this,true);
        iv_danmu_avatar=view.findViewById(R.id.iv_danmu_avatar);
        tv_danmu_adouid=view.findViewById(R.id.tv_danmu_adouid);
        tv_danmu_msg=view.findViewById(R.id.tv_danmu_msg);
        setDefault();
    }
    //设置默认图片
    private void setDefault() {
        ImageViewUtils.getInstance().loadCircle(R.mipmap.female,iv_danmu_avatar);
    }

    public DanmuItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater=LayoutInflater.from(getContext());
        init();
    }
    //提供设置内容的方法
    public void bindData(DanmuMsgInfo info){
        if (TextUtils.isEmpty(info.getAvatar())){
            //如果等于null就设置上默认的头像
            ImageViewUtils.getInstance().loadCircle(R.mipmap.female,iv_danmu_avatar);
        }else{
            //否则就去加载头像
            ImageViewUtils.getInstance().loadCircle(info.getAvatar(),iv_danmu_avatar);
        }
        if (!TextUtils.isEmpty(info.getAdouID())){
            tv_danmu_adouid.setText(info.getAdouID());
        }
        if (!TextUtils.isEmpty(info.getText())){
            tv_danmu_msg.setText(info.getText());
        }
    }
}
