package com.jizhi.lover.Utils;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Axes on 2018/2/22.
 */

public class TimeUtils {
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getNowTime(Long times){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(times);
		return simpleDateFormat.format(date);
	}
	/**
	 * 获取当前时间day
	 * @return
	 */
	public static String getDayTime(Long times){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(times);
		return simpleDateFormat.format(date);
	}
	/**
	 * 获取当前时间second
	 * @return
	 */
	public static String getSecTime( ){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date(System.currentTimeMillis());
		return simpleDateFormat.format(date);
	}
	public static int daysBetween(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayTime = sdf.format(new Date());// 获取当前的日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(date));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(todayTime));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		Log.d("日期", Integer.parseInt(String.valueOf(between_days)) + "");
		return Integer.parseInt(String.valueOf(between_days));
	}
}
