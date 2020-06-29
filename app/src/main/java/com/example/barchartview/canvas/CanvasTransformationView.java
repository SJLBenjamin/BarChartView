package com.example.barchartview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/*
* canvas画布平移缩放切等操作
*
* */
public class CanvasTransformationView extends View {

    Paint mPaint;
    private final Rect mRect = new Rect(0, 0, 200, 200);
    public CanvasTransformationView(Context context) {
       super(context);
       init();
    }


    public CanvasTransformationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasTransformationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);//设置画笔颜色
        canvas.drawColor(Color.GRAY);//设置画布为灰色

        canvas.translate(100,100);//设置画布平移,画布移动距离为100,100,原点位置现在为(-100,-100)

        canvas.drawRect(mRect, mPaint);//画正方形

        canvas.translate(100,300);//设置画布平移,画布移动距离为100,300,以前画的图会在屏幕一致的位置,保存不变
        canvas.scale(0.5f,0.5f);//画布缩放
        canvas.drawRect(mRect, mPaint);//再画正方形

        canvas.translate(0,300);//因为画布已经被缩放了,设置画布平移,画布移动距离原本为0,300,其实只移动了0,150
        canvas.rotate(30);//设置旋转
        canvas.drawRect(mRect, mPaint);//再画正方形

        canvas.translate(0,300);//因为画布已经被缩放了并且旋转了,设置画布平移,画布移动距离原本为0,300,其实移动距离为缩放后加上在旋转后x,Y坐标上移动,移动距离是0,150,但是是在旋转后的x,y坐标上移动
        canvas.skew(5f,5f);//扭曲
        canvas.drawRect(mRect, mPaint);//再画正方形

    }


}
