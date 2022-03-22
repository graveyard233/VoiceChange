package com.example.voicechange.FloatThings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerView extends RecyclerView {

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
