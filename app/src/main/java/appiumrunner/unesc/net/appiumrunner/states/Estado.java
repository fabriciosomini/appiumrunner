package appiumrunner.unesc.net.appiumrunner.states;

import java.io.Serializable;
import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.engine.Registro;

/**
 * Created by fabri on 18/03/2018.
 */

public class Estado implements Serializable {
    private static ArrayList<Estado> steps;
    private final Registro registro;
    private Boolean focusedState;
    private Integer elementId;
    private String stateMessage;
    private String textState;
    private String hintState;
    private Integer count;

    public Estado(Registro registro) {
        this.registro = registro;
        if (steps == null) {
            steps = new ArrayList<>();
        }
    }

    public String getStateMessage() {
        return stateMessage;
    }

    public int getElementId() {
        return elementId;
    }

    public Estado setElementId(int elementIdState) {
        this.elementId = elementIdState;
        return this;
    }

    public Estado setFocusedState(boolean focusedState) {
        this.focusedState = focusedState;
        return this;
    }

    public Estado setTextState(String textState) {
        this.textState = textState;
        return this;
    }

    public Estado setHintState(String hintState) {
        this.hintState = hintState;
        return this;
    }

    public Estado setCount(Integer count) {
        this.count = count;
        return this;
    }

    public boolean record() {

        if (elementId == null) {
            stateMessage = "É necessário especificar o identificador do elemento";
            return false;
        }
        steps.add(this);
        if (steps.size() > 0) {
            stateMessage = "";
        }

        registro.registrar(this);

        return true;
    }


}
