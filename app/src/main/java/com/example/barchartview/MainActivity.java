package com.example.barchartview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.ScrollViewActivity;
import com.example.barchartview.animation.FrameAnimatolActivity;

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
        //startActivity(new Intent(MainActivity.this, ScrollViewActivity.class));
        //startActivity(new Intent(MainActivity.this, FrameAnimatolActivity.class));
        startActivity(new Intent(MainActivity.this, CanvasActivity.class));

//        Animal a1 = new Animal("Lion");
//        Animal a2 = new Animal("Crocodile");
//
//        System.out.println("Before Swap:- a1:" + a1 + "; a2:" + a2);
//        swap(a1, a2);
//        System.out.println("After Swap:- a1:" + a1 + "; a2:" + a2);
//
//        struct *a1 = malloc(struct Animal);
//        struct *a2 = malloc(struct Animal);
//        swap(a1, a2);
    }


//    struct  Animal{
//
//    }
//
//    public  void swap(struct *animal1, struct * animal2) {
//        struct *temp = malloc(struct Animal);
//        temp = animal1;
//        animal1 = animal2;
//        animal2 = temp;
//    }
//
//
//    public  void swap(Animal animal1, Animal animal2) {
//        Animal temp = new Animal("");
//        temp = animal1;
//        animal1 = animal2;
//        animal2 = temp;
//    }


//class Animal {
//    String name;
//
//    public Animal(String name) {
//        this.name = name;
//    }
//
//    public String toString() {
//        return name;
//    }
//}



}
