package com.example.barchartview.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.barchartview.R;
import com.example.barchartview.utils.Fruit;
import java.util.List;

/*
* Adapter需要实现指定Holder对象
*
* */
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitHolder> {
    List<Fruit> mFruit;
    public FruitAdapter(List<Fruit> fruit){
        mFruit =fruit;
    }

    //holder实体类
    class FruitHolder extends RecyclerView.ViewHolder {
        private ImageView fruitImage;
        private TextView fruitName;
        public FruitHolder(@NonNull View itemView) {
            super(itemView);
            fruitImage = (ImageView) itemView.findViewById(R.id.fruit_image);
            fruitName = (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }

    //为每个条目创建Holder
    @NonNull
    @Override
    public FruitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        * 此处需要拿到上下文,因为布局文件需要依附界面,所以需要通过parent.getContext()去得到依附的对象才能去解析布局
        *
        *第二个参数表示当前解析布局要用到父控件
        *
        * 第三个参数表示是否将当前布局添加到父布局中去,如果添加,那么返回的view就是父View,当前布局在此父view中,如果不添加,那么父view只是作为父控件的参数给子控件,返回的子控件
        *
        */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);
        return new FruitHolder(view);
    }

    //绑定holder
    @Override
    public void onBindViewHolder(@NonNull FruitHolder holder, final int position) {
        holder.fruitName.setText(mFruit.get(position).getName());
        holder.fruitImage.setImageResource(mFruit.get(position).getImageId());
        holder.fruitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//得到View的上下文
                Toast.makeText(v.getContext(),mFruit.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    //返回条目
    @Override
    public int getItemCount() {
        return mFruit.size();
    }
}
