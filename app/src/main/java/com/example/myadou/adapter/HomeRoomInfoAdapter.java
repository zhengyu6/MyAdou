package com.example.myadou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myadou.R;
import com.example.myadou.bean.AllHomeInfo;
import com.example.myadou.utils.ImageViewUtils;
import com.example.myadou.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by 张晓辉 on 2018/1/18.
 */

public class HomeRoomInfoAdapter extends RecyclerView.Adapter<HomeRoomInfoAdapter.HomeInfoViewHolder> {
    Context context;
    ArrayList<AllHomeInfo.DataBean>  mList;
    OnMyClickListener myClickListener;
    private View v;

    //添加自定义点击事件
    public  interface OnMyClickListener {
        void OnMyClick(AllHomeInfo.DataBean bean);
    }


    public void setOnMyClickListener(OnMyClickListener listener){
        myClickListener= listener;
    }
    public HomeRoomInfoAdapter(Context context, ArrayList<AllHomeInfo.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public HomeInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(context).inflate(R.layout.item_home_live_info, null, false);
        int item_live_height_px = context.getResources().getDimensionPixelOffset(R.dimen.item_live_height);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, item_live_height_px);
        v.setLayoutParams(layoutParams);
        return new HomeInfoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final HomeInfoViewHolder holder, int position) {
        final AllHomeInfo.DataBean  homeInfo = mList.get(position);
        if (homeInfo!=null){
            //设置内容
            holder.setContent(homeInfo);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myClickListener!=null){
                    myClickListener.OnMyClick(homeInfo);
                    ToastUtils.show(homeInfo.getUserName());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HomeInfoViewHolder extends  RecyclerView.ViewHolder{
        TextView tv_live_title,tv_watch_nums,tv_host_id;
        ImageView iv_cover;

        public HomeInfoViewHolder(View itemView) {
            super(itemView);
            tv_host_id=itemView.findViewById(R.id.tv_hostid);
            tv_live_title=itemView.findViewById(R.id.tv_live_title);
            tv_watch_nums=itemView.findViewById(R.id.tv_watch_nums);
            iv_cover=itemView.findViewById(R.id.iv_bg);
        }
        public void setContent(AllHomeInfo.DataBean  info){
            tv_live_title.setText(info.getLiveTitle());
            tv_watch_nums.setText(info.getWatcherNums()+"");
            tv_host_id.setText(info.getUserId());
            ImageViewUtils.getInstance().load(info.getLiveCover(),iv_cover);

        }
    }
}
