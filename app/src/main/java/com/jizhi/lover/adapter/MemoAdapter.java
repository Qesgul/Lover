package com.jizhi.lover.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
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
import com.jizhi.lover.data.MyDatabaseHelper;

import java.util.List;

/**
 * Created by zheng_liu on 2018/3/19.
 */

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder>{
    private Context mContext;
    private List<Matter> list;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Handler handler=new Handler(); //在主线程中创建handler

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_memo_item, parent, false);
        final MemoAdapter.ViewHolder holder = new MemoAdapter.ViewHolder(view);
        dbHelper = new MyDatabaseHelper(mContext, "jizhi233.db", null, 1);
        db = dbHelper.getWritableDatabase();
        holder.View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                final Matter matter=list.get(position);
                matter.setIsover(Math.abs(matter.getIsover()-1));
                new Thread(){//创建一个新的线程
                    public void run(){
                        try {
                            modifyMatter(matter);
                            handler.post(new Runnable() {
                                public void run() {
                                    setItem(holder,position);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MemoAdapter.ViewHolder holder, int position) {
        setItem(holder,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void setItem(MemoAdapter.ViewHolder holder, final int position) {
        Matter matter=list.get(position);
        if(matter!=null){
            if(matter.getIsover()==1){
                SpannableString spannableContent = new SpannableString(matter.getMemo());
                spannableContent.setSpan(new StrikethroughSpan(), 0, spannableContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.TV_memo_item_content.setText(spannableContent);
                holder.TV_memo_item_content.setAlpha(0.4F);
            }else {
                holder.TV_memo_item_content.setText(matter.getMemo());
               holder.TV_memo_item_content.setAlpha(1F);
            }
        }
    }
    public void modifyMatter(Matter message){
        ContentValues values = new ContentValues();
        values.put("memo", message.getMemo());
        values.put("isover",message.getIsover());
        values.put("time",message.getTime());
        db.update("Matter", values, "time = ?", new String[] { message.getTime() });
        values.clear();
    }
}
