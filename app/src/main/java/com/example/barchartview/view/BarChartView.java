package com.example.barchartview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.barchartview.R;
import com.example.barchartview.data.ChartData;
import com.example.barchartview.utils.Bar;

import java.util.ArrayList;
import java.util.List;

public class BarChartView extends View {
    String TAG = "BarChartView";
    List<Bar> mListBar = new ArrayList<Bar>();
    //柱状图数据列表
    private float[] mDataList;
    //水平方向X轴坐标
    private String[] mHorizontalAxis;

    int mBarWith;//柱状图宽度
    int mRadius;//设置画的矩形半径
    int mMax;//最大数据
    int mGap;//文字和柱状图之间的距离
    Paint mBarPaint = new Paint();//画柱形图
    Paint mAxiPaint = new Paint();//写文字
    boolean enableGrowAnimation;//是否添加增加的动画

    public BarChartView(Context context) {
        this(context, null);
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化
        init();
    }

    public float[] getDataList() {
        return mDataList;
    }

    public void setDataList(float[] mDataList) {
        this.mDataList = mDataList;
    }

    public String[] getHorizontalAxis() {
        return mHorizontalAxis;
    }

    public void setHorizontalAxis(String[] mHorizontalAxis) {
        this.mHorizontalAxis = mHorizontalAxis;
    }

    private void init() {
        setDataList(new float[]{12, 45, 35, 84, 61, 26, 14,  24, 35});
        String data[] = new String[getDataList().length];
        for (int i = 0; i < data.length; i++) {
            data[i] = getDataList()[i] + "";
        }
        setHorizontalAxis(data);
        mMax = 84;
        mGap = 20;
        mAxiPaint.setTextAlign(Paint.Align.CENTER);//设置写的文字居中
        mBarPaint.setColor(getResources().getColor(R.color.colorAccent));//设置柱形颜色
        enableGrowAnimation = true;
    }

