package com.example.voicechange.FloatThings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerView extends RecyclerView {

    private List<FloatText> floatTextList = new ArrayList<>();

    private volatile static MyRecyclerView mInstance;

    public static MyRecyclerView get(Context context) {
        if (mInstance == null) {
            System.out.println("myRecyclerView没有单例，创建一个");
            synchronized (MyRecyclerView.class) {
                if (mInstance == null) {
                    mInstance = new MyRecyclerView(context);
                }
            }
        }
        else {
            System.out.println("已经有一个myRecyclerView了");
        }
        return mInstance;
    }



    //隐藏悬浮框
    public void hideTheView(){
        this.setVisibility(View.GONE);
        System.out.println("hide the view");
    }
    //显示悬浮框
    public void showTheView(){
        this.setVisibility(View.VISIBLE);
        System.out.println("show the view");
    }

    private void initFloatText(){
        for (int i = 0; i < 3; i++) {
            FloatText f1 = new FloatText("你好","张三");
            floatTextList.add(f1);
            FloatText f2 = new FloatText("hello","李四");
            floatTextList.add(f2);
            FloatText f3 = new FloatText("我知道了","王五");
            floatTextList.add(f3);
            FloatText f4 = new FloatText("i know i know","九六");
            floatTextList.add(f4);
        }
    }

    private MyRecyclerView(@NonNull Context context) {
        super(context);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
