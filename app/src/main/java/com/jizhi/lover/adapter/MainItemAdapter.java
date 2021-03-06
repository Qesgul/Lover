package com.jizhi.lover.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.jizhi.lover.R;
import com.jizhi.lover.act.DiaryActivity;
import com.jizhi.lover.act.MatterActivity;

/**
 * Created by zheng_liu on 2018/1/30.
 */

public class MainItemAdapter extends RecyclerView.Adapter<MainItemAdapter.ViewHolder>  {
    private Context mContext;
    int[] imgs = new int[]{R.mipmap.img_bill, R.mipmap.img_diary, R.mipmap.img_memo};
    String[] strings= new String[]{"BILL","DIARY","MATTER"};
    private OnRecyclerViewItemClickListener listener;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View View;
        ImageView img_item;
        TextView tv_title,tv_info;


        public ViewHolder(View itemView) {
            super(itemView);
            View = itemView;
            img_item=itemView.findViewById(R.id.IV_topic_icon);
            tv_title=itemView.findViewById(R.id.TV_topic_title);
            tv_info=itemView.findViewById(R.id.TV_topic_count);
        }
    }


    public MainItemAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public MainItemAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        final MainItemAdapter.ViewHolder holder = new MainItemAdapter.ViewHolder(view);
        // item click
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(holder.itemView, holder.getAdapterPosition());
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MainItemAdapter.ViewHolder holder, int position) {
        holder.img_item.setImageResource(imgs[position]);
        holder.tv_title.setText(strings[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
