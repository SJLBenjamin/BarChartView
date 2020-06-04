package com.example.barchartview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.barchartview.R;

import java.util.Calendar;
/*
* 自定义动画圆环
* */
public class CircleView extends View {
    Paint mCirclePaint;
    Paint mBackgroundPaint;
    RectF mRectF;
    int mCx;
    int mYx;
    Paint mTextPaint;
    long mDuration=3000;
    String TAG="CircleView";
    public CircleView(Context context) {
        this(context,null);

    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //绘制背景圆环
        canvas.drawArc(mRectF,0,360,false,mBackgroundPaint);
        //起始角度
        float startAngle=0;
        //计算扫过的角度
        float sweepAngle=mProgress*1.0f/100*360;
        //绘制进度
        canvas.drawArc(mRectF,startAngle,sweepAngle,false,mCirclePaint);

        //canvas.drawText("100",mCx,mYx,mTextPaint);
    }

    private void initData() {
        mCirclePaint = new Paint();
        mTextPaint = new Paint();
        mBackgroundPaint = new Paint();
        mTextPaint.setTextSize(50);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        setPadding(30,30,30,30);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int left=getPaddingLeft();
        int top=getPaddingTop();
        int right=w-getPaddingRight();
        int bottom=h-getPaddingBottom();
        mRectF = new RectF(left,top,right,bottom);
        mCx=w/2;//因为左右padding都是一样的所以中心点还是一样的
        mYx=h/2;//因为左右padding都是一样的所以中心点还是一样的
        initBackCircle();
        initCircle();
    }
int mProgress;
    //设置当前进度

    void setProgress(int progress){
        Log.d(TAG,"progress=="+progress);
        mProgress =progress;
        invalidate();
      //  Log.d(TAG,"chazhi=="+(Calendar.getInstance().getTimeInMillis()-startTime));
    }
    long startTime=0;
    void startAnimation(int degree){
        //在指定时间内会从0跑degree,调用的是setProgress方法
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "progress", 0, degree);
        objectAnimator.setDuration(mDuration);
        objectAnimator.start();
        startTime= Calendar.getInstance().getTimeInMillis();
    }

    private  void initBackCircle(){
            mBackgroundPaint.setStrokeWidth(30);
            mBackgroundPaint.setStyle(Paint.Style.STROKE);
            mBackgroundPaint.setColor( Color.parseColor("#323232"));

    }

    private void initCircle() {
        int colorArr[]={Color.parseColor("#CECECE"),
                Color.parseColor("#FF957A")};
        //float positionArr[]={0.1f,0.8f,0.1f};

        SweepGradient sweepGradient = new SweepGradient(mCx, mYx, colorArr, null);//角度着色器,渐变色,第一个和第二个参数表示圆形的坐标,第三个表示颜色数组,第四个表示每个颜色占用的比例,比例只能是0到1之间的数
        mCirclePaint.setShader(sweepGradient);//设置着色器,着色器的颜色优先于画笔的颜色
        mCirclePaint.setColor( Color.parseColor("#FF5F51"));
        mCirclePaint.setStyle(Paint.Style.STROKE);//设置描边
        mCirclePaint.setStrokeWidth(30);//设置画笔宽度
        mCirclePaint.setAntiAlias(true);//抗锯齿,如果两个坐标点可以组成标准的圆形话,那么设置和不设置效果一直
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔类型
        startAnimation(100);
    }
}
