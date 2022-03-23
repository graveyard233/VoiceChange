package com.example.voicechange.FloatThings;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
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
import android.widget.Adapter;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voicechange.Info.Expand_updateAsrResultLayoutConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FloatViewService extends Service {

    public static Boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private Button button;

    private RecyclerView recyclerView;
    private List<FloatText> floatTextList = new ArrayList<>();

    MyRecyclerView myRecyclerView;

    private Expand_updateAsrResultLayoutConfig expand;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "service is onCreate: ");
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
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;


        layoutParams.width = windowManager.getDefaultDisplay().getWidth();//设置悬浮框的宽度为手机的宽度
        layoutParams.height = 300;
        layoutParams.x = 0;
        layoutParams.y = windowManager.getDefaultDisplay().getHeight();//悬浮窗生成位置放在最底下

        showFloatWindow();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String data = intent.getStringExtra("DATA");
        Gson gson = new Gson();
        expand = gson.fromJson(data,Expand_updateAsrResultLayoutConfig.class);
        Log.d("TAG", "onStartCommand: " + expand.getContentModule().getColor());
//        System.out.println("onStartCommand:" + data);
        if (myRecyclerView.getParent() != null){
            windowManager.removeView(myRecyclerView);
        }

        //设置是否全屏
        if (expand.getFull_screen().equals("1")){
            layoutParams.height = windowManager.getDefaultDisplay().getHeight();
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;//设置悬浮窗下面的控件可按
        } else { //暂时重置为悬浮框放在最底下
            layoutParams.width = windowManager.getDefaultDisplay().getWidth();//设置悬浮框的宽度为手机的宽度
            layoutParams.height = 600;
            layoutParams.x = 0;
            layoutParams.y = windowManager.getDefaultDisplay().getHeight();//悬浮窗生成位置放在最底下
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }

        //根据expand设置悬浮框位置
        //悬浮框宽度
        double true_width = layoutParams.width;
        layoutParams.width = (int) ((1.0 - Double.parseDouble(expand.getLocation_padding_left())
                        - Double.parseDouble(expand.getLocation_padding_right()))
                        * windowManager.getDefaultDisplay().getWidth());
        double true_height = layoutParams.height;
        layoutParams.height = (int) ((1.0 - Double.parseDouble(expand.getLocation_padding_top())
                - Double.parseDouble(expand.getLocation_padding_bottom()))
                * windowManager.getDefaultDisplay().getHeight());

        double true_x = windowManager.getDefaultDisplay().getWidth() * Double.parseDouble(expand.getLocation_padding_left());
        layoutParams.x = (int) true_x;
        double true_y = windowManager.getDefaultDisplay().getHeight() * Double.parseDouble(expand.getLocation_padding_top());
        layoutParams.y = (int) true_y;

        showFloatWindow();

        return super.onStartCommand(intent, flags, startId);

    }

    private void showFloatWindow() {

        initFloatText();
        myRecyclerView = MyRecyclerView.get(getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        myRecyclerView.setLayoutManager(layoutManager);
//        NewFloatTextAdapter adapter = new NewFloatTextAdapter(floatTextList);
//
//        if (expand != null){
//            adapter.setColor(expand.getNameModule().getColor());
//        }
//
//        myRecyclerView.setAdapter(adapter);
        NewFloatTextAdapter adapter = initAdapter();
        myRecyclerView.setAdapter(adapter);
        windowManager.addView(myRecyclerView,layoutParams);
        //设置是否允许移动
        if (expand != null && expand.getWindow_allow_move().equals("1")){
            myRecyclerView.setOnTouchListener(new FloatingOnTouch());
        }

    }

    private NewFloatTextAdapter initAdapter(){
        NewFloatTextAdapter adapter = new NewFloatTextAdapter(floatTextList);
        if (expand != null){
            adapter.setExpand(expand);
        }
        return adapter;
    }

    private void initFloatText(){
        String test = "正在转写，请耐心等待...";
        for (int i = 0; i < 3; i++) {
            FloatText f1 = new FloatText("炉石的判断可以做得很复杂，比如截图判断；但也有很简单的判断，呼出esc，有个菜单栏，假如你匹配到，进入对战，" +
                    "则会有一个认输按键，这个认输是红色的且坐标固定，但注意，当鼠标悬浮在上面时，亮度会加一层金色；假如下棋输掉，则认输按键消失，则红色没有。" ,
//                    "利用这一点来判断是否进入和输掉。其实选完英雄也可以判断其他玩家是否也选好英雄，对比随便一处的颜色即可(选英雄时整个界面灰度很高)。" +
//                    "版权声明：本文为CSDN博主「graveyard233」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。" +
//                    "原文链接：https://blog.csdn.net/graveyard233/article/details/109732042" ,
                    "张三",
                    test);
            floatTextList.add(f1);
            FloatText f2 = new FloatText("hello","李四");
            floatTextList.add(f2);
            FloatText f3 = new FloatText("我知道了","王五");
            floatTextList.add(f3);
            FloatText f4 = new FloatText("i know i know","九六");
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
//                    windowManager.updateViewLayout(recyclerView,layoutParams);
                    windowManager.updateViewLayout(myRecyclerView,layoutParams);
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
//        if (recyclerView.getParent() != null){
//            windowManager.removeView(recyclerView);
//        }
        if (myRecyclerView.getParent() != null){
            windowManager.removeView(myRecyclerView);
        }
        Log.d("TAG", "onDestroy: " + "floatService");
    }
}