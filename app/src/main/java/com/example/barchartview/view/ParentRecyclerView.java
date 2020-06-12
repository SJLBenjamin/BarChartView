package com.example.barchartview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/*
* 父控件的RecyclerView,不拦截事件
* */
public class ParentRecyclerView extends RecyclerView {
    public ParentRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
       return false;
    }


}
