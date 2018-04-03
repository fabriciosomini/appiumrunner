package appiumrunner.unesc.net.appiumrunner;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.remote.MobileCapabilityType;

public class ClasseTesteTCC3 {
    private WebDriver driver = null;
    @Before
    public void setup() {
        File app = new File(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1.2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "adroid");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        //capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, "appiumrunner.unesc.net.appiumrunner.activities.MainActivity");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        try {
            driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
    }

    @Test
    public void teste() {

        WebElement nomeMotorista = driver.findElement(By.id("nomeMotorista"));
        Assert.assertEquals(nomeMotorista.equals(driver.switchTo().activeElement()), true);
        Assert.assertEquals(nomeMotorista.getText(), "");


        WebElement cpfMotorista = driver.findElement(By.id("cpfMotorista"));
        Assert.assertEquals(cpfMotorista.equals(driver.switchTo().activeElement()), false);
        Assert.assertEquals(cpfMotorista.getText(), "");


        WebElement estadoMotorista = driver.findElement(By.id("estadoMotorista"));
        Assert.assertEquals(estadoMotorista.getText(), "Acre - AC");


        nomeMotorista.click();
        nomeMotorista.clear();


        Assert.assertEquals(nomeMotorista.equals(driver.switchTo().activeElement()), true);
        Assert.assertEquals(nomeMotorista.getText(), "");


        nomeMotorista.click();
        nomeMotorista.clear();


        Assert.assertEquals(nomeMotorista.equals(driver.switchTo().activeElement()), false);
        Assert.assertEquals(nomeMotorista.getText(), "");


        WebElement abrirListaMercadorias = driver.findElement(By.id("abrirListaMercadorias"));
        abrirListaMercadorias.click();


    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}