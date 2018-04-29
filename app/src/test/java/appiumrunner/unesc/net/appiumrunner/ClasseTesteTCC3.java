package appiumrunner.unesc.net.appiumrunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;

public class ClasseTesteTCC3 {
    private AndroidDriver<AndroidElement> driver = null;
    @Before
    public void setup() {
        File app = new File(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "adroid");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void teste() {
        getElementByIdAndScrollTo("nomeMotorista");
        AndroidElement nomeMotorista = driver.findElement(By.id("nomeMotorista"));
        AndroidElement cpfMotorista = driver.findElement(By.id("cpfMotorista"));
        cpfMotorista.click();
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.sendKeys("08223597900");
        Assert.assertEquals("08223597900", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));
        Assert.assertEquals("082.235.979-00", cpfMotorista.getText());
        pressKey(AndroidKeyCode.BACK);


    }
    private void pressKey(int key) {
        driver.pressKeyCode(key);
    }
    public AndroidElement getElementByIdAndScrollTo(String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + texto + "\").instance(0))");
    }
    private boolean elementHasFocus(AndroidElement element) {
        return element.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}