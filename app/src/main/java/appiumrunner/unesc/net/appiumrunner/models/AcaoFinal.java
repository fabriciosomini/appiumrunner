package appiumrunner.unesc.net.appiumrunner.models;

import java.util.List;

import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.helpers.MethodInvoker;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class AcaoFinal {


    protected Estado estado;
    Registrador registrador;


    public AcaoFinal(Registrador registrador) {
        this.registrador = registrador;
    }

    public AcaoFinal reproduzirAcoes() {
        List<Estado.TipoAcao> acoes = (List<Estado.TipoAcao>) MethodInvoker.invoke(estado, "getAcoes");
        acoes.add(Estado.TipoAcao.REPRODUZIR);
        registrador.registrar(estado);
        return this;
    }
    public AcaoFinal verificarValores() {
        List<Estado.TipoAcao> acoes = (List<Estado.TipoAcao>) MethodInvoker.invoke(estado, "getAcoes");
        acoes.add(Estado.TipoAcao.VERIFICAR);
        registrador.registrar(estado);
        return this;
    }
}
