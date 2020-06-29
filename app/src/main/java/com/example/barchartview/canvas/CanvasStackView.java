package com.example.barchartview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.barchartview.R;

/*
* 画布裁减和复原
* */
public class CanvasStackView extends View {
    Paint mPaint;
    Rect mRect;
    public CanvasStackView(Context context) {
        super(context);
        initData();
    }

    public CanvasStackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public CanvasStackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        mPaint= new Paint();
        mRect = new Rect(0,0,300,300);
        mPaint.setColor(Color.BLACK);
        // ----------设置填充风格----------
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        canvas.drawRect(mRect,mPaint);
        canvas.save();//保存当前画布


        //裁减一块区域
        canvas.clipRect(100,100,800,800);
        canvas.drawColor(Color.YELLOW);
        canvas.save();//保存当前画布

        canvas.clipRect(200,200,600,600);
        canvas.drawColor(Color.BLUE);
        canvas.save();//保存当前画布

        canvas.restore();//将栈顶画布拿出
        canvas.restore();//将栈顶画布拿出
        canvas.restore();//将栈顶画布拿出

        //canvas.drawColor(Color.BLACK);//设置为黑色
    }

}
