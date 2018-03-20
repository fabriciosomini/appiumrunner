package appiumrunner.unesc.net.appiumrunner.engine;

/**
 * Created by fabri on 18/03/2018.
 */

public class Setup {

    private String platformVersion;
    private String deviceName;
    private String platformName;
    private String appActivity;
    private boolean useDefaultTearDown;

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

    public String getAppActivity() {
        return appActivity;
    }

    public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }

}
