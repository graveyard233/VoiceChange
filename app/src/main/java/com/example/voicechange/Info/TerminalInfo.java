package com.example.voicechange.Info;

/**
 * Created by cxg on 2020/2/20.
 * Describe:终端上线时socketIO发送上线的拓展信息（本机信息）
 */
public class TerminalInfo {
    /**
     * type : test
     * model : 9201
     * ip : 172.16.100.111
     * mac : 00:11:22:33:44:55
     * version : 1.23.0.1
     * system_type : windows
     * expand : {}
     */

    private String type;
    private String model;
    private String ip;
    private String mac;
    private String version;
    private String system_type;
    private int is_login;
    private String screen_key;
    private String expand;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSystem_type() {
        return system_type;
    }

    public void setSystem_type(String system_type) {
        this.system_type = system_type;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public int getIs_login() {
        return is_login;
    }

    public void setIs_login(int is_login) {
        this.is_login = is_login;
    }

    public String getScreen_key() {
        return screen_key;
    }

    public void setScreen_key(String screen_key) {
        this.screen_key = screen_key;
    }
}

