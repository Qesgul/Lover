package com.jizhi.lover.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_MATTER= "create table Matter ("
            + "id integer primary key autoincrement, "
            + "memo text, "
            + "time text,"
            + "isover int) " ;


    private Context mContext;

    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MATTER);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
