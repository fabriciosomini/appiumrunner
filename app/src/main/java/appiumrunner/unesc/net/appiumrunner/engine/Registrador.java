package appiumrunner.unesc.net.appiumrunner.engine;

import java.util.Random;

import appiumrunner.unesc.net.appiumrunner.states.Estado;

/**
 * Created by fabri on 18/03/2018.
 */


//TODO: Verificar se todos os metodos do utilitario de estados estão aqui (ex: Verificar Barra Progresso)
//TODO: toggle - reproduzir e verificar
//TODO: seekbar - reproduzir e verificar
//TODO: radiogroup - reproduzir e verificar
public class Registrador {

    private final Setup setup;
    private final String nomeTeste;
    private Criacao criacao;

    private String script = "";
    private String fullScript = "";

    private boolean autoSave;
    private String teardownScript;

    public Registrador(Setup setup) {
        this.setup = setup;
        criacao = new Criacao();
        nomeTeste = gerarNomeTeste();
    }

    private String gerarNomeTeste() {
        return "ClasseTesteTCC3";
    }

    public void enableAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    //TODO: Adicionar suporte ao método findElement
    public void registrar(Estado estado) {

        String elementName = estado.getIdentificadorElemento();
        String elementId = /*setup.getPackageName() + ":id/" +*/ elementName;

        Estado.Verificao verificao = estado.getVerificacao();
        StringBuilder estadoTexto = estado.getEstadoTexto();
        StringBuilder estadoSelecao = estado.getEstadoSelecao();
        boolean reproduzirPassos = estado.getReproduzirPassos();
        Estado.Foco estadoFoco = estado.getEstadoFoco();


        String findElementByid = getFindElementByIdMethod(elementId, elementName);
        String click = getClickMethod(elementName);
        String scrollToExact = getScrollToExactMethod(elementName, getSafeString(estadoSelecao));
        String clear = getClearMethod(elementName);
        String sendKeys = getSendKeysMethod(elementName, getSafeString(estadoTexto));
        String selecItem = getSelectItemMethod(elementName, getSafeString(estadoSelecao));


        String verificaoFoco = "";
        String verificaoTexto = "";
        String verificaoSelecao = "";

        if (!script.contains(findElementByid)) {
            script += findElementByid;
        }

        if (estadoSelecao != null) {

            verificaoSelecao = getSpinnerAssertionMethod(elementName, getSafeString(estadoSelecao));

            if (reproduzirPassos) {
                script += selecItem;
            }

            if (verificao == Estado.Verificao.POR_PROPRIEDADE) {
                script += verificaoSelecao;
            }

        }

        if (estadoFoco != null && estadoFoco != Estado.Foco.IGNORAR) {

            verificaoFoco = getFocusAssertionMethod(elementName, estadoFoco);

            if (reproduzirPassos) {
                script += click;
            }

            if (verificao == Estado.Verificao.POR_PROPRIEDADE) {
                script += verificaoFoco;
            }

        }

        if (estadoTexto != null) {

            verificaoTexto = getTextAssertionMethod(elementName, getSafeString(estadoTexto));


            if (estadoTexto.toString().isEmpty()) {
                if (reproduzirPassos) {
                    script += clear;
                }

                if (verificao == Estado.Verificao.POR_PROPRIEDADE) {
                    script += verificaoTexto;
                }

            } else {

                if (reproduzirPassos) {
                    script += sendKeys;
                }

                if (verificao == Estado.Verificao.POR_PROPRIEDADE) {
                    script += verificaoTexto;
                }
            }
        }

        if (verificao == Estado.Verificao.FINAL_ESTADO) {
            script += verificaoFoco;
            script += verificaoTexto;
            script += verificaoSelecao;

        }

        script += "\n\n";

        if (autoSave) {
            criacao.criar(script);
        }


    }

    private String getSafeString(StringBuilder stringBuilder) {
        if (stringBuilder != null) {
            return stringBuilder.toString();
        } else {
            return "";
        }
    }

    private String getSpinnerAssertionMethod(String elementName, String estadoSelecao) {
        //TODO: Estudar como se verifica a seleção de um spinner
        String method = getTextAssertionMethod(elementName, estadoSelecao);
        return method;
    }

    private String getTextAssertionMethod(String elementName, String estadoTexto) {
        String method = "\n" + "Assert.assertEquals(" + elementName + ".getText(), \"" + estadoTexto + "\");";
        return method;
    }

    private String getFocusAssertionMethod(String elementName, Estado.Foco foco) {
        boolean focar = false;
        if (foco == Estado.Foco.FOCADO) {
            focar = true;
        }
        String method = "\n" + "Assert.assertEquals(" + elementName + ".equals(driver.switchTo().activeElement()), " + focar + ");";
        return method;
    }

