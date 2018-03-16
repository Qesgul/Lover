package com.jizhi.lover.frg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jizhi.lover.R;
import com.jizhi.lover.Utils.HttpUtil;
import com.jizhi.lover.act.MainActivity;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zheng_liu on 2018/3/15.
 */

public class frg_diary_weather extends Fragment {
    public SwipeRefreshLayout swipeRefresh;

    // 以下是 weather_noew 的内容

    private TextView degreeText;

    private TextView weatherInfoText;

    private RelativeLayout weaherNowLayout;

    private TextView updateTimeText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.frg_weather, container, false);
        initView(view);
        requestWeather("beijing");
        return view;
    }
    
    public void initView(View view) {
        // weather_now
        degreeText = view.findViewById(R.id.degree_text);
        weatherInfoText = view.findViewById(R.id.weather_info_text);
        updateTimeText = view.findViewById(R.id.update_time_text);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        weaherNowLayout = view.findViewById(R.id.weather_now_layout);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getNetworkInfo() == null){
                    Snackbar.make(swipeRefresh, "当前无网络，无法刷新 %>_<% ",Snackbar.LENGTH_LONG).setAction("去设置网络", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        }
                    }).show();
                    swipeRefresh.setRefreshing(false);
                }else{
                    showAnimationAlpha(weaherNowLayout);
                }
            }
        });
    }
    /**
     * 根据城市地点请求城市天气信息
     */
    public void requestWeather(String cityName){
        String address = "https://api.heweather.com/v5/weather?city=" + cityName + "&key=f44eaed629a3413892daca1ae2e3ce2f";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Looper.prepare();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"fail",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                final Weather weather = Utility.handleWeatherResponse(responseText);
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (weather != null && "ok".equals(weather.status)){
//                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
//                            editor.putString("weatherResponse", responseText);
//                            editor.putString("cityName",cityName);
//                            editor.apply();
//                            showWeatherInfo(weather);
//                        }else{
//                            showShort("获取天气信息2失败");
//                        }
//                    }
//                });
                Looper.prepare();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),responseText,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        swipeRefresh.setRefreshing(false);
    }
    
    // 判断是否有网络
    public NetworkInfo getNetworkInfo(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }
    /**
     * 更新动画，在一个动画结束时进行更新，再进行另一个动画
     */
    private void showAnimationAlpha(final View view){
        Animation alpha = AnimationUtils.loadAnimation(getActivity(),R.anim.alpha_before);
        view.startAnimation(alpha);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String cityName = prefs.getString("cityName", null);
                requestWeather(cityName);
                Animation alpha = AnimationUtils.loadAnimation(getActivity(),R.anim.alpha_after);
                view.startAnimation(alpha);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
