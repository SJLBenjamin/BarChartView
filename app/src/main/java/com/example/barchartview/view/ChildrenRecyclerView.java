package com.example.barchartview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
* 子控件recycleView
* */
public class ChildrenRecyclerView extends RecyclerView {
    boolean isBottom =false;
    boolean isTop =true;//因为默认进来就是应该可以往上滑动
    String TAG = "ChildrenRecyclerView";
    public ChildrenRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public ChildrenRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        //Log.d(TAG,"dispatchTouchEvent");
        if(isBottom){
            isBottom =false;
            return false;
        }
        if(isTop){
            isTop =false;
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(!canScrollVertically(1)){//如果不能向下滑动
           // getParent().requestDisallowInterceptTouchEvent(false);
            isBottom = true;
            Log.d(TAG,"滑动底部了=="+isTop);
        }

        if(!canScrollVertically(-1)){//如果不能向上滑动
            // getParent().requestDisallowInterceptTouchEvent(false);
            isTop = true;
            Log.d(TAG,"滑动顶部了=="+isBottom);
        }
    }

}
