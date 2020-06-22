package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.barchartview.R;
/*
* scrollTo和scrollBy的区别,布局就移动画布,背景和内容都移动,TextView也移动画布,但是只重绘内容,具体看源码,先记着,移动的后View需要保持对应的坐标不变,所以看起来就像是View变化了
* */
public class ScrollViewActivity extends AppCompatActivity {
    private Button btScrollTo;
    private Button btScrollBy;
    TextView tvScrollTo;
    TextView tvScrollBy;
    private ConstraintLayout root;
    String TAG="ScrollViewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        root = (ConstraintLayout) findViewById(R.id.root);
        btScrollTo = (Button) findViewById(R.id.bt_scroll_to);
        btScrollBy = (Button) findViewById(R.id.bt_scroll_by);

        btScrollTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //此处移动的布局,布局的整个画布移动到(-50,-50)此坐标
                root.scrollTo(-50,-50);
                Log.d(TAG,"btScrollTo坐标为=="+root.getScrollX()+" y=="+root.getScrollY());
            }
        });

        btScrollBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //此处移动的布局,布局的整个画布在原有的坐标基础上往左上移动到(-50,-50)坐标
                root.scrollBy(-50,-50);
                Log.d(TAG,"btScrollBy=="+root.getScrollX()+"    y=="+root.getScrollY());
            }
        });


        tvScrollTo = (TextView) findViewById(R.id.tv_scroll_to);
        tvScrollBy = (TextView) findViewById(R.id.tv_scroll_by);

        tvScrollTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //scrollTo是移动到指定位置,并且移动的是画布,画布默认坐标是(0,0),并且TextView和Button重绘的时候只重绘内容,此画布是内容所属的画布,因为在View绘制的时候是先绘制背景,背景属于一个画布,然后绘制文字,文字也属于一个画布,
                //此处画布是(0,0),然后将画布移动到坐标为(-50,-50),所以相当于btScrollTo这个Button的内容坐标都需要下右移动50个像素,如果画布本身就到了(-50,-50),那么就不需要移动了
                tvScrollTo.scrollTo(-50,-50);
                Log.d(TAG,"tvScrollTo=="+tvScrollTo.getScrollX()+"  y=="+tvScrollTo.getScrollY());
            }
        });

        tvScrollBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //scrollBy是将画布移动多少个像素点,移动的是画布,并且TextView和Button重绘的时候只重绘内容,此画布是内容所属的画布,因为在View绘制的时候是先绘制背景,背景属于一个画布,然后绘制文字,文字也属于一个画布,我们移动的是文字所属画布
                //此处画布是(0,0),然后将画布移动(-50,-50)像素,所以相当于btScrollTo这个Button的内容坐标都需要下右移动50个像素,此处是多次移动
                tvScrollBy.scrollBy(-50,-50);
                Log.d(TAG,"tvScrollBy=="+tvScrollBy.getScrollX()+"  y=="+tvScrollBy.getScrollY());
            }
        });
    }

}


