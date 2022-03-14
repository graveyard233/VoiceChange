package com.example.voicechange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btn_link;
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_link = findViewById(R.id.btn_link);
        btn_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAsync();
            }
        });
    }

    public void getSycn(){
        new Thread(){
            @Override
            public void run() {
                String url = "https://www.baidu.com";
//                "https://www.baidu.com"    "http://172.16.100.160:3000/mock/12/v1/requipment/get_info"
                okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    Log.i(TAG, "response: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void getAsync(){
        String url = "https://www.baidu.com";
//                "https://www.baidu.com"    "http://172.16.100.160:3000/mock/12/v1/requipment/get_info"
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        //异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    Log.i(TAG, "response: " + response.body().string());
                }
            }
        });
    }
}