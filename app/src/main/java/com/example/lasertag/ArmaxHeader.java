package com.example.lasertag;

public class ArmaxHeader {
    private int addressId;
    private int deviceId;
    private boolean retranslate;
    private byte packageNumber;
    private byte deviceGroup;
    private byte packageType;


    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isRetranslate() {
        return retranslate;
    }

    public void setRetranslate(boolean retranslate) {
        this.retranslate = retranslate;
    }

    public byte getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(byte packageNumber) {
        this.packageNumber = packageNumber;
    }

    public byte getDeviceGroup() {
        return deviceGroup;
    }

    public void setDeviceGroup(byte deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    public byte getPackageType() {
        return packageType;
    }

    public void setPackageType(byte packageType) {
        this.packageType = packageType;
    }
}


