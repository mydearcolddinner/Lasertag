package com.example.lasertag;

public class PlayersList {
    private String deviceId;

    PlayersList(String deviceId){
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}