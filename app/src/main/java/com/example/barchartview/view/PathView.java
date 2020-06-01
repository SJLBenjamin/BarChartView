package com.example.barchartview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.barchartview.R;

public class PathView  extends View {

    public PathView(Context context) {
        super(context,null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPath.moveTo(0,0);
        mPath.lineTo(10,10);
        mPath.lineTo(20,20);
        mPath.lineTo(30,30);
    }

    Path mPath;
    Paint mPaint;
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath,mPaint);
    }
}
