package com.example.lasertag.playerPackageTypes;

public class ArmaxPlayerImHere {
    private byte deviceState;
    private byte irProtocol;
    private byte team;
    private byte idMiles;
    private byte idArmax;
    private byte lives;
    private short currentHealth;
    private short maxHealth;
    private byte gameSession;
    private byte volumeLevel;
    private byte autoReload;
    private byte backgroundLight;
    private byte batteryLevel;

    public byte getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(byte deviceState) {
        this.deviceState = deviceState;
    }

    public byte getIrProtocol() {
        return irProtocol;
    }

    public void setIrProtocol(byte irProtocol) {
        this.irProtocol = irProtocol;
    }

    public byte getTeam() {
        return team;
    }

    public void setTeam(byte team) {
        this.team = team;
    }

    public byte getIdMiles() {
        return idMiles;
    }

    public void setIdMiles(byte idMiles) {
        this.idMiles = idMiles;
    }

    public byte getIdArmax() {
        return idArmax;
    }

    public void setIdArmax(byte idArmax) {
        this.idArmax = idArmax;
    }

    public byte getLives() {
        return lives;
    }

    public void setLives(byte lives) {
        this.lives = lives;
    }

    public short getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(short currentHealth) {
        this.currentHealth = currentHealth;
    }

    public short getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(short maxHealth) {
        this.maxHealth = maxHealth;
    }

    public byte getGameSession() {
        return gameSession;
    }

    public void setGameSession(byte gameSession) {
        this.gameSession = gameSession;
    }

    public byte getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(byte volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    public byte getAutoReload() {
        return autoReload;
    }

    public void setAutoReload(byte autoReload) {
        this.autoReload = autoReload;
    }

    public byte getBackgroundLight() {
        return backgroundLight;
    }

    public void setBackgroundLight(byte backgroundLight) {
        this.backgroundLight = backgroundLight;
    }

    public byte getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(byte batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
