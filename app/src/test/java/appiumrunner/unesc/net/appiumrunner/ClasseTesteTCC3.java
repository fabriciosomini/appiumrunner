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

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class ClasseTesteTCC3 {
    private AppiumDriver<MobileElement> driver = null;
    @Before
    public void setup() {
        File app = new File(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4.4");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "adroid");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        try {
            driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
    }

    @Test
    public void teste() {

        MobileElement nomeMotorista = driver.findElement(By.id("nomeMotorista"));
        Assert.assertEquals(nomeMotorista.equals(driver.switchTo().activeElement()), true);
        Assert.assertEquals(nomeMotorista.getText(), "");


        MobileElement cpfMotorista = driver.findElement(By.id("cpfMotorista"));
        Assert.assertEquals(cpfMotorista.equals(driver.switchTo().activeElement()), false);
        Assert.assertEquals(cpfMotorista.getText(), "");


        MobileElement estadoMotorista = driver.findElement(By.id("estadoMotorista"));
        Assert.assertEquals(estadoMotorista.getText(), "Acre - AC");


        MobileElement abrirListaMercadorias = driver.findElement(By.id("abrirListaMercadorias"));
        abrirListaMercadorias.click();


    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}