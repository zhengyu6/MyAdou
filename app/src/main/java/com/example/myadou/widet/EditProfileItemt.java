package com.example.myadou.widet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 张晓辉 on 2018/1/10.
 */

public class EditProfileItemt extends FrameLayout {
    private ItemType type = ItemType.TYPE_NORMAL;

    private LayoutInflater inflater;
    private TextView tv_editprofile_name;
    private TextView tv_editprofile_value,tv_seperater;
    private ImageView iv_editprofile_avatar,iv_right_arraw,iv_icon;
    private RelativeLayout rl;
    public EditProfileItemt(@NonNull Context context) {
        super(context);
        inflater=LayoutInflater.from(getContext());
        initView();
    }

    private void initView() {
    }

    public EditProfileItemt(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public enum ItemType{
        ITEM_TYPE(102),TYPE_NORMAL(101);
        private int value;
        ItemType(int value){
            this.value=value;

        }
        public int getValue(){
            return  value;
        }
    }
    public void setValue(String str){

    }
}
