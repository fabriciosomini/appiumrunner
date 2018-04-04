package appiumrunner.unesc.net.appiumrunner.engine;

import java.util.ArrayList;
import java.util.List;

import appiumrunner.unesc.net.appiumrunner.states.Estado;

/**
 * Created by fabri on 18/03/2018.
 */


//TODO: Verificar se todos os metodos do utilitario de estados estão aqui (ex: Verificar Barra Progresso)
//TODO: toggle - encontrar e verificar
//TODO: seekbar - encontrar e verificar
//TODO: radiogroup - encontrar e verificar
public class Registrador {


    private final Setup setup;
    private final String nomeTeste;
    private Criacao criacao;

    private String fullScript = "";
    private boolean autoSave;
    private String teardownScript;
    private ArrayList<Estado> estados;

    public Registrador(Setup setup) {
        this.setup = setup;
        criacao = new Criacao();
        nomeTeste = gerarNomeTeste();
        estados = new ArrayList<>();

    }

    //TODO: Adicionar suporte ao método findElement
    public void registrar(Estado estado) {

        estados.add(estado);

    }

    private String gerarNomeTeste() {
        return "ClasseTesteTCC3";
    }

    private String construirScriptAcoesVerificacoes() {

        String scriptCompleto = "";

        for (Estado estado :
                estados) {


            String elementName = estado.getIdentificadorElemento();
            String elementId = elementName;


            StringBuilder estadoTexto = estado.getEstadoTexto();
            StringBuilder estadoSelecao = estado.getEstadoSelecao();
            Estado.Foco estadoFoco = estado.getEstadoFoco();

            String findElementById = getFindElementByIdMethod(elementId, elementName);
            String click = getClickMethod(elementName);
            String clear = getClearMethod(elementName);
            String sendKeys = getSendKeysMethod(elementName, getSafeString(estadoTexto));
            String selecItem = getSelectItemMethod(elementName, getSafeString(estadoSelecao));
            String scrollTo = getScrollToMethodById(elementId);

            String verificaoTexto = getTextAssertionMethod(elementName, getSafeString(estadoTexto));
            String verificaoFoco = "";
            String verificaoSelecao = getSpinnerAssertionMethod(elementName, getSafeString(estadoSelecao));

            if (estadoFoco != null && estadoFoco != Estado.Foco.IGNORAR) {
                verificaoFoco = getFocusAssertionMethod(elementName, estadoFoco);
            }

            List<Estado.TipoAcao> passos = estado.getAcoes();

            if (passos.contains(Estado.TipoAcao.SCROLL_TO)) {
                scriptCompleto += scrollTo + ";";
            }

            if (!scriptCompleto.contains(findElementById)) {
                scriptCompleto += findElementById;
            }

            Ordem ordem = getOrdem(passos);

            for (Estado.TipoAcao acao :
                    passos) {

                if (acao != null) {
                    String scriptAcoes = "";


                    switch (acao) {

                        case FOCUS:
                            if (estadoFoco == Estado.Foco.FOCADO) {
                                scriptAcoes = click;
                            } else if (estadoFoco == Estado.Foco.SEM_FOCO) {
                                scriptAcoes = getLongPressMethod(elementName, 66);
                            }

                            scriptCompleto += construirComandoOrdem(ordem, scriptAcoes, verificaoFoco);
                            break;

                        case SEND_KEYS:
                            if (estadoTexto != null) {

                                if (estadoTexto.toString().isEmpty()) {
                                    scriptAcoes = clear;

                                } else {
                                    scriptAcoes = sendKeys;
                                }
                            }
                            scriptCompleto += construirComandoOrdem(ordem, scriptAcoes, verificaoTexto);
                            break;

                        case SELECT_SPINNER_ITEM:
                            if (estadoSelecao != null) {
                                scriptAcoes = selecItem;
                            }
                            scriptCompleto += construirComandoOrdem(ordem, scriptAcoes, verificaoSelecao);
                            break;
                        case CLICK:
                            scriptCompleto += click;
                            break;
                    }
                }
            }

            scriptCompleto += "\n\n";
        }


        return scriptCompleto;


    }

    private String getLongPressMethod(String elementName, int keyCode) {
        String method = "\n" + "driver.longPressKeyCode(" + keyCode + ");";
        return method;
    }

    private String construirComandoOrdem(Ordem ordem, String reproduzir, String verificar) {

        switch (ordem) {
            case REPRODUZIR:
                return reproduzir;

            case VERIFICAR:
                return verificar;

            case NONE:
                return "";

            case REPRODUZIR_DEPOIS_VERIFICAR:
                return reproduzir + verificar;

            case VERIFICAR_DEPOIS_REPRODUZIR:
                return verificar + reproduzir;
        }

        return null;

    }

    private Ordem getOrdem(List<Estado.TipoAcao> passos) {

        Ordem ordem = Ordem.NONE;

        for (Estado.TipoAcao acao :
                passos) {
            if (acao == Estado.TipoAcao.REPRODUZIR) {
                if (ordem == Ordem.NONE) {
                    ordem = Ordem.REPRODUZIR;
                }
                if (ordem == Ordem.VERIFICAR) {
                    ordem = Ordem.VERIFICAR_DEPOIS_REPRODUZIR;
                }
            }

            if (acao == Estado.TipoAcao.VERIFICAR) {
                if (ordem == Ordem.NONE) {
                    ordem = Ordem.VERIFICAR;
                }
                if (ordem == Ordem.REPRODUZIR) {
                    ordem = Ordem.REPRODUZIR_DEPOIS_VERIFICAR;
                }
            }

        }

        return ordem;
    }

