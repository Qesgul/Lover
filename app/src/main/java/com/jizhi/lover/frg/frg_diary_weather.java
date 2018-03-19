package com.jizhi.lover.frg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
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

import com.google.gson.Gson;
import com.jizhi.lover.R;
import com.jizhi.lover.Utils.HttpUtil;
import com.jizhi.lover.Utils.TimeUtils;
import com.jizhi.lover.act.MainActivity;
import com.jizhi.lover.data.Forecast;
import com.jizhi.lover.data.Weather;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zheng_liu on 2018/3/15.
 */

public class frg_diary_weather extends Fragment {
    public SwipeRefreshLayout swipeRefresh;
    public Weather weather;
    // 以下是 weather_noew 的内容
    private TextView degreeText;
    private TextView weatherInfoText;
    private RelativeLayout weaherNowLayout;
    private TextView updateTimeText,tv_weather_fx,tv_weather_fl,tv_weather_quality,tv_weather_shidu,tv_weather_aqi,tv_weather_range,tv_times_range,tv_tips;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.frg_weather, container, false);
        initView(view);
        requestWeather();
        return view;
    }
    
    public void initView(View view) {
        // weather_now
        degreeText = view.findViewById(R.id.degree_text);
        weatherInfoText = view.findViewById(R.id.weather_info_text);
        updateTimeText = view.findViewById(R.id.update_time_text);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        weaherNowLayout = view.findViewById(R.id.weather_now_layout);
        tv_weather_fx=view.findViewById(R.id.weather_fx);
        tv_weather_fl=view.findViewById(R.id.weather_fl);
        tv_weather_quality=view.findViewById(R.id.weather_quality);
        tv_weather_shidu=view.findViewById(R.id.weather_shidu);
        tv_weather_aqi=view.findViewById(R.id.weather_aqi);
        tv_weather_range=view.findViewById(R.id.weather_range);
        tv_times_range=view.findViewById(R.id.time_range);
        tv_tips=view.findViewById(R.id.tips);
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
    public void requestWeather(){
        String address = "https://www.sojson.com/open/api/weather/json.shtml?city=%E5%8D%97%E4%BA%AC";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),"fail",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                weather=new Gson().fromJson(responseText,Weather.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather!=null)
                        degreeText.setText(weather.getData().getWendu());
                        weatherInfoText.setText(weather.getData().getForecast().get(0).getType()+" "+"|");
                        tv_weather_fx.setText(weather.getData().getForecast().get(0).getFx());
                        tv_weather_fl.setText(weather.getData().getForecast().get(0).getFl());
                        tv_weather_shidu.setText(weather.getData().getShidu());
                        tv_weather_quality.setText("空气"+weather.getData().getQuality());
                        tv_weather_aqi.setText(String.valueOf(weather.getData().getForecast().get(0).getAqi()));
                        String temp=weather.getData().getForecast().get(0).getHigh().substring(3)+"~"+weather.getData().getForecast().get(0).getLow().substring(3);
                        tv_weather_range.setText(temp);
                        tv_times_range.setText(weather.getData().getForecast().get(0).getSunrise()+"~"+weather.getData().getForecast().get(0).getSunset());
                        tv_tips.setText(weather.getData().getForecast().get(0).getNotice());
                        updateTimeText.setText("今日"+TimeUtils.getSecTime()+"发布");
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
                requestWeather();
                Animation alpha = AnimationUtils.loadAnimation(getActivity(),R.anim.alpha_after);
                view.startAnimation(alpha);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
