package appiumrunner.unesc.net.appiumrunner.engine;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import appiumrunner.unesc.net.appiumrunner.states.Estado;

/**
 * Created by fabri on 18/03/2018.
 */

public class Criacao {
    private final Setup setup;
    private final String nomeTeste;
    private final Utils utils;
    String fullScript = "";
    private Set<TipoExtraMethods> tipoExtraMethods;
    private String teardownScript;
    private ArrayList<Estado> estados;

    public Criacao(Setup setup) {
        this.setup = setup;
        utils = new Utils();
        nomeTeste = utils.gerarNomeTeste();
        tipoExtraMethods = new HashSet<>();
    }

    public void criar(ArrayList<Estado> estados) {

        ScriptBuilder scriptBuilder = new ScriptBuilder();
        this.estados = estados;
        fullScript = scriptBuilder.criarSetup();
        fullScript += scriptBuilder.criarScript();


        if (setup.isUseDefaultTearDown()) {
            teardownScript = scriptBuilder.createTeardown();
        }

        fullScript += teardownScript;

        fullScript = scriptBuilder.criarClasseTeste();

        fullScript = fullScript.replace("\n\n\n\n", "\n\n");

        Log.d("SCRIPT", fullScript);

    }

    private enum TipoOrdem {
        VERIFICAR_DEPOIS_REPRODUZIR,
        REPRODUZIR_DEPOIS_VERIFICAR,
        VERIFICAR,
        NONE, REPRODUZIR
    }

    private enum TipoExtraMethods {
        SELECT, SCROLL, FOCUS, GET_CHILD_TEXT, PROGRESS
    }

    private class ScriptBuilder {

        private final MethodBuilder methodBuilder;

        public ScriptBuilder() {
            methodBuilder = new MethodBuilder();
        }

        private String criarScript() {

            String scriptAcoes = construirScriptAcoesVerificacoes();
            scriptAcoes = scriptAcoes.replace("\n", "\n\t");
            scriptAcoes = "\n\n" + "@Test"
                    + "\n" + "public void teste() throws Exception {"
                    + "\n" + scriptAcoes
                    + "\n" + "}"
                    + criarMetodosExtras();
            return scriptAcoes;
        }

        private String criarMetodosExtras() {

            String scriptMetodosExtras = "";
            for (TipoExtraMethods extra :
                    tipoExtraMethods) {
                switch (extra) {
                    case PROGRESS:
                        scriptMetodosExtras += methodBuilder.getProgressMethodDefinition();
                        break;
                    case SELECT:
                        scriptMetodosExtras += methodBuilder.getElementUsingTextAndScrollMethodDefinition();
                        break;
                    case SCROLL:
                        scriptMetodosExtras += methodBuilder.getElementUsingIdAndScrollMethodDefinition();
                        break;
                    case FOCUS:
                        scriptMetodosExtras += methodBuilder.getFocusAssertionMethodDefinition();
                        break;
                    case GET_CHILD_TEXT:
                        scriptMetodosExtras += methodBuilder.getChildTextMethodDefinition();
                        break;
                }
            }

            return scriptMetodosExtras;
        }

