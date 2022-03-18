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

public class FloatViewService extends Service {

    public static Boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private Button button;

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
        layoutParams.width = 500;
        layoutParams.height = 500;
        layoutParams.x = 500;
        layoutParams.y = 750;

        showFloatWindow();
    }




    private void showFloatWindow() {
        button = new Button(getApplicationContext());
        button.setText("哈哈哈");
        button.setBackgroundColor(Color.GRAY);
        windowManager.addView(button,layoutParams);
        button.setOnTouchListener(new FloatingOnTouch());
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
                    windowManager.updateViewLayout(button,layoutParams);
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
        if (button.getParent() != null){
            windowManager.removeView(button);
        }
        Log.d("TAG", "onDestroy: " + "floatService");
    }
}