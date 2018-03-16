package com.jizhi.lover.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jizhi.lover.R;
import com.jizhi.lover.frg.frg_diary_calendar;
import com.jizhi.lover.frg.frg_diary_times;
import com.jizhi.lover.frg.frg_diary_weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zheng_liu on 2018/3/15.
 */

public class DiaryActivity extends BaseActivity implements View.OnClickListener{
    private ViewPager Diary_viewpager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;
    private Button btn_diary_calendar,btn_diary_times,btn_diary_weather;
    private TextView TV_diary_topbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initView();
        setSelect(1);
    }
    private void initView()
    {
        btn_diary_calendar=findViewById(R.id.btn_diary_calendar);
        btn_diary_calendar.setOnClickListener(this);
        btn_diary_times=findViewById(R.id.btn_diary_times);
        btn_diary_times.setOnClickListener(this);
        btn_diary_weather=findViewById(R.id.btn_diary_weather);
        btn_diary_weather.setOnClickListener(this);
        Diary_viewpager=findViewById(R.id.viewpager_diary);
        TV_diary_topbar_title=findViewById(R.id.TV_diary_topbar_title);
        mFragments = new ArrayList<Fragment>();
        Fragment mTab01 = new frg_diary_calendar();
        Fragment mTab02 = new frg_diary_times();
        Fragment mTab03 = new frg_diary_weather();
        mFragments.add(mTab01);
        mFragments.add(mTab02);
        mFragments.add(mTab03);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {

            @Override
            public int getCount()
            {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };
        Diary_viewpager.setAdapter(mAdapter);

        Diary_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int arg0)
            {
                int currentItem = Diary_viewpager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_diary_calendar:
                setSelect(0);
                break;
            case R.id.btn_diary_times:
                setSelect(1);
                break;
            case R.id.btn_diary_weather:
                setSelect(2);
                break;
            default:
                break;
        }
    }

    private void setSelect(int i)
    {
        setTab(i);
        Diary_viewpager.setCurrentItem(i);
    }
    private void setTab(int i)
    {
        switch (i)
        {
            case 0:
                btn_diary_calendar.setBackground(getDrawable(R.drawable.shape_diary_left_select));
                btn_diary_times.setBackground(getDrawable(R.drawable.shape_diary_mid_normal));
                btn_diary_weather.setBackground(getDrawable(R.drawable.shape_diary_right_normal));
                btn_diary_calendar.setTextColor(getResources().getColor(R.color.white));
                btn_diary_times.setTextColor(getResources().getColor(R.color.themeColor_mistuha));
                btn_diary_weather.setTextColor(getResources().getColor(R.color.themeColor_mistuha));
                TV_diary_topbar_title.setText("Calendar");
                break;
            case 1:
                btn_diary_calendar.setBackground(getDrawable(R.drawable.shape_diary_left_normal));
                btn_diary_times.setBackground(getDrawable(R.drawable.shape_diary_mid_select));
                btn_diary_weather.setBackground(getDrawable(R.drawable.shape_diary_right_normal));
                btn_diary_calendar.setTextColor(getResources().getColor(R.color.themeColor_mistuha));
                btn_diary_times.setTextColor(getResources().getColor(R.color.white));
                btn_diary_weather.setTextColor(getResources().getColor(R.color.themeColor_mistuha));
                TV_diary_topbar_title.setText("Times");
                break;
            case 2:
                btn_diary_calendar.setBackground(getDrawable(R.drawable.shape_diary_left_normal));
                btn_diary_times.setBackground(getDrawable(R.drawable.shape_diary_mid_normal));
                btn_diary_weather.setBackground(getDrawable(R.drawable.shape_diary_right_select));
                btn_diary_calendar.setTextColor(getResources().getColor(R.color.themeColor_mistuha));
                btn_diary_times.setTextColor(getResources().getColor(R.color.themeColor_mistuha));
                btn_diary_weather.setTextColor(getResources().getColor(R.color.white));
                TV_diary_topbar_title.setText("江苏 南京");
                break;
        }
    }
}
