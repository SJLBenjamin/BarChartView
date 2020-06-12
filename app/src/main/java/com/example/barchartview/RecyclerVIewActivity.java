package com.example.barchartview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.example.barchartview.adpter.FruitAdapter;
import com.example.barchartview.utils.Fruit;

import java.util.ArrayList;
import java.util.List;

/*参考链接
* https://www.jianshu.com/p/b4bb52cdbeb7
* */
public class RecyclerVIewActivity extends AppCompatActivity {
    private RecyclerView rv;
    List<Fruit> mFruitList = new ArrayList<Fruit>();
    FruitAdapter fruitAdapter = new FruitAdapter(mFruitList);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
    }

    private void initView() {
        rv = (RecyclerView) findViewById(R.id.rv);
        //必须设置LinearLayoutManager,否则不能显示,此处设置的是线性布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);//竖直方向
       // layoutManager.setOrientation(RecyclerView.HORIZONTAL);//水平方向

      /* //网格布局
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);*/

        //瀑布流布局,StaggeredGridLayoutManager传入2个参数,第一个是布局的列数,第二个是布局的排列方向。布局文件的LinearLayout的宽度设为match_parent是因为瀑布流的宽度是 根据布局的列数来自动适配的,而不是固定值 。(GridLayoutManager也是根据布局的列数来自动适配的）
       // StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        for(int i=0;i<50;i++){
           if(i==1){
               mFruitList.add(new Fruit("1234567897894561231548973164564213333333333333333333333333333335648318423213468931",R.mipmap.ic_launcher));
           }else {
               mFruitList.add(new Fruit(i+"",R.mipmap.ic_launcher));
           }
        }
        rv.setAdapter(fruitAdapter);
    }

}
