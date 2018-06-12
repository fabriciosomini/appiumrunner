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

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

public class CasoTesteAutomatizado {
    @AndroidFindBy(id = "list")
    AndroidElement list;
    @AndroidFindBy(id = "nomeMotorista")
    AndroidElement nomeMotorista;
    @AndroidFindBy(id = "cpfMotorista")
    AndroidElement cpfMotorista;
    @AndroidFindBy(id = "estadoMotorista")
    AndroidElement estadoMotorista;
    @AndroidFindBy(id = "motoristaAtivo")
    AndroidElement motoristaAtivo;
    @AndroidFindBy(id = "salvarBtn")
    AndroidElement salvarBtn;
    @AndroidFindBy(id = "deleteBtn")
    AndroidElement deleteBtn;
    @AndroidFindBy(id = "empty_text")
    AndroidElement empty_text;
    @AndroidFindBy(id = "searchEditTxt")
    AndroidElement searchEditTxt;
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
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }

    @Test
    public void teste() throws Exception {

        getElementUsingParentIdAndTextAndScrollTo("list", "Franciso Todari").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("Franciso Todari", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        cpfMotorista.clear();


        if (!elementHasFocus(cpfMotorista)) {
            cpfMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.setValue("12345678900");
        Assert.assertEquals("12345678900", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        Assert.assertEquals("123.456.789-00", cpfMotorista.getText());


        estadoMotorista.click();
        getElementUsingTextAndScrollTo("São Paulo - SP").click();
        Assert.assertEquals("São Paulo - SP", getChildText(estadoMotorista, 0));


        getElementByIdAndScrollTo("motoristaAtivo");
        checkOption(motoristaAtivo, true);
        Assert.assertEquals(true, isOptionChecked(motoristaAtivo));


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        getElementUsingParentIdAndTextAndScrollTo("list", "Fabricio Somini").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("Fabricio Somini", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        getElementByIdAndScrollTo("deleteBtn");
        deleteBtn.click();


        getElementUsingParentIdAndTextAndScrollTo("list", "Jessica Gonçalves").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("Jessica Gonçalves", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        estadoMotorista.click();
        getElementUsingTextAndScrollTo("Piauí - PI").click();
        Assert.assertEquals("Piauí - PI", getChildText(estadoMotorista, 0));


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        Assert.assertEquals(false, isElementDisplayed(empty_text));


        if (!elementHasFocus(searchEditTxt)) {
            searchEditTxt.click();
        }
        Assert.assertEquals(true, elementHasFocus(searchEditTxt));
        searchEditTxt.setValue("Jessica");
        Assert.assertEquals("Jessica", searchEditTxt.getText());


        Assert.assertEquals(false, isElementDisplayed(empty_text));


    }

    private boolean isElementDisplayed(AndroidElement element) {
        boolean result = false;
        try {
            result = element.isDisplayed();
        } catch (WebDriverException ex) {

        }
        return result;
    }

    public AndroidElement getElementByIdAndScrollTo(String id) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + id + "\").instance(0))");
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

    private void pressKey(int key) {
        driver.pressKeyCode(key);
    }

    public AndroidElement getElementUsingParentIdAndTextAndScrollTo(String id, String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + id + "\").childSelector(new UiSelector().textContains(\"" + texto + "\").instance(0)))");
    }

    private String getChildText(AndroidElement element, int index) {
        return element.findElementByAndroidUIAutomator("new UiSelector().index(" + index + ")").getText();
    }

    public AndroidElement getElementUsingTextAndScrollTo(String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + texto + "\").instance(0))");
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