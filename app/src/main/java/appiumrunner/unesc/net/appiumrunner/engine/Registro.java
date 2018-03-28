package appiumrunner.unesc.net.appiumrunner.engine;

import java.util.Random;

import appiumrunner.unesc.net.appiumrunner.states.Estado;

/**
 * Created by fabri on 18/03/2018.
 */

//TODO: Criar metodos para trazer as funcões montada, ao invez de montar em uma linha
//TODO: Verificar se todos os metodos do utilitario de estados estão aqui (ex: Verificar Barra Progresso)
public class Registro {

    private Criacao criacao;

    private String script = "";

    private boolean autoSave;

    public Registro() {
        criacao = new Criacao();
    }

    public void enableAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    //TODO: Adicionar suporte ao método findElement
    public void registrar(Estado estado) {

        Estado.Verificao verificao = estado.getVerificacao();
        String estadoTexto = estado.getEstadoTexto() == null ? "" : estado.getEstadoTexto();
        String estadoSelecao = estado.getEstadoSelecao() == null ? "" : estado.getEstadoSelecao();
        boolean reproduzirPassos = estado.getReproduzirPassos();
        boolean estadoFoco = estado.getEstadoFoco();

        String elementName = estado.getIdentificadorElemento();

        String findElementByid = getFindElementByIdMethod(elementName, elementName);
        String click = getClickMethod(elementName);
        String scrollToExact = getScrollToExactMethod(elementName, estadoSelecao);
        String clear = getClearMethod(elementName);
        String sendKeys = getSendKeysMethod(elementName, estadoTexto);
        String selecItem = getSelectItemMethod(elementName, estadoTexto);


        String verificaoFoco = getFocusAssertionMethod(elementName);
        String verificaoTexto = getTextAssertionMethod(elementName, estadoTexto);
        String verificaoSelecao = getSpinnerAssertionMethod(elementName, estadoSelecao);

        if (!script.contains(findElementByid)) {
            script += findElementByid;
        }

        if (estadoSelecao != "") {

            if (reproduzirPassos) {
                script += selecItem;
            }

            if (verificao == Estado.Verificao.POR_PROPRIEDA_ESTADO) {
                script += verificaoSelecao;
            }

        }

        if (estadoFoco) {

            if (reproduzirPassos) {
                script += click;
            }

            if (verificao == Estado.Verificao.POR_PROPRIEDA_ESTADO) {
                script += verificaoFoco;
            }

        }

        if (estadoTexto == "") {
            if (reproduzirPassos) {
                script += clear;
            }

            if (verificao == Estado.Verificao.POR_PROPRIEDA_ESTADO) {
                script += verificaoTexto;
            }

        } else {

            if (reproduzirPassos) {
                script += sendKeys;
            }

            if (verificao == Estado.Verificao.POR_PROPRIEDA_ESTADO) {
                script += verificaoTexto;
            }
        }

        if (verificao == Estado.Verificao.FINAL_ESTADO) {
            script += verificaoFoco;
            script += verificaoTexto;

        }

        script += "\n\n";

        if (autoSave) {
            criacao.criar(script);
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

    private String getFocusAssertionMethod(String elementName) {
        String method = "\n" + "Assert.assertEquals(" + elementName + ".equals(driver.switchTo().activeElement()), true);";
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

    private String getFindElementByIdMethod(String elementName, String variableName) {
        String method = "\n" + "WebElement " + variableName + " = driver.findElement(By.id(\"" + elementName + "\"));";

        return method;
    }

    private String getFindElementByNameMethod(String elementName, String variableName) {
        String method = "\n" + "WebElement " + variableName + " = driver.findElement(By.name(\"" + elementName + "\"));";

        return method;
    }


    public void stop() {
        criacao.criar(script);
    }
}
