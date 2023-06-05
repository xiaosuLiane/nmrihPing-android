package com.example.nmrihping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nmrihping.utils.PlayerDataAdapter;
import com.example.nmrihping.utils.PlayerListTemplete;
import com.example.nmrihping.utils.codeUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindActivity extends AppCompatActivity {
    private List<PlayerListTemplete> templetesList = new ArrayList<>();
    Handler handler = new Handler();
    int ipListlength = 0;

    public void _SendPacket(String[] host,final int[] count){
        String p[] = host;
        final int[] count_ = {ipListlength};
        ListView listView = (ListView) findViewById(R.id.PlayerList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("第"+i+"个选项");
                String s = "";
                PlayerListTemplete p_now = templetesList.get(i);
                Intent intent = new Intent(getApplicationContext(),ContentActivity.class);
                intent.putExtra("s_name",p_now.getServerName());
                //把玩家存储集合拼接字符串
                for(String t : p_now.getPlayerList())
                    s = s + t + "\n";
                intent.putExtra("s_players",s);
                intent.putExtra("s_map",p_now.getMapName());
                intent.putExtra("s_ip",p_now.getServerHost());

                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "正在重新发送数据包...", Toast.LENGTH_SHORT).show();
                String host = templetesList.get(i).getServerHost().split("：")[1];
                templetesList.remove(i);
                _SendPacket(new String[]{host},new int[6]);
                return false;
            }
        });
        TextView textView = (TextView)findViewById(R.id.textView6);
        textView.setText("剩余 "+ ipListlength +" 个IP未获取完毕...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String p_ : p) {
                    if(p_ == null)
                        return;
                    String[] p__ = p_.split(":");
                    System.out.println("当前Host:"+p__[0]+":"+p__[1]);
                    count[0] = 6;
                    //记录玩家列表情况
                    List<String> playerList_ = new ArrayList<>();
                    while(true){
                        try {
                            byte[] a = codeUtils.GetBytesFromHex("ffffffff54536f7572636520456e67696e6520517565727900");
                            DatagramPacket packet;
                            byte[] b,b1,b2;
                            String s,t_s;
                            DatagramSocket socket = new DatagramSocket();
                            socket.setSoTimeout(10000);
                            InetSocketAddress address = new InetSocketAddress(p__[0], Integer.parseInt(p__[1]));
                            packet = new DatagramPacket(a, a.length, address);
                            //第一次接收包
                            b = codeUtils.retData(socket,packet);
                            t_s = codeUtils.GetHexStringFromBytes(b,true);
                            s = "ffffffff54536f7572636520456e67696e6520517565727900"+t_s.split("ffffffff41")[1];
                            a = codeUtils.GetBytesFromHex(s);
                            System.out.println(s);
                            //第二次接收包（接收到第一次服务器返回包）
                            packet = new DatagramPacket(a, a.length, address);
                            b1 = codeUtils.retData(socket,packet);
                            String msg = new String(b1);
                            System.out.println("msg:"+msg);
                            //第三次接收包
                            s = "ffffffff5500000000";
                            a = codeUtils.GetBytesFromHex(s);
                            packet = new DatagramPacket(a, a.length, address);
                            b = codeUtils.retData(socket,packet);
                            //第四次接收包
                            t_s = codeUtils.GetHexStringFromBytes(b,true);
                            s = "ffffffff55"+t_s.substring(10,t_s.length());
                            a = codeUtils.GetBytesFromHex(s);
                            packet = new DatagramPacket(a, a.length, address);
                            b2 = codeUtils.retData(socket,packet);
                            String msg1 = new String(b2);
                            System.out.println("msg:"+msg1);
                            t_s = "0"+codeUtils.GetHexStringFromBytes(b2,false);
                            System.out.println(t_s);
                            String a_= codeUtils.FormatPlayer(t_s.substring(12,t_s.length()));
                            System.out.println("a_:"+a_);
                            Pattern p = Pattern.compile("00(.*?)00");
                            Matcher m = p.matcher(a_);
                            List save_ = new ArrayList<String>();
                            while(m.find()){
                                String group = m.group();
                                save_.add(group.substring(2,group.length()-2));
                            }
                            for(Object nameHex : save_){
                                String s_ = (String)nameHex;
                                if(s_.equals("0111111111111111"))
                                    continue;
                                try{
                                    playerList_.add(new String(codeUtils.GetBytesFromHex(s_)));
                                }catch (Exception e){
                                    if(s_.length() % 2 != 0){
                                        playerList_.add(new String(codeUtils.GetBytesFromHex(s_+"0")));
                                    }
                                }
                            }
                            for(Object x : playerList_)
                                System.out.println("players:"+x);
                            //获取服务器名字
                            String serverName = new String(codeUtils.GetBytesFromHex(codeUtils.GetHexStringFromBytes(b1,false).substring(12,codeUtils.GetHexStringFromBytes(b1,false).length()).split("00")[0]));
                            String serverMap = new String(codeUtils.GetBytesFromHex(codeUtils.GetHexStringFromBytes(b1,false).split("006e6d72696800")[0].split("00")[1]));
                            System.out.println(serverName);
                            //将服务器的部分信息进行保存
                            PlayerListTemplete templete_ = new PlayerListTemplete(
                                    "\n服务器IP："+p__[0]+":"+p__[1],
                                    "服务器:   "+serverName,
                                    "人数：0/1人",
                                    "地图："+serverMap,
                                    playerList_);
                            templetesList.add(templete_);
                            PlayerDataAdapter adapter = new PlayerDataAdapter(getApplicationContext(), R.layout.a, templetesList);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ipListlength -= 1;
                                    textView.setText("剩余 "+ ipListlength +" 个IP未获取完毕...");
                                    listView.setAdapter(adapter);
                                    System.err.println("设置成功.");
                                    System.gc();
                                    try{
                                        Thread.sleep(250);}catch (Exception e){}
                                }
                            });
                            break;
                        }catch (Exception e){
                            e.printStackTrace();
                            count[0] = count[0] - 1;
                            if(count[0] < 0){
                                System.err.println("count为零,结束线程.");
                                count_[0] = count_[0] - 1;
                                ipListlength -= 1;
                                break;
                            }else{
                                System.err.println("count剩下"+count[0]+"次重新连接机会.");
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }

                        }
                    }
                }
            }
        }).start();
    }

    public List<String[]> return_iplist(String ipList_){
        String[] ipList = ipList_.split("\n");
        //每多少个分一组?
        int thread_num = 10;
        List<String[]> list = new ArrayList();
        for(int i = 0;i < ipList.length;i+=(thread_num)) {
            String[] t = new String[thread_num];
            for(int j = i,j1 = 0;j < i + (thread_num);j++) {
                try {
                    t[j1++] = ipList[j];
                }catch(Exception e) {
                    break;
                }
            }
            list.add(t);
        }
        for(int i = 0;i < list.size();i++) {
            for(String s : list.get(i))
                System.out.print(s+",");
            System.out.println();
        }
        return list;
    }

    public void NMRIHPing_Thread(String ipList){
        _SendPacket(ipList.split("\n"),new int[6]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        ipListlength = getIntent().getStringExtra("serverIPList").split("\n").length;
        List<String[]> retIpList = return_iplist(getIntent().getStringExtra("serverIPList"));
        for(String[] s : retIpList){
            String s_ = "";
            for(String t_ : s)
                s_ = s_ + t_ + "\n";
            NMRIHPing_Thread(s_);
        }
    }
}