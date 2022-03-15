package com.example.voicechange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voicechange.Info.SocketMsg;
import com.example.voicechange.Info.TerminalInfo;
import com.example.voicechange.POJO.JsonRootBean;
import com.example.voicechange.Utils.AdressUtils;
import com.example.voicechange.Utils.SocketUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private Button btn_link;
    private Button btn_socketLink;
    private OkHttpClient okHttpClient;
    private TextView textView1;
    private TextView textView2;
    private int flag = 0;


    private String equip_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_link = findViewById(R.id.btn_link);
        btn_link.setOnClickListener(this);
        btn_socketLink = findViewById(R.id.btn_socketLink);
        btn_socketLink.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_link:
                System.out.println(AdressUtils.getLocalip());

                getAsync();
                break;
            case R.id.btn_socketLink:
                SocketIOLink();
                textView2 = findViewById(R.id.textView2);
                textView2.setText(equip_id);
            default:
                break;

        }
    }

    public void SocketIOLink(){
        Socket mySocket = null;
        String server_url = "http://172.16.100.160:2021/";
        try {
            IO.Options options = new IO.Options();
            mySocket = IO.socket(server_url,options);
        } catch (URISyntaxException e){
            e.printStackTrace();
        }

        mySocket.connect();
        mySocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.i(TAG, "call: " + "连接成功");
            }
        });

        Log.e(TAG, "SocketIOLink: " + "开始初始化");
        SocketMsg socketMsg = new SocketMsg();
        socketMsg.setTo("0");
        socketMsg.setFrom(String.valueOf(equip_id));
        socketMsg.setRequest_id(String.valueOf(flag++));
        socketMsg.setFashion("single");
        socketMsg.setType("online");
        socketMsg.setContent(String.valueOf(equip_id));

        TerminalInfo terminalInfo = new TerminalInfo();
        terminalInfo.setIp(AdressUtils.getLocalip());
        terminalInfo.setMac(AdressUtils.getMac(true));
        terminalInfo.setModel("未定义");
        terminalInfo.setType("terminal");//card
        terminalInfo.setIs_login(1);
        terminalInfo.setScreen_key("");

        Gson gson_online = new Gson();
        String expend = gson_online.toJson(terminalInfo);

        socketMsg.setExpand(expend);

        String msg = gson_online.toJson(socketMsg);
        mySocket.emit("online",msg);//发送上线消息
        System.out.println("消息发送去服务器了");
        mySocket.on("system", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView2 = findViewById(R.id.textView2);
                        System.out.println("接受到了消息");
                        textView2.setText(args[0].toString());
                        for (int i = 0; i < args.length; i++) {
                            System.out.println(args[i].toString());
                        }
                    }
                });
            }
        });

    }

    public void getSycn(){//同步
        new Thread(){
            @Override
            public void run() {
                String mac = AdressUtils.getMac(false);
                System.out.println(mac);
                String url = "http://172.16.100.160:83/v1/equipment/get_info";
//                "https://www.baidu.com"    "http://172.16.100.160:3000/mock/12/v1/equipment/get_info"
                okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url + "?serial_number=" + mac )
                        .build();
                try {
                    Call call = okHttpClient.newCall(request);
                    Response response = call.execute();
                    //Response response = okHttpClient.newCall(request).execute();
                    String message = response.body().string();
                    Log.i(TAG, "response: " + message);
                    getMsg(message);
                    Gson gson = new Gson();
                    JsonRootBean jsonRootBean1 = gson.fromJson(message,JsonRootBean.class);
                    System.out.println(jsonRootBean1.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getMsg(String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView1 = findViewById(R.id.textView1);
                textView1.setText(response);
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAsync(){//异步get
        String mac = AdressUtils.getMac(false);
        System.out.println(mac);
        String url = "http://172.16.100.160:83/v1/equipment/get_info";
//                "https://www.baidu.com"    "http://172.16.100.160:83/v1/equipment/get_info"
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "?serial_number=" + mac )
                .build();
        System.out.println(url + "?serial_number=" + mac );
        Call call = okHttpClient.newCall(request);
        //异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i(TAG, "onFailure: " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Gson gson = new Gson();
                String msg = response.body().string();
                JsonRootBean jsonRootBean2 = gson.fromJson(msg,JsonRootBean.class);

                if (response.isSuccessful()){
                    Log.i(TAG, "response: " + msg);

                }
                if (jsonRootBean2.getStatus_code() == 200){
                    System.out.println(jsonRootBean2.getData().getEquipment_id());
                    equip_id = String.valueOf(jsonRootBean2.getData().getEquipment_id());
                    getMsg(equip_id);
                }
//                else if (root1.getMessage().equals("FindNotRecordInDB")){
//                    System.out.println(root1.getMessage() + ",我要post");
//                    //postAsync();
//                }

            }
        });
    }

    public void postAsync(){//post异步
        String mac = AdressUtils.getMac(false);
        String ip = AdressUtils.getLocalip();

        String url = "http://172.16.100.160:83/v1/equipment/add";
//                "https://www.baidu.com"    "http://172.16.100.160:83/v1/equipment/get_info"
        okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("rid","0");
        builder.add("type","terminal");
        builder.add("name","Android-"+ip);
        builder.add("system_type","android");
        builder.add("ip",ip);
        builder.add("serial_number",mac);
        builder.add("mac",AdressUtils.getMac(true));
        builder.add("version","1.0.0.1");

        RequestBody formBody = builder.build();

//                .add("rid","0")
//                .add("type","terminal")
//                .add("name","Android-"+ip)
//                .add("ip",ip)
//                .add("serial_number",mac)
//                .add("mac",AdressUtils.getMac(true))
//                .add("version","1.0.0.1").build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("content-type","application/x-www-form-urlencoded")
                .post(formBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String response_msg = response.body().string();
                if (response.isSuccessful()){
                    Log.i(TAG, "onResponse: " + response_msg);
                }
                Gson gson = new Gson();
                JsonRootBean jsonRootBean3 = gson.fromJson(response_msg,JsonRootBean.class);
                if (jsonRootBean3.getStatus_code() == 200){
                    System.out.println("添加成功");
                } else {
                    System.out.println("添加失败");
                    Log.i(TAG, "onResponse: " + response_msg);
                    getMsg(response_msg);
//                    Toast.makeText(getApplicationContext(),response_msg,Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}