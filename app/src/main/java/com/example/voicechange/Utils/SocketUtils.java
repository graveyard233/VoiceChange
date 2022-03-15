package com.example.voicechange.Utils;

import android.content.Context;
import android.widget.Toast;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketUtils {
    private  Socket mySocket = null;
    private  String server_url = "http://172.16.100.160/";

    public  void SocketLink(Context context){
        try {
            IO.Options options = new IO.Options();
            mySocket = IO.socket(server_url,options);
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        mySocket.connect();
        mySocket.on("connect", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Toast.makeText(context,"socket连接成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
