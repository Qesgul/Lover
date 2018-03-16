package com.jizhi.lover.frg;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jizhi.lover.R;
import com.jizhi.lover.Utils.PhotoBitmapUtils;
import com.jizhi.lover.Utils.PicUtils;
import com.jizhi.lover.act.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * Created by daxia on 2016/8/27.
 */
public class YourNameDialogFragment extends DialogFragment {

    public interface YourNameCallback {
        void updateName();
    }

    /**
     * Callback
     */
    private YourNameCallback callback;
    private Context mContext;
    private Bitmap head;// 头像Bitmap
    ImageView dia_img_main,dia_img_del;
    EditText dia_name;
    Button dia_sure,dia_cancel;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    private String user_name;
    private boolean isAddNewProfilePicture = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (YourNameCallback) context;
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
        View rootView = inflater.inflate(R.layout.dialog_your_name, container);
        initView(rootView);
        return rootView;
    }
    private void initView(View view) {
        dia_img_main=view.findViewById(R.id.IV_your_name_profile_picture);
        dia_img_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
            }
        });
        dia_img_del=view.findViewById(R.id.IV_your_name_profile_picture_cancel);
        dia_img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia_img_main.setImageResource(R.mipmap.ic_person_picture_default);
                isAddNewProfilePicture=false;
            }
        });
        dia_name=view.findViewById(R.id.EDT_your_name_name);
        preference = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        user_name=preference.getString("username", "");
        isAddNewProfilePicture=preference.getBoolean("isadded",false);
        dia_name.setText(user_name);
        dia_sure=view.findViewById(R.id.Btn_name_sure);
        dia_sure.setOnClickListener(new SureClickListener());
        dia_cancel=view.findViewById(R.id.Btn_name_cancel);
        dia_cancel.setOnClickListener(new CancelClickListener());
        loadProfilePicture();
    }
    class SureClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            saveYourName(getContext());
            callback.updateName();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }
                break;
            case 2:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        setPicToView(head);
                        dia_img_main.setImageBitmap(head);// 用ImageView显示出来
                        isAddNewProfilePicture=true;
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        String path=PhotoBitmapUtils.getPhotoFileName(getContext());
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "/"+"User.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveYourName(Context context) {
        preference = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        editor = preference.edit();
        //将登录标志位设置为false，下次登录时不在显示首次登录界面
        editor.putString("username", dia_name.getText().toString());
        editor.putBoolean("isadded",isAddNewProfilePicture);
        editor.commit();

    }
    private void loadProfilePicture() {
        dia_img_main.setImageDrawable(PicUtils.getProfilePictureDrawable(getContext(),isAddNewProfilePicture));
    }

}
