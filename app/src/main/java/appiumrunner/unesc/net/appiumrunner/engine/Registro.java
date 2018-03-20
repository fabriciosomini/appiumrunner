package appiumrunner.unesc.net.appiumrunner.engine;

import appiumrunner.unesc.net.appiumrunner.states.Estado;

/**
 * Created by fabri on 18/03/2018.
 */

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

        boolean estadoFoco = estado.getEstadoFoco();
        String estadoTexto = estado.getEstadoTexto();
        Estado.Verificao verificao = estado.getVerificacao();


        String elementName = estado.getIdentificadorElemento();

        String findElementByid = "\n" + "WebElement " + elementName + " = driver.findElement(By.id(\"" + elementName + "\"));";
        String click = "\n" + elementName + ".click();";
        String clear = "\n" + elementName + ".clear();";
        String sendKeys = "\n" + elementName + ".sendKeys(\"" + estadoTexto + "\");";

        String verificaoFoco = "\n" + "Assert.assertEquals(" + elementName + ".equals(driver.switchTo().activeElement()), true);";
        String verificaoTexto = "\n" + "Assert.assertEquals(" + elementName + ".getText(), \"" + estadoTexto + "\");";


        if (!script.contains(findElementByid)) {
            script += findElementByid;
        }

        if (estadoFoco) {
            script += click;

            if (verificao == Estado.Verificao.POR_PROPRIEDA_ESTADO) {
                script += verificaoFoco;
            }

        }

        if (estadoTexto == null) {
            script += clear;

            if (verificao == Estado.Verificao.POR_PROPRIEDA_ESTADO) {
                script += verificaoTexto;
            }

        } else {
            script += sendKeys;

            if (verificao == Estado.Verificao.POR_PROPRIEDA_ESTADO) {
                script += verificaoTexto;
            }
        }

        if (verificao == Estado.Verificao.FINAL_ESTADO) {
            script += verificaoFoco;
            script += verificaoTexto;

        }

        if (autoSave) {
            criacao.criar(script);
        }


    }


    public void stop() {
        criacao.criar(script);
    }
}
