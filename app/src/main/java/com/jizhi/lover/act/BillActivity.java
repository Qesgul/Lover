package com.jizhi.lover.act;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.jizhi.lover.R;

/**
 * Created by zheng_liu on 2018/3/21.
 */

public class BillActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getPackageName(), "222");
        setContentView(R.layout.activity_bill);
    }
}
