package com.jizhi.lover.act;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jizhi.lover.R;
import com.jizhi.lover.adapter.MemoAdapter;
import com.jizhi.lover.data.Matter;
import com.jizhi.lover.data.MyDatabaseHelper;
import com.jizhi.lover.frg.AddMatterDialogFragment;
import com.jizhi.lover.frg.YourNameDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zheng_liu on 2018/3/19.
 */

public class MatterActivity extends BaseActivity implements AddMatterDialogFragment.MatterCallback{
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    List<Matter> matterList=new ArrayList<>();
    ImageView IV_memo_edit;
    RecyclerView recyclerView_matter;
    MemoAdapter memoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matter);
        init();

    }
    public void init(){
        dbHelper = new MyDatabaseHelper(this, "jizhi233.db", null, 1);
        db = dbHelper.getWritableDatabase();
        initMessage();
        IV_memo_edit=findViewById(R.id.IV_memo_edit);
        IV_memo_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMatterDialogFragment fragment=new AddMatterDialogFragment();
                fragment.show(getSupportFragmentManager(), "AddMatterDialogFragment");
            }
        });
        recyclerView_matter=findViewById(R.id.RecyclerView_memo);
        recyclerView_matter.setLayoutManager(new LinearLayoutManager(this));
        memoAdapter=new MemoAdapter(getApplicationContext(),matterList);
        recyclerView_matter.setAdapter(memoAdapter);
    }
    private void initMessage() {
        Cursor cursor = db.query("Matter", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                Matter message=new Matter();
                message.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
                message.setIsover(cursor.getInt(cursor.getColumnIndex("isover")));
                message.setTime(cursor.getString(cursor.getColumnIndex("time")));
                matterList.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void updateMatter(Matter matter) {
        if(matter!=null){
            matterList.add(matter);
            Toast.makeText(this,matter.getMemo(),Toast.LENGTH_SHORT).show();
            memoAdapter.notifyDataSetChanged();
        }
    }
}
