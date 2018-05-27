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

    @AndroidFindBy(id = "cpfMotorista")
    AndroidElement cpfMotorista;

    @AndroidFindBy(id = "salvarBtn")
    AndroidElement salvarBtn;

    @AndroidFindBy(id = "radioButton_alimenticia")
    AndroidElement radioButton_alimenticia;

    @AndroidFindBy(id = "volumeCarga")
    AndroidElement volumeCarga;

    @AndroidFindBy(id = "list")
    AndroidElement list;

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

        if (!elementHasFocus(searchEditTxt)) {
            searchEditTxt.click();
        }
        Assert.assertEquals(true, elementHasFocus(searchEditTxt));
        searchEditTxt.setValue("Laura");
        Assert.assertEquals("Laura", searchEditTxt.getText());


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
        nomeMotorista.setValue("Laura");
        Assert.assertEquals("Laura", nomeMotorista.getText());


        if (!elementHasFocus(cpfMotorista)) {
            cpfMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.setValue("56142564242");
        Assert.assertEquals("56142564242", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        Assert.assertEquals("561.425.642-42", cpfMotorista.getText());


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        Assert.assertEquals(false, isElementDisplayed(empty_text));


        searchEditTxt.clear();


        if (!elementHasFocus(searchEditTxt)) {
            searchEditTxt.click();
        }
        Assert.assertEquals(true, elementHasFocus(searchEditTxt));
        searchEditTxt.setValue("Pedro");
        Assert.assertEquals("Pedro", searchEditTxt.getText());


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
        nomeMotorista.setValue("Pedro");
        Assert.assertEquals("Pedro", nomeMotorista.getText());


        if (!elementHasFocus(cpfMotorista)) {
            cpfMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.setValue("2462372372");
        Assert.assertEquals("2462372372", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        cpfMotorista.clear();


        if (!elementHasFocus(cpfMotorista)) {
            cpfMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.setValue("24623723722");
        Assert.assertEquals("24623723722", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        Assert.assertEquals("246.237.237-22", cpfMotorista.getText());


        getElementByIdAndScrollTo("radioButton_alimenticia");
        radioButton_alimenticia.click();
        Assert.assertEquals(true, isOptionChecked(radioButton_alimenticia));


        progressTo(volumeCarga, 51);
        //TODO: Implementar método de verificação de progressbar


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        Assert.assertEquals(false, isElementDisplayed(empty_text));


        searchEditTxt.clear();


        list.click();
        getElementUsingTextAndScrollTo("list", "Laura").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("Laura", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        nomeMotorista.clear();


        progressTo(volumeCarga, 29);
        //TODO: Implementar método de verificação de progressbar


        getElementByIdAndScrollTo("radioButton_alimenticia");
        radioButton_alimenticia.click();
        Assert.assertEquals(true, isOptionChecked(radioButton_alimenticia));


        if (!elementHasFocus(nomeMotorista)) {
            nomeMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(nomeMotorista));
        nomeMotorista.setValue("Laura Cardoso");
        Assert.assertEquals("Laura Cardoso", nomeMotorista.getText());


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        list.click();
        getElementUsingTextAndScrollTo("list", "Pedro").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("Pedro", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        getElementByIdAndScrollTo("deleteBtn");
        deleteBtn.click();


        list.click();
        getElementUsingTextAndScrollTo("list", "Laura Cardoso").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("Laura Cardoso", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        getElementByIdAndScrollTo("deleteBtn");
        deleteBtn.click();


        Assert.assertEquals(true, elementHasFocus(searchEditTxt));
        Assert.assertEquals("", searchEditTxt.getText());


        Assert.assertEquals(true, isElementDisplayed(empty_text));


    }

    public AndroidElement getElementByIdAndScrollTo(String id) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + id + "\").instance(0))");
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

    private void checkOption(AndroidElement element, boolean check) {
        boolean isChecked = Boolean.valueOf(element.getAttribute("checked"));
        if (isChecked != check) {
            element.click();
        }
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

    public AndroidElement getElementUsingTextAndScrollTo(String id, String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + id + "\").childSelector(new UiSelector().textContains(\"" + texto + "\").instance(0)))");
    }

    private void pressKey(int key) {
        driver.pressKeyCode(key);
    }

    private boolean isOptionChecked(AndroidElement element) {
        boolean isChecked = Boolean.valueOf(element.getAttribute("checked"));
        return isChecked;
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