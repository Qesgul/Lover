package com.jizhi.lover.act;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jizhi.lover.R;
import com.jizhi.lover.Utils.PicUtils;
import com.jizhi.lover.adapter.MainItemAdapter;
import com.jizhi.lover.frg.YourNameDialogFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements YourNameDialogFragment.YourNameCallback{
    private ImageView IV_main_profile_picture;
    private MainItemAdapter mainItemAdapter;
    private RecyclerView main_recyclerView;
    private TextView TV_main_profile_username;
    private LinearLayout LL_main_profile;
    private SharedPreferences preference;
    private String user_name;
    private Boolean isadded=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请权限  第二个参数是一个 数组 说明可以同时申请多个权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 1);
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initProfile();
        loadProfilePicture();
    }
    public void init(){
        IV_main_profile_picture=findViewById(R.id.IV_main_profile_picture);
        TV_main_profile_username=findViewById(R.id.TV_main_profile_username);
        main_recyclerView=findViewById(R.id.RecyclerView_main);
        LL_main_profile = (LinearLayout) findViewById(R.id.LL_main_profile);
        LL_main_profile.setOnClickListener(new ChangePicListener());
        main_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainItemAdapter=new MainItemAdapter(this);
        mainItemAdapter.setOnItemClickListener(new MainItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        Intent intent=new Intent(MainActivity.this, BillActivity.class);
                        startActivity(intent);
                        Log.i(getPackageName(), "1 ");
                        break;
                    case 1:
                        Intent intent0=new Intent(MainActivity.this, DiaryActivity.class);
                        startActivity(intent0);
                        Log.i(getPackageName(), "1 ");
                        break;
                    case 2:
                        Intent intent1=new Intent(MainActivity.this, MatterActivity.class);
                        startActivity(intent1);
                        Log.i(getPackageName(), "1 ");
                        break;
                    default: break;
                }
            }
        });
        main_recyclerView.setAdapter(mainItemAdapter);
    }
    private void initProfile() {
        preference = getSharedPreferences("User", Context.MODE_PRIVATE);
        user_name=preference.getString("username", "");
        isadded=preference.getBoolean("isadded",false);
        TV_main_profile_username.setText(user_name);
    }
    private void loadProfilePicture() {
        IV_main_profile_picture.setImageDrawable(PicUtils.getProfilePictureDrawable(this,isadded));
    }
    class ChangePicListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            YourNameDialogFragment yourNameDialogFragment = new YourNameDialogFragment();
            yourNameDialogFragment.show(getSupportFragmentManager(), "yourNameDialogFragment");
        }
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void updateName() {
        initProfile();
        loadProfilePicture();
    }
}
