package com.example.myadou.widet.gift;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myadou.R;
import com.example.myadou.bean.Gift;

import java.util.ArrayList;

/**
 * Created by 张晓辉 on 2018/1/18.
 * 自定义送礼物列表  gridView
 */

public class GiftGridView extends GridView {
    //用来装数据的集合
    ArrayList<Gift> giftLists = new ArrayList<>();

    LayoutInflater inflater;
    private GiftGridAdapter giftGridAdapter;

     SetGiftDefault mSetGiftDefault;
    public GiftGridView(Context context,SetGiftDefault giftDefault) {
        super(context);
        inflater = LayoutInflater.from(context);
        mSetGiftDefault=giftDefault;
        init();
    }

    private void init() {
        //设置gridview的属性
        setNumColumns(4);
        //setGiftData();
        giftGridAdapter = new GiftGridAdapter();
        setAdapter(giftGridAdapter);
    }
    //在布局中使用时候  自动调用的构造方法
    public GiftGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);

        init();
    }

    //提供方法传入礼物数据
    public void setGiftData(ArrayList<Gift> gifts) {
        //先释放下资源
        giftLists.clear();
        //再赋值
        giftLists = gifts;
        giftGridAdapter.notifyDataSetChanged();
    }

    //定义送礼物的适配器
    class GiftGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return giftLists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_gift, null, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            final Gift gift = giftLists.get(position);
            //设置gridview item点击事件
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //设置选中礼物
                    if (gift.isSelected()){
                        gift.setSelected(false);
                        mSetGiftDefault.onUnSelected(gift);
                        //因为反选是当前gridview的事情  所以只需要在自己里面处理即可
                        giftGridAdapter.notifyDataSetChanged();

                    }else {
                        //如果当前礼物没有选中 就让调用者去处理
                        mSetGiftDefault.onSelected(gift);
                    }
                }
            });
            viewHolder.bindData(gift);

            return convertView;
        }

        class ViewHolder {

            private final ImageView iv_gift_icon;
            private final TextView tv_gift_name;
            private final TextView tv_gift_price;
            private final ImageView iv_select;

            public ViewHolder(View itemView) {
                iv_gift_icon = itemView.findViewById(R.id.iv_gift_icon);
                tv_gift_name = itemView.findViewById(R.id.tv_gift_name);
                tv_gift_price = itemView.findViewById(R.id.tv_gift_price);
                iv_select = itemView.findViewById(R.id.iv_select);

            }

            public void bindData(Gift gift) {
                if (gift != null) {
                    if (gift.getResId() != 0) {
                        iv_gift_icon.setBackgroundResource(gift.getResId());
                    } else {
                        iv_gift_icon.setBackgroundColor(getContext().getResources().getColor(R.color.transprant));
                    }
                    if (!TextUtils.isEmpty(gift.getName())) {
                        tv_gift_name.setText(gift.getName());
                    }
                    tv_gift_price.setText(gift.getPrice() + "斗币");
                    if (gift.isSelected()) {
                        iv_select.setBackgroundResource(R.mipmap.right);
                    } else {
                        iv_select.setBackgroundColor(getContext().getResources().getColor(R.color.transprant));
                    }
                }

            }


        }
    }

    //让所有礼物不选中
    public interface SetGiftDefault {
        //当未选中礼物被选中的时候
        void onSelected(Gift gift);

        //当选中礼物再次点击即取消选择的时候
        void onUnSelected(Gift gift);
    }

    //设置某一个礼物选中，这样其余礼物都置位反选
    public void setGiftSelected(Gift gift) {
        for (Gift g : giftLists){
           if (g.getGiftId()==gift.getGiftId()){
               g.setSelected(true);
               //刷新数据
           }else{
               g.setSelected(false);
           }
           giftGridAdapter.notifyDataSetChanged();
        }
    }
}
