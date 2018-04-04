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

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;

public class ClasseTesteTCC3 {
    private AndroidDriver<AndroidElement> driver = null;
    @Before
    public void setup() {
        File app = new File(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4.4");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "adroid");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
    }

    @Test
    public void teste() {

        AndroidElement nomeMotorista = driver.findElement(By.id("nomeMotorista"));
        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("", nomeMotorista.getText());


        AndroidElement cpfMotorista = driver.findElement(By.id("cpfMotorista"));
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));
        Assert.assertEquals("", cpfMotorista.getText());


        AndroidElement estadoMotorista = driver.findElement(By.id("estadoMotorista"));
        Assert.assertEquals(false, elementHasFocus(estadoMotorista));
        Assert.assertEquals("", estadoMotorista.getText());


        nomeMotorista.click();
        Assert.assertEquals(true, elementHasFocus(nomeMotorista));
        nomeMotorista.sendKeys("test");
        Assert.assertEquals("test", nomeMotorista.getText());


        cpfMotorista.click();
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.sendKeys("124");
        Assert.assertEquals("124", cpfMotorista.getText());


        estadoMotorista.click();
        getElementUsingTextAndScroll("Bahia - BA").click();
        Assert.assertEquals("Bahia - BA", estadoMotorista.findElementByAndroidUIAutomator("new UiSelector().index(0)").getText());


        AndroidElement volumeCarga = driver.findElement(By.id("volumeCarga"));
        progressTo(volumeCarga, 41);


        getElementUsingIdAndScroll("abrirListaMercadorias");
        AndroidElement abrirListaMercadorias = driver.findElement(By.id("abrirListaMercadorias"));
        abrirListaMercadorias.click();


    }

    public void progressTo(AndroidElement seekBar, int progress) {
        int width = seekBar.getSize().getWidth();
        int progressToX = progress * width / 100;
        int startX = seekBar.getLocation().getX();
        int yAxis = seekBar.getLocation().getY();
        int moveToXDirectionAt = progressToX;

        PointOption from = new PointOption();
        from.withCoordinates(startX, yAxis);

        PointOption to = new PointOption();
        to.withCoordinates(moveToXDirectionAt, yAxis);

        TouchAction action = new TouchAction(driver);
        action.longPress(from).moveTo(to).release().perform();
    }

    public AndroidElement getElementUsingIdAndScroll(String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + texto + "\").instance(0))");
    }

    public AndroidElement getElementUsingTextAndScroll(String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + texto + "\").instance(0))");
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