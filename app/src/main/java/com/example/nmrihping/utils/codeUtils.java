package com.example.nmrihping.utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class codeUtils {
    public static byte[] GetBytesFromHex(String hex){
        int len = hex.length()/2;
        byte[] a = new byte[len];
        for(int i = 0,j = 0;i < hex.length();i+=2){
            String s = String.valueOf(hex.charAt(i)) + String.valueOf(hex.charAt(i+1));
            a[j++] = (byte) Integer.parseInt(s,16);
        }
        return a;
    }

    public static String GetHexStringFromBytes(byte[] b,boolean isCheck00){
        String s = "";
        for(int i = 0;i < b.length;i++){
            String a = Integer.toHexString(b[i]&0XFF);
            if(!isCheck00){
                if(a.equals("0"))
                    s += "00";
                else
                    s += a;
            }else {
                s += a;
            }
        }
        if(isCheck00){
            for(int i = 0;i < b.length-1;i+=2){
                String s_ = s.substring(i,i+2);
                if(s_.equals("00")){
                    s = s.substring(0,i);
                    System.out.println("Hex:"+s);
                    return s;
                }
            }
        }
        System.out.println("Hex:"+s);
        return s;
    }

    public static byte[] retData(DatagramSocket socket, DatagramPacket packet) throws Exception {
        socket.send(packet);
        byte[] buf = new byte[1024];
        DatagramPacket recive = new DatagramPacket(buf, 1024);
        // 接收消息
        System.out.println("尝试接收...");
        socket.receive(recive);
//        socket.close();
        return recive.getData();
    }

    public static String deleteStr(String s,int start,int end) {
        String s_ = "";
        for (int i = 0; i < s.length();i++){
            if (i >= start && i <= end){
                s_ += '1';
                continue;
            }
            s_ += s.charAt(i);
        }
        return s_;
    }

    public static String FormatPlayer(String a_) {
        int i_1 = -1;
        int i_2 = -1;
        for (int i = 0; i < a_.length()-1;i++){
            String s = a_.substring(i,i+2);
            if(s.equals("00")){
                if(i_1 == -1){
                    i_1 = i;
                }else if(i_1 != -1 && i_2 == -1){
                    i_2 = i;
                }
                if(i_1 != -1 && i_2 != -1){
                    a_ = deleteStr(a_, i_2 + 2, i_2 + 16);
                    i_1 = -1;
                    i_2 = -1;
                }
            }
        }
        return a_;
    }
}