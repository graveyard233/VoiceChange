package com.example.voicechange.FloatThings;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FloatViewService extends Service {

    public static Boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private Button button;

    private RecyclerView recyclerView;
    private List<FloatText> floatTextList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        layoutParams.width = windowManager.getDefaultDisplay().getWidth();//设置悬浮框的宽度为手机的宽度
        layoutParams.height = 300;
        layoutParams.x = 0;
        layoutParams.y = windowManager.getDefaultDisplay().getHeight();//悬浮窗生成位置放在最底下

        showFloatWindow();
    }




    private void showFloatWindow() {

        initFloatText();
        recyclerView = new RecyclerView(getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        FloatTextAdapter adapter = new FloatTextAdapter(floatTextList);
        recyclerView.setAdapter(adapter);
        windowManager.addView(recyclerView,layoutParams);
        recyclerView.setOnTouchListener(new FloatingOnTouch());
    }

    private void initFloatText(){
        for (int i = 0; i < 3; i++) {
            FloatText f1 = new FloatText("你好",i);
            floatTextList.add(f1);
            FloatText f2 = new FloatText("hello",i);
            floatTextList.add(f2);
            FloatText f3 = new FloatText("我知道了",i);
            floatTextList.add(f3);
            FloatText f4 = new FloatText("i know i know",i);
            floatTextList.add(f4);
        }
    }

    private class FloatingOnTouch implements View.OnTouchListener{

        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    x = (int) motionEvent.getRawX();
                    y = (int) motionEvent.getRawY();
//                    Log.e("TAG", "ACTION_DOWN: " + x +" , " + y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) motionEvent.getRawX();
                    int nowY = (int) motionEvent.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(recyclerView,layoutParams);
                    break;
                default:
                    break;

            }
            return false;
        }
    }

    public FloatViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recyclerView.getParent() != null){
            windowManager.removeView(recyclerView);
        }
        Log.d("TAG", "onDestroy: " + "floatService");
    }
}