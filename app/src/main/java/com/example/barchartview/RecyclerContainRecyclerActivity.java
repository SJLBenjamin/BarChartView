package com.example.barchartview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.barchartview.adpter.FruitAdapter;
import com.example.barchartview.adpter.ParentAdapter;
import com.example.barchartview.utils.Fruit;
import com.example.barchartview.view.ChildrenRecyclerView;
import com.example.barchartview.view.ParentRecyclerView;

import java.util.ArrayList;
import java.util.List;

/*
* RecyclerView中有RecyclerView控件,解决冲突
* */
public class RecyclerContainRecyclerActivity extends AppCompatActivity {
    private ParentRecyclerView prc;
    private ChildrenRecyclerView crc;
    List<Fruit> mFruitList = new ArrayList<Fruit>();
    ParentAdapter parentAdapter = new ParentAdapter(mFruitList);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_contain_recycler);
        initView();
    }

    private void initView() {
        prc = (ParentRecyclerView) findViewById(R.id.prc);
       // crc = (ChildrenRecyclerView) findViewById(R.id.crc);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);//竖直方向
        prc.setLayoutManager(layoutManager);

//        LinearLayoutManager crcLayoutManager = new LinearLayoutManager(this);
//        crcLayoutManager.setOrientation(RecyclerView.VERTICAL);//竖直方向
//        crc.setLayoutManager(crcLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        for(int i=0;i<50;i++){
            mFruitList.add(new Fruit(i+"",R.mipmap.ic_launcher));
        }
        prc.setAdapter(parentAdapter);
    }
}
