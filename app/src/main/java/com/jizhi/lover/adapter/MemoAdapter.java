package com.jizhi.lover.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jizhi.lover.R;
import com.jizhi.lover.act.DiaryActivity;
import com.jizhi.lover.act.MatterActivity;
import com.jizhi.lover.data.Matter;

import java.util.List;

/**
 * Created by zheng_liu on 2018/3/19.
 */

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder>{
    private Context mContext;
    List<Matter> list;

    static class ViewHolder extends RecyclerView.ViewHolder {
        android.view.View View;
        TextView TV_memo_item_content;


        public ViewHolder(View itemView) {
            super(itemView);
            View = itemView;
            TV_memo_item_content=itemView.findViewById(R.id.TV_memo_item_content);
        }
    }


    public MemoAdapter(Context context, List<Matter> list) {
        this.mContext = context;
        this.list=list;
    }

    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        final MemoAdapter.ViewHolder holder = new MemoAdapter.ViewHolder(view);
        holder.View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Matter matter=list.get(position);
                SpannableString spannableContent = new SpannableString(matter.getMemo());
                spannableContent.setSpan(new StrikethroughSpan(), 0, spannableContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(v.getContext(), "you clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MemoAdapter.ViewHolder holder, int position) {
        Matter matter=list.get(position);
        if(matter.getIsover()==1){
            SpannableString spannableContent = new SpannableString(matter.getMemo());
            spannableContent.setSpan(new StrikethroughSpan(), 0, spannableContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.TV_memo_item_content.setText(spannableContent);
            holder.TV_memo_item_content.setAlpha(0.4F);
        }else {
            holder.TV_memo_item_content.setText(matter.getMemo());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
