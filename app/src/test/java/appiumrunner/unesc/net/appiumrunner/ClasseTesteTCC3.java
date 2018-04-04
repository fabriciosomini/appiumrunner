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
import io.appium.java_client.remote.MobileCapabilityType;

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
        Assert.assertEquals(false, nomeMotorista.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter()));
        Assert.assertEquals("", nomeMotorista.getText());


        AndroidElement cpfMotorista = driver.findElement(By.id("cpfMotorista"));
        Assert.assertEquals(false, cpfMotorista.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter()));
        Assert.assertEquals("", cpfMotorista.getText());


        AndroidElement estadoMotorista = driver.findElement(By.id("estadoMotorista"));
        Assert.assertEquals(false, estadoMotorista.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter()));
        Assert.assertEquals("", estadoMotorista.getText());


        nomeMotorista.sendKeys("test");
        Assert.assertEquals("test", nomeMotorista.getText());
        driver.longPressKeyCode(66);
        Assert.assertEquals(false, nomeMotorista.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter()));


        cpfMotorista.sendKeys("14");
        Assert.assertEquals("14", cpfMotorista.getText());
        driver.longPressKeyCode(66);
        Assert.assertEquals(false, cpfMotorista.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter()));


        estadoMotorista.click();
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Maranhão - MA\").instance(0))").click();
        Assert.assertEquals("Maranhão - MA", estadoMotorista.findElementByAndroidUIAutomator("new UiSelector().index(0)").getText());


        nomeMotorista.clear();


        nomeMotorista.sendKeys("abc");
        Assert.assertEquals("abc", nomeMotorista.getText());
        driver.longPressKeyCode(66);
        Assert.assertEquals(false, nomeMotorista.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter()));


        cpfMotorista.clear();


        cpfMotorista.sendKeys("888");
        Assert.assertEquals("888", cpfMotorista.getText());
        driver.longPressKeyCode(66);
        Assert.assertEquals(false, cpfMotorista.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter()));


        estadoMotorista.click();
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Amazonas - AM\").instance(0))").click();
        Assert.assertEquals("Amazonas - AM", estadoMotorista.findElementByAndroidUIAutomator("new UiSelector().index(0)").getText());


        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*abrirListaMercadorias\").instance(0))");
        AndroidElement abrirListaMercadorias = driver.findElement(By.id("abrirListaMercadorias"));
        abrirListaMercadorias.click();


    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}