        private String criarClasseTeste() {

            String packageName = setup.getPackageName();
            fullScript = fullScript.replace("\n", "\n\t");

            String packages = "package " + packageName + ";";

            String imports =
                    "\n" + "import org.junit.After;"
                            + "\n" + "import org.junit.Assert;"
                            + "\n" + "import org.junit.Before;"
                            + "\n" + "import org.junit.Test;"
                            + "\n" + "import org.openqa.selenium.By;"
                            + "\n" + "import org.openqa.selenium.remote.DesiredCapabilities;"
                            + "\n" + "import java.io.File;"
                            + "\n" + "import java.net.MalformedURLException;"
                            + "\n" + "import java.net.URL;"
                            + "\n" + "import java.util.concurrent.TimeUnit;"
                            + "\n" + "import io.appium.java_client.TouchAction;"
                            + "\n" + "import io.appium.java_client.android.AndroidDriver;"
                            + "\n" + "import io.appium.java_client.android.AndroidElement;"
                            + "\n" + "import io.appium.java_client.remote.MobileCapabilityType;"
                            + "\n" + "import io.appium.java_client.touch.offset.PointOption;";

            String classe =
                    "\n\n" + "public class " + nomeTeste + " {"
                            + "\n\t" + "private AndroidDriver<AndroidElement> driver = null;"
                            + fullScript
                            + "\n" + "}";

            return packages + imports + classe;
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

        private String construirScriptAcoesVerificacoes() {

            String scriptCompleto = "";

            for (Estado estado :
                    estados) {


                String elementName = estado.getIdentificadorElemento();
                String elementId = elementName;


                StringBuilder estadoTexto = estado.getEstadoTexto();
                StringBuilder estadoSelecao = estado.getEstadoSelecao();
                Estado.Foco estadoFoco = estado.getEstadoFoco();
                Integer estadoProgresso = estado.getEstadoProgresso();

                String findElementById = methodBuilder.getFindElementByIdMethod(elementId, elementName);
                String click = methodBuilder.getClickMethod(elementName);
                String clear = methodBuilder.getClearMethod(elementName);
                String sendKeys = methodBuilder.getSendKeysMethod(elementName, utils.getSafeString(estadoTexto));
                String scrollTo = methodBuilder.getScrollToMethodById(elementId);
                String progress = methodBuilder.getProgressMethod(elementName, estadoProgresso);
                String selecItem = methodBuilder.getSelectItemMethod(elementName, utils.getSafeString(estadoSelecao));

                String verificaoTexto = methodBuilder.getTextAssertionMethod(elementName, utils.getSafeString(estadoTexto));
                String verificaoFoco = "";
                String verificaoSelecao = "";

                if (estadoFoco != null && estadoFoco != Estado.Foco.IGNORAR) {
                    verificaoFoco = methodBuilder.getFocusAssertionMethod(elementName, estadoFoco);
                    utils.addExtraMethod(TipoExtraMethods.FOCUS);
                }

                if (estadoSelecao != null) {
                    verificaoSelecao = methodBuilder.getSpinnerAssertionMethod(elementName, utils.getSafeString(estadoSelecao));
                    utils.addExtraMethod(TipoExtraMethods.GET_CHILD_TEXT);
                }

                List<Estado.TipoAcao> passos = estado.getAcoes();

                if (passos.contains(Estado.TipoAcao.ROLAR)) {
                    scriptCompleto += scrollTo;
                    utils.addExtraMethod(TipoExtraMethods.SCROLL);
                }

                if (!scriptCompleto.contains(findElementById)) {
                    scriptCompleto += findElementById;
                }

                TipoOrdem tipoOrdem = utils.getOrdem(passos);

                for (Estado.TipoAcao acao :
                        passos) {

                    if (acao != null) {
                        String scriptAcoes = "";


                        switch (acao) {

                            case FOCAR:
                                if (estadoFoco == Estado.Foco.FOCADO) {
                                    scriptAcoes = click;
                                } else if (estadoFoco == Estado.Foco.SEM_FOCO) {
                                    scriptAcoes = methodBuilder.getLongPressMethod(elementName, 66);
                                }

                                scriptCompleto += utils.construirComandoEmOrdem(tipoOrdem, scriptAcoes, verificaoFoco);
                                break;

                            case ESCREVER:
                                if (estadoTexto != null) {

                                    if (estadoTexto.toString().isEmpty()) {
                                        scriptAcoes = clear;

                                    } else {
                                        scriptAcoes = sendKeys;
                                    }
                                }
                                scriptCompleto += utils.construirComandoEmOrdem(tipoOrdem, scriptAcoes, verificaoTexto);
                                break;

                            case SELECIONAR:
                                if (estadoSelecao != null) {
                                    scriptAcoes = selecItem;
                                    utils.addExtraMethod(TipoExtraMethods.SELECT);
                                }
                                scriptCompleto += utils.construirComandoEmOrdem(tipoOrdem, scriptAcoes, verificaoSelecao);
                                break;

                            case CLICAR:
                                scriptCompleto += click;
                                break;

                            case PROGREDIR:
                                scriptCompleto += progress;
                                utils.addExtraMethod(TipoExtraMethods.PROGRESS);
                                break;
                        }
                    }
                }

                scriptCompleto += "\n\n";
            }


            return scriptCompleto;


        }
    }

    private class MethodBuilder {
        private String getProgressMethod(String elementName, Integer estadoProgresso) {
            String method = "\n" + "progressTo(" + elementName + ", " + estadoProgresso + ");";
            return method;
        }

        private String getLongPressMethod(String elementName, int keyCode) {
            String method = "\n" + "driver.longPressKeyCode(" + keyCode + ");";
            return method;
        }

        private String getSpinnerAssertionMethod(String elementName, String estadoSelecao) {

            String method = "\n" + "Assert.assertEquals(\"" + estadoSelecao + "\", getChildText(" + elementName + ", 0));";
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
            String method = "\n" + "Assert.assertEquals(" + focar + ", elementHasFocus(" + elementName + "));";
            return method;
        }

