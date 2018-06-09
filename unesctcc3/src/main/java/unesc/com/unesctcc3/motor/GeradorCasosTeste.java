package unesc.com.unesctcc3.motor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.modelos.Preferencias;
import unesc.com.unesctcc3.modelos.Setup;
import unesc.com.unesctcc3.modelos.Teste;
import unesc.com.unesctcc3.utilitarios.MetodosUtilitario;


/**
 * Created by fabri on 18/03/2018.
 */
public class GeradorCasosTeste {
    private String fullScript;
    private ArrayList<Atividade> atividades;
    private Preferencias preferencias;
    private Setup setup;
    private Utilitarios utilitarios;
    private String nomeTeste;
    private Set<TipoExtraMethods> tipoExtraMethods;

    public GeradorCasosTeste(Setup setup, Preferencias preferencias) {

        inicializar(setup, preferencias);
    }


    public GeradorCasosTeste() {
        this(null, null);
    }


    private void inicializar(Setup setup, Preferencias preferencias) {
        fullScript = null;
        atividades = null;
        this.preferencias = preferencias == null ? new Preferencias() : preferencias;
        this.setup = setup;
        utilitarios = new Utilitarios();
        nomeTeste = utilitarios.gerarNomeTeste();
        tipoExtraMethods = new HashSet<>();
    }

    public Teste gerar(ArrayList<Atividade> atividades, String nomeTeste) {

        this.nomeTeste = nomeTeste == null ? this.nomeTeste : nomeTeste;
        MontadorTestes montadorTestes = new MontadorTestes();
        this.atividades = atividades;
        Teste teste = montadorTestes.montarTesteCompleto();
        fullScript = montadorTestes.montarSetup();
        fullScript += teste.getCasoTeste();
        if (!this.preferencias.isSkipTearDownDeclaration()) {
            String teardownScript;
            teardownScript = montadorTestes.montarTeardown();
            fullScript += teardownScript;
        }
        fullScript = montadorTestes.montarClasseTeste();
        fullScript = fullScript.replace("\n\n\n\n", "\n\n");


        teste.setCasoTeste(fullScript);
        inicializar(this.setup, this.preferencias);

        return teste;
    }

    private enum TipoOrdem {
        VERIFICAR_DEPOIS_REPRODUZIR,
        REPRODUZIR_DEPOIS_VERIFICAR,
        VERIFICAR,
        REPRODUZIR,
        NENHUM
    }

    private enum TipoExtraMethods {
        SELECT, SCROLL, ISFOCUSED, GET_CHILD_TEXT, CHECK, ISCHECKED, PRESSKEY, ISDISPLAYED, SELECT_LIST_ITEM, PROGRESS
    }

    private class MontadorTestes {
        private final MontadorMetodos montadorMetodos;
        private String glogalVariablesScript;

        public MontadorTestes() {
            montadorMetodos = new MontadorMetodos();
            glogalVariablesScript = "";
        }

        private Teste montarTesteCompleto() {
            Teste teste = montarCasoTeste();
            String scriptAcoes = teste.getCasoTeste();
            scriptAcoes = scriptAcoes.replace("\n", "\n\t");
            scriptAcoes = "\n\n" + "@Test"
                    + "\n" + "public void teste() throws Exception {"
                    + "\n" + scriptAcoes
                    + "\n" + "}"
                    + montarMetodosExtras();

            teste.setCasoTeste(scriptAcoes);
            String documentacao = teste.getDocumentacao();
            String[] lines = documentacao.split("\n");
            documentacao = "";
            for (int i = 1; i < lines.length; i++) {
                String str = lines[i];
                str = "Passo " + (i) + ": " + str + "\n";
                documentacao += str;
            }
            documentacao = "Documentação do caso de teste" + "\n-----------------------------\n"
                    + documentacao;
            teste.setDocumentacao(documentacao);

            return teste;
        }

