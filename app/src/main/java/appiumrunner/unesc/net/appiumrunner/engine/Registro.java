package appiumrunner.unesc.net.appiumrunner.engine;

import java.lang.reflect.Field;

import appiumrunner.unesc.net.appiumrunner.states.Estado;

/**
 * Created by fabri on 18/03/2018.
 */

public class Registro {

    Criacao criacao;

    private String script;

    private boolean autoSave;

    public Registro() {
        criacao = new Criacao();
    }

    public void enableAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    public void registrar(Estado estado) {

        int elementId = estado.getElementId();
        String elementName = "element" + elementId;
        String findElementByid = "WebElement " + elementName + " = driver.findElement(By.id(" + elementId + "))";

        if (!script.contains(findElementByid)) {
            script += "\n" + findElementByid;
        }
        String click = elementName + ".click()";
        Field[] fields = estado.getClass().getDeclaredFields();
        if (fields.length > 0) {

        }

        if (autoSave) {
            criacao.write(script);
        }
        /*if(estado instanceof ButtonState){
           ButtonState estado = (ButtonState)estado;



        }*/
    }

    public void stop() {
        criacao.write(script);
    }
}
