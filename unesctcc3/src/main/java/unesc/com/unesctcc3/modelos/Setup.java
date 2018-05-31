package unesc.com.unesctcc3.modelos;

/**
 * Created by fabri on 18/03/2018.
 */
public class Setup {
    private String platformVersion;
    private String deviceName;
    private String apkName;
    private String appiumServerAddress;
    private String appDirectory;

    public void setAppPath(String appDirectory, String apkName) {
        this.appDirectory = appDirectory;
        this.apkName = apkName;
    }

    public String getAppDirectory() {
        return appDirectory;
    }

    public String getApkName() {
        return apkName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAppiumServerAddress() {
        return appiumServerAddress;
    }

    public void setAppiumServerAddress(String appiumServerAddress) {
        this.appiumServerAddress = appiumServerAddress;
    }
}