        private String montarMetodosExtras() {
            String scriptMetodosExtras = "";
            if (preferencias != null) {
                if (preferencias.isSkipMethodsDeclaration()) {
                    return scriptMetodosExtras;
                }
            }
            for (TipoExtraMethods extra :
                    tipoExtraMethods) {
                switch (extra) {
                    case PROGRESS:
                        scriptMetodosExtras += montadorMetodos.getProgressMethodDefinition();
                        break;
                    case SELECT:
                        scriptMetodosExtras += montadorMetodos.getElementUsingTextAndScrollMethodDefinition();
                        break;
                    case SELECT_LIST_ITEM:
                        scriptMetodosExtras += montadorMetodos.getElementUsingParentIdAndTextAndScrollMethodDefinition();
                        break;
                    case SCROLL:
                        scriptMetodosExtras += montadorMetodos.getElementByIdAndScrollToMethodDefinition();
                        break;
                    case ISFOCUSED:
                        scriptMetodosExtras += montadorMetodos.getElementHasFocusMethodDefinition();
                        break;
                    case GET_CHILD_TEXT:
                        scriptMetodosExtras += montadorMetodos.getChildTextMethodDefinition();
                        break;
                    case CHECK:
                        scriptMetodosExtras += montadorMetodos.getCheckMethodDefinition();
                        break;
                    case ISCHECKED:
                        scriptMetodosExtras += montadorMetodos.getIsOptionCheckedMethodDefinition();
                        break;
                    case PRESSKEY:
                        scriptMetodosExtras += montadorMetodos.getPressKeyMethodDefinition();
                        break;
                    case ISDISPLAYED:
                        scriptMetodosExtras += montadorMetodos.getVisibilityMethodDefinition();
                        break;
                }
            }
            return scriptMetodosExtras;
        }

        private String montarClasseTeste() {
            String addedImports = "";
            if (preferencias.getPackages() != null) {
                for (String packageName : preferencias.getPackages()) {
                    addedImports += "\n" + "import " + packageName + ";";
                }
            }
            String extendedClass = "";
            boolean extendDriver = false;
            if (preferencias.getExtendedClass() != null) {
                extendedClass = " extends " + preferencias.getExtendedClass();
                extendDriver = preferencias.isExtendDriver();
            }
            String packageName = preferencias.getTestPackageName() == null ?
                    "appiumrunner.unesc.net.appiumrunner" : preferencias.getTestPackageName();
            fullScript = fullScript.replace("\n", "\n\t");

            if (!glogalVariablesScript.isEmpty()) {
                glogalVariablesScript = "\n" + glogalVariablesScript;
            }

            String driverDeclaration = "";
            if (!extendDriver) {
                driverDeclaration = "\n\t" + "private AndroidDriver<AndroidElement> driver = null;";
            }

            String packages = "package " + packageName + ";";
            String imports =
                    "\n" + "import org.junit.After;"
                            + "\n" + "import org.junit.Assert;"
                            + "\n" + "import org.junit.Before;"
                            + "\n" + "import org.junit.Test;"
                            + "\n" + "import org.openqa.selenium.WebDriverException;"
                            + "\n" + "import org.openqa.selenium.remote.DesiredCapabilities;"
                            + "\n" + "import org.openqa.selenium.support.PageFactory;"
                            + "\n" + "import java.io.File;"
                            + "\n" + "import java.net.MalformedURLException;"
                            + "\n" + "import java.net.URL;"
                            + "\n" + "import java.time.Duration;"
                            + "\n" + "import java.util.concurrent.TimeUnit;"
                            + "\n" + "import io.appium.java_client.TouchAction;"
                            + "\n" + "import io.appium.java_client.android.AndroidDriver;"
                            + "\n" + "import io.appium.java_client.android.AndroidElement;"
                            + "\n" + "import io.appium.java_client.android.AndroidKeyCode;"
                            + "\n" + "import io.appium.java_client.pagefactory.AndroidFindBy;"
                            + "\n" + "import io.appium.java_client.pagefactory.AppiumFieldDecorator;"
                            + "\n" + "import io.appium.java_client.remote.MobileCapabilityType;"
                            + "\n" + "import io.appium.java_client.touch.offset.PointOption;"
                            + addedImports;
            String classe =
                    "\n\n" + "public class " + nomeTeste + extendedClass + " {"
                            + driverDeclaration
                            + glogalVariablesScript
                            + fullScript
                            + "\n" + "}";
            return packages + imports + classe;
        }

        private String montarSetup() {
            if (setup != null) {
                String platformVersion = setup.getPlatformVersion();
                String deviceName = setup.getDeviceName();
                String appDirectory = setup.getAppDirectory();
                String apkName = setup.getApkName();
                String appiumServerAddress = setup.getAppiumServerAddress();

                if (platformVersion == null || platformVersion.isEmpty()) {
                    throw new RuntimeException("É necessário definir um valor para a propriedade 'platformVersion' no Setup do teste");
                }

                if (deviceName == null || deviceName.isEmpty()) {
                    throw new RuntimeException("É necessário definir um valor para a propriedade 'deviceName' no Setup do teste");
                }

                if (appDirectory == null || appDirectory.isEmpty()) {
                    throw new RuntimeException("É necessário definir um valor para a propriedade 'appDirectory' no Setup do teste");
                }

                if (apkName == null || apkName.isEmpty()) {
                    throw new RuntimeException("É necessário definir um valor para a propriedade 'apkName' no Setup do teste");
                }

                if (appiumServerAddress == null || appiumServerAddress.isEmpty()) {
                    throw new RuntimeException("É necessário definir um valor para a propriedade 'appiumServerAddress' no Setup do teste");
                }

                appDirectory = appDirectory.replace("\\", "\\\\");
                fullScript =
                        "\n" + "@Before"
                                + "\n" + "public void setup() {"
                                + "\n\t" + "File app = new File(\"" + appDirectory + "\", \"" + apkName + "\");"
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
                                + "\n\t" + "driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);"
                                + "\n\t" + "PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);"
                                + "\n" + "}";
            }
            return fullScript;
        }

