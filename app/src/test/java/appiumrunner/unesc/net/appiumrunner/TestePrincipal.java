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
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;

public class TestePrincipal {
    private AndroidDriver<AndroidElement> driver = null;

    @Before
    public void setup() {
        File app = new File(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0.0");
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

        AndroidElement searchEditTxt = driver.findElement(By.id("searchEditTxt"));
        Assert.assertEquals("Pesquisar", searchEditTxt.getText());
        Assert.assertEquals(false, elementHasFocus(searchEditTxt));


        getElementByIdAndScrollTo("add_driver_btn");
        AndroidElement add_driver_btn = driver.findElement(By.id("add_driver_btn"));
        add_driver_btn.click();


        AndroidElement nomeMotorista = driver.findElement(By.id("nomeMotorista"));
        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        nomeMotorista.click();
        Assert.assertEquals(true, elementHasFocus(nomeMotorista));
        nomeMotorista.sendKeys("Fabricio");
        Assert.assertEquals("Fabricio", nomeMotorista.getText());


        AndroidElement cpfMotorista = driver.findElement(By.id("cpfMotorista"));
        cpfMotorista.click();
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.sendKeys("012345678900");
        Assert.assertEquals("012345678900", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        cpfMotorista.clear();


        cpfMotorista.click();
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.sendKeys("01234567890");
        Assert.assertEquals("01234567890", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        Assert.assertEquals("012.345.678-90", cpfMotorista.getText());


        AndroidElement estadoMotorista = driver.findElement(By.id("estadoMotorista"));
        estadoMotorista.click();
        getElementUsingTextAndScroll("Pará - PA").click();
        Assert.assertEquals("Pará - PA", getChildText(estadoMotorista, 0));


        getElementByIdAndScrollTo("salvarBtn");
        AndroidElement salvarBtn = driver.findElement(By.id("salvarBtn"));
        salvarBtn.click();


        getElementByIdAndScrollTo("add_driver_btn");
        add_driver_btn.click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        nomeMotorista.click();
        Assert.assertEquals(true, elementHasFocus(nomeMotorista));
        nomeMotorista.sendKeys("test");
        Assert.assertEquals("test", nomeMotorista.getText());


        cpfMotorista.click();
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.sendKeys("135135133515");
        Assert.assertEquals("135135133515", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        cpfMotorista.clear();


        cpfMotorista.click();
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.sendKeys("13513513351");
        Assert.assertEquals("13513513351", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        Assert.assertEquals("135.135.133-51", cpfMotorista.getText());


        estadoMotorista.click();
        getElementUsingTextAndScroll("Santa Catarina - SC").click();
        Assert.assertEquals("Santa Catarina - SC", getChildText(estadoMotorista, 0));


        getElementByIdAndScrollTo("radioButton_perigosa");
        AndroidElement radioButton_perigosa = driver.findElement(By.id("radioButton_perigosa"));
        radioButton_perigosa.click();
        Assert.assertEquals(true, isOptionChecked(radioButton_perigosa));


        getElementByIdAndScrollTo("bitrem");
        AndroidElement bitrem = driver.findElement(By.id("bitrem"));
        checkOption(bitrem, true);
        Assert.assertEquals(true, isOptionChecked(bitrem));


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        AndroidElement list = driver.findElement(By.id("list"));
        list.click();
        getElementUsingTextAndScroll("Fabricio").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("Fabricio", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        AndroidElement volumeCarga = driver.findElement(By.id("volumeCarga"));
        progressTo(volumeCarga, 51);
        //TODO: Implementar método de verificação de progressbar


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        list.click();
        getElementUsingTextAndScroll("test").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("test", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        getElementByIdAndScrollTo("deleteBtn");
        AndroidElement deleteBtn = driver.findElement(By.id("deleteBtn"));
        deleteBtn.click();


        list.click();
        getElementUsingTextAndScroll("Fabricio").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("Fabricio", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        getElementByIdAndScrollTo("motoristaAtivo");
        AndroidElement motoristaAtivo = driver.findElement(By.id("motoristaAtivo"));
        checkOption(motoristaAtivo, false);
        Assert.assertEquals(false, isOptionChecked(motoristaAtivo));


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


    }

    private void pressKey(int key) {
        driver.pressKeyCode(key);
    }

    public AndroidElement getElementUsingTextAndScroll(String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + texto + "\").instance(0))");
    }

    private boolean elementHasFocus(AndroidElement element) {
        return element.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter());
    }

    public AndroidElement getElementByIdAndScrollTo(String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + texto + "\").instance(0))");
    }

    private void checkOption(AndroidElement element, boolean check) {
        boolean isChecked = Boolean.valueOf(element.getAttribute("checked"));
        if (isChecked != check) {
            element.click();
        }
    }

    private boolean isOptionChecked(AndroidElement element) {
        boolean isChecked = Boolean.valueOf(element.getAttribute("checked"));
        return isChecked;
    }

    private String getChildText(AndroidElement element, int index) {
        return element.findElementByAndroidUIAutomator("new UiSelector().index(" + index + ")").getText();
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

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}