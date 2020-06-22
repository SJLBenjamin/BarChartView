package com.example.barchartview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/*
* 此类是自定义ViewGroup实现分发事件给子控件
* */
public class MyViewGroup extends ViewGroup {
    String TAG ="MyViewGroup";
    Context mContext;
    public MyViewGroup(Context context) {
       this(context,null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN://如果down方法都拦截的话,子控件就不会收到任何事件,所以不能拦截down方法
                super.onInterceptTouchEvent(ev);
                return false;
            default:
                return true;//返回true代表view接收不到此事件,除非子控件要求父控件别拦截
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent");
        return super.onTouchEvent(event);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1.测量自身,类图,时序图
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //对子控件进行测量
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(int i=0;i<getChildCount();i++){//遍历得到子View
            View child = getChildAt(i);
            int left =getLeft();//求出左坐标
            int top =getTop();//求出上坐标
            int right = getRight() ;//求出右坐标
            int bottom =getBottom() ;//求出下坐标
            child.layout(left, top, right, bottom);
        }
    }

}
