package com.example.nmrihping.utils;

public class _Timer {
    private long ms;

    public _Timer(){this.ms = 0;}

    public boolean isDelay(long delay){return System.currentTimeMillis() - ms >= delay;}

    public void reset(){this.ms = System.currentTimeMillis();}


}