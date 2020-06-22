package com.example.barchartview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import androidx.core.view.ViewConfigurationCompat;

import com.example.barchartview.R;
import com.example.barchartview.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/*
 * 自定义流式布局View,并且自定义属性LayoutParams
 * */
public class FlowLayout extends ViewGroup {
    List<View> mLineList; //一行的View
    List<List<View>> mAllList; //所有行的View
    List<Integer> mHeightList;//得到宽度集合
    String TAG = "FlowLayout";
    float mLastInterceptX =0;
    float mLastInterceptY= 0;
    private int mScaledPagingTouchSlop;
    boolean scrollable =false;//判断需不需要滑动,如果测量的高度大于流式布局的高度,那么才需要滑动
    int realH = 0;//表示所有子View的累加高度,即内容高度
    int measureHeight =0;//代表流式布局的测量高度
    Scroller mScroller;//滑动工具类

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration viewConfiguration =  ViewConfiguration.get(context);
        //获取最小的滑动距离
        mScaledPagingTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);

        mScroller = new Scroller(context);//初始化滑动类
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int currentY = 0;//当前的Y值
        Log.d(TAG, "长度为===" + mAllList.size());
        for (int i = 0; i < mAllList.size(); i++) {
            int width = 0;//宽度
            List<View> views = mAllList.get(i);//每一行的View
            for (int j = 0; j < views.size(); j++) {//遍历每一行的view
                View view = views.get(j);
                view.layout(width, currentY, width + view.getMeasuredWidth(), currentY + view.getMeasuredHeight());
                Log.d(TAG, "每个点的位置===" + width + " " + currentY + " " + width + view.getMeasuredWidth() + " " + currentY + view.getMeasuredHeight());
                width += view.getMeasuredWidth();//下一个View的left坐标
            }
            Log.d(TAG, "坐标为==" + mHeightList.get(i));
            currentY += mHeightList.get(i);//得出下一个点的坐标
        }
    }

    //因为measure需要多次测量,所以每次在measure中需要初始化,否则会保存上一次的信息,导致出错
    private void init() {
        mLineList = new ArrayList<>();
        mAllList = new ArrayList<>();
        mHeightList = new ArrayList<>();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept =false;//是否需要拦截
        float xInterceptX =ev.getX();//这一次的X坐标
        float yInterceptY =ev.getY();//这一次Y坐标
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isIntercept =false;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx =xInterceptX-mLastInterceptX;//x差值
                float dy =yInterceptY-mLastInterceptY;//y差值
               if(Math.abs(dy)-Math.abs(dx)>0&&Math.abs(dy)>mScaledPagingTouchSlop){//如果Y轴滑动大于X轴,并且大于最小滑动距离
                   isIntercept =true;
               }else {
                   isIntercept =false;
               }
                break;
            case MotionEvent.ACTION_UP:
                isIntercept =false;
                break;
            default:
                break;
        }
        mLastInterceptX=xInterceptX;
        mLastInterceptY=yInterceptY;
        return  isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//处理滑动事件
        if(!scrollable){//如果不需要滑动
            return super.onTouchEvent(event);
        }
        float eventY = event.getY();
        switch (event.getAction()){
           case MotionEvent.ACTION_DOWN:
               if(!mScroller.isFinished()){//如果还未完成滑动效果.那么停止滑动效果
                   mScroller.abortAnimation();
               }
               mLastInterceptY = eventY;
               break;
           case MotionEvent.ACTION_MOVE:
               float dy=mLastInterceptY-eventY;//本次手势滑动了多大距离
               int oldScrollY = getScrollY();//已经偏移了的距离


               break;
           case MotionEvent.ACTION_UP:
               break;
           default:
               break;
       }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //得到父控件的宽高和模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //流式布局的测量高度
        measureHeight =heightSize;

        //重点,每次measure的时候都初始化集合
        init();
        //当前行的宽高
        int currentLineHeight = 0;//当前行的最高高度
        int currentLineWidth = 0;//当前行的宽度,是所有View的宽度

        //整个流式布局的宽高
        int flowLayoutHeight = 0;//流式布局的高度
        int flowLayoutWidth = 0;//流式布局的宽度
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //测量子布局
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            int childViewMeasuredWidth = childView.getMeasuredWidth();//得到宽度
            int childViewMeasuredHeight = childView.getMeasuredHeight();//得到高度

            //看下当前的行的剩余的宽度是否可以容纳下一个子View,
            // 如果放不下，换行 保存当前行的所有子View,累加行高，当前的宽度，高度 置零
            if (currentLineWidth + childViewMeasuredWidth > widthSize) {//如果当前宽度加上此View宽度已经超过了父控件最大宽度
                if(mLineList.size()==1&&mLineList.get(0).getLayoutParams().height==LayoutParams.MATCH_PARENT){//如果当前行只有一行并且高度设置匹配父控件
                    currentLineHeight = Utils.dp2px(150);//强制设置高度
                }
                mAllList.add(mLineList);//先添加已经存储的View行
                mLineList = new ArrayList<>();//再创建一个一行View
                flowLayoutHeight += currentLineHeight;//得到总高
                flowLayoutWidth = Math.max(currentLineWidth, flowLayoutWidth);//得到最宽的宽度,此处得到的宽度是上一个View行的宽度
                mHeightList.add(currentLineHeight);//因为每次计算出来的都是最高的高度,所以直接添加就行,下一行会在下一行清0
                currentLineWidth = 0;//宽度清0
                currentLineHeight = 0;//高度清0
            }
            mLineList.add(childView);//添加child
            currentLineWidth += childViewMeasuredWidth;//得到宽度
            //if (childView.getMeasuredHeight() != ViewGroup.LayoutParams.MATCH_PARENT) {//getMeasuredHeight是需要测量完才能得到正确的数值,即onMeasure方法后
            //if (childView.getHeight() != ViewGroup.LayoutParams.MATCH_PARENT) {//如果getHeight需要布局完才能得到正确的数值,即layout方法,因为通过坐标计算的
                if (childView.getLayoutParams().height != ViewGroup.LayoutParams.MATCH_PARENT) {//如果View的布局参数宽度设置为MATCH_PARENT,那么就不参与计算
                currentLineHeight = Math.max(childViewMeasuredHeight, currentLineHeight);//得到最高的高度
            }
            if (i == childCount - 1) {
                mAllList.add(mLineList);//如果是最后一个,那么需要添加已经存储的View行
                flowLayoutHeight += currentLineHeight;//得到总高
                flowLayoutWidth = Math.max(flowLayoutWidth, currentLineWidth);
                mHeightList.add(currentLineHeight);//因为每次计算出来的都是最高的高度,所以直接添加就行,下一行会在下一行清0
            }
        }

        //重新测量layout_heigth = match_parent,因为宽高已经算了,所以不需要重新去累加宽高
        remeasure(widthMeasureSpec, heightMeasureSpec);

        if(heightMode==MeasureSpec.EXACTLY){//如果是精确模式,比较父view测量的高度和所有的子View累计高度
            flowLayoutHeight=Math.max(heightSize,flowLayoutHeight);
        }

        //如果真实(子View累计)高度大于测量高度,那么说明需要滑动
        scrollable=flowLayoutHeight>measureHeight;

        realH =flowLayoutHeight;//记录真实的高度

        //测量本身,如果是精确模式,那么就是本身的宽高
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : flowLayoutWidth, heightMode == MeasureSpec.EXACTLY ? heightSize : flowLayoutHeight);
    }

    //重新测量
    private void remeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < mAllList.size(); i++) {
            List<View> lineView = mAllList.get(i);//每一行的View集合
            for (int j = 0; j < lineView.size(); j++) {
                View view = lineView.get(j);//单个View
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                    int childMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, layoutParams.width);
                    //将宽度设置为MATCH_PARENT重写设置为当前行的高度,如果当前行只有一行应该强制赋值为150,在前面去设置
                    int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, mHeightList.get(i));
                    view.measure(childMeasureSpec, childHeightSpec);
                }
            }
        }
    }


    // 对传入的LayoutParams进行转化
    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    // 对传入的LayoutParams进行转化,传入的是xml文件
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    // 生成默认的LayoutParams
    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    // 检查LayoutParams是否合法
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) && p instanceof LayoutParams;
    }

    /*
     *自定义属性,继承MarginLayoutParamsCompat,因为其他LayoutParas没有setMargin方法,此类有
     *
     */
    static int gravity;

    static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.FlowLayout_Layout);
            try {
                gravity = a.getInt(R.styleable.FlowLayout_Layout_android_layout_gravity, -1);
            } finally {
                //需要释放
                a.recycle();
            }
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        @Override
        public String toString() {
            return "LayoutParams{" +
                    "gravity=" + gravity +
                    ", bottomMargin=" + bottomMargin +
                    ", leftMargin=" + leftMargin +
                    ", rightMargin=" + rightMargin +
                    ", topMargin=" + topMargin +
                    ", height=" + height +
                    ", width=" + width +
                    "} ";
        }

    }
}
