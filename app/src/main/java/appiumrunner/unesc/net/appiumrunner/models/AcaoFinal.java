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
        registrador.registrar(estado);
        return this;
    }

    public AcaoFinal verificar() {

        estado.getAcoes().add(Estado.TipoAcao.VERIFICAR);
        registrador.registrar(estado);
        return this;
    }
    /*public Boolean concluir() {

        registrador.registrar(estado);
        return true;
    }*/

}
