package com.example.lasertag;

public class PlayerSettingsList {
    private String deviceState;
    private String irProtocol;
    private String team;
    private int idMiles;
    private int idArmax;
    private String lives;
    private String currentHealth;
    private String maxHealth;
    private String gameSession;
    private String volumeLevel;
    private String autoReload;
    private String backgroundLight;

    public String getIrProtocol() {
        return irProtocol;
    }

    public String getTeam() {
        return team;
    }

    public int getIdMiles() {
        return idMiles;
    }

    public int getIdArmax() {
        return idArmax;
    }

    public String getGameSession() {
        return gameSession;
    }

    public String getVolumeLevel() {
        return volumeLevel;
    }

    public String getAutoReload() {
        return autoReload;
    }

    public String getBackgroundLight() {
        return backgroundLight;
    }



    public PlayerSettingsList(int idMiles, int idArmax, String irProtocol, String team,
                              String gameSession, String backgroundLight, String autoReload, String volumeLevel) {
        this.irProtocol = irProtocol;
        this.team = team;
        this.idMiles = idMiles;
        this.idArmax = idArmax;
        this.gameSession = gameSession;
        this.volumeLevel = volumeLevel;
        this.autoReload = autoReload;
        this.backgroundLight = backgroundLight;
    }
}
