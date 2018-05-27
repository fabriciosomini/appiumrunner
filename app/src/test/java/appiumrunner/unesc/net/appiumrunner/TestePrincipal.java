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

    @AndroidFindBy(id = "salvarBtn")
    AndroidElement salvarBtn;

    @AndroidFindBy(id = "radioButton_perigosa")
    AndroidElement radioButton_perigosa;

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
        searchEditTxt.setValue("Flavia");
        Assert.assertEquals("Flavia", searchEditTxt.getText());


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
        nomeMotorista.setValue("Flavia");
        Assert.assertEquals("Flavia", nomeMotorista.getText());


        if (!elementHasFocus(cpfMotorista)) {
            cpfMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.setValue("14513513511");
        Assert.assertEquals("14513513511", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        Assert.assertEquals("145.135.135-11", cpfMotorista.getText());


        estadoMotorista.click();
        getElementUsingTextAndScrollTo("Distrito Federal - DF").click();
        Assert.assertEquals("Distrito Federal - DF", getChildText(estadoMotorista, 0));


        progressTo(volumeCarga, 82);
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


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        Assert.assertEquals(false, isElementDisplayed(empty_text));


        searchEditTxt.clear();


        Assert.assertEquals(true, elementHasFocus(searchEditTxt));
        Assert.assertEquals("Pesquisar", searchEditTxt.getText());


        Assert.assertEquals(false, isElementDisplayed(empty_text));


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
        cpfMotorista.setValue("01234567890");
        Assert.assertEquals("01234567890", cpfMotorista.getText());
        pressKey(AndroidKeyCode.ENTER);
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));


        Assert.assertEquals("012.345.678-90", cpfMotorista.getText());


        estadoMotorista.click();
        getElementUsingTextAndScrollTo("Mato Grosso do Sul - MS").click();
        Assert.assertEquals("Mato Grosso do Sul - MS", getChildText(estadoMotorista, 0));


        progressTo(volumeCarga, 25);
        //TODO: Implementar método de verificação de progressbar


        getElementByIdAndScrollTo("radioButton_perigosa");
        radioButton_perigosa.click();
        Assert.assertEquals(true, isOptionChecked(radioButton_perigosa));


        getElementByIdAndScrollTo("bitrem");
        checkOption(bitrem, true);
        Assert.assertEquals(true, isOptionChecked(bitrem));


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        getElementUsingParentIdAndTextAndScrollTo("list", "Flavia").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("Flavia", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        nomeMotorista.clear();


        getElementByIdAndScrollTo("radioButton_perigosa");
        radioButton_perigosa.click();
        Assert.assertEquals(true, isOptionChecked(radioButton_perigosa));


        progressTo(volumeCarga, 53);
        //TODO: Implementar método de verificação de progressbar


        if (!elementHasFocus(nomeMotorista)) {
            nomeMotorista.click();
        }
        Assert.assertEquals(true, elementHasFocus(nomeMotorista));
        nomeMotorista.setValue("Flavia Tavares");
        Assert.assertEquals("Flavia Tavares", nomeMotorista.getText());


        getElementByIdAndScrollTo("salvarBtn");
        salvarBtn.click();


        getElementUsingParentIdAndTextAndScrollTo("list", "Pedro").click();


        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("Pedro", nomeMotorista.getText());


        getElementByIdAndScrollTo("nomeMotorista");


        getElementByIdAndScrollTo("deleteBtn");
        deleteBtn.click();

        //TESTE


    }

    private boolean elementHasFocus(AndroidElement element) {
        return element.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter());
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

    private String getChildText(AndroidElement element, int index) {
        return element.findElementByAndroidUIAutomator("new UiSelector().index(" + index + ")").getText();
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

    public AndroidElement getElementUsingTextAndScrollTo(String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + texto + "\").instance(0))");
    }

    private void pressKey(int key) {
        driver.pressKeyCode(key);
    }

    private boolean isElementDisplayed(AndroidElement element) {
        boolean result = false;
        try {
            result = element.isDisplayed();
        } catch (WebDriverException ex) {

        }
        return result;
    }

    public AndroidElement getElementUsingParentIdAndTextAndScrollTo(String id, String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + id + "\").childSelector(new UiSelector().textContains(\"" + texto + "\").instance(0)))");
    }

    private boolean isOptionChecked(AndroidElement element) {
        boolean isChecked = Boolean.valueOf(element.getAttribute("checked"));
        return isChecked;
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}