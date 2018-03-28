package appiumrunner.unesc.net.appiumrunner.engine;

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

        boolean estadoFoco = estado.getEstadoFoco();
        String estadoTexto = estado.getEstadoTexto();
        String estadoSelecao = estado.getEstadoSelecao();
        Estado.Verificao verificao = estado.getVerificacao();
        boolean reproduzirPassos = estado.getReproduzirPassos();

        String elementName = estado.getIdentificadorElemento();

        String findElementByid = "\n" + "WebElement " + elementName + " = driver.findElement(By.id(\"" + elementName + "\"));";
        String click = "\n" + elementName + ".click();";
        String scrollToExact = "\n" + elementName + ".scrollToExact(\"" + estadoSelecao + "\");";
        String clear = "\n" + elementName + ".clear();";
        String sendKeys = "\n" + elementName + ".sendKeys(\"" + estadoTexto + "\");";
        String selecItem = click
                + scrollToExact
                + "\n" + "WebElement option=driver.findElement(By.name(" + estadoTexto + "));"
                + "\n" + "option.click();"; //TODO: O nome option é muito generico, ajustar


        String verificaoFoco = "\n" + "Assert.assertEquals(" + elementName + ".equals(driver.switchTo().activeElement()), true);";
        String verificaoTexto = "\n" + "Assert.assertEquals(" + elementName + ".getText(), \"" + estadoTexto + "\");";
        String verificaoSelecao = ""; //TODO: Estudar como se verifica a seleção de um spinner

        if (!script.contains(findElementByid)) {
            script += findElementByid;
        }

        if (estadoSelecao != null) {

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

        if (estadoTexto == null) {
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

        if (autoSave) {
            criacao.criar(script);
        }


    }


    public void stop() {
        criacao.criar(script);
    }
}
