package com.example.barchartview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.ScrollViewActivity;

public class MainActivity extends AppCompatActivity {
    private Button btEvent;
    String TAG="totalMemory";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(MainActivity.this,PagerViewActivity.class));
        //startActivity(new Intent(MainActivity.this,RecyclerVIewActivity.class));
       // startActivity(new Intent(MainActivity.this,RecyclerContainRecyclerActivity.class));
       // startActivity(new Intent(MainActivity.this,TextViewGroupActivity.class));
        //startActivity(new Intent(MainActivity.this,FlowLayoutActivity.class));
        startActivity(new Intent(MainActivity.this, ScrollViewActivity.class));

    }




}
