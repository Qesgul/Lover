package com.jizhi.lover.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.jizhi.lover.R;

/**
 * Created by zheng_liu on 2018/3/21.
 */

public class ObliqueImage extends View{
    private Paint paint,paintPath;
    private int mWidth, mHight;
    Path pathPointer = new Path();

    public ObliqueImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setFilterBitmap(true);
        //抗锯齿
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        paintPath = new Paint();
        paintPath.setAntiAlias(true);
        paintPath.setColor(getResources().getColor(R.color.themeColor_mistuha));
        paintPath.setStyle(Paint.Style.FILL_AND_STROKE);
        paintPath.setStrokeWidth(3);
        paintPath.setDither(true);
    }
    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(600, widthMeasureSpec);
        int height = getMySize(600, heightMeasureSpec);

        if (width < height) {
            height = width;
        } else {
            width = height;
        }
        setMeasuredDimension(width, height);

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHight = getHeight();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSrc(canvas);
    }
    private void drawSrc(Canvas canvas) {
//        canvas.save();
//        canvas.clipPath(pathPointer);// 先画好模块
        Bitmap ceshi=BitmapFactory.decodeResource(getResources(), R.mipmap.weather_bg);
        float scale = 2;// 缩放量
        float w = getWidth();
        float h = getWidth();
        float scaleX = w / ceshi.getWidth();
        float scaleY = h / ceshi.getHeight();
        scale = scaleX > scaleY ? scaleX : scaleY;
        Matrix bottomMatrix=new Matrix();
        bottomMatrix.setScale(scale, scale);
//        canvas.drawBitmap(ceshi,bottomMatrix,paint);
//        canvas.restore();
        canvas.save();
        pathPointer.moveTo(0, mHight*2/3);
        pathPointer.lineTo(mWidth, mHight);
        pathPointer.lineTo(mWidth,0);
        pathPointer.lineTo(0,0);
        pathPointer.close();
 //       pathPointer.addCircle(500, 500, 300, Path.Direction.CW);
        canvas.clipPath(pathPointer);
        canvas.drawBitmap(ceshi,bottomMatrix,paint);
        canvas.restore();//恢复画布
    }
}

