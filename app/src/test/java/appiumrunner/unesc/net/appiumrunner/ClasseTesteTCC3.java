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
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;

public class ClasseTesteTCC3 {
    private AndroidDriver<AndroidElement> driver = null;
    @Before
    public void setup() {
        File app = new File(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "adroid");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
    }

    @Test
    public void teste() {

        AndroidElement nomeMotorista = driver.findElement(By.id("nomeMotorista"));
        Assert.assertEquals(false, elementHasFocus(nomeMotorista));
        Assert.assertEquals("", nomeMotorista.getText());


        AndroidElement cpfMotorista = driver.findElement(By.id("cpfMotorista"));
        Assert.assertEquals(false, elementHasFocus(cpfMotorista));
        Assert.assertEquals("", cpfMotorista.getText());


        AndroidElement estadoMotorista = driver.findElement(By.id("estadoMotorista"));
        Assert.assertEquals(false, elementHasFocus(estadoMotorista));
        Assert.assertEquals("", estadoMotorista.getText());


        nomeMotorista.click();
        Assert.assertEquals(true, elementHasFocus(nomeMotorista));
        nomeMotorista.sendKeys("abelha");
        Assert.assertEquals("abelha", nomeMotorista.getText());


        cpfMotorista.click();
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.sendKeys("12314232");
        Assert.assertEquals("12314232", cpfMotorista.getText());


        estadoMotorista.click();
        getElementUsingTextAndScroll("Minas Gerais - MG").click();
        Assert.assertEquals("Minas Gerais - MG", getChildText(estadoMotorista, 0));


        AndroidElement volumeCarga = driver.findElement(By.id("volumeCarga"));
        progressTo(volumeCarga, 21);
        //TODO: Implementar método de verificação de progressbar


        progressTo(volumeCarga, 16);
        //TODO: Implementar método de verificação de progressbar


        AndroidElement radioButton1 = driver.findElement(By.id("radioButton1"));
        radioButton1.click();
        Assert.assertEquals(true, isOptionChecked(radioButton1));


        AndroidElement bitrem = driver.findElement(By.id("bitrem"));
        checkOption(bitrem, true);
        Assert.assertEquals(true, isOptionChecked(bitrem));


        AndroidElement motoristaAtivo = driver.findElement(By.id("motoristaAtivo"));
        checkOption(motoristaAtivo, false);
        Assert.assertEquals(false, isOptionChecked(motoristaAtivo));


        getElementUsingIdAndScroll("abrirListaMercadorias");
        AndroidElement abrirListaMercadorias = driver.findElement(By.id("abrirListaMercadorias"));
        abrirListaMercadorias.click();


        AndroidElement searchEditTxt = driver.findElement(By.id("searchEditTxt"));
        Assert.assertEquals("Pesquisar Carga", searchEditTxt.getText());
        Assert.assertEquals(false, elementHasFocus(searchEditTxt));


        Assert.assertEquals(true, elementHasFocus(searchEditTxt));
        Assert.assertEquals("let you go", searchEditTxt.getText());


        nomeMotorista.clear();


        nomeMotorista.click();
        Assert.assertEquals(true, elementHasFocus(nomeMotorista));
        nomeMotorista.sendKeys("abelha zumbindo");
        Assert.assertEquals("abelha zumbindo", nomeMotorista.getText());


        cpfMotorista.clear();


        cpfMotorista.click();
        Assert.assertEquals(true, elementHasFocus(cpfMotorista));
        cpfMotorista.sendKeys("68068068");
        Assert.assertEquals("68068068", cpfMotorista.getText());


        estadoMotorista.click();
        getElementUsingTextAndScroll("Roraima - RR").click();
        Assert.assertEquals("Roraima - RR", getChildText(estadoMotorista, 0));


        progressTo(volumeCarga, 83);
        //TODO: Implementar método de verificação de progressbar


        AndroidElement radioButton3 = driver.findElement(By.id("radioButton3"));
        radioButton3.click();
        Assert.assertEquals(true, isOptionChecked(radioButton3));

        checkOption(bitrem, false);
        Assert.assertEquals(false, isOptionChecked(bitrem));

        checkOption(motoristaAtivo, true);
        Assert.assertEquals(true, isOptionChecked(motoristaAtivo));


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

    private boolean elementHasFocus(AndroidElement element) {
        return element.getCenter().equals(driver.findElementByAndroidUIAutomator("new UiSelector().focused(true)").getCenter());
    }

    public AndroidElement getElementUsingIdAndScroll(String texto) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + texto + "\").instance(0))");
    }

    private boolean isOptionChecked(AndroidElement element) {
        boolean isChecked = Boolean.valueOf(element.getAttribute("checked"));
        return isChecked;
    }

    public AndroidElement getElementUsingTextAndScroll(String texto) {
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