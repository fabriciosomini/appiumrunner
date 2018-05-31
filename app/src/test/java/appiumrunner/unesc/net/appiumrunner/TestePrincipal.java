package appiumrunner.unesc.net.appiumrunner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;

public class TestePrincipal {
    private AndroidDriver<AndroidElement> driver = null;

    @AndroidFindBy(id = "add_driver_btn")
    AndroidElement add_driver_btn;

    @AndroidFindBy(id = "nomeMotorista")
    AndroidElement nomeMotorista;

    @AndroidFindBy(id = "salvarBtn")
    AndroidElement salvarBtn;

    @AndroidFindBy(id = "cpfMotorista")
    AndroidElement cpfMotorista;

    @AndroidFindBy(id = "list")
    AndroidElement list;

    @AndroidFindBy(id = "bitrem")
    AndroidElement bitrem;

    @AndroidFindBy(id = "radioButton_perigosa")
    AndroidElement radioButton_perigosa;

    @AndroidFindBy(id = "volumeCarga")
    AndroidElement volumeCarga;

    @AndroidFindBy(id = "estadoMotorista")
    AndroidElement estadoMotorista;

    @AndroidFindBy(id = "deleteBtn")
    AndroidElement deleteBtn;

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
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }

    @Test
    public void teste() {

        getElementByIdAndScrollTo("add_driver_btn");
        add_driver_btn.click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        if (!elementHasFocus(nomeMotorista)) {
            nomeMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(nomeMotorista));
        nomeMotorista.setValue("test");
        Assert.assertEquals("test", nomeMotorista.getText());


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        if (!elementHasFocus(cpfMotorista)) {
            cpfMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.setValue("08223597900");
        Assert.assertEquals("08223597900", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        Assert.assertEquals("082.235.979-00", cpfMotorista.getText());


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        getElementUsingParentIdAndTextAndScrollTo("list", "test").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("test", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        getElementByIdAndScrollTo("bitrem");
        checkOption(bitrem, true);
        Assert.assertEquals(true, isOptionChecked(bitrem));


        getElementByIdAndScrollTo("radioButton_perigosa");
        radioButton_perigosa.click();
        Assert.assertEquals(true, isOptionChecked(radioButton_perigosa));


        progressTo(volumeCarga, 51);
        //TODO: Implementar método de verificação de progressbar


        estadoMotorista.click();
        getElementUsingTextAndScrollTo("Piauí - PI").click();
        Assert.assertEquals("Piauí - PI", getChildText(estadoMotorista, 0));


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        getElementUsingParentIdAndTextAndScrollTo("list", "test").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("test", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        getElementByIdAndScrollTo("deleteBtn");
        deleteBtn.click();


    }

    private void pressKey(int key) {
        driver.pressKeyCode(key);
    }

    public AndroidElement getElementUsingParentIdAndTextAndScrollTo(String id, String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + id + "\").childSelector(new UiSelector().textContains(\"" + texto + "\").instance(0)))");
    }

    private boolean isOptionChecked(AndroidElement element) {
        boolean isChecked = Boolean.valueOf(element.getAttribute("checked"));
        return isChecked;
    }

    private void checkOption(AndroidElement element, boolean check) {
        boolean isChecked = Boolean.valueOf(element.getAttribute("checked"));
        if (isChecked != check) {
            element.click();
        }
    }

    public AndroidElement getElementByIdAndScrollTo(String id) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + id + "\").instance(0))");
    }

    private String getChildText(AndroidElement element, int index) {
        return element.findElementByAndroidUIAutomator("new UiSelector().index(" + index + ")").getText();
    }

    private boolean elementHasFocus(AndroidElement element) {
        return element.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter());
    }

    public AndroidElement getElementUsingTextAndScrollTo(String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + texto + "\").instance(0))");
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