    private String getSelectItemMethod(String elementName, String estadoTexto) {
        Random rand = new Random();
        int randomNum = 10000000 + rand.nextInt((90000000 - 10000000) + 1);
        String optionName = elementName + "Option" + randomNum;

        String method = getClickMethod(elementName)
                + getScrollToExactMethod(elementName, estadoTexto)
                + getFindElementByNameMethod(elementName, optionName)
                + getClickMethod(optionName);

        return method;
    }

    private String getSendKeysMethod(String elementName, String estadoTexto) {
        String method = "\n" + elementName + ".sendKeys(\"" + estadoTexto + "\");";
        return method;
    }

    private String getClearMethod(String elementName) {
        String method = "\n" + elementName + ".clear();";
        return method;
    }

    private String getScrollToExactMethod(String elementName, String estadoSelecao) {
        String method = "\n" + elementName + ".scrollToExact(\"" + estadoSelecao + "\");";
        return method;
    }

    private String getClickMethod(String elementName) {
        String method = "\n" + elementName + ".click();";
        return method;
    }

    private String getFindElementByIdMethod(String elementId, String variableName) {
        String method = "\n" + "MobileElement " + variableName + " = driver.findElement(By.id(\"" + elementId + "\"));";

        return method;
    }

    private String getFindElementByNameMethod(String elementName, String variableName) {
        String method = "\n" + "MobileElement " + variableName + " = driver.findElement(By.name(\"" + elementName + "\"));";

        return method;
    }


    public void parar() {

        fullScript = criarSetup();
        fullScript += criarScript();


        if (setup.isUseDefaultTearDown()) {
            teardownScript = createTeardown();
        }

        fullScript += teardownScript;

        fullScript = criarClasseTeste();

        criacao.criar(fullScript);
        script = "";
        criacao = new Criacao();
    }

    private String criarClasseTeste() {

        String packageName = setup.getPackageName();
        fullScript = fullScript.replace("\n", "\n\t");
        String classe = "package " + packageName + ";"
                + "\n" + "import org.junit.After;"
                + "\n" + "import org.junit.Assert;"
                + "\n" + "import org.junit.Before;"
                + "\n" + "import org.junit.Test;"
                + "\n" + "import org.openqa.selenium.By;"
                + "\n" + "import org.openqa.selenium.WebDriver;"
                + "\n" + "import org.openqa.selenium.MobileElement;"
                + "\n" + "import org.openqa.selenium.remote.DesiredCapabilities;"
                + "\n" + "import org.openqa.selenium.remote.RemoteWebDriver;"
                + "\n" + "import java.io.File;"
                + "\n" + "import java.net.MalformedURLException;"
                + "\n" + "import java.net.URL;"
                + "\n" + "import java.util.concurrent.TimeUnit;"
                + "\n" + "import io.appium.java_client.remote.MobileCapabilityType;"
                + "\n\n" + "public class " + nomeTeste + " {"
                + "\n\t" + "private WebDriver driver = null;"
                + fullScript
                + "\n" + "}";

        return classe;
    }

    private String criarScript() {

        script = script.replace("\n", "\n\t");
        script = "\n\n" + "@Test"
                + "\n" + "public void teste() throws Exception {"
                + "\n" + script
                + "\n" + "}";
        return script;
    }

    private String createTeardown() {
        String teardown =
                "\n\n" + "@After"
                        + "\n" + "public void tearDown() {"
                        + "\n\t" + "if (driver != null) {"
                        + "\n\t" + "driver.quit();"
                        + "\n\t" + "}"
                        + "\n" + "}";
        return teardown;
    }

    private String criarSetup() {
        //String activity = setup.getAppActivity();
        String platformVersion = setup.getPlatformVersion();
        String deviceName = setup.getDeviceName();
        String appPath = setup.getAppPath();
        String apkName = setup.getApkName();
        String appiumServerAddress = setup.getAppiumServerAddress();

        appPath = appPath.replace("\\", "\\\\");
        fullScript =
                "\n" + "@Before"
                        + "\n" + "public void setup() {"
                        + "\n\t" + "File app = new File(\"" + appPath + "\", \"" + apkName + "\");"
                        + "\n\t" + "DesiredCapabilities capabilities = new DesiredCapabilities();"
                        + "\n\t" + "capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, \"" + platformVersion + "\");"
                        + "\n\t" + "capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, \"" + deviceName + "\");"
                        + "\n\t" + "capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, \"Android\");"
                        //+ "\n\t" + "capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY,\"" + activity + "\");"
                        + "\n\t" + "capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());"
                        + "\n"
                        + "\n\t" + "try {"
                        + "\n\t\t" + "driver = new RemoteWebDriver(new URL(\"" + appiumServerAddress + "\"), capabilities);"
                        + "\n\t" + "} catch (MalformedURLException e) {"
                        + "\n\t\t" + "e.printStackTrace();"
                        + "\n\t" + "}"
                        + "\n\t" + "driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);"
                        + "\n" + "}";

        return fullScript;
    }
}
