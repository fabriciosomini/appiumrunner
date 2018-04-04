package appiumrunner.unesc.net.appiumrunner.models;

import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class AcaoFinal {


    protected Estado estado;
    Registrador registrador;


    public AcaoFinal(Registrador registrador) {
        this.registrador = registrador;
    }

    public AcaoFinal reproduzir() {
        estado.getAcoes().add(Estado.TipoAcao.REPRODUZIR);
        return this;
    }

    public AcaoFinal verificar() {

        estado.getAcoes().add(Estado.TipoAcao.VERIFICAR);
        return this;
    }
    public Boolean finalizar() {

        registrador.registrar(estado);
        return true;
    }

}
