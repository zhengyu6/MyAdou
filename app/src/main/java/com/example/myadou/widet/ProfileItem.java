package com.example.myadou.widet;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myadou.R;
import com.example.myadou.utils.ImageViewUtils;

/**
 * Created by 张晓辉 on 2018/1/10.
 */

public class ProfileItem extends FrameLayout {
    private ItemType type = ItemType.TYPE_NORMAL;
    private LayoutInflater inflater;
    private TextView tv_editprofile_name;
    private TextView tv_editprofile_value, tv_seperater;
    private ImageView iv_editprofile_avatar, iv_right_arraw, iv_icon;
    private RelativeLayout rl;

    public ProfileItem(@NonNull Context context) {
        super(context);
        inflater = LayoutInflater.from(getContext());
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.profile_item, null, false);
        rl = view.findViewById(R.id.rl);
        //为了避免布局发生变化，使用layoutparams重新设定view 的宽高
        int height = getResources().getDimensionPixelOffset(R.dimen.item_edit_profile_height);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        rl.setLayoutParams(layoutParams);

        tv_editprofile_name = view.findViewById(R.id.tv_editprofile_name);
        tv_editprofile_value = view.findViewById(R.id.tv_editprofile_value);
        iv_editprofile_avatar = view.findViewById(R.id.iv_editprofile_avatar);
        tv_seperater = view.findViewById(R.id.tv_seperater);
        iv_right_arraw = view.findViewById(R.id.iv_right_arraw);
        iv_icon = view.findViewById(R.id.iv_icon);
        addView(view);

    }

    /**
     * 在布局xml只要使用这个组件就会自动调用这个构造方法创建出来
     *
     * @param context
     * @param attrs
     */
    public ProfileItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(getContext());
        initView();
        //先通过上下文拿到一个typeArray
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProfileItem);
        int itemtype =  typedArray.getInt(R.styleable.ProfileItem_profileitemtype, 101);
        String itemtitle = typedArray.getString(R.styleable.ProfileItem_profileitemtitle);

        //当你拿引用的时候,getResourceId用这个 不用getInt;
        int leftIcon= typedArray.getResourceId(R.styleable.ProfileItem_profilelefticon,0);

        if (!TextUtils.isEmpty(itemtitle)){
            tv_editprofile_name.setText(itemtitle);
        }

        if (itemtype==ItemType.TYPE_NORMAL.getValue()){


        }
    }

    //设置条目的类型
    public void setType(ItemType type){
        this.type=type;
        if (type==ItemType.TYPE_AVATAR){
            tv_editprofile_value.setVisibility(View.INVISIBLE);
            iv_right_arraw.setVisibility(View.INVISIBLE);
            iv_editprofile_avatar.setVisibility(View.VISIBLE);
        }else if (type==ItemType.TYPE_NORMAL){
            tv_editprofile_value.setVisibility(View.VISIBLE);
            iv_right_arraw.setVisibility(View.INVISIBLE);
            iv_editprofile_avatar.setVisibility(View.INVISIBLE);

        }
    }

    public void setAvatar(String path){
        ImageViewUtils.getInstance().loadCircle(path,iv_editprofile_avatar);
    }
    public void setAvatar(Uri path){
        ImageViewUtils.getInstance().loadCircle(path,iv_editprofile_avatar);
    }


    public enum ItemType {
        TYPE_AVATAR(102), TYPE_NORMAL(101);
        private int value;

        ItemType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public void setValue(String str) {

    }
}