    boolean isEventInside = false;//是否触摸
    int touchID = 0;//触摸的条数

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == 0) {//如果是一根手指触摸的话
            if (isContain(event.getX(), event.getY())) {//如果在触屏内
               // Bar bar =mListBar.get(touchID);
                invalidate();
            }
        }
        return super.onTouchEvent(event);
    }

    //判断是否在触摸点中
    public boolean isContain(float x, float y) {
        for (int i = 0; i < mListBar.size(); i++) {
            if (mListBar.get(i).getLeft() < x && mListBar.get(i).getRight() > x && mListBar.get(i).getTop() < y && mListBar.get(i).getBottom() > y) {//触摸边缘不算
                touchID = i;
                isEventInside=true;
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //drawBarWidthAnimation(canvas);
        drawBars(canvas);
//        if(enableGrowAnimation){
//            drawBarWidthAnimation(canvas);
//        }else {
//            drawBars(canvas);
//        }
    }

    RectF rectF = new RectF();

    //画柱状图
    private void drawBars(Canvas canvas) {
            //遍历所有的Bar对象,一个个绘制
            for (int i = 0; i < mListBar.size(); i++) {
                if(i==touchID&&isEventInside){//如果触摸了并且触摸点在柱子里面
                    mBarPaint.setColor(getResources().getColor(R.color.colorPrimary));
                    isEventInside =false;
                }
                Bar bar = mListBar.get(i);
                String axis = mHorizontalAxis[i];//获取文本信息
                Log.d(TAG, "axis=" + axis);
                //mRadius是画的圆柱宽度的一半
                float textX = bar.getLeft() + mRadius;
                Log.d(TAG, "textX=" + textX);
                float textY = getHeight() - getPaddingBottom();
                Log.d(TAG, "textY=" + textY);
                //绘制坐标文本
                canvas.drawText(axis, textX, textY, mAxiPaint);
                rectF.set(bar.getLeft(), bar.getTop(), bar.getRight(), bar.getBottom());
                canvas.drawRoundRect(rectF, mRadius, mRadius, mBarPaint);
                mBarPaint.setColor(getResources().getColor(R.color.colorAccent));//设置柱形颜色

                //canvas.drawRect();
        }
    }

    private void drawBarWidthAnimation(Canvas canvas) {
        //遍历所有的Bar对象,一个个绘制
        for (int i = 0; i < mListBar.size(); i++) {
            Bar bar = mListBar.get(i);
            String axis = mHorizontalAxis[i];//获取文本信息
            Log.d(TAG, "axis=" + axis);
            //mRadius是画的圆柱宽度的一半
            float textX = bar.getLeft() + mRadius;
            Log.d(TAG, "textX=" + textX);
            float textY = getHeight() - getPaddingBottom();
            Log.d(TAG, "textY=" + textY);
            //绘制坐标文本
            canvas.drawText(axis, textX, textY, mAxiPaint);

            bar.setCurrentTop(bar.getCurrentTop() - 10);
            if (bar.getCurrentTop() <= bar.getTop()) {
                bar.setCurrentTop(bar.getTop());
                if (bar.getCurrentTop() == getPaddingTop()) {
                    enableGrowAnimation = false;
                }
            }
            rectF.set(bar.getLeft(), bar.getCurrentTop(), bar.getRight(), bar.getBottom());

            canvas.drawRoundRect(rectF, mRadius, mRadius, mBarPaint);
            //canvas.drawRect();
        }
        if (enableGrowAnimation) {
            postInvalidateDelayed(100);
        }
    }

    //资料填完是需要打印出来吗?打印后寄给谁
//一个人申请一房一厅可能性大吗  一房一厅有多大,价格多少

    //算出每个bar的真实位置
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //清空柱状条Bar的集合
        mListBar.clear();

        //去除padding,计算绘制所有柱状条所占的宽和高,此处是padding,内边距,不是外边距
        int width = w - getPaddingLeft() - getPaddingRight();
        int height = h - getPaddingTop() - getPaddingBottom();

        //按照数据集合的大小平分宽度,算出每个柱状图占的空间大小,包含空白区域
        int step = width / mDataList.length;

        mBarWith = step / 2;//设置要画的矩形底部宽
        mRadius = (int) (mBarWith / 2);//设置mRadius是底部矩形宽带的一半

        //计算第一条柱状条左边位置,此处直接减mRadius是因为mRadius是底部宽带的一半,所以可以直接减
        int barLeft = getPaddingLeft() + step / 2 - mRadius;

        //矩形参数
        Rect mTextRect = new Rect();

        //设置要画的字体大小,设置每个字符的大小
        mAxiPaint.setTextSize(36);
        mAxiPaint.setFakeBoldText(true);
        //第一个是文字信息,第二个是文字起始点,第三个是文字终点,获取指定长度字符大小,将结果封装到mTextRect中,C语言的写法
        mAxiPaint.getTextBounds(mHorizontalAxis[0], 0, mHorizontalAxis[0].length(), mTextRect);

        int MaxBarHeight = height - mTextRect.height() - mGap;//算出柱状图的高度


        //计算最大像素和最大数据值的比值
        float heightRatio = MaxBarHeight / mMax;
        Log.d(TAG, "heightRatio==" + heightRatio);
        for (float data : mDataList) {
            //创建柱状条对象
            Bar bar = new Bar();
            //设置原始数据
            bar.setValue(data);
            //设置柱状图的高度
            bar.setTransformedValue(bar.getValue() * heightRatio);
            //计算绘制柱状条的四个位置
            bar.setLeft(barLeft);
            //设置top点的坐标
            bar.setTop((int) (getPaddingTop() + MaxBarHeight - bar.getTransformedValue()));
            //设置right的坐标
            bar.setRight(barLeft + mBarWith);
            //设置底部坐标
            bar.setBottom(getPaddingTop() + MaxBarHeight);
            //将初始化的bar加入集合
            mListBar.add(bar);
            barLeft += step;//下一个bar的位置
        }
    }
}
