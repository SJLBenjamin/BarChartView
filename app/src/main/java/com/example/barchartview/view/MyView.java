package com.example.barchartview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    Paint mPaint;
    String TAG="MyView";
    public MyView(Context context) {
       this(context,null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        mPaint=new Paint();
        mPaint.setColor(Color.parseColor("#FF7165"));
        mPaint.setStrokeWidth(15);
       // mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(0,0,getWidth()/2,getHeight()/2,mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return  super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);//请求父控件不要拦截事件
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"ACTION_UP");
                break;
        }
        //Log.d(TAG,"other thing");
        //Log.d(TAG,"onTouchEvent");
        return true;
    }

}
