package appiumrunner.unesc.net.appiumrunner.engine;

/**
 * Created by fabri on 18/03/2018.
 */

public class Setup {

    private String platformVersion;
    private String deviceName;
    private String platformName;
    //private String appActivity;
    private String appDirectory;
    private boolean useDefaultTearDown;
    private String apkName;
    private String packageName;
    private String appiumServerAddress;
    private String appPath;

    public void setAppPath(String appPath, String apkName) {
        this.appPath = appPath;
        this.apkName = apkName;
    }

    public String getAppPath() {
        return appPath;
    }

    public String getApkName() {
        return apkName;
    }

    public boolean isUseDefaultTearDown() {
        return useDefaultTearDown;
    }

    public void setUseDefaultTearDown(boolean useDefaultTearDown) {
        this.useDefaultTearDown = useDefaultTearDown;
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

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    /*/public String getAppActivity() {
        return appActivity;
    }*/

    /*public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }*/

    public void setAppDirectory(String appDirectory) {
        this.appDirectory = appDirectory;
    }

    public String getAppiumServerAddress() {
        return appiumServerAddress;
    }

    public void setAppiumServerAddress(String appiumServerAddress) {
        this.appiumServerAddress = appiumServerAddress;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
