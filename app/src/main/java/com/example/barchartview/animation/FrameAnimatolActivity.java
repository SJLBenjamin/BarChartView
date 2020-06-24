package com.example.barchartview.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.barchartview.R;

/*
* 逐帧动画
* */
public class FrameAnimatolActivity extends AppCompatActivity {

    private ImageView imgTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animatol);
        imgTarget = (ImageView) findViewById(R.id.img_target);
        fromJava();
    }

    //xml实现
    void fromXml(){
        AnimationDrawable drawable = (AnimationDrawable)imgTarget.getDrawable();
        drawable.start();
    }

    //java代码实现
    void fromJava(){
        AnimationDrawable animationDrawable = new AnimationDrawable();
        for (int i = 1; i < 8; i++) {
            //  第一个参数是你的资源文件的名字，不带后缀的，，第二个参数是你资源文件所在的目录，比如layout,drawable或者是values，，，第三个是你的包名
            int id = getResources().getIdentifier("run" + i, "drawable", getPackageName());
            Drawable drawable = getDrawable(id);
            if (null != drawable) {
                //加入每帧,并且设置时间
                animationDrawable.addFrame(drawable, 200);
            }
        }
        imgTarget.setImageDrawable(animationDrawable);
        animationDrawable.start();
    }

}
