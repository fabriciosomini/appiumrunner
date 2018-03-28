package appiumrunner.unesc.net.appiumrunner;

import org.junit.After;
import org.junit.Assert;
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
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, "appiumrunner.unesc.net.appiumrunner.activities.MainActivity");
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

        WebElement nome_motorista = driver.findElement(By.id("appiumrunner.unesc.net.appiumrunner:id/nome_motorista"));
        Assert.assertEquals(nome_motorista.equals(driver.switchTo().activeElement()), true);
        Assert.assertEquals(nome_motorista.getText(), "");


        WebElement driver_cpf = driver.findElement(By.id("appiumrunner.unesc.net.appiumrunner:id/driver_cpf"));
        Assert.assertEquals(driver_cpf.equals(driver.switchTo().activeElement()), false);
        Assert.assertEquals(driver_cpf.getText(), "");


        WebElement driver_state = driver.findElement(By.id("appiumrunner.unesc.net.appiumrunner:id/driver_state"));
        Assert.assertEquals(driver_state.getText(), "Acre - AC");


        nome_motorista.click();
        nome_motorista.clear();


        Assert.assertEquals(nome_motorista.equals(driver.switchTo().activeElement()), true);
        Assert.assertEquals(nome_motorista.getText(), "");


        nome_motorista.click();
        nome_motorista.clear();


        Assert.assertEquals(nome_motorista.equals(driver.switchTo().activeElement()), false);
        Assert.assertEquals(nome_motorista.getText(), "");


        WebElement abrir_lista_mercadorias = driver.findElement(By.id("appiumrunner.unesc.net.appiumrunner:id/abrir_lista_mercadorias"));
        abrir_lista_mercadorias.click();


    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}