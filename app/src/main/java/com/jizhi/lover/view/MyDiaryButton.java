package com.jizhi.lover.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

import com.jizhi.lover.R;

/**
 * Created by daxia on 2016/11/13.
 */

public class MyDiaryButton extends AppCompatButton {


    public MyDiaryButton(Context context) {
        super(context);
    }

    public MyDiaryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDiaryButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setAllCaps(false);
        this.setTextColor(getResources().getColor(R.color.white));
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            this.setStateListAnimator(null);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
