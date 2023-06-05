package com.example.nmrihping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import com.example.nmrihping.utils.codeUtils;

public class MainActivity extends AppCompatActivity {
    //保存全部的服务器IP地址
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText InputIP = findViewById(R.id.editTextTextPersonName2);
        InputIP.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        InputIP.setGravity(Gravity.TOP);
        Button searchBtn = findViewById(R.id.button);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "开始搜索...\n估计可能用的有点久?", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),FindActivity.class);
                intent.putExtra("serverIPList",InputIP.getText().toString());
                startActivity(intent);
//                NMRIHPing_Thread("today",InputIP.getText().toString());
//                _SendPacket("81.71.88.107",27070,new int[6]);
            }
        });
        //尝试连接服务器存储的服务器IP文件
        new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println("正在尝试从Gtiee获取存储的服务器IP...");
                try{
                    HttpsURLConnection httpURLConnection = (HttpsURLConnection)new URL("https://gitee.com/NiYeiDie/hell-is-full-server-ip/raw/master/AllServerIP.txt").openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String t = "";
                    while((t = br.readLine()) != null){
                        ip = ip + t + "\n";
                    }
                    System.out.println(ip);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            InputIP.setText(ip.replaceAll("null",""));
                            System.out.println("设置到TextEdit...");
                            InputIP.setSingleLine(false);
                            InputIP.setHorizontallyScrolling(false);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}