    public void parar() {

        fullScript = criarSetup();
        fullScript += criarScript();


        if (setup.isUseDefaultTearDown()) {
            teardownScript = createTeardown();
        }

        fullScript += teardownScript;

        fullScript = criarClasseTeste();

        fullScript = fullScript.replace("\n\n\n\n", "\n\n");
        criacao.criar(fullScript);

        criacao = new Criacao();
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
        String method = "\n" + "Assert.assertEquals(\"" + estadoSelecao + "\", " + elementName + ".findElementByAndroidUIAutomator(\"new UiSelector().index(0)\").getText()" + ");";
        return method;
    }

    private String getTextAssertionMethod(String elementName, String estadoTexto) {
        String method = "\n" + "Assert.assertEquals(\"" + estadoTexto + "\", " + elementName + ".getText()" + ");";
        return method;
    }

    private String getFocusAssertionMethod(String elementName, Estado.Foco foco) {
        boolean focar = false;
        if (foco == Estado.Foco.FOCADO) {
            focar = true;
        }
        String method = "\n" + "Assert.assertEquals(" + focar + ", " + elementName + ".getCenter().equals(" + getFocusElementMethod() + ".getCenter()));";
        return method;
    }

    private String getSelectItemMethod(String elementName, String estadoTexto) {

        String method = getClickMethod(elementName)
                + getScrollToMethodByText(estadoTexto)
                + ".click();";

        return method;
    }

    private String getSendKeysMethod(String elementName, String estadoTexto) {
        String method = "\n" + elementName + ".sendKeys(\"" + estadoTexto + "\");";
        return method;
    }

    private String getSendInputMethod(String elementName, String input) {
        String method = "\n" + elementName + ".sendKeys(" + input + ");";
        return method;
    }

    private String getClearMethod(String elementName) {
        String method = "\n" + elementName + ".clear();";
        return method;
    }

    private String getScrollToMethodByText(String estadoSelecao) {
        String method = "\n" + "driver.findElementByAndroidUIAutomator(\"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(" + "\\" + "\"" + estadoSelecao + "\\" + "\"" + ").instance(0))\")";
        return method;
    }

    private String getScrollToMethodById(String elementName) {
        String method = "\n" + "driver.findElementByAndroidUIAutomator(\"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(" + "\\" + "\".*" + elementName + "\\" + "\"" + ").instance(0))\")";
        return method;
    }

    private String getClickMethod(String elementName) {
        String method = "\n" + elementName + ".click();";
        return method;
    }

    private String getFindElementByIdMethod(String elementId, String variableName) {
        String method = "\n" + "AndroidElement " + variableName + " = driver.findElement(By.id(\"" + elementId + "\"));";

        return method;
    }

    private String getFindElementByNameMethod(String elementName, String variableName) {
        String method = "\n" + "AndroidElement " + variableName + " = driver.findElement(By.name(\"" + elementName + "\"));";

        return method;
    }

    public String getFocusElementMethod() {
        String method = "driver.findElementByAndroidUIAutomator(\"new UiSelector().focused(true)\")";
        return method;
    }

    private String criarScript() {

        String scriptAcoes = construirScriptAcoesVerificacoes();
        scriptAcoes = scriptAcoes.replace("\n", "\n\t");
        scriptAcoes = "\n\n" + "@Test"
                + "\n" + "public void teste() throws Exception {"
                + "\n" + scriptAcoes
                + "\n" + "}";
        return scriptAcoes;
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
                + "\n" + "import org.openqa.selenium.Keys;"
                + "\n" + "import org.openqa.selenium.remote.DesiredCapabilities;"
                + "\n" + "import java.io.File;"
                + "\n" + "import java.net.MalformedURLException;"
                + "\n" + "import java.net.URL;"
                + "\n" + "import java.util.concurrent.TimeUnit;"
                + "\n" + "import io.appium.java_client.android.AndroidDriver;"
                + "\n" + "import io.appium.java_client.android.AndroidElement;"
                + "\n" + "import io.appium.java_client.remote.MobileCapabilityType;"
                + "\n\n" + "public class " + nomeTeste + " {"
                + "\n\t" + "private AndroidDriver<AndroidElement> driver = null;"
                + fullScript
                + "\n" + "}";

        return classe;
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
                        + "\n\t\t" + "driver = new AndroidDriver(new URL(\"" + appiumServerAddress + "\"), capabilities);"
                        + "\n\t" + "} catch (MalformedURLException e) {"
                        + "\n\t\t" + "e.printStackTrace();"
                        + "\n\t" + "}"
                        + "\n\t" + "driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);"
                        + "\n" + "}";

        return fullScript;
    }

    private enum Ordem {
        VERIFICAR_DEPOIS_REPRODUZIR,
        REPRODUZIR_DEPOIS_VERIFICAR,
        VERIFICAR,
        NONE, REPRODUZIR
    }


}
