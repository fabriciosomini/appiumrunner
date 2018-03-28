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
        StringBuilder estadoTexto = estado.getEstadoTexto();
        StringBuilder estadoSelecao = estado.getEstadoSelecao();
        boolean reproduzirPassos = estado.getReproduzirPassos();
        Estado.Foco estadoFoco = estado.getEstadoFoco();

        String elementName = estado.getIdentificadorElemento();

        String findElementByid = getFindElementByIdMethod(elementName, elementName);
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
        script = "";
        criacao = new Criacao();
    }
}