        private String montarTeardown() {
            String teardown =
                    "\n\n" + "@After"
                            + "\n" + "public void tearDown() {"
                            + "\n\t" + "if (driver != null) {"
                            + "\n\t" + "driver.quit();"
                            + "\n\t" + "}"
                            + "\n" + "}";
            return teardown;
        }


        private Teste montarCasoTeste() {
            MontadorDocumentacao montadorDocumentacao = new MontadorDocumentacao();
            Teste teste = new Teste();
            String scriptCompleto = "";
            String documentacao = "";
            for (Atividade atividade :
                    atividades) {
                String elementName = (String) MetodosUtilitario.invocarMetodo(atividade, "getIdentificadorElemento");
                String elementId = elementName;
                if (elementId.startsWith("screen:")) {
                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId,
                            TipoOrdem.REPRODUZIR,
                            Atividade.TipoAtividade.ACESSO_TELA,
                            false);
                } else {
                    Atividade.Tecla estadoTecla = (Atividade.Tecla) MetodosUtilitario.invocarMetodo(atividade, "getEstadoTecla");
                    Atividade.Marcacao estadoMarcacaoOpcao = (Atividade.Marcacao) MetodosUtilitario.invocarMetodo(atividade, "getEstadoMarcacaoOpcao");
                    StringBuilder estadoTexto = (StringBuilder) MetodosUtilitario.invocarMetodo(atividade, "getEstadoTexto");
                    StringBuilder estadoTextoLimpo = (StringBuilder) MetodosUtilitario.invocarMetodo(atividade, "getEstadoTextoLimpo");
                    StringBuilder estadoLeitura = (StringBuilder) MetodosUtilitario.invocarMetodo(atividade, "getEstadoLeitura");
                    StringBuilder estadoSelecao = (StringBuilder) MetodosUtilitario.invocarMetodo(atividade, "getEstadoSelecao");
                    StringBuilder estadoSelecaoLista = (StringBuilder) MetodosUtilitario.invocarMetodo(atividade, "getEstadoSelecaoLista");
                    Atividade.Foco estadoFoco = (Atividade.Foco) MetodosUtilitario.invocarMetodo(atividade, "getEstadoFoco");
                    Atividade.Foco estadoDesfoque = (Atividade.Foco) MetodosUtilitario.invocarMetodo(atividade, "getEstadoDesfoque");
                    Atividade.Visibilidade estadoVisibilidade = (Atividade.Visibilidade) MetodosUtilitario.invocarMetodo(atividade, "getEstadoVisibilidade");
                    Integer estadoProgresso = (Integer) MetodosUtilitario.invocarMetodo(atividade, "getEstadoProgresso");
                    List<Atividade.TipoAtividade> tipoAtividades = (List<Atividade.TipoAtividade>) MetodosUtilitario.invocarMetodo(atividade, "getTipoAtividades");
                    String findElementByIdCall = montadorMetodos.getFindElementByIdMethod(elementId, elementName);
                    String clickCall = montadorMetodos.getClickMethod(elementName);
                    String focusCall = montadorMetodos.getFocusElementMethod(elementName);
                    String clearCall = montadorMetodos.getClearMethod(elementName);
                    String setValueCall = montadorMetodos.getSetValueMethod(elementName, utilitarios.getSafeString(estadoTexto));
                    String scrollToCall = montadorMetodos.getScrollToMethodById(elementId);
                    String progressCall = montadorMetodos.getProgressMethod(elementName, estadoProgresso);
                    String selectItemCall = montadorMetodos.getElementUsingTextAndScrollToItemMethod(elementName, utilitarios.getSafeString(estadoSelecao));
                    String pickListItemCall = montadorMetodos.getElementUsingParentIdAndTextAndScrollMethod(elementName, utilitarios.getSafeString(estadoSelecaoLista));
                    String checkOptionCall = montadorMetodos.getCheckMethod(elementName, estadoMarcacaoOpcao);
                    String pressKeyCall = montadorMetodos.getPressKeyMethod(estadoTecla);
                    String verificacaoVisibilidade = montadorMetodos.getVisibilityAssertionMethod(elementName, estadoVisibilidade);
                    String verificaoSelecao = montadorMetodos.getSpinnerAssertionMethod(elementName, utilitarios.getSafeString(estadoSelecao));
                    String verificaoSelecaoLista = ""; //TODO
                    String verificarProgresso = montadorMetodos.getProgressAssertionMethod(elementName, estadoProgresso);
                    String verificacaoOpcaoMarcada = estadoMarcacaoOpcao == null ? montadorMetodos.getCheckAsssertionMethod(elementName, Atividade.Marcacao.MARCADO) :
                            montadorMetodos.getCheckAsssertionMethod(elementName, estadoMarcacaoOpcao);
                    if (tipoAtividades.contains(Atividade.TipoAtividade.FOCAR)
                            && tipoAtividades.contains(Atividade.TipoAtividade.VERIFICAR)
                            && estadoFoco != null && estadoFoco != Atividade.Foco.IGNORAR) {
                        utilitarios.addExtraMethod(TipoExtraMethods.ISFOCUSED);
                    }

                    if (tipoAtividades.contains(Atividade.TipoAtividade.DESFOCAR)
                            && tipoAtividades.contains(Atividade.TipoAtividade.VERIFICAR)
                            && estadoDesfoque != null && estadoDesfoque != Atividade.Foco.IGNORAR) {
                        utilitarios.addExtraMethod(TipoExtraMethods.ISFOCUSED);
                    }

                    //Marcar opção desmarcável usa o método isOptionChecked na ação e na asserção, então adicione
                    if (tipoAtividades.contains(Atividade.TipoAtividade.MARCAR_OPCAO_DESMARCAVEL)) {
                        utilitarios.addExtraMethod(TipoExtraMethods.ISCHECKED);
                    }
                    //Marcar opcao não usa o método isOptionChecked, então apenas adicione se tiver asserção
                    if (tipoAtividades.contains(Atividade.TipoAtividade.MARCAR_OPCAO) && tipoAtividades.contains(Atividade.TipoAtividade.VERIFICAR)) {
                        utilitarios.addExtraMethod(TipoExtraMethods.ISCHECKED);
                    }
                    if (tipoAtividades.contains(Atividade.TipoAtividade.SELECIONAR_COMBO)
                            && tipoAtividades.contains(Atividade.TipoAtividade.VERIFICAR)
                            && estadoSelecao != null) {
                        utilitarios.addExtraMethod(TipoExtraMethods.GET_CHILD_TEXT);
                    }
                    if (tipoAtividades.contains(Atividade.TipoAtividade.ROLAR)) {
                        scriptCompleto += scrollToCall;
                        utilitarios.addExtraMethod(TipoExtraMethods.SCROLL);
                    }
                    if (tipoAtividades.contains(Atividade.TipoAtividade.MARCAR_OPCAO) ||
                            tipoAtividades.contains(Atividade.TipoAtividade.MARCAR_OPCAO_DESMARCAVEL)) {
                        utilitarios.addExtraMethod(TipoExtraMethods.CHECK);
                    }
                    if (elementName != null && !glogalVariablesScript.contains(findElementByIdCall)) {
                        glogalVariablesScript += findElementByIdCall;
                    }
                    TipoOrdem tipoOrdem = utilitarios.getOrdem(tipoAtividades);
                    for (Atividade.TipoAtividade acao :
                            tipoAtividades) {
                        if (acao != null) {
                            String scriptAcoes = "";
                            Atividade.Foco estadoFocoDesfoque = null;
                            String texto = "";
                            if (acao == Atividade.TipoAtividade.FOCAR) {
                                estadoFocoDesfoque = Atividade.Foco.FOCADO;
                            }
                            if (acao == Atividade.TipoAtividade.DESFOCAR) {
                                estadoFocoDesfoque = Atividade.Foco.SEM_FOCO;
                            }
                            if (acao == Atividade.TipoAtividade.ESCREVER) {
                                texto = estadoTexto.toString();
                            }
                            if (acao == Atividade.TipoAtividade.LIMPAR) {
                                texto = estadoTextoLimpo.toString();
                            }
                            if (acao == Atividade.TipoAtividade.LER) {
                                texto = estadoLeitura.toString();
                            }
                            String verificaoFoco = montadorMetodos.getFocusAssertionMethod(elementName, estadoFocoDesfoque);
                            String verificaoTexto = montadorMetodos.getTextAssertionMethod(elementName, texto);
                            switch (acao) {
                                case CLICAR:
                                    scriptCompleto += clickCall;
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, false);
                                    break;
                                case PRESSIONAR:
                                    scriptCompleto += pressKeyCall;
                                    utilitarios.addExtraMethod(TipoExtraMethods.PRESSKEY);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, false, utilitarios.getSafeString(estadoTecla));
                                    break;
                                case FOCAR:
                                    if (estadoFoco == Atividade.Foco.FOCADO) {
                                        scriptAcoes = focusCall;
                                    }
                                    utilitarios.addExtraMethod(TipoExtraMethods.ISFOCUSED);
                                    scriptCompleto += utilitarios.montarComandosNaOrdem(tipoOrdem, scriptAcoes, verificaoFoco);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, !verificaoFoco.isEmpty());
                                    break;
                                case DESFOCAR:
                                    if (estadoDesfoque == Atividade.Foco.SEM_FOCO) {
                                        scriptAcoes = montadorMetodos.getPressKeyMethod(Atividade.Tecla.ENTER);
                                        utilitarios.addExtraMethod(TipoExtraMethods.PRESSKEY);
                                    }
                                    scriptCompleto += utilitarios.montarComandosNaOrdem(tipoOrdem, scriptAcoes, verificaoFoco);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, !verificaoFoco.isEmpty());
                                    break;
                                case ESCREVER:
                                    if (estadoTexto != null) {
                                        if (!estadoTexto.toString().isEmpty()) {
                                            scriptAcoes = setValueCall;
                                        }
                                    }
                                    scriptCompleto += utilitarios.montarComandosNaOrdem(tipoOrdem, scriptAcoes, verificaoTexto);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, !verificaoTexto.isEmpty(), utilitarios.getSafeString(estadoTexto));
                                    break;
                                case LIMPAR:
                                    scriptAcoes = clearCall;
                                    scriptCompleto += utilitarios.montarComandosNaOrdem(tipoOrdem, scriptAcoes, verificaoTexto);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, !verificaoTexto.isEmpty());
                                    break;
                                case LER:
                                    scriptCompleto += utilitarios.montarComandosNaOrdem(tipoOrdem, scriptAcoes, verificaoTexto);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, !verificaoTexto.isEmpty(), utilitarios.getSafeString(estadoLeitura));
                                    break;
                                case SELECIONAR_COMBO:
                                    if (estadoSelecao != null) {
                                        scriptAcoes = selectItemCall;
                                        utilitarios.addExtraMethod(TipoExtraMethods.SELECT);
                                    }
                                    scriptCompleto += utilitarios.montarComandosNaOrdem(tipoOrdem, scriptAcoes, verificaoSelecao);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, !verificaoSelecao.isEmpty(), utilitarios.getSafeString(estadoSelecao));
                                    break;
                                case SELECIONAR_LISTA:
                                    if (estadoSelecaoLista != null) {
                                        scriptAcoes = pickListItemCall;
                                        utilitarios.addExtraMethod(TipoExtraMethods.SELECT_LIST_ITEM);
                                    }
                                    scriptCompleto += utilitarios.montarComandosNaOrdem(tipoOrdem, scriptAcoes, verificaoSelecaoLista);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, !verificaoSelecaoLista.isEmpty(), utilitarios.getSafeString(estadoSelecaoLista));
                                    break;
                                case PROGREDIR:
                                    scriptAcoes = progressCall;
                                    scriptCompleto += utilitarios.montarComandosNaOrdem(tipoOrdem, scriptAcoes, verificarProgresso);
                                    utilitarios.addExtraMethod(TipoExtraMethods.PROGRESS);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, !verificarProgresso.isEmpty(), utilitarios.getSafeString(estadoProgresso));
                                    break;
                                case MARCAR_OPCAO:
                                    scriptAcoes = clickCall;
                                    scriptCompleto += utilitarios.montarComandosNaOrdem(tipoOrdem, scriptAcoes, verificacaoOpcaoMarcada);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, !verificacaoOpcaoMarcada.isEmpty());
                                    break;
                                case MARCAR_OPCAO_DESMARCAVEL:
                                    scriptAcoes = checkOptionCall;
                                    scriptCompleto += utilitarios.montarComandosNaOrdem(tipoOrdem, scriptAcoes, verificacaoOpcaoMarcada);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, !verificacaoOpcaoMarcada.isEmpty(), utilitarios.getSafeString(estadoMarcacaoOpcao));
                                    break;
                                case VISUALIZAR:
                                    scriptCompleto += utilitarios.montarComandosNaOrdem(tipoOrdem, scriptAcoes, verificacaoVisibilidade);
                                    utilitarios.addExtraMethod(TipoExtraMethods.ISDISPLAYED);
                                    documentacao += montadorDocumentacao.gerarDocumentacao(elementId, tipoOrdem, acao, !verificacaoVisibilidade.isEmpty(), utilitarios.getSafeString(estadoVisibilidade));
                                    break;
                            }
                        }
                    }
                    scriptCompleto += "\n\n";
                }
            }
            teste.setCasoTeste(scriptCompleto);
            teste.setDocumentacao(documentacao);
            return teste;
        }
    }

    private class MontadorMetodos {
        private String getProgressMethod(String elementName, Integer estadoProgresso) {
            String method = "\n" + "progressTo(" + elementName + ", " + estadoProgresso + ");";
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

        private String getFocusAssertionMethod(String elementName, Atividade.Foco foco) {
            boolean focar = false;
            if (foco == Atividade.Foco.FOCADO) {
                focar = true;
            }
            String method = "\n" + "Assert.assertEquals(" + focar + ", " + getElementHasFocusMethod(elementName) + ");";
            return method;
        }

        private String getElementHasFocusMethod(String elementName) {
            return "elementHasFocus(" + elementName + ")";
        }

        private String getElementUsingTextAndScrollToItemMethod(String elementName, String estadoSelecao) {
            String method = getClickMethod(elementName)
                    + "\n" + "getElementUsingTextAndScrollTo(\"" + estadoSelecao + "\").click();";
            return method;
        }

        private String getElementUsingParentIdAndTextAndScrollMethod(String elementName, String estadoSelecao) {
            String method = "\n" + "getElementUsingParentIdAndTextAndScrollTo(\"" + elementName + "\", \"" + estadoSelecao + "\").click();";
            return method;
        }

        private String getSetValueMethod(String elementName, String estadoTexto) {
            String method = "\n" + elementName + ".setValue(\"" + estadoTexto + "\");";
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
            String method = "\n" + "getElementByIdAndScrollTo(" + "\"" + elementName + "\");";
            return method;
        }

        private String getClickMethod(String elementName) {
            String method = "\n" + elementName + ".click();";
            return method;
        }

        private String getFindElementByIdMethod(String elementId, String variableName) {
            String method = "\n" + "@AndroidFindBy(id = \"" + elementId + "\")"
                    + "\n" + "AndroidElement " + variableName + ";"
                    + "\n";
            return method;
        }

        public String getFindFocusedElementMethod() {
            String method = "driver.findElementByAndroidUIAutomator(\"new UiSelector().focused(true)\")";
            return method;
        }

        private String getElementUsingParentIdAndTextAndScrollMethodDefinition() {
            String method =
                    "\n\n" + "public AndroidElement getElementUsingParentIdAndTextAndScrollTo(String id, String texto){"
                            + "\n\t" + "return driver.findElementByAndroidUIAutomator(\"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(" + "\\" + "\".*\"+id+\"\\" + "\"" + ").childSelector(new UiSelector().textContains(" + "\\" + "\"\"+texto+\"\\" + "\"" + ").instance(0)))\");"
                            + "\n" + "}";
            return method;
        }

        private String getElementUsingTextAndScrollMethodDefinition() {
            String method =
                    "\n\n" + "public AndroidElement getElementUsingTextAndScrollTo(String texto){"
                            + "\n\t" + "return driver.findElementByAndroidUIAutomator(\"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(" + "\\" + "\"\"+texto+\"\\" + "\"" + ").instance(0))\");"
                            + "\n" + "}";
            return method;
        }

        private String getElementByIdAndScrollToMethodDefinition() {
            String method =
                    "\n\n" + "public AndroidElement getElementByIdAndScrollTo(String id){"
                            + "\n\t" + "return driver.findElementByAndroidUIAutomator(\"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(" + "\\" + "\".*\"+id+\"\\" + "\"" + ").instance(0))\");"
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

        public String getElementHasFocusMethodDefinition() {
            String method =
                    "\n\n" + "private boolean elementHasFocus(AndroidElement element) {"
                            + "\n\t" + "return element" + ".getCenter().equals(" + getFindFocusedElementMethod() + ".getCenter());"
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

        public String getCheckMethodDefinition() {
            String method =
                    "\n\n" + "private void checkOption(AndroidElement element, boolean check) {"
                            + "\n\t" + "boolean isChecked =  Boolean.valueOf(element.getAttribute(\"checked\"));"
                            + "\n\t" + "if(isChecked != check){"
                            + "\n\t\t" + "element.click();"
                            + "\n\t" + "}"
                            + "\n" + "}";
            return method;
        }

        public String getIsOptionCheckedMethodDefinition() {
            String method =
                    "\n\n" + "private boolean isOptionChecked(AndroidElement element) {"
                            + "\n\t" + "boolean isChecked = Boolean.valueOf(element.getAttribute(\"checked\"));"
                            + "\n\t" + "return isChecked;"
                            + "\n" + "}";
            return method;
        }

        public String getProgressAssertionMethod(String elementName, int progresso) {
            String method = "";
            return method;
        }

        public String getCheckMethod(String elementName, Atividade.Marcacao marcacao) {
            boolean marcar = marcacao == Atividade.Marcacao.MARCADO;
            String method = "\n" + "checkOption( " + elementName + ", " + marcar + ");";
            return method;
        }

        public String getCheckAsssertionMethod(String elementName, Atividade.Marcacao marcacao) {
            boolean marcar = marcacao == Atividade.Marcacao.MARCADO;
            String method = "\n" + "Assert.assertEquals(" + marcar + ", isOptionChecked(" + elementName + "));";
            return method;
        }

        public String getPressKeyMethod(Atividade.Tecla estadoTecla) {
            String key = "-1";
            if (estadoTecla != null) {
                switch (estadoTecla) {
                    case VOLTAR:
                        key = "AndroidKeyCode.BACK";
                        break;
                    case ENTER:
                        key = "AndroidKeyCode.ENTER";
                        break;
                }
            }
            String method = "\n" + "pressKey(" + key + ");";
            return method;
        }

        public String getPressKeyMethodDefinition() {
            String method =
                    "\n\n" + "private void pressKey(int key) {"
                            + "\n\t" + "driver.pressKeyCode(key);"
                            + "\n" + "}";
            return method;
        }

        public String getFocusElementMethod(String elementName) {
            String method = "\nif(!" + getElementHasFocusMethod(elementName) + "){\n" + "\t" + elementName + ".click();\n}";
            return method;
        }

        public String getVisibilityMethodDefinition() {

            String method = "\n\n" + "private boolean isElementDisplayed(AndroidElement element) {"
                    + "\n\t" + "boolean result = false;"
                    + "\n\t" + "try{"
                    + "\n\t\t" + "result = element.isDisplayed();"
                    + "\n\t" + "}catch (WebDriverException ex){"
                    + "\n"
                    + "\n\t" + "}"
                    + "\n\t" + "return result;"
                    + "\n" + "}";

            return method;
        }

        public String getVisibilityAssertionMethod(String elementName, Atividade.Visibilidade visibilidade) {
            boolean visible = visibilidade == Atividade.Visibilidade.VISIVEL;
            String method = "\n" + "Assert.assertEquals(" + visible + ", isElementDisplayed(" + elementName + "));";
            return method;
        }

    }

    private class MontadorDocumentacao {
        public String gerarDocumentacao(String elementId, TipoOrdem tipoOrdem,
                                        Atividade.TipoAtividade acao, boolean isVerificar) {
            return gerarDocumentacao(elementId, tipoOrdem, acao, isVerificar, "");
        }

        public String gerarDocumentacao(String elementId, TipoOrdem tipoOrdem,
                                        Atividade.TipoAtividade acao, boolean isVerificar, String valor) {

            valor = valor == null ? "" : valor;
            String reproduzir = "";
            String verificacao = "";
            String documentacao = "";
            switch (acao) {
                case CLICAR:
                    reproduzir = "\n" + "Clicar no elemento '" + elementId + "'";
                    if (isVerificar) {
                        verificacao = "";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case PRESSIONAR:
                    reproduzir = "\n" + "Pressionar a tecla '" + valor + "'";
                    if (isVerificar) {
                        verificacao = "";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case FOCAR:
                    reproduzir = "\n" + "Focar no elemento '" + elementId + "'";
                    if (isVerificar) {
                        verificacao = "\n" + "Verificar se o elemento '" + elementId + "' está com foco";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case DESFOCAR:
                    reproduzir = "\n" + "Tirar o foco do elemento '" + elementId + "'";
                    if (isVerificar) {
                        verificacao = "\n" + "Verificar se o elemento '" + elementId + "' está sem o foco";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case ESCREVER:
                    reproduzir = "\n" + "Digitar o texto '" + valor + "' no elemento '" + elementId + "'";
                    if (isVerificar) {
                        verificacao = "\n" + "Verificar se o elemento '" + elementId + "' possui o texto '" + valor + "'";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case LIMPAR:
                    reproduzir = "\n" + "Limpar o conteúdo do elemento '" + elementId + "'";
                    if (isVerificar) {
                        verificacao = "\n" + "Verificar se o elemento '" + elementId + "' está sem nenhum valor";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case LER:
                    reproduzir = "";
                    if (isVerificar) {
                        verificacao = "\n" + "Verificar se o elemento '" + elementId + "' possui o valor '" + valor + "'";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case SELECIONAR_COMBO:
                    reproduzir = "\n" + "Selecionar a opção '" + valor + "' no elemento '" + elementId + "'";
                    if (isVerificar) {
                        verificacao = "\n" + "Verificar se o elemento '" + elementId + "' possui a opção '" + valor + "' selecionada";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case SELECIONAR_LISTA:
                    reproduzir = "\n" + "Selecionar o item '" + valor + "' no elemento '" + elementId + "'";
                    if (isVerificar) {
                        verificacao = "";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case PROGREDIR:
                    reproduzir = "\n" + "Progredir até '" + valor + "' no elemento '" + elementId + "'";
                    if (isVerificar) {
                        verificacao = "\n" + "Verificar se o elemento '" + elementId + "' possui o progresso de '" + valor + "'";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case MARCAR_OPCAO:
                    reproduzir = "\n" + "Marcar a opção única '" + elementId + "'";
                    if (isVerificar) {
                        verificacao = "\n" + "Verificar se a opção  '" + elementId + "' está 'marcada'";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case MARCAR_OPCAO_DESMARCAVEL:
                    if (valor != null) {
                        if (!valor.isEmpty()) {
                            if (valor.equals("true")) {
                                valor = "Marcar";
                            } else {
                                valor = "Desmarcar";
                            }
                        }
                    }
                    reproduzir = "\n" + valor + " a opção desmarcável '" + elementId + "'";
                    if (valor != null) {
                        if (!valor.isEmpty()) {
                            if (valor.equals(Atividade.Marcacao.MARCADO.name())) {
                                valor = "marcada";
                            } else {
                                valor = "desmarcada";
                            }
                        }
                    }
                    if (isVerificar) {
                        verificacao = "\n" + "Verificar se a opção  '" + elementId + "' está '" + valor + "'";
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case VISUALIZAR:
                    reproduzir = "";
                    if (isVerificar) {
                        if (valor != null) {
                            if (!valor.isEmpty()) {
                                if (valor.equals(Atividade.Visibilidade.VISIVEL.name())) {
                                    valor = "visível";
                                } else {
                                    valor = "oculto";
                                }
                            }
                        }
                        verificacao = "\n" + "Verificar se o elemento '" + elementId + "' está " + valor;
                    }
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, verificacao);
                    break;
                case ACESSO_TELA:
                    elementId = elementId.replace("screen:", "");
                    reproduzir = "\n" + "Acessar a tela '" + elementId + "'";
                    documentacao += utilitarios.montarComandosNaOrdem(tipoOrdem, reproduzir, "");
                    break;
            }
            return documentacao;
        }
    }


    private class Utilitarios {
        private String gerarNomeTeste() {
            return "CasoTesteAutomatizado";
        }

        private String getSafeString(StringBuilder stringBuilder) {
            if (stringBuilder != null) {
                return stringBuilder.toString();
            } else {
                return "";
            }
        }


        public String getSafeString(Integer integer) {
            if (integer != null) {
                return String.valueOf(integer);
            } else {
                return "";
            }
        }

        public String getSafeString(Enum visibilidade) {
            if (visibilidade != null) {
                return visibilidade.name();
            } else {
                return "";
            }
        }

        private void addExtraMethod(TipoExtraMethods progress) {
            if (!tipoExtraMethods.contains(progress)) {
                tipoExtraMethods.add(progress);
            }
        }

        private String montarComandosNaOrdem(TipoOrdem tipoOrdem, String reproduzir, String verificar) {
            switch (tipoOrdem) {
                case REPRODUZIR:
                    return reproduzir;
                case VERIFICAR:
                    return verificar;
                case NENHUM:
                    return "";
                case REPRODUZIR_DEPOIS_VERIFICAR:
                    return reproduzir + verificar;
                case VERIFICAR_DEPOIS_REPRODUZIR:
                    return verificar + reproduzir;
            }
            return null;
        }

        private TipoOrdem getOrdem(List<Atividade.TipoAtividade> passos) {
            TipoOrdem tipoOrdem = TipoOrdem.NENHUM;
            for (Atividade.TipoAtividade acao :
                    passos) {
                if (acao == Atividade.TipoAtividade.REPRODUZIR) {
                    if (tipoOrdem == TipoOrdem.NENHUM) {
                        tipoOrdem = TipoOrdem.REPRODUZIR;
                    }
                    if (tipoOrdem == TipoOrdem.VERIFICAR) {
                        tipoOrdem = TipoOrdem.VERIFICAR_DEPOIS_REPRODUZIR;
                    }
                }
                if (acao == Atividade.TipoAtividade.VERIFICAR) {
                    if (tipoOrdem == TipoOrdem.NENHUM) {
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
