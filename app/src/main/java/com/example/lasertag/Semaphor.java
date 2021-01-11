package com.example.lasertag;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Semaphor {
    private boolean acquired = false;
    private Instant acquireTime;

    public synchronized boolean acquire(){

        if (acquired == true){
            if (Instant.now().minus(15, ChronoUnit.MILLIS).isAfter(acquireTime)){
                acquireTime = Instant.now();
                return true;
            }
            return false;
        } else {
            acquired = true;
            acquireTime = Instant.now();
            return true;
        }
    }
    public synchronized void release(){
        acquired = false;
    }
}
