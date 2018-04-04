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


        nomeMotorista.click();
        Assert.assertEquals(true, nomeMotorista.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter()));
        nomeMotorista.sendKeys("test");
        Assert.assertEquals("test", nomeMotorista.getText());


        cpfMotorista.click();
        Assert.assertEquals(true, cpfMotorista.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter()));
        cpfMotorista.sendKeys("123");
        Assert.assertEquals("123", cpfMotorista.getText());


        estadoMotorista.click();
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Alagoas - AL\").instance(0))").click();
        Assert.assertEquals("Alagoas - AL", estadoMotorista.findElementByAndroidUIAutomator("new UiSelector().index(0)").getText());


        nomeMotorista.clear();


        nomeMotorista.click();
        Assert.assertEquals(true, nomeMotorista.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter()));
        nomeMotorista.sendKeys("aabb");
        Assert.assertEquals("aabb", nomeMotorista.getText());


        cpfMotorista.clear();


        cpfMotorista.click();
        Assert.assertEquals(true, cpfMotorista.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter()));
        cpfMotorista.sendKeys("579");
        Assert.assertEquals("579", cpfMotorista.getText());


        estadoMotorista.click();
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Goiás - GO\").instance(0))").click();
        Assert.assertEquals("Goiás - GO", estadoMotorista.findElementByAndroidUIAutomator("new UiSelector().index(0)").getText());


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