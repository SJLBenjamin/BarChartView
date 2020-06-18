package com.example.barchartview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/*
* 自定义TextViewGroup,按照老师写的,重点参考
* */
public class TextViewGroup extends ViewGroup {
    private static final int OFFSET = 100;//表示缩进的尺寸

    public TextViewGroup(Context context) {
        this(context, null);
    }

    public TextViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left =0;
        int top =0;
        int right =0;
        int bottom=0;
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            View child = getChildAt(i);
            left=i*OFFSET;

            //getWidth和getHeight是通过坐标相减得出来的,而坐标出来需要在onLayout执行完后才能得到
//            right+=left+child.getWidth();
//            bottom+=top+child.getHeight();
            right+=left+child.getMeasuredWidth();
            bottom+=top+child.getMeasuredHeight();
            child.layout(left,top,right,bottom);
            top+=child.getMeasuredHeight();//不考虑padding margin gravity xxx
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1.测量本身
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //2.得到父控件的宽高限制参数
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //3.将父控件的限制参数给子控件
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //得到子控件的布局参数
            LayoutParams layoutParams = child.getLayoutParams();
            //将父控件的限制参数和子控件的布局参数结合,得到子控件测量的实际大小
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, layoutParams.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, layoutParams.height);
            //子控件measure自己
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }

        //测量父控件的大小
        int width = 0;
        int height = 0;
        //求出宽度
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST://此处不是应该受到父控件的限制吗?此处是直接算出最大宽度
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    int widthAddOffset = i * OFFSET + child.getMeasuredWidth();
                    width = Math.max(width, widthAddOffset);//取最大的宽度
                }
                break;
        }
        //求出宽度
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                height =heightSize;
                break;
            case MeasureSpec.AT_MOST://此处不是应该受到父控件的限制吗?此处是直接算出最大高度
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    height += child.getMeasuredHeight();
                }
                break;
            default:
                break;
        }
        //6. 保存自身的尺寸
        setMeasuredDimension(width,height);
    }




}
