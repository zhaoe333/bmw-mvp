package com.cm.datalog.entity;

public class ClientInfo {

    private String id;			// WeChat session id (before login)
    private String userId;      // WeChat unique id (after login)
    private String daimlerId; 	// Daimler id (after login daimer)
    private String deviceId;    // Unique device id (if possible)
    private String deviceType;  // Type of the device, such as mobile, headunit etc.
    private String deviceSystem;    // Device system info, for example Android, iOS etc.
    private Object extra;       // For reservation

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDaimlerId() {
        return daimlerId;
    }

    public void setDaimlerId(String daimlerId) {
        this.daimlerId = daimlerId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceSystem() {
        return deviceSystem;
    }

    public void setDeviceSystem(String deviceSystem) {
        this.deviceSystem = deviceSystem;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
