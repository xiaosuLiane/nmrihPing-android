package com.example.nmrihping.utils;

import java.util.List;

public class PlayerListTemplete {
    private String ServerHost;
    private String ServerName;
    private String Online;
    private String MapName;
    private List<String> PlayerList;

    public PlayerListTemplete(String serverHost, String serverName, String online, String mapName, List<String> playerList) {
        ServerHost = serverHost;
        ServerName = serverName;
        Online = online;
        MapName = mapName;
        PlayerList = playerList;
    }
    public String getServerHost() { return ServerHost; }

    public String getServerName() {
        return ServerName;
    }

    public String getOnline() {
        return Online;
    }

    public String getMapName() {
        return MapName;
    }

    public List<String> getPlayerList() {
        return PlayerList;
    }

}