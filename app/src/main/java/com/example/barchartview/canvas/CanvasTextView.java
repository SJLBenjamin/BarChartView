package com.example.barchartview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.barchartview.utils.Utils;

/*
* 画文字自定义View
* */
public class CanvasTextView extends View {

    private Paint mPaint;
    private float mDensity;
    private final int mCy = Utils.dp2px(550);
    private final int mRadius = Utils.dp2px(100);
    public CanvasTextView(Context context) {
        super(context);
        initData(context);
    }

    public CanvasTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public CanvasTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = context.getResources().getDisplayMetrics();
        mDensity = displayMetrics.density;
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);//设置画笔颜色
        mPaint.setStrokeWidth(5);//设置画笔宽度
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setTextSize(80);//设置文字大小
        mPaint.setStyle(Paint.Style.FILL);//设置填充

        float[] pos = new float[]{80, 100, 80, 200, 80, 300, 80, 400,
                25 * mDensity, 30 * mDensity,
                25 * mDensity, 60 * mDensity,
                25 * mDensity, 90 * mDensity,
                25 * mDensity, 120 * mDensity,};
        canvas.drawPosText("画图示例", pos, mPaint);// 两个构造函数,在这些点上画文字

        Path lineTextPath = new Path();
        lineTextPath.moveTo(65 * mDensity, 5 * mDensity);
        lineTextPath.lineTo(65 * mDensity, 200 * mDensity);
        //该方法可以沿着Path绘制文本 其中hOffset参数指定水平偏移 vOffset参数指定垂直偏移
        canvas.drawTextOnPath("在线上画图", lineTextPath, 0, 0, mPaint);


        canvas.save();
        canvas.translate(100 * mDensity, 5 * mDensity);//移动
        canvas.rotate(90);//画布旋转
        canvas.drawText("画图示例string2", 0, 11, 0, 0, mPaint);
        canvas.restore();

        canvas.save();
        mPaint.setShadowLayer(10, 15, 15, Color.GREEN);// 设置阴影
        //Canvas中的drawText的第一个参数是文字，第二个参数是开始位置（这里是从0开始，也就是从第一个文字开始），第三个参数是结束位置（这里是从文字的长度减一结束，也就是到最后一个文字结束），第四个参数是文字的在屏幕上的X位置，也就是最左边、最顶部的位置，第五个参数是文字在屏幕上的Y左边位置，也就是文字底部的位置
        canvas.drawText("画图示例string3", 0, 11, 140 * mDensity, 35 * mDensity, mPaint);// 对文字有效

        canvas.drawCircle(200 * mDensity, 150 * mDensity, 40 * mDensity, mPaint);// 阴影对图形无效,实测有效
        canvas.restore();


        //缩放文字
        for (int i = 0; i < 6; i++) {
            mPaint.setTextScaleX(0.4f + 0.3f * i);//设置按照X轴缩放
            canvas.drawText("画", 0, 1,
                    5 * mDensity + 50 * mDensity * i, 250 * mDensity, mPaint);
        }


        //沿着任意路径
        Path bSplinePath = new Path();
        bSplinePath.moveTo(5 * mDensity, 320 * mDensity);//移动
        bSplinePath.cubicTo(80 * mDensity, 260 * mDensity,//设置贝塞尔曲线
                200 * mDensity, 480 * mDensity,
                350 * mDensity, 350 * mDensity);
        mPaint.setStyle(Paint.Style.STROKE);
        // 先画出这两个路径
        canvas.drawPath(bSplinePath, mPaint);
        // 依据路径写出文字
        String text = "风萧萧兮易水寒，壮士一去兮不复返";
        mPaint.setColor(Color.GRAY);
        mPaint.setTextScaleX(1.0f);
        mPaint.setTextSize(20 * mDensity);
        canvas.drawTextOnPath(text, bSplinePath, 0, 15, mPaint);

        //恢复画笔默认设置
        mPaint.reset();
        canvas.drawLine(0, Utils.dp2px(420), getMeasuredWidth(), Utils.dp2px(420), mPaint);

        //文字测量,绘制一个圆
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);//设置灰色
        mPaint.setStrokeWidth(Utils.dp2px(15));//设置画笔宽度
        canvas.drawCircle(Utils.dp2px(110),mCy,mRadius,mPaint);
        //110+100=210   第二个圆  100+210=310
        //再画一个圆
        canvas.drawCircle(Utils.dp2px(320),mCy,mRadius,mPaint);

        //画圆弧
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置圆头
        RectF rectArc = new RectF(Utils.dp2px(10),mCy - mRadius,Utils.dp2px(210),mCy + mRadius);
        canvas.drawArc(rectArc,-90,225,false,mPaint);

        //画线
        Paint paintLine = new Paint();//这是一个反面教材，容易GC
        paintLine.setStyle(Paint.Style.STROKE);
        canvas.drawLine(0,mCy,getWidth(),mCy,paintLine);
        canvas.drawLine(Utils.dp2px(110),mCy - mRadius,Utils.dp2px(110),mCy + mRadius,paintLine);

        //绘制文字
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);//居中显示
        mPaint.setTextSize(Utils.dp2px(50));//设置文字大小

        //获取字体的属性
        Rect rect = new Rect();
        mPaint.getTextBounds("fgav",0,4,rect);
        float offsety =(rect.top+rect.bottom)/2;
        canvas.drawText("fgav",Utils.dp2px(110),mCy-offsety,mPaint);//画内容



        //拿基准线画,具体怎么弄得不知道搞什么
        Rect rect1 = new Rect();
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        mPaint.getFontMetrics(fontMetrics);
        float offsety2 = (fontMetrics.ascent + fontMetrics.descent)/2;
        float offsety1 = (rect1.top + rect1.bottom)/2;
        canvas.drawText("aaaa",Utils.dp2px(320),mCy - offsety2,mPaint);

        mPaint.reset();
        canvas.drawLine(0,Utils.dp2px(680),getMeasuredWidth(),Utils.dp2px(680),mPaint);

        //文字绘制2
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(Utils.dp2px(150));
        Rect rect3 = new Rect();
        mPaint.getTextBounds("aaaa",0,4,rect3);
        canvas.drawText("aaaa",0 - rect3.left,Utils.dp2px(800),mPaint);

        mPaint.setTextSize(Utils.dp2px(15));
        canvas.drawText("aaaa",0,Utils.dp2px(800) + mPaint.getFontSpacing(),mPaint);


    }

}
