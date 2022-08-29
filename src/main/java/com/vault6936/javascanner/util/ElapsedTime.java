package com.vault6936.javascanner.util;

public class ElapsedTime {
    private long startTime;
    public ElapsedTime() {};
    public void reset() {
        startTime = System.currentTimeMillis();
    }
    public long getElapsedTime() {
        return System.currentTimeMillis()-startTime;
    }
}
