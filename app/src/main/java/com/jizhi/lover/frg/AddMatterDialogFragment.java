package com.jizhi.lover.frg;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jizhi.lover.R;
import com.jizhi.lover.Utils.PhotoBitmapUtils;
import com.jizhi.lover.Utils.PicUtils;
import com.jizhi.lover.Utils.TimeUtils;
import com.jizhi.lover.data.Matter;
import com.jizhi.lover.data.MyDatabaseHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * Created by daxia on 2016/8/27.
 */
public class AddMatterDialogFragment extends DialogFragment {

    public interface MatterCallback {
        void updateMatter(Matter matter);
    }

    /**
     * Callback
     */
    private MatterCallback callback;
    private Context mContext;
    private EditText EDT_matter;
    private Button dia_sure,dia_cancel;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (MatterCallback) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(R.layout.dialog_matter, container);
        initView(rootView);
        return rootView;
    }
    private void initView(View view) {
        EDT_matter=view.findViewById(R.id.EDT_matter);
        dia_sure=view.findViewById(R.id.Btn_matter_sure);
        dia_sure.setOnClickListener(new SureClickListener());
        dia_cancel=view.findViewById(R.id.Btn_matter_cancel);
        dia_cancel.setOnClickListener(new CancelClickListener());
        dbHelper = new MyDatabaseHelper(getContext(), "jizhi233.db", null, 1);
        db = dbHelper.getWritableDatabase();
    }
    class SureClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Matter msg=new Matter();
            msg.setMemo(EDT_matter.getText().toString());
            msg.setIsover(0);
            msg.setTime(TimeUtils.getNowTime());
            AddMatter();
            callback.updateMatter(msg);
            dismiss();
        }

    }
    class CancelClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            dismiss();
        }
    }

    public void AddMatter(){
        ContentValues values = new ContentValues();
        values.put("memo", EDT_matter.getText().toString());
        values.put("isover",0);
        values.put("time", TimeUtils.getNowTime());
        db.insert("Matter", null, values); // 插入数据
        values.clear();
    }
}