        private String getSelectItemMethod(String elementName, String estadoSelecao) {


            String method = getClickMethod(elementName)
                    + "\n" + "getElementUsingTextAndScroll(" + "\"" + estadoSelecao + "\").click();";

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

        private String getScrollToMethodById(String elementName) {
            String method = "\n" + "getElementUsingIdAndScroll(" + "\"" + elementName + "\");";
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

        private String getElementUsingTextAndScrollMethodDefinition() {

            String method =
                    "\n\n" + "public AndroidElement getElementUsingTextAndScroll(String texto){"
                            + "\n\t" + "return driver.findElementByAndroidUIAutomator(\"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(" + "\\" + "\"\"+texto+\"\\" + "\"" + ").instance(0))\");"
                            + "\n" + "}";

            return method;
        }

        private String getElementUsingIdAndScrollMethodDefinition() {

            String method =
                    "\n\n" + "public AndroidElement getElementUsingIdAndScroll(String texto){"
                            + "\n\t" + "return driver.findElementByAndroidUIAutomator(\"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(" + "\\" + "\".*\"+texto+\"\\" + "\"" + ").instance(0))\");"
                            + "\n" + "}";

            return method;
        }

        public String getProgressMethodDefinition() {
            String method =
                    "\n\n" + "public void progressTo(AndroidElement seekBar, int progress) {"
                            + "\n\t" + "int width = seekBar.getSize().getWidth();"
                            + "\n\t" + "int progressToX = progress * width /100;"
                            + "\n\t" + "int startX = seekBar.getLocation().getX();"
                            + "\n\t" + "int yAxis = seekBar.getLocation().getY();"
                            + "\n\t" + "int moveToXDirectionAt = progressToX;"
                            + "\n\n\t" + "PointOption from = new PointOption();"
                            + "\n\t" + "from.withCoordinates(startX, yAxis);"
                            + "\n\n\t" + "PointOption to = new PointOption();"
                            + "\n\t" + "to.withCoordinates(moveToXDirectionAt, yAxis);"
                            + "\n\n\t" + "TouchAction action=new TouchAction(driver);"
                            + "\n\t" + "action.longPress(from).moveTo(to).release().perform();"
                            + "\n" + "}";
            return method;
        }


        public String getFocusAssertionMethodDefinition() {
            String method =
                    "\n\n" + "private boolean elementHasFocus(AndroidElement element) {"
                            + "\n\t" + "return element" + ".getCenter().equals(" + getFocusElementMethod() + ".getCenter());"
                            + "\n" + "}";
            return method;
        }

        public String getChildTextMethodDefinition() {
            String method =
                    "\n\n" + "private String getChildText(AndroidElement element, int index) {"
                            + "\n\t" + "return  element.findElementByAndroidUIAutomator(\"new UiSelector().index(\"+index+\")\").getText();"
                            + "\n" + "}";
            return method;
        }
    }

    private class Utils {
        private String gerarNomeTeste() {
            return "ClasseTesteTCC3";
        }

        private String getSafeString(StringBuilder stringBuilder) {
            if (stringBuilder != null) {
                return stringBuilder.toString();
            } else {
                return "";
            }
        }

        private void addExtraMethod(TipoExtraMethods progress) {
            if (!tipoExtraMethods.contains(progress)) {
                tipoExtraMethods.add(progress);
            }
        }


        private String construirComandoEmOrdem(TipoOrdem tipoOrdem, String reproduzir, String verificar) {

            switch (tipoOrdem) {
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

        private TipoOrdem getOrdem(List<Estado.TipoAcao> passos) {

            TipoOrdem tipoOrdem = TipoOrdem.NONE;

            for (Estado.TipoAcao acao :
                    passos) {
                if (acao == Estado.TipoAcao.REPRODUZIR) {
                    if (tipoOrdem == TipoOrdem.NONE) {
                        tipoOrdem = TipoOrdem.REPRODUZIR;
                    }
                    if (tipoOrdem == TipoOrdem.VERIFICAR) {
                        tipoOrdem = TipoOrdem.VERIFICAR_DEPOIS_REPRODUZIR;
                    }
                }

                if (acao == Estado.TipoAcao.VERIFICAR) {
                    if (tipoOrdem == TipoOrdem.NONE) {
                        tipoOrdem = TipoOrdem.VERIFICAR;
                    }
                    if (tipoOrdem == TipoOrdem.REPRODUZIR) {
                        tipoOrdem = TipoOrdem.REPRODUZIR_DEPOIS_VERIFICAR;
                    }
                }

            }

            return tipoOrdem;
        }

    }


}
