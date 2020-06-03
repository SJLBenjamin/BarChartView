package com.example.barchartview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;
import androidx.customview.widget.ViewDragHelper.Callback;

public class SwipeCards extends ViewGroup {
    int mCenterX;
    int mCenterY;
    private int mCardGap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
    private static final float MAX_ALPHA_RANGE = 0.5f;
    private static final int MAX_DEGREE = 60;
    String TAG ="SwipeCards";
    ViewDragHelper mViewDragHelper;
    public SwipeCards(Context context) {
      this(context,null);
    }

    public SwipeCards(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(int i=0;i<getChildCount();i++){//遍历得到子View
            View child = getChildAt(i);
            int left =mCenterX-child.getMeasuredWidth()/2;//求出左坐标
            int top =mCenterY-child.getMeasuredHeight()/2+i*mCardGap;//求出上坐标
            int right = left + child.getMeasuredWidth();//求出右坐标
            int bottom = top + child.getMeasuredHeight();//求出下坐标
            child.layout(left, top, right, bottom);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w/2;
        mCenterY = h/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       measureChildren(widthMeasureSpec,heightMeasureSpec);
       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //滑动计算,当滑动一段时间后,手指松开就会调用此方法
    @Override
    public void computeScroll() {
        Log.d(TAG,"computeScroll");
        //mViewDragHelper的continueSettling方法,如果动画还没结束,那么就会返回true,动画结束了就返回false,触发重绘
        if (mViewDragHelper.continueSettling(false)) {
            invalidate();//invalidate会调用draw(),draw会调用computeScroll()方法,所以此处的逻辑是如果动画还没结束那么就一直重绘,直到动画结束
        }
    }

    Callback callback = new Callback() {

        //允许拖动所有的View
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        //拖动的的View水平方向的坐标
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            Log.d(TAG, "clampViewPositionHorizontal: " + left);
            return left;
        }

        //拖动的的View竖直方向的坐标
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return  top;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            //Log.d(TAG, "onViewPositionChanged: " + left);
            Log.d(TAG, " changedView.getWidth(): " +  changedView.getWidth());
            //计算位置改变后，与原来位置的中心点变化量
            int diffX = left + changedView.getWidth() / 2 - mCenterX;
            float ratio = diffX * 1.0f / getWidth();//计算偏移量相对于本身的宽度
            float degree = MAX_DEGREE * ratio;//求出偏移量,此处最大偏移量为60
            changedView.setRotation(degree);//设置旋转
            float alpha = 1 - Math.abs(ratio) * MAX_ALPHA_RANGE;//设置透明度
            changedView.setAlpha(alpha);//开启透明度
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            int left=releasedChild.getLeft();
            int right=releasedChild.getRight();
            if(left>mCenterX){//如果松手后,左边超过原先的中心,那么往左滑
                animateToRight(releasedChild);
            }else if(right<mCenterX){//如果松手后,右边超过原先的中心,那么往右滑
                animateToLeft(releasedChild);
            }else {//幅度不大,就会到中间位置
                animateToCenter(releasedChild);
            }
        }

        private void animateToCenter(View releasedChild) {
            int finalLeft = mCenterX - releasedChild.getWidth() / 2;
            int indexOfChild = indexOfChild(releasedChild);
            int finalTop = mCenterY - releasedChild.getHeight() / 2 + mCardGap * (getChildCount() - indexOfChild);
            mViewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);//指定位置重新画形状,此方法会调用onViewPositionChanged方法
            invalidate();
        }

        private void animateToRight(View releasedChild) {
            int finalLeft = getWidth() + mCenterX*2;
            int finalTop = releasedChild.getTop();
            mViewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);
            invalidate();
        }

        private void animateToLeft(View releasedChild) {
            int finalLeft = -getWidth();//左上角坐标为-本身宽度,这样子就不可见了
            int finalTop = 0;//距离为0
            mViewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);
            invalidate();
        }


//        private void animationToRight(View releasedChild) {
//            int finalLeft = getWidth() + releasedChild.getHeight();
//            int finalTop = releasedChild.getTop();
//            mViewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);
//            invalidate();
//        }
//
//        private void animationToLeft(View releasedChild){
//            int finalLeft = getWidth() + releasedChild.getHeight();
//            int finalTop = releasedChild.getTop();
//            mViewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);
//            invalidate();
//        }

    };
    
}
