package xyz.agala.czzuwlan;

/**
 * Created by Galaxy on 2016/7/5.
 */

class stuffMain {
    private String captcha;
    private String connStatement;
    private String wifiName;
    private String authUrl;

    stuffMain(){
        super();
    }
    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    void setCaptcha(String captcha){
        this.captcha=captcha;
    }
    String getCaptcha(){
        return captcha;
    }

    String getConnStatement() {
        return connStatement;
    }

    void setConnStatement(String connStatement) {
        this.connStatement = connStatement;
    }
}
