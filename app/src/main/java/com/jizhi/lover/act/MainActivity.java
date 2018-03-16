package com.jizhi.lover.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
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
    public void updateName() {
        initProfile();
        loadProfilePicture();
    }
}
