package appiumrunner.unesc.net.appiumrunner;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.remote.MobileCapabilityType;


public class PesquisarVerificarResultadoTeste {
    private WebDriver driver = null;

    @Before
    public void setup() {
        File appDir = new File(".\\app\\build\\outputs\\apk\\debug\\");
        File app = new File(appDir, "app-debug.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4.4");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "adroid");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, "appiumrunner.unesc.net.appiumrunner.MainActivity");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());


        try {
            driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);

    }

    @Test
    public void pesquisarVerificarResultado() throws Exception {

        String  expectedText = "Nenhum Resultado";

        WebElement searchBoxTxt = driver.findElement(By.id("searchBoxTxt"));
        WebElement searchButton = driver.findElement(By.id("searchButton"));

        searchBoxTxt.sendKeys("ESSE É UM TESTE SIMPLES");
        searchButton.click();

        WebElement resultsTxtView = driver.findElement(By.id("resultsTxtView"));
        String  currentText = resultsTxtView.getText();

        Assert.assertEquals(currentText, expectedText);


    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}