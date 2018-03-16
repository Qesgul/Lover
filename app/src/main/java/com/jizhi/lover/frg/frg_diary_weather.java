package com.jizhi.lover.frg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jizhi.lover.R;

/**
 * Created by zheng_liu on 2018/3/15.
 */

public class frg_diary_weather extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.frg_weather, container, false);
        return view;
    }


}
