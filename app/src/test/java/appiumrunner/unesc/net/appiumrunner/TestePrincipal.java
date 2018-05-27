package appiumrunner.unesc.net.appiumrunner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriverException;
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

    @AndroidFindBy(id = "searchEditTxt")
    AndroidElement searchEditTxt;

    @AndroidFindBy(id = "empty_text")
    AndroidElement empty_text;

    @AndroidFindBy(id = "add_driver_btn")
    AndroidElement add_driver_btn;

    @AndroidFindBy(id = "nomeMotorista")
    AndroidElement nomeMotorista;

    @AndroidFindBy(id = "estadoMotorista")
    AndroidElement estadoMotorista;

    @AndroidFindBy(id = "volumeCarga")
    AndroidElement volumeCarga;

    @AndroidFindBy(id = "radioButton_alimenticia")
    AndroidElement radioButton_alimenticia;

    @AndroidFindBy(id = "bitrem")
    AndroidElement bitrem;

    @AndroidFindBy(id = "motoristaAtivo")
    AndroidElement motoristaAtivo;

    @AndroidFindBy(id = "cpfMotorista")
    AndroidElement cpfMotorista;

    @AndroidFindBy(id = "salvarBtn")
    AndroidElement salvarBtn;

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

        if (!elementHasFocus(searchEditTxt)) {
            searchEditTxt.click();
        }
        Assert.assertEquals(true, elementHasFocus(searchEditTxt));
        searchEditTxt.setValue("test");
        Assert.assertEquals("test", searchEditTxt.getText());


        Assert.assertEquals(true, isElementDisplayed(empty_text));


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


        estadoMotorista.click();
        getElementUsingTextAndScrollTo("Amapá - AP").click();
        Assert.assertEquals("Amapá - AP", getChildText(estadoMotorista, 0));


        progressTo(volumeCarga, 48);
        //TODO: Implementar método de verificação de progressbar


        getElementByIdAndScrollTo("radioButton_alimenticia");
        radioButton_alimenticia.click();
        Assert.assertEquals(true, isOptionChecked(radioButton_alimenticia));


        getElementByIdAndScrollTo("bitrem");
        checkOption(bitrem, true);
        Assert.assertEquals(true, isOptionChecked(bitrem));


        getElementByIdAndScrollTo("motoristaAtivo");
        checkOption(motoristaAtivo, false);
        Assert.assertEquals(false, isOptionChecked(motoristaAtivo));


        if (!elementHasFocus(cpfMotorista)) {
            cpfMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.setValue("2525252422");
        Assert.assertEquals("2525252422", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        cpfMotorista.clear();


        if (!elementHasFocus(cpfMotorista)) {
            cpfMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.setValue("252525242");
        Assert.assertEquals("252525242", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        cpfMotorista.clear();


        if (!elementHasFocus(cpfMotorista)) {
            cpfMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.setValue("25252524288");
        Assert.assertEquals("25252524288", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        Assert.assertEquals("252.525.242-88", cpfMotorista.getText());


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        Assert.assertEquals(false, isElementDisplayed(empty_text));


    }

    private void checkOption(AndroidElement element, boolean check) {
        boolean isChecked = Boolean.valueOf(element.getAttribute("checked"));
        if (isChecked != check) {
            element.click();
        }
    }

    private boolean elementHasFocus(AndroidElement element) {
        return element.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter());
    }

    private void pressKey(int key) {
        driver.pressKeyCode(key);
    }

    public AndroidElement getElementByIdAndScrollTo(String id) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + id + "\").instance(0))");
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

    private boolean isOptionChecked(AndroidElement element) {
        boolean isChecked = Boolean.valueOf(element.getAttribute("checked"));
        return isChecked;
    }

    private boolean isElementDisplayed(AndroidElement element) {
        boolean result = false;
        try {
            result = element.isDisplayed();
        } catch (WebDriverException ex) {

        }
        return result;
    }

    private String getChildText(AndroidElement element, int index) {
        return element.findElementByAndroidUIAutomator("new UiSelector().index(" + index + ")").getText();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}