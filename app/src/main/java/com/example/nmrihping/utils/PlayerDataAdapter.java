package com.example.nmrihping.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nmrihping.R;

import java.util.List;


public class PlayerDataAdapter extends ArrayAdapter<PlayerListTemplete> {
    private int resourceID;

    public PlayerDataAdapter(Context context, int resourceID, List<PlayerListTemplete> resource) {
        super(context, resourceID,resource);
        this.resourceID = resourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlayerListTemplete browser = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,null);
        TextView ServerName = (TextView)view.findViewById(R.id.ServerName);
        TextView ServerMap = (TextView)view.findViewById(R.id.ServerMap);
        TextView ServerPlayers = (TextView)view.findViewById(R.id.ServerPlayer);
        ServerName.setText(browser.getServerName());
        ServerMap.setText(browser.getMapName());
        String t = "玩家列表：\n";
        for(String s : browser.getPlayerList())
            t = t + s + "\n";
        ServerPlayers.setText(t);
        return view;
    }
}