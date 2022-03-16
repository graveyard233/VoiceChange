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

    //全局变量
    private static final String TAG = "MainActivity";
    private Button btn_link;
    private Button btn_socketLink;
    private Button btn_joinGroup;
    private Button btn_login;
    private OkHttpClient okHttpClient;
    private TextView textView1;
    private TextView textView2;
    private int flag = 0;

//  新的分支,这是标记

    public static final String joinGroup = "joinGroup";//加进组通知
    private static final String EVENT_GROUP = "group";
    private static final String FASHION_SINGLE = "single";
    public static final String join = "join";//加进组
    public static final String startMeeting = "startMeeting";

    private String equip_id;

    private JsonRootBean equipmentInfo = new JsonRootBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_link = findViewById(R.id.btn_link);
        btn_link.setOnClickListener(this);
        btn_socketLink = findViewById(R.id.btn_socketLink);
        btn_socketLink.setOnClickListener(this);
        btn_joinGroup = findViewById(R.id.btn_joinGroup);
        btn_joinGroup.setOnClickListener(this);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_link:
                System.out.println(AdressUtils.getLocalip());//打印虚拟机IP
                getEquipInfo();//连接服务器，获取设备信息
                break;
            case R.id.btn_socketLink:
                SocketIOLink();//连接服务器
                textView2 = findViewById(R.id.textView2);
                textView2.setText(equip_id);
                break;
            case R.id.btn_joinGroup:
//                SocketIOJoinGroup();
                break;
            case R.id.btn_login:
                postAsync_Login();//登录的同时进行加组
                break;
            default:
                break;

        }
    }

    //socket连接服务器，并且监听服务器的返回消息
    public void SocketIOLink(){
        Socket mySocket = null;
        String server_url = "http://172.16.0.160:2021/";
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
        String expend = gson_online.toJson(terminalInfo);//将类转成string类型方便发送

        socketMsg.setExpand(expend);

        String msg = gson_online.toJson(socketMsg);
        mySocket.emit("online",msg);//发送上线消息
        System.out.println("发送上线消息");
        Socket finalMySocket = mySocket;
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
                            SocketMsg socketMsg1 = new SocketMsg();
                            socketMsg1 = gson_online.fromJson(args[i].toString(),SocketMsg.class);//将服务器返回的json信息转换成自己的对象
                            System.out.println(socketMsg1.getType());

                            if (socketMsg1.getType().equals(joinGroup)){//判断服务器返回的类型是否为joingroup
                                sendData(finalMySocket,EVENT_GROUP,"0",FASHION_SINGLE,join,socketMsg1.getContent());//将设备加入组，之后就能收到来自服务器的信息了
                            }
                        }
                    }
                });
            }
        });

    }

    //发送登录的socket信息
    public void sendData(Socket socket,String emitEvent,String to,String fashion,String type,String content) {
        // Log.e("TAG","sendData");
        SocketMsg socketMsg = new SocketMsg();
        socketMsg.setTo(to);
        socketMsg.setFrom(String.valueOf(equip_id));
        socketMsg.setFashion(fashion);
        socketMsg.setRequest_id(String.valueOf(flag++));
        socketMsg.setType(type);
        socketMsg.setContent(content);

        Gson gson = new Gson();
        String msg = gson.toJson(socketMsg);
        socket.emit(emitEvent,msg);
    }

    //同步请求，一般不用
    public void getSycn(){
        new Thread(){
            @Override
            public void run() {
                String mac = AdressUtils.getMac(false);
                System.out.println(mac);
                String url = "http://172.16.0.160/v1/equipment/get_info";
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

    //打印信息
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

    //异步get,获取设备信息
    public void getEquipInfo(){//getAsync
        String mac = AdressUtils.getMac(false);
        System.out.println(mac);
        String url = "http://172.16.0.160/v1/equipment/get_info";
        okHttpClient = new OkHttpClient();//实例化一个okhttp对象
        Request request = new Request.Builder()
                .url(url + "?serial_number=" + mac )
                .build(); // 配置请求主题
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
                equipmentInfo = gson.fromJson(msg,JsonRootBean.class);//获取这个设备

                if (response.isSuccessful()){//假如连接成功了就打印回复
                    Log.i(TAG, "response: " + msg);
                }
                if (jsonRootBean2.getStatus_code() == 200){
                    System.out.println(jsonRootBean2.getData().getEquipment_id());
                    equip_id = String.valueOf(jsonRootBean2.getData().getEquipment_id());
                    getMsg(equip_id);//打印设备ID
                }
                else if (jsonRootBean2.getMessage().equals("FindNotRecordInDB")){
                    System.out.println(jsonRootBean2.getMessage() + ",我要添加设备");
                    addEquip();
                }

            }
        });
    }

    //post异步,向服务器添加设备
    public void addEquip(){//postAsync
        String mac = AdressUtils.getMac(false);
        String ip = AdressUtils.getLocalip();

        String url = "http://172.16.0.160/v1/equipment/add";
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

        Request request = new Request.Builder()
                .url(url)
                .addHeader("content-type","application/x-www-form-urlencoded")
                .post(formBody)
                .build();
        System.out.println(request.toString());
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String response_msg = response.body().string();
                if (response.isSuccessful()){//打印回复信息
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
                }
            }
        });
    }

    //post异步，人员登录
    public void postAsync_Login(){//登录之后才能接受到消息

        String url = "http://172.16.0.160/v1/public/login";

        okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();

        builder.add("username",equipmentInfo.getData().getParticipant_name());
        builder.add("password","123456");
        builder.add("type","meeting_participant");
        builder.add("participant_id",String.valueOf(equipmentInfo.getData().getParticipant_id()));
        builder.add("department_id","0");
        builder.add("serial_number",AdressUtils.getMac(false));


        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("content-type","application/x-www-form-urlencoded")
                .addHeader("authorization",
                        "MWFjMDY0NjlxOGZjZjg3ZmJiZDQ3ZTViZjkwOWYwZDY4YjU1NWMxNTJkZDhhZDk4MDFhZjA2MzE3OTEwMWEyMQ==")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String response_msg = response.body().string();
                if (response.isSuccessful()){
                    Log.i(TAG, "onResponse: " + response_msg);
                    Gson gson = new Gson();
                    JsonRootBean jsonRootBean4 = gson.fromJson(response_msg,JsonRootBean.class);
                }

            }
        });
    }

}