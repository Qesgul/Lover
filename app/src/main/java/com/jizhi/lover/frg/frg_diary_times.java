package com.jizhi.lover.frg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jizhi.lover.R;
import com.jizhi.lover.Utils.TimeUtils;

import java.text.ParseException;

/**
 * Created by zheng_liu on 2018/3/15.
 */

public class frg_diary_times extends Fragment {
    TextView tv_times;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.frg_times, container, false);
        tv_times=view.findViewById(R.id.LL_entries_item_day);
        try {
            tv_times.setText(String.valueOf(TimeUtils.daysBetween("2017-07-12")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }


}
