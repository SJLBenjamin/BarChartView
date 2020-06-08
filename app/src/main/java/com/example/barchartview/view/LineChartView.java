package com.example.barchartview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.barchartview.R;
import com.example.barchartview.utils.Bar;
import com.example.barchartview.utils.Dot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*
* 自定义折线图
* */
public class LineChartView extends View {
    List<Dot> mList = new ArrayList<Dot>();
    Paint mPaint = new Paint();
    Path mPath = new Path();
    String TAG = "LineChartView";
    Paint mTextPaint = new Paint();//写文字


    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    int circleRadius = 15;//设置画的圆弧半径
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);//在画布上画线段
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        for (int i = 0; i < mList.size(); i++) {
            canvas.drawText("2", mList.get(i).x, getHeight() / 2 - getBottom(), mTextPaint);
            canvas.drawCircle(mList.get(i).x, mList.get(i).y, circleRadius / 2, mPaint);
        }
    }


    int mBarMax;//Bar中最大值

    private void initData() {
        mPaint.setStrokeWidth(10);//设置线宽
        mPaint.setStyle(Paint.Style.STROKE);//设置描边,不如不这样设置,那么就画不出折线图
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        // mPath.close();
        for (int i = 3; i < 9; i++) {
            mList.add(new Dot(i, i + ""));
        }
        mList.add(new Dot(4, "4"));
        for (int i = 9; i >= 6; i--) {
            mList.add(new Dot(i, "" + i));
        }
        mBarMax = 9;
        setPadding(circleRadius * 2, circleRadius * 2, circleRadius * 2, circleRadius * 2);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Rect rect = new Rect();
        //设置要画的字体大小,设置每个字符的大小
        mTextPaint.setTextSize(36);
        mTextPaint.setFakeBoldText(true);
        mTextPaint.getTextBounds(mList.get(0).des, 0, mList.get(0).des.length(), rect);
        int realW = w - getPaddingLeft() - getPaddingRight();//求出实际宽度
        int realH = h - getPaddingBottom() - getPaddingTop();//求出实际高度
        int count = realW / (mList.size() - 1);//算出每份大小,5个点,组成四份,那么每个点都是起始位置

        int realHNotText = realH - rect.height();
        int pixBar = realHNotText / mBarMax;//算出最大值和高度的比值
        Log.d(TAG, "pixBar==" + pixBar);
        for (int i = 0; i < mList.size(); i++) {
            Dot dot = mList.get(i);
            dot.x = getPaddingLeft() + count * i;//这个就是中心点
            Log.d(TAG, "x==" + dot.x);
            dot.transformedValue = pixBar * dot.value;
            Log.d(TAG, "transformedValue==" + dot.transformedValue);
            dot.y = ((int) (realHNotText + getPaddingTop() - dot.transformedValue));//这个就是高度
            Log.d(TAG, "y==" + dot.y);
            if (i == 0) {
                mPath.moveTo(dot.x, dot.y);//设置起点
            } else {
                mPath.lineTo(dot.x, dot.y);//将当前点和上一个连起来
            }
        }
    }
}
