package com.example.barchartview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ClipView extends View {
    Paint mPaint;

    public ClipView(Context context) {
        this(context,null);

    }

    public ClipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setTextSize(16);
        mPaint.setTextAlign(Paint.Align.RIGHT);
       //mPath = new Path();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();//保存画布
        canvas.clipRect(0, 0, 300, 300);//设置第一块裁减的区域为(0,0) (300,300)
        canvas.clipRect(200, 200, 500, 500, Region.Op.INTERSECT);//设置第二块裁减的区域为(200,200,500,500),并且设置只显示和前面设置的裁减区域重叠的部分
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, 300, 300, mPaint);//在这两块裁减的区域上画矩形
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(200, 200, 500, 500, mPaint);//在这两块裁减的区域上画矩形
        canvas.restore();//修复
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }
}
