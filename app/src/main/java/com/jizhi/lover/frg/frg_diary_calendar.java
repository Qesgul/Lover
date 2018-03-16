package com.jizhi.lover.frg;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jizhi.lover.R;
import com.jizhi.lover.act.DiaryActivity;
import com.jizhi.lover.view.calendar.PageEffectView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static java.util.Collections.binarySearch;

/**
 * Created by zheng_liu on 2018/3/15.
 */

public class frg_diary_calendar extends Fragment implements View.OnClickListener,
        OnDateSelectedListener, DayViewDecorator {

    /**
     * Calendar
     */
    private Calendar calendar;
    private Date currentDate;


    /**
     * UI
     */
    private RelativeLayout RL_calendar_content;
    private FloatingActionButton FAB_calendar_change_mode;

    /**
     * calendar Mode
     */
    private PageEffectView pageEffectView;
    private MaterialCalendarView materialCalendarView;

    private int currentMode;

    private static final int MODE_DAY = 1;
    private static final int MODE_MONTH = 2;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        currentDate = new Date();
        calendar.setTime(currentDate);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.frg_calendar, container, false);

        RL_calendar_content = (RelativeLayout) rootView.findViewById(R.id.RL_calendar_content);

        FAB_calendar_change_mode = (FloatingActionButton) rootView.findViewById(R.id.FAB_calendar_change_mode);
        //Set the color
        FAB_calendar_change_mode.getDrawable()
                .setColorFilter(getResources().getColor(R.color.themeColor_mistuha), PorterDuff.Mode.SRC_ATOP);
        FAB_calendar_change_mode.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //default mode
        currentMode = MODE_DAY;
        initCalendarMode();
    }

    public void refreshCalendar() {
        switch (currentMode) {
            case MODE_DAY:
                //TODO add decorators
                break;
            case MODE_MONTH:
                materialCalendarView.invalidateDecorators();
                break;
        }
    }

    private void initCalendarMode() {
        RL_calendar_content.removeAllViews();
        switch (currentMode) {
            case MODE_DAY:
                materialCalendarView = null;
                pageEffectView = new PageEffectView(getActivity(), calendar);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
                pageEffectView.setLayoutParams(params);
                RL_calendar_content.addView(pageEffectView);
                break;
            case MODE_MONTH:
                pageEffectView = null;
                materialCalendarView = new MaterialCalendarView(getActivity());
                RelativeLayout.LayoutParams calendarViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
                materialCalendarView.setLayoutParams(calendarViewParams);
                materialCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
                materialCalendarView.setSelectionColor(getResources().getColor(R.color.themeColor_mistuha));
                materialCalendarView.state().edit()
                        .setFirstDayOfWeek(Calendar.MONDAY)
                        .setCalendarDisplayMode(CalendarMode.MONTHS)
                        .commit();
                materialCalendarView.setCurrentDate(calendar);
                materialCalendarView.setDateSelected(calendar, true);
                materialCalendarView.setOnDateChangedListener(this);
                RL_calendar_content.addView(materialCalendarView);
                //Add view first , then add Decorator
                materialCalendarView.addDecorator(this);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.FAB_calendar_change_mode:
                //togle the mode
                if (currentMode == MODE_DAY) {
                    currentMode = MODE_MONTH;
                } else {
                    currentMode = MODE_DAY;
                }
                initCalendarMode();
                break;
        }
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //Make calendar sync the new date
        calendar.setTime(date.getDate());

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
      //  return binarySearch(getEntriesList(), day) >= 0;
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, getResources().getColor(R.color.themeColor_mistuha)));
    }
}